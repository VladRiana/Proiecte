package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProgramareConverter extends AbstractEntityConverter<Programare> {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public String toString(Programare programare) {
        return programare.getId() + "," + programare.getPacient().getId() + "," + programare.getPacient().getNume() + "," + programare.getPacient().getPrenume() + "," + programare.getPacient().getVarsta() + "," + sdf.format(programare.getData()) + "," + programare.getScopul_programarii();
    }

    public Programare fromString(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        int idPacient = Integer.parseInt(parts[1]);
        String nume = parts[2];
        String prenume = parts[3];
        int varsta = Integer.parseInt(parts[4]);
        Date data = null;
        try {
            data = sdf.parse(parts[5]);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String scopul_programarii = parts[6];
        Pacient pacient = new Pacient(idPacient, nume, prenume, varsta);
        return new Programare(id, pacient, data, scopul_programarii);
    }
}
