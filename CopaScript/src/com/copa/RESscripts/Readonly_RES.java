package com.copa.RESscripts;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import FrameworkCode.BFrameworkQueryObjects;

public class Readonly_RES {
	
	static String seatamt;
	public static boolean Re_Assignseat=false;
	static boolean isSeatAssigned=false;
	
	public void readonly_RES(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {
	
		String ordPgVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("OrdVerify"));
		String serPgVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("SSRVerify"));
		String tktPgVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("TktVerify"));
		String paxPgVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxVerify"));
		String sumPgVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("SumVerify"));
		String hmeBtnVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("Homebtn"));
		String schPgVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("SchVerify"));
		String farePgVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("fareVerify"));
		String flifoPgVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("flifoVerify"));
		String searchPgVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("searchVerify"));
		String searchPaxPgVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("searchPaxVerify"));
		String searchEMDPgVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("searchEMDVerify"));
		String settingsIcon = FlightSearch.getTrimTdata(queryObjects.getTestData("settingsVerify"));
		String logout = FlightSearch.getTrimTdata(queryObjects.getTestData("logout"));
		
	login(driver, queryObjects);
	//TestCase 1
	if(FlightSearch.getTrimTdata(queryObjects.getTestData("AppVerify")).equalsIgnoreCase("Y")) 
		AppVerify(driver, queryObjects);
	driver.findElement(By.xpath("//md-menu/button[contains(text(),'Reservations')]")).click();
	driver.findElement(By.xpath("//md-menu-item/button[contains(text(),'Reservations')]")).click();
	FlightSearch.loadhandling(driver);
	
	//TestCase 2		
	if(ordPgVerify.equalsIgnoreCase("Y")) 
		OrdVerify(driver, queryObjects);
	//TestCase 3	
	if(serPgVerify.equalsIgnoreCase("Y")) 
		SSRVerify(driver, queryObjects);
	//TestCase 4	
	if(tktPgVerify.equalsIgnoreCase("Y")) 
		TktVerify(driver, queryObjects);
	//TestCase 5
	//PASSENGER TAB
	//Checking if all 4 tabs displayed	
	if(paxPgVerify.equalsIgnoreCase("Y")) 
		PaxVerify(driver, queryObjects);
	//TestCase 6	
	if(sumPgVerify.equalsIgnoreCase("Y")) 
		SumVerify(driver, queryObjects);
	//TestCase 7
	//HOME BUTTON VALIDATION	
	if(hmeBtnVerify.equalsIgnoreCase("Y")) 
		Homebtn(driver, queryObjects);
	//TEST CASE - 8		
	if(schPgVerify.equalsIgnoreCase("Y")) 
		SchVerify(driver, queryObjects);
	//TEST CASE 9	
	if(farePgVerify.equalsIgnoreCase("Y")) 
		fareVerify(driver, queryObjects);
	//TEST CASE - 10	
	if(flifoPgVerify.equalsIgnoreCase("Y")) 
		flifoVerify(driver, queryObjects);
	//TEST CASE - 11	
	if(searchPgVerify.equalsIgnoreCase("Y")) 
		searchVerify(driver, queryObjects);
	//TEST CASE - 12	
	if(searchPaxPgVerify.equalsIgnoreCase("Y")) 
		searchPaxVerify(driver, queryObjects);
	//TEST CASE - 13	
	if(searchEMDPgVerify.equalsIgnoreCase("Y")) 
		searchEMDVerify(driver, queryObjects);
	//TEST CASE - 14	
	if(settingsIcon.equalsIgnoreCase("Y")) 
		settingsVerify(driver, queryObjects);
	//TEST CASE - 15
	if(logout.equalsIgnoreCase("Y"))
		logout(driver, queryObjects);
	//Write everything before this

}
public void login(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException, InterruptedException{
	try{
		driver.navigate().to("https://pssguicmm.airservices.svcs.entsvcs.com");
		Thread.sleep(3000);
		driver.findElement(By.name("USER")).sendKeys("dxcpsstest2");
		driver.findElement(By.name("PASSWORD")).sendKeys("Copa");
		driver.findElement(By.name("submit")).click();
		Thread.sleep(1000);
		if(driver.findElement(By.xpath("//a[contains(text(),'css')]")).isDisplayed() == true)
		{
			queryObjects.logStatus(driver, Status.PASS, "Login should be done", "Login done successfully", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Login should be done", "Login not done successfully", null);
		}
		driver.findElement(By.xpath("//a[contains(text(),'css')]")).click();
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		FlightSearch.loadhandling(driver);	
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Reservations')]")));
		try{
			if(driver.findElement(By.xpath("//h2[text()='Login reminder']")).isDisplayed()==true) {
				driver.findElement(By.xpath("//button[text()='Ok']")).click();
				Thread.sleep(2000);
			}
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.INFO, "Read only case Login", "OLD POS details pop up", e);
		}
		
		//Sales Office
		driver.findElement(By.xpath("//div[@action='saleOfficeInfo']//i")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//label[text()='Select Sales Office']/following-sibling::md-select//span/div")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//md-option/div[normalize-space()='ADZ ATO']")).click();
		Thread.sleep(2000);
		
		//Currency
		driver.findElement(By.xpath("//label[text()='Currency']/following-sibling::md-select")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//md-select-menu//md-option[@value='COP']")).click();
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//button[text()='OK']")).click();
		FlightSearch.loadhandling(driver);
		
		//Sales office Reminder
		try{
			if(driver.findElement(By.xpath("//h2[text()='Login reminder']")).isDisplayed()==true) {
				driver.findElement(By.xpath("//button[text()='Ok']")).click();
				Thread.sleep(2000);
			}
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.INFO, "Read only case Login", "OLD POS details pop up", e);
		}
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Read only case Login", "Login not done successfully", e);
		}
	}
public void OrderPage(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {
			if(driver.findElement(By.xpath("//div[text()='Order']")).isDisplayed()) {
				if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]//div[text()='Order']")).size()>0) {
					System.out.println("Order Page");
				}
				else {
					driver.findElement(By.xpath("//div[text()='Order']")).click();
				}
			if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]//div[text()='Order']")).size()>0) {
				queryObjects.logStatus(driver, Status.PASS, "Page should navigate to Order page", "Page navigated to Order page", null);
					
				//Segments
				int seg = driver.findElements(By.xpath("//td[@class='flight-name']")).size();
				if(seg>0) {
					queryObjects.logStatus(driver, Status.PASS, "Segments should be displayed correctly", "Segments are displayed correctly", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Segments should be displayed correctly", "Segments are not displayed correctly", null);
				}
					
				//Date
				int date = driver.findElements(By.xpath("//td[@class='date']")).size();
				if(date>0) {
					queryObjects.logStatus(driver, Status.PASS, "Dates should be displayed correctly", "Dates are displayed correctly", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Dates should be displayed correctly", "Dates are not displayed correctly", null);
				}
					
				//From
				int origin = driver.findElements(By.xpath("//div[@airport-code='originDestination.origin']")).size();
				if(origin>0) {
					queryObjects.logStatus(driver, Status.PASS, "Origin should be displayed correctly", "Origin are displayed correctly", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Origin should be displayed correctly", "Origin are not displayed correctly", null);
				}
					
				//FromTime
				int depTime = driver.findElements(By.xpath("//div[@airport-code='originDestination.origin']/following-sibling::div")).size();
				if(depTime>0) {
					queryObjects.logStatus(driver, Status.PASS, "Departure Time should be displayed correctly", "Departure Time are displayed correctly", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Departure Time should be displayed correctly", "Departure Time are not displayed correctly", null);
				}
					
				//To
				int dest = driver.findElements(By.xpath("//div[@airport-code='originDestination.destination']")).size();
				if(dest>0) {
					queryObjects.logStatus(driver, Status.PASS, "Destination should be displayed correctly", "Destination are displayed correctly", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Destination should be displayed correctly", "Destination are not displayed correctly", null);
				}
					
				//ToTime
				int arrTime = driver.findElements(By.xpath("//div[@airport-code='originDestination.destination']/following-sibling::div")).size();
				if(arrTime>0) {
					queryObjects.logStatus(driver, Status.PASS, "Arrival Time should be displayed correctly", "Arrival Time are displayed correctly", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Arrival Time should be displayed correctly", "Arrival Time are not displayed correctly", null);
				}
					
				//Class
				int fltClass = driver.findElements(By.xpath("//td[@class='flight-class']")).size();
				if(fltClass>0) {
					queryObjects.logStatus(driver, Status.PASS, "Class should be displayed correctly", "Class are displayed correctly", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Class should be displayed correctly", "Class are not displayed correctly", null);
				}
					
				//Status
				int status = driver.findElements(By.xpath("//td[@class='status']")).size();
				if(status>0) {
					queryObjects.logStatus(driver, Status.PASS, "Status should be displayed correctly", "Status are displayed correctly", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Status should be displayed correctly", "Status are not displayed correctly", null);
				}
				
				//Delete Icon
				int del = driver.findElements(By.xpath("//td[@class='actions']//span")).size();
				if(del>0) {
					queryObjects.logStatus(driver, Status.PASS, "Delete Icon should be displayed correctly", "Delete Icon are displayed correctly", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Delete Icon should be displayed correctly", "Delete Icon are not displayed correctly", null);
				}
				
				if(seg == date && date==origin && origin==depTime && depTime==dest && dest==arrTime && arrTime==fltClass && fltClass==status && status==del){
					queryObjects.logStatus(driver, Status.PASS, "Information should display correctly", "Information displayed correctly", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Information should display correctly", "Information not displayed correctly", null);
				}
			
				//Remarks tab validation
				
				WebElement flag1 = driver.findElement(By.xpath("//div[contains(text(),'OSI')]"));
				WebElement flag2 = driver.findElement(By.xpath("//div[contains(text(),'Remarks')]"));
				WebElement flag3 = driver.findElement(By.xpath("//div[contains(text(),'Order history')]"));
			
			
				if(flag1.isDisplayed() && flag2.isDisplayed() && flag3.isDisplayed()) {
					queryObjects.logStatus(driver, Status.PASS, "Three tab pages should display", "3 tab pages are displayed", null);
				}
			
				else{
					queryObjects.logStatus(driver, Status.FAIL, "Three tab pages should display", "3 tab pages are not displayed", null);
				}
				
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "Page should navigate to Order page", "Page not navigated to Order page", null);
			}
			
			//Validating left panel
			try {
				//PNR and Ticket Status
				int pnrNum = driver.findElements(By.xpath("//pssgui-reservations-home//form/following-sibling::div/div/div[1]/div[1]")).size();
				if(pnrNum>0) 
				queryObjects.logStatus(driver, Status.PASS, "PNR should be displayed correctly", "PNR is displayed correctly", null);
				
				int tktStatus = driver.findElements(By.xpath("//pssgui-reservations-home//form/following-sibling::div/div/div[1]/div[2]")).size();
				if(tktStatus>0)
				queryObjects.logStatus(driver, Status.PASS, "Ticket Status should be displayed correctly", "Ticket Status is displayed correctly", null);
				
				//No. of pax and inf
				int noOfPax = driver.findElements(By.xpath("//pssgui-reservations-home//form/following-sibling::div/div/div[2]/div[1]//span")).size();
				if(noOfPax>0)
				queryObjects.logStatus(driver, Status.PASS, "Number of Passengers should be displayed correctly", "Number of Passengers is  displayed correctly", null);
				
				int noOfInf = driver.findElements(By.xpath("//div[label[text()='INF']]/span")).size();
				if(noOfInf>0)
				queryObjects.logStatus(driver, Status.PASS, "Number of Infants should be displayed correctly", "Number of Infants is  displayed correctly", null);
				
				//Passenger Name
				int paxName = driver.findElements(By.xpath("//md-content//div/div[2]/preceding-sibling::div/span[@role='button']")).size();
				queryObjects.logStatus(driver, Status.PASS, "Passenger Name should be displayed correctly", "Passenger Name is  displayed correctly", null);
			}
			catch(Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Passenger details should be correctly displayed", "Passenger details not displayed correctly", e);
			}
			
				//Navigate to Passengers Page
				driver.findElement(By.xpath("//div[text()='Passengers']")).click();
				FlightSearch.loadhandling(driver);
				if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]/div[text()='Passengers']")).size()>0) {
					queryObjects.logStatus(driver, Status.PASS, "Should navigate to Passengers Page", "Navigated to Passengers Page", null);
					
					//Surname
					
					if(driver.findElements(By.xpath("//input[contains(@class,'ng-not-empty') and @aria-label='Last Name']")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "Passenger last name should be displayed", "Passenger last name is displayed", null);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL, "Passenger last name should be displayed", "Passenger last name is not displayed", null);
					}
					
					//Given Name
					if(driver.findElements(By.xpath("//input[contains(@class,'ng-not-empty') and @aria-label='First Name']")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "Passenger first name should be displayed", "Passenger first name is displayed", null);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL, "Passenger first name should be displayed", "Passenger first name is not displayed", null);
					}
					
					//Phone Number
					if(driver.findElements(By.xpath("//input[@aria-label='Phone Number' and contains(@class,'ng-not-empty')]")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "Passenger phone number should be displayed", "Passenger phone number is displayed", null);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL, "Passenger phone number should be displayed", "Passenger phone number is not displayed", null);
					}
					
					
					//Area Code
					if(driver.findElements(By.xpath("//input[contains(@class,'ng-not-empty') and @aria-label='Area Code']")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "Area code should be displayed", "Area code is displayed", null);
					}
					else {
						queryObjects.logStatus(driver, Status.WARNING, "Area code name should be displayed", "Area code is not displayed", null);
					}
					
					//Navigate to Home Page
					driver.findElements(By.xpath("//span[text()='Home']"));
					System.out.println("Home Page");
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Should navigate to Passengers Page", "Unable to navigate to Passengers Page", null);
				}
				
			
		}
	}
	
public void assignSeat(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 300);
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
		
		List<WebElement> TotalFlight=new ArrayList<>();
		TotalFlight=driver.findElements(By.xpath("//md-content[contains(@class,'segment-box')]/div[contains(@class,'seatmap-segmen')]/div"));
		int size=TotalFlight.size();
		seatamt = "";
		String Seatnm="";
		String Reassginstnm="";
		
		for(int allflights=1;allflights<=size;allflights++)
		{
			if(allflights>=2)
			{
				driver.findElement(By.xpath("//md-content[contains(@class,'segment-box')]/div[contains(@class,'seatmap-segmen')]/div["+allflights+"]//child::tbody/tr[@ng-repeat='leg in flight']/td[1]/md-checkbox/div")).click();
				FlightSearch.loadhandling(driver);
			}
			String flightnos=driver.findElement(By.xpath("//md-content[contains(@class,'segment-box')]/div[contains(@class,'seatmap-segmen')]/div["+allflights+"]/div/div[1]")).getText();
			int noOfPax = driver.findElements(By.xpath("//pssgui-reservations-home//form/following-sibling::div/div/div[2]/div[1]//span")).size();
			int noOfInf = driver.findElements(By.xpath("//pssgui-reservations-home//form/following-sibling::div/div/div[2]/div[2]//span")).size();
			int totalPAX = noOfPax + noOfInf;
			for(int i=1; i<=totalPAX; i++) {
				
				Seatnm="";
				Reassginstnm="";
				//clicking on the number assigned for the passenger
				//Thread.sleep(3000);
				FlightSearch.loadhandling(driver);
				
					//FlightSearch.loadhandling(driver);
					if(driver.findElements(By.xpath("//form[@name='seat.form']/div["+ i +"]//div[contains(@class,'passengerNo')and not (contains(@class,'selected'))]")).size()>0) 
						driver.findElement(By.xpath("//form[@name='seat.form']/div["+ i +"]//div[contains(@class,'passengerNo')and not (contains(@class,'selected'))]")).click();
					
						if (Re_Assignseat) {
							Seatnm=driver.findElement(By.xpath("//form[@name='seat.form']/div["+ i +"]//div[div[contains(@class,'passengerNo')]]/span/input")).getAttribute("value");
						}
					Thread.sleep(4000);
					String seatMapXpath="//div[contains(@class,'cabin')]//child::div[contains(@class,'icn-available') and not (contains(@class,'icn-chargeable')) and not (contains(@class,'icn-held')) and not (contains(@class,'seat-selection-color'))]";
					List<WebElement> allPaymentseat=driver.findElements(By.xpath(seatMapXpath));
					allPaymentseat.get(0).click();
				
					if (Re_Assignseat) {
						Reassginstnm=driver.findElement(By.xpath("//form[@name='seat.form']/div["+ i +"]//div[div[contains(@class,'passengerNo')]]/span/input")).getAttribute("value");
						if(!Reassginstnm.equalsIgnoreCase(Seatnm))
							queryObjects.logStatus(driver, Status.PASS,"Seat Re_Assigen validation","Seat Re_Assigned successfully for this flight no "+flightnos +"and "+i+" PAX ",null);
						else
							queryObjects.logStatus(driver, Status.FAIL,"Seat Re_Assigen validation","Seat Re_Assigned un successfully for this flight no "+flightnos +"and "+i+" PAX ",null);
					
						wait=new WebDriverWait(driver, 50);
						wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='seat-link' and @disabled='disabled']")));
						Thread.sleep(2000);
						//Need to check whether seat assigned or not
						List<WebElement> seatsAssigned = driver.findElements(By.xpath("//div[contains(@ng-repeat,'seat in serviceIcon')]//span[@ng-if='seat.number']"));

						for(WebElement seat: seatsAssigned) {
							if(seat.getText().trim().length() < 2) {
								/*isSeatAssigned = false;
								break;*/
							}
							else{
								isSeatAssigned=true;}
						}

					}
				
			}
		}
	}

public void AppVerify(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{
	
	//Verifying application main page
	
		if(driver.findElements(By.xpath("//figure/div[text()='Reservations']")).size()>0) {
			if(driver.findElements(By.xpath("//md-grid-tile[contains(@class,'tile-disabled')]/figure/div[contains(text(),'Reservations')]")).size()>0) {
				queryObjects.logStatus(driver, Status.WARNING, "Reservation tab should be enabled for the user", "Reservation tab is not enabled", null);
			}
			else {
				queryObjects.logStatus(driver, Status.PASS, "Reservation tab should be enabled for the user", "Reservation tab is enabled", null);
			}
			queryObjects.logStatus(driver, Status.PASS, "Reservation tab should be displayed for the user", "Reservation tab is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Reservation tab should be displayed for the user", "Reservation tab is not displayed", null);
		}
		if(driver.findElements(By.xpath("//figure/div[text()='Check-In']")).size()>0) {
			if(driver.findElements(By.xpath("//md-grid-tile[contains(@class,'tile-disabled')]/figure/div[contains(text(),'Check-In')]")).size()>0) {
				queryObjects.logStatus(driver, Status.WARNING, "Check-In tab should be enabled for the user", "Check-In tab is not enabled", null);
			}
			else {
				queryObjects.logStatus(driver, Status.PASS, "Check-In tab should be enabled for the user", "Check-In tab is enabled", null);
			}
			queryObjects.logStatus(driver, Status.PASS, "Check-In tab should be displayed for the user", "Check-In tab is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Check-In tab should be enabled for the user", "Check-In tab is not displayed", null);
		}
		if(driver.findElements(By.xpath("//figure/div[text()='Gate']")).size()>0) {
			if(driver.findElements(By.xpath("//md-grid-tile[contains(@class,'tile-disabled')]/figure/div[contains(text(),'Gate')]")).size()>0) {
				queryObjects.logStatus(driver, Status.WARNING, "Gate tab should be enabled for the user", "Gate tab is not enabled", null);
			}
			else {
				queryObjects.logStatus(driver, Status.PASS, "Gate tab should be enabled for the user", "Gate tab is enabled", null);
			}
			queryObjects.logStatus(driver, Status.PASS, "Gate tab should be displayed for the user", "Gate tab is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Gate tab should be displayed for the user", "Gate tab is not displayed", null);
		}
		if(driver.findElements(By.xpath("//figure/div[text()='Agent Sales Report']")).size()>0) {
			if(driver.findElements(By.xpath("//md-grid-tile[contains(@class,'tile-disabled')]/figure/div[contains(text(),'Agent Sales Report')]")).size()>0) {
				queryObjects.logStatus(driver, Status.WARNING, "Agent Sales Report tab should be enabled for the user", "Agent Sales Report tab is not enabled", null);
			}
			else {
				queryObjects.logStatus(driver, Status.PASS, "Agent Sales Report tab should be enabled for the user", "Agent Sales Report tab is enabled", null);
			}
			queryObjects.logStatus(driver, Status.PASS, "Agent Sales Report tab should be displayed for the user", "Agent Sales Report tab is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Agent Sales Report tab should be displayed for the user", "Agent Sales Report tab is not displayed", null);
		}
		if(driver.findElements(By.xpath("//figure/div[text()='Travel Compensation']")).size()>0) {
			if(driver.findElements(By.xpath("//md-grid-tile[contains(@class,'tile-disabled')]/figure/div[contains(text(),'Travel Compensation')]")).size()>0) {
				queryObjects.logStatus(driver, Status.WARNING, "Travel Compensation tab should be enabled for the user", "Travel Compensation tab is not enabled", null);
			}
			else {
				queryObjects.logStatus(driver, Status.PASS, "Travel Compensation tab should be enabled for the user", "Travel Compensation tab is enabled", null);
			}
			queryObjects.logStatus(driver, Status.PASS, "Travel Compensation tab should be displayed for the user", "Travel Compensation tab is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Travel Compensation tab should be displayed for the user", "Travel Compensation tab is not displayed", null);
		}
		if(driver.findElements(By.xpath("//figure/div[text()='Codeshare']")).size()>0) {
			if(driver.findElements(By.xpath("//md-grid-tile[contains(@class,'tile-disabled')]/figure/div[contains(text(),'Codeshare')]")).size()>0) {
				queryObjects.logStatus(driver, Status.WARNING, "Codeshare tab should be enabled for the user", "Codeshare tab is not enabled", null);
			}
			else {
				queryObjects.logStatus(driver, Status.PASS, "Codeshare tab should be enabled for the user", "Codeshare tab is enabled", null);
			}
			queryObjects.logStatus(driver, Status.PASS, "Codeshare tab should not be displayed for the user", "Codeshare tab is not displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Codeshare tab should not be displayed for the user", "Codeshare tab is displayed", null);
		}
		if(driver.findElements(By.xpath("//figure/div[text()='User Provisioning Tool']")).size()>0) {
			if(driver.findElements(By.xpath("//md-grid-tile[contains(@class,'tile-disabled')]/figure/div[contains(text(),'User Provisioning Tool')]")).size()>0) {
				queryObjects.logStatus(driver, Status.WARNING, "User Provisioning Tool tab should be enabled for the user", "User Provisioning Tool tab is not enabled", null);
			}
			else {
				queryObjects.logStatus(driver, Status.PASS, "User Provisioning Tool tab should be enabled for the user", "User Provisioning Tool tab is enabled", null);
			}
			queryObjects.logStatus(driver, Status.PASS, "User Provisioning Tool tab should be displayed for the user", "User Provisioning Tool tab is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "User Provisioning Tool tab should be displayed for the user", "User Provisioning Tool tab is not displayed", null);
		}
	

	//Verifying Top Header Panel
	try {
		if(driver.findElement(By.xpath("//div/div/div[@role='button']")).isDisplayed()==true) {//Copa logo
			queryObjects.logStatus(driver, Status.PASS, "Copa logo should be enabled for the user", "Copa logo is enabled", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Copa logo should be enabled for the user", "Copa logo is not enabled", null);
		}
	/*Current date and time*/
	
		ZoneOffset zoneOffset = ZoneOffset.of("-05:00");
		ZoneId zoneId=ZoneId.ofOffset("UTC", zoneOffset);
		LocalDateTime offsetTime = LocalDateTime.now(zoneId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy  HH:mm");
		String formattedTime=offsetTime.format(formatter);
		
	String dateStampd1 = driver.findElement(By.xpath("//pssgui-date-time[@action = 'display']/div")).getText();
	
	
		if(dateStampd1.equals(formattedTime)) {
			queryObjects.logStatus(driver, Status.PASS, "Current date and time should be enabled for the user", "Current date and time is enabled", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Current date and time should be enabled for the user", "Current date and time is not enabled", null);
		}
	//Verifying mpdules list
	try {
		if(driver.findElements(By.xpath("//md-menu/button[contains(text(),'Reservations')]")).size()>0) {
			queryObjects.logStatus(driver, Status.PASS, "Reservations should be displayed", "Reservations is displayed", null);
			
			driver.findElement(By.xpath("//md-menu/button[contains(text(),'Reservations')]")).click();
			try {
				List<WebElement> res = driver.findElements(By.xpath("//md-menu-item/button[contains(text(),'Reservations')]"));
				if(res.size()>0)
				{
					queryObjects.logStatus(driver, Status.PASS, "Reservations should be displayed", "Reservations is displayed", null);
				}
				else {
					queryObjects.logStatus(driver, Status.WARNING, "Reservations should be displayed", "Reservations is not displayed", null);
				}
				if(driver.findElements(By.xpath("//md-menu-item/button[contains(text(),'Check-In')]")).size()>0)
				{
					queryObjects.logStatus(driver, Status.PASS, "Check-In should be displayed", "Check-In is displayed", null);
				}
				else {
					queryObjects.logStatus(driver, Status.WARNING, "Check-In should be displayed", "Check-In is not displayed", null);
				}
				if(driver.findElements(By.xpath("//md-menu-item/button[contains(text(),'Gate')]")).size()>0)
				{
					queryObjects.logStatus(driver, Status.PASS, "Gate should be displayed", "Gate is displayed", null);
				}
				else {
					queryObjects.logStatus(driver, Status.WARNING, "Gate should be displayed", "Gate is not displayed", null);
				}
				if(driver.findElements(By.xpath("//md-menu-item/button[contains(text(),'Sales Reporting')]")).size()>0)
				{
					queryObjects.logStatus(driver, Status.PASS, "Sales Reporting should be displayed", "Sales Reporting is displayed", null);
				}
				else {
					queryObjects.logStatus(driver, Status.WARNING, "Sales Reporting should be displayed", "Sales Reporting is not displayed", null);
				}
				if(driver.findElements(By.xpath("//md-menu-item/button[contains(text(),'Travel Compensation')]")).size()>0)
				{
					queryObjects.logStatus(driver, Status.PASS, "Travel Compensation should be displayed", "Travel Compensation is displayed", null);
				}
				else {
					queryObjects.logStatus(driver, Status.WARNING, "Travel Compensation should be displayed", "Travel Compensation is not displayed", null);
				}
				if(driver.findElements(By.xpath("//md-menu-item/button[contains(text(),'User Provisioning Tool')]")).size()>0)
				{
					queryObjects.logStatus(driver, Status.PASS, "User Provisioning Tool should be displayed", "User Provisioning Tool is displayed", null);
				}
				else {
					queryObjects.logStatus(driver, Status.WARNING, "User Provisioning Tool should be displayed", "User Provisioning Tool is not displayed", null);
				}
				
			}
			catch(Exception e) {}
			if(driver.findElement(By.xpath("//pssgui-boardingpass-scanner/div")).isDisplayed()==true) {
				queryObjects.logStatus(driver, Status.PASS, "Boarding pass Scanner should be displayed", "Boarding pass Scanner is displayed", null);
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "Boarding pass Scanner should be displayed", "Boarding pass Scanner is not displayed", null);
			}
			if(driver.findElement(By.xpath("//div[@aria-label='Document Scanner']")).isDisplayed()==true) {
				queryObjects.logStatus(driver, Status.PASS, "Document Scanner should be displayed", "Document Scanner is displayed", null);
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "Document Scanner should be displayed", "Document Scanner is not displayed", null);
			}
			if(driver.findElement(By.xpath("//div[@aria-label='Printer']")).isDisplayed()==true) {
				queryObjects.logStatus(driver, Status.PASS, "Printer should be displayed", "Printer is displayed", null);
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "Printer should be displayed", "Printer is not displayed", null);
			}
			if(driver.findElement(By.xpath("//div[@aria-label = 'Printer']/following-sibling::div/i")).isDisplayed()==true) {
				queryObjects.logStatus(driver, Status.PASS, "Help icon should be displayed", "Help icon is displayed", null);
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "Help icon should be displayed", "Help icon is not displayed", null);
			}
			
			if(driver.findElement(By.xpath("//div[@id='toolbar']")).isDisplayed()==true) {
				queryObjects.logStatus(driver, Status.PASS, "Toolbar should be displayed", "Toolbar is displayed", null);
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "Toolbar should be displayed", "Toolbar is not displayed", null);
			}
			
		}
			
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Reservations should be displayed", "Reservations is not displayed", null);
		}
		
		//Changing POS
		//Sales Office
		try{
			driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
			driver.findElement(By.xpath("//div[@action='saleOfficeInfo']//i")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//label[text()='Select Sales Office']/following-sibling::md-select//span/div")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//md-option/div[normalize-space()='EZE ATO']")).click();
			Thread.sleep(2000);
		
			//Currency
			driver.findElement(By.xpath("//label[text()='Currency']/following-sibling::md-select")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//md-select-menu//md-option[@value='ARS']")).click();
			Thread.sleep(2000);
		
			driver.findElement(By.xpath("//button[text()='OK']")).click();
			FlightSearch.loadhandling(driver);
			
			queryObjects.logStatus(driver, Status.PASS, "POS should change", "POS changed successfully", null);
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "POS should change", "POS not changed", e);
		}
		
		//Sales office Reminder
		try{
			if(driver.findElement(By.xpath("//h2[text()='Login reminder']")).isDisplayed()==true) {
				driver.findElement(By.xpath("//button[text()='Ok']")).click();
				Thread.sleep(2000);
			}
		}
		catch(Exception e) {}
		
		//Navigating to Reservations page
		driver.findElement(By.xpath("//md-menu/button[contains(text(),'Reservations')]")).click();
		driver.findElement(By.xpath("//md-menu-item/button[contains(text(),'Reservations')]")).click();
	}
	catch(Exception e) {}
	
}
catch(Exception e) {
	
}

}

public void OrdVerify(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{

	
	//Navigating to Reservations or Order landing Page

	
		if(driver.findElement(By.xpath("//div[@role='button']/div[text()='Search']")).isDisplayed()==true) {
			queryObjects.logStatus(driver, Status.PASS, "Search tab should be displayed", "Search tab is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Search tab should be displayed", "Search tab is not displayed", null);
		}
		if(driver.findElement(By.xpath("//div[@role='button']/div[text()='Schedule']")).isDisplayed()==true) {
			queryObjects.logStatus(driver, Status.PASS, "Schedule tab should be displayed", "Schedule tab is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Schedule tab should be displayed", "Schedule tab is not displayed", null);
		}
		if(driver.findElement(By.xpath("//div[@role='button']/div[text()='Fare']")).isDisplayed()==true) {
			queryObjects.logStatus(driver, Status.PASS, "Fare tab should be displayed", "Fare tab is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Fare tab should be displayed", "Fare tab is not displayed", null);
		}
		if(driver.findElement(By.xpath("//div[@role='button']/div[text()='Flifo Search']")).isDisplayed()==true) {
			queryObjects.logStatus(driver, Status.PASS, "Flifo Search tab should be displayed", "Flifo Search tab is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Flifo Search tab should be displayed", "Flifo Search tab is not displayed", null);
		}
		if(driver.findElement(By.xpath("//div[@role='button']/div[text()='New Order']")).isDisplayed()==true) {
			queryObjects.logStatus(driver, Status.PASS, "New Order tab should be displayed", "New Order tab is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "New Order tab should be displayed", "New Order tab is not displayed", null);
		}
		if(driver.findElement(By.xpath("//toggle-title/div[text()='Memo Pad']")).isDisplayed()==true) {
			queryObjects.logStatus(driver, Status.PASS, "Memo Pad should be displayed", "Memo Pad is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Memo Pad should be displayed", "Memo Pad is not displayed", null);
		}
		if(driver.findElement(By.xpath("//button[text()='Clear' and @aria-label='Flight']")).isDisplayed()==true) {
			queryObjects.logStatus(driver, Status.PASS, "Clear button should be displayed", "Clear button is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Clear button should be displayed", "Clear button is not displayed", null);
		}
		if(driver.findElement(By.xpath("//form//button[text()='Search' and @aria-label='Search']")).isDisplayed()==true) {
			queryObjects.logStatus(driver, Status.PASS, "Submit button should be displayed", "Submit button is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Submit button should be displayed", "Submit button is not displayed", null);
		}
		if(driver.findElement(By.xpath("//span[text()='Queue widget']")).isDisplayed()==true) {
			queryObjects.logStatus(driver, Status.PASS, "Queue widget should be displayed", "Queue widget is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Queue widget should be displayed", "Queue widget is not displayed", null);
		}
	
	//Validating Search functionality
	
		//Search using PNR
		if(FlightSearch.getTrimTdata(queryObjects.getTestData("PNRSearch")).equalsIgnoreCase("Y")){
			driver.findElement(By.xpath("//input[@aria-label='Search']")).clear();
			driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(queryObjects.getTestData("PNR"));
			driver.findElement(By.xpath("//button[text()='Clear' and @aria-label='Flight']/following-sibling::button")).click();
			FlightSearch.loadhandling(driver);
			OrderPage(driver,queryObjects);
		}

		//Search using Ticket No.
		if(FlightSearch.getTrimTdata(queryObjects.getTestData("TicketSearch")).equalsIgnoreCase("Y")){
			driver.findElement(By.xpath("//input[@aria-label='Search']")).clear();
			driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(queryObjects.getTestData("Ticket#"));
			driver.findElement(By.xpath("//button[text()='Clear' and @aria-label='Flight']/following-sibling::button")).click();
			FlightSearch.loadhandling(driver);
			OrderPage(driver,queryObjects);
		}

		//Search using FF no.
		if(FlightSearch.getTrimTdata(queryObjects.getTestData("FFNumSearch")).equalsIgnoreCase("Y")){
			driver.findElement(By.xpath("//input[@aria-label='Search']")).clear();
			driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(queryObjects.getTestData("FFNum"));
			Thread.sleep(3000);
			driver.findElement(By.xpath("//button[text()='Clear' and @aria-label='Flight']/following-sibling::button")).click();
			FlightSearch.loadhandling(driver);
			String oId = FlightSearch.getTrimTdata(queryObjects.getTestData("OrderID"));
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//md-content//tr/td[text()= '" +oId+ "']")).click();
			FlightSearch.loadhandling(driver);
			OrderPage(driver,queryObjects);
		}
	

}

public void SSRVerify (WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException, Exception{

	if(FlightSearch.getTrimTdata(queryObjects.getTestData("PNRSearch")).equalsIgnoreCase("Y")){
		driver.findElement(By.xpath("//input[@aria-label='Search']")).clear();
		driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(queryObjects.getTestData("PNR"));
		driver.findElement(By.xpath("//button[text()='Clear' and @aria-label='Flight']/following-sibling::button")).click();
		FlightSearch.loadhandling(driver);
		if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]//div[text()='Services']")).size()>0) {
			
		}
		else {
			driver.findElement(By.xpath("//div[text()='Services']")).click();
			FlightSearch.loadhandling(driver);
		}
		if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]//div[text()='Services']")).size()>0) {
			queryObjects.logStatus(driver, Status.PASS, "Should navigate to Service Page", "Navigated to Service Page", null);
			int origin = driver.findElements(By.xpath("//div[@airport-code='item.Origin.AirportCode || item.Origin.LocationCode']")).size();
			int dest = driver.findElements(By.xpath("//div[@airport-code='item.Destination.AirportCode || item.Destination.LocationCode']")).size();
			if(origin>0 && dest>0) {
				queryObjects.logStatus(driver, Status.PASS, "Checking Segments", "Segments are displayed correctly", null);//Segments Validation
				int pax = driver.findElements(By.xpath("//div[contains(@class,'order-passenger-name')]")).size();
				if(pax>0) {
					queryObjects.logStatus(driver, Status.PASS, "Checking Passengers", "Passengers are displayed correctly", null);//Passengers Validation
					
					//SSR search
					
					String ssr = FlightSearch.getTrimTdata(queryObjects.getTestData("SSR"));
					String[] multiSsr= ssr.split(";");
					String ssrcde = null;
					String ssrname = null;
					String ssrcode = null;
					for(int noofSsr = 0; noofSsr<multiSsr.length; noofSsr++) {
						driver.findElement(By.xpath("//label[text()='ssr']/following-sibling::input")).clear();
						driver.findElement(By.xpath("//label[text()='ssr']/following-sibling::input")).click();
						driver.findElement(By.xpath("//label[text()='ssr']/following-sibling::input")).sendKeys(multiSsr[noofSsr]);
						ssrcde = driver.findElement(By.xpath("//md-autocomplete-parent-scope/span[1]/span")).getText();
						ssrname = driver.findElement(By.xpath("//md-autocomplete-parent-scope/span[2]/span")).getText();
						ssrcode = driver.findElement(By.xpath("//md-autocomplete-parent-scope/span[3]/span")).getText();
						if(ssrcde.contains(multiSsr[noofSsr]) || ssrname.contains(multiSsr[noofSsr]) || ssrcode.contains(multiSsr[noofSsr])) {
							queryObjects.logStatus(driver, Status.PASS, "Correct SSR should be searched", "Search Complete", null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Correct SSR should be searched", "Invalid SSR-- Search incomplete", null);
						}
					}
					driver.findElement(By.xpath("//label[text()='ssr']/following-sibling::input")).clear();
					
					if(FlightSearch.getTrimTdata(queryObjects.getTestData("Seatmap")).equalsIgnoreCase("yes")) {

						assignSeat(driver, queryObjects);
						FlightSearch.loadhandling(driver);
						
						//Left Panel
						if(driver.findElement(By.xpath("//toggle-title[text()='Advanced Display']")).isDisplayed())
							queryObjects.logStatus(driver, Status.PASS, "Advanced Display should be displayed", "Advanced Display is displayed", null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Advanced Display should be displayed", "Advanced Display is not displayed", null);
						}
					
						//Seat Map
						WebElement available = driver.findElement(By.xpath("//label[text()='Available']"));
						WebElement taken = driver.findElement(By.xpath("//label[text()='Taken']"));
						WebElement assigned = driver.findElement(By.xpath("//label[text()='Assigned']"));
						WebElement blocked = driver.findElement(By.xpath("//label[text()='Blocked']"));
						WebElement held = driver.findElement(By.xpath("//label[text()='Held']"));
						WebElement unusable = driver.findElement(By.xpath("//label[text()='Unusable']"));
						 
						if(available.isDisplayed() && taken.isDisplayed() && assigned.isDisplayed() && blocked.isDisplayed() && held.isDisplayed() && unusable.isDisplayed()){
							queryObjects.logStatus(driver, Status.PASS, "Seat Map Keys should be displayed", "Seat Map Keys is displayed", null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Seat Map Keys should be displayed", "Seat Map Keys is not displayed", null);
						}
						
						
						//Right Panel
						if(driver.findElements(By.xpath("//input[contains(@class,'seat-number') and contains(@class,'ng-not-empty')]")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Seat Number be displayed", "Seat Number is displayed", null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Seat Number should be displayed", "Seat Number is not displayed", null);
						}
						
						if(driver.findElements(By.xpath("//button[contains(text(),'Submit')]")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Submit should be displayed", "Submit is displayed", null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Submit should be displayed", "Submit is not displayed", null);
						}
						try {
							driver.findElement(By.xpath("//span[text()='Home']")).click();
							queryObjects.logStatus(driver, Status.PASS, "Navigate to Home Page", "Navigated to Home Page", null);
						}
						catch(Exception e){
							queryObjects.logStatus(driver, Status.FAIL, "Navigate to Home Page", "Not navigated to Home Page", e);
						}
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Checking Passengers", "Passengers are not displayed correctly", null);
				}
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "Checking Segments", "Segments are not displayed correctly", null);
			}
			
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Should navigate to Service Page", "Not navigated to Service Page", null);
		}
	}

}

public void TktVerify(WebDriver driver,BFrameworkQueryObjects queryObjects) throws NumberFormatException, IOException, Exception{

	if(FlightSearch.getTrimTdata(queryObjects.getTestData("PNRSearch")).equalsIgnoreCase("Y")){
		driver.findElement(By.xpath("//input[@aria-label='Search']")).clear();
		
		driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(queryObjects.getTestData("PNR"));
		driver.findElement(By.xpath("//button[text()='Clear' and @aria-label='Flight']/following-sibling::button")).click();
		FlightSearch.loadhandling(driver);
		if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]//div[text()='Order']")).size()>0) {
			queryObjects.logStatus(driver, Status.PASS, "Navigate to Order Page", "Navigated to Order Page", null);
			int seg = driver.findElements(By.xpath("//td[@class='flight-name']")).size();
			//Navigate to Tickets Page
			driver.findElement(By.xpath("//div[text()='Tickets']")).click();
			FlightSearch.loadhandling(driver);
			if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]//div[text()='Tickets']")).size()>0) {
				queryObjects.logStatus(driver, Status.PASS, "Navigate to Tickets Page", "Navigated to Tickets Page", null);
				String paxNo = driver.findElement(By.xpath("//pssgui-reservations-home//form/following-sibling::div/div/div[2]/div[1]//span")).getText();
				int noOfPax = Integer.parseInt(paxNo);
				String infNo = driver.findElement(By.xpath("//div[label[text()='INF']]/span")).getText();
				int noOfInf = Integer.parseInt(infNo);
				
					List<WebElement> statusAssigned = driver.findElements(By.xpath("//tbody/tr/td[12]"));
					
					for(WebElement st: statusAssigned) {
						String status = st.getText().trim();
						if(status.equalsIgnoreCase("Open") || status.equalsIgnoreCase("EXCHANGED") || status.equalsIgnoreCase("REFUNDED") || status.equalsIgnoreCase("Voided") || status.equalsIgnoreCase("Flown")) {
							queryObjects.logStatus(driver, Status.PASS, "Status of the segment is: ", status, null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Status of the segment is", "Status is invalid", null);
						}
						
					}
			
					//Verifying tkt details
					int noPax = noOfPax + noOfInf;
					List <WebElement> Passen = driver.findElements(By.xpath("//toggle-title[md-checkbox[@aria-label='Select']]"));
					if(Passen.size()==noPax) {
						queryObjects.logStatus(driver, Status.PASS, "Number of pax should be same as in right panel", "Number of pax is same", null);
					
					
					
					//List<WebElement> Tickts = driver.findElements(By.xpath("//div[contains(@ng-repeat,'PrimaryTickets')]//div[contains(@class,'toggle layout-column flex expand')]//i[@class='icon-arrow-down']"));
					int index = 0;
					int i=0;
					for(WebElement pax: Passen) {
						if(driver.findElements(By.xpath("//span[text()='All Passengers']")).size()>0) {
							driver.findElement(By.xpath("//span[text()='All Passengers']")).click();
							FlightSearch.loadhandling(driver);
						}
						List<WebElement> Tickets = driver.findElements(By.xpath("//div[contains(@ng-repeat,'PrimaryTickets')]//toggle-title/span"));
						Tickets.get(index).click();
						FlightSearch.loadhandling(driver);
						int count = driver.findElements(By.xpath("//div[contains(@ng-repeat,'PrimaryTickets')]//toggle-title/span")).size();
						index = index + count;
						//SEGMENT DETAILS
						//Flight Number
						if(driver.findElements(By.xpath("//tr[@ng-repeat='segment in flightResult.segments']/td[3]")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Flight Segments should be correctly displayed", "Flight Segments is displayed correctly", null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Flight Segments should be correctly displayed", "Flight Segments is not displayed correctly", null);
						}
			
						//Origin
						if(driver.findElements(By.xpath("//pssgui-origin-destination//div[@airport-code='originDestination.origin']")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Origin should be correctly displayed", "Origin is displayed correctly", null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Origin should be correctly displayed", "Origin is not displayed correctly", null);
						}
			
						//Destination
						if(driver.findElements(By.xpath("//pssgui-origin-destination//div[@airport-code='originDestination.destination']")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Destination should be correctly displayed", "Destination is displayed correctly", null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Destination should be correctly displayed", "Destination is not displayed correctly", null);
						}
			
						//Class
						if(driver.findElements(By.xpath("//td[contains(@class,'class-code')]")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Class should be correctly displayed", "Class is displayed correctly", null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Class should be correctly displayed", "Class is not displayed correctly", null);
						}
			
						//Status
						if(driver.findElements(By.xpath("//span[@ng-click='flightResult.onSelectCoupon(segment)']")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Status should be correctly displayed", "Status is displayed correctly", null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Status should be correctly displayed", "Status is not displayed correctly", null);
						}
				
						//PRICING DETAILS
						//Construction
						if(driver.findElements(By.xpath("//td[contains(@class,'construction')]")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Construction should be correctly displayed", "Construction is displayed correctly", null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Construction should be correctly displayed", "Construction is not displayed correctly", null);
						}
						
						//Endorsement
						if(driver.findElements(By.xpath("//td[contains(@class,'construction')]/following-sibling::td/div[1]")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Endorsement should be correctly displayed", "Endorsement is displayed correctly", null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Endorsement should be correctly displayed", "Endorsement is not displayed correctly", null);
						}
				
						//PAYMENT
						//Type
						int type = driver.findElements(By.xpath("//tr[contains(@ng-repeat,'FormOfPayments')]")).size();
						int amt = driver.findElements(By.xpath("//tr[contains(@ng-repeat,'FormOfPayments')]//div/div")).size();
						if(type>0 && amt>0) {
							queryObjects.logStatus(driver, Status.PASS, "Payment Type should be correctly displayed", "Payment Type is displayed correctly", null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Payment Type should be correctly displayed", "Payment Type is not displayed correctly", null);
						}
				
						//ISSUE DETAILS
						if(driver.findElements(By.xpath("//tr[contains(@ng-repeat,'FormOfPayments')]")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Issue details should be correctly displayed", "Issue details is displayed correctly", null);
						}
						else {
							queryObjects.logStatus(driver, Status.FAIL, "Issue details should be correctly displayed", "Issue details is not displayed correctly", null);
						}
						
						//RULE TABS
						//Fare Rules
						try {
							List <WebElement> fareRules = driver.findElements(By.xpath("//div[text()='Fare Rules']"));
							if(fareRules.size()>0) {
								queryObjects.logStatus(driver, Status.PASS, "Fare Rules tab should be displayed", "Fare Rules tab is displayed", null);
								
								for(WebElement fr:fareRules) {
									fr.click();
									FlightSearch.loadhandling(driver);
									queryObjects.logStatus(driver, Status.PASS, "Fare Rules tab should open without errors", "Fare Rules tab opened without errors", null);
									System.out.println(i++);
								}
							}
							else {
								queryObjects.logStatus(driver, Status.FAIL, "Fare Rules tab should be displayed", "Fare Rules tab should be displayed", null);
							}
						}
						catch(Exception e) {
							queryObjects.logStatus(driver, Status.FAIL, "Fare Rules tab should open without errors", "Fare Rules tab should open without errors", e);
						}
						
						//Baggage Rules
						try {
							List <WebElement> baggageRules = driver.findElements(By.xpath("//div[text()='Baggage Rules']"));
							if(baggageRules.size()>0) {
								queryObjects.logStatus(driver, Status.PASS, "Baggage Rules tab should be displayed", "Baggage Rules tab is displayed", null);
								
								for(WebElement br:baggageRules) {
									br.click();
									FlightSearch.loadhandling(driver);
									queryObjects.logStatus(driver, Status.PASS, "Baggage Rules tab should open without errors", "Fare Rules tab opened without errors", null);
									System.out.println(i++);
								}
							}
							else {
								queryObjects.logStatus(driver, Status.FAIL, "Baggage Rules tab should be displayed", "Baggage Rules tab should be displayed", null);
							}
						}
						catch(Exception e) {
							queryObjects.logStatus(driver, Status.FAIL, "Baggage Rules tab should open without errors", "Baggage Rules tab should open without errors", e);
						}
						
						//Reissue Rule
						try {
							List <WebElement> fareRules = driver.findElements(By.xpath("//div[text()='Reissue Rule']"));
							if(fareRules.size()>0) {
								queryObjects.logStatus(driver, Status.PASS, "Reissue Rule tab should be displayed", "Reissue Rule tab is displayed", null);
								
								for(WebElement fr:fareRules) {
									fr.click();
									FlightSearch.loadhandling(driver);
									queryObjects.logStatus(driver, Status.PASS, "Reissue Rule tab should open without errors", "Reissue Rule tab opened without errors", null);
									System.out.println(i++);
								}
							}
							else {
								queryObjects.logStatus(driver, Status.FAIL, "Reissue Rule tab should be displayed", "Reissue Rule tab should be displayed", null);
							}
						}
						catch(Exception e) {
							queryObjects.logStatus(driver, Status.FAIL, "Reissue Rule tab should open without errors", "Reissue Rule tab should open without errors", e);
						}
						
						//Fare Rules
						try {
							List <WebElement> baggageRules = driver.findElements(By.xpath("//toggle-content//div[text()='History']"));
							if(baggageRules.size()>0) {
								queryObjects.logStatus(driver, Status.PASS, "History tab should be displayed", "History tab is displayed", null);
								
								for(WebElement br:baggageRules) {
									br.click();
									FlightSearch.loadhandling(driver);
									queryObjects.logStatus(driver, Status.PASS, "History tab should open without errors", "History tab opened without errors", null);
									System.out.println(i++);
								}
							}
							else {
								queryObjects.logStatus(driver, Status.FAIL, "History tab should be displayed", "History tab should be displayed", null);
							}
						}
						catch(Exception e) {
							queryObjects.logStatus(driver, Status.FAIL, "History tab should open without errors", "History tab should open without errors", e);
						}
						
					}
				
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Number of pax should be same as in right panel", "Number of pax should is not same", null);
				}
					
				driver.findElement(By.xpath("//div[text()='EMD']")).click();//Clicking on EMD Tab
				FlightSearch.loadhandling(driver);
				try {	
					List<WebElement> emdToggle = driver.findElements(By.xpath("//toggle-content//i[@role='button']"));//Clicking on toggle icon and No. 
					
					
					if(driver.findElements(By.xpath("//div[i]//div[@role='button']")).size()>0) {
						for(WebElement et:emdToggle) {//for loop size --- No. of pax
							//for(int j = 0; j<emdToggle.size();j++) {
							if(driver.findElements(By.xpath("//toggle-content//i[contains(@class,'icon-forward') and @role='button']")).size()>0)
							{
								Thread.sleep(5000);
								et.click();
								FlightSearch.loadhandling(driver);
							}
							List<WebElement> emdNo = driver.findElements(By.xpath("//div[i]//div[@role='button']"));
							for(int i=0;i<emdNo.size();i++) {  //for loop size --- No. of EMDs
							//for(int k = 0; k<emdNo.size();k++) {
								emdNo = driver.findElements(By.xpath("//div[i]//div[@role='button']"));
								try {
									emdNo.get(i).click();// EMD per person display page
									FlightSearch.loadhandling(driver);
								}
								catch(Exception e) {
									queryObjects.logStatus(driver, Status.FAIL, "EMD Number should be clicked", "EMD Number not clicked", e);
								}
								
								//Checking if details tab is selected
								List<WebElement> details = driver.findElements(By.xpath("//div[contains(@class,'tab-active')]/div[text()='Details']"));
								if(details.size()==0)
									driver.findElement(By.xpath("//div[text()='Details']")).click();
									FlightSearch.loadhandling(driver);									
								
								WebElement status = driver.findElement(By.xpath("//td/div[@role='button']"));
								String st = status.getText().trim();
								
								//Service Fee Details (Status & Fare Amount)
								if(st.equalsIgnoreCase("Open") || st.equalsIgnoreCase("EXCHANGED") || st.equalsIgnoreCase("REFUNDED") || st.equalsIgnoreCase("Voided") || st.equalsIgnoreCase("Flown")) {
									queryObjects.logStatus(driver, Status.PASS, "Status of the segment is: ", st, null);
								}
								else 
									queryObjects.logStatus(driver, Status.FAIL, "Status of the segment is", "Status is invalid", null);
								
								if(driver.findElements(By.xpath("//div[@action='emd-price']//td/div[contains(@model,'price.total')]")).size()>0)
									queryObjects.logStatus(driver, Status.PASS, "EMD Fare should be displayed", "EMD fare amount is displayed", null);
								else 
									queryObjects.logStatus(driver, Status.FAIL, "EMD Fare should be displayed", "EMD fare amount is not displayed", null);
						
							
								//EMD Details (Tax Code, Tax Amount & Currency)
								//Tax Code
								if(driver.findElements(By.xpath("//table[thead[tr[th[text()='Tax Code']]]]//tbody/tr[2]/td[2]")).size()>0)
									queryObjects.logStatus(driver, Status.PASS, "Tax Code should be displayed", "Tax Code is displayed", null);
								else 
									queryObjects.logStatus(driver, Status.FAIL, "Tax Code should be displayed", "Tax Code is not displayed", null);
								
								//Tax Amount
								if(driver.findElements(By.xpath("//table[thead[tr[th[text()='Tax Code']]]]//tbody/tr[2]/td[3]")).size()>0)
									queryObjects.logStatus(driver, Status.PASS, "Tax Amount should be displayed", "Tax amount is displayed", null);
								else 
									queryObjects.logStatus(driver, Status.FAIL, "Tax Amount should be displayed", "Tax amount is not displayed", null);
								
								//Currency
								if(driver.findElements(By.xpath("//table[thead[tr[th[text()='Tax Code']]]]//tbody/tr[2]/td[4]")).size()>0)
									queryObjects.logStatus(driver, Status.PASS, "Currency should be displayed", "Currency is displayed", null);
								else 
									queryObjects.logStatus(driver, Status.FAIL, "Currency should be displayed", "Currency is not displayed", null);
								
								
								//Payment Details
								if(driver.findElements(By.xpath("//div[text()= 'Payment']")).size()>0) {
									if(driver.findElements(By.xpath("//div[text()= 'Payment']/following-sibling::table//td[1]")).size()>0)
										queryObjects.logStatus(driver, Status.PASS, "Payment Type be displayed", "Payment Type is displayed", null);
									else 
										queryObjects.logStatus(driver, Status.FAIL, "Payment Type should be displayed", "Payment Type is not displayed", null);
									
									
								}
								
								//Navigating to EMD History tab
								List<WebElement> history = driver.findElements(By.xpath("//div[div[text()= 'Details']]/following-sibling::div[@role='button' and div[text()='History']]"));
								if(history.size()==1) {
									history.get(0).click();
									FlightSearch.loadhandling(driver);
									queryObjects.logStatus(driver, Status.PASS, "Should Navigate to History Page", "Navigated to History Page", null);
								}
								//Returning to EMD tab										
								if(driver.findElements(By.xpath("//span[text()='All Passengers']")).size()>0) {
									driver.findElement(By.xpath("//span[text()='All Passengers']")).click();
									FlightSearch.loadhandling(driver);
								}
							
								WebElement icon = driver.findElement(By.xpath("//toggle-content//i[contains(@class,'icon-arrow-down')]"));
								try{
									if(icon.isDisplayed()){
										icon.click();
										FlightSearch.loadhandling(driver);
									}
								}
								catch(Exception e) {
									queryObjects.logStatus(driver, Status.FAIL, "Check if icon is arrow down", "No arrow-down icon available", e);
								}
								
								
								
							}
					}
				}
			}
				catch(Exception e) {
					
				}
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "Navigate to Tickets Page", "Not navigated to Tickets Page", null);
			}
		}
	}

}

public void PaxVerify(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException, Exception{

	if(FlightSearch.getTrimTdata(queryObjects.getTestData("PNRSearch")).equalsIgnoreCase("Y")){
		driver.findElement(By.xpath("//input[@aria-label='Search']")).clear();
		
		driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(queryObjects.getTestData("PNR"));
		driver.findElement(By.xpath("//button[text()='Clear' and @aria-label='Flight']/following-sibling::button")).click();
		FlightSearch.loadhandling(driver);
		if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]//div[text()='Order']")).size()>0) {
			queryObjects.logStatus(driver, Status.PASS, "Navigate to Order Page", "Navigated to Order Page", null);
			if(driver.findElements(By.xpath("//div[text()='Passengers']")).size()>0) {
				driver.findElement(By.xpath("//div[text()='Passengers']")).click();
				FlightSearch.loadhandling(driver);
				
				queryObjects.logStatus(driver, Status.PASS, "Navigate to Passenger Page", "Navigated to Passenger Page", null);
	
				//Personal Information
				if(driver.findElements(By.xpath("//div[text()= 'Personal Information']")).size()>0) 
					queryObjects.logStatus(driver, Status.PASS, "Personal Information tab be displayed", "Personal Information tab is displayed", null);
				else 
					queryObjects.logStatus(driver, Status.FAIL, "Personal Information tab should be displayed", "Personal Information tab is not displayed", null);
				//Travel document
				if(driver.findElements(By.xpath("//div[text()= 'Travel document']")).size()>0) 
					queryObjects.logStatus(driver, Status.PASS, "Travel document tab be displayed", "Travel document tab is displayed", null);
				else 
					queryObjects.logStatus(driver, Status.FAIL, "Travel document tab should be displayed", "Travel document tab is not displayed", null);
				//Address
				if(driver.findElements(By.xpath("//div[text()= 'Address']")).size()>0) 
					queryObjects.logStatus(driver, Status.PASS, "Address tab be displayed", "Address tab is displayed", null);
				else 
					queryObjects.logStatus(driver, Status.FAIL, "Address tab should be displayed", "Address tab is not displayed", null);
				//Frequent Flyer
				if(driver.findElements(By.xpath("//div[text()= 'Frequent Flyer']")).size()>0) 
					queryObjects.logStatus(driver, Status.PASS, "Frequent Flyer tab be displayed", "Frequent Flyer tab is displayed", null);
				else 
					queryObjects.logStatus(driver, Status.FAIL, "Frequent Flyer tab should be displayed", "Frequent Flyer tab is not displayed", null);
			
			
				if(driver.findElements(By.xpath("//button[text()='Edit' and @disabled='disabled']")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Edit button should be disabled", "Edit button is disabled", null);
				else 
					queryObjects.logStatus(driver, Status.FAIL, "Edit button should be disabled", "Edit button is not disabled", null);
			
				List <WebElement> Pax = driver.findElements(By.xpath("//div[contains(@ng-repeat,'Fullname')]/div/div[1]/span[@role='button']"));
				for(int i = 0; i<Pax.size();i++) {
					//Pax = driver.findElements(By.xpath("//div[contains(@ng-repeat,'Fullname')]/div/div[1]/span[@role='button']"));
					Pax.get(i).click();
					String PName = Pax.get(i).getText();
					String[] name = PName.split(", ");
					try {
						//Validating Personal Information
						//Validating Last Name
						String paxLm = driver.findElement(By.xpath("//input[@aria-label='Last Name']")).getAttribute("value");
						if(name[0].equalsIgnoreCase(paxLm))
							queryObjects.logStatus(driver, Status.PASS, "Last Name should be displayed correctly", "Last Name displayed correctly", null);
						else 
							queryObjects.logStatus(driver, Status.FAIL, "Last Name should be displayed correctly", "Last Name not displayed correctly", null);
					
						//Validating First Name
						String paxFm = driver.findElement(By.xpath("//input[@aria-label='First Name']")).getAttribute("value");
						if(name[1].equalsIgnoreCase(paxFm))
							queryObjects.logStatus(driver, Status.PASS, "First Name should be displayed correctly", "First Name displayed correctly", null);
						else 
							queryObjects.logStatus(driver, Status.FAIL, "First Name should be displayed correctly", "First Name not displayed correctly", null);
						
						//Displaying Passenger type
						String paxType = driver.findElement(By.xpath("//md-select-value//div/span")).getText();								
						queryObjects.logStatus(driver, Status.PASS, "Passenger Type: ", paxType, null);
					
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Personal Information should be displayed correctly", "Personal Information not displayed correctly", e);
					}
					try {
						//Validating Travel document
						driver.findElement(By.xpath("//div[text()= 'Travel document']")).click();
					
						//Gender
						String gender = driver.findElement(By.xpath("//md-select-value/span/div/div")).getText();
						queryObjects.logStatus(driver, Status.PASS, "Gender of the pax is: ", gender, null);
					
						//DoB
						String dob = driver.findElement(By.xpath("//md-select-value//div//ancestor::pssgui-menu/following-sibling::pssgui-date-time")).getText();
						queryObjects.logStatus(driver, Status.PASS, "Gender of the pax is: ", dob, null);
					
						//Nationality
						List<WebElement> Nat = driver.findElements(By.xpath("//label[text()='Nationality']/following-sibling::input"));
						if(Nat.size()>0) {
							String nation = Nat.get(0).getText();
							queryObjects.logStatus(driver, Status.PASS, "Nationality of the pax is: ", nation, null);
						}
						else
							queryObjects.logStatus(driver, Status.WARNING, "Nationality of the pax should be displayed", "Nationality of the pax is not displayed", null);
					
						//Country or State
						String cont = driver.findElement(By.xpath("//label[text()='Country or State Code']/following-sibling::input")).getText();
						if(!cont.equals(null))
							queryObjects.logStatus(driver, Status.PASS, "Country or State of the pax is: ", cont, null);
						else
							queryObjects.logStatus(driver, Status.WARNING, "Country or State of the pax should be displayed", "Country or State of the pax is not displayed", null);
					
						//FOID details
						String docType = driver.findElement(By.xpath("//label[text()='Document Name']//following-sibling::md-select//span/div")).getText();
						queryObjects.logStatus(driver, Status.PASS, "Document Type of the pax is: ", docType, null);
					
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Travel document should be displayed correctly", "Travel document not displayed correctly", e);
					}
					//Address
					try {
						driver.findElement(By.xpath("//div[text()= 'Address']")).click();
						
						//Country
						String cont = driver.findElement(By.xpath("//label[text()='Country']/following-sibling::input")).getText();
						if(!cont.equals(null))
							queryObjects.logStatus(driver, Status.PASS, "Country of the pax is: ", cont, null);
						else
							queryObjects.logStatus(driver, Status.WARNING, "Country of the pax should be displayed", "Country of the pax is not displayed", null);
					
						//Street Address
						String stAdd = driver.findElement(By.xpath("//label[text()='Street Address']/following-sibling::input")).getText();
						if(!stAdd.equals(null))
							queryObjects.logStatus(driver, Status.PASS, "Street Address of the pax is: ", cont, null);
						else
							queryObjects.logStatus(driver, Status.WARNING, "Street Address of the pax should be displayed", "Street Address of the pax is not displayed", null);
					
						//City
						String city = driver.findElement(By.xpath("//label[span[text()='City']]/following-sibling::input")).getText();
						if(!cont.equals(null))
							queryObjects.logStatus(driver, Status.PASS, "City of the pax is: ", cont, null);
						else
							queryObjects.logStatus(driver, Status.WARNING, "City of the pax should be displayed", "City of the pax is not displayed", null);
					
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Address details should be displayed correctly", "Address details not displayed correctly", e);
					}
				
					//Frequent Flyer
					try {
						driver.findElement(By.xpath("//div[text()= 'Frequent Flyer']")).click();
						String ffp = driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).getText();
						if(!ffp.equals(null))
							queryObjects.logStatus(driver, Status.PASS, "City of the pax is: ", ffp, null);
						else
							queryObjects.logStatus(driver, Status.WARNING, "City of the pax should be displayed", "City of the pax is not displayed", null);
											
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Navigate to Frequent Flyer Tab", "Did not navigate to Frequent Flyer Tab", e);
					}	
				}
			}
			else
				queryObjects.logStatus(driver, Status.FAIL, "Navigate to Passenger Page", "Not Navigated to Passenger Page", null);
		}
		else 
			queryObjects.logStatus(driver, Status.FAIL, "Navigate to Order Page", "Not Navigated to Order Page", null);
	}

}

public void SumVerify(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException, Exception{

	if(FlightSearch.getTrimTdata(queryObjects.getTestData("PNRSearch")).equalsIgnoreCase("Y")){
		driver.findElement(By.xpath("//input[@aria-label='Search']")).clear();
		
		driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(queryObjects.getTestData("PNR"));
		driver.findElement(By.xpath("//button[text()='Clear' and @aria-label='Flight']/following-sibling::button")).click();
		FlightSearch.loadhandling(driver);
		if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]//div[text()='Order']")).size()>0) {
			queryObjects.logStatus(driver, Status.PASS, "Navigate to Order Page", "Navigated to Order Page", null);
			int seg = driver.findElements(By.xpath("//td[@class='flight-name']")).size();
			
			//Navigate to Summary Tab and Validate
			driver.findElement(By.xpath("//div[text()='Summary']")).click();//Navigate to Summary Tab
			FlightSearch.loadhandling(driver);
			
			//Itinerary Details
			List<WebElement> itinerary = driver.findElements(By.xpath("//div[i[contains(@class,'icon-arrow-down')]]//span[text()='Itinerary']"));
			if(itinerary.size()>0)
				queryObjects.logStatus(driver, Status.PASS, "Itinerary details should be displayed", "Itinerary details is displayed", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Itinerary details should be displayed", "Itinerary details is displayed", null);
							
			//Email Itinerary button
			List<WebElement> email = driver.findElements(By.xpath("//button[text()='Email Itinerary']"));
			if(email.size()>0) {
				queryObjects.logStatus(driver, Status.PASS, "Email Itinerary button should be displayed", "Email Itinerary button is displayed", null);
				if(driver.findElements(By.xpath("//button[text()='Email Itinerary' and @disabled='disabled']")).size()<=0)
					queryObjects.logStatus(driver, Status.PASS, "Email Itinerary button should be enabled", "Email Itinerary button is enabled", null);
				else
					queryObjects.logStatus(driver, Status.WARNING, "Email Itinerary button should be enabled", "Email Itinerary button is not enabled", null);
			}
			else
				queryObjects.logStatus(driver, Status.FAIL, "Email Itinerary button should be displayed", "Email Itinerary button is not displayed", null);
			
			//View button
			List<WebElement> view = driver.findElements(By.xpath("//button[text()='View']"));
			if(view.size()>0) {
				queryObjects.logStatus(driver, Status.PASS, "View button should be displayed", "View button is displayed", null);
				if(driver.findElements(By.xpath("//button[text()='View' and @disabled='disabled']")).size()<=0)
					queryObjects.logStatus(driver, Status.PASS, "View button should be enabled", "View button is enabled", null);
				else
					queryObjects.logStatus(driver, Status.WARNING, "View button should be enabled", "View button is not enabled", null);
			}
			else
				queryObjects.logStatus(driver, Status.FAIL, "View button should be displayed", "View button is not displayed", null);
			
			//ITINERARY DETAILS
			
			//Flight Number
			if(driver.findElements(By.xpath("//toggle-content//table[2]//tr/td[1]")).size()==seg)
				queryObjects.logStatus(driver, Status.PASS, "Flight Number should be displayed correctly", "Flight Number is displayed correctly", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Flight Number should be displayed correctly", "Flight Number is not displayed correctly", null);
			
			//Date
			if(driver.findElements(By.xpath("//toggle-content//table[2]//tr/td[2]")).size()==seg)
				queryObjects.logStatus(driver, Status.PASS, "Date should be displayed correctly", "Date is displayed correctly", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Date should be displayed correctly", "Date is not displayed correctly", null);
			
			//From
			if(driver.findElements(By.xpath("//toggle-content//table[2]//tr/td[3]")).size()==seg)
				queryObjects.logStatus(driver, Status.PASS, "Origin should be displayed correctly", "Origin is displayed correctly", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Origin should be displayed correctly", "Origin is not displayed correctly", null);
			
			//To
			if(driver.findElements(By.xpath("//toggle-content//table[2]//tr/td[4]")).size()==seg)
				queryObjects.logStatus(driver, Status.PASS, "Destination should be displayed correctly", "Destination is displayed correctly", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Destination should be displayed correctly", "Destination is not displayed correctly", null);
			
			//Class
			if(driver.findElements(By.xpath("//toggle-content//table[2]//tr/td[5]")).size()==seg)
				queryObjects.logStatus(driver, Status.PASS, "Class should be displayed correctly", "Class is displayed correctly", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Class should be displayed correctly", "Class is not displayed correctly", null);
			
			//Status
			if(driver.findElements(By.xpath("//toggle-content//table[2]//tr/td[6]")).size()==seg)
				queryObjects.logStatus(driver, Status.PASS, "Status should be displayed correctly", "Status is displayed correctly", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Status should be displayed correctly", "Status is not displayed correctly", null);
			
			//PASSENGER DETAILS
		
				driver.findElement(By.xpath("//div[div[toggle-title[span[text()='Passengers']]]]/i")).click();
				FlightSearch.loadhandling(driver);
			
				//Names
				List<WebElement> names = driver.findElements(By.xpath("//div[contains(@ng-repeat,'travelerDetails.passengers')]/div[contains(@class,'traveler-name')]"));
				for(int i=0;i<names.size();i++) {
					String name = names.get(i).getText();
					queryObjects.logStatus(driver, Status.PASS, "Passenger Name is:", name, null);						
				}
			
				//Reserved seats
				String noOfPaxS = driver.findElement(By.xpath("//pssgui-reservations-home//form/following-sibling::div/div/div[2]/div[1]//span")).getText();
				int noOfPax = Integer.parseInt(noOfPaxS);
				
				int totSeats = noOfPax*seg;
				if(driver.findElements(By.xpath("//div[@ng-show='seat.isVisible']")).size()==totSeats)
					queryObjects.logStatus(driver, Status.PASS, "Reserved Seats should be displayed correctly", "Reserved Seats is displayed correctly", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Reserved Seats should be displayed correctly", "Reserved Seats is not displayed correctly", null);
			
				driver.findElement(By.xpath("//div[div[toggle-title[span[text()='Passengers']]]]/i")).click();
				FlightSearch.loadhandling(driver);
			
			
			//BAGGAGE ALLOWANCE
			
				int namesze = driver.findElements(By.xpath("//div[contains(@ng-repeat,'travelerDetails.passengers')]/div[contains(@class,'traveler-name')]")).size();
				if(driver.findElements(By.xpath("//div[@ng-show='seat.isVisible']")).size()==namesze)
					queryObjects.logStatus(driver, Status.PASS, "Reserved Seats should be displayed correctly", "Reserved Seats is displayed correctly", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Reserved Seats should be displayed correctly", "Reserved Seats is not displayed correctly", null);
		
			
			
			//FARE RULES
			try {
				driver.findElement(By.xpath("//span[text()='Fare Rules']")).click();
				FlightSearch.loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Expand Fare Rules", "Fare Rules expanded successfully", null);
			}
			catch(Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Expand Fare Rules", "Fare Rules did not expand", e);
			}
			
			
		}
	}

}

public void Homebtn(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException, Exception{

	if(FlightSearch.getTrimTdata(queryObjects.getTestData("PNRSearch")).equalsIgnoreCase("Y")){
		driver.findElement(By.xpath("//input[@aria-label='Search']")).clear();
		
		driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(queryObjects.getTestData("PNR"));
		driver.findElement(By.xpath("//button[text()='Clear' and @aria-label='Flight']/following-sibling::button")).click();
		FlightSearch.loadhandling(driver);
		if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]//div[text()='Order']")).size()>0) {
			queryObjects.logStatus(driver, Status.PASS, "Navigate to Order Page", "Navigated to Order Page", null);
			 //Click on Home Button
			driver.findElement(By.xpath("//span[text()='Home']")).click();
			FlightSearch.loadhandling(driver);
			
		}
	}

}

public void SchVerify(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{

	driver.findElement(By.xpath("//div[text()='Schedule']")).click();
	FlightSearch.loadhandling(driver);
	if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]/div[text()='Schedule']")).size()>0) {
		queryObjects.logStatus(driver, Status.PASS, "Navigate to Schedule Page", "Navigated to Schedule Page", null);
	
		//From
		driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).click();
		driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).clear();
		String from=FlightSearch.getTrimTdata(queryObjects.getTestData("From"));
		driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).sendKeys(from);
		driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).sendKeys(Keys.ENTER);
	
	
		//To
		driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).click();
		driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).clear();
		String to=FlightSearch.getTrimTdata(queryObjects.getTestData("To"));
		driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).sendKeys(to);
		driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).sendKeys(Keys.ENTER);
	
		//Entering segment Date
		String sgetDate=FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
		int getDays=0;
		if (sgetDate.isEmpty())
			getDays=30;
		else
			getDays=Integer.parseInt(sgetDate);

		Calendar calc = Calendar.getInstance();
		calc.add(Calendar.DATE, +getDays);
		String timeStampd = new SimpleDateFormat("MM/dd/yyyy").format(calc.getTime());
		WebElement DateXpath = driver.findElement(By.xpath("//pssgui-autocomplete/following-sibling::pssgui-date-time//div[contains(@class,'datepicker')]//input"));
		if (getDays!=0) {
			DateXpath.clear();
			DateXpath.sendKeys(timeStampd);
		}
		Thread.sleep(1000);
	
		//Search Button
		WebElement searchbtn = driver.findElement(By.xpath("//button[text()='Search' and contains(@ng-disabled,'invalid')]"));
		searchbtn.click();
		FlightSearch.loadhandling(driver);
		int flag = 0;
		try {
			//Flight
			List<WebElement> flight = driver.findElements(By.xpath("//div[table[thead[tr[th[text()='Flights']]]]]/following-sibling::div//td[1]"));
						
			//EQP
			List<WebElement> equip = driver.findElements(By.xpath("//div[table[thead[tr[th[text()='Flights']]]]]/following-sibling::div//td[3]"));
		
			//Origin/Departure Time
			List<WebElement> orig = driver.findElements(By.xpath("//div[table[thead[tr[th[text()='Flights']]]]]/following-sibling::div//td[4]"));
		
			//Destination/Arrival Time
			List<WebElement> dest = driver.findElements(By.xpath("//div[table[thead[tr[th[text()='Flights']]]]]/following-sibling::div//td[6]"));
		
			//Stops
			List<WebElement> stops = driver.findElements(By.xpath("//div[table[thead[tr[th[text()='Flights']]]]]/following-sibling::div//td[7]"));
		
			queryObjects.logStatus(driver, Status.PASS, "Schedule Search should be performed", "Schedule Search is performed successfully", null);
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Schedule Search should be performed", "Schedule Search not performed", e);
		}
	
	
	//Entering invalid Date
		String sgetinvDate=FlightSearch.getTrimTdata(queryObjects.getTestData("invDay"));
		int getinvDays=0;
		if (sgetinvDate.isEmpty())
			getinvDays=30;
		else
			getinvDays=Integer.parseInt(sgetinvDate);

		Calendar calc1 = Calendar.getInstance();
		calc.add(Calendar.DATE, +getinvDays);
		String timeStampd1 = new SimpleDateFormat("MM/dd/yyyy").format(calc.getTime());
		DateXpath = driver.findElement(By.xpath("//pssgui-autocomplete/following-sibling::pssgui-date-time//div[contains(@class,'datepicker')]//input"));
		if (getinvDays!=0) {
			DateXpath.clear();
			DateXpath.sendKeys(timeStampd1);
		}
		Thread.sleep(1000);
	
		//Search Button
		searchbtn = driver.findElement(By.xpath("//button[text()='Search' and contains(@ng-disabled,'invalid')]"));
		searchbtn.click();
		FlightSearch.loadhandling(driver);
	
	
	//Schedule Frequency Search
	try {
		driver.findElement(By.xpath("//input[@aria-label='Flight']")).click();
		driver.findElement(By.xpath("//input[@aria-label='Flight']")).clear();
		String fltNum=FlightSearch.getTrimTdata(queryObjects.getTestData("flghtNum"));
		driver.findElement(By.xpath("//input[@aria-label='Flight']")).sendKeys(fltNum);
		driver.findElement(By.xpath("//input[@aria-label='Flight']")).sendKeys(Keys.ENTER);
		
		//Search Button
		searchbtn = driver.findElement(By.xpath("//button[text()='Search' and contains(@ng-disabled,'flightNumber')]"));
		searchbtn.click();
		FlightSearch.loadhandling(driver);
		
		//Validating
		if(driver.findElements(By.xpath("//span[text()='Schedule Frequency Search Results :']")).size()>0)
			queryObjects.logStatus(driver, Status.PASS, "Validate Schedule Frequency Search using flight number", "Schedule Frequency Search using flight number successful", null);
		
		
	}
	catch(Exception e) {
		queryObjects.logStatus(driver, Status.FAIL, "Validate Schedule Frequency Search using flight number", "Schedule Frequency Search using flight number not successful", e);
	}

}

}

public void fareVerify(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException, Exception{

	driver.findElement(By.xpath("//div[text()='Fare']")).click();
	FlightSearch.loadhandling(driver);
	//From
	driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).click();
	driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).clear();
	String from=FlightSearch.getTrimTdata(queryObjects.getTestData("From"));
	driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).sendKeys(from);
	driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).sendKeys(Keys.ENTER);


	//To
	driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).click();
	driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).clear();
	String to=FlightSearch.getTrimTdata(queryObjects.getTestData("To"));
	driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).sendKeys(to);
	driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).sendKeys(Keys.ENTER);

	//Entering segment Date
	String sgetDate=FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
	int getDays=0;
	if (sgetDate.isEmpty())
		getDays=30;
	else
		getDays=Integer.parseInt(sgetDate);

	Calendar calc = Calendar.getInstance();
	calc.add(Calendar.DATE, +getDays);
	String timeStampd = new SimpleDateFormat("MM/dd/yyyy").format(calc.getTime());
	WebElement DateXpath = driver.findElement(By.xpath("//div[contains(@class,'datepicker')]/input"));
	if (getDays!=0) {
		DateXpath.clear();
		DateXpath.sendKeys(timeStampd);
	}
	Thread.sleep(1000);

	//Search Button
	WebElement searchbtn = driver.findElement(By.xpath("//button[@aria-label='Search']"));
	searchbtn.click();
	FlightSearch.loadhandling(driver);

	//Checking Fare Details
	if(driver.findElements(By.xpath("//table[thead[tr[th[text()='Carrier']]]]//tr[1]")).size()>0)
		queryObjects.logStatus(driver, Status.PASS, "Fare Details should be displayed", "Fare Details is displayed", null);
	else
		queryObjects.logStatus(driver, Status.PASS, "Fare Details should be displayed", "Fare Details is not displayed", null);

	List<WebElement> arrowIcon = driver.findElements(By.xpath("//div[toggle-title]/preceding-sibling::i[contains(@class,'icon-forward') and @role='button']"));
	if(arrowIcon.size()>0) {
		arrowIcon.get(0).click();
		FlightSearch.loadhandling(driver);
		
		//Fare Display by Date
		if(driver.findElements(By.xpath("//div[toggle-title[span[text()='Fare Display by Date']]]")).size()>0) {
			driver.findElement(By.xpath("//div[toggle-title[span[text()='Fare Display by Date']]]/preceding-sibling::i")).click();
			FlightSearch.loadhandling(driver);
			try {
				//Entering segment Date
				String sgettktDate=FlightSearch.getTrimTdata(queryObjects.getTestData("tktDate"));
				int gettktDays=0;
				if (sgettktDate.isEmpty())
					gettktDays=30;
				else
					gettktDays=Integer.parseInt(sgettktDate);

				Calendar calc2 = Calendar.getInstance();
				calc2.add(Calendar.DATE, +gettktDays);
				String timeStampd2 = new SimpleDateFormat("MM/dd/yyyy").format(calc.getTime());
				WebElement DateXpath2 = driver.findElement(By.xpath("//div[label[span[text()='Ticket Date']]]/following-sibling::md-datepicker/div[contains(@class,'datepicker')]/input"));
				if (gettktDays!=0) {
					DateXpath2.clear();
					DateXpath2.sendKeys(timeStampd2);
				}
				Thread.sleep(1000);
	

				//Search Button
				WebElement searchbt = driver.findElement(By.xpath("//button[@aria-label='Search']"));
				searchbt.click();
				FlightSearch.loadhandling(driver);
			
				queryObjects.logStatus(driver, Status.PASS, "Should Search by Date without Error", "Search by Date successful without Error", null);

				//Checking Fare Details
				if(driver.findElements(By.xpath("//table[thead[tr[th[text()='Carrier']]]]//tr[1]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Fare Details should be displayed", "Fare Details is displayed", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Fare Details should be displayed", "Fare Details is not displayed", null);
			
			}
			catch(Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Should Search by Date without Error", "Search by Date not successful", e);
			}
								
		}
		
		//Passenger Reduction Type
		try {
			if(driver.findElements(By.xpath("//div[toggle-title[span[text()='Passenger Reduction Type']]]")).size()>0) {
				driver.findElement(By.xpath("//div[toggle-title[span[text()='Passenger Reduction Type']]]/preceding-sibling::i")).click();
				FlightSearch.loadhandling(driver);
			
				//Dropdown Selection
				String paxRedType = FlightSearch.getTrimTdata(queryObjects.getTestData("paxRedType"));
				driver.findElement(By.xpath("//pssgui-menu[@action='dropdown'][1]//md-select")).click();
				driver.findElement(By.xpath("//div[5]//md-select-menu//md-option[@value='"+paxRedType+"']//span")).click();
				FlightSearch.loadhandling(driver);
				
				//Search Button
				WebElement searchbt = driver.findElement(By.xpath("//button[@aria-label='Search']"));
				searchbt.click();
				FlightSearch.loadhandling(driver);
			
				queryObjects.logStatus(driver, Status.PASS, "Should select Passenger Reduction Type", "Selected Passenger Reduction Type Successful", null);

				//Checking Fare Details
				if(driver.findElements(By.xpath("//table[thead[tr[th[text()='Carrier']]]]//tr[1]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Passenger Reduction Type should be selected", "Passenger Reduction Type is selected", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Passenger Reduction Type should be selected", "Passenger Reduction Type is not selected", null);
			
			}
			
		
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Should select Passenger Reduction Type", "Selected Passenger Reduction Type not Successful", e);
			
		}
		
		//Fare Restrictions
		try {
			if(driver.findElements(By.xpath("//div[toggle-title[span[text()='Fare Restrictions']]]")).size()>0) {
				driver.findElement(By.xpath("//div[toggle-title[span[text()='Fare Restrictions']]]/preceding-sibling::i")).click();
				FlightSearch.loadhandling(driver);
				
				//Refundable
				driver.findElement(By.xpath("//div[span[text()='Refundable']]")).click();
				FlightSearch.loadhandling(driver);

				//Search Button
				WebElement searchbtn1 = driver.findElement(By.xpath("//button[@aria-label='Search']"));
				searchbtn1.click();
				FlightSearch.loadhandling(driver);
			
				queryObjects.logStatus(driver, Status.PASS, "Should select Fare Restriction Type", "Selected Fare Restriction Type Successful", null);

				//Checking Fare Details
				if(driver.findElements(By.xpath("//table[thead[tr[th[text()='Carrier']]]]//tr[1]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Fare Restriction type should display fare details", "Fare details displayed", null);
				else if(driver.findElements(By.xpath("//div[text()='No records found']")).size()>0)
					queryObjects.logStatus(driver, Status.WARNING, "Fare Restriction type should display fare details", "No records found", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Fare Restriction type should display fare details", "Fare details is not displayed", null);
				
				driver.findElement(By.xpath("//div[span[text()='Refundable']]")).click();
								

				//No penalties
				driver.findElement(By.xpath("//div[span[text()='No penalties']]")).click();
				FlightSearch.loadhandling(driver);

				//Search Button
				WebElement searchbtn2 = driver.findElement(By.xpath("//button[@aria-label='Search']"));
				searchbtn2.click();
				FlightSearch.loadhandling(driver);
			
				queryObjects.logStatus(driver, Status.PASS, "Should select Fare Restriction Type", "Selected Fare Restriction Type Successful", null);

				//Checking Fare Details
				if(driver.findElements(By.xpath("//table[thead[tr[th[text()='Carrier']]]]//tr[1]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Fare Restriction type should display fare details", "Fare details displayed", null);
				else if(driver.findElements(By.xpath("//div[text()='No records found']")).size()>0)
					queryObjects.logStatus(driver, Status.WARNING, "Fare Restriction type should display fare details", "No records found", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Fare Restriction type should display fare details", "Fare details is not displayed", null);
				
				driver.findElement(By.xpath("//div[span[text()='No penalties']]")).click();
								

				//With penalties
				driver.findElement(By.xpath("//div[span[text()='With penalties']]")).click();
				FlightSearch.loadhandling(driver);

				//Search Button
				WebElement searchbtn3 = driver.findElement(By.xpath("//button[@aria-label='Search']"));
				searchbtn1.click();
				FlightSearch.loadhandling(driver);
			
				queryObjects.logStatus(driver, Status.PASS, "Should select Fare Restriction Type", "Selected Fare Restriction Type Successful", null);

				//Checking Fare Details
				if(driver.findElements(By.xpath("//table[thead[tr[th[text()='Carrier']]]]//tr[1]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Fare Restriction type should display fare details", "Fare details displayed", null);
				else if(driver.findElements(By.xpath("//div[text()='No records found']")).size()>0)
					queryObjects.logStatus(driver, Status.WARNING, "Fare Restriction type should display fare details", "No records found", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Fare Restriction type should display fare details", "Fare details is not displayed", null);
				
				driver.findElement(By.xpath("//div[span[text()='With penalties']]")).click();
								

				//No advanced purchase
				driver.findElement(By.xpath("//div[span[text()='No advanced purchase']]")).click();
				FlightSearch.loadhandling(driver);

				//Search Button
				WebElement searchbtn4 = driver.findElement(By.xpath("//button[@aria-label='Search']"));
				searchbtn2.click();
				FlightSearch.loadhandling(driver);
			
				queryObjects.logStatus(driver, Status.PASS, "Should select Fare Restriction Type", "Selected Fare Restriction Type Successful", null);

				//Checking Fare Details
				if(driver.findElements(By.xpath("//table[thead[tr[th[text()='Carrier']]]]//tr[1]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Fare Restriction type should display fare details", "Fare details displayed", null);
				else if(driver.findElements(By.xpath("//div[text()='No records found']")).size()>0)
					queryObjects.logStatus(driver, Status.WARNING, "Fare Restriction type should display fare details", "No records found", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Fare Restriction type should display fare details", "Fare details is not displayed", null);
				
				driver.findElement(By.xpath("//div[span[text()='No advanced purchase']]")).click();
						

				//No minimum stay
				driver.findElement(By.xpath("//div[span[text()='No minimum stay']]")).click();
				FlightSearch.loadhandling(driver);

				//Search Button
				WebElement searchbtn5 = driver.findElement(By.xpath("//button[@aria-label='Search']"));
				searchbtn1.click();
				FlightSearch.loadhandling(driver);
			
				queryObjects.logStatus(driver, Status.PASS, "Should select Fare Restriction Type", "Selected Fare Restriction Type Successful", null);

				//Checking Fare Details
				if(driver.findElements(By.xpath("//table[thead[tr[th[text()='Carrier']]]]//tr[1]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Fare Restriction type should display fare details", "Fare details displayed", null);
				else if(driver.findElements(By.xpath("//div[text()='No records found']")).size()>0)
					queryObjects.logStatus(driver, Status.WARNING, "Fare Restriction type should display fare details", "No records found", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Fare Restriction type should display fare details", "Fare details is not displayed", null);
				
				driver.findElement(By.xpath("//div[span[text()='No minimum stay']]")).click();
								

				//No maximum stay
				driver.findElement(By.xpath("//div[span[text()='No maximun stay']]")).click();
				FlightSearch.loadhandling(driver);

				//Search Button
				WebElement searchbtn6 = driver.findElement(By.xpath("//button[@aria-label='Search']"));
				searchbtn2.click();
				FlightSearch.loadhandling(driver);
			
				queryObjects.logStatus(driver, Status.PASS, "Should select Fare Restriction Type", "Selected Fare Restriction Type Successful", null);

				//Checking Fare Details
				if(driver.findElements(By.xpath("//table[thead[tr[th[text()='Carrier']]]]//tr[1]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Fare Restriction type should display fare details", "Fare details displayed", null);
				else if(driver.findElements(By.xpath("//div[text()='No records found']")).size()>0)
					queryObjects.logStatus(driver, Status.WARNING, "Fare Restriction type should display fare details", "No records found", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Fare Restriction type should display fare details", "Fare details is not displayed", null);
				
				driver.findElement(By.xpath("//div[span[text()='No maximun stay']]")).click();
										
			}
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Should display details with Fare Restrictions", "Display Fare Restrictions not Successful", e);
		
		}
		
		//Carrier/Currency
		try {
			if(driver.findElements(By.xpath("//div[toggle-title[span[text()='Carrier']]]")).size()>0) {
				driver.findElement(By.xpath("//div[toggle-title[span[text()='Carrier']]]/preceding-sibling::i")).click();
				FlightSearch.loadhandling(driver);
				
				WebElement carrCode = driver.findElement(By.xpath("//input[@aria-label='Carrier Code']"));
				carrCode.click();
				carrCode.clear();
				String cCode = FlightSearch.getTrimTdata(queryObjects.getTestData("code"));
				carrCode.sendKeys(cCode);
				
				WebElement cabin = driver.findElement(By.xpath("//input[@aria-label='Cabin']"));
				cabin.click();
				cabin.clear();
				String clss = FlightSearch.getTrimTdata(queryObjects.getTestData("class"));
				cabin.sendKeys(clss);

				WebElement curr = driver.findElement(By.xpath("//input[@aria-label='Currency']"));
				curr.click();
				curr.clear();
				String curre = FlightSearch.getTrimTdata(queryObjects.getTestData("currency"));
				curr.sendKeys(curre);
				

				//Search Button
				WebElement searchbn = driver.findElement(By.xpath("//button[@aria-label='Search']"));
				searchbn.click();
				FlightSearch.loadhandling(driver);
			
				queryObjects.logStatus(driver, Status.PASS, "Should search by Carrier/Currency", "Search by Carrier/Currency Successful", null);

				//Checking Fare Details
				if(driver.findElements(By.xpath("//table[thead[tr[th[text()='Carrier']]]]//tr[1]")).size()>0)
					queryObjects.logStatus(driver, Status.PASS, "Carrier/Currency should display fare details", "Fare details displayed", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Carrier/Currency should display fare details", "Fare details is not displayed", null);
				
				}
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Should search by Carrier/Currency", "Search by Carrier/Currency not Successful", e);
		
		}
		
		//Fare Basis/Private Fares
		if(driver.findElements(By.xpath("//div[toggle-title[span[text()='Fare Basis']]]")).size()>0) {
			driver.findElement(By.xpath("//div[toggle-title[span[text()='Fare Basis']]]/preceding-sibling::i")).click();
		
		}
		
		//Discounts
		if(driver.findElements(By.xpath("//div[toggle-title[span[text()='Discounts']]]")).size()>0) {
			driver.findElement(By.xpath("//div[toggle-title[span[text()='Discounts']]]/preceding-sibling::i")).click();
		
		}
		
		//Direction
		if(driver.findElements(By.xpath("//div[toggle-title[span[text()='Direction']]]")).size()>0) {
			driver.findElement(By.xpath("//div[toggle-title[span[text()='Direction']]]/preceding-sibling::i")).click();
		
		}
		
		//Global Direction 
		if(driver.findElements(By.xpath("//div[toggle-title[span[text()='Global Direction']]]")).size()>0) {
			driver.findElement(By.xpath("//div[toggle-title[span[text()='Global Direction']]]/preceding-sibling::i")).click();
		
		}
		try {
			//Clear
			driver.findElement(By.xpath("//button[text()='Clear']")).click();
			FlightSearch.loadhandling(driver);
			queryObjects.logStatus(driver, Status.PASS, "Should clear the required field", "Cleared the required field", null);
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.PASS, "Should clear the required field", "Did not clear the required field", e);
		}
	}

}

public void flifoVerify(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{

	driver.findElement(By.xpath("//div[@role='button']//div[text()='Flifo Search']")).click();
	FlightSearch.loadhandling(driver);
	if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]/div[text()='Flifo Search']")).size()>0) {
		queryObjects.logStatus(driver, Status.PASS, "Navigate to Flifo Search Page", "Navigated to Flifo Search Page", null);
		
		//FROM and TO
		//From
		driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).click();
		driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).clear();
		String from=FlightSearch.getTrimTdata(queryObjects.getTestData("From"));
		driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).sendKeys(from);
		driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).sendKeys(Keys.ENTER);
	
	
		//To
		driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).click();
		driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).clear();
		String to=FlightSearch.getTrimTdata(queryObjects.getTestData("To"));
		driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).sendKeys(to);
		driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).sendKeys(Keys.ENTER);
							
		//Search Button
		WebElement searchbtn = driver.findElement(By.xpath("//button[text()='Search' and contains(@ng-disabled,'invalid')]"));
		searchbtn.click();
		FlightSearch.loadhandling(driver);
		
		List<WebElement> flights = driver.findElements(By.xpath("//md-content//div[contains(@ng-if,'Flight')]"));
		if(flights.size()>0) {
			queryObjects.logStatus(driver, Status.PASS, "Flight Info Search results should be displayed for all flights of the route", "Flight Info Search results for all flights is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Flight Info Search results should be displayed for all flights of the route", "Flight Info Search results for all flights is not displayed", null);
		}
		
		//FltNo, FROM and TO
		//Flight Number
		driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).click();
		driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).clear();
		String flight=FlightSearch.getTrimTdata(queryObjects.getTestData("flghtNum"));
		driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).sendKeys(flight);
		//driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).sendKeys(Keys.ENTER);
							
		//From
		driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).click();
		driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).clear();
		String from2=FlightSearch.getTrimTdata(queryObjects.getTestData("From"));
		driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).sendKeys(from2);
		driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).sendKeys(Keys.ENTER);
	
	
		//To
		driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).click();
		driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).clear();
		String to2=FlightSearch.getTrimTdata(queryObjects.getTestData("To"));
		driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).sendKeys(to2);
		driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).sendKeys(Keys.ENTER);
							
		//Search Button
		WebElement searchbtn2 = driver.findElement(By.xpath("//button[text()='Search' and contains(@ng-disabled,'invalid')]"));
		searchbtn2.click();
		FlightSearch.loadhandling(driver);
		
		List<WebElement> flightInfo = driver.findElements(By.xpath("//div[text()='Flight Info Search Results']"));
		if(flightInfo.size()>0) {
			queryObjects.logStatus(driver, Status.PASS, "Flight Info Search results should be displayed", "Flight Info Search results is displayed", null);
		}
		else {
			queryObjects.logStatus(driver, Status.PASS, "Flight Info Search results should be displayed", "Flight Info Search results is not displayed", null);
		}
		
		
		//Entering invalid Date
		String sgetinvDate=FlightSearch.getTrimTdata(queryObjects.getTestData("invDay"));
		int getinvDays=0;
		if (sgetinvDate.isEmpty())
			getinvDays=30;
		else
			getinvDays=Integer.parseInt(sgetinvDate);

		Calendar calc1 = Calendar.getInstance();
		calc1.add(Calendar.DATE, +getinvDays);
		String timeStampd1 = new SimpleDateFormat("MM/dd/yyyy").format(calc1.getTime());
		WebElement DateXpath = driver.findElement(By.xpath("//div[contains(@class,'datepicker')]//input"));
		if (getinvDays!=0) {
			DateXpath.clear();
			DateXpath.sendKeys(timeStampd1);
		}
		Thread.sleep(1000);
	
		//Search Button
		List<WebElement> searchbttn = driver.findElements(By.xpath("//button[text()='Search' and @disabled='disabled']"));
		if(searchbttn.size()>0)
			queryObjects.logStatus(driver, Status.PASS, "Search button should be disabled", "Search button is disabled", null);
		
		else
			queryObjects.logStatus(driver, Status.FAIL, "Search button should be disabled", "Search button is not disabled", null);

		
	}

}

public void searchVerify(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{

	driver.findElement(By.xpath("//div[@role='button']/div[text()='Search']")).click();
	FlightSearch.loadhandling(driver);
	if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]/div[text()='Search']")).size()>0) {
		queryObjects.logStatus(driver, Status.PASS, "Navigate to Search Page", "Navigated to Search Page", null);
		
		//Expand Advance Search
		if(driver.findElements(By.xpath("//span[text()='Advanced Search']")).size()>0) {
			driver.findElement(By.xpath("//div[div[toggle-title[span[text()='Advanced Search']]]]/i")).click();
			FlightSearch.loadhandling(driver);
			
			//Click Orders and Tickets
			if(driver.findElements(By.xpath("//div[text()='Orders and Tickets']")).size()>0) {
				//Area Code
				driver.findElement(By.xpath("//label[text()='Area Code']/following-sibling::input")).click();
				driver.findElement(By.xpath("//label[text()='Area Code']/following-sibling::input")).clear();
				String areacode=FlightSearch.getTrimTdata(queryObjects.getTestData("areaCode"));
				driver.findElement(By.xpath("//label[text()='Area Code']/following-sibling::input")).sendKeys(areacode);
				/*driver.findElement(By.xpath("//label[text()='Area Code']/following-sibling::input")).sendKeys(Keys.ENTER);*/
						
				//Phone Number
				driver.findElement(By.xpath("//label[text()='Phone Number']/following-sibling::input")).click();
				driver.findElement(By.xpath("//label[text()='Phone Number']/following-sibling::input")).clear();
				String phone=FlightSearch.getTrimTdata(queryObjects.getTestData("phNum"));
				driver.findElement(By.xpath("//label[text()='Phone Number']/following-sibling::input")).sendKeys(phone);
				/*driver.findElement(By.xpath("//label[text()='Phone Number']/following-sibling::input")).sendKeys(Keys.ENTER);*/
				
				//Search Button
				WebElement searchbtn = driver.findElement(By.xpath("//button[text()='Search' and contains(@ng-click,'phone-number')]"));
				searchbtn.click();
				FlightSearch.loadhandling(driver);
				
				if(driver.findElements(By.xpath("//tbody/tr[@role='button']")).size()>0) {
					queryObjects.logStatus(driver, Status.PASS, "Search should display results", "Search displayed results", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Search should display results", "Search did not displayed results", null);
				}
			
				//Area Code
				driver.findElement(By.xpath("//label[text()='Area Code']/following-sibling::input")).click();
				driver.findElement(By.xpath("//label[text()='Area Code']/following-sibling::input")).clear();
				String invareacode=FlightSearch.getTrimTdata(queryObjects.getTestData("invArea"));
				driver.findElement(By.xpath("//label[text()='Area Code']/following-sibling::input")).sendKeys(invareacode);
				//driver.findElement(By.xpath("//label[text()='Area Code']/following-sibling::input")).sendKeys(Keys.ENTER);
						
				//Phone Number
				driver.findElement(By.xpath("//label[text()='Phone Number']/following-sibling::input")).click();
				driver.findElement(By.xpath("//label[text()='Phone Number']/following-sibling::input")).clear();
				String invphone=FlightSearch.getTrimTdata(queryObjects.getTestData("invNum"));
				driver.findElement(By.xpath("//label[text()='Phone Number']/following-sibling::input")).sendKeys(invphone);
				//driver.findElement(By.xpath("//label[text()='Phone Number']/following-sibling::input")).sendKeys(Keys.ENTER);
				
				//Search Button (Invalid)
				WebElement searchbtn4 = driver.findElement(By.xpath("//button[text()='Search' and contains(@ng-click,'phone-number')]"));
				searchbtn4.click();
				FlightSearch.loadhandling(driver);
				
				if(driver.findElements(By.xpath("//tbody/tr[@role='button']")).size()==0) {
					queryObjects.logStatus(driver, Status.PASS, "Search should display error", "Search displayed error", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Search should display error", "Search did not displayed error", null);
				}
				
				//Surname
				driver.findElement(By.xpath("//label[text()='Surname']/following-sibling::input")).click();
				driver.findElement(By.xpath("//label[text()='Surname']/following-sibling::input")).clear();
				String surname=FlightSearch.getTrimTdata(queryObjects.getTestData("surname"));
				driver.findElement(By.xpath("//label[text()='Surname']/following-sibling::input")).sendKeys(surname);
				//driver.findElement(By.xpath("//label[text()='Surname']/following-sibling::input")).sendKeys(Keys.ENTER);
						
				//Given Name
				driver.findElement(By.xpath("//label[text()='Given Name']/following-sibling::input")).click();
				driver.findElement(By.xpath("//label[text()='Given Name']/following-sibling::input")).clear();
				String frstname=FlightSearch.getTrimTdata(queryObjects.getTestData("frstname"));
				driver.findElement(By.xpath("//label[text()='Given Name']/following-sibling::input")).sendKeys(frstname);
				//driver.findElement(By.xpath("//label[text()='Given Name']/following-sibling::input")).sendKeys(Keys.ENTER);
				
				//Flight Number
				driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).click();
				driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).clear();
				String flight=FlightSearch.getTrimTdata(queryObjects.getTestData("flghtNum"));
				driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).sendKeys(flight);
				//driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).sendKeys(Keys.ENTER);
				
				//Departure Date
				WebElement depDate = driver.findElement(By.xpath("//div[label[span[text()='Departure Date']]]/following-sibling::md-datepicker//input"));
				depDate.click();
				depDate.clear();
				String departure=FlightSearch.getTrimTdata(queryObjects.getTestData("depDate"));
				depDate.sendKeys(departure);
				//depDate.sendKeys(Keys.ENTER);
				
				//Airport
				driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).click();
				driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).clear();
				String airport=FlightSearch.getTrimTdata(queryObjects.getTestData("depAirport"));
				driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).sendKeys(airport);
				driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).sendKeys(Keys.ENTER);
				
				//Search Button
				WebElement searchbtn2 = driver.findElement(By.xpath("//button[text()='Search' and contains(@ng-click,'passenger-search')]"));
				searchbtn2.click();
				FlightSearch.loadhandling(driver);
				
				if(driver.findElements(By.xpath("//div[text()='Order']")).size()>0) {
					if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]//div[text()='Order']")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "Search should display results", "Search displayed results", null);

						//Click on Home Button
						driver.findElement(By.xpath("//span[text()='Home']")).click();
						FlightSearch.loadhandling(driver);
						
						//Expand Advance Search
						driver.findElement(By.xpath("//div[div[toggle-title[span[text()='Advanced Search']]]]/i")).click();
						FlightSearch.loadhandling(driver);
						
					}
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Search should display results", "Search did not display results", null);
				}
				
				//Surname
				driver.findElement(By.xpath("//label[text()='Surname']/following-sibling::input")).click();
				driver.findElement(By.xpath("//label[text()='Surname']/following-sibling::input")).clear();
				String invsurname=FlightSearch.getTrimTdata(queryObjects.getTestData("invSurname"));
				driver.findElement(By.xpath("//label[text()='Surname']/following-sibling::input")).sendKeys(invsurname);
				//driver.findElement(By.xpath("//label[text()='Surname']/following-sibling::input")).sendKeys(Keys.ENTER);
						
				//Given Name
				driver.findElement(By.xpath("//label[text()='Given Name']/following-sibling::input")).click();
				driver.findElement(By.xpath("//label[text()='Given Name']/following-sibling::input")).clear();
				String invfrstname=FlightSearch.getTrimTdata(queryObjects.getTestData("invFrstname"));
				driver.findElement(By.xpath("//label[text()='Given Name']/following-sibling::input")).sendKeys(invfrstname);
				//driver.findElement(By.xpath("//label[text()='Given Name']/following-sibling::input")).sendKeys(Keys.ENTER);
				
				//Flight Number
				driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).click();
				driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).clear();
				flight=FlightSearch.getTrimTdata(queryObjects.getTestData("flghtNum"));
				driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).sendKeys(flight);
				//driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).sendKeys(Keys.ENTER);
				
				//Departure Date
				depDate = driver.findElement(By.xpath("//div[label[span[text()='Departure Date']]]/following-sibling::md-datepicker//input"));
				depDate.click();
				depDate.clear();
				departure=FlightSearch.getTrimTdata(queryObjects.getTestData("depDate"));
				depDate.sendKeys(departure);
				//depDate.sendKeys(Keys.ENTER);
				
				//Airport
				driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).click();
				driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).clear();
				airport=FlightSearch.getTrimTdata(queryObjects.getTestData("depAirport"));
				driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).sendKeys(airport);
				driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).sendKeys(Keys.ENTER);
				
				//Search Button
				searchbtn2 = driver.findElement(By.xpath("//button[text()='Search' and contains(@ng-click,'passenger-search')]"));
				searchbtn2.click();
				FlightSearch.loadhandling(driver);
				
				if(driver.findElements(By.xpath("//div[text()='Order']")).size()==0) {
					queryObjects.logStatus(driver, Status.PASS, "Search should display error", "Search displayed error", null);
						
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Search should display error", "Search did not display error", null);
				}
				
				
				//Frequent Flyer
				driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).click();
				driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).clear();
				String ffpNum=FlightSearch.getTrimTdata(queryObjects.getTestData("ffpNum"));
				driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).sendKeys(ffpNum);
				//driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).sendKeys(Keys.ENTER);

				//Search Button
				WebElement searchbtn3 = driver.findElement(By.xpath("//button[text()='Search' and contains(@ng-click,'flyer-search')]"));
				searchbtn3.click();
				FlightSearch.loadhandling(driver);
				
				if(driver.findElements(By.xpath("//tbody/tr[@role='button']")).size()>0) {
					queryObjects.logStatus(driver, Status.PASS, "Search should display results", "Search displayed results", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Search should display results", "Search did not displayed results", null);
				}

				//Frequent Flyer
				driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).click();
				driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).clear();
				String invffpNum=FlightSearch.getTrimTdata(queryObjects.getTestData("invffpNum"));
				driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).sendKeys(invffpNum);
				//driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).sendKeys(Keys.ENTER);

				//Search Button
				searchbtn3 = driver.findElement(By.xpath("//button[text()='Search' and contains(@ng-click,'flyer-search')]"));
				searchbtn3.click();
				FlightSearch.loadhandling(driver);
				
				if(driver.findElements(By.xpath("//tbody/tr[@role='button']")).size()==0) {
					queryObjects.logStatus(driver, Status.PASS, "Search should display error", "Search displayed error", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Search should display error", "Search did not displayed error", null);
				}
										
			}
		
		}
	}
	else {
		queryObjects.logStatus(driver, Status.FAIL, "Navigate to Search Page", "Not navigated to Search Page", null);
		
	}

}

public void searchPaxVerify(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{

	driver.findElement(By.xpath("//div[@role='button']/div[text()='Search']")).click();
	FlightSearch.loadhandling(driver);
	if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]/div[text()='Search']")).size()>0) {
		queryObjects.logStatus(driver, Status.PASS, "Navigate to Search Page", "Navigated to Search Page", null);
		
		//Expand Advance Search
		if(driver.findElements(By.xpath("//span[text()='Advanced Search']")).size()>0) {
			driver.findElement(By.xpath("//div[div[toggle-title[span[text()='Advanced Search']]]]/i")).click();
			FlightSearch.loadhandling(driver);
			
			//Search by Passenger List
			driver.findElement(By.xpath("//div[text()='By passenger List']")).click();
			FlightSearch.loadhandling(driver);
			
			//Flight Number
			driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).click();
			driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).clear();
			String flight=FlightSearch.getTrimTdata(queryObjects.getTestData("flghtNum"));
			driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).sendKeys(flight);
			
			//Airport
			driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).click();
			driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).clear();
			String airport=FlightSearch.getTrimTdata(queryObjects.getTestData("depAirport"));
			driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).sendKeys(airport);
			driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).sendKeys(Keys.ENTER);
			
			//PAX LIST
			//All passengers (firming)
			driver.findElement(By.xpath("//label[text()='Passenger List']/following-sibling::md-select")).click();
			driver.findElement(By.xpath("//div[contains(text(),'All passengers (firming)')]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//toggle-content//button[text()='Search']")).click();
			FlightSearch.loadhandling(driver);
			
			List<WebElement> details = driver.findElements(By.xpath("//pssgui-passenger-search-result//div"));
			if(details.size()>0)
				queryObjects.logStatus(driver, Status.PASS, "Search results should be displayed", "Search results displayed", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Search results should be displayed", "Search results not displayed", null);

			
			//Cancelled passengers
			driver.findElement(By.xpath("//label[text()='Passenger List']/following-sibling::md-select")).click();
			driver.findElement(By.xpath("//div[contains(text(),'Cancelled passengers')]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//toggle-content//button[text()='Search']")).click();
			FlightSearch.loadhandling(driver);
			
			details = driver.findElements(By.xpath("//pssgui-passenger-search-result//div"));
			if(details.size()>0)
				queryObjects.logStatus(driver, Status.PASS, "Search results should be displayed", "Search results displayed", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Search results should be displayed", "Search results not displayed", null);

			//Corporate inhouse bookings
			driver.findElement(By.xpath("//label[text()='Passenger List']/following-sibling::md-select")).click();
			driver.findElement(By.xpath("//div[contains(text(),'Corporate inhouse bookings')]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//toggle-content//button[text()='Search']")).click();
			FlightSearch.loadhandling(driver);
			
			details = driver.findElements(By.xpath("//pssgui-passenger-search-result//div"));
			if(details.size()>0)
				queryObjects.logStatus(driver, Status.PASS, "Search results should be displayed", "Search results displayed", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Search results should be displayed", "Search results not displayed", null);

			//Destination name list
			driver.findElement(By.xpath("//label[text()='Passenger List']/following-sibling::md-select")).click();
			driver.findElement(By.xpath("//div[contains(text(),'Destination name list')]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//toggle-content//button[text()='Search']")).click();
			FlightSearch.loadhandling(driver);
			
			details = driver.findElements(By.xpath("//pssgui-passenger-search-result//div"));
			if(details.size()>0)
				queryObjects.logStatus(driver, Status.PASS, "Search results should be displayed", "Search results displayed", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Search results should be displayed", "Search results not displayed", null);

			//Firming
			driver.findElement(By.xpath("//label[text()='Passenger List']/following-sibling::md-select")).click();
			driver.findElement(By.xpath("//div[contains(text(),'Firming')]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//toggle-content//button[text()='Search']")).click();
			FlightSearch.loadhandling(driver);
			
			details = driver.findElements(By.xpath("//pssgui-passenger-search-result//div"));
			if(details.size()>0)
				queryObjects.logStatus(driver, Status.PASS, "Search results should be displayed", "Search results displayed", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Search results should be displayed", "Search results not displayed", null);

			//Space-available non-revenue passengers
			driver.findElement(By.xpath("//label[text()='Passenger List']/following-sibling::md-select")).click();
			driver.findElement(By.xpath("//div[contains(text(),'Space-available non-revenue passengers')]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//toggle-content//button[text()='Search']")).click();
			FlightSearch.loadhandling(driver);
			
			details = driver.findElements(By.xpath("//pssgui-passenger-search-result//div"));
			if(details.size()>0) {
				queryObjects.logStatus(driver, Status.PASS, "Search results should be displayed", "Search results displayed", null);
				
				//Click on Order ID
				driver.findElement(By.xpath("//pssgui-passenger-search-result//md-content//div[1]/div[1]")).click();
				FlightSearch.loadhandling(driver);

				if(driver.findElements(By.xpath("//div[text()='Order']")).size()>0) {
					if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]//div[text()='Order']")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "Search should display results", "Search displayed results", null);

						//Click on Home Button
						driver.findElement(By.xpath("//span[text()='Home']")).click();
						FlightSearch.loadhandling(driver);
						
						//Click on Search
						driver.findElement(By.xpath("//div[@role='button']/div[text()='Search']")).click();
						FlightSearch.loadhandling(driver);
						
						if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]/div[text()='Search']")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Navigate to Search Page", "Navigated to Search Page", null);
							
							//Expand Advance Search
							if(driver.findElements(By.xpath("//span[text()='Advanced Search']")).size()>0) {
								driver.findElement(By.xpath("//div[div[toggle-title[span[text()='Advanced Search']]]]/i")).click();
								FlightSearch.loadhandling(driver);
								
								//Search by Passenger List
								driver.findElement(By.xpath("//div[text()='By passenger List']")).click();
								FlightSearch.loadhandling(driver);
								
								//Flight Number
								driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).click();
								driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).clear();
								flight=FlightSearch.getTrimTdata(queryObjects.getTestData("flghtNum"));
								driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).sendKeys(flight);
								
								//Airport
								driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).click();
								driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).clear();
								String invairport=FlightSearch.getTrimTdata(queryObjects.getTestData("invAirport"));
								driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).sendKeys(invairport);
								driver.findElement(By.xpath("//label[text()='Departure Airport']/following-sibling::input")).sendKeys(Keys.ENTER);
								try {
									//Search Button
									WebElement searchbtn = driver.findElement(By.xpath("//button[text()='Search' and contains(@ng-disabled,'invalid')]"));
									searchbtn.click();
									FlightSearch.loadhandling(driver);
									queryObjects.logStatus(driver, Status.PASS, "Search button should display errors", "Search button displayed errors", null);
								
									//Click on Clear
									driver.findElement(By.xpath("//button[text()='Clear']")).click();
									FlightSearch.loadhandling(driver);
								}
								catch(Exception e) {
									queryObjects.logStatus(driver, Status.FAIL, "Search button should display errors", "Search button did not display errors", e);
								}
							}
						}
					}
				}
			}
			else
				queryObjects.logStatus(driver, Status.FAIL, "Search results should be displayed", "Search results not displayed", null);
		

			
			/*String paxlist=FlightSearch.getTrimTdata(queryObjects.getTestData("paxList"));
			driver.findElement(By.xpath("//label[text()='Passenger List']/following-sibling::md-select")).sendKeys(airport);*/
			
			
		}
	}

}

public void searchEMDVerify(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException, Exception{

	driver.findElement(By.xpath("//div[@role='button']/div[text()='Search']")).click();
	FlightSearch.loadhandling(driver);
	if(driver.findElements(By.xpath("//div[contains(@class,'tab-active')]/div[text()='Search']")).size()>0) {
		queryObjects.logStatus(driver, Status.PASS, "Navigate to Search Page", "Navigated to Search Page", null);
		
		//Expand Advance Search
		if(driver.findElements(By.xpath("//span[text()='Advanced Search']")).size()>0) {
			driver.findElement(By.xpath("//div[div[toggle-title[span[text()='Advanced Search']]]]/i")).click();
			FlightSearch.loadhandling(driver);
			
			//Search EMDs
			driver.findElement(By.xpath("//div[text()='Search EMDs']")).click();
			FlightSearch.loadhandling(driver);
			
			//Frequent Flyer
			driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).click();
			driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).clear();
			String ffpNum=FlightSearch.getTrimTdata(queryObjects.getTestData("ffpNum"));
			driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).sendKeys(ffpNum);
			//driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).sendKeys(Keys.ENTER);

			//Search Button
			WebElement searchbtn3 = driver.findElement(By.xpath("//button[text()='Search' and contains(@ng-click,'flyer-search')]"));
			searchbtn3.click();
			FlightSearch.loadhandling(driver);

			if(driver.findElements(By.xpath("//tbody/tr[@role='button']")).size()>0) {
				queryObjects.logStatus(driver, Status.PASS, "Search should display results", "Search displayed results", null);
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "Search should display results", "Search did not displayed results", null);
			}


			//Invalid Frequent Flyer
			driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).click();
			driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).clear();
			String invffpNum=FlightSearch.getTrimTdata(queryObjects.getTestData("invffpNum"));
			driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).sendKeys(invffpNum);
			//driver.findElement(By.xpath("//label[text()='FF Number']/following-sibling::input")).sendKeys(Keys.ENTER);

			//Search Button
			searchbtn3 = driver.findElement(By.xpath("//button[text()='Search' and contains(@ng-click,'flyer-search')]"));
			searchbtn3.click();
			FlightSearch.loadhandling(driver);

			if(driver.findElements(By.xpath("//tbody/tr[@role='button']")).size()>0) {
				queryObjects.logStatus(driver, Status.PASS, "Search should display error", "Search displayed errors", null);
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "Search should display error", "Search did not displayed errors", null);
			}

			
		}
	}

}

public void settingsVerify(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{
	
		driver.findElement(By.xpath("//div[@id='toolbar']")).click();
		FlightSearch.loadhandling(driver);
		try {
			//Calendar with Count Functions
		
			if(driver.findElements(By.xpath("//div[contains(text(),'Calendar')]")).size()>0) {
				driver.findElement(By.xpath("//div[contains(text(),'Calendar')]")).click();
				FlightSearch.loadhandling(driver);
		
				driver.findElement(By.xpath("//md-datepicker/button[@type='button']")).click();
				driver.findElement(By.xpath("//md-datepicker/button[@type='button']")).sendKeys(Keys.ENTER);
				queryObjects.logStatus(driver, Status.INFO, "Date should be selected", "Date is selected", null);
			
				//Enter +/- 'x' days to calculate the next/prev 'x' days
				driver.findElement(By.xpath("//input[@aria-label='Enter']")).click();
				String inputnum = FlightSearch.getTrimTdata(queryObjects.getTestData("input"));
				int inp = Integer.parseInt(inputnum);
				driver.findElement(By.xpath("//input[@aria-label='Enter']")).sendKeys(inputnum);
			
				//Validate date
				String calDate = driver.findElement(By.xpath("//button[@aria-label='Open calendar']/preceding-sibling::input")).getAttribute("value");
		
				Calendar calc = Calendar.getInstance();
				calc.add(Calendar.DATE, +inp);
				String timeStampd = new SimpleDateFormat("MM/dd/yyyy").format(calc.getTime());
				//calc.add(Calendar.DATE, +inputnum);
				//String timeStampd = new SimpleDateFormat("MM/dd/yyyy").format(calc.getTime());
				String getApptimeStampd = driver.findElement(By.xpath("//md-content/div[2]")).getText();
				if(getApptimeStampd.equalsIgnoreCase(timeStampd)) {
					queryObjects.logStatus(driver, Status.PASS, "Valid Date should be calculated and displayed", "Valid date is calculated and displayed", null);
				}
				else
					queryObjects.logStatus(driver, Status.FAIL, "Valid Date should be calculated and displayed", "Valid date is not calculated and displayed", null);
				
				driver.findElement(By.xpath("//button[@aria-label= 'Ok']")).click();
				FlightSearch.loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Validate Calendar with Count Functions", "Validated Calendar with Count Functions", null);
			}
			else
				queryObjects.logStatus(driver, Status.FAIL, "", "", null);
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Validate Calendar with Count Functions", e.getLocalizedMessage(), e);	
		}
	
	
	driver.findElement(By.xpath("//div[@id='toolbar']")).click();
	FlightSearch.loadhandling(driver);
	
	//Converter					
	try {
	
		if(driver.findElements(By.xpath("//div[text()= 'Converter']")).size()>0) {
			driver.findElement(By.xpath("//div[text()= 'Converter']")).click();
			FlightSearch.loadhandling(driver);
			
			driver.findElement(By.xpath("//md-select[@aria-label='converterMethod']")).click();
			driver.findElement(By.xpath("//div[contains(text(),'Currency by BSR')]")).click();
			FlightSearch.loadhandling(driver);
			
			//From
			WebElement from = driver.findElement(By.xpath("//label[span[text()='From Currency']]/following-sibling::input"));
			from.click();
			from.clear();
			String fcurr = FlightSearch.getTrimTdata(queryObjects.getTestData("frCurr"));
			from.sendKeys(fcurr);
			
			//To
			WebElement to = driver.findElement(By.xpath("//label[span[text()='To currency']]/following-sibling::input"));
			to.click();
			to.clear();
			String tcurr = FlightSearch.getTrimTdata(queryObjects.getTestData("toCurr"));
			to.sendKeys(tcurr);
			
			//Amount
			WebElement amt = driver.findElement(By.xpath("//label[span[text()='Amount']]/following-sibling::input"));
			amt.click();
			amt.clear();
			String amnt = FlightSearch.getTrimTdata(queryObjects.getTestData("amount"));
			amt.sendKeys(amnt);
			
			//Display button
			WebElement disp = driver.findElement(By.xpath("//button[text()='Display']"));
			disp.click();
			FlightSearch.loadhandling(driver);
			
			//Conversion
			List<WebElement> frCur = driver.findElements(By.xpath("//div[text()='Orginal Amount']/following-sibling::div"));
			List<WebElement> toCur = driver.findElements(By.xpath("//div[text()='Converted Amount']/following-sibling::div"));
			if(frCur.size()>0 && toCur.size()>0)
				queryObjects.logStatus(driver, Status.PASS, "Validate Converter pop-up", "Validated Converter pop-up", null);	
			else
				queryObjects.logStatus(driver, Status.FAIL, "Validate Converter pop-up", "Not validated Converter pop-up", null);
		
			WebElement cancel = driver.findElement(By.xpath("//div[text()='Converter']/following-sibling::div/i"));
			cancel.click();
			FlightSearch.loadhandling(driver);
			
		}
		
	}
	catch(Exception e) {
		
	}
	
	
	driver.findElement(By.xpath("//div[@id='toolbar']")).click();
	FlightSearch.loadhandling(driver);
	//
	
		if(driver.findElements(By.xpath("//div[text()='Timatic']")).size()>0) {
			driver.findElement(By.xpath("//div[text()='Timatic']")).click();
			FlightSearch.loadhandling(driver);
			
			//Selecting
			driver.findElement(By.xpath("//label[text()='Select']/following-sibling::md-select")).click();
			driver.findElement(By.xpath("//div[contains(text(),'Health Information')]")).click();
			driver.findElement(By.xpath("//md-select[contains(@aria-label,'passport Type: ')]")).click();
			driver.findElement(By.xpath("//md-option/div[contains(text(),'NORMAL')]")).click();
			WebElement loc1 = driver.findElement(By.xpath("//label[text()='Resident country']/following-sibling::input")); 
			loc1.click();
			loc1.clear();
			String res = FlightSearch.getTrimTdata(queryObjects.getTestData("resLoc"));
			loc1.sendKeys(res);
			FlightSearch.loadhandling(driver);
			loc1.sendKeys(Keys.ENTER);
			
			WebElement loc2 = driver.findElement(By.xpath("//label[text()='Embarkation location']/following-sibling::input"));
			loc2.click();
			loc2.clear();
			String emb = FlightSearch.getTrimTdata(queryObjects.getTestData("embLoc"));
			loc2.sendKeys(emb);
			FlightSearch.loadhandling(driver);
			loc2.sendKeys(Keys.ENTER);
			
			WebElement loc3 = driver.findElement(By.xpath("//label[text()='Country Location']/following-sibling::input"));
			loc3.click();
			loc3.clear();
			String count = FlightSearch.getTrimTdata(queryObjects.getTestData("countLoc"));
			loc3.sendKeys(count);
			FlightSearch.loadhandling(driver);
			loc3.sendKeys(Keys.ENTER);
			
			driver.findElement(By.xpath("//button[text()='Display']")).click();
			FlightSearch.loadhandling(driver);
	
			if(driver.findElements(By.xpath("//md-dialog//div[text()='Timatic']")).size()>0) {
				queryObjects.logStatus(driver, Status.PASS, "Validate Timatic pop-up", "Validated Timatic pop-up", null);
			}
			else
				queryObjects.logStatus(driver, Status.FAIL, "Validate Timatic pop-up", "Not validated Timatic pop-up", null);
			
			WebElement cancel = driver.findElement(By.xpath("//div[text()='Timatic']/following-sibling::div/i"));
			cancel.click();
			FlightSearch.loadhandling(driver);
			
				
		}
	
	
	driver.findElement(By.xpath("//div[@id='toolbar']")).click();
	FlightSearch.loadhandling(driver);
	
	//Flifo Search					
	
		if(driver.findElements(By.xpath("//i/following-sibling::div[text()= 'Flifo Search']")).size()>0) {
			driver.findElement(By.xpath("//i/following-sibling::div[text()= 'Flifo Search']")).click();
			FlightSearch.loadhandling(driver);
			
			//FltNo, FROM and TO
			//Flight Number
			driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).click();
			driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).clear();
			String flight=FlightSearch.getTrimTdata(queryObjects.getTestData("flghtNum"));
			driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).sendKeys(flight);
			//driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).sendKeys(Keys.ENTER);
						
			//From
			driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).click();
			driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).clear();
			String from2=FlightSearch.getTrimTdata(queryObjects.getTestData("From"));
			driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).sendKeys(from2);
			driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).sendKeys(Keys.ENTER);
	
	
			//To
			driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).click();
			driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).clear();
			String to2=FlightSearch.getTrimTdata(queryObjects.getTestData("To"));
			driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).sendKeys(to2);
			driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).sendKeys(Keys.ENTER);
			
	
			//Entering segment Date
			String sgetDate=FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
			int getDays=0;
			getDays=Integer.parseInt(sgetDate);
	
			Calendar calc = Calendar.getInstance();
			calc.add(Calendar.DATE, +getDays);
			String timeStampd = new SimpleDateFormat("MM/dd/yyyy").format(calc.getTime());
			WebElement DateXpath = driver.findElement(By.xpath("//md-datepicker//input"));
			DateXpath.clear();
			DateXpath.sendKeys(timeStampd);
			Thread.sleep(1000);
			
			//Search
			driver.findElement(By.xpath("//div[@type='submit']/i")).click();
			FlightSearch.loadhandling(driver);
			
			if(driver.findElements(By.xpath("//table/tbody/tr")).size()>0) {
				queryObjects.logStatus(driver, Status.PASS, "Validate FLIFO Search", "Validated FLIFO Search", null);
			}
			else
				queryObjects.logStatus(driver, Status.FAIL, "Validate FLIFO Search", "Not validated FLIFO Search", null);
											
			//Cancel
			WebElement cancel = driver.findElement(By.xpath("//div[text()='Flifo Search']/following-sibling::div/i"));
			cancel.click();
			FlightSearch.loadhandling(driver);
			
			
		}
	
	
	driver.findElement(By.xpath("//div[@id='toolbar']")).click();
	FlightSearch.loadhandling(driver);
	
	//Passengers Booked					
	
		if(driver.findElements(By.xpath("//i/following-sibling::div[text()= 'Passengers Booked']")).size()>0) {
			driver.findElement(By.xpath("//i/following-sibling::div[text()= 'Passengers Booked']")).click();
			FlightSearch.loadhandling(driver);
			
			//Flight Number
			driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).click();
			driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).clear();
			String flight=FlightSearch.getTrimTdata(queryObjects.getTestData("flghtNum"));
			driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).sendKeys(flight);
			//driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).sendKeys(Keys.ENTER);
			
			//Search
			driver.findElement(By.xpath("//pssgui-date-time/following-sibling::div/button[text()='Search']")).click();
			FlightSearch.loadhandling(driver);
			
			if(driver.findElements(By.xpath("//md-content//table[2]")).size()>0) {
				queryObjects.logStatus(driver, Status.PASS, "Validate Passengers Booked", "Validated Passengers Booked", null);
			}
			else
				queryObjects.logStatus(driver, Status.FAIL, "Validate Passengers Booked", "Not validated Passengers Booked", null);
											
			//Cancel
			WebElement cancel = driver.findElement(By.xpath("//div[text()='Passengers Booked']/following-sibling::div/i"));
			cancel.click();
			FlightSearch.loadhandling(driver);
			
			
		}
	
	
	driver.findElement(By.xpath("//div[@id='toolbar']")).click();
	FlightSearch.loadhandling(driver);
	//SSR Inventory
	
		if(driver.findElements(By.xpath("//div[text()= 'SSR Inventory']")).size()>0) {
			driver.findElement(By.xpath("//div[text()= 'SSR Inventory']")).click();
			FlightSearch.loadhandling(driver);
			
	
			//SSR INVENTORY
			//Flight Number
			driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).click();
			driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).clear();
			String flight=FlightSearch.getTrimTdata(queryObjects.getTestData("flghtNum"));
			driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).sendKeys(flight);
			//driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input")).sendKeys(Keys.ENTER);
						
			//From
			driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).click();
			driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).clear();
			String from2=FlightSearch.getTrimTdata(queryObjects.getTestData("From"));
			driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).sendKeys(from2);
			driver.findElement(By.xpath("//label[text()='From']/following-sibling::input")).sendKeys(Keys.ENTER);
	
	
			//To
			driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).click();
			driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).clear();
			String to2=FlightSearch.getTrimTdata(queryObjects.getTestData("To"));
			driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).sendKeys(to2);
			driver.findElement(By.xpath("//label[text()='To']/following-sibling::input")).sendKeys(Keys.ENTER);
			
	
			//Entering segment Date
			String sgetDate=FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
			int getDays=0;
			getDays=Integer.parseInt(sgetDate);
	
			Calendar calc = Calendar.getInstance();
			calc.add(Calendar.DATE, +getDays);
			String timeStampd = new SimpleDateFormat("MM/dd/yyyy").format(calc.getTime());
			WebElement DateXpath = driver.findElement(By.xpath("//md-datepicker//input"));
			DateXpath.clear();
			DateXpath.sendKeys(timeStampd);
			Thread.sleep(1000);
			
			//ssrcode
			driver.findElement(By.xpath("//label[text()='ssrcode']/following-sibling::input")).click();
			driver.findElement(By.xpath("//label[text()='ssrcode']/following-sibling::input")).clear();
			String ssr=FlightSearch.getTrimTdata(queryObjects.getTestData("SSR"));
			driver.findElement(By.xpath("//label[text()='ssrcode']/following-sibling::input")).sendKeys(ssr);
			driver.findElement(By.xpath("//label[text()='ssrcode']/following-sibling::input")).sendKeys(Keys.ENTER);
			
			
			//Search
			driver.findElement(By.xpath("//button[text()='Display']")).click();
			FlightSearch.loadhandling(driver);
			
			if(driver.findElements(By.xpath("//table/tbody/tr")).size()>0) {
				queryObjects.logStatus(driver, Status.PASS, "Validate SSR Search", "Validated SSR Search", null);
			}
			else
				queryObjects.logStatus(driver, Status.WARNING, "Validate SSR Search", "No Items in TBL", null);
											
			//Clear
			WebElement clear = driver.findElement(By.xpath("//button[text()='Cancel']/following-sibling::button"));
			clear.click();
			FlightSearch.loadhandling(driver);
			
			//Cancel
			WebElement cancel = driver.findElement(By.xpath("//button[text()='Cancel']"));
			clear.click();
			FlightSearch.loadhandling(driver);
			
					
		}
	
}

	
public void logout(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{

	driver.findElement(By.xpath("//md-menu-bar[1]/md-menu/button[@type='button']")).click();
	driver.findElement(By.xpath("//md-menu-item/button[contains(text(),'Logout')]")).click();
	FlightSearch.loadhandling(driver);
	
	if(driver.findElements(By.xpath("//div[@class='error-panel']")).size()>0)
		queryObjects.logStatus(driver, Status.PASS, "Application should logout", "Logout Successful", null);
	else
		queryObjects.logStatus(driver, Status.FAIL, "Application should logout", "Logout not Successful", null);
		
	}
	
}
