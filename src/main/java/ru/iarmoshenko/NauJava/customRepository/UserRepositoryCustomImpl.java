package ru.iarmoshenko.NauJava.customRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import ru.iarmoshenko.NauJava.entity.User;

import java.util.List;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final EntityManager entityManager;

    @Autowired
    public UserRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findByUsernameOrEmail(String username, String email) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(User.class);
        var userRoot = criteriaQuery.from(User.class);

        var usernamePredicate = criteriaBuilder.equal(userRoot.get("username"), username);
        var emailPredicate = criteriaBuilder.equal(userRoot.get("email"), email);
        var finalPredicate = criteriaBuilder.or(usernamePredicate, emailPredicate);
        criteriaQuery.select(userRoot).where(finalPredicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
