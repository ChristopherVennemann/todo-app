package com.christopher.todo_app.controller;

import com.christopher.todo_app.entity.Item;
import com.christopher.todo_app.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getItems() {
        List<Item> items = itemService.getItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping("/items")
    public ResponseEntity<Item> saveItem(@RequestBody Item item) {
        Item retrievedItem = itemService.saveItem(item);
        return new ResponseEntity<>(retrievedItem, HttpStatus.CREATED);
    }
}
