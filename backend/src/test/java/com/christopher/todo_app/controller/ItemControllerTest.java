package com.christopher.todo_app.controller;

import com.christopher.todo_app.entity.Item;
import com.christopher.todo_app.service.ItemService;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    @DisplayName("GET /items - returns OK and list of all items")
    void getItemsShouldReturnListOfAllItems() throws Exception {
        final List<Item> expectedList = List.of(
            new Item("item 1", 1L),
            new Item("item 2", 2L),
            new Item("item 3", 3L)
        );
        when(itemService.getItems()).thenReturn(expectedList);

        mockMvc.perform(get("/items"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].message", is(expectedList.get(0).getMessage())))
            .andExpect(jsonPath("$[0].id", is(expectedList.get(0).getId()), Long.class))
            .andExpect(jsonPath("$[1].message", is(expectedList.get(1).getMessage())))
            .andExpect(jsonPath("$[1].id", is(expectedList.get(1).getId()), Long.class))
            .andExpect(jsonPath("$[2].message", is(expectedList.get(2).getMessage())))
            .andExpect(jsonPath("$[2].id", is(expectedList.get(2).getId()), Long.class));
    }

    //TODO: test for empty list

    @Test
    @DisplayName("POST /items - should return CREATED and saved item")
    void postItemsShouldReturnSavedItem() throws Exception {
        @Language("json") final String requestBody = """
            {
                "message": "item 1"
            }""";
        final Item expectedItem = new Item("item 1", 1L);

        when(itemService.saveItem(any()))
            .thenReturn(expectedItem);

        mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message", is(expectedItem.getMessage())))
            .andExpect(jsonPath("$.id", is(expectedItem.getId()), Long.class));
    }

    @Test
    @DisplayName("POST /items - should return BAD_REQUEST for empty message")
    void postItemsShouldReturnBadRequestForEmptyMessage() throws Exception {
        @Language("json") final String requestBodyWithEmptyMessage = """
            {
                "message": ""
            }""";

        mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyWithEmptyMessage))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /items - should return BAD_REQUEST for empty request")
    void postItemsShouldReturnBadRequestForEmptyRequest() throws Exception {
        final String emptyRequestBody = "{}";

        mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emptyRequestBody))
            .andExpect(status().isBadRequest());
    }
}
