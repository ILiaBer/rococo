package grpc;

import config.Config;
import guru.qa.grpc.rococo.grpc.AllMuseumRequest;
import guru.qa.grpc.rococo.grpc.MuseumRequest;
import guru.qa.grpc.rococo.grpc.MuseumResponse;
import guru.qa.grpc.rococo.grpc.RococoMuseumServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.grpc.AllureGrpc;
import jupiter.annotations.GrpcTest;
import jupiter.annotations.Museum;
import model.MuseumJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.BaseTest;

import java.util.UUID;

import static com.google.protobuf.ByteString.copyFromUtf8;
import static model.MuseumJson.fromGrpcMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@GrpcTest
public class MuseumGrpcTests extends BaseTest {
    private static final Config CFG = Config.getInstance();

    private static final Channel museumChannel;

    static {
        museumChannel = ManagedChannelBuilder
                .forAddress(CFG.museumGrpcAddress(), CFG.museumGrpcPort())
                .intercept(new AllureGrpc())
                .usePlaintext()
                .build();
    }

    private final RococoMuseumServiceGrpc.RococoMuseumServiceBlockingStub museumStub = RococoMuseumServiceGrpc.newBlockingStub(museumChannel);

    @Test
    @DisplayName("GRPC: Получение информации о музее из rococo-museum")
    @Museum
    void getMuseumDataTest(MuseumJson createdMuseum) {
        String createdMuseumId = createdMuseum.getId().toString();
        MuseumRequest request = MuseumRequest.newBuilder()
                .setId(copyFromUtf8(createdMuseumId))
                .build();

        MuseumResponse museumResponse = museumStub.getMuseum(request);

        assertEquals(createdMuseum, fromGrpcMessage(museumResponse));
    }

    @Test
    @DisplayName("GRPC: Ошибка при запросе музея из rococo-museum по несуществующему ID")
    void notExistingMuseumIdErrorTest() {
        String notExistingId = UUID.randomUUID().toString();
        MuseumRequest request = MuseumRequest.newBuilder()
                .setId(copyFromUtf8(notExistingId))
                .build();

        StatusRuntimeException exception = assertThrows(StatusRuntimeException.class, () -> museumStub.getMuseum(request));

        assertEquals(Status.NOT_FOUND.getCode(), exception.getStatus().getCode());
        assertEquals("Museum not found by id: " + notExistingId, exception.getStatus().getDescription());
    }

    @Test
    @Museum
    @DisplayName("GRPC: Фильтрация списка музеев по названию")
    void filterByTitleTest(MuseumJson createdMuseum) {
        String museumTitle = createdMuseum.getTitle();
        AllMuseumRequest request = AllMuseumRequest.newBuilder()
                .setTitle(museumTitle)
                .setPage(0)
                .setSize(10)
                .build();

        MuseumResponse museumResponse = museumStub.getAllMuseum(request).getMuseum(0);

        assertEquals(createdMuseum, fromGrpcMessage(museumResponse));
    }

    @Test
    @DisplayName("GRPC: Ошибка при запросе музея с некорректным ID")
    void invalidMuseumIdFormatTest() {
        String invalidId = "not-a-uuid";
        MuseumRequest request = MuseumRequest.newBuilder()
                .setId(copyFromUtf8(invalidId))
                .build();

        StatusRuntimeException exception =
                assertThrows(StatusRuntimeException.class, () -> museumStub.getMuseum(request));
        assertEquals(Status.UNKNOWN.getCode(), exception.getStatus().getCode());
    }
}