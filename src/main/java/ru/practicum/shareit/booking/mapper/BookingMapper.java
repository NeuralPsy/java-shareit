package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

public class BookingMapper {

    public static Booking dtoToBooking(BookingDto bookingDto){
        Booking booking = new Booking();
        booking.setBooker(bookingDto.getBooker());
        booking.setStartTime(bookingDto.getStart());
        booking.setEndTime(bookingDto.getEnd());
        booking.setItem(bookingDto.getItem());
        booking.setStatus(bookingDto.getStatus());
        return booking;
    }

    public static BookingDto bookingToDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getBookingId());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setStart(booking.getStartTime());
        bookingDto.setEnd(booking.getEndTime());
        bookingDto.setItem(booking.getItem());
        bookingDto.setBooker(booking.getBooker());
        return bookingDto;
    }
}
