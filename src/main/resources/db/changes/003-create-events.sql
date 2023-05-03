--liquibase formatted sql


--changeset ssanko:003_1
CREATE TABLE IF NOT EXISTS events (
                                      id SERIAL PRIMARY KEY,
                                      "date" timestamp,
                                      duration integer,
                                      type varchar,
                                      description varchar);

--changeset ssanko:003_2
DROP TABLE IF EXISTS appointments;