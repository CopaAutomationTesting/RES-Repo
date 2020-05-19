package com.copa.ATOscripts;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.ATOscripts.Atoflow;
import com.copa.RESscripts.FlightSearch;
import com.copa.Util.url_path;

import FrameworkCode.BFrameworkQueryObjects;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

public class ProductionCases extends url_path{

	public static  String EnvP=null;
	static String SalesOff =null;
	static String Cur=null;
	public static String Usernm=null;
	public static String Call_Center_Login=null;
	public static final int PNRDetails=0;
	public static String Change_Salesoffice="";
	public static String Change_Currency="";
	public static boolean CardDeposit=false;
	public static String CardDeposit_Change_Salesoffice="";


	//public class Login extends url_path{
	// Production Read only cases it will work for SIT but in UAT need to  assign POS......

	public void ProductionSanity(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{

		try{

			EnvP=(queryObjects.getTestData("EnvP")); 
			if (EnvP.equalsIgnoreCase("SIT")) 
				driver.navigate().to(uSITurl);
			else if (EnvP.equalsIgnoreCase("DEV")|| EnvP.equalsIgnoreCase("JSIT")) 
				driver.navigate().to(uDevurl);
			else if (EnvP.equalsIgnoreCase("UAT")) 
				driver.navigate().to(uUATurl+"/css");
			else if (EnvP.equalsIgnoreCase("PER")) //performance environment.
				driver.navigate().to(uPERurl);


			
			String usnm=queryObjects.getTestData("usnm");
			String psw=queryObjects.getTestData("psw");
			
			driver.findElement(By.name("USER")).sendKeys(usnm);
			driver.findElement(By.name("PASSWORD")).sendKeys(psw);
			driver.findElement(By.name("submit")).click();
			Thread.sleep(10000);


			driver.findElement(By.xpath("//a[contains(text(),'css')]")).click();


			driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
			FlightSearch.loadhandling(driver);	
			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Reservations')]")));
			String Home = driver.findElement(By.xpath("//div[contains(text(),'Reservations')]")).getText();
			Thread.sleep(2000);
			if(driver.findElements(By.xpath("//md-dialog[@aria-label='Login reminder']//following::button[contains(text(),'Ok')]")).size()>0 )
				driver.findElement(By.xpath("//md-dialog[@aria-label='Login reminder']//following::button[contains(text(),'Ok')]")).click();

			if (Home.trim().equals("Reservations"))
				queryObjects.logStatus(driver, Status.PASS, "Login to COPA Application", "LoginSuccess", null);

		}
		catch(Exception e) {

			queryObjects.logStatus(driver, Status.FAIL, "Login to COPA Application", "LoginFAilure", e);
		}


		if((queryObjects.getTestData("ChangePOSandvalidate")).equalsIgnoreCase("yes")) {

			try {

				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[@action='saleOfficeInfo']/div[@class='padding-top']/i")).click(); 
				Thread.sleep(3000);
				driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div/div[1]/md-input-container/md-select/md-select-value/span[2]")).click();
				Thread.sleep(3000);
				//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'loading-message')]")));
				//driver.findElement(By.xpath("//md-option[div[@class='md-text ng-binding' and contains(text(),'"+SalesOff+"')]]")).click(); 
				driver.findElement(By.xpath("//md-option[div[@class='md-text ng-binding' and contains(text(),'ADZ ATO')]][1]")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div/div[2]/md-input-container")).click();//Clicking on Currency drop down
				Thread.sleep(3000);
				driver.findElement(By.xpath("//md-option[contains(@ng-value,'currency')][div[@class='md-text ng-binding' and contains(text(),'COP')]] ")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div[2]/button[contains(text(),'OK')]")).click(); 
				FlightSearch.loadhandling(driver);
				Thread.sleep(4000);
				if(driver.findElements(By.xpath("//md-dialog[@aria-label='Login reminder']//following::button[contains(text(),'Ok')]")).size()>0 )
					driver.findElement(By.xpath("//md-dialog[@aria-label='Login reminder']//following::button[contains(text(),'Ok')]")).click();


				queryObjects.logStatus(driver, Status.PASS, "Change POS and validate", "Change POS and validate-->Success", null);
			}

			catch(Exception e) {

				queryObjects.logStatus(driver, Status.FAIL, "Change POS and validate", "Change POS and validate-->FAilure", e);
			}

		}


		if((queryObjects.getTestData("Verifyapplicationmainpage")).equalsIgnoreCase("yes")) {

			try {

				if(driver.findElements(By.xpath("//md-grid-tile[contains(@class,'tile-disabled')]/figure/div[contains(text(),'Reservations')]")).size()>0)
				{
					queryObjects.logStatus(driver, Status.WARNING, "Reservation tab should be enabled for the user", "Reservation tab is not enabled", null);
				}
				else {
					queryObjects.logStatus(driver, Status.PASS, "Reservation tab should be enabled for the user", "Reservation tab is enabled", null);
				}
				queryObjects.logStatus(driver, Status.PASS, "Reservation tab should be displayed for the user", "Reservation tab is displayed", null);


				if(driver.findElements(By.xpath("//md-grid-tile[contains(@class,'tile-disabled')]/figure/div[contains(text(),'Check-In')]")).size()>0) {
					queryObjects.logStatus(driver, Status.WARNING, "Check-In tab should be enabled for the user", "Check-In tab is not enabled", null);
				}
				else {
					queryObjects.logStatus(driver, Status.PASS, "Check-In tab should be enabled for the user", "Check-In tab is enabled", null);
				}
				queryObjects.logStatus(driver, Status.PASS, "Check-In tab should be displayed for the user", "Check-In tab is displayed", null);


				if(driver.findElements(By.xpath("//md-grid-tile[contains(@class,'tile-disabled')]/figure/div[contains(text(),'Gate')]")).size()>0) {
					queryObjects.logStatus(driver, Status.WARNING, "Gate tab should be enabled for the user", "Gate tab is not enabled", null);
				}
				else {
					queryObjects.logStatus(driver, Status.PASS, "Gate tab should be enabled for the user", "Gate tab is enabled", null);
				}
				queryObjects.logStatus(driver, Status.PASS, "Gate tab should be displayed for the user", "Gate tab is displayed", null);


				if(driver.findElements(By.xpath("//md-grid-tile[contains(@class,'tile-disabled')]/figure/div[contains(text(),'Sales Reporting')]")).size()>0) {
					queryObjects.logStatus(driver, Status.WARNING, "Sales Reporting tab should be enabled for the user", "Sales Reporting tab is not enabled", null);
				}
				else {
					queryObjects.logStatus(driver, Status.PASS, "Sales Reporting tab should be enabled for the user", "Sales Reporting tab is enabled", null);
					queryObjects.logStatus(driver, Status.PASS, "Sales Reporting tab should be displayed for the user", "Sales Reporting tab is displayed", null);
				}


				if(driver.findElements(By.xpath("//md-grid-tile[contains(@class,'tile-disabled')]/figure/div[contains(text(),'Travel Compensation')]")).size()>0) {
					queryObjects.logStatus(driver, Status.WARNING, "Travel Compensation tab should be enabled for the user", "Travel Compensation tab is not enabled", null);
				}
				else {
					queryObjects.logStatus(driver, Status.PASS, "Travel Compensation tab should be enabled for the user", "Travel Compensation tab is enabled", null);
				}
				queryObjects.logStatus(driver, Status.PASS, "Travel Compensation tab should be displayed for the user", "Travel Compensation tab is displayed", null);


				if(driver.findElements(By.xpath("//md-grid-tile[contains(@class,'tile-disabled')]/figure/div[contains(text(),'Codeshare')]")).size()>0) {
					queryObjects.logStatus(driver, Status.WARNING, "Codeshare tab should be enabled for the user", "Codeshare tab is not enabled", null);
				}
				else {
					queryObjects.logStatus(driver, Status.PASS, "Codeshare tab should be enabled for the user", "Codeshare tab is enabled", null);
				}
				queryObjects.logStatus(driver, Status.PASS, "Codeshare tab should not be displayed for the user", "Codeshare tab is not displayed", null);


				if(driver.findElements(By.xpath("//md-grid-tile[contains(@class,'tile-disabled')]/figure/div[contains(text(),'User Provisioning Tool')]")).size()>0) {
					queryObjects.logStatus(driver, Status.WARNING, "User Provisioning Tool tab should be enabled for the user", "User Provisioning Tool tab is not enabled", null);
				}
				else {
					queryObjects.logStatus(driver, Status.PASS, "User Provisioning Tool tab should be enabled for the user", "User Provisioning Tool tab is enabled", null);
				}
				queryObjects.logStatus(driver, Status.PASS, "User Provisioning Tool tab should be displayed for the user", "User Provisioning Tool tab is displayed", null);


			}
			catch(Exception e) {

				queryObjects.logStatus(driver, Status.FAIL, "Verify application main page  ", "Verify application main page  -->Failed", e);
			}

		}

		if((queryObjects.getTestData("Verifytopheaderpanel")).equalsIgnoreCase("yes")) {
			try {
				queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "Verify top header panel--->Started", null);
				Thread.sleep(1000);
				boolean Copa=driver.findElement(By.xpath("//div[contains(@class,'icn-logo pssgui-link')]")).isDisplayed();
				if(Copa)
					queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "CopaLogo--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Verify top header panel", "CopaLogo--->Not_Displaying ", null);
				Thread.sleep(1000);
				boolean Date=driver.findElement(By.xpath("//div[contains(@class,'hpe-pssgui date-time ng-binding')]")).isDisplayed();
				if(Date)
					queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "Curernt Date--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Verify top header panel", "Curernt Date--->Not_Displaying ", null);
				Thread.sleep(1000);
				boolean POS=driver.findElement(By.xpath("//div[contains(@class,'inset padding-right-0 pos-header ellipsis-text ng-binding')]")).isDisplayed();
				if(POS)
					queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "POS details--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Verify top header panel", "POS details--->Not_Displaying ", null);

				boolean LoginD=driver.findElement(By.xpath("//button[contains(text(),'Panama, Pam')]")).isEnabled();
				if(LoginD)
					queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "Login details--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Verify top header panel", "Login details--->Not_Displaying ", null);

				boolean Printer=driver.findElement(By.xpath("//pssgui-print-email[@action=\"printer-status\"]")).isEnabled();
				if(Printer)
					queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "Printer status--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Verify top header panel", "Printer status--->Not_Displaying ", null);

				boolean bgr=driver.findElement(By.xpath("//pssgui-boardingpass-scanner[@action=\"bgr-status\"]")).isEnabled();
				if(bgr)
					queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "bgr status--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Verify top header panel", "bgr status--->Not_Displaying ", null);

				boolean Doc=driver.findElement(By.xpath("//div[@aria-label=\"Document Scanner\"]")).isEnabled();
				if(Doc)
					queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "Document Scanner status--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Verify top header panel", "Document Scanner status--->Not_Displaying ", null);

				boolean Help=driver.findElement(By.xpath("//i[contains(@class,'icon-help')]")).isEnabled();
				if(Help)
					queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "Help status--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Verify top header panel", "Help status--->Not_Displaying ", null);

				driver.findElement(By.xpath("//md-menu//button[contains(text(),'Reservations')]")).click();

				Thread.sleep(4000);
				if(driver.findElements(By.xpath("//md-menu-item//button[contains(text(),'Reservations')]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "RES Module--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.WARNING, "Verify top header panel", "RES Module--->Not_Displaying in this POS", null);
				Thread.sleep(1000);
				if(driver.findElements(By.xpath("//md-menu-item//button[contains(text(),'Check-In')]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "Check-In Module--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.WARNING, "Verify top header panel", "Check-In Module--->Not_Displaying in this POS", null);
				Thread.sleep(1000);
				if(driver.findElements(By.xpath("//md-menu-item//button[contains(text(),'Gate')]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "Gate Module--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.WARNING, "Verify top header panel", "Gate Module--->Not_Displaying in this POS", null);
				Thread.sleep(1000);
				if(driver.findElements(By.xpath("//md-menu-item//button[contains(text(),'Sales Reporting')]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "Sales Reporting Module--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.WARNING, "Verify top header panel", "Sales Reporting Module--->Not_Displaying in this POS", null);
				Thread.sleep(1000);
				if(driver.findElements(By.xpath("//md-menu-item//button[contains(text(),'Travel Compensation')]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "Travel Compensation Module--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.WARNING, "Verify top header panel", "Travel Compensation Module--->Not_Displaying in this POS", null);
				Thread.sleep(1000);
				if(driver.findElements(By.xpath("//md-menu-item//button[contains(text(),'User Provisioning Tool')]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "User Provisioning Tool Module--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.WARNING, "Verify top header panel", "User Provisioning Tool Module--->Not_Displaying in this POS ", null);


				queryObjects.logStatus(driver, Status.PASS, "Verify top header panel", "Verify top header panel--->End", null);


			}
			catch(Exception e) {

				queryObjects.logStatus(driver, Status.FAIL, "Verify application main page  ", "Verify application main page  -->Failed", e);
			}

		}
		if((queryObjects.getTestData("Check-In")).equalsIgnoreCase("yes")) {
			try {
				boolean chck=driver.findElement(By.xpath("//md-menu//button[contains(text(),'Reservations')]")).isDisplayed();
				if(chck)
				{
					driver.findElement(By.xpath("//md-menu-item//button[contains(text(),'Check-In')]")).click();
				}
				else
				{
					driver.findElement(By.xpath("//md-menu//button[contains(text(),'Reservations')]")).click();
					driver.findElement(By.xpath("//md-menu-item//button[contains(text(),'Check-In')]")).click();
				}	
				//driver.findElement(By.xpath("//div[contains(text(),'Reservations')]")).click();
				FlightSearch.loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Check-In  ", "Check-In  -->clicked", null);

			}
			catch(Exception e) {

				queryObjects.logStatus(driver, Status.FAIL, "Check-In  ", "Check-In  -->Not clicked", e);
			}
		}
		if((queryObjects.getTestData("Check-InP")).equalsIgnoreCase("yes")) {
			try {

				driver.findElement(By.xpath("//md-menu//button[contains(text(),'Reservations')]")).click();
				driver.findElement(By.xpath("//md-menu-item//button[contains(text(),'Check-In')]")).click();
				FlightSearch.loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Check-InP  ", "Check-InP -->clicked", null);

			}
			catch(Exception e) {

				queryObjects.logStatus(driver, Status.FAIL, "Check-InP  ", "Check-InP -->Not clicked", e);
			}
		}


		if((queryObjects.getTestData("Checkinpage")).equalsIgnoreCase("yes")) {
			try {	


				boolean FlightSearch=driver.findElement(By.xpath("//form[@name='flightSearch.form']//div[contains(text(),'Flight Search')]")).isDisplayed();
				if(FlightSearch)
					queryObjects.logStatus(driver, Status.PASS, "Checkin page", "Checkin page Flight Search--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Checkin page", "Checkin page Flight Search--->Not_Displaying ", null);


				boolean Passenger=driver.findElement(By.xpath("//form[@name='passengerSearch.form']//div[contains(text(),'Passenger Search')]")).isDisplayed();
				if(Passenger)
					queryObjects.logStatus(driver, Status.PASS, "Checkin page", "Checkin page Passenger Search--->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Checkin page", "Checkin page Passenger Search--->Not_Displaying ", null);


				queryObjects.logStatus(driver, Status.PASS, "Checkin page  ", "Checkin page  -->Verified", null);
			}
			catch(Exception e) {

				queryObjects.logStatus(driver, Status.FAIL, "Order landingpage  ", "Order landingpage  -->NotVerified", e);
			}	
		}
		if((queryObjects.getTestData("SearchbyFlight")).equalsIgnoreCase("yes")) {
			try {	
				FlightSearch.loadhandling(driver);
				String DepAir= null;
				String tab=null;
				boolean Flight=driver.findElement(By.xpath("//label[contains(text(),'Fligh')]/following::input[@name='Flight'][1]")).isEnabled();
				if(Flight)
					queryObjects.logStatus(driver, Status.PASS, "Search by Flight page", "Search by Flight page Flight --->Displaying", null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Search by Flight page", "Search by Flight page Flight --->Not_Displaying ", null);

				DepAir=driver.findElement(By.xpath("//ng-form/following::input[@name='origin']")).getAttribute("value");
				tab=driver.findElement(By.xpath("//div[contains(@class,'inset padding-right-0 pos-header ellipsis-text ng-binding')]")).getText().trim();
				String POS=tab.split("-")[0].trim();

				if(DepAir.equalsIgnoreCase(POS))
					queryObjects.logStatus(driver, Status.PASS, "Search by Flight page", "Search by Flight page POS is same", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Search by Flight page", "Search by Flight page POS is not same", null);

				if(driver.findElements(By.xpath("//tbody//tr")).size()>0) 
					queryObjects.logStatus(driver, Status.PASS, "Search by Flight page", "Search by Flight page upcoming Departures-->Displaying " , null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Search by Flight page", "Search by Flight page upcoming Departures-->notdisplaying" , null);

				queryObjects.logStatus(driver, Status.PASS, "Search by Flight page", "Search by Flight page  -->Verified", null);
			}
			catch(Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Search by Flight page", "Search by Flight page-->NotVerified", e);
			}	
		}	
		if((queryObjects.getTestData("EnterFlight")).equalsIgnoreCase("yes")) {
			try {	
				driver.findElement(By.xpath("//label[@translate='pssgui.flight']/following::input[@name='Flight'][1]")).sendKeys("CM611");
				driver.findElement(By.xpath("//div[@ng-if]//button[@type='submit']")).click();

				FlightSearch.loadhandling(driver);
				if(driver.findElements(By.xpath("//label[@translate='pssgui.flight']/following::input[@name='Flight'][1]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "EnterFlight", "EnterFlight Passenger list  -->Displaying", null);
				else
					queryObjects.logStatus(driver, Status.PASS, "EnterFlight", "EnterFlight  Passenger list-->Not Displaying", null);
			}
			catch(Exception e) {

				queryObjects.logStatus(driver, Status.FAIL, "Search by Flight page", "EnterFlight-->NotVerified", e);
			}	
		}	

		if((queryObjects.getTestData("ValidateTopheaderpanel")).equalsIgnoreCase("yes")) {
			try {	
				String DepAir= null;
				String tab=null;
				DepAir=driver.findElement(By.xpath("//div[contains(@class,'pssgui-design-sub-heading-3 pssgui-bold ng-binding ng-isolate-scope segment-1')]")).getText();


				tab=driver.findElement(By.xpath("//div[contains(@class,'inset padding-right-0 pos-header ellipsis-text ng-binding')]")).getText().trim();
				String POS=tab.split("-")[0].trim();

				if(DepAir.equalsIgnoreCase(POS))
					queryObjects.logStatus(driver, Status.PASS, "ValidateTopheaderpanel", "ValidateTopheaderpanel POS is same", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "ValidateTopheaderpanel", "ValidateTopheaderpanel POS is not same", null);

				String ArvAir=driver.findElement(By.xpath("//div[contains(text(),'ADZ')]")).getText();

				driver.findElement(By.xpath("//input[@name='smartsearch']")).sendKeys("a");
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[contains(text(),'ll Booked Pax')]")).click();
				FlightSearch.loadhandling(driver);

				String AllPax=driver.findElement(By.xpath("//div[span[contains(text(),'All Booked Pax')]]")).getText().trim();

				String[] count=AllPax.split("\\r?\\n");
				String count1=count[2].trim();
				String[] bookedpax=count1.split("()");
				String bpax=bookedpax[1].trim();
				System.out.println(bpax);


				queryObjects.logStatus(driver, Status.PASS, "ValidateTopheaderpanel", "ValidateTopheaderpanel-->Verified", null);
			}
			catch(Exception e) {

				queryObjects.logStatus(driver, Status.FAIL, "ValidateTopheaderpanel", "ValidateTopheaderpanel-->NotVerified", e);
			}	
		}	
		if((queryObjects.getTestData("PassengerListPage")).equalsIgnoreCase("yes")) {
			CheckINpagevalidation(driver,queryObjects);
		}	
		if((queryObjects.getTestData("Passengerlistoption")).equalsIgnoreCase("yes"))
		{
			try {
				//--All Booked Pax
				driver.findElement(By.xpath("//input[@name='smartsearch']")).sendKeys("a");
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[contains(text(),'ll Booked Pax')]")).click();
				FlightSearch.loadhandling(driver);
				if(driver.findElement(By.xpath("//div[span[contains(text(),'All Booked Pax')]]")).isDisplayed())
					queryObjects.logStatus(driver, Status.PASS, "Passenger List Option", "All Booked Pax-->Displayed", null);
				else
					queryObjects.logStatus(driver, Status.PASS, "Passenger List Option", "All Booked Pax-->not Displayed", null);
				driver.findElement(By.xpath("//input[@name='smartsearch']")).clear();
				//--All Booked Standby Pax
				driver.findElement(By.xpath("//input[@name='smartsearch']")).sendKeys("a");		
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[contains(text(),'ll Booked Standby Pax')]")).click();
				FlightSearch.loadhandling(driver);
				if(driver.findElement(By.xpath("//div[span[contains(text(),'All Booked Standby Pax')]]")).isDisplayed())
					queryObjects.logStatus(driver, Status.PASS, "Passenger List Option", "All Booked Standby Pax-->Displayed", null);
				else
					queryObjects.logStatus(driver, Status.PASS, "Passenger List Option", "All Booked Standby Pax->not Displayed", null);
				driver.findElement(By.xpath("//input[@name='smartsearch']")).clear();
				//All checked in Pax
				driver.findElement(By.xpath("//input[@name='smartsearch']")).sendKeys("a");
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[contains(text(),'ll Checked-in Pax')]")).click();
				FlightSearch.loadhandling(driver);
				if(driver.findElement(By.xpath("//div[span[contains(text(),'All Checked-in Pax')]]")).isDisplayed())
					queryObjects.logStatus(driver, Status.PASS, "Passenger List Option", "All checked in Pax-->Displayed", null);
				else
					queryObjects.logStatus(driver, Status.PASS, "Passenger List Option", "All checked in Pax->not Displayed", null);
				driver.findElement(By.xpath("//input[@name='smartsearch']")).clear();

				//--All ETKT check in Pax
				driver.findElement(By.xpath("//input[@name='smartsearch']")).sendKeys("a");
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[contains(text(),'ll ETKT Checked-in Pax')]")).click();
				FlightSearch.loadhandling(driver);
				if(driver.findElement(By.xpath("//div[span[contains(text(),'All ETKT Checked-in Pax')]]")).isDisplayed())
					queryObjects.logStatus(driver, Status.PASS, "Passenger List Option", "All ETKT check in Pax-->Displayed", null);
				else
					queryObjects.logStatus(driver, Status.PASS, "Passenger List Option", "All ETKT check in Pax->not Displayed", null);
				driver.findElement(By.xpath("//input[@name='smartsearch']")).clear();

				//--Held seat pax…
				driver.findElement(By.xpath("//input[@name='smartsearch']")).sendKeys("h");
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[contains(text(),'eld Seat Pax')]")).click();
				FlightSearch.loadhandling(driver);
				if(driver.findElement(By.xpath("//div[span[contains(text(),'Held Seat Pax')]]")).isDisplayed())
					queryObjects.logStatus(driver, Status.PASS, "Passenger List Option", "Held seat pax-->Displayed", null);
				else
					queryObjects.logStatus(driver, Status.PASS, "Passenger List Option", "Held seat pax->not Displayed", null);


				queryObjects.logStatus(driver, Status.PASS, "Passenger List Option", "Passenger List Option-->Verified", null);
			}
			catch(Exception e) {

				queryObjects.logStatus(driver, Status.FAIL, "Passenger List Option", "Passenger List Option-->NotVerified", e);
			}

		}

		if((queryObjects.getTestData("GateCheckIn")).equalsIgnoreCase("yes"))
		{
			try {	
				Thread.sleep(1000);
				driver.findElement(By.xpath("//md-menu//button[contains(text(),'Reservations')]")).click();
				driver.findElement(By.xpath("//md-menu-item//button[contains(text(),'Gate')]")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//label[@translate='pssgui.flight']/following::input[@name='Flight'][1]")).sendKeys("CM611");
				driver.findElement(By.xpath("//div[@ng-if]//button[@type='submit']")).click();
				Thread.sleep(1000);
				FlightSearch.loadhandling(driver);
				if(driver.findElements(By.xpath("//label[@translate='pssgui.flight']/following::input[@name='Flight'][1]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Gate", "Gate Page Search-->Verified", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Gate", "Gate Page Search-->NotVerified", null);

				queryObjects.logStatus(driver, Status.PASS, "Gate", "Gate Page-->Verified", null);

			}
			catch(Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Gate", "Gate Page-->NotVerified", e);
			}
		}
		if((queryObjects.getTestData("GateCheckInPagevalidation")).equalsIgnoreCase("yes")) {
			CheckINpagevalidation(driver,queryObjects);
		}


		if(queryObjects.getTestData("PassengerDetailPage").equalsIgnoreCase("yes")){
			try {
				Thread.sleep(1000);

				List<WebElement> getcheckinpaxname=driver.findElements(By.xpath("//span[contains(@security-validate,'ViewPassengerDetails-passengerItinerary-passengerDetailsDisabled')]"));
				getcheckinpaxname.get(0).click();
				FlightSearch.loadhandling(driver);
				if(driver.findElement(By.xpath("//span[contains(@class,'pssgui-design-page-title-link ng-binding')]")).isDisplayed()) 
					queryObjects.logStatus(driver, Status.PASS, "Passenger Details Page", "Passenger Details Page -->PNR displayed on left pannel", null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Passenger Details Page", "Passenger Details Page -->PNR not displayed on left pannel", null);
				if(driver.findElement(By.xpath("//span[contains(@class,'pssgui-design-page-title-link ng-binding')]")).isDisplayed())	
					queryObjects.logStatus(driver, Status.PASS, "Passenger Details Page", "Passenger Details Page -->Selcted pax is checked and same data is displayed", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Passenger Details Page", "Passenger Details Page -->Selcted pax is checked and same data is not displayed", null);
				//--Bag icon

				if(driver.findElements(By.xpath("//div[contains(@class,'in-active-state') and (@security-validate='UpdateBag-passengerInfo-paxBagDisabled')]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Passenger Details Page", "Passenger Details Page -->Baggege icon Disabled", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Passenger Details Page", "Passenger Details Page -->Baggege icon Enabled", null);	

				//--Seat ICON
				if(driver.findElements(By.xpath("//i[@class='icon-seatmap in-active-state']")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Passenger Details Page", "Passenger Details Page -->Seat icon Disabled", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Passenger Details Page", "Passenger Details Page -->Seat icon Enabled", null);	

			}
			catch(Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Passenger Details Page", "Passenger Details Page-->NotVerified", e);
			}

		}

	}


	public static void CheckINpagevalidation(WebDriver driver ,BFrameworkQueryObjects queryObjects) throws IOException{

		try {	
			Boolean ALLtab=	driver.findElement(By.xpath("//div[@translate='pssgui.all']")).isEnabled();
			if(ALLtab)
				queryObjects.logStatus(driver, Status.PASS, "Passenger List Page", "Passenger List Page All tab-->Enabled", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page", "Passenger List Page All tab-->Not Enabled", null);


			boolean ProceedTochckiN=driver.findElement(By.xpath("//button[@aria-label='Proceed to checkin']")).isEnabled();
			if(ProceedTochckiN)
				queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page", "Passenger List Page Proceedtocheckin -->Enabled", null);	
			else
				queryObjects.logStatus(driver, Status.PASS, "Passenger List Page", "Passenger List Page Proceedtocheckin -->Disabled", null);

			boolean Offload=driver.findElement(By.xpath("//button[@aria-label='Offload']")).isEnabled();
			if(Offload)
				queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page", "Passenger List Page Offload -->Enabled", null);
			else
				queryObjects.logStatus(driver, Status.PASS, "Passenger List Page", "Passenger List Page Offload -->Disabled", null);

			List<WebElement> Pnrnum=driver.findElements(By.xpath("//span[contains(@security-validate,'ViewOrder-passengerItinerary-viewOrderDisabled')]"));
			String PNR=Pnrnum.get(0).getText();
			driver.findElement(By.xpath(" //md-select[@ng-model='menuCtrl.menuModel']/md-select-value")).click();
			driver.findElement(By.xpath("//div[contains(text(),'PNR')]")).click();
			Thread.sleep(4000);
			driver.findElement(By.xpath("//input[@aria-label='Enter']")).sendKeys(PNR);


			List<WebElement> PnrnumChck=driver.findElements(By.xpath("//span[contains(@security-validate,'ViewOrder-passengerItinerary-viewOrderDisabled')]"));
			String PNRCH=Pnrnum.get(0).getText();
			if(PNR.equalsIgnoreCase(PNRCH)) 
				queryObjects.logStatus(driver, Status.PASS, "Passenger List Page", "Passenger List Page PNR search -->verified sucessfully", null);	
			else
				queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page", "Passenger List Page PNR search -->verification Failed", null);


			List<WebElement> NAME=driver.findElements(By.xpath("//span[contains(@security-validate,'ViewPassengerDetails-passengerItinerary-passengerDetailsDisabled')]"));
			String name1=NAME.get(0).getText().trim();
			String name=name1.split("/")[0].trim();
			System.out.println(name);
			driver.findElement(By.xpath(" //md-select[@ng-model='menuCtrl.menuModel']/md-select-value")).click();
			driver.findElement(By.xpath("//div[contains(text(),'Name')]")).click();
			Thread.sleep(4000);
			driver.findElement(By.xpath("//input[@aria-label='Enter']")).clear();
			driver.findElement(By.xpath("//input[@aria-label='Enter']")).sendKeys(name);


			List<WebElement> NAMEC=driver.findElements(By.xpath("//span[contains(@security-validate,'ViewPassengerDetails-passengerItinerary-passengerDetailsDisabled')]"));
			String namech=NAMEC.get(0).getText();
			String namechk=namech.split("/")[0].trim();
			System.out.println(namechk);

			if(name.equalsIgnoreCase(namechk)) 
				queryObjects.logStatus(driver, Status.PASS, "Passenger List Page", "Passenger List Page name search -->verified sucessfully", null);	
			else
				queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page", "Passenger List Page name search -->verification Failed", null);

			driver.findElement(By.xpath("//input[@aria-label='Enter']")).clear();
			//--Seat number selection
			Thread.sleep(4000);
			boolean se=false;
			String Seatnumber=null;
			List<WebElement> SEAT=driver.findElements(By.xpath("//i[@class='icon-seatmap']/following-sibling::div/div/span"));
			for(int i=0;i<=SEAT.size();i++) {
				String getseatnum=SEAT.get(i).getText().trim();
				if(!getseatnum.isEmpty()) {
					Seatnumber=SEAT.get(i).getText().trim();	
					se=true;
					break;}
			}
			if(se) {
				driver.findElement(By.xpath(" //md-select[@ng-model='menuCtrl.menuModel']/md-select-value")).click();
				driver.findElement(By.xpath("//md-option[@value='seat']")).click();
				Thread.sleep(4000);
				driver.findElement(By.xpath("//input[@aria-label='Enter']")).clear();
				driver.findElement(By.xpath("//input[@aria-label='Enter']")).sendKeys(Seatnumber);
				System.out.println(Seatnumber);
			}
			else {queryObjects.logStatus(driver, Status.PASS, "Passenger List Page", "No pax have seat", null);}
			String Seatnumberchk=null;
			boolean chk=false;
			List<WebElement> SEATchck=driver.findElements(By.xpath("//i[@class='icon-seatmap']/following-sibling::div/div/span"));
			for(int i=0;i<=SEATchck.size();i++) {
				String getSeatnchck=SEATchck.get(i).getText().trim();
				if(!getSeatnchck.isEmpty())
				{
					Seatnumberchk=SEATchck.get(i).getText();
					chk=true;
					break;
				}
			}
			if(chk) {
				if(Seatnumber.equalsIgnoreCase(Seatnumberchk)) 
					queryObjects.logStatus(driver, Status.PASS, "Passenger List Page", "Passenger List Page Seatnumber search -->verified sucessfully", null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page", "Passenger List Page Seatnumber search -->verification Failed", null);
			}
			else {queryObjects.logStatus(driver, Status.PASS, "Passenger List Page", "No pax have seat", null);}
			driver.findElement(By.xpath("//input[@aria-label='Enter']")).clear();

			//-- bag tag number check

			//--PNR HYperlink check
			List<WebElement> Pnrhyperlink=driver.findElements(By.xpath("//span[contains(@security-validate,'ViewOrder-passengerItinerary-viewOrderDisabled')]"));
			Pnrhyperlink.get(0).click();
			FlightSearch.loadhandling(driver);

			if(driver.findElement(By.xpath("//div[@translate='pssgui.order']")).isEnabled())
			{
				driver.findElement(By.xpath("//div[@pssgui-shortcut='home.pssguiShortcutConstants.back']")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[@translate='pssgui.proceed.to.check.in']")).isEnabled();
				queryObjects.logStatus(driver, Status.PASS, "Passenger List Page", "Passenger List Page Click Order id hyperlink-->Verified succesfully", null);
			}	
			else
				queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page", "Passenger List Page Order id hyperlink-->verification failed", null);	

			List<WebElement> NAMEHyp=driver.findElements(By.xpath("//span[contains(@security-validate,'ViewPassengerDetails-passengerItinerary-passengerDetailsDisabled')]"));
			NAMEHyp.get(0).click();
			FlightSearch.loadhandling(driver);

			if(driver.findElement(By.xpath("//span[contains(@class,'pssgui-design-page-title-link ng-binding')]")).isEnabled())
			{
				driver.findElement(By.xpath("//div[@translate='pssgui.back']")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[@translate='pssgui.all']")).isEnabled();
				queryObjects.logStatus(driver, Status.PASS, "Passenger List Page", "Passenger List Page Click Name hyperlink-->Verified succesfully", null);
			}	
			else
				queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page", "Passenger List Page Name hyperlink-->verification failed", null);
			//-- need apis list check
			if(driver.findElement(By.xpath("//i[@class='icon-apis']/following-sibling::div")).isEnabled())
			{		
				try {
					Thread.sleep(1000);
					driver.findElement(By.xpath("//div[@translate='pssgui.need.apis']")).click();
					String apis=driver.findElement(By.xpath("//i[@class='icon-apis']/following-sibling::div")).getText();
					int apiscnt = Integer.parseInt(apis);
					if(apiscnt==0)
						queryObjects.logStatus(driver, Status.PASS, "Need Apis list", "Need Apis list--->No Passengers Found", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "Need Apis list", "Need Apis list--->List is present", null);

					queryObjects.logStatus(driver, Status.PASS, "Need Apis list", "Need Apis list--->APIS list verified", null);
				}

				catch(Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page", "Need Apis list-->APIS list not verified", e);
				}}
			//--etckt not sync
			if(driver.findElement(By.xpath("//i[@class='icon-etkt-not-sync']/following-sibling::div")).isEnabled())
			{		
				try {
					Thread.sleep(1000);
					driver.findElement(By.xpath("//div[@translate='pssgui.inbound']")).click();
					String inbound=driver.findElement(By.xpath("//i[@class='icon-etkt-not-sync']/following-sibling::div")).getText();
					int inboundcnt = Integer.parseInt(inbound);
					if(inboundcnt==0)
						queryObjects.logStatus(driver, Status.PASS, "Etckt not sync list ", "Etckt not sync list--->No Passengers Found", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "Etckt not sync list ", "Etckt not sync list--->List is present", null);

					queryObjects.logStatus(driver, Status.PASS, "Passenger List Page ", "Etckt not sync list --->Etckt not sync list verified", null);
				}
				catch(Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page", "Etckt not sync list-->Etckt not sync list not verified", e);
				}	
			}
			//-- Held seat 
			if(driver.findElement(By.xpath("//i[@class='icon-heldseat']/following-sibling::div")).isEnabled())
			{		
				try {
					Thread.sleep(1000);
					driver.findElement(By.xpath("//div[@translate='pssgui.held.seats']")).click();
					String heldseat=driver.findElement(By.xpath("//i[@class='icon-heldseat']/following-sibling::div")).getText();
					int heldseatCNT = Integer.parseInt(heldseat);
					if(heldseatCNT==0)
						queryObjects.logStatus(driver, Status.PASS, "Held seat list ", "Held seat list --->No Passengers Found", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "Etckt not sync list ", "Etckt not sync list--->List is present", null);
					Thread.sleep(1000);
					//--proceed to checkin
					if(driver.findElement(By.xpath("//button[contains(@security-validate,'ProceedToCheckIn-airportPassenger-proceedCheckInDisabled')]")).isEnabled())

						queryObjects.logStatus(driver, Status.FAIL, "HeldSeat list ", "HeldSeat list  list--->Proceed to check in Enabled", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "HeldSeat list ", "Held seat list--->Proceed to check in  disabled", null);	
					//---Relese

					if(driver.findElement(By.xpath("//button[@translate='pssgui.release']")).isEnabled())

						queryObjects.logStatus(driver, Status.FAIL, "HeldSeat list ", "HeldSeat list--->release Enabled", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "HeldSeat list ", "Held seat list--->release disabled", null);
					//---Relese ASA
					if(driver.findElement(By.xpath("//button[@translate='pssgui.release.asa']")).isEnabled())

						queryObjects.logStatus(driver, Status.FAIL, "HeldSeat list ", "HeldSeat list--->Release asa is Enabled", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "HeldSeat list ", "Held seat list--->Release asa is disabled", null);
					//--Release Held
					if(driver.findElement(By.xpath("//button[@translate='pssgui.release.asa']")).isEnabled())

						queryObjects.logStatus(driver, Status.FAIL, "HeldSeat list ", "HeldSeat list  list--->Release Held is Enabled", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "HeldSeat list ", "Held seat list--->Release Held  disabled", null);	
				}
				catch(Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page", "Etckt not sync list-->Etckt not sync list not verified", e);
				}	
			}
			//--standby list
			if(driver.findElement(By.xpath("//div[@translate='pssgui.standby']")).isEnabled())		
			{
				try {
					Thread.sleep(1000);
					driver.findElement(By.xpath("//div[@translate='pssgui.standby']")).click();
					String standby=driver.findElement(By.xpath("//i[@class='icon-standby']/following-sibling::div")).getText();
					int Standbycnt = Integer.parseInt(standby);
					if(Standbycnt==0)
						queryObjects.logStatus(driver, Status.PASS, "Standby list ", "Standby list --->No Passengers Found", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "Standby list ", "Standby list--->List is present", null);
					//--upagrade
					if(driver.findElement(By.xpath("//button[@translate='pssgui.upgrade']")).isEnabled())

						queryObjects.logStatus(driver, Status.FAIL, "Standby list ", "Standby list  list--->upgrade Enabled", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "Standby list ", "Standby seat list--->upgrade Disabled", null);	
					//--offload
					if(driver.findElement(By.xpath("//button[@translate='pssgui.offload']")).isEnabled())

						queryObjects.logStatus(driver, Status.FAIL, "Standby list ", "Standby list  list--->offload Enabled", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "Standby list ", "Standby list--->offload  Disabled", null);	
					//--transfer
					if(driver.findElement(By.xpath("//button[@translate='pssgui.transfer']")).isEnabled())

						queryObjects.logStatus(driver, Status.FAIL, "Standby list ", "Standby list--->Transfer Enabled", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "Standby list ", "Standby list--->Transfer Disabled", null);
					//--Enable imdate standby
					if(driver.findElement(By.xpath("//button[@translate='pssgui.transfer']")).isEnabled())

						queryObjects.logStatus(driver, Status.FAIL, "Standby list ", "Standby list--->Enable imdate standby Enabled", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "Standby list ", "Standby list--->Enable imdate standby Disabled", null);
					//--initiate Standby
					if(driver.findElement(By.xpath("//button[@translate='pssgui.transfer']")).isEnabled())

						queryObjects.logStatus(driver, Status.FAIL, "Standby list ", "Standby list--->initiate Standby Enabled", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "Standby list ", "Standby list--->initiate Standby Disabled", null);
				}
				catch(Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page", "Etckt not sync list-->Etckt not sync list not verified", e);
				}	
			}

			//--inbound list check
			if(driver.findElement(By.xpath("//div[@translate='pssgui.inbound']")).isEnabled())
			{
				try {
					Thread.sleep(1000);
					String inbound=driver.findElement(By.xpath("//i[@class='icon-inbound']/following-sibling::div")).getText();
					int inboundcnt = Integer.parseInt(inbound);
					driver.findElement(By.xpath("//div[@translate='pssgui.inbound']")).click();
					FlightSearch.loadhandling(driver);
					if(inboundcnt==0)
						queryObjects.logStatus(driver, Status.PASS, "inbound list ", "inbound list--->No flights found", null);	
					else {
						List<WebElement> inboundlist=driver.findElements(By.xpath("//div[@security-validate='ChooseFlightToBeMisconnected-connectingFlights-chooseFlightDisabled']//div/div[1]"));
						String getinflightnum=inboundlist.get(0).getText().trim();
						driver.findElement(By.xpath("//input[@aria-label='Flight Number']")).sendKeys(getinflightnum);
						String chkflight=driver.findElement(By.xpath("//div[@security-validate='ChooseFlightToBeMisconnected-connectingFlights-chooseFlightDisabled']//div/div[1]")).getText();
						if(getinflightnum.equalsIgnoreCase(chkflight))
							queryObjects.logStatus(driver, Status.PASS, "inbound list ", "inbound list--->flight check done", null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Standby list ", "Standby seat list--->flight check  failed", null);
					}

				}
				catch(Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page", "Inbound list-->Inbound list not verified", e);
				}	
			}
			//--reconcile list
			if(driver.findElement(By.xpath("//div[@translate='pssgui.unreconciled']")).isEnabled())
			{
				try {
					Thread.sleep(1000);
					String reco=driver.findElement(By.xpath("//i[@class='icon-unreconciled']/following-sibling::div")).getText();
					int unreco = Integer.parseInt(reco);
					driver.findElement(By.xpath("//div[@translate='pssgui.unreconciled']")).click();
					driver.findElement(By.xpath("//button[@translate='pssgui.cancel']")).click();

					FlightSearch.loadhandling(driver);
					if(unreco==0)
						queryObjects.logStatus(driver, Status.PASS, "unreconciled list ", "unreconciled list--->No PAX found", null);	
					else {
						queryObjects.logStatus(driver, Status.PASS, "unreconciled list ", "unreconciled list--->PAX dispalyed", null);	

						//--Abort
						if(driver.findElement(By.xpath("//button[(@aria-label='Abort') and (@security-validate='AbortBoarding-airportPanel-abortDisabled')]")).isEnabled())
							queryObjects.logStatus(driver, Status.FAIL, "unreconciled list ", "unreconciled list Abort button--->Enabled", null);	
						else 
							queryObjects.logStatus(driver, Status.PASS, "unreconciled list ", "unreconciled list Abort button--->Disabled", null);	
						//--close boarding	
						if(driver.findElement(By.xpath("//button[(@aria-label='Close Boarding') and (@security-validate='CloseBoarding-airportPanel-closeDisabled')]")).isEnabled())
							queryObjects.logStatus(driver, Status.FAIL, "unreconciled list ", "unreconciled list close boarding button--->Enabled", null);	
						else 
							queryObjects.logStatus(driver, Status.PASS, "unreconciled list ", "unreconciled list close boarding button--->Disabled", null);
						//reopen boarding	
						if(driver.findElement(By.xpath("//button[(@aria-label='Reopen Boarding') and (@security-validate='ReopenBoarding-airportPanel-reopenDisabled')]")).isEnabled())
							queryObjects.logStatus(driver, Status.FAIL, "unreconciled list ", "unreconciled list reopen boarding button--->Disabled", null);	
						else 
							queryObjects.logStatus(driver, Status.PASS, "unreconciled list ", "unreconciled list reopen boarding button--->Enabled", null);
					}
				}
				catch(Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page","unreconciled list-->unreconciled list not verified", e);
				}	
			}
			//--Verify Doc check
			driver.findElement(By.xpath("//div[@translate='pssgui.all']")).click();
			Thread.sleep(1000);

			if(driver.findElement(By.xpath("//span[@translate='pssgui.doc.check']")).isEnabled())
				queryObjects.logStatus(driver, Status.PASS, "APIS icon ", "APIS icon -->Disabled", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "APIS icon ", "APIS icon -->Enabled", null);

			queryObjects.logStatus(driver, Status.PASS, "Passenger List Page", "Passenger List Page-->Verified", null);

			if((queryObjects.getTestData("Standardcheckinbox")).equalsIgnoreCase("yes"))
			{
				try {
					//--Standard checkin
					Thread.sleep(1000);
					driver.findElement(By.xpath("//md-select[contains(@aria-label,'Passenger Actions: Standard CheckIn')]")).click();
					if(driver.findElements(By.xpath("//md-option[@value='CheckIn' and @aria-disabled='false']")).size()>0)
						queryObjects.logStatus(driver, Status.PASS, "Standard checkin box", "Standard checkin option-->Displayed", null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "Standard checkin box", "Standard checkin option->not Displayed", null);
					//--Protect Passenger
					Thread.sleep(1000);
					//boolean prtct=driver.findElement(By.xpath("//md-option[@value='ProtectSpace' and @aria-disabled='true']")).isEnabled();
					if(driver.findElements(By.xpath("//md-option[@value='ProtectSpace' and @aria-disabled='false']")).size()>0)
						queryObjects.logStatus(driver, Status.FAIL, "Standard checkin box", "Protect Passenger option-->Displayed", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "Standard checkin box", "Protect Passenger option->not Displayed", null);
					//--Go show / Force Sale
					Thread.sleep(1000);
					//boolean goshw=driver.findElement(By.xpath("//md-option[@value='goShowForceSale' and @aria-disabled='true']")).isEnabled();
					if(driver.findElements(By.xpath("//md-option[@value='goShowForceSale' and @aria-disabled='false']")).size()>0)
						queryObjects.logStatus(driver, Status.FAIL, "Standard checkin box", "Go show / Force Sale option-->Displayed", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "Standard checkin box", "Go show / Force Sale option->not Displayed", null);
					//--Add Passenger Note
					Thread.sleep(1000);
					//boolean addpax=driver.findElement(By.xpath("//md-option[@value='addPassengerNote' and @aria-disabled='true']")).isEnabled();
					if(driver.findElements(By.xpath("//md-option[@value='addPassengerNote' and @aria-disabled='false']")).size()>0)
						queryObjects.logStatus(driver, Status.FAIL, "Standard checkin box", "Add Passenger Note option-->Displayed", null);
					else
						queryObjects.logStatus(driver, Status.PASS, "Standard checkin box", "Add Passenger Note option->not Displayed", null);
					//driver.findElement(By.xpath("//md-select[contains(@aria-label,'Passenger Actions: Standard CheckIn')]")).click();
				}
				catch(Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Standard checkin box", "Standard checkin box-->NotVerified", e);
				}
			}
		}					
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Passenger List Page", "Passenger List Page bot verified", e);
		}	
	}	
}


