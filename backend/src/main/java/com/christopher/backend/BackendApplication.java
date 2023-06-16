package com.christopher.backend;

import com.christopher.backend.entity.Item;
import com.christopher.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class BackendApplication /*implements CommandLineRunner*/ {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}