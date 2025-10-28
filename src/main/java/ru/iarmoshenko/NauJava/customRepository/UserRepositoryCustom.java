package ru.iarmoshenko.NauJava.customRepository;

import ru.iarmoshenko.NauJava.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findByUsernameOrEmail(String username, String email);
}
