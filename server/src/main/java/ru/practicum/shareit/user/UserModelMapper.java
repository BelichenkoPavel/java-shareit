package ru.practicum.shareit.user;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.user.dto.CreateUserDto;

@UtilityClass
public class UserModelMapper {
    public static UserModel mapCreateDto(CreateUserDto user) {
        return UserModel.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static UserModel mapDto(User user) {
        return UserModel.builder()
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
