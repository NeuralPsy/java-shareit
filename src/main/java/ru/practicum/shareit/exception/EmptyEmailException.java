package ru.practicum.shareit.exception;

public class EmptyEmailException extends RuntimeException {
    public EmptyEmailException(String msg) {
        super(msg);
    }
}
