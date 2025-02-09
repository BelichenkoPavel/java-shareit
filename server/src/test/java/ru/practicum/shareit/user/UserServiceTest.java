package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    IUserDBRepository userRepository;

    CreateUserDto createUserDto = CreateUserDto
            .builder()
            .email("test@test.ru")
            .name("test")
            .build();

    UserModel userModel = UserModel
            .builder()
            .email("test@test.ru")
            .name("test")
            .id(1L)
            .build();

    UpdateUserDto updateUserDto = UpdateUserDto
            .builder()
            .email("test@test.ru")
            .name("test")
            .build();

    @Test
    public void testCreate() {
        Mockito.when(userRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(null);

        Mockito.when(userRepository.save(ArgumentMatchers.any(UserModel.class)))
                .thenReturn(userModel);

        User user = userService.createUser(createUserDto);

        assertEquals(user.getId(), userModel.getId());
    }

    @Test
    public void testCreateUserExist() {
        Mockito.when(userRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(userModel);

        Exception exception = assertThrows(ValidationException.class, () -> userService.createUser(createUserDto));

        assertEquals(exception.getMessage(), "User with email test@test.ru already exists");
    }

    @Test
    public void testUpdateUser() {
        Mockito.when(userRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(null);

        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(userModel));

        Mockito.when(userRepository.save(ArgumentMatchers.any(UserModel.class)))
                .thenReturn(userModel);

        User user = userService.updateUser(1L, updateUserDto);

        assertEquals(user.getId(), userModel.getId());
    }

    @Test
    public void testUpdateUserExistEmail() {
        Mockito.when(userRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(userModel);

        Exception exception = assertThrows(ValidationException.class, () -> userService.updateUser(1L, updateUserDto));

        assertEquals(exception.getMessage(), "User with email test@test.ru already exists");
    }

    @Test
    public void testUpdateUserExit() {
        Mockito.when(userRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(null);

        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> userService.updateUser(1L, updateUserDto));

        assertEquals(exception.getMessage(), "User with id 1 not found");
    }

    @Test
    public void testGetUser() {
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(userModel));

        User user = userService.getUser(1L);

        assertEquals(user.getId(), userModel.getId());
    }

    @Test
    public void testGetUserNotExist() {
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> userService.updateUser(1L, updateUserDto));

        assertEquals(exception.getMessage(), "User with id 1 not found");
    }
}
