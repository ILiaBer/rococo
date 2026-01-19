package api;

import data.DataPresets;
import jupiter.annotations.ApiTest;
import model.ArtistJson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import utils.BaseTest;

@ApiTest
public class ArtistApiTest extends BaseTest {

    @Test
    @DisplayName("Получение художника по ID")
    void artistShouldBeReturnedById() {
        ArtistJson createdArtist = ArtistJson.fromEntity(artistRepositoryHibernate.getArtistByName(
                DataPresets.getTitian().getName()).orElseThrow());
        final Response<ArtistJson> response = artistApiClient
                .getArtist(createdArtist.getId().toString());
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertEquals(createdArtist, response.body());
    }
}
