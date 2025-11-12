package ru.iarmoshenko.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.iarmoshenko.NauJava.entity.User;
import ru.iarmoshenko.NauJava.service.UserService;

@Controller
public class RegistrationController {
    @Autowired
    public UserService userService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(String username, String email, String password, Model model) {
        try {
            var passwordHash = passwordEncoder.encode(password);
            userService.createUser(username, email, passwordHash);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("message", "User exists");
            return "registration";
        }
    }
}
