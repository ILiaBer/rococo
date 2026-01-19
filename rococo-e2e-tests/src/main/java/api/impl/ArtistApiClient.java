package api.impl;


import api.ArtistApi;
import api.core.RestClient;
import io.qameta.allure.Step;
import jupiter.annotations.ApiTest;
import model.ArtistJson;
import retrofit2.Response;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static java.util.Objects.requireNonNull;


@ParametersAreNonnullByDefault
public class ArtistApiClient extends RestClient {

    private final ArtistApi artistApi;

    public ArtistApiClient() {
        super(CFG.gatewayUrl());
        this.artistApi = create(ArtistApi.class);
    }

    @Step("Get artist by id={0} using GET /api/artist")
    @Nonnull
    public Response<ArtistJson> getArtist(String id) {
        return requireNonNull(execute(artistApi.getArtistById(id)));
    }
}