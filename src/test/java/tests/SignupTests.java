package tests;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.CommonPage;
import pages.SignUpPage;
import utils.Driver;
import utils.TestDataReader;

public class SignupTests {
	
	@BeforeMethod (groups = "smoke-test")
	public void setup() {
		Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("Before method");
	}
	
	@Test
	public void test2() throws InterruptedException {
		Driver.getDriver().get("https://indeed.com");
		Thread.sleep(2000);
		System.out.println("Test 2 : " + " Thread:" +  Thread.currentThread().getId());
	}
	
	@Test
	public void test3() throws InterruptedException {
		Driver.getDriver().get("https://google.com");
		Thread.sleep(2000);
		System.out.println("Test 3 : " + " Thread:" +  Thread.currentThread().getId());
	}
	
	@Test
	public void test4() throws InterruptedException {
		Driver.getDriver().get("https://etsy.com");
		Thread.sleep(2000);
		System.out.println("Test 4 : " + " Thread:" +  Thread.currentThread().getId());
	}
	
	@Test (groups = "smoke-test")
	public void test5() throws InterruptedException {
		Driver.getDriver().get("https://saucedemo.com");
		Thread.sleep(2000);
		System.out.println("Test 5 : " + " Thread:" +  Thread.currentThread().getId());
	}
	
	@Test
	public void test6() throws InterruptedException {
		Driver.getDriver().get("https://etsy.com");
		Thread.sleep(2000);
		System.out.println("Test 6 : " + " Thread:" +  Thread.currentThread().getId());
	}
	
	@Test (groups = {"smoke-test", "signupPage"}, description = "verify signup page components")
	public void signUpPageTest() throws InterruptedException {
		System.out.println("Sign up test running on : " + " Thread:" +  Thread.currentThread().getId());
		CommonPage commonpage = new CommonPage();
		SignUpPage signuppage = new SignUpPage();
	// when
		Driver.getDriver().get(TestDataReader.getProperty("appurl"));
	// and click 
		Assert.assertTrue(commonpage.welcomeLink.isDisplayed());
		commonpage.welcomeLink.click();
    // and click 
		Assert.assertTrue(commonpage.signUpButton.isDisplayed());
		commonpage.signUpButton.click();
		
		// verify the sign up page web components
		Assert.assertTrue(signuppage.signupText.isDisplayed());
		
		// email field verification
		Assert.assertTrue(signuppage.emailFieldLabel.isDisplayed());
		Assert.assertEquals(signuppage.emailField.getAttribute("placeholder"), "Please Enter Your Email");
	
		// first name field verification
		Assert.assertTrue(signuppage.firstNameLabel.isDisplayed());
		Assert.assertEquals(signuppage.firstNameField.getAttribute("placeholder"), "Please Enter Your First Name");
		// last name field verification
		Assert.assertTrue(signuppage.lastNameLabel.isDisplayed());
		Assert.assertEquals(signuppage.lastNameField.getAttribute("placeholder"), "Please Enter Your Last Name");
		// password name field verification
		Assert.assertTrue(signuppage.passwordLabel.isDisplayed());
		Assert.assertEquals(signuppage.passwordField.getAttribute("placeholder"), "Please Enter Your Password");
		
		
		// google and facebook login verification
		Assert.assertTrue(signuppage.signWithGoogleLink.isDisplayed());
		Assert.assertTrue(signuppage.signWithFacebookLink.isDisplayed());
		
		// check box verification
		Assert.assertTrue(signuppage.subscribeCheckBox.isDisplayed());
		Assert.assertFalse(signuppage.subscribeCheckBox.isSelected());
		Assert.assertTrue(signuppage.subcribeToNewsletter.isDisplayed());
		
		// back and signup buttons 
		Assert.assertTrue(signuppage.signUpBtn.isDisplayed());
		Assert.assertTrue(signuppage.backToLoginLink.isDisplayed());
			
	}
	
	
	@AfterMethod (groups = "smoke-test")
	public void quitDriver() {
		//Driver.quitDriver();
		System.out.println("After method");
	}
}
