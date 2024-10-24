package com;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {

		String item = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");

		// login in to the page

		driver.findElement(By.id("userEmail")).sendKeys("tina123@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("#Tina123");
		driver.findElement(By.id("login")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

		// list of products
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

		// select the product - ZARA COAT 3
		WebElement prod = products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).findFirst()
				.orElse(null);

		// Add to cart button
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

		// product added to cart message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		// ng-animating
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

		// cart button
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

		// product names from cart
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));

		// Match with selected product - ZARA COAT 3
		Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(item));
		Assert.assertTrue(match);

		// check out button
		driver.findElement(By.cssSelector(".totalRow button")).click();

//		driver.findElement(By.cssSelector("input[placeholder='Select Country']")).sendKeys("ind");

		Actions a = new Actions(driver);

		// type country
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();

		// get list option
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-item")));

		// select option
		driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();

		// submit
		driver.findElement(By.className("action__submit")).click();

		// get thankypu msg
		String confirmMsg = driver.findElement(By.className("hero-primary")).getText();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));

		driver.close();

	}

}
