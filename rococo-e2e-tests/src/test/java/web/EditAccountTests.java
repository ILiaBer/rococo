package web;

import data.entities.UserDataEntity;
import jupiter.annotations.User;
import jupiter.annotations.WebTest;
import model.UserJson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.BaseUiTest;
import utils.InputGenerators;

import java.io.IOException;

@WebTest
public class EditAccountTests extends BaseUiTest {

    @Test
    @User
    @DisplayName("Установка изображения на профиль")
    public void setProfilePhotoTest(UserJson user) throws IOException {
        commonSteps().login(user);
        mainPage()
                .profileIcon.click()
                .profileModal()
                .uploadFile.uploadFile("test.png")
                .submit.click();
        mainPage().profileIcon.click()
                .profileModal().picture.checkPicturesEqual("test.png");
    }

    @Test
    @User
    @DisplayName("Изображение сохраняется после логаута")
    public void profilePhotoExistAfterLogoutTest(UserJson user) throws IOException {
        commonSteps().login(user);
        mainPage()
                .profileIcon.click()
                .profileModal()
                .uploadFile.uploadFile("test.png")
                .submit.click()
                .submit.checkNotVisible();
        mainPage().profileIcon.click()
                .profileModal().logout.click();
        commonSteps().login(user);
        mainPage().profileIcon.click()
                .profileModal().picture.checkPicturesEqual("test.png");
    }

    @Test
    @User
    @DisplayName("Сохранение имени при редактировании")
    public void setFirstNameTest(UserJson user) {
        String newFirstName = InputGenerators.randomName();
        commonSteps().login(user);
        mainPage()
                .profileIcon.click()
                .profileModal()
                .firstname.fill(newFirstName)
                .submit.click()
                .submit.checkNotVisible();
        UserDataEntity userFromDb = userRepositoryHibernate.getUser(user.username());
        Assertions.assertEquals(newFirstName, userFromDb.getFirstname());
    }

    @Test
    @User
    @DisplayName("Сохранение фамилии при редактировании")
    public void setSurnameNameTest(UserJson user) {
        String newSurnameName = InputGenerators.randomName();
        commonSteps().login(user);
        mainPage()
                .profileIcon.click()
                .profileModal()
                .firstname.fill(newSurnameName)
                .submit.click()
                .submit.checkNotVisible();
        UserDataEntity userFromDb = userRepositoryHibernate.getUser(user.username());
        Assertions.assertEquals(newSurnameName, userFromDb.getFirstname());
    }
}