package ru.iarmoshenko.NauJava.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Тесты для проверки функционала UserControllerView.
 * Тесты написаны на базе Selenium.
 */
@SpringBootTest
public class UserControllerViewTest extends ViewTest {
    /**
     * Просмотр списка пользователей без прав администратора.
     */
    @Test
    public void testViewUsersAccessDenied() {
        login("tests_user", "tests_user");
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/"));

        driver.get("http://localhost:8080/view/users/list");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        var content = driver.getPageSource();

        Assertions.assertNotNull(content);
        Assertions.assertTrue(content.contains("type=Forbidden, status=403"));
    }

    /**
     * Тест просмотра списка пользователей с правами администратора.
     * Проверяется корректность отображаемых данных, возможность изменения роли, удаления пользователя.
     */
    @Test
    public void testViewUsers() {
        login("tests_admin", "tests_admin");
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/"));

        var userRows = viewUsers();
        checkListedUsers(userRows);

        var lastUser = userRows.getLast();
        var role = lastUser.findElements(By.tagName("td")).get(3).getText();
        var expectedRole = role.equals("ADMIN") ? "USER" : "ADMIN";
        changeRole(lastUser);
        var updatedUsers = viewUsers();
        var updatedLastUser = updatedUsers.getLast();
        checkRoleChange(updatedLastUser, expectedRole);

        deleteUser(updatedLastUser);
        var finalUsers = viewUsers();
        Assertions.assertFalse(finalUsers.contains(updatedLastUser));
    }

    private void deleteUser(WebElement row) {
        var cells = row.findElements(By.tagName("td"));
        var deleteButton = cells.get(6).findElement(By.cssSelector("input[type='submit']"));
        deleteButton.click();
    }

    /**
     * Проверка изменения роли пользователя в таблице.
     * @param row строка таблицы пользователя
     * @param expectedRole ожидаемая роль пользователя после изменения
     */
    private void checkRoleChange(WebElement row, String expectedRole) {
        var cells = row.findElements(By.tagName("td"));
        var role = cells.get(3).getText();
        Assertions.assertEquals(expectedRole, role);
    }

    /**
     * Изменение роли пользователя.
     * USER -> ADMIN или ADMIN -> USER
     * @param row строка таблицы пользователя
     */
    private void changeRole(WebElement row) {
        var cells = row.findElements(By.tagName("td"));
        var role = cells.get(3).getText();
        var changeRoleButton = role.equals("USER") ?
                cells.get(4).findElement(By.cssSelector("input[type='submit']")) :
                cells.get(5).findElement(By.cssSelector("input[type='submit']"));
        changeRoleButton.click();
    }

    /**
     * Проверка корректности отображаемых данных пользователей в таблице.
     * @param userRows список строк таблицы пользователей
     */
    private void checkListedUsers(List<WebElement> userRows) {
        for (var row : userRows) {
            var cells = row.findElements(By.tagName("td"));
            var id = Integer.parseInt(cells.getFirst().getText());
            var tableEmail = cells.get(1).getText();
            var tableUsername = cells.get(2).getText();
            var tableRole = cells.get(3).getText();

            var userOpt = userRepository.findById(id);
            Assertions.assertTrue(userOpt.isPresent());
            var user = userOpt.get();

            Assertions.assertEquals(user.getUsername(), tableUsername);
            Assertions.assertEquals(user.getEmail(), tableEmail);
            Assertions.assertEquals(user.getRole().name(), tableRole);
        }
    }

    /**
     * Просмотр списка пользователей.
     * @return список строк таблицы пользователей
     */
    private List<WebElement> viewUsers() {
        driver.get("http://localhost:8080/view/users/list");

        wait.until(d -> d.findElements(By.cssSelector("table tbody tr")));

        return driver.findElements(By.cssSelector("table tbody tr")).stream().skip(1).toList();
    }
}
