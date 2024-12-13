package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.User;

@Data
@Builder
public class Item {
    @NotNull
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
    private String request;
}
