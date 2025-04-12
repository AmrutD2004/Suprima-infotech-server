package com.example.attendance_api.repository;

import com.example.attendance_api.model.Attendance;
import com.example.attendance_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUserOrderByTimestampDesc(User user);

    List<Attendance> findByUserAndTimestampBetween(User user, LocalDateTime start, LocalDateTime end);
}