--liquibase formatted sql


--changeset ssanko:008_1
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (8.99, 'Opakowanie', 19), (3.99, 'Tabletka', 19);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (4.99, 'Kapsułka', 20), (7.49, 'Opakowanie', 20);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (2.50, 'Tabletka', 21), (6.99, 'Kapsułka', 21);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (3.99, 'Opakowanie', 22), (5.99, 'Tabletka', 22);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (1.75, 'Kapsułka', 23), (4.49, 'Opakowanie', 23);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (6.49, 'Tabletka', 24), (3.25, 'Kapsułka', 24);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (9.50, 'Opakowanie', 25), (2.99, 'Tabletka', 25);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (2.75, 'Kapsułka', 26), (7.49, 'Opakowanie', 26);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (6.49, 'Tabletka', 27), (3.75, 'Kapsułka', 27);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (8.99, 'Opakowanie', 28), (4.99, 'Tabletka', 28);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (2.25, 'Kapsułka', 29), (7.99, 'Opakowanie', 29);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (6.49, 'Tabletka', 30), (3.75, 'Kapsułka', 30);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (9.99, 'Opakowanie', 31), (4.99, 'Tabletka', 31);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (2.75, 'Kapsułka', 32), (7.49, 'Opakowanie', 32);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (6.49, 'Tabletka', 33), (3.75, 'Kapsułka', 33);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (4.99, 'Opakowanie', 1), (2.50, 'Tabletka', 1);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (8.99, 'Kapsułka', 2), (5.99, 'Opakowanie', 2);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (1.99, 'Tabletka', 3), (6.50, 'Kapsułka', 3);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (3.99, 'Opakowanie', 4), (2.25, 'Tabletka', 4);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (7.99, 'Kapsułka', 5), (5.49, 'Opakowanie', 5);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (2.99, 'Tabletka', 6), (9.50, 'Kapsułka', 6);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (4.49, 'Opakowanie', 7), (1.75, 'Tabletka', 7);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (5.99, 'Kapsułka', 8), (3.99, 'Opakowanie', 8);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (2.10, 'Tabletka', 9), (6.99, 'Kapsułka', 9);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (7.49, 'Opakowanie', 10), (2.75, 'Tabletka', 10);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (4.99, 'Kapsułka', 11), (6.49, 'Opakowanie', 11);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (3.25, 'Tabletka', 12), (8.99, 'Kapsułka', 12);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (3.99, 'Opakowanie', 13), (1.50, 'Tabletka', 13);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (5.49, 'Kapsułka', 14), (4.99, 'Opakowanie', 14);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (2.25, 'Tabletka', 15), (7.99, 'Kapsułka', 15);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (6.49, 'Opakowanie', 16), (3.75, 'Tabletka', 16);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (9.99, 'Kapsułka', 17), (4.99, 'Opakowanie', 17);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (2.75, 'Tabletka', 18), (6.49, 'Kapsułka', 18);
INSERT INTO medicine_units (price, unit, medicine_id) VALUES (6.49, 'Tabletka', 34), (3.75, 'Kapsułka', 34);

--changeset ssanko:008_2
ALTER TABLE visits_medicines ADD COLUMN medicine_unit_id integer;