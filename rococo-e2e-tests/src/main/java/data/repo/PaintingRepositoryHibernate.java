package data.repo;


import config.Config;
import data.entities.ArtistEntity;
import data.entities.PaintingEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class PaintingRepositoryHibernate extends BaseRepository {

    public PaintingRepositoryHibernate() {
        super(Config.getInstance().paintingJdbcUrl());
    }

    @Transactional
    public void createPainting(PaintingEntity painting) {
        tx.execute(() -> em.persist(painting));
    }

    @Transactional
    public Optional<PaintingEntity> getPaintingByName(String title) {
        return Optional.ofNullable(em.createQuery("select artist from PaintingEntity artist where artist.title=:title", PaintingEntity.class)
                .setParameter("title", title)
                .getSingleResult());
    }

    @Transactional
    public void deletePainting(String title) {
        List<PaintingEntity> paintings = em.createQuery("SELECT u FROM PaintingEntity u WHERE u.title = :title", PaintingEntity.class)
                .setParameter("title", title)
                .getResultList();

        if (!paintings.isEmpty()) {
            PaintingEntity user = paintings.get(0);
            tx.execute(() -> em.remove(user));
        } else {
            throw new EntityNotFoundException(title + " not found.");
        }
    }
}
