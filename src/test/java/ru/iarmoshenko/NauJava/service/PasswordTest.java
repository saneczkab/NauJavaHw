package ru.iarmoshenko.NauJava.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iarmoshenko.NauJava.PasswordGeneratorTest;
import ru.iarmoshenko.NauJava.entity.ContentType;
import ru.iarmoshenko.NauJava.entity.Password;

import java.util.List;
import java.util.Random;

/**
 * Тесты для проверки функционала репозитория и сервиса паролей.
 */
@SpringBootTest
public class PasswordTest extends PasswordGeneratorTest {
    /**
     * Тест генерации паролей с проверкой корректности сохранения их в базе.
     */
    @Test
    public void testGeneratePasswords() {
        var random = new Random();
        var user = users.getLast();
        var length = random.nextInt(1, 64);
        var count = random.nextInt(32, 64);
        var content = ContentType.MIX;
        List<String> generatedPasswords = passwordService.generatePassword(count, length, content, user.getUsername());
        var userDecryptedPasswords = passwordRepository.findByUserId(user.getId())
                .stream()
                .map(pass -> passwordService.decryptPassword(pass.getEncryptedPassword(), pass.getSalt()))
                .toList();

        Assertions.assertNotNull(generatedPasswords);
        Assertions.assertEquals(count, generatedPasswords.size());
        for (var pass : generatedPasswords) {
            Assertions.assertEquals(length, pass.length());
            Assertions.assertTrue(userDecryptedPasswords.contains(pass));
        }

        Assertions.assertTrue(userDecryptedPasswords.contains(generatedPasswords));
    }

    @Test
    public void testFindByUserId() {
        var firstUser = users.get(0);
        var secondUser = users.get(1);
        var firstPasswords = passwords.stream().filter(p -> p.getUser().equals(firstUser)).toList();
        var secondPasswords = passwords.stream().filter(p -> p.getUser().equals(secondUser)).toList();

        var actualFirstUserPasswords = passwordRepository.findByUserId(firstUser.getId());
        checkPasswordsEquality(firstPasswords, actualFirstUserPasswords);

        var actualSecondUserPasswords = passwordRepository.findByUserId(secondUser.getId());
        checkPasswordsEquality(secondPasswords, actualSecondUserPasswords);
    }

    private void checkPasswordsEquality(List<Password> expected, List<Password> actual) {
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.size(), actual.size());

        for (var password : expected) {
            Assertions.assertTrue(actual.stream().anyMatch(p -> p.equals(password)));
        }
    }
}
