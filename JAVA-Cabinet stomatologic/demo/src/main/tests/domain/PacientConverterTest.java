package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PacientConverterTest {

    @Test
    void testToString() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);

        PacientConverter converter = new PacientConverter();

        String expected = "1,Ion,Popescu,30";
        assertEquals(expected, converter.toString(pacient), "Metoda toString nu returneaza valoarea corecta!");
    }

    @Test
    void fromString() {
        String line = "1,Ion,Popescu,30";

        PacientConverter converter = new PacientConverter();

        Pacient pacient = converter.fromString(line);

        assertNotNull(pacient, "Pacientul nu a fost creat corect din string!");
        assertEquals(1, pacient.getId(), "ID-ul pacientului nu este corect!");
        assertEquals("Ion", pacient.getNume(), "Numele pacientului nu este corect!");
        assertEquals("Popescu", pacient.getPrenume(), "Prenumele pacientului nu este corect!");
        assertEquals(30, pacient.getVarsta(), "Varsta pacientului nu este corecta!");
    }
}