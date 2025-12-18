package se.gritacademy.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Annons> getAnnonsById(@PathVariable int id) {
        for(Annons annons : annonser) {
            if(annons.getId() == id) {
                return ResponseEntity.ok(annons); //200 OK
            }
        }
        return ResponseEntity.notFound().build(); //404 Not Found
    }

    @PostMapping //Anger att detta är en POST-endpoint.
    //(@RequestBody Annons nyAnnons) säger åt Spring Boot att läsa
    // JSON från klientens request och omvandla till ett Annons-objekt.
    public ResponseEntity<Annons> createAnnons(@RequestBody Annons nyAnnons) {
        for (Annons annons : annonser) {
            if (annons.getId() == nyAnnons.getId()) {
                // 409 Conflict om id redan finns
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        annonser.add(nyAnnons);
        return ResponseEntity.status(HttpStatus.CREATED).body(nyAnnons);
    }

    //Anger att detta är en PUT-endpoint, för uppdatering av
    //annonser i annonslistan,
    @PutMapping("/{id}")
    public ResponseEntity<Annons> updateAnnonsPris(@PathVariable int id, @RequestBody
                                   Annons uppdateradAnnons) {
        for(Annons annons : annonser) {
            if(annons.getId() == id) {
                annons.setPris(uppdateradAnnons.getPris());
                annons.setAmnesrad(uppdateradAnnons.getAmnesrad());
                annons.setBeskrivning(uppdateradAnnons.getBeskrivning());
                annons.setSaljare(uppdateradAnnons.getSaljare());
                return ResponseEntity.ok(annons); //200 OK
            }
        }
        return ResponseEntity.notFound().build(); //404 Not Found
    }
    //Delete-endpoint som GET använder vi enbart PathVariable
    //pga att vi hämtar id:et direkt från URL:en.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnons(@PathVariable int id) {
        for (Annons annons : annonser) {
            if (annons.getId() == id) {
                annonser.remove(annons);
                return ResponseEntity.noContent().build(); // 204 No Content
            }
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }



}
