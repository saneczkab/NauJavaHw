package ru.iarmoshenko.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iarmoshenko.NauJava.entity.Algorithm;
import ru.iarmoshenko.NauJava.entity.Content;
import ru.iarmoshenko.NauJava.entity.Password;
import ru.iarmoshenko.NauJava.repository.PasswordRepository;
import ru.iarmoshenko.NauJava.repository.UserRepository;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PasswordServiceImpl implements PasswordService {
    private final PasswordRepository passwordRepository;
    private final UserRepository userRepository;
    private SecretKey secretKey;
    private Cipher cipher;

    @Autowired
    public PasswordServiceImpl(PasswordRepository passwordRepository, UserRepository userRepository) {
        this.passwordRepository = passwordRepository;
        this.userRepository = userRepository;
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

    private String generateRawPassword(int length, Content content) {
        var chars = content.getUsedSymbols();

        var result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * chars.length());
            result.append(chars.charAt(randomIndex));
        }

        return result.toString();
    }

    @Override
    public String generatePassword(int length, Content content, Algorithm algorithm, Integer userId) {
        var rawPassword = generateRawPassword(length, content);
        var user = userRepository.findById(userId).orElseThrow();
        var salt = Long.toHexString(Double.doubleToLongBits(Math.random()));
        var encryptedPassword = encryptPassword(rawPassword, salt);
        var updateAt = LocalDateTime.now();

        var password = new Password(user, encryptedPassword ,content, algorithm, salt, length, updateAt);
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
    public List<Password> getPasswordById(Integer id) {
        return passwordRepository.findByUserId(id);
    }

    @Override
    public List<Password> getUserPasswords(Integer userId) {
        return passwordRepository.findByUserId(userId);
    }

    @Override
    public void savePassword(Password password) {
        passwordRepository.save(password);
    }

    @Override
    public void deletePassword(Integer id) {
        passwordRepository.deleteById(id);
    }
}
