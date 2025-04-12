package com.example.attendance_api.service;

import com.example.attendance_api.model.Attendance;
import com.example.attendance_api.model.User;
import com.example.attendance_api.repository.AttendanceRepository;
import com.example.attendance_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    private String uploadDir = "uploads/attendance/";

    public AttendanceService() {
        // Create upload directory if it doesn't exist
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public Attendance recordAttendance(Long userId, String type, String base64Image, Double latitude, Double longitude) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setType(type);
        attendance.setTimestamp(LocalDateTime.now());
        attendance.setLatitude(latitude);
        attendance.setLongitude(longitude);

        // Save image if provided
        if (base64Image != null && !base64Image.isEmpty()) {
            String imageUrl = saveImage(base64Image, userId);
            attendance.setImageUrl(imageUrl);
        }

        return attendanceRepository.save(attendance);
    }

    private String saveImage(String base64Image, Long userId) throws IOException {
        // Remove base64 prefix if present (e.g., "data:image/jpeg;base64,")
        if (base64Image.contains(",")) {
            base64Image = base64Image.split(",")[1];
        }

        // Decode base64 to byte array
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Generate unique filename
        String filename = userId + "_" + UUID.randomUUID().toString() + ".jpg";
        String filePath = uploadDir + filename;

        // Save to file system
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(imageBytes);
        fos.close();

        return filePath;
    }

    public List<Attendance> getUserAttendance(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        return attendanceRepository.findByUserOrderByTimestampDesc(user);
    }

    public List<Attendance> getUserAttendanceByDate(Long userId, LocalDate date) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return attendanceRepository.findByUserAndTimestampBetween(user, startOfDay, endOfDay);
    }
}