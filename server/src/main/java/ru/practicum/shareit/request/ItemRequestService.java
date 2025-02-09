package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Lazy
public class ItemRequestService {
    private final UserService userService;

    private final ItemService itemService;

    private final IItemRequestDBRepository repository;

    public ItemRequestDto create(CreateItemRequestDto dto, Long userId) {
        User user = userService.getUser(userId);

        ItemRequestModel model = ItemRequestModelMapper.mapCreate(dto, user);

        ItemRequestModel createRequest = repository.save(model);

        return ItemRequestDtoMapper.map(createRequest);
    }

    public ItemRequestDto getById(Long id) {
        ItemRequestModel model = repository.getById(id);

        List<Item> list = itemService.getByRequestId(id);

        return ItemRequestDtoMapper.map(model, list);
    }

    public List<ItemRequestDto> getAll(Long userId) {
        User user = userService.getUser(userId);

        List<ItemRequestModel> list = repository.findByUserId(user.getId());

        return ItemRequestDtoMapper.mapList(list);
    }
}
