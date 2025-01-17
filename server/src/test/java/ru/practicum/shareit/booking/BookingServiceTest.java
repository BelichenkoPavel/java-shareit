package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.exceptions.ForbiddenException;
import ru.practicum.shareit.exceptions.InternalServerException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemModelMapper;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserModelMapper;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @InjectMocks
    private BookingService bookingService;

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    @Mock
    private IBookingDBRepository bookingRepository;

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
            .build();

    BookingModel bookingModel = BookingModel.builder()
            .id(1L)
            .start(LocalDateTime.now())
            .end(LocalDateTime.now().plusDays(1))
            .item(ItemModelMapper.map(item))
            .booker(UserModelMapper.mapDto(user))
            .build();

    @Test
    public void testCreate() {
        Mockito.when(userService.getUser(ArgumentMatchers.anyLong()))
                .thenReturn(user);

        Mockito.when(itemService.getItemById(ArgumentMatchers.anyLong()))
                .thenReturn(item);

        Mockito.when(bookingRepository.save(ArgumentMatchers.any(BookingModel.class)))
                .thenReturn(bookingModel);

        BookingDto booking = bookingService.createBooking(createBookingDto, 1L);

        assertEquals(booking.getId(), bookingModel.getId());
    }

    @Test
    public void testCreateEqualDates() {
        LocalDateTime time = LocalDateTime.now();
        createBookingDto.setStart(time);
        createBookingDto.setEnd(time);

        Exception e = assertThrows(InternalServerException.class, () -> bookingService.createBooking(createBookingDto, 1L));

        assertEquals(e.getMessage(), "Start and end dates are equal");
    }

    @Test
    public void testUpdate() {
        Mockito.when(bookingRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(java.util.Optional.of(bookingModel));

        BookingDto booking = bookingService.updateBooking(1L, true, 1L);

        assertEquals(booking.getId(), bookingModel.getId());

        BookingDto booking2 = bookingService.updateBooking(1L, false, 1L);

        assertEquals(booking2.getId(), bookingModel.getId());
    }

    @Test
    public void testUpdateBookingNotFound() {
        Mockito.when(bookingRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(java.util.Optional.empty());

        Exception e = assertThrows(NotFoundException.class, () -> bookingService.updateBooking(1L, true, 1L));

        assertEquals(e.getMessage(), "Booking not found");
    }

    @Test
    public void testUpdateBookingNotOwner() {
        Mockito.when(bookingRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(java.util.Optional.of(bookingModel));

        Exception e = assertThrows(ForbiddenException.class, () -> bookingService.updateBooking(1L, true, 2L));

        assertEquals(e.getMessage(), "You are not owner of this item");
    }

    @Test
    public void testGetBooking() {
        Mockito.when(bookingRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(java.util.Optional.of(bookingModel));

        BookingDto booking = bookingService.getBooking(1L);

        assertEquals(booking.getId(), bookingModel.getId());
    }

    @Test
    public void testGetBookingNotFound() {
        Mockito.when(bookingRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(java.util.Optional.empty());

        Exception e = assertThrows(NotFoundException.class, () -> bookingService.getBooking(1L));

        assertEquals(e.getMessage(), "Booking not found");
    }

    @Test
    public void testGetBookings() {
        Mockito.when(bookingRepository.findByBookerId(ArgumentMatchers.anyLong()))
                .thenReturn(java.util.List.of(bookingModel));

        assertEquals(bookingService.getBookings(1L).size(), 1);
    }
}
