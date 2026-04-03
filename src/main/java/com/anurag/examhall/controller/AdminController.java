package com.anurag.examhall.controller;

import com.anurag.examhall.dto.HallNotificationDTOs.*;
import com.anurag.examhall.model.Student;
import com.anurag.examhall.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ---- Students ----
    @GetMapping("/students")
    public ResponseEntity<ApiResponse> getAllStudents() {
        List<Student> students = adminService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true).message("Students fetched").data(students).build());
    }

    @GetMapping("/students/branch/{branch}")
    public ResponseEntity<ApiResponse> getStudentsByBranch(@PathVariable String branch) {
        List<Student> students = adminService.getStudentsByBranch(branch);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true).message("Students fetched").data(students).build());
    }

    @GetMapping("/students/branch/{branch}/section/{section}")
    public ResponseEntity<ApiResponse> getStudentsByBranchAndSection(
            @PathVariable String branch, @PathVariable String section) {
        List<Student> students = adminService.getStudentsByBranchAndSection(branch, section);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true).message("Students fetched").data(students).build());
    }

    // ---- Hall Allocation ----
    @PostMapping("/allocate")
    public ResponseEntity<ApiResponse> allocateHall(
            @RequestBody HallAllocationRequest request, Authentication auth) {
        try {
            HallAllocationResponse response = adminService.allocateHall(request, auth.getName());
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true).message("Hall allocated successfully").data(response).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .success(false).message(e.getMessage()).build());
        }
    }

    @GetMapping("/allocations")
    public ResponseEntity<ApiResponse> getAllAllocations() {
        List<HallAllocationResponse> allocations = adminService.getAllAllocations();
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true).message("Allocations fetched").data(allocations).build());
    }

    @GetMapping("/allocations/branch/{branch}")
    public ResponseEntity<ApiResponse> getAllocationsByBranch(@PathVariable String branch) {
        List<HallAllocationResponse> allocations = adminService.getAllocationsByBranch(branch);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true).message("Allocations fetched").data(allocations).build());
    }

    @DeleteMapping("/allocations/{id}")
    public ResponseEntity<ApiResponse> deleteAllocation(@PathVariable Long id) {
        try {
            adminService.deleteAllocation(id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true).message("Allocation deleted").build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .success(false).message(e.getMessage()).build());
        }
    }

    // ---- Notifications ----
    @PostMapping("/notify")
    public ResponseEntity<ApiResponse> sendNotification(
            @RequestBody NotificationRequest request, Authentication auth) {
        try {
            NotificationResponse response = adminService.sendNotification(request, auth.getName());
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true).message("Notification sent successfully").data(response).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .success(false).message(e.getMessage()).build());
        }
    }

    @GetMapping("/notifications")
    public ResponseEntity<ApiResponse> getAllNotifications() {
        List<NotificationResponse> notifications = adminService.getAllNotifications();
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true).message("Notifications fetched").data(notifications).build());
    }
}
