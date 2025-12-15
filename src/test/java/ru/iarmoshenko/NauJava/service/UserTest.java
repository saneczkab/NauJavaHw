package ru.iarmoshenko.NauJava.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iarmoshenko.NauJava.PasswordGeneratorTest;
import ru.iarmoshenko.NauJava.entity.User;

import java.util.List;

/**
 * Тесты для проверки функционала репозитория и сервиса пользователей.
 */
@SpringBootTest
public class UserTest extends PasswordGeneratorTest {
    /**
     * Тест поиска пользователя по имени пользователя или email, где только один параметр задан.
     * Ожидается, что будет найден только один пользователь.
     */
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

    /**
     * Тест поиска пользователей по имени пользователя и email, где оба параметра принадлежат разным пользователям.
     * Ожидается, что будут найдены оба пользователя.
     */
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

    /**
     * Тест поиска пользователя по несуществующему имени пользователя и email.
     * Ожидается, что результат будет пустым списком.
     */
    @Test
    public void testFindUserByUsernameOrEmailNoUsers() {
        var foundUsers = userRepository.findByUsernameOrEmail("not exist", "nonexistent@mail.com");
        Assertions.assertTrue(foundUsers.isEmpty());
    }

    /**
     * Тест удаления пользователя без прав администратора.
     */
    @Test
    public void testDeleteUser() {
        var nonAdminUser = users.stream()
                .filter(user -> user.getRole().name().equals("USER")).findFirst().orElseThrow();
        var nonAdminId = nonAdminUser.getId();

        try {
            userService.deleteUserById(nonAdminId);
        } catch (IllegalAccessException e) {
            Assertions.fail();
        }

        Assertions.assertTrue(userRepository.findById(nonAdminId).isEmpty());
    }

    /**
     * Тест удаления пользователя с правами администратора.
     * Ожидается выброс исключения IllegalAccessException и сохранение пользователя в репозитории.
     */
    @Test
    public void testDeleteUserByIdAdmin() {
        var adminUser = users.stream()
                .filter(user -> user.getRole().name().equals("ADMIN")).findFirst().orElseThrow();
        var adminId = adminUser.getId();

        Assertions.assertThrows(IllegalAccessException.class, () -> userService.deleteUserById(adminId));
        Assertions.assertTrue(userRepository.findById(adminId).isPresent());
    }

    /**
     * Тест получения пользователя по имени пользователя.
     */
    @Test
    public void testGetUserByUsername() {
        var firstUser = users.getFirst();
        var foundUser = userService.getUserByUsername(firstUser.getUsername());

        Assertions.assertEquals(firstUser, foundUser);
    }

    /**
     * Тест получения пользователя по несуществующему имени пользователя.
     */
    @Test
    public void testGetUserByUsernameNoUser() {
        var foundUser = userService.getUserByUsername("not exist");
        Assertions.assertNull(foundUser);
    }

    /**
     * Тест создания нового пользователя.
     */
    @Test
    public void testCreateUser() {
        String username = "newuser";
        String email = "example@mail.ai";
        String password = "securepassword";
        userService.createUser(username, email, password);

        var foundUsers = userRepository.findByUsernameOrEmail(username, email);
        Assertions.assertEquals(1, foundUsers.size());
        var createdUser = foundUsers.getFirst();
        Assertions.assertEquals(username, createdUser.getUsername());
        Assertions.assertEquals(email, createdUser.getEmail());
    }

    /**
     * Тест установки роли администратора для пользователя без прав администратора.
     */
    @Test
    public void testSetUserRoleAdmin() {
        var nonAdminUser = users.stream()
                .filter(user -> user.getRole().name().equals("USER")).findFirst().orElseThrow();

        userService.setUserRoleAdmin(nonAdminUser.getId());
        var updatedUser = userRepository.findById(nonAdminUser.getId());

        Assertions.assertTrue(updatedUser.isPresent());
        Assertions.assertEquals("ADMIN", updatedUser.get().getRole().name());
    }

    /**
     * Тест установки роли пользователя для пользователя с правами администратора.
     */
    @Test
    public void testSetUserRoleUser() {
        var adminUser = users.stream()
                .filter(user -> user.getRole().name().equals("ADMIN")).findFirst().orElseThrow();

        userService.setUserRoleUser(adminUser.getId());
        var updatedUser = userRepository.findById(adminUser.getId());

        Assertions.assertTrue(updatedUser.isPresent());
        Assertions.assertEquals("USER", updatedUser.get().getRole().name());
    }
}
