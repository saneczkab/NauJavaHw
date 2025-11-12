package ru.iarmoshenko.NauJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iarmoshenko.NauJava.entity.Password;

import java.util.List;

@SpringBootTest
public class PasswordTest extends PasswordGeneratorTest {
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

    @Test
    public void testFindByUserIdCustom() {
        var firstUser = users.get(0);
        var secondUser = users.get(1);
        var firstPasswords = passwords.stream().filter(p -> p.getUser().equals(firstUser)).toList();
        var secondPasswords = passwords.stream().filter(p -> p.getUser().equals(secondUser)).toList();

        var actualFirstUserPasswords = passwordRepositoryCustom.findByUserId(firstUser.getId());
        checkPasswordsEquality(firstPasswords, actualFirstUserPasswords);

        var actualSecondUserPasswords = passwordRepositoryCustom.findByUserId(secondUser.getId());
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
