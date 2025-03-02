package com.example.pispring.Repository;

import com.example.pispring.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCartAndItem(Cart cart, Item item);
}
