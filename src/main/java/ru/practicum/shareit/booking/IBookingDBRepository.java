package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookingDBRepository extends JpaRepository<BookingModel, Long> {
    List<BookingModel> findByBookerId(Long bookerId);
}
