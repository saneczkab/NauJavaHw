package ru.iarmoshenko.NauJava;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.iarmoshenko.NauJava.entity.*;
import ru.iarmoshenko.NauJava.repository.PasswordRepository;
import ru.iarmoshenko.NauJava.repository.UserRepository;
import ru.iarmoshenko.NauJava.service.PasswordService;
import ru.iarmoshenko.NauJava.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактный класс для генерации тестовых данных пользователей и паролей.
 */
public abstract class PasswordGeneratorTest {
    protected List<Password> passwords;
    protected List<User> users;

    @Autowired
    protected PasswordRepository passwordRepository;
    @Autowired
    protected PasswordService passwordService;

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected UserService userService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    public PasswordGeneratorTest() {}

    @BeforeEach
    public void setUp() {
        users = generateUsers(5);
        passwords = generatePasswords(5, users);
        createAdminUser();
    }

    /**
     * Создание пользователя с правами администратора для тестов, сохранение в базе.
     */
    private void createAdminUser() {
        var passHash = passwordEncoder.encode("admin");
        var adminUser = new User("admin", "admin@admin.ru", passHash);
        adminUser.setRole(Role.ADMIN);
        userRepository.save(adminUser);
    }

    @AfterEach
    public void cleanUp() {
        passwordRepository.deleteAll();
        userRepository.deleteAll();
    }

    /**
     * Генерация тестовых паролей для заданного списка пользователей, сохранение паролей в базе.
     * @param count число паролей на пользователя
     * @param users список пользователей
     * @return
     */
    public List<Password> generatePasswords(int count, List<User> users) {
        var passwords = new ArrayList<Password>();
        var now = LocalDateTime.now();

        for (int i = 0; i < count; i++) {
            for (var user : users) {
                var encryptedPassword = new byte[]{(byte) i};
                var password = new Password(user, encryptedPassword, ContentType.MIX, "salt", i, now);
                passwords.add(password);
                passwordRepository.save(password);
            }
        }

        return passwords;
    }

    /**
     * Генерация тестовых пользователей, сохранение их в базе.
     * @param count число пользователей для генерации
     * @return список сгенерированных пользователей
     */
    public List<User> generateUsers(int count) {
        var result = new ArrayList<User>();

        for (int i = 0; i < count; i++) {
            var user = new User("username" + i, "email" + i + "@example.com", "passwordHash" + i);
            result.add(user);
            userRepository.save(user);
        }

        return result;
    }
}
