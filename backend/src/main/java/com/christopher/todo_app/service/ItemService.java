package com.christopher.todo_app.service;

import com.christopher.todo_app.dto.ItemResponse;
import com.christopher.todo_app.entity.Item;
import com.christopher.todo_app.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<ItemResponse> getItems() {
        return ItemResponse.of((List<Item>) itemRepository.findAll());
    }

    public ItemResponse saveItem(final ItemResponse itemResponse) {
        return ItemResponse.of(itemRepository.save(Item.of(itemResponse)));
    }
}
