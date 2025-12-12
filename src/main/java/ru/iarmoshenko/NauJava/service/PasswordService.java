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
     * @param length длина пароля
     * @param content тип контента (набор символов)
     * @param userId идентификатор пользователя
     * @return сгенерированный пароль в виде строки
     */
    List<String> generatePassword(int count, int length, ContentType content, Integer userId);

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
     * @param userId идентификатор пользователя
     * @return список паролей пользователя
     */
    List<Password> getUserPasswords(Integer userId);

    /**
     * Получает пароль по его идентификатору.
     *
     * @param id идентификатор пароля
     * @return объект пароля или null, если не найден
     */
    List<Password> getPasswordById(Integer id);

    /**
     * Сохраняет пароль в хранилище.
     *
     * @param password объект пароля для сохранения
     */
    void savePassword(Password password);

    /**
     * Удаляет пароль по его идентификатору.
     *
     * @param id идентификатор пароля для удаления
     */
    void deletePassword(Integer id);
}