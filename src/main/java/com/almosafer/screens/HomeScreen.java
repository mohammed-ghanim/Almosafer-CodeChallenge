package com.almosafer.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.almosafer.base.Base;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomeScreen extends Base {
	
	public HomeScreen() {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy (id = "com.travel.almosafer:id/tvUserTitle")
	private WebElement homeTitle;
	
	@AndroidFindBy (xpath = "(//android.widget.ImageView[@resource-id=\"com.travel.almosafer:id/ivProductIcon\"])[1]")
	private WebElement flightBtn;
	
	public void checkTitle() {
		SoftAssert soft = new SoftAssert();
		String title = homeTitle.getText();
		soft.assertEquals(title, "Hi there!");
	}
	
	public void clickFlights() {
		flightBtn.click();
	}

}