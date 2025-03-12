package repository;

import domain.Pacient;
import domain.Programare;
import exceptions.RepositoryException;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class SQLProgramareRepository extends Repository<Programare> implements AutoCloseable {

    private static final String JDBC_URL = "jdbc:sqlite:C:/Users/Asus/IdeaProjects/demo/src/main/java/repository/cabinet_stomatologic.db";
    private Connection conn;

    public SQLProgramareRepository() {
        openConnection();
        createSchema();
        loadData();
    }

    private void loadData() {
        String query = """
        SELECT p.id AS pacient_id, p.nume, p.prenume, p.varsta,
               pr.id AS programare_id, pr.data_programare, pr.scopul_programarii
        FROM programari pr
        INNER JOIN pacienti p ON pr.pacient_id = p.id
    """;

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            while (rs.next()) {
                Pacient pacient = new Pacient(
                        rs.getInt("pacient_id"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getInt("varsta")
                );

                String data = rs.getString("data_programare");
                Date dataProgramare = sdf.parse(data);
                Timestamp sqlTimestamp = new Timestamp(dataProgramare.getTime());

                Programare programare = new Programare(
                        rs.getInt("programare_id"),
                        pacient,
                        sqlTimestamp,
                        rs.getString("scopul_programarii")
                );

                items.add(programare);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void createSchema() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS programari(id int PRIMARY KEY, pacient_id int, data_programare timestamp, scopul_programarii varchar(50), FOREIGN KEY (pacient_id) REFERENCES pacienti(id) on delete cascade on update cascade);"
            );
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    private void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la conectarea cu baza de date", e);
        }
    }

    @Override
    public void add(Programare programare) throws RepositoryException {
        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO programari VALUES (?, ?, ?, ?)")) {
            statement.setInt(1, programare.getId());
            statement.setInt(2, programare.getPacient().getId());

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String data = sdf.format(programare.getData());
            statement.setString(3, data);

            statement.setString(4, programare.getScopul_programarii());
            statement.executeUpdate();

            super.add(programare);

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed") || e.getErrorCode() == 19) {
                throw new RepositoryException("ID-ul " + programare.getId() + " exista deja in baza de date.", e);
            }
            if (e.getMessage().contains("FOREIGN KEY constraint failed")) {
                throw new RepositoryException("Eroare: Programarea nu poate fi adaugata deoarece pacientul nu exista in baza de date.", e);
            }
            throw new RepositoryException("Eroare la salvarea in baza de date", e);
        }
    }

    @Override
    public void remove(int id) throws RepositoryException {
        super.remove(id);

        try (PreparedStatement statement = conn.prepareStatement("DELETE FROM programari WHERE id = ?")) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new RepositoryException("Nu a fost gasita o programare cu id-ul: " + id);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Eroare la stergerea programarii", e);
        }
    }

    @Override
    public void update(Programare programare) throws RepositoryException {
        try (PreparedStatement statement = conn.prepareStatement(
                "UPDATE programari SET pacient_id = ?, data_programare = ?, scopul_programarii = ? WHERE id = ?")) {
            statement.setInt(1, programare.getPacient().getId());

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String data = sdf.format(programare.getData());
            statement.setString(2, data);

            statement.setString(3, programare.getScopul_programarii());
            statement.setInt(4, programare.getId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RepositoryException("Nu a fost gasita o programare cu id-ul: " + programare.getId());
            }
        } catch (SQLException e) {
            throw new RepositoryException("Eroare la actualizarea programarii cu id-ul " + programare.getId(), e);
        }

        super.update(programare);
    }

//    @Override
//    public Programare findById(int id) {
//        String query = """
//    SELECT p.id AS pacient_id, p.nume, p.prenume, p.varsta,
//           pr.id AS programare_id, pr.data_programare, pr.scopul_programarii
//    FROM programari pr
//    INNER JOIN pacienti p ON pr.pacient_id = p.id
//    WHERE pr.id = ?
//    """;
//
//        try (PreparedStatement statement = conn.prepareStatement(query)) {
//            statement.setInt(1, id);
//            ResultSet rs = statement.executeQuery();
//            if (rs.next()) {
//                Pacient pacient = new Pacient(
//                        rs.getInt("pacient_id"),
//                        rs.getString("nume"),
//                        rs.getString("prenume"),
//                        rs.getInt("varsta")
//                );
//
//                return new Programare(
//                        rs.getInt("programare_id"),
//                        pacient,
//                        rs.getTimestamp("data_programare"),
//                        rs.getString("scopul_programarii")
//                );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Nu a fost gasita o programare cu id-ul: " + id, e);
//        }
//        return null;
//    }

    @Override
    public Collection<Programare> getAll() {
        var programariList = new ArrayList<Programare>();
        String query = """
    SELECT p.id AS pacient_id, p.nume, p.prenume, p.varsta,
           pr.id AS programare_id, pr.data_programare, pr.scopul_programarii
    FROM programari pr
    INNER JOIN pacienti p ON pr.pacient_id = p.id
    """;

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            while (rs.next()) {
                Pacient pacient = new Pacient(
                        rs.getInt("pacient_id"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getInt("varsta")
                );

                String data = rs.getString("data_programare");
                Date dataProgramare = sdf.parse(data);
                Timestamp sqlTimestamp = new Timestamp(dataProgramare.getTime());

                Programare programare = new Programare(
                        rs.getInt("programare_id"),
                        pacient,
                        sqlTimestamp,
                        rs.getString("scopul_programarii")
                );

                programariList.add(programare);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return programariList;
    }

    @Override
    public void close() throws Exception {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
