package ru.iarmoshenko.NauJava.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Компонент для обработки консольного ввода.
 * Предоставляет интерфейс командной строки для взаимодействия с приложением.
 */
@Component
public class ConsoleListener {
    @Autowired
    private CommandProcessor commandProcessor;

    /**
     * Создает CommandLineRunner для сканирования ввода с консоли.
     * Метод закомментирован, но может быть активирован при необходимости.
     *
     * @return CommandLineRunner для обработки консольных команд
     */
    // @Bean
    // @PostConstruct
    public CommandLineRunner commandScanner()
    {
        return args ->
        {
            try (Scanner scanner = new Scanner(System.in))
            {
                System.out.println("Введите команду. 'help' - помощь.");
                while (true)
                {
                    System.out.print("> ");
                    String input = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(input.trim()))
                    {
                        System.out.println("Выход из программы...");
                        break;
                    }
                    commandProcessor.processCommand(input);
                    System.out.println();
                }
            }
        };
    }
}