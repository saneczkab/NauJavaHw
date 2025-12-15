package ru.iarmoshenko.NauJava.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.iarmoshenko.NauJava.service.ReportService;

@Controller
public class ReportControllerView {
    private final ReportService reportService;

    public ReportControllerView(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report")
    public String statsView(Model model) {
        var report = reportService.generateReport();
        model.addAttribute("report", report);
        return "report";
    }
}
