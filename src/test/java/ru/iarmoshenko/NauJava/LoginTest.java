package ru.iarmoshenko.NauJava;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
public class LoginTest extends PasswordGeneratorTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeAll
    public static void init() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        super.setUp();

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLogin() {
        var afterLoginContent = login();
        Assertions.assertNotNull(afterLoginContent);
        Assertions.assertTrue(afterLoginContent.contains("_links"));

        var afterLogoutContent = logout();
        Assertions.assertNotNull(afterLogoutContent);
        Assertions.assertTrue(afterLogoutContent.contains("You have been signed out"));
    }

    private String login(){
        driver.get("http://localhost:8080/login");

        var usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("username"))
        );
        usernameField.sendKeys("admin");

        var passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("admin");

        var loginButton = driver.findElement(By.className("primary"));
        loginButton.click();

        wait.until(
                ExpectedConditions.urlToBe("http://localhost:8080/")
        );

        return driver.getPageSource();
    }

    private String logout() {
        driver.get("http://localhost:8080/logout");

        var logoutButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.className("primary"))
        );
        logoutButton.click();

        wait.until(
                ExpectedConditions.urlToBe("http://localhost:8080/login?logout")
        );

        return driver.getPageSource();
    }

    @Test
    public void testLoginInvalidCredentials() {
        driver.get("http://localhost:8080/login");

        var usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("username"))
        );
        usernameField.sendKeys("wrongUser");

        var passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("wrongPass");

        var loginButton = driver.findElement(By.className("primary"));
        loginButton.click();

        wait.until(
                ExpectedConditions.urlToBe("http://localhost:8080/login?error")
        );
        var pageContent = driver.getPageSource();

        Assertions.assertNotNull(pageContent);
        Assertions.assertTrue(pageContent.contains("Invalid credentials"));
    }
}
