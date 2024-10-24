package com;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v127.input.Input;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.TestComponents.BaseTest;

import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckOutPage;
import rahulshettyacademy.pageobjects.OrderHistoryPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalog;

public class StandAloneTest2 extends BaseTest {
	String productName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void StandAloneTest(HashMap<String, String> input) throws IOException, InterruptedException {

		ProductCatalog productCatalog = landingPage.loginApplication(input.get("userEmail"), input.get("userPassword"));

		List<WebElement> products = productCatalog.getProductList();
		productCatalog.addProductToCart(input.get("productName"));
		CartPage cartPage = productCatalog.goToCart();
		Thread.sleep(3000);
		// Match with selected product - ZARA COAT 3
		Boolean match = cartPage.matchProduct(input.get("productName"));
		Assert.assertTrue(match);

		// check out button
		CheckOutPage checkOutPage = cartPage.goToCheckOut();

		checkOutPage.selectCountry("India");
		OrderHistoryPage orderHistoryPage = checkOutPage.submit();

		String message = orderHistoryPage.getMessage();
		Assert.assertTrue(message.equalsIgnoreCase("Thankyou for the order."));

	}

	// to test if ZARA COAT 3 is displaying in orders page
	@Test(dependsOnMethods = "StandAloneTest")
	public void OrderHistoryTest() {
		ProductCatalog productCatalog = landingPage.loginApplication("tina123@gmail.com", "#Tina123");
		OrderPage orderPage = productCatalog.goToOrder();
		Assert.assertTrue(orderPage.matchProduct(productName));
	}

//	@DataProvider
//	public Object[][] getData() {
//		return new Object[][] { { "tina123@gmail.com", "#Tina123", "ZARA COAT 3" },
//				{ "shetty@gmail.com", "Iamking@000", "ADIDAS ORIGINAL" } };
//	}

	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\com\\data\\PurchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

//	@DataProvider
//	public Object[][] getData() throws IOException {
//
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("userEmail", "tina123@gmail.com");
//		map.put("userPassword", "#Tina123");
//		map.put("productName", "ZARA COAT 3");
//
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("userEmail", "shetty@gmail.com");
//		map1.put("userPassword", "Iamking@000");
//		map1.put("productName", "ADIDAS ORIGINAL");
//
//		return new Object[][] { { map }, { map1 } };

}
