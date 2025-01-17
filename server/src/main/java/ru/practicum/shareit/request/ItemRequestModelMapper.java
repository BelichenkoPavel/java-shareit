package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserModelMapper;

import java.time.LocalDateTime;

public class ItemRequestModelMapper {
    public static ItemRequestModel mapCreate(CreateItemRequestDto item, User user) {
        return ItemRequestModel.builder()
                .description(item.getDescription())
                .created(LocalDateTime.now())
                .user(UserModelMapper.mapDto(user))
                .build();
    }
}
