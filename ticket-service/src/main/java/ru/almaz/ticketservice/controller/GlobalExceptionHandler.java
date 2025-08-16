package ru.almaz.ticketservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.almaz.ticketservice.exception.InvalidAccessTokenException;
import ru.almaz.ticketservice.exception.InvalidRefreshTokenException;
import ru.almaz.ticketservice.exception.UserAlreadyExistException;
import ru.almaz.ticketservice.exception.UserUnauthenticatedException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            UserAlreadyExistException.class,
            UsernameNotFoundException.class,
    })
    public ProblemDetail handleUserAlreadyExistException(RuntimeException e) {
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
}
