package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateItemRequestDto {
    @NotNull(message = "description не может быть пустым")
    private String description;
}
