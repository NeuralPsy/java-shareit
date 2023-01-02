package ru.practicum.shareit.user.model;

import lombok.Data;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private int userId;
    private String name;
    private String email;
    @OneToMany(mappedBy = "booker")
    private List<Booking> bookings;
    @OneToMany(mappedBy = "owner")
    private List<Item> items;
    @OneToMany(mappedBy = "commentator")
    private List<Comment> comments;
    @OneToMany(mappedBy = "requestor")
    private List<ItemRequest> requests;

}
