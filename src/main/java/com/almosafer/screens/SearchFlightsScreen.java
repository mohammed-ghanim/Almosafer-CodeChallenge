package com.almosafer.screens;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.almosafer.base.Base;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SearchFlightsScreen extends Base {
	
	public String selectedDatesString;
	public SearchFlightsScreen() {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy (xpath = "//android.widget.TextView[@text=\"Search flights\"]")
	private WebElement flightsTitle;
	
	@AndroidFindBy (xpath = "//android.widget.TextView[@text=\"Round-trip\"]")
	private WebElement roundTab;
	
	@AndroidFindBy (xpath = "//androidx.cardview.widget.CardView[@resource-id=\"com.travel.almosafer:id/originView\"]/android.view.ViewGroup")
	private WebElement fromInput;
	
	@AndroidFindBy (id = "com.travel.almosafer:id/edSearch")
	private WebElement fromSearch;
	
	@AndroidFindBy (xpath = "//androidx.cardview.widget.CardView[@resource-id=\"com.travel.almosafer:id/originView\"]/android.view.ViewGroup")
	private WebElement toInput;
	
	@AndroidFindBy (id = "com.travel.almosafer:id/edSearch")
	private WebElement toSearch;
	
	@AndroidFindBy (xpath = "//android.widget.TextView[@text='Search origin']")
	private WebElement searchOriginHeader;
	
	@AndroidFindBy (xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.travel.almosafer:id/rvSearchAirports\"]/android.view.ViewGroup[1]")
	private WebElement countryResult;
	
	@AndroidFindBy (xpath = "//androidx.cardview.widget.CardView[@resource-id=\"com.travel.almosafer:id/checkIn\"]")
	private WebElement departureDate;
	
	@AndroidFindBy (xpath = "(//wm.d[@content-desc=\"month_view\"])[2]")
	private WebElement futureMonth;
	
	@AndroidFindBy (id = "com.travel.almosafer:id/datesSelected")
	private WebElement selectedDates;
	
	@AndroidFindBy (id = "com.travel.almosafer:id/confirmBtn")
	private WebElement confirmBtn;
	
	@AndroidFindBy (accessibility = "paxAndClass")
	private WebElement passAndClass;
	
	@AndroidFindBy (xpath = "//android.widget.TextView[@text=\"Passengers and cabin class\"]")
	private WebElement passAndClassHeader;
	
	@AndroidFindBy (xpath = "(//android.widget.ImageView[@resource-id=\"com.travel.almosafer:id/stepUp\"])[2]")
	private WebElement stepUpChildren;
	
	@AndroidFindBy (xpath = "(//android.widget.ImageView[@resource-id=\"com.travel.almosafer:id/stepUp\"])[3]")
	private WebElement stepUpInfants;
	
	@AndroidFindBy (id = "com.travel.almosafer:id/rdCabinItem")
	private WebElement capinEconomyRadio;
	
	@AndroidFindBy (id = "com.travel.almosafer:id/btnApply")
	private WebElement applyBtn;
	
	@AndroidFindBy (id = "com.travel.almosafer:id/btnFlightSearch")
	private WebElement flightSearchBtn;
	
	public void checkTitle() {
		SoftAssert soft = new SoftAssert();
		String title = flightsTitle.getText();
		soft.assertEquals(title, "Search flights");
		soft.assertAll();
	}
	
	public void checkRoundTab(){
		SoftAssert soft = new SoftAssert();
		String roundtabstatus = roundTab.getAttribute("selected");
		if (roundtabstatus == "false") {
			roundTab.click();
		}
		soft.assertEquals(roundtabstatus, "true");
		soft.assertAll();
	}
	
	public String randomCountry(String[] countryArray) {
		Random random = new Random();
		int randomIndex = random.nextInt(countryArray.length);
		return countryArray[randomIndex];
		
	}
	
	public void checkHeaderSearchOrigin() {
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(searchOriginHeader.getAttribute("text"), "Search origin");
		soft.assertAll();
	}
	
	public void makeCountriesSelection(String origin, String destination) {
			fromInput.click();			
			fromSearch.click();
			fromSearch.sendKeys(origin);
			checkHeaderSearchOrigin();
			countryResult.click();
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.travel.almosafer:id/edSearch")));
			
			toSearch.click();
			toSearch.sendKeys(destination);
			countryResult.click();
	}
	
	
	public void makeDatesSelection() {
		
		departureDate.click();
		
		driver.findElement(AppiumBy.androidUIAutomator
				("new UiScrollable(new UiSelector()).scrollIntoView(className(\"wm.d\").instance(2));"));
		
		Random random = new Random();
		int randomStartDay = random.nextInt(23);
		int randomEndDay = randomStartDay + 7;
		futureMonth.findElement(By.className("wm.g")).findElement(By.xpath("//android.widget.CheckedTextView[@index = '"+randomStartDay+"']")).click();
		futureMonth.findElement(By.className("wm.g")).findElement(By.xpath("//android.widget.CheckedTextView[@index = '"+randomEndDay+"']")).click();
		selectedDatesString = selectedDates.getAttribute("text");
		confirmBtn.click();
	}
	
	public void makePassengersSelection() {
		passAndClass.click();
		
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(passAndClassHeader.getAttribute("text"), "Passengers and cabin class");
		soft.assertAll();
		stepUpChildren.click();
		stepUpChildren.click();
		stepUpInfants.click();
		capinEconomyRadio.click();
		applyBtn.click();
	}
	
	public void searchFlights() {
		flightSearchBtn.click();
	}
	
}