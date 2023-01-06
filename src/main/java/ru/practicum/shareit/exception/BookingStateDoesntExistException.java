package ru.practicum.shareit.exception;

public class BookingStateDoesntExistException extends RuntimeException{
    public BookingStateDoesntExistException(String msg){
        super(msg);
    }
}
