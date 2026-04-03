CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL
);