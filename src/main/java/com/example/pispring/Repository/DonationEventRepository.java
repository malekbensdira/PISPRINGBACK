package com.example.pispring.Repository;

import com.example.pispring.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationEventRepository extends JpaRepository<DonationEvent, Integer> {}
