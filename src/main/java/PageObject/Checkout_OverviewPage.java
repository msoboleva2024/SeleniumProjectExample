package PageObject;

import AbstractComponent.AbstractComponent;
import Resources.Product;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Checkout_OverviewPage extends AbstractComponent {

	
	public Checkout_OverviewPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="cancel")
	WebElement cancelBtn;
	
	@FindBy(id = "continue-shopping")
	WebElement continueShoppingBtn;
	
	@FindBy(id = "checkout")
	WebElement checkoutBtn;
	
	@FindBy (id = "finish")
	WebElement finishBtn;
	
	@FindBy (css = ".summary_info_label")
	List<WebElement> infoLabelList;
	
	@FindBy (css = ".summary_subtotal_label")
	WebElement itemTotalFld;
	
	@FindBy (css = ".summary_tax_label")
	WebElement taxFld;
	
	@FindBy (css = ".summary_total_label")
	WebElement totalLabelFld;
	
	@Step("Get itemTotal to check price of order")
	public String getItemTotal() {
		
		String itemTotal="";
		
		waitVisibilityOfWebElement(itemTotalFld);
		itemTotal= itemTotalFld.getText().replace("$","");
		Allure.addAttachment("itemTotal is:", itemTotal);
		return itemTotal;
	}
	
	@Step("Get tax to check is it 8% from price of order")
	public String getTax() {
		
		String tax="";
		
		waitVisibilityOfWebElement(taxFld);
		tax= taxFld.getText().replace("$","");
		Allure.addAttachment("Tax is:", tax);
		
		return tax;
	}
	
	@Step("Get total Label to check that it was correct calculated")
	public String getTotalLabel() {
		
		String totalLabel="";
		
		waitVisibilityOfWebElement(totalLabelFld);
		totalLabel= totalLabelFld.getText().replace("$","");
		Allure.addAttachment("Total label is:", totalLabel);
		
		return totalLabel;
	}
	
	@Step("Check that info labels: Payment Informaiton, Shipping Information, Price appeared on the Overview page")
	public boolean isLabelsAppeared() {
		
		Boolean isAppeared = false;
		int counter = 0;
		
		try {
			waitVisibilityOfWebElements(infoLabelList);
			for (int i=0;i<infoLabelList.size();i++) {
				
				if (infoLabelList.get(i).getText().contains("Payment Information:")||infoLabelList.get(i).getText().contains("Shipping Information:")||infoLabelList.get(i).getText().contains("Price Total"))
				{
					counter =counter+1;
				}
				
			}
			if (counter==3)
			
				 isAppeared=true;
		}
		
		catch(Exception e) {

			
		}
		Allure.addAttachment("Labels: Payment Informaiton, Shipping Information, Price are on the screen:", isAppeared.toString());
		return isAppeared;
		
	}

	@Step("Click Cancel btn on the 'Checkout' Page")
	public ProductsPage clickCancel() {
		
		waitVisibilityOfWebElement(cancelBtn);
		cancelBtn.click();
		
		return new ProductsPage(driver);
	}
	
	
	
	

	@Step("Click 'Finish' btn to finalize the order")
	public Checkout_CompletePage clickFinishBtn() {
		
		waitVisibilityOfWebElement(finishBtn);
		finishBtn.click();
		
		return new Checkout_CompletePage(driver);
		
	}

	
	@Step("get subtitle of Checkout page")
	public String getPageSubtitle() {
		
		String subtitle  = "";
		subtitle = driver.findElement(By.cssSelector("span[class='title']")).getText();
		Allure.addAttachment("Checkout page subtitle", subtitle);
		return subtitle;
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
	
	
	@Step("Open Details for Product {0}")
	public YourCartPage clickOnProductForDetailedView(String product) {
		
	
		
		List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
	
			
			for(int i=0;i<cartItems.size();i++) {
			
					if (cartItems.get(i).findElement(By.className("inventory_item_name")).getText().equals(product))
						
						{
						cartItems.get(i).findElement(By.className("inventory_item_name")).click();
						return new YourCartPage(driver);
						}
					
			}
			return null;
}
//deleted all comments - morningBranch 08-29
}
