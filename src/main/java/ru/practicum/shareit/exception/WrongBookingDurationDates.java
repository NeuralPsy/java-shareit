package ru.practicum.shareit.exception;

public class WrongBookingDurationDates extends RuntimeException {
    public WrongBookingDurationDates(String msg) {
        super(msg);
    }
}
