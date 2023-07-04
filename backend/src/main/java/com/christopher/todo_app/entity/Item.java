package com.christopher.todo_app.entity;

import com.christopher.todo_app.dto.ItemResponse;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item")
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Nonnull
    @NotBlank(message = "message must not be empty")
    @Column(name = "message", nullable = false)
    @Getter
    private String message;

    @Getter
    @Setter
    @Column(name = "is_done", nullable = false)
    private boolean isDone;

    public Item() {
        isDone = false;
    }

    public static Item of(ItemResponse itemResponse) {
        return new Item(itemResponse.getId(), itemResponse.getMessage(), itemResponse.isDone());
    }
}
