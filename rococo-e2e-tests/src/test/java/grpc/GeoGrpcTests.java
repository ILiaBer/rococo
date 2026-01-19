package grpc;

import config.Config;
import guru.qa.grpc.rococo.grpc.*;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.grpc.AllureGrpc;
import jupiter.annotations.GrpcTest;
import model.CountryJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.BaseTest;

import java.util.UUID;

import static com.google.protobuf.ByteString.copyFromUtf8;
import static data.ModelsGenerator.getRandomCountry;
import static org.junit.jupiter.api.Assertions.*;
import static utils.InputGenerators.randomName;

@GrpcTest
public class GeoGrpcTests extends BaseTest {

    private static final Config CFG = Config.getInstance();
    private static final Channel geoChannel;

    static {
        geoChannel = ManagedChannelBuilder
                .forAddress(CFG.geoGrpcAddress(), CFG.geoGrpcPort())
                .intercept(new AllureGrpc())
                .usePlaintext()
                .build();
    }

    private final RococoGeoServiceGrpc.RococoGeoServiceBlockingStub geoStub = RococoGeoServiceGrpc.newBlockingStub(geoChannel);

    @Test
    @DisplayName("GRPC: Получение информации о стране из rococo-geo по id")
    void getCountryDataByIdTest() {
        CountryJson country = getRandomCountry();
        CountryId request = CountryId.newBuilder()
                .setId(copyFromUtf8(country.id().toString()))
                .build();

        CountryResponse countryResponse = geoStub.getCountry(request);
        assertEquals(country.name(), countryResponse.getName());
        assertEquals(country.id(), UUID.fromString(countryResponse.getId().toStringUtf8()));
    }

    @Test
    @DisplayName("GRPC: Получение информации о стране из rococo-geo по названию страны")
    void getCountryDataByNameTest() {
        CountryJson country = getRandomCountry();
        CountryName request = CountryName.newBuilder()
                .setName(country.name())
                .build();

        CountryResponse countryResponse = geoStub.getCountryByName(request);

        assertEquals(country.name(), countryResponse.getName());
        assertEquals(country.id(), UUID.fromString(countryResponse.getId().toStringUtf8()));
    }


    @Test
    @DisplayName("GRPC: Ошибка при запросе страны из rococo-geo по несуществующему имени")
    void notExistingCountryNameErrorTest() {
        String notExistingCountryName = randomName();
        CountryName request = CountryName.newBuilder()
                .setName(notExistingCountryName)
                .build();

        StatusRuntimeException exception = assertThrows(StatusRuntimeException.class, () -> geoStub.getCountryByName(request));

        assertEquals(Status.NOT_FOUND.getCode(), exception.getStatus().getCode());
        assertEquals("Country not found by name: " + notExistingCountryName, exception.getStatus().getDescription());
    }

    @Test
    @DisplayName("GRPC: Получение списка всех стран")
    void getAllCountriesTest() {
        AllCountryRequest request = AllCountryRequest.newBuilder()
                .setPage(0)
                .setSize(20)
                .build();

        AllCountryResponse response = geoStub.getAllCountry(request);
        assertEquals(20, response.getCountryCount());
        CountryResponse country = response.getCountry(19);
        assertEquals("Бразилия", country.getName());
    }

    @Test
    @DisplayName("GRPC: Получение списка стран с ограничением размера страницы")
    void getAllCountriesWithSmallPageSizeTest() {
        AllCountryRequest request = AllCountryRequest.newBuilder()
                .setPage(0)
                .setSize(5)
                .build();

        AllCountryResponse response = geoStub.getAllCountry(request);

        assertEquals(5, response.getCountryCount());
    }

    @Test
    @DisplayName("GRPC: Получение второй страницы списка стран")
    void getAllCountriesSecondPageTest() {
        AllCountryRequest firstPageRequest = AllCountryRequest.newBuilder()
                .setPage(0)
                .setSize(5)
                .build();

        AllCountryRequest secondPageRequest = AllCountryRequest.newBuilder()
                .setPage(1)
                .setSize(5)
                .build();

        AllCountryResponse firstPage = geoStub.getAllCountry(firstPageRequest);
        AllCountryResponse secondPage = geoStub.getAllCountry(secondPageRequest);

        assertEquals(5, firstPage.getCountryCount());
        assertEquals(5, secondPage.getCountryCount());

        assertNotEquals(
                firstPage.getCountry(0).getId(),
                secondPage.getCountry(0).getId()
        );
    }
}