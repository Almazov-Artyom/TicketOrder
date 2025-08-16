package ru.almaz.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.almaz.ticketservice.dto.*;
import ru.almaz.ticketservice.entity.User;
import ru.almaz.ticketservice.enums.Role;
import ru.almaz.ticketservice.exception.UserUnauthenticatedException;
import ru.almaz.ticketservice.mapper.UserMapper;
import ru.almaz.ticketservice.validator.JwtValidator;
import ru.almaz.ticketservice.validator.UserValidator;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    private final JwtCacheService jwtCacheService;

    private final JwtValidator jwtValidator;

    private final UserValidator userValidator;

    public RegistrationResponse registration(RegistrationRequest registrationRequest) {
        userValidator.isEmailValid(registrationRequest.email());

        User user = userMapper.toUser(registrationRequest);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        userService.save(user);
        return new RegistrationResponse("Вы успешно зарегистрировались");
    }


    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
            User user = (User) authentication.getPrincipal();

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            jwtCacheService.putAccessToken(user.getUsername(), accessToken);
            jwtCacheService.putRefreshToken(user.getUsername(), refreshToken);

            return new LoginResponse(accessToken, refreshToken);
        } catch (AuthenticationException ex) {
            throw new UserUnauthenticatedException("Пользователь не аутентифицирован");
        }
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();
        String email = jwtService.extractEmail(refreshToken);

        jwtValidator.isRefreshTokenValid(email, refreshToken);

        String role = jwtService.extractRole(refreshToken);

        User user = new User();
        user.setEmail(email);
        user.setRole(Role.valueOf(role));

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        jwtCacheService.putAccessToken(email, newAccessToken);
        jwtCacheService.putRefreshToken(email, newRefreshToken);

        return new RefreshTokenResponse(newAccessToken,newRefreshToken);
    }

}
