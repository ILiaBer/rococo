package data.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Countries {

    FRANCE("Франция" );

    @Getter
    @Setter
    String name;

}
