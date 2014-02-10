package com.e2open.falcon;

import com.e2open.falcon.browser.Browser;
import com.e2open.falcon.browser.BrowserManager;
import com.e2open.falcon.browser.BrowserType;
import com.e2open.falcon.helpers.FileHelper;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static junit.framework.TestCase.assertEquals;

public class BrowserTest {

    @After
    public void afterEach() {
        BrowserManager.INSTANCE.browser().close();
    }

    @Test
    public void openDefaultBrowser() {
        openUrl();
    }

    @Test
    public void internetExplorer() {
        BrowserManager.INSTANCE.browser(BrowserType.INTERNETEXPLORER);
        openUrl();
        checkPage();
    }

    @Test
    public void firefox() {
        BrowserManager.INSTANCE.browser(BrowserType.FIREFOX);
        openUrl();
        checkPage();
    }

    @Test
    public void chrome() {
        BrowserManager.INSTANCE.browser(BrowserType.CHROME);
        openUrl();
        checkPage();
    }

    @Test
    public void default_browser_equals_configuration_setting() {
        Configuration.removeProperty("browser");
        assertEquals(BrowserType.FIREFOX, BrowserManager.INSTANCE.getBrowserType());
    }

    @Test
    public void configuration_setting_used() {
        Configuration.setProperty("browser", "chrome");
        assertEquals(BrowserType.CHROME, BrowserManager.INSTANCE.getBrowserType());
    }

    @Test
    public void browser_reuse() {
        Configuration.removeProperty("browser");
        Browser driver = BrowserManager.INSTANCE.browser();
        assertEquals(driver, BrowserManager.INSTANCE.browser());
    }


    private void openUrl() {
        String url = FileHelper.getResourceFilePath("html/elements.html");
        browser().get(String.format("file:%s", url));
    }

    private void checkPage() {
        assertEquals("Falcon Element Test Page", browser().getTitle());
    }

    private WebDriver browser() {
        return BrowserManager.INSTANCE.getDriver();
    }
}
