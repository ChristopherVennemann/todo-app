package com.christopher.todo_app.repository;

import org.springframework.data.repository.CrudRepository;

import com.christopher.todo_app.entity.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
