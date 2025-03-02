package com.example.pispring.Service;

import com.example.pispring.Entities.*;
import com.example.pispring.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service

public class ItemService implements IItemService{
    @Autowired
    private final ItemRepository itemRepository;
    @Autowired
    private  PartnerRepository partnerRepository;
    @Autowired
    private EmailService emailService;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository; }
    public void checkStockAndAlert(Item item) {
        int stockThreshold = 5;  // Seuil de stock faible

        if (item.getStock() <= stockThreshold) {
            String partnerName = item.getPartner().getName(); // Supposons que l'Item a un partenaire
            emailService.sendLowStockAlert(item.getName(), item.getStock(), partnerName);
        }
    }
    public List<Item> getAllItems() {
        return itemRepository.findAll(); }
    public Optional<Item> getItemById(int id) {
        return itemRepository.findById(id); }
    public Item addItemToPartner(int partnerId, Item item) {
        Optional<Partner> partnerOpt = partnerRepository.findById(partnerId);
        if (partnerOpt.isPresent()) {
            Partner partner = partnerOpt.get();
            item.setPartner(partner);
            Item savedItem = itemRepository.save(item);

            // Vérifier si l'item ajouté est déjà en stock faible
            checkStockAndAlert(savedItem);

            return savedItem;
        } else {
            throw new RuntimeException("Partner not found with ID: " + partnerId);
        }
    }

    public Item updateItem(Item item) {
        Item updatedItem = itemRepository.save(item);

        // Vérifier si l'item mis à jour a un stock faible
        checkStockAndAlert(updatedItem);

        return updatedItem;
    }


    public void deleteItem(int id) { itemRepository.deleteById(id); }
}