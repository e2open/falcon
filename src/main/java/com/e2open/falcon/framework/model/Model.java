package com.e2open.falcon.framework.model;

import com.e2open.falcon.framework.Configuration;
import com.e2open.falcon.framework.annotations.AutoPopulateOff;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Model {
    @AutoPopulateOff
    public static final String testRunUUID = initializeUUID();

    @AutoPopulateOff
    private String instanceUUID = null;

    @AutoPopulateOff
    public UIDStrategy uidStrategy;

    public enum UIDStrategy {NONE, BY_TEST_RUN, BY_CLASS_INSTANCE}

    public Model() {
        instanceUUID = initializeUUID();
        uidStrategy = getDefaultStrategy();
    }

    private UIDStrategy getDefaultStrategy() {
        String defaultStrategy = Configuration.INSTANCE.getProperty("model.default.uuid.strategy");
        if (defaultStrategy != null) {
            return UIDStrategy.valueOf(defaultStrategy.toUpperCase());
        } else {
            return UIDStrategy.NONE;
        }
    }

    public Model(UIDStrategy uidStrategy) {
        instanceUUID = initializeUUID();
        this.uidStrategy = uidStrategy;
    }

    public String getTestRunUUID() {
        return testRunUUID;
    }

    public String getInstanceUUID() {
        return instanceUUID;
    }

    public String getUUID() {
        if (uidStrategy == null) uidStrategy = getDefaultStrategy();
        if (uidStrategy == UIDStrategy.NONE) {
            return null;
        } else if (uidStrategy == UIDStrategy.BY_TEST_RUN) {
            return testRunUUID;
        } else if (uidStrategy == UIDStrategy.BY_CLASS_INSTANCE) {
            return instanceUUID;
        } else {
            throw new RuntimeException("Unknown strategy: " + uidStrategy.toString());
        }
    }

    protected String getUniqueValue(String value) {
        if (StringUtils.isBlank(value)) return null;
        if (uidStrategy == null) uidStrategy = getDefaultStrategy();
        String uuid = getUUID();
        if (uuid == null || value.endsWith(uuid)) {
            return value;
        } else {
            return value + getUUID();
        }
    }

    private static String initializeUUID() {
        String uuid = Configuration.INSTANCE.getProperty("model.uuid");
        if (StringUtils.isNotBlank(uuid)) {
            return uuid;
        } else {
            uuid = Long.toString(UUID.randomUUID().getMostSignificantBits());
            return uuid;
        }
    }

    // TODO: there has to be a better way
    protected String transformConfigurationReferences(String rawValue) {
        if (StringUtils.isNotBlank(rawValue)) {
            String patternStr = "__CONFIGURATION.(.+)__";
            Pattern p = Pattern.compile(patternStr);
            Matcher m = p.matcher(rawValue);
            if (m.find(0)) {
                String matchedText = m.group(1);
                String property = Configuration.INSTANCE.getProperty(matchedText);
                rawValue = rawValue.replace(m.group(0), property);
            }
        }
        return rawValue;
    }

}

