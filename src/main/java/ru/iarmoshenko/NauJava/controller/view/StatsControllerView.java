package ru.iarmoshenko.NauJava.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.iarmoshenko.NauJava.repository.PasswordRepository;
import ru.iarmoshenko.NauJava.repository.UserRepository;

@Controller
@RequestMapping("/view/stats")
public class StatsControllerView {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @GetMapping("")
    public String statsView(Model model) {
        var totalWatch = new StopWatch();
        totalWatch.start();

        var threads = createThreads(model);

        for (var thread : threads){
            thread.start();
        }

        for (var thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        totalWatch.stop();
        model.addAttribute("totalTimeElapsed", totalWatch.getTotalTimeMillis());
        return "stats";
    }

    private Thread[] createThreads(Model model) {
        var userCountThread = getUserCountThread(model);
        var passwordThread = getPasswordThread(model);

        return new Thread[] { userCountThread, passwordThread };
    }

    private Thread getUserCountThread(Model model) {
        return new Thread(() -> {
            var userCountWatch = new StopWatch();
            userCountWatch.start();
            var userCount = userRepository.count();
            userCountWatch.stop();

            model.addAttribute("userCount", userCount);
            model.addAttribute("userCountTimeElapsed", userCountWatch.getTotalTimeMillis());
        });
    }

    private Thread getPasswordThread(Model model) {
        return new Thread(() -> {
            var passwordWatch = new StopWatch();

            passwordWatch.start();
            var passwords = passwordRepository.findAll();
            passwordWatch.stop();

            model.addAttribute("passwords", passwords);
            model.addAttribute("passwordsTimeElapsed", passwordWatch.getTotalTimeMillis());
        });
    }
}
