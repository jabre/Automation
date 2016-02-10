package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import common.Log;

public class HeaderPageObject extends BasePageObject {

    public HeaderPageObject(WebDriver driver, Log log) {
    	super(driver, log);
    }
    
    @FindBy(css = "a.secondary-nav__link.secondary-nav__link--account")
    public WebElement accountLink;
    
    @FindBy(linkText = "Help")
    public WebElement helpLink;
}

