package ru.practicum.shareit.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.status.BookingStatus;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.rmi.AccessException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, UserRepository userRepository,
                          ItemRepository itemRepository){
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public BookingDto getById(Integer bookingId, Integer userId) {
        if(!bookingRepository.existsById(bookingId)) throw new NotFoundException("Booking does not exist");
        Booking booking = bookingRepository.getById(bookingId);
        if(!(userId.equals(booking.getBooker().getUserId()) ||
                userId.equals(booking.getItem().getOwner().getUserId()))) {
            throw new NotBookerOrOwnerException("Access denied");
        }
        return BookingMapper.bookingToDto(booking);
    }

    public BookingDto addBooking(BookingDtoCreate bookingDto, Integer userId) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("User does not exist");
        if (!itemRepository.existsById(bookingDto.getItemId())) throw new NotFoundException("Item does not exist");

        Item item = itemRepository.getById(bookingDto.getItemId());
        User booker = userRepository.getById(userId);
        Booking booking = BookingMapper.fromInitialDto(bookingDto);



        if (!item.getAvailable()) throw new ItemIsUnavailableException("The item is not available for booking");
        if (booking.getEndTime().isBefore(booking.getStartTime())) throw new WrongBookingDurationDates("End date cannot be before start date");
        if (booking.getStartTime().isBefore(LocalDateTime.now())) throw new WrongBookingDurationDates("Start date cannot be before current date");

        booking.setBooker(booker);
        booking.setStatus(BookingStatus.WAITING);
        booking.setItem(item);

        if(userId.equals(booking.getItem().getOwner().getUserId())) throw new NotBookerOrOwnerException("Not possible to book own item");


        Booking bookingToReturn = bookingRepository.save(booking);
        UserDto userDto = UserMapper.userToDto(userRepository.getById(userId));
        ItemDto itemDto = ItemMapper.itemToDto(item);

        BookingDto bookingDtoToReturn = BookingMapper.bookingToDto(bookingToReturn);
        bookingDtoToReturn.setBooker(userDto);
        bookingDtoToReturn.setItem(itemDto);
        return bookingDtoToReturn;
    }

    public Collection<BookingDto> getUserBookings(Integer userId, String state) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("User does not exist");
        Collection<Booking> bookings;
        Collection<String> statuses = Arrays.stream(BookingStatus.values()).map(value -> value.name()).collect(Collectors.toList());
        if (state.toLowerCase().equals("all")) {
            bookings = bookingRepository.findByBooker_UserId(userId);

        }

        else if (state.toLowerCase().equals("future")){
            bookings = bookingRepository.findFutureBookings(userId);
        }
        else if (statuses.contains(state.toUpperCase())){
            BookingStatus status = BookingStatus.valueOf(state.toUpperCase());
            bookings = bookingRepository.findByBooker_UserIdAndAndStatus(userId, status);
        }
        else {
            throw new BookingStateDoesntExistException("Unknown state: "+state);
        }

        return bookings.stream()
                .map(booking -> BookingMapper.bookingToDto(booking))
                .sorted((b2, b1) -> b1.getStart().compareTo(b2.getStart()))
                .collect(Collectors.toList());

    }


    public Collection<BookingDto> getAllItemsBookings(Integer ownerId, String state) {
        if(!userRepository.existsById(ownerId)) throw new NotFoundException("User does not exist");
        Collection<Booking> bookings;
        BookingStatus status;
        Collection<String> statuses = Arrays.stream(BookingStatus.values()).map(value -> value.name()).collect(Collectors.toList());
        if (state.toLowerCase().contains("all")) {
            bookings = bookingRepository.findByItem_Owner_UserId(ownerId);

        }  else if (state.toLowerCase().equals("future")){
            bookings = bookingRepository.findFutureBookingsOwner(ownerId);
        }
        else if (statuses.contains(state.toUpperCase())){
            status = BookingStatus.valueOf(state.toUpperCase());
            bookings = bookingRepository.findByItem_Owner_UserIdAndStatus(ownerId, status);
        } else {
            throw new BookingStateDoesntExistException("Unknown state: "+state);
        }

        return bookings.stream()
                .map(booking -> BookingMapper.bookingToDto(booking))
                .sorted((b2, b1) -> b1.getStart().compareTo(b2.getStart()))
                .collect(Collectors.toList());

    }

    public BookingDto approveBooking(Integer bookingId, Boolean approved, Integer userId) {
        Booking booking = bookingRepository.getById(bookingId);
        if(booking.getStatus().equals(BookingStatus.APPROVED)) throw new AlreadyApprovedException("You cannot approve booking twice");
        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);

        if(!userId.equals(booking.getItem().getOwner().getUserId())) throw new NotAnOwnerException("Only owner of the item ia able to approve booking");
//        booking.getItem().setAvailable(false);
        Booking bookingToReturn = bookingRepository.save(booking);
        UserDto booker = UserMapper.userToDto(bookingToReturn.getBooker());
        Item item = bookingToReturn.getItem();
        ItemDto itemDto = ItemMapper.itemToDto(item);

        BookingDto bookingDto = BookingMapper.bookingToDto(bookingToReturn);
        bookingDto.setBooker(booker);

        bookingDto.setItem(itemDto);

        return bookingDto;
    }


}
