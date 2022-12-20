package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotEmpty;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    private int id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private Boolean available;
}
