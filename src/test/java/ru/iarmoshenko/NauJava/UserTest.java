package ru.iarmoshenko.NauJava;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest extends PasswordGeneratorTest {
    @Test
    public void testFindUserByUsernameOrEmailSingleUser(){
        var firstUser = users.get(0);
        var secondUser = users.get(1);

        var foundByUsername = userRepository.findByUsernameOrEmail(firstUser.getUsername(), null);
        var foundByEmail = userRepository.findByUsernameOrEmail(null, firstUser.getEmail());

        Assertions.assertEquals(1, foundByUsername.size());
        Assertions.assertTrue(foundByUsername.contains(firstUser));
        Assertions.assertFalse(foundByUsername.contains(secondUser));

        Assertions.assertEquals(foundByUsername, foundByEmail);
    }

    @Test
    public void testFindUserByUsernameOrEmailSingleUserCustom() {
        var firstUser = users.get(0);
        var secondUser = users.get(1);

        var foundByUsername = userRepositoryCustom.findByUsernameOrEmail(firstUser.getUsername(), null);
        var foundByEmail = userRepositoryCustom.findByUsernameOrEmail(null, firstUser.getEmail());

        Assertions.assertEquals(1, foundByUsername.size());
        Assertions.assertTrue(foundByUsername.contains(firstUser));
        Assertions.assertFalse(foundByUsername.contains(secondUser));

        Assertions.assertEquals(foundByUsername, foundByEmail);
    }

    @Test
    public void testFindUserByUsernameOrEmailMultipleUsers() {
        var firstUser = users.get(0);
        var secondUser = users.get(1);

        var firstUsername = firstUser.getUsername();
        var secondEmail = secondUser.getEmail();
        var foundUsers = userRepository.findByUsernameOrEmail(firstUsername, secondEmail);

        Assertions.assertEquals(2, foundUsers.size());
        Assertions.assertTrue(foundUsers.contains(firstUser));
        Assertions.assertTrue(foundUsers.contains(secondUser));
    }

    @Test
    public void testFindUserByUsernameOrEmailMultipleUsersCustom() {
        var firstUser = users.get(0);
        var secondUser = users.get(1);

        var firstUsername = firstUser.getUsername();
        var secondEmail = secondUser.getEmail();
        var foundUsers = userRepositoryCustom.findByUsernameOrEmail(firstUsername, secondEmail);

        Assertions.assertEquals(2, foundUsers.size());
        Assertions.assertTrue(foundUsers.contains(firstUser));
        Assertions.assertTrue(foundUsers.contains(secondUser));
    }

    @Test
    public void testFindUserByUsernameOrEmailNoUsers() {
        var foundUsers = userRepository.findByUsernameOrEmail("not exist", "nonexistent@mail.com");
        Assertions.assertTrue(foundUsers.isEmpty());
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
