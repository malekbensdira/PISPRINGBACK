package com.example.pispring.Service;

import com.example.pispring.Entities.*;
import com.example.pispring.Repository.*;
import com.example.pispring.dto.CartItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService implements ICartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CartService cartService;
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }
    public CartItemDTO addCartItem(int cartId, int itemId, int quantity) {
        // Vérifier si l'item existe
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with ID: " + itemId));

        // Vérifier si le stock est suffisant
        if (item.getStock() < quantity) {
            throw new RuntimeException("Not enough stock available for item: " + item.getName());
        }

        // Réduire le stock de l'item
        item.setStock(item.getStock() - quantity);
        itemRepository.save(item);
        itemService.checkStockAndAlert(item);

        // Vérifier si le panier existe
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        // Vérifier si l'item est déjà dans le panier
        Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndItem(cart, item);

        CartItem cartItem;
        if (existingCartItem.isPresent()) {
            // L'item existe déjà dans le panier, augmenter la quantité
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // Sinon, créer un nouveau CartItem
            cartItem = new CartItem();
            cartItem.setItem(item);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
        }
        cartService.updateCartTotal(cart);

        cartItem = cartItemRepository.save(cartItem);

        return new CartItemDTO(cartItem);
    }

    public List<CartItem> getAllCartItems() { return cartItemRepository.findAll(); }
    public Optional<CartItem> getCartItemById(int id) { return cartItemRepository.findById(id); }
    public CartItem saveCartItem(CartItem cartItem) { return cartItemRepository.save(cartItem); }
    public CartItem updateCartItem(CartItem cartItem) { return cartItemRepository.save(cartItem); }

    @Override
    public void deleteCartItem(int id) {
        cartItemRepository.deleteById(id);
    }

    public void removeCartItem(int cartItemId) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);

        if (!cartItemOpt.isPresent()) {
            throw new RuntimeException("CartItem not found with ID: " + cartItemId);
        }

        CartItem cartItem = cartItemOpt.get();
        Cart cart = cartItem.getCart();

        // Mettre à jour le total du panier
        double newTotal = cart.getTotal() - (cartItem.getItem().getPrice() * cartItem.getQuantity());
        cart.setTotal(Math.max(0, newTotal)); // Éviter les valeurs négatives
        cartRepository.save(cart);

        cartItemRepository.delete(cartItem);
    }

}
