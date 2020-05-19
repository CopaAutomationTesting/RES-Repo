package com.copa.ATOscripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.RESscripts.FlightSearch;
import com.copa.RESscripts.Login;

import FrameworkCode.BFrameworkQueryObjects;

public class SharePnrSearch {
	public static String SharesPNR;
	public void SharePnrDetails(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception
	{
		SharesPNR =FlightSearch.getTrimTdata(queryObjects.getTestData("PNRis"));
		boolean contflag=false;
		if(SharesPNR.equals("")) 
			queryObjects.logStatus(driver, Status.FAIL, "PNR was not exist", "Test data issue" , null);
		else
			contflag=true;	
		if(contflag) 
		{
			try {
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
				driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(SharesPNR);

				//Clicking on Search button
				driver.findElement(By.xpath("//div[contains(@class,'itinerary-search')]//button[contains(text(),'Search')]")).click();
				FlightSearch.loadhandling(driver);
				String Pnris=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@action='pnr']/div/div/div[1]/div[1]")).getText());
				//Wait until loading wrapper closed
				
				String Flightnum=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]/tbody[1]/tr/td[2]/span")).getText());
				
				Login.shareflightnm= Flightnum;
				queryObjects.logStatus(driver, Status.PASS, "Search PNR", "Search PNR successfully" , null);
	
			}
			catch(Exception e) {
				
				queryObjects.logStatus(driver, Status.FAIL, "Search PNR", "Search PNR failed: " + e.getLocalizedMessage() , e);
			}
		}

		
	}

}
