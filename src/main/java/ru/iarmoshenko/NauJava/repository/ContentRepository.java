package ru.iarmoshenko.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.iarmoshenko.NauJava.entity.Content;

/**
 * Репозиторий для работы с контентом (наборами символов).
 * Предоставляет CRUD операции и автоматически экспонируется как REST API.
 */
@RepositoryRestResource(path = "contents")
public interface ContentRepository extends CrudRepository<Content, Integer>  {
}