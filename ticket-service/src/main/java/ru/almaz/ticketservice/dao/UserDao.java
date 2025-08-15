package ru.almaz.ticketservice.dao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.almaz.ticketservice.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String CREATE_TABLE_SQL = """
                CREATE TABLE IF NOT EXISTS users (
                    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
                    email varchar(255) NOT NULL,
                    password varchar(255) NOT NULL,
                    last_name varchar(255) NOT NULL,
                    first_name varchar(255) NOT NULL,
                    middle_name varchar(255) NOT NULL,
                    role varchar(255) NOT NULL
                )
            """;

    private static final String SAVE_SQL = """
                INSERT INTO users (email, password, last_name, first_name, middle_name, role)
                VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String FIND_BY_EMAIL_SQL = """
                SELECT * 
                FROM users
                WHERE email = ?
            """;

    @PostConstruct
    public void init() {
        jdbcTemplate.execute(CREATE_TABLE_SQL);
    }

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

        user.setId(((Number) Objects.requireNonNull(generatedKeyHolder.getKeys()).get("id")).longValue());
        return user;
    }

    public Optional<User> findByEmail(String email) {
        User user = jdbcTemplate.queryForObject(FIND_BY_EMAIL_SQL, User.class, email);
        return Optional.ofNullable(user);
    }

}
