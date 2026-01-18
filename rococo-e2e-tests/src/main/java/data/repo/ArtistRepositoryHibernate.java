package data.repo;

import config.Config;
import data.entities.ArtistEntity;
import org.springframework.transaction.annotation.Transactional;

public class ArtistRepositoryHibernate extends BaseRepository {

    public ArtistRepositoryHibernate() {
        super(Config.getInstance().artistJdbcUrl());
    }

    @Transactional
    public void createArtist(ArtistEntity artist) {
        tx.execute(() -> em.persist(artist));
    }
}