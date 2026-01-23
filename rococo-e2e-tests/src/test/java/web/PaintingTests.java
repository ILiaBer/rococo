package web;

import data.DataPresets;
import jupiter.annotations.*;
import model.ArtistJson;
import model.MuseumJson;
import model.PaintingJson;
import model.UserJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.BaseUiTest;
import utils.InputGenerators;

@WebTest
public class PaintingTests extends BaseUiTest {

    @Test
    @DisplayName("Картина может быть найдена с помощью поиска")
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

    @Test
    @User
    @Museum
    @Artist
    @Painting
    @DisplayName("Картина находится по части названия")
    void checkPaintingCanBeFoundByPartialTitle(UserJson user, PaintingJson painting) {
        commonSteps().login(user);
        mainPage().paintings.click();
        String partialTitle = painting.getTitle().substring(0, 4);
        paintingPage()
                .searchInput.fill(partialTitle)
                .searchBtn.click()
                .table.checkCellExistByName(painting.getTitle());
    }

    @Test
    @User
    @DisplayName("Поиск по несуществующей картине")
    void checkPaintingSearchWithNoResults(UserJson user) {
        commonSteps().login(user);
        mainPage().paintings.click();
        paintingPage()
                .searchInput.fill("Non Existing Painting")
                .searchBtn.click()
                .table.checkTableEmpty();
    }

    @Test
    @User
    @Artist
    @Museum
    @DisplayName("Добавление новой картины")
    void checkPaintingCanBeAdded(UserJson user, MuseumJson museum, ArtistJson artist) {
        String name = InputGenerators.randomPaintingName();
        commonSteps().login(user);
        mainPage().paintings.click();
        paintingPage()
                .addPicture.click();
        paintingPage()
                .addPictureModal()
                .title.fill(name)
                .museumStorage.choose(museum.getTitle())
                .description.fill(InputGenerators.randomPaintingDescription())
                .imageUpload.uploadFile("test.png")
                .author.choose(artist.getName())
                .submit.click();
        paintingPage()
                .table.checkCellExistByName(name);
        paintingRepositoryHibernate.deletePainting(name);
    }

}
