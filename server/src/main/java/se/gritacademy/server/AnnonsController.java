package se.gritacademy.server;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//Detta skriver vi för att visa att detta är en REST - kontroller.
//Det gör att vi kan ta emot HTTP anrop (TEX GET, PUT, POST, DELETE.)
//Alla metoder i denna klassen som returnerar ett objekt skickas automatiskt som JSON til klienten.
@RestController

//Anger basadressen för alla metoder i klassen
//Bestämmer URL:en klienten använder för att prata med servern
@RequestMapping("/annonser")
public class AnnonsController {

    // Här skapar vi listan i minnet
    private List<Annons> annonser = new ArrayList<>();

    @GetMapping //Anger att detta är en GET-endpoint
    public List<Annons> getAllAnnonser() {
        return annonser;
    }

    @PostMapping //Anger att detta är en POST-endpoint
    //(@RequestBody Annons nyAnnons) säger åt Spring Boot att läsa
    // JSON från klientens request och omvandla till ett Annons-objekt
    public Annons createAnnons(@RequestBody Annons nyAnnons) {
        annonser.add(nyAnnons); //Lägger till i minneslistan
        return nyAnnons; //returnerar annonsen som svar
    }

}
