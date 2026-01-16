package ui.components;

import com.codeborne.selenide.Selenide;
import jupiter.extensions.ScreenShotTestExtension;
import org.openqa.selenium.By;
import utils.ScreenDiffResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Picture<T> extends BaseComponent<T> {

    public Picture(By locator, T page) {
        super(locator, page);
    }

    public void checkPicturesAreIdentical(BufferedImage expected) throws IOException {
        Selenide.sleep(1000);
        BufferedImage actualImage = ImageIO.read(Objects.requireNonNull($(locator).screenshot()));
        assertFalse(
                new ScreenDiffResult(
                        actualImage, expected
                ),
                ScreenShotTestExtension.ASSERT_SCREEN_MESSAGE
        );
    }
}