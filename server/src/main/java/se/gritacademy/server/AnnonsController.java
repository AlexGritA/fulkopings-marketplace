package se.gritacademy.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Spring injicerar AnnonsRepository automatiskt
    private final AnnonsRepository repository;

    public AnnonsController(AnnonsRepository repository) {
        this.repository = repository;
    }

    //GET /annonser
    //Returnerar alla annonser
    @GetMapping //GET-endpoint.
    public List<Annons> getAllAnnonser() {
        return repository.getAll();
    }

    //GET /annonser/{id}
    //Hämtar en annons med specifikt id
    @GetMapping("/{id}")
    public ResponseEntity<Annons> getAnnonsById(@PathVariable int id) {
        for(Annons annons : repository.getAll()) {
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
        for(Annons annons : repository.getAll()) {
            if(annons.getId() == nyAnnons.getId()) {
                //409 Conflict om id redan finns
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        repository.getAll().add(nyAnnons);
        repository.sparaTillFil(); //Spara till fil
        return ResponseEntity.status(HttpStatus.CREATED).body(nyAnnons);
    }

    //PUT /annonser/{id}
    //Uppdaterar endast priset
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAnnonsPris(@PathVariable int id, @RequestBody Annons uppdateradAnnons) {
        for (Annons annons : repository.getAll()) {
            if (annons.getId() == id) {
                // Kontrollera pinkod
                if (annons.getPinkod() != uppdateradAnnons.getPinkod()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Fel pinkod, testa igen!"); //403 Forbidden
                }
                //Uppdatera priset
                annons.setPris(uppdateradAnnons.getPris());
                repository.sparaTillFil(); //Spara till fil
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
    public ResponseEntity<String> deleteAnnons(@PathVariable int id, @RequestParam int pinkod) {
        List<Annons> annonser = repository.getAll();
        for (int i = 0; i < annonser.size(); i++) {
            Annons annons = annonser.get(i);
            if (annons.getId() == id) {
                if (annons.getPinkod() != pinkod) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Fel pinkod, testa igen!"); // 403
                }
                annonser.remove(i);
                repository.sparaTillFil(); //Spara till fil
                return ResponseEntity.ok("Annons raderad!"); // 200
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Ingen annons med det id:t hittades."); // 404
    }
}