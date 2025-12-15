package ru.iarmoshenko.NauJava.service;

import ru.iarmoshenko.NauJava.entity.User;

import java.util.Optional;

/**
 * Сервис для работы с пользователями.
 * Определяет основные операции управления пользователями.
 */
public interface UserService {
  /**
   * Удаляет пользователя по его id.
   * Нельзя удалить пользователя с правами администратора.
   *
   * @param id id пользователя для удаления
   * @throws IllegalAccessException при попытке удалить администратора
   */
  void deleteUserById(Integer id) throws IllegalAccessException;

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

    /**
     * Получает всех пользователей.
     * @return список всех пользователей
     */
    Iterable<User> getAllUsers();

    /**
     * Устанавливает роль администратора для пользователя по его userId.
     * @param userId id пользователя, которому устанавливается роль
     */
    void setUserRoleAdmin(int userId);

    /**
     * Устанавливает роль администратора для пользователя по его id.
     * @param userId id пользователя, которому устанавливается роль
     */
    void setUserRoleUser(int userId);
}