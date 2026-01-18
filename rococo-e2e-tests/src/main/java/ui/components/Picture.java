package ui.components;

import com.codeborne.selenide.Selenide;
import jupiter.extensions.ScreenShotTestExtension;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.springframework.core.io.ClassPathResource;
import utils.ScreenDiffResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Picture<T> extends BaseComponent<T> {

    public Picture(By locator, T page) {
        super(locator, page);
    }

    public T checkScreenshotAreIdenticalWithPic(BufferedImage expected) throws IOException {
        Selenide.sleep(1000);
        BufferedImage actualImage = ImageIO.read(Objects.requireNonNull($(locator).screenshot()));
        assertFalse(
                new ScreenDiffResult(
                        actualImage, expected
                ),
                ScreenShotTestExtension.ASSERT_SCREEN_MESSAGE
        );
        return page;
    }

    public File fileFromImgElement() {
        $(locator).shouldHave(attributeMatching(
                "src",
                "^data:image/.*;base64,.*"
        ));
        String src = $(locator).getAttribute("src");
        String base64 = src.substring(src.indexOf(',') + 1);
        byte[] bytes = Base64.getDecoder().decode(base64);
        try {
            File tempFile = File.createTempFile("image-from-html-", ".png");
            tempFile.deleteOnExit();
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(bytes);
            }
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create file from img src", e);
        }
    }


    private byte[] getBytesFromFile(File file) {
        try (InputStream in = new FileInputStream(file)) {
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file: " + file.getName(), e);
        }
    }

    public T checkPicturesEqual(String fileName) throws IOException {
        byte[] actualFile = getBytesFromFile(fileFromImgElement());
        byte[] arrayBytesSecondFile = getBytesFromFile(new ClassPathResource("img/" + fileName).getFile());
        if (!Arrays.equals(actualFile, arrayBytesSecondFile)) {
            throw new AssertionError("Files not equals!");
        }
        return page;
    }
}