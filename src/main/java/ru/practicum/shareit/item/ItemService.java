package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    private final UserService userService;

    public Item addItem(CreateItemDto itemDto, Long userId) {
        User user = userService.getUser(userId);

        return itemRepository.addItem(itemDto, user);
    }

    public Item updateItem(Long id, UpdateItemDto itemDto, Long userId) {
        User user = userService.getUser(userId);

        if (!user.getId().equals(userId)) {
            throw new NotFoundException("You can't update your own item");
        }

        return itemRepository.updateItem(id, itemDto);
    }

    public Item getItem(Long id, Long userId) {
        return itemRepository.getItem(id, userId);
    }

    public List<Item>  getItems(Long userId) {
        return itemRepository.getItems(userId);
    }

    public List<Item> findByName(String name) {
        return itemRepository.findByName(name);
    }
}
