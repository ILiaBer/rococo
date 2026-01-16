package ui.page;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.UserJson;
import org.openqa.selenium.By;
import ui.components.Button;
import ui.components.Label;
import ui.components.LineEdit;

@Slf4j
public class RegisterPage {

    public final LineEdit<RegisterPage> usernameInput = new LineEdit<>(By.cssSelector("input[name='username']"), this);
    public final LineEdit<RegisterPage> passwordInput = new LineEdit<>(By.cssSelector("input[name='password']"), this);
    public final LineEdit<RegisterPage> passwordSubmitInput = new LineEdit<>(By.cssSelector("input[name='passwordSubmit']"), this);
    public final Button<RegisterPage> submitBtn = new Button<>(By.cssSelector("button.form__submit"), this);
    public final Button<RegisterPage> loginBtn = new Button<>(By.cssSelector("a.form__submit"), this);
    public final Label<RegisterPage> error = new Label<>(By.cssSelector("span.form__error"), this);

    @Step("Зарегестрироваться")
    public RegisterPage register(String username, String password) {
        usernameInput.fill(username);
        passwordInput.fill(password);
        passwordSubmitInput.fill(password);
        submitBtn.click();
        loginBtn.click();
        return this;
    }

    @Step("Зарегестрироваться")
    public RegisterPage register(UserJson user) {
        register(user.username(), user.testData().password());
        return this;
    }
}
