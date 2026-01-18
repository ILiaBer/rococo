package data.repo;

import config.Config;
import data.entities.ArtistEntity;
import data.entities.MuseumEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ArtistRepositoryHibernate extends BaseRepository {

    public ArtistRepositoryHibernate() {
        super(Config.getInstance().artistJdbcUrl());
    }

    @Transactional
    public void createArtist(ArtistEntity artist) {
        tx.execute(() -> em.persist(artist));
    }

    @Transactional
    public void deleteArtist(String name) {
        List<ArtistEntity> paintings = em.createQuery("SELECT u FROM ArtistEntity u WHERE u.name = :name", ArtistEntity.class)
                .setParameter("name", name)
                .getResultList();

        if (!paintings.isEmpty()) {
            ArtistEntity user = paintings.get(0);
            tx.execute(() -> em.remove(user));
        } else {
            throw new EntityNotFoundException(name + " not found.");
        }
    }
}