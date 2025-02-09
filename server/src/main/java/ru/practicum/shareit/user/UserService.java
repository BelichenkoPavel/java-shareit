package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserDBRepository userRepository;

    @Transactional
    User createUser(CreateUserDto userDto) {
        UserModel existUser = userRepository.findByEmail(userDto.getEmail());

        if (existUser != null) {
            throw new ValidationException("User with email " + userDto.getEmail() + " already exists");
        }

        UserModel user = userRepository.save(UserModelMapper.mapCreateDto(userDto));

        return UserModelMapper.map(user);
    }

    @Transactional
    public User updateUser(Long id, UpdateUserDto userDto) {
        UserModel emailUser = userRepository.findByEmail(userDto.getEmail());

        if (emailUser != null) {
            throw new ValidationException("User with email " + userDto.getEmail() + " already exists");
        }

        Optional<UserModel> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new NotFoundException("User with id " + id + " not found");
        }

        UserModel user = userOpt.get();

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

        userRepository.save(user);

        return UserModelMapper.map(user);
    }

    public User getUser(Long id) {
        Optional<UserModel> existUser = userRepository.findById(id);

        if (existUser.isEmpty()) {
            throw new NotFoundException("User with id " + id + " not found");
        }

        UserModel user = existUser.get();

        return UserModelMapper.map(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
