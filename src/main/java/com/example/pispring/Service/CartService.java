package com.example.pispring.Service;

import com.example.pispring.Entities.Cart;
import com.example.pispring.Entities.CartItem;
import com.example.pispring.Repository.CartRepository;
import com.example.pispring.Repository.DonationEventRepository;
import com.example.pispring.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    private final DonationEventRepository eventRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    @Autowired
    private BadgeService badgeService;

    public CartService(CartRepository cartRepository, ItemRepository itemRepository, DonationEventRepository eventRepository) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Optional<Cart> getCartById(int id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart saveCart(Cart cart) {

        // Vérifier si les CartItems existent et les associer au Cart
        if (cart.getCartItems() != null) {
            for (CartItem cartItem : cart.getCartItems()) {
                cartItem.setCart(cart);  // Associer chaque CartItem au Cart
            }
        }

        // Vérifier si la date du panier est nulle, sinon, l'assigner à la date actuelle
        if (cart.getDatepanier() == null) {
            cart.setDatepanier(LocalDateTime.now());
        }

        // Sauvegarder le panier dans la base de données
        Cart savedCart = cartRepository.save(cart);

        // Mettre à jour le badge de l'utilisateur après avoir sauvegardé le panier
        System.out.println("Mise à jour badge pour User ID : " + cart.getUser().getIdUser());
        badgeService.updateUserBadge(savedCart.getUser().getIdUser());

        return savedCart;  // Retourner le panier sauvegardé
    }


    @Override
    public Cart updateCart(Cart cart) {
        if (cartRepository.existsById(cart.getIdCart())) {
            return cartRepository.save(cart);
        } else {
            throw new RuntimeException("Cart not found with id: " + cart.getIdCart());
        }
    }

    @Override
    public void deleteCart(int id) {
        if (cartRepository.existsById(id)) {
            cartRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cart not found with id: " + id);
        }
    }

}