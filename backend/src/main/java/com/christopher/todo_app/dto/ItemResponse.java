package com.christopher.todo_app.dto;


import com.christopher.todo_app.entity.Item;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse extends RepresentationModel<ItemResponse> {

    @Getter
    private Long id;

    @Nonnull
    @NotBlank(message = "message must not be empty")
    @Getter
    private String message;

    @Getter
    @Setter
    private boolean isDone;

    public static ItemResponse of(Item item) {
        return new ItemResponse(item.getId(), item.getMessage(), item.isDone());
    }

    public static List<ItemResponse> of(List<Item> items) {
        return items.stream()
            .map(item -> new ItemResponse(item.getId(), item.getMessage(), item.isDone()))
            .toList();
    }
}
