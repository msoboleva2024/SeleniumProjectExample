package PageObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import AbstractComponent.AbstractComponent;
import Resources.Product;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class ProductsPage extends AbstractComponent {

	public ProductsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".product_sort_container")
	WebElement filter;

	@FindBy(css = ".inventory_item")
	List<WebElement> products;

	@Step("Get filter values from the Products page")
	public ArrayList<String> getFilter() {

		ArrayList<String> actualFilterValues = new ArrayList<String>();
		waitVisibilityOfWebElement(filter);
		filter.click();

		List<WebElement> list = filter.findElements(By.cssSelector("option"));
		for (int i = 0; i < list.size(); i++) {

			actualFilterValues.add(list.get(i).getText());
			System.out.println(list.get(i).getText());
		}

		Allure.addAttachment("Possible filter values are: ", actualFilterValues.toString());
		filter.click();
		return actualFilterValues;
	}

	@Step("Set filter on the Products Page: {0}")
	public void setFilter(String visibleFilterText) {

		waitVisibilityOfWebElement(filter);
		Select select = new Select(filter);
		select.selectByVisibleText(visibleFilterText);
	}

	@Step("Get list of products from Products page")
	public List<String> getListOfProducts() {

		List<String> listOfProducts = new ArrayList<String>();
		waitVisibilityOfWebElements(products);

		for (int i = 0; i < products.size(); i++) {

			listOfProducts.add(products.get(i).findElement(By.cssSelector(".inventory_item_name ")).getText());
		}

		Allure.attachment("List of products from Products page:", listOfProducts.toString());
		return listOfProducts;
	}

	@Step("Get list of prices for products from Products page")
	public List<String> getListOfProductsPrices() {

		waitVisibilityOfWebElements(products);
		List<String> listOfProducts = new ArrayList<String>();

		for (int i = 0; i < products.size(); i++) {

			listOfProducts.add(
					products.get(i).findElement(By.cssSelector(".inventory_item_price ")).getText().split("\\$")[1]);
		}

		Allure.attachment("List of products prices from Products page:", listOfProducts.toString());
		return listOfProducts;
	}

	@Step("Check if list is sorted alphabetically ascending")
	public boolean isListSortedAlphabetical(List<String> list) {

		List<String> sortedList = new ArrayList<String>(list);
		Collections.sort(sortedList);
		Boolean isSorted = list.equals(sortedList);
		Allure.addAttachment("Is List of products Sorted  alphabetical ascending: ", isSorted.toString());
		return isSorted;

	}

	@Step("Check if list is sorted alphabetically descending")
	public boolean isListSortedAlphabeticalDescending(List<String> list) {

		List<String> sortedList = new ArrayList<>(list);
		sortedList.sort(Collections.reverseOrder());
		Boolean isSorted = list.equals(sortedList);

		Allure.addAttachment("Is List of products Sorted  alphabetical descending: ", isSorted.toString());
		return isSorted;
	}

	@Step("Check if list is sorted according to the desc of prices")
	public boolean isListOfPricesSortedDescending(List<String> list) {

		Boolean isSorted = false;
		for (int i = 0; i < list.size() - 1; i++) {
			double current = Double.parseDouble(list.get(i));
			double next = Double.parseDouble(list.get(i + 1));

			if (current < next) {
				isSorted = false;
				Allure.addAttachment("Is List Of Prices Sorted Descending: ", isSorted.toString());
				return isSorted;
			}
		}
		isSorted = true;
		Allure.addAttachment("Is List Of Prices Sorted Descending: ", isSorted.toString());
		return isSorted;
	}

	@Step("Check if list is sorted according to the asc of prices")
	public boolean isListPricesSortedAscending(List<String> list) {

		Boolean isSorted = false;
		for (int i = 0; i < list.size() - 1; i++) {
			double current = Double.parseDouble(list.get(i));
			double next = Double.parseDouble(list.get(i + 1));

			if (current > next) {
				isSorted = false;
				Allure.addAttachment("Is List Of Prices Sorted Ascending: ", isSorted.toString());
			}

		}
		isSorted = true;
		Allure.addAttachment("Is List Of Prices Sorted Ascending: ", isSorted.toString());
		return isSorted;

	}

	@Step("Add Product {0} to Shopping Cart")
	public Product addToShoppingCart(String product) {

		Product addedProduct = null;
		
		Boolean isAdded = false;
		try {
			driver.findElement(By.xpath("//a/div[contains(text(),'" + product
					+ "')]/parent::a/parent::div/following-sibling::div/button[@class='btn btn_primary btn_small btn_inventory ']"))
					.click();
			isAdded = true;
			addedProduct = new Product();
			addedProduct.setDescription(driver.findElement(By.xpath("//a/div[contains(text(),'"+product+"')]/parent::a/following-sibling::div")).getText());
			addedProduct.setInShoppingCart(isAdded);
			addedProduct.setName(product);
			addedProduct.setPrice(driver.findElement(By.xpath("//a/div[contains(text(),'"+product+"')]/parent::a/following-sibling::div/parent::div/following-sibling::div/div[@class='inventory_item_price']")).getText());
			Allure.addAttachment("Is product added to the shopping cart:  ", isAdded.toString());
			return addedProduct;
		}

		catch (NoSuchElementException e) {

			if (driver.findElement(By.xpath("//a/div[contains(text(),'" + product
					+ "')]/parent::a/parent::div/following-sibling::div/button")).getText().equals("Remove"))
			{
				System.out.println(e.getStackTrace());

			}

		}
		Allure.addAttachment("Is product added to the shopping cart: ", isAdded.toString());
		return addedProduct;

	}

	@Step("Delete Product {0} from Shopping Cart")
	public boolean deleteFromShoppingCart(String product) {

		Boolean isDeleted = false;
		try {
			driver.findElement(By.xpath("//a/div[contains(text(),'" + product
					+ "')]/parent::a/parent::div/following-sibling::div/button[@class='btn btn_secondary btn_small btn_inventory ']"))
					.click();
			isDeleted = true;
			Allure.addAttachment("Is product deleted from the shopping cart: ", isDeleted.toString());
			return isDeleted;
		}

		catch (Exception e) {
			System.out.println("EXCEPTION!");
			System.out.println("Product " + product + "could not be added, because it is already in the shoppingCart");

			Allure.addAttachment("Is product deleted from the shopping cart: ", isDeleted.toString());
			return isDeleted;
		}
	}
	
	@Step("Is remove button presented for product {0}")
	public boolean isRemoveBtnPresentedForProduct(String product) {
		
		Boolean isPresented = false;
		
		String button = driver.findElement(By.xpath("//a/div[contains(text(),'" + product
				+ "')]/parent::a/parent::div/following-sibling::div/button")).getText();
		
		if (button.equals("Remove"))
			isPresented = true;
		
		Allure.addAttachment("Is 'REmove' button presented for the product: ", isPresented.toString());
		return isPresented;
	}

}
