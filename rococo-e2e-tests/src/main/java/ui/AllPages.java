package ui;

import ui.page.MainPage;
import ui.page.RegisterPage;

import static com.codeborne.selenide.Selenide.page;

public class AllPages {

    protected MainPage mainPage() {
        return page(MainPage.class);
    }

    protected RegisterPage registerPage() {
        return page(RegisterPage.class);
    }
}
