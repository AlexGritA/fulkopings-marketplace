package se.gritacademy.server;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Annons {

    long id;
    String amnesrad;
    String beskrivning;
    double pris;
    Saljare saljare; // (Objekt)
    @JsonIgnore//Pinkod ska ej synas för klienter
    int pinkod;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAmnesrad() {
        return amnesrad;
    }

    public void setAmnesrad(String amnesrad) {
        this.amnesrad = amnesrad;
    }

    public String getBeskrivning() {
        return beskrivning;
    }

    public void setBeskrivning(String beskrivning) {
        this.beskrivning = beskrivning;
    }

    public double getPris() {
        return pris;
    }

    public void setPris(double pris) {
        this.pris = pris;
    }

    public Saljare getSaljare() {
        return saljare;
    }

    public void setSaljare(Saljare saljare) {
        this.saljare = saljare;
    }

    public int getPinkod() {
        return pinkod;
    }

    public void setPinkod(int pinkod) {
        this.pinkod = pinkod;
    }
}
