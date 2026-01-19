package api;

import data.DataPresets;
import jupiter.annotations.*;
import model.ArtistJson;
import model.MuseumJson;
import model.PaintingJson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import utils.BaseTest;

import java.util.UUID;

@ApiTest
public class ApiTests extends BaseTest {

    @Test
    @DisplayName("Получение художника по ID")
    void artistShouldBeReturnedByIdTest() {
        ArtistJson createdArtist = ArtistJson.fromEntity(artistRepositoryHibernate.getArtistByName(
                DataPresets.getTitian().getName()).orElseThrow());
        final Response<ArtistJson> response = artistApiClient
                .getArtist(createdArtist.getId().toString());
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertEquals(createdArtist, response.body());
    }

    @Test
    @DisplayName("Получение музея по ID")
    void museumShouldBeReturnedByIdTest() {
        MuseumJson createdMuseum = MuseumJson.fromEntity(museumRepositoryHibernate.getMuseumByName(
                DataPresets.getTheBorgheseGallery().getTitle()).orElseThrow());
        final Response<MuseumJson> response = museumApiClient
                .getMuseum(createdMuseum.getId().toString());
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertEquals(createdMuseum.getTitle(), response.body().getTitle());
    }

    @Test
    @DisplayName("Картинка может быть найдена с помощью поиска")
    @Artist
    @Museum
    @Painting
    void paintingShouldBeReturnedByIdTest(PaintingJson painting) {
        final Response<PaintingJson> response = paintingApiClient.getPainting(painting.getId().toString());
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertEquals(painting.getTitle(), response.body().getTitle());
    }

    @Test
    @DisplayName("Получение художника по несуществующему ID должно возвращать 404")
    void getArtistByNonExistingIdShouldReturn404() {
        String nonExistingId = UUID.randomUUID().toString();
        final Response<ArtistJson> response = artistApiClient.getArtist(nonExistingId);

        Assertions.assertFalse(response.isSuccessful());
        Assertions.assertEquals(404, response.code());
    }

    @Test
    @DisplayName("Получение музея по несуществующему ID должно возвращать 404")
    void getMuseumByNonExistingIdShouldReturn404() {
        String nonExistingId = UUID.randomUUID().toString();
        final Response<MuseumJson> response = museumApiClient.getMuseum(nonExistingId);

        Assertions.assertFalse(response.isSuccessful());
        Assertions.assertEquals(404, response.code());
    }

    @Test
    @DisplayName("Получение картины по несуществующему ID должно возвращать 404")
    void getPaintingByNonExistingIdShouldReturn404() {
        String nonExistingId = UUID.randomUUID().toString();
        final Response<PaintingJson> response = paintingApiClient.getPainting(nonExistingId);

        Assertions.assertFalse(response.isSuccessful());
        Assertions.assertEquals(404, response.code());
    }
}
