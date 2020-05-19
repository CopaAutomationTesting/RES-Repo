package com.copa.RESscripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

public class headless_tesing_Demo {
	
	public static void PageLoad(WebDriver driver) throws InterruptedException{
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 300);
			wait = new WebDriverWait(driver, 300);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("popup locator")));
			Thread.sleep(1000);
			//System.out.println("Page Load Successful");
		}
		catch(Exception e) {
			Thread.sleep(1000);
			System.out.println("exception :: 5 Second");
		}		

	}
	
	public static void main(String[] args) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		System.setProperty("webdriver.chrome.driver", "C:\\MSmart\\Driver\\Chromedriver.exe");
		ChromeOptions options=new ChromeOptions();
		options.addArguments("window-size=1400,800");
		options.addArguments("headless");
		WebDriver driver=new ChromeDriver(options);
	
	
		
	
	try {
		
		// Wait Till the COPA Application is Loaded Completely
		
		//driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		driver.get("http://pssguicmmb.airservices.svcs.entsvcs.com");
		
		// Maximize the Chrome Window
		driver.manage().window().maximize();
		//PageLoad(driver);
		
		// Perform Login into the COPA Application
		driver.findElement(By.name("USER")).sendKeys("cm.pty.agent");	
		driver.findElement(By.name("PASSWORD")).sendKeys("Pss@test17");		
		driver.findElement(By.name("submit")).click();
		PageLoad(driver);
		
		//Click on the CSS Link and wait for the Application to Load
		driver.findElement(By.xpath("//a[contains(text(),'css')]")).click();
		
		System.out.println("Login Successful");
	
	}		
	catch (Exception e) {
		System.out.println("Log - Fail - Login into the COPA Application Failed");
		System.out.println("Log - Fail - Exit Script");
	}
	
	try {	
	
		// Change Sale office
		PageLoad(driver);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("/html/body/div/div/div[1]/div[3]/div[1]/div/div[1]/i")).click();
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div/div[1]/md-input-container/md-select/md-select-value/span[2]")).click();		
		Thread.sleep(1000);
		driver.findElement(By.xpath("//md-option[div[@class='md-text ng-binding' and contains(text(),'PTY - ATO')]]")).click();
		
		// Change Currency
		driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div/div[2]/md-input-container")).click();//
		Thread.sleep(1000);
		driver.findElement(By.xpath("//md-option[contains(@ng-value,'currency')][div[@class='md-text ng-binding' and contains(text(),'USD')]]")).click();
		
	//	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div[2]/button[contains(text(),'OK')]")).click();
		PageLoad(driver);
		
		System.out.println("Salesoffice change Successfully");
	}
	catch (Exception e) {
		System.out.println("Log - Fail - Unable to Change the Sale Office and Currency:");
		System.out.println("Log - Fail - Exit Script");
	}

		
	try {
		// Click on Reservation
		
		driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button[contains(text(),'Reservations')]")).click();
		Thread.sleep(1000);
				
		driver.findElement(By.xpath("//md-menu-content/md-menu-item/button[contains(text(),'Check-In')]")).click();
		Thread.sleep(2000);
		PageLoad(driver);
		
		System.out.println("Check - in Page Loaded Successfully");
		
	}
	catch (Exception e) {
		System.out.println("Log - Fail - Unable to Click on the Check - In Drop Down Menu :");
		System.out.println("Log - Fail - Exit Script");
	}
		
		
		try {
				String Flight = "371";
			//	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//form[@name = 'flightSearch.form']//input[@name ='Flight']")).sendKeys(Flight);	
				Thread.sleep(1000);
				
				WebElement From = driver.findElement(By.xpath("//form[@name = 'flightSearch.form']//input[@name ='origin']"));
				From.clear();
				From.sendKeys("PTY");
				From.sendKeys(Keys.ENTER);
				Thread.sleep(1000);
				
				WebElement Day = driver.findElement(By.xpath("//form[@name = 'flightSearch.form']//input[@class='md-datepicker-input']"));
				Day.clear();
				Day.sendKeys("3/15/2020");
				Thread.sleep(1000);
				
			//	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//form[@name = 'flightSearch.form']//button[@aria-label ='Search']")).click();
				
				PageLoad(driver);
				//if (driver.findElement(By.xpath("//div[@class=\"error-panel inset ng-scope layout-column\"]//span"))!=null) {
					
				//	System.out.println("Log - Pass - The Specified Flight "+ Flight +" is Not Open");
				//}
				//else {
				//	System.out.println("Log - Pass - The Specified Flight "+ Flight +" is Open");
						
				//}
				
				if(driver.getPageSource().contains("The specified flight is not open")){
					System.out.println("Log - Pass - The Specified Flight "+ Flight +" is not Open");
					}else{
						System.out.println("Log - Pass - The Specified Flight "+ Flight +" is Open");
					}
		}
		catch (Exception e) {
			System.out.println("Log - Fail - Not Able to Perform Flight Serch Operation in Check-In Page :");
			System.out.println("Log - Fail - Exit Script");
		}

		System.out.println("Script Successfully Executed");
		
		driver.close();		
		 long endTime = System.currentTimeMillis();
		  long duration = (endTime - startTime);
		  
		  System.out.println("Execution Total time ");
		  System.out.println(duration);
	}
}
