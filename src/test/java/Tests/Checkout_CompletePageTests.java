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

public class Checkout_CompletePageTests extends BaseTest {
	
	

	ProductsPage productsPage;
	String productToBeOrdered;
	YourCartPage yourCartPage;
	Product productAdded;
	String productToBeOrderedSecond;
	Product productAddedSecond;
	List<String> listOfProducts;
	Checkout_CompletePage completePage;
	
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
		
		Checkout_YourInformationPage checkoutYourInformationPage = yourCartPage.clickCheckoutBtn();
		checkoutYourInformationPage.setCheckoutInformation("TestUserSurname", "TestUser name", "12345");
		Checkout_OverviewPage overviewPage = checkoutYourInformationPage.clickContinueBtn();
		softAssert.assertFalse(checkoutYourInformationPage.isErrorShown());
		List<Product> products = new ArrayList<Product>();
		products.add(productAdded);
		softAssert.assertTrue(overviewPage.checkProductsInOrder(products));
		completePage = overviewPage.clickFinishBtn();
		
		softAssert.assertAll();
	

	}
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 0, description = "Checkout order on the last Checkout Complete page")
	@Description("Test-case will check final infoon Complete page and possibility to return back to ProductsPage")

	public void getInfoAboutOrderAndReturnHome() {
		
		String finalInfo = completePage.getInfoAboutOrder();
		softAssert.assertEquals(finalInfo,
				"Thank you for your order! Your order has been dispatched, and will arrive just as fast as the pony can get there!");

		softAssert.assertAll();
		
	}
}
