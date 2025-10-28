package ru.iarmoshenko.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.iarmoshenko.NauJava.entity.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByUsernameOrEmail(String username, String email);
}
