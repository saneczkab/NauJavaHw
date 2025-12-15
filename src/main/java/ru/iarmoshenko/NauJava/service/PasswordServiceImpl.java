package ru.iarmoshenko.NauJava.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.iarmoshenko.NauJava.entity.ContentType;
import ru.iarmoshenko.NauJava.entity.Password;
import ru.iarmoshenko.NauJava.repository.PasswordRepository;
import ru.iarmoshenko.NauJava.repository.UserRepository;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
public class PasswordServiceImpl implements PasswordService {
    private final PasswordRepository passwordRepository;
    private final UserRepository userRepository;
    private SecretKey secretKey;
    private Cipher cipher;

    @Value("${crypto.secret-key}")
    private String secretKeyValue;

    @PostConstruct
    private void initKey() {
        var decoded = Base64.getDecoder().decode(secretKeyValue);
        secretKey = new SecretKeySpec(decoded, "AES");
    }

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
            cipher = Cipher.getInstance(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateRawPassword(int length, ContentType content) {
        var chars = content.getSymbols();

        var result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * chars.length());
            result.append(chars.charAt(randomIndex));
        }

        return result.toString();
    }

    @Override
    public List<String> generatePassword(int count, int length, ContentType content, String username) {
        var user = userRepository.findByUsernameOrEmail(username, null).getFirst();
        List<String> generated = Collections.synchronizedList(new ArrayList<>());
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Thread t = new Thread(() -> {
                try {
                    var rawPassword = generateRawPassword(length, content);
                    var salt = Long.toHexString(Double.doubleToLongBits(Math.random()));
                    var cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    var encrypted = cipher.doFinal((rawPassword + salt).getBytes());
                    var passwordEntity = new Password(user, encrypted, content, salt, length, LocalDateTime.now()
                    );

                    savePassword(passwordEntity);
                    generated.add(rawPassword);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            threads.add(t);
        }

        threads.forEach(Thread::start);

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        return generated;
    }


    @Override
    public byte[] encryptPassword(String password, String salt) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal((password + salt).getBytes());
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Object[]> getUserPasswords(String username) {
        var user = userRepository
                .findByUsernameOrEmail(username, null)
                .getFirst();

        return passwordRepository.findByUserId(user.getId())
                .stream()
                .map(p -> new Object[]{
                        p.getId(),
                        decryptPassword(p.getEncryptedPassword(), p.getSalt()),
                        p.getUpdatedAt()
                })
                .toList();
    }

    @Override
    public void savePassword(Password password) {
        passwordRepository.save(password);
    }

    @Override
    public void deletePassword(int passId, String username) {
        var password = passwordRepository.findById(passId).orElseThrow();

        if (password.getUser().getUsername().equals(username)) {
            passwordRepository.deleteById(passId);
        }
    }
}
