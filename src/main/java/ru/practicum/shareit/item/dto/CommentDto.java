package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CommentDto {
    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Comment text is required")
    private String text;

    @NotNull(message = "Item is required")
    private ItemDto item;

    @NotNull(message = "Author is required")
    private String authorName;

    @NotNull(message = "Created is required")
    private LocalDateTime created;
}
