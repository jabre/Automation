package common;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;


public class WebDriverFactory {
	
	private static DesiredCapabilities GetDesiredCapabilities (String browserName, Proxy proxy) {
		DesiredCapabilities desiredCapabilities = null;
		
		switch (browserName.toLowerCase()) {
        case "firefox":
            desiredCapabilities = DesiredCapabilities.firefox();
            break;
        case "chrome":
        	desiredCapabilities = DesiredCapabilities.chrome();
        	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe");
        	break;
        case "safari":
			desiredCapabilities = DesiredCapabilities.safari();
			SafariOptions safariOptions = new SafariOptions();
			safariOptions.setUseCleanSession(true);
			desiredCapabilities.setCapability(SafariOptions.CAPABILITY,safariOptions);
			break;
        case "internet explorer":
        	desiredCapabilities = DesiredCapabilities.internetExplorer();
        	desiredCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        	break;
        default:
            desiredCapabilities = new DesiredCapabilities();
        }
		
		if (proxy != null)
			desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);
		
		return desiredCapabilities;
	}

    public static WebDriver CreateDriver(String browser, Proxy proxy) {
    	String browserName = browser;
    	if (browserName == null)
    		browserName = Configuration.getBrowser();
    	
    	DesiredCapabilities desiredCapabilities = GetDesiredCapabilities(browserName, proxy);
    	
        switch (browserName.toLowerCase()) {
        case "firefox":
        	return new FirefoxDriver(desiredCapabilities);
        case "chrome":
            return new ChromeDriver(desiredCapabilities);
        case "safari":
			return new SafariDriver(desiredCapabilities);
        case "internet explorer":        	
            return new InternetExplorerDriver(desiredCapabilities);
        default:
            return new HtmlUnitDriver();
        }
    }
    
    public static RemoteWebDriver CreateRemoteDriver(String browser, Proxy proxy) {
    	URL url = null;
    	DesiredCapabilities desiredCapabilities = null;
    	if (browser == null)
    		browser = Configuration.getBrowser();
    	
		try {
			url = new URL(Configuration.getSeleniumGridUrl());
			desiredCapabilities = GetDesiredCapabilities(browser, proxy);
		} catch (MalformedURLException e) {
			System.out.println("Failed to create remote driver.");
			e.printStackTrace();
			assert false;
		}

		return new RemoteWebDriver(url, desiredCapabilities);
	}
}


