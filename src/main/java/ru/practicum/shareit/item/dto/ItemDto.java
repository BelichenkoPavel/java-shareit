package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ItemDto {
    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message = "Available is required")
    private Boolean available;

    @NotNull(message = "Owner is required")
    private UserDto owner;

    private String request;

    private List<CommentDto> comments = new ArrayList<>();

    private String lastBooking;

    private String nextBooking;
}
