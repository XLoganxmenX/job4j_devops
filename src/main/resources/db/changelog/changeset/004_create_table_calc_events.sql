--liquibase formatted sql
--changeset loganxmen:create_table_calc_events
CREATE TABLE calc_events (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    first INT,
    second INT,
    result INT,
    create_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    type VARCHAR(100)
);

--rollback DROP TABLE calc_events;