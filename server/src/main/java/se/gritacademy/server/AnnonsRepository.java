package se.gritacademy.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Hanterar läsning och skrivning av annonser till JSON-fil
@Component
public class AnnonsRepository {

    private static final String FIL = "annonser.json";
    private final ObjectMapper mapper = new ObjectMapper();
    private List<Annons> annonser;

    //Läser in filen när Spring skapar objektet
    public AnnonsRepository() {
        annonser = lasIn();
    }

    //Returnerar listan av annonser
    public List<Annons> getAll() {
        return annonser;
    }

    //Skriver hela listan till JSON-fil
    public void sparaTillFil() {
        try {
            mapper.writeValue(new File(FIL), annonser);
        } catch (IOException e) {
            System.out.println("Kunde inte skriva till fil: " + e.getMessage());
        }
    }

    //Läser in annonser från fil vid uppstart
    private List<Annons> lasIn() {
        File fil = new File(FIL);
        if (!fil.exists()) {
            return new ArrayList<>(); // Ingen fil = tom lista
        }
        try {
            return mapper.readValue(fil, new TypeReference<List<Annons>>() {});
        } catch (IOException e) {
            System.out.println("Kunde inte läsa fil: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}