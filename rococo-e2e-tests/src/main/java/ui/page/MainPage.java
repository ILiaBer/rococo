package ui.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.UserJson;
import org.openqa.selenium.By;
import ui.components.Button;
import ui.components.Label;
import ui.components.LineEdit;
import ui.components.Picture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class MainPage {


    public final Button<MainPage> paintings = new Button<>(By.cssSelector("img[alt='Ссылка на картины']"), this);

    public final LineEdit<MainPage> usernameInput = new LineEdit<>(By.xpath("//input[@name='username']"), this);
    public final LineEdit<MainPage> passwordInput = new LineEdit<>(By.cssSelector("input[name='password']"), this);
    public final Button<MainPage> submitBtn = new Button<>(By.cssSelector("button[type='submit']"), this);
    public final LineEdit<MainPage> createAccountBtn = new LineEdit<>(By.cssSelector("a[href='/register']"), this);
    public final Button<MainPage> loginBtn = new Button<>(By.xpath("//button[contains(text(),'Войти')]"), this);
    public final Button<MainPage> profileIcon = new Button<>(By.cssSelector("figure.avatar"), this);
    public final Button<MainPage> registerBtn = new Button<>(By.xpath("//*[@data-testid='registerLink']"), this);
    public final Label<MainPage> error = new Label<>(By.cssSelector("form p"), this);
    public final Picture<MainPage> welcomeScreen = new Picture<>(By.cssSelector("#page-content"), this);

    @Step("Залогиниться")
    public MainPage login(String username, String password) {
        loginBtn.click();
        usernameInput.fill(username);
        passwordInput.fill(password);
        submitBtn.click();
        return this;
    }

    @Step("Залогиниться")
    public MainPage login(UserJson user) {
        login(user.username(), user.testData().password());
        return this;
    }

    public static class ProfileModal {
        public final Button<ProfileModal> logout = new Button<>(By.cssSelector("div.modal button"), this);
    }

    public ProfileModal profileModal() {
        return new ProfileModal();
    }

    private static BufferedImage screenshot(SelenideElement element) throws IOException {
        return ImageIO.read(Objects.requireNonNull(Objects.requireNonNull(element).screenshot()));
    }
}
