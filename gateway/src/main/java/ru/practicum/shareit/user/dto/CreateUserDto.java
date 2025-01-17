package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserDto {
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    @Email(regexp = ".+[@].+[\\.].+", message = "Email is not valid")
    private String email;
}
