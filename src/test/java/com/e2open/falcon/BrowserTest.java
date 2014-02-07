package com.e2open.falcon;

import com.e2open.falcon.framework.browser.BrowserManager;
import com.e2open.falcon.framework.browser.BrowserType;
import com.e2open.falcon.framework.helpers.FileHelper;
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
        BrowserManager.INSTANCE.createBrowser(BrowserType.INTERNETEXPLORER);
        openUrl();
        checkPage();
    }

    @Test
    public void firefox() {
        BrowserManager.INSTANCE.createBrowser(BrowserType.FIREFOX);
        openUrl();
        checkPage();
    }

    @Test
    public void chrome() {
        BrowserManager.INSTANCE.createBrowser(BrowserType.CHROME);
        openUrl();
        checkPage();
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
