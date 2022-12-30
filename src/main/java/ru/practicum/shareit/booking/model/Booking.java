package ru.practicum.shareit.booking.model;

import lombok.Data;
import ru.practicum.shareit.booking.status.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name ="bookings")
public class Booking {
    @Id
    @GeneratedValue
    private int bookingId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @OneToOne
    @JoinColumn(name = "itemId")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User booker;
    private BookingStatus status;


}
