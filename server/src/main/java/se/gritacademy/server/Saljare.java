package se.gritacademy.server;

public class Saljare {

    private String namn;
    private String epost;
    private int telefon;

    //Konstruktor
    public Saljare() {}

    //Getter och setters
    public String getNamn(){
        return namn;
    }
    public void setNamn(String namn){
        this.namn = namn;
    }

    public String getEpost(){
        return epost;
    }

    public void setEpost(String epost){
        this.epost = epost;
    }

    public int getTelefon(){
        return telefon;
    }

    public void setTelefon(int telefon){
        this.telefon = telefon;
    }
}

