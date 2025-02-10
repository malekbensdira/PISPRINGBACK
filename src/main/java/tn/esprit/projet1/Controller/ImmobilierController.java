package tn.esprit.projet1.Controller;


import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projet1.Model.Immobilier;
import tn.esprit.projet1.Model.TypeImmobilier;
import tn.esprit.projet1.Service.ImmobilierService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/immobilier")
public class ImmobilierController {

    @Autowired
    private ImmobilierService immobilierService;

    @PostMapping("/immobilier")
    public ResponseEntity<Immobilier> addImmobilier(@RequestBody Immobilier immobilier) {
        Immobilier addedImmobilier = immobilierService.addImmobilier(immobilier);
        return new ResponseEntity<>(addedImmobilier, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Immobilier>> getAllImmobiliers() {
        List<Immobilier> immobiliers = immobilierService.getAllImmobiliers();
        return ResponseEntity.ok(immobiliers);
    }


    @GetMapping("/types")
    public TypeImmobilier[] getTypes() {
        return TypeImmobilier.values();

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImmobilier(@PathVariable Long id) {
        immobilierService.deleteImmobilier(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
    @GetMapping("/immobiliers")
    public String getAllImmobiliers(Model model) {
        List<Immobilier> immobiliers = immobilierService.getAllImmobiliers();
        model.addText("immobiliers");
        return "immobiliers";  // Nom de la vue Thymeleaf
    }
    @GetMapping("/{id}")
    public ResponseEntity<Immobilier> getImmobilierById(@PathVariable Long id) {
        Optional<Immobilier> immobilier = immobilierService.findImmobilierById(id);
        if (immobilier.isPresent()) {
            return ResponseEntity.ok(immobilier.get());
        } else {
            return ResponseEntity.notFound().build();  // Retourne un 404 si l'immobilier n'est pas trouvé
        }
    }
}
