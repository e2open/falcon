package com.e2open.falcon.framework.controller;

import com.e2open.falcon.framework.model.Model;
import org.openqa.selenium.WebDriver;

public interface BrowserController {
    public WebDriver browser();
    public void populateValues(Model model) throws Exception;
    public String getErrorMessage();
}

