package ui.components;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class Label<T> extends BaseComponent<T> {


    public Label(By locator, T page) {
        super(locator, page);
    }

    public T checkText(String text) {
        $(locator).shouldHave(Condition.text(text));
        return page;
    }
}