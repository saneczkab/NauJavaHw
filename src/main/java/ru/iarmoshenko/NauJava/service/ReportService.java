package ru.iarmoshenko.NauJava.service;

public interface ReportService {
    /**
     * Генерация отчета.
     * Содержимое: количество пользователей, количество паролей, время выполнения.
     * @return Отчет в виде строки.
     */
    String generateReport();
}
