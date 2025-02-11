package com.example.pispring.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class DonationEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int idEvent;
    private  String name;
    private String description;
    private Date dateEvent;

}