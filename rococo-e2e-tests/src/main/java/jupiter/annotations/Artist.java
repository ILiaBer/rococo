package jupiter.annotations;

import jupiter.extensions.ArtistExtension;
import jupiter.extensions.MuseumExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(ArtistExtension.class)
public @interface Artist {
    String name() default "";
    String bio() default "";
    String photoPath() default "";
    boolean removeAfterTest() default true;
}
