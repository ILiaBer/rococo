package jupiter.extensions;


import data.entities.AuthUserEntity;
import data.repo.UserRepositoryHibernate;
import jakarta.persistence.EntityNotFoundException;
import jupiter.annotations.User;
import model.UserJson;
import org.junit.jupiter.api.extension.*;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static data.entities.AuthUserEntity.fillAuthUserEntity;
import static utils.InputGenerators.randomUsername;


@ParametersAreNonnullByDefault
public class UserExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UserExtension.class);
    private static final String DEFAULT_PW = "12345";

    private final UserRepositoryHibernate userRepositoryHibernate = new UserRepositoryHibernate();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        User userAnnotation = context.getRequiredTestMethod().getAnnotation(User.class);
        if (userAnnotation != null) {
            final String username = "".equals(userAnnotation.username()) ?
                    randomUsername() : userAnnotation.username();
            createAndSetUser(username);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return createdUser();
    }

    @Nullable
    public static UserJson createdUser() {
        final ExtensionContext context = TestMethodContextExtension.context();
        return context.getStore(NAMESPACE).get(
                context.getUniqueId(),
                UserJson.class
        );
    }

    public static void setUser(UserJson testUser) {
        final ExtensionContext context = TestMethodContextExtension.context();
        context.getStore(NAMESPACE).put(
                context.getUniqueId(),
                testUser
        );
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
//        User userAnnotation = context.getRequiredTestMethod().getAnnotation(User.class);
        // Получаем имя пользователя из созданного объекта UserJson
        UserJson createdUser = createdUser();
        if (createdUser != null) {
            userRepositoryHibernate.deleteUser(createdUser.username());
        } else {
            throw new EntityNotFoundException("User not found in the context.");
        }
    }

    private void createAndSetUser(String username) {
        AuthUserEntity user = fillAuthUserEntity(username, DEFAULT_PW);
        userRepositoryHibernate.createUser(user);
        setUser(UserJson.fromEntity(user));
    }
}
