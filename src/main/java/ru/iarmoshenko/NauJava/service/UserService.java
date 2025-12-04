package ru.iarmoshenko.NauJava.service;

import ru.iarmoshenko.NauJava.entity.User;

import java.util.Optional;

/**
 * Сервис для работы с пользователями.
 * Определяет основные операции управления пользователями.
 */
public interface UserService {
    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя для удаления
     */
    void deleteUserById(Integer id);

    /**
     * Получает пользователя по его имени пользователя.
     *
     * @param username имя пользователя для поиска
     * @return объект пользователя
     */
    User getUserByUsername(String username);

    /**
     * Создает нового пользователя.
     *
     * @param username имя пользователя
     * @param email email пользователя
     * @param password пароль пользователя
     */
    void createUser(String username, String email, String password);
}