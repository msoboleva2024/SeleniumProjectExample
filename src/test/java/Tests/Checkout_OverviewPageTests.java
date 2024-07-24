package Tests;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
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

public class Checkout_OverviewPageTests extends BaseTest {
	
	
	ProductsPage productsPage;
	String productToBeOrdered;
	YourCartPage yourCartPage;
	Product productAdded;
	String productToBeOrderedSecond;
	Product productAddedSecond;
	List<String> listOfProducts;
	Checkout_YourInformationPage checkoutYourInformationPage;
	Checkout_OverviewPage overviewPage;

	List<Product> products ;
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
		products = new ArrayList<Product>();
		products.add(productAdded);
		
		softAssert.assertAll();
	


	}

//click cancel
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 0, description = "Checkout order and click cancel on the Overview page")
	@Description("Test-case will check that it is  possible to cancel checkout process on the overview page")

	public void checkoutProductClickCancel() {
		
		checkoutYourInformationPage = yourCartPage.clickCheckoutBtn();
		checkoutYourInformationPage.setCheckoutInformation("TestUserSurname", "TestUser name", "12345");
		 overviewPage = checkoutYourInformationPage.clickContinueBtn();
		softAssert.assertFalse(checkoutYourInformationPage.isErrorShown());
		productsPage = overviewPage.clickCancel();
		yourCartPage = productsPage.clickShoppingCartIcon();
		Assert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrdered));
		

	}
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 0, description = "Checkout order and click finish on the Overview page")
	@Description("Test-case will check that it is  possible to checkout the order if user will click to finish btn on the overview page")

	public void checkoutProductClickFinish() {
		
		checkoutYourInformationPage = yourCartPage.clickCheckoutBtn();
		checkoutYourInformationPage.setCheckoutInformation("TestUserSurname", "TestUser name", "12345");
		 overviewPage = checkoutYourInformationPage.clickContinueBtn();
		softAssert.assertFalse(checkoutYourInformationPage.isErrorShown());
		Checkout_CompletePage completePage = overviewPage.clickFinishBtn();
		String finalInfo = completePage.getInfoAboutOrder();
		softAssert.assertEquals(finalInfo,
				"Thank you for your order! Your order has been dispatched, and will arrive just as fast as the pony can get there!");

		softAssert.assertAll();
		
	}
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 1, description = "Checkout order and click to the link of product name on the Overview page")
	@Description("Test-case will check that it is  possible to open Details view of Product from the overview page")

	public void checkoutProductOpenDetailedView() {
		
		checkoutYourInformationPage = yourCartPage.clickCheckoutBtn();
		checkoutYourInformationPage.setCheckoutInformation("TestUserSurname", "TestUser name", "12345");
		 overviewPage = checkoutYourInformationPage.clickContinueBtn();
		softAssert.assertFalse(checkoutYourInformationPage.isErrorShown());

		yourCartPage = overviewPage.clickOnProductForDetailedView(productToBeOrdered);
	    Assert.assertEquals(yourCartPage.getProductNameDetailsPage(),productAdded.getName() );
	    Assert.assertEquals(yourCartPage.getProductPriceDetailsPage(), productAdded.getPrice());
	    Assert.assertEquals(yourCartPage.getDescriptionOfProductDetailsPage(), productAdded.getDescription());
		
	}
	


	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 0, description = "Checkout order and check info on the Overview page")
	@Description("Test-case will check that the info, available on the overview page is valid and correct")

	public void checkoutProductCheckOverviewInfo() {
		
		checkoutYourInformationPage = yourCartPage.clickCheckoutBtn();
		checkoutYourInformationPage.setCheckoutInformation("TestUserSurname", "TestUser name", "12345");
		 overviewPage = checkoutYourInformationPage.clickContinueBtn();
		softAssert.assertFalse(checkoutYourInformationPage.isErrorShown());
		//products = new ArrayList<Product>();
		//products.add(productAdded);
		
		softAssert.assertTrue(overviewPage.checkProductsInOrder(products));
		softAssert.assertTrue(overviewPage.isLabelsAppeared());
		double totalPrice=overviewPage.calculateItemTotal(products);
		BigDecimal roundedValueTax = overviewPage.calculateTax(products);
		softAssert.assertEquals(overviewPage.getItemTotal(), "Item total: "+totalPrice);
		softAssert.assertEquals(overviewPage.getTax(), "Tax: " +roundedValueTax);
		double price = overviewPage.calculateTotalPrice(roundedValueTax,totalPrice);
		softAssert.assertEquals(overviewPage.getTotalLabel(),"Total: "+ price);
		
		softAssert.assertAll();
	}
	
	
	
	@Epic("SauceDemo Application")
	@Feature("Checkout Page")
	@Test(priority = 0, description = "Checkout order with several products and check info on the Overview page")
	@Description("Test-case will check that the info, available on the overview page is valid and correct")

	public void checkoutProductsCheckOverviewInfo() {
		

		
		listOfProducts.remove(productToBeOrdered);
		productsPage = yourCartPage.clickAllItemsBtn();
		productToBeOrderedSecond = productsPage.getRandomElement(listOfProducts);
		
		productAddedSecond = productsPage.addToShoppingCart(productToBeOrderedSecond);
		Assert.assertNotNull(productAddedSecond);

		int actualCounter = productsPage.getShoppingCartBageCounter();

		softAssert.assertEquals(actualCounter, 2);
		yourCartPage = productsPage.clickShoppingCartIcon();
		softAssert.assertTrue(yourCartPage.isProductInShoppingCart(productToBeOrderedSecond));
		
		checkoutYourInformationPage = yourCartPage.clickCheckoutBtn();
		checkoutYourInformationPage.setCheckoutInformation("TestUserSurname", "TestUser name", "12345");
		 overviewPage = checkoutYourInformationPage.clickContinueBtn();
		softAssert.assertFalse(checkoutYourInformationPage.isErrorShown());

		products.add(productAddedSecond);
		
		softAssert.assertTrue(overviewPage.isLabelsAppeared());
		
		double totalPrice=overviewPage.calculateItemTotal(products);
		BigDecimal roundedValueTax = overviewPage.calculateTax(products);
		softAssert.assertEquals(overviewPage.getItemTotal(), "Item total: "+totalPrice);
		softAssert.assertEquals(overviewPage.getTax(), "Tax: " +roundedValueTax);
		double price = overviewPage.calculateTotalPrice(roundedValueTax,totalPrice);
		softAssert.assertEquals(overviewPage.getTotalLabel(),"Total: "+ price);
		
		softAssert.assertAll();
	}
	
	


}
