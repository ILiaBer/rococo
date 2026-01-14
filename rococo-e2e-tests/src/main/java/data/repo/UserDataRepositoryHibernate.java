package data.repo;

import config.Config;
import data.entities.AuthUserEntity;
import data.entities.UserDataEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserDataRepositoryHibernate extends BaseRepository {

    public UserDataRepositoryHibernate() {
        super(Config.getInstance().userdataJdbcUrl());
    }

    @Transactional
    public void createUserInUserData(UserDataEntity user) {
        tx.execute(() -> em.persist(user));
//        em.flush(); // <-- заставляет Hibernate сходить в БД
    }

//    @Transactional
//    public void deleteUserFromUserData(String username) {
//        UserDataEntity user = em.find(UserDataEntity.class, username);
//        if (user != null) {
//            tx.execute(() -> em.remove(user));
//        } else {
//            throw new EntityNotFoundException("User " + username + " not found.");
//        }
//    }
//
//    @Transactional
//    public void deleteUser(String username) {
//        List<AuthUserEntity> users = em.createQuery("SELECT u FROM UserDataEntity u WHERE u.username = :username", AuthUserEntity.class)
//                .setParameter("username", username)
//                .getResultList();
//
//        if (!users.isEmpty()) {
//            AuthUserEntity user = users.get(0); // Получаем первого пользователя из списка
//            tx.execute(() -> em.remove(user));
//        } else {
//            throw new EntityNotFoundException("User " + username + " not found.");
//        }
//    }
}
