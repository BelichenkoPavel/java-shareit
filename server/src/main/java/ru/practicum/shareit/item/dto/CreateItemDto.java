package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateItemDto {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Available is required")
    private Boolean available;

    private Long requestId;
}
