package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    UserService service;

    CreateUserDto createUserDto = CreateUserDto
            .builder()
            .email("test@test.ru")
            .name("test")
            .build();

    UserDto userDto = UserDto
            .builder()
            .email("test@test.ru")
            .name("test")
            .id(1L)
            .build();

    User user = User
            .builder()
            .email("test@test.ru")
            .name("test")
            .id(1L)
            .build();

    UpdateUserDto updateUserDto = UpdateUserDto
            .builder()
            .email("test@test.ru")
            .name("test")
            .build();

    @Test
    public void testCreate() throws Exception {
        Mockito.when(service.createUser(ArgumentMatchers.any(CreateUserDto.class))).thenReturn(user);

        String result = mvc.perform(post("/users")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(createUserDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(userDto));
    }

    @Test
    public void testUpdate() throws Exception {
        Mockito.when(service.updateUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any(UpdateUserDto.class))).thenReturn(user);

        String result = mvc.perform(patch("/users/1")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(userDto));
    }

    @Test
    public void testGetById() throws Exception {
        Mockito.when(service.getUser(ArgumentMatchers.anyLong())).thenReturn(user);

        String result = mvc.perform(get("/users/1")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(userDto));
    }
}
