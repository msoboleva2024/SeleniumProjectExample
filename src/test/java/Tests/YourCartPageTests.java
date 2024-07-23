package Tests;

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

public class YourCartPageTests extends BaseTest {

	ProductsPage productsPage;
	String productToBeOrdered;
	YourCartPage yourCartPage;
	Product productAdded;
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

		int initialCounter = productsPage.getShoppingCartBageCounter();
		List<String> listOfProducts = productsPage.getListOfProducts();
		productToBeOrdered = productsPage.getRandomElement(listOfProducts);
		productAdded = productsPage.addToShoppingCart(productToBeOrdered);
		Assert.assertNotNull(productAdded);
		int actualCounter = productsPage.getShoppingCartBageCounter();

		softAssert = new SoftAssert();
		softAssert.assertEquals(actualCounter, initialCounter + 1);
		yourCartPage = productsPage.clickShoppingCartIcon();
		softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrdered));
		softAssert.assertAll();
	}

	@Epic("SauceDemo Application")
	@Feature("ShoppingCart Page")
	@Test(priority = 0, description = "Check that it is possible to take a look to the details of product in ShoppingCart ")
	@Description("Test-case will check that it is possible to take a look to the product's details fromn shopping cart")

	public void checkDetailsOfProductYourCartPage() {

		yourCartPage.openProductForDetailsView(productToBeOrdered);
		String description = yourCartPage.getDescriptionOfProductDetailsPage();
		String name = yourCartPage.getProductNameDetailsPage();
		String price = yourCartPage.getProductPriceDetailsPage();

		softAssert.assertEquals(description, productAdded.getDescription());
		softAssert.assertEquals(name, productAdded.getName());
		softAssert.assertEquals(price, productAdded.getPrice());

	}

	@Epic("SauceDemo Application")
	@Feature("ShoppingCart Page")
	@Test(priority = 0, description = "Check that it is possible to delete product from the Detailed view page ")
	@Description("Test-case will check that it is possible to delete  a product from detiled view page")

	public void deleteProductFromDetailedViewPage() {

		yourCartPage.openProductForDetailsView(productToBeOrdered);
		int initialCounter = productsPage.getShoppingCartBageCounter();

		softAssert.assertTrue(yourCartPage.deleteProductFromDetailedViewPage());
		int actualCounter = productsPage.getShoppingCartBageCounter();
		softAssert.assertEquals(initialCounter - 1, actualCounter);
		softAssert.assertAll();

	}

	@Epic("SauceDemo Application")
	@Feature("ShoppingCart Page")
	@Test(priority = 1, description = "Check that it is possible to take a look to the details of product in ShoppingCart and return back to Products ")
	@Description("Test-case will check that it is possible to take a look to the product's details from shopping cart and return back to products")

	public void checkDetailsOfProductYourCartPageAndReturnBackToProducts() {

		yourCartPage.openProductForDetailsView(productToBeOrdered);
		String description = yourCartPage.getDescriptionOfProductDetailsPage();
		String name = yourCartPage.getProductNameDetailsPage();
		String price = yourCartPage.getProductPriceDetailsPage();
		productsPage = yourCartPage.clickBackToProducts();

		softAssert.assertEquals(description, productAdded.getDescription());
		softAssert.assertEquals(name, productAdded.getName());
		softAssert.assertEquals(price, productAdded.getPrice());
		softAssert.assertEquals(productsPage.getPageTitle(), "Products");

	}

	@Epic("SauceDemo Application")
	@Feature("ShoppingCart Page")
	@Test(priority = 1, description = "Check that it is possible to delete product from the Detailed view page and than add it again ")
	@Description("Test-case will check that it is possible to delete  a product from detiled view page and than add it again")

	public void deleteProductFromDetailedViewPageAndThanAddItAgain() {

		yourCartPage.openProductForDetailsView(productToBeOrdered);
		int initialCounter = productsPage.getShoppingCartBageCounter();

		softAssert.assertTrue(yourCartPage.deleteProductFromDetailedViewPage());
		int actualCounter = productsPage.getShoppingCartBageCounter();
		softAssert.assertEquals(initialCounter - 1, actualCounter);
		softAssert.assertTrue(yourCartPage.addProductFromDetailedViewPage());
		int updatedActualCounter = productsPage.getShoppingCartBageCounter();
		softAssert.assertEquals(actualCounter + 1, updatedActualCounter);

		softAssert.assertAll();

	}

	@Epic("SauceDemo Application")
	@Feature("ShoppingCart Page")
	@Test(priority = 1, description = "Check that it is possible to open Shopping cart, than continue Shopping ")
	@Description("Test-case will check that it is possible to add product in order, open Shopping cart, return back to continue shopping")

	public void openShoppingCartThanContinueShopping() {

		productsPage = yourCartPage.clickContinueShopping();
		softAssert.assertEquals(productsPage.getPageTitle(), "Products");
		int initialCounter = productsPage.getShoppingCartBageCounter();
		List<String> listOfProducts = productsPage.getListOfProducts();
		listOfProducts.remove(productToBeOrdered);
		String productToBeOrderedSecond = productsPage.getRandomElement(listOfProducts);
		Product productAddedSecond = productsPage.addToShoppingCart(productToBeOrderedSecond);
		softAssert.assertNotNull(productAddedSecond);
		int actualCounter = productsPage.getShoppingCartBageCounter();
		softAssert.assertEquals(initialCounter + 1, actualCounter);
		yourCartPage = productsPage.clickShoppingCartIcon();
		softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrdered));
		softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrderedSecond));
		softAssert.assertAll();

	}

	@Epic("SauceDemo Application")
	@Feature("ShoppingCart Page")
	@Test(priority = 1, description = "Check that all information about all added products in the shopping cart is correct ")
	@Description("Test-case will check that all information about all added products in the shopping cart is correct")

	public void openShoppingCartCheckDetailsOfProducts() {

		productsPage = yourCartPage.clickContinueShopping();
		softAssert.assertEquals(productsPage.getPageTitle(), "Products");
		int initialCounter = productsPage.getShoppingCartBageCounter();
		List<String> listOfProducts = productsPage.getListOfProducts();
		listOfProducts.remove(productToBeOrdered);
		String productToBeOrderedSecond = productsPage.getRandomElement(listOfProducts);
		Product productAddedSecond = productsPage.addToShoppingCart(productToBeOrderedSecond);
		softAssert.assertNotNull(productAddedSecond);
		int actualCounter = productsPage.getShoppingCartBageCounter();
		softAssert.assertEquals(initialCounter + 1, actualCounter);
		yourCartPage = productsPage.clickShoppingCartIcon();
		softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrdered));
		softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrderedSecond));
		yourCartPage.clickShoppingCartIcon();

		String description = yourCartPage.getDescriptionYourCart(productToBeOrdered);
		String name = yourCartPage.getNameYourCart(productToBeOrdered);
		String price = yourCartPage.getPriceYourCart(productToBeOrdered);

		softAssert.assertEquals(description, productAdded.getDescription());
		softAssert.assertEquals(name, productAdded.getName());
		softAssert.assertEquals(price, productAdded.getPrice());

		description = yourCartPage.getDescriptionYourCart(productToBeOrderedSecond);
		name = yourCartPage.getNameYourCart(productToBeOrderedSecond);
		price = yourCartPage.getPriceYourCart(productToBeOrderedSecond);

		softAssert.assertEquals(description, productAddedSecond.getDescription());
		softAssert.assertEquals(name, productAddedSecond.getName());
		softAssert.assertEquals(price, productAddedSecond.getPrice());

		softAssert.assertAll();

	}

    @Epic("SauceDemo Application")
    @Feature("ShoppingCart Page")
    @Test(priority = 0, description = "Check that it is possible to delete the product from shopping cart page ")
    @Description("Test-case will check that it is possible to delete added to shopping cart product from the list of orders from shopping cart page")

     public void deleteProductFromShoppingCartPage() {
	
    	 yourCartPage = productsPage.clickShoppingCartIcon();
    	 int initialCounter = productsPage.getShoppingCartBageCounter();
    	 yourCartPage.deleteProductFromTheShoppingCart(productToBeOrdered);
    	 int actualCounter = productsPage.getShoppingCartBageCounter();
    	 softAssert.assertEquals(initialCounter-1, actualCounter);
    	 softAssert.assertFalse(yourCartPage.isProductInShoppingCart(productToBeOrdered));
    	 
    	 softAssert.assertAll();
    }
    
    
    @Epic("SauceDemo Application")
    @Feature("ShoppingCart Page")
    @Test(priority = 0, description = "Check that it is possible to delete several products from shopping cart page ")
    @Description("Test-case will check that it is possible to delete several added to shopping cart products from the list of orders from shopping cart page")

     public void deleteSeveralProductsFromShoppingCartPage() {
	
		productsPage = yourCartPage.clickAllItemsBtn();
	 	int initialCounter = productsPage.getShoppingCartBageCounter();
    	List<String> listOfProducts = productsPage.getListOfProducts();
		listOfProducts.remove(productToBeOrdered);
		String productToBeOrderedSecond = productsPage.getRandomElement(listOfProducts);
		Product productAddedSecond = productsPage.addToShoppingCart(productToBeOrderedSecond);
		softAssert.assertNotNull(productAddedSecond);
		listOfProducts.remove(productToBeOrderedSecond);
		String productToBeOrderedThird = productsPage.getRandomElement(listOfProducts);
		Product productAddedThird = productsPage.addToShoppingCart(productToBeOrderedThird);
		softAssert.assertNotNull(productAddedThird);
		
		int actualCounter = productsPage.getShoppingCartBageCounter();
		softAssert.assertEquals(initialCounter + 2, actualCounter);
    	 yourCartPage = productsPage.clickShoppingCartIcon();
    	
    	 yourCartPage.deleteProductFromTheShoppingCart(productToBeOrdered);
    	 yourCartPage.deleteProductFromTheShoppingCart(productToBeOrderedSecond);
    	
    	 actualCounter = yourCartPage.getShoppingCartBageCounter();
    	 softAssert.assertEquals(initialCounter, actualCounter);
    	 softAssert.assertFalse(yourCartPage.isProductInShoppingCart(productToBeOrdered));
    	 softAssert.assertFalse(yourCartPage.isProductInShoppingCart(productToBeOrderedSecond));
    	 softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrderedThird));
    	 
    	 softAssert.assertAll();
    }
   }



