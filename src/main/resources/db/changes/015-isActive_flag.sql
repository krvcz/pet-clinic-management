
--liquibase formatted sql


--changeset ssanko:015_1
ALTER TABLE customers
    ADD COLUMN is_active boolean;

--changeset ssanko:015_2
ALTER TABLE medical_procedures
    ADD COLUMN is_active boolean;

--changeset ssanko:015_3
ALTER TABLE medicines
    ADD COLUMN is_active boolean;

--changeset ssanko:015_4
ALTER TABLE pets
    ADD COLUMN is_active boolean;

--changeset ssanko:015_5
ALTER TABLE veterinarians
    ADD COLUMN is_active  boolean;

--changeset ssanko:015_6
UPDATE customers SET is_active = true;

--changeset ssanko:015_7
UPDATE medical_procedures SET is_active = true;

--changeset ssanko:015_8
UPDATE medicines SET is_active = true;

--changeset ssanko:015_9
UPDATE pets SET is_active = true;

--changeset ssanko:015_10
UPDATE veterinarians SET is_active = true;