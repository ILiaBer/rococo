package data.repo;


import config.Config;

import data.entities.PaintingEntity;
import org.springframework.transaction.annotation.Transactional;

public class PaintingRepositoryHibernate extends BaseRepository {

    public PaintingRepositoryHibernate() {
        super(Config.getInstance().paintingJdbcUrl());
    }

    @Transactional
    public void createPainting(PaintingEntity painting) {
        tx.execute(() -> em.persist(painting));
    }
}
