package com.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.TestComponents.BaseTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckOutPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.OrderHistoryPage;
import rahulshettyacademy.pageobjects.ProductCatalog;

public class StepDefinition extends BaseTest {

	public LandingPage landingPage;
	public ProductCatalog productCatalog;
	public OrderHistoryPage orderHistoryPage;

//	Given I landed on Ecommerce Page
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException {
		landingPage = launchApplication();
	}

//	Given Logged in with username "<name>" and password "<password>"

	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_with_username_and_password(String userName, String password) {
		productCatalog = landingPage.loginApplication(userName, password);
	}

//	 When I add product "<productName>" to Cart

	@When("^I add product (.+) to Cart$")
	public void i_add_product_to_cart(String productName) throws InterruptedException {
		List<WebElement> products = productCatalog.getProductList();
		productCatalog.addProductToCart(productName);
	}

//	And Checkout "<productName>" and submit the order

	@When("^Checkout (.+) and submit the order$")
	public void checkout_and_submit_the_order(String productName) {
		CartPage cartPage = productCatalog.goToCart();

		Boolean match = cartPage.matchProduct(productName);
		Assert.assertTrue(match);

		// check out button
		CheckOutPage checkOutPage = cartPage.goToCheckOut();

		checkOutPage.selectCountry("India");
		orderHistoryPage = checkOutPage.submit();
	}

//	 Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage

	@Then("{string} message is displayed on ConfirmationPage")
	public void message_is_displayed_on_ConfirmationPage(String string) {
		String message = orderHistoryPage.getMessage();
		Assert.assertTrue(message.equalsIgnoreCase(string));
		driver.close();
	}

//	 Then "Incorrect email or password." message is displayed
	@Then("{string} message is displayed")
	public void message_is_displayed(String string) {
		Assert.assertEquals(landingPage.getErrorMsg(), string);
		driver.close();
	}

}