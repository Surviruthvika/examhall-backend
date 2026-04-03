package com.anurag.examhall.repository;

import com.anurag.examhall.model.HallAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HallAllocationRepository extends JpaRepository<HallAllocation, Long> {
    List<HallAllocation> findByBranch(String branch);
    List<HallAllocation> findByBranchAndSection(String branch, String section);

    @Query("SELECT h FROM HallAllocation h WHERE h.branch = :branch AND h.section = :section " +
           "AND :rollNumber >= h.startRoll AND :rollNumber <= h.endRoll")
    Optional<HallAllocation> findAllocationForStudent(
            @Param("branch") String branch,
            @Param("section") String section,
            @Param("rollNumber") String rollNumber);
}
