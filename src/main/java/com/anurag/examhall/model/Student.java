package com.anurag.examhall.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @Column(name = "roll_number", nullable = false, unique = true)
    private String rollNumber;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotBlank
    private String branch;

    @Column(nullable = false)
    @NotBlank
    private String section;

    @Column
    private Integer year;

    @Column(nullable = false)
    @NotBlank
    private String phone;

    @Column(nullable = false)
    private String password;
}
