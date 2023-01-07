package ru.practicum.shareit.item.mapper;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoWithBooker;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ItemMapper {




    public static ItemDto itemToDto(Item item, Booking last, Booking next, List<Comment> comments) {

        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getItemId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setComments(
                comments.stream()
                        .map(comment -> CommentMapper.commentToDto(comment))
                        .collect(Collectors.toList())
        );
        if (last != null) {
            itemDto.setLastBooking(BookingMapper.bookingToDtoWithBooker(last));
        }
        if(next != null) {
            itemDto.setNextBooking(BookingMapper.bookingToDtoWithBooker(next));
        }
        return itemDto;
    }


    public static ItemDto itemToDto(Item item, List<Comment> comments) {

        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getItemId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setComments(
                comments.stream()
                        .map(comment -> CommentMapper.commentToDto(comment))
                        .collect(Collectors.toList())
        );
        return itemDto;
    }


    public static ItemDto itemToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getItemId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());

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
