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
			webdriver.quit();
		}
		BrowserManager.INSTANCE.resetActiveBrowser();
	}
	
	public BrowserType getType() {
		return type;
	}
}
