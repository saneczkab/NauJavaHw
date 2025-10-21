package ru.iarmoshenko.NauJava.service;

import ru.iarmoshenko.NauJava.entity.LegacyContent;
import ru.iarmoshenko.NauJava.entity.LegacyPassword;

import java.util.List;

public interface PasswordService {
    String generatePassword(int length, LegacyContent content, long userId);
    byte[] encryptPassword(String password, String salt);
    String decryptPassword(byte[] encryptedPassword, String salt);
    List<LegacyPassword> getUserPasswords(Long userId);
    LegacyPassword getPasswordById(Long id);
    void savePassword(LegacyPassword password);
    void deletePassword(Long id);
}
