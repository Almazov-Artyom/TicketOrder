package ru.almaz.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.ticketservice.dao.UserDao;
import ru.almaz.ticketservice.entity.User;
import ru.almaz.ticketservice.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email).
                orElseThrow(()->new UserNotFoundException("Пользователь не найден"));
    }

    public Long getCurrentUserId() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return Long.valueOf(userId);
    }

    public UserDetailsService userDetailsService() {
        return this::getUserByEmail;
    }



}
