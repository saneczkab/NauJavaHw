package ru.iarmoshenko.NauJava.service;

import ru.iarmoshenko.NauJava.entity.User;

import java.util.Optional;

/**
 * Сервис для работы с пользователями.
 * Определяет основные операции управления пользователями.
 */
public interface UserService {
  /**
   * Удаляет пользователя по его идентификатору, если запрос сделан администратором.
   * Нельзя удалить пользователя с правами администратора.
   *
   * @param id id пользователя для удаления
   * @param requesterUsername никнейм пользователя, делающего запрос
   * @throws IllegalAccessException если запрос сделан не администратором
   */
  void deleteUserById(Integer id, String requesterUsername) throws IllegalAccessException;

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
     * Получает всех пользователей, если запрос сделан администратором.
     * @param getterUsername никнейм пользователя, делающего запрос
     * @return список всех пользователей
     * @throws IllegalAccessException если пользователь не администратор
     */
    Iterable<User> getAllUsers(String getterUsername) throws IllegalAccessException;

    /**
     * Устанавливает роль администратора для пользователя по его userId, если запрос сделан администратором.
     * @param userId id пользователя, которому устанавливается роль
     * @param requesterUsername никнейм пользователя, делающего запрос
     * @throws IllegalAccessException если пользователь не администратор
     */
    void setUserRoleAdmin(int userId, String requesterUsername) throws IllegalAccessException;

    /**
     * Устанавливает роль администратора для пользователя по его id, если запрос сделан администратором.
     * @param userId id пользователя, которому устанавливается роль
     * @param requesterUsername никнейм пользователя, делающего запрос
     * @throws IllegalAccessException если пользователь не администратор
     */
    void setUserRoleUser(int userId, String requesterUsername) throws IllegalAccessException;
}