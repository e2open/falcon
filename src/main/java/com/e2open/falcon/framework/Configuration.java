package com.e2open.falcon.framework;

import java.io.IOException;
import java.util.Properties;

public final class Configuration {
    private static final Properties properties = new Properties();

    static {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        loadPropertiesFromFile(loader, "global.properties");
        String localConfiguration = properties.getProperty("local.configuration.file");
        if (localConfiguration != null) {
            loadPropertiesFromFile(loader, localConfiguration);
        }
    }

    private static void loadPropertiesFromFile(ClassLoader loader, String fileName) {
        try {
            properties.load(loader.getResourceAsStream(String.format("%s/%s", "configuration", fileName)));
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
