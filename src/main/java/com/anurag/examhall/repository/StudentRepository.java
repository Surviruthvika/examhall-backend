package com.anurag.examhall.repository;

import com.anurag.examhall.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByRollNumber(String rollNumber);
    List<Student> findByBranch(String branch);
    List<Student> findByBranchAndSection(String branch, String section);
    boolean existsByRollNumber(String rollNumber);
    boolean existsByPhone(String phone);
}
