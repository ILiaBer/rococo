package config;

import javax.annotation.Nonnull;

public interface Config {
    static @Nonnull Config getInstance() {
        return LocalConfig.INSTANCE;
    }

    @Nonnull
    String frontUrl();

    @Nonnull
    String authUrl();

    @Nonnull
    String gatewayUrl();

    @Nonnull
    String authJdbcUrl();

    @Nonnull
    String userdataJdbcUrl();

    @Nonnull
    String paintingJdbcUrl();

    @Nonnull
    String museumJdbcUrl();

    @Nonnull
    String artistJdbcUrl();

    @Nonnull
    String geoJdbcUrl();

    @Nonnull
    String userdataGrpcAddress();

    default int userdataGrpcPort() {
        return 8091;
    }

    @Nonnull
    String artistGrpcAddress();

    default int artistGrpcPort() {
        return 8092;
    }

    @Nonnull
    String museumGrpcAddress();

    default int museumGrpcPort() {
        return 8093;
    }

    @Nonnull
    String geoGrpcAddress();

    default int geoGrpcPort() {
        return 8094;
    }

    @Nonnull
    String paintingGrpcAddress();

    default int paintingGrpcPort() {
        return 8095;
    }

    @Nonnull
    String screenshotBaseDir();
}