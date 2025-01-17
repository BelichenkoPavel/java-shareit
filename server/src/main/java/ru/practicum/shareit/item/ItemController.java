package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestBody CreateItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        Item item = itemService.addItem(itemDto, userId);

        return ItemDtoMapper.map(item);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@PathVariable Long id, @RequestBody UpdateItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        Item item = itemService.updateItem(id, itemDto, userId);

        return ItemDtoMapper.map(item);
    }

    @GetMapping("/{id}")
    public ItemDto getItem(@PathVariable Long id, @RequestHeader("X-Sharer-User-Id") Long userId) {
        Item item = itemService.getItem(id, userId);

        return ItemDtoMapper.map(item);
    }

    @GetMapping()
    public List<ItemDto> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        List<Item> items = itemService.getItems(userId);

        return ItemDtoMapper.mapList(items);
    }

    @GetMapping("/search")
    public List<ItemDto> findItems(@RequestParam String text) {
        List<Item> items = itemService.findByName(text);

        return ItemDtoMapper.mapList(items);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable Long itemId, @RequestBody CommentCreateDto commentDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.addComment(itemId, commentDto, userId);
    }
}
