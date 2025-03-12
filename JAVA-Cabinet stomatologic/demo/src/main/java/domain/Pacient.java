package domain;

import java.io.Serializable;
import java.util.Objects;

public class Pacient extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nume;
    private String prenume;
    private int varsta;

    public Pacient() {
        super();
    }

    public Pacient(int id, String nume, String prenume, int varsta) {
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public String toString() {
        return "Pacient {" + "id=" + id + ", nume=" + nume + ", prenume=" + prenume + ", varsta=" + varsta + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pacient pacient = (Pacient) o;
        return id == pacient.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
