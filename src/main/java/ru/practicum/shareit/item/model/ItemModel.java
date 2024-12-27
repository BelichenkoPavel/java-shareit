package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.user.UserModel;

@Builder
@Table(name = "item", schema = "public")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "available")
    private Boolean available;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private UserModel owner;

    @Column(name = "request")
    private String request;
}
