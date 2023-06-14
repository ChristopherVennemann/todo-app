package com.christopher.backend;

import com.christopher.backend.entity.Item;
import com.christopher.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

	@Autowired
	ItemRepository itemRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Item[] items = new Item[] {
				new Item("item 1", 1L),
				new Item("item 2", 2L),
				new Item("item 3", 3L)
		};

		itemRepository.saveAll(Arrays.asList(items));

		List<Item> retrievedItems = (List<Item>) itemRepository.findAll();

		for (Item item : retrievedItems) {
			System.out.println(item.getMessage());
		}
	}


}