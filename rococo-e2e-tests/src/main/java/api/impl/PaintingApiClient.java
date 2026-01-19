package api.impl;

import api.PaintingApi;
import api.core.RestClient;
import io.qameta.allure.Step;
import model.PaintingJson;
import retrofit2.Response;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static java.util.Objects.requireNonNull;

@ParametersAreNonnullByDefault
public class PaintingApiClient extends RestClient {

    private final PaintingApi paintingApi;

    public PaintingApiClient() {
        super(CFG.gatewayUrl());
        this.paintingApi = create(PaintingApi.class);
    }

    @Step("Get painting by id={0} using GET /api/painting")
    @Nonnull
    public Response<PaintingJson> getPainting(String id) {
        return requireNonNull(execute(paintingApi.getPaintingById(id)));
    }
}
