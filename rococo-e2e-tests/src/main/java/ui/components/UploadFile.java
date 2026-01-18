package ui.components;

import config.Config;
import org.openqa.selenium.By;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;


public class UploadFile<T> extends BaseComponent<T> {

    protected static final Config CFG = Config.getInstance();


    public UploadFile(By locator, T page) {
        super(locator, page);
    }


    public T uploadFile(String fileName) {
        ClassPathResource resource = new ClassPathResource(CFG.screenshotBaseDir() + fileName);
        File file;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            throw new RuntimeException("File not found in classpath: " + fileName, e);
        }
        $(locator).uploadFile(file);
        return page;
    }
}