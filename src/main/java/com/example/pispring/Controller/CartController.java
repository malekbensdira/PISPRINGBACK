package com.example.pispring.Controller;

import com.example.pispring.Entities.*;
import com.example.pispring.Service.*;
import com.example.pispring.dto.CartDTO;
import com.itextpdf.text.DocumentException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) { this.cartService = cartService; }
    @GetMapping
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<CartDTO> carts = cartService.getAllCarts().stream()
                .map(CartDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carts);
    }
    @GetMapping("/receipt/{cartId}")
    public ResponseEntity<byte[]> getReceipt(@PathVariable int cartId) throws DocumentException, IOException {
        byte[] pdfContent = cartService.generateReceipt(cartId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("recu_cart_" + cartId + ".pdf")
                .build());

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
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
