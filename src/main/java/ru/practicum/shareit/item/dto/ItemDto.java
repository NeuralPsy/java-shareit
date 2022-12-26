package ru.practicum.shareit.item.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class ItemDto {
    private int id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private Boolean available;
}
