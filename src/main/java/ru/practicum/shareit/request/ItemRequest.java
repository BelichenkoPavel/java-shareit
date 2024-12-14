package ru.practicum.shareit.request;

import ru.practicum.shareit.user.User;

import java.util.Date;

public class ItemRequest {
    private Long id;
    private String description;
    private User requester;
    private Date created;
}
