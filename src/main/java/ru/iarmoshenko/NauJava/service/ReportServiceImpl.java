package ru.iarmoshenko.NauJava.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import ru.iarmoshenko.NauJava.entity.Report;
import ru.iarmoshenko.NauJava.entity.ReportStatus;
import ru.iarmoshenko.NauJava.repository.PasswordRepository;
import ru.iarmoshenko.NauJava.repository.ReportRepository;
import ru.iarmoshenko.NauJava.repository.UserRepository;

import java.util.concurrent.CompletableFuture;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;

    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository,
                             PasswordRepository passwordRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.passwordRepository = passwordRepository;
    }

    @Override
    public String getReportContentById(int id) {
        var report = reportRepository.findById(id);
        return report.map(Report::getContent).orElse(null);
    }

    @Override
    public int createReport() {
        var report = reportRepository.save(new Report());
        return report.getId();
    }

    @Override
    public void generateReport(int id) {
        CompletableFuture.runAsync(() -> {
            var reportDb = reportRepository.findById(id);
            if (reportDb.isEmpty()){
                return;
            }
            var report = reportDb.get();

            var totalWatch = new StopWatch();
            totalWatch.start();
            var reportContent = new StringBuilder();
            var threads = getReportThreads(reportContent);
            for (var thread : threads){
                thread.start();
            }

            for (var thread : threads){
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    report.setStatus(ReportStatus.FAILED);
                    reportRepository.save(report);
                    return;
                }
            }

            totalWatch.stop();
            reportContent.append("Total time elapsed: ").append(totalWatch.getTotalTimeMillis()).append(" ms");
            report.setContent(reportContent.toString());

            report.setStatus(ReportStatus.FINISHED);
            reportRepository.save(report);
        });
    }

    private Thread[] getReportThreads(StringBuilder report) {
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

            report.append("User count: ").append(userCount).append(", time elapsed: ")
                    .append(userCountWatch.getTotalTimeMillis()).append(" ms<br/>");
        });
    }

    private Thread getPasswordThread(StringBuilder report) {
        return new Thread(() -> {
            var result = new StringBuilder();
            var passwordWatch = new StopWatch();
            passwordWatch.start();
            var passwords = passwordRepository.findAll();
            passwordWatch.stop();

            result.append("Passwrods:<br/>");
            for (var password : passwords){
                result.append(password.toString());
            }
            result.append("Time elapsed: ").append(passwordWatch.getTotalTimeMillis()).append(" ms<br/>");

            report.append(result);
        });
    }
}
