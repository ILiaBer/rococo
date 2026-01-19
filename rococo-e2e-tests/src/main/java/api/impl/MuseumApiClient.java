package api.impl;


import api.MuseumApi;
import api.core.RestClient;
import io.qameta.allure.Step;
import model.MuseumJson;
import retrofit2.Response;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static java.util.Objects.requireNonNull;

@ParametersAreNonnullByDefault
public class MuseumApiClient extends RestClient {

    private final MuseumApi museumApi;

    public MuseumApiClient() {
        super(CFG.gatewayUrl());
        this.museumApi = create(MuseumApi.class);
    }

    @Step("Get museum by id={0} using GET /api/museum")
    @Nonnull
    public Response<MuseumJson> getMuseum(String id) {
        return requireNonNull(execute(museumApi.getMuseumById(id)));
    }
}
