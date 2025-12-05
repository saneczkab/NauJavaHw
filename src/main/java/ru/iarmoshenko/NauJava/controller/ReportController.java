package ru.iarmoshenko.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.iarmoshenko.NauJava.service.ReportService;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    public ReportService reportService;

    @GetMapping("/generate")
    public String generateReport() {
        var id = reportService.createReport();
        reportService.generateReport(id);
        return "Created report with ID: " + id;
    }

    @GetMapping("/{id}")
    public String getReportById(@PathVariable Integer id) {
        return reportService.getReportContentById(id);
    }
}
