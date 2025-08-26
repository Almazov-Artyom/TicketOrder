package ru.almaz.ticketservice.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.almaz.ticketservice.exception.*;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    private String getMessage(RuntimeException e, Locale locale) {
        return messageSource.getMessage(e.getMessage(), null, e.getMessage(), locale);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ProblemDetail handleConflictException(UserAlreadyExistException e, Locale locale) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, getMessage(e, locale));
    }

    @ExceptionHandler({
            InvalidDepartureTimeException.class,
            TicketUnavailableException.class
    })
    public ProblemDetail handleBadRequestException(RuntimeException e, Locale locale) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, getMessage(e, locale));
    }

    @ExceptionHandler({
            UserUnauthenticatedException.class,
            InvalidRefreshTokenException.class
    })
    public ProblemDetail handleUserUnauthenticatedException(RuntimeException e, Locale locale) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, getMessage(e, locale));
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
                ex.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList()
        );
        return response;
    }

    @ExceptionHandler({
            CarrierNotFoundException.class,
            RouteNotFoundException.class,
            TicketNotFoundException.class,
            UserNotFoundException.class,
    })
    public ProblemDetail handleNotFoundException(RuntimeException e, Locale locale) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, getMessage(e, locale));
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
