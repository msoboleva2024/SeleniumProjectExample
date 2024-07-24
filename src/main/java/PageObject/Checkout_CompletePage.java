package PageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponent.AbstractComponent;
import Resources.Product;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class Checkout_CompletePage extends AbstractComponent {

	public Checkout_CompletePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}
	
	
	
	@FindBy (css = "#checkout_complete_container h2")
	WebElement checkoutInfo;
	
	@FindBy (css = "#checkout_complete_container .complete-text")
	WebElement checkoutInfoText;
	
	@FindBy (id = "back-to-products")
	WebElement backHomeBtn;
	
	
	@Step("Get final info about placed order")
	public String getInfoAboutOrder() {
		
		waitVisibilityOfWebElement(checkoutInfo);
		String info = checkoutInfo.getText();
		String text = checkoutInfoText.getText();
		Allure.addAttachment("Get final info about placed order: ", info+" "+text);
		return info+" "+ text;
		
	}

	@Step("Return back to Products page")
	public ProductsPage clickBackHome() {
		
		waitVisibilityOfWebElement(backHomeBtn);
		backHomeBtn.click();
		return new ProductsPage(driver);
		
	}
	
	

	



}

