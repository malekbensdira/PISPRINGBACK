package com.example.testintegration.services;



import com.example.testintegration.entities.Credit;
import com.example.testintegration.entities.Remboursement;
import com.example.testintegration.repository.CreditRepository;
import com.example.testintegration.repository.RemboursementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/*
@Service
public class CreditService {

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private RemboursementRepository remboursementRepository;

    // CRUD Operations
    @Transactional
    public Credit createCredit(Credit credit) {
        credit.setCreditStatus(Credit.CreditStatus.PENDING);
        Credit savedCredit = creditRepository.save(credit);
        List<Remboursement> repaymentSchedule = getRepaymentSchedule(savedCredit);
        saveRemboursements(savedCredit, repaymentSchedule);
        return creditRepository.save(savedCredit);
    }

    public Credit getCreditById(Long id) {
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credit not found with id: " + id));
        credit.setRemboursements(remboursementRepository.findByCredit(credit));
        return credit;
    }
/*
    public List<Credit> getAllCredits() {
        List<Credit> credits = creditRepository.findAll();
        for (Credit credit : credits) {
            credit.setRemboursements(remboursementRepository.findByCredit(credit));
        }
        return credits;
    }*/

/*
private static final Logger logger = LoggerFactory.getLogger(CreditService.class);

    public List<Credit> getAllCredits() {
        logger.info("Fetching all credits");
        List<Credit> credits = creditRepository.findAll();
        logger.info("Found {} credits", credits.size());
        for (Credit credit : credits) {
            credit.setRemboursements(remboursementRepository.findByCredit(credit));
            logger.info("Loaded remboursements for credit ID {}: {}", credit.getCreditId(), credit.getRemboursements().size());
        }
        logger.info("Returning credits");
        return credits;
    }

    @Transactional
    public Credit updateCredit(Long id, Credit creditDetails) {
        Credit credit = getCreditById(id);
        credit.setGender(creditDetails.getGender());
        credit.setMarried(creditDetails.getMarried());
        credit.setEducation(creditDetails.getEducation());
        credit.setSelfEmployed(creditDetails.getSelfEmployed());
        credit.setIncome(creditDetails.getIncome());
        credit.setLoanAmount(creditDetails.getLoanAmount());
        credit.setLoanTerm(creditDetails.getLoanTerm());
        credit.setCreditType(creditDetails.getCreditType());
        credit.setCreditStatus(creditDetails.getCreditStatus());
        credit.setInterestType(creditDetails.getInterestType());

        if (creditDetails.getLoanAmount() != null || creditDetails.getLoanTerm() != null || creditDetails.getInterestType() != null) {
            remboursementRepository.deleteByCredit(credit);
            List<Remboursement> repaymentSchedule = getRepaymentSchedule(credit);
            saveRemboursements(credit, repaymentSchedule);
        }
        return creditRepository.save(credit);
    }

    @Transactional
    public void deleteCredit(Long id) {
        Credit credit = getCreditById(id);
        remboursementRepository.deleteByCredit(credit);
        creditRepository.delete(credit);
    }

    // Repayment Schedule Calculation
    public List<Remboursement> getRepaymentSchedule(Credit credit) {
        Double loanAmount = credit.getLoanAmount();
        Integer loanTerm = credit.getLoanTerm();
        Double annualInterestRate = 0.05; // 5% example rate
        Double monthlyInterestRate = annualInterestRate / 12.0;

        switch (credit.getInterestType()) {
            case BLOC:
                return generateRemboursementEnBlocSchedule(loanAmount, annualInterestRate, loanTerm);
            case CONSTANT:
                return generateAmortissementConstantSchedule(loanAmount, monthlyInterestRate, loanTerm);
            case NON_CONSTANT:
                return generateAmortissementNotConstantSchedule(loanAmount, monthlyInterestRate, loanTerm);
            default:
                throw new IllegalArgumentException("Invalid interest type: " + credit.getInterestType());
        }
    }

    private List<Remboursement> generateRemboursementEnBlocSchedule(Double loanAmount, Double annualInterestRate, Integer loanTerm) {
        List<Remboursement> remboursements = new ArrayList<>();
        Double totalRepayment = loanAmount * (1 + annualInterestRate * (loanTerm / 12.0));

        Remboursement remboursement = new Remboursement();
        remboursement.setMonth(loanTerm);
        remboursement.setAnnuite(totalRepayment);
        remboursement.setCapitalRepayment(loanAmount);
        remboursement.setInterest(totalRepayment - loanAmount);
        remboursement.setRemainingCapital(0.0);
        remboursements.add(remboursement);
        return remboursements;
    }

    private List<Remboursement> generateAmortissementConstantSchedule(Double loanAmount, Double monthlyInterestRate, Integer loanTerm) {
        List<Remboursement> remboursements = new ArrayList<>();
        Double monthlyPayment = loanAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTerm)) /
                (Math.pow(1 + monthlyInterestRate, loanTerm) - 1);
        Double remainingCapital = loanAmount;

        for (int i = 1; i <= loanTerm; i++) {
            Double interest = remainingCapital * monthlyInterestRate;
            Double capitalRepayment = monthlyPayment - interest;
            remainingCapital -= capitalRepayment;

            Remboursement remboursement = new Remboursement();
            remboursement.setMonth(i);
            remboursement.setAnnuite(monthlyPayment);
            remboursement.setCapitalRepayment(capitalRepayment);
            remboursement.setInterest(interest);
            remboursement.setRemainingCapital(remainingCapital);
            remboursements.add(remboursement);
        }
        return remboursements;
    }

    private List<Remboursement> generateAmortissementNotConstantSchedule(Double loanAmount, Double monthlyInterestRate, Integer loanTerm) {
        List<Remboursement> remboursements = new ArrayList<>();
        Double remainingCapital = loanAmount;
        Double equalPrincipalRepayment = loanAmount / loanTerm;

        for (int i = 1; i <= loanTerm; i++) {
            Double interest = remainingCapital * monthlyInterestRate;
            Double annuity = interest + equalPrincipalRepayment;
            remainingCapital -= equalPrincipalRepayment;

            Remboursement remboursement = new Remboursement();
            remboursement.setMonth(i);
            remboursement.setAnnuite(annuity);
            remboursement.setCapitalRepayment(equalPrincipalRepayment);
            remboursement.setInterest(interest);
            remboursement.setRemainingCapital(remainingCapital);
            remboursements.add(remboursement);
        }
        return remboursements;
    }

    private void saveRemboursements(Credit credit, List<Remboursement> repaymentSchedule) {
        for (Remboursement remboursement : repaymentSchedule) {
            remboursement.setCredit(credit);
            remboursementRepository.save(remboursement);
        }
    }
}*/


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
/*
@Service
public class CreditService {

    private static final Logger logger = LoggerFactory.getLogger(CreditService.class);

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private RemboursementRepository remboursementRepository;

    @Transactional
    public Credit createCredit(Credit credit) {
        logger.info("Creating new credit: {}", credit);
        credit.setCreditStatus(Credit.CreditStatus.PENDING);
        Credit savedCredit = creditRepository.save(credit);
        logger.info("Saved credit with ID: {}", savedCredit.getCreditId());
        List<Remboursement> repaymentSchedule = getRepaymentSchedule(savedCredit);
        saveRemboursements(savedCredit, repaymentSchedule);
        return creditRepository.save(savedCredit);
    }

    public Credit getCreditById(Long id) {
        logger.info("Fetching credit with ID: {}", id);
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credit not found with id: " + id));
        credit.setRemboursements(remboursementRepository.findByCredit(credit));
        return credit;
    }

    public List<Credit> getAllCredits() {
        logger.info("Fetching all credits");
        List<Credit> credits = creditRepository.findAll();
        for (Credit credit : credits) {
            credit.setRemboursements(remboursementRepository.findByCredit(credit));
        }
        return credits;
    }

    @Transactional
    public Credit updateCredit(Long id, Credit creditDetails) {
        logger.info("Updating credit with ID: {}", id);
        logger.info("Received credit details: {}", creditDetails);
        Credit credit = getCreditById(id);
        logger.info("Fetched existing credit: {}", credit);

        // Update fields only if they are not null
        if (creditDetails.getGender() != null) {
            credit.setGender(creditDetails.getGender());
        } else {
            logger.warn("Gender is null in request");
        }
        if (creditDetails.getMarried() != null) {
            credit.setMarried(creditDetails.getMarried());
        } else {
            logger.warn("Married is null in request");
        }
        if (creditDetails.getEducation() != null) {
            credit.setEducation(creditDetails.getEducation());
        } else {
            logger.warn("Education is null in request");
        }
        if (creditDetails.getSelfEmployed() != null) {
            credit.setSelfEmployed(creditDetails.getSelfEmployed());
        } else {
            logger.warn("SelfEmployed is null in request");
        }
        if (creditDetails.getIncome() != null) {
            credit.setIncome(creditDetails.getIncome());
        } else {
            logger.warn("Income is null in request");
        }
        if (creditDetails.getLoanAmount() != null) {
            credit.setLoanAmount(creditDetails.getLoanAmount());
        } else {
            logger.warn("LoanAmount is null in request");
        }
        if (creditDetails.getLoanTerm() != null) {
            credit.setLoanTerm(creditDetails.getLoanTerm());
        } else {
            logger.warn("LoanTerm is null in request");
        }
        if (creditDetails.getCreditType() != null) {
            credit.setCreditType(creditDetails.getCreditType());
        } else {
            logger.warn("CreditType is null in request");
        }
        if (creditDetails.getCreditStatus() != null) {
            credit.setCreditStatus(creditDetails.getCreditStatus());
        } else {
            logger.warn("CreditStatus is null in request");
        }
        if (creditDetails.getInterestType() != null) {
            credit.setInterestType(creditDetails.getInterestType());
        } else {
            logger.warn("InterestType is null in request");
        }

        logger.info("Updated credit fields: {}", credit);

        if (creditDetails.getLoanAmount() != null || creditDetails.getLoanTerm() != null || creditDetails.getInterestType() != null) {
            logger.info("Loan details changed, regenerating repayment schedule");
            remboursementRepository.deleteByCredit(credit);
            List<Remboursement> repaymentSchedule = getRepaymentSchedule(credit);
            saveRemboursements(credit, repaymentSchedule);
        }

        logger.info("Saving updated credit");
        return creditRepository.save(credit);
    }

    @Transactional
    public void deleteCredit(Long id) {
        logger.info("Deleting credit with ID: {}", id);
        Credit credit = getCreditById(id);
        remboursementRepository.deleteByCredit(credit);
        creditRepository.delete(credit);
    }

    public List<Remboursement> getRepaymentSchedule(Credit credit) {
        logger.info("Generating repayment schedule for credit ID: {}", credit.getCreditId());
        Double loanAmount = credit.getLoanAmount();
        Integer loanTerm = credit.getLoanTerm();
        Double annualInterestRate = 0.05;
        Double monthlyInterestRate = annualInterestRate / 12.0;

        switch (credit.getInterestType()) {
            case BLOC:
                return generateRemboursementEnBlocSchedule(loanAmount, annualInterestRate, loanTerm);
            case CONSTANT:
                return generateAmortissementConstantSchedule(loanAmount, monthlyInterestRate, loanTerm);
            case NON_CONSTANT:
                return generateAmortissementNotConstantSchedule(loanAmount, monthlyInterestRate, loanTerm);
            default:
                throw new IllegalArgumentException("Invalid interest type: " + credit.getInterestType());
        }
    }

    private List<Remboursement> generateRemboursementEnBlocSchedule(Double loanAmount, Double annualInterestRate, Integer loanTerm) {
        List<Remboursement> remboursements = new ArrayList<>();
        Double totalRepayment = loanAmount * (1 + annualInterestRate * (loanTerm / 12.0));

        Remboursement remboursement = new Remboursement();
        remboursement.setMonth(loanTerm);
        remboursement.setAnnuite(totalRepayment);
        remboursement.setCapitalRepayment(loanAmount);
        remboursement.setInterest(totalRepayment - loanAmount);
        remboursement.setRemainingCapital(0.0);
        remboursements.add(remboursement);
        return remboursements;
    }

    private List<Remboursement> generateAmortissementConstantSchedule(Double loanAmount, Double monthlyInterestRate, Integer loanTerm) {
        List<Remboursement> remboursements = new ArrayList<>();
        Double monthlyPayment = loanAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTerm)) /
                (Math.pow(1 + monthlyInterestRate, loanTerm) - 1);
        Double remainingCapital = loanAmount;

        for (int i = 1; i <= loanTerm; i++) {
            Double interest = remainingCapital * monthlyInterestRate;
            Double capitalRepayment = monthlyPayment - interest;
            remainingCapital -= capitalRepayment;

            Remboursement remboursement = new Remboursement();
            remboursement.setMonth(i);
            remboursement.setAnnuite(monthlyPayment);
            remboursement.setCapitalRepayment(capitalRepayment);
            remboursement.setInterest(interest);
            remboursement.setRemainingCapital(remainingCapital);
            remboursements.add(remboursement);
        }
        return remboursements;
    }

    private List<Remboursement> generateAmortissementNotConstantSchedule(Double loanAmount, Double monthlyInterestRate, Integer loanTerm) {
        List<Remboursement> remboursements = new ArrayList<>();
        Double remainingCapital = loanAmount;
        Double equalPrincipalRepayment = loanAmount / loanTerm;

        for (int i = 1; i <= loanTerm; i++) {
            Double interest = remainingCapital * monthlyInterestRate;
            Double annuity = interest + equalPrincipalRepayment;
            remainingCapital -= equalPrincipalRepayment;

            Remboursement remboursement = new Remboursement();
            remboursement.setMonth(i);
            remboursement.setAnnuite(annuity);
            remboursement.setCapitalRepayment(equalPrincipalRepayment);
            remboursement.setInterest(interest);
            remboursement.setRemainingCapital(remainingCapital);
            remboursements.add(remboursement);
        }
        return remboursements;
    }

    private void saveRemboursements(Credit credit, List<Remboursement> repaymentSchedule) {
        logger.info("Saving {} remboursements for credit ID: {}", repaymentSchedule.size(), credit.getCreditId());
        for (Remboursement remboursement : repaymentSchedule) {
            remboursement.setCredit(credit);
            remboursementRepository.save(remboursement);
        }
    }
}*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
/*
@Service
public class CreditService {

    private static final Logger logger = LoggerFactory.getLogger(CreditService.class);

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private RemboursementRepository remboursementRepository;

    @Transactional
    public Credit createCredit(Credit credit) {
        logger.info("Creating new credit: {}", credit);
        credit.setCreditStatus(Credit.CreditStatus.PENDING);
        Credit savedCredit = creditRepository.save(credit);
        logger.info("Saved credit with ID: {}", savedCredit.getCreditId());
        List<Remboursement> repaymentSchedule = getRepaymentSchedule(savedCredit);
        saveRemboursements(savedCredit, repaymentSchedule);
        return creditRepository.save(savedCredit);
    }

    public Credit getCreditById(Long id) {
        logger.info("Fetching credit with ID: {}", id);
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Credit not found with ID: {}", id);
                    return new RuntimeException("Credit not found with id: " + id);
                });
        credit.setRemboursements(remboursementRepository.findByCredit(credit));
        return credit;
    }

    public List<Credit> getAllCredits() {
        logger.info("Fetching all credits");
        List<Credit> credits = creditRepository.findAll();
        for (Credit credit : credits) {
            credit.setRemboursements(remboursementRepository.findByCredit(credit));
        }
        return credits;
    }

    @Transactional
    public Credit updateCredit(Long id, Credit creditDetails) {
        logger.info("Updating credit with ID: {}", id);
        logger.info("Received credit details: {}", creditDetails);
        Credit credit = getCreditById(id);
        logger.info("Fetched existing credit: {}", credit);

        // Update fields only if they are not null
        if (creditDetails.getGender() != null) {
            credit.setGender(creditDetails.getGender());
            logger.info("Updated gender: {}", credit.getGender());
        }
        if (creditDetails.getMarried() != null) {
            credit.setMarried(creditDetails.getMarried());
            logger.info("Updated married: {}", credit.getMarried());
        }
        if (creditDetails.getEducation() != null) {
            credit.setEducation(creditDetails.getEducation());
            logger.info("Updated education: {}", credit.getEducation());
        }
        if (creditDetails.getSelfEmployed() != null) {
            credit.setSelfEmployed(creditDetails.getSelfEmployed());
            logger.info("Updated selfEmployed: {}", credit.getSelfEmployed());
        }
        if (creditDetails.getIncome() != null) {
            credit.setIncome(creditDetails.getIncome());
            logger.info("Updated income: {}", credit.getIncome());
        }
        if (creditDetails.getLoanAmount() != null) {
            credit.setLoanAmount(creditDetails.getLoanAmount());
            logger.info("Updated loanAmount: {}", credit.getLoanAmount());
        }
        if (creditDetails.getLoanTerm() != null) {
            credit.setLoanTerm(creditDetails.getLoanTerm());
            logger.info("Updated loanTerm: {}", credit.getLoanTerm());
        }
        if (creditDetails.getCreditType() != null) {
            credit.setCreditType(creditDetails.getCreditType());
            logger.info("Updated creditType: {}", credit.getCreditType());
        }
        if (creditDetails.getCreditStatus() != null) {
            credit.setCreditStatus(creditDetails.getCreditStatus());
            logger.info("Updated creditStatus: {}", credit.getCreditStatus());
        }
        if (creditDetails.getInterestType() != null) {
            credit.setInterestType(creditDetails.getInterestType());
            logger.info("Updated interestType: {}", credit.getInterestType());
        }

        if (creditDetails.getLoanAmount() != null || creditDetails.getLoanTerm() != null || creditDetails.getInterestType() != null) {
            logger.info("Loan details changed, regenerating repayment schedule for credit ID: {}", id);
            // Clear the existing remboursements list in-memory
            credit.getRemboursements().clear();
            logger.info("Cleared existing remboursements for credit ID: {}", id);
            // Generate new repayment schedule
            List<Remboursement> repaymentSchedule = getRepaymentSchedule(credit);
            // Add new remboursements to the list
            credit.getRemboursements().addAll(repaymentSchedule);
            logger.info("Added {} new remboursements for credit ID: {}", repaymentSchedule.size(), id);
        }

        logger.info("Saving updated credit with ID: {}", id);
        Credit updatedCredit = creditRepository.save(credit);
        logger.info("Successfully updated credit with ID: {}", id);
        return updatedCredit;
    }

    @Transactional
    public void deleteCredit(Long id) {
        logger.info("Deleting credit with ID: {}", id);
        Credit credit = getCreditById(id);
        logger.info("Fetched credit for deletion: {}", credit);
        logger.info("Deleting credit with ID: {} (cascading will handle remboursements)", id);
        creditRepository.delete(credit);
        logger.info("Successfully deleted credit with ID: {}", id);
    }

    public List<Remboursement> getRepaymentSchedule(Credit credit) {
        logger.info("Generating repayment schedule for credit ID: {}", credit.getCreditId());
        if (credit.getLoanAmount() == null || credit.getLoanTerm() == null || credit.getInterestType() == null) {
            logger.error("Cannot generate repayment schedule: loanAmount={}, loanTerm={}, interestType={}",
                    credit.getLoanAmount(), credit.getLoanTerm(), credit.getInterestType());
            throw new IllegalStateException("Loan amount, loan term, and interest type must not be null");
        }

        Double loanAmount = credit.getLoanAmount();
        Integer loanTerm = credit.getLoanTerm();
        Double annualInterestRate = 0.05;
        Double monthlyInterestRate = annualInterestRate / 12.0;

        switch (credit.getInterestType()) {
            case BLOC:
                return generateRemboursementEnBlocSchedule(loanAmount, annualInterestRate, loanTerm);
            case CONSTANT:
                return generateAmortissementConstantSchedule(loanAmount, monthlyInterestRate, loanTerm);
            case NON_CONSTANT:
                return generateAmortissementNotConstantSchedule(loanAmount, monthlyInterestRate, loanTerm);
            default:
                throw new IllegalArgumentException("Invalid interest type: " + credit.getInterestType());
        }
    }

    private List<Remboursement> generateRemboursementEnBlocSchedule(Double loanAmount, Double annualInterestRate, Integer loanTerm) {
        List<Remboursement> remboursements = new ArrayList<>();
        Double totalRepayment = loanAmount * (1 + annualInterestRate * (loanTerm / 12.0));

        Remboursement remboursement = new Remboursement();
        remboursement.setMonth(loanTerm);
        remboursement.setAnnuite(totalRepayment);
        remboursement.setCapitalRepayment(loanAmount);
        remboursement.setInterest(totalRepayment - loanAmount);
        remboursement.setRemainingCapital(0.0);
        remboursements.add(remboursement);
        return remboursements;
    }

    private List<Remboursement> generateAmortissementConstantSchedule(Double loanAmount, Double monthlyInterestRate, Integer loanTerm) {
        List<Remboursement> remboursements = new ArrayList<>();
        Double monthlyPayment = loanAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTerm)) /
                (Math.pow(1 + monthlyInterestRate, loanTerm) - 1);
        Double remainingCapital = loanAmount;

        for (int i = 1; i <= loanTerm; i++) {
            Double interest = remainingCapital * monthlyInterestRate;
            Double capitalRepayment = monthlyPayment - interest;
            remainingCapital -= capitalRepayment;

            Remboursement remboursement = new Remboursement();
            remboursement.setMonth(i);
            remboursement.setAnnuite(monthlyPayment);
            remboursement.setCapitalRepayment(capitalRepayment);
            remboursement.setInterest(interest);
            remboursement.setRemainingCapital(remainingCapital);
            remboursements.add(remboursement);
        }
        return remboursements;
    }

    private List<Remboursement> generateAmortissementNotConstantSchedule(Double loanAmount, Double monthlyInterestRate, Integer loanTerm) {
        List<Remboursement> remboursements = new ArrayList<>();
        Double remainingCapital = loanAmount;
        Double equalPrincipalRepayment = loanAmount / loanTerm;

        for (int i = 1; i <= loanTerm; i++) {
            Double interest = remainingCapital * monthlyInterestRate;
            Double annuity = interest + equalPrincipalRepayment;
            remainingCapital -= equalPrincipalRepayment;

            Remboursement remboursement = new Remboursement();
            remboursement.setMonth(i);
            remboursement.setAnnuite(annuity);
            remboursement.setCapitalRepayment(equalPrincipalRepayment);
            remboursement.setInterest(interest);
            remboursement.setRemainingCapital(remainingCapital);
            remboursements.add(remboursement);
        }
        return remboursements;
    }

    private void saveRemboursements(Credit credit, List<Remboursement> repaymentSchedule) {
        logger.info("Associating {} remboursements with credit ID: {}", repaymentSchedule.size(), credit.getCreditId());
        for (Remboursement remboursement : repaymentSchedule) {
            remboursement.setCredit(credit);
            // No need to call remboursementRepository.save(remboursement) here
            // The entities will be saved when the Credit entity is saved
        }
    }
}*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditService {

    private static final Logger logger = LoggerFactory.getLogger(CreditService.class);

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private RemboursementRepository remboursementRepository;
    @Autowired
    private PredictionService predictionService;

    @Transactional
    public Credit createCredit(Credit credit) {
        logger.info("Creating new credit: {}", credit);
        credit.setCreditStatus(Credit.CreditStatus.PENDING);
        Credit savedCredit = creditRepository.save(credit);
        logger.info("Saved credit with ID: {}", savedCredit.getCreditId());
        List<Remboursement> repaymentSchedule = getRepaymentSchedule(savedCredit);
        savedCredit.getRemboursements().addAll(repaymentSchedule);
        for (Remboursement remboursement : repaymentSchedule) {
            remboursement.setCredit(savedCredit);
        }
        return creditRepository.save(savedCredit);
    }

    @Transactional(readOnly = true)
    public Credit getCreditById(Long id) {
        logger.info("Fetching credit with ID: {}", id);
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Credit not found with ID: {}", id);
                    return new RuntimeException("Credit not found with id: " + id);
                });
        // Do not fetch or set remboursements here
        return credit;
    }

    @Transactional(readOnly = true)
    public Credit getCreditByIdWithRemboursements(Long id) {
        logger.info("Fetching credit with ID: {} (with remboursements)", id);
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Credit not found with ID: {}", id);
                    return new RuntimeException("Credit not found with id: " + id);
                });
        // Force Hibernate to initialize the remboursements list within the transaction
        credit.getRemboursements().size();
        return credit;
    }

    @Transactional(readOnly = true)
    public List<Credit> getAllCredits() {
        logger.info("Fetching all credits");
        List<Credit> credits = creditRepository.findAll();
        // Force Hibernate to initialize the remboursements list for each credit
        for (Credit credit : credits) {
            credit.getRemboursements().size();
        }
        return credits;
    }

    @Transactional
    public Credit updateCredit(Long id, Credit creditDetails) {
        logger.info("Updating credit with ID: {}", id);
        logger.info("Received credit details: {}", creditDetails);
        Credit credit = getCreditById(id);
        logger.info("Fetched existing credit: {}", credit);

        // Update fields only if they are not null
        if (creditDetails.getGender() != null) {
            credit.setGender(creditDetails.getGender());
            logger.info("Updated gender: {}", credit.getGender());
        }
        if (creditDetails.getMarried() != null) {
            credit.setMarried(creditDetails.getMarried());
            logger.info("Updated married: {}", credit.getMarried());
        }
        if (creditDetails.getEducation() != null) {
            credit.setEducation(creditDetails.getEducation());
            logger.info("Updated education: {}", credit.getEducation());
        }
        if (creditDetails.getSelfEmployed() != null) {
            credit.setSelfEmployed(creditDetails.getSelfEmployed());
            logger.info("Updated selfEmployed: {}", credit.getSelfEmployed());
        }
        if (creditDetails.getIncome() != null) {
            credit.setIncome(creditDetails.getIncome());
            logger.info("Updated income: {}", credit.getIncome());
        }
        if (creditDetails.getLoanAmount() != null) {
            credit.setLoanAmount(creditDetails.getLoanAmount());
            logger.info("Updated loanAmount: {}", credit.getLoanAmount());
        }
        if (creditDetails.getLoanTerm() != null) {
            credit.setLoanTerm(creditDetails.getLoanTerm());
            logger.info("Updated loanTerm: {}", credit.getLoanTerm());
        }
        if (creditDetails.getCreditType() != null) {
            credit.setCreditType(creditDetails.getCreditType());
            logger.info("Updated creditType: {}", credit.getCreditType());
        }
        if (creditDetails.getCreditStatus() != null) {
            credit.setCreditStatus(creditDetails.getCreditStatus());
            logger.info("Updated creditStatus: {}", credit.getCreditStatus());
        }
        if (creditDetails.getInterestType() != null) {
            credit.setInterestType(creditDetails.getInterestType());
            logger.info("Updated interestType: {}", credit.getInterestType());
        }

        if (creditDetails.getLoanAmount() != null || creditDetails.getLoanTerm() != null || creditDetails.getInterestType() != null) {
            logger.info("Loan details changed, regenerating repayment schedule for credit ID: {}", id);
            // Clear the existing remboursements list in-memory
            credit.getRemboursements().clear();
            logger.info("Cleared existing remboursements for credit ID: {}", id);
            // Generate new repayment schedule
            List<Remboursement> repaymentSchedule = getRepaymentSchedule(credit);
            // Add new remboursements to the list
            credit.getRemboursements().addAll(repaymentSchedule);
            for (Remboursement remboursement : repaymentSchedule) {
                remboursement.setCredit(credit);
            }
            logger.info("Added {} new remboursements for credit ID: {}", repaymentSchedule.size(), id);
        }

        logger.info("Saving updated credit with ID: {}", id);
        Credit updatedCredit = creditRepository.save(credit);
        logger.info("Successfully updated credit with ID: {}", id);
        return updatedCredit;
    }
/*
    @Transactional
    public void deleteCredit(Long id) {
        logger.info("Deleting credit with ID: {}", id);
        Credit credit = getCreditById(id);
        logger.info("Fetched credit for deletion: {}", credit);
        logger.info("Deleting credit with ID: {} (cascading will handle remboursements)", id);
        creditRepository.delete(credit);
        logger.info("Successfully deleted credit with ID: {}", id);
    }*/


/*

    public void deleteCredit(Long id) {
        Credit credit = getCreditById(id);
        remboursementRepository.deleteByCredit(credit); // Delete associated remboursements
        creditRepository.delete(credit);
    }
*/

    @Transactional
    public void deleteCredit(Long id) {
        logger.info("Deleting credit with ID: {}", id);
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Credit not found with ID: {}", id);
                    return new RuntimeException("Credit not found with id: " + id);
                });
        remboursementRepository.deleteByCredit(credit);
        logger.info("Deleted associated remboursements for credit ID: {}", id);
        creditRepository.delete(credit);
        logger.info("Successfully deleted credit with ID: {}", id);
    }

    public List<Remboursement> getRepaymentSchedule(Credit credit) {
        logger.info("Generating repayment schedule for credit ID: {}", credit.getCreditId());
        if (credit.getLoanAmount() == null || credit.getLoanTerm() == null || credit.getInterestType() == null) {
            logger.error("Cannot generate repayment schedule: loanAmount={}, loanTerm={}, interestType={}",
                    credit.getLoanAmount(), credit.getLoanTerm(), credit.getInterestType());
            throw new IllegalStateException("Loan amount, loan term, and interest type must not be null");
        }

        Double loanAmount = credit.getLoanAmount();
        Integer loanTerm = credit.getLoanTerm();
        Double annualInterestRate = 0.05;
        Double monthlyInterestRate = annualInterestRate / 12.0;

        switch (credit.getInterestType()) {
            case BLOC:
                return generateRemboursementEnBlocSchedule(loanAmount, annualInterestRate, loanTerm);
            case CONSTANT:
                return generateAmortissementConstantSchedule(loanAmount, monthlyInterestRate, loanTerm);
            case NON_CONSTANT:
                return generateAmortissementNotConstantSchedule(loanAmount, monthlyInterestRate, loanTerm);
            default:
                throw new IllegalArgumentException("Invalid interest type: " + credit.getInterestType());
        }
    }

    private List<Remboursement> generateRemboursementEnBlocSchedule(Double loanAmount, Double annualInterestRate, Integer loanTerm) {
        List<Remboursement> remboursements = new ArrayList<>();
        Double totalRepayment = loanAmount * (1 + annualInterestRate * (loanTerm / 12.0));

        Remboursement remboursement = new Remboursement();
        remboursement.setMonth(loanTerm);
        remboursement.setAnnuite(totalRepayment);
        remboursement.setCapitalRepayment(loanAmount);
        remboursement.setInterest(totalRepayment - loanAmount);
        remboursement.setRemainingCapital(0.0);
        remboursements.add(remboursement);
        return remboursements;
    }

    private List<Remboursement> generateAmortissementConstantSchedule(Double loanAmount, Double monthlyInterestRate, Integer loanTerm) {
        List<Remboursement> remboursements = new ArrayList<>();
        Double monthlyPayment = loanAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTerm)) /
                (Math.pow(1 + monthlyInterestRate, loanTerm) - 1);
        Double remainingCapital = loanAmount;

        for (int i = 1; i <= loanTerm; i++) {
            Double interest = remainingCapital * monthlyInterestRate;
            Double capitalRepayment = monthlyPayment - interest;
            remainingCapital -= capitalRepayment;

            Remboursement remboursement = new Remboursement();
            remboursement.setMonth(i);
            remboursement.setAnnuite(monthlyPayment);
            remboursement.setCapitalRepayment(capitalRepayment);
            remboursement.setInterest(interest);
            remboursement.setRemainingCapital(remainingCapital);
            remboursements.add(remboursement);
        }
        return remboursements;
    }

    private List<Remboursement> generateAmortissementNotConstantSchedule(Double loanAmount, Double monthlyInterestRate, Integer loanTerm) {
        List<Remboursement> remboursements = new ArrayList<>();
        Double remainingCapital = loanAmount;
        Double equalPrincipalRepayment = loanAmount / loanTerm;

        for (int i = 1; i <= loanTerm; i++) {
            Double interest = remainingCapital * monthlyInterestRate;
            Double annuity = interest + equalPrincipalRepayment;
            remainingCapital -= equalPrincipalRepayment;

            Remboursement remboursement = new Remboursement();
            remboursement.setMonth(i);
            remboursement.setAnnuite(annuity);
            remboursement.setCapitalRepayment(equalPrincipalRepayment);
            remboursement.setInterest(interest);
            remboursement.setRemainingCapital(remainingCapital);
            remboursements.add(remboursement);
        }
        return remboursements;
    }


    public List<Credit> searchAndFilterCredits(
            Credit.Gender gender,
            Credit.Married married,
            Credit.Education education,
            Credit.SelfEmployed selfEmployed,
            Credit.CreditType creditType,
            Credit.CreditStatus creditStatus,
            Credit.InterestType interestType,
            Double minLoanAmount,
            Double maxLoanAmount,
            Integer minLoanTerm,
            Integer maxLoanTerm
    ) {
        List<Credit> credits = creditRepository.searchAndFilterCredits(
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

        for (Credit credit : credits) {
            credit.setRemboursements(remboursementRepository.findByCredit(credit));
        }

        return credits;
    }
}