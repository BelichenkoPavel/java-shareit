package ru.practicum.shareit.booking;

import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;

public class Booking {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private Booking booker;
    private Status status;
}
