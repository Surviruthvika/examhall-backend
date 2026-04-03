package com.anurag.examhall.controller;

import com.anurag.examhall.dto.HallNotificationDTOs.*;
import com.anurag.examhall.model.Student;
import com.anurag.examhall.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(Authentication auth) {
        try {
            Student student = studentService.getStudentProfile(auth.getName());
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true).message("Profile fetched").data(student).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .success(false).message(e.getMessage()).build());
        }
    }

    @GetMapping("/hall-allocation")
    public ResponseEntity<ApiResponse> getHallAllocation(Authentication auth) {
        try {
            Optional<HallAllocationResponse> allocation =
                    studentService.getMyHallAllocation(auth.getName());
            if (allocation.isPresent()) {
                return ResponseEntity.ok(ApiResponse.builder()
                        .success(true).message("Hall allocation found").data(allocation.get()).build());
            } else {
                return ResponseEntity.ok(ApiResponse.builder()
                        .success(true).message("No hall allocation found yet").data(null).build());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .success(false).message(e.getMessage()).build());
        }
    }

    @GetMapping("/notifications")
    public ResponseEntity<ApiResponse> getNotifications(Authentication auth) {
        try {
            List<NotificationResponse> notifications =
                    studentService.getMyNotifications(auth.getName());
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true).message("Notifications fetched").data(notifications).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .success(false).message(e.getMessage()).build());
        }
    }
}
