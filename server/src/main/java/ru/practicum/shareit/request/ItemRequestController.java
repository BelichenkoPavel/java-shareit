package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
@Validated
public class ItemRequestController {
    private final ItemRequestService service;

    @PostMapping
    ItemRequestDto create(@Valid @RequestBody CreateItemRequestDto dto, @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.create(dto, userId);
    }

    @GetMapping("/{id}")
    ItemRequestDto get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    List<ItemRequestDto> getAll(@NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.getAll(userId);
    }
}
