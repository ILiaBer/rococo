package web;

import com.codeborne.selenide.Selenide;
import model.TestData;
import model.UserJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.BaseUiTest;
import utils.InputGenerators;

public class RegistrationTests extends BaseUiTest {

    @Test
    @DisplayName("Юзер может быть зарегестрирован")
    public void userCanBeRegisteredTest() {
        UserJson user = UserJson.getRandomUser();
        Selenide.open(CFG.frontUrl());
        mainPage().loginBtn.click();
        loginPage().registerBtn.click();
        registerPage().register(user);
        commonSteps().login(user);
        mainPage()
                .profileIcon.checkVisible()
                .loginBtn.checkNotVisible();
        userRepositoryHibernate.deleteUser(user);
    }

    @Test
    @DisplayName("Юзер может быть зарегестрирован c зернеймом на кириллице")
    public void userCanBeRegisteredWithCyrillicUsernameTest() {
        UserJson user = new UserJson(null, InputGenerators.randomCyrillicUsername(), null, null,
                null, new TestData(InputGenerators.randomPassword()));
        Selenide.open(CFG.frontUrl());
        mainPage().loginBtn.click();
        loginPage().registerBtn.click();
        registerPage().register(user);
        userRepositoryHibernate.checkUserExist(user);
        userRepositoryHibernate.deleteUser(user);
    }

    @Test
    @DisplayName("Нельзя зарегерстрировать юзера с разными паролями")
    public void userCantBeRegisteredWithNonEqualPasswordsTest() {
        UserJson user = UserJson.getRandomUser();
        Selenide.open(CFG.frontUrl());
        mainPage().loginBtn.click();
        loginPage().registerBtn.click();
        registerPage()
                .usernameInput.fill(user.username())
                .passwordInput.fill(user.testData().password())
                .passwordSubmitInput.fill(InputGenerators.randomPassword())
                .submitBtn.click()
                .error.checkText("Passwords should be equal");
        userRepositoryHibernate.checkUserNotExist(user);
    }

    @Test
    @DisplayName("Нельзя зарегерстрировать юзера без юзернэйма")
    public void userCantBeRegisteredWithoutUsernameTest() {
        UserJson user = UserJson.getRandomUser();
        Selenide.open(CFG.frontUrl());
        mainPage().loginBtn.click();
        loginPage().registerBtn.click();
        registerPage()
                .passwordInput.fill(user.testData().password())
                .passwordSubmitInput.fill(user.testData().password())
                .submitBtn.click();
        userRepositoryHibernate.checkUserNotExist(user);
    }
}