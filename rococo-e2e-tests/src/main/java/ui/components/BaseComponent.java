package ui.components;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public abstract class BaseComponent<T> {

    protected By locator;
    protected T page;

    public BaseComponent(By locator, T page) {
        this.locator = locator;
        this.page = page;
    }


    public void checkVisible() {
        $(locator).shouldBe((Condition.visible));
    }
}
