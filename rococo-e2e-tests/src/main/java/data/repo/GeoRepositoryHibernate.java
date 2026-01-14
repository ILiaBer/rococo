package data.repo;

import config.Config;
import data.entities.CountryEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class GeoRepositoryHibernate extends BaseRepository {

    public GeoRepositoryHibernate() {
        super(Config.getInstance().geoJdbcUrl());
    }

    @Transactional
    public CountryEntity getCountryByName(String countryName) {
        return em.createQuery("select country from CountryEntity country where country.name=:countryName", CountryEntity.class)
                .setParameter("countryName", countryName)
                .getSingleResult();
    }

    @Transactional
    public List<CountryEntity> getAllCountry() {
        return em.createQuery("select c from CountryEntity c", CountryEntity.class)
                .getResultList();
    }
}