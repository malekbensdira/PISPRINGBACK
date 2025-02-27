package com.example.pispring.Controller;

import com.example.pispring.Entities.*;
import com.example.pispring.Service.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) { this.cartService = cartService; }
    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts(); }
    @GetMapping("/{id}")
    public Optional<Cart> getCartById(@PathVariable int id) { return cartService.getCartById(id); }
    @PostMapping("/addCart")
    public Cart createCart(@RequestBody Cart cart) {
        return cartService.saveCart(cart); }
    @PutMapping("/updateCart/{id}")
    public Cart updateCart(@RequestBody Cart cart,@PathVariable int id) {
        cart.setIdCart(id);
        return cartService.updateCart(cart); }
    @DeleteMapping("/deleteCart/{id}")
    public void deleteCart(@PathVariable int id) { cartService.deleteCart(id); }
}
