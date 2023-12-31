package com.christopher.todo_app.controller;

import com.christopher.todo_app.dto.ItemResponse;
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

import static com.christopher.todo_app.Constants.WAS_NOT_SUCCESSFUL;
import static com.christopher.todo_app.Constants.WAS_SUCESSFUL;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        final List<ItemResponse> expectedList = List.of(
            new ItemResponse(1L, "item 1", false),
            new ItemResponse(2L, "item 2", false),
            new ItemResponse(3L, "item 3", false)
        );
        when(itemService.getItems()).thenReturn(expectedList);

        mockMvc.perform(get("/items"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.itemResponseList[0].message", is(expectedList.get(0).getMessage())))
            .andExpect(jsonPath("$._embedded.itemResponseList[0].id", is(expectedList.get(0).getId()), Long.class))
            .andExpect(jsonPath("$._embedded.itemResponseList[1].message", is(expectedList.get(1).getMessage())))
            .andExpect(jsonPath("$._embedded.itemResponseList[1].id", is(expectedList.get(1).getId()), Long.class))
            .andExpect(jsonPath("$._embedded.itemResponseList[2].message", is(expectedList.get(2).getMessage())))
            .andExpect(jsonPath("$._embedded.itemResponseList[2].id", is(expectedList.get(2).getId()), Long.class));
    }

    //TODO: test for empty list

    @Test
    @DisplayName("POST /items - should return CREATED and saved item")
    void postItemsShouldReturnSavedItem() throws Exception {
        @Language("json") final String requestBody = """
            {
                "message": "item 1"
            }""";
        final ItemResponse expectedItem = new ItemResponse(1L, "item 1", false);

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
    @DisplayName("POST /items - should return BAD_REQUEST for empty request body")
    void shouldReturnBadRequestForEmptyRequest() throws Exception {
        final String requestBody = "{}";

        mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /items/{id} - should return NO_CONTENT for existing id")
    void shouldReturnNO_CONTENTForExistingId() throws Exception {
        final long deleteId = 1L;

        when(itemService.deleteItem(1L))
            .thenReturn(WAS_SUCESSFUL);

        mockMvc.perform(delete(String.format("/items/%d", deleteId)))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /items/{id} - should return NOT_FOUND for non-existing id")
    void shouldReturnNOT_FOUNDForNonExistingId() throws Exception {
        final long deleteId = 2L;

        when(itemService.deleteItem(2L))
            .thenReturn(WAS_NOT_SUCCESSFUL);

        mockMvc.perform(delete(String.format("/items/%d", deleteId)))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /items/{id}/done - should return OK and item with isDone==true for existing id")
    void shouldReturnOKAndDoneItemForExistingId() throws Exception {
        final ItemResponse expectedItem = new ItemResponse(1L, "test", true);

        when(itemService.setItemToDone(expectedItem.getId()))
            .thenReturn(expectedItem);

        mockMvc.perform(put(String.format("/items/%d/done", expectedItem.getId())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isDone", is(expectedItem.isDone())));
    }

    @Test
    @DisplayName("PUT /items/{id}/done - should return NO_CONTENT and empty body for non-existing id")
    void shouldReturnNO_CONTENTAndEmptyBodyForDoneOnNonExistingId() throws Exception {
        when(itemService.setItemToDone(1L))
            .thenReturn(null);

        mockMvc.perform(put(String.format("/items/%d/done", 1)))
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @DisplayName("PUT /items/{id}/undone - should return OK and item with isDone==false for existing id")
    void shouldReturnOKAndUndoneItemForExistingId() throws Exception {
        final ItemResponse expectedItem = new ItemResponse(1L, "test", false);

        when(itemService.setItemToUndone(expectedItem.getId()))
            .thenReturn(expectedItem);

        mockMvc.perform(put(String.format("/items/%d/undone", expectedItem.getId())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isDone", is(expectedItem.isDone())));
    }

    @Test
    @DisplayName("PUT /items/{id}/done - should return NO_CONTENT and empty body for non-existing id")
    void shouldReturnNO_CONTENTAndEmptyBodyForNonExistingId() throws Exception {
        when(itemService.setItemToDone(1L))
            .thenReturn(null);

        new ItemResponse(1L, "test", false);

        mockMvc.perform(put(String.format("/items/%d/undone", 1)))
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$").doesNotExist());
    }
}