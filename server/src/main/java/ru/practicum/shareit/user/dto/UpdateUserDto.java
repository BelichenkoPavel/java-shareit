package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserDto {
    private String name;

    @Email(regexp = ".+[@].+[\\.].+", message = "Email is not valid")
    private String email;
}
