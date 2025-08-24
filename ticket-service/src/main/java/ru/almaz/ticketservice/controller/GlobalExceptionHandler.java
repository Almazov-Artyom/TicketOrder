package ru.almaz.ticketservice.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.almaz.ticketservice.exception.*;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            UserAlreadyExistException.class,
            UsernameNotFoundException.class,
            InvalidDepartureTimeException.class,
            TicketUnavailableException.class,
    })
    public ProblemDetail handleBadRequestException(RuntimeException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({
            UserUnauthenticatedException.class,
            InvalidAccessTokenException.class,
            InvalidRefreshTokenException.class
    })
    public ProblemDetail handleUserUnauthenticatedException(RuntimeException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex) {
        ProblemDetail response = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Error");
        response.setProperty(
                "errors",
                ex.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList()
        );
        return response;
    }

    @ExceptionHandler({
            CarrierNotFoundException.class,
            RouteNotFoundException.class,
            TicketNotFoundException.class,
            UserNotFoundException.class,
    })
    public ProblemDetail handleNotFoundException(RuntimeException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();
        String message = e.getMessage();

        if (cause instanceof InvalidFormatException invalidFormatException) {
            if (invalidFormatException.getTargetType() == Timestamp.class)
                message = "Дата должна быть в формате dd.MM.yyyy H:mm";
        }

        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
    }
}
