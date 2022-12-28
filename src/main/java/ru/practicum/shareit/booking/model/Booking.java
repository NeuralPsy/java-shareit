package ru.practicum.shareit.booking.model;

import lombok.Data;
import ru.practicum.shareit.booking.status.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Data
@Entity
public class Booking {
    @Id
    private int id;
    private LocalDateTime start;
    private LocalDateTime end;
    @OneToOne
    private Item item;
    @ManyToOne
    private User booker;
    private BookingStatus status;


}
