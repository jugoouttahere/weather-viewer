CREATE TABLE locations
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(256)  NOT NULL,
    user_id   INT           NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    latitude  DECIMAL(9, 6) NOT NULL,
    longitude DECIMAL(9, 6) NOT NULL
);