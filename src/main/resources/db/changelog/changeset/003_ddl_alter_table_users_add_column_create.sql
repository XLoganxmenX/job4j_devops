--liquibase formatted sql
--changeset loganxmen:users_add_create_date_column
ALTER TABLE users ADD COLUMN create_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now();

--rollback ALTER TABLE users DROP COLUMN create_date;