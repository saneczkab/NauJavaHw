package ru.iarmoshenko.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.iarmoshenko.NauJava.entity.Content;

@RepositoryRestResource(path = "contents")
public interface ContentRepository extends CrudRepository<Content, Integer>  {
}
