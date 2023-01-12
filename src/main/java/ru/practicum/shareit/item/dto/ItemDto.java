package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingDtoWithBooker;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Data
public class ItemDto {
    private int id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private Boolean available;
    private BookingDtoWithBooker lastBooking;
    private BookingDtoWithBooker nextBooking;
    private List<CommentDto> comments;
}
