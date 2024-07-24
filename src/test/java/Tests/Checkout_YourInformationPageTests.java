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

public class Checkout_YourInformationPageTests extends BaseTest {
	
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
	@Test(priority = 0, description = "Checkout order and dont fill the name data on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order is user will not enter the name of checkout page")

	public void checkoutProductEmptyName() {

		Checkout_YourInformationPage checkoutYouInformationPage = yourCartPage.clickCheckoutBtn();
		checkoutYouInformationPage.setCheckoutInformation("", "Surname", "12345");
		checkoutYouInformationPage.clickContinueBtn();
		softAssert.assertTrue(checkoutYouInformationPage.isErrorShown());
		softAssert.assertEquals(checkoutYouInformationPage.getErrorMessageText(), "Error: First Name is required");
		softAssert.assertTrue(checkoutYouInformationPage.isRedCircleAppeared("firstName"));
		softAssert.assertFalse(checkoutYouInformationPage.isRedCircleAppeared("lastName"));
		softAssert.assertFalse(checkoutYouInformationPage.isRedCircleAppeared("postalCode"));
		softAssert.assertAll();
	}

	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 0, description = "Checkout order and dont fill the surname data on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order is user will not enter the surname of checkout page")

	public void checkoutProductEmptySurname() {

		Checkout_YourInformationPage checkoutYouInformationPage  = yourCartPage.clickCheckoutBtn();
		checkoutYouInformationPage.setCheckoutInformation("Name test", "", "12345");
		checkoutYouInformationPage.clickContinueBtn();
		softAssert.assertTrue(checkoutYouInformationPage.isErrorShown());
		softAssert.assertEquals(checkoutYouInformationPage.getErrorMessageText(), "Error: Last Name is required");
		softAssert.assertFalse(checkoutYouInformationPage.isRedCircleAppeared("firstName"));
		softAssert.assertTrue(checkoutYouInformationPage.isRedCircleAppeared("lastName"));
		softAssert.assertFalse(checkoutYouInformationPage.isRedCircleAppeared("postalCode"));
		softAssert.assertAll();
	}

	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 0, description = "Checkout order and dont fill the postalCode data on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order is user will not enter the postalCode of checkout page")

	public void checkoutProductEmptyPostalCode() {

		Checkout_YourInformationPage checkoutYouInformationPage  = yourCartPage.clickCheckoutBtn();
		checkoutYouInformationPage.setCheckoutInformation("Name test", "Surname user", "");
		checkoutYouInformationPage.clickContinueBtn();
		softAssert.assertTrue(checkoutYouInformationPage.isErrorShown());
		softAssert.assertEquals(checkoutYouInformationPage.getErrorMessageText(), "Error: Postal Code is required");
		softAssert.assertFalse(checkoutYouInformationPage.isRedCircleAppeared("firstName"));
		softAssert.assertFalse(checkoutYouInformationPage.isRedCircleAppeared("lastName"));
		softAssert.assertTrue(checkoutYouInformationPage.isRedCircleAppeared("postalCode"));
		softAssert.assertAll();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 1, description = "Checkout order and dont fill any data on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order is user will not enter any data of checkout page")

	public void checkoutProductWithoutData() {

		Checkout_YourInformationPage checkoutYouInformationPage  = yourCartPage.clickCheckoutBtn();
		checkoutYouInformationPage.setCheckoutInformation("", "", "");
		checkoutYouInformationPage.clickContinueBtn();
		softAssert.assertTrue(checkoutYouInformationPage.isErrorShown());
		softAssert.assertEquals(checkoutYouInformationPage.getErrorMessageText(), "Error: First Name is required. Last Name is required. Postal Code is required");
		softAssert.assertTrue(checkoutYouInformationPage.isRedCircleAppeared("firstName"));
		softAssert.assertTrue(checkoutYouInformationPage.isRedCircleAppeared("lastName"));
		softAssert.assertTrue(checkoutYouInformationPage.isRedCircleAppeared("postalCode"));
		softAssert.assertAll();
	}
	
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 1, description = "Checkout order and dont fill name and surname on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order is user will not enter name and surname of checkout page")

	public void checkoutProductWithoutDataInSurnameName() {

		Checkout_YourInformationPage checkoutYouInformationPage  = yourCartPage.clickCheckoutBtn();
		checkoutYouInformationPage.setCheckoutInformation("", "", "23344");
		checkoutYouInformationPage.clickContinueBtn();
		softAssert.assertTrue(checkoutYouInformationPage.isErrorShown());
		softAssert.assertEquals(checkoutYouInformationPage.getErrorMessageText(), "Error: First Name is required. Last Name is required");
		softAssert.assertTrue(checkoutYouInformationPage.isRedCircleAppeared("firstName"));
		softAssert.assertTrue(checkoutYouInformationPage.isRedCircleAppeared("lastName"));
		softAssert.assertFalse(checkoutYouInformationPage.isRedCircleAppeared("postalCode"));
		softAssert.assertAll();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 1, description = "Checkout order and dont fill name and postalCode on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order is user will not enter name and postalCode of checkout page")

	public void checkoutProductWithoutDataInPostalCodeName() {

		Checkout_YourInformationPage checkoutYouInformationPage = yourCartPage.clickCheckoutBtn();
		checkoutYouInformationPage.setCheckoutInformation("", "Test surname", "");
		checkoutYouInformationPage.clickContinueBtn();
		softAssert.assertTrue(checkoutYouInformationPage.isErrorShown());
		softAssert.assertEquals(checkoutYouInformationPage.getErrorMessageText(), "Error: First Name is required. Postal Code is required");
		softAssert.assertTrue(checkoutYouInformationPage.isRedCircleAppeared("firstName"));
		softAssert.assertFalse(checkoutYouInformationPage.isRedCircleAppeared("lastName"));
		softAssert.assertTrue(checkoutYouInformationPage.isRedCircleAppeared("postalCode"));
		softAssert.assertAll();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 1, description = "Checkout order and dont fill surname and postalCode on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order is user will not enter surname and postalCode of checkout page")

	public void checkoutProductWithoutDataInPostalCodeSurname() {

		Checkout_YourInformationPage checkoutYouInformationPage  = yourCartPage.clickCheckoutBtn();
		checkoutYouInformationPage.setCheckoutInformation("Test", "", "");
		checkoutYouInformationPage.clickContinueBtn();
		softAssert.assertTrue(checkoutYouInformationPage.isErrorShown());
		softAssert.assertEquals(checkoutYouInformationPage.getErrorMessageText(), "Error: Last Name is required. Postal Code is required");
		softAssert.assertFalse(checkoutYouInformationPage.isRedCircleAppeared("firstName"));
		softAssert.assertTrue(checkoutYouInformationPage.isRedCircleAppeared("lastName"));
		softAssert.assertTrue(checkoutYouInformationPage.isRedCircleAppeared("postalCode"));
		softAssert.assertAll();
	}
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 2, description = "Checkout order and fill the name data with a String more than 254 symbols on checkoutPage ")
	@Description("Test-case will check that it is possible to checkout the order if user will  enter the name more than 254 symbols of checkout page")

	public void checkoutProductLongName() {

		Checkout_YourInformationPage checkoutYourInformationPage = yourCartPage.clickCheckoutBtn();
		checkoutYourInformationPage.setCheckoutInformation("TestUserSurnameTestUserSurnameTestUserSurnameTestUserSurnameTestUserSurnameTestUserSurnameTestUserSurnameTestUserSurnameTestUserSurnameTestUserSurnameTestUserSurnameTestUserSurnameTestUserSurnameTestUserSurnameTestUserSurnameTestUserSurnameTestUserSurnamf", "TestUser surname", "12345");
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
	@Test(priority = 2, description = "Checkout order and fill the postal code data with a String instead of numeric value on checkoutPage ")
	@Description("Test-case will check that it is not possible to checkout the order if user will  enter String value for Postal code on checkout page")

	public void checkoutProductStringPostalCode() {

		Checkout_YourInformationPage checkoutYourInformationPage = yourCartPage.clickCheckoutBtn();
	    checkoutYourInformationPage.clickContinueBtn();
		softAssert.assertTrue(checkoutYourInformationPage.isErrorShown());

		softAssert.assertAll();
	}

}
