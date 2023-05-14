--liquibase formatted sql


--changeset ssanko:006_1

ALTER TABLE medicines
    ADD COLUMN registration_number varchar;

ALTER TABLE medicines
    ADD COLUMN composition varchar;

ALTER TABLE medicines
    ADD COLUMN contraindications varchar;

ALTER TABLE medicines
    ADD COLUMN side_effects varchar;

ALTER TABLE medicines
    ADD COLUMN administration_route varchar;

ALTER TABLE medicines
    ADD COLUMN manufacturer varchar;

ALTER TABLE medicines
    ADD COLUMN price decimal;

--changeset ssanko:006_2
ALTER TABLE medical_procedures
    RENAME COLUMN cost TO price;

--changeset ssanko:006_3
INSERT INTO medicines (id, name, registration_number, composition, dosage, contraindications, side_effects, administration_route, manufacturer, price)
VALUES
    (1, 'Stomorgyl', 'PL/123456', 'Spiramycyna, Metronidazol', '1 tabletka na 10 kg masy ciała 2 razy dziennie', 'Nie stosować u psów z ciężką niewydolnością wątroby', 'Wymioty, biegunka', 'Doustnie', 'Firma X', 25.00),
    (2, 'Marbocyl', 'PL/234567', 'Marbocyl', '1 tabletka na 20 kg masy ciała 1 raz dziennie', 'Nie stosować u psów z zaburzeniami czynności nerek', 'Nudności, wymioty', 'Doustnie', 'Firma Y', 35.00),
    (3, 'Enroxil', 'PL/345678', 'Enrofloksacyna', '1 tabletka na 10 kg masy ciała 2 razy dziennie', 'Nie stosować u kotów w ciąży i karmiących', 'Biegunka, nudności', 'Doustnie', 'Firma Z', 20.00),
    (4, 'Noromycin', 'PL/456789', 'Oksytetracyklina', '1 ml na 10 kg masy ciała 2 razy dziennie', 'Nie stosować u zwierząt z nadwrażliwością na oksytetracyklinę', 'Reakcje alergiczne', 'Podskórnie, domięśniowo', 'Firma A', 30.00),
    (5, 'Suvaxyn', 'PL/567890', 'Suvaxyn PCV', '2 ml podskórnie', 'Nie stosować u zwierząt z ciężką niewydolnością serca lub płuc', 'Obrzęk, ból w miejscu wstrzyknięcia', 'Podskórnie', 'Firma B', 40.00),
    (6, 'Ectoline', 'PL/678901', 'Fipronil', '1 pipeta na zwierzę', 'Nie stosować u kotów w ciąży i karmiących', 'Reakcje skórne, świąd', 'Nakrapianie na skórę', 'Firma C', 15.00),
    (7, 'Advocate', 'PL/790123', 'Imidakloprid, Moxidectin', '1 pipeta na zwierzę', 'Nie stosować u szczeniąt poniżej 7 tygodni życia', 'Reakcje skórne, świąd', 'Nakrapianie na skórę', 'Firma D', 45.00),
    (8, 'Doxycycline', 'PL/012345', 'Doksycyklina', '1 tabletka na 20 kg masy ciała 2 razy dziennie', 'Nie stosować u szczeniąt poniżej 6 miesiąca życia', 'Reakcje skórne, zaburzenia żołądkowo-jelitowe', 'Doustnie', 'Firma E', 22.00),
    (9, 'Clamoxyl', 'PL/234501', 'Amoksycylina', '1 tabletka na 10 kg masy ciała 2 razy dziennie', 'Nie stosować u zwierząt z alergią na penicyliny', 'Biegunka, wymioty', 'Doustnie', 'Firma F', 28.00),
    (10, 'Rilexine', 'PL/345012', 'Cefaleksyna', '1 tabletka na 10 kg masy ciała 2 razy dziennie', 'Nie stosować u kotów w ciąży i karmiących', 'Nudności, wymioty', 'Doustnie', 'Firma G', 32.00),
    (11, 'Milbemax', 'PL/450123', 'Milbemycyna, Praziquantel', '1 tabletka na 5 kg masy ciała', 'Nie stosować u szczeniąt poniżej 2 tygodni życia', 'Zaburzenia żołądkowo-jelitowe, biegunka', 'Doustnie', 'Firma H', 38.00),
    (12, 'Frontline', 'PL/501234', 'Fipronil', '1 pipeta na zwierzę', 'Nie stosować u kotów w ciąży i karmiących', 'Reakcje skórne, świąd', 'Nakrapianie na skórę', 'Firma I', 16.00),
    (13, 'Biomoxin', 'PL/012345', 'Amoksycylina', '1 ml na 10 kg masy ciała 2 razy dziennie', 'Nie stosować u zwierząt z alergią na penicyliny', 'Biegunka, wymioty', 'Podskórnie, domięśniowo', 'Firma J', 42.00),
    (14, 'Metacam', 'PL/234501', 'Meloksykam', '0.2 mg/kg masy ciała raz dziennie', 'Nie stosować u zwierząt z chorobami nerek, wątroby lub serca', 'Nudności, wymioty', 'Doustnie', 'Firma K', 24.00),
    (15, 'Dectomax', 'PL/123456', 'Doramektyna', '1 ml na 50 kg masy ciała', 'Nie stosować u zwierząt z nadwrażliwością na doramektynę', 'Reakcje skórne, świąd', 'Wstrzyknięcie podskórne', 'Firma E', 50.00),
    (16, 'Biomoxan', 'PL/234567', 'Amoksycylina', '1 tabletka na 10 kg masy ciała 2 razy dziennie', 'Nie stosować u zwierząt z nadwrażliwością na amoksycylinę', 'Biegunka, nudności', 'Doustnie', 'Firma F', 35.00),
    (17, 'Vetmedin', 'PL/345678', 'Pimobendan', '1 tabletka na 10 kg masy ciała 2 razy dziennie', 'Nie stosować u zwierząt z ciężką niewydolnością serca', 'Wymioty, ból brzucha', 'Doustnie', 'Firma G', 45.00),
    (18, 'Canigen', 'PL/456789', 'Wirusy żywe osłabione', '1 dawka podskórna', 'Nie stosować u szczeniąt poniżej 6 tygodni życia', 'Obrzęk, reakcje alergiczne', 'Podskórnie', 'Firma H', 30.00),
    (19, 'Dolpac', 'PL/567890', 'Praziquantel, Pyrantel, Febantel', '1 tabletka na 10 kg masy ciała', 'Nie stosować u szczeniąt poniżej 2 tygodni życia', 'Biegunka, wymioty', 'Doustnie', 'Firma I', 25.00),
    (20, 'Cimalgex', 'PL/678901', 'Cimicoxib', '1 tabletka na 20 kg masy ciała 1 raz dziennie', 'Nie stosować u zwierząt z nadwrażliwością na cimicoxib', 'Nudności, wymioty', 'Doustnie', 'Firma J', 55.00),
    (21, 'Milbemax', 'PL/790123', 'Milbemycyna, Praziquantel', '1 tabletka na 10 kg masy ciała', 'Nie stosować u szczeniąt poniżej 2 tygodni życia', 'Biegunka, reakcje alergiczne', 'Doustnie', 'Firma K', 40.00),
    (22, 'Doxiciclina', 'PL/00000/0000', 'Doksycyklina', '10mg', 'Hipersensyjność na doksycyklinę lub inny tetracykliny', 'Przejściowe zaburzenia układu pokarmowego', 'Doustnie', 'Dopharma', 12.99),
    (23, 'Stomorgyl', 'PL/00000/0000', 'Spiramycyna, Metronidazol', '150mg, 50mg', 'Nie stosować u szczeniąt i kotek do 6 miesiąca życia oraz u zwierząt w ciąży i laktacji', 'Zaburzenia przewodu pokarmowego', 'Doustnie', 'Vetoquinol', 45.00),
    (24, 'Cimalgex', 'PL/00000/0000', 'Cimicoxib', '8mg', 'Nie stosować u psów z ciężką niewydolnością wątroby i nerek oraz u szczeniąt poniżej 4 miesiąca życia', 'Zaburzenia przewodu pokarmowego, zaburzenia nerek i wątroby', 'Doustnie', 'Virbac', 35.99),
    (25, 'Bilovet', 'PL/00000/0000', 'Ivermektyna', '10mg', 'Nie stosować u szczeniąt i kotek poniżej 6 miesiąca życia oraz u zwierząt w ciąży i laktacji', 'Zaburzenia układu nerwowego', 'Podskórnie', 'Biowet', 7.99),
    (26, 'Anipryl', 'PL/00000/0000', 'Selegilina', '5mg', 'Nie stosować u psów poniżej 6 roku życia, suki karmiącej i ciężarnej', 'Zaburzenia przewodu pokarmowego, zaburzenia nerwowego', 'Doustnie', 'Zoetis', 69.99),
    (27, 'Felimazole', 'PL/00000/0000', 'Metimazol', '5mg', 'Nie stosować u kotów w ciąży, karmiących, a także u zwierząt z ciężką niewydolnością wątroby', 'Zaburzenia przewodu pokarmowego, biegunka', 'Doustnie', 'Dechra Veterinary Products', 39.99),
    (28, 'Procox', 'PL/00000/0000', 'Toltrazuril', '25mg', 'Nie stosować u szczeniąt poniżej 6 tygodnia życia, u ciężarnych i karmiących suczek', 'Zaburzenia przewodu pokarmowego', 'Doustnie', 'MSD Animal Health', 49.99),
    (29, 'Amoxycare', 'VMP000775', 'Amoksycylina', '5 mg/kg m.c.', 'Nadwrażliwość na penicyliny, cefalosporyny', 'Nadwrażliwość, objawy ze strony przewodu pokarmowego', 'Orale', 'Vetoquinol', 35.50),
    (30, 'Gastrogel', 'VMP000764', 'Siarczan wapnia, magnezu, glicyny', '0,5 - 1 g/kg m.c.', 'Hipermagnezemia, hiperkalcemia', 'Zaparcia, biegunka', 'Orale', 'Krka', 17.00),
    (31, 'Milprazon', 'VMP000763', 'Milbemycyna oxym, praziquantel', '2 mg/kg m.c.', 'Nadwrażliwość', 'Nadwrażliwość, objawy ze strony przewodu pokarmowego', 'Orale', 'Krka', 35.00),
    (32, 'Solu-Delta-Cortef', 'VMP000360', 'Deksametazon', '0,05 - 0,2 mg/kg m.c.', 'Nadwrażliwość na kortykosteroidy', 'Nadwrażliwość, zahamowanie układu immunologicznego', 'Iniekcje', 'Pfizer', 55.00),
    (33, 'Seresto', 'VMP000763', 'Imidakloprid, flumetryna', '1,25 g/necklace', 'Nie stosować u szczeniąt poniżej 7 tygodni', 'Reakcje skórne, objawy ogólne', 'Zewnętrzne', 'Bayer', 89.00),
    (34, 'Convenia', 'VMP000766', 'Cefovecyna', '8 mg/kg m.c.', 'Nadwrażliwość na cefalosporyny', 'Nadwrażliwość, objawy ze strony przewodu pokarmowego', 'Iniekcje', 'Zoetis', 150.00);



