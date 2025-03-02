package com.example.pispring.Controller;

import com.example.pispring.Entities.*;
import com.example.pispring.Repository.ItemRepository;
import com.example.pispring.Service.*;
import com.example.pispring.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private final IItemService itemService;
    public ItemController(IItemService itemService) { this.itemService = itemService; }
    @Autowired
    private ItemRepository itemRepository;
    @GetMapping("/getItems")
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(ItemDTO::new) // Conversion Item → ItemDTO
                .collect(Collectors.toList());
    }    @GetMapping("/getItemById/{id}")
    public ItemDTO getItemById(@PathVariable int id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        return new ItemDTO(item);
    }
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
