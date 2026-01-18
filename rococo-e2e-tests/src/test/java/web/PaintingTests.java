package web;

import jupiter.annotations.*;
import model.PaintingJson;
import model.UserJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WebTest
public class PaintingTests extends BaseUiTest {

    @Test
    @DisplayName("Картинка может быть найдена с помощью поиска")
    @User
    @Artist
    @Museum
    @Painting
    void checkPaintingCanBeFoundBySearch(UserJson user, PaintingJson painting) {
        commonSteps().login(user);
        mainPage().paintings.click();
        paintingPage()
                .searchInput.fill(painting.getTitle())
                .searchBtn.click()
                .table.checkCellExistByName(painting.getTitle())
                .table.checkTableSize(1);

    }
}
