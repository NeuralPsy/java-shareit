package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public ItemServiceImpl(CommentRepository commentRepository, ItemRepository itemRepository,
                           UserRepository userRepository, BookingRepository bookingRepository) {
        this.commentRepository = commentRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public ItemDto addItem(ItemDto itemDto, Integer ownerId) {
        if (!userRepository.existsById(ownerId)) throw new NotFoundException("User is not found");
        if (itemDto.getName() == null || itemDto.getName().isEmpty())
            throw new EmptyItemFieldException("Item name is empty");
        if (itemDto.getDescription() == null || itemDto.getDescription().isEmpty())
            throw new EmptyItemFieldException("Item description is empty");
        if (itemDto.getAvailable() == null) throw new EmptyItemFieldException("Item availability field is empty");

        User owner = userRepository.getById(ownerId);

        Item item = ItemMapper.dtoToItem(itemDto);
        item.setOwner(owner);
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


        itemRepository.save(oldItem);

        return ItemMapper.itemToDto(itemRepository.getById(itemId));

    }

    @Override
    public ItemDto getItemById(Integer itemId, Integer userId) {
        if (!itemRepository.existsById(itemId)) throw new NotFoundException("Item is not found");
        Item item = itemRepository.getById(itemId);
        List<Comment> comments = commentRepository.findAllByItemItemId(itemId).stream().collect(Collectors.toList());

        if (Objects.equals(userId, item.getOwner().getUserId())) {
            Booking lastBooking = getLastBooking(item.getItemId());
            Booking nextBooking = getNextBooking(item.getItemId());
            return ItemMapper.itemToDto(item, lastBooking, nextBooking, comments);
        }

        return ItemMapper.itemToDto(item, comments);
    }

    @Override
    public Collection<ItemDto> getAllOwnersItems(Integer ownerId) {

        return itemRepository.findAllByOwnerUserId(ownerId)
                .stream()
                .map(item -> {
                            Booking lastBooking = getLastBooking(item.getItemId());
                            Booking nextBooking = getNextBooking(item.getItemId());
                            List<Comment> comments = commentRepository.findAllByItemItemId(item.getItemId())
                                    .stream().collect(Collectors.toList());

                            ItemDto itemDto = ItemMapper.itemToDto(item, lastBooking, nextBooking, comments);
                            return itemDto;
                        }
                ).collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> findItemByWord(String text) {

        if (text.isEmpty()) {
            return new ArrayList<>();
        }

        return itemRepository.findAllByWord(text)
                .stream()
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toList());
    }


    public String removeItem(Integer itemId) {
        itemRepository.deleteById(itemId);
        return "Item " + itemId + " is deleted";
    }

    public CommentDto postComment(Integer itemId, Comment comment, Integer userId) {
        Item item = itemRepository.getById(itemId);
        if (comment.getText().equals("") || comment.getText() == null)
            throw new CommentException("Comment text should not be empty");
        if (!isAcceptedToComment(userId, itemId)) throw new BookingException("Only booker or owner may post comments");
        User user = userRepository.getById(userId);

        comment.setItem(item);
        comment.setPostDate(LocalDateTime.now());
        comment.setCommentator(user);
        return CommentMapper.commentToDto(commentRepository.save(comment));
    }

    private boolean isAcceptedToComment(Integer userId, Integer itemId) {
        Integer bookingsSize = bookingRepository.findByByOwnerOrBooker(userId, itemId, LocalDateTime.now());
        return (bookingsSize > 0) && (bookingsSize != null);
    }

    private Booking getLastBooking(Integer itemId) {

        return bookingRepository.getLastForItem(itemId, LocalDateTime.now());
    }

    private Booking getNextBooking(Integer itemId) {

        return bookingRepository.getNextForItem(itemId, LocalDateTime.now());
    }

}
