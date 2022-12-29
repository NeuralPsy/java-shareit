package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.storage.ItemInMemoryStorage;
import ru.practicum.shareit.user.storage.UserInMemoryStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemInMemoryStorage itemInMemoryStorage;
    private final UserInMemoryStorage userInMemoryStorage;

    private  final CommentRepository commentRepository;

    @Autowired
    public ItemServiceImpl(ItemInMemoryStorage storage,
                           UserInMemoryStorage userInMemoryStorage,
                           CommentRepository commentRepository) {
        this.itemInMemoryStorage = storage;
        this.userInMemoryStorage = userInMemoryStorage;
        this.commentRepository = commentRepository;
    }

    @Override
    public ItemDto addItem(ItemDto itemDto, Integer ownerId) {
        if (!userInMemoryStorage.findById(ownerId)) throw new NotFoundException("User is not found");
        if (itemDto.getName() == null || itemDto.getName().isEmpty())
            throw new EmptyItemFieldException("Item name is empty");
        if (itemDto.getDescription() == null || itemDto.getDescription().isEmpty())
            throw new EmptyItemFieldException("Item description is empty");
        if (itemDto.getAvailable() == null) throw new EmptyItemFieldException("Item availability field is empty");

        Item item = ItemMapper.dtoToItem(itemDto);
        return itemInMemoryStorage.addItem(item, ownerId);
    }

    @Override
    public ItemDto editItem(Integer itemId, Integer ownerId, ItemDto itemDto) {
        if (userInMemoryStorage.getUser(ownerId) == null) throw new NotFoundException("User is not found");
        if (!itemInMemoryStorage.getUserIdByItemId(itemId).equals(ownerId))
            throw new NotFoundException("User is not an owner of the item");

        Item oldItem = itemInMemoryStorage.getItemById(itemId);

        if (itemDto.getAvailable() != oldItem.getAvailable() && itemDto.getAvailable() != null)
            oldItem.setAvailable(itemDto.getAvailable());
        if (itemDto.getName() != oldItem.getName() && itemDto.getName() != null) oldItem.setName(itemDto.getName());
        if (itemDto.getDescription() != oldItem.getDescription() && itemDto.getDescription() != null)
            oldItem.setDescription(itemDto.getDescription());

        try {
            itemInMemoryStorage.updateItem(oldItem);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundException("Item is not found");
        }

        return ItemMapper.itemToDto(itemInMemoryStorage.getItemById(itemId));

    }

    @Override

    public ItemDto getItemById(Integer itemId) {
        ItemDto itemDto = ItemMapper.itemToDto(itemInMemoryStorage.getItemById(itemId));
        return itemDto;
    }

    @Override
    public Collection<ItemDto> getAllOwnersItems(Integer ownerId) {
        return itemInMemoryStorage.getAllOwnersItems(ownerId)
                .stream()
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> findItemByWord(String text) {
        return itemInMemoryStorage.findItemByWord(text)
                .stream()
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> getAll() {
        return itemInMemoryStorage.getAll()
                .stream()
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toList());
    }

    public String removeItem(Integer itemId) {
        itemInMemoryStorage.removeItem(itemId);
        return "Item " + itemId + " is deleted";
    }

    public String postComment(Comment comment) {
        commentRepository.save(comment);
        return comment.getText();
    }
}
