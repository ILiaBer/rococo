package ui.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class Table<T> extends BaseComponent<T> {

    public Table(By locator, T page) {
        super(locator, page);
    }

    private SelenideElement findCellByName(String cellName) {
        sleep(500);
        return $(locator).$$x(".//li//child::*[.='" + cellName + "']").first();
    }

    private ElementsCollection findAllCells() {
        sleep(500);
        return $(locator).$$x(".//li//child::img");
    }

    public T checkTableEmpty() {
        $(locator).shouldNotBe(Condition.exist);
        return page;
    }

    public T checkCellExistByName(String name) {
        findCellByName(name).shouldBe(Condition.exist);
        return page;
    }

    public T checkCellNotExistByName(String name) {
        findCellByName(name).shouldNotBe(Condition.exist);
        return page;
    }

    public T clickCellByName(String name) {
        findCellByName(name).click();
        return page;
    }

    public T checkTableSize(int expectedSize) {
        Assertions.assertEquals(expectedSize, findAllCells().size());
        return page;
    }
}
