package ru.practicum.shareit.request;

import ru.practicum.shareit.item.ItemDtoMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public class ItemRequestDtoMapper {
    public static ItemRequestDto map(ItemRequestModel item) {
        return ItemRequestDto.builder()
                .id(item.getId())
                .description(item.getDescription())
                .created(item.getCreated())
                .build();
    }

    public static ItemRequestDto map(ItemRequestModel item, List<Item> list) {
        return ItemRequestDto.builder()
                .id(item.getId())
                .description(item.getDescription())
                .created(item.getCreated())
                .items(ItemDtoMapper.mapList(list))
                .build();
    }

    public static List<ItemRequestDto> mapList(List<ItemRequestModel> items) {
        return items.stream()
                .map(ItemRequestDtoMapper::map)
                .toList();
    }
}
