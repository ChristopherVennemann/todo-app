package com.christopher.todo_app.service;

import com.christopher.todo_app.entity.Item;
import com.christopher.todo_app.repository.ItemRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

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
        Item expectedItem1 = new Item("item1", 1L);
        Item expectedItem2 = new Item("item2", 2L);
        List<Item> expectedList = Arrays.asList(
                expectedItem1,
                expectedItem2
        );
        when(itemRepository.findAll()).thenReturn(expectedList);

        List<Item> actualList = itemService.getItems();

        assertThat(actualList)
                .hasSize(2)
                .containsExactly(expectedItem1, expectedItem2);
    }
}