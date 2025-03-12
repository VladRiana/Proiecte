package service;

import domain.Pacient;
import domain.Programare;
import exceptions.DuplicateIDException;
import exceptions.ObjectNotFoundException;
import exceptions.RepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ProgramareServiceTest {

    private Repository<Programare> programareRepository;
    private ProgramareService programareService;

    @BeforeEach
    void setUp() {
        programareRepository = new Repository<>();
        programareService = new ProgramareService(programareRepository);
    }

    @Test
    void seSuprapune() throws RepositoryException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        Programare programare = new Programare(1, new Pacient(1, "Ion", "Popescu", 30), sdf.parse("15-11-2024 14:00"), "Consultatie");
        programareService.addProgramare(programare);

        Programare programareNoua = new Programare(2, new Pacient(2, "Maria", "Ionescu", 25), sdf.parse("15-11-2024 14:30"), "Control");
        assertTrue(programareService.seSuprapune(programareNoua), "Programarea ar trebui sa se suprapuna.");
    }

    @Test
    void addProgramare() throws RepositoryException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        Programare programare1 = new Programare(1, new Pacient(1, "Ion", "Popescu", 30), sdf.parse("15-11-2024 14:00"), "Consultatie");
        Programare programare2 = new Programare(1, new Pacient(2, "Maria", "Ionescu", 25), sdf.parse("15-11-2024 15:00"), "Control");
        Programare programare3 = new Programare(2, new Pacient(3, "Vasile", "Georgescu", 40), sdf.parse("15-11-2024 14:30"), "Extractie");
        programareService.addProgramare(programare1);

        Programare result = programareService.getProgramareById(1);
        assertEquals(programare1, result, "Programarea nu a fost adaugata corect.");

        try {
            programareService.addProgramare(programare2);
            fail("Ar trebui sa fie aruncata exceptia pentru ID duplicat.");
        } catch (DuplicateIDException e) {
            assertEquals("Exista deja un element cu acest id!", e.getMessage());
        }

        boolean suprapunere = programareService.addProgramare(programare3);
        assertFalse(suprapunere, "Programarea nu ar trebui sa se adauge din cauza unei suprapuneri.");
    }

    @Test
    void removeProgramare() throws RepositoryException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        Programare programare = new Programare(1, new Pacient(1, "Ion", "Popescu", 30), sdf.parse("15-11-2024 14:00"), "Consultatie");
        programareService.addProgramare(programare);
        programareService.removeProgramare(1);

        try {
            programareService.removeProgramare(1);
            fail("Ar trebui să fie aruncata exceptia ca nu exista o programare cu acest ID.");
        } catch (ObjectNotFoundException e) {
            assertEquals("Nu exista un element cu id-ul dat!", e.getMessage());
        }
    }

    @Test
    void updateProgramare() throws RepositoryException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        Programare programare = new Programare(1, new Pacient(1, "Ion", "Popescu", 30), sdf.parse("15-11-2024 14:00"), "Consultatie");
        programareService.addProgramare(programare);

        programare.setScopul_programarii("Control");
        programareService.updateProgramare(programare);

        Programare result = programareService.getProgramareById(1);
        assertEquals("Control", result.getScopul_programarii(), "Scopul programarii nu a fost actualizat corect.");

        Programare programare2 = new Programare(2, new Pacient(2, "Maria", "Ionescu", 25), sdf.parse("15-11-2024 15:00"), "Control");
        try {
            programareService.updateProgramare(programare2);
            fail("Ar trebui sa fie aruncata exceptia ca nu exista o programare cu acest ID.");
        } catch (ObjectNotFoundException e) {
            assertEquals("Nu exista un element cu id-ul dat!", e.getMessage());
        }
    }

    @Test
    void getProgramareById() throws RepositoryException, ParseException {
        try {
            programareService.getProgramareById(1);
            fail("Ar trebui sa fie aruncata excepția ca nu exista o programare cu acest ID.");
        } catch (ObjectNotFoundException e) {
            assertEquals("Nu exista nicio programare cu acest id!", e.getMessage());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        Programare programare = new Programare(1, new Pacient(1, "Ion", "Popescu", 30), sdf.parse("15-11-2024 14:00"), "Consultatie");
        programareService.addProgramare(programare);

        Programare result = programareService.getProgramareById(1);
        assertEquals(programare, result, "Programarea nu a fost găsită corect.");
    }

    @Test
    void getAllProgramari() throws RepositoryException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        java.util.Collection<Programare> programari = programareService.getAllProgramari();
        assertTrue(programari.isEmpty(), "Colectia de programari nu este goala.");

        Programare programare1 = new Programare(1, new Pacient(1, "Ion", "Popescu", 30), sdf.parse("15-11-2024 14:00"), "Consultatie");
        Programare programare2 = new Programare(2, new Pacient(2, "Maria", "Ionescu", 25), sdf.parse("15-11-2024 15:00"), "Control");
        programareService.addProgramare(programare1);
        programareService.addProgramare(programare2);

        Collection<Programare> programari2 = programareService.getAllProgramari();
        assertEquals(2, programari2.size(), "Numarul programarilor returnate nu este corect.");
    }
}