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
    protected static final UserRepositoryHibernate userRepositoryHibernate = new UserRepositoryHibernate();
    protected static final PaintingRepositoryHibernate paintingRepositoryHibernate = new PaintingRepositoryHibernate();
    protected static final MuseumRepositoryHibernate museumRepositoryHibernate = new MuseumRepositoryHibernate();
    protected static final ArtistRepositoryHibernate artistRepositoryHibernate = new ArtistRepositoryHibernate();

    @BeforeAll
    public static void createContentPresetsIfNotExist() {
        if (museumRepositoryHibernate.getMuseumByName(DataPresets.getTheHermitage().getTitle()).isEmpty()) {
            ArtistEntity artist = artistRepositoryHibernate.createArtist(DataPresets.getTheJosephStevens());
            MuseumEntity museum = museumRepositoryHibernate.createMuseum(DataPresets.getTheHermitage());
            paintingRepositoryHibernate.createPainting(DataPresets.getTheEnemies(museum.getId(), artist.getId()));
        }
        if (museumRepositoryHibernate.getMuseumByName(DataPresets.getTheBorgheseGallery().getTitle()).isEmpty()) {
            ArtistEntity artist = artistRepositoryHibernate.createArtist(DataPresets.getTitian());
            MuseumEntity museum = museumRepositoryHibernate.createMuseum(DataPresets.getTheBorgheseGallery());
            paintingRepositoryHibernate.createPainting(DataPresets.getFlagellationOfChrist(museum.getId(), artist.getId()));
            paintingRepositoryHibernate.createPainting(DataPresets.getTheHeavenlyLoveAndEarthlyLove(museum.getId(), artist.getId()));
        }
    }

    @BeforeEach
    public void openUI() {
        Selenide.open(CFG.frontUrl());
    }

    @AfterEach
    public void closeDriver() {
        WebDriverRunner.getWebDriver().close();
    }
}
