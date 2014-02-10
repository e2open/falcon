package com.e2open.falcon.framework;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public final class Configuration {
    private static final Logger log = Logger.getLogger(Configuration.class);
    private static final Properties properties = new Properties();

    static {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        loadPropertiesFromFile(loader, "default.properties");
        loadPropertiesFromFile(loader, "global.properties");
        String localConfiguration = properties.getProperty("local.configuration.file");
        if (localConfiguration != null) {
            loadPropertiesFromFile(loader, localConfiguration);
        }
        showConfiguration();
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

    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public static void removeProperty(String key) {
        properties.remove(key);
    }

    private static void showConfiguration() {
        log.info("===================================================================================");
        for (String property : properties.stringPropertyNames()) {
            log.info(String.format("%-40s%s", property, getProperty(property)));
        }
        log.info("===================================================================================");
    }

}
