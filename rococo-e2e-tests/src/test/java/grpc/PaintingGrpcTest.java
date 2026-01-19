package grpc;

import guru.qa.grpc.rococo.grpc.*;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.grpc.AllureGrpc;
import jupiter.annotations.Artist;
import jupiter.annotations.GrpcTest;
import jupiter.annotations.Museum;
import jupiter.annotations.Painting;
import model.PaintingJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.BaseTest;

import static com.google.protobuf.ByteString.copyFromUtf8;
import static io.qameta.allure.Allure.step;
import static java.util.UUID.randomUUID;
import static model.PaintingJson.fromGrpcMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.InputGenerators.randomPaintingName;

@GrpcTest
public class PaintingGrpcTest extends BaseTest {

    private static final Channel paintingChannel;

    static {
        paintingChannel = ManagedChannelBuilder
                .forAddress(CFG.paintingGrpcAddress(), CFG.paintingGrpcPort())
                .intercept(new AllureGrpc())
                .usePlaintext()
                .build();
    }

    protected final RococoPaintingServiceGrpc.RococoPaintingServiceBlockingStub paintingStub = RococoPaintingServiceGrpc.newBlockingStub(paintingChannel);

    @Test
    @DisplayName("GRPC: Получение информации о картине из rococo-painting")
    @Museum
    @Artist
    @Painting
    void getArtistDataTest(PaintingJson createdPainting) {
        String createdPaintingId = createdPainting.getId().toString();
        PaintingRequest request = PaintingRequest.newBuilder()
                .setId(copyFromUtf8(createdPaintingId))
                .build();

        PaintingResponse paintingResponse = paintingStub.getPainting(request);
        assertEquals(createdPainting, fromGrpcMessage(paintingResponse));
    }

    @Test
    @DisplayName("GRPC: Ошибка при запросе картины из rococo-painting по несуществующему ID")
    void notExistingPaintingIdErrorTest() {
        String notExistingId = randomUUID().toString();
        PaintingRequest request = PaintingRequest.newBuilder()
                .setId(copyFromUtf8(notExistingId))
                .build();

        StatusRuntimeException exception = assertThrows(StatusRuntimeException.class, () -> paintingStub.getPainting(request));

        assertEquals(Status.NOT_FOUND.getCode(), exception.getStatus().getCode());
        assertEquals("Painting not found by id: " + notExistingId, exception.getStatus().getDescription());
    }

    @Test
    @Museum
    @Artist
    @Painting
    @DisplayName("GRPC: Фильтрация списка картин по названию")
    void filterByTitleTest(PaintingJson createdPainting) {
        String paintingTitle = createdPainting.getTitle();
        AllPaintingRequest request = AllPaintingRequest.newBuilder()
                .setTitle(paintingTitle)
                .setPage(0)
                .setSize(10)
                .build();

        final PaintingResponse paintingResponse = paintingStub.getAllPainting(request).getPainting(0);

        assertEquals(createdPainting, fromGrpcMessage(paintingResponse));
    }

    @Test
    @DisplayName("GRPC: Фильтрация списка картин по названию: результаты отсутствуют")
    void filterByTitleEmptyResultsTest() {
        String paintingTitle = randomPaintingName();
        AllPaintingRequest request = AllPaintingRequest.newBuilder()
                .setTitle(paintingTitle)
                .setPage(0)
                .setSize(10)
                .build();

        AllPaintingResponse response = paintingStub.getAllPainting(request);
        assertEquals(0, response.getPaintingCount());
        assertEquals(0, response.getTotalCount());
    }


    @Test
    @Museum
    @Artist
    @Painting
    @DisplayName("GRPC: Получение списка картин по ID художника")
    void getAllPaintingByArtistIdTest(PaintingJson createdPainting) {
        String artistId = createdPainting.getArtist().getId().toString();

        AllPaintingByArtistIdRequest request = AllPaintingByArtistIdRequest.newBuilder()
                .setArtistId(copyFromUtf8(artistId))
                .setPage(0)
                .setSize(10)
                .build();

        PaintingResponse paintingResponse =
                paintingStub.getAllPaintingByArtistId(request).getPainting(0);

        assertEquals(createdPainting, fromGrpcMessage(paintingResponse));
    }

    @Test
    @DisplayName("GRPC: Получение списка картин по несуществующему ID художника")
    void getAllPaintingByNotExistingArtistIdEmptyResultTest() {
        String notExistingArtistId = randomUUID().toString();

        AllPaintingByArtistIdRequest request = AllPaintingByArtistIdRequest.newBuilder()
                .setArtistId(copyFromUtf8(notExistingArtistId))
                .setPage(0)
                .setSize(10)
                .build();

        AllPaintingResponse response =
                paintingStub.getAllPaintingByArtistId(request);

        step("Проверить, что список картин пустой", () -> {
            assertEquals(0, response.getPaintingCount());
            assertEquals(0, response.getTotalCount());
        });
    }
}