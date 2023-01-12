package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

public interface ItemService {
    ItemDto addItem(ItemDto itemDto, Integer ownerId);

    ItemDto editItem(Integer itemId, Integer ownerId, ItemDto itemDto);

    ItemDto getItemById(Integer itemId, Integer userId);

    Collection<ItemDto> getAllOwnersItems(Integer ownerId);

    Collection<ItemDto> findItemByWord(String text);

}
