package ru.iarmoshenko.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.iarmoshenko.NauJava.entity.Password;

public interface PasswordRepository extends CrudRepository<Password, Integer> {
}
