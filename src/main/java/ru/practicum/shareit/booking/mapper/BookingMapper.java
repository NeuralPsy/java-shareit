package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.dto.BookingDtoWithBooker;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.mapper.UserMapper;

public class BookingMapper {

    public static Booking dtoToBooking(BookingDto bookingDto, Item item) {
        Booking booking = new Booking();
//        booking.setBooker(bookingDto.getBooker());
        booking.setStartTime(bookingDto.getStart());
        booking.setEndTime(bookingDto.getEnd());
        booking.setItem(item);
        booking.setStatus(bookingDto.getStatus());
        return booking;
    }

    public static Booking fromInitialDto(BookingDtoCreate bookingDto) {
        Booking booking = new Booking();
        booking.setStartTime(bookingDto.getStart());
        booking.setEndTime(bookingDto.getEnd());
        return booking;
    }

    public static BookingDto bookingToDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getBookingId());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setStart(booking.getStartTime());
        bookingDto.setEnd(booking.getEndTime());

        bookingDto.setItem(ItemMapper.itemToDto(booking.getItem()));
        bookingDto.setBooker(UserMapper.userToDto(booking.getBooker()));
        return bookingDto;
    }

    public static BookingDtoWithBooker bookingToDtoWithBooker(Booking booking) {
        BookingDtoWithBooker bookingDto = new BookingDtoWithBooker();
        bookingDto.setId(booking.getBookingId());
        bookingDto.setBookerId(booking.getBooker().getUserId());
        return bookingDto;
    }
}
