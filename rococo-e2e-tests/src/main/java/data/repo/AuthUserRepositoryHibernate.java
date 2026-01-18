package data.repo;

import config.Config;
import data.entities.AuthUserEntity;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class AuthUserRepositoryHibernate extends BaseRepository {
    private static final PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public AuthUserRepositoryHibernate() {
        super(Config.getInstance().authJdbcUrl());
    }

    @Transactional
    public void createUser(AuthUserEntity user) {
        user.setPassword(pe.encode(user.getPassword()));
        tx.execute(() -> em.persist(user));
    }

    @Transactional
    public void deleteUser(String username) {
        List<AuthUserEntity> users = em.createQuery("SELECT u FROM AuthUserEntity u WHERE u.username = :username", AuthUserEntity.class)
                .setParameter("username", username)
                .getResultList();

        if (!users.isEmpty()) {
            AuthUserEntity user = users.get(0);
            tx.execute(() -> em.remove(user));
        } else {
            throw new EntityNotFoundException("User " + username + " not found.");
        }
    }

    private List<AuthUserEntity> searchUser(String username) {
        return em.createQuery(
                        "SELECT u FROM AuthUserEntity u WHERE u.username = :username",
                        AuthUserEntity.class)
                .setParameter("username", username)
                .getResultList();
    }

    @Transactional
    public void checkUserExist(String username) {
        List<AuthUserEntity> users = searchUser(username);
        if (users.isEmpty()) {
            throw new EntityNotFoundException("User " + username + " not found.");
        }
    }

    @Transactional
    public void checkUserNotExist(String username) {
        List<AuthUserEntity> users = searchUser(username);
        if (!users.isEmpty()) {
            throw new EntityExistsException("User " + username + " exists.");
        }
    }
}
