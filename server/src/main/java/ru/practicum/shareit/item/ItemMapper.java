package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.model.CommentModel;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemModel;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserModelMapper;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ItemMapper {
    public static Item map(ItemModel item) {
        User user = UserModelMapper.map(item.getOwner());

        Item.ItemBuilder builder = Item.builder();

        if (item.getRequest() != null) {
            builder.requestId(item.getRequest().getId());
        }

        return builder
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(user)
                .comments(new ArrayList<>())
                .build();
    }

    public static Item map(ItemModel item, List<CommentModel> commentModels) {
        User user = UserModelMapper.map(item.getOwner());

        Item.ItemBuilder builder = Item.builder();

        if (item.getRequest() != null) {
            builder.requestId(item.getRequest().getId());
        }

        return builder
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(user)
                .comments(CommentMapper.mapList(commentModels))
                .build();
    }

    public static List<Item> mapModelsList(List<ItemModel> items) {
        return items.stream()
                .map(ItemMapper::map)
                .toList();
    }
}
