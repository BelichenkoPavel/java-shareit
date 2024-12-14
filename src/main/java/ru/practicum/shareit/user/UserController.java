package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping()
    public UserDto createUser(@Valid @RequestBody CreateUserDto userDto) {
        User user = userService.createUser(userDto);

        return UserDtoMapper.map(user);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@Valid @RequestBody UpdateUserDto userDto, @PathVariable Long id) {
        User user = userService.updateUser(id, userDto);

        return UserDtoMapper.map(user);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        User user = userService.getUser(id);

        return UserDtoMapper.map(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
