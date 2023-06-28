package com.christopher.todo_app.service;

import com.christopher.todo_app.dto.ItemResponse;
import com.christopher.todo_app.entity.Item;
import com.christopher.todo_app.repository.ItemRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Should return list of all Items")
    public void shouldReturnItemList() {
        final Item expectedItem1 = new Item(1L, "item1");
        final Item expectedItem2 = new Item(2L, "item2");
        final List<Item> expectedList = Arrays.asList(
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
    @DisplayName("Should save an Item and return it")
    public void shouldSaveItemAndReturnIt() {
        final ItemResponse initialItem = new ItemResponse(null, "item1");
        final ItemResponse expectedItem = new ItemResponse(1L, "item1");
        when(itemRepository.save(any(Item.class))).thenReturn(Item.of(expectedItem));

        ItemResponse actualItem = itemService.saveItem(initialItem);

        assertThat(actualItem)
            .usingRecursiveComparison()
            .isEqualTo(expectedItem);
    }

    @Test
    @DisplayName("Should return WAS_DELETED if Id exists")
    public void shouldReturnWAS_DELETEDIfIdExists() {
        final long deleteId = 1L;
        when(itemRepository.existsById(1L)).thenReturn(true);

        final boolean wasSuccessful = itemService.deleteItem(deleteId);

        assertThat(wasSuccessful).isTrue();
    }

    @Test
    @DisplayName("Should return WAS_NOT_DELETED if Id does not exists")
    public void shouldReturnWAS_NOT_DELETEDIfIdDoesntExist() {
        final long deleteId = 2L;
        when(itemRepository.existsById(2L)).thenReturn(false);

        final boolean wasSuccessful = itemService.deleteItem(deleteId);

        assertThat(wasSuccessful).isFalse();
    }
}
