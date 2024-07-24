package Tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import PageObject.Checkout_CompletePage;
import PageObject.Checkout_OverviewPage;
import PageObject.Checkout_YourInformationPage;
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

		Checkout_YourInformationPage checkoutYourInformationPage = yourCartPage.clickCheckoutBtn();
		checkoutYourInformationPage.setCheckoutInformation("TestUserSurname", "TestUser name", "12345");
		Checkout_OverviewPage overviewPage = checkoutYourInformationPage.clickContinueBtn();
		softAssert.assertFalse(checkoutYourInformationPage.isErrorShown());
		List<Product> products = new ArrayList<Product>();
		products.add(productAdded);
		softAssert.assertTrue(overviewPage.checkProductsInOrder(products));
		Checkout_CompletePage completePage = overviewPage.clickFinishBtn();
		String finalInfo = completePage.getInfoAboutOrder();
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

		
		Checkout_YourInformationPage checkoutYourInformationPage = yourCartPage.clickCheckoutBtn();
		checkoutYourInformationPage.setCheckoutInformation("TestUser name", "TestUser Surname", "12345");
		Checkout_OverviewPage overviewPage = checkoutYourInformationPage.clickContinueBtn();
		softAssert.assertFalse(checkoutYourInformationPage.isErrorShown());
		List<Product> products = new ArrayList<Product>();
		products.add(productAdded);
		products.add(productAddedSecond);
		softAssert.assertTrue(overviewPage.checkProductsInOrder(products));
		Checkout_CompletePage completePage = overviewPage.clickFinishBtn();
		String finalInfo = completePage.getInfoAboutOrder();
		softAssert.assertEquals(finalInfo,
				"Thank you for your order! Your order has been dispatched, and will arrive just as fast as the pony can get there!");

		softAssert.assertAll();
	}

	
	
	public void checkoutWithoutItemsInShoppingCart() {

		yourCartPage.deleteProductFromTheShoppingCart(productToBeOrdered);
		Checkout_YourInformationPage checkoutYourInformaitonPage = yourCartPage.clickCheckoutBtn();
		Assert.assertFalse(checkoutYourInformaitonPage.getPageSubtitle().equals("Checkout: Your Information"),"Error:It is possible to checkout without orders in shopping cart!");
	}
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 1, description = "Checkout order and  fill the data on another language checkoutPage ")
	@Description("Test-case will check that it is  possible to checkout the order is user will  enter data on DE language")

	public void checkoutProductAnotherLanguage() {

		Checkout_YourInformationPage checkoutYouInformationPage = yourCartPage.clickCheckoutBtn();
		checkoutYouInformationPage.setCheckoutInformation("Test äüöß", "äüöß Familienname", "12345");
		
		Checkout_OverviewPage overviewPage  = checkoutYouInformationPage.clickContinueBtn();
		softAssert.assertFalse(checkoutYouInformationPage.isErrorShown());
		List<Product> products = new ArrayList<Product>();
		products.add(productAdded);
		softAssert.assertTrue(overviewPage.checkProductsInOrder(products));
		Checkout_CompletePage completePage = overviewPage.clickFinishBtn();
		String finalInfo = completePage.getInfoAboutOrder();
		softAssert.assertEquals(finalInfo,
				"Thank you for your order! Your order has been dispatched, and will arrive just as fast as the pony can get there!");

		softAssert.assertAll();
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
