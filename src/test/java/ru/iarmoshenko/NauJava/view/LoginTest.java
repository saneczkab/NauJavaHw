package ru.iarmoshenko.NauJava.view;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Тесты для проверки функционала логина и выхода из аккаунта.
 * Тесты написаны на базе Selenium.
 */
@SpringBootTest
public class LoginTest extends ViewTest {
    @Test
    public void testLogin() {
        login("tests_admin", "tests_admin");
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/"));
        var afterLoginContent = driver.getPageSource();

        Assertions.assertNotNull(afterLoginContent);
        Assertions.assertTrue(afterLoginContent.contains("_links"));

        var afterLogoutContent = logout();
        Assertions.assertNotNull(afterLogoutContent);
        Assertions.assertTrue(afterLogoutContent.contains("You have been signed out"));
    }

    @Test
    public void testLoginInvalidCredentials() {
        login("wrong_user", "wrong_password");
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/login?error"));
        var pageContent = driver.getPageSource();

        Assertions.assertNotNull(pageContent);
        Assertions.assertTrue(pageContent.contains("Invalid credentials"));
    }
}
