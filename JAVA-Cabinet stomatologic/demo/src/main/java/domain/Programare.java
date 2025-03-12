package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Programare extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Pacient pacient;
    private Date data;
    private String scopul_programarii;

    public Programare() {
        super();
    }

    public Programare(int id, Pacient pacient, Date data, String scopul_programarii) {
        super(id);
        this.pacient = pacient;
        this.data = data;
        this.scopul_programarii = scopul_programarii;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getScopul_programarii() {
        return scopul_programarii;
    }

    public void setScopul_programarii(String scopul_programarii) {
        this.scopul_programarii = scopul_programarii;
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return "Programare {" + "id=" + id + ", pacient=" + pacient.getId() + "," + pacient.getNume() + "," + pacient.getPrenume() + "," + pacient.getVarsta() + ", data=" + sdf.format(data) + ", scop=" + scopul_programarii + "}";
    }
}
