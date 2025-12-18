package se.gritacademy.server;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//Detta skriver vi för att visa att detta är en REST - kontroller.
//Det gör att vi kan ta emot HTTP anrop (TEX GET, PUT, POST, DELETE.)
//Alla metoder i denna klassen som returnerar ett objekt skickas
// automatiskt som JSON til klienten.
@RestController

//Anger basadressen för alla metoder i klassen.
//Bestämmer URL:en klienten använder för att prata med servern.
@RequestMapping("/annonser")//Bas URL
public class AnnonsController {

    // Här skapar vi listan i minnet
    private List<Annons> annonser = new ArrayList<>();

    @GetMapping //Anger att detta är en GET-endpoint.
    public List<Annons> getAllAnnonser() {
        return annonser;
    }

    //PathVariable tar värdet från URL:en och
    // skickar det till metoden som en variabel och inte som text.
    @GetMapping("/{id}")
    public Annons getAnnonsById(@PathVariable int id) {
        for(Annons annons : annonser) {
            if(annons.getId() == id) {
                return annons;
            }
        }
        return null;
    }

    @PostMapping //Anger att detta är en POST-endpoint.
    //(@RequestBody Annons nyAnnons) säger åt Spring Boot att läsa
    // JSON från klientens request och omvandla till ett Annons-objekt.
    public Annons createAnnons(@RequestBody Annons nyAnnons) {
        annonser.add(nyAnnons); //Lägger till i minneslistan
        return nyAnnons; //returnerar annonsen som svar
    }

    //Anger att detta är en PUT-endpoint, för uppdatering av
    //annonser i annonslistan,
    @PutMapping("/{id}")
    public Annons updateAnnonsPris(@PathVariable int id, @RequestBody
                                   Annons uppdateradAnnons) {
        for(Annons annons : annonser) {
            if(annons.getId() == id) {
                annons.setPris(uppdateradAnnons.getPris());
                annons.setAmnesrad(uppdateradAnnons.getAmnesrad());
                annons.setBeskrivning(uppdateradAnnons.getBeskrivning());
                annons.setSaljare(uppdateradAnnons.getSaljare());
                return annons;
            }
        }
        return null;
    }
    //Delete-endpoint som GET använder vi enbart PathVariable
    //pga att vi hämtar id:et direkt från URL:en.
    @DeleteMapping("/{id}")
    public void deleteAnnons(@PathVariable int id) {
        annonser.removeIf(annons -> annons.getId() == id);
    }


}
