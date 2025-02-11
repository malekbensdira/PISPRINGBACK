package com.example.BACK.repository;

import com.example.BACK.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Méthode pour trouver un utilisateur par son email
    Optional<User> findByEmail(String email);

    // Méthode pour trouver un utilisateur par son CIN
    Optional<User> findByCin(Integer cin);

    // Ajoutez d'autres méthodes personnalisées si besoin
}
