package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import common.Configuration;
import common.Log;

public class LoginPageObject extends BasePageObject {

	public String path = "/login";
	
    public LoginPageObject(WebDriver driver, Log log) {
        super(driver, log);
    }
    
    HeaderPageObject header = new HeaderPageObject(this.driver, this.log);
    
    @FindBy(css = "input[name='email']")
    public WebElement username;

    @FindBy(css = "input[name='password']")
    public WebElement password;

    @FindBy(css = ".auth-panel__submit")
    public WebElement loginButton;
    
    public String openPage() {		
		log.info("Login page URL: " + Configuration.getBaseUrl() + path);
		this.driver.get(Configuration.getBaseUrl() + path);
		this.isAjaxCompleted();

		return path;
	}
	
	public boolean isPageOpened() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".auth-panel__submit")));
			wait.until(ExpectedConditions.visibilityOf(loginButton));			
			return true;
			
		} catch (TimeoutException e) {
			log.error("Login page failed to open.");
			return false;
		}
	}
    
    public void login(String username, String password) {
		this.username.clear();
		this.username.sendKeys(username);
		this.password.clear();
		this.password.sendKeys(password);
        this.loginButton.isDisplayed();
        this.loginButton.click();
		this.isAjaxCompleted();
        
        log.info("Verify that the account link is displayed after login.");
        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(header.accountLink));
        Assert.assertTrue(header.accountLink.isDisplayed(), "Account link is not displayed, login attempt unsuccessful.");
    }
}