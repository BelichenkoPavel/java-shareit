package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;

@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
@Validated
@Slf4j
public class ItemRequestController {
    ItemRequestClient client;

    @PostMapping
    ResponseEntity<Object> create(@Valid @RequestBody CreateItemRequestDto dto, @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return client.create(dto, userId);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> get(@PathVariable Long id) {
        return client.getById(id);
    }

    @GetMapping
    ResponseEntity<Object> getAll(@NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return client.getAll(userId);
    }
}
