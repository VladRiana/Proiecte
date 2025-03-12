package domain;

public class PacientConverter extends AbstractEntityConverter<Pacient> {

    public String toString(Pacient pacient) {
        return pacient.getId() + "," + pacient.getNume() + "," + pacient.getPrenume() + "," + pacient.getVarsta();
    }

    public Pacient fromString(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String nume = parts[1];
        String prenume = parts[2];
        int varsta = Integer.parseInt(parts[3]);
        return new Pacient(id, nume, prenume, varsta);
    }
}
