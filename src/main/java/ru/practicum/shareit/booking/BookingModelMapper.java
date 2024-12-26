package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.item.ItemModelMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemModel;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserModel;
import ru.practicum.shareit.user.UserModelMapper;

public class BookingModelMapper {
    static BookingModel createMap(CreateBookingDto dto, Item item, User user) {
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
