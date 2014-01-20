package com.e2open.falcon.framework.browser;

import org.openqa.selenium.WebDriver;

abstract class AbstractBrowser implements Browser {
	
	protected WebDriver webdriver = null; 
	public BrowserType type = null;

	public void open() {
		driver();
	}
	
	public void close() {
		if (webdriver != null){
			try	{
				webdriver.quit();
			} catch (Exception e){
				// ignore any exceptions
			}
		}
		BrowserManager.INSTANCE.resetActiveBrowser();
	}
	
	public BrowserType getType() {
		return type;
	}
	
    public void maximize() {
    	webdriver.manage().window().maximize();
    }
}
