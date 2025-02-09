package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDtoMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    ItemService itemService;

    CreateItemDto createItemDto = CreateItemDto
            .builder()
            .name("name")
            .requestId(1L)
            .description("description")
            .available(true)
            .build();

    UpdateItemDto updateItemDto = UpdateItemDto.builder()
            .name("name")
            .description("description")
            .available(true)
            .build();

    User userDto = User.builder()
            .id(1L)
            .name("name")
            .email("test@test.com")
            .build();

    Item itemModel = Item.builder()
            .id(1L)
            .name("name")
            .description("description")
            .available(true)
            .owner(userDto)
            .comments(List.of())
            .build();

    ItemDto itemDto = ItemDto.builder()
            .id(1L)
            .name("name")
            .description("description")
            .available(true)
            .owner(UserDtoMapper.map(userDto))
            .comments(List.of())
            .build();

    CommentCreateDto commentCreateDto = CommentCreateDto
            .builder()
            .text("test")
            .build();

    CommentDto commentDto = CommentDto
            .builder()
            .id(1L)
            .text("test text")
            .build();

    @Test
    public void testCreate() throws Exception {
        Mockito.when(itemService.addItem(ArgumentMatchers.any(CreateItemDto.class), ArgumentMatchers.anyLong()))
                .thenReturn(itemModel);

        String result = mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(createItemDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(itemDto));
    }

    @Test
    public void testUpdate() throws Exception {
        Mockito.when(itemService.updateItem(ArgumentMatchers.anyLong(), ArgumentMatchers.any(UpdateItemDto.class), ArgumentMatchers.anyLong()))
                .thenReturn(itemModel);

        String result = mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(updateItemDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(itemDto));
    }

    @Test
    public void testGetById() throws Exception {
        Mockito.when(itemService.getItem(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .thenReturn(itemModel);

        String result = mvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(itemDto));
    }

    @Test
    public void testGetList() throws Exception {
        Mockito.when(itemService.getItems(ArgumentMatchers.anyLong()))
                .thenReturn(List.of(itemModel));

        String result = mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(List.of(itemDto)));
    }

    @Test
    public void testSearch() throws Exception {
        Mockito.when(itemService.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(itemModel));

        String result = mvc.perform(get("/items/search")
                        .header("X-Sharer-User-Id", 1L)
                        .param("text", "test")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(List.of(itemDto)));
    }

    @Test
    public void testAddComment() throws Exception {
        Mockito.when(itemService.addComment(ArgumentMatchers.anyLong(), ArgumentMatchers.any(CommentCreateDto.class), ArgumentMatchers.anyLong()))
                .thenReturn(commentDto);

        String result = mvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(commentCreateDto))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(commentDto));
    }
}
