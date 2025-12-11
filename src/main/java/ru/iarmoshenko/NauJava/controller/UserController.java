package ru.iarmoshenko.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.iarmoshenko.NauJava.entity.User;
import ru.iarmoshenko.NauJava.repository.UserRepository;

import java.util.List;

/**
 * Контроллер для работы с пользователями.
 * */
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    /**
     * GET-запрос на получение списка пользователей по имени пользователя или email.
     * Пример: GET /api/users/findByUsernameOrEmail?email=white@cat.com
     * Пример: GET /api/users/findByUsernameOrEmail?username=water_fox
     * Пример: GET /api/users/findByUsernameOrEmail?email=white@cat.com&username=water_fox
     *
     * @param username - имя пользователя (необязательный параметр)
     * @param email - email пользователя (необязательный параметр)
     * @return список пользователей, соответствующих заданным параметрам (объекты User)
     * */
    @GetMapping("findByUsernameOrEmail")
    public List<User> findByUsernameOrEmail(
            @RequestParam(required = false) String username, @RequestParam(required = false) String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }
}
