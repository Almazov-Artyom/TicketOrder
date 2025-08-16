package ru.almaz.ticketservice.exception;

public class UserUnauthenticatedException extends RuntimeException {
    public UserUnauthenticatedException(String message) {
        super(message);
    }
}
