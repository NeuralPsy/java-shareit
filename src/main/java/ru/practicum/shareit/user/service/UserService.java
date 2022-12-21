package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {
    UserDto addUser(UserDto userDto);

    UserDto getUser(Integer userId);

    UserDto updateUser(UserDto userDto, Integer userId);

    String deleteUser(Integer userId);

    Collection<UserDto> getAll();
}
