package com.anurag.examhall.controller;

import com.anurag.examhall.dto.AuthDTOs.*;
import com.anurag.examhall.dto.HallNotificationDTOs.ApiResponse;
import com.anurag.examhall.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/admin/signup")
    public ResponseEntity<ApiResponse> adminSignup(@RequestBody AdminSignupRequest request) {
        try {
            AuthResponse response = authService.adminSignup(request);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true).message("Admin registered successfully").data(response).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .success(false).message(e.getMessage()).build());
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<ApiResponse> adminLogin(@RequestBody AdminLoginRequest request) {
        try {
            AuthResponse response = authService.adminLogin(request);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true).message("Login successful").data(response).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .success(false).message(e.getMessage()).build());
        }
    }

    @PostMapping("/student/signup")
    public ResponseEntity<ApiResponse> studentSignup(@RequestBody StudentSignupRequest request) {
        try {
            AuthResponse response = authService.studentSignup(request);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true).message("Student registered successfully").data(response).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .success(false).message(e.getMessage()).build());
        }
    }

    @PostMapping("/student/login")
    public ResponseEntity<ApiResponse> studentLogin(@RequestBody StudentLoginRequest request) {
        try {
            AuthResponse response = authService.studentLogin(request);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true).message("Login successful").data(response).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .success(false).message(e.getMessage()).build());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse> health() {
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true).message("Exam Hall Locator API is running").build());
    }
}
