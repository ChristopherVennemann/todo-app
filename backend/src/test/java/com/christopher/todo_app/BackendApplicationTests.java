package com.christopher.todo_app;

import com.christopher.todo_app.dto.ItemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
    }

    @Test
    @DisplayName("GET /items - should return OK")
    void shouldReturnStatusOk200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/items"))
            .andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    @DisplayName("POST /items - should return CREATED and the created item for valid request")
    void shouldReturnStatusCreated201ForValidPost() throws Exception {
        @Language("json") final String requestBody = """
            {
                "message": "item 1"
            }""";
        final ItemResponse expectedItem = new ItemResponse(1L, "item 1", false);

        RequestBuilder request = MockMvcRequestBuilders
            .post("/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        mockMvc.perform(request)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message", is(expectedItem.getMessage())))
            .andExpect(jsonPath("$.id", is(expectedItem.getId()), Long.class));
    }

    @Test
    @DirtiesContext
    @DisplayName("POST /items - should return BAD_REQUEST for invalid request")
    void shouldReturnStatusBadRequest400ForInvalidPost() throws Exception {
        @Language("json") final String requestBody = """
            {
                "message": ""
            }""";

        RequestBuilder request = MockMvcRequestBuilders
            .post("/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        mockMvc.perform(request)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @DirtiesContext
    @DisplayName("DELETE /items/{id} - should return NO_CONTENT and delete the item for valid request")
    void shouldReturnStatusNoContent204ForValidDelete() throws Exception {
        final List<ItemResponse> expectedItems = List.of(
            new ItemResponse(null, "item1", false),
            new ItemResponse(null, "item2", false)
        );
        final String deletePath = "/items/1";

        for (ItemResponse item : expectedItems) {
            mockMvc.perform(MockMvcRequestBuilders
                .post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(item)));
        }

        mockMvc.perform(MockMvcRequestBuilders
                .delete(deletePath))
            .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/items"))
            .andExpect(jsonPath("$._embedded.itemResponseList", hasSize(expectedItems.size() - 1)))
            .andExpect(jsonPath("$._embedded.itemResponseList[0].message", is(expectedItems.get(1).getMessage())));
    }

    @Test
    @DirtiesContext
    @DisplayName("DELETE /items/{id} - should return NOT_FOUND and not delete items for invalid request")
    void shouldReturnStatusNotFound404ForValidDelete() throws Exception {
        final List<ItemResponse> expectedItems = List.of(
            new ItemResponse(null, "item1", false),
            new ItemResponse(null, "item2", false)
        );
        final String deletePath = "/items/3";

        for (ItemResponse item : expectedItems) {
            mockMvc.perform(MockMvcRequestBuilders
                .post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(item)));
        }

        mockMvc.perform(MockMvcRequestBuilders
                .delete(deletePath))
            .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/items"))
            .andExpect(jsonPath("$._embedded.itemResponseList", hasSize(2)));
    }

    @Test
    @DirtiesContext
    @DisplayName("PUT /items/{id}/done - Should return Status OK and item with isDone==true for valid request")
    void shouldReturnStatusOkAndDoneItemValidSetDoneRequest() throws Exception {
        @Language("json") final String postRequestBody = """
            {
                "message": "test"
            }""";

        mockMvc.perform(MockMvcRequestBuilders
            .post("/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(postRequestBody));

        mockMvc.perform(MockMvcRequestBuilders
                .put("/items/1/done"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.done", is(true)));
    }

    @Test
    @DirtiesContext
    @DisplayName("PUT /items/{id}/done - Should return Status NO_Content and no body for invalid request")
    void shouldReturnStatusNoContentAndNoBodyForInvalidSetDoneRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .put("/items/1/done"))
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @DirtiesContext
    @DisplayName("PUT /items/{id}/undone - Should return Status OK and item with isDone==false for valid request")
    void shouldReturnStatusOkAndUnoneItemValidSetUndoneRequest() throws Exception {
        @Language("json") final String postRequestBody = """
            {
                "message": "test"
            }""";

        mockMvc.perform(MockMvcRequestBuilders
            .post("/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(postRequestBody));

        mockMvc.perform(MockMvcRequestBuilders
                .put("/items/1/undone"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.done", is(false)));
    }

    @Test
    @DirtiesContext
    @DisplayName("PUT /items/{id}/undone - Should return Status NO_Content and no body for invalid request")
    void shouldReturnStatusNoContentAndNoBodyForInvalidSetUndoneRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .put("/items/1/undone"))
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$").doesNotExist());
    }
}
