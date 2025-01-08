package ru.practicum.shareit.user;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class User {
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    @Email(regexp = ".+[@].+[\\.].+", message = "Email is not valid")
    private String email;

    @OneToMany
    private List<Item> items = new ArrayList<>();
}
