package com.example.BACK.service;

import com.example.BACK.model.User;
import com.example.BACK.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Créer un nouvel utilisateur
    public User createUser(User user) {
        // Vérification si l'email existe déjà
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("L'email est déjà utilisé");
        }

        // Vérification si le CIN existe déjà
        if (userRepository.findByCin(user.getCin()).isPresent()) {
            throw new IllegalArgumentException("Le CIN est déjà utilisé");
        }

        return userRepository.save(user);
    }

    // Mettre à jour un utilisateur
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setNom(updatedUser.getNom());
            user.setPrenom(updatedUser.getPrenom());
            user.setCin(updatedUser.getCin());
            user.setEmail(updatedUser.getEmail());
            user.setMdp(updatedUser.getMdp());
            user.setSexe(updatedUser.getSexe());
            user.setTel(updatedUser.getTel());
            user.setRole(updatedUser.getRole());  // Mise à jour du rôle
            user.setAdresse(updatedUser.getAdresse());
            user.setImage(updatedUser.getImage());
            user.setSoldeCourant(updatedUser.getSoldeCourant());
            user.setRib(updatedUser.getRib());
            user.setAge(updatedUser.getAge());

            return userRepository.save(user);
        }
        return null;
    }

    // Récupérer un utilisateur par ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Supprimer un utilisateur
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    //trier par nom
      public List<User> getUsersSortedByName() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "nom"));
    }

}
