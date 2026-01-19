package api;


import model.PaintingJson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PaintingApi {

    @GET("/api/painting/{id}")
    Call<PaintingJson> getPaintingById(
            @Path("id") String id
    );
}
