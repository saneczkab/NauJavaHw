package ru.iarmoshenko.NauJava.customRepository;

import ru.iarmoshenko.NauJava.entity.Password;

import java.util.List;

public interface PasswordRepositoryCustom {
    List<Password> findByUserId(Integer userId);
}
