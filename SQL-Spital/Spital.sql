--------------------LAB 1--------------------

create database Spital;

use Spital;

create table Pacienti(
	id_pacient int identity(1,1) primary key,
	nume varchar(50),
	prenume varchar(50),
	data_nasterii date
);

create table Medicamente(
	id_medicament int identity(1,1) primary key,
	nume_medicament varchar(50)
);

create table Prescriptie(
	id_pacient int,
	id_medicament int,
	data_prescriptie date,
	mod_administrare varchar(50),
	primary key (id_pacient, id_medicament),
	foreign key (id_pacient) references Pacienti(id_pacient)
		ON DELETE CASCADE ON UPDATE CASCADE,
	foreign key (id_medicament) references Medicamente(id_medicament)
		ON DELETE CASCADE ON UPDATE CASCADE
);

create table Sectii(
	id_sectie int identity(1,1) primary key,
	nume_sectie varchar(50)
);

create table Doctori(
	id_doctor int identity(1,1) primary key,
	nume varchar(50),
	prenume varchar(50),
	id_sectie int,
	foreign key (id_sectie) references Sectii (id_sectie)
		ON DELETE CASCADE ON UPDATE CASCADE
);

create table Consultatie(
	id_pacient int,
	id_doctor int,
	data_consultatie date,
	diagnostic varchar(50),
	primary key (id_pacient, id_doctor),
	foreign key (id_pacient) references Pacienti(id_pacient)
		ON DELETE CASCADE ON UPDATE CASCADE,
	foreign key (id_doctor) references Doctori(id_doctor)
		ON DELETE CASCADE ON UPDATE CASCADE
);

--------------------LAB 2--------------------

INSERT INTO Pacienti (nume, prenume, data_nasterii) 
VALUES ('Popescu', 'Ion', '1985-06-15'),
       ('Ionescu', 'Maria', '1990-02-10'),
       ('Vasilescu', 'Andrei', '1978-11-03'),
       ('Mihai', 'Elena', '1995-04-21'),
       ('Georgescu', 'Alex', '2000-09-12'),
       ('Dumitrescu', 'Ana', '1983-12-28'),
       ('Stan', 'George', '1975-08-19'),
       ('Petrescu', 'Daniela', '1992-07-05'),
       ('Pop', 'Vlad', '1988-01-30'),
       ('Radu', 'Simona', '1987-03-11');

INSERT INTO Medicamente (nume_medicament) 
VALUES ('Paracetamol'),
       ('Ibuprofen'),
       ('Aspirina'),
       ('Amoxicilina'),
       ('Vitamina C'),
       ('Antibiotic X'),
       ('Furosemid'),
       ('Diclofenac'),
       ('Cefalexin'),
       ('Metronidazol');

INSERT INTO Prescriptie (id_pacient, id_medicament, data_prescriptie, mod_administrare)
VALUES (1, 1, '2024-01-10', 'oral'),
       (2, 2, '2024-01-12', 'oral'),
       (3, 3, '2024-01-15', 'oral'),
       (4, 4, '2024-01-18', 'intravenos'),
       (5, 5, '2024-01-20', 'oral'),
       (6, 6, '2024-01-22', 'intramuscular'),
       (7, 7, '2024-01-25', 'oral'),
       (8, 8, '2024-01-28', 'oral'),
       (9, 9, '2024-01-30', 'oral'),
       (10, 10, '2024-02-01', 'oral');

INSERT INTO Sectii (nume_sectie) 
VALUES ('Cardiologie'),
       ('Neurologie'),
       ('Chirurgie'),
       ('Pediatrie'),
       ('Ginecologie'),
       ('Ortopedie'),
       ('Dermatologie'),
       ('Oncologie'),
       ('Oftalmologie'),
       ('Urologie');

INSERT INTO Doctori (nume, prenume, id_sectie) 
VALUES ('Popescu', 'Andrei', 1),
       ('Ionescu', 'Daniela', 2),
       ('Vasilescu', 'Cristian', 3),
       ('Mihai', 'Irina', 4),
       ('Georgescu', 'Raluca', 5),
       ('Dumitrescu', 'Alexandru', 6),
       ('Stan', 'Mihai', 7),
       ('Petrescu', 'Maria', 8),
       ('Pop', 'Ovidiu', 9),
       ('Radu', 'Carmen', 10);

INSERT INTO Consultatie (id_pacient, id_doctor, data_consultatie, diagnostic)
VALUES (1, 1, '2024-01-05', 'Hipertensiune'),
       (2, 2, '2024-01-07', 'Migrene'),
       (3, 3, '2024-01-10', 'Apendicita'),
       (4, 4, '2024-01-12', 'Raceala'),
       (5, 5, '2024-01-14', 'Anemie'),
       (6, 6, '2024-01-17', 'Infectie urinara'),
       (7, 7, '2024-01-19', 'Psoriazis'),
       (8, 8, '2024-01-21', 'Cancer pulmonar'),
       (9, 9, '2024-01-23', 'Cataracta'),
       (10, 10, '2024-01-25', 'Pietre la rinichi');

UPDATE Pacienti
SET nume = 'Popa', prenume = 'Ionel'
WHERE id_pacient = 2 AND nume = 'Ionescu' AND prenume = 'Maria';

DELETE FROM Prescriptie
WHERE data_prescriptie <= '1978-12-01';

DELETE FROM Pacienti
WHERE id_pacient = 1;

UPDATE Pacienti
SET data_nasterii = NULL
WHERE id_pacient in (1,2,3)

UPDATE Pacienti
SET nume = 'Popa', prenume = 'Ionel'
WHERE data_nasterii IS NULL;

SELECT * FROM Pacienti
WHERE data_nasterii IS NOT NULL;

select * from Pacienti
select * from Medicamente
select * from Prescriptie
select * from Sectii
select * from Doctori
select * from Consultatie

--------------------LAB 3--------------------

SELECT nume, prenume FROM Pacienti
UNION
SELECT nume, prenume FROM Doctori

SELECT id_medicament, nume_medicament
FROM Medicamente
EXCEPT
SELECT M.id_medicament, M.nume_medicament
FROM Medicamente M
JOIN Prescriptie P ON M.id_medicament = P.id_medicament;

select * from Pacienti
select * from Doctori
select * from Consultatie

SELECT P.nume AS Nume_Pacient, 
       P.prenume AS Prenume_Pacient, 
       D.nume AS Nume_Doctor, 
       D.prenume AS Prenume_Doctor, 
       C.data_consultatie AS Data_Consultatie, 
       C.diagnostic AS Diagnostic
FROM Pacienti P
INNER JOIN Consultatie C ON P.id_pacient = C.id_pacient
INNER JOIN Doctori D ON C.id_doctor = D.id_doctor;

SELECT DISTINCT P.nume AS Nume_Pacient, 
                P.prenume AS Prenume_Pacient, 
                D.nume AS Nume_Doctor, 
                D.prenume AS Prenume_Doctor
FROM Pacienti P
INNER JOIN Consultatie C ON P.id_pacient = C.id_pacient
INNER JOIN Doctori D ON C.id_doctor = D.id_doctor;

select * from Pacienti
select * from Medicamente
select * from Prescriptie

SELECT P.nume AS Nume_Pacient, 
       P.prenume AS Prenume_Pacient, 
       M.nume_medicament AS Medicament_Prescris
FROM Prescriptie PS
RIGHT JOIN Pacienti P ON PS.id_pacient = P.id_pacient
RIGHT JOIN Medicamente M ON PS.id_medicament = M.id_medicament
WHERE M.nume_medicament in ('Paracetamol', 'Ibuprofen', 'Vitamina C')

INSERT INTO Consultatie (id_pacient, id_doctor, data_consultatie, diagnostic)
VALUES (2, 3, '2024-02-07', 'Migrene'),
       (3, 4, '2024-02-10', 'Apendicita')

SELECT D.nume AS Nume_Doctor, 
       D.prenume AS Prenume_Doctor, 
       COUNT(C.id_pacient) AS Numar_Consultatii
FROM Doctori D
INNER JOIN Consultatie C ON D.id_doctor = C.id_doctor
GROUP BY D.nume, D.prenume
HAVING COUNT(C.id_pacient) > 1;

INSERT INTO Consultatie (id_pacient, id_doctor, data_consultatie, diagnostic)
VALUES (2, 5, '2024-03-07', 'Migrene'),
       (3, 2, '2024-03-10', 'Apendicita')

select * from Consultatie

SELECT D.nume AS Nume_Doctor, 
       D.prenume AS Prenume_Doctor, 
       MIN(C.data_consultatie) AS Prima_Consultatie, 
       MAX(C.data_consultatie) AS Ultima_Consultatie
FROM Doctori D
INNER JOIN Consultatie C ON D.id_doctor = C.id_doctor
GROUP BY D.nume, D.prenume;

select * from Doctori

INSERT INTO Doctori (nume, prenume, id_sectie) 
VALUES ('Popa', 'Marian', 1),
       ('Grigore', 'Ana', 2),
       ('Pop', 'Alin', 3)

SELECT S.nume_sectie AS Nume_Sectie, 
       COUNT(D.id_doctor) AS Numar_Doctori
FROM Sectii S
JOIN Doctori D ON S.id_sectie = D.id_sectie
GROUP BY S.nume_sectie;

select * from Pacienti
select * from Medicamente
select * from Prescriptie
select * from Sectii
select * from Doctori
select * from Consultatie 

--------------------LAB 4--------------------

CREATE FUNCTION dbo.ValidareData(@data DATE)
RETURNS BIT
AS
BEGIN
    RETURN CASE 
        WHEN @data <= GETDATE() THEN 1
        ELSE 0
    END
END;

CREATE FUNCTION dbo.ValidareNume(@nume VARCHAR(50))
RETURNS BIT
AS
BEGIN
    RETURN CASE 
        WHEN LEN(@nume) > 0 AND LEN(@nume) <= 50 THEN 1
        ELSE 0
    END
END;

CREATE FUNCTION dbo.ValidareModAdministrare(@mod VARCHAR(50))
RETURNS BIT
AS
BEGIN
    RETURN CASE 
        WHEN @mod IN ('oral', 'intravenos', 'intramuscular') THEN 1
        ELSE 0
    END
END;

CREATE PROCEDURE AdaugaPacient
    @nume VARCHAR(50),
    @prenume VARCHAR(50),
    @data_nasterii DATE
AS
BEGIN
    IF dbo.ValidareNume(@nume) = 1 AND dbo.ValidareNume(@prenume) = 1 AND dbo.ValidareData(@data_nasterii) = 1
    BEGIN
        INSERT INTO Pacienti (nume, prenume, data_nasterii)
        VALUES (@nume, @prenume, @data_nasterii);
        PRINT 'Pacient adaugat cu succes!';
    END
    ELSE
    BEGIN
        PRINT 'Parametri invalizi!';
    END
END;

select * from Pacienti

EXEC AdaugaPacient @nume = 'Vasilescu', @prenume = 'Ionel', @data_nasterii = '1985-06-15';

delete from Pacienti
where id_pacient = 13

EXEC AdaugaPacient @nume = 'Vasilescu', @prenume = 'Ion', @data_nasterii = '2025-06-15';

CREATE PROCEDURE AdaugaMedicament
    @nume_medicament VARCHAR(50)
AS
BEGIN
    IF dbo.ValidareNume(@nume_medicament) = 1
    BEGIN
        INSERT INTO Medicamente (nume_medicament)
        VALUES (@nume_medicament);
        PRINT 'Medicament adaugat cu succes!';
    END
    ELSE
    BEGIN
        PRINT 'Numele medicamentului este invalid!';
    END
END;

select * from Medicamente

EXEC AdaugaMedicament @nume_medicament = 'Vitamina D';

CREATE PROCEDURE AdaugaPrescriptie
    @id_pacient INT,
    @id_medicament INT,
    @data_prescriptie DATE,
    @mod_administrare VARCHAR(50)
AS
BEGIN
    IF dbo.ValidareData(@data_prescriptie) = 1 AND dbo.ValidareModAdministrare(@mod_administrare) = 1
    BEGIN
        INSERT INTO Prescriptie (id_pacient, id_medicament, data_prescriptie, mod_administrare)
        VALUES (@id_pacient, @id_medicament, @data_prescriptie, @mod_administrare);
        PRINT 'Prescriptie adaugata cu succes!';
    END
    ELSE
    BEGIN
        PRINT 'Parametri invalizi!';
    END
END;

select * from Prescriptie

EXEC AdaugaPrescriptie @id_pacient = 11, @id_medicament = 11, @data_prescriptie = '2024-10-13', @mod_administrare = 'oral';
EXEC AdaugaPrescriptie @id_pacient = 11, @id_medicament = 11, @data_prescriptie = '2025-10-13', @mod_administrare = 'orar';

CREATE VIEW VW_ConsultatiiPacientiDoctori AS
SELECT 
    P.nume AS Nume_Pacient, 
    P.prenume AS Prenume_Pacient, 
    D.nume AS Nume_Doctor, 
    D.prenume AS Prenume_Doctor, 
    C.data_consultatie, 
    C.diagnostic
FROM Pacienti P
INNER JOIN Consultatie C ON P.id_pacient = C.id_pacient
INNER JOIN Doctori D ON C.id_doctor = D.id_doctor;

select * from VW_ConsultatiiPacientiDoctori

CREATE TRIGGER trg_InsertPacient
ON Pacienti
AFTER INSERT
AS
BEGIN
    DECLARE @DataOra DATETIME = GETDATE();
    PRINT 'Operatie: INSERT in tabelul Pacienti la ' + CAST(@DataOra AS VARCHAR) + '.';
END;

CREATE TRIGGER trg_DeletePacient
ON Pacienti
AFTER DELETE
AS
BEGIN
    DECLARE @DataOra DATETIME = GETDATE();
    PRINT 'Operatie: DELETE in tabelul Pacienti la ' + CAST(@DataOra AS VARCHAR) + '.';
END;

INSERT INTO Pacienti (nume, prenume, data_nasterii) 
VALUES ('Popescu', 'Ion', '1980-01-01');

DELETE FROM Pacienti 
WHERE nume = 'Popescu' AND prenume = 'Ion';

select * from Pacienti
