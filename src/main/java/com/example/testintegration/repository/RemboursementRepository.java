package com.example.testintegration.repository;


import com.example.testintegration.entities.Credit;
import com.example.testintegration.entities.Remboursement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RemboursementRepository extends JpaRepository<Remboursement, Long> {
    List<Remboursement> findByCredit(Credit credit);
    void deleteByCredit(Credit credit);
}