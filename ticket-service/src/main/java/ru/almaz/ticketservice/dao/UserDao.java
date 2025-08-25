package ru.almaz.ticketservice.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.almaz.ticketservice.entity.User;


import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String SAVE_SQL = """
                INSERT INTO users (email, password, last_name, first_name, middle_name, role)
                VALUES (?, ?, ?, ?, ?, ?)
                RETURNING id;
            """;

    private static final String FIND_BY_EMAIL_SQL = """
                SELECT * 
                FROM users
                WHERE email = ?
            """;

    public User save(User user) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getMiddleName());
            ps.setString(6, user.getRole().toString());
            return ps;
        }, generatedKeyHolder);

        user.setId((Long) generatedKeyHolder.getKey());
        return user;
    }

    public Optional<User> findByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject(FIND_BY_EMAIL_SQL, new BeanPropertyRowMapper<>(User.class) , email);
            return Optional.ofNullable(user);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

}
