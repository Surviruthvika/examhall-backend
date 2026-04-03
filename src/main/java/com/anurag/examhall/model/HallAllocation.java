package com.anurag.examhall.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hall_allocations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HallAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String branch;

    @Column(nullable = false)
    private String section;

    @Column(nullable = false)
    private String startRoll;

    @Column(nullable = false)
    private String endRoll;

    @Column(nullable = false)
    private String hallName;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private String subject;

    @Column
    private String examDate;

    @Column
    private String examTime;
}
