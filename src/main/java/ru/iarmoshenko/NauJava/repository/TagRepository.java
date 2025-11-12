package ru.iarmoshenko.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.iarmoshenko.NauJava.entity.Tag;

public interface TagRepository extends CrudRepository<Tag, Integer> {
}
