package com.christopher.todo_app;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
public class HateoasStarter extends RepresentationModel<HateoasStarter> {
    private String description;
}
