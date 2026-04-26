-- Инициализация таблицы users
CREATE TABLE users
(
    id       UUID PRIMARY KEY,
    login    VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL
);

-- Инициализация таблицы locations
CREATE TABLE locations
(
    id        UUID PRIMARY KEY,
    name      VARCHAR(256)  NOT NULL,
    user_id   UUID           NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    latitude  DECIMAL(9, 6) NOT NULL,
    longitude DECIMAL(9, 6) NOT NULL
);

-- Инициализация таблицы sessions
CREATE TABLE sessions
(
    id         UUID PRIMARY KEY,
    user_id    UUID       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    expires_at TIMESTAMP NOT NULL
);