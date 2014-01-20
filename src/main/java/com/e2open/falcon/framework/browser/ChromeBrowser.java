package com.e2open.falcon.framework.browser;

import com.e2open.falcon.framework.Configuration;
import com.e2open.falcon.framework.helpers.FileHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromeBrowser extends AbstractBrowser {

	public ChromeBrowser() {
		super();
		type = BrowserType.CHROME;
	}

	public WebDriver driver() {
		if (webdriver == null) {
			updateProfile();
			webdriver = new ChromeDriver(getProfile());
		}
		return webdriver;
	}
	
	public void maximize () {
		// Chrome does not currently support maximizing the browser
	}

	private void updateProfile () {
		System.setProperty("webdriver.chrome.driver", FileHelper.getResourceFilePath("browser_drivers/chromedriver.exe"));
	}
	
	private DesiredCapabilities getProfile()	{
		DesiredCapabilities profile = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		// http://peter.sh/experiments/chromium-command-line-switches/
		options.addArguments(Configuration.INSTANCE.getProperty("chrome.properties").split("\\s*,\\s*"));
		profile.setCapability(ChromeOptions.CAPABILITY, options);
		profile.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		return profile;
	}
}	