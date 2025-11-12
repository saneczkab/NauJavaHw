package ru.iarmoshenko.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iarmoshenko.NauJava.entity.Password;
import ru.iarmoshenko.NauJava.repository.PasswordRepository;

import java.util.List;

@RestController
@RequestMapping("/api/passwords")
public class PasswordController {
    @Autowired
    private PasswordRepository passwordRepository;

    @GetMapping("/findByUserId")
    public List<Password> findByUserId(Integer userId) {
        return passwordRepository.findByUserId(userId);
    }
}
