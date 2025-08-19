package ru.almaz.ticketservice.exception;

public class TicketUnavailableException extends RuntimeException {
  public TicketUnavailableException(String message) {
    super(message);
  }
}
