package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    User createUser(CreateUserDto userDto) {
        User user = userRepository.getByEmail(userDto.getEmail());

        if (user != null) {
            throw new ValidationException("User with email " + userDto.getEmail() + " already exists");
        }

        return userRepository.createUser(userDto);
    }

    public User updateUser(Long id, UpdateUserDto userDto) {
        User user = userRepository.getByEmail(userDto.getEmail());

        if (user != null) {
            throw new ValidationException("User with email " + userDto.getEmail() + " already exists");
        }

        return userRepository.updateUser(id, userDto);
    }

    public User getUser(Long id) {
        User user = userRepository.getUser(id);

        if (user == null) {
            throw new NotFoundException("User with id " + id + " not found");
        }

        return user;
    }

    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }
}
