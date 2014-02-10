package com.e2open.falcon.view;

import org.openqa.selenium.WebElement;

public interface BrowserPage {
    public void setContext();
    public WebElement saveButton();
    public WebElement editButton();
    public WebElement cancelButton();
    public WebElement addButton();
    public WebElement searchButton();
    public String getErrorMessage();
}
