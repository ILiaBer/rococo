package web;

import jupiter.annotations.User;
import jupiter.annotations.WebTest;
import model.UserJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@WebTest
public class EditAccountTests extends BaseUiTest {


    @Test
    @User
    @DisplayName("Проверка логаута")
    public void setProfilePhotoTest(UserJson user) throws IOException {
        mainPage().loginBtn.click();
        loginPage().login(user);
        mainPage()
                .profileIcon.click()
                .profileModal()
                .uploadFile.uploadFile("test.png")
                .submit.click();
        mainPage().profileIcon.click()
                .profileModal().picture.checkPicturesEqual("test.png");
    }
}
