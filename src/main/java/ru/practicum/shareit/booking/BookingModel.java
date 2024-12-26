package ru.practicum.shareit.booking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.ItemModel;
import ru.practicum.shareit.user.UserModel;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start", columnDefinition = "TIMESTAMP")
    private LocalDateTime start;

    @Column(name = "\"end\"", columnDefinition = "TIMESTAMP")
    private LocalDateTime end;

    @ManyToOne()
    private ItemModel item;

    @ManyToOne()
    @JoinColumn(name = "booker_id")
    private UserModel booker;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
