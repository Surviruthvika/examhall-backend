package com.anurag.examhall.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(columnDefinition = "TEXT")
    private String studentRolls; // comma-separated roll numbers, or "ALL_<BRANCH>"

    @Column
    private String targetBranch; // null means specific rolls

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String time;

    @Column
    private String sentBy; // admin email
}
