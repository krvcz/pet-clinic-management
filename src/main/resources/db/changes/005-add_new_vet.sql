--liquibase formatted sql


--changeset ssanko:005_1

INSERT INTO veterinarians (first_name, last_name, specialization)
VALUES ('Jan', 'Kowalski', 'Weterynarz');