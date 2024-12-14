package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

public class UserDtoMapper {
    public static UserDto map(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
