package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EmptyEmailException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UserAlreadyExistsException;
import ru.practicum.shareit.exception.WrongEmailFormatException;
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
        if (user.getEmail() == null || user.getEmail().isEmpty()) throw new EmptyEmailException("Email is empty");
        if (storage.findByEmail(userDto.getEmail())) throw new UserAlreadyExistsException("User Already exists");
        if (!userDto.getEmail().contains("@")) throw new WrongEmailFormatException("Wrong email format");

        return UserMapper.userToDto(storage.addUser(user));


    }

    @Override
    public UserDto getUser(Integer userId) {
        return UserMapper.userToDto(storage.getUser(userId));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        if (storage.getUser(userId) == null) throw new NotFoundException("User is not found");
        if (storage.findByEmail(userDto.getEmail())) throw new UserAlreadyExistsException("Email already exists");

        User user = storage.getUser(userId);

        String email = user.getEmail();


        if (user.getName() != userDto.getName() && userDto.getName() != null) user.setName(userDto.getName());
        if (user.getEmail() != userDto.getEmail() && userDto.getEmail() != null) user.setEmail(userDto.getEmail());


        return UserMapper.userToDto(storage.updateUser(user, email));
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
                .map(UserMapper::userToDto)
                .collect(Collectors.toList());
    }
}
