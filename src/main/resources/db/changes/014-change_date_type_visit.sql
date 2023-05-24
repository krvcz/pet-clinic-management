
--liquibase formatted sql


--changeset ssanko:014_1
ALTER TABLE visits
ALTER COLUMN "date" TYPE TIMESTAMPTZ;