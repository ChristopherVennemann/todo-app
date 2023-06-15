package com.christopher.backend.controller;

import com.christopher.backend.entity.Item;
import com.christopher.backend.repository.ItemRepository;
import com.christopher.backend.service.ItemService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    public void shouldReturnAllItems() throws Exception{
        when(itemService.getItems()).thenReturn(
                List.of(
                        new Item("item 1", 1L),
                        new Item("item 2", 2L),
                        new Item("item 3", 3L)
                ));

        this.mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message", Matchers.is("item 1")));
    }
}