package utils;

import api.impl.ArtistApiClient;
import api.impl.MuseumApiClient;
import api.impl.PaintingApiClient;
import config.Config;
import data.DataPresets;
import data.entities.ArtistEntity;
import data.entities.MuseumEntity;
import data.repo.ArtistRepositoryHibernate;
import data.repo.MuseumRepositoryHibernate;
import data.repo.PaintingRepositoryHibernate;
import data.repo.UserRepositoryHibernate;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static final UserRepositoryHibernate userRepositoryHibernate = new UserRepositoryHibernate();
    protected static final PaintingRepositoryHibernate paintingRepositoryHibernate = new PaintingRepositoryHibernate();
    protected static final MuseumRepositoryHibernate museumRepositoryHibernate = new MuseumRepositoryHibernate();
    protected static final ArtistRepositoryHibernate artistRepositoryHibernate = new ArtistRepositoryHibernate();
    protected static final ArtistApiClient artistApiClient = new ArtistApiClient();
    protected static final MuseumApiClient museumApiClient = new MuseumApiClient();
    protected static final PaintingApiClient paintingApiClient = new PaintingApiClient();

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
}
