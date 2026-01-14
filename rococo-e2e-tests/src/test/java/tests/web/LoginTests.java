package tests.web;

import com.codeborne.selenide.Selenide;
import config.Config;
import jupiter.annotations.User;
import jupiter.annotations.WebTest;
import model.UserJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import tests.BaseUiTest;
import utils.InputGenerators;

@WebTest
@Execution(ExecutionMode.SAME_THREAD)
public class LoginTests extends BaseUiTest {

    protected static final Config CFG = Config.getInstance();


    @Test
    @User
    @DisplayName("Успешная авторизация")
    public void successfulLoginTest(UserJson user) {
        Selenide.open(CFG.frontUrl());
        mainPage().login(user);
        mainPage().login(user);

    }

    @Test
    @User
    @DisplayName("Авторизация")
    public void authorizationWithWrongPasswordTest(UserJson user) {
        Selenide.open(CFG.frontUrl());
        mainPage().login(user.username(), InputGenerators.randomPassword());
    }


}
