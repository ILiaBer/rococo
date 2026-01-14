package ui.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import static com.codeborne.selenide.Selenide.$;

public class Button<T> extends BaseComponent<T> {


    public Button(By locator, T page) {
        super(locator, page);
    }

    public T click() {
        $(locator).scrollTo().click();
        return page;
    }

    public T jsClick() {
        JavascriptExecutor js = (JavascriptExecutor) WebDriverRunner.getWebDriver();
        $(locator).shouldBe(Condition.visible);
        js.executeScript("arguments[0].click();", $(locator));
        return page;
    }
}