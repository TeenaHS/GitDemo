package com;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.TestComponents.BaseTest;
import com.TestComponents.Retry;

import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.ProductCatalog;

public class ErrorValidationTest extends BaseTest {

	@Test(groups = { "ErrorHandling" }, retryAnalyzer = Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException {

		landingPage.loginApplication("tina23@gmail.com", "#Tina123");
		Assert.assertEquals(landingPage.getErrorMsg(), "Incorrect email or password.");
	}

	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException {

		String productName = "ZARA COAT 3";

		ProductCatalog productCatalog = landingPage.loginApplication("tina123@gmail.com", "#Tina123");

		List<WebElement> products = productCatalog.getProductList();
		productCatalog.addProductToCart(productName);
		CartPage cartPage = productCatalog.goToCart();

		// Match with selected product - ZARA COAT 3
		Boolean match = cartPage.matchProduct("ZARA COAT 33");
		Assert.assertFalse(match);

	}

}
