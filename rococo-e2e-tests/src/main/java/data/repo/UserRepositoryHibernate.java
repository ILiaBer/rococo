package data.repo;


import data.entities.AuthUserEntity;
import data.entities.UserDataEntity;
import model.UserJson;

import java.util.Optional;

public class UserRepositoryHibernate {
    private final AuthUserRepositoryHibernate authUserDAO;
    private final UserDataRepositoryHibernate userDataDAO;

    public UserRepositoryHibernate() {
        this(new AuthUserRepositoryHibernate(), new UserDataRepositoryHibernate());
    }

    public UserRepositoryHibernate(AuthUserRepositoryHibernate authUserDAO, UserDataRepositoryHibernate userDataDAO) {
        this.authUserDAO = authUserDAO;
        this.userDataDAO = userDataDAO;
    }

    public void createUser(AuthUserEntity user) {
        this.authUserDAO.createUser(user);
        this.userDataDAO.createUserInUserData(fromAuthUser(user));
    }

    public void deleteUser(String username) {
        this.authUserDAO.deleteUser(username);
    }

    public void deleteUser(UserJson user) {
        deleteUser(user.username());
    }

    public void checkUserExist(String username) {
        this.authUserDAO.checkUserExist(username);
    }

    public UserDataEntity getUser(String username) {
        return this.userDataDAO.searchUser(username).stream().findFirst().orElseThrow();
    }

    public void checkUserExist(UserJson user) {
        checkUserExist(user.username());
    }

    public void checkUserNotExist(String username) {
        this.authUserDAO.checkUserNotExist(username);
    }

    public void checkUserNotExist(UserJson user) {
        checkUserNotExist(user.username());
    }

    private UserDataEntity fromAuthUser(AuthUserEntity user) {
        UserDataEntity userData = new UserDataEntity();
        userData.setUsername(user.getUsername());
        return userData;
    }
}
