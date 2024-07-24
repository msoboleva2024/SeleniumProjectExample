package Tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObject.ProductsPage;
import TestComponent.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

public class LoginPageTests extends BaseTest {

	@Epic("SauceDemo Application")
	@Feature("Login Page")
	@Test(priority = 0, description = "Positiv scenario for testing of login to the application")
	@Description("Test-case will check that it is possible to login to application, if user will enter valid username and password and click to Login btn.")
	public void loginToApplication_Positive() {

		// preconditions: get test-data
		List<String> users = loginPage.getTestUsers();
		String password = loginPage.getTestPassword();

		// login to application
		loginPage.setUsername(users.get(0));
		System.out.println("The following User was used for the login: " + users.get(0));
		loginPage.setPassword(password);
		System.out.println("The following Password was used for the login: " + password);
		ProductsPage productsPage = loginPage.clickLoginBtn();

		// check expected results
		Assert.assertEquals(productsPage.getPageTitle(), "Products");

	}

	@Epic("SauceDemo Application")
	@Feature("Login Page")
	@Test(priority = 0, description = "Negative scenario for testing of login to the application: invalid pair of username and password")
	@Description("Test-case will check that it is not possible to login to application, if user will enter invalid username and password and click to Login btn. Error message should appear on the screen")
	public void loginToApplication_InvalidCombinationOfUsernameAndPassword() {

		// preconditions: get test-data
		List<String> users = loginPage.getTestUsers();
		String password = loginPage.getTestPassword();

		// login to application
		loginPage.setUsername(users.get(0));
		System.out.println("The following User was used for the login: " + users.get(0));
		loginPage.setPassword((password + "invlaid"));
		System.out.println("The following Password was used for the login: " + password + "invalid");
		loginPage.clickLoginBtn();

		// check expected results
		Assert.assertTrue(loginPage.isErrorMsgAppeared());
		Assert.assertTrue(loginPage.isErrorCircleAppearedForFields());
		Assert.assertEquals(loginPage.getTextErrorMsg(),
				"Epic sadface: Username and password do not match any user in this service");

	}

	@Epic("SauceDemo Application")
	@Feature("Login Page")
	@Test(priority = 1, description = "Negative scenario for testing of login to the application without enetered username")
	@Description("Test-case will check that it is not possible to login to application, if user will not enter the username, but will add the password and click to Login btn. Error message should appear on the screen")
	public void loginToApplication_EmptyUsernameAndValidPassword() {

		// preconditions: get test-data
		String password = loginPage.getTestPassword();

		// login to application
		loginPage.setUsername("");
		System.out.println("The following User was used for the login: " + "no data was set: (empty) field");
		loginPage.setPassword(password);
		System.out.println("The following Password was used for the login: " + password);
		loginPage.clickLoginBtn();

		// check expected results
		Assert.assertTrue(loginPage.isErrorMsgAppeared());
		Assert.assertTrue(loginPage.isErrorCircleAppearedForFields());
		Assert.assertEquals(loginPage.getTextErrorMsg(), "Epic sadface: Username is required");

	}

	@Epic("SauceDemo Application")
	@Feature("Login Page")
	@Test(priority = 1, description = "Negative scenario for testing of login to the application without enetered password")
	@Description("Test-case will check that it is not possible to login to application, if user will  enter the valid username, but will not add the password and click to Login btn. Error message should appear on the screen")
	public void loginToApplication_ValidUsernameAndEmptyPassword() {

		// preconditions: get test-data
		List<String> users = loginPage.getTestUsers();

		// login to application
		loginPage.setUsername(users.get(0));
		System.out.println("The following User was used for the login: " + users.get(0));
		loginPage.setPassword("");
		System.out.println("The following Password was used for the login: " + "empty field, no password was set");
		loginPage.clickLoginBtn();

		// check expected results
		Assert.assertTrue(loginPage.isErrorMsgAppeared());
		Assert.assertTrue(loginPage.isErrorCircleAppearedForFields());
		Assert.assertEquals(loginPage.getTextErrorMsg(), "Epic sadface: Password is required");

	}

	@Epic("SauceDemo Application")
	@Feature("Login Page")
	@Test(priority = 2, description = "Check page title")
	@Description("Test-case will check that the valid page title for Login Page is presented on the screen")
	public void loginToApplication_checkPageTitle() {

		Assert.assertEquals(loginPage.getPageName(), "Swag Labs");

	}
	
	@Epic("SauceDemo Application")
	@Feature("Login Page")
	@Test(priority = 0, description = "Login to application for the lockedOut user")
	@Description("Test-case will check that it is NOT possible to login to application, if user is blocked.")
	public void loginToApplication_LockedOutUser() {

		// preconditions: get test-data
		List<String> users = loginPage.getTestUsers();
		String password = loginPage.getTestPassword();

		// login to application
		loginPage.setUsername(users.get(1));
		System.out.println("The following User was used for the login: " + users.get(1));
		loginPage.setPassword(password);
		System.out.println("The following Password was used for the login: " + password);
		loginPage.clickLoginBtn();

		// check expected results
		Assert.assertTrue(loginPage.isErrorMsgAppeared());
		Assert.assertTrue(loginPage.isErrorCircleAppearedForFields());
		Assert.assertEquals(loginPage.getTextErrorMsg(), "Epic sadface: Sorry, this user has been locked out.");

	}
	
	@Epic("SauceDemo Application")
	@Feature("Login Page")
	@Test(priority = 2, description = "Login to application for the user, who has a problems in account")
	@Description("Test-case will check that it is still possible to login to application, if there are problems with Product page.")
	public void loginToApplication_ProblemtUser() {

		// preconditions: get test-data
		List<String> users = loginPage.getTestUsers();
		String password = loginPage.getTestPassword();

		// login to application
		loginPage.setUsername(users.get(2));
		System.out.println("The following User was used for the login: " + users.get(2));
		loginPage.setPassword(password);
		System.out.println("The following Password was used for the login: " + password);
		ProductsPage productsPage = loginPage.clickLoginBtn();

		// check expected results
		Assert.assertEquals(productsPage.getPageTitle(), "Products");

	}
	
	
	@Epic("SauceDemo Application")
	@Feature("Login Page")
	@Test(priority = 2, description = "Login to application for the PerfomanceGlitch user")
	@Description("Test-case will check that it is still possible to login to application, if there are problems with performance.")
	public void loginToApplication_PerfomanceGlitchUser() {

		// preconditions: get test-data
		List<String> users = loginPage.getTestUsers();
		String password = loginPage.getTestPassword();

		// login to application
		loginPage.setUsername(users.get(3));
		System.out.println("The following User was used for the login: " + users.get(3));
		loginPage.setPassword(password);
		System.out.println("The following Password was used for the login: " + password);
		ProductsPage productsPage = loginPage.clickLoginBtn();

		// check expected results
		Assert.assertEquals(productsPage.getPageTitle(), "Products");

	}
	
	@Epic("SauceDemo Application")
	@Feature("Login Page")
	@Test(priority = 1, description = "Login to application for the error user")
	@Description("Test-case will check that it is still possible to login to application, if user account has errors.")
	public void loginToApplication_ErrorUser() {

		// preconditions: get test-data
		List<String> users = loginPage.getTestUsers();
		String password = loginPage.getTestPassword();

		// login to application
		loginPage.setUsername(users.get(4));
		System.out.println("The following User was used for the login: " + users.get(4));
		loginPage.setPassword(password);
		System.out.println("The following Password was used for the login: " + password);
		ProductsPage productsPage = loginPage.clickLoginBtn();

		// check expected results
		Assert.assertEquals(productsPage.getPageTitle(), "Products");

	}
	
	@Epic("SauceDemo Application")
	@Feature("Login Page")
	@Test(priority = 2, description = "Login to application for the user, who has visual problems on the pages of application")
	@Description("Test-case will check that it is still possible to login to application, if user account has a problems with visual part of pages from application.")
	public void loginToApplication_VisualUser() {

		// preconditions: get test-data
		List<String> users = loginPage.getTestUsers();
		String password = loginPage.getTestPassword();

		// login to application
		loginPage.setUsername(users.get(5));
		System.out.println("The following User was used for the login: " + users.get(5));
		loginPage.setPassword(password);
		System.out.println("The following Password was used for the login: " + password);
		ProductsPage productsPage = loginPage.clickLoginBtn();

		// check expected results
		Assert.assertEquals(productsPage.getPageTitle(), "Products");

	}

	@Epic("SauceDemo Application")
	@Feature("Login Page")
	@Test(priority = 2, description = "Login to application with pressing of Enter btn instead of Login Btn click")
	@Description("Test-case will check that it is possibletologinto applicatin if user will click 'ENTER' instead of LoginBtn.")

	public void loginWithEnterInsteadOfLoginBtn() {

		// preconditions: get test-data
				List<String> users = loginPage.getTestUsers();
				String password = loginPage.getTestPassword();

				// login to application
				loginPage.setUsername(users.get(0));
				System.out.println("The following User was used for the login: " + users.get(0));
				loginPage.setPassword(password);
				System.out.println("The following Password was used for the login: " + password);
		
				ProductsPage productsPage = loginPage.clickENTERToLogin();
				// check expected results
				Assert.assertEquals(productsPage.getPageTitle(), "Products");
	
	}
}

