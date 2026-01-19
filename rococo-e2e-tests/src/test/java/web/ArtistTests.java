package web;

import data.DataPresets;
import jupiter.annotations.Artist;
import jupiter.annotations.User;
import jupiter.annotations.WebTest;
import model.ArtistJson;
import model.UserJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WebTest
public class ArtistTests extends BaseUiTest {

    @Test
    @DisplayName("Художник может быть найден с помощью поиска")
    @User
    @Artist
    void checkArtistCanBeFoundBySearch(UserJson user, ArtistJson artist) {
        commonSteps().login(user);
        mainPage().artists.click();
        artistsPage()
                .searchInput.fill(artist.getName())
                .searchBtn.click()
                .table.checkCellExistByName(artist.getName())
                .table.checkTableSize(1);
    }

    @Test
    @User
    @DisplayName("Картины отображаются в профиле художника")
    void checkPaintingsAreDisplayedOnArtistProfile(UserJson user) {
        commonSteps().login(user);
        mainPage().artists.click();
        artistsPage()
                .searchInput.fill(DataPresets.getTitian().getName())
                .searchBtn.click()
                .table.clickCellByName(DataPresets.getTitian().getName())
                .previewArtistModal()
                .table.checkCellExistByName(DataPresets.getFlagellationOfChrist().getTitle())
                .table.checkCellExistByName(DataPresets.getTheHeavenlyLoveAndEarthlyLove().getTitle())
                .table.checkTableSize(2);
    }
}
