package com.example.pispring.Controller;

import com.example.pispring.Entities.DonationCertificate;
import com.example.pispring.Repository.DonationCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    @Autowired
    private DonationCertificateRepository donationCertificateRepository;

    @GetMapping("/{userId}/download")
    public ResponseEntity<Resource> downloadCertificate(@PathVariable Integer userId) {
        // Créer un Pageable pour récupérer le dernier certificat
        Pageable pageable = PageRequest.of(0, 1);

        // Trouver le dernier certificat de l'utilisateur
        DonationCertificate certificate = donationCertificateRepository
                .findByUserUserIdOrderByDateIssuedDesc(userId, pageable)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Aucun certificat trouvé pour cet utilisateur"));

        // Vérifier si le fichier existe
        File file = new File(certificate.getCertificatePath());
        if (!file.exists()) {
            throw new RuntimeException("Fichier introuvable");
        }

        // Utilisation de FileSystemResource
        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

}
