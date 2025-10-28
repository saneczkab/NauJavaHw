package ru.iarmoshenko.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.iarmoshenko.NauJava.entity.Tag;

@RepositoryRestResource(path = "tags")
public interface TagRepository extends CrudRepository<Tag, Integer> {
}
