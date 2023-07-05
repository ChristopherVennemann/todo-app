package com.christopher.todo_app.service;

import com.christopher.todo_app.dto.ItemResponse;
import com.christopher.todo_app.entity.Item;
import com.christopher.todo_app.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.christopher.todo_app.Constants.WAS_NOT_SUCCESSFUL;
import static com.christopher.todo_app.Constants.WAS_SUCESSFUL;

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

    public boolean deleteItem(final Long id) {
        if (!itemRepository.existsById(id)) {
            return WAS_NOT_SUCCESSFUL;
        }
        itemRepository.deleteById(id);
        return WAS_SUCESSFUL;
    }

    public ItemResponse setItemToDone(Long id) {
        Item changedItem = null;
        Optional<Item> retrievedItem = itemRepository.findById(id);

        if (retrievedItem.isPresent()) {
            changedItem = retrievedItem.get();
            changedItem.setDone(true);
            itemRepository.save(changedItem);
        }
        return changedItem != null ? ItemResponse.of(changedItem) : null;
    }

    public ItemResponse setItemToUndone(Long id) {
        Item changedItem = null;
        Optional<Item> retrievedItem = itemRepository.findById(id);

        if (retrievedItem.isPresent()) {
            changedItem = retrievedItem.get();
            changedItem.setDone(false);
            itemRepository.save(changedItem);
        }
        return changedItem != null ? ItemResponse.of(changedItem) : null;
    }
}
