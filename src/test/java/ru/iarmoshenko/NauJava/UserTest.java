package ru.iarmoshenko.NauJava;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iarmoshenko.NauJava.entity.User;

@SpringBootTest
public class UserTest extends PasswordGeneratorTest {
    @Test
    public void testFindUserByUsernameOrEmail(){
        var firstUser = users.get(0);
        var secondUser = users.get(1);

        var foundByUsername = userRepository.findByUsernameOrEmail(firstUser.getUsername(), null);
        var foundByEmail = userRepository.findByUsernameOrEmail(null, firstUser.getEmail());

        checkUserEquality(firstUser, secondUser, foundByUsername);
        checkUserEquality(firstUser, secondUser, foundByEmail);
    }

    @Test
    public void testFindUserByUsernameOrEmailCustom() {
        var firstUser = users.get(0);
        var secondUser = users.get(1);

        var foundByUsername = userRepositoryCustom.findByUsernameOrEmail(firstUser.getUsername(), null);
        var foundByEmail = userRepositoryCustom.findByUsernameOrEmail(null, firstUser.getEmail());

        checkUserEquality(firstUser, secondUser, foundByUsername);
        checkUserEquality(firstUser, secondUser, foundByEmail);
    }

    private void checkUserEquality(User expected, User notExpected, User actual) {
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
        Assertions.assertNotEquals(notExpected, actual);
    }

    @Test
    public void testDeleteUserById() {
        var userToDelete = users.getFirst();
        var idToDelete = userToDelete.getId();

        Assertions.assertFalse(userRepository.findById(idToDelete).isEmpty());
        Assertions.assertFalse(passwordRepository.findByUserId(idToDelete).isEmpty());

        userService.deleteUserById(idToDelete);

        Assertions.assertTrue(userRepository.findById(idToDelete).isEmpty());
        Assertions.assertTrue(passwordRepository.findByUserId(idToDelete).isEmpty());
    }
}
