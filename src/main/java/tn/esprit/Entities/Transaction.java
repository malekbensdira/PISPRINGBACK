package tn.esprit.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTransaction;
    private int cartId;
    private  int issuerId;
    private  int receiverId;
    @Enumerated(value = EnumType.STRING)
    private TypeTransaction typeTransaction;
    private float amount;
    private Date dateTransaction;
    private String status;
}
