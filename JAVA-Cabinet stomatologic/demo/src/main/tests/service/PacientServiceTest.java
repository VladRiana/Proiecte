package service;

import domain.Pacient;
import exceptions.DuplicateIDException;
import exceptions.ObjectNotFoundException;
import exceptions.RepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.Repository;

import static org.junit.jupiter.api.Assertions.*;

class PacientServiceTest {

    private Repository<Pacient> pacientRepository;
    private PacientService pacientService;

    @BeforeEach
    void setUp() {
        pacientRepository = new Repository<>();
        pacientService = new PacientService(pacientRepository);
    }

    @Test
    void addPacient() throws RepositoryException {
        Pacient pacient1 = new Pacient(1, "Ion", "Popescu", 30);
        Pacient pacient2 = new Pacient(1, "Maria", "Ionescu", 25);

        pacientService.addPacient(pacient1);

        Pacient result = pacientService.getPacientById(1);
        assertEquals(pacient1, result, "Pacientul nu a fost adaugat corect.");

        try {
            pacientService.addPacient(pacient2);
            fail("Ar trebui sa fie aruncata exceptia pentru ID duplicat.");
        } catch (DuplicateIDException e) {
            assertEquals("Exista deja un element cu acest id!", e.getMessage());
        }
    }

    @Test
    void removePacient() throws RepositoryException {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        pacientService.addPacient(pacient);

        pacientService.removePacient(1);

        try {
            pacientService.removePacient(1);
            fail("Ar trebui să fie aruncata exceptia ca nu exista un pacient cu acest ID.");
        } catch (ObjectNotFoundException e) {
            assertEquals("Nu exista un element cu id-ul dat!", e.getMessage());
        }
    }

    @Test
    void updatePacient() throws RepositoryException {
        Pacient pacient1 = new Pacient(1, "Ion", "Popescu", 30);
        pacientService.addPacient(pacient1);

        pacient1.setVarsta(31);
        pacientService.updatePacient(pacient1);

        Pacient result = pacientService.getPacientById(1);
        assertEquals(31, result.getVarsta(), "Varsta pacientului nu a fost actualizata corect.");

        Pacient pacient2 = new Pacient(2, "Maria", "Ionescu", 25);
        try {
            pacientService.updatePacient(pacient2);
            fail("Ar trebui sa fie aruncata excepția ca nu exista un pacient cu acest ID.");
        } catch (ObjectNotFoundException e) {
            assertEquals("Nu exista un element cu id-ul dat!", e.getMessage());
        }
    }

    @Test
    void getPacientById() throws RepositoryException {
        try {
            pacientService.getPacientById(1);
            fail("Ar trebui sa fie aruncata excepția ca nu exista un pacient cu acest ID.");
        } catch (ObjectNotFoundException e) {
            assertEquals("Nu exista niciun pacient cu acest id!", e.getMessage());
        }

        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        pacientService.addPacient(pacient);

        Pacient result = pacientService.getPacientById(1);
        assertEquals(pacient, result, "Pacientul nu a fost gasit corect.");
    }

    @Test
    void getAllPacienti() throws RepositoryException {
        java.util.Collection<Pacient> pacienti = pacientService.getAllPacienti();
        assertTrue(pacienti.isEmpty(), "Colectia de pacienti nu este goala.");

        Pacient pacient1 = new Pacient(1, "Ion", "Popescu", 30);
        Pacient pacient2 = new Pacient(2, "Maria", "Ionescu", 25);

        pacientService.addPacient(pacient1);
        pacientService.addPacient(pacient2);

        java.util.Collection<Pacient> pacienti2 = pacientService.getAllPacienti();
        assertEquals(2, pacienti2.size(), "Numarul pacientilor nu este corect.");
    }
}