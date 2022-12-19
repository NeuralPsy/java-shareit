package ru.practicum.shareit.exception;

public class WrongEmailFormatException extends RuntimeException{
    public WrongEmailFormatException(String msg){
        super(msg);
    }
}
