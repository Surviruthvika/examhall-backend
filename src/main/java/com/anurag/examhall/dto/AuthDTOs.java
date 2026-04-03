package com.anurag.examhall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ---- Auth DTOs ----

public class AuthDTOs {

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AdminSignupRequest {
        private String email;
        private String password;
        private String name;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AdminLoginRequest {
        private String email;
        private String password;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class StudentSignupRequest {
        private String rollNumber;
        private String name;
        private String branch;
        private String section;
        private Integer year;
        private String phone;
        private String password;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class StudentLoginRequest {
        private String rollNumber;
        private String password;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AuthResponse {
        private String token;
        private String role;
        private String identifier; // email for admin, rollNumber for student
        private String name;
        private String branch;
        private String section;
        private String message;
    }
}
