package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

import java.util.List;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(@Valid @RequestBody CreateItemDto itemDto, @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.addItem(itemDto, userId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@PathVariable Long id, @Valid @RequestBody UpdateItemDto itemDto, @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.updateItem(id, itemDto, userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItem(@PathVariable Long id, @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.getItem(id, userId);
    }

    @GetMapping()
    public ResponseEntity<Object> getItems(@NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.getItems(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findItems(@NotNull @RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam String text) {
        if (text == null || text.isEmpty() || text.isBlank()) {
            return (ResponseEntity<Object>) List.of();
        }

        return itemClient.findByName(userId, text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@PathVariable Long itemId, @Valid @RequestBody CommentCreateDto commentDto, @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.addComment(itemId, commentDto, userId);
    }
}
