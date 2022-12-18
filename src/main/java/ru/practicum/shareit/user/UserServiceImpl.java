package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserInMemoryStorage storage;

    public UserServiceImpl(UserInMemoryStorage storage){
        this.storage = storage;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = UserMapper.dtoToUser(userDto);

        return UserMapper.userToDto(storage.addUser(user));
    }

    @Override
    public UserDto getUser(Integer userId) {
        return UserMapper.userToDto(storage.getUser(userId));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        if (storage.getUser(userId).equals(null)) throw new NotFoundException("User is not found");

        User user = storage.getUser(userId);
        if (user.getName() != userDto.getName()) user.setName(userDto.getName());
        if (user.getEmail() != userDto.getEmail()) user.setEmail(userDto.getEmail());

        return UserMapper.userToDto(storage.updateUser(user));
    }

    @Override
    public String deleteUser(Integer userId) {
        storage.deleteUser(userId);
        return "User "+userId+" is deleted";
    }

    @Override
    public Collection<UserDto> getAll() {
        return storage.getAll()
                .stream()
                .map(user -> UserMapper.userToDto(user))
                .collect(Collectors.toList());
    }
}
