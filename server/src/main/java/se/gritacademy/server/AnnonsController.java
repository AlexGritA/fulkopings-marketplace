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
    private final String filNamn = "annonser.json";

    // Konstruktorn läser in annonser från fil när servern startar
    public AnnonsController() {
    }

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
        for(Annons annons : annonser) {
            if(annons.getId() == nyAnnons.getId()) {
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
    public ResponseEntity<String> updateAnnonsPris(@PathVariable int id, @RequestBody Annons uppdateradAnnons) {
        for (Annons annons : annonser) {
            if (annons.getId() == id) {
                // Kontrollera pinkod
                if (annons.getPinkod() != uppdateradAnnons.getPinkod()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Fel pinkod, testa igen!"); //403 Forbidden
                }
                //Uppdatera priset
                annons.setPris(uppdateradAnnons.getPris());
                return ResponseEntity.ok("Annons uppdaterad!");// 200 OK
            }
        }
        //Om id inte finns
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Ingen annons med det id:t hittades."); // 404 Not Found
    }

    //Delete-endpoint som GET använder enbart PathVariable
    //pga att vi hämtar id:et direkt från URL:en. RequestParam???
    //Kan inte ta bort ett element från en lista medan du itererar
    // med en for-each-loop, kör med vanlig for-loop
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnnons(
            @PathVariable int id, @RequestParam int pinkod) {

        for (int i = 0; i < annonser.size(); i++) {
            Annons annons = annonser.get(i);

            if (annons.getId() == id) {
                if (annons.getPinkod() != pinkod) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Fel pinkod, testa igen!"); // 403
                }
                annonser.remove(i);
                return ResponseEntity.ok("Annons raderad!"); // 200
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Ingen annons med det id:t hittades."); // 404
    }
}