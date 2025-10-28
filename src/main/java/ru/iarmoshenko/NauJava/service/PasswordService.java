package ru.iarmoshenko.NauJava.service;

import ru.iarmoshenko.NauJava.entity.Content;
import ru.iarmoshenko.NauJava.entity.Password;

import java.util.List;

public interface PasswordService {
    String generatePassword(int length, Content content, long userId);
    byte[] encryptPassword(String password, String salt);
    String decryptPassword(byte[] encryptedPassword, String salt);
    List<Password> getUserPasswords(Long userId);
    Password getPasswordById(Long id);
    void savePassword(Password password);
    void deletePassword(Long id);
}
