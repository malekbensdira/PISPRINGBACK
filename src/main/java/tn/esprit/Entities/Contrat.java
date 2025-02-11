package tn.esprit.Entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idContrat;
    private int transactionId;
    @Enumerated(value = EnumType.STRING)
    private StatusContrat statusContrat;
    @Enumerated(value = EnumType.STRING)
    private TypeContrat typeContrat;
}
