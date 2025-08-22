package ru.almaz.ticketservice.exception;

public class CarrierNotFoundException extends RuntimeException {
    public CarrierNotFoundException(String message) {
        super(message);
    }
}
