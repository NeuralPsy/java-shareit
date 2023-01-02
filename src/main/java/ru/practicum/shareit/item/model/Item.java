package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue
    private int itemId;
    private String name;
    private String description;
    private Boolean available;
    @ManyToOne
    @JoinColumn(referencedColumnName = "userId")
    private User owner;
    @OneToMany(mappedBy = "commentId")
    private List<Comment> comments;

}
