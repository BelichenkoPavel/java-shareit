package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Item addItem(@Valid @RequestBody CreateItemDto itemDto, @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.addItem(itemDto, userId);
    }

    @PatchMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @Valid @RequestBody UpdateItemDto itemDto, @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.updateItem(id, itemDto, userId);
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id, @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getItem(id, userId);
    }

    @GetMapping()
    public List<Item> getItems(@NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getItems(userId);
    }

    @GetMapping("/search")
    public List<Item> findItems(@RequestParam String text) {
        return itemService.findByName(text);
    }
}
