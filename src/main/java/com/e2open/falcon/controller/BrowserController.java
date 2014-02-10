package com.e2open.falcon.controller;

import com.e2open.falcon.model.Model;
import org.openqa.selenium.WebDriver;

public interface BrowserController {
    public WebDriver browser();
    public void populateValues(Model model) throws Exception;
    public String getErrorMessage();
}

