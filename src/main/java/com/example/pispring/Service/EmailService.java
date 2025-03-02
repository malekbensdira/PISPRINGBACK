package com.example.pispring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendLowStockAlert(String itemName, int stock, String partnerName) {
        String to = "rima.guedria11@gmail.com";  // Remplace par l'email de l'admin
        String subject = "⚠️ Alerte Stock Faible: " + itemName;

        String message = "L'item '" + itemName + "' est presque épuisé !\n"
                + "📦 Stock restant : " + stock + "\n"
                + "🤝 Partenaire : " + partnerName + "\n\n"
                + "Veuillez prendre des mesures rapidement.";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
        System.out.println("📧 Email envoyé à l'admin avec le nom du partenaire !");
    }
}
