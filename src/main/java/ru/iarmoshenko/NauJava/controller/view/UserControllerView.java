package ru.iarmoshenko.NauJava.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.iarmoshenko.NauJava.service.UserService;

/**
 * Контроллер для управления представлениями, связанными с пользователями.
 */
@Controller
@RequestMapping
public class UserControllerView {
    private final UserService userService;

    public UserControllerView(UserService userService) {
        this.userService = userService;
    }

    /**
     * Отображает список всех пользователей.
     * @param model модель для передачи данных на страницу
     * @return имя представления для просмотра списка пользователей
     */
    @GetMapping("/users/list")
    public String userListView(Model model) {
        var users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "userList";
    }

//    /**
//     * Устанавливает роль администратора для пользователя по его id.
//     * @param userId id пользователя, которому устанавливается роль
//     * @param model модель для передачи данных на страницу
//     * @param principal информация о текущем пользователе
//     * @return перенаправление на страницу списка пользователей
//     */
//    @PostMapping("/user/{userId}/setRoleAdmin")
//    public String setUserRoleAdmin(@PathVariable int userId, Model model, Principal principal) {
//        var requesterUsername = principal.getName();
//        try {
//            userService.setUserRoleAdmin(userId, requesterUsername);
//        } catch (IllegalAccessException e) {
//            model.addAttribute("message", e.getMessage());
//        }
//
//        return "redirect:/users/list";
//    }
//
//    /**
//     * Устанавливает роль User для пользователя по его id.
//     * @param userId id пользователя, которому устанавливается роль
//     * @param model модель для передачи данных на страницу
//     * @param principal информация о текущем пользователе
//     * @return перенаправление на страницу списка пользователей
//     */
//    @PostMapping("/user/{userId}/setRoleUser")
//    public String setUserRoleUser(@PathVariable int userId, Model model, Principal principal) {
//        var requesterUsername = principal.getName();
//        try {
//            userService.setUserRoleUser(userId, requesterUsername);
//        } catch (IllegalAccessException e) {
//            model.addAttribute("message", e.getMessage());
//        }
//
//        return "redirect:/users/list";
//    }

    @PostMapping("/user/{userId}/delete")
    public String deleteUser(@PathVariable int userId, Model model) {
        try {
            userService.deleteUserById(userId);
        } catch (IllegalAccessException e) {
            model.addAttribute("message", e.getMessage());
        }

        return "redirect:/users/list";
    }
}
