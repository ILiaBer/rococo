package web;

import jupiter.annotations.User;
import jupiter.annotations.WebTest;
import model.UserJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import utils.InputGenerators;

@WebTest
@Execution(ExecutionMode.SAME_THREAD)
public class LoginTests extends BaseUiTest {

    @Test
    @User
    @DisplayName("Успешная авторизация")
    public void successfulLoginTest(UserJson user) {
        mainPage()
                .login(user)
                .profileIcon.checkVisible()
                .loginBtn.checkNotVisible();

    }

    @Test
    @User
    @DisplayName("Авторизация с неверным паролем и существующим username")
    public void authorizationWithWrongPasswordTest(UserJson user) {
        mainPage()
                .login(user.username(), InputGenerators.randomPassword())
                .profileIcon.checkNotVisible()
                .error.checkText("Неверные учетные данные пользователя");
    }

    @Test
    @User
    @DisplayName("Проверка логаута")
    public void checkLogout(UserJson user) {
        mainPage()
                .login(user)
                .profileIcon.click()
                .profileModal().logout.click();
        mainPage()
                .profileIcon.checkNotVisible()
                .loginBtn.checkVisible();
    }
}