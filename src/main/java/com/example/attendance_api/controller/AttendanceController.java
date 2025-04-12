package com.example.attendance_api.controller;

import com.example.attendance_api.dto.ApiResponse;
import com.example.attendance_api.dto.AttendanceRequest;
import com.example.attendance_api.model.Attendance;
import com.example.attendance_api.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/record")
    public ResponseEntity<ApiResponse> recordAttendance(@Valid @RequestBody AttendanceRequest request) {
        try {
            Attendance attendance = attendanceService.recordAttendance(
                    request.getUserId(),
                    request.getType(),
                    request.getBase64Image(),
                    request.getLatitude(),
                    request.getLongitude()
            );

            return ResponseEntity.ok(new ApiResponse(true, "Attendance recorded successfully", attendance));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserAttendance(@PathVariable Long userId) {
        try {
            List<Attendance> attendanceList = attendanceService.getUserAttendance(userId);
            return ResponseEntity.ok(new ApiResponse(true, "Attendance records retrieved", attendanceList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}/date/{date}")
    public ResponseEntity<ApiResponse> getUserAttendanceByDate(
            @PathVariable Long userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<Attendance> attendanceList = attendanceService.getUserAttendanceByDate(userId, date);
            return ResponseEntity.ok(new ApiResponse(true, "Attendance records retrieved", attendanceList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
}