package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.ForbiddenException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class BookingService {
    @Lazy
    private final ItemService itemService;

    private final UserService userService;

    private final IBookingDBRepository bookingRepository;

    BookingDto createBooking(CreateBookingDto bookingDto, Long userId) {
        if (bookingDto.getStart().isEqual(bookingDto.getEnd())) {
            throw new BadRequestException("Start and end dates are equal");
        }

        User user = userService.getUser(userId);

        Item item = itemService.getItemById(bookingDto.getItemId());

        BookingModel saveData = BookingModelMapper.createMap(bookingDto, item, user);
        BookingModel bookingModel = bookingRepository.save(saveData);

        return BookingDtoMapper.map(bookingModel);
    }

    public BookingDto updateBooking(Long bookingId, Boolean approved, Long userId) {
        Optional<BookingModel> modelOpt = bookingRepository.findById(bookingId);

        if (modelOpt.isEmpty()) {
            throw new NotFoundException("Booking not found");
        }

        BookingModel model = modelOpt.get();

        if (!Objects.equals(model.getItem().getOwner().getId(), userId)) {
            throw new ForbiddenException("You are not owner of this item");
        }

        if (approved) {
            model.setStatus(Status.APPROVED);
        } else {
            model.setStatus(Status.REJECTED);
        }

        bookingRepository.save(model);

        return BookingDtoMapper.map(model);
    }

    public BookingDto getBooking(Long bookingId) {
        Optional<BookingModel> bookingModel = bookingRepository.findById(bookingId);

        if (bookingModel.isEmpty()) {
            throw new NotFoundException("Booking not found");
        }

        return BookingDtoMapper.map(bookingModel.get());
    }

    public List<BookingDto> getBookings(Long userId) {
        List<BookingModel> bookingModels = bookingRepository.findByBookerId(userId);

        return BookingDtoMapper.mapList(bookingModels);
    }
}
