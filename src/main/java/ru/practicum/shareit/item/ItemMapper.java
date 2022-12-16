package ru.practicum.shareit.item;

import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@Data
public class ItemMapper {


    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(itemDto.getId());
        itemDto.setName(itemDto.getName());
        itemDto.setOwner(item.getOwner());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.isAvailable());

        return itemDto;
    }
}
