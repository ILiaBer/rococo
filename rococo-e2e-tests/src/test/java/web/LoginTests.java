package web;

import jupiter.annotations.User;
import jupiter.annotations.WebTest;
import model.UserJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ui.BaseUiTest;
import utils.InputGenerators;

@WebTest
@Execution(ExecutionMode.SAME_THREAD)
public class LoginTests extends BaseUiTest {

    @Test
    @User
    @DisplayName("Успешная авторизация")
    public void successfulLoginTest(UserJson user) {
        commonSteps().login(user);
        mainPage()
                .profileIcon.checkVisible()
                .loginBtn.checkNotVisible();
    }

    @Test
    @User
    @DisplayName("Авторизация с неверным паролем и существующим username")
    public void authorizationWithWrongPasswordTest(UserJson user) {
        commonSteps().login(user.username(), InputGenerators.randomPassword());
        mainPage().profileIcon.checkNotVisible();
        loginPage().error.checkText("Неверные учетные данные пользователя");
    }

    @Test
    @User
    @DisplayName("Проверка логаута")
    public void checkLogout(UserJson user) {
        commonSteps().login(user);
        mainPage()
                .profileIcon.click()
                .profileModal().logout.click();
        mainPage()
                .profileIcon.checkNotVisible()
                .loginBtn.checkVisible();
    }

    @Test
    @User
    @DisplayName("Попытка авторизации с несуществующим пользователем")
    public void loginWithNonExistingUserTest(UserJson user) {
        UserJson fakeUser = new UserJson(InputGenerators.randomUsername(), InputGenerators.randomPassword());
        commonSteps().login(fakeUser);
        loginPage().error.checkText("Неверные учетные данные пользователя");
        mainPage().profileIcon.checkNotVisible();
    }

    @Test
    @User
    @DisplayName("Логин с пустым username")
    public void loginWithEmptyUsernameTest(UserJson user) {
        commonSteps().login("", user.testData().password());
        mainPage().profileIcon.checkNotVisible();
        loginPage()
                .usernameInput.checkVisible()
                .passwordInput.checkVisible();
    }

    @Test
    @User
    @DisplayName("Логин с пустым паролем")
    public void loginWithEmptyPasswordTest(UserJson user) {
        commonSteps().login(user.username(), "");
        mainPage().profileIcon.checkNotVisible();
        loginPage()
                .usernameInput.checkVisible()
                .passwordInput.checkVisible();
    }

    @Test
    @User
    @DisplayName("Авторизация с пробелами")
    public void loginWithWhitespaceTest(UserJson user) {
        commonSteps().login("  " + user.username() + "  ", "  " + user.testData().password() + "  ");
        loginPage().error.checkText("Неверные учетные данные пользователя");
        mainPage().profileIcon.checkNotVisible();
    }
}