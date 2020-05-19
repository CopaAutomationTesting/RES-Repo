package com.copa.ATOscripts;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.RESscripts.FlightSearch;
import com.copa.RESscripts.Login;
import com.copa.Util.ATOflowPageObjects;
import com.copa.Util.FlightSearchPageObjects;

import FrameworkCode.BFrameworkQueryObjects;


public class FlightAction extends ATOflowPageObjects{
	
	public void FlightActions(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		String Reason = FlightSearch.getTrimTdata(queryObjects.getTestData("Reason"));
		String NewFlight = FlightSearch.getTrimTdata(queryObjects.getTestData("NewFlight"));
		String From = FlightSearch.getTrimTdata(queryObjects.getTestData("From"));
		String Days = FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
		String TransferOption = FlightSearch.getTrimTdata(queryObjects.getTestData("TransferOption"));
		String MassTransfer = FlightSearch.getTrimTdata(queryObjects.getTestData("MassTransfer"));
		String CrewReport = FlightSearch.getTrimTdata(queryObjects.getTestData("CrewReport"));
		if(MassTransfer.equalsIgnoreCase("Y")){
			try{

				/*if(driver.findElement(By.xpath(ReservationXpath)).isDisplayed()){
					driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
					Thread.sleep(2000);
					driver.findElement(By.xpath(CheckInXpath)).click();
					FlightSearch.loadhandling(driver);
				}*/
				//method for direct mass transfer - need to be written

				//method for mass transfer after PNR Check-in
				//click flight action
				driver.findElement(By.xpath("//md-select[@aria-label='Flight Actions']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//md-option/div[contains(text(),'Mass Transfer')]")).click();
				FlightSearch.loadhandling(driver);
				//Mass transfer option
				
				String PNR = Login.PNRNUM.trim();
				driver.findElement(By.xpath("//md-select[@aria-label='Reason Code']")).click();
				Thread.sleep(2000);
				
				driver.findElement(By.xpath("//md-option//div[contains(text(),'"+Reason+"')]")).click();
				Thread.sleep(2000);
				if(Days != "") {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, Integer.parseInt(Days));
					String Date = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
					driver.findElement(By.xpath("//md-content//input[@class='md-datepicker-input']")).clear();
					driver.findElement(By.xpath("//md-content//input[@class='md-datepicker-input']")).sendKeys(Date);
				}
				driver.findElement(By.xpath("//md-content//i[@class='icon-search']")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//input[@aria-label='Enter Name']")).sendKeys(NewFlight);
				Thread.sleep(2000);
				driver.findElement(By.xpath("//md-radio-button//div[@class='md-off']")).click();
				Thread.sleep(2000);
				try{
					driver.findElement(By.xpath("//button[@aria-label='transfer all' and not( @disabled='disabled')]")).isDisplayed();
				}catch(Exception e){
					queryObjects.logStatus(driver, Status.FAIL,"Need Some mandory field to be selected for Mass Transfer", "Mass transfer details needed "+e.getLocalizedMessage(),e );
				}
				driver.findElement(By.xpath("//md-select[@aria-label='Transfer Options']")).click();
				try{
					driver.findElement(By.xpath("//md-option//div[contains(text(),'"+TransferOption+"')]")).click();//need to give relative Xpath
				}catch(Exception e){
					queryObjects.logStatus(driver, Status.FAIL,"Transfer Option details Xpath and test data having error", "Mass transfer details issue "+e.getLocalizedMessage(),e );
				}
				try{
					if(driver.findElement(By.xpath("//div[contains(text(),'Mass Transfer') and contains(text(),'Checked in')]")).isDisplayed()){
						driver.findElement(By.xpath("//button[@aria-label='Mass Transfer confirm']")).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//button[@aria-label='ok']")).click();
						FlightSearch.loadhandling(driver);
					}
				}catch(Exception e){
					queryObjects.logStatus(driver, Status.FAIL,"Mass Transfer Action Not performed", "Mass transfer pop-up Issue "+e.getLocalizedMessage(),e );
				}
				try{
					driver.findElement(By.xpath("//input[@aria-label='Flight']")).clear();
					driver.findElement(By.xpath("//input[@aria-label='Flight']")).sendKeys(NewFlight);
					driver.findElement(By.xpath("//input[@aria-label='From']")).clear();
					driver.findElement(By.xpath("//input[@aria-label='From']")).sendKeys(From);
					driver.findElement(By.xpath("//input[@aria-label='From']")).sendKeys(Keys.ENTER);
					if(Days != "") {
						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.DATE, Integer.parseInt(Days));
						String Date = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
						driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@class='md-datepicker-input']")).clear();
						driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@class='md-datepicker-input']")).sendKeys(Date);
					}
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//form[@name='flightSearch.form']//i")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//pssgui-menu//span[@class='md-select-icon']")).click();
					Thread.sleep(5000);
					driver.findElement(By.xpath("//div[contains(text(),'PNR')]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//input[@aria-label='Enter']")).clear();
					driver.findElement(By.xpath("//input[@aria-label='Enter']")).sendKeys(PNR);
					driver.findElement(By.xpath("//input[@aria-label='Enter']")).sendKeys(Keys.ENTER);
					Thread.sleep(5000);
				}catch(Exception e){
					queryObjects.logStatus(driver, Status.FAIL,"The given Flight details is not available", "After Mass Transfer given PNR is not available in new flight",null );
				}
				try{
					if(driver.findElement(By.xpath("//div[@class='passenger-list layout-row']//span[contains(text(),'"+PNR+"')]")).isDisplayed()){
						queryObjects.logStatus(driver, Status.PASS,"The given PNR is available", "After Mass Transfer "+PNR+" is available in new flight",null );
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL,"The given PNR is not available", "After Mass Transfer "+PNR+" is not available in new flight",null );
					}
					driver.findElement(By.xpath("//div[@class='passenger-list layout-row']//span[contains(text(),'"+PNR+"')]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//div[@role='button']//div[contains(text(),'Tickets')]")).click();
					if(driver.findElement(By.xpath("//div[contains(text(),'"+PNR+"')]")).isDisplayed()){
						queryObjects.logStatus(driver, Status.PASS,"The given PNR is available in New Flight", "After Mass Transfer "+PNR+" is available in new flight",null );
					}
					else{
						queryObjects.logStatus(driver, Status.FAIL,"The given PNR is not available", "After Mass Transfer "+PNR+" is not available in new flight",null );
					}
				}catch(Exception e){
					queryObjects.logStatus(driver, Status.FAIL,"The given PNR is not available", "After Mass Transfer "+PNR+" is not available in new flight"+e.getLocalizedMessage(),e);
				}
			}
			catch(Exception e){
				queryObjects.logStatus(driver, Status.FAIL,"Flight action Failes", "Mass Transfer issue "+e.getLocalizedMessage(),e );
			}
		}
		if(CrewReport.equalsIgnoreCase("Y")){

			
			try{
  				 driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
				 Thread.sleep(2000);
				 driver.findElement(By.xpath(CheckInXpath)).click();
				 FlightSearch.loadhandling(driver);
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LabelPassengerXpath)));
				 queryObjects.logStatus(driver, Status.PASS, "Search Passenger", "Search Passenger displayed ",null);
				 Thread.sleep(5000);
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(NewFlight);
				 Thread.sleep(5000);
				 Calendar cal = Calendar.getInstance();
				 cal.add(Calendar.DATE, Integer.parseInt(Days));
				 String sDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
				 driver.findElement(By.xpath("//pssgui-date-time[contains(@ng-model,'flightSearch')]//input[@class='md-datepicker-input']")).clear();
				 driver.findElement(By.xpath("//pssgui-date-time[contains(@ng-model,'flightSearch')]//input[@class='md-datepicker-input']")).sendKeys(sDate);
				 FlightSearch.loadhandling(driver);
				 driver.findElement(By.xpath("//div[@class='ng-scope flex']/button[@translate='pssgui.search']")).click();
				 FlightSearch.loadhandling(driver);
				 if(driver.findElement(By.xpath("//md-select[@aria-label='Flight Actions']")).isDisplayed()){
					 driver.findElement(By.xpath("//md-select[@aria-label='Flight Actions']")).click();
					 Thread.sleep(2000);
					 driver.findElement(By.xpath("//md-option/div[contains(text(),'Cabin Crew Report (Final)')]")).click();
					 FlightSearch.loadhandling(driver);
					 if(driver.findElement(By.xpath("//div[contains(text(),'Cabin Crew - Final Report')]")).isDisplayed()){
						 queryObjects.logStatus(driver, Status.PASS, "Displaying the Cabin Crew Details ", "Cabin Crew Details ",null);
					 }
					 else{
						 queryObjects.logStatus(driver, Status.FAIL, "Unable to find the Cabin Crew Details ", "Cabin Crew Details not found ",null);
					 }
				 }
				 else{
					 queryObjects.logStatus(driver, Status.FAIL, "Unable to find the details for specified flight ", "Flown flight details not found ",null);
				 }
				 
			}
			catch(Exception e){
				queryObjects.logStatus(driver, Status.FAIL, "Crew report failed", "FAILED in crew report data display"+e.getLocalizedMessage(),e);
			}
		
		}
		
	}
	
}