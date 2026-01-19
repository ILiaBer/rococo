package jupiter.annotations;


import io.qameta.allure.junit5.AllureJunit5;
import jupiter.extensions.ArtistExtension;
import jupiter.extensions.MuseumExtension;
import jupiter.extensions.PaintingExtension;
import jupiter.extensions.UserExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
//@ExtendWith({MuseumExtension.class,
//        ArtistExtension.class,
//        PaintingExtension.class,
//        UserExtension.class,
//        AllureJunit5.class})
public @interface GrpcTest {
}
