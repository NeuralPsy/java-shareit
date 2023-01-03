package ru.practicum.shareit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

import javax.transaction.Transactional;

@Component
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
}
