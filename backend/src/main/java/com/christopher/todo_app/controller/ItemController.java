package com.christopher.todo_app.controller;

import com.christopher.todo_app.dto.ItemResponse;
import com.christopher.todo_app.service.ItemService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<ItemResponse>> getItems() {
        List<ItemResponse> items = itemService.getItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping("/items")
    public ResponseEntity<ItemResponse> saveItem(@Valid @RequestBody ItemResponse itemResponse) {
        ItemResponse retrieved = itemService.saveItem(itemResponse);
        return new ResponseEntity<>(retrieved, HttpStatus.CREATED);
    }
}
