package ru.practicum.shareit.exception;

public class NotBookerOrOwnerException extends RuntimeException{
    public NotBookerOrOwnerException(String msg){
        super(msg);
    }
}
