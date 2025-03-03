package com.example.pispring.Service;

import com.example.pispring.Entities.Cart;
import com.example.pispring.Entities.CartItem;
import com.example.pispring.Repository.CartRepository;
import com.example.pispring.Repository.DonationEventRepository;
import com.example.pispring.Repository.ItemRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public byte[] generateReceipt(int cartId) throws DocumentException, IOException {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (!cartOpt.isPresent()) {
            throw new RuntimeException("Cart not found with ID: " + cartId);
        }
        Cart cart = cartOpt.get();

        // 🟢 Concatenate `firstName` and `lastName`
        String fullName = cart.getUser().getFirstName() + " " + cart.getUser().getLastName();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        // **Add Logo (Top Right)**
        try {
            String logoPath = "certificates/logo.png";  // 🔴 Change this to the actual path of your logo
            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(100, 100);  // Resize logo if necessary
            logo.setAlignment(Element.ALIGN_RIGHT);
            document.add(logo);
        } catch (Exception e) {
            System.err.println("Error loading logo: " + e.getMessage());
        }

        // **Title of the receipt**
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph title = new Paragraph("🛍️ Order Receipt", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph("\nCustomer: " + fullName, new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)));
        document.add(new Paragraph("Date: " + cart.getDatepanier().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

        // **Table of items**
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.addCell("Item");
        table.addCell("Quantity");
        table.addCell("Unit Price (TND)");
        table.addCell("Total (TND)");

        for (CartItem item : cart.getCartItems()) {
            table.addCell(item.getItem().getName());
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(String.valueOf(item.getItem().getPrice()));
            BigDecimal itemTotal = BigDecimal.valueOf(item.getQuantity()).multiply(BigDecimal.valueOf(item.getItem().getPrice()));
            table.addCell(itemTotal.toString());
        }

        document.add(table);

        // **Cart Total**
        Font totalFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph total = new Paragraph("Total: " + cart.getTotal() + " TND", totalFont);
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);

        document.close();
        return out.toByteArray();
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

    public void updateCartTotal(Cart cart) {
        double total = 0.0;
        for (CartItem cartItem : cart.getCartItems()) {
            total += cartItem.getQuantity() * cartItem.getItem().getPrice();
        }
        cart.setTotal(total);
        cartRepository.save(cart);  // 🔴 Sauvegarde le total mis à jour
    }


}