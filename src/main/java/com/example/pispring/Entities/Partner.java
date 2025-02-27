package com.example.pispring.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPartner;
    private String name;

    private String picture;


    @Enumerated(EnumType.STRING)  // Utilisation de l'énumération comme chaîne
    private Categories category;

    @OneToMany
    private List<Item> items;
    @ManyToOne
    @JoinColumn(name = "idEvent")
    private DonationEvent event;

    public int getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(int idPartner) {
        this.idPartner = idPartner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public DonationEvent getEvent() {
        return event;
    }

    public void setEvent(DonationEvent event) {
        this.event = event;
    }
}