package com.example.pispring.dto;

import com.example.pispring.Entities.Categories;
import com.example.pispring.Entities.Item;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ItemDTO {
    private int idItem;
    private String name;
    private double price;
    private String description;
    private String picture;
    private int stock;
    @Enumerated(EnumType.STRING)  // Utilisation de l'énumération comme chaîne
    private Categories category;    private int partnerId;
    private String partnerName;

    // Constructeur qui transforme un Item en DTO
    public ItemDTO(Item item) {
        this.idItem = item.getIdItem();
        this.name = item.getName();
        this.price = item.getPrice();
        this.description = item.getDescription();
        this.picture = item.getPicture();
        this.stock = item.getStock();
        this.category = item.getCategory();

        // Récupérer uniquement les informations essentielles du partner
        if (item.getPartner() != null) {
            this.partnerId = item.getPartner().getIdPartner();
            this.partnerName = item.getPartner().getName();
        }
    }

    // Getters et Setters
    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Categories getCategory() {
        return category;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }
}
