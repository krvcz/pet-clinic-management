--liquibase formatted sql


--changeset ssanko:007_1
DROP TABLE IF EXISTS public.visits_medicines;

--changeset ssanko:007_2
CREATE TABLE IF NOT EXISTS public.visits_medicines
(
    visit_id integer NOT NULL,
    medicine_id integer NOT NULL,
    quantity integer,
    CONSTRAINT visits_medicines_visit_id_medicine_id_pk PRIMARY KEY (visit_id, medicine_id),
    CONSTRAINT visits_medicines_medicine_id_fkey FOREIGN KEY (medicine_id)
    REFERENCES public.medicines (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT visits_medicines_visit_id_fkey FOREIGN KEY (visit_id)
    REFERENCES public.visits (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );

ALTER TABLE IF EXISTS public.visits_medicines
    OWNER to admin;

--changeset ssanko:007_3
ALTER TABLE public.medicines DROP COLUMN price;

--changeset ssanko:007_4
CREATE TABLE IF NOT EXISTS public.medicine_units
(
    id bigserial PRIMARY KEY,
    medicine_id integer NOT NULL,
    unit varchar NOT NULL,
    price decimal NOT NULL,
    CONSTRAINT medicine_units_medicine_id_fkey FOREIGN KEY (medicine_id)
    REFERENCES public.medicines (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );
