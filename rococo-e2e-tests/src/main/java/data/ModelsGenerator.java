package data;

import data.repo.GeoRepositoryHibernate;
import model.ArtistJson;
import model.CountryJson;

import java.util.List;
import java.util.Random;

import static utils.InputGenerators.randomName;
import static utils.InputGenerators.randomSentence;

public class ModelsGenerator {

    public static CountryJson getRandomCountry() {
        GeoRepositoryHibernate geoRepository = new GeoRepositoryHibernate();

        List<CountryJson> countryName = geoRepository.getAllCountry().stream().map(CountryJson::fromEntity).toList();
        Random random = new Random();
        int randomIndex = random.nextInt(countryName.size());
        return countryName.get(randomIndex);
    }

    public static ArtistJson getRandomArtist() {
        ArtistJson artist = new ArtistJson();
        artist.setName(randomName());
        artist.setBiography(randomSentence(20));
        return artist;
    }
}