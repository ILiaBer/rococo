package web;

import jupiter.annotations.ScreenShotTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.BaseUiTest;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ScreenTest extends BaseUiTest {

    @ScreenShotTest(expected = "main_page_preset.png")
    @DisplayName("Успешная авторизация")
    public void successfulLoginTest(BufferedImage expectedImage) throws IOException {
        mainPage().welcomeScreen.checkScreenshotAreIdenticalWithPic(expectedImage);
    }
}