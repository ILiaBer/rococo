package data.repo;

import config.Config;
import data.entities.ArtistEntity;
import data.entities.MuseumEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class ArtistRepositoryHibernate extends BaseRepository {

    public ArtistRepositoryHibernate() {
        super(Config.getInstance().artistJdbcUrl());
    }

    @Transactional
    public ArtistEntity createArtist(ArtistEntity artist) {
        tx.execute(() -> em.persist(artist));
        return artist;
    }

    @Transactional
    public Optional<ArtistEntity> getArtistByName(String name) {
        return Optional.ofNullable(em.createQuery("select artist from ArtistEntity artist where artist.name=:name", ArtistEntity.class)
                .setParameter("name", name)
                .getSingleResult());
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