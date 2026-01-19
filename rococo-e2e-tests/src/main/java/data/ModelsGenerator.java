package data;

import data.repo.GeoRepositoryHibernate;
import model.CountryJson;

import java.util.List;
import java.util.Random;

public class ModelsGenerator {

    public static CountryJson getRandomCountry() {
        GeoRepositoryHibernate geoRepository = new GeoRepositoryHibernate();

        List<CountryJson> countryName = geoRepository.getAllCountry().stream().map(CountryJson::fromEntity).toList();
        Random random = new Random();
        int randomIndex = random.nextInt(countryName.size());
        return countryName.get(randomIndex);
    }
}
