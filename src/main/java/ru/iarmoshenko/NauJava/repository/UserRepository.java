package ru.iarmoshenko.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.iarmoshenko.NauJava.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
