package data.repo;

import config.Config;
import data.entities.UserDataEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserDataRepositoryHibernate extends BaseRepository {

    public UserDataRepositoryHibernate() {
        super(Config.getInstance().userdataJdbcUrl());
    }

    @Transactional
    public void createUserInUserData(UserDataEntity user) {
        tx.execute(() -> em.persist(user));
    }

    public List<UserDataEntity> searchUser(String username) {
        return em.createQuery(
                        "SELECT u FROM UserDataEntity u WHERE u.username = :username",
                        UserDataEntity.class)
                .setParameter("username", username)
                .getResultList();
    }
}