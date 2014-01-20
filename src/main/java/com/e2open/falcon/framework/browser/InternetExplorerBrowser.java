package com.e2open.falcon.framework.browser;

import com.e2open.falcon.framework.helpers.FileHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class InternetExplorerBrowser extends AbstractBrowser {

    private static InternetExplorerDriverService service;

    public InternetExplorerBrowser() {
        super();
        type = BrowserType.INTERNETEXPLORER;
    }

    public WebDriver driver() {
        if (webdriver == null) {
            updateProfile();
            webdriver = new InternetExplorerDriver();
        }
        return webdriver;
    }

    private void updateProfile() {
        System.setProperty("webdriver.ie.driver", FileHelper.getResourceFilePath("browser_drivers/IEDriverServer.exe"));
    }

    private DesiredCapabilities profile() {
        return DesiredCapabilities.internetExplorer();
    }
}
