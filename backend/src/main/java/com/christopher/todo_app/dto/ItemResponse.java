package com.christopher.todo_app.dto;


import com.christopher.todo_app.entity.Item;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ItemResponse {

    private Long id;

    @Nonnull
    @NotBlank(message = "message must not be empty")
    private String message;

    public ItemResponse(String message, Long id) {
        this.message = message;
        this.id = id;
    }

    public ItemResponse() {
    }

    public static ItemResponse of(Item item) {
        return new ItemResponse(item.getMessage(), item.getId());
    }

    public static List<ItemResponse> of(List<Item> items) {
        return items.stream()
            .map(item -> new ItemResponse(item.getMessage(), item.getId()))
            .toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}