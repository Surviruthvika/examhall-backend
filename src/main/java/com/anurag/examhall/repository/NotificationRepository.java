package com.anurag.examhall.repository;

import com.anurag.examhall.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByTargetBranch(String branch);
    
    @Query("SELECT n FROM Notification n WHERE n.studentRolls LIKE %:rollNumber% OR n.targetBranch = :branch")
    List<Notification> findNotificationsForStudent(@Param("rollNumber") String rollNumber, @Param("branch") String branch);
    
    List<Notification> findAllByOrderByIdDesc();
}
