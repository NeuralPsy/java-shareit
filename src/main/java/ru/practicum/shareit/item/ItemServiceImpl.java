package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserInMemoryStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemMemoryStorage itemMemoryStorage;

    private final UserInMemoryStorage userInMemoryStorage;

    @Autowired
    public ItemServiceImpl(ItemMemoryStorage storage, UserInMemoryStorage userInMemoryStorage) {
        this.itemMemoryStorage = storage;
        this.userInMemoryStorage = userInMemoryStorage;
    }


    @Override
    public ItemDto addItem(ItemDto itemDto, Integer ownerId) {
        if (!userInMemoryStorage.findById(ownerId)) throw new NotFoundException("User is not found");
        if (itemDto.getName() == null || itemDto.getName().isEmpty()) throw new EmptyItemFieldException("Item name is empty");
        if (itemDto.getDescription() == null || itemDto.getDescription().isEmpty()) throw new EmptyItemFieldException("Item description is empty");
        if (itemDto.getAvailable() == null) throw new EmptyItemFieldException("Item availability field is empty");

        Item item = ItemMapper.dtoToItem(itemDto);
        return itemMemoryStorage.addItem(item, ownerId);
    }
    @Override
    public ItemDto editItem(Integer itemId, Integer ownerId, ItemDto itemDto) {
        if (userInMemoryStorage.getUser(ownerId) == null) throw new NotFoundException("User is not found");
        if (!itemMemoryStorage.getUserIdByItemId(itemId).equals(ownerId)) throw new NotFoundException("User is not an owner of the item");

        Item oldItem = itemMemoryStorage.getItemById(itemId);

        if (itemDto.getAvailable() != oldItem.getAvailable() && itemDto.getAvailable() != null) oldItem.setAvailable(itemDto.getAvailable());
        if (itemDto.getName() != oldItem.getName() && itemDto.getName() != null) oldItem.setName(itemDto.getName());
        if (itemDto.getDescription() != oldItem.getDescription() && itemDto.getDescription() != null) oldItem.setDescription(itemDto.getDescription());

        try {
            itemMemoryStorage.updateItem(oldItem);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundException("Item is not found");
        }

        return ItemMapper.itemToDto(itemMemoryStorage.getItemById(itemId));

    }
    @Override

    public ItemDto getItemById(Integer itemId) {
        ItemDto itemDto = ItemMapper.itemToDto(itemMemoryStorage.getItemById(itemId));
        return itemDto;
    }

    @Override
    public Collection<ItemDto> getAllOwnersItems(Integer ownerId) {
        return itemMemoryStorage.getAllOwnersItems(ownerId)
                .stream()
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> findItemByWord(String text) {
        return  itemMemoryStorage.findItemByWord(text)
                .stream()
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toList());
    }
    @Override
    public Collection<ItemDto> getAll() {
        return itemMemoryStorage.getAll()
                .stream()
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toList());
    }

    public String removeItem(Integer itemId) {
        itemMemoryStorage.removeItem(itemId);
        return "Item "+itemId+" is deleted";
    }
}
