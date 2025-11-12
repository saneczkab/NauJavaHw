package ru.iarmoshenko.NauJava.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.iarmoshenko.NauJava.repository.UserRepository;

@Controller
@RequestMapping("/view/users")
public class UserControllerView {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("list")
    public String userListView(Model model) {
        var users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList";
    }
}
