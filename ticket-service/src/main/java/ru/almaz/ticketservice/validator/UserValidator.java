package ru.almaz.ticketservice.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.dao.UserDao;
import ru.almaz.ticketservice.exception.UserAlreadyExistException;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserDao userDao;

    public void isEmailValid(String email) {
        if (userDao.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistException("Пользователь с таким email уже существует");
        }
    }
}
