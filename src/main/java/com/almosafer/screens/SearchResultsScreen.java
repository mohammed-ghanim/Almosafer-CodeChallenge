package com.almosafer.screens;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.almosafer.base.Base;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SearchResultsScreen extends Base{
	
	public SearchResultsScreen() {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	
	@AndroidFindBy (xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.travel.almosafer:id/rvFlightResult\"]/androidx.cardview.widget.CardView[2]")
	private WebElement resultsView;
	
	@AndroidFindBy (id = "com.travel.almosafer:id/tvFlightToolbarSubTitle")
	private WebElement datesHeader;
	
	@AndroidFindBy (xpath = "//android.widget.Button[@content-desc=\"SortOption\"]")
	private WebElement sortOption;
	
	@AndroidFindBy (xpath = "//android.widget.TextView[@resource-id=\"com.travel.almosafer:id/checkRowTitle\" and @text=\"Lowest price\"]")
	private WebElement lowestPriceOption;
	
	@AndroidFindBy (xpath = "//android.widget.TextView[@resource-id=\"com.travel.almosafer:id/tvTag\"")
	private List<WebElement> resultCards;
	
	@AndroidFindBy (xpath = "//android.widget.TextView[@resource-id=\"com.travel.almosafer:id/tvTag\" and @text=\"Cheapest\"]")
	private WebElement cheapestTag;
	
	@AndroidFindBy (xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.travel.almosafer:id/rvFlightResult\"]/androidx.cardview.widget.CardView[3]")
	private WebElement firstCard;
	
	@AndroidFindBy (id = "com.travel.almosafer:id/tvFinalPrice")
	private WebElement cheapestPrice;
	
	@AndroidFindBy (xpath = "//android.widget.Button[@content-desc=\"Filters\"]")
	private WebElement filtersBtn;
	
	@AndroidFindBy (xpath = "//android.widget.TextView[@text=\"Filters\"]")
	private WebElement filtersHeader;
	
	@AndroidFindBy (className = "android.widget.EditText")
	private WebElement minValuePrice;
	
	@AndroidFindBy (xpath = "//android.widget.TextView[@text=\"Cheapest\"]")
	private WebElement tagsBar;
	
	
	public void waitResults() {
	//	Wait <AndroidDriver> wait = new FluentWait<AndroidDriver>(driver)
				//.pollingEvery(Duration.ofSeconds(1))
				//.withTimeout(Duration.ofSeconds(20))
				//.ignoring(NoSuchElementException.class);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//androidx.cardview.widget.CardView")));
	}
	
	public void assertDates(String selectedDatesString) {
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(datesHeader.getAttribute("text"), "4 Travellers - " + selectedDatesString);
		soft.assertAll();
	}
	
	public void sortByCheapest() {
		sortOption.click();
		lowestPriceOption.click();
	}
	
	public void checkCheapestTag() {

		System.out.println(tagsBar.getAttribute("text"));
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(tagsBar.getAttribute("text"), "Cheapest");
		soft.assertAll();
		//WebElement firstCard = resultCards.get(1).findElement(By.className("android.widget.LinearLayout"));
		//System.out.println(firstCard);
	}
	
	public String getCheapestPrice() {
		return cheapestPrice.getAttribute("text");
	}
	
	public void checkMinPriceFilter() {
		filtersBtn.click();
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(filtersHeader.getAttribute("text"), "Filters");
		 String scrollableUiSelector = 
				 "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollForward().scrollIntoView(new UiSelector().textContains(\"Price range\").instance(0))";
		driver.findElement(AppiumBy.androidUIAutomator(scrollableUiSelector));
		soft.assertAll();
	}
	
	public void checkPrice(String cheapestPrice) {
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(minValuePrice.getAttribute("text").replace("Min ", ""), cheapestPrice);
		soft.assertAll();
		
	}
}
