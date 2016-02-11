package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import common.BaseSeleniumTestCase;
import java.io.*;
import net.sourceforge.tess4j.*;

public class LoginTests extends BaseSeleniumTestCase {

	@Test
	public void testSignInFromLoginPage() {
		log.info("Navigate to Login page.");
		loginPage.openPage();
		Assert.assertTrue(loginPage.isPageOpened());

		log.info("Sign in");
	    loginPage.login("qaautomation@test.com", "7D*3gK!i");
	    File imageFile = new File("C:\\Users\\mradivojevic\\Documents\\test.png");
	    Tesseract instance = Tesseract.getInstance(); //

	    try {

	    String result = instance.doOCR(imageFile);
	    System.out.println(result);

	    } catch (TesseractException e) {
	    System.err.println(e.getMessage());
	    }
	}
}

