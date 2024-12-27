package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemModel;
import ru.practicum.shareit.user.UserDtoMapper;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ItemDtoMapper {
    public static List<ItemDto> mapList(List<Item> items) {
        return items.stream()
                .map(ItemDtoMapper::map)
                .toList();
    }

    public static ItemDto map(Item item) {
        UserDto owner = UserDtoMapper.map(item.getOwner());
        ItemDto itemDto = ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(owner)
                .request(item.getRequest())
                .build();

        if (item.getComments().isEmpty()) {
            itemDto.setComments(new ArrayList<>());
        } else {
            itemDto.setComments(CommentDtoMapper.mapList(item.getComments()));
        }

        return itemDto;
    }

    public static ItemDto map(ItemModel model) {
        return ItemDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .available(model.getAvailable())
                .owner(UserDtoMapper.mapModel(model.getOwner()))
                .build();
    }
}
