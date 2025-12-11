package ru.iarmoshenko.NauJava.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.iarmoshenko.NauJava.entity.ContentType;
import ru.iarmoshenko.NauJava.repository.UserRepository;
import ru.iarmoshenko.NauJava.service.PasswordService;

import java.security.Principal;

@Controller
public class PasswordControllerView {
    private final PasswordService passwordService;
    private final UserRepository userRepository;

    public PasswordControllerView(PasswordService passwordService, UserRepository userRepository) {
        this.passwordService = passwordService;
        this.userRepository = userRepository;
    }

    @GetMapping("/view/passwords/generate")
    public String passwordGenerateView() {
        return "passwordGenerator";
    }

    @PostMapping("/generate")
    public String generatePassword(boolean passContentLetters, boolean passContentDigits, boolean passContentSymbols,
                                   Integer passLen, Integer passCount, Model model, Principal principal) {
        if (passLen == null || passLen < 1) {
            passLen = 12;
        }
        if (passCount == null || passCount < 1) {
            passCount = 1;
        }

        var username = principal.getName();
        var user = userRepository.findByUsernameOrEmail(username, null).getFirst();
        var contentType = getContentType(passContentLetters, passContentDigits, passContentSymbols);
        var pass = passwordService.generatePassword(passLen, contentType, user.getId());

        model.addAttribute("message", pass);
        return "passwordGenerator";
    }
    
    private ContentType getContentType(boolean passContentLetters, boolean passContentDigits, boolean passContentSymbols) {
        if (passContentLetters && !passContentDigits && !passContentSymbols) {
            return ContentType.LETTERS;
        } else if (!passContentLetters && passContentDigits && !passContentSymbols) {
            return ContentType.DIGITS;
        } else if (!passContentLetters && !passContentDigits && passContentSymbols) {
            return ContentType.SPECIAL_CHARS;
        } else if (passContentLetters && passContentDigits && !passContentSymbols) {
            return ContentType.LETTERS_DIGITS;
        } else if (passContentLetters && !passContentDigits && passContentSymbols) {
            return ContentType.LETTERS_SPECIAL_CHARS;
        } else if (!passContentLetters && passContentDigits && passContentSymbols) {
            return ContentType.DIGITS_SPECIAL_CHARS;
        } else if (passContentLetters && passContentDigits && passContentSymbols) {
            return ContentType.MIX;
        }
        return ContentType.LETTERS;
    }
}
