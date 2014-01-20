package com.e2open.falcon.framework.browser;

import com.e2open.falcon.framework.Configuration;
import org.openqa.selenium.WebDriver;

import java.util.EnumMap;

public enum BrowserManager {
    INSTANCE;

    private Browser activeBrowser = null;
    public EnumMap<BrowserType, Browser> browsers = new EnumMap<BrowserType, Browser>(BrowserType.class);

    public void resetActiveBrowser()	{
        browsers.remove(activeBrowser.getType());
    	activeBrowser = null;
    }

    public Browser browser () {
        if (activeBrowser == null) {
            getDefaultBrowser();
        }
        return activeBrowser;
    }

    public BrowserType getBrowserType () {
        return browser().getType();
    }

    public WebDriver getDriver () {
        return browser().driver();
    }

    public WebDriver getBrowser(BrowserType browserType) {
        Browser browser = browsers.get(browserType);
        if (browser != null) {
            return setActiveBrowser(browser);
        } else {
            return createBrowser(browserType);
        }
    }

    public WebDriver getDefaultBrowser () {
        return getBrowser(getDefaultBrowserType());
    }


    public WebDriver createBrowser(BrowserType browserType) {
        Browser newBrowser = browserType.create_browser();
        return setActiveBrowser(newBrowser);
    }

    private WebDriver setActiveBrowser(Browser browser) {
        activeBrowser = browser;
        return addBrowser(browser);
    }

    private WebDriver addBrowser(Browser browser) {
        BrowserType browserType = browser.getType();
        if (browsers.get(browserType) == null) {
            browsers.put(browserType, browser);
        }
        return getDriver();
    }

    public BrowserType getDefaultBrowserType() {
        String browserName = Configuration.INSTANCE.getProperty("browser.type");
        BrowserType browserType;
        if(browserName != null) {
            browserType =  BrowserType.valueOf(browserName.toUpperCase());
        } else {
            browserType =  BrowserType.FIREFOX;
        }
        return browserType;
    }
}
