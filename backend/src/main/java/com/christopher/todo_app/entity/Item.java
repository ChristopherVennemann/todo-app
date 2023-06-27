package com.christopher.todo_app.entity;

import com.christopher.todo_app.dto.ItemResponse;
import jakarta.persistence.*;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "item")
public class Item {

    public static Item of(ItemResponse itemResponse) {
        Item item = new Item();
        item.setMessage(itemResponse.getMessage());
        item.setId(itemResponse.getId());
        return item;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @NotBlank(message = "message must not be empty")
    @Column(name = "message", nullable = false)
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

    public Item(String message, Long id) {
        this.message = message;
        this.id = id;
    }

    public Item() {
    }
}
