package Tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import PageObject.ProductsPage;
import PageObject.YourCartPage;
import TestComponent.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

public class MenuItemsTests extends BaseTest {

	ProductsPage productsPage;

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
	@Test(priority = 0, description = "Check Menu Items for Products Page")
	@Description("Test-case will check that it is possible to open menu from Products page and all needed meny items are presented")
	public void checkMenuItemsProductsPage() {

		productsPage.clickMenuItemsIcon();
		ArrayList<String> menuItems = productsPage.getAllMenuItems();

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(menuItems.contains("All Items"), true);
		softAssert.assertEquals(menuItems.contains("About"), true);
		softAssert.assertEquals(menuItems.contains("Logout"), true);
		softAssert.assertEquals(menuItems.contains("Reset App State"), true);
		softAssert.assertEquals(menuItems.size(), 4);

		softAssert.assertAll();
	}

	@Epic("SauceDemo Application")
	@Feature("Menu Items for all pages")
	@Test(priority = 0, description = "Check Menu Items for Shopping Cart")
	@Description("Test-case will check that it is possible to open menu from ShoppingCart page and all needed meny items are presented")
	public void checkMenuItemsShoppingCart() {

		YourCartPage yourCartPage = productsPage.clickShoppingCartIcon();
		yourCartPage.clickMenuItemsIcon();
		ArrayList<String> menuItems = productsPage.getAllMenuItems();

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(menuItems.contains("All Items"), true);
		softAssert.assertEquals(menuItems.contains("About"), true);
		softAssert.assertEquals(menuItems.contains("Logout"), true);
		softAssert.assertEquals(menuItems.contains("Reset App State"), true);
		softAssert.assertEquals(menuItems.size(), 4);

		softAssert.assertAll();
	}

	@Epic("SauceDemo Application")
	@Feature("Menu Items for all pages")
	@Test(priority = 0, description = "Check All Items from  ShoppingCart page")
	@Description("Test-case will check that it is possible to return back to Products page from ShoppingCart page with help of 'All Items' menu item")
	public void checkAllItemsMenuItem() {

		YourCartPage yourCartPage = productsPage.clickShoppingCartIcon();
		productsPage = yourCartPage.clickAllItemsBtn();

		Assert.assertEquals(productsPage.getPageTitle(), "Products");
	}

	@Epic("SauceDemo Application")
	@Feature("Menu Items for all pages")
	@Test(priority = 0, description = "Check About menu item from  Products page")
	@Description("Test-case will check that it is possible to open info about company with help of menu Item 'About' ")
	public void checkAboutMenuItem() {

		String title = productsPage.clickAboutBtn();
		Assert.assertEquals(title, "Sauce Labs: Cross Browser Testing, Selenium Testing & Mobile Testing");
	}
	
	@Epic("SauceDemo Application")
	@Feature("Menu Items for all pages")
	@Test(priority = 0, description = "Check Logout menu item from  Products page")
	@Description("Test-case will check that it is possible to logout from application if user click to 'Logout' menu item ")
	public void checkLogoutMenuItem() {

		loginPage = productsPage.clickLogoutBtn();
		Assert.assertEquals(loginPage.getPageName(), "Swag Labs");
	}
	
	@Epic("SauceDemo Application")
	@Feature("Menu Items for all pages")
	@Test(priority = 0, description = "Check Reset menu item from  Product page")
	@Description("Test-case will check that it is possible to reset added in shoppingCart products with help of the click to menu item 'Reset'")
	public void checkResetMenuItem() {
				
				int initialCounter = productsPage.getShoppingCartBageCounter();
				List<String> listOfProducts = productsPage.getListOfProducts();
				String productToBeOrdered = productsPage.getRandomElement(listOfProducts);
				productsPage.addToShoppingCart(productToBeOrdered);
				int actualCounter = productsPage.getShoppingCartBageCounter();
				
				SoftAssert softAssert = new SoftAssert();
				softAssert.assertEquals(actualCounter,initialCounter+1);
				productsPage.clickResetBtn();
				actualCounter = productsPage.getShoppingCartBageCounter();
				softAssert.assertEquals(actualCounter,initialCounter);
                softAssert.assertFalse(productsPage.isRemoveBtnPresentedForProduct(productToBeOrdered));
				softAssert.assertAll();
	}

}
