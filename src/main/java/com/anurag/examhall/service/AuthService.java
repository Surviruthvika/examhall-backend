package com.anurag.examhall.service;

import com.anurag.examhall.config.JwtUtil;
import com.anurag.examhall.dto.AuthDTOs.*;
import com.anurag.examhall.model.Admin;
import com.anurag.examhall.model.Student;
import com.anurag.examhall.repository.AdminRepository;
import com.anurag.examhall.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse adminSignup(AdminSignupRequest request) {
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Admin with this email already exists");
        }

        Admin admin = Admin.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();

        adminRepository.save(admin);
        String token = jwtUtil.generateToken(admin.getEmail(), "ADMIN");

        return AuthResponse.builder()
                .token(token)
                .role("ADMIN")
                .identifier(admin.getEmail())
                .name(admin.getName())
                .message("Admin registered successfully")
                .build();
    }

    public AuthResponse adminLogin(AdminLoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(admin.getEmail(), "ADMIN");

        return AuthResponse.builder()
                .token(token)
                .role("ADMIN")
                .identifier(admin.getEmail())
                .name(admin.getName())
                .message("Login successful")
                .build();
    }

    public AuthResponse studentSignup(StudentSignupRequest request) {
        if (studentRepository.existsByRollNumber(request.getRollNumber())) {
            throw new RuntimeException("Student with this roll number already exists");
        }

        Student student = Student.builder()
                .rollNumber(request.getRollNumber())
                .name(request.getName())
                .branch(request.getBranch().toUpperCase())
                .section(request.getSection().toUpperCase())
                .year(request.getYear())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        studentRepository.save(student);
        String token = jwtUtil.generateToken(student.getRollNumber(), "STUDENT");

        return AuthResponse.builder()
                .token(token)
                .role("STUDENT")
                .identifier(student.getRollNumber())
                .name(student.getName())
                .branch(student.getBranch())
                .section(student.getSection())
                .message("Student registered successfully")
                .build();
    }

    public AuthResponse studentLogin(StudentLoginRequest request) {
        Student student = studentRepository.findByRollNumber(request.getRollNumber())
                .orElseThrow(() -> new RuntimeException("Invalid roll number or password"));

        if (!passwordEncoder.matches(request.getPassword(), student.getPassword())) {
            throw new RuntimeException("Invalid roll number or password");
        }

        String token = jwtUtil.generateToken(student.getRollNumber(), "STUDENT");

        return AuthResponse.builder()
                .token(token)
                .role("STUDENT")
                .identifier(student.getRollNumber())
                .name(student.getName())
                .branch(student.getBranch())
                .section(student.getSection())
                .message("Login successful")
                .build();
    }
}
