package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.model.CommentModel;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserModelMapper;

import java.time.LocalDateTime;

public class CommentModelMapper {
    static CommentModel createMap(CommentCreateDto createDto, Item item, User user) {
        return CommentModel.builder()
                .text(createDto.getText())
                .author(UserModelMapper.mapDto(user))
                .created(LocalDateTime.now())
                .item(ItemModelMapper.map(item))
                .build();
    }
}
