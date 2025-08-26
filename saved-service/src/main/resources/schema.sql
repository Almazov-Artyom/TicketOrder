CREATE TABLE IF NOT EXISTS ticket_info
(
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ticket_id      BIGINT         NOT NULL,
    origin         varchar(255)   NOT NULL,
    destination    varchar(255)   NOT NULL,
    carrier_name   varchar(255)   NOT NULL,
    duration       integer        NOT NULL,
    departure_time timestamp      NOT NULL,
    seat_number    varchar(50)    NOT NULL,
    price          numeric(10, 2) NOT NULL
);