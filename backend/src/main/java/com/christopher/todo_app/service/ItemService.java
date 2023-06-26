package com.christopher.todo_app.service;

import com.christopher.todo_app.entity.Item;
import com.christopher.todo_app.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItems() {
        return (List<Item>) itemRepository.findAll();
    }

    public Item saveItem(final Item item) {
        return itemRepository.save(item);
    }
}
