--liquibase formatted sql
--changeset loganxmen:create_users_table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(2000)
);