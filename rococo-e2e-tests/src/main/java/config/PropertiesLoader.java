package config;

import org.hibernate.boot.cfgxml.internal.ConfigLoader;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private static final Properties properties = new Properties();

    static {
        properties.putAll(System.getProperties());
        load("at.properties");
    }

    private static void load(String name) {
        try (InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream(name)) {

            if (in == null) {
                System.out.println("SKIP: " + name + " (not found)");
                return;
            }

            System.out.println("LOAD: " + name);

            Properties tmp = new Properties();
            tmp.load(in);

            for (String key : tmp.stringPropertyNames()) {
                String value = tmp.getProperty(key);
                if (value != null && !value.trim().isEmpty()) {
                    properties.setProperty(key, value);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Cannot load " + name, e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
