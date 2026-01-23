package web;

import com.codeborne.selenide.Selenide;
import config.Config;
import model.TestData;
import model.UserJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.BaseUiTest;
import utils.InputGenerators;

public class RegistrationTests extends BaseUiTest {
    private static final Config CFG = Config.getInstance();

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
    @DisplayName("Юзер может быть зарегестрирован c юзернеймом на кириллице")
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

    @Test
    @DisplayName("Нельзя зарегистрироваться с пустым паролем")
    public void userCantBeRegisteredWithEmptyPasswordTest() {
        UserJson user = UserJson.getRandomUser();
        Selenide.open(CFG.frontUrl());
        mainPage().loginBtn.click();
        loginPage().registerBtn.click();
        registerPage()
                .usernameInput.fill(user.username())
                .submitBtn.click();
        userRepositoryHibernate.checkUserNotExist(user);
    }

    @Test
    @DisplayName("Нельзя зарегистрироваться со слишком коротким паролем")
    public void userCantBeRegisteredWithShortPasswordTest() {
        UserJson user = new UserJson(
                null,
                InputGenerators.randomUsername(),
                null,
                null,
                null,
                new TestData(InputGenerators.randomSentence(2))
        );

        Selenide.open(CFG.frontUrl());
        mainPage().loginBtn.click();
        loginPage().registerBtn.click();

        registerPage()
                .usernameInput.fill(user.username())
                .passwordInput.fill(user.testData().password())
                .passwordSubmitInput.fill(user.testData().password())
                .submitBtn.click()
                .error.checkVisible();

        userRepositoryHibernate.checkUserNotExist(user);
    }

    @Test
    @DisplayName("Нельзя зарегистрироваться со слишком длинным паролем")
    public void userCantBeRegisteredWithLongPasswordTest() {
        UserJson user = new UserJson(
                null,
                InputGenerators.randomUsername(),
                null,
                null,
                null,
                new TestData(InputGenerators.randomSentence(14))
        );

        Selenide.open(CFG.frontUrl());
        mainPage().loginBtn.click();
        loginPage().registerBtn.click();
        registerPage()
                .usernameInput.fill(user.username())
                .passwordInput.fill(user.testData().password())
                .passwordSubmitInput.fill(user.testData().password())
                .submitBtn.click()
                .error.checkVisible();

        userRepositoryHibernate.checkUserNotExist(user);
    }

    @Test
    @DisplayName("Ошибка регистрации исчезает после корректного ввода данных")
    public void registrationErrorDisappearsAfterValidInputTest() {
        UserJson user = UserJson.getRandomUser();
        Selenide.open(CFG.frontUrl());
        mainPage().loginBtn.click();
        loginPage().registerBtn.click();

        registerPage()
                .usernameInput.fill(user.username())
                .passwordInput.fill("123")
                .passwordSubmitInput.fill("456")
                .submitBtn.click()
                .error.checkVisible();

        registerPage()
                .passwordInput.clearThenFill(user.testData().password())
                .passwordSubmitInput.clearThenFill(user.testData().password())
                .submitBtn.click();

        userRepositoryHibernate.checkUserExist(user);
        userRepositoryHibernate.deleteUser(user);
    }

}