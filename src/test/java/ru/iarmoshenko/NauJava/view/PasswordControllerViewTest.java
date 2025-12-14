package ru.iarmoshenko.NauJava.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

/**
 * Тесты для проверки функционала PasswordControllerView.
 * Тесты написаны на базе Selenium.
 */
@SpringBootTest
public class PasswordControllerViewTest extends ViewTest {
    /**
     * Тест генерации паролей, их просмотра и удаления.
     */
    @Test
    public void testGenerateAndDeletePassword() {
        login("tests_admin", "tests_admin");

        var random = new Random();
        var expectedLength = random.nextInt(1, 20);
        var expectedCount = random.nextInt(1, 20);

        generatePasswords(expectedLength, expectedCount);
        var passwords = viewPasswords();
        checkTablePasswords(passwords, expectedCount, expectedLength);

        var firstPassword = passwords.getFirst();
        deleteFirstPassword();
        var passwordsAfterDelete = viewPasswords();
        Assertions.assertFalse(passwordsAfterDelete.contains(firstPassword));
        checkTablePasswords(passwordsAfterDelete, expectedCount - 1, expectedLength);
    }

    /**
     * Тест генерации небольшого числа паролей.
     */
    @Test
    public void testGeneratePasswords() {
        login("tests_admin", "tests_admin");

        var random = new Random();
        var expectedLength = random.nextInt(1, 20);
        var expectedCount = random.nextInt(1, 20);
        var generated = generatePasswords(expectedLength, expectedCount);

        Assertions.assertEquals(expectedCount, generated.size());
        for (var passwordElement : generated) {
            var passwordText = passwordElement.getText();
            Assertions.assertEquals(expectedLength, passwordText.length());
        }
    }

    /**
     * Тест генерации большого числа паролей.
     */
    @Test
    public void testGenerateLotPasswords() {
        login("tests_admin", "tests_admin");

        var random = new Random();
        var expectedLength = random.nextInt(1000, 2000);
        var expectedCount = random.nextInt(1000, 2000);
        var generated = generatePasswords(expectedLength, expectedCount);

        Assertions.assertEquals(expectedCount, generated.size());
        for (var passwordElement : generated) {
            var passwordText = passwordElement.getText();
            Assertions.assertEquals(expectedLength, passwordText.length());
        }
    }

    /**
     * Тест генерации паролей с некорректной длиной.
     * Ожидается генерация паролей стандартной длины 12 символов.
     */
    @Test
    public void testGeneratePasswordsInvalidLen() {
        login("tests_admin", "tests_admin");

        var len = -42;
        var standardLen = 12;
        var expectedCount = 12;
        var generated = generatePasswords(len, expectedCount);

        Assertions.assertNotNull(generated);
        Assertions.assertEquals(expectedCount, generated.size());
        for (var passwordElement : generated) {
            var passwordText = passwordElement.getText();
            Assertions.assertEquals(standardLen, passwordText.length());
        }
    }

    /**
     * Тест генерации некорректного числа паролей.
     * Ожидается генерация стандартного количества паролей 12 штук.
     */
    @Test
    public void testGeneratePasswordsInvalidCount() {
        login("tests_admin", "tests_admin");

        var expectedLen = 13;
        var count = -42;
        var standardCount = 12;
        var generated = generatePasswords(expectedLen, count);

        Assertions.assertNotNull(generated);
        Assertions.assertEquals(standardCount, generated.size());
        for (var passwordElement : generated) {
            var passwordText = passwordElement.getText();
            Assertions.assertEquals(expectedLen, passwordText.length());
        }
    }

    /**
     * Проверка сгенерированных паролей в таблице.
     * @param passwordElements элементы таблицы с паролями
     * @param expectedCount ожидаемое количество паролей
     * @param expectedLength ожидаемая длина паролей
     */
    private void checkTablePasswords(List<WebElement> passwordElements, int expectedCount, int expectedLength) {
        Assertions.assertEquals(expectedCount, passwordElements.size());

        for (var passwordElement : passwordElements) {
            var cols = passwordElement.findElements(By.tagName("td"));
            var passText = cols.getFirst().getText();
            Assertions.assertEquals(expectedLength, passText.length());
        }
    }

    /**
     * Генерация паролей через веб-интерфейс.
     * @param len длина паролей
     * @param count количество паролей
     * @return список сгенерированных паролей (веб-элементы)
     */
    private List<WebElement> generatePasswords(int len, int count) {
        driver.get("http://localhost:8080/view/passwords/generate");

        var lenField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("passLen"))
        );
        lenField.sendKeys(String.valueOf(len));

        var countField = driver.findElement(By.name("passCount"));
        countField.sendKeys(String.valueOf(count));

        var submit = driver.findElement(By.cssSelector("input[type='submit']"));
        submit.click();

        wait.until(d -> d.findElements(By.cssSelector("ul li")));

        return driver.findElements(By.cssSelector("ul li"));
    }

    /**
     * Просмотр всех паролей пользователя через веб-интерфейс.
     * @return список паролей (веб-элементы таблицы)
     */
    private List<WebElement> viewPasswords() {
        driver.get("http://localhost:8080/view/passwords");

        wait.until(d -> d.findElements(By.cssSelector("table tbody tr")));

        return driver.findElements(By.cssSelector("table tbody tr")).stream().skip(1).toList();
    }

    /**
     * Удаление первого пароля из таблицы паролей.
     */
    private void deleteFirstPassword() {
        var submit = driver.findElement(By.cssSelector("input[type='submit']"));
        submit.click();
    }
}
