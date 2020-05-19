package com.copa.RESscripts;

import java.io.IOException;
import java.rmi.server.LoaderHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.copa.ATOscripts.ISharesflow;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.Util.FlightSearchPageObjects;

import FrameworkCode.BFrameworkQueryObjects;

public class Unwholly extends FlightSearchPageObjects {

	static String changeDate = null;
	static String changeRoute = null;
	static String tempXpath = null;
	static String prevDate = null;
	static String newDate = null;
	static String newBRDpoint = null;
	static String newOFFpoint = null;
	static String prevClass = null;
	static String SameClass = null;
	static int Segmt=0;
	static String reissueAmount = null;
	static String reissueType = null;
	static String Balanceamt = null;
	String MultipleFOPType=null;
	String MultFOPsubType=null;
	String fopCardNums = null;
	static int totalTravellers;
	static List<Double> getpenamtamt=null;
	static boolean classselectbon;

	static List<WebElement> totSegments = null;
	public static String pnrfornrps;
	//static List<String> ticketsnum =null;

	static List<String> gettickets;
	static List<String> ExchangeTkt;
	public static List<String> ExchngTktamt= new ArrayList<>();
	public static List<String> aTicket= null;
	static List<String> gettReissuecaseEMD;
	//static List<String> gettecketamtExchangecase;
	static String ppspaymentmodeExchangecase=null;
	public static List<String> flightinfo=new ArrayList<>(); 
	public static String storeQuoteId = "";
	public static String DeleteSeg="";
	public static String AirlinesRefund = "";
	//String pnrNum;
	public void Unwholly(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {

		try {
			
			FlightSearch.MultipleFOPType="";
			FlightSearch.MultFOPsubType="";
			FlightSearch.fopCardNums="";
			pnrfornrps="";
			
			FlightSearch.TotalRefundamt=0.0;
			FlightSearch.PanaltyAmount=0.0;
			FlightSearch.Fare_Diff=0.0;
			
			boolean retval=getpnrinexcel(driver, queryObjects);
			
			ADDINF(driver, queryObjects);
			//Waiver Atul
			String waive= FlightSearch.getTrimTdata(queryObjects.getTestData("Quote_WaiverPassenger"));
			String Waive_Fee = FlightSearch.getTrimTdata(queryObjects.getTestData("Quote_Waiver_Fee"));
			String Tot_Pax = FlightSearch.getTrimTdata(queryObjects.getTestData("TotalPax"));  // need to check jenny
			String TaxCode=FlightSearch.getTrimTdata(queryObjects.getTestData("TaxCode"));
			String VerifyTaxCd = FlightSearch.getTrimTdata(queryObjects.getTestData("TaxCodeVerifiation"));
			String TaxPer = FlightSearch.getTrimTdata(queryObjects.getTestData("TaxPrcnt"));
			String ModPrice = FlightSearch.getTrimTdata(queryObjects.getTestData("ModifyPrice"));
			storeQuoteId=FlightSearch.getTrimTdata(queryObjects.getTestData("storeQuoteId"));  //if yes reissuce case Quote id will store in Remarks
			DeleteSeg = FlightSearch.getTrimTdata(queryObjects.getTestData("DeleteOldSeg"));	
			if (!Tot_Pax.isEmpty()) {
				FlightSearch.totalnoofPAX = Integer.parseInt(Tot_Pax);
			}
			else {
				List<WebElement> paxcount=driver.findElements(By.xpath("//div [@class='pax-info-result']"));		
				FlightSearch.totalnoofPAX=paxcount.size();
			}
			String Residual_Emd = FlightSearch.getTrimTdata(queryObjects.getTestData("Residual_EMD"));
			if (Residual_Emd.contains("Reissue")) {
				FlightSearch.Reissue_Residual(driver, queryObjects, Residual_Emd);
			}
			if (retval && FlightSearch.getTrimTdata(queryObjects.getTestData("Quote")).equalsIgnoreCase("yes")) {
				PNRsearch.reQuote(driver, queryObjects, waive, Waive_Fee,"","");
				
			}
			/*if(FlightSearch.getTrimTdata(queryObjects.getTestData("AddInfant")).equalsIgnoreCase("yes")) 
				Add_infant(driver, queryObjects);*/
			/*if (retval && FlightSearch.getTrimTdata(queryObjects.getTestData("SSRafterInfant")).equalsIgnoreCase("yes")) {
				if(FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRBeforePay")).equalsIgnoreCase("yes")) {
					String addSSRSpecificSegment=FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRSpecificSegment"));
					String totSSRs=queryObjects.getTestData("totSSRs");
					String ssrNames=queryObjects.getTestData("ssrNames");
					String After_Pay_addSSR_OR_Book_Case_addSSR= queryObjects.getTestData("SSRafterInfant_After_Pay_addSSR_OR_Book_Case_addSSR"); 

					FlightSearch ssr=new FlightSearch();					
					if (FlightSearch.getTrimTdata(queryObjects.getTestData("SpecificSSRsforSpecificPAXBefore")).equalsIgnoreCase("yes")) 
						ssr.addspecificSSR(driver,queryObjects);
					else //if(FlightSearch.getTrimTdata(queryObjects.getTestData("SpecificSSRforAllPAX")).equalsIgnoreCase("yes"))
						ssr.addSSR(driver,queryObjects,"No",addSSRSpecificSegment,totSSRs,ssrNames,After_Pay_addSSR_OR_Book_Case_addSSR);
					
					if(driver.findElements(By.xpath("//div[contains(@model,'shoppingCart.model.payment.balanceDue')]//div")).size()>0 && driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).isEnabled())
					{
					driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
					// Before payment amount checking....
					FlightSearch.loadhandling(driver);
					
					String Balanceamt=(driver.findElement(By.xpath("//div[@model='paymentCtrl.model.payment.balanceDue']/div")).getText().trim());
					
					double totalPaymentamt = Double.parseDouble(Balanceamt);
					FlightSearch flop=new FlightSearch();
					FlightSearch.MultipleFOPType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPType_SSR"));
					FlightSearch.MultFOPsubType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPSub_SSR"));
					FlightSearch.fopCardNums = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPCardNums_SSR"));
					String BankNames = queryObjects.getTestData("MultipleFOPBankName_SSR").trim();
					String InstallmentNums = queryObjects.getTestData("MultipleFOPInstallmentNum_SSR").trim();
					flop.MulFOP(driver,queryObjects,totalPaymentamt,FlightSearch.MultipleFOPType,FlightSearch.MultFOPsubType,FlightSearch.fopCardNums,BankNames,InstallmentNums,"issueTicket");
					WebDriverWait wait = new WebDriverWait(driver, 50);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Pay']")));
                    
                    driver.findElement(By.xpath("//button[text()='Pay']")).click();
                    //Handling Email recipients popup
                    FlightSearch.emailhandling(driver,queryObjects);
                    try {
                          wait = new WebDriverWait(driver, 120);
                          wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
                          String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
                          queryObjects.logStatus(driver, Status.PASS, " Payment", statusMessage, null);
                    }
                    catch(Exception e) {
                          queryObjects.logStatus(driver, Status.FAIL, " Payment", "Payment Unsuccessful: " + e.getMessage() , e);
                    }
                    
                    //Clicking on Done button
                    driver.findElement(By.xpath("//button[text()='Done']")).click();

					}
			}
			}*/
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("SecureFlightDoc")).equalsIgnoreCase("yes")) {
				FlightSearch secureflight=new FlightSearch();
				secureflight.SecureFlightInfo(driver,queryObjects);
			}
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("ChangeName")).equalsIgnoreCase("yes") && retval==true) {
				modifyName(driver, queryObjects);
			}
			
			//Atul - Adding emergency contact
			String EmerCont = FlightSearch.getTrimTdata(queryObjects.getTestData("EmergencyContact"));
			if (EmerCont.equalsIgnoreCase("yes")) {
				EmergencyContact(driver, queryObjects);
			}
			
			//Add Tax and Verify the same
			if (VerifyTaxCd.contains("SetTax")) {
				driver.findElement(By.xpath("//i[@class='icon-add']/..//span[@translate='pssgui.add.a.tax']")).click();
				driver.findElement(By.xpath("//input[@ng-model='TaxInfo.TaxCode']")).sendKeys(TaxCode);
				if (!TaxPer.isEmpty()) {
					driver.findElement(By.xpath("//input[@ng-model='service.Amount']")).sendKeys(TaxPer);
				}
				if (!ModPrice.isEmpty()) {
					driver.findElement(By.xpath("//input[@ng-model='TaxInfo.pricingOption']")).sendKeys(ModPrice);
				}
			}
			/*//if (retval && FlightSearch.getTrimTdata(queryObjects.getTestData("SSRbeforereissue")).equalsIgnoreCase("yes")) {
				if(FlightSearch.getTrimTdata(queryObjects.getTestData("SSRbeforereissue_addSSRBeforePay")).equalsIgnoreCase("yes")) {
					FlightSearch ssr=new FlightSearch();
					String addSSRSpecificSegment=FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRSpecificSegment"));
					String totSSRs=queryObjects.getTestData("totSSRs");
					String ssrNames=queryObjects.getTestData("ssrNames");
					String After_Pay_addSSR_OR_Book_Case_addSSR= queryObjects.getTestData("SSRbeforereissue_After_Pay_addSSR_OR_Book_Case_addSSR"); 

					if (FlightSearch.getTrimTdata(queryObjects.getTestData("SpecificSSRsforSpecificPAXBefore")).equalsIgnoreCase("yes")) 
						ssr.addspecificSSR(driver,queryObjects);
					else //if(FlightSearch.getTrimTdata(queryObjects.getTestData("SpecificSSRforAllPAX")).equalsIgnoreCase("yes"))
						ssr.addSSR(driver,queryObjects,"No",addSSRSpecificSegment,totSSRs,ssrNames,After_Pay_addSSR_OR_Book_Case_addSSR);
					driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
					// Before payment amount checking....
					FlightSearch.loadhandling(driver);
					//String fareAmount=driver.findElement(By.xpath(farepriceXpath)).getText().trim();
					//String totalamt=(driver.findElement(By.xpath("//div[@model='currency.total']/div")).getText().trim());
							
					String Balanceamt=(driver.findElement(By.xpath("//div[@model='paymentCtrl.model.payment.balanceDue']/div")).getText().trim());
					if (Double.parseDouble(Balanceamt)==Double.parseDouble(fareAmount)) {
						queryObjects.logStatus(driver, Status.PASS, "Quote amount and Balance amount echecking", "Quote amount showing  in Balance due balance amt is: "+Balanceamt, null);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL, "Quote amount and Balance amount echecking", "Quoter amount should show in Balance due actual: "+Balanceamt+" expected: "+fareAmount, null);
					}
					if (Double.parseDouble(totalamt)==Double.parseDouble(fareAmount)) {
						queryObjects.logStatus(driver, Status.PASS, "Quote amount and Total amount echecking", "Quote Total amount showing  correctly total amt is :"+totalamt, null);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL, "Quote amount and Total amount echecking", "Quote Totlal amount should show correctly actual: "+totalamt+" expected: "+fareAmount, null);
					}
					double totalPaymentamt = Double.parseDouble(Balanceamt);
					FlightSearch flop=new FlightSearch();
					FlightSearch.MultipleFOPType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPType_SSR"));
					FlightSearch.MultFOPsubType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPSub_SSR"));
					FlightSearch.fopCardNums = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPCardNums_SSR"));
					String MulCCV=FlightSearch.getTrimTdata(queryObjects.getTestData("MulCCV_SSR"));
					String BankNames = queryObjects.getTestData("MultipleFOPBankName_SSR").trim();
					String InstallmentNums = queryObjects.getTestData("MultipleFOPInstallmentNum_SSR").trim();
					flop.MulFOP(driver,queryObjects,totalPaymentamt,FlightSearch.MultipleFOPType,FlightSearch.MultFOPsubType,FlightSearch.fopCardNums,BankNames,InstallmentNums,"issueTicket");
					WebDriverWait wait = new WebDriverWait(driver, 50);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Pay']")));
                    
                    driver.findElement(By.xpath("//button[text()='Pay']")).click();
                    //Handling Email recipients popup
                    FlightSearch.emailhandling(driver,queryObjects);
                    try {
                          wait = new WebDriverWait(driver, 120);
                          wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
                          String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
                          queryObjects.logStatus(driver, Status.PASS, " Payment", statusMessage, null);
                    }
                    catch(Exception e) {
                          queryObjects.logStatus(driver, Status.FAIL, " Payment", "Payment Unsuccessful: " + e.getMessage() , e);
                    }
                    
                    //Clicking on Done button
                    driver.findElement(By.xpath("//button[text()='Done']")).click();

			}
			//}
*/			if (FlightSearch.getTrimTdata(queryObjects.getTestData("SplitPNR")).equalsIgnoreCase("yes")) {
				PNRsearch Split = new PNRsearch();
				//String oldPnr =Login.envRead(Login.PNRNUM).trim();
				String oldPnr =Login.PNRNUM.trim();
				Split.SplitBooking(driver,queryObjects,oldPnr);
			}
			String Reissue=FlightSearch.getTrimTdata(queryObjects.getTestData("Reissue"));
			if (Reissue.equalsIgnoreCase("yes") && retval==true) {
				classselectbon=false;
				reissue(driver,queryObjects);
				String addSSRSpecificSegment=FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRSpecificSegment"));
				String totSSRs=queryObjects.getTestData("totSSRs");
				String ssrNames=queryObjects.getTestData("ssrNames");
				String After_Pay_addSSR_OR_Book_Case_addSSR= queryObjects.getTestData("After_Pay_addSSR_OR_Book_Case_addSSR"); 

				FlightSearch sr = new FlightSearch();//srini
				if (queryObjects.getTestData("addSSRAfterPay").equalsIgnoreCase("yes")) {
					if (queryObjects.getTestData("SpecificSSRsforSpecificPAXAfter").equalsIgnoreCase("yes")) 
						FlightSearch.addspecificSSR(driver,queryObjects);
					else
						sr.addSSR(driver, queryObjects,"",addSSRSpecificSegment,totSSRs,ssrNames,After_Pay_addSSR_OR_Book_Case_addSSR);
					
					if (queryObjects.getTestData("SpecificSSRforAllPAX").equalsIgnoreCase("yes"))
						sr.addSSR(driver, queryObjects,"",addSSRSpecificSegment,totSSRs,ssrNames,After_Pay_addSSR_OR_Book_Case_addSSR);
					
					//Navira 4MAR
					String confirmation_res = FlightSearch.getTrimTdata(queryObjects.getTestData("Confirmation_resissue"));
					if(confirmation_res.equalsIgnoreCase("yes")) {
						FlightSearch fs = new FlightSearch();
						fs.resConfirmationwopayment(driver,queryObjects);
					}
					//String saddSSRafterpay=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
					String saddSSRpaychek="";
					if(driver.findElements(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).size()>0)
						saddSSRpaychek=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
					Double iaddSSRgetAMT = 0.0;
					if(!saddSSRpaychek.isEmpty())
						iaddSSRgetAMT=Double.parseDouble(saddSSRpaychek);
					if(iaddSSRgetAMT>0)
					{
						if (driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).isEnabled()) {
							driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click(); // 29 march
							FlightSearch.loadhandling(driver);
							FlightSearch.CheckforError(driver,queryObjects);
							String sError = FlightSearch.getErrorMSGfromAppliction(driver,queryObjects);

							//check if SSR not available

							if (!sError.contains("SSR NOT AVAILABLE")) {
								sr.MultipleFOPType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPType_SSR"));
								sr.MultFOPsubType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPSub_SSR"));
								sr.fopCardNums = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPCardNums_SSR"));
								String BankNames = queryObjects.getTestData("MultipleFOPBankName_SSR").trim();
								String InstallmentNums = queryObjects.getTestData("MultipleFOPInstallmentNum_SSR").trim();
								sr.MulFOP(driver, queryObjects, Double.parseDouble(sr.ssrAmount), sr.MultipleFOPType, sr.MultFOPsubType, sr.fopCardNums, BankNames, InstallmentNums, "SSRCASE");
								
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//button[text()='Pay' and not(@disabled='disabled')]")).click();
								FlightSearch.loadhandling(driver);
								FlightSearch.enterFoiddetails(driver,queryObjects);	
								FlightSearch.emailhandling(driver,queryObjects);
								Thread.sleep(2000);
								driver.findElement(By.xpath("//button[text()='Done']")).click();
								
							}
							else
								driver.findElement(By.xpath("//button[text()='Cancel']")).click();
							FlightSearch.loadhandling(driver);
						}
					}
				}
				
			}
			String Refund=FlightSearch.getTrimTdata(queryObjects.getTestData("Refund"));
			if (Refund.equalsIgnoreCase("yes") && retval==true) {
				FlightSearch.refundticket(driver, queryObjects);
			}
			
			//below commented code is duplicate 
			//if any issue need check  meenu
			/*String assignSeat = FlightSearch.getTrimTdata(queryObjects.getTestData("AssignSeat"));
			if(assignSeat.equalsIgnoreCase("yes")) {
				String AssignseatAllPAX = FlightSearch.getTrimTdata(queryObjects.getTestData("AssignseatAllPAX"));
				String AssignSeatSinglePAX = FlightSearch.getTrimTdata(queryObjects.getTestData("AssignSeatSinglePAX"));
				String AssignSpecSeat = FlightSearch.getTrimTdata(queryObjects.getTestData("AssignSpecSeat"));

				String totalTravels=driver.findElement(By.xpath(PaxCountXpath)).getText().trim();
				int totalTravers=Integer.parseInt(totalTravels);

				FlightSearch.assignSeat(driver, queryObjects, totalTravers, AssignseatAllPAX, AssignSeatSinglePAX, AssignSpecSeat);
				FlightSearch payseat=new FlightSearch();
				payseat.paymentAfterassignseat(driver, queryObjects);
			}*/
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Unwholly", "Unwholly Failed: " + e.getMessage(), e);
		}


	}

	public static boolean getpnrinexcel(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		String pnrNum =FlightSearch.getTrimTdata(queryObjects.getTestData("PNRis"));
		String emdNum = FlightSearch.getTrimTdata(queryObjects.getTestData("EMDis"));
		boolean contflag=false;
		pnrfornrps=pnrNum;
		
		//Krishna - Get PNR From Login.PNRNUM if its an Share PNR is YES and PNR is Not Present in the Test Data Sheet
		if(FlightSearch.getTrimTdata(queryObjects.getTestData("Share_PNR")).equalsIgnoreCase("YES") && pnrNum.isEmpty()) {
			//	Login.PNRNUM=SharesPNR;
			pnrNum = ISharesflow.SharesPNR;
			/*if (pnrNum.equalsIgnoreCase("SharesPNR")) {
				pnrNum = ISharesflow.SharesPNR;
			} 
			else
				Login.PNRNUM=pnrNum; //Navira need to check
				
*/		}
		else    // this is for given only pnr in unhowlly
			Login.PNRNUM=pnrNum;
		
		if(pnrNum.equals(""))
			if(!emdNum.equalsIgnoreCase(""))//Modified by Navira
                queryObjects.logStatus(driver, Status.INFO, "PNR was not exist", "Test data issue" , null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "PNR was not exist", "Test data issue" , null);
		else
			contflag=true;	
		if(!emdNum.isEmpty()){
			if(!Login.EMDNO.isEmpty()){
				String append=Login.EMDNO;
				append=append+";";
				Login.EMDNO= append+emdNum ;  //Navira
			}	
			else
				Login.EMDNO=emdNum;
			String Search_EMD = FlightSearch.getTrimTdata(queryObjects.getTestData("SearchEMD"));
		    if(Search_EMD.equalsIgnoreCase("yes")) 
		    	Search_EMD(driver, queryObjects, emdNum);
		}
		//EMD Details Verification - Navira
		
		String EMDVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("EMDVerify"));
		if(EMDVerify.equalsIgnoreCase("yes")) {//Code available only  for one EMD 
			EMDVerifi(driver, queryObjects);
		}
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
				driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(pnrNum);

				//Clicking on Search button
				driver.findElement(By.xpath("//div[contains(@class,'itinerary-search')]//button[contains(text(),'Search')]")).click();
				FlightSearch.loadhandling(driver);
				String Pnris=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@action='pnr']/div/div/div[1]/div[1]")).getText());
				//Wait until loading wrapper closed


				queryObjects.logStatus(driver, Status.PASS, "Search PNR", "Search PNR successfully" , null);

			}
			catch(Exception e) {
				contflag = false;
				queryObjects.logStatus(driver, Status.FAIL, "Search PNR", "Search PNR failed: " + e.getLocalizedMessage() , e);
			}
		}
		return contflag;
	}



	public static void reissue(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		FlightSearch.Pnrstate="EXCHANGED";
		String Review_Reject = FlightSearch.getTrimTdata(queryObjects.getTestData("Review_Reject"));
		String Biller = FlightSearch.getTrimTdata(queryObjects.getTestData("BillerDetails"));
		String errormsgchec = "";
		if(! Login.CardDeposit_Change_Salesoffice.isEmpty())   // card Deposit case change the POS
			PNRsearch.ChangeSalesOffice(driver,queryObjects,Login.CardDeposit_Change_Salesoffice,Login.Cur);
		
		/*String FirstflightforAto=driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]//tbody[1]//child::td[@class='flight-name']/span")).getText();
		Login.envwrite(Login.shareflightnm, FirstflightforAto);*/
		if (FlightSearch.getTrimTdata(queryObjects.getTestData("TaxCodeVerifiation")).equalsIgnoreCase("yes")) {
			driver.findElement(By.xpath("//div[div[text()='Tickets']]")).click();
			Thread.sleep(1000);
			List<WebElement> getetkts=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]"));
			String xpathis;  //="//tr[@ng-if='orderTableDisplay.tableData.taxes']/td[@ng-if='orderTableDisplay.tableData.taxes']/div/div[1]";
			String tcketno;
			for (int iticket = 0; iticket < getetkts.size(); iticket++) {
				getetkts=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]"));
				Thread.sleep(1000);
				tcketno=getetkts.get(iticket).getText();
				getetkts.get(iticket).click();
				FlightSearch.loadhandling(driver);
				xpathis="//span[contains(@class,'pssgui-link primary-ticket-number') and contains(text(),'"+tcketno+"')]/ancestor::div[contains(@ng-repeat,'tktDetailCtrl.passenger.PrimaryTickets')]//tr[contains(@ng-if,'orderTableDisplay.tableData.taxes')]/td[2]";
				queryObjects.logStatus(driver, Status.PASS, "Tax Code verification for "+(iticket+1)+" pax", "Tax Code verification for "+(iticket+1)+" PAX", null);
				PNRsearch.TaxCodeValidation(driver,queryObjects,xpathis,"Ticket Tab");  // tax code verification
				driver.findElement(By.xpath("//span[contains(text(),'All Passengers')]")).click();
				FlightSearch.loadhandling(driver);
			}
		}
		String Original_Currency=GetOriginalcurrency(driver,queryObjects);   // this is for get original currecny for CR189
		changeDate = FlightSearch.getTrimTdata(queryObjects.getTestData("changeDate"));
		String changeClassonly = FlightSearch.getTrimTdata(queryObjects.getTestData("changeClass"));
		changeRoute = FlightSearch.getTrimTdata(queryObjects.getTestData("changeRoute"));
		SameClass = FlightSearch.getTrimTdata(queryObjects.getTestData("SameClass"));

		reissueType = FlightSearch.getTrimTdata(queryObjects.getTestData("ReissueType"));

		//Temporary Xpath for 1st unused PNR segment
		tempXpath = "//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]][1]";
		driver.findElement(By.xpath("//div[div[text()='Order']]")).click();
		FlightSearch.loadhandling(driver);

		int segment=driver.findElements(By.xpath("//tbody[@ng-repeat='flight in flightResult.segments']")).size();
		//Get the previous date
		if(driver.findElements(By.xpath(tempXpath + "//td[@class='date']//span")).size()>0)
			prevDate = driver.findElement(By.xpath(tempXpath + "//td[@class='date']//span")).getText().trim();
		
		//Get the previous class
		if(driver.findElements(By.xpath(tempXpath + "//td[@class='flight-class']//span")).size()>0)
			prevClass = driver.findElement(By.xpath(tempXpath + "//td[@class='flight-class']//span")).getText().trim();
		
		String getflghtnm="";
		if(driver.findElements(By.xpath(tempXpath + "//td[@class='flight-name']/span")).size()>0)
			getflghtnm=driver.findElement(By.xpath(tempXpath + "//td[@class='flight-name']/span")).getText().trim();
		
		// Penalty checking ..... Created pnr Q and K class panality should display
		List<WebElement> classcheck=driver.findElements(By.xpath("//td[@class='flight-class']//span"));
		ArrayList getClasscheck = new ArrayList<>();
		classcheck.forEach(a -> getClasscheck.add(a.getText().trim().toUpperCase()));

		if(changeDate.equalsIgnoreCase("yes") && !changeRoute.equalsIgnoreCase("yes")) {
			List<WebElement> editdate=new ArrayList<>();
			String sNthsegment=FlightSearch.getTrimTdata(queryObjects.getTestData("changeclass_OR_Date_Nth_Segment"));
			String xpathofpointtodelet="//table[contains(@ng-table,'flightResult')]//tbody[tr[td[div[span[i[@class='icon-removed']]]]]]";


			//Add two days to the existing date
			Calendar cal1 = Calendar.getInstance();

			SimpleDateFormat sdf = new  SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdf2 = new  SimpleDateFormat("dd-MMM-yyyy");

			cal1.setTime(sdf2.parse(prevDate));
			String days=FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
			int tmpdate=0;
			if(days.equalsIgnoreCase(""))
			{
				tmpdate=Integer.parseInt("2");
				cal1.add(Calendar.DATE, tmpdate);

			}
			else
			{
				tmpdate=Integer.parseInt(days);
				cal1.add(Calendar.DATE, tmpdate);	
			}

			newDate = sdf.format(cal1.getTime());
			//newDate = sdf.format(sdf2.parse(newDate));

			if (!sNthsegment.isEmpty()) {
				WebElement segChkBoxa ;
				List<WebElement> lDeleticons=new ArrayList<>();
				lDeleticons=driver.findElements(By.xpath(xpathofpointtodelet));
				if(sNthsegment.equalsIgnoreCase("all")){
					for (int changeclss = 0; changeclss < lDeleticons.size(); changeclss++) {
						int val=changeclss+1;
						//20Mar - Navira
						if(driver.findElements(By.xpath(xpathofpointtodelet+"["+val+"]" +"//td[contains(text(),'ARNK')]")).size()>0) {
							continue;
						}
						segChkBoxa = driver.findElement(By.xpath(xpathofpointtodelet+"["+val+"]" + "//md-checkbox"));
						if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
							segChkBoxa.click();
						driver.findElement(By.xpath(xpathofpointtodelet+"["+val+"]//td[@class='date']//span")).click();
						Thread.sleep(2000);
						editdate=driver.findElements(By.xpath("//input[@class='md-datepicker-input']"));
						for (int iedit = 0; iedit < editdate.size(); iedit++) {
							//int vla=iedit+1;
							editdate.get(iedit).click();
							editdate.get(iedit).clear();
							editdate.get(iedit).sendKeys(newDate);
							cal1.add(Calendar.DATE, tmpdate+2);
							newDate = sdf.format(cal1.getTime());
							FlightSearch.loadhandling(driver);
						}
						driver.findElement(By.xpath("//span[@class='save']")).click();
						FlightSearch.loadhandling(driver);
					}
					segChkBoxa = driver.findElement(By.xpath("//md-checkbox[contains(@class,'segment-checkbox') and div[contains(@class,'md-ink-ripple')]]"));
					if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
						segChkBoxa.click();
				}
				else
				{
					//prevDate = driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]]["+sNthsegment+"]//td[@class='date']//span")).getText().trim();
					int intsNthsegment=Integer.parseInt(sNthsegment);
					segChkBoxa = driver.findElement(By.xpath(xpathofpointtodelet+"["+intsNthsegment+"]" + "//md-checkbox"));
					if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
						segChkBoxa.click();
					driver.findElement(By.xpath(xpathofpointtodelet+"["+intsNthsegment+"]//td[@class='date']//span")).click();
					Thread.sleep(2000);
					editdate=driver.findElements(By.xpath("//input[@class='md-datepicker-input']"));
					//int vla=0;
					for (int iedit = 0; iedit < editdate.size(); iedit++) {
						//int vla=iedit+1;
						editdate.get(iedit).click();
						editdate.get(iedit).clear();
						editdate.get(iedit).sendKeys(newDate);
						FlightSearch.loadhandling(driver);
						cal1.add(Calendar.DATE, tmpdate+2);
						newDate = sdf.format(cal1.getTime());
					}
					driver.findElement(By.xpath("//span[@class='save']")).click();
					FlightSearch.loadhandling(driver);

					try{
						List<WebElement>maridseg=driver.findElements(By.xpath("//table[contains(@ng-table,'flightResult')]//tbody[tr[td[div[div[2][div[span[2][starts-with(text(),'"+intsNthsegment+"')]]]]]]]//md-checkbox"));
						if (maridseg.size()>0) {
							for (int i = 0; i <maridseg.size(); i++) {
								maridseg.get(i).click();
							}
						}
						else{
							segChkBoxa = driver.findElement(By.xpath(xpathofpointtodelet+"["+intsNthsegment+"]" + "//md-checkbox"));
							if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
								segChkBoxa.click();
						}

					}
					catch (Exception e) { 
						segChkBoxa = driver.findElement(By.xpath(xpathofpointtodelet+"["+intsNthsegment+"]" + "//md-checkbox"));
						if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
							segChkBoxa.click(); }
				}
				classselectbon=true;
			}
			else{
				WebElement segChkBox = driver.findElement(By.xpath(tempXpath + "//md-checkbox"));
				if(segChkBox.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
					segChkBox.click();			
				driver.findElement(By.xpath(tempXpath + "//td[@class='date']//span")).click();
				editdate=driver.findElements(By.xpath("//input[@class='md-datepicker-input']"));
				for (int iedit = 0; iedit < editdate.size(); iedit++) {
					int vla=iedit+1;
					String datxpath="//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]]["+vla+"]//input[@class='md-datepicker-input']";
					driver.findElement(By.xpath(datxpath)).click();
					driver.findElement(By.xpath(datxpath)).clear();
					driver.findElement(By.xpath(datxpath)).sendKeys(newDate);
				}
				//Click on save button
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath(tempXpath + "//span[@class='save']")).click();
				Thread.sleep(2000);
				//Wait until loading wrapper closed
				FlightSearch.loadhandling(driver);
				//Navira (for Search by E-tkt Number - After loading, lways goes to Tickets Tab
				driver.findElement(By.xpath("//div[text()='Order']")).click();
				FlightSearch.loadhandling(driver);
				segChkBox = driver.findElement(By.xpath(tempXpath + "//md-checkbox"));
				if(segChkBox.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
					segChkBox.click();
				classselectbon=true;
			}

		}
		if(changeClassonly.equalsIgnoreCase("yes") && !changeRoute.equalsIgnoreCase("yes")) {
			List<WebElement> editclas=new ArrayList<>();
			String changeclassNM= FlightSearch.getTrimTdata(queryObjects.getTestData("changeclassNM")).toUpperCase();
			String sNthsegment=FlightSearch.getTrimTdata(queryObjects.getTestData("changeclass_OR_Date_Nth_Segment"));

			String xpathofpointtodelet="//table[contains(@ng-table,'flightResult')]//tbody[tr[td[div[span[i[@class='icon-removed']]]]]]";
			if (!sNthsegment.isEmpty()) {
				WebElement segChkBoxa ;
				List<WebElement> lDeleticons=new ArrayList<>();
				lDeleticons=driver.findElements(By.xpath(xpathofpointtodelet));
				if(sNthsegment.equalsIgnoreCase("all")){
					for (int changeclss = 0; changeclss < lDeleticons.size(); changeclss++) {
						int val=changeclss+1;
						//20Mar - Navira
						if(driver.findElements(By.xpath(xpathofpointtodelet+"["+val+"]" +"//td[contains(text(),'ARNK')]")).size()>0) {
							continue;
						}
						segChkBoxa = driver.findElement(By.xpath(xpathofpointtodelet+"["+val+"]" + "//md-checkbox"));
						if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
							segChkBoxa.click();
						driver.findElement(By.xpath(xpathofpointtodelet+"["+val+"]/tr/td[@class='flight-class']//span")).click();
						editclas=driver.findElements(By.xpath("//tr/td[@class='flight-class']//span//div"));
						for (int iedit = 0; iedit < editclas.size(); iedit++) {
							//int vla=iedit+1;
							editclas.get(iedit).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//div[@aria-hidden='false']//md-option[@value='"+changeclassNM+"']")).click();
							FlightSearch.loadhandling(driver);
						}
						driver.findElement(By.xpath("//span[@class='save']")).click();
						FlightSearch.loadhandling(driver);
					}
					segChkBoxa = driver.findElement(By.xpath("//md-checkbox[contains(@class,'segment-checkbox') and div[contains(@class,'md-ink-ripple')]]"));
					if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
						segChkBoxa.click();
				}
				else
				{
					int intsNthsegment=Integer.parseInt(sNthsegment);
					segChkBoxa = driver.findElement(By.xpath(xpathofpointtodelet+"["+intsNthsegment+"]" + "//md-checkbox"));
					if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
						segChkBoxa.click();
					driver.findElement(By.xpath(xpathofpointtodelet+"["+intsNthsegment+"]/tr/td[@class='flight-class']//span")).click();
					editclas=driver.findElements(By.xpath("//tr/td[@class='flight-class']//span//div"));
					//int vla=0;
					for (int iedit = 0; iedit < editclas.size(); iedit++) {
						//vla=iedit+1;
						editclas.get(iedit).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//div[@aria-hidden='false']//md-option[@value='"+changeclassNM+"']")).click();
						FlightSearch.loadhandling(driver);
					}
					driver.findElement(By.xpath("//span[@class='save']")).click();
					FlightSearch.loadhandling(driver);

					try{
						List<WebElement>maridseg=driver.findElements(By.xpath("//table[contains(@ng-table,'flightResult')]//tbody[tr[td[div[div[2][div[span[2][starts-with(text(),'"+intsNthsegment+"')]]]]]]]//md-checkbox"));
						if (maridseg.size()>0) {
							for (int i = 0; i <maridseg.size(); i++) {
								maridseg.get(i).click();
							}
						}
						else{
							segChkBoxa = driver.findElement(By.xpath(xpathofpointtodelet+"["+intsNthsegment+"]" + "//md-checkbox"));
							if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
								segChkBoxa.click();
						}

					}
					catch (Exception e) { 
						segChkBoxa = driver.findElement(By.xpath(xpathofpointtodelet+"["+intsNthsegment+"]" + "//md-checkbox"));
						if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
							segChkBoxa.click(); }
				}
				classselectbon=true;
			}
			//esle-----
			else{
				WebElement segChkBox = driver.findElement(By.xpath(tempXpath + "//md-checkbox"));
				if(segChkBox.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
					segChkBox.click();
				driver.findElement(By.xpath(tempXpath + "/tr/td[@class='flight-class']//span")).click();
				//FlightSearch.loadhandling(driver);
				editclas=driver.findElements(By.xpath("//input[@class='md-datepicker-input']"));
				for (int iedit = 0; iedit < editclas.size(); iedit++) {
					int vla=iedit+1;
					driver.findElement(By.xpath( "//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]]["+vla+"]/tr/td[@class='flight-class']//span//div")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//div[@aria-hidden='false']//md-option[@value='"+changeclassNM+"']")).click();
					FlightSearch.loadhandling(driver);
				}
				//Click on save button
				driver.findElement(By.xpath(tempXpath + "//span[@class='save']")).click();
				Thread.sleep(2000);
				//Wait until loading wrapper closed
				FlightSearch.loadhandling(driver);
				segChkBox = driver.findElement(By.xpath(tempXpath + "//md-checkbox"));
				if(segChkBox.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
					segChkBox.click();
				classselectbon=true;
			}
		}

		if(changeRoute.equalsIgnoreCase("yes")) {
			String k="";
			String s=k.toUpperCase();
			//Get the previous date
			//String lltempXpath = "//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]]["++"]";
			if(driver.findElements(By.xpath(tempXpath + "//td[@class='date']//span")).size()>0)
				prevDate = driver.findElement(By.xpath(tempXpath + "//td[@class='date']//span")).getText().trim();
			else{
				Calendar cal2nowshow = Calendar.getInstance();
				SimpleDateFormat sdf2nowshow = new  SimpleDateFormat("dd-MMM-yyyy");
				cal2nowshow.add(Calendar.DATE, 35);
				prevDate=sdf2nowshow.format(cal2nowshow.getTime());
			}
			newBRDpoint = FlightSearch.getTrimTdata(queryObjects.getTestData("newBRDpoint")).toUpperCase();
			newOFFpoint = FlightSearch.getTrimTdata(queryObjects.getTestData("newOFFpoint")).toUpperCase();
			totalTravellers = Integer.parseInt(driver.findElement(By.xpath(PaxCountXpath)).getText().trim());
			String samerootdifffligh = FlightSearch.getTrimTdata(queryObjects.getTestData("sameroot_But_dif_fligh"));
			//prevClass = driver.findElement(By.xpath(tempXpath + "//td[@class='flight-class']//span")).getText().trim();
			String RoundRetDate=null;
			String roundtrip=FlightSearch.getTrimTdata(queryObjects.getTestData("RoundTrip"));
			if(roundtrip.equalsIgnoreCase("Yes"))
				RoundRetDate=driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]/tbody[2]/tr/td[3]/div")).getText();

			if(driver.findElements(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]/tbody[1]/tr/td[2]/span")).size()>0)
				flightinfo.add(driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]/tbody[1]/tr/td[2]/span")).getText());

			if(roundtrip.equalsIgnoreCase("Yes"))
				flightinfo.add(driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]/tbody[2]/tr/td[2]/span")).getText());

			DeleteOldSegments(driver, queryObjects,DeleteSeg);   
			//Expand Availability Grid if it is not alreday opened
			List<WebElement> no=driver.findElements(By.xpath("//tbody[@ng-repeat='flight in flightResult.segments']"));
			Segmt=no.size(); 

			//Navira (for Search by E-tkt Number - After loading, lways goes to Tickets Tab
			driver.findElement(By.xpath("//div[text()='Order']")).click();
			FlightSearch.loadhandling(driver);
			
			WebDriverWait wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[toggle-title[text()='Availability']]/preceding-sibling::i")));
			WebElement availExpand = driver.findElement(By.xpath("//div[toggle-title[text()='Availability']]/preceding-sibling::i"));
			if(availExpand.getAttribute("class").contains("icon-forward"))
				availExpand.click();

			//Enter new board point in the text box
			driver.findElement(By.xpath("//input[@name='origin']")).click();
			driver.findElement(By.xpath("//input[@name='origin']")).clear();
			driver.findElement(By.xpath("//input[@name='origin']")).sendKeys(newBRDpoint);
			Thread.sleep(2000);
			//driver.findElement(By.xpath("//input[@name='origin']")).sendKeys(Keys.TAB);
			driver.findElement(By.xpath(clickUSpopuXpath)).click();
			//Select new board point from the result list
			//driver.findElement(By.xpath("//md-virtual-repeat-container[@aria-hidden='false']//li[md-autocomplete-parent-scope[span[span[span[text()='"+newBRDpoint+"']]]]]")).click();

			//Selecting new destination
			driver.findElement(By.xpath("//input[@name='destination']")).click();
			driver.findElement(By.xpath("//input[@name='destination']")).clear();
			driver.findElement(By.xpath("//input[@name='destination']")).sendKeys(newOFFpoint);
			//select new destination from selection list
			Thread.sleep(2000);
			//driver.findElement(By.xpath("//input[@name='destination']")).sendKeys(Keys.TAB);
			driver.findElement(By.xpath(clickUSpopuXpath)).click();
			//driver.findElement(By.xpath("//md-virtual-repeat-container[@aria-hidden='false']//li[md-autocomplete-parent-scope[span[span[span[text()='"+newOFFpoint+"']]]]]")).click();

			//Entering segment Date
			String sgetDate=FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
			int getDays=0;
			if (!sgetDate.isEmpty())
				getDays=Integer.parseInt(sgetDate);
			else
				getDays=30;

			Calendar calc = Calendar.getInstance();
			//calc.add(Calendar.DATE, +getDays);
			String timeStampd = new SimpleDateFormat("MM/dd/yyyy").format(calc.getTime());
			Calendar cal2 = Calendar.getInstance();
			//Add two days to the existing date
			SimpleDateFormat sdf = new  SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdf2 = new  SimpleDateFormat("dd-MMM-yyyy");

			cal2.setTime(sdf2.parse(prevDate));
			if(changeDate.equalsIgnoreCase("yes")) {
				if (!sgetDate.isEmpty()) 
					//cal2.add(Calendar.DATE, getDays+4);
					cal2.add(Calendar.DATE, getDays+4);
				else
					cal2.add(Calendar.DATE, 5);

				newDate = sdf.format(cal2.getTime());	
			}
			else {
				newDate = sdf.format(cal2.getTime());
			}
			//Clear old date and add two days to the existing date
			driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).click();
			driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).clear();

			//Enter new date
			driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(newDate);
			Thread.sleep(1000);
			String sRoundTrip=FlightSearch.getTrimTdata(queryObjects.getTestData("RoundTrip"));
			if (sRoundTrip.equalsIgnoreCase("yes") && (FlightSearch.getTrimTdata(samerootdifffligh).equalsIgnoreCase("yes")) ){



				driver.findElement(By.xpath(RoundtripXpath)).click();  
				Calendar retDate = Calendar.getInstance();
				retDate.setTime(sdf2.parse(prevDate));
				retDate.add(Calendar.DATE, (getDays+8));
				if(!sgetDate.equalsIgnoreCase("")) 
				{ 
					String timeStamp1retDate = new SimpleDateFormat("MM/dd/yyyy").format(retDate.getTime());      
					driver.findElement(By.xpath(DestinationSecondXpath)).clear();
					Thread.sleep(1500);
					driver.findElement(By.xpath(DestinationSecondXpath)).sendKeys(timeStamp1retDate);
					Thread.sleep(1500);
					//driver.findElement(By.xpath(DestinationSecondXpath)).sendKeys(Keys.TAB);
					driver.findElement(By.xpath(clickUSpopuXpath)).click();
				} 
				else
				{   //String OldReturnDate=driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]/tbody[2]/tr/td[3]/div")).getText();
					driver.findElement(By.xpath(DestinationSecondXpath)).clear();
					driver.findElement(By.xpath(DestinationSecondXpath)).sendKeys(RoundRetDate);	
				}								

			} 
			else if(sRoundTrip.equalsIgnoreCase("yes"))
			{   
				driver.findElement(By.xpath("//div[@ng-form='availabilityForm']/div[1]/div/div[1]/div[2]/md-checkbox/div[1]")).click();	  
				Calendar retDate = Calendar.getInstance();
				retDate.setTime(sdf2.parse(prevDate));
				retDate.add(Calendar.DATE, (getDays+8));
				if(!sgetDate.equalsIgnoreCase("")) 
				{ 
					String timeStamp1retDate = new SimpleDateFormat("MM/dd/yyyy").format(retDate.getTime());      
					driver.findElement(By.xpath(DestinationSecondXpath)).clear();
					driver.findElement(By.xpath(DestinationSecondXpath)).sendKeys(timeStamp1retDate);
				}
				else
				{   
					Calendar retDate1 = Calendar.getInstance();
					retDate1.setTime(sdf2.parse(RoundRetDate)); 
					//retDate1.add(Calendar.DATE, (getDays)+3);
					retDate1.add(Calendar.DATE, 3);
					String timeStamp1retDate1 = new SimpleDateFormat("MM/dd/yyyy").format(retDate1.getTime());
					driver.findElement(By.xpath(DestinationSecondXpath)).clear();
					driver.findElement(By.xpath(DestinationSecondXpath)).sendKeys(timeStamp1retDate1); 
					//String OldReturnDate=driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]/tbody[2]/tr/td[3]/div")).getText();
					driver.findElement(By.xpath(DestinationSecondXpath)).clear();
					driver.findElement(By.xpath(DestinationSecondXpath)).sendKeys(timeStamp1retDate1);
					FlightSearch.loadhandling(driver);
				}

			} 
			//Navira 04Mar - Time Start
			String sgettime=FlightSearch.getTrimTdata(queryObjects.getTestData("Time"));
			if (sgettime.isEmpty())
				sgettime="Any";

			driver.findElement(By.xpath(TimeXpath)).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-clickable')]//div[contains(text(),'"+sgettime+"')]/parent::md-option")).click();
			//End
			String Availability_Type=FlightSearch.getTrimTdata(queryObjects.getTestData("Availability_Type"));
			if (!Availability_Type.isEmpty()) {  // if it is not empty select particual option..
				driver.findElement(By.xpath("//md-select[@aria-label='Availability type']")).click();
				Thread.sleep(2000);
				if (Availability_Type.equals("Redemption availability")) 
					driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false' ]//child::md-option[div[contains(text(),'Redemption availability')]]")).click();
				else if (Availability_Type.equals("Alliance availability"))
					driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false' ]//child::md-option[div[contains(text(),'Alliance availability')]]")).click();
				else if (Availability_Type.equals("Carrier specific availability"))
					driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false' ]//child::md-option[div[contains(text(),'Carrier specific availability')]]")).click();
				else if (Availability_Type.equals("Any availability"))
					driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false' ]//child::md-option[div[contains(text(),'Any availability')]]")).click();
				else
					queryObjects.logStatus(driver, Status.FAIL, "Flight search Availability type checking", "Test Data/script issue Given value not available", null);	
			}
			//md-select[@aria-label='Availability type']
			//Click on Search button
			driver.findElement(By.xpath("//button[@aria-label='Search']")).click();

			FlightSearch.loadhandling(driver);
			FlightSearch.loadhandling(driver);
			//Wait until Search results loaded
			//WebDriverWait wait1 = new WebDriverWait(driver, 90);
			//wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(viewXpath)));
			// this list is Number of Trips (PTY-SJO , SJO-PTY)
			java.util.List<WebElement> trips=driver.findElements(By.xpath(numberoftripsXpath));
			int nuberofTrips=trips.size();

			Boolean classselectbon=false;
			for (int tripiter = 0; tripiter < nuberofTrips; tripiter++) {
				trips.get(tripiter).click();
				FlightSearch.loadhandling(driver);
				//This list is View click ...
				List<WebElement> lview=driver.findElements(By.xpath(viewXpath));
				int nuberofviews=lview.size();	
				//boolean getflight=false;
				classselectbon=false;
				for (int iviewsiter = 0; iviewsiter <nuberofviews; iviewsiter++) {
					lview.get(iviewsiter).click();
					FlightSearch.loadhandling(driver);

					//String sgetflighno=lallflightno.get(iviewsiter).getText();
					String flightno=driver.findElement(By.xpath(lselectedflightnoXpath)).getText().trim();
					List<WebElement> lselectedflightno=driver.findElements(By.xpath(lselectedflightnoXpath));
					int viewnuberoflflightnoin;
					viewnuberoflflightnoin=lselectedflightno.size();

					if (samerootdifffligh.equalsIgnoreCase("yes") && tripiter == 0) {  // Same root but different Flight case
						if (!flightno.equalsIgnoreCase(getflghtnm) &&(!sRoundTrip.equalsIgnoreCase("yes"))) {

							classselectbon=flightsearching(driver,viewnuberoflflightnoin,queryObjects);
						}
						if (!flightno.equalsIgnoreCase(flightinfo.get(tripiter)) && sRoundTrip.equalsIgnoreCase("yes")) {
							classselectbon=flightsearching(driver,viewnuberoflflightnoin,queryObjects);
						} 


					}
					else
						classselectbon=flightsearching(driver,viewnuberoflflightnoin,queryObjects);

					if (classselectbon==true) {
						break;
					}
					lview.get(iviewsiter).click();
					FlightSearch.loadhandling(driver);	
				}
			}

			//Get the no.of flights selected and enter segment numbers starting from 1
			if (classselectbon==false)
				queryObjects.logStatus(driver, Status.FAIL, "Reissue case Flight search", "Flight Not available", null);
			else{
				totSegments = driver.findElements(By.xpath("//div[@action='availability-flight-selected']//div[@ng-repeat='flight in segment.Legs']//input[@name='flight-order']"));
				//int segmNum = 1;
				//int segmNum = (driver.findElements(By.xpath("//i[@class='icon-weblink']")).size()) + 1; // need to check in all cases ...
				int segmNum = (driver.findElements(By.xpath("//td[@class='date'] //span[contains(@ng-click, 'date')]")).size()) + 1;
				for(WebElement seg: totSegments) {
					seg.click();
					seg.clear();
					seg.sendKeys(String.valueOf(segmNum));
					segmNum++;
				}
				//Book new segment
				driver.findElement(By.xpath("//md-select[@aria-label='Actions']")).click();

				Thread.sleep(2000);
				//select book option
				driver.findElement(By.xpath("//md-option[div[contains(text(),'Book')] and @value='order-book']")).click();
				FlightSearch.loadhandling(driver);
				/*//Selecting Quote Option
				driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false']//md-option[div[contains(text(),'Quote')]]")).click();
				FlightSearch.loadhandling(driver);*///Jenny
				/*List<WebElement> segno=new ArrayList<WebElement>();
				segno=driver.findElements(By.xpath("//md-input-container[@class='order-no']/input"));

				for(int i=0;i<segno.size();i++)
				{
					segno.get(i).sendKeys(Integer.toString(++Segmt));
				}*/
				String spec_segno=FlightSearch.getTrimTdata(queryObjects.getTestData("specSegno"));
				if (!spec_segno.isEmpty())//meenu_keep all to let all the segments 
				{
					if(!spec_segno.contains(";")) {
						spec_segno=spec_segno+";";
					}
					String[] aspec_segno=spec_segno.split(";");
					List<WebElement> segno=new ArrayList<WebElement>();
					segno=driver.findElements(By.xpath("//md-input-container[@class='order-no']/input"));

					for(int i=0;i<segno.size();i++)
					{
						if(spec_segno!="") {
							segno.get(i).sendKeys(aspec_segno[i]);
						}
						else {
							segno.get(i).sendKeys(Integer.toString(++Segmt));
						}
					}
				}
				
				
				//Selecting all segments check box
				driver.findElement(By.xpath("//md-checkbox[@aria-label='coupon-check']")).click();

				driver.findElement(By.xpath("//button[contains(text(),'Book')]")).click();
				FlightSearch.loadhandling(driver);
				/*for(int i=1;i<=totSegments.size();i++) {
						driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]]["+i+"]//td[@class='checkbox']//md-checkbox")).click();
						Thread.sleep(500);
					}*/
				//Navira (for Search by E-tkt Number - After loading, lways goes to Tickets Tab
				driver.findElement(By.xpath("//div[text()='Order']")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//md-checkbox[contains(@class,'segment-checkbox')]")).click();

			}
		}
		//Himani 20April
			String ARNK_Before=FlightSearch.getTrimTdata(queryObjects.getTestData("ARNK Before"));
			String ARNK_After=FlightSearch.getTrimTdata(queryObjects.getTestData("ARNK After"));
			if(ARNK_Before.equalsIgnoreCase("yes")||ARNK_After.equalsIgnoreCase("yes"))
			{
				driver.findElement(By.xpath("//md-checkbox[contains(@aria-label,'Select All segments')]//div")).click();			
				FlightSearch.ARNK(driver, queryObjects);
				driver.findElement(By.xpath("//md-checkbox[contains(@aria-label,'Select All segments')]//div")).click();			
			}
		//add SSR before Pay in reissue_meenu
		//if(FlightSearch.getTrimTdata(queryObjects.getTestData("ReissueSSRbeforepay")).equalsIgnoreCase("yes")) {
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("Reissue_beforpay_ssr")).equalsIgnoreCase("yes")){
				String addSSRSpecificSegment=FlightSearch.getTrimTdata(queryObjects.getTestData("Reissue_beforpay_ssr_addSSRSpecificSegment"));
				String totSSRs=queryObjects.getTestData("Reissue_beforpay_ssr_totSSRs");
				String ssrNames=queryObjects.getTestData("Reissue_beforpay_ssr_ssrNames");
				String After_Pay_addSSR_OR_Book_Case_addSSR= queryObjects.getTestData("After_Pay_addSSR_OR_Book_Case_addSSR"); 

				FlightSearch ssr=new FlightSearch();					
				if (FlightSearch.getTrimTdata(queryObjects.getTestData("SpecificSSRsforSpecificPAXBefore")).equalsIgnoreCase("yes")) 
					ssr.addspecificSSR(driver,queryObjects);
				else //if(FlightSearch.getTrimTdata(queryObjects.getTestData("SpecificSSRforAllPAX")).equalsIgnoreCase("yes"))
				{
					ssr.addSSR(driver,queryObjects,"No",addSSRSpecificSegment,totSSRs,ssrNames,After_Pay_addSSR_OR_Book_Case_addSSR);
					driver.findElement(By.xpath("//div[text()='Order']")).click();
					Thread.sleep(3000);
				}
			}
		//}
		//Given an extra condition (Rushil)
		String Reissue_penaltywaiver=FlightSearch.getTrimTdata(queryObjects.getTestData("Reissue_penaltywaiver"));
		String Reissue_reqticketdate=FlightSearch.getTrimTdata(queryObjects.getTestData("Reissue_reqticketdate"));
		String Reissue_reqticketdate_date=FlightSearch.getTrimTdata(queryObjects.getTestData("Reissue_reqticketdate_date"));
		String Reissue_paxReductionType = FlightSearch.getTrimTdata(queryObjects.getTestData("Reissue_paxReductionType"));
		if(!Reissue_paxReductionType.isEmpty()||!Reissue_penaltywaiver.isEmpty()||!Reissue_reqticketdate.isEmpty()){
			classselectbon=true;
		}
		//extra condition ends here
		if (classselectbon==true){
			String Reissuce_beforepayment_changelogin=queryObjects.getTestData("Reissuce _beforepayment_changelogin").trim();
			if(Reissuce_beforepayment_changelogin.equalsIgnoreCase("yes"))  //Before click Reissue change POS---After modify Root or change Date case
				PNRsearch.ChangeSalesOffice(driver,queryObjects,Login.Change_Salesoffice,Login.Change_Currency);
			
			//Krishna - PartiallyFlown Case
			String PartiallyFlown = queryObjects.getTestData("PartiallyFlown");
			if(PartiallyFlown.equalsIgnoreCase("yes")) {
				WebElement segChkBoxa = driver.findElement(By.xpath("//md-checkbox[contains(@class,'segment-checkbox') and div[contains(@class,'md-ink-ripple')]]"));
				if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
					segChkBoxa.click();
			}
			//Select Voluntary Reissue
			driver.findElement(By.xpath("//md-select[contains(@aria-label,'Actions')]")).click();		
			Thread.sleep(2000);


			if(reissueType.equalsIgnoreCase("vol")) {
				driver.findElement(By.xpath("//md-option[div[contains(text(),'Voluntary Reissue')]]")).click();
				FlightSearch.loadhandling(driver);
				String Vol_pricebestBuy=FlightSearch.getTrimTdata(queryObjects.getTestData("pricebestbuy_Reissue"));
				if(!Vol_pricebestBuy.equalsIgnoreCase("")) {
					driver.findElement(By.xpath(PriceOptionXpath)).click();
					Thread.sleep(2000);
					if(Vol_pricebestBuy.equalsIgnoreCase("yes")) 
						driver.findElement(By.xpath(BestBuyXpath)).click();
					else if(Vol_pricebestBuy.equalsIgnoreCase("Upsell")) 
						driver.findElement(By.xpath(UpsellXpath)).click();
					Thread.sleep(500);
					
					queryObjects.logStatus(driver, Status.PASS, "PricebestBuy", "Booking seat with best buy option", null);
				}
				
				String TourCode=FlightSearch.getTrimTdata(queryObjects.getTestData("TourCode_Reissue"));
				if(!TourCode.equalsIgnoreCase(""))
					driver.findElement(By.xpath("//input[contains(@ng-model,'quoteInfoCtrl.model.pricingOptions.AccountCode')]")).sendKeys(TourCode);
				// Entering PAX Discount
				String Reissuce_Discount=queryObjects.getTestData("Reissuce_Discount");
				String Reissue_discountType = queryObjects.getTestData("Reissue_discountType");//2
				String Reissue_PaxForDiscount = queryObjects.getTestData("Reissue_PaxForDiscount");//4
				String Reissue_noofSeg = queryObjects.getTestData("Reissue_noofSeg");//6
				String Reissue_discountValue = queryObjects.getTestData("Reissue_discountValue");//7
				String Reissue_Discount_valueType = queryObjects.getTestData("Reissue_Discount_valueType");//8
				String Reissue_Discount_Taxes = queryObjects.getTestData("Reissue_Discount_Taxes");//9
				String Reissue_Discount_Ticket_Designator = queryObjects.getTestData("Reissue_Discount_Ticket_Designator");//10
				boolean BeforeCreatePNR=false;
				FlightSearch fss=new FlightSearch();
				if(Reissuce_Discount.equalsIgnoreCase("yes"))
					fss.Discount(driver, queryObjects, BeforeCreatePNR,Reissue_discountType,Reissue_PaxForDiscount,Reissue_noofSeg,Reissue_discountValue,Reissue_Discount_valueType,Reissue_Discount_Taxes,Reissue_Discount_Ticket_Designator);
				//rushil Reissue case Penalty Waiver and Requested Ticketing Date
				if(Reissue_penaltywaiver.equalsIgnoreCase("yes")) {
					driver.findElement(By.xpath(Penaltywaiverp)).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//md-select[contains(@aria-label,'Penalty Waiver') and contains(@ng-model,'PenaltyWaiver')]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//md-option[div[contains(text(),'Good will')]]")).click();
					Thread.sleep(2000);
				}
				if(Reissue_reqticketdate.equalsIgnoreCase("yes")) {
					//Reissue_reqticketdate_date
					driver.findElement(By.xpath(Reqticdatep)).click();
					Thread.sleep(2000);
					int ticdate=0;
					if(!Reissue_reqticketdate_date.isEmpty())
					ticdate=Integer.parseInt(Reissue_reqticketdate_date);
					Calendar reqticdate = Calendar.getInstance();
					reqticdate.add(Calendar.DATE, -ticdate);
					String reqticdatetimeStamp = new SimpleDateFormat("MM/dd/yyyy").format(reqticdate.getTime());
					/*driver.findElement(By.xpath(Reqticdate_datep)).click();
					Thread.sleep(2000);*/
					/*driver.findElement(By.xpath(Reqticdate_datep)).clear();
					Thread.sleep(2000);*/
					driver.findElement(By.xpath(Reqticdate_datep)).sendKeys(reqticdatetimeStamp);
					Thread.sleep(2000);
				}
				//himani  Reissuce case Pax Reduction
			//	String Reissue_paxReductionType = FlightSearch.getTrimTdata(queryObjects.getTestData("Reissue_paxReductionType"));
				String paxReductionTypeCount = queryObjects.getTestData("Reissue_paxReductionTypeCount");
				String paxReductionTypeName = queryObjects.getTestData("Reissue_paxReductionTypeName");
				if(Reissue_paxReductionType.equalsIgnoreCase("yes")) {
					FlightSearch reductiontype=new FlightSearch();
					reductiontype.PAXreductionType(driver, queryObjects,paxReductionTypeCount,paxReductionTypeName);
				}
				
				//Click on Next button
				Thread.sleep(5000);
				driver.findElement(By.xpath(nextbuttonXpath)).click();	
				//wait till loading closed
				FlightSearch.loadhandling(driver);
				//if penalty waiver and request ticket date is yes it will select reason for waiver after clicking next button (Rushil)
				if(Reissue_penaltywaiver.equalsIgnoreCase("yes")) {
					driver.findElement(By.xpath("//div[div[contains(text(),'Penalty Waiver')]]/following-sibling::div//md-select[contains(@aria-label,'waiver process')]")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//div[contains(@class,'md-active md-clickable')]//md-option[//div[contains(text(),'CHANGES')]]")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//div[div[contains(text(),'Penalty Waiver')]]/following-sibling::div//md-select[contains(@aria-label,'waiver reason')]")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//div[contains(@class,'md-active md-clickable')]//md-option[//div[contains(text(),'WFAR EXP')]]")).click();
					Thread.sleep(1000);
				}
				if(Reissue_reqticketdate.equalsIgnoreCase("yes")) {
					driver.findElement(By.xpath("//div[div[contains(text(),'Pricing By Date')]]/following-sibling::div//md-select[contains(@aria-label,'waiver process')]")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//div[contains(@class,'md-active md-clickable')]//md-option[//div[contains(text(),'CHANGES')]]")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//div[div[contains(text(),'Pricing By Date')]]/following-sibling::div//md-select[contains(@aria-label,'waiver reason')]")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//div[contains(@class,'md-active md-clickable')]//md-option[//div[contains(text(),'WFAR EXP')]]")).click();
					Thread.sleep(1000);
				}
				if(!Reissue_penaltywaiver.isEmpty()||!Reissue_reqticketdate.isEmpty()) {
					try {
						driver.findElement(By.xpath("//button[contains(text(),'Next')]")).click();
						FlightSearch.loadhandling(driver);
					} catch (Exception e) {
						errormsgchec=FlightSearch.getErrorMSGfromAppliction(driver, queryObjects);
						if(errormsgchec.contains("Manual Reissue Required - Ticket not supported ")) {
							queryObjects.logStatus(driver, Status.INFO, "Manual reissue required, error message display", "Manual reissue required --  error message is displayed ", null);
						}
					}
				}
			}

			else if(reissueType.equalsIgnoreCase("invol")) {
				driver.findElement(By.xpath("//div[contains(text(),'Involuntary Reissue')]/parent::md-option")).click();
				//Select process
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//md-select[@aria-label='waiver process']")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//md-option[@ng-value='ProcessList' and @role='option']")).click();
				FlightSearch.loadhandling(driver);
				//Select Reason Code
				driver.findElement(By.xpath("//md-select[@aria-label='waiver reason']")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[@aria-hidden='false']//md-select-menu[contains(@class,'md md-overflow')]//md-option[1]")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[contains(text(),'Quote')]")).click();
				FlightSearch.loadhandling(driver);
			}
			errormsgchec=FlightSearch.getErrorMSGfromAppliction(driver, queryObjects);
			
			//Krishna - Manual Reissues is Required for Voluntary Change issue.
			
			//if(errormsgchec.contains("Manual Reissue Required") || errormsgchec.contains("Manual ReIssue Required")){
			if(errormsgchec.contains("Manual Reissue Required") || errormsgchec.contains("Manual ReIssue Required") || errormsgchec.contains("TRR is unable to determine")
					|| errormsgchec.contains("does not support automated")){
				String ManualReisssue = FlightSearch.getTrimTdata(queryObjects.getTestData("Manual_Reissue"));
				queryObjects.logStatus(driver, Status.PASS, "Checking for Manual Reissue error", "Manual Reissue error occurred", null);
				String Exp_Errormsg=queryObjects.getTestData("Expected_erromsg").trim();
				if(Exp_Errormsg.equalsIgnoreCase("ManualReissueRequiredmessage")){
					return;
				}	
				if(ManualReisssue.equalsIgnoreCase("Yes")) {			
					List<WebElement> paxcount=driver.findElements(By.xpath("//div [@class='pax-info-result']"));		
					FlightSearch.totalnoofPAX=paxcount.size();		
					FlightSearch.Manualquotecase=true;		
					//FlightSearch.currencyType=Login.CURRENCY;
					FlightSearch.currencyType=Login.Cur;
				}
				
				
			}
			else if (errormsgchec.equalsIgnoreCase("")) {
				FlightSearch.Manualquotecase=false;   //  reissue Accepting Mean No Manual reissue.............
			}
			String case_Type="";
			if(FlightSearch.Manualquotecase==false){

				//Krishna - Upsell Case
				String Vol_pricebestBuy=FlightSearch.getTrimTdata(queryObjects.getTestData("pricebestbuy_Reissue"));
				if(Vol_pricebestBuy.equalsIgnoreCase("Upsell")) 
					reissueAmount = driver.findElement(By.xpath("//div[div[span[text()='Total']]]//div[@model='items.value' and @ng-if='items.isAmount']")).getText().trim();
				else
					if(driver.findElements(By.xpath("//div[@title='pssgui.total']//div[text()='Total']/following-sibling::div")).size()>0)
						reissueAmount = driver.findElement(By.xpath("//div[@title='pssgui.total']//div[text()='Total']/following-sibling::div")).getText().trim();
					else{
						queryObjects.logStatus(driver, Status.PASS, "Reissuce cases ADDcollect +Refund scenario", "ADD collect + Refund cases started", null);
						reissueAmount = driver.findElement(By.xpath("//div[@title='pssgui.total.payable']//div[text()='Total Payable']/following-sibling::div")).getText().trim();
					}
				reissueAmount=reissueAmount.replaceAll("[()]", "").trim();
				String reissueAmount_Currency=reissueAmount.split("\\s+")[1];
				reissueAmount = reissueAmount.split("\\s+")[0];    // need to upate cr 189 
				
				// Reissuce case Rebooking Fee Amount validation............. start
				// Penalty checking ..... Created pnr Q and K class panality should display   --- this lines added in above 
				/*List<WebElement> classcheck=driver.findElements(By.xpath("//md-dialog[@aria-label='pssgui-dialog']//child::td[contains(@class,'flight-class')]/div/div/span"));
				ArrayList getClasscheck = new ArrayList<>();
				classcheck.forEach(a -> getClasscheck.add(a.getText().trim().toUpperCase()));*/  
				
				if (getClasscheck.contains("Q") || getClasscheck.contains("K")) {
					
					//Krishna - Upsell Case
					String GetPenalityamt="";
					if(Vol_pricebestBuy.equalsIgnoreCase("Upsell")) 
						GetPenalityamt=driver.findElement(By.xpath("//div[div[span[text()='Rebooking Fee']]]//div[@model='items.value' and @ng-if='items.isAmount']")).getText().trim();
						//GetPenalityamt=driver.findElement(By.xpath("//div[div[span[text()='Fare Difference']]]//div[@model='items.value' and @ng-if='items.isAmount']")).getText().trim();
					else
						GetPenalityamt=driver.findElement(By.xpath("//div[@title='pssgui.rebooking.fee' and @model='leftTabInfo.farePrice.rebookingFee']/div[2]")).getText().trim();
					Double getpenamt=Double.parseDouble(GetPenalityamt.split("\\s+")[0]);
					
					if (getpenamt>0) 
						queryObjects.logStatus(driver, Status.PASS, "SPECIFIC class Q or K case Penalty checking", "it is displaying penalty, Penalty amount is:"+getpenamt, null);
					else if(reissueType.equalsIgnoreCase("invol"))
						queryObjects.logStatus(driver, Status.PASS, "SPECIFIC class Q or K case Penalty checking", "Penalty should be zero, Penalty amount is:"+getpenamt, null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "SPECIFIC class Q or K case Penalty checking", "it should  more than zero penalty, amount is:"+getpenamt, null);
					
					//Krishna - Upsell Case
					String Fare_Difference="";
					if(Vol_pricebestBuy.equalsIgnoreCase("Upsell"))
						Fare_Difference=driver.findElement(By.xpath("//div[div[span[text()='Fare Difference']]]//div[@model='items.value' and @ng-if='items.isAmount']")).getText().trim();
					else
						Fare_Difference=driver.findElement(By.xpath("//div[@title='pssgui.fare.difference' and @model='leftTabInfo.farePrice.fareDifference']/div[2]")).getText().trim();
					FlightSearch.TotalRefundamt=Double.parseDouble(reissueAmount);
					FlightSearch.PanaltyAmount=getpenamt;
					
					String Fare_Difference_split="";
					String Fare_Difference_Amount="";
					double diffamt = 0;
					String Fare_Difference_Currency="";
					
					if(Fare_Difference.contains("(")){
						Fare_Difference_split = Fare_Difference.replaceAll("[()]", "").trim();
						Fare_Difference_Amount=FlightSearch.roundDouble((Fare_Difference_split.split("\\s+")[0]).trim());
						Fare_Difference_Currency=Fare_Difference_split.split("\\s+")[1].trim();
						String Current_Currency=driver.findElement(By.xpath("//div[@action='saleOfficeInfo']/div[3]")).getText();
						//String Original_Currency=GetOriginalcurrency(driver,queryObjects);
						String Convertion_Required=Fare_Difference_Amount+";"+Current_Currency+";"+Original_Currency;
						//Double Converted_AMT=Reissue_StoreQuoteid(driver, queryObjects,Convertion_Required);   // store quote and re open remarks cr 189 chagen
						FlightSearch.Fare_Diff=Double.parseDouble(Fare_Difference_Amount);
						//FlightSearch.Fare_Diff=Converted_AMT;
						
						if(Fare_Difference_Currency.equalsIgnoreCase(Original_Currency))
							queryObjects.logStatus(driver, Status.PASS,"Reissue Refund case Fare Difference Currecny checking","Curreency displaying as exepected",null);
						else
							queryObjects.logStatus(driver, Status.FAIL,"Reissue Refund case Fare Difference Currecny checking","currecny should display correctly Actual "+Fare_Difference_Currency+" Expected "+Original_Currency,null);
						
						if(Current_Currency.equalsIgnoreCase(Original_Currency)){   // Current and Original correcny equal case
							if(FlightSearch.Fare_Diff > FlightSearch.PanaltyAmount){   // Refund case
								diffamt=Math.abs(Double.parseDouble(FlightSearch.roundDouble (String.valueOf(FlightSearch.Fare_Diff-FlightSearch.PanaltyAmount))));
								case_Type="Refund";
							}
							else if(FlightSearch.Fare_Diff < FlightSearch.PanaltyAmount) {  // Add collect case
								diffamt=Math.abs(Double.parseDouble(FlightSearch.roundDouble (String.valueOf(FlightSearch.PanaltyAmount-FlightSearch.Fare_Diff))));
								case_Type="Addcollect";
							}
							if(reissueAmount_Currency.equalsIgnoreCase(Current_Currency))
								queryObjects.logStatus(driver, Status.PASS, "REissues case if Original and Current Currency are Equal case Reissue  Currecny checking", "Reissue currecny displaying currecny", null);
							else
								queryObjects.logStatus(driver, Status.FAIL, "REissues case if Original and Current Currency are Equal case Reissue  Currecny checking", "Reissue currecny should displaying currecny Acutal "+reissueAmount_Currency+" Expected "+Current_Currency, null);
						}
						else      
						{
							Double Converted_AMT=Reissue_StoreQuoteid(driver, queryObjects,Convertion_Required,"yes");   // store quote and re open remarks cr 189 chagen
							//FlightSearch.Fare_Diff=Double.parseDouble(Fare_Difference_Amount);
							FlightSearch.Fare_Diff=Converted_AMT;
							if(FlightSearch.Fare_Diff > FlightSearch.PanaltyAmount){   // Refund case
								diffamt=Math.abs(Double.parseDouble(FlightSearch.roundDouble (String.valueOf(FlightSearch.Fare_Diff-FlightSearch.PanaltyAmount))));
								Convertion_Required=diffamt+";"+Original_Currency+";"+Current_Currency; 
								diffamt=Reissue_StoreQuoteid(driver, queryObjects,Convertion_Required,"No");
								case_Type="Refund";
							}
							else if(FlightSearch.Fare_Diff < FlightSearch.PanaltyAmount) {  // Add collect case
								diffamt=Math.abs(Double.parseDouble(FlightSearch.roundDouble (String.valueOf(FlightSearch.PanaltyAmount-FlightSearch.Fare_Diff))));	
								case_Type="Addcollect";
							}
							if(case_Type.equalsIgnoreCase("Refund")){
								if(reissueAmount_Currency.equalsIgnoreCase(Original_Currency))
									queryObjects.logStatus(driver, Status.PASS, "REissues case if Original and Current Currency are Not Equal case Reissue  Currecny checking", "Reissue with Refund case currecny displaying currecny", null);
								else
									queryObjects.logStatus(driver, Status.FAIL, "REissues case if Original and Current Currency are Not Equal case Reissue  Currecny checking", "Reissue with Refund case currecny should displaying currecny Acutal "+reissueAmount_Currency+" Expected "+Original_Currency, null);
							}
								
							else if(case_Type.equalsIgnoreCase("Addcollect")){
								if(reissueAmount_Currency.equalsIgnoreCase(Current_Currency))
									queryObjects.logStatus(driver, Status.PASS, "REissues case if Original and Current Currency are Not Equal case Reissue  Currecny checking", "Reissue with Addcollect case currecny displaying currecny", null);
								else
									queryObjects.logStatus(driver, Status.FAIL, "REissues case if Original and Current Currency are Not Equal case Reissue  Currecny checking", "Reissue with Addcollect case currecny should displaying currecny Acutal "+reissueAmount_Currency+" Expected "+Current_Currency, null);
							}
								
							
						}
					}
					else{
						
						Fare_Difference_Amount=FlightSearch.roundDouble((Fare_Difference.split("\\s+")[0]).trim());
						FlightSearch.Fare_Diff=Double.parseDouble(Fare_Difference_Amount);
						double temdd=Double.parseDouble(FlightSearch.roundDouble (String.valueOf(FlightSearch.Fare_Diff+FlightSearch.PanaltyAmount)));
						diffamt=Math.abs(temdd);
						case_Type="Addcollect";
					}
					queryObjects.logStatus(driver, Status.PASS, "SPECIFIC class Q or K case Get Fare Differnce Amount", "Fare Difference Amount is :"+Fare_Difference_Amount, null);

					if (FlightSearch.TotalRefundamt==diffamt) 
						queryObjects.logStatus(driver, Status.PASS, "SPECIFIC class Q or K case Total Refund amount checking", "Total Refund amount calculation correct", null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "SPECIFIC class Q or K case Total Refund amount checking", "Total Refund amount calculation Wrong Tot Amount :(Actual)="+FlightSearch.TotalRefundamt +" Fare Expected Amount="+ diffamt +" PanaltyAmount is "+FlightSearch.PanaltyAmount , null);
					
					List<WebElement> sgetpenamtamt=driver.findElements(By.xpath("//div[@model='quoteDetail.TotalFares']/following-sibling::div/div[2]"));
					getpenamtamt = new ArrayList<>();
					for (int reebook = 0; reebook < sgetpenamtamt.size(); reebook++) {  //Storing All Reebookibng amount
						Double Reebooking_Amt=Double.parseDouble(sgetpenamtamt.get(reebook).getText().trim().split(" ")[0]);
						getpenamtamt.add(Reebooking_Amt);
					}
					
				}
				// Reissuce case Rebooking Fee Amount validation............. End
				// tax is not Refundable verification.
				String TaxCodeRefundableVerifiation=FlightSearch.getTrimTdata(queryObjects.getTestData("Not_refundable_Taxes"));
				try{
					if (TaxCodeRefundableVerifiation.equalsIgnoreCase("yes")) {
						String sTaxcodereunfdver=FlightSearch.getTrimTdata(queryObjects.getTestData("Not_refundable_TaxCodes"));
						if (sTaxcodereunfdver.contains(";")) {
							String []splitTaxRefver=sTaxcodereunfdver.split(";");
							for (String sTaxcodeis : splitTaxRefver) {
								if (driver.findElement(By.xpath("//div[contains(text(),'The "+sTaxcodeis+" tax is not refundable')]")).isDisplayed())
									queryObjects.logStatus(driver, Status.PASS, "Not Refundable Tax verification", "it is showing Not Refundable message", null);
								else
									queryObjects.logStatus(driver, Status.FAIL, "Not Refundable Tax verification", "it should show Not Refundable message The "+sTaxcodeis+" tax is not refundable", null);
							}
						}
						else
						{
							if (driver.findElement(By.xpath("//div[contains(text(),'The "+sTaxcodereunfdver+" tax is not refundable')]")).isDisplayed())
								queryObjects.logStatus(driver, Status.PASS, "Not Refundable Tax verification", "it is showing Not Refundable message", null);
							else
								queryObjects.logStatus(driver, Status.FAIL, "Not Refundable Tax verification", "it should show Not Refundable message The "+sTaxcodereunfdver+" tax is not refundable", null);
						}
					}
				}
				catch(Exception e) {
					queryObjects.logStatus(driver, Status.INFO, " vol reissue case Refundable tax", "message verification " + e.getMessage() , e);
				}
				// tax code verification
				String TaxCodeVerifiation=FlightSearch.getTrimTdata(queryObjects.getTestData("TaxCodeVerifiation"));
				if (TaxCodeVerifiation.equalsIgnoreCase("yes")) {
					List<WebElement> Quoteresults=driver.findElements(By.xpath("//div[div[toggle-title[contains(text(),'Quote result')]]]/i"));
					String xpathis="//div[@action='taxes']/div[@ng-repeat='tax in orderTableDisplay.list']/div[2]";
					for (int iQuoteresults = 0; iQuoteresults < Quoteresults.size(); iQuoteresults++) {
						Quoteresults.get(iQuoteresults).click();
						FlightSearch.loadhandling(driver);
						//Tax refunded validation
						String Refundvalidationy=FlightSearch.getTrimTdata(queryObjects.getTestData("Refund_Taxverification"));
						if (Refundvalidationy.equalsIgnoreCase("yes")) {
							String taxnm="";
							String []splitTaxCode;
							String TaxCode=FlightSearch.getTrimTdata(queryObjects.getTestData("Refund_TaxCode"));
							List<WebElement> lRefundtax=driver.findElements(By.xpath("//tr[contains(@ng-repeat,'orderTableDisplay.model.DisplayTax')]/td[contains(@class,'layout-align-start-center')]"));
							for (int refuntaxnm= 0; refuntaxnm < lRefundtax.size(); refuntaxnm++) {
								taxnm=lRefundtax.get(refuntaxnm).getText().trim();
								String rettaxamt="";
								if(TaxCode.contains(";")){
									splitTaxCode=TaxCode.split(";");
									for (String gettaxcodeisinexcel: splitTaxCode ) {
										TaxCode=gettaxcodeisinexcel;
										if (taxnm.contains(TaxCode)){
											rettaxamt=driver.findElement(By.xpath("//tr[contains(@ng-repeat,'orderTableDisplay.model.DisplayTax')]/td[contains(@class,'layout-align-start-center') and contains(text(),'"+taxnm+"')]//parent::tr/td[5]/div/div")).getText().trim();
											if (!rettaxamt.isEmpty()) 
												queryObjects.logStatus(driver, Status.PASS, "Tax Rentrun verification in "+taxnm, "Tax Return was available Return ammount is "+rettaxamt, null);
											else
												queryObjects.logStatus(driver, Status.FAIL, "Tax Rentrun verification in "+taxnm, "Tax Return should available, Actual retruns is"+rettaxamt, null);

										}
									}
								}
								else{
									if (taxnm.contains(TaxCode)) {
										rettaxamt=driver.findElement(By.xpath("//tr[contains(@ng-repeat,'orderTableDisplay.model.DisplayTax')]/td[contains(@class,'layout-align-start-center') and contains(text(),'"+taxnm+"')]//parent::tr/td[5]/div/div")).getText().trim();
										if (!rettaxamt.isEmpty()) 
											queryObjects.logStatus(driver, Status.PASS, "Tax Rentrun verification in "+taxnm, "Tax Return was available Return ammount is "+rettaxamt, null);
										else
											queryObjects.logStatus(driver, Status.FAIL, "Tax Rentrun verification in "+taxnm, "Tax Return should available, Actual retruns is"+rettaxamt, null);
									}
								}

							}
						}
						// tax exist or not
						PNRsearch.TaxCodeValidation(driver,queryObjects,xpathis,"Quote Screen");  // tax code verification
						Quoteresults.get(iQuoteresults).click();
						FlightSearch.loadhandling(driver);
					}
				}
				
				
				if (!getClasscheck.contains("Q") &&  !getClasscheck.contains("K")) {   // if other than Q and K class requied only clicking Store Quote id
					String storeQuoteId=FlightSearch.getTrimTdata(queryObjects.getTestData("storeQuoteId"));  //if yes reissuce case Quote id will store in Remarks
					if(storeQuoteId.equalsIgnoreCase("yes")){
						Reissue_StoreQuoteid(driver, queryObjects,"","yes");
					}
				}
				// All  reissue case doing store quote id 
			/*	String storeQuoteId=FlightSearch.getTrimTdata(queryObjects.getTestData("storeQuoteId"));  //if yes reissuce case Quote id will store in Remarks
				if(storeQuoteId.equalsIgnoreCase("yes")){
					
					Map<String,ArrayList<String>> getsegdet1=new HashMap<String,ArrayList<String>>();  //store segments in map
					getsegdet1=storeFlightdetails(driver,getsegdet1);
					String Reissuecasequoteid=driver.findElement(By.xpath("//div[span[text()='Quote ID']]")).getText().trim();
					String quoteid[]=Reissuecasequoteid.split(":");
					Reissuecasequoteid=quoteid[1].trim();
					
					driver.findElement(By.xpath("//button[contains(text(),'Store Quote ID') and not(@disabled='disabled')]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath(remarks_Xpath)).click();
					FlightSearch.loadhandling(driver);
					
					driver.findElement(By.xpath("//div[contains(text(),'QUOTE ID: "+Reissuecasequoteid+"')]")).click();
					FlightSearch.loadhandling(driver);
					
					Map<String,ArrayList<String>> getsegdet2=new HashMap<String,ArrayList<String>>();  //store segments in map
					getsegdet2=storeFlightdetails(driver,getsegdet2);
					
					if (getsegdet1.entrySet().containsAll(getsegdet2.entrySet())) 
						queryObjects.logStatus(driver, Status.PASS, "Compare Segment details Before Store Quote id and After store Quote id", "Details are matched", null);
			        else 
			        	queryObjects.logStatus(driver, Status.FAIL, "Compare Segment details Before Store Quote id and After store Quote id", "Details not Matched Expected:"+getsegdet1+" ,Actual:"+getsegdet2+"", null);
					
				}*/
				if(reissueType.equalsIgnoreCase("vol")) {
					//Click on Add to order
					driver.findElement(By.xpath("//button[text()='Add To Order']")).click();
					FlightSearch.loadhandling(driver);
					Login.reissuetypeticktvalid=Login.reissuetypeticktvalid + "vol"; 
				}

				else if(reissueType.equalsIgnoreCase("invol")) {
					//clck on reissue ticket
					driver.findElement(By.xpath("//button[text()='Reissue ticket']")).click();
					FlightSearch.loadhandling(driver);
					Login.reissuetypeticktvalid=Login.reissuetypeticktvalid + "invol";
				}
				
				
				if(FlightSearch.getTrimTdata(queryObjects.getTestData("SYNCStatus")).equalsIgnoreCase("yes"))
				{
					return;
				}
			} /// Automate quote case
			else if (FlightSearch.Manualquotecase==true) { // Manual quote case
				String Manualquote_errormsg=FlightSearch.getErrorMSGfromAppliction(driver, queryObjects);
				if(Manualquote_errormsg.contains("TRR is unable to determine") || Manualquote_errormsg.contains("Manual ReIssue Required")|| Manualquote_errormsg.contains("Manual Reissue Required")
						|| Manualquote_errormsg.contains("does not support automated")){
					queryObjects.logStatus(driver, Status.PASS, "Manual reissue case expecting error message", "After select voluntary reissue displayied error message like Manual reissue required", null);
				}
				try{
					reissueAmount=Manual_reissue(driver,queryObjects);
					AirlinesRefund = "ManualReissue";	// added by jenny
				}catch(Exception e){
					queryObjects.logStatus(driver, Status.FAIL, "Manual reissue process strted", "Manual reissue case issue", e);
				}

			}
			String Residual_EMD =FlightSearch.getTrimTdata(queryObjects.getTestData("Residual_EMD"));  
			String reiwopay=FlightSearch.getTrimTdata(queryObjects.getTestData("Reissue_Without_Payment"));
			//checking for Reissue without payment. If true it will skip payment rushil
			if(!reiwopay.equalsIgnoreCase("yes")) {
				//payment
				//String totalamt=driver.findElement(By.xpath("//div[@model='currency.total']/div")).getText().trim();
				Balanceamt=driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText().trim();
				double bBalamt;
				try{
					bBalamt=Double.parseDouble(Balanceamt);  // this is Balance amount paying  PAX to COPA
	
				}catch (Exception e) {
					Balanceamt=Balanceamt.replaceAll("[()]", "").trim();
					bBalamt=Double.parseDouble(Balanceamt);  // this is for Balance amount paying COPA to PAX
				//	Login.CardDeposit=true;  // This flag is using for Execute Card Deposit functionality as per new CR 189 Reissue with refund cardDeposit should not come
					
					queryObjects.logStatus(driver, Status.INFO, "Balance getting issue", e.getLocalizedMessage(), e);
	
				}
				// below currecy only fully round and comparing
				//String Current_Currency=driver.findElement(By.xpath("//div[@action='saleOfficeInfo']/div[3]")).getText();
				//if(Current_Currency.equalsIgnoreCase("MEX")||Current_Currency.equalsIgnoreCase("CLP")||Current_Currency.equalsIgnoreCase("COP")||Current_Currency.equalsIgnoreCase("MXN")  )
					//reissueAmount=FlightSearch.convertinteger(String.valueOf(reissueAmount));
				
				if (bBalamt==Double.parseDouble(reissueAmount)) {
					queryObjects.logStatus(driver, Status.PASS, "Reissue case After click Add to order:: Reissue amount  checking", "Reissue case After click Add to orde amount showing  in Balance due", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Reissue case After click Add to order:: Reissue amount checking", "Reissue case After click Add to orde amount should show in Balance due actual: "+Balanceamt+" expected: "+reissueAmount, null);
				}
				//Navira (for Search by E-tkt Number - After loading, lways goes to Tickets Tab
				driver.findElement(By.xpath("//div[text()='Order']")).click();
				FlightSearch.loadhandling(driver);
				String Balanceamt=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
				//if(Integer.parseInt(Balanceamt)!=0) {
				Thread.sleep(2000);
				driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
				FlightSearch.loadhandling(driver);
				
				//Jen Feb 10
				//SSRbeforereissue
				String VerifyTaxCd = FlightSearch.getTrimTdata(queryObjects.getTestData("TaxCodeVerifiation"));
				if (VerifyTaxCd.contains("SetTax-SSR")) {
					String sSSRAmt = "";
					String TaxCode=FlightSearch.getTrimTdata(queryObjects.getTestData("TaxCode"));
					String TaxPer = FlightSearch.getTrimTdata(queryObjects.getTestData("TaxPrcnt"));
					String ModPrice = FlightSearch.getTrimTdata(queryObjects.getTestData("ModifyPrice"));
					if (!ModPrice.isEmpty()) {
						driver.findElement(By.xpath("//i[contains(@ng-click,'quoteDetail.editService')]")).click();
						driver.findElement(By.xpath("//input[@ng-model='service.Amount']")).sendKeys(ModPrice);
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//input[@ng-model='service.Amount']")).clear();
						driver.findElement(By.xpath("//input[@ng-model='service.Amount']")).sendKeys(ModPrice);
						driver.findElement(By.xpath("//input[@ng-model='service.Amount']")).sendKeys(Keys.TAB);
						sSSRAmt = ModPrice;
					}
					if (!TaxPer.isEmpty()) {
						driver.findElement(By.xpath("//i[@class='icon-add']/..//span[@translate='pssgui.add.a.tax']")).click();
						driver.findElement(By.xpath("//input[@ng-model='TaxInfo.TaxCode']")).sendKeys(TaxCode);
						driver.findElement(By.xpath("//input[@ng-model='TaxInfo.pricingOption']")).sendKeys(TaxPer);
						sSSRAmt = Double.parseDouble(sSSRAmt) + Double.parseDouble(TaxPer)+"";
					}
					driver.findElement(By.xpath("//md-select[@ng-model='WaiverReasonInfo.process']")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//div[contains(@class,'md-active')]//md-option[@ng-value='ProcessList']/div[1]")).click();

					driver.findElement(By.xpath("//md-select[@ng-model='WaiverReasonInfo.Reason']")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//md-option[@ng-repeat='Reason in WaiverReasonInfo.process.Reason']/div[contains(text(),'WOTH SERVICERECOVERY')]")).click();
					driver.findElement(By.xpath("//button[text()='Next']")).click();
					FlightSearch.loadhandling(driver);
					if (Double.parseDouble(driver.findElement(By.xpath("//input[@name='amount']")).getAttribute("value"))==Double.parseDouble(sSSRAmt)) {
						queryObjects.logStatus(driver, Status.PASS, "Check the amount to be paid is per the service fee and the tax provided", "SSR amount is updated as the given value", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Check the amount to be paid is per the service fee and the tax provided", "SSR amount to be paid is correct", null);
					}
				}
				
				// CR166 validation adding.............start  pay ment screen validation
				String CR166_Taxcode=queryObjects.getTestData("CR166_Taxcode");
				String CR166_Tax_percent=queryObjects.getTestData("CR166_Tax_percent");
				if((!CR166_Taxcode.isEmpty()) && (!CR166_Tax_percent.isEmpty())){
					if(FlightSearch.Manualquotecase==false){
						List<WebElement> getRebookingFee=driver.findElements(By.xpath("//div[span[contains(text(),'Rebooking Fee')]]/following-sibling::div/div/div"));
						List<WebElement> getRebookingFeeTax=driver.findElements(By.xpath("//div[contains(text(),'"+CR166_Taxcode.toUpperCase()+"')]/following-sibling::div"));
						
						for (int itax = 0; itax < getRebookingFee.size(); itax++) {
							String	sgetRebookingFee=getRebookingFee.get(itax).getText();
							String	sgetRebookingFeeTax=getRebookingFeeTax.get(itax).getText();
							double dgetRebookingFee=Double.parseDouble(sgetRebookingFee.split(" ")[0]);
							double dgetRebookingFeeTax=Double.parseDouble(sgetRebookingFeeTax.split(" ")[0]);
							double manualper=Double.parseDouble(FlightSearch.roundDouble(String.valueOf(dgetRebookingFeeTax/dgetRebookingFee)));
							double calpercent=Double.parseDouble(CR166_Tax_percent)/100;
							double Aftercal_percent_value=Double.parseDouble(FlightSearch.roundDouble_one(String.valueOf(dgetRebookingFee*calpercent)));
							//if(dgetRebookingFeeTax==Aftercal_percent_value)
							if(calpercent ==manualper)
								queryObjects.logStatus(driver, Status.PASS, "CR166_Tax_percent calculation", "As per login POS it is calculating Percentage currectly as "+CR166_Tax_percent+ " %" , null);
							else if(FlightSearch.convertinteger(String.valueOf(calpercent)).equals(FlightSearch.convertinteger(String.valueOf(manualper))))
								queryObjects.logStatus(driver, Status.PASS, "CR166_Tax_percent calculation", "As per login POS it is calculating Percentage currectly as "+CR166_Tax_percent+ " %" , null);
							else
								queryObjects.logStatus(driver, Status.FAIL, "CR166_Tax_percent calculation", "As per login POS it should calculate Percentage currectly Note: CHECK TEST DATA LIKE PERCENTAGE AND CLASE , Actual value::"+dgetRebookingFeeTax +"Expected::"+Aftercal_percent_value , null);
						}
					}
					else 
						queryObjects.logStatus(driver, Status.WARNING, "CR166_Tax_percent calculation","Ticket created in Manual Quote so Tax not applicable",null);
	
					
				}
				// CR166 validation ......end
				//String Residual_EMD =FlightSearch.getTrimTdata(queryObjects.getTestData("Residual_EMD"));  // REsidual Emd functionality 
				if(Residual_EMD.equalsIgnoreCase("yes") && FlightSearch.Manualquotecase==false)  // it shold not manualquotecase
					driver.findElement(By.xpath("//md-checkbox[@ng-model='refundOverride.isResidualEmd' and @aria-disabled='false'  and @aria-checked='false']")).click();
	
				if(case_Type.equalsIgnoreCase("Refund")){  // CR189 related functionality
					List<WebElement>residual=driver.findElements(By.xpath("//md-checkbox[@ng-model='refundOverride.isResidualEmd' and @aria-disabled='true'  and @aria-checked='true']"));
					if(residual.size()>0)
						queryObjects.logStatus(driver, Status.PASS, "Reissue with Refund case Residual EMD checking ", "Reissue with Refund Residual EMD checkbox displaying  and auto selected", null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "Reissue with Refund case Residual EMD checking ", " Reissue with Refund case Residual EMD checkbox should displaying and By Default should  select ", null);
						
				}	
				
				driver.findElement(By.xpath("//button[contains(text(),'Reissue')]")).click();
				FlightSearch.loadhandling(driver);
				//Krishna - Change SaleOffice before Reissue Payment
				String Reissue_ChangePOS_Before_Payment =FlightSearch.getTrimTdata(queryObjects.getTestData("Reissue_ChangePOS_Before_Payment"));
				if(!Reissue_ChangePOS_Before_Payment.isEmpty()) {
					String Reissue_ChangePOS_Before_Payment_Curr =FlightSearch.getTrimTdata(queryObjects.getTestData("Reissue_ChangePOS_Before_Payment_Curr"));
					PNRsearch.ChangeSalesOffice(driver,queryObjects,Reissue_ChangePOS_Before_Payment,Reissue_ChangePOS_Before_Payment_Curr);
				}
				if (FlightSearch.getTrimTdata(queryObjects.getTestData("Reissue_MultipleFOP")).equalsIgnoreCase("yes") && (bBalamt)>0 ) {
	
					double totalamt;
					try{
						
						if (Balanceamt.contains("(")) {
							Balanceamt = Balanceamt.substring(Balanceamt.indexOf("(")+1, Balanceamt.indexOf(")")).trim();
						}
						totalamt=Double.parseDouble(Balanceamt);  // this is Balance amount paying  PAX to COPA
						FlightSearch flop=new FlightSearch();
						FlightSearch.MultipleFOPType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPType"));
						FlightSearch.MultFOPsubType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPSubType"));
						FlightSearch.fopCardNums = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPCardNums"));
						String BankNames = queryObjects.getTestData("MultipleFOPBankName").trim();
						String InstallmentNums = queryObjects.getTestData("MultipleFOPInstallmentNum").trim();
						flop.MulFOP(driver,queryObjects,totalamt,FlightSearch.MultipleFOPType,FlightSearch.MultFOPsubType,FlightSearch.fopCardNums,BankNames,InstallmentNums,"Reissue");  //calling for multiple FOP....
	
					}catch (Exception e) {
						Balanceamt=Balanceamt.replaceAll("[()]", "").trim();
						totalamt=Double.parseDouble(Balanceamt);  // this is for Balance amount paying COPA to PAX
						queryObjects.logStatus(driver, Status.PASS, "Reissue case but Copa giving amount to PAX ","Cop giving amount o PAX so FOP is Cash ,Return amount is :"+Balanceamt,null);
						queryObjects.logStatus(driver, Status.INFO, "Balance getting issue", e.getLocalizedMessage(), e);
						AirlinesRefund = "yes";//Jenny
					}
	
				}
				else if (bBalamt==0) {
					AirlinesRefund = "FREE";//Jenny Feb 17

				}
				else if((bBalamt)>0 && (!Balanceamt.contains("(")) )
					FlightSearch.singleFOP(driver, queryObjects,"Cash","Reissue payment");
				else{
					FlightSearch.ppspaymentmodesingle="Cash";
				}
				driver.findElement(By.xpath("//button[text()='Pay' and not(@disabled='disabled')]")).click();
	
				try{
					Thread.sleep(1000);
					if (driver.findElement(By.xpath("//div[text()='FOID Details']")).isDisplayed()) {
						FlightSearch.enterFoiddetails(driver,queryObjects);
						FlightSearch.loadhandling(driver);
						Thread.sleep(1000);
						driver.findElement(By.xpath("//button[text()='Next']")).click();
					}
	
				}catch (Exception e) {
					queryObjects.logStatus(driver, Status.INFO, "reissue case FOID detils", e.getLocalizedMessage(), e);
	
				}
				FlightSearch.emailhandling(driver,queryObjects);
				FlightSearch.CardDepositExecution(driver, queryObjects); //Card Deposit functionality
	
				
				
				// Rject_Review Message Handle PPS Case--- Start
				String Review_Reject_cases=queryObjects.getTestData("Review_Reject");
				// payment sucessfull validation checking 
				boolean PymtError_Msg = FlightSearch.paymentvalidation(driver, queryObjects,Review_Reject_cases);
				
				// kishore created method anad moved
			/*	try {
					String Review_Reject_cases=FlightSearch.getTrimTdata(queryObjects.getTestData("Review_Reject_cases"));
					String Actwarningmsg="";
					if (!Review_Reject_cases.isEmpty()) {
						try{
							Actwarningmsg=driver.findElement(By.xpath("//div[contains(@class,'md-dialog-content-body')]")).getText().trim();
						}
						catch(Exception e){
							queryObjects.logStatus(driver, Status.FAIL,"PPS Review/Reject  case ","Xpath change",null);
						}
						if (Review_Reject_cases.equalsIgnoreCase("REVIEW")){
							if(Actwarningmsg.contains("480")) 
								queryObjects.logStatus(driver, Status.PASS, "PPS Review case warning message validation", "Review case message displaying correctly", null);
							else
								queryObjects.logStatus(driver, Status.FAIL, "PPS Review case warning message validation", "Review case message displaying wrong :: "+Actwarningmsg, null);
						}
						else if(Review_Reject_cases.equalsIgnoreCase("REJECT") && Actwarningmsg.contains("481")){ 
							if(Actwarningmsg.contains("481")) 
								queryObjects.logStatus(driver, Status.PASS, "PPS Reject case warning message validation", "Reject case message displaying correctly", null);
							else
								queryObjects.logStatus(driver, Status.FAIL, "PPS Reject case warning message validation", "Reject case message displaying wrong :: "+Actwarningmsg, null);
						}
						else
							queryObjects.logStatus(driver, Status.FAIL, "REVIEW/REJECT case", "REVIEW/REJECT case should display message :: "+Actwarningmsg, null);
	
					}
					String Expected_erromsg=queryObjects.getTestData("Expected_erromsg");   // this is for if we give Wrong Error message checking functionality
					if(Expected_erromsg.equalsIgnoreCase("invalid_Email")){	
						String error_count_xpath="//span[contains(@class,'error-count')]";
						if(driver.findElements(By.xpath(error_count_xpath)).size()>0){
							//String getMssg=driver.findElement(By.xpath("//span[contains(@class,'error-count')]//preceding-sibling::span")).getText().trim();
							boolean getMssg=false;
							driver.findElement(By.xpath("//span[contains(@class,'error-count')]//preceding-sibling::span")).click();
							Thread.sleep(2000);
							List<WebElement> GetError_msg = driver.findElements(By.xpath("//md-menu-content[@class='error-panel-menu']/md-menu-item/div/div[contains(@class,'msg-error') or contains(@class,'msg-alert')]"));
							 for (int iErro =0 ; iErro < GetError_msg.size(); iErro++) { 
								 String sError_msg=GetError_msg.get(iErro).getText().trim();
								 if(sError_msg.contains("102 - One or more fields in the request contains invalid data. c:billTo/c:email for which values are invalid")){
									 getMssg=true;	 
								 }
							 }			 
							 
							Thread.sleep(3000);
							WebElement email=	driver.findElement(By.xpath("//button[text()='Done']"));
							Actions actions = new Actions(driver);
					        actions.moveToElement(email);
					        actions.click();
					        actions.build().perform();
					        driver.findElement(By.xpath("//button[text()='Cancel']")).click();	
					        FlightSearch.loadhandling(driver);
					        PymtError_Msg = true;
					        if(getMssg)
					        	queryObjects.logStatus(driver, Status.PASS, "While payment Given Wrong email id Error message checking", "If we given wrong Email id proper Error message displaying", null);
					        else
					        	queryObjects.logStatus(driver, Status.FAIL, "While payment Given Wrong email id Error message checking", "If we given wrong Email id it should be display Error message like  102 - One or more fields in the request contains invalid data", null);
						}
						else
							queryObjects.logStatus(driver, Status.FAIL, "While payment Given Wrong email id Error message checking", "If we given wrong Email id it should be display Error message like  102 - One or more fields in the request contains invalid data", null);
					}
					else{
						driver.findElement(By.xpath("//md-dialog-actions/button[contains(text(), 'Ok')]")).click();
						FlightSearch.loadhandling(driver);
						PymtError_Msg = true;
						Boolean Cancelbtn = driver.findElement(By.xpath("//button[text()='Cancel']")).isEnabled();
						if (Cancelbtn) {
							driver.findElement(By.xpath("//button[text()='Cancel']")).click();	
							FlightSearch.loadhandling(driver);
						} else {
							Boolean Donebtn = driver.findElement(By.xpath("//button[text()='Done']")).isEnabled();
							if (Donebtn) {
								//Check for Payment successful message if done is enabled
								WebDriverWait wait = new WebDriverWait(driver, 120);
								wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
								String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
								queryObjects.logStatus(driver, Status.PASS, "Reissue Payment", statusMessage, null);
								driver.findElement(By.xpath("//button[text()='Done']")).click();
								FlightSearch.loadhandling(driver);
							}
		
						}
					}
	
				} catch (Exception e) {
					WebDriverWait wait1 = new WebDriverWait(driver, 60);
					wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
					String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
					queryObjects.logStatus(driver, Status.PASS, "Reissue Payment", statusMessage, null);
					
					//pnrcreated=true;	
				}*/  // kishore created method anad moved
	
				// Rject_Review Message Handle PPS Case--- End
	
	
				//ADD Remrks table......yash
				if ((FlightSearch.getTrimTdata(queryObjects.getTestData("PNRVerify_CCFOP")).equalsIgnoreCase("YES"))) {		
					
					CyberSoursePNR(driver, queryObjects);
					FlightSearch.Aftecybebersourec_Update_check_GUI=true;
				}
				//get ticket number
				boolean ticketveryflag=false;
				if ((!PymtError_Msg)) {
					try {
						//Check the existing ticket number if any
						if (!FlightSearch.gettecketno.isEmpty()) {
							ExchangeTkt = FlightSearch.gettecketno;
							ExchngTktamt = FlightSearch.gettecketamt;
						}
						List<WebElement> etkts = driver.findElements(By.xpath("//span[text()='Ticket']/following-sibling::span"));
						int totalTKTs = etkts.size();
						gettickets = new ArrayList<>();
						etkts.forEach(a -> gettickets.add(a.getText().trim()));		
		
						//Sales report value checking purpose added value to this list
						//if (!FlightSearch.gettecketno.equals(null))
						FlightSearch.gettecketno.clear();
		
						FlightSearch.gettecketno=gettickets;
						FlightSearch.tickttkype="E-Ticket";
		
						ticketveryflag=true;
						
						// Trying to get EMD number
						List<WebElement> emd = driver.findElements(By.xpath("//span[text()='EMD']/following-sibling::span"));
						gettReissuecaseEMD= new ArrayList<>();
						emd.forEach(a -> gettReissuecaseEMD.add(a.getText().trim()));
						
						// Get Residual EMD
						
						
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Reissue case ticktet number checking", "Reissue case ticket number not generated: " + e.getMessage(), e);
					}
		
					//Clicking on Done button
					driver.findElement(By.xpath("//button[text()='Done']")).click();
					Thread.sleep(2000);
					String FirstflightforAto=driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]//tbody[1]//child::td[@class='flight-name']/span")).getText();
					//Login.envwrite(Login.shareflightnm, FirstflightforAto);
					//Login.shareflightnm= FirstflightforAto;
					//Krishna - ReAssociate EMD after Voluntary Reissue Payment - Done
					/*String ErrorMsg=FlightSearch.getErrorMSGfromAppliction(driver, queryObjects);
					if(ErrorMsg.contains("Disassociated EMD(s) exist") || ErrorMsg.contains("Disassociated")) {
						if(FlightSearch.getTrimTdata(queryObjects.getTestData("Reassociate")).equalsIgnoreCase("Yes")) { 	
							ReassociateEMD(driver, queryObjects);
						}
					}*/

					if(FlightSearch.getTrimTdata(queryObjects.getTestData("Reassociate")).equalsIgnoreCase("Yes")) { 	
						String ErrorMsg=FlightSearch.getErrorMSGfromAppliction(driver, queryObjects);
						if(ErrorMsg.contains("Disassociated EMD(s) exist") || ErrorMsg.contains("Disassociated")) {

							ReassociateEMD(driver, queryObjects);
						} 
						else
							queryObjects.logStatus(driver, Status.FAIL, "Reassociating EMD", "EMD is not disassociated" , null);

					}
					
				}
				
				//Navira
				// to enroll connect miles - Copied from FlightSearch (Navira 04Mar)
				if (FlightSearch.getTrimTdata(queryObjects.getTestData("EnrollConnect")).equalsIgnoreCase("yes")){
					int EnrollPaxcnt;
					String paxcount=queryObjects.getTestData("EnrollPaxcnt");
					if(paxcount.isEmpty())
						EnrollPaxcnt=1;
					else
						EnrollPaxcnt=Integer.parseInt(paxcount);
					List<WebElement> Frequent_Flyer=driver.findElements(By.xpath("//span[@translate=\"pssgui.add.a.frequent.flyer\"]"));
						for(int i=0;i<EnrollPaxcnt;i++)
						{
							Frequent_Flyer=driver.findElements(By.xpath("//span[@translate=\"pssgui.add.a.frequent.flyer\"]"));
							Frequent_Flyer.get(0).click();
							FlightSearch.loadhandling(driver);
							Thread.sleep(200);
							FlightSearch fl = new FlightSearch();//Navira
							fl.EnrollConnectMiles(driver,queryObjects);
						}

				}
				if(!PymtError_Msg){
					//Check Baggage Rules Updated after Adding SSR
					String Rules = FlightSearch.getTrimTdata(queryObjects.getTestData("Rules"));//Jenny Feb 10
						if (!Rules.isEmpty()) {
							if (!Rules.isEmpty() && Rules.contains("BaggageRules")) {
								driver.findElement(By.xpath("//div[div[text()='Tickets']]")).click();
								FlightSearch.loadhandling(driver);
								List<WebElement> getetkts=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]"));
								String Origin = Rules.substring(Rules.indexOf(";")+1, Rules.indexOf("-"));
								String Destination = Rules.substring(Rules.indexOf("-")+1, Rules.length());
								for (int ct = 0; ct < getetkts.size(); ct++) {
									getetkts.get(ct).click();
									FlightSearch.loadhandling(driver);
									driver.findElement(By.xpath("//div[contains(text(),'Baggage Rules')]")).click();
									//Check the Baggage Rules are added for the onward segments
									List<WebElement> airports=driver.findElements(By.xpath("//div[contains(@class,'airport-code')]"));
									for (int ac = 0; ac < airports.size(); ac++) {
										if (airports.get(ac).getText().contains(Origin.join(" ", ""))) {
											driver.findElement(By.xpath("//div[contains(@class,'airport-code') and contains(text(),'"+Origin+" ')]/parent::div/following-sibling::div//i[contains(@class,'icon-forward')]")).click();
											if (driver.findElements(By.xpath("//div[contains(@ng-repeat,'rules in baggage')]")).size()>0) {
												queryObjects.logStatus(driver, Status.PASS, "Check the Baggage Rules updated after adding SSR", "Baggage Rules are updated" , null);
											} else {
												queryObjects.logStatus(driver, Status.FAIL, "Check the Baggage Rules updated after adding SSR", "Baggage Rules are not updated" , null);
											}
											driver.findElement(By.xpath("//div[contains(@class,'airport-code') and contains(text(),'"+Origin+" ')]/parent::div/following-sibling::div//i[contains(@class,'icon-arrow-down')]")).click();
											if (!airports.get(ac).getText().contains(" "+Destination) && airports.get(ac+1).getText().contains(" "+Destination)) {
												driver.findElement(By.xpath("//div[contains(text()[2],'"+Destination+"')]/../following-sibling::div[contains(@ng-if,'BaggageInfo')]/..//i[contains(@class,'icon-forward')]")).click();
												if (driver.findElements(By.xpath("//div[contains(@ng-repeat,'rules in baggage')]")).size()>0) {
													queryObjects.logStatus(driver, Status.PASS, "Check the Baggage Rules updated after adding SSR", "Baggage Rules are updated for the second segment" , null);
												} else {
													queryObjects.logStatus(driver, Status.FAIL, "Check the Baggage Rules updated after adding SSR", "Baggage Rules are not updated for the second segment" , null);
												}
												driver.findElement(By.xpath("//div[contains(text()[2],'"+Destination+"')]/../following-sibling::div[contains(@ng-if,'BaggageInfo')]/..//i[contains(@class,'icon-arrow-down')]")).click();
											}
										} 
										//BaggageRules;HAV-COR
										//BaggageRules;PTY-JFK
									}
								}
						}
					}
				}
				if (ticketveryflag && (!PymtError_Msg)) {
					Thread.sleep(2000);
					driver.findElement(By.xpath("//div[text()='Tickets']/parent::div")).click();
					FlightSearch.loadhandling(driver);
					//get Ticket amount
					List<WebElement> getetktsamt=driver.findElements(By.xpath("//md-content/div[contains(@class,'pax-info')]//div[@model='primaryTicket.TotalAmount']"));
					FlightSearch.gettecketamt = new ArrayList<>();
					FlightSearch.gettecketamt.clear();
					List<WebElement> reissueticketnumbers=driver.findElements(By.xpath("//span[contains(@class,'primary-ticket-number')]"));
					String refundsr=FlightSearch.getTrimTdata(queryObjects.getTestData("ReissueType"));
					aTicket = new ArrayList<>();
					reissueticketnumbers.forEach(a -> aTicket.add(a.getText().trim()));
					getetktsamt.forEach(a -> FlightSearch.gettecketamt.add(a.getText().trim()));
					FlightSearch.TktStatus=new ArrayList<>();
					for (WebElement ele : reissueticketnumbers) {
						String ticketnumber=ele.getText().trim();
						ticketnumber.contains("-");
						ticketnumber=ticketnumber.split("-")[0].trim();
						List<WebElement> currentticketstate=driver.findElements(By.xpath("//span[text()='"+ticketnumber+"']/parent::div/parent::div//td[contains(@ng-if,'ticketStatusUpdate')]"));
						boolean bticketstae=false;
						String stickstate=null;
						if (gettickets.contains(ticketnumber)){
							for (WebElement cureele : currentticketstate) {
								stickstate="Open";  // As per new functionality ticket state updated on 30-jan-2018  --- 'Open for Use' to 'Open' 
								if (cureele.getText().trim().equalsIgnoreCase(stickstate)||cureele.getText().trim().contains("CNTRL")){
									//getetktsamt.forEach(a -> FlightSearch.gettecketamt.add(a.getText().trim()));
									bticketstae=true;
								}
	
							}
							
							FlightSearch.TktStatus.add("Exchange");//Since this ticket is exchanged with Issued ticket
						}
						else{
							for (WebElement cureele : currentticketstate) {
								//stickstate="EXCHANGED";
								//String refundsr=FlightSearch.getTrimTdata(queryObjects.getTestData("ReissueType"));
								if(refundsr.equalsIgnoreCase("vol"))
									stickstate="EXCHANGED";
								else
									stickstate="EXCHD IRR";
								//if (!cureele.getText().trim().equalsIgnoreCase(stickstate))
								if (cureele.getText().trim().equalsIgnoreCase(stickstate))
									bticketstae=true;
								else if(Login.reissuetypeticktvalid.contains("vol") && Login.reissuetypeticktvalid.contains("invol"))
									bticketstae=true;
							}
							FlightSearch.TktStatus.add("issue");//This the first issued ticket
						}
						if (bticketstae) 
							queryObjects.logStatus(driver, Status.PASS, "After reissue particular ticket status checking ticket number is:"+ticketnumber, "After reisse this ticket status is showing correctly ticket statu is "+stickstate, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "After reissue particular ticket status checking ticket number is:"+ticketnumber, "After reisse this ticket status should  show correctly ticket statu is "+stickstate, null);
	
					}
					// get Ticket wise FOP detailssss

					if (driver.findElements(By.xpath("//span[contains(text(),'Open')]")).size()>0 && FlightSearch.getTrimTdata(queryObjects.getTestData("Void")).equalsIgnoreCase("Ticket"))
					{

						driver.findElement(By.xpath("//span[contains(@class,'md-select-icon')]")).click();
						Thread.sleep(3000);
						driver.findElement(By.xpath("//div[contains(@class,'md-active')]//div[contains(text(),'Void Ticket')]")).click();//Xpath Update by Himani
						FlightSearch.loadhandling(driver);
						String VoidNeg=FlightSearch.getTrimTdata(queryObjects.getTestData("VoidNeg"));
						if(VoidNeg.equalsIgnoreCase("Yes")) {
							driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
							FlightSearch.loadhandling(driver);
							if (driver.findElement(By.xpath("//td/span[contains(text(),'Open')]")).isDisplayed())
							{
								queryObjects.logStatus(driver, Status.PASS, "Ticket is Not voided: Still Open","Negative Case" ,null);
							}
							else 
							{
								queryObjects.logStatus(driver, Status.FAIL, "Ticket is voided: Issue","Negative Case",null);
							}
						}
						else if(VoidNeg.contentEquals("Reissuevoid")){
							driver.findElement(By.xpath("//button[contains(text(),'Void Ticket')]")).click();
							FlightSearch.loadhandling(driver);
							List<WebElement> errmsg=driver.findElements(By.xpath("//md-dialog[contains(@aria-label,'pssgui-dialog')]//span[contains(text(),'VOID NOT VALID')]"));
							if(errmsg.size()>0)
								queryObjects.logStatus(driver, Status.PASS, "After Reissuce try to void the Ticket: ","it is not Voided displaying currect message currectly : "+errmsg.get(0).getText().trim() ,null);
							else
								queryObjects.logStatus(driver, Status.FAIL, "After Reissuce try to void the Ticket: ","it should show currect message : "+errmsg.get(0).getText().trim() ,null);
						}
						else {
							driver.findElement(By.xpath("//button[contains(text(),'Void Ticket')]")).click();
							FlightSearch.loadhandling(driver);
							if (driver.findElement(By.xpath("//td[contains(text(),'VOIDED')]")).isDisplayed())
							{
								queryObjects.logStatus(driver, Status.PASS, "Ticket is voided: ","Positive case" ,null);
							}
							else 
							{
								queryObjects.logStatus(driver, Status.FAIL, "Ticket not voided: ","Positive case",null);
							}
						}
						
						return;
					}
					
					// CR166 EMD tab tax validation Start
					// Tax validation checking in EMD tab ....................
					if((!CR166_Taxcode.isEmpty()) && (!CR166_Tax_percent.isEmpty()) && (!PymtError_Msg)){
						if(FlightSearch.Manualquotecase==false){   // Automate Quote only need to check Tax code and amount validation .....
							driver.findElement(By.xpath("//div[text()='Tickets']/parent::div")).click();
							Thread.sleep(2000);
							driver.findElement(By.xpath("//div[text()='EMD']")).click();
							FlightSearch.loadhandling(driver);
							//List <WebElement> LgetEMDAmt=driver.findElements(By.xpath("//div[@model='emd.TotalFare']/div"));
							List <WebElement> LgetEMDNo=driver.findElements(By.xpath("//div[@model='emd.TotalFare']/following-sibling::div"));
							for (int iemdno = 0; iemdno < LgetEMDNo.size(); iemdno++) {	
								LgetEMDNo=driver.findElements(By.xpath("//div[@model='emd.TotalFare']/following-sibling::div"));
								String GetTickno=LgetEMDNo.get(iemdno).getText().trim();
								String get_EMD_AMt=driver.findElement(By.xpath("//div[@model='emd.TotalFare']/following-sibling::div[contains(text(),'"+GetTickno+"')]/preceding-sibling::div[1]/div")).getText().trim();
								double cut_EMD_AMt=Double.parseDouble(get_EMD_AMt.split(" ")[0]);
		
								if ((getpenamtamt.contains(cut_EMD_AMt)) && gettReissuecaseEMD.contains(GetTickno)) {
									queryObjects.logStatus(driver, Status.PASS, "CR166_Tax_percent checking in EMD Tab", "EMD Number is:: "+GetTickno , null);
									driver.findElement(By.xpath("//div[contains(text(),'"+GetTickno+"')]")).click();
									FlightSearch.loadhandling(driver);
									String getEMD_AMT=driver.findElement(By.xpath("//table[thead[tr[th[text()='Tax Code']]]]//tbody/tr[1]/td[1]/div/div")).getText().trim();
									double dgetEMD_Fee=Double.parseDouble(getEMD_AMT.split(" ")[0]); // Tax Fare amount
									String getEMD_TAX_Code=driver.findElement(By.xpath("//table[thead[tr[th[text()='Tax Code']]]]//tbody/tr[2]/td[2]")).getText().trim();
									
									String getEMD_TAX_AMT=driver.findElement(By.xpath("//table[thead[tr[th[text()='Tax Code']]]]//tbody/tr[2]/td[3]")).getText().trim();
									double getEMD_Tax_AMT_Actual=Double.parseDouble(getEMD_TAX_AMT);   // Tax Amount
									
									if(getEMD_TAX_Code.equalsIgnoreCase(CR166_Taxcode))
										queryObjects.logStatus(driver, Status.PASS, "CR166_Tax_percent checking in EMD Tab", "EMD tab tax Code display correctly" , null);
									else
										queryObjects.logStatus(driver, Status.FAIL, "CR166_Tax_percent checking in EMD Tab", "EMD Tab tax Code should display correctly Actual is :: "+getEMD_TAX_Code+" Expected is:: "+CR166_Taxcode , null);
									double calpercent2=Double.parseDouble(CR166_Tax_percent)/100;
									//Himani
									double manualEper= Double.parseDouble(FlightSearch.roundDouble(String.valueOf(getEMD_Tax_AMT_Actual/dgetEMD_Fee)));			
									double Aftercal_percent_value_inEMD=Double.parseDouble(FlightSearch.roundDouble_one(String.valueOf(dgetEMD_Fee*calpercent2)));
									//if(Aftercal_percent_value_inEMD==getEMD_Tax_AMT_Actual)
									if(calpercent2 == manualEper)
										queryObjects.logStatus(driver, Status.PASS, "CR166_Tax_percent calculation check in EMD Tab", "As per login POS it is calculating Percentage currectly as "+CR166_Tax_percent +" %", null);
									else if(FlightSearch.convertinteger(String.valueOf(calpercent2)).equals(FlightSearch.convertinteger(String.valueOf(manualEper))))
										queryObjects.logStatus(driver, Status.PASS, "CR166_Tax_percent calculation check in EMD Tab", "As per login POS it is calculating Percentage currectly as "+CR166_Tax_percent+ " %" , null);
									else
										queryObjects.logStatus(driver, Status.FAIL, "CR166_Tax_percent calculation check in EMD Tab", "As per login POS it should calculate Percentage currectly, Actual value::"+getEMD_Tax_AMT_Actual +"Expected::"+Aftercal_percent_value_inEMD , null);
									driver.findElement(By.xpath("//span[contains(text(),'All Passengers')]")).click();
									FlightSearch.loadhandling(driver);
								}
							}
						}
						else
							queryObjects.logStatus(driver, Status.WARNING, "CR166_Tax_percent calculation","Ticket created in Manual Quote so Tax validation not applicable",null);	
						
					}
					
					// CR166 EMD tab tax validation End
				} 
				}
			//Navira (for Search by E-tkt Number - After loading, lways goes to Tickets Tab
			/*driver.findElement(By.xpath("//div[text()='Order']")).click();
			FlightSearch.loadhandling(driver);
			reiwopay="";*/
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("Remarks_tab")).equalsIgnoreCase("yes"))
			{
				try {
					driver.findElement(By.xpath("//div[@translate='pssgui.order']")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//div[contains(text(),'Remarks')]")).click();
					Thread.sleep(2000);
					String VolQuoteID=driver.findElement(By.xpath("//div[contains(text(),'VOL MANUAL REISSUE QUOTE ID:')]")).getText();
					String[] QuoteID=VolQuoteID.split(":");
					String getQuoteID=QuoteID[1].trim();
					driver.findElement(By.xpath("//div[contains(text(),'VOL MANUAL REISSUE QUOTE ID:')]")).click();
					FlightSearch.loadhandling(driver);
					String getVolQuoteID=driver.findElement(By.xpath("//div[span[@translate='pssgui.quote.id']]")).getText();
					if(getVolQuoteID.contains(getQuoteID)) 
						queryObjects.logStatus(driver, Status.PASS, "Vol manual Reissue remarks Tab", "QuoteID is Same", null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "Vol manual Reissue remarks Tab", "QuoteID is Different", null);
					driver.findElement(By.xpath("//button[@translate='pssgui.cancel ']")).click();
					Thread.sleep(1000);
					if(driver.findElements(By.xpath("//button[@translate='pssgui.store.quote.id' and @disabled='disabled']")).size()>0) 
						queryObjects.logStatus(driver, Status.FAIL, "Vol manual Reissue remarks Tab Quote ID button checking", "Store QuoteID button is Enabled", null);
					else
						queryObjects.logStatus(driver, Status.PASS," Vol manual Reissue remarks Tab Quote ID button checking ", "Store QuoteID button is Disabled", null);
					
					if(driver.findElements(By.xpath("//button[@translate='pssgui.back' and @disabled='disabled']")).size()>0) 
						queryObjects.logStatus(driver, Status.FAIL, "Vol manual Reissue remarks Tab Cance button checking", "Cancel button is Enabled", null);
					else
						queryObjects.logStatus(driver, Status.PASS," Vol manual Reissue remarks Tab Cance button checking", "Cancel button is Disabled", null);
						
					if(driver.findElements(By.xpath("//button[@translate='pssgui.add.to.order' and @disabled='disabled']")).size()>0) 
						queryObjects.logStatus(driver, Status.FAIL, "Vol manual Reissue remarks Tab Add to order button checking", "Add to order button is Enabled", null);
					else
						queryObjects.logStatus(driver, Status.PASS," Vol manual Reissue remarks Tab Add to order button checking", "Add to order button is Disabled", null);
					
				}
				catch(Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Vol manual Reissue remarks Tab", "Vol manual Reissue remarks tab---> Verification Failed", e);
				}
			}
			
			
			////span[text()='2302168612658']/parent::div/parent::div//td[contains(@ng-if,'ticketStatusUpdate')]
			// resudial EMD case EMD validation --Start
			if(Residual_EMD.equalsIgnoreCase("yes") && FlightSearch.Manualquotecase==false) {   // Need to check this once
				FlightSearch.Residual_EMD_Validation(driver,queryObjects);
			}
			
			if(case_Type.equalsIgnoreCase("Refund")){
				Residual_EMD_Validation_reissue_Refund(driver, queryObjects);
			}
				
			// resudial EMD case EMD validation --End

			queryObjects.logStatus(driver, Status.INFO, "Reissue case", "Reissue Case done", null);
		}
		String assignSeat = FlightSearch.getTrimTdata(queryObjects.getTestData("AssignSeat"));
		if(assignSeat.equalsIgnoreCase("yes")) {
			String AssignseatAllPAX = FlightSearch.getTrimTdata(queryObjects.getTestData("AssignseatAllPAX"));
			String AssignSeatSinglePAX = FlightSearch.getTrimTdata(queryObjects.getTestData("AssignSeatSinglePAX"));
			String AssignSpecSeat = FlightSearch.getTrimTdata(queryObjects.getTestData("AssignSpecSeat"));
			String PaymentSeat = FlightSearch.getTrimTdata(queryObjects.getTestData("paymentSeat"));
			String totalTravels=driver.findElement(By.xpath(PaxCountXpath)).getText().trim();
			int totalTravers=Integer.parseInt(totalTravels);

			FlightSearch.assignSeat(driver, queryObjects, totalTravers, AssignseatAllPAX, AssignSeatSinglePAX, AssignSpecSeat);
			FlightSearch payseat=new FlightSearch();
			if(PaymentSeat.equalsIgnoreCase("yes"))
				payseat.paymentAfterassignseat(driver, queryObjects);
		}
		
		

	}

	public static boolean flightsearching(WebDriver driver,int viewnuberoflflightnoin,BFrameworkQueryObjects queryObjects) throws Exception{
		
		String Specifitclass=FlightSearch.getTrimTdata(queryObjects.getTestData("classType_Specific")).trim();
		if (!Specifitclass.equalsIgnoreCase("")){
			Specifitclass=Specifitclass.toUpperCase();
		}
		/*else
			Specifitclass="K";*/
		
		if (viewnuberoflflightnoin>1 )  {
				
			for (int icontflights = 0; icontflights < viewnuberoflflightnoin; icontflights++) {
				int kk=icontflights+1;
				if (icontflights>0 && (classselectbon=false)) // if multiple flights case(multey leg case) if first flight not selected no need verify second flight
					break;
				List<WebElement> lavbclass=driver.findElements(By.xpath("//div[div[div[div[div[span[span[i[@class='icon-arrow-up']]]]]]]]/div["+kk+"]//span[contains(@class,'active-state')]"));
				int avbclass=lavbclass.size();
				classselectbon=false;

				for (int iselectClass = 0; iselectClass < avbclass; iselectClass++) {

					String getavbclassnm=lavbclass.get(iselectClass).getText();
					if(SameClass.equalsIgnoreCase("yes")) {
						
						if (prevClass.equalsIgnoreCase(String.valueOf(getavbclassnm.charAt(0)))){
							int classnum=Integer.parseInt(FlightSearch.right(getavbclassnm,1));
							if (classnum>=totalTravellers  ) {
								lavbclass.get(iselectClass).click();
								classselectbon=true;
								break;
							}
						}
					}
					else {
						//if (!prevClass.equalsIgnoreCase(String.valueOf(getavbclassnm.charAt(0)))) {
						//if (!prevClass.equalsIgnoreCase(String.valueOf(getavbclassnm.charAt(0)==Specifitclass.charAt(0)))) {
							if (Specifitclass.isEmpty()) {
								int classnum=Integer.parseInt(FlightSearch.right(getavbclassnm,1));
								if (classnum>=totalTravellers ) {
									lavbclass.get(iselectClass).click();
									classselectbon=true;
									break;
								}
							}
							else{
								if (getavbclassnm.charAt(0)==Specifitclass.charAt(0)) {
									int classnum=Integer.parseInt(FlightSearch.right(getavbclassnm,1));
									if (classnum>=totalTravellers ) {
										lavbclass.get(iselectClass).click();
										classselectbon=true;
										break;
									}
								}
								
							}
							
						//}
					}

				}

				if (classselectbon==false) {
					queryObjects.logStatus(driver, Status.INFO, "specific Flight and specific Class selection", "specific flight was available but class was not available", null);
				}

			}
		}
		else {
			List<WebElement> lavbclass=driver.findElements(By.xpath(lavbclassXpath));
			int avbclass=lavbclass.size();
			classselectbon=false;
			for (int iselectClass = 0; iselectClass < avbclass; iselectClass++) {
				String getavbclassnm=lavbclass.get(iselectClass).getText();
				if(SameClass.equalsIgnoreCase("yes")) {
					if (prevClass.equalsIgnoreCase(String.valueOf(getavbclassnm.charAt(0)))){
						int classnum=Integer.parseInt(FlightSearch.right(getavbclassnm,1));
						if (classnum>=totalTravellers) {
							lavbclass.get(iselectClass).click();
							classselectbon=true;
							break;
						}
					}
				}
				else {
					//if (!prevClass.equalsIgnoreCase(String.valueOf(getavbclassnm.charAt(0)==Specifitclass.charAt(0)))) {
					//if (!prevClass.equalsIgnoreCase(String.valueOf(getavbclassnm.charAt(0)))) {	
						if (Specifitclass.isEmpty()) {
							int classnum=Integer.parseInt(FlightSearch.right(getavbclassnm,1));
							if (classnum>=totalTravellers) {
								lavbclass.get(iselectClass).click();
								classselectbon=true;
								break;
							}
						}
						else{
							if (getavbclassnm.charAt(0)==Specifitclass.charAt(0)) {
								int classnum=Integer.parseInt(FlightSearch.right(getavbclassnm,1));
								if (classnum>=totalTravellers) {
									lavbclass.get(iselectClass).click();
									classselectbon=true;
									break;
								}
							}
							
						}
						
					//}
				}
			}
		}
		return classselectbon;	
	}


	public static void DeleteOldSegments(WebDriver driver, BFrameworkQueryObjects queryObjects,String DelSeg) throws Exception {

		//int totIcons = driver.findElements(By.xpath("//span[@class='pssgui-design-label-5 ng-binding' and contains(text(),'2-')]")).size();
		List<WebElement> seg= driver.findElements(By.xpath("//i[@class='icon-removed' and contains(@ng-class,'flight.MarriageGrp')]"));
		//Get excel data
		//String DeleteSeg = FlightSearch.getTrimTdata(queryObjects.getTestData("DeleteOldSeg"));
		if (DelSeg.equalsIgnoreCase("All")) {
			int segCount=seg.size();
			int totIcons = driver.findElements(By.xpath("//i[@class='icon-removed' and contains(@ng-class,'flight.MarriageGrp')]")).size();
			for (int Itmnum = 0; Itmnum < segCount; Itmnum++) {
				seg= driver.findElements(By.xpath("//i[@class='icon-removed' and contains(@ng-class,'flight.MarriageGrp')]"));
				FlightSearch.loadhandling(driver);
				seg.get(0).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[@class='pssgui-modal-content hpe-pssgui flight-result ng-isolate-scope layout-column flex']//button[@type='button'][2]")).click();
				FlightSearch.loadhandling(driver);
			}			

		} else {
			String[] Deletes;
			int DeleteSize=0;
			if(DelSeg.contains(";")){
				Deletes=DelSeg.split(";");
				DeleteSize=Deletes.length;
				for (int i = 0; i < DeleteSize; i++) {
					DeleteOldSegments_SegNum(driver, queryObjects, Integer.parseInt(Deletes[i])-1);	
				}
			}
			else
			if (DelSeg.equals(""))///Deleting first segment
				DelSeg="1";
			if(!DelSeg.contains(";")){
				if(Integer.parseInt(DelSeg) >0)
					DeleteOldSegments_SegNum(driver, queryObjects, Integer.parseInt(DelSeg)-1); 
			}
		}
	}

	public static void DeleteOldSegments_SegNum(WebDriver driver, BFrameworkQueryObjects queryObjects, int segNum) {
		// TODO Auto-generated method stub
		String lltempXpath = "//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]]["+(segNum+1)+"]";
		prevDate = driver.findElement(By.xpath(lltempXpath + "//td[@class='date']//span")).getText().trim();
		driver.findElements(By.xpath("//i[@class='icon-removed' and contains(@ng-class,'flight.MarriageGrp')]")).get(segNum).click();
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//div[@class='pssgui-modal-content hpe-pssgui flight-result ng-isolate-scope layout-column flex']//button[@type='button'][2]")).click();
		FlightSearch.loadhandling(driver);

	}
	public static String Manual_reissue(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		queryObjects.logStatus(driver, Status.PASS, "Manual reissue process started", "Manual reissue process started", null);
		String ManuQuote_reisssue_refund= FlightSearch.getTrimTdata(queryObjects.getTestData("ManuQuote_reisssue_refund"));
		String ManuQuote_reisssue_zero_payment= FlightSearch.getTrimTdata(queryObjects.getTestData("ManuQuote_reisssue_zero_payment"));
		String Fareamt="2000";
		double TotalFare=0.0;
		int paxtotal=0;
		List<WebElement> paxcount=driver.findElements(By.xpath("//div [@class='pax-info-result']"));		
		FlightSearch.totalnoofPAX=paxcount.size();	 // himani  need to check if pax
		paxtotal=FlightSearch.totalnoofPAX;
		Thread.sleep(1000);
		
		if(FlightSearch.conjunctiveTicketManualreissue)  // this is for conjunctive ticket 
			paxtotal=FlightSearch.totalnoofPAX*2;
		
		driver.findElement(By.xpath("//md-select[contains(@aria-label,'Actions')]")).click();		
		FlightSearch.loadhandling(driver);
		if(driver.findElement(By.xpath("//md-option[@value='voluntary-manual-reissue' and (@aria-disabled='false')]")).isEnabled())
			driver.findElement(By.xpath("//md-option[@value='voluntary-manual-reissue' and (@aria-disabled='false')]")).click();
		else
			queryObjects.logStatus(driver, Status.FAIL, "Manual reissue link disabled", "Manual reissue link disabled", null);
		
		for (int Nopax=1;Nopax<=paxtotal;Nopax++){
			if(driver.findElements(By.xpath("//div[@translate ='pssgui.voluntary.manual.ticket.reissue']")).size()>0){
				FlightSearch.loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Manual reisue Detailse", Nopax+" PAX Detailes entering", null);
				driver.findElement(By.xpath("//div[contains(text(),'Select Coupon')]//following::md-checkbox[@ng-model='flightResult.isAllChecked' and (@aria-label='coupon-check')][1]")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath(nextbuttonXpath)).click();
				FlightSearch.loadhandling(driver);
				List<WebElement> farebase=driver.findElements(By.xpath("//input[@name='fare basis']"));
				List<WebElement> getclass=driver.findElements(By.xpath("//td[contains(@class,'class-code')]"));
	
				for (int ifar = 0; ifar < farebase.size(); ifar++) {
	
					String classcode=getclass.get(ifar).getText();
					farebase.get(ifar).clear();
					farebase.get(ifar).sendKeys(classcode);
					Thread.sleep(1000);
				}
	
				List<WebElement> bagcode=driver.findElements(By.xpath("//input[@name='baggage allowance']"));
				for (int ifar = 0; ifar < bagcode.size(); ifar++) {
					bagcode.get(ifar).sendKeys("2PC");
					Thread.sleep(1000);
				}
				List<WebElement> NVAcode=driver.findElements(By.xpath("//input[@name='not-valid-after-date']"));
				for (int ifar = 0; ifar < NVAcode.size(); ifar++) {
					NVAcode.get(ifar).sendKeys("2MAR");
					Thread.sleep(1000);
				}
	
	
				if (ManuQuote_reisssue_zero_payment.equalsIgnoreCase("YES"))
					Fareamt="0";
				//else
					//Fareamt=driver.findElement(By.xpath("//input[@name='equivalentfare amount']")).getAttribute("value");
				//Fareamt=driver.findElement(By.xpath("//input[@name='Fare amount']")).getAttribute("value");  // due to new CR 176 code updating
				
				// due to new CR 176 code updating
				if(FlightSearch.currencyType.trim().equalsIgnoreCase("ARS")|| FlightSearch.currencyType.trim().equalsIgnoreCase("COP") ){



					List<WebElement> equlfareamtt=driver.findElements(By.xpath("//div[span[text()='Equivalent Fare']]/following-sibling::div[1]"));
					if(equlfareamtt.size()>0)
						Fareamt=equlfareamtt.get(0).getText().trim();
					if(Fareamt.isEmpty())
						Fareamt="2000";
					
					Thread.sleep(2000);
					driver.findElement(By.xpath("//input[@name='New Equivalent amount']")).sendKeys(Fareamt);  //equivalent fare net amount
					
					List<WebElement> equlfareCurrL=driver.findElements(By.xpath("//div[span[text()='Equivalent Fare']]/following-sibling::div[3]"));
					String FareamtCurr="";
					if(equlfareamtt.size()>0)
						FareamtCurr = equlfareCurrL.get(0).getText().trim();
					if(FareamtCurr.isEmpty())
						FareamtCurr=FlightSearch.currencyType.trim();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//input[@name='New Equivalent amount currency']")).sendKeys(FlightSearch.currencyType.trim());  //equivalent fare net amount Currency
					//driver.findElement(By.xpath("//input[@name='New Equivalent amount currency']")).sendKeys(FareamtCurr);  //equivalent fare net amount Currency
				}
				/*else { // this is for other than Ars and cop currency
					driver.findElement(By.xpath("//input[@name='New Base amount']")).sendKeys(Fareamt); 
					String BasetCurr=driver.findElement(By.xpath("//div[span[text()='Base fare']]/following-sibling::div[3]")).getText().trim();
					driver.findElement(By.xpath("//input[@name='New Base amount currency']")).sendKeys(BasetCurr);
				}*/
				// Adding TAX   Add_Tax_Nm=xd;pa 
				
				String Add_Tax_Nm=queryObjects.getTestData("Manual_reissuce_Add_Tax_Nm");
				double Taxamt=0;
				List<WebElement> taddetails = null;
				List<WebElement> taddpaid;
				List<WebElement> taddrefund;
				List<WebElement> taddcharge = null;
				if(!Add_Tax_Nm.isEmpty()){
					queryObjects.logStatus(driver, Status.PASS, "Manual Reissuce case Try to enter Tax", "we are able to enter Tax data Tax nm: "+Add_Tax_Nm, null);
					String []Add_Tax_Nm_arry=Add_Tax_Nm.split(";");
					List<WebElement> taxexist=driver.findElements(By.xpath("//div[@ng-if='!ticketTax.isNew']"));
					int taxexistcnt=taxexist.size();
					
					for (int iadd = 0; iadd < Add_Tax_Nm_arry.length; iadd++) {
						driver.findElement(By.xpath("//span[@translate='pssgui.add.new.tax']")).click();
						Thread.sleep(3000);
					}
					taddetails=driver.findElements(By.xpath("//input[@name='tax code']"));
					taddpaid=driver.findElements(By.xpath("//input[@name='paid']"));
					taddrefund=driver.findElements(By.xpath("//input[@name='tax refunded']"));
					taddcharge=driver.findElements(By.xpath("//input[@name='tax charged']"));
					
					for (int itax = 0; itax < Add_Tax_Nm_arry.length; itax++) {
						
						if(Add_Tax_Nm_arry[itax].equalsIgnoreCase("xf")){
							taddetails.get(itax).sendKeys(Add_Tax_Nm_arry[itax]);
							taddetails.get(itax).sendKeys(Keys.TAB);
							Thread.sleep(2000);
							int xval;
							if(taxexistcnt>0)
								xval=(itax+taxexistcnt)-1;
							else
								xval=0;
							taddpaid=driver.findElements(By.xpath("//input[@name='paid']"));
							taddrefund=driver.findElements(By.xpath("//input[@name='tax refunded']"));
							Thread.sleep(2000);
								taddpaid.get(xval).sendKeys("20");
								boolean xfvalidationpaid=(driver.findElements(By.xpath("//input[@name='tax refunded' and (@disabled='disabled')]")).size()>0);
								if(xfvalidationpaid)
									queryObjects.logStatus(driver, Status.PASS, "Manual Reissuce case XF Tax validation: Enter value in paid field and checking refund field status", "Refund Field Disabled ", null);
								else
									queryObjects.logStatus(driver, Status.FAIL, "Manual Reissuce case XF Tax validation: Enter value in paid field and checking refund field status", "Refund Field should Disabled ", null);
								taddpaid.get(xval).clear();
								Thread.sleep(2000);
								// Enter value in refund field and checking paid field status ..........
								taddrefund.get(xval).sendKeys("20");
								boolean xfvalidationrefund=(driver.findElements(By.xpath("//input[@name='paid' and (@disabled='disabled')]")).size()>0);
								if(xfvalidationrefund)
									queryObjects.logStatus(driver, Status.PASS, "Manual Reissuce case XF Tax validation: Enter value in refund field and checking paid field status", "paid Field Disabled ", null);
								else
									queryObjects.logStatus(driver, Status.PASS, "Manual Reissuce case XF Tax validation: Enter value in refund field and checking paid field status", "paid Field should Disabled ", null);
								Thread.sleep(2000);
								taddrefund.get(xval).clear();
								Thread.sleep(2000);
								taddetails.get(itax).clear();
								Thread.sleep(2000);
						}
						
						taddetails.get(itax).sendKeys(Add_Tax_Nm_arry[itax]);
						taddcharge.get(itax+taxexistcnt).sendKeys("20");
						Taxamt=Taxamt+20;
						
					}
					queryObjects.logStatus(driver, Status.PASS, "Manual Reissuce case Try to enter Tax", "we are able to enter Tax data", null);
				}
				else{
					List<WebElement> taxnml=driver.findElements(By.xpath("//div[contains(@pssgui-toggle,'ticketTax.toggleState')]"));
					if(!(taxnml.size()>0)) {  // it means if no tax available adding tax else skip
						driver.findElement(By.xpath("//span[@translate='pssgui.add.new.tax']")).click();
						Thread.sleep(3000);
						driver.findElement(By.xpath("//input[@name='tax code']")).sendKeys("AB");
						driver.findElement(By.xpath("//input[@name='tax charged']")).sendKeys("20");
						Taxamt=Taxamt+20;
					}
					else{   // this is for tax code available 
						List<WebElement> tax_charge=driver.findElements(By.xpath("//input[@name='tax charged']"));
						List<WebElement> get_tax=driver.findElements(By.xpath("//div[contains(@pssgui-toggle,'ticketTax.toggleState')]//toggle-title/div[2]/div[1]"));
						//List<WebElement> tax_Code=driver.findElements(By.xpath("//toggle-title[@class='ng-scope layout-align-space-between-center layout-row flex padding-left-5' and 1]/div[1]"));
						List<WebElement> tax_Code=driver.findElements(By.xpath("//div[contains(@pssgui-toggle,'ticketTax.toggleState')]//toggle-title//div[@ng-if='!ticketTax.isNew']"));
						for (int Taxi = 0; Taxi < taxnml.size(); Taxi++) {
							get_tax=driver.findElements(By.xpath("//div[contains(@pssgui-toggle,'ticketTax.toggleState')]//toggle-title/div[2]/div[1]"));
							//tax_Code=driver.findElements(By.xpath("//div[contains(@pssgui-toggle,'ticketTax.toggleState')]//toggle-title//div[@ng-if='!ticketTax.isNew']"));
							String taxaa=get_tax.get(Taxi).getText();
							String cod=tax_Code.get(Taxi).getText().trim();
							if(!cod.equalsIgnoreCase("XF")){
								if(taxaa.isEmpty()){   // tax amount not available
									tax_charge.get(Taxi).sendKeys("20");
									Taxamt=Taxamt+20;
								}
								else{
									tax_charge.get(Taxi).sendKeys(taxaa);
									Taxamt=Taxamt+Double.parseDouble(taxaa);
									}
							}
								
						}
					}
					
					
				}
				
				//verification of add to order button
				if(driver.findElements(By.xpath("//button[text()='Store Quote ID' and @disabled='disabled']")).size()>0) 
					queryObjects.logStatus(driver, Status.PASS, "Voluntary Manual Reissue ", "Store Quote ID button Disabled Before adding currency", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Voluntary Manual Reissue ", "Store Quote ID button Enabled Before adding currency", null);	
				String totlamt="";
				Double Temtotal=0.0;
				if(FlightSearch.currencyType.trim().equalsIgnoreCase("ARS")|| FlightSearch.currencyType.trim().equalsIgnoreCase("COP") ){



					String base_Cur=driver.findElement(By.xpath("//div[span[text()='Base fare']]/following-sibling::div[3]")).getText().trim();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//input[@name='New Base amount currency']")).sendKeys(base_Cur);
					String basefare=driver.findElement(By.xpath("//div[span[text()='Base fare']]/following-sibling::div[1]/span")).getText().trim();
					Thread.sleep(2000);
					if(Double.parseDouble(basefare)>0)
						driver.findElement(By.xpath("//input[@name='New Base amount']")).sendKeys(basefare);
					else
						driver.findElement(By.xpath("//input[@name='New Base amount']")).sendKeys(Fareamt);
					
					//TotalFare=TotalFare+Double.parseDouble(Fareamt)+Taxamt;
					Temtotal=Double.parseDouble(Fareamt)+Taxamt;
					totlamt=String.valueOf(Double.parseDouble(Fareamt)+Taxamt);
				}
				else{
					String base_Cur=driver.findElement(By.xpath("//div[span[text()='Base fare']]/following-sibling::div[3]")).getText().trim();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//input[@name='New Base amount currency']")).sendKeys(base_Cur);
					String basefare=driver.findElement(By.xpath("//div[span[text()='Base fare']]/following-sibling::div[1]/span")).getText().trim();
					Thread.sleep(2000);
					if(Double.parseDouble(basefare)>0){
						driver.findElement(By.xpath("//input[@name='New Base amount']")).sendKeys(basefare);
						//TotalFare=TotalFare+Double.parseDouble(basefare)+Taxamt;
						Temtotal=Double.parseDouble(basefare)+Taxamt;
						totlamt=String.valueOf(Double.parseDouble(basefare)+Taxamt);
					}
					else{
						driver.findElement(By.xpath("//input[@name='New Base amount']")).sendKeys(Fareamt);
						//TotalFare=TotalFare+Double.parseDouble(Fareamt)+Taxamt;
						Temtotal=Double.parseDouble(Fareamt)+Taxamt;
						totlamt=String.valueOf(Double.parseDouble(Fareamt)+Taxamt);
					}
				}
	
					
				totlamt=FlightSearch.roundDouble(totlamt);
				driver.findElement(By.xpath("//input[@aria-label='Total Price']")).clear();
				driver.findElement(By.xpath("//input[@aria-label='Total Price']")).sendKeys(totlamt);
				
				if(FlightSearch.currencyType.equalsIgnoreCase("MXN")||FlightSearch.currencyType.equalsIgnoreCase("DOP")||FlightSearch.currencyType.equalsIgnoreCase("COP"))
					Temtotal=Double.parseDouble(FlightSearch.convertinteger(String.valueOf(Temtotal)));
				TotalFare=TotalFare+Temtotal;
				
				if (ManuQuote_reisssue_refund.equalsIgnoreCase("YES"))
					if(!Fareamt.equalsIgnoreCase("0"))
						driver.findElement(By.xpath("//md-checkbox[@aria-label='is-refund']")).click();
					else
						queryObjects.logStatus(driver, Status.PASS, "Manual reissue case is refund funtionality", "Manual reissue case Amount is zero so we are not selecting is refund check box", null);
				Thread.sleep(1000);
				driver.findElement(By.xpath("//input[@aria-label='currency' and (contains(@ng-model,'TotalFares'))]")).sendKeys(FlightSearch.currencyType);
				//driver.findElement(By.xpath("//button[text()='Add To Order']")).click();  ///manual kishore
				driver.findElement(By.xpath("//button[text()='Store Quote ID']")).click();
				FlightSearch.loadhandling(driver);
			}
		}
		
		Reopen_storeQtoreID(driver, queryObjects);
		driver.findElement(By.xpath("//button[text()='Add To Order']")).click();
		FlightSearch.loadhandling(driver);
		TotalFare=Double.parseDouble(FlightSearch.roundDouble(String.valueOf(TotalFare)));
		String Current_Currency=driver.findElement(By.xpath("//div[@action='saleOfficeInfo']/div[3]")).getText();
		
		//if(Current_Currency.equalsIgnoreCase("MEX")||Current_Currency.equalsIgnoreCase("CLP")||Current_Currency.equalsIgnoreCase("COP")||Current_Currency.equalsIgnoreCase("MXN")  )
		/*if(Current_Currency.equalsIgnoreCase("MXN"))
			TotalFare=Double.parseDouble(FlightSearch.convertinteger(String.valueOf(TotalFare)));*/
		
		return String.valueOf(TotalFare);
	}
	
	public static Map storeFlightdetails(WebDriver driver,Map Mapnm){
		
		//Map<String,ArrayList<String>> Mapnm=new HashMap<String,ArrayList<String>>();
	    
		 int Segmentsize=driver.findElements(By.xpath("//tr[@ng-repeat='flight in flightResult.segments']")).size();
		 String segnum="SegmentNum";
		 ArrayList<String> segparameter=new ArrayList<String>();
		 List<WebElement> Segvale=new ArrayList<WebElement>();
		 for(int k=0;k<Segmentsize;k++)
		 {
			 Segvale=new ArrayList<WebElement>();
			 segnum=segnum+k;
			for(int l=0;l<1;l++)
			{  int j=k+1;
				 Segvale = driver.findElements(By.xpath("//tr[@ng-repeat='flight in flightResult.segments']["+j+"]/td"));
				int totalTKTs = Segvale.size();
				//etktsnum = new ArrayList<>();

				Segvale.forEach(a -> segparameter.add(a.getText().trim()));
			
			}
			Mapnm.put(segnum,segparameter);
			//Segvale.clear();
		 }
		 return Mapnm;
	}
	
public static void CyberSoursePNR(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{
		
		/*String PNNN=driver.findElement(By.xpath("//div[@action='pnr']/div/div/div[1]/div[1]")).getText().trim();
		driver.findElement(By.xpath("//pssgui-search-panel[@label='pssgui.search.reservations']/div/md-input-container/input[@ng-model='searchPanel.searchText']")).sendKeys(PNNN);
		Thread.sleep(500);
		driver.findElement(By.xpath("//pssgui-search-panel[@label='pssgui.search.reservations']/div/i[@class='icon-search']")).click();
		FlightSearch.loadhandling(driver);
		Thread.sleep(500);*/
		//Click on order tab
		driver.findElement(By.xpath("//div[@translate='pssgui.order']")).click();
		Thread.sleep(300);
		//Click on Remarks tab
		driver.findElement(By.xpath(remarks_Xpath)).click();
		Thread.sleep(2000);
		List<WebElement>PNRS_inremark=driver.findElements(By.xpath("//div[contains(@class, 'pssgui-link ng-binding')and contains(text(), ':') and contains(text(), '')]"));
		 List<String> remarkpnrs=new ArrayList<String>();

		//PNRS_inremark.forEach(a -> remarkpnrs.add(a.getText()));
		for (int iPnr_rem = 0; iPnr_rem < PNRS_inremark.size(); iPnr_rem++) {
			String PNR_orgerid=PNRS_inremark.get(iPnr_rem).getText().trim();
			if ((!PNR_orgerid.contains("VOL MANUAL REISSUE QUOTE ID")) && (!PNR_orgerid.contains("102"))   ) {
				remarkpnrs.add(PNR_orgerid);
			}
			
		}
		
		
		int iTemp=0;	
		if(!FlightSearch.ppspaymentmodesingle.isEmpty()) {
			if (FlightSearch.ppspaymentmodesingle.equalsIgnoreCase("creditcard"))
				iTemp=1;
		}
		else if (!FlightSearch.ppspaymentmodedouble.isEmpty()) {
			String[] paymodearry=FlightSearch.ppspaymentmodedouble.split(";");
			for (String paytype : paymodearry) {
				if (paytype.equalsIgnoreCase("creditcard")) 
					iTemp=iTemp+1;
			}
			
		}
			FlightSearch.CyberPNR="";
			for (int iremark = iTemp; iremark < remarkpnrs.size(); iremark++) {
				FlightSearch.PNRTempPath = "//div[contains(@class, 'pssgui-link ng-binding') and contains(text(), ':') and contains(text(), '"+remarkpnrs.get(iremark)+"')]";
				if(FlightSearch.CyberPNR.isEmpty())
					FlightSearch.CyberPNR=(driver.findElement(By.xpath(FlightSearch.PNRTempPath)).getText().trim())+";";
				else
					FlightSearch.CyberPNR=FlightSearch.CyberPNR+(driver.findElement(By.xpath(FlightSearch.PNRTempPath)).getText().trim())+";";
				
			}	
		
	}
	
	public void modifyName(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException, Exception{
		
		String ModSurname = FlightSearch.getTrimTdata(queryObjects.getTestData("Modify_Surname"));
		String ModFname =FlightSearch.getTrimTdata(queryObjects.getTestData("Modify_FirstName"));
		String PaxType=FlightSearch.getTrimTdata(queryObjects.getTestData("paxType"));
		driver.findElement(By.xpath(passangerTabXpath)).click();	
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//div[div[text()='Personal Information']]")).click();	
		FlightSearch.loadhandling(driver);
		if(!PaxType.isEmpty()) {
			if(PaxType.equalsIgnoreCase("infant")) {
			driver.findElement(By.xpath("//md-content//div[contains(@ng-repeat,'paxInfo in leftPanel')]//i[contains(@class,'ng-scope icon-infant')]/../../..//div//span[contains(@ng-click,'paxInfo')]")).click();
			Thread.sleep(1000);
			}
			if(PaxType.equalsIgnoreCase("child")) {
			driver.findElement(By.xpath("//md-content//div[contains(@ng-repeat,'paxInfo in leftPanel')]//i[contains(@class,'ng-scope icon-unaccompained-minor')]/../../..//div//span[contains(@ng-click,'paxInfo')]")).click();
			Thread.sleep(1000);
			}
			if(PaxType.equalsIgnoreCase("adult")) {
			//to-do have to find xpath for adult and click	
			}
		}
		driver.findElement(By.xpath("//button[@translate='pssgui.edit']")).click();	
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//input[@ng-model='personalInfo.model.Firstname']")).clear();
		//String modifysurname=queryObjects.getTestData("Modify_Surname");
		driver.findElement(By.xpath("//input[@ng-model='personalInfo.model.Firstname']")).sendKeys(ModSurname);
		//driver.findElement(By.xpath("//button[text()='OK']")).click();
		driver.findElement(By.xpath("//input[@ng-model='personalInfo.model.Lastname']")).clear();
		driver.findElement(By.xpath("//input[@ng-model='personalInfo.model.Lastname']")).sendKeys(ModFname);
		//driver.findElement(By.xpath("//input[@ng-model='personalInfo.model.Surname']")).sendKeys(ModFname);
		driver.findElement(By.xpath("//button[text()='OK']")).click();
		FlightSearch.loadhandling(driver);
		if(!PaxType.isEmpty()) {
			if(PaxType.equalsIgnoreCase("infant")) {
			driver.findElement(By.xpath("//md-content//div[contains(@ng-repeat,'paxInfo in leftPanel')]//i[contains(@class,'ng-scope icon-infant')]/../../..//div//span[contains(@ng-click,'paxInfo')]")).click();
			Thread.sleep(1000);
			}
			if(PaxType.equalsIgnoreCase("child")) {
			driver.findElement(By.xpath("//md-content//div[contains(@ng-repeat,'paxInfo in leftPanel')]//i[contains(@class,'ng-scope icon-unaccompained-minor')]/../../..//div//span[contains(@ng-click,'paxInfo')]")).click();
			Thread.sleep(1000);
			}
			if(PaxType.equalsIgnoreCase("adult")) {
			//to-do have to find xpath for adult and click	
			}
		}
		ModFname=ModFname.trim().toUpperCase();
		ModSurname=ModSurname.trim().toUpperCase();
		
		try{
			//driver.findElement(By.xpath("//div[div[span[contains(text(),'"+ ModFname +"') and contains(text(),'"+ ModSurname +"')]]]//span[contains(@ng-click,'frequentFlyer')]")).click();
			driver.findElement(By.xpath("//div[div[span[contains(text(),'"+ ModFname +"') and contains(text(),'"+ ModSurname +"')]]]")).click();
			//driver.findElement(By.xpath("//span[contains(text(),'"+ slastName +"')]")).click();
			queryObjects.logStatus(driver, Status.PASS, "change Firstnm", "First name was updated " , null);
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "change Firstnm", "First name not updated " + e.getLocalizedMessage(), e);
		}
		/*
		String getmodifynme=driver.findElement(By.xpath("//input[@ng-model='personalInfo.model.Lastname']")).getAttribute("value");
		if(getmodifynme.equalsIgnoreCase(ModFname))
			queryObjects.logStatus(driver, Status.PASS, "Modify Name validation", "Name updated succesfully", null);
		else
			queryObjects.logStatus(driver, Status.FAIL, "Modify Name validation", "unable to update Name", null);*/
	}
	
public static void ReassociateEMD(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{
		
		List<String>AllPaxOpenTicket = new ArrayList<>();
		AllPaxOpenTicket.clear();

		List<String>AllPaxName = new ArrayList<>();
		AllPaxName.clear();
		
		List<String>EMDPaxName = new ArrayList<>();
		EMDPaxName.clear();
		
		int TotalAssocaiteEMDCount=0;
		int AssociatedTOT = 0;
		driver.findElement(By.xpath("//div[text()='Tickets']/parent::div")).click();
		FlightSearch.loadhandling(driver);
		Thread.sleep(2000);
		
		List<WebElement> AllPaxNameList=driver.findElements(By.xpath("//span[@ng-if=' paxInfo.TicketedFullName ']"));
		AllPaxNameList.forEach(a -> AllPaxName.add(a.getText().trim()));
		
		List<WebElement> TicketNumber=driver.findElements(By.xpath("//span[contains(@class,'primary-ticket-number')]"));

			
		for (WebElement Lab : TicketNumber) {
			String ticketnumber=Lab.getText().trim();
			ticketnumber.contains("-");
            ticketnumber=ticketnumber.split("-")[0].trim();
			List<WebElement> currentticketstate=driver.findElements(By.xpath("//span[text()='"+ticketnumber+"']/parent::div/parent::div//td[contains(@ng-if,'ticketStatusUpdate')]"));
				if(currentticketstate.get(0).getText().trim().equalsIgnoreCase("Open")) {
					 int firstIndex = AllPaxOpenTicket.indexOf(ticketnumber);
					 if (firstIndex == -1)
						 AllPaxOpenTicket.add(ticketnumber);		
				}
				
		}

		driver.findElement(By.xpath("//div[text()='EMD']")).click();
		FlightSearch.loadhandling(driver);
		Thread.sleep(2000);
		//22Mar - Navira
		String emdConfirm = FlightSearch.getTrimTdata(queryObjects.getTestData("emdConfirm"));//Column 4
		if(emdConfirm.equalsIgnoreCase("yes")) {
			FlightSearch.emdConfirmation(driver,queryObjects);

		}
		List<WebElement> EMDPaxNameList=driver.findElements(By.xpath("//div[@class='pssgui-design-sub-heading-6 word-break ng-binding']"));
		EMDPaxNameList.forEach(a -> EMDPaxName.add(a.getText().trim()));
		
		for (int pax = 0; pax< EMDPaxNameList.size();pax++) {
			
			String PAXName=EMDPaxNameList.get(pax).getText().trim();
			
			List<WebElement> CollapseTab=driver.findElements(By.xpath("//div[div[div[div[toggle-title[div[text()='"+PAXName+"']]]]]]//i[@class='toggle-arrow ng-scope icon-forward']"));
		
			for(int ctab = 0; ctab < CollapseTab.size();ctab++ ) {
				Thread.sleep(2000);
				CollapseTab.get(ctab).click();
				Thread.sleep(2000);
			}
			
			List<WebElement>DeassociateSpan=driver.findElements(By.xpath("//div[div[div[div[toggle-title[div[text()='"+PAXName+"']]]]]]//table/tbody/tr/td//span[text()='Disassociated']"));
			List<WebElement>Associatedlist=driver.findElements(By.xpath("//div[div[div[div[toggle-title[div[text()='"+PAXName+"']]]]]]//table/tbody/tr/td//span[text()='Associated']"));
			
			int Dsize = DeassociateSpan.size();
			int PaxAssocaiteEMDCount = 0;
			//Himani
			int Asize = Associatedlist.size();
			if (Asize>0 && (FlightSearch.getTrimTdata(queryObjects.getTestData("Reassociate")).equalsIgnoreCase("check"))) {
				AssociatedTOT+=1;
				queryObjects.logStatus(driver, Status.PASS, PAXName, "Found Associated SSr ", null);
			}
			if(Dsize>0)
				queryObjects.logStatus(driver, Status.PASS, PAXName, "Found Deassocaited EMD ", null);
			else
				queryObjects.logStatus(driver, Status.PASS, PAXName, "No Deassocaited EMD Found ", null);
			if((FlightSearch.getTrimTdata(queryObjects.getTestData("Reassociate")).equalsIgnoreCase("yes"))) {
				for(int c = 0; c<Dsize;c++) {
					Thread.sleep(2000);
					FlightSearch.loadhandling(driver);
					Thread.sleep(2000);				
					DeassociateSpan=driver.findElements(By.xpath("//div[div[div[div[toggle-title[div[text()='"+PAXName+"']]]]]]//table/tbody/tr/td//span[text()='Disassociated']"));
					if(DeassociateSpan.size()==0)
						break;
					List<WebElement> CouponNo = driver.findElements(By.xpath("//div[div[div[div[toggle-title[div[text()='"+PAXName+"']]]]]]//table/tbody/tr[td//span[text()='Disassociated']]/td[2]"));
					PaxAssocaiteEMDCount = PaxAssocaiteEMDCount+1;
					DeassociateSpan.get(0).click();
					FlightSearch.loadhandling(driver);
					List<WebElement> TicketNumberInput = driver.findElements(By.xpath("//input[@name='TicketNumber']"));
					List<WebElement> CouponNumberInput = driver.findElements(By.xpath("//input[@name='CouponNumber Number']"));
					int t = AllPaxName.indexOf(String.join(",",PAXName.split(", ")[0],PAXName.split(", ")[1]));
					
					String S = AllPaxOpenTicket.get(t);
					TicketNumberInput.get(0).sendKeys(S);
					String CN = CouponNo.get(0).getText();
					CN = CN.substring(1);
					CouponNumberInput.get(0).sendKeys(CN);
					
					driver.findElement(By.xpath("//button[contains(text(),'Associate')]")).click();  // clicking reservation menu bar
					FlightSearch.loadhandling(driver);
					CollapseTab=driver.findElements(By.xpath("//div[div[div[div[toggle-title[div[text()='"+PAXName+"']]]]]]//i[@class='toggle-arrow ng-scope icon-forward']"));
				
					for(int ctab = 0; ctab < CollapseTab.size();ctab++ ) {
						Thread.sleep(2000);
						CollapseTab.get(ctab).click();
						Thread.sleep(2000);
					}	
				}
				
				TotalAssocaiteEMDCount = TotalAssocaiteEMDCount+ PaxAssocaiteEMDCount;
				if(PaxAssocaiteEMDCount>0)
					queryObjects.logStatus(driver, Status.PASS, "Re-Assocaited EMD" , PaxAssocaiteEMDCount+" Deassocaited EMD are Assocated for "+PAXName, null);
			}
		}
		Thread.sleep(2000);
		if(TotalAssocaiteEMDCount>0)
			queryObjects.logStatus(driver, Status.PASS, "Disassociated EMD ", "All Disassocaited EMD are Assocaited ", null);
		else
			queryObjects.logStatus(driver, Status.FAIL, "Disassociated EMD ", "No Deassocaited EMD were found ", null);
		
		FlightSearch.loadhandling(driver);
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//div[@translate='pssgui.order']")).click();
		
		FlightSearch.loadhandling(driver);
		Thread.sleep(2000);
	}

	public static double Reissue_StoreQuoteid(WebDriver driver,BFrameworkQueryObjects queryObjects,String Fare_Difference ,String StroreQuote) throws NumberFormatException, InterruptedException, Exception{
		
		double RetunrAmt = 0;
		Map<String,ArrayList<String>> getsegdet1=new HashMap<String,ArrayList<String>>();  //store segments in map
		getsegdet1=storeFlightdetails(driver,getsegdet1);
		
		String Vol_pricebestBuy=FlightSearch.getTrimTdata(queryObjects.getTestData("pricebestbuy_Reissue"));
		String Reissuecasequoteid="";
		if(Vol_pricebestBuy.equalsIgnoreCase("Upsell")){
			Reissuecasequoteid=driver.findElement(By.xpath("(//div[contains(text(),'Quote ID')])[2]")).getText().trim();
			String quoteid[]=Reissuecasequoteid.split(" ");
			Reissuecasequoteid=quoteid[2].trim();
		}
		else{
			Reissuecasequoteid=driver.findElement(By.xpath("//div[span[text()='Quote ID']]")).getText().trim();
			String quoteid[]=Reissuecasequoteid.split(":");
			Reissuecasequoteid=quoteid[1].trim();
		}

		if(StroreQuote.equalsIgnoreCase("yes")){
			driver.findElement(By.xpath("//button[contains(text(),'Store Quote ID') and not(@disabled='disabled')]")).click();
			FlightSearch.loadhandling(driver);
		}
		else
			driver.findElement(By.xpath("//button[@aria-label='cancel']")).click();
		// call converter method 
		//Fare_Difference_Amount+";"+Current_Currency+";"+Original_Currency;
		if(!Fare_Difference.isEmpty()){
			String converterdet[]=Fare_Difference.split(";");
			String Fare_Difference_Amount=converterdet[0];
			String Current_Currency=converterdet[1];
			String Original_Currency=converterdet[2];
			RetunrAmt= Convert_Amt(driver, queryObjects, Original_Currency,Current_Currency, Fare_Difference_Amount);
		}
			
		
		driver.findElement(By.xpath(remarks_Xpath)).click();
		FlightSearch.loadhandling(driver);
		
		driver.findElement(By.xpath("//div[contains(text(),'QUOTE ID: "+Reissuecasequoteid+"')]")).click();
		FlightSearch.loadhandling(driver);
		FlightSearch.loadhandling(driver);
		if(StroreQuote.equalsIgnoreCase("yes")){
			Map<String,ArrayList<String>> getsegdet2=new HashMap<String,ArrayList<String>>();  //store segments in map
			getsegdet2=storeFlightdetails(driver,getsegdet2);
			
			if (getsegdet1.entrySet().containsAll(getsegdet2.entrySet())) 
				queryObjects.logStatus(driver, Status.PASS, "Compare Segment details Before Store Quote id and After store Quote id", "Details are matched", null);
		    else 
		    	queryObjects.logStatus(driver, Status.INFO, "Compare Segment details Before Store Quote id and After store Quote id", "Details not Matched Expected:"+getsegdet1+" ,Actual:"+getsegdet2+"", null);
		}
	return RetunrAmt;
	}
	
	public static String GetOriginalcurrency(WebDriver driver,BFrameworkQueryObjects queryObjects){
		
		String Org_Currency="";
		driver.findElement(By.xpath("//div[div[text()='Tickets']]")).click();
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//div[@translate='pssgui.ticket']")).click();
		FlightSearch.loadhandling(driver);
		List<WebElement> getetkts=driver.findElements(By.xpath("//div[@model='primaryTicket.TotalAmount']/div"));
		Org_Currency=getetkts.get(0).getText();
		Org_Currency=Org_Currency.split("\\s+")[1];
		return Org_Currency;
	}
	
	
	public static double  Convert_Amt(WebDriver driver, BFrameworkQueryObjects queryObjects, String Fromcurrecny, String ToCurrecny ,String ConAmt) throws NumberFormatException, IOException, InterruptedException, Exception
    {
           String Amnnt;
           
           driver.findElement(By.xpath("//div[@id='toolbar']")).click();
           Thread.sleep(2000);
           driver.findElement(By.xpath("//div[text()='Converter']/parent::div/parent::button")).click();
           Thread.sleep(1000);
           driver.findElement(By.xpath("//md-select[@aria-label='converterMethod']")).click();
           Thread.sleep(1000);
           driver.findElement(By.xpath("//md-option[contains(@value,'BSR')]")).click();
           Thread.sleep(2000);
           driver.findElement(By.xpath("//input[contains(@ng-model,'currencyConverter.model.fromCurrency')]")).sendKeys(Fromcurrecny);
           driver.findElement(By.xpath("//input[contains(@ng-model,'currencyConverter.model.toCurrency')]")).sendKeys(ToCurrecny);
           driver.findElement(By.xpath("//input[contains(@ng-model,'currencyConverter.model.amount')]")).sendKeys(ConAmt);
           
           driver.findElement(By.xpath("//button[contains(@translate,'pssgui.display')]")).click();
           FlightSearch.loadhandling(driver);
           
           String Amt = driver.findElement(By.xpath("//div[contains(@ng-if,'currencyConverter.model.convert')]/div[4]/div[2]")).getText();
           Amnnt = Amt.split(ToCurrecny)[0];
           double amt= Double.parseDouble(Amnnt);
           queryObjects.logStatus(driver, Status.PASS,"Converded Amount is","Converd amout is "+amt+" From Currency "+Fromcurrecny+" To Currecny "+ToCurrecny,null);
           driver.findElement(By.xpath("//div[contains(@ng-click,'dlgCtrl.closeDialog')]")).click();
           
           return amt;
           
    }
	public static void Residual_EMD_Validation_reissue_Refund(WebDriver driver ,BFrameworkQueryObjects queryObjects) throws IOException{

		//get EMD Numbers
		driver.findElement(By.xpath("//div[contains(text(),'EMD')]")).click();
		FlightSearch.loadhandling(driver);
		List<WebElement> getemds=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']"));
		ArrayList Residual_getemdno = new ArrayList<>();
		getemds.forEach(a -> Residual_getemdno.add(a.getText().trim()));
		//get EMD amount
		List<WebElement> getemdsamt=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']//preceding-sibling::div[1]"));
		Boolean REs_EMD_OPEn=false;
		for (int iEMD = 0; iEMD < getemds.size(); iEMD++) {
			getemds=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']"));
			getemdsamt=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']//preceding-sibling::div[1]"));
			
			String residualEMD=getemdsamt.get(iEMD).getText().split("\\s+")[0];
			double cResidualEmd=Double.parseDouble(residualEMD);
			String ResEMDno=getemds.get(iEMD).getText();
			System.out.println("EMD no: "+ResEMDno);
			getemds.get(iEMD).click();
			FlightSearch.loadhandling(driver);
			//String EMD_Status=driver.findElement(By.xpath("//div[@model='emdDetail.model']//child::tbody/tr/td[7]/div")).getText().trim();
			List<WebElement> resstate=driver.findElements(By.xpath("//td[text()='99I']/following-sibling::td[2]/div")); // getting open EMD state
			if(resstate.size() > 0){
				String EMD_Status=resstate.get(0).getText().trim();
				if(EMD_Status.equalsIgnoreCase("Open")){
					queryObjects.logStatus(driver, Status.PASS, "Residual EMD case RESIDUAL VALUE(Total Refund Amount) ticket status checking", "it is Displaying correctly (OPEN) EMD no:"+ResEMDno , null);
					REs_EMD_OPEn=true;
					String semdnoTemp = Login.EMDNO;
					
					semdnoTemp=semdnoTemp.trim();
					
					if(!semdnoTemp.isEmpty())  // storing for multiple EMD number in ENv sheet 
						Login.EMDNO= semdnoTemp+";"+ResEMDno;
					else
						Login.EMDNO= ResEMDno;
				}
				
			}
			driver.findElement(By.xpath("//span[@translate='pssgui.all.passengers']")).click();
			FlightSearch.loadhandling(driver);
			
		}
		if(REs_EMD_OPEn==false)
			queryObjects.logStatus(driver, Status.FAIL,"REsidual EMD Checking","Residual EMD(Total Refund Amount) should OPEN State..",null);
	}
	
public void ADDINF(WebDriver driver, BFrameworkQueryObjects queryObjects) throws NumberFormatException, IOException, InterruptedException, Exception{
	//AddINfant - Navira
	WebDriverWait wait = new WebDriverWait(driver, 50);//Added - Navira
	if(FlightSearch.getTrimTdata(queryObjects.getTestData("AddInfant")).equalsIgnoreCase("yes")) {

		//click on Add infant button
		driver.findElement(By.xpath("//span[contains(@ng-click,'addTraveler')]//i[@class='icon-add']")).click();
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath(surnameXpath)).sendKeys(RandomStringUtils.random(6, true, false));
		driver.findElement(By.xpath(firstnmXpath)).sendKeys(RandomStringUtils.random(6, true, false));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -2);
		String timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
		driver.findElement(By.xpath(datepickerXpath)).clear();
		driver.findElement(By.xpath(datepickerXpath)).sendKeys(timeStamp);
					
			driver.findElement(By.xpath(traverlwithXpath)).click();
			Thread.sleep(2500);
			driver.findElement(By.xpath("//div[@role='presentation' and @aria-hidden='false']//md-option[1]")).click();
			//driver.findElement(By.xpath(selectfirsttraverxpath)).click();
			String gender = "Male";
			driver.findElement(By.xpath(gengerXpath)).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//md-option[div[div[contains(text(),'" + gender + "')]]]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Next')]")).click();
			FlightSearch.loadhandling(driver);
			
			String fareDetails = driver.findElement(By.xpath(farepriceXpath)).getText().trim();
			String fareAmount = fareDetails.split("\\s+")[3];
			//String currencyType = fareDetails.split("\\s+")[4];
			driver.findElement(By.xpath("//button[text()='Book & File Fare']")).click();
			FlightSearch.loadhandling(driver);
			
			String payBalanceamt=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
			
			if (fareAmount.equals(payBalanceamt))
				queryObjects.logStatus(driver, Status.PASS, "Infant Add amount verified ","Infant added after payment", null);
			
			String INFBalanceamt = driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText().trim();
			double totalPaymentamt = Double.parseDouble(INFBalanceamt);
			
			//Navira - 04Mar
			String confirmation = FlightSearch.getTrimTdata(queryObjects.getTestData("Confirmation_without_payment"));
			if(confirmation.equalsIgnoreCase("yes")) {
				FlightSearch fs = new FlightSearch();
				fs.resConfirmationwopayment(driver, queryObjects);
			}
			
			//Clicking on Checkout button
			driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
			FlightSearch.loadhandling(driver);
			FlightSearch.MultipleFOPType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPType_Quote"));
			FlightSearch.MultFOPsubType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPSubType_Quote"));
			FlightSearch.fopCardNums = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPCardNums_Quote"));
			String BankNames = queryObjects.getTestData("MultipleFOPBankName_Quote").trim();
			String InstallmentNums = queryObjects.getTestData("MultipleFOPInstallmentNum_Quote").trim();
			if(!FlightSearch.MultipleFOPType.isEmpty()) {
				FlightSearch flopy = new FlightSearch();
				flopy.MulFOP(driver,queryObjects,totalPaymentamt,FlightSearch.MultipleFOPType,FlightSearch.MultFOPsubType,FlightSearch.fopCardNums,BankNames,InstallmentNums,"AddInfMulFOP");
			}
			//
			else {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")));
				driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Miscellaneous')]]")).click();
				//FlightSearch.paymentmodeHidenchek(driver);
				driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='subType']")).click();
				Thread.sleep(2000);
				FlightSearch.selectingonemisc(driver,queryObjects,"banktransfer");
				String ckDate = null;
				Calendar cal1 = Calendar.getInstance();
				ckDate = new SimpleDateFormat("MM/dd/yyyy").format(cal1.getTime());
				driver.findElement(By.xpath("//div[contains(@class,'md-datepicker-input-container')]/input")).sendKeys(ckDate);
				Thread.sleep(2000);
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Pay']")));
			//Start - Navira
			String type = FlightSearch.getTrimTdata(queryObjects.getTestData("Type_of_Transfer"));
			if(!type.equalsIgnoreCase(""))
			FlightSearch.bulkFare(driver, queryObjects,type);
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//button[text()='Pay']")).click();
			//FlightSearch.loadhandling(driver);
			FlightSearch.enterFoiddetails(driver,queryObjects);
			//Handling Email recipients popup
			FlightSearch.emailhandling(driver,queryObjects);
			try {
				wait = new WebDriverWait(driver, 120);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
				String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
				queryObjects.logStatus(driver, Status.PASS, " Payment", statusMessage, null);
			}
			catch(Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, " Payment", "Payment Unsuccessful: " + e.getMessage() , e);
			}
			
			//Clicking on Done button
			driver.findElement(By.xpath("//button[text()='Done']")).click();
	}
}

public static void EMDVerifi(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {
	String emdNum = Login.EMDNO;
	if(!emdNum.contains(";")) {
		String emdno = emdNum;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 50);
			driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Reservations')]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//span[text()='Home']")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//div[div[text()='Search'] and @role='button']")).click();
			Thread.sleep(2000);
			//Entering EMD number in search input
			driver.findElement(By.xpath("//input[@aria-label='Search']")).click();
			driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(emdno);
	
			//Clicking on Search button
			driver.findElement(By.xpath("//div[contains(@class,'itinerary-search')]//button[contains(text(),'Search')]")).click();
			
			FlightSearch.loadhandling(driver);
			
			queryObjects.logStatus(driver, Status.PASS, "Search EMD", "Search EMD successful" , null);
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Search EMD", "Search EMD failed: " + e.getLocalizedMessage() , e);
		}
		
		if(driver.findElements(By.xpath("//div[text()=' "+emdno+" ']")).size()>0) {
			driver.findElement(By.xpath("//div[text()=' "+emdno+" ']")).click();
			String issueDate = new String();
			if(driver.findElements(By.xpath("//div[text()='Details']")).size()>0) {
					driver.findElement(By.xpath("//div[text()='Details']")).click();
					
					//Issue Date
					try {
						issueDate = driver.findElement(By.xpath("//thead[tr[th[text()='Issuing Date']]]/following-sibling::tbody/tr/td[3]")).getText();
						if(!issueDate.equalsIgnoreCase(""))
							queryObjects.logStatus(driver, Status.PASS, "Checking Issue Date", "Issue Date is: "+issueDate, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Checking Issue Date", "Issue Date is blank", null);								
						
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Checking Issue Date", "Issue Date is not present", null);	
					}
					
					
						
						
						
					//RFISC
					try {
						String rfisc = driver.findElement(By.xpath("//thead[tr[th[text()='RFISC']]]/following-sibling::tbody/tr/td[5]")).getText();
						if(!rfisc.equalsIgnoreCase(""))
							queryObjects.logStatus(driver, Status.PASS, "Checking RFISC", "RFISC is: "+rfisc, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Checking RFISC", "RFISC is blank", null);								
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Checking RFISC", "RFISC is not present", null);	
					}
					
					//Value
					try {
						String Value = driver.findElement(By.xpath("//thead[tr[th[text()='Value']]]/following-sibling::tbody/tr/td[6]")).getText();
						if(!Value.equalsIgnoreCase(""))
							queryObjects.logStatus(driver, Status.PASS, "Checking Value", "Value is: "+Value, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Checking Value", "Value is blank", null);								
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Checking Value", "Value is not present", null);	
					}
					
					//Status
					try {
						
						//String flStatus = driver.findElement(By.xpath("//thead[tr[th[text()='Status']]]/following-sibling::tbody/tr/td[7]/div")).getText();
						String flStatus = driver.findElement(By.xpath("//thead[tr[th[text()='Value']]]/following-sibling::tbody/tr/td[7]/div")).getText();
						if(!flStatus.equalsIgnoreCase(""))
							queryObjects.logStatus(driver, Status.PASS, "Checking Segment Status", "Segment Status is: "+flStatus, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Checking Segment Status", "Segment Status is blank", null);								
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Checking Segment Status", "Segment Status is not present", null);	
					}
					
					//Fare
					try {
						String Fare = driver.findElement(By.xpath("//div[div[text()='EMD Details']]/following-sibling::div//thead[tr[th[text()='Fare']]]/following-sibling::tbody/tr/td[1]/div")).getText();
						if(!Fare.equalsIgnoreCase(""))
							queryObjects.logStatus(driver, Status.PASS, "Checking Fare", "Fare is: "+Fare, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Checking Fare", "Fare is blank", null);								
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Checking Fare", "Fare is not present", null);	
					}
					
					//Equivalent fare
					try {
						String EquivalentFare = driver.findElement(By.xpath("//thead[tr[th[text()='Equivalent Fare']]]/following-sibling::tbody/tr/td[4]")).getText();
						if(!EquivalentFare.equalsIgnoreCase(""))
							queryObjects.logStatus(driver, Status.PASS, "Checking Equivalent Fare", "Equivalent Fare is: "+EquivalentFare, null);
						else
							queryObjects.logStatus(driver, Status.WARNING, "Checking Equivalent Fare", "Equivalent Fare is blank", null);								
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Checking Equivalent Fare", "Equivalent Fare is not present", null);	
					}
					
					//issue place
					try {
						String POI = driver.findElement(By.xpath("//thead[tr[th[span[text()='Place of Issue']]]]/following-sibling::tbody/tr/td[1]")).getText();
						if(!POI.equalsIgnoreCase(""))
							queryObjects.logStatus(driver, Status.PASS, "Checking Place of Issue", "Place of Issue is: "+POI, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Checking Place of Issue", "Place of Issue is blank", null);								
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Checking Place of Issue", "Place of Issue is not present", null);	
					}
					
					//payment type
					try {
						String PaymentType = driver.findElement(By.xpath("//thead[tr[th[text()='Type']]]/following-sibling::tbody/tr/td[1]")).getText();
						if(!PaymentType.equalsIgnoreCase(""))
							queryObjects.logStatus(driver, Status.PASS, "Checking Payment Type", "Payment Type is: "+PaymentType, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Checking Payment Type", "Payment Type is blank", null);								
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Checking Payment Type", "Payment Type is not present", null);	
					}
					
			}
			String date1=issueDate;
			
			//EMD History
			String IssueDate = new String();
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("EMD_History")).equalsIgnoreCase("yes")) {
				driver.findElement(By.xpath("//div[@pssgui-toggle='emd.emdCouponValue']//div[text()='History']")).click();
				FlightSearch.loadhandling(driver);
				if(driver.findElements(By.xpath("//div[@action='emd-history']//i[contains(@class,'icon-forward')]")).size()>0) {
					driver.findElement(By.xpath("//div[@action='emd-history']//i[contains(@class,'icon-forward')]")).click();
					//issue date
					try {
						IssueDate = driver.findElement(By.xpath("//thead[tr[th[text()='Date']]]/following-sibling::tbody/tr/td[1]")).getText();
						if(!IssueDate.equalsIgnoreCase(""))
							queryObjects.logStatus(driver, Status.PASS, "Checking Issue Date", "Issue Date is: "+IssueDate, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Checking Issue Date", "Issue Date is blank", null);								
						
						
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Checking Issue Date", "Issue Date is not present", null);	
					}
					
					String iDate[] = IssueDate.split(" "); 
					String iDate1[] = iDate[0].split("-");
					String date2 = iDate1[1]+" "+iDate1[0]+", "+iDate1[2];
					
					date2=date2.replace(",", "");
					date2=date2.replace(" ", "-");

					
					date1=date1.replace(",", "");
					date1=date1.replace(" ", "-");

					SimpleDateFormat sdf = new SimpleDateFormat("MMMM-dd-yyyy");
					Date datea = sdf.parse(date2);
			        Date datec = sdf.parse(date1);
					
					if(datea.compareTo(datec) == 0) 
						queryObjects.logStatus(driver, Status.PASS, "Comparing the date of Issue on EMD Details and EMD History Page", "Issue Date is: "+datec+"in both Pages", null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "Comparing the date of Issue on EMD Details and EMD History Page", "Issue Date is "+datec+" in Detals Page and "+datea+" in History Page", null);								
					
					//issue location code 
					try {
						String LocationCode = driver.findElement(By.xpath("//thead[tr[th[text()='Location Code']]]/following-sibling::tbody/tr/td[2]")).getText();
						if(!LocationCode.equalsIgnoreCase(""))
							queryObjects.logStatus(driver, Status.PASS, "Checking Location Code", "Location Code is: "+LocationCode, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Checking Location Code", "Location Code is blank", null);								
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Checking Location Code", "Location Code is not present", null);	
					}
					
					//status
					try {
						String EMDStatus = driver.findElement(By.xpath("//thead[tr[th[text()='Status']]]/following-sibling::tbody/tr/td[5]")).getText();
						if(!EMDStatus.equalsIgnoreCase(""))
							queryObjects.logStatus(driver, Status.PASS, "Checking EMD Status", "EMD Status is: "+EMDStatus, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Checking EMD Status", "EMD Status is blank", null);								
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Checking EMD Status", "EMD Status is not present", null);	
					}
					
				}
			}
		}
	}
}
public void Add_infant(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
	WebDriverWait wait = new WebDriverWait(driver, 50);
	//click on Add infant button
	driver.findElement(By.xpath("//span[contains(@ng-click,'addTraveler')]//i[@class='icon-add']")).click();
	FlightSearch.loadhandling(driver);
	driver.findElement(By.xpath(surnameXpath)).sendKeys(RandomStringUtils.random(6, true, false));
	driver.findElement(By.xpath(firstnmXpath)).sendKeys(RandomStringUtils.random(6, true, false));
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.MONTH, -2);
	String timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
	driver.findElement(By.xpath(datepickerXpath)).clear();
	driver.findElement(By.xpath(datepickerXpath)).sendKeys(timeStamp);
				
		driver.findElement(By.xpath(traverlwithXpath)).click();
		Thread.sleep(2500);
		driver.findElement(By.xpath("//div[@role='presentation' and @aria-hidden='false']//md-option[1]")).click();
		//driver.findElement(By.xpath(selectfirsttraverxpath)).click();
		String gender = "Male";
		driver.findElement(By.xpath(gengerXpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//md-option[div[div[contains(text(),'" + gender + "')]]]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Next')]")).click();
		FlightSearch.loadhandling(driver);
		String fareDetails = driver.findElement(By.xpath(farepriceXpath)).getText().trim();
		String fareAmount = fareDetails.split("\\s+")[3];
		//String currencyType = fareDetails.split("\\s+")[4];
		driver.findElement(By.xpath("//button[text()='Book & File Fare']")).click();
		FlightSearch.loadhandling(driver);
		
		String payBalanceamt=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
		
		if (fareAmount.equals(payBalanceamt))
			queryObjects.logStatus(driver, Status.PASS, "Infant Add amount verified ","Infant added after payment", null);
		String INFBalanceamt = driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText().trim();
		double totalPaymentamt = Double.parseDouble(INFBalanceamt);
		
		//Clicking on Checkout button
		
		driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
		FlightSearch.MultipleFOPType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPType_Quote"));
		FlightSearch.MultFOPsubType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPSubType_Quote"));
		FlightSearch.fopCardNums = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPCardNums_Quote"));
		String BankNames = queryObjects.getTestData("MultipleFOPBankName_Quote").trim();
		String InstallmentNums = queryObjects.getTestData("MultipleFOPInstallmentNum_Quote").trim();
		if(!FlightSearch.MultipleFOPType.isEmpty()) {
			FlightSearch flopy = new FlightSearch();
			flopy.MulFOP(driver,queryObjects,totalPaymentamt,FlightSearch.MultipleFOPType,FlightSearch.MultFOPsubType,FlightSearch.fopCardNums,BankNames,InstallmentNums,"AddInfMulFOP");
		}
		//
		else {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")));
		driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Miscellaneous')]]")).click();
		//FlightSearch.paymentmodeHidenchek(driver);
		driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='subType']")).click();
		Thread.sleep(2000);
		FlightSearch.selectingonemisc(driver,queryObjects,"banktransfer");
		String ckDate = null;
		Calendar cal1 = Calendar.getInstance();
		ckDate = new SimpleDateFormat("MM/dd/yyyy").format(cal1.getTime());
		driver.findElement(By.xpath("//div[contains(@class,'md-datepicker-input-container')]/input")).sendKeys(ckDate);
		Thread.sleep(2000);
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Pay']")));
		
		driver.findElement(By.xpath("//button[text()='Pay']")).click();
		//Handling Email recipients popup
		FlightSearch.emailhandling(driver,queryObjects);
		try {
			wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
			String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
			queryObjects.logStatus(driver, Status.PASS, " Payment", statusMessage, null);
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, " Payment", "Payment Unsuccessful: " + e.getMessage() , e);
		}

		//Clicking on Done button
		driver.findElement(By.xpath("//button[text()='Done']")).click();
}
	


public static void Reopen_storeQtoreID(WebDriver driver,BFrameworkQueryObjects queryObjects) throws  Exception{
	
	driver.findElement(By.xpath("//div[@translate='pssgui.order']")).click();
	FlightSearch.loadhandling(driver);
	driver.findElement(By.xpath(remarks_Xpath)).click();
	FlightSearch.loadhandling(driver);
	
	driver.findElement(By.xpath("//div[contains(text(),'VOL MANUAL REISSUE QUOTE ID:')]")).click();
	FlightSearch.loadhandling(driver);
	
}

	public static void Search_EMD(WebDriver driver,BFrameworkQueryObjects queryObjects,String emdNum) throws IOException, Exception{
		
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
	                 driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(emdNum);
	                 //Clicking on Search button
	                 driver.findElement(By.xpath("//div[contains(@class,'itinerary-search')]//button[contains(text(),'Search')]")).click();
	                 FlightSearch.loadhandling(driver);
	                 //String Pnris=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@action='pnr']/div/div/div[1]/div[1]")).getText());
	                 //Wait until loading wrapper closed
	
	
	                 queryObjects.logStatus(driver, Status.PASS, "Search EMD", "Search EMD successfully" , null);
	
	          }
	          catch(Exception e) {
	                 queryObjects.logStatus(driver, Status.FAIL, "Search PNR", "Search PNR failed: " + e.getLocalizedMessage() , e);
	          }
	    }
	
	public static void EmergencyContact(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException, InterruptedException{
		driver.findElement(By.xpath(passangerTabXpath)).click();	
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//div[div[text()='Personal Information']]")).click();	
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//button[@translate='pssgui.edit']")).click();	
		FlightSearch.loadhandling(driver);
		//Entering emergency name
		driver.findElement(By.xpath(emergencynameXpath)).click();
		driver.findElement(By.xpath(emergencynameXpath)).sendKeys(RandomStringUtils.random(6, true, false));
		driver.findElement(By.xpath(emergencyphoneXpath)).click();
		driver.findElement(By.xpath(emergencyphoneXpath)).sendKeys(RandomStringUtils.random(3, true, false));
		Thread.sleep(2000);
		//Select emergency phone type
		driver.findElement(By.xpath("//div[@action='emergency-contact']/following-sibling::div//md-select")).click();
		Thread.sleep(1500);
		driver.findElement(By.xpath(selectfirstphonetypeXpath_EmercontDet)).click();

		//Enter Country code
		driver.findElement(By.xpath(countrycodeselectXpath_EmercontDet)).click();
		driver.findElement(By.xpath(countrycodeselectXpath_EmercontDet)).clear();
		driver.findElement(By.xpath(countrycodeselectXpath_EmercontDet)).sendKeys("US");
		FlightSearch.loadhandling(driver);
		//driver.findElement(By.xpath(countrycodeselectXpath_EmercontDet)).sendKeys(Keys.TAB);
		driver.findElement(By.xpath(clickUSpopuXpath)).click();
		//driver.findElement(By.xpath("//ul/li[md-autocomplete-parent-scope[span[span[text()='US']]]]")).click();
		//driver.findElement(By.xpath(selectcountryXpath)).click();

		//driver.findElement(By.xpath(areacodeXpath)).click();
		//driver.findElement(By.xpath(areacodeXpath)).sendKeys(RandomStringUtils.random(3, false, true));
		
		driver.findElement(By.xpath(phonenumberxpath_EmercontDet)).click();
		driver.findElement(By.xpath(phonenumberxpath_EmercontDet)).sendKeys(RandomStringUtils.random(8, false, true));
		
		
		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
		FlightSearch.loadhandling(driver);
		queryObjects.logStatus(driver, Status.PASS, "Emergency Contact Details", "Emergency Contact updated successfully", null);
	}

}

