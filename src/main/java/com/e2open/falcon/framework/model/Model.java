package com.e2open.falcon.framework.model;

import com.e2open.falcon.framework.Configuration;
import com.e2open.falcon.framework.annotations.AutoPopulateOff;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Model {
    private static final Logger log = Logger.getLogger(Model.class);
    private static List<String> userNotifiedOfUUID = new ArrayList<String>();

    @AutoPopulateOff
    public static final String testRunUUID = getNewUUID();

    @AutoPopulateOff
    private String instanceUUID = null;

    @AutoPopulateOff
    public UIDStrategy uidStrategy;

    public enum UIDStrategy {NONE, BY_TEST_RUN, BY_CLASS_INSTANCE}

    public Model() {
        instanceUUID = getNewUUID();
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
        instanceUUID = getNewUUID();
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
            logUUID(testRunUUID);
            return testRunUUID;
        } else if (uidStrategy == UIDStrategy.BY_CLASS_INSTANCE) {
            logUUID(instanceUUID);
            return instanceUUID;
        } else {
            throw new RuntimeException("Unknown strategy: " + uidStrategy.toString());
        }
    }

    private void logUUID(String uid) {
        if (!userNotifiedOfUUID.contains(uid)) {
            switch (uidStrategy) {
                case NONE:
                    break;
                case BY_TEST_RUN:
                    if (!userNotifiedOfUUID.contains(uid)) {
                        log.info(String.format("UUID Created %s for test run", instanceUUID));
                        userNotifiedOfUUID.add(uid);
                    }
                    break;
                case BY_CLASS_INSTANCE:
                    if (!userNotifiedOfUUID.contains(uid)) {
                        log.info(String.format("UUID Created %s for class instance of %s", instanceUUID, getClass().getName()));
                        userNotifiedOfUUID.add(uid);
                    }
                    break;
            }
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

    private static String getNewUUID() {
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

