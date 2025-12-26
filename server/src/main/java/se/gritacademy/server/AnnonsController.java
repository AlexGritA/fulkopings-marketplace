package se.gritacademy.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



//REST - kontroller.
//Det gör att programmet kan ta emot HTTP anrop (GET, POST, PUT, DELETE.)
//Alla metoder i denna klassen som returnerar ett objekt skickas
// automatiskt som JSON till klienten.
@RestController

//Anger basadressen för alla metoder i klassen.
//Den bestämmer URL:en klienten använder för att prata med servern.
@RequestMapping("/annonser")//Bas URL
public class AnnonsController {

    // Här skapar vi listan i minnet
    private List<Annons> annonser = new ArrayList<>();

    // Konstruktorn läser in annonser från fil när servern startar
    public AnnonsController() {
    }

    //GET /annonser
    //Returnerar alla annonser
    @GetMapping //GET-endpoint.
    public List<Annons> getAllAnnonser() {
        return annonser;
    }

    //GET /annonser/{id}
    //Hämtar en annons med specifikt id
    @GetMapping("/{id}")
    public ResponseEntity<Annons> getAnnonsById(@PathVariable int id) {
        for(Annons annons : annonser) {
            if(annons.getId() == id) {
                return ResponseEntity.ok(annons); //200 OK
            }
        }
        return ResponseEntity.notFound().build(); //404 Not Found
    }
    //POST /annonser
    //Skapar en ny annons
    @PostMapping //Anger att detta är en POST-endpoint.
    public ResponseEntity<Annons> createAnnons(@RequestBody Annons nyAnnons) {
        for(Annons annons : annonser) {
            if(annons.getId() == nyAnnons.getId()) {
                //409 Conflict om id redan finns
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        annonser.add(nyAnnons);
        return ResponseEntity.status(HttpStatus.CREATED).body(nyAnnons);
    }

    //PUT /annonser/{id}
    //Uppdaterar endast priset
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

    //DELETE /annonser/{id}?pinkod=1234
    //Tar bort annons om pinkod stämmer
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