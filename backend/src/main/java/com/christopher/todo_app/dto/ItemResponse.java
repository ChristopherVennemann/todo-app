package com.christopher.todo_app.dto;


import com.christopher.todo_app.entity.Item;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class ItemResponse {

    public static ItemResponse of(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setMessage(item.getMessage());
        itemResponse.setId(item.getId());
        return itemResponse;
    }

    public static List<ItemResponse> of(List<Item> items) {
        List<ItemResponse> itemResponses = new ArrayList<>();
        for (Item item : items) {
            ItemResponse itemResponse = new ItemResponse();
            itemResponse.setMessage(item.getMessage());
            itemResponse.setId(item.getId());
            itemResponses.add(itemResponse);
        }
        return itemResponses;
    }

    private Long id;

    @Nonnull
    @NotBlank(message = "message must not be empty")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemResponse(String message, Long id) {
        this.message = message;
        this.id = id;
    }

    public ItemResponse() {
    }
}