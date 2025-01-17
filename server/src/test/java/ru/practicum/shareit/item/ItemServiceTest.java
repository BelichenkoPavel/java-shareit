package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemModel;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDtoMapper;
import ru.practicum.shareit.user.UserModelMapper;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @InjectMocks
    ItemService itemService;

    @Mock
    private UserService userService;

    @Mock
    private ItemRequestService itemRequestService;

    @Mock
    private BookingService bookingService;

    @Mock
    private IItemDBRepository repository;

    @Mock
    private ICommentDBRepository commentDBRepository;

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

    ItemModel itemModel = ItemModel.builder()
            .id(1L)
            .name("name")
            .description("description")
            .available(true)
            .owner(UserModelMapper.mapDto(user))
            .build();

    ItemRequestDto itemRequestDto = ItemRequestDto
            .builder()
            .id(1L)
            .created(LocalDateTime.now())
            .build();

    BookingDto bookingDto = BookingDto.builder()
            .id(1L)
            .booker(UserDtoMapper.map(user))
            .status(Status.APPROVED)
            .end(LocalDateTime.now())
            .item(ItemDtoMapper.map(itemModel))
            .build();

    CommentCreateDto commentCreateDto = CommentCreateDto
            .builder()
            .text("test text")
            .build();

    @Test
    public void testCreate() {
        Mockito.when(userService.getUser(ArgumentMatchers.anyLong()))
                .thenReturn(user);

        Mockito.when(repository.save(ArgumentMatchers.any(ItemModel.class)))
                .thenReturn(itemModel);

        Mockito.when(itemRequestService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(itemRequestDto);

        Item item = itemService.addItem(createItemDto, 1L);

        assertEquals(item.getId(), 1L);
    }

    @Test
    public void testUpdate() {
        Mockito.when(userService.getUser(ArgumentMatchers.anyLong()))
                .thenReturn(user);

        Mockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(itemModel));

        Mockito.when(repository.save(ArgumentMatchers.any(ItemModel.class)))
                .thenReturn(itemModel);

        Item item = itemService.updateItem(1L, updateItemDto, 1L);

        assertEquals(item.getId(), 1L);
    }

    @Test
    public void testUpdateUpdateSelfItem() {
        user.setId(2L);

        Mockito.when(userService.getUser(ArgumentMatchers.anyLong()))
                .thenReturn(user);

        Exception e = assertThrows(NotFoundException.class, () -> itemService.updateItem(1L, updateItemDto, 1L));

        assertEquals(e.getMessage(), "You can't update your own item");
    }

    @Test
    public void testUpdateItemNotFound() {
        Mockito.when(userService.getUser(ArgumentMatchers.anyLong()))
                .thenReturn(user);

        Mockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Exception e = assertThrows(NotFoundException.class, () -> itemService.updateItem(1L, updateItemDto, 1L));

        assertEquals(e.getMessage(), "Item not found");
    }

    @Test
    public void testGetItemById() {
        Mockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(itemModel));

        Item item = itemService.getItemById(1L);

        assertEquals(item.getId(), 1L);
    }

    @Test
    public void testGetItemByIdNotFound() {
        Mockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Exception e = assertThrows(NotFoundException.class, () -> itemService.getItemById(1L));

        assertEquals(e.getMessage(), "Item not found");
    }

    @Test
    public void testGetItemByIdNotAvailable() {
        itemModel.setAvailable(false);
        Mockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(itemModel));

        Exception e = assertThrows(BadRequestException.class, () -> itemService.getItemById(1L));

        assertEquals(e.getMessage(), "Item is not available");
    }

    @Test
    public void testAddComment() {
        Mockito.when(userService.getUser(ArgumentMatchers.anyLong()))
                .thenReturn(user);

        Mockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(itemModel));

        Mockito.when(bookingService.getBookings(ArgumentMatchers.anyLong()))
                .thenReturn(List.of(bookingDto));

        itemService.addComment(1L, commentCreateDto, 1L);
    }

    @Test
    public void testAddCommentBookingsNotFound() {
        Mockito.when(userService.getUser(ArgumentMatchers.anyLong()))
                .thenReturn(user);

        Mockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(itemModel));

        Mockito.when(bookingService.getBookings(ArgumentMatchers.anyLong()))
                .thenReturn(List.of());

        Exception e = assertThrows(BadRequestException.class, () -> itemService.addComment(1L, commentCreateDto, 1L));

        assertEquals(e.getMessage(), "You can't comment item");
    }
}
