package com.e2open.falcon.framework.browser;

public enum BrowserType {
	FIREFOX {
		@Override
		public Browser create_browser() {
			return new FirefoxBrowser();
		}
	},
	INTERNETEXPLORER {
		@Override
		public Browser create_browser() {
			return new InternetExplorerBrowser();
		}
	},
	CHROME {
		@Override
		public Browser create_browser() {
			return new ChromeBrowser();
		}
	},
	HTMLUNIT {
		@Override
		public Browser create_browser() {
			return new HtmlUnit();
		}
	}
	;
	
	public abstract Browser create_browser();
}