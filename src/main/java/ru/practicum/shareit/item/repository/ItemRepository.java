package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

@Component
public interface ItemRepository extends JpaRepository<Item, Integer> {


    Collection<Item> findAllByOwnerUserId(Integer ownerId);

    @Query(
            "FROM Item " +
                    "WHERE (" +
                    "UPPER(name) LIKE UPPER(CONCAT('%', :text, '%')) " +
                    "OR UPPER(description) LIKE UPPER(CONCAT('%', :text, '%')) " +
                    ") AND available = true"
    )
    Collection<Item> findAllByWord(@Param("text") String text);
}
