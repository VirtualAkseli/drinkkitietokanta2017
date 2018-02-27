package drinkkitietokanta;

import java.util.List;


public class Drinkki {
    public int drinkki_id;
    private String nimi;
    private String lasityyppi;
    private String resepti;
    private Double hinta;
    private Double alkoholiprosentti;

    public Drinkki(int drinkki_id, String nimi, String lasityyppi, String resepti) {
        this.drinkki_id = drinkki_id;
        this.nimi = nimi;
        this.lasityyppi = lasityyppi;
        this.resepti = resepti;
        hinta = 0d;
        alkoholiprosentti = 0d;
    }
    
    public void laske(DrinkkiRaaka_Aine_Neste_Dao dao) throws Exception{
        List<Raaka_aine_neste> lista = dao.listaaDrinkinAineet(drinkki_id);
        double alkoholi = 0;
        double yhteensa = 0;
        double summa = 0;
        
        for(Raaka_aine_neste raakaAine:lista){
            yhteensa += raakaAine.getMaara();
            alkoholi += raakaAine.getAlkoholipitoisuus()*raakaAine.getMaara();
            
            double price = raakaAine.getJuomaanLisattavaMaara() * (raakaAine.getHinta()/100);
            summa += price;
        }
        
        alkoholiprosentti = alkoholi / yhteensa;
        hinta = summa;
    }

    public int getDrinkki_id() {
        return drinkki_id;
    }

    public Double getAlkoholiprosentti() {
        return alkoholiprosentti;
    }

    public Double getHinta() {
        return hinta;
    }

    public String getLasityyppi() {
        return lasityyppi;
    }

    public String getNimi() {
        return nimi;
    }

    public String getResepti() {
        return resepti;
    }
    
    
}
