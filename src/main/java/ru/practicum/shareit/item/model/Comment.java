package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue
    private int commentId;
    private String text;
    private LocalDateTime postDate;
    @ManyToOne
    @JoinColumn(referencedColumnName = "userId")
    private User commentator;
}
