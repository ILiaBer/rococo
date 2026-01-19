package ui;

import ui.page.*;
import ui.steps.CommonSteps;
import utils.BaseTest;

import static com.codeborne.selenide.Selenide.page;

public class AllPages extends BaseTest {

    protected MainPage mainPage() {
        return page(MainPage.class);
    }

    protected RegisterPage registerPage() {
        return page(RegisterPage.class);
    }

    protected LoginPage loginPage() {
        return page(LoginPage.class);
    }

    protected PaintingPage paintingPage() {
        return page(PaintingPage.class);
    }

    protected MuseumPage museumPage() {
        return page(MuseumPage.class);
    }

    protected ArtistsPage artistsPage() {
        return page(ArtistsPage.class);
    }

    protected CommonSteps commonSteps() {
        return page(CommonSteps.class);
    }
}