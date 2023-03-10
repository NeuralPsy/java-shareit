package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue
    private int commentId;
    private String text;
    private LocalDateTime postDate;
    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User commentator;
}
