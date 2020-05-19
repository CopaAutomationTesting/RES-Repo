package com.copa.ATOscripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.RESscripts.FlightSearch;

import FrameworkCode.BFrameworkQueryObjects;

public class ReservationPageValidation  {
	
	//////suman// to validate the compensation changes on reservation page/////
	
	public static void EmailupdateValidationOnResPage(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException
	{
		try
		{
			
			WebDriverWait wait = new WebDriverWait(driver, 50);
			driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
			 Thread.sleep(2000);
			 driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Reservations')]")).click();
			 FlightSearch.loadhandling(driver);
			//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //30
			//driver.findElement(By.xpath("//div[text()='New Order']")).click();
			
			driver.findElement(By.xpath("//span[text()='Home']")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//div[div[text()='Search'] and @role='button']")).click();
			Thread.sleep(2000);
			//Entering PNR number in search input
			driver.findElement(By.xpath("//input[@aria-label='Search']")).click();
			driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(Compensation.PnrForEmailValidation);

			//Clicking on Search button
			driver.findElement(By.xpath("//div[contains(@class,'itinerary-search')]//button[contains(text(),'Search')]")).click();
			FlightSearch.loadhandling(driver);
			String Pnris=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@action='pnr']/div/div/div[1]/div[1]")).getText());
			//Wait until loading wrapper closed
			
			if(Pnris.equalsIgnoreCase(Compensation.PnrForEmailValidation))
			{
				queryObjects.logStatus(driver, Status.PASS, "Search PNR", "Search PNR successfully" , null);
			}
			else
			{
				queryObjects.logStatus(driver, Status.FAIL, "Search PNR", "Search  PNR NOT successfully" , null);
			}
			
			//validating email which updated in Compenesation
			
			List<WebElement> email=driver.findElements(By.xpath("//form[contains(@class,'remark-form')]//div[@class='remark-box']//div[contains(@ng-if,'arrayList.action')]//following-sibling::div[@role='button']"));
			List<String> emailString=new ArrayList<String>();
			email.forEach(a ->emailString.add(a.getText().trim()));
			
			if(emailString.contains("TEST@AUTOMATION.COM"))
			{
				queryObjects.logStatus(driver, Status.PASS, "Checking email update ", "Email update successfully FOR PNR"+Compensation.PnrForEmailValidation , null);
				
			}
			else
			{
				queryObjects.logStatus(driver, Status.FAIL, "Checking email update", "email not updated successfully FOR PNR"+Compensation.PnrForEmailValidation , null);	
			}
			
			
			
		}
		catch(Exception e)
		{
			
			queryObjects.logStatus(driver, Status.FAIL, "Search PNR", "Search PNR successfully" , e);
		}
		
	}
	

}



