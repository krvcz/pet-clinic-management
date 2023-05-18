--liquibase formatted sql


--changeset ssanko:010_1
DROP TABLE IF EXISTS public.visits_medical_procedures;

--changeset ssanko:010_2
CREATE TABLE IF NOT EXISTS public.visits_medical_procedures
(
    visit_id integer NOT NULL,
    medical_procedure_id integer NOT NULL,
    CONSTRAINT visits_medical_procedures_visit_id_medicine_id_pk PRIMARY KEY (visit_id, medical_procedure_id),
    CONSTRAINT visits_medical_procedures_medical_procedure_id_fkey FOREIGN KEY (medical_procedure_id)
    REFERENCES public.medical_procedures (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT visits_medical_procedures_visit_id_fkey FOREIGN KEY (visit_id)
    REFERENCES public.visits (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );

ALTER TABLE IF EXISTS public.visits_medical_procedures
    OWNER to admin;

--changeset ssanko:010_3
ALTER TABLE public.visits_medicines
    ADD CONSTRAINT medicine_unit_id_fkey FOREIGN KEY (medicine_unit_id) REFERENCES medicine_units (id);
