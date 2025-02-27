package com.example.pispring.Service;

import com.example.pispring.Entities.DonationCertificate;
import com.example.pispring.Entities.User;
import com.example.pispring.Repository.CartRepository;
import com.example.pispring.Repository.DonationCertificateRepository;
import com.example.pispring.Repository.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class BadgeService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationCertificateRepository donationCertificateRepository;

    public void updateUserBadge(int userId) {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);

        int cartCount = cartRepository.countByUserIdAndDateBetween(userId, startOfMonth, endOfMonth);
        System.out.println("User " + userId + " cart count: " + cartCount);

        String badge;
        if (cartCount >= 6) {
            badge = "Gold";
        } else if (cartCount >= 4) {
            badge = "Silver";
        } else {
            badge = "Bronze";
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("Previous badge: " + user.getBadge());

        user.setBadge(badge);
        userRepository.save(user);

        System.out.println("Updated badge for user " + userId + ": " + user.getBadge());

        // Generate the donation certificate
        generateCertificate(user, badge);
    }

    private void generateCertificate(User user, String badge) {
        String folderPath = "certificates/";
        String filePath = folderPath + user.getIdUser() + "_donation_certificate.pdf";

        try {
            // Ensure folder exists
            File folder = new File(folderPath);
            if (!folder.exists() && !folder.mkdirs()) {
                System.err.println("❌ Error creating folder: " + folderPath);
                return;
            }

            // Create document with margins
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            try {
                String logoPath = "certificates/logo.png"; // Mets le chemin de ton logo ici
                Image logo = Image.getInstance(logoPath);
                logo.scaleToFit(100, 100); // Redimensionner si nécessaire
                logo.setAlignment(Image.ALIGN_CENTER); // Centrer le logo
                document.add(logo);
            } catch (Exception e) {
                System.err.println("❌ Erreur lors de l'ajout du logo : " + e.getMessage());
            }

            // Define fonts
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 26, BaseColor.BLUE);
            Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 18, BaseColor.DARK_GRAY);
            Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.BLACK);
            Font signatureFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12, BaseColor.GRAY);

            // Title
            Paragraph title = new Paragraph("CERTIFICATE OF DONATION", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            // Subtitle
            Paragraph subtitle = new Paragraph("This certificate is proudly presented to:", subtitleFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle);

            document.add(new Paragraph("\n"));

            // User name
            String fullName = (user.getFirstName() != null ? user.getFirstName() : "") + " " +
                    (user.getLastName() != null ? user.getLastName() : "");
            Paragraph name = new Paragraph(fullName, new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLACK));
            name.setAlignment(Element.ALIGN_CENTER);
            document.add(name);

            document.add(new Paragraph("\n"));

            // Certificate text
            Paragraph body = new Paragraph("In recognition of their generosity and commitment to helping others.", textFont);
            body.setAlignment(Element.ALIGN_CENTER);
            document.add(body);

            document.add(new Paragraph("\n\n"));

            // Badge & date
            // Définition d'une police plus grande et en couleur
            Font badgeFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new BaseColor(255, 215, 0)); // Couleur dorée

// Ajout du badge avec une mise en forme plus visible
            Paragraph badgeParagraph = new Paragraph("Badge obtenu : " + badge, badgeFont);
            badgeParagraph.setAlignment(Element.ALIGN_CENTER); // Centrer le badge
            document.add(new Paragraph("\n")); // Ajouter un espace avant le badge
            document.add(badgeParagraph);

// Obtenir le mois en toutes lettres
            String monthName = LocalDate.now().getMonth().toString(); // Renvoie le mois en anglais en MAJUSCULES
            monthName = monthName.substring(0, 1) + monthName.substring(1).toLowerCase(); // Mettre la première lettre en majuscule

// Ajouter la phrase au document
            document.add(new Paragraph("For the month of " + monthName, textFont));

            document.add(new Paragraph("\n\n"));
            // Ajouter le cachet (signature) en bas du document
            try {
                String stampPath = "certificates/cachet.png"; // Mets le chemin de ton cachet ici
                Image stamp = Image.getInstance(stampPath);
                stamp.scaleToFit(150, 100); // Ajuster la taille du cachet
                stamp.setAlignment(Image.ALIGN_RIGHT); // Positionner à droite
                //stamp.setAbsolutePosition(document.right() - 160, document.bottom() + 50); // Positionner en bas à droite
                document.add(stamp);
            } catch (Exception e) {
                System.err.println("❌ Erreur lors de l'ajout du cachet : " + e.getMessage());
            }

            // Signature line
            Paragraph signature = new Paragraph("__________________________", signatureFont);
            signature.setAlignment(Element.ALIGN_RIGHT);
            document.add(signature);

            Paragraph signatureLabel = new Paragraph("Authorized Signature", signatureFont);
            signatureLabel.setAlignment(Element.ALIGN_RIGHT);
            document.add(signatureLabel);

            // Thank you message
            Paragraph thanks = new Paragraph("\nThank you for making a difference!", subtitleFont);
            thanks.setAlignment(Element.ALIGN_CENTER);
            document.add(thanks);



            document.close();

            // Save certificate in DB
            DonationCertificate certificate = new DonationCertificate();
            certificate.setUser(user);
            certificate.setBadge(badge);
            certificate.setDateIssued(LocalDateTime.now());
            certificate.setCertificatePath(filePath);
            donationCertificateRepository.save(certificate);

            System.out.println("✅ Certificate generated: " + filePath);

        } catch (DocumentException | IOException e) {
            System.err.println("❌ Error generating certificate: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
