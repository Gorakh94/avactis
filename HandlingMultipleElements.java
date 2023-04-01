package scripts;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class HandlingMultipleElements {
	WebDriver driver;
//	WebDriverWait wait;
	
	@Test
	public void testAllGoogleLinks() {
		// 1. Get all the links in a list
//		WebElement myElement = driver.findElement(By.tagName("a"));
		List<WebElement> allGoogleLinks = driver.findElements(By.tagName("a"));
	//	List<WebElement> allGoogleLinks =wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("a")));   
				
//		driver.findElements(By.xpath("//a"));
//		driver.findElements(By.cssSelector("a"));
//		driver.findElements(By.xpath("//a[@href]"));
		// 2. In a for loop print one by one,
		// link's visible text and href attribute value

		// Enhanced for loop, for each loop
		for (WebElement oneLink : allGoogleLinks) {
			System.out.println(oneLink.getText() + " - " + oneLink.getAttribute("href"));
		}

	}

	@BeforeMethod
	public void beforeMethod() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
//		wait=new WebDriverWait(driver, Duration.ofMinutes(1));
		// WebDriver driver = new ChromeDriver();
		driver.get("http://www.google.com");
		
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}
}
