package jupiter.extensions;

import config.PropertiesLoader;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TestMethodContextExtension implements BeforeEachCallback, AfterEachCallback {
    private static final ThreadLocal<ExtensionContext> store = new ThreadLocal<>();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        store.set(context);
        System.setProperty("test.env", PropertiesLoader.get("test.env"));
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        store.remove();
    }

    public static ExtensionContext context() {
        return store.get();
    }
}