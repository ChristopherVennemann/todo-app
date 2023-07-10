package com.christopher.todo_app.dto;


import com.christopher.todo_app.entity.Item;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@AllArgsConstructor
@Getter
public class ItemResponse extends RepresentationModel<ItemResponse> {

    private Long id;

    @NonNull
    @NotBlank(message = "message must not be empty")
    private String message;

    @JsonProperty("isDone")
    @Setter
    private boolean isDone;

    public static ItemResponse of(final Item item) {
        return new ItemResponse(item.getId(), item.getMessage(), item.isDone());
    }

    public static List<ItemResponse> of(final List<Item> items) {
        return items.stream()
            .map(ItemResponse::of)
            .toList();
    }
}
