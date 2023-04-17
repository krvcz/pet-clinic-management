--liquibase formatted sql

--changeset ssanko:001_1

CREATE TABLE IF NOT EXISTS customers (
                                         id SERIAL PRIMARY KEY,
                                         first_name varchar,
                                         last_name varchar,
                                         phone_number varchar,
                                         email varchar
);

CREATE TABLE IF NOT EXISTS pets (
                                    id SERIAL PRIMARY KEY,
                                    name varchar,
                                    species varchar,
                                    breed varchar,
                                    gender varchar,
                                    date_of_birth date,
                                    customer_id integer
);

CREATE TABLE IF NOT EXISTS visits (
                                      id SERIAL PRIMARY KEY,
                                      "date" date,
                                      description varchar,
                                      pet_id integer,
                                      veterinarian_id integer
);

CREATE TABLE IF NOT EXISTS veterinarians (
                                             id SERIAL PRIMARY KEY,
                                             first_name varchar,
                                             last_name varchar,
                                             specialization varchar
);

CREATE TABLE IF NOT EXISTS medicines (
                                         id SERIAL PRIMARY KEY,
                                         name varchar,
                                         dosage varchar
);

CREATE TABLE visits_medicines   (
                                    id SERIAL PRIMARY KEY,
                                    visit_id integer,
                                    medicine_id integer
);

CREATE TABLE IF NOT EXISTS medical_procedures (
                                                  id SERIAL PRIMARY KEY,
                                                  name varchar,
                                                  description varchar,
                                                  cost decimal
);

CREATE TABLE IF NOT EXISTS visits_medical_procedures (
                                                         id SERIAL PRIMARY KEY,
                                                         visit_id integer,
                                                         medical_procedure_id integer
);

CREATE TABLE IF NOT EXISTS payments (
                                        id SERIAL PRIMARY KEY,
                                        amount decimal,
                                        "date" date,
                                        customer_id integer
);

CREATE TABLE IF NOT EXISTS appointments (
                                            id SERIAL PRIMARY KEY,
                                            "date" date,
                                            duration int,
                                            pet_id integer,
                                            veterinarian_id integer
);

CREATE TABLE IF NOT EXISTS reminders (
                                         id SERIAL PRIMARY KEY,
                                         description varchar,
                                         due_date date,
                                         customer_id integer
);

CREATE TABLE IF NOT EXISTS veterinary_clinics (
                                                  id SERIAL PRIMARY KEY,
                                                  name varchar,
                                                  address varchar,
                                                  phone_number varchar
);

CREATE TABLE IF NOT EXISTS employees (
                                         id SERIAL PRIMARY KEY,
                                         first_name varchar,
                                         last_name varchar,
                                         position varchar,
                                         start_date date,
                                         end_date date,
                                         veterinary_clinic_id integer
);

CREATE TABLE IF NOT EXISTS availabilities (
                                              id SERIAL PRIMARY KEY,
                                              day_of_week int,
                                              start_time time,
                                              end_time time,
                                              veterinarian_id integer
);

CREATE TABLE IF NOT EXISTS stock_items (
                                           id SERIAL PRIMARY KEY,
                                           name varchar,
                                           description varchar,
                                           quantity int,
                                           cost decimal,
                                           veterinary_clinic_id integer
);

CREATE TABLE IF NOT EXISTS suppliers (
                                         id SERIAL PRIMARY KEY,
                                         name varchar,
                                         phone_number varchar,
                                         email varchar
);

CREATE TABLE IF NOT EXISTS purchases (
                                         id SERIAL PRIMARY KEY,
                                         "date" date,
                                         supplier_id integer,
                                         veterinary_clinic_id integer
);

CREATE TABLE IF NOT EXISTS purchase_items (
                                              id SERIAL PRIMARY KEY,
                                              quantity int,
                                              cost decimal,
                                              stock_item_id integer,
                                              purchase_id integer
);

--changeset ssanko:001_2
ALTER TABLE pets ADD FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE visits ADD FOREIGN KEY (pet_id) REFERENCES pets (id);

ALTER TABLE visits ADD FOREIGN KEY (veterinarian_id) REFERENCES veterinarians (id);

ALTER TABLE visits_medicines ADD FOREIGN KEY (visit_id) REFERENCES visits (id);

ALTER TABLE visits_medicines ADD FOREIGN KEY (medicine_id) REFERENCES medicines (id);

ALTER TABLE visits_medical_procedures ADD FOREIGN KEY (visit_id) REFERENCES visits (id);

ALTER TABLE visits_medical_procedures ADD FOREIGN KEY (medical_procedure_id) REFERENCES medical_procedures (id);

ALTER TABLE payments ADD FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE appointments ADD FOREIGN KEY (pet_id) REFERENCES pets (id);

ALTER TABLE appointments ADD FOREIGN KEY (veterinarian_id) REFERENCES veterinarians (id);

ALTER TABLE reminders ADD FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE employees ADD FOREIGN KEY (veterinary_clinic_id) REFERENCES veterinary_clinics (id);

ALTER TABLE availabilities ADD FOREIGN KEY (veterinarian_id) REFERENCES veterinarians ("id");

ALTER TABLE stock_items ADD FOREIGN KEY (veterinary_clinic_id) REFERENCES veterinary_clinics ("id");

ALTER TABLE purchases ADD FOREIGN KEY (supplier_id) REFERENCES suppliers (id);

ALTER TABLE purchases ADD FOREIGN KEY (veterinary_clinic_id) REFERENCES veterinary_clinics (id);

ALTER TABLE purchase_items ADD FOREIGN KEY (stock_item_id) REFERENCES stock_items (id);

ALTER TABLE purchase_items ADD FOREIGN KEY (purchase_id) REFERENCES purchases (id);