package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import javax.transaction.Transactional;
import java.util.Collection;

@Component
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Transactional
    @Modifying
    @Query("update Item set name=:name, available=:available,  description=:description  where itemId=:itemId")
    void updateItem(@Param("name") String name, @Param("available") Boolean available,
                    @Param("description") String description, @Param("itemId") Integer itemId);

    Collection<Item> findAllByOwnerUserId(Integer ownerId);

    @Query("select '*' from Item where name like :text or description like :text")
    Collection<Item> findItemByWord(@Param("text") String text);
}
