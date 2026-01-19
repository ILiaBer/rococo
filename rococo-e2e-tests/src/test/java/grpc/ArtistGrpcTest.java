package grpc;


import guru.qa.grpc.rococo.grpc.*;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.grpc.AllureGrpc;
import jupiter.annotations.Artist;
import jupiter.annotations.GrpcTest;
import model.ArtistJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.BaseTest;

import java.util.UUID;

import static io.qameta.allure.Allure.step;
import static model.ArtistJson.fromGrpcMessage;
import static org.junit.jupiter.api.Assertions.*;

@GrpcTest
public class ArtistGrpcTest extends BaseTest {

    private static final Channel artistChannel;

    static {
        artistChannel = ManagedChannelBuilder
                .forAddress(CFG.artistGrpcAddress(), CFG.artistGrpcPort())
                .intercept(new AllureGrpc())
                .usePlaintext()
                .maxInboundMessageSize(10 * 1024 * 1024)
                .build();
    }

    private final RococoArtistServiceGrpc.RococoArtistServiceBlockingStub artistStub = RococoArtistServiceGrpc.newBlockingStub(artistChannel);

    @Test
    @DisplayName("GRPC: Получение информации о художнике из rococo-artist")
    @Artist
    void returnArtistDataTest(ArtistJson createdArtist) {
        String createdArtistId = createdArtist.getId().toString();
        ArtistRequest request = ArtistRequest.newBuilder()
                .setId(createdArtistId)
                .build();

        ArtistResponse artistResponse = artistStub.getArtist(request);

        step("Проверить ответ", () -> assertEquals(createdArtist, fromGrpcMessage(artistResponse)));
    }

    @Test
    @DisplayName("GRPC: Ошибка при запросе художника из rococo-artist по несуществующему ID")
    void notExistingArtistIdErrorTest() {
        String notExistingId = UUID.randomUUID().toString();
        ArtistRequest request = ArtistRequest.newBuilder()
                .setId(notExistingId)
                .build();

        StatusRuntimeException exception = assertThrows(StatusRuntimeException.class, () -> artistStub.getArtist(request));

        assertEquals(Status.NOT_FOUND.getCode(), exception.getStatus().getCode());
        assertEquals("Artist not found by id: " + notExistingId, exception.getStatus().getDescription());
    }

    @Test
    @Artist
    @DisplayName("GRPC: Пагинация списка художников")
    void artistPaginationTest() {
        AllArtistRequest firstPageRequest = AllArtistRequest.newBuilder()
                .setPage(0)
                .setSize(1)
                .build();
        AllArtistRequest secondPageRequest = AllArtistRequest.newBuilder()
                .setPage(1)
                .setSize(1)
                .build();

        AllArtistResponse firstPage = artistStub.getAllArtist(firstPageRequest);
        AllArtistResponse secondPage = artistStub.getAllArtist(secondPageRequest);

        step("Проверить, что на первой странице один элемент",
                () -> assertEquals(1, firstPage.getArtistsCount()));

        step("Проверить, что на второй странице один элемент",
                () -> assertEquals(1, secondPage.getArtistsCount()));

        step("Проверить, что элементы на страницах разные",
                () -> assertNotEquals(
                        firstPage.getArtists(0).getId(),
                        secondPage.getArtists(0).getId()
                ));
    }

    @Test
    @Artist
    @DisplayName("GRPC: Фильтрация списка художников по имени")
    void filterArtistByNameTest(ArtistJson createdArtist) {
        String artistName = createdArtist.getName();
        AllArtistRequest request = AllArtistRequest.newBuilder()
                .setName(artistName)
                .setPage(0)
                .setSize(10)
                .build();

        ArtistResponse artistResponse = artistStub.getAllArtist(request).getArtists(0);

        step("Проверить ответ", () -> assertEquals(createdArtist, fromGrpcMessage(artistResponse)));
    }
}