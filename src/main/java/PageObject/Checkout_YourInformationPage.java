package PageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponent.AbstractComponent;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class Checkout_YourInformationPage extends AbstractComponent {
	
	public Checkout_YourInformationPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}




	@FindBy(id = "first-name")
	WebElement firstNameFld;
	
	@FindBy(id = "last-name")
	WebElement lastNameFld;
	
	@FindBy(id = "postal-code")
	WebElement postalCodeFld;
	
	@FindBy(id="cancel")
	WebElement cancelBtn;
	
	@FindBy(id = "continue")
	WebElement continueBtn;
	
	
	@FindBy (css = ".error-message-container.error h3")
	WebElement errorMsg;
	
	@FindBy (xpath = "//*[local-name()='svg']/parent::div/input")
	List<WebElement> redCircle;
	
	
	
	@Step("Check whether the error message displayed")
	public boolean isErrorShown() {
		
		Boolean isErrorShown=false;
		
		try {
			waitVisibilityOfWebElement(errorMsg);
			isErrorShown = true;
		}catch(Exception e) {
			
		}
		Allure.addAttachment("Is error shown: ", isErrorShown.toString());
		return isErrorShown;
	}
	
	@Step("Get error text from checkout page")
	public String getErrorMessageText() {
		
		String errorMessage="";
		
		errorMessage = errorMsg.getText();
		Allure.addAttachment("The follwoing error occured: ", errorMessage);
		return errorMessage;
		
	}
	
	
	
	@Step("Click 'Continue' btn on the Checkout page")
	public Checkout_OverviewPage clickContinueBtn() {
		
		waitVisibilityOfWebElement(continueBtn);
		continueBtn.click();
		return new Checkout_OverviewPage(driver);
		
	}
	
	@Step("Set firstName = {0}, lastName = {0} and postalCode = {2}")
	public void setCheckoutInformation(String firstName, String lastName, String postalCode) {
		
		waitVisibilityOfWebElement(firstNameFld);
		firstNameFld.sendKeys(firstName);
		lastNameFld.sendKeys(lastName);
		postalCodeFld.sendKeys(postalCode);
		
		
	}
	
	@Step("get subtitle of Checkout page")
	public String getPageSubtitle() {
		
		String subtitle  = "";
		subtitle = driver.findElement(By.cssSelector("span[class='title']")).getText();
		Allure.addAttachment("Checkout page subtitle", subtitle);
		return subtitle;
	}
	
	
    @Step("Check whether the redCircle appeared near field with error")
    public boolean isRedCircleAppeared(String fieldName) {
    	
    	Boolean isAppeared = false;
    	
    	for (int i=0;i<redCircle.size();i++) {
    		
    		if(redCircle.get(i).getAttribute("name").contains(fieldName))
    			{
    			isAppeared = true;
    			
    			break;
    			}		
    	}
    	

		Allure.addAttachment("The red circle appeared near field: ", isAppeared.toString());
    	return isAppeared;
    	
    	
    	
    }
	@Step("Click Cancel btn on the 'Checkout' Page")
	public void clickCancel() {
		
		waitVisibilityOfWebElement(cancelBtn);
		cancelBtn.click();
	}
	
	

}
