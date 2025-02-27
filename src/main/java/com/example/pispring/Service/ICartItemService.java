package com.example.pispring.Service;

import com.example.pispring.Entities.*;
import java.util.List;
import java.util.Optional;

public interface ICartItemService {
        List<CartItem> getAllCartItems();
        Optional<CartItem> getCartItemById(int id);
        CartItem saveCartItem(CartItem cartItem);
        CartItem updateCartItem(CartItem cartItem);
        void deleteCartItem(int id);

}
