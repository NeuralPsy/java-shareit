package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;
    private  final CommentRepository commentRepository;
    private final ItemRepository itemRepository;
    @Autowired
    public ItemServiceImpl(CommentRepository commentRepository, ItemRepository itemRepository,
                           UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ItemDto addItem(ItemDto itemDto, Integer ownerId) {
        if (!userRepository.existsById(ownerId)) throw new NotFoundException("User is not found");
        if (itemDto.getName() == null || itemDto.getName().isEmpty())
            throw new EmptyItemFieldException("Item name is empty");
        if (itemDto.getDescription() == null || itemDto.getDescription().isEmpty())
            throw new EmptyItemFieldException("Item description is empty");
        if (itemDto.getAvailable() == null) throw new EmptyItemFieldException("Item availability field is empty");

        Item item = ItemMapper.dtoToItem(itemDto);
        ItemDto itemDtoReturned = ItemMapper.itemToDto(itemRepository.save(item));
        return itemDtoReturned;
    }

    @Override
    public ItemDto editItem(Integer itemId, Integer ownerId, ItemDto itemDto) {
        if (!userRepository.existsById(ownerId)) throw new NotFoundException("User is not found");
        if (!ownerId.equals(itemRepository.getById(itemId).getOwner().getUserId()))
            throw new NotFoundException("User is not an owner of the item");
        if (!itemRepository.existsById(itemId)) throw new NotFoundException("Item is not found");

        Item oldItem = itemRepository.getById(itemId);

        if (itemDto.getAvailable() != oldItem.getAvailable() && itemDto.getAvailable() != null)
            oldItem.setAvailable(itemDto.getAvailable());
        if (itemDto.getName() != oldItem.getName() && itemDto.getName() != null) oldItem.setName(itemDto.getName());
        if (itemDto.getDescription() != oldItem.getDescription() && itemDto.getDescription() != null)
            oldItem.setDescription(itemDto.getDescription());

       itemRepository.updateItem(oldItem.getName(), oldItem.getAvailable(), oldItem.getDescription(),
                    oldItem.getItemId());

        return ItemMapper.itemToDto(itemRepository.getById(itemId));

    }

    @Override

    public ItemDto getItemById(Integer itemId) {
        ItemDto itemDto = ItemMapper.itemToDto(itemRepository.getById(itemId));
        return itemDto;
    }

    @Override
    public Collection<ItemDto> getAllOwnersItems(Integer ownerId) {
        return itemRepository.findAllByOwnerUserId(ownerId)
                .stream()
                .map(item -> ItemMapper.itemToDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> findItemByWord(String text) {
        return itemRepository.findItemByWord(text)
                .stream()
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> getAll() {
        return itemRepository.findAll()
                .stream()
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toList());
    }

    public String removeItem(Integer itemId) {
        itemRepository.deleteById(itemId);
        return "Item " + itemId + " is deleted";
    }

    public String postComment(Comment comment) {
        commentRepository.save(comment);
        return comment.getText();
    }
}
