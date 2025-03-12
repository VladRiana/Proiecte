package domain;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProgramareConverterTest {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    @Test
    void testToString() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);

        Date data = null;
        try {
            data = sdf.parse("15-11-2024 10:00");
        } catch (Exception e) {
            fail("Data nu poate fi parsata");
        }

        Programare programare = new Programare(1, pacient, data, "Consultatie");

        ProgramareConverter converter = new ProgramareConverter();

        String expected = "1,1,Ion,Popescu,30,15-11-2024 10:00,Consultatie";
        assertEquals(expected, converter.toString(programare), "Metoda toString nu returneaza valoarea corecta!");
    }

    @Test
    void fromString() {
        String line = "1,1,Ion,Popescu,30,15-11-2024 10:00,Consultatie";

        ProgramareConverter converter = new ProgramareConverter();

        Programare programare = converter.fromString(line);

        assertNotNull(programare, "Programarea nu a fost creata corect din string!");
        assertEquals(1, programare.getId(), "ID-ul programarii nu este corect!");
        assertEquals(1, programare.getPacient().getId(), "ID-ul pacientului nu este corect!");
        assertEquals("Ion", programare.getPacient().getNume(), "Numele pacientului nu este corect!");
        assertEquals("Popescu", programare.getPacient().getPrenume(), "Prenumele pacientului nu este corect!");
        assertEquals(30, programare.getPacient().getVarsta(), "Varsta pacientului nu este corecta!");

        Date expectedDate = null;
        try {
            expectedDate = sdf.parse("15-11-2024 10:00");
        } catch (Exception e) {
            fail("Data nu poate fi parsata");
        }
        assertEquals(expectedDate, programare.getData(), "Data programarii nu este corecta!");
        assertEquals("Consultatie", programare.getScopul_programarii(), "Scopul programarii nu este corect!");
    }
}