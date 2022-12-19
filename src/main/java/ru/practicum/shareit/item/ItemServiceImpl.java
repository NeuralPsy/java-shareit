package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService{

    private final ItemMemoryStorage storage;

    @Autowired
    public ItemServiceImpl(ItemMemoryStorage storage){
        this.storage = storage;
    }


    @Override
    public ItemDto addItem(ItemDto itemDto, Integer ownerId) {
        Item item = ItemMapper.dtoToItem(itemDto);
        return storage.addItem(item, ownerId);
    }
    @Override
    public ItemDto editItem(Integer itemId, Integer ownerId, ItemDto itemDto) {
        Item item = ItemMapper.dtoToItem(itemDto);
        Item oldItem = storage.getItemById(itemId);

        if (item.isAvailable() != oldItem.isAvailable()) oldItem.setAvailable(item.isAvailable());
        if (item.getName() != oldItem.getName()) oldItem.setName(item.getName());
        if (item.getDescription() != oldItem.getDescription()) oldItem.setDescription(item.getDescription());

        try {
            storage.updateItem(oldItem, ownerId);
            return itemDto;
        } catch (IndexOutOfBoundsException e){
            throw new NotFoundException("Item is not found");
        }

    }
    @Override

    public ItemDto getItemById(Integer itemId) {
        ItemDto itemDto = ItemMapper.itemToDto(storage.getItemById(itemId));
        return itemDto;
    }

    @Override
    public Collection<ItemDto> getAllOwnersItems(Integer ownerId) {
        return storage.getAllOwnersItems(ownerId)
                .stream()
                .map(item -> ItemMapper.itemToDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> findItemByWord(String text) {
        return  storage.findItemByWord(text)
                .stream()
                .map(item -> ItemMapper.itemToDto(item))
                .collect(Collectors.toList());
    }
    @Override
    public Collection<ItemDto> getAll() {
        return storage.getAll()
                .stream()
                .map(item -> ItemMapper.itemToDto(item))
                .collect(Collectors.toList());
    }

    public String removeItem(Integer itemId) {
        storage.removeItem(itemId);
        return "Item "+itemId+" is deleted";
    }
}
