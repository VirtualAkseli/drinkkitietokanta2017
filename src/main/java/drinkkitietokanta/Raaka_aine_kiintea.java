package drinkkitietokanta;

public class Raaka_aine_kiintea {
    private int raaka_aine_kiintea_id;
    private String nimi;
    private int paino;
    private double hinta;
    
    public Raaka_aine_kiintea(int a, String b, int c, double d){
        raaka_aine_kiintea_id = a;
        nimi = b;
        paino = c;
        hinta = d;
    }
    
    public int getId(){
        return raaka_aine_kiintea_id;
    }
    
    public String getNimi(){
        return nimi;
    }
    
    public int getPaino(){
        return paino;
    }
    
    public double getHinta(){
        return hinta;
    }
}
