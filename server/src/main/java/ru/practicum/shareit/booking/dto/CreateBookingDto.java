package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateBookingDto {
    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "Start date must be in the past")
    private LocalDateTime start;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "Start date must be in the past")
    private LocalDateTime end;

    @NotNull(message = "Item id is required")
    private Long itemId;
}
