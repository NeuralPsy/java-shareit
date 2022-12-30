package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.status.BookingStatus;

import java.util.Collection;

@Component
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Collection<Booking> findByBooker_UserIdAndAndStatus(Integer userId, BookingStatus status);

    Collection<Booking> findByBooker_UserId(Integer userId);

    Collection<Booking> findByItem_Owner_UserId(Integer ownerId);

    Collection<Booking> findByItem_Owner_UserIdAndStatus(Integer ownerId, BookingStatus status);

    @Query("update Booking set status = :status where bookingId = :bookingId")
    void approveBooking(@Param("bookingId") Integer bookingId, @Param("status") BookingStatus status);
}
