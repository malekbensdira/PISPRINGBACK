package com.example.pispring.Service;

import com.example.pispring.Entities.DonationEvent;
import com.example.pispring.Entities.Item;

import java.util.List;
import java.util.Optional;

public interface IItemService {
    List<Item> getAllItems();
    Optional<Item> getItemById(int id);
    //Item saveItem(Item item);
    Item updateItem(Item item);
    void deleteItem(int id);
    Item addItemToPartner(int partnerId, Item item);
    void checkStockAndAlert(Item item);
}
