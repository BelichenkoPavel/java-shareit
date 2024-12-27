package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Builder
@Data
public class BookingDto {
    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Start date is required")
    private LocalDateTime start;

    @NotNull(message = "End date is required")
    private LocalDateTime end;

    @NotNull(message = "Item is required")
    private ItemDto item;

    private UserDto booker;

    @NotNull(message = "Status is required")
    private Status status;
}
