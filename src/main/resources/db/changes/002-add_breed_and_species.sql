--liquibase formatted sql

--changeset ssanko:002_1

CREATE TABLE IF NOT EXISTS breed (
                                         id SERIAL PRIMARY KEY,
                                         name varchar,
                                         species_id integer

);

CREATE TABLE IF NOT EXISTS species (
                                    id SERIAL PRIMARY KEY,
                                    name varchar

);


--changeset ssanko:002_2
ALTER TABLE pets
    ADD COLUMN breed_id integer,
    ADD COLUMN species_id integer;



--changeset ssanko:002_3
ALTER TABLE pets ADD FOREIGN KEY (breed_id) REFERENCES breed (id);

ALTER TABLE pets ADD FOREIGN KEY (species_id) REFERENCES species (id);

ALTER TABLE breed ADD FOREIGN KEY (species_id) REFERENCES species (id);


--changeset ssanko:002_4
INSERT INTO species (name) VALUES
                               ('Pies'),
                               ('Kot'),
                               ('Królik'),
                               ('Chomik'),
                               ('Mysz'),
                               ('Koń'),
                               ('Krowa'),
                               ('Owca'),
                               ('Koza'),
                               ('Kura'),
                               ('Kaczka'),
                               ('Gęś'),
                               ('Gołąb'),
                               ('Kanarek'),
                               ('Papuga'),
                               ('Wąż'),
                               ('Żółw'),
                               ('Lis'),
                               ('Wilk'),
                               ('Kuna'),
                               ('Sarna'),
                               ('Jeleń'),
                               ('Świnka morska'),
                               ('Szczur'),
                               ('Jeż'),
                               ('Małpa'),
                               ('Dzięcioł'),
                               ('Łabędź'),
                               ('Kanarek'),
                               ('Bażant'),
                               ('Indyk');



INSERT INTO breed (species_id, name) VALUES
                                         (1, 'Owczarek niemiecki'),
                                         (1, 'Labrador'),
                                         (1, 'Golden retriever'),
                                         (1, 'Chihuahua'),
                                         (1, 'Yorkshire terrier'),
                                         (1, 'Mops'),
                                         (1, 'Beagle'),
                                         (1, 'Buldog angielski'),
                                         (1, 'Husky syberyjski'),
                                         (1, 'Bull terrier'),
                                         (1, 'Nieokreślona'),
                                         (2, 'Pers'),
                                         (2, 'Syjamski'),
                                         (2, 'Brytyjski krótkowłosy'),
                                         (2, 'Ragdoll'),
                                         (2, 'Sphynx'),
                                         (2, 'Bengalski'),
                                         (2, 'Norweski leśny'),
                                         (2, 'Rosyjski niebieski'),
                                         (2, 'Savannah'),
                                         (2, 'Cornish Rex'),
                                         (2, 'Nieokreślona'),
                                         (3, 'Królik mieszańcowy'),
                                         (3, 'Królik miniaturka'),
                                         (3, 'Królik kalifornijski'),
                                         (3, 'Królik karłowaty'),
                                         (3, 'Królik olbrzymi'),
                                         (3, 'Królik tyrolski'),
                                         (3, 'Nieokreślona'),
                                         (4, 'Chomik syryjski'),
                                         (4, 'Chomik roborowskiego'),
                                         (4, 'Chomik campbelli'),
                                         (4, 'Chomik chiński'),
                                         (4, 'Nieokreślona'),
                                         (5, 'Nieokreślona'),
                                         (6, 'Angielski koń pełnej krwi'),
                                         (6, 'Polski koń zimnokrwisty'),
                                         (6, 'Konik polski'),
                                         (6, 'Koń andaluzyjski'),
                                         (6, 'Koń arabski'),
                                         (6, 'Koń berberyjski'),
                                         (6, 'Koń czeski'),
                                        (6, 'Nieokreślona'),
                                         (7, 'Simmental'),
                                         (7, 'Holenderska'),
                                         (7, 'Hereford'),
                                         (7, 'Aberdeen Angus'),
                                         (7, 'Charolaise'),
                                         (7, 'Limousine'),
                                         (7, 'Belgijska niebieska'),
                                         (7, 'Jersey'),
                                         (7, 'Nieokreślona'),
                                         (8, 'Owca domowa'),
                                         (8, 'Merynos'),
                                         (8, 'Owca angielska'),
                                         (8, 'Owca dolly'),
                                         (8, 'Nieokreślona'),
                                         (9, 'Koza domowa'),
                                         (9, 'Koza saanen'),
                                         (9, 'Koza nubijska'),
                                         (9, 'Koza francuska alpejska'),
                                         (9, 'Nieokreślona'),
                                         (10, 'Kura nioska'),
                                         (10, 'Kura brojlerowa'),
                                         (10, 'Kura jednodniówka'),
                                         (10, 'Kura rasy Sussex'),
                                         (10, 'Nieokreślona'),
                                         (11, 'Kaczka pekińska'),
                                         (11, 'Kaczka mulard'),
                                         (11, 'Nieokreślona'),
                                         (12, 'Gęś tulańska'),
                                         (12, 'Gęś domowa'),
                                         (12, 'Nieokreślona'),
                                         (13, 'Gołąb pocztowy'),
                                         (13, 'Gołąb łukowy'),
                                         (13, 'Gołąb śląski'),
                                         (13, 'Nieokreślona'),
                                         (14, 'Nieokreślona');

