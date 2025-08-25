CREATE TABLE IF NOT EXISTS users
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email       varchar(255) NOT NULL UNIQUE,
    password    varchar(255) NOT NULL,
    last_name   varchar(255),
    first_name  varchar(255),
    middle_name varchar(255),
    role        varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS carrier
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    name         VARCHAR(255)                                    NOT NULL,
    phone_number VARCHAR(255)                                    NOT NULL
);

CREATE TABLE IF NOT EXISTS route
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    origin      VARCHAR(255) NOT NULL,
    destination VARCHAR(255) NOT NULL,
    carrier_id  BIGINT,
    duration    INT          NOT NULL,
    FOREIGN KEY (carrier_id) REFERENCES carrier (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS ticket
(
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    route_id       BIGINT,
    departure_time TIMESTAMP(0)   NOT NULL,
    seat_number    varchar(50)    NOT NULL,
    price          NUMERIC(10, 2) NOT NULL,
    status         varchar(50)    NOT NULL CHECK ( status IN ('AVAILABLE', 'PURCHASED')),
    user_id        BIGINT,
    FOREIGN KEY (route_id) REFERENCES route (id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL
);