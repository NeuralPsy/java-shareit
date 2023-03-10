package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserInMemoryStorage {

    private static int id = 1;

    private static final Map<String, User> users = new HashMap<>();

    public User addUser(User user) {
        user.setUserId(id++);
        users.put(user.getEmail(), user);
        return user;
    }

    public User getUser(Integer userId) {
        return users.values()
                .stream()
                .filter(user -> userId.equals(user.getUserId()))
                .collect(Collectors.toList())
                .get(0);
    }

    public User updateUser(User user, String email) {
        users.remove(email);
        users.put(user.getEmail(), user);
        return user;
    }

    public void deleteUser(Integer userId) {
        String userEmail = users.values()
                .stream()
                .filter(user -> userId.equals(user.getUserId()))
                .collect(Collectors.toList())
                .get(0)
                .getEmail();
        users.remove(userEmail);
    }

    public Collection<User> getAll() {
        return users.values();
    }

    public boolean findByEmail(String email) {
        return users.containsKey(email);

    }

    public boolean findById(Integer userId) {
        List<User> usersList = new ArrayList<>();
        users.values()
                .stream()
                .filter(user -> userId.equals(user.getUserId()))
                .forEach(usersList::add);
        return usersList.size() != 0;

    }
}
