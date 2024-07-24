package PageObject;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import AbstractComponent.AbstractComponent;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class YourCartPage extends AbstractComponent {

	public YourCartPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css = ".cart_item")
	List<WebElement> productList;

	@FindBy(id = "continue-shopping")
	WebElement continueShoppingBtn;

	@FindBy(id = "checkout")
	WebElement checkoutBtn;

	@FindBy(id = "remove")
	WebElement removeBtn;

	@FindBy(id = "add-to-cart")
	WebElement addToCartBtn;

	@FindBy(css = "[class*='inventory_details_name']")
	WebElement productNameDetails;

	@FindBy(css = "[class*='inventory_details_price']")
	WebElement productPriceDetails;

	@FindBy(id = "back-to-products")
	WebElement backToProductsBtn;

	@FindBy(css = ".inventory_details_desc.large_size")
	WebElement descriptionOfProduct;

	@Step("Delete product from Detailed view page")
	public boolean deleteProductFromDetailedViewPage() {
		
		Boolean isDeleted = false;
		waitVisibilityOfWebElement(removeBtn);
		removeBtn.click();
		
		try{
			waitTextToBe(By.cssSelector(".inventory_details_desc_container button"),"Add to cart");
			isDeleted = true;
		} catch(Exception e) {
			
		}
		Allure.addAttachment("Product deleted from Detailed info page: ", isDeleted.toString());
		return isDeleted;
	}
	
	@Step("Add product from Detailed view page")
	public boolean addProductFromDetailedViewPage() {
		
		Boolean isAdded = false;
		waitVisibilityOfWebElement(addToCartBtn);
		addToCartBtn.click();
		
		try{
			waitTextToBe(By.cssSelector(".inventory_details_desc_container button"),"Remove");
			isAdded = true;
		} catch(Exception e) {
			
		}
		Allure.addAttachment("Product added from Detailed info page: ", isAdded.toString());
		return isAdded;
	}
	
	
	public void waitTextToBe(By locator, String textToBe) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.textToBe(locator, textToBe));
	}
	
	@Step("Get description of product from detail's page")
	public String getDescriptionOfProductDetailsPage() {

		waitVisibilityOfWebElement(descriptionOfProduct);
		String description = descriptionOfProduct.getText();
		Allure.addAttachment("Product description in Details info: ", description);
		return description;
	}

	@Step("Click ' back to products' link on the ShoppingCart page in area detailed info about item in order")
	public ProductsPage clickBackToProducts() {

		waitVisibilityOfWebElement(backToProductsBtn);
		backToProductsBtn.click();
		return new ProductsPage(driver);
	}

	@Step("Get product name from Detailed info about product in ShoppingCart")
	public String getProductNameDetailsPage() {

		String productName;
		productName = productNameDetails.getText();
		Allure.addAttachment("Product name in Details info: ", productName);
		return productName;
	}

	@Step("Get product name from Detailed info about product in ShoppingCart")
	public String getProductPriceDetailsPage() {

		String productPrice;
		productPrice = productPriceDetails.getText();
		Allure.addAttachment("Product price in Details info: ", productPrice);
		return productPrice;
	}

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
	
	

	@Step("Check whether the product: {0} in a shopping cart")
	public boolean isProductInShoppingCart(String product) {

		Boolean isProductInShoppingCart = false;

		// waitVisibilityOfWebElements(productList);

		if (isShoppingCartEmpty()) {
			isProductInShoppingCart = false;
			Allure.addAttachment("Is product in a shopping Cart? ", isProductInShoppingCart.toString());
			return isProductInShoppingCart;
		} else {

			for (int i = 0; i < productList.size(); i++) {

				if (productList.get(i).findElement(By.cssSelector(".cart_item_label [class='inventory_item_name']"))
						.getText().equals(product)) {
					isProductInShoppingCart = true;
					Allure.addAttachment("Is product in a shopping Cart? ", isProductInShoppingCart.toString());
					return isProductInShoppingCart;

				}
			}

			Allure.addAttachment("Is product in a shopping Cart? ", isProductInShoppingCart.toString());
			return isProductInShoppingCart;
		}
	}

	
	@Step("Get description of product from shopping Cart")
	public String getDescriptionYourCart(String product) {
		
		String description="";
		
		for (int i = 0; i < productList.size(); i++) {

			if (productList.get(i).findElement(By.cssSelector(".cart_item_label [class='inventory_item_name']"))
					.getText().equals(product)) {
				
				description = productList.get(i).findElement(By.cssSelector(".inventory_item_desc"))
						.getText();
				Allure.addAttachment("Description:  ", description);
				return description;
			}
		}
		Allure.addAttachment("Description:  ", description);
		return description;
	}
	

	@Step("Get price of product from shopping Cart")
	public String getPriceYourCart(String product) {
		
		String price="";
		
		for (int i = 0; i < productList.size(); i++) {

			if (productList.get(i).findElement(By.cssSelector(".cart_item_label [class='inventory_item_name']"))
					.getText().equals(product)) {
				
				price = productList.get(i).findElement(By.cssSelector(".inventory_item_price"))
						.getText();
				Allure.addAttachment("Price:  ", price);
				return price;
			}
		}
		Allure.addAttachment("Price:  ", price);
		return price;
	}
	
	
	
	@Step("Get name of product from shopping Cart")
	public String getNameYourCart(String product) {
		
		String name="";
		
		for (int i = 0; i < productList.size(); i++) {

			if (productList.get(i).findElement(By.cssSelector(".cart_item_label [class='inventory_item_name']"))
					.getText().equals(product)) {
				
				name = productList.get(i).findElement(By.cssSelector(".cart_item_label [class='inventory_item_name']"))
						.getText();
				Allure.addAttachment("Name:  ", name);
				return name;
			}
		}
		
		Allure.addAttachment("Name:  ", name);
		return name;
	}
	
	@Step("Open product: {0} for a detail's view")
	public void openProductForDetailsView(String product) {

		for (int i = 0; i < productList.size(); i++) {

			if (productList.get(i).findElement(By.cssSelector(".cart_item_label [class='inventory_item_name']"))
					.getText().equals(product)) {

				productList.get(i).findElement(By.cssSelector(".cart_item_label [class='inventory_item_name']"))
						.click();
				break;
			}
		}

	}
	
	
	
	private boolean isShoppingCartEmpty() {

		Boolean isEmpty = false;

		try {
			waitVisibilityOfWebElements(productList);
		} catch (Exception e) {
			isEmpty = true;
		}

		Allure.addAttachment("Is the Shopping Cart empty? ", isEmpty.toString());
		return isEmpty;
	}
	
	
	@Step("Delete product from the list from page ShoppingCart")
	public void deleteProductFromTheShoppingCart(String product) {
		
		for (int i = 0; i < productList.size(); i++) {

			if (productList.get(i).findElement(By.cssSelector(".cart_item_label [class='inventory_item_name']"))
					.getText().equals(product)) {
				
				productList.get(i).findElement(By.cssSelector(".cart_item_label [class='btn btn_secondary btn_small cart_button']")).click();
			break;
			}
		}

	}
	

}
