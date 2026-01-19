package api;

import model.ArtistJson;
import retrofit2.Call;
import retrofit2.http.*;

public interface ArtistApi {


    @GET("/api/artist/{id}")
    Call<ArtistJson> getArtistById(
            @Path("id") String id
    );
}
