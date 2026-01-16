package ui.page;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.UserJson;
import org.openqa.selenium.By;
import ui.components.Button;
import ui.components.Label;
import ui.components.LineEdit;

@Slf4j
public class LoginPage {

    public final LineEdit<LoginPage> usernameInput = new LineEdit<>(By.xpath("//input[@name='username']"), this);
    public final LineEdit<LoginPage> passwordInput = new LineEdit<>(By.cssSelector("input[name='password']"), this);
    public final Button<LoginPage> submitBtn = new Button<>(By.cssSelector("button[type='submit']"), this);
    public final Button<LoginPage> registerBtn = new Button<>(By.xpath("//*[@data-testid='registerLink']"), this);
    public final Label<LoginPage> error = new Label<>(By.cssSelector("form p"), this);

    @Step("Залогиниться")
    public LoginPage login(String username, String password) {
        usernameInput.fill(username);
        passwordInput.fill(password);
        submitBtn.click();
        return this;
    }

    @Step("Залогиниться")
    public LoginPage login(UserJson user) {
        login(user.username(), user.testData().password());
        return this;
    }
}