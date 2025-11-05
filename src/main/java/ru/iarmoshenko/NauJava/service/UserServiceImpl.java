package ru.iarmoshenko.NauJava.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.iarmoshenko.NauJava.entity.User;
import ru.iarmoshenko.NauJava.repository.PasswordRepository;
import ru.iarmoshenko.NauJava.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;
    private final PlatformTransactionManager transactionManager;

    public UserServiceImpl(UserRepository userRepository, PasswordRepository passwordRepository,
                           PlatformTransactionManager transactionManager) {
        this.userRepository = userRepository;
        this.passwordRepository = passwordRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void deleteUserById(Integer id) {
        var status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            var passwords = passwordRepository.findByUserId(id);
            passwordRepository.deleteAll(passwords);
            userRepository.deleteById(id);
            transactionManager.commit(status);
        }
        catch (DataAccessException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        var status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            var user = userRepository.findByUsernameOrEmail(username, null)
                    .stream().findFirst().orElse(null);
            transactionManager.commit(status);
            return user;
        }
        catch (DataAccessException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    @Override
    public void createUser(String username, String email, String password) {
        var status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            var user = new User(username, email, password);
            userRepository.save(user);
            transactionManager.commit(status);
        }
        catch (DataAccessException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
