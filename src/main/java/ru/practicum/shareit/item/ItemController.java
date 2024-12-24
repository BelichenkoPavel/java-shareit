package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
@Validated
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto addItem(@Valid @RequestBody CreateItemDto itemDto, @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        Item item = itemService.addItem(itemDto, userId);

        return ItemDtoMapper.map(item);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@PathVariable Long id, @Valid @RequestBody UpdateItemDto itemDto, @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        Item item = itemService.updateItem(id, itemDto, userId);

        return ItemDtoMapper.map(item);
    }

    @GetMapping("/{id}")
    public ItemDto getItem(@PathVariable Long id, @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        Item item = itemService.getItem(id, userId);

        return ItemDtoMapper.map(item);
    }

    @GetMapping()
    public List<ItemDto> getItems(@NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        List<Item> items = itemService.getItems(userId);

        return ItemDtoMapper.mapList(items);
    }

    @GetMapping("/search")
    public List<ItemDto> findItems(@RequestParam String text) {
        List<Item> items = itemService.findByName(text);

        return ItemDtoMapper.mapList(items);
    }
}
