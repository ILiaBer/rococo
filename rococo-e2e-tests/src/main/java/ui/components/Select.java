package ui.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class Select <T> extends ui.components.BaseComponent<T> {



    public Select(By locator, T page) {
        super(locator, page);
    }


    public T choose(String variant) {
        $(locator).shouldBe(Condition.visible);
        $(locator).scrollTo();
        ElementsCollection list = $(locator).findAll(By.xpath(".//option"));
        for (SelenideElement row : list) {
            if (row.getText().contains(variant)) {
                row.shouldBe(Condition.exist);
                row.scrollTo();
                row.shouldBe(Condition.visible);
                row.click();
                return page;
            }
        }
        return page;
    }
}
