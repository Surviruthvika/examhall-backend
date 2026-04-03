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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;
    private final HallAllocationRepository hallAllocationRepository;
    private final NotificationRepository notificationRepository;

    public Student getStudentProfile(String rollNumber) {
        return studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Optional<HallAllocationResponse> getMyHallAllocation(String rollNumber) {
        Student student = getStudentProfile(rollNumber);

        return hallAllocationRepository.findAllocationForStudent(
                student.getBranch(), student.getSection(), rollNumber)
                .map(h -> HallAllocationResponse.builder()
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
                        .build());
    }

    public List<NotificationResponse> getMyNotifications(String rollNumber) {
        Student student = getStudentProfile(rollNumber);

        return notificationRepository.findNotificationsForStudent(rollNumber, student.getBranch())
                .stream()
                .map(n -> NotificationResponse.builder()
                        .id(n.getId())
                        .message(n.getMessage())
                        .studentRolls(n.getStudentRolls())
                        .targetBranch(n.getTargetBranch())
                        .date(n.getDate())
                        .time(n.getTime())
                        .sentBy(n.getSentBy())
                        .build())
                .collect(Collectors.toList());
    }
}
