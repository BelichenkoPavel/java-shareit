package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public class ItemRequestMapper {
    public static ItemRequestModel map(ItemRequestDto item) {
        return ItemRequestModel.builder()
                .id(item.getId())
                .description(item.getDescription())
                .created(item.getCreated())
                .build();
    }

    public static List<ItemRequestModel> mapList(List<ItemRequestDto> items) {
        return items.stream()
                .map(ItemRequestMapper::map)
                .toList();
    }
}
