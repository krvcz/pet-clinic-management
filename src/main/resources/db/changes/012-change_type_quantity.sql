--liquibase formatted sql


--changeset ssanko:012_1
ALTER TABLE visits_medicines
ALTER COLUMN quantity TYPE float(53);