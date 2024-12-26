package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemModel;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserModelMapper;

public class ItemModelMapper {
    public static ItemModel mapCreateItem(CreateItemDto itemDto, User user) {
        return ItemModel.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .owner(UserModelMapper.mapDto(user))
                .build();
    }

    public static ItemModel map(Item item) {
        return ItemModel.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(UserModelMapper.mapDto(item.getOwner()))
                .build();
    }
}
