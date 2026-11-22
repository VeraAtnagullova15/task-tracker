package ru.atnagullova.tasktrackerbackend.exception;

public class BadDataRequestException extends RuntimeException {
    public BadDataRequestException(String message) {
        super(message);
    }
}
