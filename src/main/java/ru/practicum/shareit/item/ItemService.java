package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.CommentModel;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemModel;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Lazy
public class ItemService {
    private final IItemDBRepository itemRepository;

    private final ICommentDBRepository commentDBRepository;

    private final UserService userService;

    private final BookingService bookingService;

    @Transactional
    public Item addItem(CreateItemDto itemDto, Long userId) {
        User user = userService.getUser(userId);

        ItemModel userModel = itemRepository.save(ItemModelMapper.mapCreateItem(itemDto, user));
        return ItemMapper.map(userModel);
    }

    @Transactional
    public Item updateItem(Long id, UpdateItemDto itemDto, Long userId) {
        User user = userService.getUser(userId);

        if (!user.getId().equals(userId)) {
            throw new NotFoundException("You can't update your own item");
        }

        Optional<ItemModel> modelOpt = itemRepository.findById(id);

        if (modelOpt.isEmpty()) {
            throw new NotFoundException("Item not found");
        }

        ItemModel model = modelOpt.get();

        if (itemDto.getName() != null) {
            model.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            model.setDescription(itemDto.getDescription());
        }

        if (itemDto.getAvailable() != null) {
            model.setAvailable(itemDto.getAvailable());
        }

        ItemModel itemModel = itemRepository.save(model);

        return ItemMapper.map(itemModel);
    }

    public Item getItemById(Long id) {
        Optional<ItemModel> itemModelOpt = itemRepository.findById(id);

        if (itemModelOpt.isEmpty()) {
            throw new NotFoundException("Item not found");
        }

        ItemModel itemModel = itemModelOpt.get();

        if (!itemModel.getAvailable()) {
            throw new BadRequestException("Item is not available");
        }

        return ItemMapper.map(itemModel);
    }

    public Item getItem(Long id, Long userId) {
        Optional<ItemModel> itemModelOpt = itemRepository.findById(id);

        if (itemModelOpt.isEmpty()) {
            throw new NotFoundException("Item not found");
        }

        ItemModel itemModel = itemModelOpt.get();

        List<CommentModel> commentModels = commentDBRepository.findAllByItemId(itemModel.getId());

        return ItemMapper.map(itemModel, commentModels);
    }

    public List<Item> getItems(Long userId) {
        List<ItemModel> itemsModels = itemRepository.findByOwnerId(userId);

        return ItemMapper.mapModelsList(itemsModels);
    }

    public List<Item> findByName(String name) {
        List<ItemModel> itemsModels = itemRepository.findByNameContaining(name);

        return ItemMapper.mapModelsList(itemsModels);
    }

    @Transactional
    public CommentDto addComment(Long itemId, CommentCreateDto commentDto, Long userId) {
        User user = userService.getUser(userId);

        Item item = getItemById(itemId);

        List<BookingDto> bookingsDto = bookingService.getBookings(userId);

        if (bookingsDto.isEmpty()) {
            throw new BadRequestException("You can't comment item");
        }

        BookingDto bookingDto = bookingsDto.get(0);

        if (bookingDto.getEnd().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("You can't comment item");
        }

        CommentModel saveData = CommentModelMapper.createMap(commentDto, item, user);

        commentDBRepository.save(saveData);

        return CommentDtoMapper.map(saveData);
    }
}
