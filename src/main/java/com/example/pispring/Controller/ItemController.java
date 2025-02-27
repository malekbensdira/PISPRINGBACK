package com.example.pispring.Controller;

import com.example.pispring.Entities.*;
import com.example.pispring.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private final IItemService itemService;
    public ItemController(IItemService itemService) { this.itemService = itemService; }
    @GetMapping("/getItems")
    public List<Item> getAllItems() { return itemService.getAllItems(); }
    @GetMapping("/getItemById/{id}")
    public Optional<Item> getItemById(@PathVariable int id) { return itemService.getItemById(id); }

    @PostMapping("/addItemToPartner/{partnerId}")
    public Item addItemToPartner(@PathVariable int partnerId, @RequestBody Item item) {
        return itemService.addItemToPartner(partnerId, item);
    }
    @PutMapping("/updateItem/{id}")
    public Item updateItem(@RequestBody Item item, @PathVariable int id) {
        item.setIdItem(id);
        return itemService.updateItem(item);
    }
    @DeleteMapping("/deleteItem/{id}")
    public void deleteItem(@PathVariable int id) { itemService.deleteItem(id); }

}
