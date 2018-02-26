package drinkkitietokanta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Raaka_Aine_Kiintea_Dao {

    private Database database;

    public Raaka_Aine_Kiintea_Dao(Database database) {
        this.database = database;
    }

    public Raaka_aine_kiintea findOne(Integer key) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RAAKA_AINE_KIINTEA WHERE Raaka_aine_kiintea_id = ?");
            stmt.setInt(1, key);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new Raaka_aine_kiintea(
                    result.getInt("Raaka_aine_kiintea_id"),
                    result.getString("Nimi"),
                    result.getInt("Paino"),
                    result.getDouble("Hinta"));
        }
    }

    public List<Raaka_aine_kiintea> findAll() throws SQLException, Exception {
        List<Raaka_aine_kiintea> kiinteat = new ArrayList<>();
        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT * FROM Raaka_aine_kiintea").executeQuery()) {

            while (result.next()) {
                kiinteat.add(new Raaka_aine_kiintea(result.getInt("Raaka_aine_kiintea_id"), result.getString("Nimi"), result.getInt("Paino"), result.getDouble("Hinta")));
            }
        }

        return kiinteat;
    }

    public Raaka_aine_kiintea saveOrUpdate(Raaka_aine_kiintea object) throws SQLException, Exception {
        // simply support saving -- disallow saving if task with
        // same name exists
        Raaka_aine_kiintea byName = findByName(object.getNimi());

        if (byName != null) {
            return byName;
        }

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO RAAKA_AINE_KIINTEA (Nimi, Paino, Hinta) VALUES (?, ?, ?)");
            stmt.setString(1, object.getNimi());
            stmt.setInt(2, object.getPaino());
            stmt.setDouble(3, object.getHinta());

            stmt.executeUpdate();
        }

        return findByName(object.getNimi());
    }

    private Raaka_aine_kiintea findByName(String name) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RAAKA_AINE_KIINTEA WHERE nimi = ?");
            stmt.setString(1, name);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new Raaka_aine_kiintea(
                    result.getInt("Raaka_aine_kiintea_id"),
                    result.getString("Nimi"),
                    result.getInt("Paino"),
                    result.getDouble("Hinta"));
        }
    }

    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
