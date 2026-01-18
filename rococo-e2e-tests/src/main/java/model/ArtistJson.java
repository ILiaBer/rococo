package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.protobuf.ByteString;
import data.entities.ArtistEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class ArtistJson {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("biography")
    private String biography;
    @JsonProperty("photo")
    private String photo;

    public ArtistJson(UUID id, String name, String biography, String photo) {
        this.id = id;
        this.name = name;
        this.biography = biography;
        this.photo = photo;
    }

    public ArtistJson() {

    }

//    public static ArtistJson fromGrpcMessage(ArtistResponse response) {
//        ArtistJson artistJson = new ArtistJson();
//        artistJson.setId(fromString(response.getId()));
//        artistJson.setName(response.getName());
//        artistJson.setBiography(response.getBiography());
//        artistJson.setPhoto(response.getPhoto().toStringUtf8());
//        return artistJson;
//    }

    public static ArtistJson fromEntity(ArtistEntity entity) {
        ArtistJson artistJson = new ArtistJson();
        artistJson.setId(entity.getId());
        artistJson.setName(entity.getName());
        artistJson.setBiography(entity.getBiography());
        artistJson.setPhoto(ByteString.copyFrom(entity.getPhoto()).toStringUtf8());
        return artistJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistJson that = (ArtistJson) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(biography, that.biography) && Objects.equals(photo, that.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, biography, photo);
    }
}