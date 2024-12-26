package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.ItemDtoMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserDtoMapper;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public class BookingDtoMapper {
    static BookingDto map(BookingModel model) {
        ItemDto item = ItemDtoMapper.map(model.getItem());
        UserDto booker = UserDtoMapper.mapModel(model.getBooker());

        return BookingDto.builder()
                .id(model.getId())
                .start(model.getStart())
                .end(model.getEnd())
                .status(model.getStatus())
                .item(item)
                .booker(booker)
                .build();
    }

    static List<BookingDto> mapList(List<BookingModel> models) {
        return models.stream()
                .map(BookingDtoMapper::map)
                .toList();
    }
}
