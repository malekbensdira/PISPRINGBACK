package com.example.pispring.dto;

import com.example.pispring.Entities.Cart;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CartDTO {
    private int idCart;
    private LocalDateTime datepanier;
    private String status;
    private double total;
    private List<CartItemDTO> cartItems;

    // Constructeur
    public CartDTO(Cart cart) {
        this.idCart = cart.getIdCart();
        this.datepanier = cart.getDatepanier();
        this.status = cart.getStatus();
        this.total = cart.getTotal();
        this.cartItems = cart.getCartItems().stream().map(CartItemDTO::new).collect(Collectors.toList());
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public LocalDateTime getDatepanier() {
        return datepanier;
    }

    public void setDatepanier(LocalDateTime datepanier) {
        this.datepanier = datepanier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }
}
