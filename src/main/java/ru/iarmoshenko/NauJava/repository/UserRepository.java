package ru.iarmoshenko.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.iarmoshenko.NauJava.entity.User;

import java.util.List;

/**
 * Репозиторий для работы с пользователями.
 * Предоставляет CRUD операции и автоматически экспонируется как REST API.
 */
@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Integer> {
    /**
     * Находит пользователей по имени пользователя или email.
     *
     * @param username имя пользователя для поиска
     * @param email email для поиска
     * @return список пользователей, соответствующих критериям поиска
     */
    List<User> findByUsernameOrEmail(String username, String email);
}