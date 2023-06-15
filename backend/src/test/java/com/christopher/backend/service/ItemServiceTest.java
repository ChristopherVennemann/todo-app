package com.christopher.backend.service;


import com.christopher.backend.entity.Item;
import com.christopher.backend.repository.ItemRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    public void shouldReturnItemList() {
        when(itemRepository.findAll()).thenReturn(Arrays.asList(
                new Item("item1", 1L),
                new Item("item2", 2L)
        ));

        List<Item> result = itemService.getItems();

        assertEquals("item1", result.get(0).getMessage());
        assertEquals("item2", result.get(1).getMessage());
    }
}