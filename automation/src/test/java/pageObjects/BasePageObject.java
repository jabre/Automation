package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import common.Configuration;
import common.Log;

public class BasePageObject {

    protected WebDriver driver;
    protected Log log;
    protected WebDriverWait wait;
    protected Actions actions;

    public BasePageObject(WebDriver driver, Log log) {
        this.driver = driver;
        this.log = log;
        wait = new WebDriverWait(driver, Configuration.getDefaultWebElementTimeout());
        actions = new Actions(driver); 
        PageFactory.initElements(driver, this);
    }
    
    public void navigate(String url) {
        driver.navigate().to(url);      
    }
    
	public void mouseOver(WebElement element) {
		if (!(driver instanceof SafariDriver)) {
			actions.moveToElement(element).perform();
		} else {
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			((JavascriptExecutor) driver).executeScript(mouseOverScript, element);
		}
	}
    
    public boolean isAjaxCompleted() {
		WebDriverWait wait = new WebDriverWait(driver, Configuration.getDefaultWebElementTimeout());
		try {
			wait.until(new Predicate<WebDriver>() {
				public boolean apply(WebDriver d) {
	            	return (boolean) ((JavascriptExecutor)d).executeScript("if (typeof jQuery !== 'undefined') return jQuery.active === 0 && document.readyState === 'complete'; else if (typeof jQuery === 'undefined') return document.readyState === 'complete'; else return false;");
	            }
	        });
		} catch (TimeoutException e) {
			return false;
		}
		
		return true;
	}
    
    /**
     * Scrolls to the specified element.
     * @param WebElement
     */
    public void scrollToElement(WebElement element){
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }
    
    /**
     * Waits for specified element to become visible.
     * @param WebElement
     * Returns visible element or null
     */
    public WebElement isElementVisible(WebElement element){    	
    	try{
    		wait.until(ExpectedConditions.visibilityOf(element));
    		return element;
    	}
    	catch(TimeoutException e){
    		return null;
    	}
    }
    
	public void staticWait(long seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {}
	}
}