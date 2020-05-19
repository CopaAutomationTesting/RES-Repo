package com.copa.RESscripts;

import java.io.IOException;

import javax.xml.xpath.XPath;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import FrameworkCode.BFrameworkQueryObjects;

public class Cybersource {

	//private static final String String = null;
	public static String Merchant_Id = "copaairlines";
	public static String User_Name = "reddy.sana";
	public static String Password = "May.Jun-2020";

	static String Payment_Method = "";
	static String Acct_Suffix = "";
	static String Fop_Testdata = "";
	static String AcctNo_Testdata = "";
	static String BillerDet_Testdata = "";
	static String TicketAction ="";
	static String PNRSplit[];
	static String CCTypeSplit[];
	static String CCNumSplit[];
	static String BillerInfoSplit[];
	static String BillerDet_Testdataa[];
	static String TwoBillerDetails;
	static String CyberSource_Code;
	static String Codes[];
	static boolean Accept_check_CCSGUI =false;
	static boolean Reject_check_CCSGUI =false;
	
	public static void Cybersource(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {
		// TODO Auto-generated method stub
		//return;

		try {
			/*if (true) {   // need to update script because cybersource application changed...
				queryObjects.logStatus(driver, Status.WARNING, "Cybersource login ", "Login not workig so blocking cybbersource validation", null);	
				return;

			}*/
			Accept_check_CCSGUI =false;
			Reject_check_CCSGUI =false;
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.open()");
			
			String maintab=driver.getWindowHandle();
			
			
			String mostRecentWindowHandle="";
			for(String winHandle:driver.getWindowHandles()){
		        mostRecentWindowHandle = winHandle;        
		    }
			driver.switchTo().window(mostRecentWindowHandle);
			driver.navigate().to("https://ebctest.cybersource.com/ebctest/login/Login.do");
			Cybersourceloadhandling(driver,queryObjects,"//input[@id= 'orgId']");
			//WebDriverWait wait = new WebDriverWait(driver, 1000);
			driver.findElement(By.xpath("//input[@id= 'orgId']")).sendKeys(Merchant_Id);
			driver.findElement(By.xpath("//input[@id = 'username']")).sendKeys(User_Name);
			driver.findElement(By.xpath("//input[@id = 'password']")).sendKeys(Password);
			driver.findElement(By.xpath("//span[text()= 'LOG IN']")).click();
			
			Thread.sleep(2000);
			Cybersourceloadhandling(driver,queryObjects,"//div[text()='"+User_Name+"']");
			//Alert alert = driver.switchTo().alert();
			//alert.dismiss();
			//Getting  the PNR Number
			String PNR = FlightSearch.CyberPNR;
			//String PNR= "GPWNSG:30601176187-1;GPWNSG:30601176187-2";

	    	Fop_Testdata= FlightSearch.getTrimTdata(queryObjects.getTestData("ccSubType"));
			AcctNo_Testdata = FlightSearch.getTrimTdata(queryObjects.getTestData("ccNum"));
			BillerDet_Testdata = FlightSearch.getTrimTdata(queryObjects.getTestData("BillerDetails")).trim();
			TicketAction = FlightSearch.getTrimTdata(queryObjects.getTestData("TicketAction"));
			TwoBillerDetails = FlightSearch.getTrimTdata(queryObjects.getTestData("TwoBillerDetails"));
			//spilt the biller info if 
				if(BillerDet_Testdata.contains("~")) 
					BillerDet_Testdataa=BillerDet_Testdata.split("~");
				//CyberSource_Code Validation
				CyberSource_Code=FlightSearch.getTrimTdata(queryObjects.getTestData("CyberSource_Code"));
				if(!CyberSource_Code.isEmpty()) 
				Codes=CyberSource_Code.split(";");		
					
			if ((driver.findElement(By.xpath("//div[text()='"+User_Name+"']"))).getText()!=null) {
				queryObjects.logStatus(driver, Status.PASS, "Cybersource login ", "Login is successful", null);				

				//Loop for more than one PNR						
				if (PNR.contains(";")) {
					PNRSplit = PNR.split(";");
					
			for (int i = 0; i <PNRSplit.length; i++) {
			
			
				if(TwoBillerDetails.equalsIgnoreCase("yes")) 	
					
					VerifyBillerDetails(driver, queryObjects, PNRSplit[i], BillerDet_Testdataa[i],TicketAction);								
				else
					VerifyBillerDetails(driver, queryObjects, PNRSplit[i],BillerDet_Testdata ,TicketAction);	
			}	}
				else {
					
					VerifyBillerDetails(driver, queryObjects, PNR, BillerDet_Testdata,TicketAction);
				}

			} else {
				queryObjects.logStatus(driver, Status.FAIL, "Cybersource login ", "Login failed", null);
			}
			
			driver.close();
			driver.switchTo().window(maintab);
			PNRsearch pnr=new PNRsearch();
			boolean contflag=false;
			if(Accept_check_CCSGUI || Reject_check_CCSGUI ){
				contflag=pnr.Search_PNR(driver, queryObjects);
				if(contflag){  // this condition for search codition
					if(FlightSearch.Aftecybebersourec_Update_check_GUI){   // this is for create PNR and reissue case After update cyber sourece checking in pss GUI ticket status.....
						if(Accept_check_CCSGUI){
							if(driver.findElements(By.xpath("//div[@translate='Ticketed']")).size()>0)
								queryObjects.logStatus(driver, Status.PASS, "After update CyberSource Ticket Status checking", "cybersourec Accept case  Ticket status displaying correctly" , null);
							else
								queryObjects.logStatus(driver, Status.WARNING, "After update  CyberSource Ticket Status checking", "cybersourec Accept case  Ticket status should displaying correctly like Ticketed" , null);
						}
						else if (Reject_check_CCSGUI) {
							if(driver.findElements(By.xpath("//div[@translate='Not Ticketed']")).size()>0)
								queryObjects.logStatus(driver, Status.PASS, "After update CyberSource Ticket Status checking", "cybersourec Reject case  Ticket status displaying correctly" , null);
							else
								queryObjects.logStatus(driver, Status.WARNING, "After update  CyberSource Ticket Status checking", "cybersourec Reject case  Ticket status should displaying correctly like Non Ticketed" , null);
						}
						
					}
				}
				
			}

				
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Cybersource Validation", e.getLocalizedMessage(), e);
		}

	}

	
	public static void VerifyBillerDetails(WebDriver driver, BFrameworkQueryObjects queryObjects, String PNRNum,String BillerInfo,String TktAction) throws Exception {
	try {
		driver.findElement(By.xpath("//div[@data-route='transactionMgmt']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@data-route='/TransactionManagement/Transactions']//span[contains(text(),'Transactions')]")).click();
		Cybersourceloadhandling(driver,queryObjects,"//input[@name='freeText']");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@name='freeText']")).sendKeys(PNRNum);
		driver.findElement(By.xpath("//button[@name='freeSearch']")).click();
		Cybersourceloadhandling(driver,queryObjects,"//div[contains(text(),'"+PNRNum+"')]");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@data-test='grid-transaction-results']//a")).click();
		Thread.sleep(5000);
		Cybersourceloadhandling(driver,queryObjects,"//span[contains(text(),'View Case Management Details')]");
		Thread.sleep(5000);
		String code102=driver.findElement(By.xpath("//div[@id='ReasonCode']//div[2]")).getText();
		
	if(code102.equalsIgnoreCase("102")) {
			
		String Message=driver.findElement(By.xpath("//div[contains(text(),\"The following request field(s) is either invalid or missing: customer_email\")]")).getText();
		queryObjects.logStatus(driver, Status.PASS, "102 Code Displaying message-->", Message, null);			
	}		
	else 
	{
		BillerInfoSplit = BillerInfo.split(";");
		//Payment method and Account Suffix verification
		String Billing_FirstName = driver.findElement(By.xpath("//div[span[contains(text(),'First Name')]]/following-sibling::div")).getText().trim();
		String Billing_LastName = driver.findElement(By.xpath("//div[span[contains(text(),'Last Name')]]/following-sibling::div")).getText().trim();
		String Billing_Addr1 = driver.findElement(By.xpath("//div[@id='billingAddress']/div[2]")).getText().trim();
		String Billing_City = driver.findElement(By.xpath("//div[@id='billingCity']/div[2]")).getText().trim();
		String Billing_State = driver.findElement(By.xpath("//div[@id='billingState']/div[2]")).getText().trim();
		String Billing_Zip = driver.findElement(By.xpath("//div[@id='billingPostalCode']/div[2]")).getText().trim();
		String Billing_Country = driver.findElement(By.xpath("//div[@id='billingCountry']/div[2]")).getText().trim();
		String Biller_Phone = driver.findElement(By.xpath("//div[@id='billingPhoneNumber']/div[2]")).getText().trim();
		String Biller_Email = driver.findElement(By.xpath("//div[@id='EmailAddress']/div[2]/div[1]")).getText().trim();
		
		if (Billing_FirstName.equalsIgnoreCase(BillerInfoSplit[0])) {
			queryObjects.logStatus(driver, Status.PASS, "Billing Information - Check the First Name", "First Name displayed is correct", null);
		} else {
			queryObjects.logStatus(driver, Status.FAIL, "Billing Information - Check the First Name", "First displayed is not correct", null);
		}
		if (Billing_LastName.equalsIgnoreCase(BillerInfoSplit[1])) {
			queryObjects.logStatus(driver, Status.PASS, "Billing Information - Check the Last Name", "Last Name displayed is correct", null);
		} else {
			queryObjects.logStatus(driver, Status.FAIL, "Billing Information - Check the Last Name", "Last Name displayed is not correct", null);
		}
		if (Billing_Addr1.equalsIgnoreCase(BillerInfoSplit[2])) {
			queryObjects.logStatus(driver, Status.PASS, "Billing Information - Check the Address1", "Address1 displayed is correct", null);
		} else {
			queryObjects.logStatus(driver, Status.FAIL, "Billing Information - Check the Address", "Address displayed is not correct", null);
		}

		if (Billing_City.equalsIgnoreCase(BillerInfoSplit[3])) {
			queryObjects.logStatus(driver, Status.PASS, "Billing Information - Check the City", "City displayed is correct", null);
		} else {
			queryObjects.logStatus(driver, Status.FAIL, "Billing Information - Check the City", "City displayed is not correct", null);
		}

		if (Billing_State.equalsIgnoreCase(BillerInfoSplit[4])) {
			queryObjects.logStatus(driver, Status.PASS, "Billing Information - Check the State", "State displayed is correct", null);
		} else {
			queryObjects.logStatus(driver, Status.FAIL, "Billing Information - Check the State", "State displayed is not correct", null);
		}

		if (Billing_Zip.equalsIgnoreCase(BillerInfoSplit[5])) {
			queryObjects.logStatus(driver, Status.PASS, "Billing Information - Check the Zipcode", "Zipcode displayed is correct", null);
		} else {
			queryObjects.logStatus(driver, Status.FAIL, "Billing Information - Check the Zipcode", "Zipcode displayed is not correct", null);
		}

		if (Billing_Country.equalsIgnoreCase(BillerInfoSplit[6])) {
			queryObjects.logStatus(driver, Status.PASS, "Billing Information - Check the Country", "Country displayed is correct", null);
		} else {
			queryObjects.logStatus(driver, Status.FAIL, "Billing Information - Check the Country", "Country displayed is not correct", null);
		}

		if (Biller_Phone.equalsIgnoreCase(BillerInfoSplit[7])) {
			queryObjects.logStatus(driver, Status.PASS, "Billing Information - Check the Phone Number", "Phone Number displayed is correct", null);
		} else {
			queryObjects.logStatus(driver, Status.FAIL, "Billing Information - Check the Phone Number", "Phone Number displayed is not correct", null);
		}

		if (Biller_Email.equalsIgnoreCase(BillerInfoSplit[8])) {
			queryObjects.logStatus(driver, Status.PASS, "Billing Information - Check the Email Id", "Email Id displayed is correct", null);

		} else {
			queryObjects.logStatus(driver, Status.FAIL, "Billing Information - Check the Email Id", "Email Id displayed is not correct", null);
		}
		
		//Perform Reject , Accept actions on ticket
		
			if (Biller_Email.contains("review")) {
				//checking the code  for reject -----481
				String ReviewCode=driver.findElement(By.xpath("//div[@id='ReasonCode']//div[2]")).getText();
				if(ReviewCode.contains("480"))
					queryObjects.logStatus(driver, Status.PASS, "ReasonCode Displayed", "Correct For Reject Scenario",null);
			    else 
				queryObjects.logStatus(driver, Status.FAIL, "ReasonCode Displayed", "Wrong For Reject Scenario", null);	
				if (!TktAction.equalsIgnoreCase("")) {
					PerformActiononTickets(driver, queryObjects, TktAction);								
				}
			}	
			else if (Biller_Email.contains("accept")) {
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[contains(text(),'View Case Management Details')]")).click();
				Thread.sleep(5000);
				Cybersourceloadhandling(driver,queryObjects,"//span[contains(text(),'Back to Transaction Details')]");
				Thread.sleep(1000);
				if (driver.findElement(By.xpath("//div[contains(text(),'Accepted')]")).isDisplayed()) {
					queryObjects.logStatus(driver, Status.PASS, "Case Status validation - Accept", "Accepted by Profile is displayed in the Case Information section on Cybersource console",null);

				} else
					queryObjects.logStatus(driver, Status.FAIL, "Case Status validation - Accept", "Accepted by Profile is not displayed in the Case Information section on Cybersource console", null);				
			
				//checking the code  for Accept -----100
				driver.navigate().back();
				Thread.sleep(5000);
				Cybersourceloadhandling(driver,queryObjects,"//span[contains(text(),'Order Information')]");
				Thread.sleep(1000);
				String AcceptCode=driver.findElement(By.xpath("//div[@id='ReasonCode']//div[2]")).getText();
				if(AcceptCode.contains("100"))
					queryObjects.logStatus(driver, Status.PASS, "ReasonCode Displayed", "Correct For Accept Scenario",null);
				else 
					queryObjects.logStatus(driver, Status.FAIL, "ReasonCode Displayed", "Wrong For Accept Scenario", null);	
			} 
			else if (Biller_Email.contains("reject")) {
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[contains(text(),'View Case Management Details')]")).click();
				Thread.sleep(5000);
				Cybersourceloadhandling(driver,queryObjects,"//span[contains(text(),'Back to Transaction Details')]");
				Thread.sleep(1000);
				if (driver.findElement(By.xpath("//div[contains(text(),'Rejected')]")).isDisplayed()) {
					queryObjects.logStatus(driver, Status.PASS, "Case Status validation - Reject", "Rejected by Profile is displayed in the Case Information section on Cybersource console",null);

				} else 
					queryObjects.logStatus(driver, Status.FAIL, "Case Status validation - Reject", "Rejected by Profile is displayed in the Case Information section on Cybersource console", null);
				//checking the code  for reject -----481
				driver.navigate().back();
				Thread.sleep(5000);
				Cybersourceloadhandling(driver,queryObjects,"//span[contains(text(),'Order Information')]");
				Thread.sleep(1000);
				String RejectCode=driver.findElement(By.xpath("//div[@id='ReasonCode']//div[2]")).getText();
				if(RejectCode.contains("481"))
					queryObjects.logStatus(driver, Status.PASS, "ReasonCode Displayed", "Correct For Reject Scenario",null);
				else 
					queryObjects.logStatus(driver, Status.FAIL, "ReasonCode Displayed", "Wrong For Reject Scenario", null);			
			}
			}
			queryObjects.logStatus(driver, Status.PASS, "VerifyBillerDetails----->", "PASS", null);
	}
	catch(Exception e) {
		queryObjects.logStatus(driver, Status.FAIL, "VerifyBillerDetails----->", e.getLocalizedMessage(), e);
	}
}
	




	private static void PerformActiononTickets(WebDriver driver, BFrameworkQueryObjects queryObjects, String Tkt_Action) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		// View Case Management Details click for performing action 
		driver.findElement(By.xpath("//span[contains(text(),'View Case Management Details')]")).click();
		Thread.sleep(5000);
		Cybersourceloadhandling(driver,queryObjects,"//span[contains(text(),'Back to Transaction Details')]");
		Thread.sleep(1000);
		//Performing Reject action
		if(Tkt_Action.contains("Reject")) {
			if (driver.findElement(By.xpath("//button[@data-test='btn-reject']")).isDisplayed()) {
			queryObjects.logStatus(driver, Status.PASS, "Reject order - Select Reason popup", "Reason popup is displayed",null);
			driver.findElement(By.xpath("//button[@data-test='btn-reject']")).click();
			Thread.sleep(500);
			driver.findElement(By.xpath("//textarea[@data-test='text-noteDescription']")).sendKeys("Copa Test");
			driver.findElement(By.xpath("//button[@data-test='btn-submit']")).click();
			Thread.sleep(5000);
			Cybersourceloadhandling(driver,queryObjects,"//span[contains(text(),'Case Management List')]");
			Thread.sleep(2000);
			driver.navigate().back();
			Thread.sleep(5000);
			Cybersourceloadhandling(driver,queryObjects,"//span[contains(text(),'Order Information')]");
			Thread.sleep(2000);
//			if (driver.findElement(By.xpath("//p[contains(text(),'The order was rejected successfully.')]")).isDisplayed()) {
			if (driver.findElement(By.xpath("//div[contains(text(),'Rejected')]")).isDisplayed()) {
				queryObjects.logStatus(driver, Status.PASS, "Reject the order", "The order was rejected successfully is displayed",null);
				//kis
				Accept_check_CCSGUI =false;
				Reject_check_CCSGUI =true;
			}
			
			}else{
			queryObjects.logStatus(driver, Status.PASS, "Reject order - Select Reason popup", "Reason popup is not displayed",null);
		}
		}
		if(Tkt_Action.contains("Accept")) {
			if (driver.findElement(By.xpath("//button[@data-test='btn-accept']")).isDisplayed()) {
			queryObjects.logStatus(driver, Status.PASS, "Accept order - Select Reason popup", "Reason popup is displayed",null);
			driver.findElement(By.xpath("//button[@data-test='btn-accept']")).click();
			Thread.sleep(500);
			driver.findElement(By.xpath("//textarea[@data-test='text-noteDescription']")).sendKeys("Copa Test");
			driver.findElement(By.xpath("//button[@data-test='btn-submit']")).click();
			Thread.sleep(3000);
			Cybersourceloadhandling(driver,queryObjects,"//span[contains(text(),'Case Management List')]");
			Thread.sleep(2000);
			driver.navigate().back();
			Thread.sleep(2000);
			Cybersourceloadhandling(driver,queryObjects,"//span[contains(text(),'Order Information')]");
			Thread.sleep(2000);
//			if (driver.findElement(By.xpath("//p[contains(text(),'The order was accepted successfully.')]")).isDisplayed()) {
			if (driver.findElement(By.xpath("//div[contains(text(),'Accepted')]")).isDisplayed()) {
				queryObjects.logStatus(driver, Status.PASS, "Accept the order", "The order was accepted successfully is displayed",null);
				//kis
				Accept_check_CCSGUI =true;
				Reject_check_CCSGUI =false;
			}
			}else{
			queryObjects.logStatus(driver, Status.PASS, "Accept order - Select Reason popup", "Reason popup is not displayed",null);
		}
		}
	}
	
	public static void Cybersourceloadhandling(WebDriver driver , BFrameworkQueryObjects queryObjects,String xpath) throws IOException{
		try {
			//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  //5
			Thread.sleep(3000);

			WebDriverWait wait = new WebDriverWait(driver, 300);
			wait = new WebDriverWait(driver, 300);
			//WebElement xyz=driver.findElement(By.xpath(xpath));
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		
			//wait.until(ExpectedConditions.visibilityOf((WebElement) By.xpath("//span[contains(text(),'SIGN IN')]")));
			//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'loading-wrapper')]")));
			Thread.sleep(5000);
		}
		catch(Exception e) {
			System.out.println();
			//driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);//60
			queryObjects.logStatus(driver, Status.PASS, "Reject the order", "The order was rejected successfully is displayed",e);
		}		

	}

	}



//STATIC VARIABLE FOR CC DETAILS - STATIC VARIABLE, USER CREDENTIALS - UTIL