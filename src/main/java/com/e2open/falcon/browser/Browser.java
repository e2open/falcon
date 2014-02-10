package com.e2open.falcon.browser;

import org.openqa.selenium.WebDriver;

public interface Browser {
	void open();
	
	void close();
	
	BrowserType getType();
	
	WebDriver driver();
}
