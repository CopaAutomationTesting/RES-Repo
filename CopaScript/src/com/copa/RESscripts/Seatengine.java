package com.copa.RESscripts;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.Util.FlightSearchPageObjects;

import FrameworkCode.BFrameworkQueryObjects;

public class Seatengine extends FlightSearchPageObjects{
	
	public void seatengincase(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		
		boolean retval=Unwholly.getpnrinexcel(driver, queryObjects);
		FlightSearch engin=new FlightSearch();
		
		if (retval) {
			// Assign seat...............
			String sAssignseatAllPAX=FlightSearch.getTrimTdata(queryObjects.getTestData("AssignseatAllPAX"));
			String assignSeatSinglePAX = FlightSearch.getTrimTdata(queryObjects.getTestData("AssignSeatSinglePAX"));
			String assignSpecSeat = FlightSearch.getTrimTdata(queryObjects.getTestData("AssignSpecSeat"));
			String paymentSeat = FlightSearch.getTrimTdata(queryObjects.getTestData("paymentSeat"));
			if ((sAssignseatAllPAX.equalsIgnoreCase("YES") || !assignSeatSinglePAX.equals("") || !assignSpecSeat.equals(""))){
				queryObjects.logStatus(driver, Status.PASS,"Assign Seat process started","Assign seat process startd",null);
				int totalPax = Integer.parseInt(driver.findElement(By.xpath(PaxCountXpath)).getText().trim());
				FlightSearch.assignSeat(driver,queryObjects,totalPax, sAssignseatAllPAX, assignSeatSinglePAX, assignSpecSeat);
				if(paymentSeat.equalsIgnoreCase("yes"))
					engin.paymentAfterassignseat(driver, queryObjects);
				String Re_Assign=FlightSearch.getTrimTdata(queryObjects.getTestData("Re_Assigneseat"));
				if (Re_Assign.equalsIgnoreCase("yes")) {
					FlightSearch.Re_Assignseat=true;
					FlightSearch.assignSeat(driver,queryObjects,totalPax, sAssignseatAllPAX, assignSeatSinglePAX, assignSpecSeat);
					FlightSearch.Re_Assignseat=false;	
				}
				Delete_Seat(driver, queryObjects);
				
			}
		}
		
		
		
	}

	public void Delete_Seat(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException{
		try{
			
			WebDriverWait wait = new WebDriverWait(driver, 300);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[div[text()='Services']]")));
			driver.findElement(By.xpath("//div[div[text()='Services']]")).click();
			FlightSearch.loadhandling(driver);
			//Selecting "All Segments" check box
			//WebDriverWait wait = new WebDriverWait(driver, 300);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//md-checkbox[div[span[text()='All Segments']]]")));

			String cheboxstateAllSegment=driver.findElement(By.xpath("//md-checkbox[div[span[text()='All Segments']]]")).getAttribute("aria-checked");
			String cheboxstateAllpax=driver.findElement(By.xpath("//md-checkbox[div[span[text()='All Segments']]]")).getAttribute("aria-checked");
			if (Boolean.parseBoolean(cheboxstateAllSegment)==false)
				driver.findElement(By.xpath("//md-checkbox[div[span[text()='All Segments']]]")).click();

			//Selecting All Passengers
			if (Boolean.parseBoolean(cheboxstateAllpax)==false)
				driver.findElement(By.xpath("//md-checkbox[div[span[text()='All Passengers']]]")).click();
			Thread.sleep(1000);

			//Selecting Assign Seat icon
			driver.findElement(By.xpath("//button[@class='seat-link']")).click();
			FlightSearch.loadhandling(driver);
			queryObjects.logStatus(driver, Status.PASS,"Delete seat process started","Delete seat process started",null);
			List<WebElement> TotalFlight=driver.findElements(By.xpath("//md-content[contains(@class,'segment-box')]/div[contains(@class,'seatmap-segmen')]/div"));
			int size=TotalFlight.size();
			for(int allflights=1;allflights<=size;allflights++)
			{
				if(allflights>=2)
				{
					driver.findElement(By.xpath("//md-content[contains(@class,'segment-box')]/div[contains(@class,'seatmap-segmen')]/div["+allflights+"]//child::tbody/tr[@ng-repeat='leg in flight']/td[1]/md-checkbox/div")).click();
					FlightSearch.loadhandling(driver);
				}
			
				List<WebElement> deleteseat=driver.findElements(By.xpath("//i[contains(@class,'icon-removed')]"));
				for (int i = 0; i < deleteseat.size(); i++) {
					deleteseat.get(i).click();
					FlightSearch.loadhandling(driver);	
				}
			}
			
			driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
			FlightSearch.loadhandling(driver);
			boolean deletseatcheking=false;
			List<WebElement> seatsAssigned = driver.findElements(By.xpath("//div[contains(@ng-repeat,'seat in serviceIcon')]//span[@ng-if='seat.number']"));
			if (seatsAssigned.size()>0)
				queryObjects.logStatus(driver, Status.FAIL,"Deleteing seat ","Un able to Delete seats",null);
			else
				queryObjects.logStatus(driver, Status.PASS,"Deleteing seat ","Delete seats successfully",null);
		}
		catch(Exception e){
			queryObjects.logStatus(driver, Status.FAIL,"Deleteing seat ","Un able to Delete seats ",e);
		}
	}
}
