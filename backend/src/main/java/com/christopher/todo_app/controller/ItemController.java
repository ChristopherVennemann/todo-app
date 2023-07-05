package com.christopher.todo_app.controller;

import com.christopher.todo_app.dto.ItemResponse;
import com.christopher.todo_app.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
@RequestMapping("/items")

public class ItemController {

    @Autowired
    private ItemService itemService;

    private static ItemResponse addLinks(ItemResponse item) {
        item.add(linkTo(methodOn(ItemController.class).getItems()).withRel(IanaLinkRelations.COLLECTION));
        item.add(linkTo(methodOn(ItemController.class).deleteItem(item.getId())).withRel("delete"));
        return item;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ItemResponse> getItems() {
        List<ItemResponse> items = itemService.getItems()
            .stream()
            .map(ItemController::addLinks)
            .toList();

        CollectionModel<ItemResponse> model = CollectionModel.of(items);
        model.add(linkTo(methodOn(ItemController.class).getItems()).withSelfRel());
        model.add(linkTo(methodOn(ItemController.class).saveItem(null)).withRel("post"));

        return model;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse saveItem(@Valid @RequestBody ItemResponse itemResponse) {
        return addLinks(itemService.saveItem(itemResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Long id) {
        boolean wasDeleted = itemService.deleteItem(id);
        if (!wasDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/done")
    public ResponseEntity<ItemResponse> setItemToDone(@PathVariable Long id) {
        ItemResponse response = itemService.setItemToDone(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
