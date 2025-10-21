package ru.iarmoshenko.NauJava.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.iarmoshenko.NauJava.entity.LegacyPassword;

import java.util.Date;
import java.util.List;

@Component
public class LegacyPasswordRepository implements LegacyCrudRepository<LegacyPassword, Long> {
    private final List<LegacyPassword> passwordRepository;

    @Autowired
    public LegacyPasswordRepository(List<LegacyPassword> passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    @Override
    public void create(LegacyPassword password) {
        passwordRepository.add(password);
    }

    @Override
    public LegacyPassword read(Long id) {
        return passwordRepository.stream()
                .filter(pass -> pass.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(LegacyPassword newPassword) {
        var password = read(newPassword.getId());

        if (password != null) {
            password.setUserId(newPassword.getUserId());
            password.setEncryptedPassword(newPassword.getEncryptedPassword());
            password.setContent(newPassword.getContent());
            password.setSalt(newPassword.getSalt());
            password.setLength(newPassword.getLength());
            password.setUpdatedAt(new Date());
        }
//        else {
//            create(newPassword);
//        }
    }

    @Override
    public void delete(Long id) {
        passwordRepository.removeIf(password -> password.getId().equals(id));
    }

    public List<LegacyPassword> getUserPasswords(Long userId) {
        return passwordRepository.stream()
                .filter(pass -> pass.getUserId().equals(userId))
                .toList();
    }
}
