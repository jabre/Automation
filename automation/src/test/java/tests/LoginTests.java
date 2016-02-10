package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.BaseSeleniumTestCase;

public class LoginTests extends BaseSeleniumTestCase {

	@Test
	public void testSignInFromLoginPage() {
		log.info("Navigate to Login page.");
		loginPage.openPage();
		Assert.assertTrue(loginPage.isPageOpened());

		log.info("Sign in");
	    loginPage.login("qaautomation@test.com", "7D*3gK!i");
	}
}

