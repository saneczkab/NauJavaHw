package ru.iarmoshenko.NauJava.service;

import ru.iarmoshenko.NauJava.entity.User;

import java.util.Optional;

public interface UserService {
    void deleteUserById(Integer id);
    User getUserByUsername(String username);
    void createUser(String username, String email, String password);
}
