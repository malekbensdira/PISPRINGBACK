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
    public ItemService(ItemRepository itemRepository) { this.itemRepository = itemRepository; }
    public List<Item> getAllItems() { return itemRepository.findAll(); }
    public Optional<Item> getItemById(int id) { return itemRepository.findById(id); }
    public Item addItemToPartner(int partnerId, Item item) {
        Optional<Partner> partnerOpt = partnerRepository.findById(partnerId);
        if (partnerOpt.isPresent()) {
            Partner partner = partnerOpt.get();
            item.setPartner(partner);
            return itemRepository.save(item);
        } else {
            throw new RuntimeException("Partner not found with ID: " + partnerId);
        }
    }
    public Item updateItem(Item item) { return itemRepository.save(item); }
    public void deleteItem(int id) { itemRepository.deleteById(id); }
}