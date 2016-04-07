package common;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import pageObjects.HeaderPageObject;
import pageObjects.LoginPageObject;
import pageObjects.mytelenor.OseMyTelenorSubscriptionPageObject;
import pageObjects.oseagentview.OseAgentViewCustomerPageObject;
import pageObjects.oseagentview.OseAgentViewPageObject;
import pageObjects.oseagentview.OseAgentViewShopPageObject;
import pageObjects.veriscrm.VerisCrmDashboardPageObject;
import pageObjects.veriscrm.VerisCrmLoginPageObject;

public class BaseSeleniumTestCase {

    public WebDriver driver;
    public WebDriverWait wait;
    public String testMethodName;
    public String sessionId;
    public Log log;
    
    // create Page objects
    public LoginPageObject loginPage;
    public HeaderPageObject header;
    public VerisCrmLoginPageObject verisCrmLoginPage;
    public VerisCrmDashboardPageObject verisCrmDashboardPage;
    public OseAgentViewPageObject oseAgentViewPage;
    public OseAgentViewCustomerPageObject oseAgentViewCustomerPage;
    public OseMyTelenorSubscriptionPageObject oseMyTelenorSubscriptionPage;
    public OseAgentViewShopPageObject oseAgentViewShopPage;
    
    public boolean acceptNextAlert = true;

    private WebDriver createWebDriver(String browser, Proxy proxy) {
        String environment = Configuration.getEnvironment();
        
    	if (environment.equals("remote")) {
            return WebDriverFactory.CreateRemoteDriver(browser, proxy);
        } else {
            return WebDriverFactory.CreateDriver(browser, proxy);
        }
    }
    
    @Parameters({"browser"})
    @BeforeClass
    public void setUpSuite(@Optional String browser) {

    	Proxy proxy = null;

    	this.driver = this.createWebDriver(browser, proxy);
    	this.sessionId = ((RemoteWebDriver) this.driver).getSessionId().toString();
    	this.log = new Log(this.sessionId);

        this.driver.manage().timeouts().implicitlyWait(Configuration.getDefaultPageTimeout(), TimeUnit.SECONDS);
        this.wait = new WebDriverWait(driver, Configuration.getDefaultWebElementTimeout());
        this.driver.get(Configuration.getBaseUrl());
        this.driver.manage().deleteAllCookies();
        
        this.driver.manage().window().maximize();
        
        //initialize all the page objects here
        loginPage = new LoginPageObject(this.driver, this.log);
        header = new HeaderPageObject(this.driver, this.log);
        verisCrmLoginPage = new VerisCrmLoginPageObject(this.driver, this.log);
        verisCrmDashboardPage = new VerisCrmDashboardPageObject(this.driver, this.log);
        oseAgentViewPage = new OseAgentViewPageObject (this.driver, this.log);
        oseAgentViewCustomerPage = new OseAgentViewCustomerPageObject (this.driver, this.log);
        oseMyTelenorSubscriptionPage = new OseMyTelenorSubscriptionPageObject(this.driver, this.log);
        oseAgentViewShopPage = new OseAgentViewShopPageObject(this.driver, this.log);     

    }
    
    @BeforeMethod
    public void setUpMethod(Method method)
    {
    	this.testMethodName = method.getName();
    	log.info(this.testMethodName + ", browser = " + Configuration.getBrowser() + ", thread id = " + Thread.currentThread().getId());
    }
    
    
	public void takeScreenshot(String failedMethod) {

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("dd.M.yyyy:hhmmssa");
		File directory = new File("");
		if (!Configuration.getEnvironment().equals("local"))
			driver = new Augmenter().augment(driver);
		final File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		log.info("Thread id that failed = " + Thread.currentThread().getId());
		try {
			// Save the Screenshot
			String filePath = getClass().getSimpleName() + "_" + failedMethod
					+ "_" + ft.format(dNow);
			String saveAs = directory.getAbsolutePath()
					+ "\\target\\screenshots\\" + filePath + ".png";
			FileUtils.copyFile(scrFile, new File(saveAs));
			log.info("Screenshot saved as: " + saveAs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
        
	@AfterMethod
	public void tearDownMethod(ITestResult result) {	
		if (!result.isSuccess()) {
			log.error(this.testMethodName + " failed.");
			this.takeScreenshot(this.testMethodName);
		}
		
		log.outputLog(result);
	}
	
	
	@AfterClass
	public void tearDown() {	
		driver.quit();
	}
}
