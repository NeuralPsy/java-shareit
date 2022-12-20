package ru.practicum.shareit.exception;

public class EmptyItemFieldException extends  RuntimeException{
    public EmptyItemFieldException(String msg){
        super(msg);
    }
}
