package ru.iarmoshenko.NauJava;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.iarmoshenko.NauJava.customRepository.PasswordRepositoryCustom;
import ru.iarmoshenko.NauJava.customRepository.UserRepositoryCustom;
import ru.iarmoshenko.NauJava.entity.*;
import ru.iarmoshenko.NauJava.repository.AlgorithmRepository;
import ru.iarmoshenko.NauJava.repository.ContentRepository;
import ru.iarmoshenko.NauJava.repository.PasswordRepository;
import ru.iarmoshenko.NauJava.repository.UserRepository;
import ru.iarmoshenko.NauJava.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class PasswordGeneratorTest {
    protected List<Password> passwords;
    protected List<User> users;

    @Autowired
    protected PasswordRepository passwordRepository;
    @Autowired
    protected PasswordRepositoryCustom passwordRepositoryCustom;

    @Autowired
    protected ContentRepository contentRepository;

    @Autowired
    protected AlgorithmRepository algorithmRepository;

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected UserRepositoryCustom userRepositoryCustom;
    @Autowired
    protected UserService userService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    private static int id = 0;

    public PasswordGeneratorTest() {}

    @BeforeEach
    public void setUp() {
        users = generateUsers(5);
        passwords = generatePasswords(5, users);
        saveAllPasswordEntities(passwords);
        createAdminUser();
    }

    private void createAdminUser() {
        var passHash = passwordEncoder.encode("admin");
        var adminUser = new User("admin", "admin@admin.ru", passHash);
        adminUser.setRole(Role.ADMIN);
        userRepository.save(adminUser);
    }

    @AfterEach
    public void cleanUp() {
        passwordRepository.deleteAll();
        contentRepository.deleteAll();
        algorithmRepository.deleteAll();
        userRepository.deleteAll();
    }

    public static List<Password> generatePasswords(int count, List<User> users) {
        var passwords = new ArrayList<Password>();
        var now = LocalDateTime.now();

        for (int i = 0; i < count; i++) {
            for (var user : users) {
                var encryptedPassword = new byte[]{(byte) i};
                var content = new Content("name" + id, "description" + id, "usedSymbols" + id);
                var algo = new Algorithm("name" + id, id, "mode" + id);
                var password = new Password(user, encryptedPassword, content, algo, "salt" + id, id, now);

                id++;
                passwords.add(password);
            }
        }

        return passwords;
    }

    public static List<User> generateUsers(int count) {
        var result = new ArrayList<User>();

        for (int i = 0; i < count; i++) {
            var user = new User("username" + id, "email" + id + "@example.com", "passwordHash" + id);
            id++;
            result.add(user);
        }

        return result;
    }

    public void saveAllPasswordEntities(List<Password> passwords) {
        for (var password : passwords) {
            userRepository.save(password.getUser());
            contentRepository.save(password.getContent());
            algorithmRepository.save(password.getAlgorithm());
            passwordRepository.save(password);
        }
    }
}
