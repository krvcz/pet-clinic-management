--liquibase formatted sql


--changeset ssanko:013_1
ALTER TABLE medical_procedures
    ADD COLUMN type varchar;

--changeset ssanko:013_2
DELETE FROM visits_medical_procedures;
DELETE FROM medical_procedures;

--changeset ssanko:013_3
INSERT INTO medical_procedures (id, name, description, price, type)
VALUES
    (1, 'Badania krwi', 'Pełne badanie krwi dla zwierząt', 150.00, 'Badanie laboratoryjne'),
    (2, 'Rentgen', 'Badanie rentgenowskie dla zwierząt', 200.00, 'RTG/USG'),
    (3, 'Znieczulenie ogólne', 'Zabieg z zastosowaniem znieczulenia ogólnego', 300.00, 'Zabieg'),
    (4, 'Badanie moczu', 'Analiza moczu dla zwierząt', 120.00, 'Badanie laboratoryjne'),
    (5, 'USG brzucha', 'Badanie ultrasonograficzne brzucha zwierząt', 250.00, 'RTG/USG'),
    (6, 'Wizyta kontrolna', 'Standardowa wizyta kontrolna', 80.00, 'Zabieg'),
    (7, 'EKG', 'Elektrokardiogram dla zwierząt', 180.00, 'Badanie laboratoryjne'),
    (8, 'Zastrzyk przeciwpasożytniczy', 'Podanie zastrzyku przeciwpasożytniczego', 50.00, 'Zabieg'),
    (9, 'Konsultacja dietetyczna', 'Konsultacja dotycząca diety zwierząt', 120.00, 'Zabieg'),
    (10, 'Badanie kału', 'Analiza kału dla zwierząt', 100.00, 'Badanie laboratoryjne'),
    (11, 'Kontrola ciąży', 'Badanie ultrasonograficzne w celu kontroli ciąży', 150.00, 'RTG/USG'),
    (12, 'Zabieg chirurgiczny', 'Zabieg chirurgiczny dla zwierząt', 400.00, 'Zabieg'),
    (13, 'Badanie krzyżowe', 'Badanie krzyżowe dla zwierząt', 180.00, 'Badanie laboratoryjne'),
    (14, 'USG serca', 'Badanie ultrasonograficzne serca zwierząt', 300.00, 'RTG/USG'),
    (15, 'Pielęgnacja zębów', 'Profesjonalna pielęgnacja zębów zwierząt', 220.00, 'Zabieg'),
    (16, 'Badanie wzroku', 'Badanie wzroku u zwierząt', 120.00, 'Badanie laboratoryjne'),
    (17, 'Kąpiel pielęgnacyjna', 'Kąpiel pielęgnacyjna dla zwierząt', 80.00, 'Zabieg'),
    (18, 'Badanie dermatologiczne', 'Badanie dermatologiczne zwierząt', 200.00, 'Badanie laboratoryjne'),
    (19, 'Zastrzyk przeciwko alergii', 'Podanie zastrzyku przeciwko alergii', 150.00, 'Zabieg'),
    (20, 'Mycie zębów', 'Mycie zębów u zwierząt', 100.00, 'Zabieg'),
    (21, 'Badanie nerek', 'Badanie ultrasonograficzne nerek zwierząt', 180.00, 'RTG/USG'),
    (22, 'Konsultacja behawioralna', 'Konsultacja behawioralna dla zwierząt', 250.00, 'Zabieg'),
    (23, 'Badanie hormonalne', 'Badanie hormonalne zwierząt', 160.00, 'Badanie laboratoryjne'),
    (24, 'Konsultacja żywieniowa', 'Konsultacja dotycząca żywienia zwierząt', 120.00, 'Zabieg'),
    (25, 'USG jamy brzusznej', 'Badanie ultrasonograficzne jamy brzusznej zwierząt', 300.00, 'RTG/USG'),
    (26, 'Sterylizacja', 'Zabieg sterylizacji zwierząt', 350.00, 'Zabieg'),
    (27, 'Badanie alergiczne', 'Badanie alergiczne u zwierząt', 220.00, 'Badanie laboratoryjne'),
    (28, 'Eutanazja', 'Zabieg eutanazji dla zwierząt', 100.00, 'Zabieg'),
    (29, 'Badanie wątroby', 'Badanie ultrasonograficzne wątroby zwierząt', 200.00, 'RTG/USG'),
    (30, 'Masaż terapeutyczny', 'Masaż terapeutyczny dla zwierząt', 150.00, 'Zabieg'),
    (31, 'Badanie serologiczne', 'Badanie serologiczne u zwierząt', 180.00, 'Badanie laboratoryjne'),
    (32, 'Zabieg stomatologiczny', 'Zabieg stomatologiczny dla zwierząt', 250.00, 'Zabieg'),
    (33, 'Badanie tarczycy', 'Badanie ultrasonograficzne tarczycy zwierząt', 200.00, 'RTG/USG'),
    (34, 'Konsultacja szczepień', 'Konsultacja dotycząca szczepień zwierząt', 120.00, 'Zabieg'),
    (35, 'Badanie cytologiczne', 'Badanie cytologiczne u zwierząt', 150.00, 'Badanie laboratoryjne'),
    (36, 'Zabieg usunięcia guza', 'Zabieg chirurgiczny usunięcia guza u zwierząt', 400.00, 'Zabieg'),
    (37, 'Badanie stawów', 'Badanie ultrasonograficzne stawów zwierząt', 300.00, 'RTG/USG'),
    (38, 'Konsultacja opieki nad szczeniętami', 'Konsultacja dotycząca opieki nad szczeniętami', 100.00, 'Zabieg'),
    (39, 'Badanie krwi na alergie', 'Badanie krwi na alergie u zwierząt', 180.00, 'Badanie laboratoryjne'),
    (40, 'Zabieg usuwania ciała obcego', 'Zabieg chirurgiczny usuwania ciała obcego u zwierząt', 350.00, 'Zabieg'),
    (41, 'Badanie genetyczne', 'Badanie genetyczne zwierząt', 220.00, 'Badanie laboratoryjne'),
    (42, 'Konsultacja treningowa', 'Konsultacja treningowa dla zwierząt', 150.00, 'Zabieg'),
    (43, 'Badanie węzłów chłonnych', 'Badanie ultrasonograficzne węzłów chłonnych zwierząt', 200.00, 'RTG/USG'),
    (44, 'Zabieg amputacji', 'Zabieg chirurgiczny amputacji dla zwierząt', 400.00, 'Zabieg'),
    (45, 'Konsultacja reprodukcyjna', 'Konsultacja dotycząca zagadnień reprodukcyjnych u zwierząt', 120.00, 'Zabieg'),
    (46, 'Badanie moczu na obecność kamieni', 'Badanie moczu na obecność kamieni u zwierząt', 180.00, 'Badanie laboratoryjne'),
    (47, 'Zabieg usunięcia szwu', 'Zabieg usunięcia szwu pooperacyjnego u zwierząt', 120.00, 'Zabieg'),
    (48, 'Badanie oczu', 'Badanie oczu u zwierząt', 200.00, 'Badanie laboratoryjne'),
    (49, 'Konsultacja zachowania kociąt', 'Konsultacja dotycząca zachowania kociąt', 100.00, 'Zabieg'),
    (50, 'Badanie kału na pasożyty', 'Badanie kału na obecność pasożytów u zwierząt', 150.00, 'Badanie laboratoryjne'),
    (51, 'Zabieg korekcji zgryzu', 'Zabieg korekcji zgryzu u zwierząt', 350.00, 'Zabieg'),
    (52, 'Badanie mięśni', 'Badanie ultrasonograficzne mięśni zwierząt', 300.00, 'RTG/USG'),
    (53, 'Konsultacja dotycząca odchudzania', 'Konsultacja dotycząca programu odchudzania zwierząt', 120.00, 'Zabieg'),
    (54, 'Badanie krwi na wirusy', 'Badanie krwi na obecność wirusów u zwierząt', 180.00, 'Badanie laboratoryjne'),
    (55, 'Zabieg chirurgiczny korygowania złamań', 'Zabieg chirurgiczny korygowania złamań u zwierząt', 400.00, 'Zabieg'),
    (56, 'Badanie genetyczne predyspozycji chorobowych', 'Badanie genetyczne predyspozycji chorobowych u zwierząt', 220.00, 'Badanie laboratoryjne'),
    (57, 'Konsultacja dotycząca terapii zachowań', 'Konsultacja dotycząca terapii zachowań u zwierząt', 150.00, 'Zabieg'),
    (58, 'Badanie tkanek', 'Badanie ultrasonograficzne tkanek zwierząt', 200.00, 'RTG/USG'),
    (59, 'Zabieg chirurgiczny usunięcia guza skóry', 'Zabieg chirurgiczny usunięcia guza skóry u zwierząt', 400.00, 'Zabieg'),
    (60, 'Konsultacja dotycząca opieki nad starszymi zwierzętami', 'Konsultacja dotycząca opieki nad starszymi zwierzętami', 120.00, 'Zabieg');