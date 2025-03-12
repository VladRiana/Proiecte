package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PacientTest {

    @Test
    void testConstructor() {
        Pacient pacient = new Pacient();

        assertNotNull(pacient, "Pacientul nu trebuie sa fie null");
        assertEquals(0, pacient.getId(), "Id-ul pacientului trebuie sa fie 0");

    }

    @Test
    void getNume() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        assertEquals("Ion", pacient.getNume(), "Numele nu este corect!");
    }

    @Test
    void setNume() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        pacient.setNume("Alex");
        assertEquals("Alex", pacient.getNume(), "Numele nu a fost setat corect!");
    }

    @Test
    void getPrenume() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        assertEquals("Popescu", pacient.getPrenume(), "Prenumele este corect!");
    }

    @Test
    void setPrenume() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        pacient.setPrenume("Ionescu");
        assertEquals("Ionescu", pacient.getPrenume(), "Prenumele nu a fost setat corect!");
    }

    @Test
    void getVarsta() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        assertEquals(30, pacient.getVarsta(), "Varsta nu este corecta!");
    }

    @Test
    void setVarsta() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        pacient.setVarsta(35);
        assertEquals(35, pacient.getVarsta(), "Varsta nu a fost setata corect!");
    }

    @Test
    void testToString() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        String expectedToString = "Pacient {id=1, nume=Ion, prenume=Popescu, varsta=30}";
        assertEquals(expectedToString, pacient.toString(), "Metoda toString nu returnează valoarea corecta!");
    }
}