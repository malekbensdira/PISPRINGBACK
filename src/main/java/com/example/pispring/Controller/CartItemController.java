package com.example.pispring.Controller;

import com.example.pispring.Entities.*;
import com.example.pispring.Repository.CartItemRepository;
import com.example.pispring.Service.*;
import com.example.pispring.dto.CartItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cartItem")
public class CartItemController {
    private final CartItemService cartItemService;
    @Autowired
    private CartItemRepository cartItemRepository;
    public CartItemController(CartItemService cartItemService) { this.cartItemService = cartItemService; }
    @GetMapping

    public List<CartItemDTO> getAllCartItems() {
        List<CartItem> cartItems = cartItemRepository.findAll();
        return cartItems.stream()
                .map(CartItemDTO::new) // Conversion CartItem → CartItemDTO
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public Optional<CartItem> getCartItemById(@PathVariable int id) {
        return cartItemService.getCartItemById(id); }
    @PostMapping("/add")
    public ResponseEntity<CartItemDTO> addCartItem(
            @RequestParam int cartId,
            @RequestParam int itemId,
            @RequestParam int quantity) {
        CartItemDTO cartItemDTO = cartItemService.addCartItem(cartId, itemId, quantity);
        return ResponseEntity.ok(cartItemDTO);
    }
    @PutMapping
    public CartItem updateCartItem(@RequestBody CartItem cartItem) {
        return cartItemService.updateCartItem(cartItem); }
    @DeleteMapping("/{id}") public void deleteCartItem(@PathVariable int id) {
        cartItemService.deleteCartItem(id); }
}
