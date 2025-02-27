package com.example.pispring.Service;

import com.example.pispring.Entities.*;
import com.example.pispring.Repository.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    public CartItemService(CartItemRepository cartItemRepository) { this.cartItemRepository = cartItemRepository; }
    public List<CartItem> getAllCartItems() { return cartItemRepository.findAll(); }
    public Optional<CartItem> getCartItemById(int id) { return cartItemRepository.findById(id); }
    public CartItem saveCartItem(CartItem cartItem) { return cartItemRepository.save(cartItem); }
    public CartItem updateCartItem(CartItem cartItem) { return cartItemRepository.save(cartItem); }
    public void deleteCartItem(int id) { cartItemRepository.deleteById(id); }
}
