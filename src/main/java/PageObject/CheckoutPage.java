package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponent.AbstractComponent;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class CheckoutPage extends AbstractComponent {

	public CheckoutPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}
	
	
	
	@FindBy(id = "continue-shopping")
	WebElement continueShoppingBtn;
	
	@FindBy(id = "checkout")
	WebElement checkoutBtn;
	
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
	
	@FindBy (id = "finish")
	WebElement finishBtn;
	
	@FindBy (id = "checkout_complete_container")
	WebElement checkoutInfo;
	
	@FindBy (id = "back-to-products")
	WebElement backHomeBtn;
	
	
	@Step("Get final info about placed order")
	public String getInfoAboutOrder() {
		
		waitVisibilityOfWebElement(checkoutInfo);
		String info = checkoutInfo.getText();
		Allure.addAttachment("Get final info about placed order: ", info);
		return info;
		
	}
	
	@Step("Return back to Products page")
	public ProductsPage clickBackHome() {
		
		waitVisibilityOfWebElement(backHomeBtn);
		backHomeBtn.click();
		return new ProductsPage(driver);
		
	}
	
	
	
	@Step("Click 'Continue Shopping' to return back to ProductsPage")
	public ProductsPage clickContinueShopping() {
		
		waitVisibilityOfWebElement(continueShoppingBtn);
		continueShoppingBtn.click();
		return new ProductsPage(driver);
		
	}
	
	@Step("Click 'Checkout' btn to finalize the order")
	public void clickCheckoutBtn() {
		
		waitVisibilityOfWebElement(checkoutBtn);
		checkoutBtn.click();
		
	}
	

	@Step("Click 'Finish' btn to finalize the order")
	public void clickFinishBtn() {
		
		waitVisibilityOfWebElement(finishBtn);
		finishBtn.click();
		
	}
	@Step("Click 'Continue' btn on the Checkout page")
	public void clickContinueBtn() {
		
		waitVisibilityOfWebElement(continueBtn);
		continueBtn.click();
		
	}
	
	@Step("Set firstName = {0}, lastName = {0} and postalCode = {2}")
	public void setCheckoutInformation(String firstName, String lastName, String postalCode) {
		
		waitVisibilityOfWebElement(firstNameFld);
		firstNameFld.sendKeys(firstName);
		lastNameFld.sendKeys(lastName);
		postalCodeFld.sendKeys(postalCode);
		
		
	}
	
	@Step("Click Cancel btn on the 'Checkout' Page")
	public void clickCancel() {
		
		waitVisibilityOfWebElement(cancelBtn);
		cancelBtn.click();
	}
	
	
	


}
