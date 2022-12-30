package ru.practicum.shareit.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.status.BookingStatus;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class BookingService {

    private final BookingRepository repository;

    @Autowired
    public BookingService(BookingRepository repository){
        this.repository = repository;
    }

    public BookingDto getById(Integer bookingId) {
        Booking booking = repository.getById(bookingId);
        return BookingMapper.bookingToDto(booking);
    }

    public Booking addBooking(BookingDto bookingDto) {
        Booking booking = BookingMapper.dtoToBooking(bookingDto);
        return repository.save(booking);
    }

    public Collection<BookingDto> getUserBookings(Integer userId, String state) {
        Collection<Booking> bookings;
        BookingStatus status = BookingStatus.valueOf(state.toUpperCase());
        if (state.toLowerCase().contains("all")) {
            bookings = repository.findByBooker_UserId(userId);

        }
        else {
            bookings = repository.findByBooker_UserIdAndAndStatus(userId, status);
        }

        return bookings.stream()
                .map(booking -> BookingMapper.bookingToDto(booking))
                .sorted((b2, b1) -> b1.getStart().compareTo(b2.getStart()))
                .collect(Collectors.toList());

    }


    public Collection<BookingDto> getAllItemsBookings(Integer ownerId, String state) {
        Collection<Booking> bookings;
        BookingStatus status = BookingStatus.valueOf(state.toUpperCase());
        if (state.toLowerCase().contains("all")) {
            bookings = repository.findByItem_Owner_UserId(ownerId);

        }
        else {
            bookings = repository.findByItem_Owner_UserIdAndStatus(ownerId, status);
        }

        return bookings.stream()
                .map(booking -> BookingMapper.bookingToDto(booking))
                .sorted((b2, b1) -> b1.getStart().compareTo(b2.getStart()))
                .collect(Collectors.toList());

    }

    public Boolean approveBooking(Integer bookingId, Boolean approved) {
        BookingStatus status = approved ? BookingStatus.valueOf("APPROVED") : BookingStatus.valueOf("REJECTED");
        repository.approveBooking(bookingId, status);

        return approved;
    }
}
