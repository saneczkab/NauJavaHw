package ru.iarmoshenko.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.iarmoshenko.NauJava.entity.User;

import java.util.List;

@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByUsernameOrEmail(String username, String email);
}
