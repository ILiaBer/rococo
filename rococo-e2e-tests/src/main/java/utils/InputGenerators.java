package utils;


import com.github.javafaker.Faker;

public class InputGenerators {
    private static final Faker faker = new Faker();

    public static String randomUsername() {
        return faker.name().username();
    }

    public static String randomPassword() {
        return faker.lorem().characters(3, 12);
    }

    public static String randomName() {
        return faker.name().firstName();
    }

    public static String randomSurname() {
        return faker.name().lastName();
    }

    public static String randomSentence(int wordsCount) {
        return faker.lorem().sentence(wordsCount);
    }

    public static String randomString(int charCount) {
        return faker.lorem().fixedString(charCount);
    }

    public static String randomMuseumTitle() {
        return "Государственный музей №" + randomNumber(1, 9999);
    }

    public static String randomMuseumDescription() {
        return randomSentence(20);
    }

    public static String randomArtistBio() {
        return randomSentence(20);
    }

    public static String randomArtistName() {
        return faker.name().fullName();
    }

    public static String randomPaintingName() {
        return faker.name().title();
    }

    public static String randomPaintingDescription() {
        return randomSentence(20);
    }

    public static int randomNumber(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

//    public static CountryJson getRandomCountry() {
//        GeoRepositoryHibernate geoRepository = new GeoRepositoryHibernate();
//
//        List<CountryJson> countryName = geoRepository.getAllCountry().stream().map(CountryJson::fromEntity).toList();
//        Random random = new Random();
//        int randomIndex = random.nextInt(countryName.size());
//        return countryName.get(randomIndex);
//    }

    public static String getRandomCityName() {
        return faker.address().cityName();
    }
}