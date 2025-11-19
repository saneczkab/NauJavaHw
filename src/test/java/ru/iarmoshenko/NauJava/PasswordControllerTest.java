package ru.iarmoshenko.NauJava;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iarmoshenko.NauJava.entity.Password;

@SpringBootTest
public class PasswordControllerTest extends PasswordGeneratorTest {
    @BeforeEach
    public void setUp() {
        super.setUp();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void testFindByUserId() {
        for (var user : users) {
            var expectedPasswords = passwords.stream()
                    .filter(p -> p.getUser().getId() == user.getId())
                    .toList();

            var apiPasswords = RestAssured.given()
                    .auth()
                    .basic("admin", "admin")
                    .param("userId", user.getId())
                    .get("/api/passwords/findByUserId")
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(Password[].class);

            Assertions.assertArrayEquals(expectedPasswords.toArray(), apiPasswords);
        }
    }

    @Test
    public void testFindByUserIdNoAuth() {
        RestAssured.given()
                .param("userId", users.getFirst().getId())
                .get("/api/passwords/findByUserId")
                .then()
                .statusCode(401);
    }

    @Test
    public void testFindByUserIdNotExist() {
        var apiPasswords = RestAssured.given()
                .auth()
                .basic("admin", "admin")
                .param("userId", 9999)
                .get("/api/passwords/findByUserId")
                .then()
                .statusCode(200)
                .extract()
                .as(Password[].class);

        Assertions.assertEquals(0, apiPasswords.length);
    }
}
