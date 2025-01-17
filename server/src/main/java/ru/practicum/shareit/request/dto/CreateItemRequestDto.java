package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequestDto {
    @NotNull(message = "description не может быть пустым")
    private String description;
}
