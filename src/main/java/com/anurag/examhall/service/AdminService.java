package com.anurag.examhall.service;

import com.anurag.examhall.dto.HallNotificationDTOs.*;
import com.anurag.examhall.model.HallAllocation;
import com.anurag.examhall.model.Notification;
import com.anurag.examhall.model.Student;
import com.anurag.examhall.repository.HallAllocationRepository;
import com.anurag.examhall.repository.NotificationRepository;
import com.anurag.examhall.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final StudentRepository studentRepository;
    private final HallAllocationRepository hallAllocationRepository;
    private final NotificationRepository notificationRepository;
    private final SmsService smsService;

    // ---- Students ----
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByBranch(String branch) {
        return studentRepository.findByBranch(branch.toUpperCase());
    }

    public List<Student> getStudentsByBranchAndSection(String branch, String section) {
        return studentRepository.findByBranchAndSection(branch.toUpperCase(), section.toUpperCase());
    }

    // ---- Hall Allocation ----
    public HallAllocationResponse allocateHall(HallAllocationRequest request, String adminEmail) {
        HallAllocation allocation = HallAllocation.builder()
                .branch(request.getBranch().toUpperCase())
                .section(request.getSection().toUpperCase())
                .startRoll(request.getStartRoll())
                .endRoll(request.getEndRoll())
                .hallName(request.getHallName())
                .roomNumber(request.getRoomNumber())
                .subject(request.getSubject())
                .examDate(request.getExamDate())
                .examTime(request.getExamTime())
                .build();

        HallAllocation saved = hallAllocationRepository.save(allocation);
        log.info("Hall allocated by {} for branch {} section {}", adminEmail, request.getBranch(), request.getSection());
        return toHallResponse(saved);
    }

    public List<HallAllocationResponse> getAllAllocations() {
        return hallAllocationRepository.findAll().stream()
                .map(this::toHallResponse)
                .collect(Collectors.toList());
    }

    public List<HallAllocationResponse> getAllocationsByBranch(String branch) {
        return hallAllocationRepository.findByBranch(branch.toUpperCase()).stream()
                .map(this::toHallResponse)
                .collect(Collectors.toList());
    }

    public void deleteAllocation(Long id) {
        hallAllocationRepository.deleteById(id);
    }

    // ---- Notifications ----
    public NotificationResponse sendNotification(NotificationRequest request, String adminEmail) {
        String rollsString = null;
        List<Student> targetStudents;

        if (request.getTargetBranch() != null && !request.getTargetBranch().isEmpty()) {
            // Send to entire branch
            targetStudents = studentRepository.findByBranch(request.getTargetBranch().toUpperCase());
            rollsString = "ALL_" + request.getTargetBranch().toUpperCase();
        } else {
            // Send to specific rolls
            List<String> rolls = request.getStudentRolls();
            rollsString = String.join(",", rolls);
            targetStudents = rolls.stream()
                    .map(roll -> studentRepository.findByRollNumber(roll).orElse(null))
                    .filter(s -> s != null)
                    .collect(Collectors.toList());
        }

        Notification notification = Notification.builder()
                .message(request.getMessage())
                .studentRolls(rollsString)
                .targetBranch(request.getTargetBranch())
                .date(request.getDate())
                .time(request.getTime())
                .sentBy(adminEmail)
                .build();

        Notification saved = notificationRepository.save(notification);

        // Send SMS if enabled
        if (request.isSendSms()) {
            String smsText = String.format(
                "[Anurag University] %s\nDate: %s | Time: %s",
                request.getMessage(), request.getDate(), request.getTime()
            );
            for (Student student : targetStudents) {
                try {
                    smsService.sendSms(student.getPhone(), smsText);
                } catch (Exception e) {
                    log.error("SMS failed for {}: {}", student.getRollNumber(), e.getMessage());
                }
            }
        }

        log.info("Notification sent by {} to {} students", adminEmail, targetStudents.size());
        return toNotifResponse(saved);
    }

    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAllByOrderByIdDesc().stream()
                .map(this::toNotifResponse)
                .collect(Collectors.toList());
    }

    // ---- Mappers ----
    private HallAllocationResponse toHallResponse(HallAllocation h) {
        return HallAllocationResponse.builder()
                .id(h.getId())
                .branch(h.getBranch())
                .section(h.getSection())
                .startRoll(h.getStartRoll())
                .endRoll(h.getEndRoll())
                .hallName(h.getHallName())
                .roomNumber(h.getRoomNumber())
                .subject(h.getSubject())
                .examDate(h.getExamDate())
                .examTime(h.getExamTime())
                .build();
    }

    private NotificationResponse toNotifResponse(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId())
                .message(n.getMessage())
                .studentRolls(n.getStudentRolls())
                .targetBranch(n.getTargetBranch())
                .date(n.getDate())
                .time(n.getTime())
                .sentBy(n.getSentBy())
                .build();
    }
}
