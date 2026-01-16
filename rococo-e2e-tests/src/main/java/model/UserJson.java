package model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import data.entities.AuthUserEntity;
import utils.InputGenerators;

import javax.annotation.Nullable;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserJson(
        @Nullable
        @JsonProperty("id")
        UUID id,
        @JsonProperty("username")
        String username,
        @JsonProperty("firstname")
        String firstname,
        @JsonProperty("lastname")
        String lastname,
        @JsonProperty("avatar")
        byte[] avatar,
        @JsonIgnore
        TestData testData
) {

    public static UserJson getRandomUser() {
        return new UserJson(
                null,
                InputGenerators.randomUsername(),
                InputGenerators.randomName(),
                InputGenerators.randomSurname(),
                null,
                new TestData(InputGenerators.randomPassword())
        );
    }

    public static UserJson fromEntity(AuthUserEntity entity) {
        return new UserJson(
                entity.getId(),
                entity.getUsername(),
                null,
                null,
                null,
                new TestData(entity.getEncodedPassword())
        );
    }
}