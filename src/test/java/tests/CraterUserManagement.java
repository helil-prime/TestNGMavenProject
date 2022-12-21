package tests;

import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.webdriver.WebDriverBrowser;
import pages.CraterDashboardPage;
import pages.CraterLoginPage;
import utils.Driver;
import utils.TestDataReader;

import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class CraterUserManagement {
	
	
  @Test
  public void validLogin() {
	  /*
	   * Scenario: Successful log in
         Given user is on the login page
         When user enters valid username and password
         And click login button
         Then user should be on the dashboard page
	   */
	  
	  Driver.getDriver().get(TestDataReader.getProperty("craterUrl"));
	  CraterLoginPage loginpage = new CraterLoginPage();
	 
	  loginpage.useremail.sendKeys(TestDataReader.getProperty("craterValidUserEmail"));
	  loginpage.password.sendKeys(TestDataReader.getProperty("craterValidPassword"));
	  loginpage.loginButton.click();
	  
	  // verify the amount due element display
	  CraterDashboardPage dashboardPage = new CraterDashboardPage();
	  Assert.assertTrue(dashboardPage.amountDueText.isDisplayed()); 
	  
	  // verify the url contains dashboard
	  String dashboardUrl = Driver.getDriver().getCurrentUrl();
	  Assert.assertTrue(dashboardUrl.contains("dashboard"));
  }
  
  @BeforeMethod
  public void setup() {
	  Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  @AfterMethod
  public void tearDown() {
	  Driver.quitDriver();
  }

}
