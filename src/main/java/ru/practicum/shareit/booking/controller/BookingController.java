package ru.practicum.shareit.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService service;


    @Autowired
    public BookingController(BookingService service) {
        this.service = service;

    }


    @PostMapping
    public BookingDto addBooking(@RequestBody BookingDtoCreate bookingDto, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return service.addBooking(bookingDto, userId);

    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@PathVariable Integer bookingId,
                                     @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return service.getById(bookingId, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approveBooking(@PathVariable Integer bookingId,
                                     @RequestParam("approved") Boolean approved,
                                     @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return service.approveBooking(bookingId, approved, userId);
    }

    @GetMapping
    public Collection<BookingDto> getCurrentUserBookings(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                                         @RequestParam(name = "state", defaultValue = "all") String state) {
        return service.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public Collection<BookingDto> getAllItemsBookings(@RequestHeader("X-Sharer-User-Id") Integer ownerId,
                                                      @RequestParam(name = "state", defaultValue = "all") String state) {
        return service.getAllItemsBookings(ownerId, state);
    }
}
