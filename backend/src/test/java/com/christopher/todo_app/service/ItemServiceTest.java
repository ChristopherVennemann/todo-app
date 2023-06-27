package com.christopher.todo_app.service;

import com.christopher.todo_app.dto.ItemResponse;
import com.christopher.todo_app.entity.Item;
import com.christopher.todo_app.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

        List<ItemResponse> actualList = itemService.getItems();

        assertThat(actualList)
                .hasSize(2)
                .usingRecursiveComparison()
                .isEqualTo(ItemResponse.of(expectedList));
    }

    @Test
    public void shouldSaveItemAndReturnIt() throws Exception {
        ItemResponse initialItem = new ItemResponse("item1", null);
        ItemResponse expectedItem = new ItemResponse("item1", 1L);
        when(itemRepository.save(any(Item.class))).thenReturn(Item.of(expectedItem));

        ItemResponse actualItem = itemService.saveItem(initialItem);

        assertThat(actualItem)
                .usingRecursiveComparison()
                .isEqualTo(expectedItem);
    }
}