package jupiter.annotations;


import io.qameta.allure.junit5.AllureJunit5;
import jupiter.extensions.ApiLoginExtension;
import jupiter.extensions.BrowserExtension;
import jupiter.extensions.ScreenShotTestExtension;
import jupiter.extensions.UserExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith({
        BrowserExtension.class,
        AllureJunit5.class,
        UserExtension.class,
        ApiLoginExtension.class,
        ScreenShotTestExtension.class
})
public @interface WebTest {
}