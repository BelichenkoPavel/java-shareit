package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.ItemModel;

import java.util.List;

public interface IItemDBRepository extends JpaRepository<ItemModel, Long> {
    List<ItemModel> findByOwnerId(Long userId);

    @Query("Select i from ItemModel i where lower(i.name) like lower(%:name%) and available = true")
    List<ItemModel> findByNameContaining(String name);
}
