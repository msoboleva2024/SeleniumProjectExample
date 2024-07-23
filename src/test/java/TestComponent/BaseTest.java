package TestComponent;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import PageObject.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class BaseTest {

	public WebDriver driver;
	public String url;
	public LoginPage loginPage;

	@Step("Initialize WebDriver")
	public WebDriver initializeDriver() throws IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//test//java//Resources//Global.properties");
		prop.load(fis);
		url = prop.getProperty("url");
		String browserName = prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}

		else if (browserName.equalsIgnoreCase("edge")) {

			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();

		Allure.addAttachment("Browser: ", browserName);
		return driver;

	}

	@BeforeMethod
	public LoginPage launchApplication() throws IOException {

		driver = initializeDriver();

		loginPage = new LoginPage(driver);
		loginPage.goTo(url);
		return loginPage;
	}

	@AfterMethod
	public void closeApplication() {

		try{
			Thread.sleep(600);
			driver.close();
		}
		catch(Exception e) {
		driver.quit();
		}
	}
	
}
