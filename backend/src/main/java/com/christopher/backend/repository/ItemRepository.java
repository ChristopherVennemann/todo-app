package com.christopher.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.christopher.backend.entity.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
