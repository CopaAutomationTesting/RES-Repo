package com.copa.ATOscripts;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Date;
import org.apache.bcel.generic.FDIV;
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.ATOscripts.Atoflow;
import com.copa.RESscripts.FlightSearch;
import com.copa.RESscripts.Login;
import com.copa.Util.*;

import FrameworkCode.BFrameworkQueryObjects;


public class FlightCheckin extends ATOflowPageObjects {
	
	public void flightCheckin(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception{
		
	
		try {
			driver.findElement(By.xpath(ReservationXpath)).click(); 
			FlightSearch.loadhandling(driver);
			 driver.findElement(By.xpath(CheckInXpath)).click();
			 FlightSearch.loadhandling(driver);
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LabelPassengerXpath)));
			 queryObjects.logStatus(driver, Status.PASS, "Search Passenger", "Search Passenger displayed ",null);
	
		}catch(Exception e) {
			 queryObjects.logStatus(driver, Status.FAIL, "Search Passenger", "Search Passenger displayed ",e);
					
		}
		
		
			 String sFlight= FlightSearch.getTrimTdata(queryObjects.getTestData("Flight"));
				if (sFlight.equalsIgnoreCase("Res")) {
					 sFlight= Login.FLIGHTNUM;
				}
				
				String sFrom = FlightSearch.getTrimTdata(queryObjects.getTestData("From"));
				if(sFrom != "") {
					String FromXpathleft = "//div[@class='left-fixed-column layout-column']//input[@name='origin']";
					 driver.findElement(By.xpath(FromXpathleft)).clear();
					 driver.findElement(By.xpath(FromXpathleft)).sendKeys(sFrom);
					 driver.findElement(By.xpath(FromXpathleft)).sendKeys(Keys.TAB);
				 }	
	
	 try {
		 
	 
				driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sFlight);
	 boolean FLAG=true;
	 Calendar cal1 = Calendar.getInstance();    
	 SimpleDateFormat sdf = new  SimpleDateFormat("MM/dd/yyyy");

	 cal1.add(Calendar.DATE, 1);	

	String newDate = sdf.format(cal1.getTime());
	 
	 driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).clear();
	 driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).click();
	 driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(newDate);
	 
	 driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::div[@ng-if='flightSearch.display.gateSearchBtn']/button")).click();
	 FlightSearch.loadhandling(driver); 
	 
	 }catch(Exception e) {
		 queryObjects.logStatus(driver, Status.FAIL, "Search Passenger", "Search Passenger displayed ",e);
			
	}
	 
	 if(sFlight != "") {
		 boolean Flightdisplayed = driver.findElement(By.xpath("//div[contains(@class,'flight-details')]/div[contains(text(),'"+sFlight+"')]")).isDisplayed();
		 if (Flightdisplayed) {
			 queryObjects.logStatus(driver, Status.PASS, "Search Passenger by Flight details", "Passenger search completed: "+sFlight , null);
		 }else {
			 queryObjects.logStatus(driver, Status.FAIL, "Search Passenger by Flight details", "Passenger search completed: "+sFlight , null);
			 }
	 }	
	 
	 try {
	 String Paxno=FlightSearch.getTrimTdata(queryObjects.getTestData("pax_nocheckin"));
	 int no=Integer.parseInt(Paxno);
	// List<WebElement> SelectPnr=driver.findElements(By.xpath("//div[contains(@ng-repeat,'passengerItinerary')]/child::div/div[1]/div[2]/i[contains(@class,'in-active-state')]//parent::div/preceding-sibling::md-checkbox/div"));
	int k=0;
	List<WebElement> SelectPnr=driver.findElements(By.xpath("//div[contains(@ng-repeat,'passengerItinerary')]/child::div/div[1]/div[2]/i[contains(@class,'in-active-state')]//parent::div/preceding-sibling::md-checkbox/div[1]"));
	for(int i=0;i<no;i++)
	{    
		SelectPnr.get(k).click();
	  //driver.findElement(By.xpath("")).click(); 
		//SelectPnr.clear();
		Thread.sleep(1000);
		++k;
	}
	
	}catch(Exception e) {
		 queryObjects.logStatus(driver, Status.FAIL, "Search Passenger", "Search Passenger displayed ",e);
			
	}
	 boolean Proceed = false;
		try {
			
			
		  Proceed = driver.findElement(By.xpath(ProceedCheckInXpath)).isDisplayed();
		}catch(Exception e){}
		
		if (Proceed) {
			driver.findElement(By.xpath(ProceedCheckInXpath)).click();
			 FlightSearch.loadhandling(driver);
		}
		
		try {
		String PaxNmae=driver.findElement(By.xpath("//div/div[contains(@ng-repeat,'OrderPassenger.finalDocuments')]")).getText().trim();
		 String[] lastname=PaxNmae.split("/");
		 String name=lastname[0];
		 
		}catch(Exception e) {
			 queryObjects.logStatus(driver, Status.FAIL, "Search Passenger", "Search Passenger displayed ",e);
				
		}
		 boolean IsExpectedCondition = driver.findElement(By.xpath(SecurityXpath)).isDisplayed();
		 boolean IsEnabled = driver.findElement(By.xpath(SurnameXpath1)).isEnabled();
		 
		 boolean IsEnabledCOR = driver.findElement(By.xpath(CORXpath)).isEnabled();
		 
		 //suman
		 if(IsEnabledCOR && (!IsEnabled))
		 {
			 driver.findElement(By.xpath(CORXpath)).sendKeys("US"); 
			 Thread.sleep(2000);
			 driver.findElement(By.xpath(CORXpath)).sendKeys(Keys.ENTER);  
		 
		 //suman
		 boolean IsEnabledEmername = driver.findElement(By.xpath(EmernameXpath)).isEnabled();
		 if(IsEnabledEmername)
		 {
			 driver.findElement(By.xpath(EmernameXpath)).sendKeys("Jhon"); 
			 driver.findElement(By.xpath(EmernameXpath)).sendKeys(Keys.ENTER); 
		 }
		 //suman
		 boolean IsEnabledEmerPh = driver.findElement(By.xpath(EmerPhXpath)).isEnabled();
		 if(IsEnabledEmerPh)
		 {
			 driver.findElement(By.xpath(EmerPhXpath)).sendKeys("2324248656"); 
			 driver.findElement(By.xpath(EmerPhXpath)).sendKeys(Keys.ENTER); 
		 }
		 }
		 
		 if (IsExpectedCondition && IsEnabled) {
			 queryObjects.logStatus(driver, Status.PASS, "Navigated to the Security Document Verification Page", "Expected Page dispalyed ", null);
			try { 
			 Atoflow.EnterDocCheck(driver,queryObjects);
			}catch(Exception e) {
				 queryObjects.logStatus(driver, Status.FAIL, "UnExpected Page dispalyed", "UnExpected Page dispalyed "+sFlight , e); 
			}
			}
		 
		 
		 else if(IsExpectedCondition) {
			 queryObjects.logStatus(driver, Status.PASS, "Security Document Verification Page with ADC , APIS completed", "Expected Page dispalyed ", null);
			 //boolean IsEnabledEmerPh = driver.findElement(By.xpath(EmerPhXpath)).isEnabled();
			 boolean IsEnabledEmername = driver.findElement(By.xpath(EmernameXpath)).isEnabled();
			 if(IsEnabledEmername)
			 {
				 driver.findElement(By.xpath(EmernameXpath)).clear(); 
				 driver.findElement(By.xpath(EmernameXpath)).sendKeys("Jhon"); 				 
			
				 driver.findElement(By.xpath(EmerPhXpath)).clear();
				 driver.findElement(By.xpath(EmerPhXpath)).sendKeys("2324248656"); 
				// 
			 }
			try {
			 Atoflow.HandleSecDoc(driver,queryObjects);
			}catch(Exception e) {
				 queryObjects.logStatus(driver, Status.FAIL, "UnExpected Page dispalyed", "UnExpected Page dispalyed "+sFlight , e); 
			}
		 }

		 else {
			 
			 queryObjects.logStatus(driver, Status.FAIL, "UnExpected Page dispalyed", "UnExpected Page dispalyed "+sFlight , null); 
		 }
	 
		
	 
	try {
		 driver.findElement(By.xpath(SubmitXpath)).click();
		
	}catch(Exception e){}
	

	try {			 	
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DoneXpath)));
		 driver.findElement(By.xpath(DoneXpath)).click();
		 Thread.sleep(1000);
		 FlightSearch.loadhandling(driver);
	}catch(Exception e) {}
	
	// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FinalCheckInXpath)));
	 driver.findElement(By.xpath(FinalCheckInXpath)).click();
	 FlightSearch.loadhandling(driver);
	 
	 String Errmsg = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_CheckinMsg"));
	 
	 if (!Errmsg.isEmpty()) {
		
		 boolean err = false;
		 try {
		err =  driver.findElement(By.xpath("//span[contains(text(),'"+Errmsg+"')]")).isDisplayed();
		
		if (err) {
			 queryObjects.logStatus(driver, Status.PASS, "Expected Error Page dispalyed", "Expected Page dispalyed " ,null); 	
		}
		 }catch(Exception e) {
			 String Strdate = driver.findElement(By.xpath("//pssgui-date-time[@action='count-down']/div")).getText();
             String[] arrtime = Strdate.split(":");
             int time = Integer.parseInt(arrtime[0]);
             if (time >2) 
                    queryObjects.logStatus(driver, Status.PASS, "Flight is not restricted since time is still not within 2  hours", "Expected Page dispalyed " ,e);  
                    else
             queryObjects.logStatus(driver, Status.FAIL, "UnExpected Page dispalyed", "UnExpected Page dispalyed " ,e);  

		 }
		 
	 }
	 
	}
	
	

}
