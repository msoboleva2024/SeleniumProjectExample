package PageObject;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponent.AbstractComponent;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class Checkout_YourCartPage extends AbstractComponent {

	public Checkout_YourCartPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}
	
	
	
	@FindBy(id = "continue-shopping")
	WebElement continueShoppingBtn;
	
	@FindBy(id = "checkout")
	WebElement checkoutBtn;
	
	
	
	@Step("Click 'Continue Shopping' to return back to ProductsPage")
	public ProductsPage clickContinueShopping() {
		
		waitVisibilityOfWebElement(continueShoppingBtn);
		continueShoppingBtn.click();
		return new ProductsPage(driver);
		
	}
	
	@Step("Click 'Checkout' btn to finalize the order")
	public Checkout_YourInformationPage clickCheckoutBtn() {
		
		waitVisibilityOfWebElement(checkoutBtn);
		checkoutBtn.click();
		return new Checkout_YourInformationPage(driver);
		
	}

	
	@Step("get subtitle of Checkout page")
	public String getPageSubtitle() {
		
		String subtitle  = "";
		subtitle = driver.findElement(By.cssSelector("span[class='title']")).getText();
		Allure.addAttachment("Checkout page subtitle", subtitle);
		return subtitle;
	}
	
//branch3 - added new comment to YourCartPage - 09:04
}
