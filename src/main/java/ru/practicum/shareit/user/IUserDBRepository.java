package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDBRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmail(String email);
}
