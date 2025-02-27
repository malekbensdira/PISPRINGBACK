package com.example.pispring.Controller;

import com.example.pispring.Entities.Partner;
import com.example.pispring.Entities.User;
import com.example.pispring.Service.PartnerService;
import com.example.pispring.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    public UserController(UserService userService) { this.userService = userService; }
    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUsers(); }
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return userService.getUserById((long) id); }
}
