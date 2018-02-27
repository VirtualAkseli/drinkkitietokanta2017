package drinkkitietokanta;

public class Raaka_aine_neste {
    public int raaka_aine_neste_id;
    private String nimi;
    private int maara;
    private double hinta;
    private double alkoholipitoisuus;
    
    private int juomaanLisattavaMaara;
    private int kayttokerrat;
    
    public Raaka_aine_neste(int a, String b, int c, double d, double e){
        raaka_aine_neste_id = a;
        nimi = b;
        maara = c;
        hinta = d;
        alkoholipitoisuus = e;
    }

    public void setKayttokerrat(int kayttokerrat) {
        this.kayttokerrat = kayttokerrat;
    }

    public int getKayttokerrat() {
        return kayttokerrat;
    }
    
    public void setJuomaanLisattavaMaara(int juomaanLisattavaMaara) {
        this.juomaanLisattavaMaara = juomaanLisattavaMaara;
    }

    public int getJuomaanLisattavaMaara() {
        return juomaanLisattavaMaara;
    }
    
    public int getId(){
        return raaka_aine_neste_id;
    }
    
    public String getNimi(){
        return nimi;
    }
    
    public int getMaara(){
        return maara;
    }    
    
    public double getHinta(){
        return hinta;
    }
    
    public double getAlkoholipitoisuus(){
        return alkoholipitoisuus;
    }

    @Override
    public String toString() {
        return nimi + " (" + alkoholipitoisuus +"%)";
    }
    
    
}
