package com.christopher.todo_app;

import com.christopher.todo_app.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
	public void shouldReturnStatusOk200() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/items");

		mockMvc.perform(request)
				.andExpect(status().isOk());
	}

	@Test
	public void shouldReturnStatusCreated201ForValidPost() throws Exception {
		String requestBody = "{\"message\": \"item 1\"}";
		Item expectedItem = new Item("item 1", 1L);

		RequestBuilder request = MockMvcRequestBuilders.post("/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody);

		mockMvc.perform(request)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.message", is(expectedItem.getMessage())))
				.andExpect(jsonPath("$.id", is(expectedItem.getId()), Long.class));
	}
	@Test
	public void shouldReturnStatusBadRequest400ForInvalidPost() throws Exception {
		String requestBody = "{\"message\": \"\"}";

		RequestBuilder request = MockMvcRequestBuilders.post("/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody);

		mockMvc.perform(request)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").doesNotExist());
	}
}
