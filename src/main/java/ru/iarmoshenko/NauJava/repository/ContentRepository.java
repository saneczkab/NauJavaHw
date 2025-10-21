package ru.iarmoshenko.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.iarmoshenko.NauJava.entity.Content;

public interface ContentRepository extends CrudRepository<Content, Integer>  {
}
