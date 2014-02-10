package com.e2open.falcon.view;

import com.e2open.falcon.waiters.Waiter;
import com.e2open.falcon.browser.BrowserManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.List;

public abstract class PageImpl implements BrowserPage {
    public final BrowserManager browserSession = BrowserManager.INSTANCE;
    private String lastActiveWindow;
    private Waiter waiter;

    // make sure we always have a context properly set so the
    // page objects are primed. Mostly useful for making sure you're
    // not chasing ghosts in debug mode
    public PageImpl() {
        waiter = new Waiter();
    }

    public WebDriver browser() {
        return browserSession.getDriver();
    }

    public boolean exists(By by) {
        boolean exists;
        try {
            exists = browser().findElements(by).size() != 0;
        } catch (UnreachableBrowserException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            exists = false;
        }
        return exists;
    }

    public boolean visible(By by) {
        return browser().findElement(by).isDisplayed();
    }


    public WebElement parent(WebElement element) {
        return element.findElement(By.xpath(".."));
    }

    public WebElement getElement(By by) {
        setContext();
        return browser().findElement(waiter.waitUntilPresent(by));
    }

    protected void setFrame(String name) {
        String[] names = new String[1];
        names[0] = name;
        setFrame(names);
    }

    // switch to the default context (main window)
    // and then get to any nested frame
    protected void setFrame(String... names) {
        browser().switchTo().defaultContent();
        for (String name : names) {
            By by = By.cssSelector(name);
            waiter.waitUntilFrameVisibleAndSwitch(by);
        }
    }

    protected void popupWindow() {
        waiter.waitUntilDialogPresent();
        Object[] windows = browser().getWindowHandles().toArray();
        lastActiveWindow = (String) windows[windows.length - 2];
        String lastWindow = (String) windows[windows.length - 1];
        browser().switchTo().window(lastWindow);
    }

    public void releasePopupWindow() {
        browser().switchTo().window(lastActiveWindow);
        lastActiveWindow = null;
    }

    public void setContext() {
        browser().switchTo().defaultContent();
    }

    // table locators
    protected static String getRowContainingText(String tableId, String text) {
        return String.format("//table[@id='%s']//tr[td//text()[contains(., '%s')]]", tableId, text);
    }

    protected int getColumnContainingText(WebElement headerRow, String searchText) {
        List<WebElement> headerCells = headerRow.findElements(By.cssSelector("td"));
        int cellIndex = -1;
        for (int i = 0; i < headerCells.size(); i++) {
            WebElement cell = headerCells.get(i);
            if (cell.getText().toLowerCase().equals(searchText.toLowerCase())) {
                cellIndex = i;
            }
        }
        return cellIndex;
    }

    protected WebElement getCellByColumnName(WebElement headerRow, String columnName, String tableId, String documentType) {
        int columnIndex = getColumnContainingText(headerRow, columnName);
        return getCellByColumnIndex(tableId, columnIndex, documentType);
    }

    protected WebElement getCellByColumnIndex(String tableId, int columnIndex, String documentType) {
        WebElement row = getElement(By.xpath(getRowContainingText(tableId, documentType)));
        List<WebElement> cells = row.findElements(By.cssSelector("td"));
        return cells.get(columnIndex);
    }


}
