package ru.iarmoshenko.NauJava.service;

public interface ReportService {
    String getReportContentById(int id);
    int createReport();
    void generateReport(int id);
}
