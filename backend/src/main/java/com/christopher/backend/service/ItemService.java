package com.christopher.backend.service;

import com.christopher.backend.entity.Item;
import com.christopher.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;
    public List<Item> getItems() {
        return (List<Item>) itemRepository.findAll();
    }
}
