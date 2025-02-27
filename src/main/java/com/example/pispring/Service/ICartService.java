package com.example.pispring.Service;

import com.example.pispring.Entities.*;
import java.util.List;
import java.util.Optional;

public interface ICartService {
    List<Cart> getAllCarts();
    Optional<Cart> getCartById(int id);
    Cart saveCart(Cart cart);
    Cart updateCart(Cart cart);
    void deleteCart(int id);
}
