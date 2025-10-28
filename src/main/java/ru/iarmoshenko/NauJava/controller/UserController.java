package ru.iarmoshenko.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.iarmoshenko.NauJava.entity.User;
import ru.iarmoshenko.NauJava.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("findByUsernameOrEmail")
    public List<User> findByUsernameOrEmail(
            @RequestParam(required = false) String username, @RequestParam(required = false) String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }
}
