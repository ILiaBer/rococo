package data.repo;


import data.entities.AuthUserEntity;
import data.entities.UserDataEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

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

    private UserDataEntity fromAuthUser(AuthUserEntity user) {
        UserDataEntity userData = new UserDataEntity();
        userData.setUsername(user.getUsername());
        return userData;
    }
}
