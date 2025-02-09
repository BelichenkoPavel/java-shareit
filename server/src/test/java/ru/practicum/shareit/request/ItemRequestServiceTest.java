package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class ItemRequestServiceTest {
    @InjectMocks
    ItemRequestService itemRequestService;

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    @Mock
    private IItemRequestDBRepository repository;

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

    ItemRequestModel model = ItemRequestModel.builder()
            .id(1L)
            .description("description")
            .created(LocalDateTime.now())
            .build();

    CreateItemRequestDto dto = CreateItemRequestDto.builder()
            .description("description")
            .build();

    @Test
    public void testCreate() {
        Mockito.when(userService.getUser(ArgumentMatchers.anyLong()))
                .thenReturn(user);

        Mockito.when(repository.save(ArgumentMatchers.any(ItemRequestModel.class)))
                .thenReturn(model);

        ItemRequestDto result = itemRequestService.create(dto, 1L);

        assertEquals(result.getId(), model.getId());
    }

    @Test
    public void testGetById() {
        Mockito.when(repository.getById(ArgumentMatchers.anyLong()))
                .thenReturn(model);

        Mockito.when(itemService.getByRequestId(ArgumentMatchers.anyLong()))
                .thenReturn(List.of(item));

        ItemRequestDto result = itemRequestService.getById(1L);

        assertEquals(result.getId(), model.getId());
    }

    @Test
    public void testGetAll() {
        Mockito.when(userService.getUser(ArgumentMatchers.anyLong()))
                .thenReturn(user);

        Mockito.when(repository.findByUserId(ArgumentMatchers.anyLong()))
                .thenReturn(List.of(model));

        List<ItemRequestDto> result = itemRequestService.getAll(1L);

        assertEquals(result.size(), 1);
    }
}
