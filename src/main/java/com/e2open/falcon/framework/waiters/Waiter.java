package com.e2open.falcon.framework.waiters;

import com.e2open.falcon.framework.Configuration;
import com.e2open.falcon.framework.browser.BrowserManager;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

public class Waiter {
    private static Long waitTimeout = (long) 60;


    private WebDriver browser() {
        return BrowserManager.INSTANCE.getDriver();
    }

    public Waiter() {
        String waitConfigTimeout = Configuration.getProperty("wait.timeout");
        if (StringUtils.isNotBlank(waitConfigTimeout)) waitTimeout = Long.parseLong(waitConfigTimeout);
    }

    public By waitUntilPresent(By by) {
        waitUntilElementPresentInDOM(by);
        return waitUntilEnabled(by);
    }

    public By waitUntilVisible(By by) {
        WebDriverWait wait = new WebDriverWait(browser(), waitTimeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return by;
    }

    public void waitUntilFrameVisibleAndSwitch(By by) {
        WebDriverWait wait = new WebDriverWait(browser(), waitTimeout);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
    }

    public By waitUntilEnabled(final By by) {
        WaitCondition elementIsEnabled = new WaitCondition() {
            public boolean isSatisfied() {
                return browser().findElement(by).isEnabled();
            }
        };
        waitForCondition(elementIsEnabled, "Element not enabled: " + by.toString());
        return by;
    }

    public void waitUntilSelectOptionPresent(final Select selectList, final String optionText) {
        WaitCondition selectListOptionVisible = new WaitCondition() {
            public boolean isSatisfied() {
                boolean found = false;
                for (WebElement option : selectList.getOptions()) {
                    if (option.getText().equals(optionText)) found = true;
                }
                return found;
            }
        };
        waitForCondition(selectListOptionVisible,  "Unable to locate select element text: " + optionText);
    }

    public void waitUntilDialogPresent() {
        WaitCondition dialogIsPresent = new WaitCondition() {
            public boolean isSatisfied() {
                return (browser().getWindowHandles().toArray().length > 1);
            }
        };
        waitForCondition(dialogIsPresent, "Unable to locate popup window");
    }


    // a generic waiter that will poll for a result to
    // be true. This is passed in as a WaitCondition so
    // we can set some arbitrary code as a condition but still
    // use the same polling logic
    public boolean waitForCondition(WaitCondition waitCondition, String errorMessage) {
        Long elapsed = (long) 0;
        while (elapsed <= (waitTimeout * 1000)) {
            try {
                if (waitCondition.isSatisfied()) return true;
            } catch (NoSuchElementException e) {
                //keep waiting
            }
            Long pollingInterval = (long) 250;
            try {
                Thread.sleep(pollingInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            elapsed += pollingInterval;
        }
        if (elapsed >= waitTimeout) {
            throw new RuntimeException(errorMessage);
        }
        return false;
    }

    private void waitUntilElementPresentInDOM(By by) {
        WebDriverWait wait = new WebDriverWait(browser(), waitTimeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

}
