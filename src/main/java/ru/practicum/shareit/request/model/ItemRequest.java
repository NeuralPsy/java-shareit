package ru.practicum.shareit.request.model;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class ItemRequest {
    @Id
    @GeneratedValue
    private int id;
    private String description;
    @OneToOne
    private Item item;
    @ManyToOne
    @JoinColumn(referencedColumnName = "userId")
    private User requestor;
    private LocalDateTime created;
}
