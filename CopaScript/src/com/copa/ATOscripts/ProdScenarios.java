package com.copa.ATOscripts;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Date;
import org.apache.bcel.generic.FDIV;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.RESscripts.*;

import com.copa.Util.ATOflowPageObjects;

import FrameworkCode.BFrameworkQueryObjects;


public class ProdScenarios extends ATOflowPageObjects{
	
	
	public void ProdScenarios(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception {
		
		WebDriverWait wait = new WebDriverWait(driver, 50);
		
		driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
		Thread.sleep(2000);
		driver.findElement(By.xpath(CheckInXpath)).click();
		FlightSearch.loadhandling(driver);
		
	//*************************Search ATO********************************************************************	
		String sCheckin = FlightSearch.getTrimTdata(queryObjects.getTestData("ProceedtoCheckin"));
		String sOrderNum = FlightSearch.getTrimTdata(queryObjects.getTestData("OrderNumber"));
		String sTicket = FlightSearch.getTrimTdata(queryObjects.getTestData("Ticket"));
		String sFFProgram = FlightSearch.getTrimTdata(queryObjects.getTestData("FFProgram"));
		String sFFnum = FlightSearch.getTrimTdata(queryObjects.getTestData("FFNumber"));
		String sSubmit = FlightSearch.getTrimTdata(queryObjects.getTestData("Submit"));
		String sErr = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_ErrMessage"));
		String sFlight = FlightSearch.getTrimTdata(queryObjects.getTestData("Flight"));
		String sDays = FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
		String sFlSearch = FlightSearch.getTrimTdata(queryObjects.getTestData("FlightSearch"));
		String sOrderSearch = FlightSearch.getTrimTdata(queryObjects.getTestData("OrderSearch"));
		String AssignSeats=FlightSearch.getTrimTdata(queryObjects.getTestData("Assign_Seats"));
		String sCheckinMsg = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_CheckinMsg"));
		String sSelSeatType= FlightSearch.getTrimTdata(queryObjects.getTestData("SeatType"));
		String sTo = FlightSearch.getTrimTdata(queryObjects.getTestData("To"));
		String sSpecialPass	= FlightSearch.getTrimTdata(queryObjects.getTestData("specialPassenger"));
		String sBlockorUnblock = FlightSearch.getTrimTdata(queryObjects.getTestData("Block_Unblock_Seats"));
		String Select_pax=FlightSearch.getTrimTdata(queryObjects.getTestData("Select_pax"));
		String sExitDays=FlightSearch.getTrimTdata(queryObjects.getTestData("ExitDays"));
		String ADCNotTrigger = FlightSearch.getTrimTdata(queryObjects.getTestData("ADCNotTrigger"));
		String sGName = FlightSearch.getTrimTdata(queryObjects.getTestData("GivenName"));
		String sSName = FlightSearch.getTrimTdata(queryObjects.getTestData("Sur_name"));
		
				boolean FLAG=false;
				
				if(sGName != "") {
					// driver.findElement(By.xpath("//input[contains(@ng-model,'givenName')]")).click();
					driver.findElement(By.xpath(GivenNameXpath)).sendKeys(sGName);
				}
				if(sSName != "") {
					//driver.findElement(By.xpath("//input[contains(@ng-model,'surName')]")).click();
					driver.findElement(By.xpath(SurNameXpath)).sendKeys(sSName);

				}
				if(sFlight.equalsIgnoreCase("Res") && sOrderSearch.equalsIgnoreCase("Y")) {
					sFlight= Login.FLIGHTNUM;
					driver.findElement(By.xpath(FlightXpath)).sendKeys(sFlight); 
					 FLAG=true;
				}
				else if(sFlight.equalsIgnoreCase("Res")&& sFlSearch.equalsIgnoreCase("Y") ) {
					sFlight= Login.FLIGHTNUM;
					driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sFlight);
					 FLAG=true;
					Calendar cal1 = Calendar.getInstance();    ///suman selectee
					SimpleDateFormat sdf = new  SimpleDateFormat("MM/dd/yyyy");
					if(!sDays.equalsIgnoreCase("")) 
						cal1.add(Calendar.DATE, Integer.parseInt(sDays));	
					else
						cal1.add(Calendar.DATE, 1);
					String newDate = sdf.format(cal1.getTime());
		
					driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).clear();
					driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).click();
					driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(newDate);
						 
				}else if(sFlight.equalsIgnoreCase("Shareflight")) {   //// enter the store pnr in env var which from share pnr  ///suman 13 june 2018
					sFlight = Login.shareflightnm;
					driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sFlight);
					FLAG=true;
				}else if(sFlight.equalsIgnoreCase("OpenFlight"))
				{
					if(sTo != "")
					{
						driver.findElement(By.xpath("//label[contains(text(),'Destination Code')]/following-sibling::input")).sendKeys(sTo);
						sFlight= Atoflow.OpenFlightSearch(driver,queryObjects, "");
						Login.FLIGHTNUM=sFlight;
						FlightSearch.loadhandling(driver);
						FLAG=true;
						driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sFlight);
					}
					else
					{
						sFlight= Atoflow.OpenFlightSearch(driver,queryObjects, "");
						Login.FLIGHTNUM=sFlight;
		
						FLAG=true;
						driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sFlight);	 
					}
				}
				else if(sFlight.equalsIgnoreCase("OutSync"))
				{
					FLAG=true;
					sFlight= Atoflow.GetOutofSync(driver,queryObjects);
					driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sFlight);
					sFlight="OutSync";
		
				}
				else if (sFlight != ""){
					FLAG=true;
					driver.findElement(By.xpath(FlightXpath)).sendKeys(sFlight); 
					driver.findElement(By.xpath(FlightXpath)).sendKeys(Keys.TAB);
				}
				//FLIGHT SEARCH
				if(sFlSearch.equalsIgnoreCase("Y")) {
					driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::div[@ng-if='flightSearch.display.gateSearchBtn']/button")).click();
					FlightSearch.loadhandling(driver); 
					if(sFlight != "OutSync") {
						boolean Flightdisplayed = driver.findElement(By.xpath("//div[contains(@class,'flight-details')]/div[contains(text(),'"+sFlight+"')]")).isDisplayed();
						if (Flightdisplayed) {
							queryObjects.logStatus(driver, Status.PASS, "Search Passenger by Flight details", "Passenger search completed: "+sFlight , null);
						}else {
							queryObjects.logStatus(driver, Status.FAIL, "Search Passenger by Flight details", "Passenger search completed: "+sFlight , null);
						}
					}
				}
				
				
				if(sFlight.equalsIgnoreCase("OutSync"))

				{
					//driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::div[@ng-if='flightSearch.display.gateSearchBtn']/button")).click();
					//FlightSearch.loadhandling(driver); 
					driver.findElement(By.xpath(TktNotSyncStatus)).click();
					List<WebElement> details = driver.findElements(By.xpath("//div[contains(@ng-class,'passengerItinerary.model.Flight')]"));
					details.get(0).click();
					FlightSearch.loadhandling(driver);
				}
				
				if(Select_pax.equalsIgnoreCase("Y")) {
					
					FlightSearch.loadhandling(driver);
					String Selectetext="selectee Pax";   //suman
					driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).clear();
					for (int i = 0; i < Selectetext.length(); i++){

						char c = Selectetext.charAt(i);
						String s = new StringBuilder().append(c).toString();
						driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(s);
					}

					driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(Keys.ENTER);
					FlightSearch.loadhandling(driver);
					// String Filter_Val= FlightSearch.getTrimTdata(queryObjects.getTestData("Filter_Val"));
					
					boolean Selecteeflag=true;
					if(Selecteeflag)
					{
						sOrderNum = Login.PNRNUM.trim();	
						boolean exist=false;
						try { exist = driver.findElement(By.xpath("(//span[text()='"+sOrderNum+"'])[1]")).isDisplayed();
						}catch(Exception e) {}
						if (exist) {
							queryObjects.logStatus(driver, Status.PASS, "Checking the selectee PAX "+sOrderNum, "Given selectee pax found",null);
						}else {
							queryObjects.logStatus(driver, Status.FAIL, "Checking the selectee PAX "+sOrderNum, "Given selectee pax NOT found",null);
						}
						
						  
						
					}

				}
				
				
				//ORDER SEARCH
				if(sOrderNum != ""  ){
					try {
						if(sOrderNum.equalsIgnoreCase("RESPNR")){
							sOrderNum = Login.PNRNUM.trim();				 
						}
					}catch(Exception e) {}

					driver.findElement(By.xpath(OrderNumXpath)).sendKeys(sOrderNum);
				}
				
				if(sOrderSearch.equalsIgnoreCase("Y")) {
					driver.findElement(By.xpath(SearchXpath)).click();
					FlightSearch.loadhandling(driver);
				}
				
		
	//*********************************MULTIPLE MATCHES*****************************************************
				boolean MultipleMatches = false;
				boolean MultipleFlight = false;

				try {
					MultipleMatches = driver.findElement(By.xpath(FirstpnrXpath)).isDisplayed();


				} catch(Exception e) {}

				try {
					MultipleFlight = driver.findElement(By.xpath(FirstflightXpath)).isDisplayed();


				} catch(Exception e) {}


				if (MultipleMatches || MultipleFlight) {
					queryObjects.logStatus(driver, Status.PASS, "Search Passenger resulted in multiple passengers", "multiple passenger dispalyed ", null);


					if (MultipleFlight) {
						try {
							FlightSearch.loadhandling(driver);
							List<WebElement> Flights =  driver.findElements(By.xpath(FirstflightXpath));
							Thread.sleep(1000);
							Flights.get(0).click();
							FlightSearch.loadhandling(driver);					
						}
						catch(Exception e) {
							queryObjects.logStatus(driver, Status.FAIL, "Search Passenger ", "search not completed: " , e); 
						}
					}
					if (MultipleMatches) {
						try {
							FlightSearch.loadhandling(driver);
							List<WebElement> Orders =  driver.findElements(By.xpath(FirstpnrXpath));
							Thread.sleep(1000);
							Orders.get(0).click();
							//String strPNR = 
							//List<WebElement> pnrs =driver.findElements(By.xpath("//div[contains(text(),'"+strPNR+"')]"));
							//pnrs.get(1).click();
							FlightSearch.loadhandling(driver);

						}
						catch(Exception e) {
							queryObjects.logStatus(driver, Status.FAIL, "Search Passenger ", "search not completed: " , e); 
						}
					}
				}
	//********************************BLOCK/UNBLOCK**********************************************************
				if (!sBlockorUnblock.equalsIgnoreCase("")){
					driver.findElement(By.xpath("//md-select[@aria-label='Flight Actions']")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//md-option[contains(@value,'update-seatmap')]")).click();
					FlightSearch.loadhandling(driver);

					List<WebElement> blocksets=driver.findElements(By.xpath("//div[contains(@class,'icn-blocked')]"));
					int iBlockSeats=blocksets.size();
					Thread.sleep(2000);
					List<WebElement> available=driver.findElements(By.xpath("//div[@class='seat']/div[contains(@class,'icn-available') or contains(@class,'icn-chargeable') and not (contains(@class,'icn-held')) and not (contains(@class,'icn-blocked'))]"));
					int iAvilableseats=available.size();
					driver.findElement(By.xpath("//md-radio-button[@aria-label='Local Block']")).click();
					if (sBlockorUnblock.equalsIgnoreCase("BLOCK")) {
						available.get(0).click();
						Thread.sleep(2000);
						driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
						FlightSearch.loadhandling(driver);
						try {
							boolean blnerr = driver.findElement(By.xpath("//span[text()='Function restricted to controlling agents']")).isDisplayed();
							if (blnerr) {
								driver.findElement(By.xpath("//md-select[@aria-label='Flight Actions']")).click();
								Thread.sleep(2000);
								driver.findElement(By.xpath("//md-option[contains(@value,'controllingAgents')]")).click();
								FlightSearch.loadhandling(driver);

								driver.findElement(By.xpath("//input[contains(@name,'agent')]")).sendKeys(Login.Usernm);
								Thread.sleep(2000);
								driver.findElement(By.xpath("//input[contains(@name,'agent')]")).sendKeys(Keys.TAB);
								Thread.sleep(1000);
								driver.findElement(By.xpath("//button[text()='Add']")).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//button[text()='Save']")).click();
								FlightSearch.loadhandling(driver);
							}

							//.get(0).click();
							Thread.sleep(2000);
							driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
							FlightSearch.loadhandling(driver);
						}catch (Exception e) {}


						List<WebElement> afterupdateblocksets=driver.findElements(By.xpath("//div[contains(@class,'icn-blocked')]"));
						int iafterupdateBlockSeats=afterupdateblocksets.size();
						if(iafterupdateBlockSeats>iBlockSeats)
							queryObjects.logStatus(driver, Status.PASS, "Checking the Seat has blocked or not", "Seat has blocked: " , null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Checking the Seat has blocked or not", "Seat has not blocked:", null);
					}
					else if(sBlockorUnblock.equalsIgnoreCase("UNBLOCK")){
						blocksets.get(0).click();
						Thread.sleep(2000);
						driver.findElement(By.xpath("//div[contains(text(),'Undo')]")).click();
						FlightSearch.loadhandling(driver);
						List<WebElement> afterupdateblocksets=driver.findElements(By.xpath("//div[contains(@class,'icn-blocked')]"));
						int iafterupdateBlockSeats=afterupdateblocksets.size();
						if(iafterupdateBlockSeats<iBlockSeats)
							queryObjects.logStatus(driver, Status.PASS, "Checking the Seat has blocked or not", "Seat has unblocked : " , null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Checking the Seat has blocked or not", "Seat has not unblocked:", null);
					}		
				}
				
	//************************* FILL ADC/APIS********************************************************************	
				
				if (sCheckin.equalsIgnoreCase("Y")) {

					boolean Proceed = false;
					try {
						Proceed = driver.findElement(By.xpath(ProceedCheckInXpath)).isDisplayed();
					}catch(Exception e){}

					if (Proceed) {
						Thread.sleep(1000);
						driver.findElement(By.xpath(ProceedCheckInXpath)).click();
						FlightSearch.loadhandling(driver);
					}
				}
				try {
					String PaxNmae=driver.findElement(By.xpath("//div/div[contains(@ng-repeat,'OrderPassenger.finalDocuments')]")).getText().trim();
					String[] lastname=PaxNmae.split("/");
					String name=lastname[0];
				}catch(Exception e) {}

				boolean IsExpectedCondition =false;
				boolean IsEnabled=false;
				boolean IsEnabledCOR=false;
				try {
					IsExpectedCondition = driver.findElement(By.xpath(SecurityXpath)).isDisplayed();
					IsEnabled = driver.findElement(By.xpath(SurnameXpath1)).isEnabled();

					IsEnabledCOR = driver.findElement(By.xpath(CORXpath)).isEnabled();
				}catch(Exception e) {}
				//suman
				if(IsEnabledCOR && (!IsEnabled))
				{
					driver.findElement(By.xpath(CORXpath)).sendKeys("US"); 
					driver.findElement(By.xpath(CORXpath)).sendKeys(Keys.ENTER);  

				}

				if (IsExpectedCondition && IsEnabled) {
					queryObjects.logStatus(driver, Status.PASS, "Navigated to the Security Document Verification Page", "Expected Page dispalyed ", null);

					Atoflow.EnterDocCheck(driver,queryObjects);
					Atoflow.HandleSecDoc(driver, queryObjects);
					
					try{
						driver.findElement(By.xpath(SubmitXpath)).click();
					}catch(Exception e) {}
				}
				
				
				else {
					if (ADCNotTrigger.equalsIgnoreCase("Y")) {
						queryObjects.logStatus(driver, Status.PASS, "ADC/APIS was not triggered", "Expected Page dispalyed "+sOrderNum , null);
					}else {
						boolean blndone=false;
						try {
						blndone= driver.findElement(By.xpath(DoneXpath)).isDisplayed();
						}catch(Exception e) {}
						if (blndone) {
							queryObjects.logStatus(driver, Status.PASS, "ADC was completed by default", "ADC was completed by default " , null);
						}else {
							queryObjects.logStatus(driver, Status.FAIL, "UnExpected Page dispalyed", "UnExpected Page dispalyed "+sFlight , null);
						}
					
					}
				}
			
//**************************************complete checkin*************************************************************************
				if (sCheckin.equalsIgnoreCase("Y")) {
				try {
				
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DoneXpath)));
					driver.findElement(By.xpath(DoneXpath)).click();
					FlightSearch.loadhandling(driver);
				}catch(Exception e) {}
				
				}
	//************************AssignSeat*******************************************************************************************
				if (AssignSeats.equalsIgnoreCase("Y")) {
					boolean Assignseat = false;
					driver.findElement(By.xpath("//i[@class='icon-seatmap']")).click();
					FlightSearch.loadhandling(driver);
					List<WebElement> ChkCnt = driver.findElements(By.xpath(FlightLegChkbox));
					if (sCheckinMsg.equalsIgnoreCase("Partial routing check In")) {
						Assignseat=false;
						if ((Boolean.parseBoolean(driver.findElement(By.xpath(FlightLegChkbox+"[1]")).getAttribute("aria-checked")))==true && (Boolean.parseBoolean(driver.findElement(By.xpath(FlightLegChkbox+"[2]")).getAttribute("aria-checked")))==true) {
							driver.findElement(By.xpath(FlightLegChkbox+"[2]")).click();
							if ((Boolean.parseBoolean(driver.findElement(By.xpath(FlightLegChkbox+"[2]")).getAttribute("aria-checked")))==false) {
								Assignseat =true;
								queryObjects.logStatus(driver, Status.PASS, "Select one leg ", "Only one leg is selected, secobnd leg is deselected", null);
							}

						}
					}else {
						for (int i = 1; i <= ChkCnt.size(); i++) {
							Assignseat=false;
							if ((Boolean.parseBoolean(driver.findElement(By.xpath(FlightLegChkbox+"["+i+"]")).getAttribute("aria-checked")))==true) {
								List<WebElement> AvSeat =driver.findElements(By.xpath("//div[@class='seat-holder ng-scope icn-available']"));
								AvSeat.get(1).click();
								Assignseat =true;
							}else {
								driver.findElement(By.xpath(FlightLegChkbox+"["+i+"]")).click();
								driver.findElement(By.xpath("(//div[@class='seat-holder ng-scope icn-available']//following-sibling::span)[1]")).click();
								Assignseat=true;
							}				
						}
					}			
					if (Assignseat) {
						if (!sSelSeatType.isEmpty()) {
							if (sCheckinMsg.equalsIgnoreCase("Split Seating")) {
								if (driver.findElements(By.xpath("//md-select[@aria-label='Passenger Seat Characteristics']")).size()==1) 
									queryObjects.logStatus(driver, Status.PASS, "Generic seat drop down for all pax ", "Single Generic seat drop down is available for all pax, Split seating is not possible ", null);
								else 
									queryObjects.logStatus(driver, Status.FAIL, "Generic seat drop down for all pax ", "Single Generic set drop verification failed", null);
							}
							driver.findElement(By.xpath("//md-select[@aria-label='Passenger Seat Characteristics']")).click();
							driver.findElement(By.xpath("//md-option/div[contains(text(),'"+sSelSeatType+"')]")).click();
							try {
								driver.findElement(By.xpath("//button[contains(text(),'Yes')]")).click();
							} catch (Exception e) { 
							// TODO: handle exception
							}
						}

						driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
						FlightSearch.loadhandling(driver);
						if (sCheckinMsg.equalsIgnoreCase("Partial routing check In")) {
							if ((Boolean.parseBoolean(driver.findElement(By.xpath(FlightLegChkbox+"[1]")).getAttribute("aria-checked")))==true && (Boolean.parseBoolean(driver.findElement(By.xpath(FlightLegChkbox+"[2]")).getAttribute("aria-checked")))==true) {
								queryObjects.logStatus(driver, Status.PASS, "Partial routing checkin ", "Partial routing checkin is not allowed, both the legs got selected ", null);								
							}else {
								queryObjects.logStatus(driver, Status.FAIL, "Partial routing checkin ", "Partial routing checkin is allowed.", null);
							}
						}
						driver.findElement(By.xpath("//button[contains(text(),'Continue')]")).click();
						FlightSearch.loadhandling(driver);
						boolean SeatTypeStatus = false;
						List<WebElement> SeatAssigned = driver.findElements(By.xpath("//input[@ng-model='seat.NewSeatNumber']"));

						for (int j = 0; j < SeatAssigned.size(); j++) {
							SeatAssigned = driver.findElements(By.xpath("//input[@ng-model='seat.NewSeatNumber']"));

							if (!SeatAssigned.get(j).getAttribute("value").trim().isEmpty()) {
								queryObjects.logStatus(driver, Status.PASS, "Check the Seat assigned status for Segment "+j, "Seat is assigned for the route ", null);
								if (SeatTypeStatus == false && sSelSeatType.equalsIgnoreCase("Aisle") && (SeatAssigned.get(j).getAttribute("value").trim().contains("C") || SeatAssigned.get(j).getAttribute("value").trim().contains("D"))) {
									SeatTypeStatus=true;
								}				

							} 
							else 
								queryObjects.logStatus(driver, Status.FAIL, "Check the Seat assigned status for Segment "+j, "Seat is not assigned ", null);

						}
						if (SeatTypeStatus && sSelSeatType.equalsIgnoreCase("Aisle")) {
							queryObjects.logStatus(driver, Status.PASS, "Assign Aisle seat ", "Aisle Seat is assigned for the passenger", null);
						}else if (SeatTypeStatus==false && sSelSeatType.equalsIgnoreCase("Aisle")) {
							queryObjects.logStatus(driver, Status.FAIL, "Assign Aisle seat ", "Aisle Seat is not assigned ", null);
						}
					}
					
				}

				//*****************************Add Baggage****************************************************************
				//Add Baggage

				String AddBaggagekg = FlightSearch.getTrimTdata(queryObjects.getTestData("AddBaggagekg"));
				String AllowBag = FlightSearch.getTrimTdata(queryObjects.getTestData("AllowBag"));		
				String MulBaggageWgt = FlightSearch.getTrimTdata(queryObjects.getTestData("MultiplebagWgt"));
				String MulBaggageType = FlightSearch.getTrimTdata(queryObjects.getTestData("MulBagType"));
				String BagType="";
				String BagForAllPax = FlightSearch.getTrimTdata(queryObjects.getTestData("BagForAllPax"));
				String BagForSpecificPax = FlightSearch.getTrimTdata(queryObjects.getTestData("BagForSpecificPax"));
				String Catalog = FlightSearch.getTrimTdata(queryObjects.getTestData("CatalogValue"));

				if (!AddBaggagekg.isEmpty()|| !MulBaggageWgt.isEmpty()) {			



					try {
						if(BagForAllPax.equalsIgnoreCase("Y")) {
							List<WebElement> AddBaggage =  driver.findElements(By.xpath(BaggageXpath));

							for (int i = 0; i < AddBaggage.size(); i++) {
								AddBaggage =  driver.findElements(By.xpath(BaggageXpath));

								AddBaggage.get(i).click();

								Atoflow.CompleteBaggage(driver,queryObjects, AddBaggagekg, AllowBag, MulBaggageWgt, MulBaggageType, "", Catalog);
							} //for loop 
						}// /if all pax

						if(!BagForSpecificPax.isEmpty()) {
							if (BagForSpecificPax.contains(";")) {
								String SplitMulBag[] = BagForSpecificPax.split(";");
								for (int p = 0; p < SplitMulBag.length; p++) {
									driver.findElement(By.xpath("//span[contains(text(),'"+SplitMulBag[p]+"')]/ancestor::div[contains(@ng-repeat,'orderPassengers')]//i[@class='icon-baggage']/parent::*")).click();
								}
							}else {
								driver.findElement(By.xpath("//span[contains(text(),'"+BagForSpecificPax+"')]/ancestor::div[contains(@ng-repeat,'orderPassengers')]//i[@class='icon-baggage']/parent::*")).click();	 
							} 

							Atoflow.CompleteBaggage(driver,queryObjects, AddBaggagekg, AllowBag, MulBaggageWgt, MulBaggageType, "", Catalog);
						}

						

					}catch (Exception e) {}
				}
				
//************************SpecialPassenger***********************************************************************************
				if(sSpecialPass!="") {
					driver.findElement(By.xpath("//md-input-container//md-select[@aria-label='Checkin Passenger Actions']")).click();//added by Jenny
					driver.findElement(By.xpath("//md-option/div[text()='Special Passenger']")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//md-dialog//div[text()='Special Passenger']")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//pssgui-menu[@startup='SpecialPassengerTypeTable']//md-select")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//div/span[contains(text(),'"+sSpecialPass+"')]")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//button[text()='OK']")).click();
					FlightSearch.loadhandling(driver);
					Boolean Check =driver.findElement(By.xpath("//button[text()='Check In']")).isDisplayed();
					if (Check)
					{
						queryObjects.logStatus(driver, Status.PASS, sSpecialPass+" was successful", "Special Passenger selected ", null); 
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, sSpecialPass+" was not successful", "Special Passenger not selected ", null); 
					}
				}


//**********************final Checkin *****************************************************************************************
				if (sCheckin.equalsIgnoreCase("Y")) {
					try{
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FinalCheckInXpath)));
						driver.findElement(By.xpath(FinalCheckInXpath)).click();
						FlightSearch.loadhandling(driver);
					}catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Check in was not successful ", "Exception caught ", e);
					}
					
					//validation of check in success
					boolean confirmok = false;
					try {
						confirmok = driver.findElement(By.xpath(ConfirmOKXpath)).isDisplayed();

					}catch (Exception e) {}

					if (confirmok) {
						if (driver.findElement(By.xpath("("+GreenChkMarkIcon+")[1]")).isDisplayed()) {
							if (driver.findElements(By.xpath("//i[@class='icon-checked-in icon-small active-state']")).size()==driver.findElements(By.xpath("//div[@class='pssgui-design-heading-3-bg ng-binding']")).size()) {
								queryObjects.logStatus(driver, Status.PASS, "Checkin green mark status ", "Green check mark is displayed for all the checked in passenger in all flights ", null);
								queryObjects.logStatus(driver, Status.PASS, "Check in successful ", "Check in successful ", null);
							}else {
								queryObjects.logStatus(driver, Status.FAIL, "Checkin green mark status ", "Green check mark is not displayed for the checked in passenger in all flights ", null);
								queryObjects.logStatus(driver, Status.FAIL, "Check in Unsuccessful ", "Check in Un successful ", null);
							}
						}
						else{
							
							queryObjects.logStatus(driver, Status.FAIL, "Checkin green mark status ", "Green check mark is not displayed for the checked in passenger in all flights ", null);
							queryObjects.logStatus(driver, Status.FAIL, "Check in Unsuccessful ", "Check in Un successful ", null);
							
						}

						if (FlightSearch.getTrimTdata(queryObjects.getTestData("ReturnToCheckin")).equalsIgnoreCase("Y")) {
							
							driver.findElement(By.xpath(ReturnXpath)).click();
							confirmok=false;
						}else {
							driver.findElement(By.xpath(ConfirmOKXpath)).click();
						}
					}
					else {
						if(sCheckinMsg.contains("ticket out of sync")) {
							if (driver.findElement(By.xpath("//p[contains(text(),'Ticket Out of Sync')]")).isDisplayed()) {
								queryObjects.logStatus(driver, Status.PASS, "Ticket out of sync message was displayed ", "Expected message displayed ", null);
								driver.findElement(By.xpath("//button[text()='OK']")).click();
								FlightSearch.loadhandling(driver);
								if (driver.findElement(By.xpath("//div[text()='Tickets']")).isDisplayed()) {
									queryObjects.logStatus(driver, Status.PASS, "Ticket redirected to RESGUI ", "Expected page displayed ", null);
								}else {
									queryObjects.logStatus(driver, Status.FAIL, "Ticket not redirected to RESGUI ", "Expected page not displayed ", null);
								}
							}else {
								queryObjects.logStatus(driver, Status.FAIL, "Ticket out of sync message was not displayed ", "unExpected message displayed ", null);
							}
							
						}else {
						
						queryObjects.logStatus(driver, Status.FAIL, "Check in was not successful ", "Confirmation Screen not displayed", null);
					}

				}	
	}

}
	public void Specific(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception{

		WebDriverWait wait = new WebDriverWait(driver, 50);
		String sFlight = Login.FLIGHTNUM;
		String sSearchby = FlightSearch.getTrimTdata(queryObjects.getTestData("SearchBy"));
		String sSurname = FlightSearch.getTrimTdata(queryObjects.getTestData("surname"));
		String sPnr = Login.PNRNUM;
		String sDelete = FlightSearch.getTrimTdata(queryObjects.getTestData("Delete"));
		
		String sFName = FlightSearch.getTrimTdata(queryObjects.getTestData("FirstName"));
		String sSNameS = FlightSearch.getTrimTdata(queryObjects.getTestData("SurName"));				
		String sAge = FlightSearch.getTrimTdata(queryObjects.getTestData("Age"));
		String sGender = FlightSearch.getTrimTdata(queryObjects.getTestData("Gender"));
		String sDocType = FlightSearch.getTrimTdata(queryObjects.getTestData("DocumentType"));
		String sDocNum = FlightSearch.getTrimTdata(queryObjects.getTestData("DocumentNum"));
		String sNationality = FlightSearch.getTrimTdata(queryObjects.getTestData("Nationality"));
		String sCOI = FlightSearch.getTrimTdata(queryObjects.getTestData("COI"));
		String sExpdays = FlightSearch.getTrimTdata(queryObjects.getTestData("ExpiryDays"));
		String sCOR = FlightSearch.getTrimTdata(queryObjects.getTestData("COR"));
		String sExitDays=FlightSearch.getTrimTdata(queryObjects.getTestData("ExitDays"));

		driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
		Thread.sleep(2000);
		driver.findElement(By.xpath(CheckInXpath)).click();
		FlightSearch.loadhandling(driver);
		 
		if(!sFlight.equalsIgnoreCase("")){
			driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sFlight);
			Thread.sleep(1000);
			driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::div[@ng-if='flightSearch.display.gateSearchBtn']/button")).click();
			FlightSearch.loadhandling(driver);
		}
		if(sSearchby.equalsIgnoreCase("Name")){
			if(!sSurname.equalsIgnoreCase("")){
				driver.findElement(By.xpath("//pssgui-menu[@action='dropdown']//span[@class='md-select-icon']")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//md-option[@value='passenger_name']//div[contains(text(),'Name')]")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//input[@aria-label='Enter']")).sendKeys(sSurname);
				if(driver.findElements(By.xpath("//span[contains(text(),'"+sPnr+"')]")).size()>0){
					queryObjects.logStatus(driver, Status.PASS, "Found the PNR with given Surname", "PNR Got", null);
				}
				else
					queryObjects.logStatus(driver, Status.FAIL, "Not Found the PNR with given Surname", "PNR not available", null);
			}
			else
				queryObjects.logStatus(driver, Status.FAIL, "Surname is not given", "Test Data Issue", null);
		}
		if(sDelete.equalsIgnoreCase("Y")){
			driver.findElement(By.xpath("//md-checkbox[@aria-label='pax-chk']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(ProceedCheckInXpath)).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//button[contains(text(),'Delete')]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//button[contains(text(),'Yes')]")).click();
			FlightSearch.loadhandling(driver);
			queryObjects.logStatus(driver, Status.PASS, "Deleted the APIS details", "APIS Deleted", null);
			driver.findElement(By.xpath("//div[contains(text(),'Back')]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//span[contains(text(),'Doc Check')]")).click();
			FlightSearch.loadhandling(driver);
			
			Calendar cal = Calendar.getInstance();
			String sDate = "";
			if (sAge.contains("/")) {//Added by Jenny)
				sDate = sAge;					
			} else {
				cal.add(Calendar.YEAR, -Integer.parseInt(sAge));
				sDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
			}
			driver.findElement(By.xpath(SurnameXpath1)).sendKeys(sSNameS);

			driver.findElement(By.xpath(FirstnameXpath)).sendKeys(sFName);



			driver.findElement(By.xpath(BirthDateXpath)).sendKeys(sDate);
			String gender;

			//Selecting Gender
			if (!sGender.isEmpty()) 
				gender = sGender;					 
			else 
				gender = "Male";
			driver.findElement(By.xpath(genderXpath)).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//md-option[div[div[contains(text(),'" + gender + "')]]]")).click();
			Thread.sleep(2000);

			//selecting document type
			
			driver.findElement(By.xpath(DocTypeXpath)).click();
			driver.findElement(By.xpath("//md-option[div[normalize-space(text())='"+sDocType+"']]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(DocIDXpath)).sendKeys(sDocNum);
			Thread.sleep(1000);

			Calendar calen = Calendar.getInstance();
			String sExpDate="";
			if (sExpdays.contains("/")) {//Added by Jenny)
				sExpDate = sExpdays;						
			} else {
				calen.add(Calendar.MONTH, Integer.parseInt(sExpdays));
				sExpDate = new SimpleDateFormat("MM/dd/yyyy").format(calen.getTime());
			}

			FlightSearch.loadhandling(driver);
			for (int i = 0; i < sExpDate.length(); i++){

				char c = sExpDate.charAt(i);
				String s = new StringBuilder().append(c).toString();
				driver.findElement(By.xpath(ExpdateXpath)).sendKeys(s);

			}
			driver.findElement(By.xpath(COIXpath)).sendKeys(sCOI);
			Thread.sleep(2000);
			driver.findElement(By.xpath(COIXpath)).sendKeys(Keys.TAB);
			Thread.sleep(2000);
			driver.findElement(By.xpath(NtionalXpath)).sendKeys(sNationality);
			Thread.sleep(1000);
			driver.findElement(By.xpath(NtionalXpath)).sendKeys(Keys.TAB);
			Thread.sleep(2000);
			driver.findElement(By.xpath(CORXpath)).sendKeys(sCOR);
			Thread.sleep(1000);
			driver.findElement(By.xpath(CORXpath)).sendKeys(Keys.TAB);
			Thread.sleep(3000);
			if(driver.findElements(By.xpath(ExitdateXpath)).size()>0){
				
				Calendar calend = Calendar.getInstance();
				String sExitDate="";
				if (sExitDays!=""){
					calend.add(Calendar.DATE, +Integer.parseInt(sExitDays));
				}
				calend.add(Calendar.DATE, +10);
				sExitDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
				driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExitDate);
				Thread.sleep(1000);
				driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);
			}
			if(driver.findElements(By.xpath(ExitdateJustificationXpath)).size()>0){
				
				driver.findElement(By.xpath(ExitdateJustificationXpath)).sendKeys("Justification");
				Thread.sleep(1000);
				driver.findElement(By.xpath(ExitdateJustificationXpath)).sendKeys(Keys.TAB);
			}
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath(SubmitXpath)).click();
			FlightSearch.loadhandling(driver);
			if(driver.findElements(By.xpath("//span[contains(text(),'APIS Complete')]")).size()>0){
				queryObjects.logStatus(driver, Status.PASS, "Updated the APIS details", "APIS Completed", null);
			}
			else
				queryObjects.logStatus(driver, Status.FAIL, "APIS details not updated", "APIS In-Completed", null);
			if(driver.findElements(By.xpath("//span[contains(text(),'ADC OK')]")).size()>0){
				queryObjects.logStatus(driver, Status.PASS, "Updated the ADC details", "ADC Completed", null);
			}
			else
				queryObjects.logStatus(driver, Status.FAIL, "ADC details not updated", "ADC In-Completed", null);
		}
	}
}
