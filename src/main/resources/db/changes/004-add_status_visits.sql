--liquibase formatted sql


--changeset ssanko:004_1
ALTER TABLE visits
    ADD COLUMN status varchar;

--changeset ssanko:004_2
UPDATE visits SET status = 'w trakcie';

