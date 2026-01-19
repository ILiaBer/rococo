package ui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import data.DataPresets;
import data.entities.ArtistEntity;
import data.entities.MuseumEntity;
import data.repo.ArtistRepositoryHibernate;
import data.repo.MuseumRepositoryHibernate;
import data.repo.PaintingRepositoryHibernate;
import data.repo.UserRepositoryHibernate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseUiTest extends AllPages {

    @BeforeEach
    public void openUI() {
        Selenide.open(CFG.frontUrl());
    }

    @AfterEach
    public void closeDriver() {
        WebDriverRunner.getWebDriver().close();
    }
}
