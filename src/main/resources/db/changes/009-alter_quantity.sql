--liquibase formatted sql


--changeset ssanko:009_1
ALTER TABLE visits_medicines
ALTER COLUMN quantity TYPE decimal;