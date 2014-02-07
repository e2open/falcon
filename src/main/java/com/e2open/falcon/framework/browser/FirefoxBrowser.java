package com.e2open.falcon.framework.browser;

import com.e2open.falcon.framework.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirefoxBrowser extends AbstractBrowser {

    public FirefoxBrowser() {
        super();
        type = BrowserType.FIREFOX;
    }

    public WebDriver driver() {
        if (webdriver == null) webdriver = new FirefoxDriver(getProfile());
        return webdriver;
    }

    private FirefoxProfile getProfile() {
        FirefoxProfile profile = new FirefoxProfile();
        String[] options = Configuration.getProperty("firefox.properties").split("\\s*,\\s*");
        for (String option : options) {
            String lineRegex = "([^=]+)=(.+)$";
            Pattern p = Pattern.compile(lineRegex);
            Matcher m = p.matcher(option);
            String key = null;
            String value = null;
            if (m.find(0)) {
                key = m.group(1);
                value = m.group(2).trim();
            }
            if (key == null) throw new RuntimeException("Unable to parse configuration option: " + option);
            if (value.matches("\\d+")) {
                profile.setPreference(key, Integer.parseInt(value));
            } else if (value.matches("true|false")) {
                profile.setPreference(key, Boolean.parseBoolean(value));
            } else {
                profile.setPreference(key, value);
            }
        }
        profile.setEnableNativeEvents(false);
        return profile;
    }
}
