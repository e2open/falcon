package framework;

import com.e2open.falcon.framework.browser.Browser;
import com.e2open.falcon.framework.browser.BrowserManager;
import com.e2open.falcon.framework.browser.BrowserType;
import com.e2open.falcon.framework.helpers.FileHelper;
import org.junit.After;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class BrowserTest {

    @After
    public void afterEach() {
        browser().close();
    }

    @Test
    public void openDefaultBrowser() {
        openUrl(BrowserManager.INSTANCE.getDefaultBrowserType());
    }

// TODO: why does this test lose the window handle after navigation?
//    @Test
//    public void internetExplorer() {
//        BrowserManager.INSTANCE.createBrowser(BrowserType.INTERNETEXPLORER);
//        openUrl(BrowserType.INTERNETEXPLORER);
//        browser().close();
//        assertTrue(browser().driver().getWindowHandles().size() == 0);
//    }

    @Test
    public void firefox() {
        BrowserManager.INSTANCE.createBrowser(BrowserType.FIREFOX);
        openUrl(BrowserType.FIREFOX);
    }

    @Test
    public void chrome() {
        BrowserManager.INSTANCE.createBrowser(BrowserType.CHROME);
        openUrl(BrowserType.CHROME);
    }

    private void openUrl(BrowserType browserType) {
        String url = FileHelper.getResourceFilePath("html/elements.html");
        browser().driver().navigate().to(String.format("file:/%s", url));
        assertTrue(browser().driver().getCurrentUrl().contains("elements.html"));
        assertEquals(browser().getType(), browserType);
    }

    private Browser browser() {
        return BrowserManager.INSTANCE.browser();
    }
}
