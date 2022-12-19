package ru.practicum.shareit.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class UserDto {
    private int id;
    private String name;
    @NotNull
    @NotEmpty
    private String email;

}
