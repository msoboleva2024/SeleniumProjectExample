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
	
	@FindBy (css = "#checkout_complete_container h2")
	WebElement checkoutInfo;
	
	@FindBy (css = "#checkout_complete_container .complete-text")
	WebElement checkoutInfoText;
	
	@FindBy (id = "back-to-products")
	WebElement backHomeBtn;
	
	@FindBy (css = ".error-message-container.error h3")
	WebElement errorMsg;
	
	@FindBy (xpath = "//*[local-name()='svg']/parent::div/input")
	List<WebElement> redCircle;
	
	@Step("Get final info about placed order")
	public String getInfoAboutOrder() {
		
		waitVisibilityOfWebElement(checkoutInfo);
		String info = checkoutInfo.getText();
		String text = checkoutInfoText.getText();
		Allure.addAttachment("Get final info about placed order: ", info+" "+text);
		return info+" "+ text;
		
	}
	
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
	
	@Step("Check orders details {0}")
	public Boolean checkProductsInOrder(List<Product> products) {
		
		Boolean isOk = false;
		int counter = 0;
		
		List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
		
		if (cartItems.size()==products.size()) {
			
			for(int i=0;i<cartItems.size();i++) {
				
				for (int j=0;j<products.size();j++) {
			
					if (cartItems.get(i).findElement(By.className("inventory_item_name")).getText().equals(products.get(j).getName())
							&&cartItems.get(i).findElement(By.className("inventory_item_desc")).getText().equals(products.get(j).getDescription())
							&&cartItems.get(i).findElement(By.className("item_pricebar")).getText().equals(products.get(j).getPrice()))
						      
						        counter = counter+1;
						
				}
			}	
			
		}
		
		else {
			
			Allure.addAttachment("The number of elements in order is another from expected value: ", String.valueOf(cartItems.size()));
			return isOk;
			
		}
		
		if (counter==products.size())
			
			isOk=true;
		
		Allure.addAttachment("The products from the list are presented in the Order with valid names, prices and descriptions", isOk.toString());
		return isOk;
	}
	
	


}
