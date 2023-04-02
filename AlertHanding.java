package scripts;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class AlertHanding {
	WebDriver driver;
	WebDriverWait wait; // declare explicit wait object reference

	@Test
	public void testToHandleAlertPopup() throws InterruptedException {

		WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));

		// Selenium gives a unique identification to each window opened. This
		// identification called Window Handle.
		String parentWindowHandle = driver.getWindowHandle(); // It will give current window Handle
		username.sendKeys("stc123");
		/*
		 * WebElement password =
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))
		 * ); password.clear(); password.sendKeys("12345");
		 */
		username.submit(); // Enter

		Alert myAlert;

		Thread.sleep(3000); // Just test verify purpose we are using here sleep(). its recommended not to
							// use Thread.sleep method
		try {
			myAlert = driver.switchTo().alert(); // It will switch focus to alert window
			String expectedMessage = "Please enter Password";
			String actualMessage = myAlert.getText();
			assertEquals(actualMessage, expectedMessage, "Message does not matched."); // To verify the message of alert
																						// box
			myAlert.accept(); // To click on 'Ok' alert button
//			myAlert.dismiss();		// To click on 'Cancel' alert button 	
			Thread.sleep(3000);
		} catch (NoAlertPresentException e) {
			fail("Alert was expected but did not come.");
		}

		WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		password.clear();
		password.sendKeys("12345");

		driver.findElement(By.xpath("//button[text()='Contact us!']")).click();
		try {
			driver.switchTo().window("Contact");
		} catch (NoSuchWindowException e) {
			fail("Expected Contact us window is not present !!");
		}
		Thread.sleep(3000);
		driver.findElement(By.className("glyphicon-search")).click(); // XPath ---> a[@onclick='prompty()']

		// To Handle Contact Us Window page alert window

		try {
			myAlert = driver.switchTo().alert(); // It will switch focus to alert window
			Thread.sleep(3000);
			myAlert.sendKeys("London");
			myAlert.accept(); // To click on 'Ok' alert button
			Thread.sleep(5000);
		} catch (NoAlertPresentException e) {
			fail("Alert was expected but did not come.");
		}
		driver.close(); // It will close the Contact window page
		driver.switchTo().window(parentWindowHandle); // It will move the focus to main home page

		// Write to us! Window Page
		driver.findElement(By.xpath("//button[text()='Write to us!']")).click();

		Set<String> allOpenWindowsByThisDriver = driver.getWindowHandles();

		// For loop is using to iterate through open windows
		for (String oneWindow : allOpenWindowsByThisDriver) {
			if (!oneWindow.equals(parentWindowHandle)) {
				driver.switchTo().window(oneWindow);
				driver.findElement(By.name("mobile")).sendKeys("1234567898");
				driver.close();	// Close the current window page
				Thread.sleep(3000);
			}
		}
		driver.switchTo().window(parentWindowHandle);	// Navigate back to main home page
		Thread.sleep(4000);
	}

	@BeforeMethod
	public void beforeMethod() {
		// driver = new ChromeDriver();
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Initialized explicit wait
		driver.get("https://nichethyself.com/tourism/home.html");
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
