package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Repository
public class ItemRepository {
    private TreeMap<Long, Item> items = new TreeMap<>();

    private Long id = 0L;

    public Item addItem(CreateItemDto itemDto, User user) {
        Item item = Item.builder()
                .id(nextId())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .owner(user)
                .name(itemDto.getName()).build();
        items.put(item.getId(), item);

        return item;
    }

    private Long nextId() {
        return id++;
    }

    public Item updateItem(Long itemId, UpdateItemDto itemDto) {
        Item item = items.get(itemId);

        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }

        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }

        return item;
    }

    public Item getItem(Long id, Long userId) {
        Item item = items.get(id);

        if (item == null) {
            throw new BadRequestException("Item not found");
        }

        if (!item.getOwner().getId().equals(userId)) {
            throw new BadRequestException("You are not owner of this item");
        }

        return item;
    }

    public List<Item> getItems(Long userId) {
        ArrayList<Item> findItems = new ArrayList<>();

        items.values().forEach(item -> {
            if (item.getOwner().getId().equals(userId)) {
                findItems.add(item);
            }
        });

        return findItems;
    }

    public List<Item> findByName(String name) {
        ArrayList<Item> findItems = new ArrayList<>();

        if (name.isBlank()) {
            return findItems;
        }

        items.values().forEach(item -> {
            if (!item.getAvailable()) {
                return;
            }

            if (item.getName().toLowerCase().equals(name.toLowerCase())) {
                findItems.add(item);
            }
        });

        return findItems;
    }
}
