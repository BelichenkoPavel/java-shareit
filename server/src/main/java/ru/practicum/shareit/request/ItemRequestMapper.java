package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;

public class ItemRequestMapper {
    public static ItemRequestModel map(ItemRequestDto item) {
        return ItemRequestModel.builder()
                .id(item.getId())
                .description(item.getDescription())
                .created(item.getCreated())
                .build();
    }
}
