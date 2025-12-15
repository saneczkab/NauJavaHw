package ru.iarmoshenko.NauJava.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.iarmoshenko.NauJava.entity.Role;
import ru.iarmoshenko.NauJava.entity.User;
import ru.iarmoshenko.NauJava.repository.UserRepository;

/**
 * Класс, отвечающий за срздание пользователя с правами администратора при запуске приложения.
 */
@Component
public class AdminUserInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Создает пользователя с именем "admin" и паролем "admin", если такого пользователя еще нет.
     * Пользователь получает права администратора.
     */
    @Override
    public void run(ApplicationArguments args) {
        var admin = "admin";

        var existing = userRepository.findByUsernameOrEmail(admin, admin);
        if (existing.isEmpty()) {
            var user = new User(admin, admin, passwordEncoder.encode(admin));
            user.setRole(Role.ADMIN);
            userRepository.save(user);
        }
    }
}
