package com.example.pispring.dto;

import com.example.pispring.Entities.CartItem;

public class CartItemDTO {
    private int idCartItem;
    private int quantity;
    private int itemId;
    private String itemName;

    // Constructeur qui transforme un CartItem en DTO
    public CartItemDTO(CartItem cartItem) {
        this.idCartItem = cartItem.getIdCartItem();
        this.quantity = cartItem.getQuantity();
        this.itemId = cartItem.getItem().getIdItem();
        this.itemName = cartItem.getItem().getName();
    }

    // Getters et Setters
    public int getIdCartItem() {
        return idCartItem;
    }

    public void setIdCartItem(int idCartItem) {
        this.idCartItem = idCartItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
