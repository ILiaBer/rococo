package config;

import org.jetbrains.annotations.NotNull;

enum DockerConfig implements Config {
    INSTANCE;


    @Override
    public String frontUrl() {
        return "http://client.rococo.dc/";
    }

    @Override
    public String authUrl() {
        return "http://auth.rococo.dc:9000/";
    }

    @Override
    public String gatewayUrl() {
        return "http://gateway.rococo.dc:8090/";
    }

    @NotNull
    @Override
    public String authJdbcUrl() {
        return "jdbc:mysql://localhost:3306/rococo-auth";
    }

    @NotNull
    @Override
    public String userdataJdbcUrl() {
        return "jdbc:mysql://localhost:3306/rococo-userdata";
    }

    @NotNull
    @Override
    public String paintingJdbcUrl() {
        return "jdbc:mysql://localhost:3306/rococo-painting";
    }

    @NotNull
    @Override
    public String museumJdbcUrl() {
        return "jdbc:mysql://localhost:3306/rococo-museum";
    }

    @NotNull
    @Override
    public String artistJdbcUrl() {
        return "jdbc:mysql://localhost:3306/rococo-artist";
    }

    @NotNull
    @Override
    public String geoJdbcUrl() {
        return "jdbc:mysql://localhost:3306/rococo-geo";
    }

    @Override
    public String userdataGrpcAddress() {
        return "userdata.rococo.dc";
    }

    @Override
    public String artistGrpcAddress() {
        return "artist.rococo.dc";
    }

    @Override
    public String museumGrpcAddress() {
        return "museum.rococo.dc";
    }

    @Override
    public String geoGrpcAddress() {
        return "geo.rococo.dc";
    }

    @Override
    public String paintingGrpcAddress() {
        return "painting.rococo.dc";
    }

    @NotNull
    @Override
    public String screenshotBaseDir() {
        return "img/";
    }
}
