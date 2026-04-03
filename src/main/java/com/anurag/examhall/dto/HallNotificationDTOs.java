package com.anurag.examhall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class HallNotificationDTOs {

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class HallAllocationRequest {
        private String branch;
        private String section;
        private String startRoll;
        private String endRoll;
        private String hallName;
        private String roomNumber;
        private String subject;
        private String examDate;
        private String examTime;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class HallAllocationResponse {
        private Long id;
        private String branch;
        private String section;
        private String startRoll;
        private String endRoll;
        private String hallName;
        private String roomNumber;
        private String subject;
        private String examDate;
        private String examTime;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class NotificationRequest {
        private String message;
        private List<String> studentRolls; // null or empty = send to branch
        private String targetBranch;       // null = send to specific rolls
        private String date;
        private String time;
        private boolean sendSms;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class NotificationResponse {
        private Long id;
        private String message;
        private String studentRolls;
        private String targetBranch;
        private String date;
        private String time;
        private String sentBy;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ApiResponse {
        private boolean success;
        private String message;
        private Object data;
    }
}
