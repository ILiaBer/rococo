package ui.components;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LineEdit<T> extends BaseComponent<T> {

    public LineEdit(By locator, T page) {
        super(locator, page);
    }

    public T fill(String value) {
        $(locator).scrollTo();
        $(locator).setValue(value);
        return page;
    }

    public T clear() {
        $(locator).scrollTo();
        $(locator).clear();
        return page;
    }

    public T clearThenFill(String value) {
        clear();
        fill(value);
        return page;
    }
}
