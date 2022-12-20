package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.exception.NotFoundException;

public class UserValidator {

    private static UserInMemoryStorage storage;

    @Autowired
    public UserValidator(UserInMemoryStorage storage){
        this.storage = storage;
    }


    public static void doesExistById(Integer userId){
        if (storage.getUser(userId).equals(null)) throw new NotFoundException("User is not found");

    }

}
