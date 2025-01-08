package ru.practicum.shareit.user;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.user.dto.UserDto;

@UtilityClass
public class UserMapper {
    public static User map(UserDto user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User map(UserModel model) {
        return User.builder()
                .id(model.getId())
                .name(model.getName())
                .email(model.getEmail())
                .build();
    }
}
