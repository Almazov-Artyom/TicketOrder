package ru.almaz.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.ticketservice.dao.UserDao;
import ru.almaz.ticketservice.entity.User;
import ru.almaz.ticketservice.enums.Role;
import ru.almaz.ticketservice.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void saveUser(User user) {
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

    @Transactional
    public void createAdmin() {
        if(userDao.findByEmail(adminEmail).isPresent())
            return;

        User admin = new User();
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRole(Role.ADMIN);

        userDao.save(admin);
    }
}
