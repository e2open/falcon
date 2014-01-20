package com.e2open.falcon.framework.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class FirefoxBrowser extends AbstractBrowser {

	public FirefoxBrowser() {
		super();
		type = BrowserType.FIREFOX;
	}

	public WebDriver driver() {
		if (webdriver == null) webdriver = new FirefoxDriver(profile());
		return webdriver;
	}
	
	private FirefoxProfile profile ()	{
		FirefoxProfile profile = new FirefoxProfile();

		// Avoid unexpected behavior (disable auto update).
		profile.setPreference ("app.update.auto", false);

		// Avoid unexpected behavior (disable updates in general).
		profile.setPreference ("app.update.enabled", false);

		// Avoid unexpected dialog boxes (do not prompt for new updates).
		profile.setPreference ("app.update.mode", 0);

		// Avoid unexpected dialog boxes (do not prompt for new updates).
		profile.setPreference ("app.update.silent", true);

		// Avoid unexpected dialog boxes (warning after a com.e2open.falcon.framework.browser crash).
		profile.setPreference ("com.e2open.falcon.framework.browser.sessionstore.resume_from_crash", false);

		// Enhance performance. Controls how many closed tabs are kept track of through the Session Restore service.
		profile.setPreference ("com.e2open.falcon.framework.browser.sessionstore.max_tabs_undo", 0);

		// Enhance performance. Controls how many closed windows are kept track of through the Session Restore service.
		profile.setPreference ("com.e2open.falcon.framework.browser.sessionstore.max_windows_undo", 0);

		// Avoid unexpected dialog boxes. Controls whether the last saved session is restored once the next time the com.e2open.falcon.framework.browser starts.
		profile.setPreference ("com.e2open.falcon.framework.browser.sessionstore.resume_session_once", false);

		// Avoid unexpected dialog boxes. Checks if Mozilla Firefox is the default com.e2open.falcon.framework.browser.
		profile.setPreference ("com.e2open.falcon.framework.browser.shell.checkDefaultBrowser", false);

		// Minimize start up time of new tabs.
		profile.setPreference ("com.e2open.falcon.framework.browser.startup.homepage", "about:blank");

		// Minimize com.e2open.falcon.framework.browser startup time (no start page in initial tab).
		profile.setPreference ("com.e2open.falcon.framework.browser.startup.page", 0);

		// Avoid unexpected dialog boxes (warning when closing multiple tabs).
		profile.setPreference ("com.e2open.falcon.framework.browser.tabs.warnOnClose", false);

		// Avoid unexpected dialog boxes (warning when opening multiple tabs).
		profile.setPreference ("com.e2open.falcon.framework.browser.tabs.warnOnOpen", false);

		// Avoid unexpected dialog boxes (warning when XUL code takes too long to execute, timeout in seconds).
		profile.setPreference ("dom.max_chrome_script_run_time", 180);

		// Avoid unexpected dialog boxes (warning when script code takes too long to execute, timeout in seconds).
		profile.setPreference ("dom.max_script_run_time", 600);

		// Avoid unexpected dialog boxes. Disables automatic extension update.
		profile.setPreference ("extensions.update.enabled", false);

		// turn off popup blocker
		profile.setPreference ("com.e2open.falcon.framework.browser.popups.showPopupBlocker", true);
		profile.setPreference ("dom.disable_open_during_load", false);

		// Turn off session recovery when Firefox crashes
		profile.setPreference ("com.e2open.falcon.framework.browser.sessionstore.resume_from_crash", false);

		// Open links in new window (option=2).
		profile.setPreference ("com.e2open.falcon.framework.browser.link.open_newwindow", 2);

		// Dont remember passwords
		profile.setPreference ("signon.rememberSignons", false);

		profile.setEnableNativeEvents(false);
		return profile;
	}
}
