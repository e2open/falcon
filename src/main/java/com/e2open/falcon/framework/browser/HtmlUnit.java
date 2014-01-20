package com.e2open.falcon.framework.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class HtmlUnit extends AbstractBrowser {

	public WebDriver driver() {
		if (webdriver == null) {
			webdriver = new HtmlUnitDriver();
		}
		return webdriver;
	}

}
