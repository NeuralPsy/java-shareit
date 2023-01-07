package ru.practicum.shareit.user.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

/**
 * The class representing endpoints to manipulate users
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param userDto JSON object of DTO user that comes from request body
     * @return DTO object of user with ID is returned when added into storage
     */
    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    /**
     * @param userId comes from path as variable. This is ID of user that other user wants to get
     * @return DTO object user is returned in accordance with it's ID (userId param)
     */
    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Integer userId) {
        return userService.getUser(userId);
    }

    /**
     * @return collection of all user DTOs
     */
    @GetMapping
    public Collection<UserDto> getAll() {
        return userService.getAll();
    }

    /**
     * @param userDto JSON object of new version of DTO user that comes from request body
     * @param userId  comes from path as variable. This is ID of user that is wanted to be edited (updated)
     * @return DTO object of updated user is returned when added into storage
     */
    @PatchMapping("/{userId}")
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable Integer userId) {
        return userService.updateUser(userDto, userId);
    }

    /**
     * @param userId comes from path as variable. This is ID of user that is wanted to be deleted
     * @return String object that contains message of successfully deleted user
     */
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Integer userId) {
        return userService.deleteUser(userId);
    }
}
