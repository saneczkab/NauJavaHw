package ru.iarmoshenko.NauJava.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.iarmoshenko.NauJava.entity.ContentType;
import ru.iarmoshenko.NauJava.repository.UserRepository;
import ru.iarmoshenko.NauJava.service.PasswordService;

import java.security.Principal;

/**
 * Контроллер для обработки генерации паролей.
 */
@Controller
public class PasswordControllerView {
    private final PasswordService passwordService;
    private final UserRepository userRepository;

    public PasswordControllerView(PasswordService passwordService, UserRepository userRepository) {
        this.passwordService = passwordService;
        this.userRepository = userRepository;
    }

    /**
     * GET-запрос на отображение страницы с паролями пользователя.
     * @param model модель для передачи данных на страницу
     * @param principal информация о текущем пользователе
     * @return имя представления для просмотра истории сгенерированных паролей
     */
    @GetMapping("/view/passwords")
    public String viewPasswords(Model model, Principal principal) {
        var username = principal.getName();
        var passwords = passwordService.getUserPasswords(username);

        model.addAttribute("passwords", passwords);
        return "passwords";
    }

    /**
     * POST-запрос на удаление пароля по его id.
     * @param passId id пароля для удаления
     * @param principal информация о текущем пользователе
     * @return редирект на страницу с паролями пользователя
     */
    @PostMapping("/password/{passId}/delete")
    public String deletePassword(@PathVariable int passId, Principal principal) {
        var username = principal.getName();
        passwordService.deletePassword(passId, username);
        return "redirect:/view/passwords";
    }

    /**
     * GET-запрос на отображение страницы генерации паролей.
     * @return имя представления для генерации паролей
     */
    @GetMapping("/view/passwords/generate")
    public String passwordGenerateView() {
        return "passwordGenerator";
    }

    /**
     * POST-запрос на генерацию паролей, отображение их на странице и сохранение их в базе данных (через сервис).
     * @param passContentLetters флаг включения букв в пароль
     * @param passContentDigits флаг включения цифр в пароль
     * @param passContentSymbols флаг включения спецсимволов в пароль
     * @param passLen длина пароля
     * @param passCount количество паролей для генерации
     * @param model модель для передачи данных на страницу
     * @param principal информация о текущем пользователе
     * @return имя представления для генерации паролей
     */
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
        var contentType = getContentType(passContentLetters, passContentDigits, passContentSymbols);
        var passwords = passwordService.generatePassword(passCount, passLen, contentType, username);
        model.addAttribute("message", passwords);

        return "passwordGenerator";
    }

    /**
     * Определение типа содержимого пароля на основе флагов.
     * @param passContentLetters флаг включения букв в пароль
     * @param passContentDigits флаг включения цифр в пароль
     * @param passContentSymbols флаг включения спецсимволов в пароль
     * @return тип содержимого пароля
     */
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
