package ru.iarmoshenko.NauJava.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import ru.iarmoshenko.NauJava.repository.PasswordRepository;
import ru.iarmoshenko.NauJava.repository.UserRepository;

@Service
public class ReportServiceImpl implements ReportService {
    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;

    public ReportServiceImpl(UserRepository userRepository, PasswordRepository passwordRepository) {
        this.userRepository = userRepository;
        this.passwordRepository = passwordRepository;
    }

    public String generateReport() {
        var report = new StringBuilder();
        var totalWatch = new StopWatch();
        totalWatch.start();

        var threads = createThreads(report);

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

        report.append("Total time elapsed: ").append(totalWatch.getTotalTimeMillis()).append(" ms.");

        return report.toString();
    }

    private Thread[] createThreads(StringBuilder report) {
        var userCountThread = getUserCountThread(report);
        var passwordThread = getPasswordThread(report);

        return new Thread[] { userCountThread, passwordThread };
    }

    private Thread getUserCountThread(StringBuilder report) {
        return new Thread(() -> {
            var userCountWatch = new StopWatch();
            userCountWatch.start();
            var userCount = userRepository.count();
            userCountWatch.stop();

            report.append("User count: ").append(userCount)
                  .append(", time elapsed: ").append(userCountWatch.getTotalTimeMillis()).append(" ms. ");
        });
    }

    private Thread getPasswordThread(StringBuilder report) {
        return new Thread(() -> {
            var passwordWatch = new StopWatch();

            passwordWatch.start();
            var passwordCount = passwordRepository.count();
            passwordWatch.stop();

            report.append("Password count: ").append(passwordCount)
                  .append(", time elapsed: ").append(passwordWatch.getTotalTimeMillis()).append(" ms. ");
        });
    }
}
