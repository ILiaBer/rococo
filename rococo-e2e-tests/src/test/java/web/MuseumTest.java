package web;

import jupiter.annotations.Museum;
import jupiter.annotations.User;
import jupiter.annotations.WebTest;
import model.MuseumJson;
import model.UserJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
