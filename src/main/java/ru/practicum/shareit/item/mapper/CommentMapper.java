package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;

public class CommentMapper {
    public static CommentDto commentToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setText(comment.getText());
        return commentDto;
    }
}
