package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentModel;

import java.util.List;

@UtilityClass
public class CommentDtoMapper {
    public static CommentDto map(CommentModel model) {
        return CommentDto.builder()
                .id(model.getId())
                .text(model.getText())
                .authorName(model.getAuthor().getName())
                .build();
    }

    public static CommentDto map(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .build();
    }

    public static List<CommentDto> mapList(List<Comment> comments) {
        return comments.stream()
                .map(CommentDtoMapper::map)
                .toList();
    }
}
