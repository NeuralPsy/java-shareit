package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserInMemoryStorage {

    private static int id = 1;

    private static final Map<String, User> users = new HashMap<>();

    public User addUser(User user) {
        user.setId(id++);
        users.put(user.getEmail(), user);
        return user;
    }

    public User getUser(Integer userId) {
        return users.values()
                .stream()
                .filter(user -> userId.equals(user.getId()))
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
                .filter(user -> userId.equals(user.getId()))
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
                .filter(user -> userId.equals(user.getId()))
                .forEach(usersList::add);
        return usersList.size() != 0;

    }
}
