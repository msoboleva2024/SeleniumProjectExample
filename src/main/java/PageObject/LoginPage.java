package PageObject;


import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponent.AbstractComponent;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class LoginPage extends AbstractComponent {

	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css = ".login_logo")
	WebElement loginLogo;
	
	@FindBy(id = "user-name")
	WebElement usernameFld;

	@FindBy(id = "password")
	WebElement passwordFld;

	@FindBy(id = "login-button")
	WebElement loginBtn;

	@FindBy(id = "login_credentials")
	WebElement loginCredentialsPart;

	@FindBy(css = ".login_password")
	WebElement loginPasswordPart;
	
	@FindBy(css = ".error-message-container.error h3")
	WebElement errorMsg;
	
	@FindBy (css = "svg[data-icon='times-circle']")
	List<WebElement> redCircles;
	
	

	@Step("Set username to the Username field on LoginPage: {0}")
	public void setUsername(String username) {

		waitVisibilityOfWebElement(usernameFld);
		usernameFld.sendKeys(username);
	}

	@Step("Set password to the Password field on LoginPage: {0}")
	public void setPassword(String password) {

		waitVisibilityOfWebElement(passwordFld);
		passwordFld.sendKeys(password);
	}
	
	@Step("Click LoginButton to login to the application")
	public ProductsPage clickLoginBtn() {
		
		loginBtn.click();
		return new ProductsPage(driver);
	}
	
	@Step("Get users for test")
	public List<String> getTestUsers() {
			
		String[] usernames = loginCredentialsPart.getText().split(":")[1].trim().split("\\n");
		List<String> possibleUsernames = Arrays.asList(usernames);
		
	    Allure.attachment("Possible users for test: ", possibleUsernames.toString());
		return possibleUsernames;
	}
	
	@Step("Get password for tests")
	public String getTestPassword() {
		
		String password = loginPasswordPart.getText();
		password = password.split("Password for all users:")[1].trim();
		
	    Allure.addAttachment("Possible password for test:", password);
		return password;
	}
	
	@Step("Check if the error message appeared on the screen after user clicked Login button")
	public boolean isErrorMsgAppeared() {
		
	   try {
		   waitVisibilityOfWebElement(errorMsg);
	   }
		catch(Exception e) {
			
			Allure.addAttachment("Error message appeared:", "false");
			return false;
		}
		Allure.addAttachment("Error message appeared:", "true");
		return true;
	}
	
	@Step("Get text from error message, which appeared on the screen after unsuccessfull login")
	public String getTextErrorMsg() {
		
		String errorMsgText = errorMsg.getText();
		Allure.addAttachment("Error message text:", errorMsgText);
		
		return errorMsgText;
	}
	
	@Step("Check whether the red circle's icons appeared near username and password fields in case of error during login")
	public boolean isErrorCircleAppearedForFields() {
		
		 try {
			   waitVisibilityOfWebElements(redCircles);
			   
		   }
			catch(Exception e) {
			    
				Allure.addAttachment("Error circles appeared for username/pasword fields:", "false");
				return false;
			}
		 
		 if (redCircles.size()==2)
		 {
			Allure.addAttachment("Error circles appeared for username/pasword fields:", "true");
			 return true;
		 }
		
		 Allure.addAttachment("Error circles appeared for username/pasword fields:", "false");
		 return false;
		}
	
	@Step("Get the Page Name")
	public String getPageName() {
		
		String pageNameLogo = loginLogo.getText();
		 
		Allure.addAttachment("Page Name: ", pageNameLogo);
		return pageNameLogo;
	}
	
	@Step("Open application: {0}")
	public void goTo(String url) {

		driver.get(url);

}
	
}
