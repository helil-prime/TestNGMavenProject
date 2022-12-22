package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import io.github.bonigarcia.wdm.managers.EdgeDriverManager;
import io.github.bonigarcia.wdm.managers.FirefoxDriverManager;

public class Driver {
	
public static WebDriver driver;
	
	public static WebDriver getDriver() {
		String browser = System.getProperty("browser");
		if (browser == null) {
			browser = TestDataReader.getProperty("browser");
		}
		if (driver == null || ((RemoteWebDriver) driver).getSessionId() == null) {
			switch(browser) {
			case "chrome-headless" :
				ChromeDriverManager.chromedriver().setup();
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--headless");
				driver = new ChromeDriver(chromeOptions);
				break;
			case "chrome" :
				ChromeDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				break;
			case "firefox" :
				FirefoxDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				break;
			case "firefox-headless" :
				FirefoxDriverManager.firefoxdriver().setup();
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.setHeadless(true);
				driver = new FirefoxDriver(firefoxOptions);
				break;
			case "safari" :
				driver = new SafariDriver();
				break;
			case "edge" :
				EdgeDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				break;
			default:
				ChromeDriverManager.chromedriver().setup();
				ChromeOptions Options = new ChromeOptions();
				Options.addArguments("--headless");
				driver = new ChromeDriver(Options);
				break;
			}
		}
		return driver;
	}
	
	public static void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
    }
}
