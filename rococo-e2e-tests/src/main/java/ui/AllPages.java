package ui;

import ui.page.MainPage;

import static com.codeborne.selenide.Selenide.page;

public class AllPages {

    protected MainPage mainPage() {
        return page(MainPage.class);
    }
}
