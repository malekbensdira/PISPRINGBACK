package com.example.pispring.Controller;

import com.example.pispring.Entities.*;
import com.example.pispring.Service.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cartItem")
public class CartItemController {
    private final CartItemService cartItemService;
    public CartItemController(CartItemService cartItemService) { this.cartItemService = cartItemService; }
    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItemService.getAllCartItems(); }
    @GetMapping("/{id}")
    public Optional<CartItem> getCartItemById(@PathVariable int id) {
        return cartItemService.getCartItemById(id); }
    @PostMapping ("/add")
    public CartItem createCartItem(@RequestBody CartItem cartItem) {
        return cartItemService.saveCartItem(cartItem); }
    @PutMapping
    public CartItem updateCartItem(@RequestBody CartItem cartItem) {
        return cartItemService.updateCartItem(cartItem); }
    @DeleteMapping("/{id}") public void deleteCartItem(@PathVariable int id) {
        cartItemService.deleteCartItem(id); }
}
