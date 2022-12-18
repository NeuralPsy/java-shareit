package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserInMemoryStorage {

    private static int id = 1;

    private static List<User> users = new ArrayList<>();

    public User addUser(User user) {
        user.setId(id++);
        users.add(user);
        return user;
    }

    public User getUser(Integer userId) {
        return users.stream()
                .filter(user -> userId.equals(user.getId()))
                .collect(Collectors.toList())
                .get(0);
    }

    public User updateUser(User user) {
        users.add(user.getId(), user);
        return user;
    }

    public void deleteUser(Integer userId) {
        users.forEach(user -> {
            if (userId.equals(user.getId())) users.remove(user);
        });
    }

    public Collection<User> getAll() {
        return users;
    }
}
