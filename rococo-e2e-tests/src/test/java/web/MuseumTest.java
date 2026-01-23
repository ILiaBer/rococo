package web;

import jupiter.annotations.Museum;
import jupiter.annotations.User;
import jupiter.annotations.WebTest;
import model.MuseumJson;
import model.UserJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.BaseUiTest;
import utils.InputGenerators;

@WebTest
public class MuseumTest extends BaseUiTest {

    @Test
    @DisplayName("Музей может быть найден с помощью поиска")
    @User
    @Museum
    void checkMuseumCanBeFoundBySearch(UserJson user, MuseumJson museum) {
        commonSteps().login(user);
        mainPage().museum.click();
        museumPage()
                .searchInput.fill(museum.getTitle())
                .searchBtn.click()
                .table.checkCellExistByName(museum.getTitle())
                .table.checkTableSize(1);
    }

    @Test
    @User
    @DisplayName("Пользователь может добавить новый музей")
    void checkMuseumCanBeAdded(UserJson user) {
        String name = InputGenerators.randomName();
        commonSteps().login(user);
        mainPage().museum.click();
        museumPage()
                .addMuseum.click();
        museumPage()
                .addMuseumModal()
                .title.fill(name)
                .country.choose("Австралия")
                .city.fill("Сидней")
                .description.fill(InputGenerators.randomMuseumDescription())
                .imageUpload.uploadFile("test.png")
                .submit.click();

        museumPage()
                .table.checkCellExistByName(name);
        museumRepositoryHibernate.deleteMuseum(name);
    }

    @Test
    @User
    @DisplayName("Поиск по несуществующему музею")
    void checkMuseumSearchWithNoResults(UserJson user) {
        commonSteps().login(user);
        mainPage().museum.click();
        museumPage()
                .searchInput.fill("Non Existing Museum")
                .searchBtn.click()
                .table.checkTableEmpty();
    }

    @Test
    @User
    @Museum
    @DisplayName("Музей находится по части названия")
    void checkMuseumCanBeFoundByPartialTitle(UserJson user, MuseumJson museum) {
        commonSteps().login(user);
        mainPage().museum.click();
        String partialTitle = museum.getTitle().substring(0, 3);
        museumPage()
                .searchInput.fill(partialTitle)
                .searchBtn.click()
                .table.checkCellExistByName(museum.getTitle());
    }

}
