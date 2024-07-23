package Tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import PageObject.CheckoutPage;
import PageObject.ProductsPage;
import PageObject.YourCartPage;
import Resources.Product;
import TestComponent.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

public class CheckoutPageTests extends BaseTest {

	ProductsPage productsPage;
	String productToBeOrdered;
	YourCartPage yourCartPage;
	Product productAdded;
	String productToBeOrderedSecond;
	Product productAddedSecond;
	List<String> listOfProducts;

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
		listOfProducts = productsPage.getListOfProducts();
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
	@Feature("Checkout Page")
	@Test(priority = 0, description = "Checkout order and fill all data correct ")
	@Description("Test-case will check that it is possible to checkout the order after successfull addition of needed information")

	public void checkoutProduct() {

		CheckoutPage checkoutPage = yourCartPage.clickCheckoutBtn();
		checkoutPage.setCheckoutInformation("TestUserSurname", "TestUser name", "12345");
		checkoutPage.clickContinueBtn();
		softAssert.assertFalse(checkoutPage.isErrorShown());
		List<Product> products = new ArrayList<Product>();
		products.add(productAdded);
		softAssert.assertTrue(checkoutPage.checkProductsInOrder(products));
		checkoutPage.clickFinishBtn();
		String finalInfo = checkoutPage.getInfoAboutOrder();
		softAssert.assertEquals(finalInfo,
				"Thank you for your order! Your order has been dispatched, and will arrive just as fast as the pony can get there!");

		softAssert.assertAll();
	}

	

	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 0, description = "Checkout order with several products and fill all data correct ")
	@Description("Test-case will check that it is possible to checkout the order with several products after successfull addition of needed information")

	public void checkoutSeveralProducts() {

		listOfProducts.remove(productToBeOrdered);
		productsPage = yourCartPage.clickAllItemsBtn();
		productToBeOrderedSecond = productsPage.getRandomElement(listOfProducts);
		
		productAddedSecond = productsPage.addToShoppingCart(productToBeOrderedSecond);
		Assert.assertNotNull(productAddedSecond);

		int actualCounter = productsPage.getShoppingCartBageCounter();

		softAssert.assertEquals(actualCounter, 2);
		yourCartPage = productsPage.clickShoppingCartIcon();
		softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrderedSecond));

		
		CheckoutPage checkoutPage = yourCartPage.clickCheckoutBtn();
		checkoutPage.setCheckoutInformation("TestUser name", "TestUser Surname", "12345");
		checkoutPage.clickContinueBtn();
		softAssert.assertFalse(checkoutPage.isErrorShown());
		List<Product> products = new ArrayList<Product>();
		products.add(productAdded);
		products.add(productAddedSecond);
		softAssert.assertTrue(checkoutPage.checkProductsInOrder(products));
		checkoutPage.clickFinishBtn();
		String finalInfo = checkoutPage.getInfoAboutOrder();
		softAssert.assertEquals(finalInfo,
				"Thank you for your order! Your order has been dispatched, and will arrive just as fast as the pony can get there!");

		softAssert.assertAll();
	}

	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 0, description = "Checkout order and dont fill the name data on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order is user will not enter the name of checkout page")

	public void checkoutProductEmptyName() {

		CheckoutPage checkoutPage = yourCartPage.clickCheckoutBtn();
		checkoutPage.setCheckoutInformation("", "Surname", "12345");
		checkoutPage.clickContinueBtn();
		softAssert.assertTrue(checkoutPage.isErrorShown());
		softAssert.assertEquals(checkoutPage.getErrorMessageText(), "Error: First Name is required");
		softAssert.assertTrue(checkoutPage.isRedCircleAppeared("firstName"));
		softAssert.assertFalse(checkoutPage.isRedCircleAppeared("lastName"));
		softAssert.assertFalse(checkoutPage.isRedCircleAppeared("postalCode"));
		softAssert.assertAll();
	}

	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 0, description = "Checkout order and dont fill the surname data on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order is user will not enter the surname of checkout page")

	public void checkoutProductEmptySurname() {

		CheckoutPage checkoutPage = yourCartPage.clickCheckoutBtn();
		checkoutPage.setCheckoutInformation("Name test", "", "12345");
		checkoutPage.clickContinueBtn();
		softAssert.assertTrue(checkoutPage.isErrorShown());
		softAssert.assertEquals(checkoutPage.getErrorMessageText(), "Error: Last Name is required");
		softAssert.assertFalse(checkoutPage.isRedCircleAppeared("firstName"));
		softAssert.assertTrue(checkoutPage.isRedCircleAppeared("lastName"));
		softAssert.assertFalse(checkoutPage.isRedCircleAppeared("postalCode"));
		softAssert.assertAll();
	}

	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 0, description = "Checkout order and dont fill the postalCode data on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order is user will not enter the postalCode of checkout page")

	public void checkoutProductEmptyPostalCode() {

		CheckoutPage checkoutPage = yourCartPage.clickCheckoutBtn();
		checkoutPage.setCheckoutInformation("Name test", "Surname user", "");
		checkoutPage.clickContinueBtn();
		softAssert.assertTrue(checkoutPage.isErrorShown());
		softAssert.assertEquals(checkoutPage.getErrorMessageText(), "Error: Postal Code is required");
		softAssert.assertFalse(checkoutPage.isRedCircleAppeared("firstName"));
		softAssert.assertFalse(checkoutPage.isRedCircleAppeared("lastName"));
		softAssert.assertTrue(checkoutPage.isRedCircleAppeared("postalCode"));
		softAssert.assertAll();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 1, description = "Checkout order and dont fill any data on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order is user will not enter any data of checkout page")

	public void checkoutProductWithoutData() {

		CheckoutPage checkoutPage = yourCartPage.clickCheckoutBtn();
		checkoutPage.setCheckoutInformation("", "", "");
		checkoutPage.clickContinueBtn();
		softAssert.assertTrue(checkoutPage.isErrorShown());
		softAssert.assertEquals(checkoutPage.getErrorMessageText(), "Error: First Name is required. Last Name is required. Postal Code is required");
		softAssert.assertTrue(checkoutPage.isRedCircleAppeared("firstName"));
		softAssert.assertTrue(checkoutPage.isRedCircleAppeared("lastName"));
		softAssert.assertTrue(checkoutPage.isRedCircleAppeared("postalCode"));
		softAssert.assertAll();
	}
	
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 1, description = "Checkout order and dont fill name and surname on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order is user will not enter name and surname of checkout page")

	public void checkoutProductWithoutDataInSurnameName() {

		CheckoutPage checkoutPage = yourCartPage.clickCheckoutBtn();
		checkoutPage.setCheckoutInformation("", "", "23344");
		checkoutPage.clickContinueBtn();
		softAssert.assertTrue(checkoutPage.isErrorShown());
		softAssert.assertEquals(checkoutPage.getErrorMessageText(), "Error: First Name is required. Last Name is required");
		softAssert.assertTrue(checkoutPage.isRedCircleAppeared("firstName"));
		softAssert.assertTrue(checkoutPage.isRedCircleAppeared("lastName"));
		softAssert.assertFalse(checkoutPage.isRedCircleAppeared("postalCode"));
		softAssert.assertAll();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 1, description = "Checkout order and dont fill name and postalCode on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order is user will not enter name and postalCode of checkout page")

	public void checkoutProductWithoutDataInPostalCodeName() {

		CheckoutPage checkoutPage = yourCartPage.clickCheckoutBtn();
		checkoutPage.setCheckoutInformation("", "Test surname", "");
		checkoutPage.clickContinueBtn();
		softAssert.assertTrue(checkoutPage.isErrorShown());
		softAssert.assertEquals(checkoutPage.getErrorMessageText(), "Error: First Name is required. Postal Code is required");
		softAssert.assertTrue(checkoutPage.isRedCircleAppeared("firstName"));
		softAssert.assertFalse(checkoutPage.isRedCircleAppeared("lastName"));
		softAssert.assertTrue(checkoutPage.isRedCircleAppeared("postalCode"));
		softAssert.assertAll();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 1, description = "Checkout order and dont fill surname and postalCode on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order is user will not enter surname and postalCode of checkout page")

	public void checkoutProductWithoutDataInPostalCodeSurname() {

		CheckoutPage checkoutPage = yourCartPage.clickCheckoutBtn();
		checkoutPage.setCheckoutInformation("Test", "", "");
		checkoutPage.clickContinueBtn();
		softAssert.assertTrue(checkoutPage.isErrorShown());
		softAssert.assertEquals(checkoutPage.getErrorMessageText(), "Error: Last Name is required. Postal Code is required");
		softAssert.assertFalse(checkoutPage.isRedCircleAppeared("firstName"));
		softAssert.assertTrue(checkoutPage.isRedCircleAppeared("lastName"));
		softAssert.assertTrue(checkoutPage.isRedCircleAppeared("postalCode"));
		softAssert.assertAll();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 1, description = "Checkout order and  fill the data on another language checkoutPage ")
	@Description("Test-case will check that it is  possible to checkout the order is user will  enter data on DE language")

	public void checkoutProductAnotherLanguage() {

		CheckoutPage checkoutPage = yourCartPage.clickCheckoutBtn();
		checkoutPage.setCheckoutInformation("Test äüöß", "äüöß Familienname", "12345");
		
		checkoutPage.clickContinueBtn();
		softAssert.assertFalse(checkoutPage.isErrorShown());
		List<Product> products = new ArrayList<Product>();
		products.add(productAdded);
		softAssert.assertTrue(checkoutPage.checkProductsInOrder(products));
		checkoutPage.clickFinishBtn();
		String finalInfo = checkoutPage.getInfoAboutOrder();
		softAssert.assertEquals(finalInfo,
				"Thank you for your order! Your order has been dispatched, and will arrive just as fast as the pony can get there!");

		softAssert.assertAll();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 0, description = "Checkout order without items")
	@Description("Test-case will check that it is not possible to checkout the order without items in it")

	public void checkoutWithoutItemsInShoppingCart() {

		yourCartPage.deleteProductFromTheShoppingCart(productToBeOrdered);
		CheckoutPage checkoutPage = yourCartPage.clickCheckoutBtn();
		Assert.assertFalse(checkoutPage.getPageSubtitle().equals("Checkout: Your Information"),"Error:It is possible to checkout without orders in shopping cart!");
	}

	

	// нажимаем cancel во время chekout
	// нажимаем cancel c overview page
	// нажимаем finish с overview page
	// back home from complete page
	// menu items from from complete page
	// shopping cart from complete page
	
	// menu items from your information page
	// shopping cart from your information page

}
