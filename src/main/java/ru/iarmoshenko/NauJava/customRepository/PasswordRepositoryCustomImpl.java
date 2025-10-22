package ru.iarmoshenko.NauJava.customRepository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.iarmoshenko.NauJava.entity.Password;

import java.util.List;

@Repository
public class PasswordRepositoryCustomImpl implements PasswordRepositoryCustom {
    private final EntityManager entityManager;

    @Autowired
    public PasswordRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Password> findByUserId(Integer userId) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Password.class);
        var passwordRoot = criteriaQuery.from(Password.class);

        var userIdPredicate = criteriaBuilder.equal(passwordRoot.get("userId"), userId);
        criteriaQuery.select(passwordRoot).where(userIdPredicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
