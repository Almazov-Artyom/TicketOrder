package ru.almaz.ticketservice.exception;

public class InvalidDepartureTimeException extends RuntimeException {
    public InvalidDepartureTimeException(String message) {
        super(message);
    }
}
