package ru.practicum.shareit.item.mapper;

import lombok.Data;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemMapper {


    public static ItemDto itemToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        List<CommentDto> comments = new ArrayList<>();
        itemDto.setId(item.getItemId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        item.getComments().forEach(comment -> comments.add(CommentMapper.commentToDto(comment)));
        itemDto.setComments(comments);

        return itemDto;
    }

    public static Item dtoToItem(ItemDto itemDto) {
        Item item = new Item();
        item.setItemId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());

        return item;
    }
}
