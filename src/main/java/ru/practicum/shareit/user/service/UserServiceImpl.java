package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EmptyEmailException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UserAlreadyExistsException;
import ru.practicum.shareit.exception.WrongEmailFormatException;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.storage.UserInMemoryStorage;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserInMemoryStorage storage, UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = UserMapper.dtoToUser(userDto);
        if (user.getEmail() == null || user.getEmail().isEmpty()) throw new EmptyEmailException("Email is empty");
        if (userRepository.existsByEmail(userDto.getEmail())) throw new UserAlreadyExistsException("User Already exists");
        if (!userDto.getEmail().contains("@")) throw new WrongEmailFormatException("Wrong email format");

        return UserMapper.userToDto(userRepository.save(user));


    }

    @Override
    public UserDto getUser(Integer userId) {
        return UserMapper.userToDto(userRepository.getById(userId));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("User is not found");
        if (userRepository.existsByEmail(userDto.getEmail())) throw new UserAlreadyExistsException("Email already exists");

        User user = userRepository.getById(userId);

        if (user.getName() != userDto.getName() && userDto.getName() != null) user.setName(userDto.getName());
        if (user.getEmail() != userDto.getEmail() && userDto.getEmail() != null) user.setEmail(userDto.getEmail());

        return UserMapper.userToDto(userRepository.updateUser(user.getName(), user.getEmail(), userId));
    }

    @Override
    public String deleteUser(Integer userId) {
        userRepository.deleteById(userId);
        return "User " + userId + " is deleted";
    }

    @Override
    public Collection<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::userToDto)
                .collect(Collectors.toList());
    }
}
