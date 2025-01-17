package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.CommentModel;

import java.util.List;

public interface ICommentDBRepository extends JpaRepository<CommentModel, Long> {
    List<CommentModel> findAllByItemId(Long authorId);
}
