package com.christopher.todo_app.controller;

import com.christopher.todo_app.HateoasStarter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
@RequestMapping("/hateoas")
public class HateoasController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public HateoasStarter getStarterLinks() {
        final HateoasStarter starter = new HateoasStarter();
        starter.add(linkTo(methodOn(ItemController.class).getItems()).withRel("itemCollection"));
        return starter;
    }
}
