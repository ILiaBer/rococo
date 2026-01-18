package data.repo;


import config.Config;
import data.entities.MuseumEntity;
import data.entities.PaintingEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MuseumRepositoryHibernate extends BaseRepository {

    public MuseumRepositoryHibernate() {
        super(Config.getInstance().museumJdbcUrl());
    }

    @Transactional
    public MuseumEntity createMuseum(MuseumEntity museum) {
        tx.execute(() -> em.persist(museum));
        return museum;
    }

    @Transactional
    public void deleteMuseum(String title) {
        List<MuseumEntity> paintings = em.createQuery("SELECT u FROM MuseumEntity u WHERE u.title = :title", MuseumEntity.class)
                .setParameter("title", title)
                .getResultList();

        if (!paintings.isEmpty()) {
            MuseumEntity user = paintings.get(0);
            tx.execute(() -> em.remove(user));
        } else {
            throw new EntityNotFoundException(title + " not found.");
        }
    }
}