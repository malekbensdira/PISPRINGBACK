package com.example.pispring.Repository;

import com.example.pispring.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT COUNT(c) FROM Cart c WHERE c.user.userId = :userId AND c.datepanier BETWEEN :startDate AND :endDate")
    int countByUserIdAndDateBetween(@Param("userId") int userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);



}