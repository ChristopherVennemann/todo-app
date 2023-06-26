package com.christopher.todo_app.controller;

import com.christopher.todo_app.entity.Item;
import com.christopher.todo_app.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Item> getItems() {
        return itemService.getItems();
    }

    @PostMapping
    public ResponseEntity<Item> saveItem(@Valid @RequestBody Item item) {
        Item retrievedItem = itemService.saveItem(item);
        return new ResponseEntity<>(retrievedItem, HttpStatus.CREATED);
    }
}
