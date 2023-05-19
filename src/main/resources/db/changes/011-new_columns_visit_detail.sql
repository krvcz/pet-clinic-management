--liquibase formatted sql


--changeset ssanko:011_1
ALTER TABLE visits
    ADD COLUMN weight varchar;

ALTER TABLE visits
    ADD COLUMN temperature varchar;

ALTER TABLE visits
    ADD COLUMN comment varchar;

ALTER TABLE visits
    ADD COLUMN interview varchar;

ALTER TABLE visits
    ADD COLUMN clinical_trials varchar;

ALTER TABLE visits
    ADD COLUMN diagnosis varchar;

ALTER TABLE visits
    ADD COLUMN recommendations varchar;