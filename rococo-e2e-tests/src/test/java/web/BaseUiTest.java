package web;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import config.Config;
import data.repo.UserRepositoryHibernate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ui.AllPages;

public class BaseUiTest extends AllPages {
    protected final UserRepositoryHibernate userRepositoryHibernate = new UserRepositoryHibernate();

    protected static final Config CFG = Config.getInstance();

    @BeforeEach
    public void openUI() {
        Selenide.open(CFG.frontUrl());
    }

    @AfterEach
    public void closeDriver() {
        WebDriverRunner.getWebDriver().close();
    }
}
