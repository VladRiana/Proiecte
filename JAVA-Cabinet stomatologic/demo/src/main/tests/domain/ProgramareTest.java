package domain;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProgramareTest {

    @Test
    void testConstructor() {
        Programare programare = new Programare();

        assertNotNull(programare, "Programarea nu ar trebui sa fie null!");
        assertEquals(0, programare.getId(), "Id-ul programarii trebuie sa fie 0!");
    }

    @Test
    void getPacient() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        Date data = new Date();
        Programare programare = new Programare(1, pacient, data, "Consultatie");
        assertEquals(pacient, programare.getPacient(), "Pacientul nu este corect!");
    }

    @Test
    void setPacient() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        Date data = new Date();
        Programare programare = new Programare(1, pacient, data, "Consultatie");
        Pacient pacientNou = new Pacient(2, "Maria", "Ionescu", 25);
        programare.setPacient(pacientNou);
        assertEquals(pacientNou, programare.getPacient(), "Pacientul nu a fost setat corect!");
    }

    @Test
    void getData() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        Date data = new Date();
        Programare programare = new Programare(1, pacient, data, "Consultatie");
        assertEquals(data, programare.getData(), "Data nu este corecta!");
    }

    @Test
    void setData() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        Date dataVeche = new Date();
        Programare programare = new Programare(1, pacient, dataVeche, "Consultatie");
        Date dataNoua = new Date(System.currentTimeMillis() + 10000); // data cu 10 secunde mai târziu
        programare.setData(dataNoua);
        assertEquals(dataNoua, programare.getData(), "Data nu a fost setata corect!");
    }

    @Test
    void getScopul_programarii() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        Date data = new Date();
        Programare programare = new Programare(1, pacient, data, "Consultatie");
        assertEquals("Consultatie", programare.getScopul_programarii(), "Scopul programarii nu este corect!");
    }

    @Test
    void setScopul_programarii() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        Date data = new Date();
        Programare programare = new Programare(1, pacient, data, "Consultatie");
        programare.setScopul_programarii("Tratament");
        assertEquals("Tratament", programare.getScopul_programarii(), "Scopul programarii nu a fost setat corect!");
    }

    @Test
    void testToString() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        Date data = new Date();
        Programare programare = new Programare(1, pacient, data, "Consultatie");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String expectedToString = "Programare {id=1, pacient=1,Ion,Popescu,30, data=" + sdf.format(data) + ", scop=Consultatie}";

        assertEquals(expectedToString, programare.toString(), "Metoda toString nu returneaza valoarea corecta!");
    }
}