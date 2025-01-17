package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
public class ItemRequestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    ItemRequestService service;

    ItemRequestDto dto = ItemRequestDto.builder()
            .id(1L)
            .description("description")
            .created(LocalDateTime.now())
            .build();

    CreateItemRequestDto createItemRequestDto = CreateItemRequestDto.builder()
            .description("description")
            .build();

    @Test
    void testCreate() throws Exception {
        Mockito.when(service.create(ArgumentMatchers.any(CreateItemRequestDto.class), ArgumentMatchers.anyLong()))
                .thenReturn(dto);

        String result = mvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(createItemRequestDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(dto));
    }

    @Test
    void testGetById() throws Exception {
        Mockito.when(service.getById(ArgumentMatchers.anyLong()))
                .thenReturn(dto);

        String result = mvc.perform(get("/requests/1")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(dto));
    }

    @Test
    void testGetAll() throws Exception {
        Mockito.when(service.getAll(ArgumentMatchers.anyLong()))
                .thenReturn(List.of(dto));

        String result = mvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(List.of(dto)));
    }
}
