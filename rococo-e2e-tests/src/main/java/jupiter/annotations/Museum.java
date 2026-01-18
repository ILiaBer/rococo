package jupiter.annotations;


import data.enums.Countries;
import jupiter.extensions.MuseumExtension;
import jupiter.extensions.PaintingExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(MuseumExtension.class)
public @interface Museum {
    String title() default "";

    String description() default "";

    Countries country() default Countries.FRANCE;

    String city() default "";

    String path() default "";

    boolean removeAfterTest() default true;
}
