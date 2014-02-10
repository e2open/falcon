package com.e2open.falcon.browser;

import com.e2open.falcon.Configuration;
import org.openqa.selenium.WebDriver;

import java.util.EnumMap;

public enum BrowserManager {
    INSTANCE;

    private Browser activeBrowser = null;
    public EnumMap<BrowserType, Browser> browsers = new EnumMap<BrowserType, Browser>(BrowserType.class);

    public BrowserType getBrowserType() {
        return browser().getType();
    }

    public WebDriver getDriver() {
        return browser().driver();
    }

    public Browser browser() {
        if (activeBrowser == null) {
            activeBrowser = createBrowser(getDefaultBrowserType());
        }
        return activeBrowser;
    }

    public Browser browser(BrowserType browserType) {
        if (activeBrowser == null) {
            activeBrowser = createBrowser(browserType);
        }
        return activeBrowser;
    }

    protected void resetActiveBrowser() {
        browsers.remove(activeBrowser.getType());
        activeBrowser = null;
    }

    private Browser createBrowser(BrowserType browserType) {
        if (browsers.containsKey(browserType)) return browsers.get(browserType);
        Browser newBrowser = browserType.create_browser();
        cacheBrowser(newBrowser);
        return newBrowser;
    }

    private void cacheBrowser(Browser browser) {
        BrowserType browserType = browser.getType();
        if (browsers.get(browserType) == null) {
            browsers.put(browserType, browser);
        }
    }

    public BrowserType getDefaultBrowserType() {
        String browserName = Configuration.getProperty("browser");
        BrowserType browserType;
        if (browserName != null) {
            browserType = BrowserType.valueOf(browserName.toUpperCase());
        } else {
            browserType = BrowserType.FIREFOX;
        }
        return browserType;
    }
}
