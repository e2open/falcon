package com.e2open.falcon.browser;

import com.e2open.falcon.Configuration;
import com.e2open.falcon.helpers.FileHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InternetExplorerBrowser extends AbstractBrowser {

    private static InternetExplorerDriverService service;

    public InternetExplorerBrowser() {
        super();
        type = BrowserType.INTERNETEXPLORER;
    }

    public WebDriver driver() {
        if (webdriver == null) {
            System.setProperty("webdriver.ie.driver", FileHelper.getResourceFilePath("browser_drivers/IEDriverServer.exe"));
            DesiredCapabilities profile = getProfile();
            webdriver = new InternetExplorerDriver(null, profile);
        }
        return webdriver;
    }

    private DesiredCapabilities getProfile() {
        DesiredCapabilities profile = DesiredCapabilities.internetExplorer();
        String[] options = Configuration.getProperty("internetexplorer.properties").split("\\s*,\\s*");
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
            if (key.equals("initialBrowserUrl")) {
                profile.setCapability("initialBrowserUrl", value);
            } else if (value.matches("\\d+")) {
                profile.setCapability(key, Integer.parseInt(value));
            } else if (value.matches("true|false")) {
                profile.setCapability(key, Boolean.parseBoolean(value));
            } else {
                profile.setCapability(key, value);
            }
        }


        profile.setCapability(CapabilityType.BROWSER_NAME, "Internet Explorer");
        profile.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
        profile.setCapability(CapabilityType.HAS_NATIVE_EVENTS,false);
        profile.setJavascriptEnabled(true);
        return profile;
    }

}
