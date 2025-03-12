package repository;

import domain.Pacient;
import domain.PacientConverter;
import domain.Programare;
import domain.ProgramareConverter;
import exceptions.RepositoryException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TextFileRepositoryTest {

    private String testFilePath;
    private TextFileRepository<Pacient> pacientRepository;
    private TextFileRepository<Programare> programareRepository;

    @BeforeEach
    void setUp() throws RepositoryException {
        testFilePath = "C:\\Users\\Asus\\IdeaProjects\\a3-VladRiana\\test_repository.txt";
        pacientRepository = new TextFileRepository<>(testFilePath, new PacientConverter());
        programareRepository = new TextFileRepository<>(testFilePath, new ProgramareConverter());
    }

    @AfterEach
    void tearDown() {
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void loadData() throws RepositoryException {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        pacientRepository.add(pacient);

        pacientRepository = new TextFileRepository<>(testFilePath, new PacientConverter());
        Pacient loadedPacient = pacientRepository.findById(1);
        assertNotNull(loadedPacient);
        assertEquals("Ion", loadedPacient.getNume(), "Pacientul nu a fost incarcat corect.");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date dataProgramare = null;
        try {
            dataProgramare = sdf.parse("12-10-2024 12:00");
        } catch (Exception e) {
            fail("Data nu a putut fi parsata.");
        }
        Programare programare = new Programare(1, pacient, dataProgramare, "Consultatie");
        programareRepository.add(programare);

        programareRepository = new TextFileRepository<>(testFilePath, new ProgramareConverter());
        Programare loadedProgramare = programareRepository.findById(1);
        assertNotNull(loadedProgramare);
        assertEquals("Consultatie", loadedProgramare.getScopul_programarii(), "Programarea nu a fost incarcata corect.");
    }

    @Test
    void saveData() throws RepositoryException {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        pacientRepository.add(pacient);
        pacientRepository.saveData();

        File file = new File(testFilePath);
        assertTrue(file.exists(), "Fisierul nu a fost creat.");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date dataProgramare = null;
        try {
            dataProgramare = sdf.parse("12-10-2024 12:00");
        } catch (Exception e) {
            fail("Data nu a putut fi parsata.");
        }
        Programare programare = new Programare(1, pacient, dataProgramare, "Consultatie");
        programareRepository.add(programare);
        programareRepository.saveData();

        assertTrue(file.exists(), "Fisierul nu a fost creat.");
    }

    @Test
    void add() throws RepositoryException {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        pacientRepository.add(pacient);
        Pacient foundPacient = pacientRepository.findById(1);
        assertNotNull(foundPacient, "Pacientul nu a fost adaugat.");
        assertEquals("Ion", foundPacient.getNume(), "Numele pacientului nu este corect.");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date dataProgramare = null;
        try {
            dataProgramare = sdf.parse("12-10-2024 12:00");
        } catch (Exception e) {
            fail("Data nu a putut fi parsata.");
        }
        Programare programare = new Programare(1, pacient, dataProgramare, "Consultatie");
        programareRepository.add(programare);
        Programare foundProgramare = programareRepository.findById(1);
        assertNotNull(foundProgramare, "Programarea nu a fost adaugata.");
        assertEquals("Consultatie", foundProgramare.getScopul_programarii(), "Scopul programarii nu este corect.");
    }

    @Test
    void remove() throws RepositoryException {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        pacientRepository.add(pacient);
        pacientRepository.remove(1);

        Pacient foundPacient = pacientRepository.findById(1);
        assertNull(foundPacient, "Pacientul nu a fost sters corect.");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date dataProgramare = null;
        try {
            dataProgramare = sdf.parse("12-10-2024 12:00");
        } catch (Exception e) {
            fail("Data nu a putut fi parsata.");
        }
        Programare programare = new Programare(1, pacient, dataProgramare, "Consultatie");
        programareRepository.add(programare);
        programareRepository.remove(1);

        Programare foundProgramare = programareRepository.findById(1);
        assertNull(foundProgramare, "Programarea nu a fost ștearsa corect.");
    }

    @Test
    void update() throws RepositoryException {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        pacientRepository.add(pacient);
        pacient.setNume("Alex");
        pacientRepository.update(pacient);

        Pacient updatedPacient = pacientRepository.findById(1);
        assertNotNull(updatedPacient, "Pacientul nu a fost actualizat.");
        assertEquals("Alex", updatedPacient.getNume(), "Numele pacientului actualizat nu este corect.");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date dataProgramare = null;
        try {
            dataProgramare = sdf.parse("12-10-2024 12:00");
        } catch (Exception e) {
            fail("Data nu a putut fi parsata.");
        }
        Programare programare = new Programare(1, pacient, dataProgramare, "Consultatie");
        programareRepository.add(programare);
        programare.setScopul_programarii("Control");
        programareRepository.update(programare);

        Programare updatedProgramare = programareRepository.findById(1);
        assertNotNull(updatedProgramare, "Programarea nu a fost actualizata.");
        assertEquals("Control", updatedProgramare.getScopul_programarii(), "Scopul programarii actualizate nu este corect.");
    }
}
