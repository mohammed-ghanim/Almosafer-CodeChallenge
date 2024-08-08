package com.almosafer.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.almosafer.base.Base;
import com.almosafer.screens.HomeScreen;
import com.almosafer.screens.SearchFlightsScreen;
import com.almosafer.screens.SearchResultsScreen;
import com.almosafer.screens.WelcomeScreen;

public class MainScenarioTest extends Base{

	
	@Test
	public void testCase() {
		
		WelcomeScreen wlcm = new WelcomeScreen();
		HomeScreen home = new HomeScreen();
		SearchFlightsScreen search = new SearchFlightsScreen();
		SearchResultsScreen results = new SearchResultsScreen();
		
		String[] fromArray = {"DXB", "AUH", "SHJ", "JED", "RUH"};
		String[] toArray = {"AMM", "CAI", "DEL", "KHI", "PAR"};
		
		String randomOrigin = search.randomCountry(fromArray);
		String randomDestination = search.randomCountry(toArray);
		
		wlcm.checkEnglishOptionandContinue();
		home.checkTitle();
		home.clickFlights();
		search.checkTitle();
		search.checkRoundTab();
		
		search.makeCountriesSelection(randomOrigin, randomDestination);
		search.makeDatesSelection();
		search.makePassengersSelection();
		search.searchFlights();
		results.waitResults();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.travel.almosafer:id/rvFlightResult\"]/androidx.cardview.widget.CardView[2]/android.widget.LinearLayout")));
		results.assertDates(search.selectedDatesString);
		results.sortByCheapest();
		results.checkCheapestTag();
		String cheapestPrice = results.getCheapestPrice();
		results.checkMinPriceFilter();
		results.checkPrice(cheapestPrice);
		}

}