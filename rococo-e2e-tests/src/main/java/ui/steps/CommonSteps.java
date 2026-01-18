package ui.steps;

import io.qameta.allure.Step;
import model.UserJson;
import ui.AllPages;

public class CommonSteps extends AllPages {

    @Step("Залогиниться")
    public void login(String username, String password) {
        mainPage().loginBtn.click();
        loginPage().usernameInput.fill(username)
                .passwordInput.fill(password)
                .submitBtn.click();
    }

    @Step("Залогиниться")
    public void login(UserJson user) {
        login(user.username(), user.testData().password());
    }
}
