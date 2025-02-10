package tn.esprit.projet1.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Entity

public class Immobilier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "L'adresse ne peut pas être vide")
    private String adresse;

    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit être un nombre positif")
    private Double prix;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le type d'immobilier est obligatoire")
    private TypeImmobilier type;

    @NotNull(message = "La superficie est obligatoire")
    @Positive(message = "La superficie doit être un nombre positif")
    private Float superficie;

    private String photoPath;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public TypeImmobilier getType() {
        return type;
    }

    public void setType(TypeImmobilier type) {
        this.type = type;
    }

    public Float getSuperficie() {
        return superficie;
    }

    public void setSuperficie(Float superficie) {
        this.superficie = superficie;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
