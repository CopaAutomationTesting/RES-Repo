package com.copa.ATOscripts;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.RESscripts.FlightSearch;
import com.copa.RESscripts.Login;
import com.copa.Util.CompensationPageObjects;

import FrameworkCode.BFrameworkQueryObjects;

@SuppressWarnings("static-access")
public class Compensation extends CompensationPageObjects {
	public static String PnrNum ="";
	public static String sOrderNum;
	public static String sAgent="";
	public static String sMonetaryAmt="";
	public static String sOverrideRsn="";
	public static String sCompensationRsn="";
	public static String EMDNum="";
	public static String PaxTktNum="";
	public static String PaxFlightNum="";
	public static String PaxDays="";
	public static String PaxFromLoc="";
	public static String PaxToLoc="";
	public static String AddDetailsTab="";
	public static String AddDetType="";
	public static String AddDetValue="";
	public static String SalesReport="";
	public static String Orders="";
	static boolean Selection;
	public static String PnrForEmailValidation="";
	public static String Verification_Detail="";
	public static String sFrom="";
	public static String sTo="";
	public static String ErrText ="";
	public static String sSelectAll = "";
	public static String  Hotel_EMD = "";
	public static String  Meal_EMD = "";
	public static String  Transport_EMD = "";
	public static String sNoOfPaxToComm = "";
	public static String IssuedPNR = "";

	//Search Data in Compensation page 
	
	public void Compensate(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception{

		try {
			//Get test data

			String sFrom = FlightSearch.getTrimTdata(queryObjects.getTestData("From"));				 
			String sFlightNo = FlightSearch.getTrimTdata(queryObjects.getTestData("FlightNumber"));
			String sDays = FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));				 
			String sSelectPaxList = FlightSearch.getTrimTdata(queryObjects.getTestData("SelectPaxList"));
			sOrderNum = FlightSearch.getTrimTdata(queryObjects.getTestData("OrderNumber"));
			String sTicketNo = FlightSearch.getTrimTdata(queryObjects.getTestData("TicketNumber"));
			String sFFProgram = FlightSearch.getTrimTdata(queryObjects.getTestData("FFProgram"));
			String sFFnum = FlightSearch.getTrimTdata(queryObjects.getTestData("FFNumber"));
			String sSearch = FlightSearch.getTrimTdata(queryObjects.getTestData("Search"));
			String sTo = FlightSearch.getTrimTdata(queryObjects.getTestData("To"));			 

			sOverrideRsn = FlightSearch.getTrimTdata(queryObjects.getTestData("OverrideReason"));
			Hotel_EMD = FlightSearch.getTrimTdata(queryObjects.getTestData("Hotel_EMD"));
			Meal_EMD = FlightSearch.getTrimTdata(queryObjects.getTestData("Meal_EMD"));
			Transport_EMD = FlightSearch.getTrimTdata(queryObjects.getTestData("Transportation_EMD"));
			SalesReport = FlightSearch.getTrimTdata(queryObjects.getTestData("SalesReport"));
			PaxTktNum = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxTktNum"));
			PaxFlightNum = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxFlightNum"));
			PaxDays = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxDays"));
			PaxFromLoc = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxFromLoc"));
			PaxToLoc = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxToLoc"));
			String SelOrder = FlightSearch.getTrimTdata(queryObjects.getTestData("SelectOrder"));
			String Misconnect = FlightSearch.getTrimTdata(queryObjects.getTestData("Misconnect"));
			sAgent = FlightSearch.getTrimTdata(queryObjects.getTestData("Agent"));
			String OrderIdFromRes = FlightSearch.getTrimTdata(queryObjects.getTestData("OrderIdFromRes"));
			sCompensationRsn = FlightSearch.getTrimTdata(queryObjects.getTestData("CompensationReason"));
			Verification_Detail = FlightSearch.getTrimTdata(queryObjects.getTestData("Verification_Detail"));
			sCompensationRsn = FlightSearch.getTrimTdata(queryObjects.getTestData("CompensationReason"));
			String User = "";
			sSelectAll = FlightSearch.getTrimTdata(queryObjects.getTestData("SelectAll"));
			sNoOfPaxToComm = FlightSearch.getTrimTdata(queryObjects.getTestData("NoOfPaxToComm"));
			ErrText =  FlightSearch.getTrimTdata(queryObjects.getTestData("ErrText"));
			String PaxListSel = "";
			
			if (Verification_Detail.contains("ModuleDisplay")) {
				if(driver.findElement(By.xpath("//figure//div[contains(text(),'Travel Compensation')]")).isEnabled()) {
					User = driver.findElement(By.xpath("//md-menu-bar[contains(@ng-if,'profileInfo')]//button")).getText();
					queryObjects.logStatus(driver, Status.PASS, "Check the Travel Compensation module is enabled in Hub landing page for the ", "Compenesation module is active for "+User,null); }
				else { queryObjects.logStatus(driver, Status.FAIL, "Checking agent is active for Compenesation", "User has no access to Travel Compenesation module",null); }
				return;
			}
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("VerifyCompensationAgentActive")).equalsIgnoreCase("Yes"))  ///Suman Kumar
			{
				if(driver.findElement(By.xpath("//figure//div[contains(text(),'Travel Compensation')]")).isEnabled())
				{
					queryObjects.logStatus(driver, Status.PASS, "Checking agent is active for Compenesation", "Compenesation module is active",null);	
				}
				else
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking agent is active for Compenesation", "Compenesation module is active",null);	
				}

				return;
			}
			Thread.sleep(1000);
			driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
			Thread.sleep(1000);
			driver.findElement(By.xpath(CompensationXpath)).click();//Select Travel Compensation Menu
			loadhandling(driver);
			
			if (sSelectPaxList.contains("Final")) {
				PaxListSel = "";
			} else {
				PaxListSel = sSelectPaxList;
			}
			if (sSearch.equalsIgnoreCase("y")) {
				SearchOrders(driver, queryObjects, sFlightNo, sDays, sFrom, sFFProgram, sFFnum, PaxListSel, sOrderNum, sTicketNo, sSearch);
			}
			
			//Search by FF
			if (sFFnum!="") {
				SearchOrder_FF(driver,queryObjects);
			}
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("Display")).equalsIgnoreCase("Yes"))   //suman
			{
				display(driver,queryObjects);
				return;
			}
			//Add New Passenger Details 
			AddNewPaxDetails(driver,queryObjects);
			
			if (sSearch.equalsIgnoreCase("y")) {
				//Select order from Order List Page - Single Order
				SelectOrder_OrderList(driver, queryObjects, sOrderNum);
			}
			

			//Select an order form the list
			if (SelOrder.equalsIgnoreCase("Y")) {

				boolean isorder = false;
				try {
					isorder =  driver.findElement(By.xpath("//md-radio-button[@ng-value='segments.FlightNo']/div")).isDisplayed();
				}catch (Exception e) {}

				if (isorder) {
					driver.findElement(By.xpath("//md-radio-button[@ng-value='segments.FlightNo']/div")).click();
					loadhandling(driver);
					driver.findElement(By.xpath("//button[contains(text(),'Continue')]")).click();
					loadhandling(driver); 
				}else {
					SelectOrder(driver, queryObjects); 
				}
			}



			if(FlightSearch.getTrimTdata(queryObjects.getTestData("NoCompensation")).equalsIgnoreCase("yes"))  /////suman
			{
				try
				{
					// driver.findElement(By.xpath("//div/span[contains(text(),'Edit')]")).click();


					UpdateCompensationWithoutReason(driver,queryObjects, sSelectAll, sNoOfPaxToComm);
					String CompensationAmt=driver.findElement(By.xpath("//div[contains(@class,'pssgui-bold pos-con')]//div[7]")).getText();

					queryObjects.logStatus(driver, Status.INFO, "Checking the compenessationamount ", "Commpenesation amount is ->"+CompensationAmt,null);
					driver.findElement(By.xpath("//div/span[contains(text(),'Edit')]")).click();

					driver.findElement(By.xpath("//div[@translate='cm.other.details']")).click();

					driver.findElement(By.xpath("//input[contains(@ng-model,'model.tempax.careNumbe')]")).click();
					driver.findElement(By.xpath("//input[contains(@ng-model,'model.tempax.careNumbe')]")).sendKeys("123456");

					driver.findElement(By.xpath("//button[@translate='cm.ok']")).click();

					queryObjects.logStatus(driver, Status.INFO, "Checking the Customer case number ", "Customer 6 digit cases number updated ->"+"123456",null); 

					driver.findElement(By.xpath("//div/span[contains(text(),'Edit')]")).click();

					driver.findElement(By.xpath("//div[@translate='cm.other.details']")).click();

					String Casenumber= driver.findElement(By.xpath("//input[contains(@ng-model,'model.tempax.careNumbe')]")).getText();

					driver.findElement(By.xpath("//button[@translate='cm.ok']")).click();
					if(Casenumber.equalsIgnoreCase("123456"))
						queryObjects.logStatus(driver, Status.PASS, "Checking the Customer case number ", "Customer 6 digit cases number updated CORRECTLY  ->"+"123456",null); 
					else
						queryObjects.logStatus(driver, Status.PASS, "Checking the Customer case number ", "Customer 6 digit cases number NOT updated CORRECTLY->"+"123456",null);  
				}catch(Exception e)
				{
					queryObjects.logStatus(driver, Status.FAIL, "Getting the exception while update customer case number  ", "",e);  
				}

				return;

			}
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("ChangeEmail")).equalsIgnoreCase("yes"))
			{
				UpdateCompensationReason(driver,queryObjects, sSelectAll);
				Compensation.ChangeEmail(driver, queryObjects);

				return;
			}
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("SaveOnly")).equalsIgnoreCase("yes"))
			{

				///it's will save the pnr with commpenesation reason
				SaveCommpensationPaxAndNotIsse(driver,queryObjects);

				loadhandling(driver);
				return;
			}
			if (!sCompensationRsn.isEmpty()) {
				if (Verification_Detail.contains("IssuedCompVerify")) {
					if (driver.findElement(By.xpath(RowItem)).getText().trim().contains(sOrderNum) && driver.findElement(By.xpath("//div[contains(@class,'passenger-list')]")).getAttribute("class").contains("row-highlight")) {
						queryObjects.logStatus(driver, Status.PASS, "Issue compensation with agent and verify using Supervisor", "Compensation status row is highlighted since it is issued",null);
						return;
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Issue compensation with agent and verify using Supervisor", "Compensation status row is not highlighted",null);
					}					
				} else {
					//Issue Compensation
					UpdateCompensationReason(driver,queryObjects, sSelectAll);
					if (driver.findElements(By.xpath("//span[contains(text(),'This Compensation Reason already exists for this Passenger')]")).size()>0) {
						if (SelOrder.equalsIgnoreCase("Y")) {
							IssuedPNR = sOrderNum+";"+IssuedPNR;
							sOrderNum = "";
							SelectOrder(driver, queryObjects);
							//Issue Compensation
							UpdateCompensationReason(driver,queryObjects, sSelectAll);
						}
					}
					//Compensation Issued Passenger Details
					IssueCompensation_Verify(driver,queryObjects, sSelectAll, sNoOfPaxToComm);
				}
				

			}
			else                                  /////suman
			{
				if (!(Verification_Detail.equalsIgnoreCase("Passenger data added")) && !(Verification_Detail.equalsIgnoreCase("Negative FF")) && !(Verification_Detail.equalsIgnoreCase("EMD Reports"))) {
					UpdateCompensationWithoutReason(driver,queryObjects, sSelectAll, sNoOfPaxToComm);
					IssueCompensation_VerifyForMulPax(driver, queryObjects);
				}
			}							 
			//Close Station Report
			CloseStationReport(driver, queryObjects, sMonetaryAmt);				 

			//Print EMD
			PrintEMD(driver,queryObjects);

			//Close Agent Report
			CloseAgentSalesReport(driver, queryObjects, sMonetaryAmt);

			//Verify EMD Status in Reservation
			//EMDVerification_Res(driver, queryObjects);  ---Check this

			//Verify Report
			VerifyReport(driver, queryObjects);
			
			//Verify the Print List Items
			if (sSelectPaxList.contains("Final")) {
				driver.findElement(By.xpath("//div[contains(text(),'Home')]")).click();
				loadhandling(driver);
				SearchOrders(driver, queryObjects, PaxFlightNum, PaxDays, PaxFromLoc, "", "", sSelectPaxList.replace("Final", ""), "", "", sSearch);
			}
						
			if (sSelectPaxList!="" && sSelectPaxList.contains("Final")) {
				VerifyUpdatedCompensationReport(driver, queryObjects);
			}

		} catch (Exception e) {
			// TODO: handle exception
			queryObjects.logStatus(driver, Status.FAIL, "Compensation case", "Compensation failed", e);
		}
	}

	public void ValidateReport(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception{
		String ReportType = FlightSearch.getTrimTdata(queryObjects.getTestData("ReportType"));
		String DateFrom = FlightSearch.getTrimTdata(queryObjects.getTestData("DateFrom"));
		String DateTo = FlightSearch.getTrimTdata(queryObjects.getTestData("DateTo"));
		String ValidateReports = FlightSearch.getTrimTdata(queryObjects.getTestData("ValidateReports"));
		String Download = FlightSearch.getTrimTdata(queryObjects.getTestData("Download"));
		String FlightNum = FlightSearch.getTrimTdata(queryObjects.getTestData("FlightNum"));
		String CloseSalesreport = FlightSearch.getTrimTdata(queryObjects.getTestData("CloseSalesreport"));
		String DisplaySalesReport = FlightSearch.getTrimTdata(queryObjects.getTestData("DisplaySalesReport"));
		String CashReport = FlightSearch.getTrimTdata(queryObjects.getTestData("CashReport"));


		int sDate;
		String sReason;
		boolean found;
		String Reason= FlightSearch.getTrimTdata(queryObjects.getTestData("Reason"));

		try {
			if(DisplaySalesReport.equalsIgnoreCase("Y")){
				if(driver.findElements(By.xpath("//md-menu//button[contains(text(),'Reservation')]")).size()>0){
					driver.findElement(By.xpath("//md-menu//button[contains(text(),'Reservation')]")).click();

					Thread.sleep(1000);
					if(driver.findElements(By.xpath("//button[contains(text(),'Sales Reporting')]")).size()>0){
						driver.findElement(By.xpath("//button[contains(text(),'Sales Reporting')]")).click();
						loadhandling(driver);
						driver.findElement(By.xpath("//button[contains(text(),'Agent Sales Report')]")).click();
						loadhandling(driver);
					}
					else if(driver.findElements(By.xpath("//button[contains(text(),'Agent Sales Report')]")).size()>0){
						driver.findElement(By.xpath("//button[contains(text(),'Agent Sales Report')]")).click();
						loadhandling(driver);
					}
				}
				else if(driver.findElements(By.xpath("//md-menu/button[contains(text(),'Travel Compensation')]")).size()>0){
					driver.findElement(By.xpath("//md-menu/button[contains(text(),'Travel Compensation')]")).click();
					Thread.sleep(1000);
					if(driver.findElements(By.xpath("//button[contains(text(),'Sales Reporting')]")).size()>0){
						driver.findElement(By.xpath("//button[contains(text(),'Sales Reporting')]")).click();
						loadhandling(driver);
						driver.findElement(By.xpath("//button[contains(text(),'Agent Sales Report')]")).click();
						loadhandling(driver);
					}
					else if(driver.findElements(By.xpath("//button[contains(text(),'Agent Sales Report')]")).size()>0){
						driver.findElement(By.xpath("//button[contains(text(),'Agent Sales Report')]")).click();
						loadhandling(driver);
					}
				}
				queryObjects.logStatus(driver, Status.PASS, "Displaying the current scenario Sales Office report", "Salesoffice details",null);
				if(!CashReport.equalsIgnoreCase("Y")||!CloseSalesreport.equalsIgnoreCase("Y")){
					return;
				}
			}
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Not able to find the current scenario Sales Office report", "Salesoffice details not available",null);
			// TODO: handle exception
		}
		try {
			if(CashReport.equalsIgnoreCase("Y")){
				driver.findElement(By.xpath("//md-menu/button[contains(text(),'Agent Sales Report')]")).click();
				loadhandling(driver);
				driver.findElement(By.xpath("//md-menu-item/button[contains(text(),'Cash Accounting Report')]")).click();
				loadhandling(driver);
				try {
					if(driver.findElements(By.xpath("//md-card//span[contains(text(),'OPEN')]")).size()>0){
						driver.findElement(By.xpath("//md-card//span[contains(text(),'OPEN')]")).click();
						queryObjects.logStatus(driver, Status.PASS, "Cash accounting report for the given scenario", "Cash Accounting Report is displayed",null);
					}
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Cash accounting report for the given scenario", "Cash Accounting Report is displayed",null);
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Cash accounting report issue", "Cash Accounting Report not available",null);
			// TODO: handle exception
		}
		try {
			if(CloseSalesreport.equalsIgnoreCase("Y")){
				if(driver.findElements(By.xpath("//div[contains(text(),'Reservations')]")).size()>0){
					driver.findElement(By.xpath("//div[contains(text(),'Reservations')]")).click();
					Thread.sleep(1000);
				}
				else if(driver.findElements(By.xpath("//md-menu/button[contains(text(),'Travel Compensation')]")).size()>0){
					driver.findElement(By.xpath("//md-menu/button[contains(text(),'Travel Compensation')]")).click();
					Thread.sleep(1000);
				}
				driver.findElement(By.xpath("//button[contains(text(),'Sales Report')]")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[contains(text(),'Agent Sales Report')]")).click();
				loadhandling(driver);
				try{
					if (driver.findElements(By.xpath("//div[contains(text(),'Message - Open Reports')]")).size()>0){
						driver.findElement(By.xpath("//div[contains(text(),'Message - Open Reports')]/parent::div//i")).click();
						queryObjects.logStatus(driver, Status.PASS, "Open Report Message closed", "Need to issue ticket to Open Report", null);
						return;
					}
				}catch(Exception e){
					queryObjects.logStatus(driver, Status.FAIL, "No Open Report Message", "All the Reports are Closed", null);
				}
				driver.findElement(By.xpath("//div/span[contains(text(),'Total Transaction Amount')]")).click();
				loadhandling(driver);
				try {
					String cur = driver.findElement(By.xpath("//div[@id='adjTable']//div[@role='button']")).getText().trim();
					String amount = driver.findElement(By.xpath("//div[@id='adjTable']//span/parent::div/div/div")).getText().trim();
					queryObjects.logStatus(driver, Status.PASS, "Currency for given sales report "+cur+"", "Amount is "+amount+"",null);
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Transaction Currency not available for the given sales office", "Check the Salesoffice transaction",null);
					// TODO: handle exception
				}
				driver.findElement(By.xpath("//button[contains(text(),'Close Report')]")).click();
				loadhandling(driver);
				driver.findElement(By.xpath("//md-dialog//button[contains(text(),'Close Report')]")).click();
				loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Salesoffice for the given scenario is closed", "Salesoffice is closed",null);
				return;
			}
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Issue while closing the Sales Office Agent Transaction", "No Report Exist in SalesOffice",null);
			return;
			// TODO: handle exception
		}
		try {
			if(driver.findElements(By.xpath("//md-menu//button[contains(text(),'Reservation')]")).size()>0){
				driver.findElement(By.xpath("//md-menu//button[contains(text(),'Reservation')]")).click();	
				Thread.sleep(500);
				driver.findElement(By.xpath("//button[contains(text(),'Travel Compensation')]")).click();
				loadhandling(driver);
				driver.findElement(By.xpath("//div[@role='button']//div[contains(text(),'Report')]")).click();
				loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Reached Travel Compensation Page Report", "Need to validate the Report",null);
				Thread.sleep(1500);
			}
			else if(driver.findElements(By.xpath("//span[contains(text(),'Search by Flight')]")).size()>0){
				driver.findElement(By.xpath("//div[@role='button']//div[contains(text(),'Report')]")).click();
				loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Reached Travel Compensation Page Report", "Need to validate the Report",null);
			}
			else if(driver.findElements(By.xpath("//md-menu//button[contains(text(),'Travel Compensation')]")).size()>0){
				driver.findElement(By.xpath("//md-menu//button[contains(text(),'Travel Compensation')]")).click();
				Thread.sleep(500);
				try {
					driver.findElement(By.xpath("//md-menu-item//button[contains(text(),'Reservation')]")).click();
					loadhandling(driver);
					driver.findElement(By.xpath("//md-menu//button[contains(text(),'Reservation')]")).click();
					loadhandling(driver);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				driver.findElement(By.xpath("//md-menu-item//button[contains(text(),'Travel Compensation')]")).click();
				loadhandling(driver);
				driver.findElement(By.xpath("//div[@role='button']//div[contains(text(),'Report')]")).click();
				loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Reached Travel Compensation Page Report", "Need to validate the Report",null);
				Thread.sleep(1500);
			}
			else{
				driver.findElement(By.xpath("//div[contains(text(),'Home')]")).click();
				loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Reached Travel Compensation Page Report", "Need to validate the Report",null);
				Thread.sleep(1500);
			}
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Reached Travel Compensation Page Report", "Need to validate the Report" +e.getStackTrace()[0].getLineNumber(),e);
			// TODO: handle exception	
		}
		try {
			if(ValidateReports.equalsIgnoreCase("Y")){
				driver.findElement(By.xpath("//md-select[@aria-label='Search By']")).click();
				Thread.sleep(500);
				if(ReportType.equalsIgnoreCase("EMD")){
					driver.findElement(By.xpath("//div[contains(text(),'EMD Report')]")).click();
					Thread.sleep(1500);
					Calendar cal = Calendar.getInstance();
					sDate=Integer.parseInt(DateFrom);
					if(DateFrom!=""){
						cal.add(Calendar.DATE,sDate);
					}else {
						cal.add(Calendar.DATE, -30);
					}
					String sDateFrom = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
					driver.findElement(By.xpath("//pssgui-date-time[@date-time-label='cm.from']//input")).sendKeys(sDateFrom);
					Calendar cal1 = Calendar.getInstance();
					sDate=Integer.parseInt(DateTo);
					if(DateTo!=""){
						cal1.add(Calendar.DATE,sDate);
					}else {
						cal1.add(Calendar.DATE, -1);
					}
					String sDateTo = new SimpleDateFormat("MM/dd/yyyy").format(cal1.getTime());
					driver.findElement(By.xpath("//pssgui-date-time[@date-time-label='cm.to']//input")).sendKeys(sDateTo);
					Thread.sleep(2000);
					driver.findElement(By.xpath("//button[contains(text(),'View')]")).click();
					loadhandling(driver);
					driver.findElement(By.xpath("//div[@role='button']/span[contains(text(),'Compensation Reason')]")).click();
					Thread.sleep(1500);
					if(Reason.equalsIgnoreCase("Cancellation")){
						found=false;
						do{
							List<WebElement> sReasons = new ArrayList<WebElement>();
							sReasons=driver.findElements(By.xpath("//div[@id='adjTable']//div[6]"));
							List<String> sReasonsText=new ArrayList<String>();
							sReasons.forEach(a->sReasonsText.add(a.getText().trim()));
							sReason=driver.findElement(By.xpath("//div[@id='adjTable']//div[6]")).getText();
							if(sReasonsText.contains("Cancellation"))


								found=false;
							else
								found=true;

						}while(found);
						if(!found){
							queryObjects.logStatus(driver, Status.PASS, "Validate Report Passed", "Compensation Reason with Cancellation is found and available",null);
						}
						else{
							queryObjects.logStatus(driver, Status.FAIL, "Validate Report Not available", "Compensation Reason with Cancellation is not found ",null);
						}
					}
					if(Reason.equalsIgnoreCase("Oversold")){
						found=false;
						do{
							List<WebElement> sReasons = new ArrayList<WebElement>();
							sReasons=driver.findElements(By.xpath("//div[@id='adjTable']//div[6]"));
							List<String> sReasonsText=new ArrayList<String>();
							sReasons.forEach(a->sReasonsText.add(a.getText().trim()));
							sReason=driver.findElement(By.xpath("//div[@id='adjTable']//div[6]")).getText();
							if(sReasonsText.contains("Oversold up to 4hrs"))


								found=false;
							else
								found=true;
						}while(found);
						if(!found){
							queryObjects.logStatus(driver, Status.PASS, "Validate Report Passed", "Compensation Reason with Oversold is found and available",null);
						}
						else{
							queryObjects.logStatus(driver, Status.FAIL, "Validate Report Not available", "Compensation Reason with Oversold is not found ",null);
						}
					}
					if(Reason.equalsIgnoreCase("Delay between 2 to 3:59hrs")){
						found=false;
						do{
							if(driver.findElements(By.xpath("//div[@id='adjTable']//div[contains(text(),'"+sOrderNum+"')]")).size()>0){
								sReason=driver.findElement(By.xpath("//div[@id='adjTable']//div[contains(text(),'"+sOrderNum+"')]/parent::div/div[6]")).getText();
								if(sReason.equalsIgnoreCase("Delay between 2 to 3:59hrs")){
									queryObjects.logStatus(driver, Status.PASS, "Validate Report Passed", "Compensation Reason with Delay between 2 to 3:59hrs is found and available",null);
								}
								else
									queryObjects.logStatus(driver, Status.FAIL, "Validate Report Not available", "Compensation Reason with Delay between 2 to 3:59hrs is not found ",null);
							}
							else if(sOrderNum.isEmpty()){
								queryObjects.logStatus(driver, Status.PASS, "Validate Report Passed", "Compensation Reason with Delay between 2 to 3:59hrs is found and available",null);
							}
							else
								queryObjects.logStatus(driver, Status.FAIL, "Validate Report PNR Not available", "Compensation Reason with Delay between 2 to 3:59hrs is not found ",null);
						}while(found);

					}
				}
			}
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Validate Report Failed", "xpath in validate report" +e.getStackTrace()[0].getLineNumber(),e);
			// TODO: handle exception
		}
		try {
			if(Download.equalsIgnoreCase("Y")){
				sOrderNum=Login.PNRNUM.trim();
				PaxFlightNum= FlightSearch.getTrimTdata(queryObjects.getTestData("FlightNum"));
				sFrom = FlightSearch.getTrimTdata(queryObjects.getTestData("From"));
				sTo = FlightSearch.getTrimTdata(queryObjects.getTestData("To"));
				driver.findElement(By.xpath("//md-select[@aria-label='Search By']")).click();
				Thread.sleep(500);
				if(ReportType.equalsIgnoreCase("Flight")){
					queryObjects.logStatus(driver, Status.PASS, "Flight Report Download", "Download the Report",null);
					driver.findElement(By.xpath("//div[contains(text(),'Flight Report')]")).click();
					Thread.sleep(1500);
					driver.findElement(By.xpath("//input[@name='Flight']")).sendKeys(PaxFlightNum);
					Thread.sleep(2000);
					Calendar cal = Calendar.getInstance();
					sDate=Integer.parseInt(DateFrom);
					if(DateFrom!=""){
						cal.add(Calendar.DATE,sDate);
					}else {
						cal.add(Calendar.DATE, -3);
					}
					String sDateFrom = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
					driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(sDateFrom);
					Thread.sleep(1500);
					driver.findElement(By.xpath("//input[@aria-label='From']")).sendKeys(sFrom);
					loadhandling(driver);
					Thread.sleep(2500);
					driver.findElement(By.xpath("//input[@aria-label='From']")).sendKeys(Keys.ENTER);
					Thread.sleep(1500);
					driver.findElement(By.xpath("//input[@aria-label='To']")).sendKeys(sTo);
					loadhandling(driver);
					Thread.sleep(2500);
					driver.findElement(By.xpath("//input[@aria-label='To']")).sendKeys(Keys.ENTER);
					Thread.sleep(1500);
					driver.findElement(By.xpath("//button[contains(text(),'View')]")).click();
					loadhandling(driver);
					if(driver.findElements(By.xpath("//button[contains(text(),'Download')]")).size()>0){
						driver.findElement(By.xpath("//button[contains(text(),'Download')]")).click();
						loadhandling(driver);
						queryObjects.logStatus(driver, Status.PASS, "Downloaded the specified flight compensation report", "Downloaded the Report",null);
					}
					else
						queryObjects.logStatus(driver, Status.PASS, "Specified flight do not have the compensation Report", "Report not available",null);
				}
				if(ReportType.equalsIgnoreCase("Passenger")){
					queryObjects.logStatus(driver, Status.PASS, "Passenger Report Download", "Download the Report",null);
					driver.findElement(By.xpath("//div[contains(text(),'Passenger Report')]")).click();
					Thread.sleep(1500);
					driver.findElement(By.xpath("//input[@name='Order']")).sendKeys(sOrderNum);
					if(sOrderNum.equalsIgnoreCase("")){
						queryObjects.logStatus(driver, Status.PASS, "Order ID not available to download the report", "need OrderID",null);
					}
					Thread.sleep(1500);
					driver.findElement(By.xpath("//button[contains(text(),'View')]")).click();
					loadhandling(driver);
					if(driver.findElements(By.xpath("//button[contains(text(),'Download')]")).size()>0){
						driver.findElement(By.xpath("//button[contains(text(),'Download')]")).click();
						loadhandling(driver);
						queryObjects.logStatus(driver, Status.PASS, "Downloaded the specified ORDER ID compensation report", "Downloaded the Report",null);
					}
					else
						queryObjects.logStatus(driver, Status.PASS, "Specified ORDER ID do not have the compensation Report", "Report not available",null);
				}
				if(ReportType.equalsIgnoreCase("EMD")){
					queryObjects.logStatus(driver, Status.PASS, "EMD Report Download", "Download the Report",null);
					driver.findElement(By.xpath("//div[contains(text(),'EMD Report')]")).click();
					Thread.sleep(1500);
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE,-10);
					String sDateFrom = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
					driver.findElement(By.xpath("//pssgui-date-time[@date-time-label='cm.from']//input")).sendKeys(sDateFrom);
					Calendar cal1 = Calendar.getInstance();
					cal1.add(Calendar.DATE, 0);
					String sDateTo = new SimpleDateFormat("MM/dd/yyyy").format(cal1.getTime());
					driver.findElement(By.xpath("//pssgui-date-time[@date-time-label='cm.to']//input")).sendKeys(sDateTo);
					Thread.sleep(1500);
					driver.findElement(By.xpath("//button[contains(text(),'View')]")).click();
					loadhandling(driver);
					if(driver.findElements(By.xpath("//button[contains(text(),'Download')]")).size()>0){
						driver.findElement(By.xpath("//button[contains(text(),'Download')]")).click();
						loadhandling(driver);
						queryObjects.logStatus(driver, Status.PASS, "Downloaded the EMD compensation report for given date", "Downloaded the Report",null);
					}
					else
						queryObjects.logStatus(driver, Status.PASS, "Specified date do not have the compensation Report", "Report not available",null);
				}
				if(ReportType.equalsIgnoreCase("Oversold")){
					queryObjects.logStatus(driver, Status.PASS, "Oversold Report Download", "Download the Report",null);
					driver.findElement(By.xpath("//div[contains(text(),'Oversold Report')]")).click();
					Thread.sleep(1500);
					driver.findElement(By.xpath("//input[@name='Flight']")).sendKeys(PaxFlightNum);
					Calendar cal = Calendar.getInstance();
					sDate=Integer.parseInt(DateFrom);
					if(DateFrom!=""){
						cal.add(Calendar.DATE,sDate);
					}else {
						cal.add(Calendar.DATE, -3);
					}
					String sDateFrom = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
					driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(sDateFrom);
					Thread.sleep(1500);
					driver.findElement(By.xpath("//input[@aria-label='From']")).sendKeys(sFrom);
					loadhandling(driver);
					Thread.sleep(2500);
					driver.findElement(By.xpath("//input[@aria-label='From']")).sendKeys(Keys.ENTER);
					Thread.sleep(1500);
					driver.findElement(By.xpath("//input[@aria-label='To']")).sendKeys(sTo);
					loadhandling(driver);
					Thread.sleep(2500);
					driver.findElement(By.xpath("//input[@aria-label='From']")).sendKeys(Keys.ENTER);
					Thread.sleep(1500);
					driver.findElement(By.xpath("//button[contains(text(),'View')]")).click();
					loadhandling(driver);
					if(driver.findElements(By.xpath("//button[contains(text(),'Download')]")).size()>0){
						driver.findElement(By.xpath("//button[contains(text(),'Download')]")).click();
						loadhandling(driver);
						queryObjects.logStatus(driver, Status.PASS, "Downloaded the specified flight compensation report", "Downloaded the Report",null);
					}
					else
						queryObjects.logStatus(driver, Status.PASS, "Specified flight do not have the compensation Report", "Report not available",null);

				}
			}
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Download Report failed", "Report to download is not available",null);
			// TODO: handle exception
		}
	}

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//		

	public static void loadhandling(WebDriver driver){
		try {
			Thread.sleep(1000);
			driver.findElement(By.xpath(LoadingXpath));
			WebDriverWait wait = new WebDriverWait(driver, 300);
			wait = new WebDriverWait(driver, 300);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LoadingXpath)));
			Thread.sleep(1000);
		}
		catch(Exception e) {
		}		
	}
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	public static void AddNewPaxDetails(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException {
		try {
			String sAddPaxData = FlightSearch.getTrimTdata(queryObjects.getTestData("AddPassengerData"));
			String GvnName = FlightSearch.getTrimTdata(queryObjects.getTestData("GivenName"));
			String Surname = FlightSearch.getTrimTdata(queryObjects.getTestData("Surname"));
			String PaxClass = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxCabin"));
			String ErrMsg = "";
			if (sAddPaxData.equalsIgnoreCase("Y")) {
				ErrMsg = driver.findElement(By.xpath("//span[@class='ng-binding msg-error']")).getText().trim();
				if (driver.findElement(By.xpath("//span[contains(text(),'"+ErrText+"')]")).isDisplayed()){
					queryObjects.logStatus(driver, Status.PASS, "Compensation - Search tab - Search with invalid data", "Error is displayed, Message is "+ErrMsg,null);
					boolean AddPaxData = driver.findElement(By.xpath(AddPaxDataXpath)).isEnabled();
					if (sAddPaxData!="" && AddPaxData) {
						driver.findElement(By.xpath(AddPaxDataXpath)).click();
						EnterOrderDetails(driver, queryObjects);
						driver.findElement(By.xpath(ContinueBtn)).click();
						loadhandling(driver);
						if (Verification_Detail.equalsIgnoreCase("Passenger data added")) {
							if (driver.findElement(By.xpath(RowItem)).getText().trim().contains(sOrderNum) && driver.findElement(By.xpath(RowItem)).getText().trim().contains(PaxClass) && driver.findElement(By.xpath(RowItem)).getText().trim().contains((Surname+" / "+GvnName).toUpperCase())) {
								queryObjects.logStatus(driver, Status.PASS, "Add PAX data ", "Added passenger data displayed correctly",null);					
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Add PAX data ", "Added passenger data is not correct",null);
							}
						}
					} 
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Compensation - Search tab - Search with invalid data", "Error message is not displayed, Actual Error is "+ErrMsg,null);
				}
			}
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Add PAX data ", "Add passenger data failed",e);
		}


	}
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//		

	public static void SelectOrder_OrderList(WebDriver driver, BFrameworkQueryObjects queryObjects, String SelOpt) throws IOException {
		boolean SelOrder = false; String ListSel = "";
		try {
			if (driver.findElement(By.xpath(OrderListView+"[1]")).isDisplayed()) {
				ListSel = OrderListView;
			}
		} catch (Exception e) {
			if (driver.findElement(By.xpath(PAXListView+"[1]")).isDisplayed()) {
				ListSel = PAXListView;
			}
		}
		try {
			if (driver.findElement(By.xpath(ListSel+"[1]")).isDisplayed()) {
				List<WebElement> OrderCnt = driver.findElements(By.xpath(ListSel));
				for (int j = 1; j <= OrderCnt.size(); j++) {
					if (driver.findElement(By.xpath(ListSel+"["+j+"]")).getText().trim().contains(SelOpt)) {
						if (PaxFromLoc!="" && PaxToLoc!=""){
							if (driver.findElement(By.xpath(ListSel+"["+j+"]")).getText().trim().replace("\n", "").contains(PaxFromLoc+PaxToLoc)) {
								driver.findElement(By.xpath("(//md-radio-button[@class='label-comp-checkbox'])["+j+"]")).click();
								SelOrder = true;
							}							
						} else {
							driver.findElement(By.xpath("(//md-radio-button[@class='label-comp-checkbox'])["+j+"]")).click();
							SelOrder = true;
						}
						if (SelOrder) {
							driver.findElement(By.xpath(ContinueBtn)).click();
							loadhandling(driver);
							if (driver.findElement(By.xpath(OrderDetailsPage)).isDisplayed()) {
								queryObjects.logStatus(driver, Status.PASS, "Select order from list display and click continue", "Navigated to Order list page",null);
								break;
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Select order from list display and click continue", "Order list page is not displayed",null);
							}
						}
						
					}						 
				}							
			}
		} catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Select order from list display ", "Order selection failed",e);
		}		
	}

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	public static void EnterOrderDetails(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException{
		boolean ButtonEnable =false;
		try {
			String GName = FlightSearch.getTrimTdata(queryObjects.getTestData("GivenName"));
			String Sname = FlightSearch.getTrimTdata(queryObjects.getTestData("Surname"));
			String PaxFFNum = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxFFNum"));
			String PaxFFPgm = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxFFPgm"));
			String PaxTierLevel = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxTierLevel"));
			String PaxClass = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxCabin"));
			String OrdNo = FlightSearch.getTrimTdata(queryObjects.getTestData("AddPaxOrdNo"));
			if (sOrderNum.isEmpty()) {
				sOrderNum = OrdNo;
			}
			driver.findElement(By.xpath(GivenName)).sendKeys(GName);
			driver.findElement(By.xpath(Surname)).sendKeys(Sname);
			driver.findElement(By.xpath(OrderIdXpath)).sendKeys(sOrderNum);
			if (PaxFFPgm!="") {
				driver.findElement(By.xpath(FFPgmListIcon)).click();
				driver.findElement(By.xpath("//md-option/div[contains(text(),'"+PaxFFPgm+"')]")).click();					
			}
			if (PaxFFNum!="") {
				driver.findElement(By.xpath(FFNumberXpath)).sendKeys(PaxFFNum);
			}
			if (PaxTierLevel!="") {
				driver.findElement(By.xpath(PaxTier)).sendKeys(PaxTierLevel);
			}
			driver.findElement(By.xpath(PaxCabin)).sendKeys(PaxClass);

			driver.findElement(By.xpath(TicketNoXpath)).sendKeys(PaxTktNum);
			driver.findElement(By.xpath(FlightNoXpath)).sendKeys(PaxFlightNum);
			driver.findElement(By.xpath(FlightNoXpath)).sendKeys(Keys.TAB);
			Calendar cal = Calendar.getInstance();
			if (PaxDays!="") {
				cal.add(Calendar.DATE, Integer.parseInt(PaxDays));
			}else {
				cal.add(Calendar.DATE, 0);
			}
			String sDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());

			driver.findElement(By.xpath(DateXpath)).sendKeys(sDate);

			driver.findElement(By.xpath(FromLocXpath)).sendKeys(PaxFromLoc);
			loadhandling(driver);
			driver.findElement(By.xpath(FromLocXpath)).sendKeys(Keys.TAB);
			driver.findElement(By.xpath(ToLocXpath)).sendKeys(PaxToLoc);
			driver.findElement(By.xpath(ToLocXpath)).sendKeys(Keys.TAB);
			Thread.sleep(200);
			ButtonEnable = driver.findElement(By.xpath(ContinueBtn)).isEnabled();
			if (ButtonEnable) {
				queryObjects.logStatus(driver, Status.PASS, "Continue Button status ", "Continue button is enabled after entering Passenger details",null);					
			} else {
				queryObjects.logStatus(driver, Status.FAIL, "Continue Button status ", "Continue button is not enabled after entering Passenger details",null);
			}

		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Add new passenger for compensation ", "Passenger details are not entered",e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	public static void SelectOrder(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException {
		try {
			String FilterItm = FlightSearch.getTrimTdata(queryObjects.getTestData("FilterItem"));
			String SplitVal[];
			String FilterVal = "";
			String SelectVal = "";
			if (driver.findElement(By.xpath(OrderDetailsPage)).isDisplayed()) {
				queryObjects.logStatus(driver, Status.PASS, "Passenger model display", "Issue Compensation page is displayed.",null);
				List<WebElement> PaxCnt = driver.findElements(By.xpath(PaxCheckbox));
				if (FilterItm!="") {
					if (FilterItm.contains("Class")) {
						driver.findElement(By.xpath("//i[contains(@ng-class,'class')]")).click();
						SplitVal = FilterItm.split("-");
						FilterVal=SplitVal[1];
					}else if (FilterItm.contains("SSR")) {
						SplitVal = FilterItm.split("-");
						FilterVal=SplitVal[1];
					}

				} /*else {
						FilterVal="1";
					}*/
				if (FilterItm.isEmpty()) {
					sOrderNum=driver.findElement(By.xpath("(//div[contains(@class,'input ng-binding flex')])[1]")).getText().trim();
				}
				else {
					for (int i=1; i<= PaxCnt.size(); i++) {
						//if (FilterItm.contains("Class")) {
						SelectVal=driver.findElement(By.xpath("(//span[@class='pssgui-bold input ng-binding'])["+i+"]")).getText().trim();
						// }
						if (SelectVal.equals(FilterVal)) {
							if (!IssuedPNR.isEmpty()) {
								if (!IssuedPNR.contains(driver.findElement(By.xpath("(//div[contains(@class,'input ng-binding flex')])["+i+"]")).getText().trim())) {
									sOrderNum=driver.findElement(By.xpath("(//div[contains(@class,'input ng-binding flex')])["+i+"]")).getText().trim();
									break;
								}
							} else {
								sOrderNum=driver.findElement(By.xpath("(//div[contains(@class,'input ng-binding flex')])["+i+"]")).getText().trim();
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Select an passenger order", "Passenger order is not available for the given condition",e);
		}

	}
	////////----------------------------------------------------------------------------------suman----------------------------------------////

	public static void UpdateCompensationWithoutReason(WebDriver driver, BFrameworkQueryObjects queryObjects, String SelPax, String NoofPax) throws IOException {

		try {
			//sCompensationRsn = FlightSearch.getTrimTdata(queryObjects.getTestData("CompensationReason"));
			
			Orders = Atoflow.PnrNum;
			Selection = false;
			driver.findElement(By.xpath("//div[contains(text(),'Compensation Not Issued')]")).click();
			List<WebElement> PaxCnt = driver.findElements(By.xpath(PaxCheckbox));
			if(NoofPax.equalsIgnoreCase(""))
				NoofPax="1";
			int PaxToComm=Integer.parseInt(NoofPax); 

			if(PaxCnt.size() < PaxToComm)
			{
				PaxToComm=PaxCnt.size();
			}
			//kkk

			for (int i=1; i<= PaxToComm; i++) {
				if (Orders!="") {
					if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(Orders)) {
						//if (PaxCnt.size()>0 && sOrderNum.contains(driver.findElement(By.xpath("("+OrderRowXpath+")["+i+"]")).getText().trim())) {
						driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
						driver.findElement(By.xpath(SelectAllChk)).click();
						Selection=true;
					}
				}
				else if(SelPax.equalsIgnoreCase("Y")) {
					driver.findElement(By.xpath(SelectAllChk)).click();
					Selection=true;
					break;
				}
				else if(PaxToComm > 0 && !NoofPax.isEmpty() )
				{
					driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
					Selection=true;
				}
				else {
					if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(sOrderNum)) {
						driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
						Selection=true;
						break;
					}
				}

			}


			if (Selection) {
				/*if (sCompensationRsn.isEmpty()) {
						 sCompensationRsn = Atoflow.sCompensationRsn;						
					 }
					 driver.findElement(By.xpath(CompReason)).click();
					 driver.findElement(By.xpath("//div[contains(text(),'"+sCompensationRsn+"')]")).click();
					 Thread.sleep(100);*/
				List<WebElement> PaxCnt2 = driver.findElements(By.xpath(PaxCheckbox));
				String ALLPAXINFO="";
				for (int i=1; i<= PaxCnt2.size(); i++) 
				{
					try
					{
						String Name=driver.findElement(By.xpath("//div[@id='adjTable']//div[contains(@ng-repeat,'compCompensate.model.displayPaxModel')]["+i+"]//div[contains(@class,'label-name')]")).getText();

						String PNR=driver.findElement(By.xpath("//div[@id='adjTable']//div[contains(@ng-repeat,'compCompensate.model.displayPaxModel')]["+i+"]//div[6]")).getText();

						ALLPAXINFO="--->"+Name+"->PNR IS-> "+PNR;
					}
					catch(Exception e)
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking list of pax from Comm not issue tab", "unbale to get all pax detail",e); 
					}


				}
				queryObjects.logStatus(driver, Status.PASS, "Checking list of pax from Comm not issue tab", ALLPAXINFO, null);
				if (driver.findElement(By.xpath(ContinueBtn)).isEnabled()) {
					driver.findElement(By.xpath(ContinueBtn)).click();
					loadhandling(driver);
					if (driver.findElements(By.xpath("//div[@class='passenger-list pssgui-bold layout-row']")).size()>0){
						queryObjects.logStatus(driver, Status.PASS, "Update compensation reason", "Compensation reason is updated as "+sCompensationRsn,null);

					}

				}
			}				
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Update compensation reason", "Compensation reason is not updated",e);
		}

	}
	////-------------------------------------------------suman---------------------------------------------------------------///////

	public static void IssueCompensation_VerifyForMulPax(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException {
		try {
			Orders = Atoflow.PnrNum;
			String  DefaultAmt = FlightSearch.getTrimTdata(queryObjects.getTestData("ExistingAmt"));

			if(sNoOfPaxToComm.equalsIgnoreCase(""))
				sNoOfPaxToComm="1";
			List<WebElement> PaxCnt = driver.findElements(By.xpath(PaxCheckbox));
			int PaxToComm=Integer.parseInt(sNoOfPaxToComm); 
			if(PaxCnt.size() < PaxToComm)
			{
				PaxToComm=PaxCnt.size();
			}
			//issueList.model.selecAll

			if (driver.findElement(By.xpath("//div[@class='passenger-list pssgui-bold layout-row']")).isDisplayed() || driver.findElement(By.xpath("//span[@translate='cm.total.value']")).isDisplayed() ) {

				//Method to updated Monetary Amount

				if (sSelectAll.equalsIgnoreCase("Y")) {
					driver.findElement(By.xpath("//md-checkbox[@ng-model='issueList.model.selecAll']")).click();
					Selection=true;	
				}
				else if(PaxToComm > 0 && !sNoOfPaxToComm.isEmpty() )
				{
					Boolean CheckVolChildNRPax=true;
					for (int i=1; i<= PaxToComm; i++) {
						Boolean Amt=driver.findElements(By.xpath("(//div[contains(@class,'label-name input  ng-binding ng-scope flex-15')])["+i+"]/child::div/span[not (contains(text(),'VOL-SB'))]")).size()>0;


						//driver.findElement(By.xpath("("+AmountXpath+")["+i+"]")).click();
						if(!Amt)
						{CheckVolChildNRPax=false;
						driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();}

						if(CheckVolChildNRPax)
						{
							queryObjects.logStatus(driver, Status.FAIL, "Checking the passanger type eligigble for compensation", "Passanger is not eligible for comanasation like child, nr, vol sb", null);	
						} 

					}
					Selection=true;

				}
				else {
					UpdateMonetaryAmt(driver, queryObjects);

					if (DefaultAmt.isEmpty()) {
						DefaultAmt=Atoflow.DefaultAmt;
					}else {
						sMonetaryAmt=DefaultAmt;
					}
					//Method to updated Additional Details Type
					UpdateAdditionalDetails(driver, queryObjects);

					// List<WebElement> PaxCnt = driver.findElements(By.xpath(PaxCheckbox));
					for (int i=1; i<= PaxCnt.size(); i++) {

						if (Orders!="") {
							if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(Orders)) {
								//if (PaxCnt.size()>0 && sOrderNum.contains(driver.findElement(By.xpath("("+OrderRowXpath+")["+i+"]")).getText().trim())) {
								driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
								Selection=true;
								break;
							}
						}else {
							if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(sOrderNum)) {
								driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
								break;
							}
						}
					}
				}
				//Check the Issue Tab status
				if (driver.findElements(By.xpath(IssueCompButton)).size()>0) {
					queryObjects.logStatus(driver, Status.PASS, "Compensation issue button status", "Issue compensation button is enabled.",null);
					driver.findElement(By.xpath(IssueCompButton)).click();
					driver.findElement(By.xpath(OkButton)).click();
					loadhandling(driver);
					try {

						if (driver.findElement(By.xpath(CompIssuedTab)).isDisplayed()) {
							driver.findElement(By.xpath(CompIssuedTab)).click();
							List<WebElement> PaxCnt1 = driver.findElements(By.xpath(PaxCheckbox));
							Selection = false;
							for (int i=1; i<= PaxCnt1.size(); i++) {
								if (Orders!="") {
									if (PaxCnt1.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(Orders)) {
										//if (PaxCnt.size()>0 && sOrderNum.contains(driver.findElement(By.xpath("("+OrderRowXpath+")["+i+"]")).getText().trim())) {
										driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
										Selection=true;
									}
								}else {
									if (PaxCnt1.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(sOrderNum)) {
										driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
										Selection=true;
									}
								}
							}
							if (sSelectAll.equalsIgnoreCase("Y")) {
								String Compnot = driver.findElement(By.xpath("//div[contains(text(),'Compensation Not Issued')]")).getText();
								String[] nCompcount = Compnot.split("(");
								String sCompcount = nCompcount[1];
								sCompcount = sCompcount.replaceAll(")","");

								if (Integer.parseInt(sCompcount.trim())>0) {
									driver.findElement(By.xpath("//div[contains(text(),'Compensation Not Issued')]")).click();
									queryObjects.logStatus(driver, Status.PASS, "Some compenstaion were not issued in bulk ", "Verification successful",null);

									///displaying all the passanger and pnr from Compensation not issue.




									driver.findElement(By.xpath("//md-checkbox[@ng-model='issueList.model.selecAll']")).click();
									Selection=true;
									driver.findElement(By.xpath(IssueCompButton)).click();
									driver.findElement(By.xpath(OkButton)).click();
									loadhandling(driver);
									String CompIssued = driver.findElement(By.xpath("//div[contains(text(),'Compensation Not Issued')]")).getText();
									String[] nCompIssuedcount = CompIssued.split("(");
									String sCompIsscount = nCompIssuedcount[1];
									sCompIsscount = sCompIsscount.replaceAll(")","");
									if (Integer.parseInt(sCompIsscount)>100) {
										queryObjects.logStatus(driver, Status.PASS, "All 100 compensation were issued", "Verification successful",null);
									}
									else {
										queryObjects.logStatus(driver, Status.FAIL, "All 100 compensation were not issued", "Verification successful",null);
									}
								}
							}
							if (Selection) {



								if (driver.findElement(By.xpath(CompIssuedTab)).isDisplayed()) 
								{
									queryObjects.logStatus(driver, Status.PASS, "Issue Compensation for the passenger ", "Compensation is  issued",null);


								}

								if( FlightSearch.getTrimTdata(queryObjects.getTestData("SendEMail")).equalsIgnoreCase("yes"))
								{
									try
									{


										if (driver.findElement(By.xpath(IssueCompButton)).isEnabled()) {
											queryObjects.logStatus(driver, Status.PASS, "Compensation issue button status", "Issue compensation button is enabled.",null);
											driver.findElement(By.xpath(IssueCompButton)).click();
											driver.findElement(By.xpath(OkButton)).click();
											loadhandling(driver);
											driver.findElement(By.xpath(EmailLink)).click();
											loadhandling(driver);

										}	 

										if (driver.findElements(By.xpath(EmailLink)).size()>0) {

											WebElement ele=driver.findElement(By.xpath("(//span[@ng-click='issueCompensation.addEmail(reports)'])[1]"));
											ele.click();
											loadhandling(driver);
											driver.findElement(By.xpath("//md-checkbox[@name='copy']")).click();
											driver.findElement(By.xpath(EmailText)).click();
											driver.findElement(By.xpath(EmailText)).clear();
											driver.findElement(By.xpath(EmailText)).sendKeys("Test@automation.com");
											driver.findElement(By.xpath(OkButton)).click();
											loadhandling(driver);





											Thread.sleep(200);
											//driver.findElement(By.xpath(PaxCheckbox)).click();
											// driver.findElement(By.xpath("//md-checkbox[@aria-label='pax-chk']")).click();
											driver.findElement(By.xpath(EmailButton)).click();													 
											loadhandling(driver);
											queryObjects.logStatus(driver, Status.PASS, "Checking email Button to update", "Checking email Button is updated",null); 

											if(driver.findElements(By.xpath("//i[contains(@class,'icon-checked-in icon-small active-state')]")).size()>0)
											{
												queryObjects.logStatus(driver, Status.PASS, "Email has updated for send a mail", "Status green has updated for email",null);

												driver.findElement(By.xpath("//div[contains(text(),'EMD Available for Print')]")).click();	
												loadhandling(driver);
												///

												String sNoOfPaxToCommemail = FlightSearch.getTrimTdata(queryObjects.getTestData("NoOfPaxToComm"));
												Orders = Atoflow.PnrNum;
												Selection = false;
												//driver.findElement(By.xpath("//div[contains(text(),'Compensation Not Issued')]")).click();
												List<WebElement> PaxCntemail = driver.findElements(By.xpath(PaxCheckbox));
												if(sNoOfPaxToComm.equalsIgnoreCase(""))
													sNoOfPaxToComm="1";
												int PaxToCommemail=Integer.parseInt(sNoOfPaxToCommemail); 

												if(PaxCnt.size() < PaxToComm)
												{
													PaxToComm=PaxCnt.size();
												}
												List<WebElement> PaxCntEmail = driver.findElements(By.xpath(PaxCheckbox));
												for (int i=1; i<= PaxCntEmail.size(); i++) {

													if (Orders!="") 
													{
														if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(Orders)) {
															//if (PaxCnt.size()>0 && sOrderNum.contains(driver.findElement(By.xpath("("+OrderRowXpath+")["+i+"]")).getText().trim())) {
															driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
															Selection=true;
															break;
														}

													}
													else if(PaxToComm > 0 && !sNoOfPaxToCommemail.isEmpty() )
													{
														driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
														Selection=true;
													}

													else {
														if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(sOrderNum)) {
															driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
															break;
														}
													}
												}

												driver.findElement(By.xpath(EmailButton)).click();
												loadhandling(driver);

												if(driver.findElements(By.xpath("//span[contains(@ng-class,'msg-error') and contains(text(),'Email sent successfully')]")).size()>0)
												{
													queryObjects.logStatus(driver, Status.PASS, "Checking the email send status", "email sent successfull display",null);
												}
												else
												{
													queryObjects.logStatus(driver, Status.FAIL, "Checking the email send status", "email sent successfull is not displaying display",null);
												}
											}
											else
											{
												queryObjects.logStatus(driver, Status.INFO, "Email has updated for send a mail", "Status green has NOT updated for email",null); 
											}
										}

										else
										{
											queryObjects.logStatus(driver, Status.FAIL, "Checking email Button to update", "Checking email Button is available",null);
										}
										if(driver.findElements(By.xpath("//span[contains(@ng-class,'msg-error') and contains(text(),'Email sent successfully')]")).size()>0)
										{
											queryObjects.logStatus(driver, Status.PASS, "Checking the email send status", "email sent successfull display",null);
										}
										else
										{
											queryObjects.logStatus(driver, Status.FAIL, "Checking the email send status", "email sent successfull is not displaying display",null);
										}
									}
									catch(Exception e)
									{
										queryObjects.logStatus(driver, Status.FAIL, "Getting an exception while sending a mail", "",e);
									}

								}
							}
						}
					}
					catch(Exception e)
					{
						queryObjects.logStatus(driver, Status.FAIL, "Issue Compensation for the passenger ", "Compensation is not issued",e); 
					}
				}
			}
		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.FAIL, "Issue Compensation for the passenger ", "Compensation is not issued",e);
		}
	}

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	public static void UpdateCompensationReason(WebDriver driver, BFrameworkQueryObjects queryObjects, String SelAll) throws IOException {

		try {
			//sCompensationRsn = FlightSearch.getTrimTdata(queryObjects.getTestData("CompensationReason"));
			Orders = Atoflow.PnrNum;
			Selection = false;
			List<WebElement> PaxCnt = driver.findElements(By.xpath(PaxCheckbox));
			for (int i=1; i<= PaxCnt.size(); i++) {
				if (Orders!="") {
					if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(Orders)) {
						//if (PaxCnt.size()>0 && sOrderNum.contains(driver.findElement(By.xpath("("+OrderRowXpath+")["+i+"]")).getText().trim())) {
						driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
						//driver.findElement(By.xpath(SelectAllChk)).click();
						Selection=true;
					}
				}
				else if(SelAll.equalsIgnoreCase("Y")) {
					driver.findElement(By.xpath(SelectAllChk)).click();
					Selection=true;
					break;
				}
				else {
					if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(sOrderNum)) {
						driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
						Selection=true;
						break;
					}
				}

			}
			if (Selection) {
				if (sCompensationRsn.isEmpty()) {
					sCompensationRsn = Atoflow.sCompensationRsn;						
				}
				driver.findElement(By.xpath(CompReason)).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//div[contains(text(),'"+sCompensationRsn+"')]")).click();
				Thread.sleep(1000);
				if (driver.findElement(By.xpath(ContinueBtn)).isEnabled()) {
					driver.findElement(By.xpath(ContinueBtn)).click();
					loadhandling(driver);
					if (driver.findElement(By.xpath("//div[@class='passenger-list pssgui-bold layout-row']")).isDisplayed()){
						queryObjects.logStatus(driver, Status.PASS, "Update compensation reason", "Compensation reason is updated as "+sCompensationRsn,null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Update compensation reason", "Compensation reason is not updated as "+sCompensationRsn,null);
						return;
					}

				}
			}				
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Update compensation reason", "Compensation reason is not updated",e);
		}

	}
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	public static void IssueCompensation_Verify(WebDriver driver, BFrameworkQueryObjects queryObjects, String SelectAll, String NoOfPax) {
		try {
			Orders = Atoflow.PnrNum;
			String  DefaultAmt = FlightSearch.getTrimTdata(queryObjects.getTestData("ExistingAmt"));
			String  IssueComp = FlightSearch.getTrimTdata(queryObjects.getTestData("IssueCompensation"));
			
			//issueList.model.selecAll

			if (driver.findElement(By.xpath("//div[@class='passenger-list pssgui-bold layout-row']")).isDisplayed() && driver.findElement(By.xpath("//span[@translate='cm.total.value']")).isDisplayed() ) {

				//Method to updated Monetary Amount

				/*if (sSelectAll.equalsIgnoreCase("Y")) {
					driver.findElement(By.xpath("//md-checkbox[@ng-model='issueList.model.selecAll']")).click();
					Selection=true;	
					UpdateMonetaryAmt(driver, queryObjects);
				}*/
				//else {
				UpdateMonetaryAmt(driver, queryObjects);

				if (DefaultAmt.isEmpty()) {
					DefaultAmt=Atoflow.DefaultAmt;
				}else {
					sMonetaryAmt=DefaultAmt;
				}
				//Method to update Additional Details Type
				UpdateAdditionalDetails(driver, queryObjects);

				List<WebElement> PaxCnt = driver.findElements(By.xpath(PaxCheckbox));
				for (int i=1; i<= PaxCnt.size(); i++) {

					if (Orders!="") {
						if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(Orders)) {
							//if (PaxCnt.size()>0 && sOrderNum.contains(driver.findElement(By.xpath("("+OrderRowXpath+")["+i+"]")).getText().trim())) {
							driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
							Selection=true;
							break;
						}
					}else {
						if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(sOrderNum)) {
							driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
							break;
						}
					}
				}
				//}
				//Check the Issue Tab status
				if (IssueComp.equalsIgnoreCase("y") && driver.findElement(By.xpath(IssueCompButton)).isEnabled()) {
					//Select all for multiple selection
					if (SelectAll.equalsIgnoreCase("Y")) {
						driver.findElement(By.xpath("//md-checkbox[@ng-model='issueList.model.selecAll']")).click();
						driver.findElement(By.xpath("//md-checkbox[@ng-model='issueList.model.selecAll']")).click();
					}
					queryObjects.logStatus(driver, Status.PASS, "Compensation issue button status", "Issue compensation button is enabled.",null);
					driver.findElement(By.xpath(IssueCompButton)).click();
					driver.findElement(By.xpath(OkButton)).click();
					loadhandling(driver);
					Thread.sleep(1000);
					try {

						if (driver.findElement(By.xpath(CompIssuedTab)).isDisplayed()) {
							driver.findElement(By.xpath(CompIssuedTab)).click();
							//List<WebElement> PaxCnt = driver.findElements(By.xpath(PaxCheckbox));
							Selection = false;
							for (int i=1; i<= PaxCnt.size(); i++) {
								if (Orders!="") {
									if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(Orders)) {
										//if (PaxCnt.size()>0 && sOrderNum.contains(driver.findElement(By.xpath("("+OrderRowXpath+")["+i+"]")).getText().trim())) {
										driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
										Selection=true;
									}
								}else {
									if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(sOrderNum)) {
										driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
										Selection=true;
									}
								}
							}
							if (SelectAll.equalsIgnoreCase("Y")) {
								String Compnot = driver.findElement(By.xpath("//div[contains(text(),'Compensation Not Issued')]")).getText();
								String[] nCompcount = Compnot.split("\\(");
								String sCompcount = nCompcount[1];
								sCompcount = sCompcount.replaceAll("\\)","");

								if (Integer.parseInt(sCompcount.trim())>0) {
									driver.findElement(By.xpath("//div[contains(text(),'Compensation Not Issued')]")).click();
									queryObjects.logStatus(driver, Status.PASS, "Some compenstaion were not issued in bulk ", "Verification successful",null);
									driver.findElement(By.xpath("//md-checkbox[@ng-model='issueList.model.selecAll']")).click();
									Selection=true;
									driver.findElement(By.xpath(IssueCompButton)).click();
									driver.findElement(By.xpath(OkButton)).click();
									loadhandling(driver);
									String CompIssued = driver.findElement(By.xpath("//div[contains(text(),'Compensation Not Issued')]")).getText();
									String[] nCompIssuedcount = CompIssued.split("\\(");
									String sCompIsscount = nCompIssuedcount[1];
									sCompIsscount = sCompIsscount.replaceAll("\\)","");
									if (Integer.parseInt(sCompIsscount)>100) {
										queryObjects.logStatus(driver, Status.PASS, "All 100 compensation were issued", "Verification successful",null);
									}
									else {
										queryObjects.logStatus(driver, Status.FAIL, "All 100 compensation were not issued", "Verification successful",null);
									}
								}
							}
							if (Selection) {
								String AmtVal = driver.findElement(By.xpath("//div[@class='pssgui-bold pos-con layout-row']")).getText().trim();
								String SplitAmt[]= AmtVal.split("\\n");

								if (SplitAmt[1].trim().equalsIgnoreCase("-")) {
									SplitAmt[1] ="0";
								}
								boolean isValidate = false;
								if (SelectAll.equalsIgnoreCase("y") && NoOfPax!="") {
									if (Integer.parseInt(SplitAmt[1].trim())==(Integer.parseInt(sMonetaryAmt) * Integer.parseInt(NoOfPax)) && Integer.parseInt(SplitAmt[2].trim())==(Integer.parseInt(Hotel_EMD)  * Integer.parseInt(NoOfPax)) && Integer.parseInt(SplitAmt[3].trim())==(Integer.parseInt(Meal_EMD) * Integer.parseInt(NoOfPax)) && Integer.parseInt(SplitAmt[4].trim())==(Integer.parseInt(Transport_EMD) * Integer.parseInt(NoOfPax))) {
										isValidate = true;
									}
								} else {
									if (Integer.parseInt(SplitAmt[1].trim())==Integer.parseInt(sMonetaryAmt) && Integer.parseInt(SplitAmt[2].trim())==Integer.parseInt(Hotel_EMD) && Integer.parseInt(SplitAmt[3].trim())==Integer.parseInt(Meal_EMD) && Integer.parseInt(SplitAmt[4].trim())==Integer.parseInt(Transport_EMD)) {
										isValidate = true;
									}
								}
								// if (Integer.parseInt(SplitAmt[1].trim())==Integer.parseInt(sMonetaryAmt) && Integer.parseInt(SplitAmt[2].trim())==0 && Integer.parseInt(SplitAmt[4].trim())==0) {
								if (isValidate) {
									queryObjects.logStatus(driver, Status.PASS, "Compensation amount verification ", "Verification successful",null);
									if (driver.findElement(By.xpath(PrintXpath)).isEnabled()) {
										driver.findElement(By.xpath(PrintXpath)).click();
										loadhandling(driver);
										if (driver.findElements(By.xpath("//span[contains(text(),'Print failed')]")).size()>0) {
											queryObjects.logStatus(driver, Status.PASS, "Compensation Issued tab: Print button verification ", "Print failed is displayed on selecting Print button, verification successful",null);
										} else {
											queryObjects.logStatus(driver, Status.WARNING, "Compensation Issued tab: Print button verification ", "Print failed warning message is not displayed on selecting Print button, verification failed",null);
										}}
									driver.findElement(By.xpath(EmailLink)).click();
									loadhandling(driver);
									if (driver.findElement(By.xpath(EmailText)).isDisplayed()) {
										driver.findElement(By.xpath("//md-checkbox[@name='copy']")).click();
										driver.findElement(By.xpath(EmailText)).sendKeys("1");
										driver.findElement(By.xpath(EmailText)).clear();

										driver.findElement(By.xpath(EmailText)).sendKeys("jenny.sb@mphasis.com");
										driver.findElement(By.xpath(OkButton)).click();
										loadhandling(driver);
										Thread.sleep(200);
										//driver.findElement(By.xpath(PaxCheckbox)).click();
										// driver.findElement(By.xpath("//md-checkbox[@aria-label='pax-chk']")).click();
										driver.findElement(By.xpath(EmailButton)).click();													 
										loadhandling(driver);

										try {
											if (driver.findElement(By.xpath(CompIssuedTab)).isDisplayed()) {
												queryObjects.logStatus(driver, Status.FAIL, "Issue Compensation for the passenger ", "Compensation is not issued",null);
											}


										} catch (Exception e) {
											if (driver.findElement(By.xpath(EMDToPrintPage)).isDisplayed()) {
												queryObjects.logStatus(driver, Status.PASS, "Issue Compensation for the passenger ", "Compensation issued",null);
											}
										}
										//break;
									} else {
										queryObjects.logStatus(driver, Status.FAIL, "Enter Email ID ", "Email pop up is not displayed",null);
									}

								} else {
									queryObjects.logStatus(driver, Status.FAIL, "Compensation amount verification ", "Verification failed",null);
								}
							}
						}
					} catch (Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Issue Compensation tab display ", "Compensation issued tab is not displayed",e);
					}

				}else if (!IssueComp.isEmpty()) {
					String AmtVal = driver.findElement(By.xpath("//div[@class='pssgui-bold pos-con layout-row']")).getText().trim();
					String SplitAmt[]= AmtVal.split("\\n");

					if (SplitAmt[1].trim().equalsIgnoreCase("-")) {
						SplitAmt[1] ="0";
					}
					boolean isValidate = false;
					if (SelectAll.equalsIgnoreCase("y") && NoOfPax!="") {
						if (Integer.parseInt(SplitAmt[1].trim())==(Integer.parseInt(sMonetaryAmt) * Integer.parseInt(NoOfPax)) && Integer.parseInt(SplitAmt[2].trim())==(Integer.parseInt(Hotel_EMD)  * Integer.parseInt(NoOfPax)) && Integer.parseInt(SplitAmt[3].trim())==(Integer.parseInt(Meal_EMD) * Integer.parseInt(NoOfPax)) && Integer.parseInt(SplitAmt[4].trim())==(Integer.parseInt(Transport_EMD) * Integer.parseInt(NoOfPax))) {
							isValidate = true;
						}
					} else {
						if (Integer.parseInt(SplitAmt[1].trim())==Integer.parseInt(sMonetaryAmt) && Integer.parseInt(SplitAmt[2].trim())==Integer.parseInt(Hotel_EMD) && Integer.parseInt(SplitAmt[3].trim())==Integer.parseInt(Meal_EMD) && Integer.parseInt(SplitAmt[4].trim())==Integer.parseInt(Transport_EMD)) {
							isValidate = true;
						}
					}
					// if (Integer.parseInt(SplitAmt[1].trim())==Integer.parseInt(sMonetaryAmt) && Integer.parseInt(SplitAmt[2].trim())==0 && Integer.parseInt(SplitAmt[4].trim())==0) {
					if (isValidate) {
						queryObjects.logStatus(driver, Status.PASS, "Compensation amount verification before issuing Compensation", "Verification successful",null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Compensation amount verification before issuing Compensation", "Verification failed",null);
					}
				}else {
					queryObjects.logStatus(driver, Status.FAIL, "Compensation issue button status", "Issue compensation button is enabled.",null);
				}

			}else {
				queryObjects.logStatus(driver, Status.FAIL, "Verify Data after Compensation reason update", "Passenger data displayed is not correct.",null);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	public static void UpdateMonetaryAmt(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {

		sMonetaryAmt = FlightSearch.getTrimTdata(queryObjects.getTestData("MonetaryAmt"));
		sOverrideRsn = FlightSearch.getTrimTdata(queryObjects.getTestData("OverrideReason"));
		String sSelectAll = FlightSearch.getTrimTdata(queryObjects.getTestData("SelectAll"));
		String UpdateEMD =  FlightSearch.getTrimTdata(queryObjects.getTestData("UpdateEMD"));
		String  Meal_EMD = FlightSearch.getTrimTdata(queryObjects.getTestData("Meal_EMD"));

		if (sMonetaryAmt.isEmpty() && sOverrideRsn.isEmpty()) {
			sMonetaryAmt = Atoflow.sMonetaryAmt;
			sOverrideRsn = Atoflow.sOverrideRsn;
		}
		if ((sMonetaryAmt!="" && sMonetaryAmt!=null) ||UpdateEMD!="") {
			try {
				/*Selection = false;
					if(sSelectAll.equalsIgnoreCase("Y")) {
						driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'model.selecAll')]")).click();
						Selection=true;
					}*/
				List<WebElement> PaxCnt = driver.findElements(By.xpath(PaxCheckbox));
				for (int i=1; i<= PaxCnt.size(); i++) {
					if (Orders!="") {
						if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(Orders)) {
							//if (PaxCnt.size()>0 && sOrderNum.contains(driver.findElement(By.xpath("("+OrderRowXpath+")["+i+"]")).getText().trim())) {
							//driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
							driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
							Selection=true;
							//break;
						}
					}else {
						if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(sOrderNum)) {
							driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
							Selection=true;
						}
					}
					if (Selection) {

						if(UpdateEMD.equalsIgnoreCase("Y")){
							driver.findElement(By.xpath("(//input[contains(@ng-blur,'Meal')])["+i+"]")).click();
							driver.findElement(By.xpath("(//input[contains(@ng-blur,'Meal')])["+i+"]")).clear();
							driver.findElement(By.xpath("(//input[contains(@ng-blur,'Meal')])["+i+"]")).sendKeys(Meal_EMD);
							driver.findElement(By.xpath("(//input[contains(@ng-blur,'Meal')])["+i+"]")).sendKeys(Keys.TAB);

						}else {

							//driver.findElement(By.xpath(PaxCheckbox)).click();
							driver.findElement(By.xpath("("+AmountXpath+")["+i+"]")).click();
							driver.findElement(By.xpath("("+AmountXpath+")["+i+"]")).clear();
							driver.findElement(By.xpath("("+AmountXpath+")["+i+"]")).sendKeys(sMonetaryAmt);//Enter Amt
							driver.findElement(By.xpath("("+AmountXpath+")["+i+"]")).sendKeys(Keys.TAB);
						}

						boolean exist = false;
						try {
							exist =  driver.findElement(By.xpath(OverrideReason)).isDisplayed();
						}catch(Exception e) {}

						String sNegative = FlightSearch.getTrimTdata(queryObjects.getTestData("Negative"));

						if(sNegative.equalsIgnoreCase("Y")) {
							try {
								boolean err = driver.findElement(By.xpath("//div[contains(@class,'error-msg')]/span")).isDisplayed();
								if (err) {
									String message = driver.findElement(By.xpath("//div[contains(@class,'error-msg')]/span")).getText();
									queryObjects.logStatus(driver, Status.PASS, "Error displayed "+message, "expected error displayed.",null);
									sMonetaryAmt = driver.findElement(By.xpath("("+AmountXpath+")["+i+"]")).getAttribute("value");
								}

							}catch(Exception e) {
								queryObjects.logStatus(driver, Status.FAIL, "Error was not displayed", "Amount was changed without error",e);
							}

						}else {
							if (exist) {

								queryObjects.logStatus(driver, Status.PASS, "Override reason page", "Override reason page is displayed.",null);
								driver.findElement(By.xpath("//md-checkbox[@name='copy']")).click();
								driver.findElement(By.xpath(OverrideReason)).click();
								driver.findElement(By.xpath(OverrideReason)).sendKeys(sOverrideRsn);
								driver.findElement(By.xpath(OkButton)).click();
								//loadhandling(driver);
								Thread.sleep(200);


							}
							/*else {
									 queryObjects.logStatus(driver, Status.FAIL, "Override reason page", "Override reason page is not displayed.",null);
								 }*/
						}

						if (driver.findElement(By.xpath(IssueCompButton)).isEnabled()) {
							queryObjects.logStatus(driver, Status.PASS, "Update Override reason", "Override reason is updated.",null);
						}else {
							queryObjects.logStatus(driver, Status.FAIL, "Update Override reason", "Override reason is not updated.",null);
						}
						if(!sSelectAll.equalsIgnoreCase("Y")) {
							driver.findElement(By.xpath("//md-checkbox[@aria-label='pax-chk']")).click();
						}

						//Click the select all checkbox

					}

				}

			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Update Override reason - Monetary Amount", "Override reason is not updated.",null);
			}
		}

	}
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	public static void UpdateAdditionalDetails(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {
		AddDetailsTab = FlightSearch.getTrimTdata(queryObjects.getTestData("AdditionalDetailsTab"));
		AddDetType = FlightSearch.getTrimTdata(queryObjects.getTestData("AddDetailsType"));
		AddDetValue = FlightSearch.getTrimTdata(queryObjects.getTestData("AddDetailsValue"));
		if (AddDetailsTab.isEmpty()) {
			AddDetailsTab = Atoflow.AddDetailsTab;						
		}
		if (AddDetailsTab!="" && AddDetailsTab!=null) {
			try {
				Selection = false;
				List<WebElement> PaxCnt = driver.findElements(By.xpath(PaxCheckbox));
				for (int i=1; i<= PaxCnt.size(); i++) {
					if (Orders!="") {
						if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(Orders)) {
							//if (PaxCnt.size()>0 && sOrderNum.contains(driver.findElement(By.xpath("("+OrderRowXpath+")["+i+"]")).getText().trim())) {
							//driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
							driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
							Selection=true;
							//break;
						}
					}else {
						if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(sOrderNum)) {
							driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
							Selection=true;
						}
					}
					if (Selection) {
						if (i==PaxCnt.size()) {
							driver.findElement(By.xpath("(//span[contains(@ng-click,'issueList.restrictions')])["+i+"]")).click();
						}
					}
				}
				if (driver.findElement(By.xpath("//div[contains(text(),'Compensation History')]")).isDisplayed()) {
					driver.findElement(By.xpath("//div[contains(text(),'"+AddDetailsTab+"')]")).click();
					Thread.sleep(100);
					if (AddDetType.isEmpty()) {
						AddDetType = Atoflow.AddDetType;						
					}
					if (AddDetValue.isEmpty()) {
						AddDetValue = Atoflow.AddDetValue;						
					}
					if (AddDetType.equalsIgnoreCase("CCCaseNum")) {
						driver.findElement(By.xpath("//input[@ng-model='compensationRestrictions.model.tempax.careNumber']")).sendKeys(AddDetValue);
						if (driver.findElement(By.xpath("//input[@ng-model='compensationRestrictions.model.tempax.careNumber']")).getAttribute("value").contains(AddDetValue)) {
							queryObjects.logStatus(driver, Status.PASS, "Verify updated Additional details - "+AddDetailsTab, AddDetType+" is updated.",null); 
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Verify updated Additional details - "+AddDetailsTab, AddDetType+" is not updated.",null);
						}
					}
					if (AddDetType.equalsIgnoreCase("Meal")) {

						driver.findElement(By.xpath("(//i[@class='toggle-arrow ng-scope icon-forward'])[2]")).click();//Hotel,Meal,Transportation
						driver.findElement(By.xpath("//input[@ng-model='reason.Endorsement']")).sendKeys(AddDetValue);
						driver.findElement(By.xpath("//md-checkbox[@name='copy']")).click();
					}
					driver.findElement(By.xpath("//button[@translate='cm.ok']")).click();
					Thread.sleep(200);
					loadhandling(driver);
					//Click the select all checkbox
					driver.findElement(By.xpath("//md-checkbox[@aria-label='pax-chk']")).click();

				}


			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Update Additional details - "+AddDetailsTab, AddDetType+" is not updated.",null);
			}
		}

	}

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	public static void VerifyUpdatedAdditionalDetails(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException {
		if (AddDetailsTab!=""  && AddDetailsTab!=null) {
			try {
				Selection = false;
				List<WebElement> PaxCnt = driver.findElements(By.xpath(PaxCheckbox));
				for (int i=1; i<= PaxCnt.size(); i++) {
					if (Orders!="") {
						if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(Orders)) {
							//if (PaxCnt.size()>0 && sOrderNum.contains(driver.findElement(By.xpath("("+OrderRowXpath+")["+i+"]")).getText().trim())) {
							driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
							Selection=true;
						}
					}else {
						if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(sOrderNum)) {
							driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
							Selection=true;
						}
					}
					if (Selection) {
						driver.findElement(By.xpath("(//span[contains(@ng-click,'compensationPrintList.restrictions')])["+i+"]")).click();
						if (driver.findElement(By.xpath("//div[contains(text(),'Compensation History')]")).isDisplayed()) {
							driver.findElement(By.xpath("//i[@class='toggle-arrow ng-scope icon-forward']")).click();
							String SplitVal[] = (driver.findElement(By.xpath("//div[@ng-repeat='item in emd.Emds']")).getText().trim()).split("\\n");
							EMDNum = SplitVal[6];
							if (AddDetType.equalsIgnoreCase("CCCaseNum")) {
								if (driver.findElement(By.xpath("//input[@ng-model='compensationRestrictions.model.tempax.careNumber']")).getAttribute("value").contains(AddDetValue)) {
									queryObjects.logStatus(driver, Status.PASS, "Verify updated Additional details - "+AddDetailsTab, AddDetType+" is updated.",null); 
								} else {
									queryObjects.logStatus(driver, Status.FAIL, "Verify updated Additional details - "+AddDetailsTab, AddDetType+" is not updated.",null);
								}
								//i[@class='toggle-arrow ng-scope icon-forward']Hotel,Meal,Transportation
							}else if (AddDetType.equalsIgnoreCase("Meal")) {
								driver.findElement(By.xpath("(//i[@class='toggle-arrow ng-scope icon-forward'])[2]")).click();//Hotel,Meal,Transportation
								if (driver.findElement(By.xpath("//div[contains(text(),'Valid Only For Dinner')]")).getAttribute("value").contains(AddDetValue)) {
									queryObjects.logStatus(driver, Status.PASS, "Verify updated Additional details - "+AddDetailsTab, AddDetType+" is updated.",null); 
								} else {
									queryObjects.logStatus(driver, Status.FAIL, "Verify updated Additional details - "+AddDetailsTab, AddDetType+" is not updated.",null);
								}										
							}
							driver.findElement(By.xpath("//button[@translate='cm.ok']")).click();
							Thread.sleep(200);
							loadhandling(driver);
						}						
					}
				}

				//Click the select all checkbox
				//driver.findElement(By.xpath("//md-checkbox[@aria-label='pax-chk']")).click();	
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Verify Additional details - "+AddDetailsTab, AddDetType+" Unable to locate the passenger data.",null);
			}
		}

	}

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	public static void PrintEMD(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {
		if (FlightSearch.getTrimTdata(queryObjects.getTestData("CheckEMD")).equalsIgnoreCase("Y")) {
			try {

				if (driver.findElement(By.xpath(EMDToPrintPage)).isDisplayed()) {
					driver.findElement(By.xpath(EMDPrintedPage)).click();
					loadhandling(driver);
					driver.findElement(By.xpath(EMDToPrintPage)).click();
					loadhandling(driver);
					driver.findElement(By.xpath(EMDToPrintPage)).click();
					List<WebElement> PaxCnt = driver.findElements(By.xpath(PaxCheckbox));

					for (int i=1; i<= PaxCnt.size(); i++) {
						Selection = false;//div[contains(@class,'issuedList-name')]
						if (Orders!="" && Orders.contains(";")) {
							if (PaxCnt.size()>0 && sOrderNum.contains(driver.findElement(By.xpath("("+OrderRowXpath+")["+i+"]")).getText().trim())) {
								driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
								Selection=true;
							}
						}else {
							if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(sOrderNum)) {
								driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
								Selection=true;
							}
						}
						if (Selection) {
							if (driver.findElement(By.xpath(PrintXpath)).isEnabled()) {
								driver.findElement(By.xpath(PrintXpath)).click();
								loadhandling(driver);
								if (driver.findElements(By.xpath("//span[contains(text(),'Print failed')]")).size()>0) {
									queryObjects.logStatus(driver, Status.PASS, "EMD Available for Print tab: Print button verification ", "Print failed is displayed on selecting Print button, verification successful",null);
								} else {
									queryObjects.logStatus(driver, Status.WARNING, "EMD Available for Print tab: Print button verification ", "Print failed is not displayed on selecting Print button, verification failed",null);
								}}
							if (driver.findElement(By.xpath("//i[contains(@ng-if,'reports.Compensations[0].emailStatus')]")).isDisplayed()) {
								queryObjects.logStatus(driver, Status.PASS, "Check Email Icon ", "Email has been sent and the icon is displayed next to email link",null);
								if (Verification_Detail.equalsIgnoreCase("Resend Email")) {//Added by Jenny
									driver.findElement(By.xpath("(//span[@ng-click='compensationPrintList.addEmail(reports)'])[1]")).click();
									loadhandling(driver);
									if (driver.findElement(By.xpath(EmailText)).isDisplayed()) {
										driver.findElement(By.xpath("//md-checkbox[@name='copy']")).click();
										driver.findElement(By.xpath(EmailText)).sendKeys("1");
										driver.findElement(By.xpath(EmailText)).clear();
										driver.findElement(By.xpath(EmailText)).sendKeys("jenny.sb@mphasis.com");
										driver.findElement(By.xpath(OkButton)).click();
										loadhandling(driver);
										Thread.sleep(200);
										driver.findElement(By.xpath(EmailButton)).click();													 
										loadhandling(driver);
										if (driver.findElement(By.xpath("//span[contains(text(),'Email sent successfully.')]")).isDisplayed()) {
											queryObjects.logStatus(driver, Status.PASS, "Resend Email ", "Email has been sent",null);
										} else {
											queryObjects.logStatus(driver, Status.FAIL, "Resend Email ", "Email is not sent",null);
										}
									}
								}
							}else {
								queryObjects.logStatus(driver, Status.FAIL, "Email Icon verification ", "Email Icon is not displayed",null);
							}
							if (!(Verification_Detail.equalsIgnoreCase("Resend Email"))) {
								loadhandling(driver);
								driver.findElement(By.xpath("(//span[contains(@ng-click,'compensationPrintList.restrictions')])["+i+"]")).click();
								loadhandling(driver);
								if (driver.findElement(By.xpath("//div[contains(text(),'Compensation History')]")).isDisplayed()) {
									driver.findElement(By.xpath("//i[@class='toggle-arrow ng-scope icon-forward']")).click();
									loadhandling(driver);
									String SplitVal[] = (driver.findElement(By.xpath("//div[@ng-repeat='item in emd.Emds']")).getText().trim()).split("\\n");
									EMDNum = SplitVal[6];
									String semdnoTemp = Login.EMDNO;
									semdnoTemp=semdnoTemp.trim();
									
									if(!semdnoTemp.isEmpty())  // storing for multiple EMD number in ENv sheet 
										Login.EMDNO= semdnoTemp+";"+EMDNum;
									else
										Login.EMDNO= EMDNum;
									driver.findElement(By.xpath("//button[@translate='cm.ok']")).click();
									Thread.sleep(200);									 
								}}}}}

				/*if (driver.findElement(By.xpath("//span[contains(text(),'Email sent successfully')]")).isDisplayed()) {
										queryObjects.Status(driver, Status.PASS, "Issue Compensation for the passenger ", "Compensation issued",null);
									}*/
				//driver.findElement(By.xpath("//md-checkbox[@aria-label='pax-chk']")).click();
				
				
				if (Verification_Detail.equalsIgnoreCase("NoPrintBtn")) {
					driver.findElement(By.xpath(EMDToPrintPage)).click();
					loadhandling(driver);
					driver.findElement(By.xpath(EMDPrintedPage)).click();
					loadhandling(driver);
					List<WebElement> PaxCnt = driver.findElements(By.xpath("//div[contains(@class,'issuedList-name')]"));
					Selection = false;
					for (int i=1; i<= PaxCnt.size(); i++) {
						if (PaxCnt.size()>0 && sOrderNum.contains(driver.findElement(By.xpath("((//div[@id='adjTable']//div[contains(@class,'input ng-binding ng-scope flex')])[2])["+i+"]")).getText().trim())) {
							Selection = true;
						}
					}
					if (Selection) {
						if (driver.findElements(By.xpath(PrintXpath)).size()==0) {
							queryObjects.logStatus(driver, Status.PASS, "EMD Printed tab - Print button verification ", "Print button does not exist, Verification successful",null);
						} else {
							queryObjects.logStatus(driver, Status.PASS, "EMD Printed tab - Print button verification ", "Print button is displayed, Verification failed",null);
						}
					} else {
						queryObjects.logStatus(driver, Status.PASS, "Check the PNR is moved to EMD Printed tab", "PNR is not available in EMD Printed tab",null);
					}
				}

			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Print/Email Icon verification ", "Print/Email Icon verification failed",e);
			}

		}
	}

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	public static void VerifyReport(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {
		if (FlightSearch.getTrimTdata(queryObjects.getTestData("VerifyReport")).equalsIgnoreCase("Y")) {
			String sReportSearchBy = FlightSearch.getTrimTdata(queryObjects.getTestData("ReportSearchBy"));
			String rDate = ""; String oDate = "";
			Calendar cal = Calendar.getInstance();
			try {
				if (sReportSearchBy!="") {
					try {
						driver.findElement(By.xpath(HomeXpath)).click();
						loadhandling(driver);
					} catch (Exception e) {}
					driver.findElement(By.xpath(ReportXpath)).click();
					loadhandling(driver);
					driver.findElement(By.xpath(ReportSearchList)).click();
					if (sReportSearchBy.equalsIgnoreCase("Passenger Report")) {
						driver.findElement(By.xpath("//div[contains(text(),'Passenger Report')]")).click();
						driver.findElement(By.xpath(OrderIdXpath)).sendKeys(sOrderNum);
						Thread.sleep(200);
						driver.findElement(By.xpath(ReportTktNoXpath)).sendKeys(PaxTktNum);
					}
					Thread.sleep(200);
					if (sReportSearchBy.equalsIgnoreCase("Flight Report")) {
						Thread.sleep(400);
						driver.findElement(By.xpath("//div[contains(text(),'Flight Report')]")).click();
						loadhandling(driver);
						driver.findElement(By.xpath("//input[@name='Flight']")).sendKeys(PaxFlightNum);
						driver.findElement(By.xpath(FromLocXpath)).sendKeys(PaxFromLoc);
						Thread.sleep(100);
						driver.findElement(By.xpath(FromLocXpath)).sendKeys(Keys.TAB);
						driver.findElement(By.xpath(ToLocXpath)).sendKeys(PaxToLoc);
						Thread.sleep(100);
						driver.findElement(By.xpath(ToLocXpath)).sendKeys(Keys.TAB);						
						if (FlightSearch.getTrimTdata(queryObjects.getTestData("Days"))!="") {
							cal.add(Calendar.DATE, Integer.parseInt(FlightSearch.getTrimTdata(queryObjects.getTestData("Days"))));
						}else {
							cal.add(Calendar.DATE, 0);
						}
						rDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
						driver.findElement(By.xpath(ReportDate)).sendKeys(rDate);
					}
					if (sReportSearchBy.equalsIgnoreCase("EMD Report")) {
						driver.findElement(By.xpath("//div[contains(text(),'EMD Report')]")).click();
						loadhandling(driver);
						if (FlightSearch.getTrimTdata(queryObjects.getTestData("Days"))!="") {
							cal.add(Calendar.DATE, Integer.parseInt(FlightSearch.getTrimTdata(queryObjects.getTestData("Days"))));
						}else {
							cal.add(Calendar.DATE, 0);
						}
						oDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
						rDate =  new SimpleDateFormat("MM/dd/yyyy").format(new Date());
						driver.findElement(By.xpath("(//input[@class='md-datepicker-input'])[1]")).sendKeys(oDate);
						driver.findElement(By.xpath("(//input[@class='md-datepicker-input'])[2]")).sendKeys(rDate);
						loadhandling(driver);
						Thread.sleep(200);
					}
					if (driver.findElement(By.xpath(ReportViewBtn)).isEnabled()) {
						driver.findElement(By.xpath(ReportViewBtn)).click();
						loadhandling(driver);
					}
					
					try {
						Date Current = null;
						String getDt = "";
						loadhandling(driver);
						if (driver.findElement(By.xpath(ReportPage)).isDisplayed()) {
							int PgNo = 0;
							String PgCnt = "";
							if (sReportSearchBy.equalsIgnoreCase("EMD Report")) {
								Date fromDt = new SimpleDateFormat("MM/dd/yyyy").parse(oDate);
								Date toDt =  new SimpleDateFormat("MM/dd/yyyy").parse(rDate);
								List<WebElement> Reports = driver.findElements(By.xpath("//div[@ng-repeat='emd in compensationReports.model.emdValues']"));
								if (Reports.size()>=1) {
									PgCnt = driver.findElement(By.xpath("//div[contains(@class,'page-margin ng-binding')]")).getText().trim();
									PgNo = Integer.parseInt(PgCnt.substring(PgCnt.indexOf("/")+1, PgCnt.length()));
									for (int pn = 1; pn <PgNo; pn++) {
										Reports = driver.findElements(By.xpath("//div[@ng-repeat='emd in compensationReports.model.emdValues']"));
										for (int rt = 1; rt <= Reports.size(); rt++) {
											getDt = "";
											getDt = driver.findElement(By.xpath("((//div[@ng-repeat='emd in compensationReports.model.emdValues'])["+rt+"]//div[contains(@class,'pssgui-bold ng-binding layout-column')])[2]")).getText().trim();
											Current =  new SimpleDateFormat("MM/dd/yyyy").parse(getDt);
											if ((fromDt.equals(Current)|| fromDt.before(Current)) && (toDt.equals(Current)|| toDt.after(Current))) {
												queryObjects.logStatus(driver, Status.PASS, "EMD REport verification for the given period", "EMD reports are displayed for the selected period",null);
											} else {
												queryObjects.logStatus(driver, Status.FAIL, "EMD REport verification for the given period", "EMD reports are not displayed for the selected period",null);
											}
										}
										driver.findElement(By.xpath("//div[@class='pagination-btn pageSection displayNext']//i[contains(@class,'toggle-arrow icon-forward')][1]")).click();
										loadhandling(driver);
									}
									
								} else {
									queryObjects.logStatus(driver, Status.FAIL, "REport verification", "No EMD report available for the given period",null);
								}
							} else {
								List<WebElement> Reports = driver.findElements(By.xpath(ReportRowData));
								Boolean sOrder = false;
								if (Reports.size()>=1) {
									for (int i = 1; i <= Reports.size(); i++) {
										if (sReportSearchBy.equalsIgnoreCase("Flight Report")) {
											if (driver.findElement(By.xpath("("+ReportRowData+")["+i+"]")).getText().trim().contains(sOrderNum)) {
												driver.findElement(By.xpath("("+ReportArrowIcon+")["+i+"]")).click();
												sOrder = true;
											}
										}
										if (sReportSearchBy.equalsIgnoreCase("Passenger Report")) {
											if (driver.findElement(By.xpath("(//div[contains(text(),'Order ID:  ')])["+i+"]")).getText().trim().contains(sOrderNum)) {
												driver.findElement(By.xpath("("+ReportArrowIcon+")["+i+"]")).click();
												sOrder = true;
											}
										}
									}
									if (sOrder) {

										driver.findElement(By.xpath(PrintReport)).click();

										//Verify the Report Details
										try{
											if (driver.findElement(By.xpath("//div[contains(text(),'"+sMonetaryAmt+"')]")).isDisplayed()){
												queryObjects.logStatus(driver, Status.PASS, "Report verification - Amount", "Verification successful",null);
											}
											if (driver.findElement(By.xpath("//div[contains(text(),'"+EMDNum+"')]")).isDisplayed()){
												queryObjects.logStatus(driver, Status.PASS, "Report verification - EMDNUM", "Verification successful",null);
											}					
											if (driver.findElement(By.xpath("//div[contains(text(),'"+sOverrideRsn+"')]")).isDisplayed()){
												queryObjects.logStatus(driver, Status.PASS, "Report verification - Override Reason", "Verification successful",null);
											}
											if (driver.findElement(By.xpath("//div[contains(text(),'"+PaxFlightNum+"')]")).isDisplayed()){
												queryObjects.logStatus(driver, Status.PASS, "Report verification - Flight Number", "Verification successful",null);
											}
											if (driver.findElement(By.xpath("//div[contains(text(),'"+PaxTktNum+"')]")).isDisplayed()){
												queryObjects.logStatus(driver, Status.PASS, "Report verification - Ticket Number", "Verification successful",null);
											}
											if (driver.findElement(By.xpath("//div[contains(text(),'CM"+FlightSearch.getTrimTdata(queryObjects.getTestData("FFNumber"))+"')]")).isDisplayed()){
												queryObjects.logStatus(driver, Status.PASS, "Report verification - Frequent Flyer Number", "Verification successful",null);
											}
											if (driver.findElement(By.xpath("//div[contains(text(),'"+sCompensationRsn+"')]")).isDisplayed()){	
												queryObjects.logStatus(driver, Status.PASS, "Report verification - Compensation Reason", "Verification successful",null);
											}
											if (driver.findElement(By.xpath("//div[contains(text(),'CM.')]")).getText().contains(sAgent)){
												queryObjects.logStatus(driver, Status.PASS, "Report verification - Agent Id", "Verification successful",null);
											}
											if (driver.findElement(By.xpath(EndorsementReport)).isDisplayed()){
												queryObjects.logStatus(driver, Status.PASS, "Report verification - Endorsement Details", "Verification successful",null);
											}
											if (driver.findElement(By.xpath(EndorsementReport1)).isDisplayed()){
												queryObjects.logStatus(driver, Status.PASS, "Report verification - Endorsement Details", "Verification successful",null);
											}
											if (driver.findElement(By.xpath(EndorsementReport2)).isDisplayed()){
												queryObjects.logStatus(driver, Status.PASS, "Report verification - Endorsement Details", "Verification successful",null);
											}
											if (driver.findElement(By.xpath(EndorsementReport3)).isDisplayed()){
												queryObjects.logStatus(driver, Status.PASS, "Report verification - Endorsement Details", "Verification successful",null);
											}
											if (driver.findElement(By.xpath(EndorsementReport4)).isDisplayed()){
												queryObjects.logStatus(driver, Status.PASS, "Report verification - Endorsement Details", "Verification successful",null);
											}
											if (driver.findElement(By.xpath(EndorsementReport5)).isDisplayed()){
												queryObjects.logStatus(driver, Status.PASS, "Report verification - Endorsement Details", "Verification successful",null);
											}
										}catch (Exception e) {
											queryObjects.logStatus(driver, Status.FAIL, "REport verification - Endorsement Details", "Verification failed",e);
										}
									}} else {
										queryObjects.logStatus(driver, Status.FAIL, "REport verification", "No report available",null);
									}
							}
							
						}

					} catch (Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "REport verification failed", "",e);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			

		}

	}
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	public static void CloseStationReport(WebDriver driver, BFrameworkQueryObjects queryObjects, String AmtVal) throws IOException {
		if (SalesReport!="" && SalesReport.equalsIgnoreCase("Station")) {
			if (AmtVal=="500") {
				AmtVal="301,500";					
			}
			try {
				driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
				Thread.sleep(500);
				driver.findElement(By.xpath("//button[contains(text(),'Sales Reporting')]")).click();
				loadhandling(driver);
				driver.findElement(By.xpath("//button[contains(text(),'Station Sales Report')]")).click(); //Select Station Sales Report Menu
				loadhandling(driver);
				try {
					driver.findElement(By.xpath("//button[@ng-click='dialog.hide()']")).click();
					queryObjects.logStatus(driver, Status.INFO, "Popup up", "Warning pop up is displayed.",null);
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					Calendar cal = Calendar.getInstance();
					String CDate = new SimpleDateFormat("dd-MMM-yy").format(cal.getTime());
					List<WebElement> ReportCnt = driver.findElements(By.xpath("//button[@translate='sr.close.report']"));
					try {
						driver.findElement(By.xpath("//button[@ng-click='dialog.hide()']")).click();
						queryObjects.logStatus(driver, Status.INFO, "Popup up", "Warning pop up is displayed.",null);
					} catch (Exception e) {
						// TODO: handle exception
					}
					for (int i=1; i <= ReportCnt.size(); i++) {
						if (driver.findElement(By.xpath("(//div/span[@class='ng-binding'])["+i+"]")).getText().trim().equals(CDate)) {
							queryObjects.logStatus(driver, Status.PASS, "Current Day Station Report - Verification", "Current day station report exists",null);
						}
						if (driver.findElement(By.xpath("(//button[@translate='sr.close.report'])["+i+"]")).isEnabled()) {
							driver.findElement(By.xpath("(//button[@translate='sr.close.report'])["+i+"]")).click();
							loadhandling(driver);
							driver.findElement(By.xpath("//div[contains(@ng-if,'closeReportPopup')]/button[@translate='sr.close.report']")).click();
							loadhandling(driver);
							Thread.sleep(1000);
						}							

						try {
							driver.findElement(By.xpath("//button[@ng-click='dialog.hide()']")).click();
							queryObjects.logStatus(driver, Status.INFO, "Popup up", "Warning pop up is displayed.",null);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}						 
					try {
						driver.findElement(By.xpath("//button[@ng-click='dialog.hide()']")).click();
						queryObjects.logStatus(driver, Status.INFO, "Popup up", "Warning pop up is displayed.",null);
					} catch (Exception e) {
						// TODO: handle exception
					}
					driver.findElement(By.xpath("//button[@translate='sr.close.station']")).click();
					loadhandling(driver);
					driver.findElement(By.xpath("//div[contains(@ng-if,'closeReportPopup')]/button[@translate='sr.close.station.report']")).click();
					loadhandling(driver);
					try {
						if (driver.findElement(By.xpath("//div[contains(@ng-if,'closeReportPopup')]/button[@translate='sr.close.report']")).isDisplayed()) {
							queryObjects.logStatus(driver, Status.FAIL, "Close Station Report popup", "Close station report popup is not closed.",null);
						}

					} catch (Exception e) {
						queryObjects.logStatus(driver, Status.PASS, "Close Station Report popup", "Close station report popup is closed.",null);
						queryObjects.logStatus(driver, Status.PASS, "Close Station Report - result", "Close station report is successfully completed.",null);
					}

				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Close Station Report", "Close station report verification failed.",null);
				}

			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Close Station Sales Report - ", "Station Sales report is not closed",null);
			}
		}

	}

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	public static void CloseAgentSalesReport(WebDriver driver, BFrameworkQueryObjects queryObjects, String AmtVal) throws IOException {
		if (SalesReport!="" && SalesReport.equalsIgnoreCase("Agent")) {
			try {
				boolean EmdStatus=false;
				driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
				Thread.sleep(500);
				try {
					driver.findElement(By.xpath(AgentReportXpath)).click(); //Select Agent Sales Report Menu
				} catch (Exception e) {
					driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Sales Reporting')]")).click(); //Select Agent Sales Report Menu
					driver.findElement(By.xpath("//button[contains(text(),'Agent Sales Report')]")).click(); //Select Agent Sales Report Menu
				}
				
				loadhandling(driver);
				//Select the list count as 50
				driver.findElement(By.xpath("//md-select[@ng-model='reportFilter.model.showNoOfRecords']")).sendKeys("50");
				//Selecting Total Transaction amount link
				List<WebElement> ReportList = driver.findElements(By.xpath("//div[contains(@ng-repeat,'reportDetailsTable')]"));
				int ReportCnt = ReportList.size();
				for (int i = 1; i <=ReportCnt; i++) {
					if (driver.findElement(By.xpath("(//div[contains(@ng-repeat,'reportDetailsTable')])["+i+"]")).getText().trim().contains(EMDNum)) {

						driver.findElement(By.xpath("(//i[contains(@class,'icon-tax icon-small')])["+i+"]")).click();
						if (driver.findElement(By.xpath("//div[@ng-if='taxPopup.model.baseFare']/div[contains(@ng-class,'amountCtrl')]")).getText().trim().contains(sMonetaryAmt)) {
							queryObjects.logStatus(driver, Status.PASS, "Agent sales report verification - Monetary Amount", "Verification successful",null);								
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Agent sales report verification - Monetary Amount", "Verification failed",null);
						}
						driver.findElement(By.xpath("//i[@class='icon-close']")).click();
						loadhandling(driver);
						driver.findElement(By.xpath("(//i[contains(@class,'toggle-arrow icon-forward')])["+i+"]")).click();
						loadhandling(driver);
						if (sCompensationRsn.equalsIgnoreCase("GW Agent Error")) {
							if (driver.findElement(By.xpath("//div[@class='pssgui-design-text-general ng-binding ng-scope']")).getText().contains("GOOD WILL  AGT ERROR COMP")) {
								EmdStatus=true;
							}
						}
						if (sCompensationRsn.equalsIgnoreCase("Delay 2 to 3:59 hours")) {
							if (driver.findElement(By.xpath("//div[@class='pssgui-design-text-general ng-binding ng-scope']")).getText().contains("DELAY")) {
								EmdStatus=true;
							}
						}
						if (sCompensationRsn.equalsIgnoreCase("Involuntary Downgrade")) {
							if (driver.findElement(By.xpath("//div[@class='pssgui-design-text-general ng-binding ng-scope']")).getText().contains("COMP DWNGRADE")) {
								EmdStatus=true;
							}
						}							 
						if (sCompensationRsn.equalsIgnoreCase("GW Web Error")) {
							if (driver.findElement(By.xpath("//div[@class='pssgui-design-text-general ng-binding ng-scope']")).getText().contains("GOOD WILL WEB ERROR")) {
								EmdStatus=true;
							}
						}
						if (FlightSearch.getTrimTdata(queryObjects.getTestData("VerifyEMD_Res")).equalsIgnoreCase("Y")) {
							driver.findElement(By.xpath("//div[contains(text(),'"+EMDNum+"')]")).click();
							loadhandling(driver);
							EMDVerification_Res(driver, queryObjects);
						}
						break;
					}
				}
				if (EmdStatus) {
					queryObjects.logStatus(driver, Status.PASS, "Agent Sales Report - Verify EMD details for the Order", "EMD details verified successfully.",null);
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Agent Sales Report - Verify EMD details for the Order", "EMD details verifition failed.",null);
				}
				
				driver.findElement(By.xpath("//i[@class='icon-home pssgui-design-page-title-link']")).click();
				loadhandling(driver);// clicking home icon
				Thread.sleep(500);
				
				//Selecting Total Transaction amount link
				driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
				Thread.sleep(500);
				try {
					driver.findElement(By.xpath(AgentReportXpath)).click(); //Select Agent Sales Report Menu
				} catch (Exception e) {
					driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Sales Reporting')]")).click(); //Select Agent Sales Report Menu
					Thread.sleep(500);
					driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Agent Sales Report')]")).click(); //Select Agent Sales Report Menu
				}
				loadhandling(driver);
				driver.findElement(By.xpath("//div/span[@translate='sr.transaction.amount']")).click();
				loadhandling(driver);
				driver.findElement(By.xpath("//div/button[contains(text(),'Close Report')]")).click();
				loadhandling(driver);
				//Click Close Report on the confirm popup
				driver.findElement(By.xpath("//div[contains(@ng-if,'closeReportPopup.popupAction')]/button[@translate='sr.close.report']")).click();
				loadhandling(driver);
				try {
					if (driver.findElement(By.xpath("//div[contains(@ng-if,'closeReportPopup.popupAction')]/button[@translate='sr.close.report']")).isDisplayed()) {
						queryObjects.logStatus(driver, Status.FAIL, "Close Agent Sales Report - Sales Report popup", "Sales report popup is not closed, unable to close agent sales report",null);
					}
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.PASS, "Close Agent Sales Report - ", "Agent Sales report is closed",null);					 
				}
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Close Agent Sales Report - ", "Agent Sales report is not closed",e);
			}
		}

	}

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	public static void EMDVerification_Res(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		if (FlightSearch.getTrimTdata(queryObjects.getTestData("VerifyEMD_Res")).equalsIgnoreCase("Y")) {
			try {
				Thread.sleep(2000);
				/*driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
					Thread.sleep(2000);
					driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Reservations')]")).click();		 
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//pssgui-search-panel[@label='pssgui.search.reservations']/div/md-input-container/input[@ng-model='searchPanel.searchText']")).sendKeys(sOrderNum);
					Thread.sleep(500);
					driver.findElement(By.xpath("//pssgui-search-panel[@label='pssgui.search.reservations']/div/i[@class='icon-search']")).click();
					FlightSearch.loadhandling(driver);
					Thread.sleep(500);
					driver.findElement(By.xpath("//div[div[text()='Tickets']]")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//div[contains(text(),'EMD')]")).click();*/
				try {
					List<WebElement> IconExpand = driver.findElements(By.xpath("//i[@class='toggle-arrow ng-scope icon-forward']"));
					for (int i = IconExpand.size(); i <=1 ; i++) {
						driver.findElement(By.xpath("(//i[@class='toggle-arrow ng-scope icon-forward'])["+i+"]")).click();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				List<WebElement> EmdCnt = driver.findElements(By.xpath(ResEMDXpath));
				for (int i = 1; i <= EmdCnt.size(); i++) {
					if (driver.findElement(By.xpath("("+ResEMDXpath+")["+i+"]")).getText().trim().contains(EMDNum)) {
						queryObjects.logStatus(driver, Status.PASS, "Verify EMD Number in Reservation", "EMD Number is displayed under Tickets - EMD screen",null);
						if (driver.findElement(By.xpath("(//td[@class='name break-word ng-binding ng-scope'])["+i+"]")).getText().trim().equalsIgnoreCase("COMP DWNGRADE")) {
							queryObjects.logStatus(driver, Status.PASS, "Verify EMD Status in Reservation ", "EMD Status is correct",null);
						}
					}						
				}

			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Verify EMD Number & status in Reservation ", "EMD verification failed",null);
			}

		}

	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	public static void VerifyUpdatedCompensationReport(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException, InterruptedException {
		try {
			if (driver.findElement(By.xpath(EMDPrintedPage)).isDisplayed()) {
				queryObjects.logStatus(driver, Status.PASS, "Search with Print List Orders ", "EMD printed page is displayed",null);				
				List<WebElement> PaxCnt = driver.findElements(By.xpath("//div[@ng-repeat='reports in compensationPrintList.model.displayPaxModel']"));
				for (int i=1; i<= PaxCnt.size(); i++) {
					Selection = false;
					if (Orders!="") {
						if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(Orders)) {
							Selection=true;
							//break;
						}
					}else {
						if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(sOrderNum)) {
							Selection=true;
						}
					}
					if (Selection) {
						queryObjects.logStatus(driver, Status.PASS, "Search for the Order ", "Order ID is displayed",null);
						String AmtVal = driver.findElement(By.xpath("(//div[@ng-repeat='reports in compensationPrintList.model.displayPaxModel'])["+i+"]//div[contains(@ng-click,'reports')]//span")).getText().trim();
						AmtVal= AmtVal.substring(0, AmtVal.indexOf("["));
						String Hotel = driver.findElement(By.xpath("(//div[@ng-repeat='reports in compensationPrintList.model.displayPaxModel'])["+i+"]//span[contains(@ng-if,'Compensations[1].Emds')]")).getText().trim();
						String Meal = driver.findElement(By.xpath("(//div[@ng-repeat='reports in compensationPrintList.model.displayPaxModel'])["+i+"]//span[contains(@ng-if,'Compensations[2].Emds')]")).getText().trim();
						String Transport = driver.findElement(By.xpath("(//div[@ng-repeat='reports in compensationPrintList.model.displayPaxModel'])["+i+"]//span[contains(@ng-if,'Compensations[3].Emds')]")).getText().trim();
						if (Hotel.contains("-")) {
							Hotel = "0";
						}
						if (Meal.contains("-")) {
							Meal = "0";
						}
						if (Transport.contains("-")) {
							Transport = "0";
						}
						boolean isValidate = false;
						if (sSelectAll.equalsIgnoreCase("y") && sNoOfPaxToComm!="") {
							if (Integer.parseInt(AmtVal.trim())==(Integer.parseInt(sMonetaryAmt) * Integer.parseInt(sNoOfPaxToComm)) && Integer.parseInt(Hotel)==(Integer.parseInt(Hotel_EMD)  * Integer.parseInt(sNoOfPaxToComm)) && Integer.parseInt(Meal)==(Integer.parseInt(Meal_EMD) * Integer.parseInt(sNoOfPaxToComm)) && Integer.parseInt(Transport)==(Integer.parseInt(Transport_EMD) * Integer.parseInt(sNoOfPaxToComm))) {
								isValidate = true;
							}
						} else {
							if (Integer.parseInt(AmtVal.trim())==Integer.parseInt(sMonetaryAmt) && Integer.parseInt(Hotel)==Integer.parseInt(Hotel_EMD) && Integer.parseInt(Meal)==Integer.parseInt(Meal_EMD) && Integer.parseInt(Transport)==Integer.parseInt(Transport_EMD)) {
								isValidate = true;
							}
						}
						// if (Integer.parseInt(SplitAmt[1].trim())==Integer.parseInt(sMonetaryAmt) && Integer.parseInt(SplitAmt[2].trim())==0 && Integer.parseInt(SplitAmt[4].trim())==0) {
						if (isValidate) {
							queryObjects.logStatus(driver, Status.PASS, "Compensation amount verification ", "Verification successful",null);
							//
							driver.findElement(By.xpath("(//div[@ng-repeat='reports in compensationPrintList.model.displayPaxModel'])["+i+"]//span[contains(@ng-click,'compensationPrintList.restrictions')]")).click();
							loadhandling(driver);
							if (driver.findElement(By.xpath("//div[contains(text(),'Compensation History')]")).isDisplayed()) {
								driver.findElement(By.xpath("//div[contains(text(),'"+AddDetailsTab+"')]")).click();
								Thread.sleep(100);
								
								if (AddDetType.equalsIgnoreCase("CCCaseNum")) {
									if (driver.findElement(By.xpath("//input[contains(@ng-model,'CustomerCareCaseNum')]")).getAttribute("value").contains(AddDetValue)) {
										queryObjects.logStatus(driver, Status.PASS, "Verify updated Additional details - "+AddDetailsTab, AddDetType+" is updated.",null); 
									} else {
										queryObjects.logStatus(driver, Status.FAIL, "Verify updated Additional details - "+AddDetailsTab, AddDetType+" is not updated.",null);
									}
								}
								driver.findElement(By.xpath("//button[@translate='cm.ok']")).click();
								Thread.sleep(200);
								loadhandling(driver);
								
							}
							//
							driver.findElement(By.xpath("(//span[contains(@ng-click,'compensationPrintList.addEmail')])["+i+"]")).click();
							loadhandling(driver);
							if (driver.findElement(By.xpath(EmailText)).isDisplayed()) {
								if (driver.findElement(By.xpath(EmailText)).getAttribute("value").contains("jenny.sb@mphasis.com")) {
									queryObjects.logStatus(driver, Status.PASS, "Check the email display ", "Email id is dispalyed",null);
								} else {
									queryObjects.logStatus(driver, Status.FAIL, "Check the email display ", "Email id is not dispalyed",null);
								}
								driver.findElement(By.xpath(OkButton)).click();
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Click Email link ", "Email pop up is not displayed",null);
							}

						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Compensation amount verification ", "Verification failed",null);
						}
						
					}
				}
				
			} else {
				queryObjects.logStatus(driver, Status.FAIL, "Select Print list ", "Emd printed page is not displayed",null);
			}
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Check the Compensation display", "Verification failed in the displayed issied order",e);
		}
				
	}

	
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	
	public static void SearchOrders(WebDriver driver, BFrameworkQueryObjects queryObjects, String Flight, String Days, String From,  String FFPgm, String FFNum, String sPaxList, String Order, String Etkt, String Search) throws IOException, InterruptedException {
		driver.findElement(By.xpath(SearchXpath)).click();//Select Search tab
		loadhandling(driver);
		if (sFrom!="") {
			driver.findElement(By.xpath(FromLocXpath)).clear();
			Thread.sleep(1000);
			driver.findElement(By.xpath(FromLocXpath)).sendKeys(sFrom);
			loadhandling(driver);
			driver.findElement(By.xpath(FromLocXpath)).sendKeys(Keys.TAB);
		}
		if (Flight!="") {
			driver.findElement(By.xpath(FlightNoXpath)).sendKeys(Flight);
		}
		if (Days!="") {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, Integer.parseInt(Days));
			String sDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
			driver.findElement(By.xpath(SearchDate)).clear();
			driver.findElement(By.xpath(SearchDate)).sendKeys(sDate);
		}
		if (sPaxList!="") {
			driver.findElement(By.xpath(SearchPaxListIcon)).click();
			if (sPaxList.equalsIgnoreCase("Compensation")) {
				driver.findElement(By.xpath(CompensationList)).click();
			}
			if (sPaxList.equalsIgnoreCase("All Passenger List")) {
				driver.findElement(By.xpath(AllPaxListXpath)).click();	
				loadhandling(driver);
			}
			else
				driver.findElement(By.xpath("//div[contains(text(),'"+sPaxList+"')]")).click();
		}
		if (Order!="" && Order!=null) {
			if(sOrderNum.equalsIgnoreCase("RESPNR")){
				sOrderNum = Login.PNRNUM.trim();
			}
			else if (sOrderNum.equalsIgnoreCase("RESMULPNR")) {
				sOrderNum = Login.MultiplePNR.trim();
			}
			if (!(Verification_Detail.equalsIgnoreCase("Resend Email"))) 
				driver.findElement(By.xpath(OrderIdXpath)).sendKeys(sOrderNum);
			
			if(!sOrderNum.equalsIgnoreCase(""))
			{
				driver.findElement(By.xpath(OrderIdXpath)).sendKeys(sOrderNum);
			}
		}

		if (Etkt!="") {
			if(Etkt.equalsIgnoreCase("TktNum"))
				driver.findElement(By.xpath(TicketNoXpath)).sendKeys(FlightSearch.gettecketno.get(0));
			else
				driver.findElement(By.xpath(TicketNoXpath)).sendKeys(Etkt);
		}
		if (FFPgm!="") {
			driver.findElement(By.xpath(FFPgmListIcon)).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//md-option/div[contains(text(),'"+FFPgm+"')]")).click();
		}
		if (FFNum!="") {
			driver.findElement(By.xpath(FFNumberXpath)).sendKeys(FFNum);
		}
		if (sTo!="") {
			driver.findElement(By.xpath(ToLocXpath)).sendKeys(sTo);
		}
		if (Search!="") {
			driver.findElement(By.xpath(SearchBtnXpath)).click();
			loadhandling(driver);
		}
		
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	public static void display(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException  ///suman
	{  

		try
		{  

			String Storedfltnum=Login.FLIGHTNUM;
			String StoredPnr=Login.PNRNUM.trim();


			String OrderId= driver.findElement(By.xpath("//md-content//md-radio-group//div[1]/div[contains(@class,'passenger-list')]//div[4]")).getText().trim();
			if(OrderId.equalsIgnoreCase(sOrderNum) && !sOrderNum.equalsIgnoreCase("") )
			{
				queryObjects.logStatus(driver, Status.PASS, "Checking orderid is displaying or not", "Order id is displayed ",null);  
			}
			else if(OrderId.equalsIgnoreCase(StoredPnr))
			{
				queryObjects.logStatus(driver, Status.PASS, "Checking orderid is displaying or not", "Order id is displayed ",null);
			}
			else
				queryObjects.logStatus(driver, Status.FAIL, "Checking orderid is displaying or not", "Order id is not displayed ",null);


			if(sOrderNum.equalsIgnoreCase(""))
			{
				driver.findElement(By.xpath("//div[contains(text(),'"+StoredPnr+"')]//preceding-sibling::div[contains(text(),'"+Storedfltnum+"')]/preceding::div//md-radio-button")).click();

				driver.findElement(By.xpath("//button[contains(text(),'Continue')]")).click();
				loadhandling(driver);


				Boolean bl=driver.findElements(By.xpath("//div[contains(text(),'"+StoredPnr+"')]")).size()>0;


				if(bl)
				{
					queryObjects.logStatus(driver, Status.PASS, "Checking orderid is displaying or not", "Order id is displayed ",null);   
				}


			} 
		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.FAIL, "Checking orderid is displaying or not", "Order id not displayed",e); 
		}
	}

	//////////////Script to update the email in for passanger///////

	public static void ChangeEmail(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException
	{

		try
		{
			List<WebElement> PaxCnt = driver.findElements(By.xpath(PaxCheckbox));
			for (int i=1; i<= PaxCnt.size(); i++) {

				if (Orders!="") {
					if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(Orders)) {
						//if (PaxCnt.size()>0 && sOrderNum.contains(driver.findElement(By.xpath("("+OrderRowXpath+")["+i+"]")).getText().trim())) {
						driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
						Selection=true;
						break;
					}
				}else {
					if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(sOrderNum)) {
						driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
						break;
					}
				}
			}


			if (driver.findElement(By.xpath(IssueCompButton)).isEnabled()) {
				queryObjects.logStatus(driver, Status.PASS, "Compensation issue button status", "Issue compensation button is enabled.",null);
				driver.findElement(By.xpath(IssueCompButton)).click();
				driver.findElement(By.xpath(OkButton)).click();
				loadhandling(driver);
				driver.findElement(By.xpath(EmailLink)).click();
				loadhandling(driver);

			}	 

			if (driver.findElement(By.xpath(EmailText)).isDisplayed()) {

				// driver.findElement(By.xpath(EmailLink)).click();
				loadhandling(driver);
				driver.findElement(By.xpath("//md-checkbox[@name='copy']")).click();
				driver.findElement(By.xpath(EmailText)).click();
				driver.findElement(By.xpath(EmailText)).clear();
				driver.findElement(By.xpath(EmailText)).sendKeys("Test@automation.com");
				driver.findElement(By.xpath(OkButton)).click();
				loadhandling(driver);





				Thread.sleep(200);
				//driver.findElement(By.xpath(PaxCheckbox)).click();
				// driver.findElement(By.xpath("//md-checkbox[@aria-label='pax-chk']")).click();
				driver.findElement(By.xpath(EmailButton)).click();													 
				loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Checking email Button to update", "Checking email Button is updated",null); 
			}
			else
			{
				queryObjects.logStatus(driver, Status.FAIL, "Checking email Button to update", "Checking email Button is available",null);
			}

			if(FlightSearch.getTrimTdata(queryObjects.getTestData("ResValidation")).equalsIgnoreCase("Yes"))
			{
				PnrForEmailValidation=driver.findElement(By.xpath(PnrFromCompenesation)).getText();
				ReservationPageValidation.EmailupdateValidationOnResPage(driver, queryObjects);
			}
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("ShareValidation")).equalsIgnoreCase("Yes"))
			{
				PnrForEmailValidation=driver.findElement(By.xpath(PnrFromCompenesation)).getText();
				//ReservationPageValidation.EmailupdateValidationOnResPage(driver, queryObjects);
				ISharesflow.EmailValidation(driver, queryObjects);
			}
		}catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.FAIL, "Getting an exception while changing email", "",e);
		}

	}
	/////..............................................................................................................................	

	public static void SaveCommpensationPaxAndNotIsse(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException
	{

		try {
			sCompensationRsn = FlightSearch.getTrimTdata(queryObjects.getTestData("CompensationReason"));
			String sSelectAll = FlightSearch.getTrimTdata(queryObjects.getTestData("SelectAll"));
			String sNoOfPaxToComm = FlightSearch.getTrimTdata(queryObjects.getTestData("NoOfPaxToComm"));
			Orders = Atoflow.PnrNum;
			Selection = false;
			if(sNoOfPaxToComm.equalsIgnoreCase(""))
				sNoOfPaxToComm="1";
			List<WebElement> PaxCnt = driver.findElements(By.xpath(PaxCheckbox));
			int PaxToComm=Integer.parseInt(sNoOfPaxToComm);    /////pax to commpansate

			if(PaxCnt.size() < PaxToComm)
			{
				PaxToComm=PaxCnt.size();
			}

			for (int i=1; i<= PaxToComm; i++) {
				if (Orders!="") {
					if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(Orders)) {
						//if (PaxCnt.size()>0 && sOrderNum.contains(driver.findElement(By.xpath("("+OrderRowXpath+")["+i+"]")).getText().trim())) {
						driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
						//driver.findElement(By.xpath("//md-checkbox[@ng-model='compensationItinerary.model.selectAll']")).click();
						Selection=true;
					}
				}
				else if(sSelectAll.equalsIgnoreCase("Y")) {
					driver.findElement(By.xpath("//md-checkbox[@ng-model='compensationItinerary.model.selectAll']")).click();
					Selection=true;
					break;
				}
				else if(PaxToComm > 0 && !sNoOfPaxToComm.isEmpty() )
				{
					driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
					Selection=true;
				}
				else {
					if (PaxCnt.size()>0 && driver.findElement(By.xpath("("+RowItem+")["+i+"]")).getText().trim().contains(sOrderNum)) {
						driver.findElement(By.xpath("("+PaxCheckbox+")["+i+"]")).click();
						Selection=true;
						break;
					}
				}

			}
			if (Selection) {
				if (sCompensationRsn.isEmpty()) {
					sCompensationRsn = Atoflow.sCompensationRsn;						
				}
				driver.findElement(By.xpath(CompReason)).click();
				driver.findElement(By.xpath("//div[contains(text(),'"+sCompensationRsn+"')]")).click();
				Thread.sleep(100);
				if (driver.findElement(By.xpath(SaveButton)).isEnabled()) {
					driver.findElement(By.xpath(SaveButton)).click();
					loadhandling(driver);
					/* if (driver.findElement(By.xpath("//div[@class='passenger-list pssgui-bold layout-row']")).isDisplayed()){
								 queryObjects.logStatus(driver, Status.PASS, "Update compensation reason", "Compensation reason is updated as "+sCompensationRsn,null);

							}*/

				}
			}				
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Update compensation reason", "Compensation reason is not updated",e);

		}


	}
	public static void SearchOrder_FF(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException {
		try {
			String Flight = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxFlightNum"));
			String GName = FlightSearch.getTrimTdata(queryObjects.getTestData("GivenName"));
			String SName = FlightSearch.getTrimTdata(queryObjects.getTestData("Surname"));
			boolean SearchStatus = false;
			int SelVal = 0; String ListSel = "";
			try {
				if (driver.findElement(By.xpath(OrderListView+"[1]")).isDisplayed()) {
					ListSel = OrderListView;
				}
			} catch (Exception e) {
				if (driver.findElement(By.xpath(PAXListView+"[1]")).isDisplayed()) {
					ListSel = PAXListView;
				}
			}
			if (driver.findElement(By.xpath("//div[@action='orderFqtvList']")).isDisplayed()) {
				//List<WebElement> OrderCnt = driver.findElements(By.xpath("//div[contains(@ng-repeat,'compOrderList.model.searchText')]"));
				List<WebElement> OrderCnt = driver.findElements(By.xpath(ListSel));
				if (OrderCnt.size()>0) {
					for (int i = 1; i <= OrderCnt.size(); i++) {
						if (driver.findElement(By.xpath(ListSel+"["+i+"]")).getText().trim().contains(Flight)) {
							SearchStatus = true;
							SelVal=i;
						}
					}
					if (SearchStatus) {
						driver.findElement(By.xpath("(//md-radio-button[@class='label-comp-checkbox'])["+SelVal+"]")).click();
						driver.findElement(By.xpath(ContinueBtn)).click();
						loadhandling(driver);
						if (driver.findElement(By.xpath(OrderDetailsPage)).isDisplayed()) {
							if (driver.findElement(By.xpath(OrderDetailsPage)).getText().trim().contains(SName+" / "+GName)) {
								queryObjects.logStatus(driver, Status.PASS, "Search by FQTV number", "Search is successful, Passenger details displayed",null);					
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Search by FQTV number", "Search is not successful, Passenger details are not displayed",null);
							}}
					}else if (Verification_Detail.equalsIgnoreCase("Negative FF")) {
						queryObjects.logStatus(driver, Status.PASS, "Other Airlines - Search by FQTV number", "Search is successful, Passenger details is not displayed",null);
					}
				}}

		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Search by FQTV number", "Search is not successful",e);
		}

	}


	public static void UpdateEMD(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException, Exception {
		String  Hotel_EMD = FlightSearch.getTrimTdata(queryObjects.getTestData("Hotel_EMD"));
		String  Meal_EMD = FlightSearch.getTrimTdata(queryObjects.getTestData("Meal_EMD"));
		String  Transport_EMD = FlightSearch.getTrimTdata(queryObjects.getTestData("Transportation_EMD"));



		Thread.sleep(1000);
	}


}
