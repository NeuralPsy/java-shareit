package ru.practicum.shareit.request.model;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "requests")
public class ItemRequest {
    @Id
    @GeneratedValue
    private int id;
    private String description;
    @ManyToOne
    @JoinColumn(referencedColumnName = "itemId")
    private Item item;
    @ManyToOne
    @JoinColumn(referencedColumnName = "userId")
    private User requestor;
    private LocalDateTime created;
}
