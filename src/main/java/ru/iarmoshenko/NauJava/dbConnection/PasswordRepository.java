package ru.iarmoshenko.NauJava.dbConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.iarmoshenko.NauJava.entity.Password;

import java.util.Date;
import java.util.List;

@Component
public class PasswordRepository implements CrudRepository<Password, Long> {
    private final List<Password> passwordRepository;

    @Autowired
    public PasswordRepository(List<Password> passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    @Override
    public void create(Password password) {
        passwordRepository.add(password);
    }

    @Override
    public Password read(Long id) {
        return passwordRepository.stream()
                .filter(pass -> pass.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Password newPassword) {
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

    public List<Password> getUserPasswords(Long userId) {
        return passwordRepository.stream()
                .filter(pass -> pass.getUserId().equals(userId))
                .toList();
    }
}
