package Tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import PageObject.ProductsPage;
import PageObject.YourCartPage;
import Resources.Product;
import TestComponent.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

public class ProductsPageTests extends BaseTest {

	ProductsPage productsPage;
	SoftAssert softAssert = new SoftAssert();

	@BeforeMethod()
	public void setPreconditions() {

		// preconditions: get test-data
		List<String> users = loginPage.getTestUsers();
		String password = loginPage.getTestPassword();

		// login to application
		loginPage.setUsername(users.get(0));
		System.out.println("The following User was used for the login: " + users.get(0));
		loginPage.setPassword(password);
		System.out.println("The following Password was used for the login: " + password);
		productsPage = loginPage.clickLoginBtn();

		// check expected results
		Assert.assertEquals(productsPage.getPageTitle(), "Products");
	}

	@Epic("SauceDemo Application")
	@Feature("Menu Items for all pages")
	@Test(priority = 1, description = "Check Filter items on the Product Page")
	@Description("Test-case will check the items presented in the filter on ProductPage")
	public void checkFilterItems() {
		
		ArrayList<String> actualFilterItems = productsPage.getFilter();

		
		softAssert.assertEquals(actualFilterItems.contains("Name (A to Z)"), true);
		softAssert.assertEquals(actualFilterItems.contains("Name (Z to A)"), true);
		softAssert.assertEquals(actualFilterItems.contains("Price (low to high)"), true);
		softAssert.assertEquals(actualFilterItems.contains("Price (high to low)"), true);
		softAssert.assertEquals(actualFilterItems.size(), 4);

		softAssert.assertAll();
		driver.close();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Menu Items for all pages")
	@Test(priority = 2, description = "Check Filter by 'Name (A to Z)' ")
	@Description("Test-case will check the items presented on the ProductPage are filtered in the order 'Name (A to Z)'")
	public void checkFilterAlphabetAsc() {
	
		productsPage.setFilter("Name (A to Z)");
		List<String> listOfProducts = productsPage.getListOfProducts();
		Assert.assertTrue(productsPage.isListSortedAlphabetical(listOfProducts));

	}
	
	@Epic("SauceDemo Application")
	@Feature("Menu Items for all pages")
	@Test(priority = 2, description = "Check Filter by 'Name (Z to A)' ")
	@Description("Test-case will check the items presented on the ProductPage are filtered in the order 'Name (Z to A)'")
	public void checkFilterAlphabetDesc() {
	
		productsPage.setFilter("Name (Z to A)");
		List<String> listOfProducts = productsPage.getListOfProducts();
		Assert.assertTrue(productsPage.isListSortedAlphabeticalDescending(listOfProducts));

	}
	
	@Epic("SauceDemo Application")
	@Feature("Menu Items for all pages")
	@Test(priority = 2, description = "Check Filter by 'Price (high to low)' ")
	@Description("Test-case will check the items presented on the ProductPage are filtered in the order 'Price (high to low)'")
	public void checkPriceFilterDesc() {
	
		productsPage.setFilter("Price (high to low)");
		List<String> listOfProducts = productsPage.getListOfProductsPrices();
		Assert.assertTrue(productsPage.isListOfPricesSortedDescending(listOfProducts));

	}
	
	@Epic("SauceDemo Application")
	@Feature("Menu Items for all pages")
	@Test(priority = 2, description = "Check Filter by 'Price (low to high)' ")
	@Description("Test-case will check the items presented on the ProductPage are filtered in the order 'Price (low to high)'")
	public void checkPriceFilterAsc() {
	
		productsPage.setFilter("Price (low to high)");
		List<String> listOfProducts = productsPage.getListOfProductsPrices();
		Assert.assertTrue(productsPage.isListPricesSortedAscending(listOfProducts));

	}
	
	@Epic("SauceDemo Application")
	@Feature("Products Page")
	@Test(priority = 0, description = "Check that it is possible to add product to the Shopping Cart ")
	@Description("Test-case will check that it is possible to add product to the shopping cart")

	public void addOneRandomProductToShoppingCart() {
		
		int initialCounter = productsPage.getShoppingCartBageCounter();
		List<String> listOfProducts = productsPage.getListOfProducts();
		String productToBeOrdered = productsPage.getRandomElement(listOfProducts);
		productsPage.addToShoppingCart(productToBeOrdered);
		int actualCounter = productsPage.getShoppingCartBageCounter();
		
	
		softAssert.assertEquals(actualCounter,initialCounter+1);
		YourCartPage yourCartPage = productsPage.clickShoppingCartIcon();
		softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrdered));
		softAssert.assertAll();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Products Page")
	@Test(priority = 0, description = "Check that it is possible to add several products to the Shopping Cart ")
	@Description("Test-case will check that it is possible to add several products to the shopping cart")

	public void addSeveralRandomProductsToShoppingCart() {
		
		int initialCounter = productsPage.getShoppingCartBageCounter();
		List<String> listOfProducts = productsPage.getListOfProducts();
		String productToBeOrderedFirst = productsPage.getRandomElement(listOfProducts);
		String productToBeOrderedSecond = productsPage.getRandomElement(listOfProducts);
		while (productToBeOrderedFirst.equals(productToBeOrderedSecond))
		{
			productToBeOrderedSecond = productsPage.getRandomElement(listOfProducts);
		}
		productsPage.addToShoppingCart(productToBeOrderedFirst);
		productsPage.addToShoppingCart(productToBeOrderedSecond);
		int actualCounter = productsPage.getShoppingCartBageCounter();
		
		
		softAssert.assertEquals(actualCounter,initialCounter+2);
		YourCartPage yourCartPage = productsPage.clickShoppingCartIcon();
		softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrderedFirst));
		softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrderedSecond));
		softAssert.assertAll();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Products Page")
	@Test(priority = 0, description = "Check that it is NOT possible to add the same product twice to the Shopping Cart ")
	@Description("Test-case will check that it is NOT possible to add the same product twice to the shopping cart")

	public void addTwiceTheSameProductToShoppingCart() {
		
		int initialCounter = productsPage.getShoppingCartBageCounter();
		List<String> listOfProducts = productsPage.getListOfProducts();
		String productToBeOrderedFirst = productsPage.getRandomElement(listOfProducts);
		String productToBeOrderedSecond = productToBeOrderedFirst;
		
		Product productOne = productsPage.addToShoppingCart(productToBeOrderedFirst);
		Product productTwo  = productsPage.addToShoppingCart(productToBeOrderedSecond);
		
		
		int actualCounter = productsPage.getShoppingCartBageCounter();
		
	
		softAssert.assertNull(productTwo);
		softAssert.assertEquals(actualCounter,initialCounter+1);
		softAssert.assertTrue(productOne.isInShoppingCart());
		
		YourCartPage yourCartPage = productsPage.clickShoppingCartIcon();
		softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrderedFirst));
	
		softAssert.assertAll();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Products Page")
	@Test(priority = 0, description = "Check that it is  possible to delete the  product from the Shopping Cart ")
	@Description("Test-case will check that it is  possible to delete the product from the shopping cart, if user clicks to the 'Remove' btn on Products page")

	public void deleteProductFromShoppingCart() {
		
		
		int initialCounter = productsPage.getShoppingCartBageCounter();
		List<String> listOfProducts = productsPage.getListOfProducts();
		String productToBeOrdered = productsPage.getRandomElement(listOfProducts);
		productsPage.addToShoppingCart(productToBeOrdered);
		int actualCounter = productsPage.getShoppingCartBageCounter();
		
	
		softAssert.assertEquals(actualCounter,initialCounter+1);
		YourCartPage yourCartPage = productsPage.clickShoppingCartIcon();
		softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrdered));
		productsPage = yourCartPage.clickAllItemsBtn();
		boolean isDeleted = productsPage.deleteFromShoppingCart(productToBeOrdered);
		softAssert.assertTrue(isDeleted);
		actualCounter = productsPage.getShoppingCartBageCounter();
		yourCartPage = productsPage.clickShoppingCartIcon();
		softAssert.assertFalse(yourCartPage.isProductInShoppingCart(productToBeOrdered));
		softAssert.assertEquals(actualCounter,initialCounter);
		softAssert.assertAll();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Products Page")
	@Test(priority = 1, description = "Check that it is  possible to delete several products from the Shopping Cart ")
	@Description("Test-case will check that it is  possible to delete several products from the shopping cart, if user clicks to the 'Remove' btn on Products page")

	public void deleteProductsFromShoppingCart() {
		
		
		int initialCounter = productsPage.getShoppingCartBageCounter();
		List<String> listOfProducts = productsPage.getListOfProducts();
		String productToBeOrderedFirst = productsPage.getRandomElement(listOfProducts);
		String productToBeOrderedSecond = productsPage.getRandomElement(listOfProducts);
		while (productToBeOrderedFirst.equals(productToBeOrderedSecond))
		{
			productToBeOrderedSecond = productsPage.getRandomElement(listOfProducts);
		}
		productsPage.addToShoppingCart(productToBeOrderedFirst);
		productsPage.addToShoppingCart(productToBeOrderedSecond);
		int actualCounter = productsPage.getShoppingCartBageCounter();
		
		
		softAssert.assertEquals(actualCounter,initialCounter+2);
		YourCartPage yourCartPage = productsPage.clickShoppingCartIcon();
		Assert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrderedFirst));
		Assert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrderedSecond));
			
		productsPage = yourCartPage.clickAllItemsBtn();
		boolean isDeleted = productsPage.deleteFromShoppingCart(productToBeOrderedFirst);
		softAssert.assertTrue(isDeleted);
	    isDeleted = productsPage.deleteFromShoppingCart(productToBeOrderedSecond);
		softAssert.assertTrue(isDeleted);
		
		actualCounter = productsPage.getShoppingCartBageCounter();
		yourCartPage = productsPage.clickShoppingCartIcon();
		softAssert.assertFalse(yourCartPage.isProductInShoppingCart(productToBeOrderedFirst));
		softAssert.assertFalse(yourCartPage.isProductInShoppingCart(productToBeOrderedSecond));
		softAssert.assertEquals(actualCounter,initialCounter);
		softAssert.assertAll();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Products Page")
	@Test(priority = 1, description = "Check that it is  possible to delete one products from the Shopping Cart and the second one will still be there")
	@Description("Test-case will check that it is  possible to delete one product from the shopping cart and second one will still be there")

	public void deleteProductFromShoppingCartKeepOneProductInShoppingCart() {
		
		
		int initialCounter = productsPage.getShoppingCartBageCounter();
		List<String> listOfProducts = productsPage.getListOfProducts();
		String productToBeOrderedFirst = productsPage.getRandomElement(listOfProducts);
		String productToBeOrderedSecond = productsPage.getRandomElement(listOfProducts);
		while (productToBeOrderedFirst.equals(productToBeOrderedSecond))
		{
			productToBeOrderedSecond = productsPage.getRandomElement(listOfProducts);
		}
		productsPage.addToShoppingCart(productToBeOrderedFirst);
		productsPage.addToShoppingCart(productToBeOrderedSecond);
		int actualCounter = productsPage.getShoppingCartBageCounter();
		

		softAssert.assertEquals(actualCounter,initialCounter+2);
		YourCartPage yourCartPage = productsPage.clickShoppingCartIcon();
		Assert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrderedFirst));
		Assert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrderedSecond));
			
		productsPage = yourCartPage.clickAllItemsBtn();
		boolean isDeleted = productsPage.deleteFromShoppingCart(productToBeOrderedFirst);
		softAssert.assertTrue(isDeleted);
		
		actualCounter = productsPage.getShoppingCartBageCounter();
		yourCartPage = productsPage.clickShoppingCartIcon();
		softAssert.assertFalse(yourCartPage.isProductInShoppingCart(productToBeOrderedFirst));
		softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrderedSecond));
		softAssert.assertEquals(actualCounter,initialCounter+1);
		softAssert.assertAll();
	}
	
	
	@Epic("SauceDemo Application")
	@Feature("Products Page")
	@Test(priority = 2, description = "Check that it is  possible to delete the  product from the Shopping Cart if after adding we will use filter on ProductsPage ")
	@Description("Test-case will check that it is  possible to delete the product from the shopping cart, if user clicks to the 'Remove' btn on Products page after he will set filter")

	public void deleteProductFromShoppingCartWithFilter() {
		
		
		int initialCounter = productsPage.getShoppingCartBageCounter();
		List<String> listOfProducts = productsPage.getListOfProducts();
		String productToBeOrdered = productsPage.getRandomElement(listOfProducts);
		productsPage.addToShoppingCart(productToBeOrdered);
		int actualCounter = productsPage.getShoppingCartBageCounter();
		
	
		softAssert.assertEquals(actualCounter,initialCounter+1);
		YourCartPage yourCartPage = productsPage.clickShoppingCartIcon();
		softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrdered));
		productsPage = yourCartPage.clickAllItemsBtn();
		productsPage.setFilter("Name (Z to A)");
		boolean isDeleted = productsPage.deleteFromShoppingCart(productToBeOrdered);
		softAssert.assertTrue(isDeleted);
		actualCounter = productsPage.getShoppingCartBageCounter();
		yourCartPage = productsPage.clickShoppingCartIcon();
		softAssert.assertFalse(yourCartPage.isProductInShoppingCart(productToBeOrdered));
		softAssert.assertEquals(actualCounter,initialCounter);
		softAssert.assertAll();
	}
}
	
	
