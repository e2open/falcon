package com.e2open.falcon.framework;

import com.e2open.falcon.framework.helpers.FileHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public enum Configuration implements Serializable {
    INSTANCE;

    private final java.util.Properties properties;

    private Configuration() {
        properties = new java.util.Properties();
        loadProperties("global.properties");
        String stackConfiguration = getProperty("site.configuration.file");
        if (stackConfiguration != null) {
            loadProperties(stackConfiguration);
        }
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    private void loadProperties(String name) {
        try {
            File file = getResourceFile(name);
            FileInputStream in = new FileInputStream(file);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to locate properties file");
        }
    }

    private File getResourceFile(String fileName) {
        String name = String.format("%s/%s", "configuration", fileName);
        return new File(FileHelper.getResourceFilePath(name));
    }
}
