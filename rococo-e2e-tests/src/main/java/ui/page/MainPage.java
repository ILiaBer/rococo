package ui.page;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ui.components.Button;
import ui.components.LineEdit;
import ui.components.Picture;
import ui.components.UploadFile;

@Slf4j
public class MainPage {

    public final Button<MainPage> paintings = new Button<>(By.cssSelector("img[alt='Ссылка на картины']"), this);
    public final Button<MainPage> artists = new Button<>(By.cssSelector("img[alt='Ссылка на художников']"), this);
    public final Button<MainPage> museum = new Button<>(By.cssSelector("img[alt='Ссылка на музеи']"), this);
    public final Button<MainPage> profileIcon = new Button<>(By.cssSelector("button figure.avatar"), this);
    public final Picture<MainPage> welcomeScreen = new Picture<>(By.cssSelector("#page-content"), this);
    public final Button<MainPage> loginBtn = new Button<>(By.xpath("//button[contains(text(),'Войти')]"), this);

    public static class ProfileModal {
        public final Button<ProfileModal> logout = new Button<>(By.cssSelector("div.modal button"), this);
        public final UploadFile<ProfileModal> uploadFile = new UploadFile<>(By.cssSelector("input[type=file]"), this);
        public final Button<ProfileModal> submit = new Button<>(By.cssSelector("button[type=submit]"), this);
        public final Picture<ProfileModal> picture = new Picture<>(By.cssSelector("img.avatar-image"), this);
        public final LineEdit<ProfileModal> firstname = new LineEdit<>(By.cssSelector("input[name=firstname]"), this);
        public final LineEdit<ProfileModal> surname = new LineEdit<>(By.cssSelector("input[name=surname]"), this);
    }

    public ProfileModal profileModal() {
        return new ProfileModal();
    }

}
