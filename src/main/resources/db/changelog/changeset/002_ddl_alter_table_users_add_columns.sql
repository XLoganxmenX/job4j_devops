--liquibase formatted sql
--changeset loganxmen:add_args_columns
ALTER TABLE users ADD COLUMN first_arg DOUBLE PRECISION;

ALTER TABLE users ADD COLUMN second_arg DOUBLE PRECISION;

ALTER TABLE users ADD COLUMN result DOUBLE PRECISION;