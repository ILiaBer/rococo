package api;


import model.MuseumJson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MuseumApi {


    @GET("/api/museum/{id}")
    Call<MuseumJson> getMuseumById(
            @Path("id") String id
    );
}
