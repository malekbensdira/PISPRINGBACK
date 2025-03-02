package com.example.pispring.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCart;

    private LocalDateTime datepanier;
    private String status;
    private double total;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<CartItem> cartItems;
    @ManyToOne
    private DonationEvent event;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)  // Assurez-vous que la colonne est bien définie
    private User user;
    @ManyToOne
    @JoinColumn(name = "idCertificat")
    private DonationCertificate donationCertificate;

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

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public DonationEvent getEvent() {
        return event;
    }

    public void setEvent(DonationEvent event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DonationCertificate getDonationCertificate() {
        return donationCertificate;
    }

    public void setDonationCertificate(DonationCertificate donationCertificate) {
        this.donationCertificate = donationCertificate;
    }
}