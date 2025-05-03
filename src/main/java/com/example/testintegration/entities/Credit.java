package com.example.testintegration.entities;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
/*
@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Married married;

    @Enumerated(EnumType.STRING)
    private Education education;

    @Enumerated(EnumType.STRING)
    private SelfEmployed selfEmployed;

    private Double income;
    private Double loanAmount;
    private Integer loanTerm;

    @Enumerated(EnumType.STRING)
    private CreditType creditType;

    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;

    @Enumerated(EnumType.STRING)
    private InterestType interestType;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Remboursement> remboursements = new ArrayList<>();

    public enum Gender { FEMALE, MALE }
    public enum Married { YES, NO }
    public enum Education { GRADUATE, NOT_GRADUATE }
    public enum SelfEmployed { YES, NO }
    public enum CreditType { BUSINESS, AGRICULTURE, HOUSE, SMALL_AMOUNT }
    public enum CreditStatus { PENDING, APPROVED, REJECTED, CLOSED }
    public enum InterestType { CONSTANT, NON_CONSTANT, BLOC }

    // Getters and Setters
    public Long getCreditId() { return creditId; }
    public void setCreditId(Long creditId) { this.creditId = creditId; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public Married getMarried() { return married; }
    public void setMarried(Married married) { this.married = married; }
    public Education getEducation() { return education; }
    public void setEducation(Education education) { this.education = education; }
    public SelfEmployed getSelfEmployed() { return selfEmployed; }
    public void setSelfEmployed(SelfEmployed selfEmployed) { this.selfEmployed = selfEmployed; }
    public Double getIncome() { return income; }
    public void setIncome(Double income) { this.income = income; }
    public Double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Double loanAmount) { this.loanAmount = loanAmount; }
    public Integer getLoanTerm() { return loanTerm; }
    public void setLoanTerm(Integer loanTerm) { this.loanTerm = loanTerm; }
    public CreditType getCreditType() { return creditType; }
    public void setCreditType(CreditType creditType) { this.creditType = creditType; }
    public CreditStatus getCreditStatus() { return creditStatus; }
    public void setCreditStatus(CreditStatus creditStatus) { this.creditStatus = creditStatus; }
    public InterestType getInterestType() { return interestType; }
    public void setInterestType(InterestType interestType) { this.interestType = interestType; }
    public List<Remboursement> getRemboursements() { return remboursements; }
    public void setRemboursements(List<Remboursement> remboursements) { this.remboursements = remboursements; }
}*/

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
/*
@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditId;

    @NotNull(message = "Gender is required")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "Married status is required")
    @Enumerated(EnumType.STRING)
    private Married married;

    @NotNull(message = "Education is required")
    @Enumerated(EnumType.STRING)
    private Education education;

    @NotNull(message = "Self-employed status is required")
    @Enumerated(EnumType.STRING)
    private SelfEmployed selfEmployed;

    @NotNull(message = "Income is required")
    private Double income;

    @NotNull(message = "Loan amount is required")
    private Double loanAmount;

    @NotNull(message = "Loan term is required")
    private Integer loanTerm;

    @NotNull(message = "Credit type is required")
    @Enumerated(EnumType.STRING)
    private CreditType creditType;

    @NotNull(message = "Credit status is required")
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;

    @NotNull(message = "Interest type is required")
    @Enumerated(EnumType.STRING)
    private InterestType interestType;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Remboursement> remboursements = new ArrayList<>();

    public enum Gender { FEMALE, MALE }
    public enum Married { YES, NO }
    public enum Education { GRADUATE, NOT_GRADUATE }
    public enum SelfEmployed { YES, NO }
    public enum CreditType { BUSINESS, AGRICULTURE, HOUSE, SMALL_AMOUNT }
    public enum CreditStatus { PENDING, APPROVED, REJECTED, CLOSED }
    public enum InterestType { CONSTANT, NON_CONSTANT, BLOC }

    // Getters and Setters
    public Long getCreditId() { return creditId; }
    public void setCreditId(Long creditId) { this.creditId = creditId; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public Married getMarried() { return married; }
    public void setMarried(Married married) { this.married = married; }
    public Education getEducation() { return education; }
    public void setEducation(Education education) { this.education = education; }
    public SelfEmployed getSelfEmployed() { return selfEmployed; }
    public void setSelfEmployed(SelfEmployed selfEmployed) { this.selfEmployed = selfEmployed; }
    public Double getIncome() { return income; }
    public void setIncome(Double income) { this.income = income; }
    public Double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Double loanAmount) { this.loanAmount = loanAmount; }
    public Integer getLoanTerm() { return loanTerm; }
    public void setLoanTerm(Integer loanTerm) { this.loanTerm = loanTerm; }
    public CreditType getCreditType() { return creditType; }
    public void setCreditType(CreditType creditType) { this.creditType = creditType; }
    public CreditStatus getCreditStatus() { return creditStatus; }
    public void setCreditStatus(CreditStatus creditStatus) { this.creditStatus = creditStatus; }
    public InterestType getInterestType() { return interestType; }
    public void setInterestType(InterestType interestType) { this.interestType = interestType; }
    public List<Remboursement> getRemboursements() { return remboursements; }
    public void setRemboursements(List<Remboursement> remboursements) { this.remboursements = remboursements; }
}*/


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditId;

    @NotNull(message = "Gender is required")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "Married status is required")
    @Enumerated(EnumType.STRING)
    private Married married;

    @NotNull(message = "Education is required")
    @Enumerated(EnumType.STRING)
    private Education education;

    @NotNull(message = "Self-employed status is required")
    @Enumerated(EnumType.STRING)
    private SelfEmployed selfEmployed;

    @NotNull(message = "Income is required")
    private Double income;

    @NotNull(message = "Loan amount is required")
    private Double loanAmount;

    @NotNull(message = "Loan term is required")
    private Integer loanTerm;

    @NotNull(message = "Credit type is required")
    @Enumerated(EnumType.STRING)
    private CreditType creditType;

    @NotNull(message = "Credit status is required")
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;

    @NotNull(message = "Interest type is required")
    @Enumerated(EnumType.STRING)
    private InterestType interestType;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Remboursement> remboursements = new ArrayList<>();

    public enum Gender { FEMALE, MALE }
    public enum Married { YES, NO }
    public enum Education { GRADUATE, NOT_GRADUATE }
    public enum SelfEmployed { YES, NO }
    public enum CreditType { BUSINESS, AGRICULTURE, HOUSE, SMALL_AMOUNT }
    public enum CreditStatus { PENDING, APPROVED, REJECTED, CLOSED }
    public enum InterestType { CONSTANT, NON_CONSTANT, BLOC }

    // Getters and Setters
    public Long getCreditId() { return creditId; }
    public void setCreditId(Long creditId) { this.creditId = creditId; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public Married getMarried() { return married; }
    public void setMarried(Married married) { this.married = married; }
    public Education getEducation() { return education; }
    public void setEducation(Education education) { this.education = education; }
    public SelfEmployed getSelfEmployed() { return selfEmployed; }
    public void setSelfEmployed(SelfEmployed selfEmployed) { this.selfEmployed = selfEmployed; }
    public Double getIncome() { return income; }
    public void setIncome(Double income) { this.income = income; }
    public Double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Double loanAmount) { this.loanAmount = loanAmount; }
    public Integer getLoanTerm() { return loanTerm; }
    public void setLoanTerm(Integer loanTerm) { this.loanTerm = loanTerm; }
    public CreditType getCreditType() { return creditType; }
    public void setCreditType(CreditType creditType) { this.creditType = creditType; }
    public CreditStatus getCreditStatus() { return creditStatus; }
    public void setCreditStatus(CreditStatus creditStatus) { this.creditStatus = creditStatus; }
    public InterestType getInterestType() { return interestType; }
    public void setInterestType(InterestType interestType) { this.interestType = interestType; }
    public List<Remboursement> getRemboursements() { return remboursements; }
    public void setRemboursements(List<Remboursement> remboursements) { this.remboursements = remboursements; }
}