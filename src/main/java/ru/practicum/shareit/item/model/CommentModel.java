package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.user.UserModel;

import java.time.LocalDateTime;

@Builder
@Table(name = "comment", schema = "public")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @ManyToOne()
    @JoinColumn(name = "item_id")
    private ItemModel item;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    private UserModel author;

    @Column(name = "\"created\"", columnDefinition = "TIMESTAMP")
    private LocalDateTime created;
}
