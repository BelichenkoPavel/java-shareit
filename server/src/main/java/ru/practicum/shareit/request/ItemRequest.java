package ru.practicum.shareit.request;

import lombok.Builder;
import lombok.Getter;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Builder
@Getter
public class ItemRequest {
    private Long id;
    private String description;
    private User requester;
    private LocalDateTime created;
}
