package ui.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class Table<T> extends BaseComponent<T> {

    public Table(By locator, T page) {
        super(locator, page);
    }

    private SelenideElement findCellByName(String cellName) {
        return $(locator).$$x(".//li//child::*[.='" + cellName + "']").first();
    }

    private ElementsCollection findAllCells() {
        return $(locator).$$x(".//li//child::img");
    }

    public void checkTableEmpty() {
        Assertions.assertEquals(0, findAllCells().size());
    }

    public void checkCellExistByName(String name) {
        findCellByName(name).shouldBe(Condition.exist);
    }

    public void checkCellNotExistByName(String name) {
        findCellByName(name).shouldNotBe(Condition.exist);
    }
}
