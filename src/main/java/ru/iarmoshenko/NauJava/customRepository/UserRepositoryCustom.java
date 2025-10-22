package ru.iarmoshenko.NauJava.customRepository;

import ru.iarmoshenko.NauJava.entity.User;

public interface UserRepositoryCustom {
    User findByUsernameOrEmail(String username, String email);
}
