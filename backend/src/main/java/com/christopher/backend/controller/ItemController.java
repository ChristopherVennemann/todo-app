package com.christopher.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/items")
public class ItemController {

    @GetMapping
    public ResponseEntity<String> getItems() {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
