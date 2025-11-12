package ru.iarmoshenko.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iarmoshenko.NauJava.repository.LegacyPasswordRepository;
import ru.iarmoshenko.NauJava.entity.LegacyContent;
import ru.iarmoshenko.NauJava.entity.LegacyPassword;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Service
public class PasswordServiceImpl implements PasswordService {
    private final LegacyPasswordRepository passwordRepository;
    private SecretKey secretKey;
    private Cipher cipher;

    @Autowired
    public PasswordServiceImpl(LegacyPasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
        prepareCipher();
    }

    private void prepareCipher() {
        try {
            var algorithm = "AES";
            var keyGen = KeyGenerator.getInstance(algorithm);
            keyGen.init(128);
            secretKey = keyGen.generateKey();
            cipher = Cipher.getInstance(algorithm);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateRawPassword(int length, LegacyContent content) {
        var letters = "abcdefghijklmnopqrstuvwxyzABCDEFGIHJKLMNOPQRSTUVWXYZ";
        var digits = "0123456789";
        var symbols = "!@#$%^&*()_-+={[]};:'\",<.>/?\\|~`";
        var chars = switch (content) {
            case LETTERS -> letters;
            case DIGITS -> digits;
            case SYMBOLS -> symbols;
            case LETTERS_DIGITS -> letters + digits;
            case LETTERS_SYMBOLS -> letters + symbols;
            case DIGITS_SYMBOLS -> digits + symbols;
            case MIXED -> letters + digits + symbols;
        };

        var result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * chars.length());
            result.append(chars.charAt(randomIndex));
        }

        return result.toString();
    }

    @Override
    public String generatePassword(int length, LegacyContent content, long userId) {
        var rawPassword = generateRawPassword(length, content);
        var salt = Long.toHexString(Double.doubleToLongBits(Math.random()));
        var encryptedPassword = encryptPassword(rawPassword, salt);
        var newPasswordId = new Date().getTime(); // Заглушка, пока нет бд

        var password = new LegacyPassword(newPasswordId, userId, encryptedPassword, content, salt, length);
        savePassword(password);

        return rawPassword;
    }

    @Override
    public byte[] encryptPassword(String password, String salt) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal((password + salt).getBytes());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String decryptPassword(byte[] encryptedPassword, String salt) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            var decryptedBytes = cipher.doFinal(encryptedPassword);
            var decryptedSaltedPass = new String(decryptedBytes);
            var passwordLength = decryptedSaltedPass.length() - salt.length();
            return decryptedSaltedPass.substring(0, passwordLength);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public LegacyPassword getPasswordById(Long id) {
        return passwordRepository.read(id);
    }

    @Override
    public List<LegacyPassword> getUserPasswords(Long userId) {
        return passwordRepository.getUserPasswords(userId);
    }

    @Override
    public void savePassword(LegacyPassword password) {
        passwordRepository.create(password);
    }

    @Override
    public void deletePassword(Long id) {
        passwordRepository.delete(id);
    }
}
