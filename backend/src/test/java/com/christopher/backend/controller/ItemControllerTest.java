package com.christopher.backend.controller;

import com.christopher.backend.entity.Item;
import com.christopher.backend.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
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
    public void shouldReturnListOfAllItems() throws Exception{
        List<Item> expectedList = List.of(
                new Item("item 1", 1L),
                new Item("item 2", 2L),
                new Item("item 3", 3L)
        );

        when(itemService.getItems()).thenReturn(
                expectedList);

        this.mockMvc.perform(get("/items"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].message", is(expectedList.get(0).getMessage())))
                .andExpect(jsonPath("$[0].id", is(expectedList.get(0).getId()), Long.class))

                .andExpect(jsonPath("$[1].message", is(expectedList.get(1).getMessage())))
                .andExpect(jsonPath("$[1].id", is(expectedList.get(1).getId()), Long.class))

                .andExpect(jsonPath("$[2].message", is(expectedList.get(2).getMessage())))
                .andExpect(jsonPath("$[2].id", is(expectedList.get(2).getId()), Long.class));
    }
}