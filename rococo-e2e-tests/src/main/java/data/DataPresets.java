package data;

import data.entities.ArtistEntity;
import data.entities.MuseumEntity;
import data.entities.PaintingEntity;
import data.repo.GeoRepositoryHibernate;
import utils.ImageUtil;

import java.util.UUID;

public class DataPresets {
    private static final GeoRepositoryHibernate geoRepository = new GeoRepositoryHibernate();

    public static MuseumEntity getTheHermitage() {
        MuseumEntity museum = new MuseumEntity();
        String photoBase64 = ImageUtil.convertImageToBase64("img/presets/museums/Hermitage.png");
        museum.setTitle("The Enemies");
        museum.setDescription("российский государственный художественный и " +
                "культурно-исторический музей в Санкт-Петербурге, одно из крупнейших в мире учреждений подобного рода");
        museum.setCity("Санкт-Петербург");
        museum.setPhoto(photoBase64.getBytes());
        museum.setGeoId(geoRepository.getCountryByName("Россия").getId());
        return museum;
    }

    public static MuseumEntity getTheBorgheseGallery() {
        MuseumEntity museum = new MuseumEntity();
        String photoBase64 = ImageUtil.convertImageToBase64("img/presets/museums/BorgheseGallery.png");
        museum.setTitle("Borghese Gallery");
        museum.setDescription("Художественное собрание знатной итальянской семьи Боргезе, которое " +
                "экспонируется в здании Виллы Пинчиана, или «Малого дворца», на территории парка виллы Боргезе в Риме");
        museum.setCity("Paris");
        museum.setPhoto(photoBase64.getBytes());
        museum.setGeoId(geoRepository.getCountryByName("Франция").getId());
        return museum;
    }

    public static ArtistEntity getTitian() {
        String photoBase64 = ImageUtil.convertImageToBase64("img/presets/artists/Titian.png");
        ArtistEntity artist = new ArtistEntity();
        artist.setName("Тициа́н Вече́ллио");
        artist.setBiography("Итальянский живописец, крупнейший представитель венецианской школы эпохи Высокого и " +
                "Позднего Возрождения");
        artist.setPhoto(photoBase64.getBytes());
        return artist;
    }

    public static ArtistEntity getTheJosephStevens() {
        String photoBase64 = ImageUtil.convertImageToBase64("img/presets/artists/noPhoto.png");
        ArtistEntity artist = new ArtistEntity();
        artist.setName("Joseph Stevens");
        artist.setBiography("Изучал живопись в брюссельской Академии искусств в 1833—1835 годах," +
                " где посещал курсы Луи Робба и Эжена Вербекховена");
        artist.setPhoto(photoBase64.getBytes());
        return artist;
    }

    public static PaintingEntity getTheEnemies() {
        PaintingEntity painting = new PaintingEntity();
        painting.setTitle("The Enemies");
        painting.setDescription("известная картина бельгийского художника-анималиста Жозефа Стевенса (1819–1892)," +
                " написанная маслом на дереве в 1854 году, изображающая извечный конфликт между собаками и котами");
        String photoBase64 = ImageUtil.convertImageToBase64("img/presets/paintings/TheEnemies.png");
        painting.setContent(photoBase64.getBytes());
        return painting;
    }

    public static PaintingEntity getTheEnemies(UUID museumId, UUID artistId) {
        PaintingEntity painting = getTheEnemies();
        painting.setMuseumId(museumId);
        painting.setArtistId(artistId);
        return painting;
    }

    public static PaintingEntity getTheHeavenlyLoveAndEarthlyLove() {
        PaintingEntity painting = new PaintingEntity();
        painting.setTitle("Heavenly Love and Earthly Love");
        painting.setDescription("Это произведение следует лучшим традициям венецианской школы Джованни Беллини и Джорджоне");
        String photoBase64 = ImageUtil.convertImageToBase64("img/presets/paintings/HeavenlyLoveAndEarthlyLove.png");
        painting.setContent(photoBase64.getBytes());
        return painting;
    }

    public static PaintingEntity getTheHeavenlyLoveAndEarthlyLove(UUID museumId, UUID artistId) {
        PaintingEntity painting = getTheHeavenlyLoveAndEarthlyLove();
        painting.setMuseumId(museumId);
        painting.setArtistId(artistId);
        return painting;
    }

    public static PaintingEntity getFlagellationOfChrist(UUID museumId, UUID artistId) {
        PaintingEntity painting = getFlagellationOfChrist();
        painting.setMuseumId(museumId);
        painting.setArtistId(artistId);
        return painting;
    }

    public static PaintingEntity getFlagellationOfChrist() {
        PaintingEntity painting = new PaintingEntity();
        painting.setTitle("Flagellation of Christ");
        painting.setDescription("Названная одним из критиков «маленькой загадочной картиной»," +
                " отличается сложной и необычной иконографической композицией");
        String photoBase64 = ImageUtil.convertImageToBase64("img/presets/paintings/FlagellationOfChrist.png");
        painting.setContent(photoBase64.getBytes());
        return painting;
    }
}
