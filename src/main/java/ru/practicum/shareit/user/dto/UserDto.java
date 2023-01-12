package ru.practicum.shareit.user.dto;

import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class UserDto {
    private int id;
    private String name;
    @NotNull
    @NotEmpty
    private String email;
    private BookingDto lastBooking;
    private BookingDto nextBooking;

}
