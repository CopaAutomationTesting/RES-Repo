package com.copa.ATOscripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.copa.RESscripts.FlightSearch;

import FrameworkCode.BFrameworkQueryObjects;

public class ValidateKTN {
	
	
	public static void ktn(WebDriver driver, BFrameworkQueryObjects queryObjects) throws InterruptedException, IOException
	{
		driver.findElement(By.xpath("//div[div[text()='Services']]")).click();
		FlightSearch.loadhandling(driver);

		//unchecking the passengers  selected
		WebElement allPaxChkBox = driver.findElement(By.xpath("//md-checkbox[@aria-label='All Passengers']"));
		if(allPaxChkBox.getAttribute("aria-checked").equalsIgnoreCase("false")) {
			allPaxChkBox.click();
			Thread.sleep(1000);
			allPaxChkBox.click();
			Thread.sleep(1000);
		}
		else {
			allPaxChkBox.click();
		}
		String PAX=driver.findElement(By.xpath("//label[contains(text(),'Passengers')]//following-sibling::span")).getText();
		int totalnoofPAX=Integer.parseInt(PAX);
		for(int ipax = 1; ipax<=totalnoofPAX; ipax++) {
			//Expanding Passenger details grid
			try {
				driver.findElement(By.xpath("//div[contains(@class,'passenger-list')]/div["+ipax+"]//i[contains(@class,'icon-forward')]")).click();
				Thread.sleep(1000);
			}
			catch(Exception e) {}
		}
		
	
		List<WebElement> ssr=driver.findElements(By.xpath("//div[contains(@class,'passenger-list')]/div//div[contains(@ng-repeat,'serviceInfos')]//div[@class='services-list']//div[contains(@class,'segment-services')][1]"));
        List<String> ssrstring=new ArrayList<String>();
        ssr.forEach(a->ssrstring.add(a.getText().trim()));
		
		
		if(ssrstring.contains("DOCO"))
		{
			queryObjects.logStatus(driver, Status.PASS, "Passenger KnownTravelerNumber verification in Reservation before checkin", "KnownTravelerNumber displayed is correct", null);	
		}
		else
		{
			queryObjects.logStatus(driver, Status.FAIL, "Passenger KnownTravelerNumber verification in Reservation before checkin", "KnownTravelerNumber displayed is NOT correct", null);	
		}
		
		driver.findElement(By.xpath("//div[contains(@class,'passenger-list')]/div//div[contains(@ng-repeat,'serviceInfos')]//div[contains(text(),'OTHER PASSENGER INFO')]")).click();
		String otherinfo=driver.findElement(By.xpath("//input[@placeholder='Explanations']")).getAttribute("value");
		
		queryObjects.logStatus(driver, Status.PASS, "displaying other info os ktn ssr", otherinfo, null);	
		
		driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Back')]")).click();
	}
}
