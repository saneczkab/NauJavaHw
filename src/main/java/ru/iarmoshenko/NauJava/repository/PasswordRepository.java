package ru.iarmoshenko.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.iarmoshenko.NauJava.entity.Password;

import java.util.List;

public interface PasswordRepository extends CrudRepository<Password, Integer> {
    @Query("select pass from Password pass where pass.user.id = :userId")
    List<Password> findByUserId(Integer userId);
}
