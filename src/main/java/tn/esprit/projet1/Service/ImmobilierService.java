package tn.esprit.projet1.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projet1.Model.Immobilier;
import tn.esprit.projet1.Repository.ImmobilierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ImmobilierService {

    @Autowired
    private ImmobilierRepository immobilierRepository;

    // Ajouter un bien immobilier
    public Immobilier addImmobilier(Immobilier immobilier) {
        return immobilierRepository.save(immobilier);
    }

    // Modifier un bien immobilier
    public Immobilier updateImmobilier(Long id, Immobilier immobilier) {
        Optional<Immobilier> existingImmobilier = immobilierRepository.findById(id);
        if (existingImmobilier.isPresent()) {
            Immobilier updatedImmobilier = existingImmobilier.get();
            updatedImmobilier.setAdresse(immobilier.getAdresse());
            updatedImmobilier.setPrix(immobilier.getPrix());
            updatedImmobilier.setType(immobilier.getType());
            updatedImmobilier.setSuperficie(immobilier.getSuperficie());
            updatedImmobilier.setPhotoPath(immobilier.getPhotoPath());
            return immobilierRepository.save(updatedImmobilier);
        } else {
            throw new RuntimeException("Immobilier avec l'ID " + id + " non trouvé");
        }
    }

    // Supprimer un bien immobilier
    public void deleteImmobilier(Long id) {
        immobilierRepository.deleteById(id);
    }

    // Récupérer un bien immobilier par ID
    public Immobilier getImmobilierById(Long id) {
        return immobilierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Immobilier avec l'ID " + id + " non trouvé"));
    }
    public Optional<Immobilier> findImmobilierById(Long id) {
        return immobilierRepository.findById(id);
    }

    // Récupérer tous les biens immobiliers
    public List<Immobilier> getAllImmobiliers() {
        return immobilierRepository.findAll();
    }
}
