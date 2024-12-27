package ru.practicum.shareit.booking;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.item.ItemDtoMapper;
import ru.practicum.shareit.item.ItemModelMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemModel;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDtoMapper;
import ru.practicum.shareit.user.UserModel;
import ru.practicum.shareit.user.UserModelMapper;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@UtilityClass
public class BookingMapper {
    public static BookingDto map(BookingModel model) {
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

    public static List<BookingDto> mapList(List<BookingModel> models) {
        return models.stream()
                .map(BookingMapper::map)
                .toList();
    }

    public static BookingModel createMap(CreateBookingDto dto, Item item, User user) {
        ItemModel itemModel = ItemModelMapper.map(item);
        UserModel userModel = UserModelMapper.mapDto(user);

        return BookingModel.builder()
                .start(dto.getStart())
                .end(dto.getEnd())
                .booker(userModel)
                .item(itemModel)
                .status(Status.WAITING)
                .build();
    }
}
