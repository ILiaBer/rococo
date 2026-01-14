package ui.page;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.UserJson;
import org.openqa.selenium.By;
import ui.components.Button;
import ui.components.LineEdit;

@Slf4j
public class MainPage {


//    public final Button paintings = new Button<>(By.cssSelector("img[alt='Ссылка на картины']"));
//
//    public final LineEdit usernameInput = new LineEdit(By.xpath("//input[@name='username']"));
//    public final LineEdit passwordInput = new LineEdit(By.cssSelector("input[name='password']"));
//    public final Button submitBtn = new Button(By.cssSelector("button[type='submit']"));
//    public final LineEdit createAccountBtn = new LineEdit(By.cssSelector("a[href='/register']"));
//    public final Button loginBtn = new Button(By.xpath("//button[contains(text(),'Войти')]"));

    public final Button<MainPage> paintings = new Button<>(By.cssSelector("img[alt='Ссылка на картины']"), this);

    public final LineEdit<MainPage> usernameInput = new LineEdit<>(By.xpath("//input[@name='username']"), this);
    public final LineEdit<MainPage> passwordInput = new LineEdit<>(By.cssSelector("input[name='password']"), this);
    public final Button<MainPage> submitBtn = new Button<>(By.cssSelector("button[type='submit']"), this);
    public final LineEdit<MainPage> createAccountBtn = new LineEdit<>(By.cssSelector("a[href='/register']"), this);
    public final Button<MainPage> loginBtn = new Button<>(By.xpath("//button[contains(text(),'Войти')]"), this);

    @Step("Залогиниться")
    public void login(String username, String password) {
        loginBtn.click();
        usernameInput.fill(username);
        passwordInput.fill(password);
        submitBtn.click();
    }

    @Step("Залогиниться")
    public void login(UserJson user) {
        login(user.username(), user.testData().password());
    }
}
