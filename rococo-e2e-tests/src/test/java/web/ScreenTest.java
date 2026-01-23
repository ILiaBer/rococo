package web;

import jupiter.annotations.ScreenShotTest;
import org.junit.jupiter.api.DisplayName;
import ui.BaseUiTest;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ScreenTest extends BaseUiTest {

    @ScreenShotTest(expected = "main_page_preset.png")
    @DisplayName("Отображение пресета главной страницы")
    public void checkMainPagePreset(BufferedImage expectedImage) throws IOException {
        mainPage().welcomeScreen.checkScreenshotAreIdenticalWithPic(expectedImage);
    }

    @ScreenShotTest(expected = "sidebar.png")
    @DisplayName("Отображение сайдбара")
    public void checkSidebar(BufferedImage expectedImage) throws IOException {
        mainPage().sidebar.checkScreenshotAreIdenticalWithPic(expectedImage);
    }
}