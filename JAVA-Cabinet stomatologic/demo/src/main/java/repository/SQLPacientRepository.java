package repository;

import domain.Pacient;
import exceptions.RepositoryException;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class SQLPacientRepository extends Repository<Pacient> implements AutoCloseable{

    private static final String JDBC_URL = "jdbc:sqlite:C:/Users/Asus/IdeaProjects/demo/src/main/java/repository/cabinet_stomatologic.db";
    private Connection conn;

    public SQLPacientRepository() {
        openConnection();
        createSchema();
        loadData();
    }

    private void loadData() {
        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * from pacienti");
                 ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    Pacient pacient = new Pacient(rs.getInt("id"),
                            rs.getString("nume"),
                            rs.getString("prenume"),
                            rs.getInt("varsta"));
                    items.add(pacient);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void createSchema() {
        try {
            try (final Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS pacienti(id int PRIMARY KEY, nume varchar(50), prenume varchar(50), varsta int);");
            }
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
    public void add(Pacient pacient) throws RepositoryException {
        super.add(pacient);

        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO pacienti VALUES (?, ?, ?, ?)")) {
                statement.setInt(1, pacient.getId());
                statement.setString(2, pacient.getNume());
                statement.setString(3, pacient.getPrenume());
                statement.setInt(4, pacient.getVarsta());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed") || e.getErrorCode() == 19) {
                throw new RepositoryException("Eroare: ID-ul " + pacient.getId() + " exista deja in baza de date.", e);
            }
            throw new RepositoryException("Eroare la adaugarea in baza de date", e);
        }
    }

    @Override
    public void remove(int id) throws RepositoryException {
        super.remove(id);

        try (var statement = conn.prepareStatement("DELETE FROM pacienti WHERE id = (?)")) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new RepositoryException("Nu a fost gasit un pacient cu id-ul: " + id);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Eroare la stergerea pacientului: " + e.getMessage());
        }
    }

    @Override
    public void update(Pacient pacient) throws RepositoryException {
        try (PreparedStatement statement = conn.prepareStatement(
                "UPDATE pacienti SET nume = ?, prenume = ?, varsta = ? WHERE id = ?")) {
            statement.setString(1, pacient.getNume());
            statement.setString(2, pacient.getPrenume());
            statement.setInt(3, pacient.getVarsta());
            statement.setInt(4, pacient.getId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RepositoryException("Nu a fost gasit un pacient cu id-ul: " + pacient.getId());
            }
        } catch (SQLException e) {
            throw new RepositoryException("Eroare la actualizarea pacientului cu id-ul " + pacient.getId(), e);
        }

        super.update(pacient);
    }

    @Override
    public Pacient findById(int id) {
        try (var statement = conn.prepareStatement("SELECT * FROM pacienti WHERE id = (?)")) {
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var pacient_id = resultSet.getInt(1);
                var pacient_nume = resultSet.getString(2);
                var pacient_prenume = resultSet.getString(3);
                var pacient_varsta = resultSet.getInt(4);
                return new Pacient(pacient_id, pacient_nume, pacient_prenume, pacient_varsta);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Nu a fost gasit un pacient cu id-ul: " + id);
        }
        return null;
    }

    @Override
    public Collection<Pacient> getAll() {
        var pacientiList = new ArrayList<Pacient>();
        try (var select = conn.prepareStatement("SELECT * FROM pacienti")) {

            ResultSet resultSet = select.executeQuery();
            while (resultSet.next() == true) {
                var pacient_id = resultSet.getInt(1);
                var pacient_nume = resultSet.getString(2);
                var pacient_prenume = resultSet.getString(3);
                var pacient_varsta = resultSet.getInt(4);
                pacientiList.add(new Pacient(pacient_id, pacient_nume, pacient_prenume, pacient_varsta));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pacientiList;
    }

    @Override
    public void close() throws Exception {
        try {
            if (conn != null && !conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
