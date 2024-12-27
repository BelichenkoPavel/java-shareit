package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentModel;
import ru.practicum.shareit.user.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CommentMapper {
    public static Comment map(CommentModel model) {
        return Comment.builder()
                .id(model.getId())
                .text(model.getText())
                .created(model.getCreated())
                .author(UserMapper.map(model.getAuthor()))
                .build();
    }

    public static List<Comment> mapList(List<CommentModel> models) {
        return models.stream()
                .map(CommentMapper::map)
                .collect(Collectors.toList());
    }
}
