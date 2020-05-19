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
import com.copa.Util.FlightSearchPageObjects;

import FrameworkCode.BFrameworkQueryObjects;



public class Regression_GUIATO_02 extends ATOflowPageObjects{

	public static String PaxName;


	boolean FLAG;
	boolean ChangeSaetFlag;
	boolean status=false;  ///checked in status
	public String name;
	public static String PnrNum ="";

	String FLTNO="";
	public String selecteePaxName="";  ///suman selectee

	public static String tktemd1=null;
	public static String sCheckinMsg = "";
	
	public void SearchPassenger(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception{

		WebDriverWait wait = new WebDriverWait(driver, 50);
        String sGateCheckin="";
		try{

			Regression_GUIATO_02.searchPNR(driver, queryObjects);
			sGateCheckin = FlightSearch.getTrimTdata(queryObjects.getTestData("GateCheckin"));
			String sDestn = "";
			String fDate = "";
			
			

			driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
			Thread.sleep(2000);
			if(sGateCheckin.equalsIgnoreCase("Yes"))
			{
					
				driver.findElement(By.xpath(gate)).click();
				FlightSearch.loadhandling(driver);
			}
			else
			{
				driver.findElement(By.xpath(CheckInXpath)).click();
				FlightSearch.loadhandling(driver);	
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LabelPassengerXpath)));
				queryObjects.logStatus(driver, Status.PASS, "Search Passenger", "Search Passenger displayed ",null);
			}
			
			
				//Regression_GUIATO_02.searchPNR(driver, queryObjects);
				
			

			String Seat_Change="";
			String sGName = FlightSearch.getTrimTdata(queryObjects.getTestData("GivenName"));
			String sSName = FlightSearch.getTrimTdata(queryObjects.getTestData("Sur_name"));
			String sFrom = FlightSearch.getTrimTdata(queryObjects.getTestData("From"));
			String sTo = FlightSearch.getTrimTdata(queryObjects.getTestData("To"));
			String sFlight = FlightSearch.getTrimTdata(queryObjects.getTestData("Flight"));
			String sDays = FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
			String sSearch = FlightSearch.getTrimTdata(queryObjects.getTestData("Search"));
			String sCheckin = FlightSearch.getTrimTdata(queryObjects.getTestData("ProceedtoCheckin"));
			String sOrderNum = FlightSearch.getTrimTdata(queryObjects.getTestData("OrderNumber"));
			String sTicket = FlightSearch.getTrimTdata(queryObjects.getTestData("Ticket"));
			
			String sSubmit = FlightSearch.getTrimTdata(queryObjects.getTestData("Submit"));
			String sErr = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_ErrMessage"));
			String[] TagNumber1 = null;

			sCheckinMsg = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_CheckinMsg"));
			
			

			boolean Orderselect = false;
			String Flightnumber=null;
			String[] Flightnumber1=null;
			String RetVal;
			// String sDestn = "";
			//String fDate = "";
			String Flight2 ="";


		
		

			
			
			if(sFlight.equalsIgnoreCase("Res")) {
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
				//driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::div[@ng-if='flightSearch.display.gateSearchBtn']/button")).click();	 
			}

			else if (sFlight != ""){
				driver.findElement(By.xpath(FlightXpath)).sendKeys(sFlight); 
			}
		
			if(sGName != "") {
				// driver.findElement(By.xpath("//input[contains(@ng-model,'givenName')]")).click();
				driver.findElement(By.xpath(GivenNameXpath)).sendKeys(sGName);
			}
			if(sSName != "") {
				//driver.findElement(By.xpath("//input[contains(@ng-model,'surName')]")).click();
				driver.findElement(By.xpath(SurNameXpath)).sendKeys(sSName);

			}

			if(sFrom != "") {
				driver.findElement(By.xpath(FromXpath)).clear();
				driver.findElement(By.xpath(FromXpath)).sendKeys(sFrom);
				driver.findElement(By.xpath(FromXpath)).sendKeys(Keys.TAB);
			}	
			if(sTo != "") {
				driver.findElement(By.xpath(ToXpath)).sendKeys(sTo);
				driver.findElement(By.xpath(ToXpath)).sendKeys(Keys.TAB);
			}
			if(sOrderNum != "" && Orderselect==false){
				try {
					if(sOrderNum.equalsIgnoreCase("RESPNR")){
						sOrderNum = Login.PNRNUM.trim();				 
					}
				}catch(Exception e) {}

				driver.findElement(By.xpath(OrderNumXpath)).sendKeys(sOrderNum);
			}	 


			if(sTicket != "")
				driver.findElement(By.xpath(TicketXpath)).sendKeys(sTicket);

			if(sDays != "") {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, Integer.parseInt(sDays));
				String sDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
				driver.findElement(By.xpath(DateXpath)).clear();
				driver.findElement(By.xpath(DateXpath)).sendKeys(sDate);
				Thread.sleep(1000);
			}
			
			FlightSearch.loadhandling(driver);


			//Submit or Proceed to check in page

			if (sSearch.equalsIgnoreCase("Y")) {         ////suman  UPDATED ON 8th May 2018 
				if(FLAG && sFlight.equalsIgnoreCase("OutSync"))

				{
					driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::div[@ng-if='flightSearch.display.gateSearchBtn']/button")).click();
					FlightSearch.loadhandling(driver); 
					driver.findElement(By.xpath(TktNotSyncStatus)).click();
					List<WebElement> details = driver.findElements(By.xpath("//div[contains(@ng-class,'passengerItinerary.model.Flight')]"));
					details.get(0).click();
					FlightSearch.loadhandling(driver);
				}

				//else if(FLAG && !sFlight.equalsIgnoreCase("OutSync"))
				else if(FLAG )	 
				{
					driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::div[@ng-if='flightSearch.display.gateSearchBtn']/button")).click();
					FlightSearch.loadhandling(driver); 
					/* driver.findElement(By.xpath(TktNotSyncStatus)).click();
				 List<WebElement> details = driver.findElements(By.xpath("//div[contains(@ng-class,'passengerItinerary.action')]"));
				 details.get(0).click();*/
				}
				else
				{

					driver.findElement(By.xpath(SearchXpath)).click();
					FlightSearch.loadhandling(driver);
				}

			}
			
			//validation of the search parameters
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
						queryObjects.logStatus(driver, Status.FAIL, "Search Passenger ", "OrderID search not completed: " , e); 
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
						queryObjects.logStatus(driver, Status.FAIL, "Search Passenger ", "OrderID search not completed: " , e); 
					}
				}
			}

			if(sOrderNum != "") {
				try {
					if(sOrderNum.equalsIgnoreCase("RESPNR")){
						sOrderNum = Login.PNRNUM.trim();				 
					}
					boolean OrderId = driver.findElement(By.xpath("//*[contains(text(),'"+sOrderNum+"')]")).isDisplayed();
					if (OrderId) {
						queryObjects.logStatus(driver, Status.PASS, "Search Passenger by Order ID", "OrderID search completed: "+sOrderNum , null);
					}else {
						queryObjects.logStatus(driver, Status.FAIL, "Search Passenger by Order ID "+sOrderNum, "OrderID search not completed: "+sOrderNum , null);
					}

				}catch(Exception e) {}
			}

			
			//Proceed to check in
			if (sCheckin.equalsIgnoreCase("Y")) {

				boolean Proceed = false;
				try {

					//button[(text()='Proceed to Check In') and @disabled='disabled']
					//button[(text()='Proceed to Check In') and @disabled='disabled']
					Proceed = driver.findElement(By.xpath(ProceedCheckInXpath)).isDisplayed();
				}catch(Exception e){}

				if (Proceed) {
					Thread.sleep(1000);
					if((driver.findElements(By.xpath("//button[(text()='Proceed to Check In') and @disabled='disabled']")).size()>0))
					{
						

						String Paxno=FlightSearch.getTrimTdata(queryObjects.getTestData("pax_nocheckin"));
						int no=Integer.parseInt(Paxno);
						
						int k=0;
						List<WebElement> SelectPnr=driver.findElements(By.xpath("//div[contains(@ng-repeat,'passengerItinerary')]/child::div/div[1]/div[2]/i[contains(@class,'in-active-state')]//parent::div/preceding-sibling::md-checkbox/div[1]"));
						for(int i=0;i<no;i++)
						{    
							SelectPnr.get(k).click();
							
							Thread.sleep(1000);
							++k;
						}

						// }

					}

					driver.findElement(By.xpath(ProceedCheckInXpath)).click();
					FlightSearch.loadhandling(driver);
				}	
				

				try {
					driver.findElement(By.xpath(OKXpath)).click();
					FlightSearch.loadhandling(driver);

				}catch (Exception e) {}

				try {
					String PaxNmae=driver.findElement(By.xpath("//div/div[contains(@ng-repeat,'OrderPassenger.finalDocuments')]")).getText().trim();
					String[] lastname=PaxNmae.split("/");
					name=lastname[0];
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
				if((driver.findElements(By.xpath("//input[@aria-label='Justification']")).size())>0)
				{
					
					driver.findElement(By.xpath("//input[@aria-label='Justification']")).sendKeys("Automation testing");
					queryObjects.logStatus(driver, Status.INFO, "Checking the Justification field", "Justification field has fill ", null);
				
					
				
				}
				
				if((driver.findElements(By.xpath("//md-checkbox[@aria-label='verified id']")).size())>0)
				{
					
					driver.findElement(By.xpath("//md-checkbox[@aria-label='verified id']")).click();;
					queryObjects.logStatus(driver, Status.INFO, "Checking the verified id field", "verified id field has click ", null);
					
				}
				else
					queryObjects.logStatus(driver, Status.FAIL, "Checking the verified id field", "verified id is not displaying ", null);
					
				if (IsExpectedCondition && IsEnabled) {
					queryObjects.logStatus(driver, Status.PASS, "Navigated to the Security Document Verification Page", "Expected Page dispalyed ", null);

				 Boolean Inhabiterror=driver.findElements(By.xpath("//span[contains(text(),'PASSENGER INHIBITED - Please Verify ID')]")).size()>0;
					if (Inhabiterror)
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking the inhabited warning message", " inhabited warning message dispalyed", null);
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL,"Checking the inhabited warning message", " inhabited warning message  must be dispalyed", null);
					}
					EnterDocCheck(driver,queryObjects);

				}
				else if(IsExpectedCondition) {
					queryObjects.logStatus(driver, Status.PASS, "Security Document Verification Page with ADC , APIS completed", "Expected Page dispalyed ", null);
					//boolean IsEnabledEmerPh = driver.findElement(By.xpath(EmerPhXpath)).isEnabled();
					if (IsEnabledCOR) {
						driver.findElement(By.xpath(CORXpath)).clear();
						driver.findElement(By.xpath(CORXpath)).sendKeys(FlightSearch.getTrimTdata(queryObjects.getTestData("COR"))); 
						Thread.sleep(1000);
						driver.findElement(By.xpath(CORXpath)).sendKeys(Keys.TAB);
					}
					boolean IsEnabledEmername = driver.findElement(By.xpath(EmernameXpath)).isEnabled();
					if(IsEnabledEmername)
					{
						driver.findElement(By.xpath(EmernameXpath)).clear(); 
						driver.findElement(By.xpath(EmernameXpath)).sendKeys("Jhon"); 				 

						driver.findElement(By.xpath(EmerPhXpath)).clear();
						driver.findElement(By.xpath(EmerPhXpath)).sendKeys("2324248656"); 
					}

					HandleSecDoc(driver,queryObjects);
					
				
				}
				else if(!IsExpectedCondition && !IsEnabled )   
				{
					queryObjects.logStatus(driver, Status.PASS, "Security Document Verification Page with ADC , APIS completed ", "Security Document Verification Page with ADC , APIS Skipped for Domestic flight ", null);
					try{
						driver.findElement(By.xpath("//button[text()='Check In']")).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
						FlightSearch.loadhandling(driver);

						if(driver.findElements(By.xpath("//i[@class='icon-checked-in active-state']")).size()>0)
						{
							queryObjects.logStatus(driver, Status.PASS, "Checking the Checkin without ADC & APIC completion", "Checking is successfull ", null); 
						}
						else
						{
							queryObjects.logStatus(driver, Status.FAIL, "Checking the Checkin without ADC & APIC completion", "Checking is not successfull ", null);  
						}
					}
					catch(Exception e)
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking the Checkin without ADC & APIC completion", "Checking is not successfull ", e);
					}

				}
				else {

					queryObjects.logStatus(driver, Status.FAIL, "UnExpected Page dispalyed", "UnExpected Page dispalyed "+sFlight , null); 
				}
			}

			
			
			//Click on Done and finish check in
			if( sSubmit.equalsIgnoreCase("Y")) {				

				   if(driver.findElements(By.xpath(SubmitXpath)).size()>0)
						driver.findElement(By.xpath(SubmitXpath)).click();

				

				boolean isenabled=false;
				try {
					FlightSearch.loadhandling(driver);
					isenabled = driver.findElement(By.xpath(AdsResponsePopup)).isEnabled();
					//driver.findElement(By.xpath(SecondaryDoc)).isDisplayed();
				} catch (Exception e) {
					// TODO: handle exception
				}
				if(isenabled)
				{
					queryObjects.logStatus(driver, Status.PASS, "Checking Secondry document need to submit:", "Secondary document is mandatory", null);
					driver.findElement(By.xpath(AdsResponsePopup)).click();
					//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DoneXpath)));
					//FlightSearch.loadhandling(driver);
					//driver.findElement(By.xpath(DoneXpath)).click();
				}

				HandleSecDoc(driver,queryObjects);



				try {			 	
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DoneXpath)));
					driver.findElement(By.xpath(DoneXpath)).click();
					FlightSearch.loadhandling(driver);
				}catch(Exception e) {}
				
				if((driver.findElements(By.xpath("//i[contains(@class,'icon-warning')]//following-sibling::span")).size())>0)
				{
					queryObjects.logStatus(driver, Status.INFO, "Checking the error message for inhabited pax", "Checking the error message for inhabited pax  display", null);
					
					String Ihbitederr=driver.findElement(By.xpath("//i[contains(@class,'icon-warning')]//following-sibling::span")).getText().trim();
					if(Ihbitederr.equalsIgnoreCase("UNABLE TO ASSIGN SEATS"))
						queryObjects.logStatus(driver,Status.INFO,"error messgae display","UNABLE TO ASSIGN SEATS message has display",null);
					else
						queryObjects.logStatus(driver,Status.INFO,"error messgae display","UNABLE TO ASSIGN SEATS message should display",null);	
					
				}
				else
					queryObjects.logStatus(driver, Status.FAIL, "Checking the error message for inhabited pax", "Checking the error message for inhabited pax not display", null);
				
				boolean ErrDisplayed = false;
				try {
					ErrDisplayed =  driver.findElement(By.xpath(ErrXpath)).isDisplayed();

				   

			
					driver.findElement(By.xpath(FinalCheckInXpath)).click();
					FlightSearch.loadhandling(driver);	 
				}catch(Exception e){}

				//validation of check in success
				boolean confirmok = false;
				try {
					confirmok = driver.findElement(By.xpath(ConfirmOKXpath)).isDisplayed();

				}catch (Exception e) {}

				if (!confirmok) {
         queryObjects.logStatus(driver, Status.PASS, "Checkin butoon search", "Checkin button is  not disable as expected " , null); 
         queryObjects.logStatus(driver, Status.PASS, "Checkin butoon search", "Checkin button is  not disable as expected and oterhstep we need to manaual " , null);
				}
         else
         {
        	 queryObjects.logStatus(driver, Status.PASS, "Checkin butoon search", "Checkin butoon is enable" , null); 
         }
		
			}}
		catch(Exception e) {

			queryObjects.logStatus(driver, Status.FAIL, "Search Passenger", "Search Passenger failed: " + e.getLocalizedMessage() , e);
		}
	}

			
	public static void EnterDocCheck(WebDriver driver,BFrameworkQueryObjects queryObjects) {
		try {


			String MulGName[] = null;
			String MulSName[] = null;			
			String SptAge[] = null;
			String SptDocType[] = null;
			String SptDocNum[] = null;
			String SptNationality[] = null;
			String SptCOI[] = null;
			String SptExpdays[] = null;
			String SptCOR[] = null;

			String sFName = FlightSearch.getTrimTdata(queryObjects.getTestData("FirstName"));
			String sSNameS = FlightSearch.getTrimTdata(queryObjects.getTestData("SurName"));				
			String sAge = FlightSearch.getTrimTdata(queryObjects.getTestData("Age"));
			String sDocType = FlightSearch.getTrimTdata(queryObjects.getTestData("DocumentType"));
			String sDocNum = FlightSearch.getTrimTdata(queryObjects.getTestData("DocumentNum"));
			String sNationality = FlightSearch.getTrimTdata(queryObjects.getTestData("Nationality"));
			String sCOI = FlightSearch.getTrimTdata(queryObjects.getTestData("COI"));
			String sExpdays = FlightSearch.getTrimTdata(queryObjects.getTestData("ExpiryDays"));
			String sCOR = FlightSearch.getTrimTdata(queryObjects.getTestData("COR"));
			String sSubmit =  FlightSearch.getTrimTdata(queryObjects.getTestData("SubmitEnable"));


			String sMulPax = FlightSearch.getTrimTdata(queryObjects.getTestData("MulPax"));
			String sCollectAPIS = FlightSearch.getTrimTdata(queryObjects.getTestData("CollectAPIS"));
			sCheckinMsg = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_CheckinMsg"));
			String SecDocSubmit = FlightSearch.getTrimTdata(queryObjects.getTestData("SecDocSubmit"));


			if (sFName.contains(";")) {
				MulGName = sFName.split(";");
				MulSName = sSNameS.split(";");			
			}
			if ((sAge.contains(";")) && (sDocType.contains(";")) && (sDocNum.contains(";")) && (sNationality.contains(";"))  && (sCOI.contains(";")) && (sExpdays.contains(";")) && (sCOR.contains(";"))) {
				SptAge = sAge.split(";");
				SptDocType = sDocType.split(";");
				SptDocNum = sDocNum.split(";");
				SptNationality = sNationality.split(";");
				SptCOI = sCOI.split(";");
				SptExpdays = sExpdays.split(";");
				SptCOR = sCOR.split(";");
			}

			if ((sFName=="") && (sFName !="SamePAX"))
				sFName="DefaultName";

			if (sSNameS=="")
				sSNameS="DefaultSurName";

			if (sAge=="")
				sAge="30";
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("FilterFlightList")).equalsIgnoreCase("Compensate") || sCheckinMsg.equalsIgnoreCase("ADCBYPASS_MENUS"))

			{
				driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
				if (sCheckinMsg.equalsIgnoreCase("ADCBYPASS_MENUS")) {
					try {
						driver.findElement(By.xpath("(//md-select[@ng-model='menuCtrl.menuModel']//span[@class='md-select-icon'])[2]")).click();
						if (driver.findElements(By.xpath("//div[contains(@class,'md-active md-clickable')]//div[contains(@ng-repeat,'menuLabels')]")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Click ADC BYPASS button ", "ADC BYPASS reasons menu, Menus are displayed. ", null);
							driver.findElement(By.xpath("(//div[contains(@class,'md-active md-clickable')]//div[contains(@ng-repeat,'menuLabels')])[1]")).click();
							Thread.sleep(100);
						}							
					} catch (Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Click ADC BYPASS button ", "ADC BYPASS reasons menu, Menus are not displayed. ", null);
					}
				}
				driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
			}



			if (sMulPax.equalsIgnoreCase("All")) {
				List<WebElement> Passengers = driver.findElements(By.xpath("//*[contains(@ng-repeat,'orderObj.Passengers')]/div/div/span"));

				for (int i = 0; i < Passengers.size(); i++) {
					FlightSearch.loadhandling(driver);
					PaxName = Passengers.get(i).getText();
					String[] Arr = PaxName.split("/");
					String	sLName= Arr[0];
					String	sFname = Arr[1];


					//Have to check - Jenny
					if (sFName.equalsIgnoreCase("SamePAX")) { 
						int Paxitem=0;
						Paxitem = i+1;	

						driver.findElement(By.xpath("(//span[contains(text(),'" +PaxName+ "')])["+Paxitem+"]")).click();				
					} else {

						driver.findElement(By.xpath("//span[contains(text(),'" +PaxName+ "')]")).click();
					}
					//driver.findElement(By.xpath("//span[contains(text(),'" +PaxName+ "')]")).click();


					if ( SptAge!=null && SptDocType!=null && (SptDocNum!=null)  && (SptExpdays!=null) && (SptCOI!=null) && (SptNationality!=null)) {	
						EnterDocDetails(driver,queryObjects,sFname,sLName,SptAge[i],SptDocType[i],SptDocNum[i],SptExpdays[i],SptCOI[i],SptNationality[i],SptCOR[i]);
						
					}/*else if (Passengers.size()==1) {//added by Jenny for mulpax
						EnterDocDetails(driver,queryObjects,sFname,sLName,sAge,sDocType,sDocNum,sExpdays,sCOI,sNationality,sCOR);
					}*/
					else {
						EnterDocDetails(driver,queryObjects,sFname,sLName,"25","Passport","1234567","30","US","US","US");
						
						
					}

					//click submit button code
				}

			}
			else if (sMulPax!="") {
				List<WebElement> Passengers = driver.findElements(By.xpath("//*[contains(@ng-repeat,'orderObj.Passengers')]/div/div/span"));

				for (int i = 0; i < Passengers.size(); i++) {

					String PaxName = Passengers.get(i).getText();

					driver.findElement(By.xpath("//span[contains(text(),'" +PaxName+ "')]")).click();


					EnterDocDetails(driver,queryObjects,MulGName[i],MulSName[i],SptAge[i],SptDocType[i],SptDocNum[i],SptExpdays[i],SptCOI[i],SptNationality[i],SptCOR[i]);						
				}
			}else {
				EnterDocDetails(driver,queryObjects,sFName,sSNameS,sAge,sDocType,sDocNum,sExpdays,sCOI,sNationality,sCOR);					
			}
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("SecDocVerify")).equalsIgnoreCase("Y")) {
				driver.findElement(By.xpath("//div[@translate='pssgui.secondary.document']/..")).click();
				EnterSecDoc(driver,queryObjects);
				boolean btnenabled = driver.findElement(By.xpath(SubmitXpath)).isEnabled();					 
				if ((!btnenabled) && SecDocSubmit.equalsIgnoreCase("N")) {
					queryObjects.logStatus(driver, Status.PASS, "Submit button status ", "Submit button was not enabled, Secondary Document was verIfied invalid ", null);	
				}
			}
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("SecDocVerify")).equalsIgnoreCase("VISASCREEN")) {
				if(driver.findElement(By.xpath(AdsResponsePopup)).isDisplayed()|| driver.findElement(By.xpath("//span[contains(text(),'Enter Visa Details for')]")).isDisplayed()) {
					queryObjects.logStatus(driver, Status.PASS, "Secondary document verify - Visa screen ", "'Enter Visa Details' screen is displayed ", null);
					driver.findElement(By.xpath(AdsResponsePopup)).click();
				}else {
					queryObjects.logStatus(driver, Status.FAIL, "Secondary document verify - Visa screen ", "'Enter Visa Details' screen is not displayed ", null);
				}
			}
			//Select Submit Button
			if (sCollectAPIS.equalsIgnoreCase("Y")) {
				if (driver.findElement(By.xpath(SubmitXpath)).isEnabled()) {
					driver.findElement(By.xpath(SubmitXpath)).click();
					//handle
					Thread.sleep(1000);
					FlightSearch.loadhandling(driver);
					if(driver.findElements(By.xpath("//div[@class='ng-binding msg-error' and contains(text(),'PLEASE PROVIDE EXIT DATE TIME')]")).size()>0){
						driver.findElement(By.xpath("//button[text()='OK']")).click();
						FlightSearch.loadhandling(driver);
						boolean nextbt=false;
						do{
							if(driver.findElements(By.xpath(ExitdateXpath)).size()>0){
								Calendar calend = Calendar.getInstance();
								String sExitDate="";
								calend.add(Calendar.DATE, +10);
								sExitDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
								driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExitDate);
								Thread.sleep(1000);
								driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);	
							}
							nextbt=driver.findElements(By.xpath(NextXpath)).size()>0;
							if (nextbt)
								driver.findElement(By.xpath(NextXpath)).click();
						}while(nextbt);
						driver.findElement(By.xpath(SubmitXpath)).click();
						Thread.sleep(1000);
						FlightSearch.loadhandling(driver);
					}
					try {

						if(driver.findElement(By.xpath(AdsResponsePopup)).isDisplayed())
						{
							queryObjects.logStatus(driver, Status.PASS, "Checking Secondry document need to submit:", "Secondary document is mandatory", null);
							driver.findElement(By.xpath(AdsResponsePopup)).click();

							if(driver.findElement(By.xpath(SecondaryDoc)).isDisplayed())
							{
								EnterSecDoc(driver,queryObjects);
								driver.findElement(By.xpath(SubmitXpath)).click();
							}
							else if(driver.findElement(By.xpath(DestinationAddress)).isDisplayed())
							{
								EnterDestiDoc(driver,queryObjects);
								driver.findElement(By.xpath(SubmitXpath)).click();
							}

						}


					} catch (Exception e) {
						// TODO: handle exception
					}

					FlightSearch.loadhandling(driver);
					if (driver.findElement(By.xpath(APISComplete)).isDisplayed()) {

						if (sCheckinMsg !="" && !(sCheckinMsg.equalsIgnoreCase("ADCBYPASS_MENUS"))) {
							if (driver.findElement(By.xpath("//span[contains(text(),'"+sCheckinMsg+"')]")).isDisplayed()) {
								queryObjects.logStatus(driver, Status.PASS, "Message Validation after APIS collect:", sCheckinMsg+" is displayed", null);
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Message Validation after APIS collect", sCheckinMsg+" is not displayed", null);
							}	
						} 
						queryObjects.logStatus(driver, Status.PASS, "Select Submit button, ADC status OK:", "APIS data collected", null);							
					}else {
						queryObjects.logStatus(driver, Status.FAIL, "Select Submit button, ADC status not OK:", "APIS data not collected ", null);
					}
				}					
			}


		}catch(Exception e) {
			//driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);//60
		}
	}


	public static  void EnterDocDetails(WebDriver driver,BFrameworkQueryObjects queryObjects,String fName,String sName,String PaxAge,String DType,String DNum, String Expdays,String COI,String Nty,String COR) throws IOException {
		// TODO Auto-generated method stub
		try {
			String sSubmit =  FlightSearch.getTrimTdata(queryObjects.getTestData("SubmitEnable"));
			String sGender = FlightSearch.getTrimTdata(queryObjects.getTestData("Gender"));
			
			Calendar cal = Calendar.getInstance();
			String sDate = "";
			if (PaxAge.contains("/")) {//Added by Jenny)
				sDate = PaxAge;					
			} else {
				cal.add(Calendar.YEAR, -Integer.parseInt(PaxAge));
				sDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());


			}

			//Select the particular Passenger in case of multiple passenger
			//


			//Enter the Passenger details

			// driver.findElement(By.xpath(SurnameXpath1)).sendKeys(fName);
			driver.findElement(By.xpath(SurnameXpath1)).sendKeys(sName);

			driver.findElement(By.xpath(FirstnameXpath)).sendKeys(fName);



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
			driver.findElement(By.xpath("//md-option[div[normalize-space(text())='"+DType+"']]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(DocIDXpath)).sendKeys(DNum);
			Thread.sleep(1000);

			Calendar calen = Calendar.getInstance();
			String sExpDate="";
			if (Expdays.contains("/")) {//Added by Jenny)
				sExpDate = Expdays;						
			} else {
				calen.add(Calendar.MONTH, Integer.parseInt(Expdays));
				sExpDate = new SimpleDateFormat("MM/dd/yyyy").format(calen.getTime());
			}

			FlightSearch.loadhandling(driver);
			for (int i = 0; i < sExpDate.length(); i++){

				char c = sExpDate.charAt(i);
				String s = new StringBuilder().append(c).toString();
				driver.findElement(By.xpath(ExpdateXpath)).sendKeys(s);

			}



			//driver.findElement(By.xpath(ExpdateXpath)).sendKeys(sExpDate);




			driver.findElement(By.xpath(COIXpath)).sendKeys(COI);
			Thread.sleep(2000);
			driver.findElement(By.xpath(COIXpath)).sendKeys(Keys.TAB);
			Thread.sleep(2000);
			driver.findElement(By.xpath(NtionalXpath)).sendKeys(Nty);
			Thread.sleep(1000);
			driver.findElement(By.xpath(NtionalXpath)).sendKeys(Keys.TAB);
			Thread.sleep(2000);
			driver.findElement(By.xpath(CORXpath)).sendKeys(COR);
			Thread.sleep(1000);
			driver.findElement(By.xpath(CORXpath)).sendKeys(Keys.TAB);
			Thread.sleep(3000);
			if(driver.findElements(By.xpath(ExitdateXpath)).size()>0){
				Calendar calend = Calendar.getInstance();
				String sExitDate="";
				calend.add(Calendar.DATE, +10);
				sExitDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
				driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExitDate);
				Thread.sleep(1000);
				driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);
			}

			driver.findElement(By.xpath(EmernameXpath)).clear();
			driver.findElement(By.xpath(EmernameXpath)).sendKeys("emergencydefault");


			driver.findElement(By.xpath(EmerPhXpath)).clear();
			driver.findElement(By.xpath(EmerPhXpath)).sendKeys("12345634");

			driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'ApplyToAllPassengers')]/div[@class='md-container md-ink-ripple']")).click();

			Thread.sleep(1000);
			String sKTNum = FlightSearch.getTrimTdata(queryObjects.getTestData("KTNum"));    
			String sRedress =  FlightSearch.getTrimTdata(queryObjects.getTestData("Redress"));
			
		   {
			driver.findElement(By.xpath(KTXpath)).sendKeys(sKTNum);
		   }
			driver.findElement(By.xpath(Redress)).sendKeys(sRedress);
			Thread.sleep(500);
			Boolean isenabled= false;
			Boolean isenabled1= false;
			try {
				isenabled = driver.findElement(By.xpath(SubmitXpath)).isEnabled();

			}catch(Exception e) {}
			try{
				isenabled1 = driver.findElement(By.xpath(NextXpath)).isEnabled();
			}catch(Exception e) {}
			if (isenabled && sSubmit.equalsIgnoreCase("Y")) {
				queryObjects.logStatus(driver, Status.PASS, "Submit button was enabled, Document was verfied:"+DType, "Submit button was enabled ", null);
				driver.findElement(By.xpath(SubmitXpath)).click();
				Thread.sleep(1000);
				FlightSearch.loadhandling(driver);
				if(driver.findElements(By.xpath("//div[@class='ng-binding msg-error' and contains(text(),'PLEASE PROVIDE EXIT DATE TIME')]")).size()>0){
					driver.findElement(By.xpath("//button[text()='OK']")).click();
					FlightSearch.loadhandling(driver);
					boolean nextbt=false;
					do{
						if(driver.findElements(By.xpath(ExitdateXpath)).size()>0){
							Calendar calend = Calendar.getInstance();
							String sExitDate="";
							calend.add(Calendar.DATE, +10);
							sExitDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
							driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExitDate);
							Thread.sleep(1000);
							driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);	
						}
						nextbt=driver.findElements(By.xpath(NextXpath)).size()>0;
						if (nextbt)
							driver.findElement(By.xpath(NextXpath)).click();
					}while(nextbt);
					driver.findElement(By.xpath(SubmitXpath)).click();
					Thread.sleep(1000);
					FlightSearch.loadhandling(driver);
					try {
						//suman added for multiple pax doc check
						//driver.findElement(By.xpath(AdsResponsePopup)).click();
						
						 boolean isenabledformulpax =false;					
							try {
									Thread.sleep(2000);
									isenabled1 = driver.findElement(By.xpath(AdsResponsePopup)).isEnabled();
									if(isenabled1){
									   queryObjects.logStatus(driver, Status.PASS, "ADS/APIS verified", "ADS/APIS verified", null);
									     driver.findElement(By.xpath(AdsResponsePopup)).click();
									 } 
									FlightSearch.loadhandling(driver);
									if (driver.findElement(By.xpath("//span[contains(text(),'APIS Incomplete')]")).isDisplayed()) {
										driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
									}
					}catch(Exception e) {}
				}
                catch(Exception e){}
				}
			}
			else if (sSubmit.equalsIgnoreCase("N")) {
				String sErr = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_ErrMessage"));
				if(sErr!="") {
					driver.findElement(By.xpath(SubmitXpath)).click();
					Thread.sleep(1000);
					FlightSearch.loadhandling(driver);
					if(driver.findElements(By.xpath("//div[@class='ng-binding msg-error' and contains(text(),'PLEASE PROVIDE EXIT DATE TIME')]")).size()>0){
						driver.findElement(By.xpath("//button[text()='OK']")).click();
						FlightSearch.loadhandling(driver);
						boolean nextbt=false;
						do{
							if(driver.findElements(By.xpath(ExitdateXpath)).size()>0){
								Calendar calend = Calendar.getInstance();
								String sExitDate="";
								calend.add(Calendar.DATE, +10);
								sExitDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
								driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExitDate);
								Thread.sleep(1000);
								driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);	
							}
							nextbt=driver.findElements(By.xpath(NextXpath)).size()>0;
							if (nextbt)
								driver.findElement(By.xpath(NextXpath)).click();
						}while(nextbt);
						driver.findElement(By.xpath(SubmitXpath)).click();
						Thread.sleep(1000);
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//button[text()='OK']")).click();
						FlightSearch.loadhandling(driver);
					}

					String sErrdisplayed = driver.findElement(By.xpath("//span[contains(@ng-class,'msg-error')]")).getText();

					if (sErrdisplayed.contains(sErr)) {
						queryObjects.logStatus(driver, Status.PASS,"Displayed error"+sErr, "Displayed Error", null);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL,"Displayed error "+sErr, "Displayed Error", null);	

					}

				}

				else if((!isenabled)){
					queryObjects.logStatus(driver, Status.PASS, "Submit button was not enabled, Document was verfied invalid:"+DType, "Submit button was not enabled ", null);
				}

			}
			else if(isenabled1 && sSubmit.equalsIgnoreCase("Y")){

				driver.findElement(By.xpath(NextXpath)).click();
				FlightSearch.loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Submit button was enabled, Document was verfied:"+DType, "Submit button was enabled ", null);
			}

			else		
			{
				queryObjects.logStatus(driver, Status.FAIL, "Submit button was not enabled, Document was not verfied:"+DType, "Submit button was not enabled ", null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			BFrameworkQueryObjects.logStatus(driver, Status.FAIL, "Exception caught:", "unable to complete APIS ", e);
		}
	}

	public static void EnterSecDoc(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException{


		try{   

			String secFName = FlightSearch.getTrimTdata(queryObjects.getTestData("SecfirstName"));
			String secSName = FlightSearch.getTrimTdata(queryObjects.getTestData("SecSurname"));
			String secDocNum = FlightSearch.getTrimTdata(queryObjects.getTestData("document_number"));
			String Sec_Date=FlightSearch.getTrimTdata(queryObjects.getTestData("Sec_Date"));
			String SecDocType = FlightSearch.getTrimTdata(queryObjects.getTestData("SecDocType"));
			String SecDocCOI = FlightSearch.getTrimTdata(queryObjects.getTestData("Sec_COI"));

			String sdate = "";
			Calendar calen = Calendar.getInstance();
			if (Sec_Date.contains("/")) {//Added by Jenny)
				sdate = Sec_Date;					
			} else {
				calen.add(Calendar.DATE, Integer.parseInt(Sec_Date));
				sdate = new SimpleDateFormat("MM/dd/yyyy").format(calen.getTime());
			}
			boolean Con = driver.findElement(By.xpath(SecSurname)).isEnabled();						
			driver.findElement(By.xpath(SecSurname)).sendKeys(secSName);
			driver.findElement(By.xpath(SecFirstName)).sendKeys(secFName);
			if (SecDocType!="") {
				driver.findElement(By.xpath("//md-select[@ng-model='document.DocType']//span[@class='md-select-icon']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//md-option/div[contains(text(),'"+SecDocType+"')]/..")).click();
				Thread.sleep(2000);
			}
			driver.findElement(By.xpath(SecDocNum)).sendKeys(secDocNum);
			driver.findElement(By.xpath(SecDatePicker)).sendKeys(sdate);


			Thread.sleep(200);
			if (!SecDocCOI.isEmpty()) 
				driver.findElement(By.xpath("//input[@name='country']")).sendKeys(SecDocCOI);
			else 
				driver.findElement(By.xpath("//input[@name='country']")).sendKeys("US");

			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//input[@name='country']")).sendKeys(Keys.TAB);
			Thread.sleep(200);

		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.FAIL, "Gettin an exception while perfoeming a operation on Secondary documet", ""+e.getMessage(),e); 
		}
	}
	public static void EnterDestiDoc(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException{


		try {
			String destiStreet = FlightSearch.getTrimTdata(queryObjects.getTestData("Street_Address"));
			boolean Con = driver.findElement(By.xpath(StreetAddress)).isEnabled();

			driver.findElement(By.xpath(StreetAddress)).sendKeys(destiStreet);
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.PASS, "Gettin an exception while performing a operation on Destination Address", ""+e.getMessage(),e); 
		}




	}
	public static void HandleSecDoc(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception {
		boolean isSecDoc=false;
		boolean isDestDoc=false;
		String  IssecFName="";
		String IsdestiStreet="";
		boolean isSecDocSubmit=false;

		try{
			IssecFName = FlightSearch.getTrimTdata(queryObjects.getTestData("SecfirstName"));
			isSecDoc= driver.findElement(By.xpath(SecondaryDoc)).isDisplayed();

		}catch(Exception e) {
			// TODO: handle exception
		}
		try{
			IsdestiStreet = FlightSearch.getTrimTdata(queryObjects.getTestData("Street_Address"));
			isDestDoc= driver.findElement(By.xpath(DestinationAddress)).isDisplayed();

		}catch(Exception e) {
			// TODO: handle exception
		}





















		if(isSecDoc && (IssecFName!=""))
		{
			EnterSecDoc(driver,queryObjects);
			isSecDocSubmit = driver.findElement(By.xpath(SubmitXpath)).isEnabled();
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("SecDocSubmit")).equalsIgnoreCase("N") && !isSecDocSubmit) {
				queryObjects.logStatus(driver, Status.PASS, "Secondary document verification for expired date", "Submit button is disabled for the expired secondary document", null);
			} else {
				driver.findElement(By.xpath(SubmitXpath)).click();
				FlightSearch.loadhandling(driver);
				if(driver.findElements(By.xpath("//div[@class='ng-binding msg-error' and contains(text(),'PLEASE PROVIDE EXIT DATE TIME')]")).size()>0){
					driver.findElement(By.xpath("//button[text()='OK']")).click();
					FlightSearch.loadhandling(driver);
					boolean nextbt=false;
					do{
						if(driver.findElements(By.xpath(ExitdateXpath)).size()>0){
							Calendar calend = Calendar.getInstance();
							String sExitDate="";
							calend.add(Calendar.DATE, +10);
							sExitDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
							driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExitDate);
							Thread.sleep(1000);
							driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);	
						}
						nextbt=driver.findElements(By.xpath(NextXpath)).size()>0;
						if (nextbt)
							driver.findElement(By.xpath(NextXpath)).click();
					}while(nextbt);

					driver.findElement(By.xpath(SubmitXpath)).click();
					Thread.sleep(1000);
					FlightSearch.loadhandling(driver);
					boolean isenabled1 =false;					
					try {
						Thread.sleep(2000);
						isenabled1 = driver.findElement(By.xpath(AdsResponsePopup)).isEnabled();
						if(isenabled1){
							queryObjects.logStatus(driver, Status.PASS, "ADS/APIS verified", "ADS/APIS verified", null);
							driver.findElement(By.xpath(AdsResponsePopup)).click();
						} 
						FlightSearch.loadhandling(driver);
						if (driver.findElement(By.xpath("//span[contains(text(),'APIS Incomplete')]")).isDisplayed()) {
							driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
							driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath(SubmitXpath)).click();
							Thread.sleep(1000);

						}
					}catch(Exception e) {}

				}
			}
			FlightSearch.loadhandling(driver);
			boolean isenabled1 =false;					
			try {
				Thread.sleep(2000);
				isenabled1 = driver.findElement(By.xpath(AdsResponsePopup)).isEnabled();
			}catch(Exception e) {}
			if(isenabled1){
				queryObjects.logStatus(driver, Status.PASS, "ADS/APIS verified", null, null);
				driver.findElement(By.xpath(AdsResponsePopup)).click();
			}
		}


		try{
			IsdestiStreet = FlightSearch.getTrimTdata(queryObjects.getTestData("Street_Address"));
			isDestDoc= driver.findElement(By.xpath(DestinationAddress)).isDisplayed();

		}catch(Exception e) {
			// TODO: handle exception
		}

		if(isDestDoc && (IsdestiStreet!="") )
		{
			EnterDestiDoc(driver,queryObjects);
			driver.findElement(By.xpath(SubmitXpath)).click();
			FlightSearch.loadhandling(driver);
		}
		else if(IsdestiStreet!="") {
			queryObjects.logStatus(driver, Status.INFO, "Expected Destination doc Page was not dispalyed","Expected Destination doc Page was not dispalyed", null);
		}
		//add pop up handle - click ok button
		boolean isenabled =false;					
		try {
			Thread.sleep(2000);
			isenabled = driver.findElement(By.xpath(AdsResponsePopup)).isEnabled();
		}catch(Exception e) {}
		if(isenabled){
			queryObjects.logStatus(driver, Status.PASS, "ADS/APIS verified", "ADS/APIS verified", null);
			driver.findElement(By.xpath(AdsResponsePopup)).click();
		}
		boolean FLAG=false;//Updated by Jenny
		try {
			FLAG = driver.findElement(By.xpath("//span[contains(text(),'APIS Incomplete')]")).isDisplayed();
		} catch (Exception e) {}

		// if(IssecFName.isEmpty() && FLAG) {
		if( FLAG) {	 

			driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
			driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
			Thread.sleep(1000);
			try{
				driver.findElement(By.xpath(SubmitXpath)).click();
			}
			catch(Exception e) {
				// TODO: handle exception
				queryObjects.logStatus(driver, Status.INFO,"Submit button nor required / submit done","submit",null);
			}
			Thread.sleep(1000); 
		}
	}

	public void SelectDocCheckFromList(WebDriver driver, BFrameworkQueryObjects queryObjects, String ValueCheck){
		List<WebElement> Checkbox_Cnt = driver.findElements(By.xpath(PaxDataChkBox));
		int PAXCnt = Checkbox_Cnt.size();
		for (int ItmSel = 1; ItmSel <= PAXCnt; ItmSel++) {
			if (((driver.findElement(By.xpath("(//div[@class='passenger-list layout-row'])["+ItmSel+"]")).getText().trim())).contains(ValueCheck)) {
				driver.findElement(By.xpath("(//span[contains(text(),'Doc Check')])["+ItmSel+"]")).click();
				FlightSearch.loadhandling(driver);
				break;
			}
		}
	}
	
		public String pnrNum = null;
		
		public static void searchPNR(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {

			
				try{
					driver.findElement(By.xpath("//div[contains(@class,'tab') and div[contains(text(),'Passengers')]]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//div[contains(text(),'Travel document')]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//button[contains(@ng-click,'itineraryTraveler.editItem')]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//md-select[contains(@ng-model,'DocumentType')]")).click();
					//driver.findElement(By.xpath("//md-input-container[label[contains(text(),'Document Name')]]//md-select-value[contains(@class,'md-select-value')]")).click();
					FlightSearch.loadhandling(driver);

					driver.findElement(By.xpath("//md-option[@value='PP']/div[contains(text(),'Passport')]")).click();
					FlightSearch.loadhandling(driver);   
					driver.findElement(By.xpath("//input[contains(@ng-model,'CountryOrStateCode')]")).sendKeys("US");
					Thread.sleep(1000);
					driver.findElement(By.xpath("//input[contains(@ng-model,'DocID')]")).sendKeys("985639213");
					Thread.sleep(1000);
					driver.findElement(By.xpath("//div[div[toggle-title[div[contains(text(),'Form of Identification')]]]]/i")).click();
					Thread.sleep(1000);
					if(driver.findElements(By.xpath("//input[@aria-label='Country of Issuance']")).size()>0)
					{
						
						
						driver.findElement(By.xpath("//input[@aria-label='Country of Issuance']")).sendKeys("US");
						Thread.sleep(1000);
						driver.findElement(By.xpath("//input[@aria-label='Country of Issuance']")).sendKeys(Keys.TAB);
						Thread.sleep(1000);
					
					}
					driver.findElement(By.xpath("//button[text()='OK' and not (@disabled='disabled')]")).click();
					FlightSearch.loadhandling(driver);
					queryObjects.logStatus(driver, Status.PASS, "Secureflight info", "Secureflight info  updated " ,null);

				}catch(Exception e) {

					queryObjects.logStatus(driver, Status.FAIL, "Secureflight info", "Secureflight info not updated ", e);
				}
				
				
			
				
			}

	}	



	
	
	

	
	
	
	
