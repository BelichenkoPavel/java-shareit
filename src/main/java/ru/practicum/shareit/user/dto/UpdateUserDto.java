package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateUserDto {
    private String name;

    @Email(regexp = ".+[@].+[\\.].+", message = "Email is not valid")
    private String email;
}
