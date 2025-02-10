package tn.esprit.projet1.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projet1.Model.Immobilier;

@Repository
public interface ImmobilierRepository extends JpaRepository<Immobilier, Long> {
}
