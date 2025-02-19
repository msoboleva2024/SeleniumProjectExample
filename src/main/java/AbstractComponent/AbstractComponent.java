package AbstractComponent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import PageObject.LoginPage;
import PageObject.ProductsPage;
import PageObject.YourCartPage;
import Resources.Product;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class AbstractComponent {

	public WebDriver driver;

	public AbstractComponent(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	@FindBy(id = "react-burger-menu-btn")
	WebElement menuItemsIcon;

	@FindBy(id = "inventory_sidebar_link")
	WebElement allItemsBtn;

	@FindBy(id = "about_sidebar_link")
	WebElement aboutBtn;

	@FindBy(id = "logout_sidebar_link")
	WebElement logoutBtn;

	@FindBy(id = "reset_sidebar_link")
	WebElement resetBtn;

	@FindBy(css = ".bm-cross-button")
	WebElement closeMenuItemBtn;

	@FindBy(id = "shopping_cart_container")
	WebElement shoppingCartIcon;

	@FindBy(css = ".shopping_cart_badge")
	WebElement shoppingCartBage;

	@FindBy(id = "continue-shopping")
	WebElement contnueShoppingBtn;

	@FindBy(css = ".social a")
	List<WebElement> linkList;

	@Step("Click 'menu items' icon to open menu")
	public void clickMenuItemsIcon() {

		waitVisibilityOfWebElement(menuItemsIcon);
		menuItemsIcon.click();

	}

	@Step("Click 'All Items' Btn in side menu to open all items")
	public ProductsPage clickAllItemsBtn() {

		clickMenuItemsIcon();
		waitVisibilityOfWebElement(allItemsBtn);
		allItemsBtn.click();

		return new ProductsPage(driver);

	}

	@Step("Click 'About' button in side menu")
	public String clickAboutBtn() {

		clickMenuItemsIcon();
		waitVisibilityOfWebElement(aboutBtn);
		aboutBtn.click();
		return driver.getTitle();

	}

	@Step("Click 'Logout' button in side menu")
	public LoginPage clickLogoutBtn() {

		clickMenuItemsIcon();
		waitVisibilityOfWebElement(logoutBtn);
		logoutBtn.click();
		return new LoginPage(driver);
	}

	@Step("Click 'Reset' button in side menu")
	public void clickResetBtn() {

		clickMenuItemsIcon();
		waitVisibilityOfWebElement(resetBtn);
		resetBtn.click();
	}

	@Step("Click 'Close' button in side menu")
	public void clickCloseBtn() {

		clickMenuItemsIcon();
		waitVisibilityOfWebElement(closeMenuItemBtn);
		closeMenuItemBtn.click();
	}

	@Step("Click 'ShoppingCart' icon")
	public YourCartPage clickShoppingCartIcon() {

		waitVisibilityOfWebElement(shoppingCartIcon);
		shoppingCartIcon.click();
		waitVisibilityOfWebElement(contnueShoppingBtn);
		return new YourCartPage(driver);

	}

	@Step("Get shopping cart bage counter")
	public int getShoppingCartBageCounter() {

		String counter;
		try {
			counter = shoppingCartBage.getText();
		} catch (Exception e) {
			counter = "0";
		}
		Allure.addAttachment("Shopping cart bage counter : ", counter);
		return Integer.parseInt(counter);

	}

	public void waitVisibilityOfWebElement(WebElement element) {

		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitVisibilityOfWebElements(List<WebElement> elements) {

		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	@Step("Get Page Title")
	public String getPageTitle() {

		String pageTitle = driver.findElement(By.cssSelector(".title")).getText();
		Allure.addAttachment("Page title: ", pageTitle);
		return pageTitle;
	}

	@Step("Get all menuItems from the list")
	public ArrayList<String> getAllMenuItems() {

		ArrayList<String> actualMenuItems = new ArrayList<String>();
		List<WebElement> menuItems = driver.findElements(By.cssSelector(".bm-menu a"));
		waitVisibilityOfWebElements(menuItems);

		for (int i = 0; i < menuItems.size(); i++) {

			actualMenuItems.add(menuItems.get(i).getText());
		}
		Allure.addAttachment("Actual MenuItems are: ", menuItems.toString());
		return actualMenuItems;

	}

	@Step("Get random product for adding to the shopping Cart")
	public String getRandomElement(List<String> list) {

		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException("List of possible products is empty or null");
		}

		Random random = new Random();
		int index = random.nextInt(list.size());
		String product = list.get(index);
		Allure.addAttachment("The selected product is: ", product);
		System.out.println("product to be ordered: " + product);
		return product;
	}

	@Step("Check links are not broken")
	public boolean areLinksBroken() {

		Boolean isBroken = true;
		Actions action = new Actions(driver);

		for (int i = 0; i < linkList.size(); i++) {

			action.moveToElement(linkList.get(i)).keyDown(Keys.COMMAND).click().build().perform();

		}

		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it = handles.iterator();
		int counter = 0;
		ArrayList<String> listHandles = new ArrayList<String>();

		while (it.hasNext()) {

			listHandles.add(it.next());

		}
		for (int i = 0; i < listHandles.size(); i++) {

			driver.switchTo().window(listHandles.get(i));

			try {
				waitVisibilityOfWebElement(driver.findElement(By.partialLinkText("not found")));
				counter = counter + 1;
			} catch (Exception e) {
				isBroken = false;
			}
		}
		driver.switchTo().window(listHandles.get(0));

		Allure.addAttachment("Are links Broken: ", isBroken.toString());

		return isBroken;
	}

	@Step("Calculate expected tax for list of products {0}")
	public BigDecimal calculateTax(List<Product> products) {

		double totalPrice = calculateItemTotal(products);

		double tax = totalPrice * 8 / 100;

		BigDecimal taxValue = new BigDecimal(Double.toString(tax));
		BigDecimal roundedValueTax = taxValue.setScale(2, RoundingMode.HALF_UP);

		Allure.addAttachment("Tax: ", roundedValueTax.toString());
		return roundedValueTax;

	}

	@Step("Calculate expected itemTotal for list of products {0}")
	public double calculateItemTotal(List<Product> products) {

		double totalPrice = 0;

		for (int i = 0; i < products.size(); i++) {

			totalPrice = totalPrice + Double.parseDouble(products.get(i).getPrice().replace("$", ""));

		}

		Allure.addAttachment("ItemTotal: ", Double.toString(totalPrice));
		return totalPrice;
	}
	
	@Step("Calculate expected totalPrice with tax= {0} and price = {1}")
	public double calculateTotalPrice(BigDecimal roundedValueTax, double totalPrice) {
		
		
        double tax = roundedValueTax.doubleValue();
		double price = totalPrice+tax;
		
		Allure.addAttachment("Price: ", Double.toString(price));
		return price;
	}

}
