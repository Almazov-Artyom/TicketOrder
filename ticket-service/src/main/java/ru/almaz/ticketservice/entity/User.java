package ru.almaz.ticketservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.almaz.ticketservice.enums.Role;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class User implements UserDetails {

    private Long id;

    private String email;

    private String password;

    private String lastName;

    private String firstName;

    private String middleName;

    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
