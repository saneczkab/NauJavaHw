package ru.iarmoshenko.NauJava.view;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.iarmoshenko.NauJava.PasswordGeneratorTest;

import java.time.Duration;

public class ViewTest extends PasswordGeneratorTest {
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
        super.cleanUp();

        if (driver != null) {
            driver.quit();
        }
    }

    protected void login(String username, String password) {
        driver.get("http://localhost:8080/login");

        var usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("username"))
        );
        usernameField.sendKeys(username);

        var passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(password);

        var loginButton = driver.findElement(By.className("primary"));
        loginButton.click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/"));
    }

    protected String logout() {
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
}
