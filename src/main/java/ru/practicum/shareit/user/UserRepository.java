package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;

import java.util.TreeMap;

@Repository
public class UserRepository {
    private TreeMap<Long, User> items = new TreeMap<>();

    private Long id = 0L;

    public User createUser(CreateUserDto userDto) {
        User user = new User(nextId(), userDto.getName(), userDto.getEmail());

        items.put(user.getId(), user);

        return  user;
    }

    public User getUser(Long id) {
        return items.get(id);
    }

    public User getByEmail(String email) {
        return items.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public User updateUser(Long id, UpdateUserDto userDto) {
        User user = items.get(id);

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        return user;
    }

    public void deleteUser(Long id) {
        items.remove(id);
    }

    private Long nextId() {
        return id++;
    }
}
