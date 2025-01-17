package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.item.ItemDtoMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDtoMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookingService bookingService;

    CreateBookingDto createBookingDto = CreateBookingDto
            .builder()
            .itemId(1L)
            .start(LocalDateTime.now())
            .end(LocalDateTime.now().plusDays(1))
            .build();

    User user = User.builder()
            .id(1L)
            .name("name")
            .email("test@test.com")
            .build();

    Item item = Item.builder()
            .id(1L)
            .name("name")
            .description("description")
            .requestId(1L)
            .available(true)
            .owner(user)
            .comments(List.of())
            .build();

    BookingDto bookingModel = BookingDto.builder()
            .id(1L)
            .start(LocalDateTime.now())
            .end(LocalDateTime.now().plusDays(1))
            .item(ItemDtoMapper.map(item))
            .booker(UserDtoMapper.map(user))
            .build();

    @Test
    public void testCreate() throws Exception {
        Mockito.when(bookingService.createBooking(ArgumentMatchers.any(CreateBookingDto.class), ArgumentMatchers.anyLong()))
                .thenReturn(bookingModel);

        String result = mvc.perform(post("/bookings")
                .header("X-Sharer-User-Id", 1L)
                .contentType("application/json")
                .content(mapper.writeValueAsString(createBookingDto)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(bookingModel));
    }
    @Test
    public void testUpdate() throws Exception {
        Mockito.when(bookingService.updateBooking(ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean(), ArgumentMatchers.anyLong()))
                .thenReturn(bookingModel);

        String result = mvc.perform(patch("/bookings/1")
                        .header("X-Sharer-User-Id", 1L)
                        .param("approved", "true")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(bookingModel));
    }

    @Test
    public void testGetOwnerList() throws Exception {
        Mockito.when(bookingService.getBookings(ArgumentMatchers.anyLong()))
                .thenReturn(List.of(bookingModel));

        String result = mvc.perform(get("/bookings/owner")
                .header("X-Sharer-User-Id", 1L)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(List.of(bookingModel)));
    }

    @Test
    public void testGetGetId() throws Exception {
        Mockito.when(bookingService.getBooking(ArgumentMatchers.anyLong()))
                .thenReturn(bookingModel);

        String result = mvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(bookingModel));
    }

    @Test
    public void testGetGetList() throws Exception {
        Mockito.when(bookingService.getBookings(ArgumentMatchers.anyLong()))
                .thenReturn(List.of(bookingModel));

        String result = mvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(List.of(bookingModel)));
    }
}
