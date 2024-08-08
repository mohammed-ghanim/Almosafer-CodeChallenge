package com.almosafer.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Base {
protected static AndroidDriver driver;
protected FileInputStream inputStream;
protected Properties prop;
public static ExtentReports extent;
public static ExtentTest logger;

	@BeforeSuite
	public void beforeSuite() {
		extent = new ExtentReports("Reports/index.html");
		extent.addSystemInfo("Framework Type", "Appium Page Object");
		extent.addSystemInfo("Author", "Mohammed Ghanim");
		extent.addSystemInfo("Environment", "Production");
		extent.addSystemInfo("App", "Almosafer: Hotels & Flights");
	}
	
	@AfterSuite
	public void afterSuite() {
		extent.flush();
	}
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		logger = extent.startTest(method.getName());
	}
	
	
	@AfterMethod
	public void afterMethod(Method method, ITestResult result) {
		
		File image = driver.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(image, new File("Snapshots/" + method.getName() + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String imageFullPath = System.getProperty("user.dir") + File.separator + "Snapshots/" + method.getName() + ".jpg";
		if(result.getStatus() == ITestResult.SUCCESS) {
			logger.log(LogStatus.PASS, method.getName() + "The test has Passed!");
		}
		else if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test is Failed");
			logger.log(LogStatus.FAIL, result.getThrowable());
			logger.log(LogStatus.FAIL, logger.addScreenCapture(imageFullPath));
	}
		else {
			logger.log(LogStatus.SKIP, method.getName() + "Test is skipped");
		}
	}
	
	@BeforeClass
	@Parameters({"deviceName", "platformName", "platformVersion"})
	public void beforeClass(String deviceName, String platformName, String platformVersion) throws IOException {
	
		File propFile = new File("C:\\Users\\LP\\eclipse-workspace\\AlmosaferCodeChallenge\\src\\main\\resources\\config\\config.properties");
		FileInputStream inputStream = new FileInputStream(propFile);
		prop = new Properties();
		
		prop.load(inputStream);
		
		if(platformName.equalsIgnoreCase("Android")) {
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("platformName", platformName);
			caps.setCapability("deviceName", deviceName);
			caps.setCapability("automationName", prop.getProperty("androidAutomationName"));
			caps.setCapability("ignoreHiddenApiPolicyError", true);
			caps.setCapability("acceptInsecureCerts", true);
			caps.setCapability("platformVersion", platformVersion);
			caps.setCapability(UiAutomator2Options.APP_ACTIVITY_OPTION, "com.travel.splash_ui_private.di.splash.SplashActivity");
			caps.setCapability(UiAutomator2Options.APP_PACKAGE_OPTION, "com.travel.almosafer");
			//caps.setCapability(UiAutomator2Options.APP_OPTION, "C:\\Users\\LP\\eclipse-workspace\\qacartappiumcourse\\app\\almosafer-hotels-and-flights-7-57-1.apk");
			driver = new AndroidDriver(new URL(prop.getProperty("appiumServerLink")), caps);
		} else if (platformName.equalsIgnoreCase("ios")) {
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("platformName", platformName);
			caps.setCapability("deviceName", deviceName);
			caps.setCapability("automationName", prop.getProperty("iosAutomationName"));
			caps.setCapability("ignoreHiddenApiPolicyError", true);
			caps.setCapability("acceptInsecureCerts", true);
			caps.setCapability("platformVersion", platformVersion);
			caps.setCapability(UiAutomator2Options.APP_ACTIVITY_OPTION, "com.travel.splash_ui_private.di.splash.SplashActivity");
			caps.setCapability(UiAutomator2Options.APP_PACKAGE_OPTION, "com.travel.almosafer");
			//caps.setCapability(UiAutomator2Options.APP_OPTION, "C:\\Users\\LP\\eclipse-workspace\\qacartappiumcourse\\app\\almosafer-hotels-and-flights-7-57-1.apk");
			driver = new AndroidDriver(new URL(prop.getProperty("appiumServerLink")), caps);
		}
		
		// I added this to check if the elements needs time to appear (10 seconds for each element before it fails)
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
