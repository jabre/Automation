package common;

public class Configuration {
	
	/**
	 * Returns base URL defined in the Maven parameter (if passed), or returns the default value.
	 * @return URL as a string.
	 */
	public static String getBaseUrl() {
		String baseUrl = "http://google.com";
		if (System.getProperty("baseurl") != null)
			baseUrl = System.getProperty("baseurl");

		return baseUrl;
	}
	
	/**
	 * Returns browser type defined in the Maven parameter (if passed), or returns default browser (firefox)
	 * @return Browser type as a string.
	 */
	public static String getBrowser() {
		String browser = "chrome";
		if (System.getProperty("browser") != null)
			browser = System.getProperty("browser");
		
		return browser;
	}

	/**
	 * Returns browser version defined in the Maven parameter (if passed).
	 * @return Browser version as a string.
	 */
	public static String getBrowserVersion() {
		String version = "";
		if (System.getProperty("browser-version") != null)
			version = System.getProperty("browser-version");
		
		return version;
	}

	/**
	 * Wait time for element to be available
	 * @return Time in seconds.
	 */
	public static long getDefaultWebElementTimeout() {
		return 45;
	}

	/**
	 * Wait time for page to load.
	 * @return Time in seconds.
	 */
	public static long getDefaultPageTimeout() {
		return 45;
	}

	/**
	 * Returns environment on which tests will be executed, defined in the Maven parameter (if passed), or returns default value (local computer). 
	 * @return Environment name.
	 */
	public static String getEnvironment() {
		String environment = "local";
		if (System.getProperty("environment") != null)
			environment = System.getProperty("environment");

		switch (environment.toLowerCase()) {
		case "local":
			return "local";
		case "remote":
			return "remote";
		default:
			return "local";
		}
	}
    
	/**
	 * Returns Selenium grid URL defined in the Maven parameter (if passed), or returns the default value.
	 * @return URL as a string.
	 */
	public static String getSeleniumGridUrl() {
		String url = "http://localhost:4444//wd/hub";
		if (System.getProperty("grid") != null)
			url = System.getProperty("grid");

		return url;
	}
}

