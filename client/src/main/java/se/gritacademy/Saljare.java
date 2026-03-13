package se.gritacademy;

public class Saljare {
    private String namn;
    private String epost;
    private String telefon; // Changed to String — safer than int for phone numbers

    public String getNamn() { return namn; }

    public void setNamn(String namn) { this.namn = namn; }

    public String getEpost() { return epost; }

    public void setEpost(String epost) { this.epost = epost; }

    public String getTelefon() { return telefon; }

    public void setTelefon(String telefon) { this.telefon = telefon; }
}