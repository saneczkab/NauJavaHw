package ru.iarmoshenko.NauJava.service;

import ru.iarmoshenko.NauJava.entity.*;

import java.util.List;

/**
 * Сервис для работы с паролями.
 * Определяет операции генерации, шифрования, дешифрования и управления паролями.
 */
public interface PasswordService {
    /**
     * Генерирует новый пароль с заданными параметрами.
     *
     * @param count количество паролей для генерации
     * @param length длина пароля
     * @param content тип контента (набор символов)
     * @param username идентификатор пользователя
     * @return сгенерированные пароли в расшифрованном виде (список строк)
     */
    List<String> generatePassword(int count, int length, ContentType content, String username);

    /**
     * Шифрует пароль с использованием соли.
     *
     * @param password пароль для шифрования
     * @param salt соль для усиления шифрования
     * @return зашифрованный пароль в виде массива байт
     */
    byte[] encryptPassword(String password, String salt);

    /**
     * Дешифрует пароль с использованием соли.
     *
     * @param encryptedPassword зашифрованный пароль
     * @param salt соль, использованная при шифровании
     * @return расшифрованный пароль в виде строки
     */
    String decryptPassword(byte[] encryptedPassword, String salt);

    /**
     * Получает все пароли пользователя.
     *
     * @param username идентификатор пользователя
     * @return список паролей пользователя.
     * Каждый object[] содержит:
     * [0] - id пароля
     * [1] - расшифрованный пароль
     * [2] - время создания пароля
     */
    List<Object[]> getUserPasswords(String username);

    /**
     * Сохраняет пароль в хранилище.
     *
     * @param password объект пароля для сохранения
     */
    void savePassword(Password password);

    /**
     * Удаляет пароль по его идентификатору.
     *
     * @param passId идентификатор пароля для удаления
     * @param username имя пользователя, которому принадлежит пароль
     */
    void deletePassword(int passId, String username);
}