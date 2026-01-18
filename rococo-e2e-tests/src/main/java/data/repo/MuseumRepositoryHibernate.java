package data.repo;


import config.Config;
import data.entities.MuseumEntity;
import org.springframework.transaction.annotation.Transactional;

public class MuseumRepositoryHibernate extends BaseRepository {

    public MuseumRepositoryHibernate() {
        super(Config.getInstance().museumJdbcUrl());
    }

    @Transactional
    public MuseumEntity createMuseum(MuseumEntity museum) {
        tx.execute(() -> em.persist(museum));
        return museum;
    }
}