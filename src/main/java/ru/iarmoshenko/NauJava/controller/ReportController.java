package ru.iarmoshenko.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.iarmoshenko.NauJava.service.ReportService;

/**
 * Контроллер для работы с отчетами.
 * */
@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    public ReportService reportService;

    /**
     * GET-запрос на создание и генерацию отчета.
     *
     * @return сообщение с id созданного отчета
     * */
    @GetMapping("/generate")
    public String generateReport() {
        var id = reportService.createReport();
        reportService.generateReport(id);
        return "Created report with ID: " + id;
    }

    /**
     * GET-запрос на получение содержимого отчета по его id.
     *
     * @param id - id отчета
     * @return содержимое отчета
     * */
    @GetMapping("/{id}")
    public String getReportById(@PathVariable Integer id) {
        return reportService.getReportContentById(id);
    }
}
