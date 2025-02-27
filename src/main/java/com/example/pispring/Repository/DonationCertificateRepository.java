package com.example.pispring.Repository;

import com.example.pispring.Entities.DonationCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationCertificateRepository extends JpaRepository<DonationCertificate, Integer> {
    Page<DonationCertificate> findByUserUserIdOrderByDateIssuedDesc(Integer userId, Pageable pageable);
}
