package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.user.UserModel;

import java.time.LocalDateTime;

@Builder
@Table(name = "request", schema = "public")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserModel user;

    @Column(name = "\"created\"", columnDefinition = "TIMESTAMP")
    private LocalDateTime created;
}
