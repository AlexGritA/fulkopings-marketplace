package se.gritacademy.server;
//Jag valde att göra Säljare till ett eget objekt för att om
// man senare vill lägga till fler uppgifter om säljaren,
// är det mycket enklare när säljaren redan är ett eget objekt.
public class Saljare {

    private String namn;
    private String epost;
    private int telefon;

    //Tom konstruktor behövs för att Jackson ska kunna skapa objektet innan värden sätts.
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

