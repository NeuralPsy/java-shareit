package ru.practicum.shareit.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService service;


    @Autowired
    public BookingController(BookingService service){
        this.service = service;

    }


    @PostMapping
    public Booking addBooking(@RequestBody BookingDto bookingDto){
        return service.addBooking(bookingDto);

    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@PathVariable Integer bookingId){
        return service.getById(bookingId);
    }

    @PatchMapping("/{bookingId}")
    public Boolean approveBooking(@PathVariable Integer bookingId, @RequestParam("approved") Boolean approved){
        return service.approveBooking(bookingId, approved);
    }

    @GetMapping
    public Collection<BookingDto> getCurrentUserBookings(@RequestHeader Integer userId,
                                                      @RequestParam(name = "state", defaultValue = "all") String state){
        return service.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public Collection<BookingDto> getAllItemsBookings(@RequestHeader Integer ownerId,
                                                         @RequestParam(name = "state", defaultValue = "all") String state){
        return service.getAllItemsBookings(ownerId, state);
    }
}
