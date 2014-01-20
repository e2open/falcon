package com.e2open.falcon.framework.model;

import com.e2open.falcon.framework.Configuration;
import com.e2open.falcon.framework.annotations.AutoPopulateOff;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Model {
    @AutoPopulateOff
    private static final String testRunUUID = initializeUUID();

    @AutoPopulateOff
    private String instanceUUID = null;

    @AutoPopulateOff
    public UIDStrategy uidStrategy;

    public enum UIDStrategy {NONE, BY_TEST_RUN, BY_CLASS_INSTANCE}

    public Model() {
        instanceUUID = initializeUUID();
        uidStrategy = UIDStrategy.NONE;
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
        if (uidStrategy == null) uidStrategy = UIDStrategy.NONE;
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
        if (uidStrategy == null) uidStrategy = UIDStrategy.NONE;
        String uuid = getUUID();
        return (uuid == null) ? value : value + getUUID();
    }

    private static String initializeUUID() {
        String uuid = Configuration.INSTANCE.getProperty("model.uuid");
        if (StringUtils.isNotBlank(uuid)) {
            return uuid;
        } else {
            uuid = Long.toString(UUID.randomUUID().getMostSignificantBits());
            System.out.println(String.format("UUID Created %s for class", uuid));
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

