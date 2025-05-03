package com.example.testintegration.controller;


import com.example.testintegration.entities.Credit;
import com.example.testintegration.entities.Remboursement;
import com.example.testintegration.services.CreditService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/*
@RestController
@RequestMapping("/api/credits")
public class CreditController {

    @Autowired
    private CreditService creditService;

    @PostMapping
    public ResponseEntity<Credit> createCredit(@RequestBody Credit credit) {
        Credit createdCredit = creditService.createCredit(credit);
        return ResponseEntity.ok(createdCredit);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Credit> getCreditById(@PathVariable Long id) {
        Credit credit = creditService.getCreditById(id);
        return ResponseEntity.ok(credit);
    }

    @GetMapping
    public ResponseEntity<List<Credit>> getAllCredits() {
        List<Credit> credits = creditService.getAllCredits();
        return ResponseEntity.ok(credits);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCredit(@PathVariable Long id, @RequestBody Credit creditDetails) {
        try {
            Credit updatedCredit = creditService.updateCredit(id, creditDetails);
            return ResponseEntity.ok(updatedCredit);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating credit: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
        return ResponseEntity.noContent().build();
    }
}*/

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
@RestController
@RequestMapping("/api/credits")
public class CreditController {

    private static final Logger logger = LoggerFactory.getLogger(CreditController.class);

    @Autowired
    private CreditService creditService;

    @PostMapping
    public ResponseEntity<?> createCredit(@Valid @RequestBody Credit credit) {
        try {
            logger.info("Received POST request to create credit: {}", credit);
            Credit createdCredit = creditService.createCredit(credit);
            return ResponseEntity.ok(createdCredit);
        } catch (Exception e) {
            logger.error("Error creating credit: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error creating credit: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCreditById(@PathVariable Long id) {
        try {
            logger.info("Received GET request for credit ID: {}", id);
            Credit credit = creditService.getCreditById(id);
            return ResponseEntity.ok(credit);
        } catch (Exception e) {
            logger.error("Error fetching credit with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("Error fetching credit: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCredits() {
        try {
            logger.info("Received GET request for all credits");
            List<Credit> credits = creditService.getAllCredits();
            return ResponseEntity.ok(credits);
        } catch (Exception e) {
            logger.error("Error fetching all credits: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error fetching all credits: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCredit(@PathVariable Long id, @Valid @RequestBody Credit creditDetails) {
        try {
            logger.info("Received PUT request to update credit ID: {} with details: {}", id, creditDetails);
            Credit updatedCredit = creditService.updateCredit(id, creditDetails);
            return ResponseEntity.ok(updatedCredit);
        } catch (Exception e) {
            logger.error("Error updating credit with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("Error updating credit: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCredit(@PathVariable Long id) {
        try {
            logger.info("Received DELETE request for credit ID: {}", id);
            creditService.deleteCredit(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting credit with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("Error deleting credit: " + e.getMessage());
        }
    }
}*/

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credits")
public class CreditController {

    private static final Logger logger = LoggerFactory.getLogger(CreditController.class);

    @Autowired
    private CreditService creditService;

    @PostMapping
    public ResponseEntity<?> createCredit(@Valid @RequestBody Credit credit) {
        try {
            logger.info("Received POST request to create credit: {}", credit);
            Credit createdCredit = creditService.createCredit(credit);
            return ResponseEntity.ok(createdCredit);
        } catch (Exception e) {
            logger.error("Error creating credit: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error creating credit: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCreditById(@PathVariable Long id) {
        try {
            logger.info("Received GET request for credit ID: {}", id);
            Credit credit = creditService.getCreditByIdWithRemboursements(id);
            return ResponseEntity.ok(credit);
        } catch (Exception e) {
            logger.error("Error fetching credit with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("Error fetching credit: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCredits() {
        try {
            logger.info("Received GET request for all credits");
            List<Credit> credits = creditService.getAllCredits();
            return ResponseEntity.ok(credits);
        } catch (Exception e) {
            logger.error("Error fetching all credits: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error fetching all credits: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCredit(@PathVariable Long id, @Valid @RequestBody Credit creditDetails) {
        try {
            logger.info("Received PUT request to update credit ID: {} with details: {}", id, creditDetails);
            Credit updatedCredit = creditService.updateCredit(id, creditDetails);
            return ResponseEntity.ok(updatedCredit);
        } catch (Exception e) {
            logger.error("Error updating credit with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("Error updating credit: " + e.getMessage());
        }
    }
/*
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/
@Transactional
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteCredit(@PathVariable Long id) {
    try {
        logger.info("Received DELETE request for credit ID: {}", id);
        creditService.deleteCredit(id);
        return ResponseEntity.noContent().build();
    } catch (Exception e) {
        logger.error("Error deleting credit with ID {}: {}", id, e.getMessage(), e);
        if (e.getMessage().contains("Credit not found")) {
            return ResponseEntity.status(404).body("Credit not found with id: " + id);
        }
        return ResponseEntity.status(500).body("Error deleting credit: " + e.getMessage());
    }
}

    @GetMapping("/search")
    public ResponseEntity<List<CreditResponse>> searchAndFilterCredits(
            @RequestParam(required = false) Credit.Gender gender,
            @RequestParam(required = false) Credit.Married married,
            @RequestParam(required = false) Credit.Education education,
            @RequestParam(required = false) Credit.SelfEmployed selfEmployed,
            @RequestParam(required = false) Credit.CreditType creditType,
            @RequestParam(required = false) Credit.CreditStatus creditStatus,
            @RequestParam(required = false) Credit.InterestType interestType,
            @RequestParam(required = false) Double minLoanAmount,
            @RequestParam(required = false) Double maxLoanAmount,
            @RequestParam(required = false) Integer minLoanTerm,
            @RequestParam(required = false) Integer maxLoanTerm
    ) {
        try {
            List<Credit> credits = creditService.searchAndFilterCredits(

                    gender,
                    married,
                    education,
                    selfEmployed,
                    creditType,
                    creditStatus,
                    interestType,
                    minLoanAmount,
                    maxLoanAmount,
                    minLoanTerm,
                    maxLoanTerm
            );

            List<CreditResponse> responses = new ArrayList<>();
            /*for (Credit credit : credits) {
                List<Remboursement> repaymentSchedule = creditService.getRepaymentSchedule(credit);
                CreditResponse response = new CreditResponse(credit, repaymentSchedule);
                responses.add(response);
            }*/

            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            // return ResponseEntity.status(500).body("Failed to search and filter credits: " + e.getMessage());
            return null;
        }
    }
}