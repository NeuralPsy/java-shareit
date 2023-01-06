package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.status.BookingStatus;

import java.time.LocalDateTime;
import java.util.Collection;

@Component
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Collection<Booking> findByBooker_UserIdAndAndStatus(Integer userId, BookingStatus status);

    Collection<Booking> findByBooker_UserId(Integer userId);

    Collection<Booking> findByItem_Owner_UserId(Integer ownerId);

    Collection<Booking> findByItem_Owner_UserIdAndStatus(Integer ownerId, BookingStatus status);

    @Query("update Booking set status = :status where bookingId = :bookingId")
    void approveBooking(@Param("bookingId") Integer bookingId, @Param("status") BookingStatus status);

    @Query("from Booking where booker.userId = :userId and startTime > current_date and endTime > current_date")
    Collection<Booking> findFutureBookings(@Param("userId") Integer userId);

    @Query("from Booking where item.owner.userId = :ownerId and startTime > current_date and endTime > current_date")
    Collection<Booking> findFutureBookingsOwner(Integer ownerId);


    @Query("from Booking where item.itemId = :itemId and endTime < :curTime order by endTime desc")
    Booking getLastForItem(@Param("itemId") Integer itemId, @Param("curTime") LocalDateTime curTime);

    @Query("from Booking where item.itemId = :itemId and startTime > :curTime order by startTime asc")
    Booking getNextForItem(@Param("itemId") Integer itemId, @Param("curTime") LocalDateTime curTime);

}
