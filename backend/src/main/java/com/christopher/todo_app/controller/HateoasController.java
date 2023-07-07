package com.christopher.todo_app.controller;

import com.christopher.todo_app.HateoasStarter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/hateoas")
public class HateoasController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public HateoasStarter getStarterLinks() {
        final HateoasStarter starter = new HateoasStarter("link to items collection");
        starter.add(linkTo(methodOn(ItemController.class).getItems()).withRel("itemsCollection"));
        return starter;
    }
}
