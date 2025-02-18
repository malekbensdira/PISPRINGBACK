package tn.esprit.projet1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.projet1.Model.Immobilier;
import tn.esprit.projet1.Model.TypeImmobilier;
import tn.esprit.projet1.Repository.ImmobilierRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

@Service
public class ImmobilierService {

    @Autowired
    private ImmobilierRepository immobilierRepository;

    @Autowired
    private ArcGISService arcGISService; // Injection correcte du service ArcGIS

    private static final Logger LOGGER = Logger.getLogger(ImmobilierService.class.getName());
    private final Random random = new Random();

    // ✅ Ajouter un bien immobilier
    public Immobilier addImmobilier(Immobilier immobilier) {
        return immobilierRepository.save(immobilier);
    }

    // ✅ Modifier un bien immobilier
    public Immobilier updateImmobilier(Long id, Immobilier immobilier) {
        return immobilierRepository.findById(id).map(existingImmobilier -> {
            existingImmobilier.setAdresse(immobilier.getAdresse());
            existingImmobilier.setPrix(immobilier.getPrix());
            existingImmobilier.setType(immobilier.getType());
            existingImmobilier.setSuperficie(immobilier.getSuperficie());
            existingImmobilier.setPhotoPath(immobilier.getPhotoPath());
            return immobilierRepository.save(existingImmobilier);
        }).orElseThrow(() -> new RuntimeException("Immobilier avec l'ID " + id + " non trouvé"));
    }

    // ✅ Supprimer un bien immobilier
    public void deleteImmobilier(Long id) {
        immobilierRepository.deleteById(id);
    }

    // ✅ Récupérer un bien immobilier par ID
    public Optional<Immobilier> findImmobilierById(Long id) {
        return immobilierRepository.findById(id);
    }

    // ✅ Récupérer tous les biens immobiliers
    public List<Immobilier> getAllImmobiliers() {
        return immobilierRepository.findAll();
    }

    // ✅ Mise à jour automatique des prix chaque jour à minuit
    @Scheduled(cron = "0 0 0 * * *") // Exécution tous les jours à 00:00
    public void updatePricesAutomatically() {
        LOGGER.info("Début de la mise à jour automatique des prix...");
        updateAllPrices();
        LOGGER.info("Mise à jour automatique des prix terminée !");
    }

    // ✅ Mettre à jour tous les prix avec ArcGIS
    public void updateAllPrices() {
        List<Immobilier> immobiliers = immobilierRepository.findAll();
        for (Immobilier immobilier : immobiliers) {
            if (immobilier.getType() == TypeImmobilier.TERRAIN) {
                Double newPrice = arcGISService.calculateNewPrice(immobilier.getAdresse(), immobilier.getPrix());
                immobilier.setPrix(newPrice);
                immobilierRepository.save(immobilier);
                LOGGER.info("Mise à jour du prix pour l'ID " + immobilier.getId() + " : " + newPrice);
            }
        }
    }
}
