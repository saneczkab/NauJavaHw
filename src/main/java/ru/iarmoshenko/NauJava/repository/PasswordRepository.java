package ru.iarmoshenko.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.iarmoshenko.NauJava.entity.Password;

import java.util.List;

/**
 * Репозиторий для работы с паролями.
 * Предоставляет CRUD операции и автоматически экспонируется как REST API.
 */
@RepositoryRestResource(path = "passwords")
public interface PasswordRepository extends CrudRepository<Password, Integer> {
    /**
     * Находит все пароли, связанные с указанным пользователем.
     *
     * @param userId идентификатор пользователя
     * @return список паролей пользователя
     */
    @Query("select pass from Password pass where pass.user.id = :userId")
    List<Password> findByUserId(Integer userId);
}