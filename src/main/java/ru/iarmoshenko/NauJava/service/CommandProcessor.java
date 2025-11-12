package ru.iarmoshenko.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.iarmoshenko.NauJava.entity.LegacyContent;

@Component
public class CommandProcessor
{
    private final PasswordService passwordService;
    private final long userId = 1L; // Заглушка, пока нет бд

    @Autowired
    public CommandProcessor(PasswordService passwordService)
    {
        this.passwordService = passwordService;
    }

    public void processCommand(String input)
    {
        var cmd = input.split(" ");

        switch (cmd[0])
        {
            case "help" -> printHelp();
            case "new" -> newCommand(cmd);
            case "list" -> listCommand();
            case "delete" -> deleteCommand(cmd);
            default -> System.out.println("Неизвестная команда. Введите 'help' для помощи");
        }
    }

    private void printHelp()
    {
        System.out.println("Доступные команды:");

        System.out.println("new <length> <content> - Создать новый пароль длины length с типом содержимого content");
        System.out.println("    Типы содержимого:");
        System.out.println("    letters - только буквы");
        System.out.println("    digits - только цифры");
        System.out.println("    symbols - только спецсимволы");
        System.out.println("    ld - буквы и цифры");
        System.out.println("    ls - буквы и спецсимволы");
        System.out.println("    ds - цифры и спецсимволы");
        System.out.println("    mix - буквы, цифры и спецсимволы");

        System.out.println("list - Показать историю созданных паролей");
        System.out.println("delete <id> - Удалить из истории пароль по id");
        System.out.println("help - Помощь");
        System.out.println("exit - Выход из программы");
    }

    private void deleteCommand(String[] cmd) {
        if (cmd.length != 2) {
            System.out.println("Неверное количество аргументов. Использование: delete <id>");
            return;
        }

        var strId = cmd[1];
        if (!strId.matches("\\d+")) {
            System.out.println("ID должен быть числом!");
            return;
        }

        var id = Long.parseLong(strId);
        var password = passwordService.getPasswordById(id);

        if (password == null || !password.getUserId().equals(userId)) {
            System.out.println("Пароль с таким ID не найден.");
            return;
        }

        passwordService.deletePassword(id);
        System.out.println("Пароль с ID " + id + " успешно удалён!");
    }

    private void listCommand() {
        var passwords = passwordService.getUserPasswords(userId);

        for (var pass : passwords) {
            var encrypted = pass.getEncryptedPassword();
            var salt = pass.getSalt();
            var decrypted = passwordService.decryptPassword(encrypted, salt);
            var id = pass.getId();
            var updatedAt = pass.getUpdatedAt();

            System.out.printf("ID: %d | Пароль: %s | Обновлён: %s%n", id, decrypted, updatedAt);
        }
    }

    private void newCommand(String[] cmd) {
        if (cmd.length != 3) {
            System.out.println("Неверное количество аргументов. Использование: new <length> <content>");
            return;
        }

        var lenStr = cmd[1];
        if (!lenStr.matches("\\d+")) {
            System.out.println("Длина пароля должна быть числом!");
            return;
        }

        var length = Integer.parseInt(lenStr);
        var contentStr = cmd[2].toLowerCase();
        var content = parseContent(contentStr);

        if (content == null) {
            System.out.println("Неверный тип содержимого. Введите 'help' для помощи");
            return;
        }

        if (length < 1) {
            System.out.println("Длина пароля должна быть положительным числом!");
            return;
        }

        var password = passwordService.generatePassword(length, content, userId);
        System.out.println("Сгенерированный пароль: " + password);
        System.out.println("Пароль сохранён. История созданных паролей: 'list'");

    }

    private LegacyContent parseContent(String contentStr) {
        return switch (contentStr) {
            case "letters" -> LegacyContent.LETTERS;
            case "digits" -> LegacyContent.DIGITS;
            case "symbols" -> LegacyContent.SYMBOLS;
            case "ld" -> LegacyContent.LETTERS_DIGITS;
            case "ls" -> LegacyContent.LETTERS_SYMBOLS;
            case "ds" -> LegacyContent.DIGITS_SYMBOLS;
            case "mix" -> LegacyContent.MIXED;
            default -> null;
        };
    }
}