package com.almosafer.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.almosafer.base.Base;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class WelcomeScreen extends Base {
	
	public WelcomeScreen() {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy (id = "com.travel.almosafer:id/title")
	private WebElement welcometitle;
	
	@AndroidFindBy (xpath = "//android.widget.Button[@index='3']")
	private WebElement englishOption;
	
	@AndroidFindBy (id = "com.travel.almosafer:id/ctaButton")
	private WebElement continueBtn;
	
	public void checkEnglishOptionandContinue() {
		SoftAssert soft = new SoftAssert();
		
		soft.assertEquals(welcometitle.getAttribute("text"), "Welcome traveller!");
		if (englishOption.getAttribute("checked") == "false") {
			englishOption.click();
		}
		
		continueBtn.click();
	}

}