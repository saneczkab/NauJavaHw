package ru.iarmoshenko.NauJava.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.iarmoshenko.NauJava.service.UserService;

/**
 * Контроллер для регистрации пользователей.
 * */
@Controller
public class RegistrationControllerView {
    @Autowired
    public UserService userService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    /**
     * GET-запрос на получение страницы регистрации.
     * */
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    /**
     * POST-запрос на регистрацию пользователя.
     *
     * @param username - имя пользователя
     * @param email - email пользователя
     * @param password - пароль пользователя
     * @param model - модель для передачи данных на страницу
     * @return перенаправление на страницу логина при успешной регистрации (иначе - вывод сообщения об ошибке)
     * */
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
