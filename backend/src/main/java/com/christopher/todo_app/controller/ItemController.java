package com.christopher.todo_app.controller;

import com.christopher.todo_app.dto.ItemResponse;
import com.christopher.todo_app.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<ItemResponse> getItems() {
        return itemService.getItems();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse saveItem(@Valid @RequestBody ItemResponse itemResponse) {
        return itemService.saveItem(itemResponse);
    }
}
