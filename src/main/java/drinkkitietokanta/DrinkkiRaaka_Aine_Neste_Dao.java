
package drinkkitietokanta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DrinkkiRaaka_Aine_Neste_Dao {
    private Database database;
    
    public DrinkkiRaaka_Aine_Neste_Dao(Database database){
        this.database = database;
    }
    
    public Raaka_aine_neste findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Raaka_aine_neste> listaaDrinkinAineet (Integer drinkkiId) throws SQLException, Exception {
        List<Raaka_aine_neste> lista = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM DrinkkiRaaka_aine_neste, Raaka_aine_neste WHERE DrinkkiRaaka_aine_neste.Drinkki_id = ? AND Raaka_aine_neste.raaka_aine_neste_id = Drinkkiraaka_aine_neste.raaka_aine_neste_id");
            s.setInt(1, drinkkiId);
            
            ResultSet rs = s.executeQuery();
            
            
            while(rs.next()) {
                Raaka_aine_neste ran = new Raaka_aine_neste(
                        //rs.getInt("Raaka_aine_neste.Raaka_aine_neste_id"), 
                        1,
                        rs.getString("Raaka_aine_neste.nimi"), 
                        rs.getInt("Raaka_aine_neste.maara"), 
                        rs.getDouble("Raaka_aine_neste.hinta"), 
                        rs.getDouble("Raaka_aine_neste.alkoholipitoisuus"));
                ran.setJuomaanLisattavaMaara(rs.getInt("DrinkkiRaaka_aine_neste.maara"));
                lista.add(ran);
            }
        }
        return lista;
    }

    public void saveOrUpdate(Integer drinkkiId, Integer raakaaineid, Integer maara) throws SQLException, Exception {
        poistaVanha(drinkkiId, raakaaineid);
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO DrinkkiRaaka_aine_neste (Drinkki_id, Raaka_aine_neste_id, maara) VALUES (?, ?, ?)");
            stmt.setInt(1, drinkkiId);
            stmt.setInt(2, raakaaineid);
            stmt.setInt(3, maara);
            stmt.executeUpdate();
        }
    }
    
    public void poistaVanha(Integer drinkkiId, Integer raakaaineid) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
            PreparedStatement s = conn.prepareStatement("DELETE FROM DrinkkiRaaka_Aine_Neste WHERE Drinkki_id = ? AND Raaka_aine_neste_id = ?;");
            s.setInt(1, drinkkiId);
            s.setInt(2, raakaaineid);
        }
    }
    
    public Integer laskeKayttokerrat(Integer raakaaineId) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT COUNT(*) FROM DrinkkiRaaka_aine_neste WHERE raaka_aine_neste_id = ?");
            s.setInt(1, raakaaineId);
            ResultSet rs = s.executeQuery();
            rs.next();
            return rs.getInt("count");
        }
    }
}
