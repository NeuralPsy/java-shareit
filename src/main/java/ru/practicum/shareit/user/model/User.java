package ru.practicum.shareit.user.model;

import lombok.Data;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    private int id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "booker")
    private List<Booking> bookings;

}
