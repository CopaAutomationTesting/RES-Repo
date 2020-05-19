package com.copa.RESscripts;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.ATOscripts.Atoflow;
import com.copa.ATOscripts.ISharesflow;
import com.copa.Util.Assigh_role_pageobjects;
import com.copa.Util.FlightSearchPageObjects;

import FrameworkCode.BFrameworkQueryObjects;

public class PNRsearch extends FlightSearchPageObjects{
	public static String pnrNum = null;
	public static String QuoteStatus = null; 
	public static  String fop = null;
	public  String MultipleFOP = null;
	public static WebElement checkBox = null;
	List<String> etktsnum;
	static List<String> getTaxcode=new ArrayList<>();
	public static String ServiceTax="";
	public static String pricebestbuy=null;
	public static  String ServFee="";	
	public static String Area_Code= null;
	public static String Adv_countryCode= null;
	public static String Adv_email= null;
	public static String Adv_firstName= null;
	public static String Adv_lastname= null;
	public static String Adv_Phone_Number= null;
	public static String Adv_FlightNo= null; 
	public static String Adv_departure= null; 
	public static String Adv_Origin= null;
	public static String Adv_DOB= null;
	public static String Adv_stateCode = null;
	public static String Adv_Passport= null;
	public static String	Adv_EMD_no= null;
	public static String Emd_order_ID= null;
	public static String Cur="";
	static boolean ManualQuote = false;
	public static String DelSeg = "";
	
	public void searchPNR(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {

		try {
			FlightSearch.MultipleFOPType="";
			FlightSearch.MultFOPsubType="";
			FlightSearch.fopCardNums="";
			String storeQuote = FlightSearch.getTrimTdata(queryObjects.getTestData("storeQuoteId"));
			Unwholly.storeQuoteId = storeQuote;
			DelSeg = FlightSearch.getTrimTdata(queryObjects.getTestData("DeleteOldSeg"));
			Unwholly.DeleteSeg = DelSeg;
			String Quote_INFSeg = FlightSearch.getTrimTdata(queryObjects.getTestData("AddInfQuote_Seg"));
			String Quote_SelPax = FlightSearch.getTrimTdata(queryObjects.getTestData("SelPax_Quote"));
			WebDriverWait wait = new WebDriverWait(driver, 50);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class='icon-home']/parent::span")));
			//driver.findElement(By.xpath("//i[@class='icon-home']/parent::span")).click();
			boolean contflag=Search_PNR(driver, queryObjects);
			
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("NewBooking_NoReissue")).equalsIgnoreCase("yes")) {
				NewBooking_NoReissue(driver, queryObjects);
			}			
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("ValidateSecureFlightDoc")).equalsIgnoreCase("yes")) {
				driver.findElement(By.xpath(passangerTabXpath)).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[contains(text(),'Travel document')]")).click();
				Thread.sleep(1000);
				boolean IsVal1=false;
				try{
				 IsVal1 = driver.findElement(By.xpath("//md-input-container[label[contains(text(),'Document Name')]]//md-select[contains(@aria-label,'Passport')]")).isDisplayed();
				}catch(Exception e){
					queryObjects.logStatus(driver, Status.FAIL, "Checking the Secure flight info Passport ","Passport was not available", null);
				}
				if(IsVal1)	{
				queryObjects.logStatus(driver, Status.PASS, "Checking the Secure flight info Passport ","Passport was available", null);
                return;
				}else {
					queryObjects.logStatus(driver, Status.FAIL, "Checking the Secure flight info Passport: Before Ticketed i have given passport details then validating after Ticket ","Passport  was not available", null);
				}
				
				
			}
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("AddInfant")).equalsIgnoreCase("yes")) {
				//click on Add infant button
				driver.findElement(By.xpath("//span[contains(@ng-click,'addTraveler')]//i[@class='icon-add']")).click();
				FlightSearch.loadhandling(driver);
				//Krishna - Update the Below code to same the Infant Name for Further Process
				String SurName = RandomStringUtils.random(6, true, false);
				driver.findElement(By.xpath(surnameXpath)).sendKeys(SurName);
				String FirstName = RandomStringUtils.random(6, true, false);
				driver.findElement(By.xpath(firstnmXpath)).sendKeys(FirstName);
				String PAXName = String.join(", ",SurName,FirstName);
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
					
					/*driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Next')]")).click();
					FlightSearch.loadhandling(driver);
					
					String fareDetails = driver.findElement(By.xpath(farepriceXpath)).getText().trim();
					String fareAmount = fareDetails.split("\\s+")[3];
					//String currencyType = fareDetails.split("\\s+")[4];
					if (Quote_INFSeg.isEmpty()) 
						driver.findElement(By.xpath("//button[text()='Book & File Fare']")).click();
					else 
						driver.findElement(By.xpath("//button[@translate='pssgui.book']")).click();
				//	driver.findElement(By.xpath("//button[text()='Book & File Fare']")).click();
					FlightSearch.loadhandling(driver);
					double totalPaymentamt = 0;
					if (Quote_INFSeg.isEmpty()) {
						String payBalanceamt=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
						
						if (fareAmount.equals(payBalanceamt))
							queryObjects.logStatus(driver, Status.PASS, "Infant Add amount verified ","Infant added after payment", null);
						
						String INFBalanceamt = driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText().trim();
						totalPaymentamt = Double.parseDouble(INFBalanceamt);
					}*/


					//Modifies by navira - 14Mar
					String fareAmount = new String();
					double totalPaymentamt = 0;
					
					//if (driver.findElements(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Next')]")).size()>0) {
					driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Next')]")).click();
					FlightSearch.loadhandling(driver);
					if(driver.findElements(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Add')]")).size()>0) {
						driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Add')]")).click();
						FlightSearch.loadhandling(driver);
						

						driver.findElement(By.xpath("//md-select[@placeholder='Actions']")).click();
						Thread.sleep(1000);
						//driver.findElement(By.xpath("//md-option[@value='Quote']")).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false']//md-option[div[contains(text(),'Quote')]]")).click();

						//Selecting Coupon check box if it is not checked already
						WebElement checkBox = driver.findElement(By.xpath("//md-checkbox[@aria-label='coupon-check']"));
						if(checkBox.getAttribute("aria-checked").equalsIgnoreCase("false"))
							checkBox.click();

						//Click on Next
						driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Next')]")).click();
						FlightSearch.loadhandling(driver);
						
						//Wait until Loading wrapper closed
						//String fareDetails = driver.findElement(By.xpath(farepriceXpath)).getText().trim();
						//String fareAmount = fareDetails.split("\\s+")[2];
						if(driver.findElements(By.xpath("//md-dialog//span")).size()>0) {//[text()='NO VALID FARE/RULE COMBINATION FOR PRICING']
							driver.findElement(By.xpath("//button[@aria-label='cancel']")).click();
							Thread.sleep(2000);	
							fareAmount =  String.valueOf(Manual_Quote_AddINFcase(driver, queryObjects, PAXName));
						}
						//Click on File Fare
						else {
							driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'File Fare')]")).click();
							//Wait until Loading wrapper closed
							FlightSearch.loadhandling(driver);
						}
					}
					else {					
						String fareDetails = driver.findElement(By.xpath(farepriceXpath)).getText().trim();
						fareAmount = fareDetails.split("\\s+")[3];
						//String currencyType = fareDetails.split("\\s+")[4];
						if (Quote_INFSeg.isEmpty()) 
							driver.findElement(By.xpath("//button[text()='Book & File Fare']")).click();
						else 
							driver.findElement(By.xpath("//button[@translate='pssgui.book']")).click();
					//	driver.findElement(By.xpath("//button[text()='Book & File Fare']")).click();
						FlightSearch.loadhandling(driver);
					}
						if (Quote_INFSeg.isEmpty()) {
							String payBalanceamt=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
							
							if (fareAmount.equals(payBalanceamt))
								queryObjects.logStatus(driver, Status.PASS, "Infant Add amount verified ","Infant added after payment", null);
							
							String INFBalanceamt = driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText().trim();
							totalPaymentamt = Double.parseDouble(INFBalanceamt);
						}
											
					//}
					
					//Krishna - Add Specific Infant for Specific Segment
					if(FlightSearch.getTrimTdata(queryObjects.getTestData("AddInfantSpecific")).equalsIgnoreCase("Yes"))  {	
						queryObjects.logStatus(driver, Status.PASS, "Add Infant for specific Segment ","Add Infant for specific Segment", null);
						AddInfantSpecificSegment(driver, queryObjects,PAXName);
					}
					if (Quote_INFSeg.isEmpty()) {
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
					/*List<WebElement> etkts = driver.findElements(By.xpath("//span[text()='Ticket']/following-sibling::span"));
					FlightSearch.gettecketno.clear();
					Unwholly.aTicket = null;  // this is for empty array
					FlightSearch.tickttkype="E-Ticket";
					etkts.forEach(a -> FlightSearch.gettecketno.add(a.getText().trim()));*/   //need to check  this is for sales report validation
					//Clicking on Done button
					driver.findElement(By.xpath("//button[text()='Done']")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//div[div[text()='Tickets']]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//div[@translate='pssgui.ticket']")).click();
					FlightSearch.loadhandling(driver);
					List<WebElement> getetkts=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]"));
					FlightSearch.gettecketno = new ArrayList<>();
					Unwholly.aTicket = null;  // this is for empty array
					FlightSearch.tickttkype="E-Ticket";
					getetkts.forEach(a -> FlightSearch.gettecketno.add(a.getText().trim()));
					//get Ticket amount
					List<WebElement> getetktsamt=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]//preceding-sibling::div"));
					FlightSearch.gettecketamt = new ArrayList<>();
					getetktsamt.forEach(a -> FlightSearch.gettecketamt.add(a.getText().trim()));
					
					for (WebElement Lab : getetkts) {
			               String ticketnumber=Lab.getText().trim();
			               ticketnumber.contains("-");
			               ticketnumber=ticketnumber.split("-")[0].trim();
			               List<WebElement> currentticketstate=driver.findElements(By.xpath("//span[text()='"+ticketnumber+"']/parent::div/parent::div//td[contains(@ng-if,'ticketStatusUpdate')]"));
			               if(currentticketstate.get(0).getText().trim().equalsIgnoreCase("Open")) {
			                    FlightSearch.TktStatus.add("Issue"); 
			                 }      
			        }
					
					
				} else {
						if (Quote_SelPax.contains("INF")) {
							Quote_SelPax = PAXName;
						} else if (Quote_SelPax.isEmpty()) {
							Quote_SelPax = "ALL";
						}
					}
			}
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("ChangeName")).equalsIgnoreCase("yes")) {
				String ModSurname = FlightSearch.getTrimTdata(queryObjects.getTestData("Modify_Surname"));
				String ModFname =FlightSearch.getTrimTdata(queryObjects.getTestData("Modify_FirstName"));
				
				driver.findElement(By.xpath(passangerTabXpath)).click();	
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[div[text()='Personal Information']]")).click();	
				FlightSearch.loadhandling(driver);
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
				String getmodifynme=driver.findElement(By.xpath("//input[@ng-model='personalInfo.model.Lastname']")).getAttribute("value");
				if(getmodifynme.equalsIgnoreCase(ModFname))
					queryObjects.logStatus(driver, Status.PASS, "Modify Name validation", "Name updated succesfully", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Modify Name validation", "unable to update Name", null);
			}
			if (contflag && FlightSearch.getTrimTdata(queryObjects.getTestData("QuoteWithoutPayment")).equalsIgnoreCase("yes")) {
				pricebestbuy=FlightSearch.getTrimTdata(queryObjects.getTestData("pricebestbuy"));
				ServiceTax =FlightSearch.getTrimTdata(queryObjects.getTestData("ServiceTax"));
				QuoteWithoutPayment(driver, queryObjects);
			}
			if (contflag && FlightSearch.getTrimTdata(queryObjects.getTestData("Quote")).equalsIgnoreCase("yes")) {
				//String ChangeSalesOffice=FlightSearch.getTrimTdata(queryObjects.getTestData("ChangeSalesOffice"));
	             if(!Login.Change_Salesoffice.equalsIgnoreCase(""))
	             {
	            	// String SalesOffice[]=ChangeSalesOffice.split(";");
	            	 
					ChangeSalesOffice(driver,queryObjects,Login.Change_Salesoffice,Login.Change_Currency);
	             } 
				reQuote(driver, queryObjects,"","",Quote_INFSeg, Quote_SelPax);
			}
			//Manual Quote--yashodha 
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("ManualQuote")).equalsIgnoreCase("yes")) {
				FlightSearch.currencyType=Login.Cur;
				FlightSearch.Manual_Quote(driver, queryObjects, "No","","",0.0);
				String Shopcart = driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText().trim();
				double totalPaymentamt = Double.parseDouble(Shopcart);
				//Clicking on Checkout button
				driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
				FlightSearch.loadhandling(driver);
				FlightSearch flop=new FlightSearch();
				FlightSearch.MultipleFOPType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPType_Quote"));
				FlightSearch.MultFOPsubType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPSubType_Quote"));
				FlightSearch.fopCardNums = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPCardNums_Quote"));
				String BankNames = queryObjects.getTestData("MultipleFOPBankName_Quote").trim();
				String InstallmentNums = queryObjects.getTestData("MultipleFOPInstallmentNum_Quote").trim();
				//flop.MulFOP(driver,queryObjects,totalPaymentamt,FlightSearch.MultipleFOPType,FlightSearch.MultFOPsubType,FlightSearch.fopCardNums,BankNames,InstallmentNums,"issueTicket");
				flop.MulFOP(driver,queryObjects,totalPaymentamt,FlightSearch.MultipleFOPType,FlightSearch.MultFOPsubType,FlightSearch.fopCardNums,BankNames,InstallmentNums,"issueTicket");
				//meenu_to proceed payment
				driver.findElement(By.xpath("//button[text()='Pay' and not(@disabled='disabled')]")).click();
				// Handling FOID Details::::
				
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
				//meenu_end
			}
			String splits=FlightSearch.getTrimTdata(queryObjects.getTestData("Split"));
			if (contflag && splits.equalsIgnoreCase("yes")) {
				SplitBooking(driver,queryObjects,pnrNum);
			}
			String srtReissue=FlightSearch.getTrimTdata(queryObjects.getTestData("Reissue"));
			if (contflag && srtReissue.equalsIgnoreCase("yes")) {
				Unwholly.reissue(driver,queryObjects);
			}
			//String saddSSRwithoutSplit = FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRwithoutSplit"));
			if (contflag && FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRwithoutSplit")).equalsIgnoreCase("yes")) {
				String addSSRSpecificSegment=FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRSpecificSegment"));
				String totSSRs=queryObjects.getTestData("totSSRs");
				String ssrNames=queryObjects.getTestData("ssrNames");
				String After_Pay_addSSR_OR_Book_Case_addSSR= queryObjects.getTestData("After_Pay_addSSR_OR_Book_Case_addSSR");
																		
				FlightSearch ssr = new FlightSearch();
				
				
				if (FlightSearch.getTrimTdata(queryObjects.getTestData("SpecificSSRsforSpecificPAXAfter")).equalsIgnoreCase("yes")) 
					ssr.addspecificSSR(driver,queryObjects);
				else //if(FlightSearch.getTrimTdata(queryObjects.getTestData("SpecificSSRforAllPAX")).equalsIgnoreCase("yes"))
					ssr.addSSR(driver, queryObjects,"No",addSSRSpecificSegment,totSSRs,ssrNames,After_Pay_addSSR_OR_Book_Case_addSSR);
				//Krishna 
				if(FlightSearch.getTrimTdata(queryObjects.getTestData("SpecificSSRforAllPAX")).equalsIgnoreCase("yes"))   //need to check old cases
					ssr.addSSR(driver, queryObjects,"",addSSRSpecificSegment,totSSRs,ssrNames,After_Pay_addSSR_OR_Book_Case_addSSR);
				String saddSSRwithoutSplit="";
				if(driver.findElements(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).size()>0)
				saddSSRwithoutSplit=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
				Double iaddSSRwithoutSplit = 0.0;
				if(!saddSSRwithoutSplit.isEmpty())
					iaddSSRwithoutSplit=Double.parseDouble(saddSSRwithoutSplit);
				if(iaddSSRwithoutSplit>0)
				{
						// ssr case payment
						queryObjects.logStatus(driver, Status.PASS, "After add ssr Pay the amount","Try to pay amount", null);
					//Click on check out button
					String SSRBalanceamt=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
					driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
					FlightSearch.loadhandling(driver);	
					String sExpected_erromsg=FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_erromsg")).trim();
					FlightSearch.loadhandling(driver);
					String errormsg=FlightSearch.getErrorMSGfromAppliction(driver,queryObjects);
					if (sExpected_erromsg.equalsIgnoreCase("ssr_expected_error")){																			
						if(errormsg.contains("SSR NOT AVAILABLE"))																
						{
							//Click on Cancel button
							driver.findElement(By.xpath("//button[text()='Cancel']")).click();
							//wait till loading wrapper closed
							FlightSearch.loadhandling(driver);
							queryObjects.logStatus(driver, Status.PASS, "Checking the correct SSR NOT AVAILABLE message ","Getting the correct message SSR NOT AVAILABLEfrom pplication", null);
		                    return;
						}
					}
					else if(sExpected_erromsg.isEmpty()){																		
						if(errormsg.contains("SSR NOT AVAILABLE"))																
						{
							//Click on Cancel button
							driver.findElement(By.xpath("//button[text()='Cancel']")).click();
							//wait till loading wrapper closed
							FlightSearch.loadhandling(driver);
							queryObjects.logStatus(driver, Status.WARNING, "Added SSR not available ","Added SSR not available ", null);
		                    return;
						}
					}
					
//					FlightSearch.loadhandling(driver);	//indrajit
					// Before payment amount checking....
					FlightSearch.loadhandling(driver);
					FlightSearch flopy=new FlightSearch();
					//fop = FlightSearch.getTrimTdata(queryObjects.getTestData("FOP"));
					MultipleFOP=FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOP_SSR"));
					/*if(!fop.equals("")) {
						FlightSearch.singleFOP(driver, queryObjects,fop,"issueTicket");
					}*/
					if(MultipleFOP.equalsIgnoreCase("yes")) {
						double totalPaymentamt = Double.parseDouble(SSRBalanceamt);
						FlightSearch.MultipleFOPType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPType_SSR"));
						FlightSearch.MultFOPsubType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPSub_SSR"));
						FlightSearch.fopCardNums = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPCardNums_SSR"));
						String BankNames = queryObjects.getTestData("MultipleFOPBankName_SSR").trim();
						String InstallmentNums = queryObjects.getTestData("MultipleFOPInstallmentNum_SSR").trim();
						flopy.MulFOP(driver,queryObjects,totalPaymentamt,FlightSearch.MultipleFOPType,FlightSearch.MultFOPsubType,FlightSearch.fopCardNums,BankNames,InstallmentNums,"SSRPayment");
					}
					else
						FlightSearch.singleFOP(driver, queryObjects,"Cash","Add SSR case");
					
					driver.findElement(By.xpath("//button[text()='Pay' and not(@disabled='disabled')]")).click();
					// Handling FOID Details::::
					
					FlightSearch.enterFoiddetails(driver,queryObjects);
					
					//Handling Email recipients popup
					FlightSearch.emailhandling(driver,queryObjects);
					
					String Review_Reject = FlightSearch.getTrimTdata(queryObjects.getTestData("Review_Reject"));
					String Biller = FlightSearch.getTrimTdata(queryObjects.getTestData("BillerDetails"));
					boolean PymtError_Msg = FlightSearch.paymentvalidation(driver, queryObjects, Review_Reject);
					
					/*try {
						wait = new WebDriverWait(driver, 120);
						wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
						String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
						queryObjects.logStatus(driver, Status.PASS, "Payment", statusMessage, null);

					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Payment", "Payment Unsuccessful: " + e.getMessage() , e);
					}*/
					if ((!PymtError_Msg)) 
						driver.findElement(By.xpath("//button[text()='Done']")).click();
						FlightSearch.loadhandling(driver);
					if ((FlightSearch.getTrimTdata(queryObjects.getTestData("PNRVerify_CCFOP")).equalsIgnoreCase("YES"))) {		
						
						Unwholly.CyberSoursePNR(driver, queryObjects);
						FlightSearch.Aftecybebersourec_Update_check_GUI=false;
					}
				}
			}
				String Assignseaatstr=FlightSearch.getTrimTdata(queryObjects.getTestData("AssignSeat"));
			if(contflag && Assignseaatstr.equalsIgnoreCase("yes") && srtReissue.equalsIgnoreCase("") ) {
				
				// Assign seat...............
				String sAssignseatAllPAX=FlightSearch.getTrimTdata(queryObjects.getTestData("AssignseatAllPAX"));
				String assignSeatSinglePAX = FlightSearch.getTrimTdata(queryObjects.getTestData("AssignSeatSinglePAX"));
				String assignSpecSeat = FlightSearch.getTrimTdata(queryObjects.getTestData("AssignSpecSeat"));
				//Get total PAX
				int totalPax = Integer.parseInt(driver.findElement(By.xpath(PaxCountXpath)).getText().trim());
				FlightSearch.assignSeat(driver,queryObjects,totalPax, sAssignseatAllPAX, assignSeatSinglePAX, assignSpecSeat);
				FlightSearch payseat=new FlightSearch();
				try{
					String saddpayemtSeat=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
					payseat.paymentAfterassignseat(driver, queryObjects);
				}
				catch(Exception e){
					queryObjects.logStatus(driver, Status.INFO,"cart details not available","Payment details not available",null);
				}

				
			}
			if(contflag && FlightSearch.getTrimTdata(queryObjects.getTestData("AddFFP")).equalsIgnoreCase("yes")) {
				//PNRsearch addFFP = new PNRsearch();
				//addFFP.addFFP(driver, queryObjects);
				
				 
				String stotalFFP=FlightSearch.getTrimTdata(queryObjects.getTestData("totalFFP"));
				String sFFPnumbers=FlightSearch.getTrimTdata(queryObjects.getTestData("FFPnumbers"));
				String sFFPcode=FlightSearch.getTrimTdata(queryObjects.getTestData("FFPcode"));
				String ssurnm = FlightSearch.getTrimTdata(queryObjects.getTestData("SurName"));
				String sfirstnm = FlightSearch.getTrimTdata(queryObjects.getTestData("FirstName"));
				
				
				if (!stotalFFP.isEmpty()) {
					int itotalFFP=Integer.parseInt(stotalFFP);
					if (itotalFFP==1) {
						FlightSearch.FFcode=sFFPcode;
						FlightSearch.FFnum=sFFPnumbers;
						FlightSearch.ffplastName=ssurnm;
						FlightSearch.ffpfirstName=sfirstnm;
						FlightSearch.ffp(driver,queryObjects);	
					}
					else if (itotalFFP>1) {
						String[] asurnm=ssurnm.split(";");
						String[] afirstnm=sfirstnm.split(";");
						String[] aFFPnumbers=sFFPnumbers.split(";");
						String[] aFFPcode=sFFPcode.split(";");
						for (int iffpiter = 0; iffpiter < itotalFFP; iffpiter++) {
							FlightSearch.FFcode=aFFPcode[iffpiter];
							FlightSearch.FFnum=aFFPnumbers[iffpiter];
							FlightSearch.ffplastName=asurnm[iffpiter];
							FlightSearch.ffpfirstName=afirstnm[iffpiter];
							FlightSearch.ffp(driver,queryObjects);
						}
					}
				}
			
			}
			if (contflag && FlightSearch.getTrimTdata(queryObjects.getTestData("Refund")).equalsIgnoreCase("yes")) {
				FlightSearch.refundticket(driver, queryObjects);
			}
			/// Atul - Add Infant after SSR
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("AddInfant_AfterSSR")).equalsIgnoreCase("yes")) {
				Add_Infant(driver, queryObjects);
			}
			
			String EmerCont = FlightSearch.getTrimTdata(queryObjects.getTestData("EmergencyContact"));
			if (EmerCont.equalsIgnoreCase("yes")) {
				Unwholly.EmergencyContact(driver, queryObjects);
			}
			//sET WAIVER
			//aD TO oRDER
			
		}
		catch(Exception e ) {
			queryObjects.logStatus(driver, Status.FAIL, "Search and Quote PNR", "Search and Quote operation failed: " + e.getMessage() , e);
		}
	}
	// Atul - method for adding infant
	
		public static void Add_Infant(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
			   //click on Add infant button
			WebDriverWait wait = new WebDriverWait(driver, 50);
			driver.findElement(By.xpath("//span[contains(@ng-click,'addTraveler')]//i[@class='icon-add']")).click();
			FlightSearch.loadhandling(driver);
			String sname=RandomStringUtils.random(6, true, false);
			driver.findElement(By.xpath(surnameXpath)).sendKeys(sname);
			String fname=RandomStringUtils.random(6, true, false);
			driver.findElement(By.xpath(firstnmXpath)).sendKeys(fname);
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
				//Atul - 24Jan - If next is disabled click Add
				
				if (driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Next')]")).isEnabled()) {
					driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Next')]")).click();
					FlightSearch.loadhandling(driver);
				} else {
					driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Add')]")).click();
					FlightSearch.loadhandling(driver);
				}
				
				//Atul - 24Jan - To add Manual Quote
				String ManQuote = FlightSearch.getTrimTdata(queryObjects.getTestData("AddInfant_AfterSSR"));
				if (ManQuote.equalsIgnoreCase("yes")) {
					String INFPax_nm=(sname).toUpperCase();
					Manual_Quote_AddINFcase(driver, queryObjects, INFPax_nm);
					
				}
				
				//driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Next')]")).click();
				//FlightSearch.loadhandling(driver);
				
				// Atul - 27Jan, Commented
				String fareDetails = driver.findElement(By.xpath(farepriceXpath)).getText().trim();
				String fareAmount = fareDetails.split("\\s+")[3];
				String currencyType = fareDetails.split("\\s+")[4];
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
	public int EMDticketcount(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		List<WebElement> EMDticketnumbers=new ArrayList<>();
		try{
			driver.findElement(By.xpath(TicketXpath)).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//div[text()='EMD']/parent::div")).click();
			FlightSearch.loadhandling(driver);
			 EMDticketnumbers=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']"));
		}
		catch(Exception e ) {
			queryObjects.logStatus(driver, Status.FAIL, "EMD verification", "EMD verification: " + e.getMessage() , e);
		}
		return EMDticketnumbers.size();
	}

	
	public void SplitBooking(WebDriver driver, BFrameworkQueryObjects queryObjects , String pnrNum)throws Exception{
		
		try {
		queryObjects.logStatus(driver, Status.PASS, "Performing Split operation","Split the PNR pnr is:"+pnrNum,null);
		driver.findElement(By.xpath("//div[text()='Order']")).click();
		FlightSearch.loadhandling(driver);
		//split the pnr
		driver.findElement(By.xpath("//md-select[@placeholder='Actions']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//md-option[@value='split-booking']")).click();
		Thread.sleep(2000);	
		//check box selection for split pnr page
		driver.findElement(By.xpath("//md-dialog//div[div[@class='ng-scope']]/div[1]/md-checkbox")).click();
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//button[text()='Split']")).click();
		FlightSearch.loadhandling(driver);
		String splited_pnris=driver.findElement(By.xpath("//div[@ng-if='splitBooking.splitTicket']")).getText().trim();
		if (!splited_pnris.isEmpty()) {
			splited_pnris=splited_pnris.split("\\s+")[5];
		}
		driver.findElement(By.xpath("//button[text()='OK']")).click();
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//div[text()='Order']")).click();
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//div[@translate='pssgui.split.info']")).click();  // click on split tab in order landing page for old pnr
		FlightSearch.loadhandling(driver);
		String getpnr=driver.findElement(By.xpath("//td[contains(@ng-click,'splitBooking.stateChange')]")).getText().trim();
		if (splited_pnris.equalsIgnoreCase(getpnr)) 
			queryObjects.logStatus(driver, Status.PASS, "OLD PNR opend case-->After Split the  PNR verfying Split tab details", "Split tab  dispaying new PNR", null);
		else
			queryObjects.logStatus(driver, Status.FAIL, "OLD PNR opend case-->After Split the  PNR verfying Split tab details", "Split tab  should dispay new PNR expected=: "+splited_pnris +" actual=: "+getpnr, null);
		
		driver.findElement(By.xpath("//td[contains(@ng-click,'splitBooking.stateChange')]")).click();  //clicking on old pnr
		
		queryObjects.logStatus(driver, Status.PASS, "Trying to navigate Old PNR to New PNR", "Trying to navigate Old PNR to New PNR", null);
		FlightSearch.loadhandling(driver);
		
		String newpnrpop=driver.findElement(By.xpath("//div[@action='pnr']/div/div[1]/div[1]/div[1]")).getText().trim();
		if (splited_pnris.equalsIgnoreCase(newpnrpop)) 
			queryObjects.logStatus(driver, Status.PASS, "After Split click new pnr in split tab and verifing pnr can open", "we can navigate OND PNR to NEW PNR", null);
		else
			queryObjects.logStatus(driver, Status.FAIL, "After Split click new pnr in split tab and verifing pnr can open", "we Should navigate OLD PNR to NEW PNR pnr expected=: "+splited_pnris +" actual=: "+newpnrpop, null);
		Thread.sleep(2000);
		String SplitCase_ValidateEMD=FlightSearch.getTrimTdata(queryObjects.getTestData("SplitCase_ValidateEMD"));
		if((SplitCase_ValidateEMD.equalsIgnoreCase("yes"))){   
			//new pnr EMD verification
			if (EMDticketcount(driver, queryObjects)>0) // After split new pnr EMD verification
				queryObjects.logStatus(driver, Status.PASS, "New PNR  case EMD verification", "After Split PNR EMD Ticktes showing correctly", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "New PNR  case EMD verification", "After Split PNR EMD Ticktes should show correctly", null);
		}
		// Addinfant to splited Pnr ---yashodha
		if(FlightSearch.getTrimTdata(queryObjects.getTestData("AddInfant_AfterSplit")).equalsIgnoreCase("yes")) {
			Add_Infant(driver, queryObjects);
		}
		driver.findElement(By.xpath("//div[text()='Order']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@translate='pssgui.split.info']")).click(); //new pnr case clicking split tab
		// get old pnr in split tab 
		String getoldpnr=driver.findElement(By.xpath("//td[contains(@ng-click,'splitBooking.stateChange')]")).getText().trim();
		if (pnrNum.equalsIgnoreCase(getoldpnr)) 
			queryObjects.logStatus(driver, Status.PASS, "New PNR opend case-->After Split the  PNR verfying Split tab details", "Split tab  dispaying OLD PNR", null);
		else
			queryObjects.logStatus(driver, Status.FAIL, "New PNR opend case-->After Split the  PNR verfying Split tab details", "Split tab  should dispay OLD PNR expected=: "+pnrNum +" actual=: "+getoldpnr, null);
		String addSSRSpecificSegment=FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRSpecificSegment"));
		String totSSRs=queryObjects.getTestData("totSSRs");
		String ssrNames=queryObjects.getTestData("ssrNames");
		String After_Pay_addSSR_OR_Book_Case_addSSR= queryObjects.getTestData("After_Pay_addSSR_OR_Book_Case_addSSR"); 
		String afterSplitssr=FlightSearch.getTrimTdata(queryObjects.getTestData("Aftersplit_addSSR"));
		if(afterSplitssr.equalsIgnoreCase("yes"))
		{
			FlightSearch flig=new FlightSearch();
			flig.addSSR(driver,queryObjects,afterSplitssr,addSSRSpecificSegment,totSSRs,ssrNames,After_Pay_addSSR_OR_Book_Case_addSSR);
			
			String sssrcaseBalanceamt="";
			if(driver.findElements(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).size()>0)
				driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText().trim();
			
			if(!sssrcaseBalanceamt.isEmpty())
			{
				// ssr case payment
				queryObjects.logStatus(driver, Status.PASS, "After add ssr Pay the amount","Try to pay amount", null);
				//Click on check out button
				driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
				FlightSearch.loadhandling(driver);
				FlightSearch.singleFOP(driver, queryObjects,"Cash","After add ssr");
				//Click on pay button
				driver.findElement(By.xpath("//button[text()='Pay']")).click();
				
				// Handling FOID Details::::
				
				FlightSearch.enterFoiddetails(driver,queryObjects);
				
				//Handling Email recipients popup
				FlightSearch.emailhandling(driver,queryObjects);
				try {
					WebDriverWait wait = new WebDriverWait(driver, 120);
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
					String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
					queryObjects.logStatus(driver, Status.PASS, "EMD Payment", statusMessage, null);
				}
				catch(Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Payment", "Payment Unsuccessful: " + e.getMessage() , e);
				}
				
				//No.of EMDs issued
				try {
					List<WebElement> etkts = driver.findElements(By.xpath("//span[text()='EMD']/following-sibling::span"));
					int totalTKTs = etkts.size();
					etktsnum = new ArrayList<>();
					
					etkts.forEach(a -> etktsnum.add(a.getText().trim()));
					
					if(totalTKTs!=0) 
						queryObjects.logStatus(driver, Status.PASS, "EMD Checking", "EMD Created", null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "EMD Checking", "EMD should create", null);
				}
				catch(Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "EMD was not created " , e.getMessage(), e);
				}
				
				//Clicking on Done button
				driver.findElement(By.xpath("//button[text()='Done']")).click();
				/*if (contflag && FlightSearch.getTrimTdata(queryObjects.getTestData("reissue")).equalsIgnoreCase("yes")) {
					Unwholly.reissue(driver,queryObjects);
				}*/
			}
		}
		// After split PNR reissue case.
		String afterSplitReissue=FlightSearch.getTrimTdata(queryObjects.getTestData("Reissue"));
		if(afterSplitReissue.equalsIgnoreCase("YES")){
			Unwholly.classselectbon=false;
			Unwholly.reissue(driver,queryObjects);
		}
		driver.findElement(By.xpath("//div[text()='Order']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@translate='pssgui.split.info']")).click();
		Thread.sleep(2000);
		queryObjects.logStatus(driver, Status.PASS, "Trying to navigate New PNR to OLD PNR", "Trying to navigate New PNR to OLD PNR", null);
		driver.findElement(By.xpath("//td[contains(@ng-click,'splitBooking.stateChange')]")).click();  //clicking on new pnr
		FlightSearch.loadhandling(driver);
		String oldpnrpop=driver.findElement(By.xpath("//div[@action='pnr']/div/div[1]/div[1]/div[1]")).getText().trim();
		if (pnrNum.equalsIgnoreCase(oldpnrpop)) 
			queryObjects.logStatus(driver, Status.PASS, "After Split click new pnr in split tab and verifing pnr can open", "we can navigate New PNR to OLD PNR", null);
		else
			queryObjects.logStatus(driver, Status.FAIL, "After Split click new pnr in split tab and verifing pnr can open", "we should navigate New PNR to OLD PNR expected=: "+pnrNum +" actual=: "+oldpnrpop, null);
	
		}
		catch(Exception e ) {
			queryObjects.logStatus(driver, Status.FAIL, "Split PNR failed", "Exception was thrown: " + e.getMessage() , e);
		}

	
}

	  public  static void TaxCodeValidation(WebDriver driver, BFrameworkQueryObjects queryObjects,String valueXpath,String verificationin) throws Exception{
		  
		  String TaxCode=FlightSearch.getTrimTdata(queryObjects.getTestData("TaxCode"));
		  String []splitTaxCode;
		  String alltax="";
			List<WebElement> taxcode=driver.findElements(By.xpath(valueXpath));
			for (WebElement gettaxcodeis: taxcode ) {
				String ss=gettaxcodeis.getText().trim();
				getTaxcode.add(ss);
				alltax=alltax+ss+";";
			}
			
		  if(TaxCode.contains(";")){
			  splitTaxCode=TaxCode.split(";");
			  for (String gettaxcodeisinexcel: splitTaxCode ) {
				  TaxCode=gettaxcodeisinexcel;
					if (getTaxcode.contains(TaxCode)) 
						queryObjects.logStatus(driver, Status.PASS, "Tax code verification in "+verificationin, "Tax code was available code is "+TaxCode, null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "Tax code verification in "+verificationin, "Tax code should available, Expected code is "+TaxCode+" Actual code is"+alltax, null);
				}
			  
		  }
		  else{
			if (getTaxcode.contains(TaxCode)) 
				queryObjects.logStatus(driver, Status.PASS, "Tax code verification in "+verificationin, "Tax code was available code is "+TaxCode, null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Tax code verification in "+verificationin, "Tax code should available, Expected code is "+TaxCode+" Actual code is"+alltax, null);
			  }
	}
	public static void reQuote(WebDriver driver, BFrameworkQueryObjects queryObjects,String waive,String Waive_Fee,String QuoteSeg, String SelPax) throws Exception{

		String addSSRSpecificSegment=FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRSpecificSegment"));
		String totSSRs=queryObjects.getTestData("totSSRs");
		String ssrNames=queryObjects.getTestData("ssrNames");
		String After_Pay_addSSR_OR_Book_Case_addSSR= queryObjects.getTestData("After_Pay_addSSR_OR_Book_Case_addSSR"); 

		
		if (FlightSearch.getTrimTdata(queryObjects.getTestData("EnrollConnect_beforeQuote")).equalsIgnoreCase("yes")){
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
		if(FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRBeforePay")).equalsIgnoreCase("yes")){
			FlightSearch ssr=new FlightSearch();					
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("SpecificSSRsforSpecificPAXBefore")).equalsIgnoreCase("yes")) 
				ssr.addspecificSSR(driver,queryObjects);
			else //if(FlightSearch.getTrimTdata(queryObjects.getTestData("SpecificSSRforAllPAX")).equalsIgnoreCase("yes"))
				ssr.addSSR(driver,queryObjects,"No",addSSRSpecificSegment,totSSRs,ssrNames,After_Pay_addSSR_OR_Book_Case_addSSR);
		}
		driver.findElement(By.xpath("//div[@translate='pssgui.order']")).click();
		FlightSearch.loadhandling(driver);
		//Select Actions check box only if it is not selected
		checkBox = driver.findElement(By.xpath("//md-checkbox[contains(@class,'segment-checkbox')]"));
		if(checkBox.getAttribute("aria-checked").equalsIgnoreCase("false"))
			checkBox.click();
		//click on Actions list
				driver.findElement(By.xpath("//md-select[@aria-label='Actions']")).click();
				Thread.sleep(2000);
				//Selecting Quote Option
				driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false']//md-option[div[contains(text(),'Quote')]]")).click();
				FlightSearch.loadhandling(driver);
		  
				//Selecting Coupon check box if it is not checked already
				checkBox = driver.findElement(By.xpath("//md-checkbox[@aria-label='coupon-check']"));
				if(checkBox.getAttribute("aria-checked").equalsIgnoreCase("false"))
					checkBox.click();
		//Selecting Particular Segment
		if (!QuoteSeg.contains("All") && !QuoteSeg.isEmpty()) {
			checkBox.click();
			for (int seg = 1; seg <= driver.findElements(By.xpath("//md-checkbox[contains(@ng-if,'flightResult')]")).size(); seg++) {
				if (driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'flightResult.segments')])["+seg+"]//div[contains(@airport-code,'.origin')]")).getText().contains(QuoteSeg.substring(0, QuoteSeg.indexOf("-")))) {
					if (driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'flightResult.segments')])["+seg+"]//div[contains(@airport-code,'.destination')]")).getText().contains(QuoteSeg.substring(QuoteSeg.indexOf("-")+1, QuoteSeg.length()))) {
						if (driver.findElement(By.xpath("(//md-checkbox[contains(@ng-if,'flightResult')])["+seg+"]")).getAttribute("aria-checked").contains("false")) {
							driver.findElement(By.xpath("(//md-checkbox[contains(@ng-if,'flightResult')])["+seg+"]")).click();
						}
					} else if (driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'flightResult.segments')])["+(seg+1)+"]//div[contains(@airport-code,'.destination')]")).getText().contains(QuoteSeg.substring(QuoteSeg.indexOf("-")+1, QuoteSeg.length()))) {
						if (driver.findElement(By.xpath("(//md-checkbox[contains(@ng-if,'flightResult')])["+(seg+1)+"]")).getAttribute("aria-checked").contains("false")) {
							driver.findElement(By.xpath("(//md-checkbox[contains(@ng-if,'flightResult')])["+(seg+1)+"]")).click();
						}
					}
				}
			}
		}
		/*//click on Actions list
		driver.findElement(By.xpath("//md-select[@aria-label='Actions']")).click();
		Thread.sleep(2000);
		//Selecting Quote Option
		driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false']//md-option[div[contains(text(),'Quote')]]")).click();
		FlightSearch.loadhandling(driver);
		//Selecting Coupon check box if it is not checked already
		checkBox = driver.findElement(By.xpath("//md-checkbox[@aria-label='coupon-check']"));
		if(checkBox.getAttribute("aria-checked").equalsIgnoreCase("false"))
			checkBox.click();*/
		if(FlightSearch.getTrimTdata(queryObjects.getTestData("pricebestbuy")).equalsIgnoreCase("yes")) {
			driver.findElement(By.xpath(PriceOptionXpath)).click();
			Thread.sleep(500);
			driver.findElement(By.xpath(BestBuyXpath)).click();
			queryObjects.logStatus(driver, Status.PASS, "PricebestBuy", "Booking seat with best buy option", null);
		}
		String TourCode=FlightSearch.getTrimTdata(queryObjects.getTestData("TourCode"));
		if(!TourCode.equalsIgnoreCase(""))
			driver.findElement(By.xpath("//input[contains(@ng-model,'quoteInfoCtrl.model.pricingOptions.AccountCode')]")).sendKeys(TourCode);
		
		String Book_Discount=queryObjects.getTestData("Book_Discount");
		String Book_discountType = queryObjects.getTestData("Book_discountType");//2
		String Book_PaxForDiscount = queryObjects.getTestData("Book_PaxForDiscount");//4
		String Book_noofSeg = queryObjects.getTestData("Book_noofSeg");//6
		String Book_discountValue = queryObjects.getTestData("Book_discountValue");//7
		String Book_Discount_valueType = queryObjects.getTestData("Book_Discount_valueType");//8
		String Book_Discount_Taxes = queryObjects.getTestData("Book_Discount_Taxes");//9
		String Book_Discount_Ticket_Designator = queryObjects.getTestData("Book_Discount_Ticket_Designator");//10
		// Entering PAX Discount
		boolean BeforeCreatePNR=false;
		FlightSearch fss=new FlightSearch();
		if(Book_Discount.equalsIgnoreCase("yes"))
			fss.Discount(driver, queryObjects, BeforeCreatePNR,Book_discountType,Book_PaxForDiscount,Book_noofSeg,Book_discountValue,Book_Discount_valueType,Book_Discount_Taxes,Book_Discount_Ticket_Designator);
		
		//Navira - Copied from FlightSearch -27Mar
		String addTax = FlightSearch.getTrimTdata(queryObjects.getTestData("PriceQuote_AddTax"));
		String addTaxCode = FlightSearch.getTrimTdata(queryObjects.getTestData("PriceQuote_AddTax_Code"));
		String addTaxValue = FlightSearch.getTrimTdata(queryObjects.getTestData("PriceQuote_AddTax_Value"));
		String addTaxType = FlightSearch.getTrimTdata(queryObjects.getTestData("PriceQuote_AddTax_Type"));
	
		if (addTax.equalsIgnoreCase("YES")) {
			if (!addTaxCode.isEmpty()) {
				driver.findElement(By.xpath("//div[4]/div[1]/div/div[1]/div/i")).click();
				driver.findElement(By.xpath("//md-input-container//input[contains(@ng-model,'taxDetail.AddTaxCode')]")).sendKeys(addTaxCode);
						  

				driver.findElement(By.xpath("//md-input-container//input[contains(@ng-model,'taxDetail.Amount')]")).sendKeys(addTaxValue);
				driver.findElement(By.xpath("//md-select[contains(@ng-model,'taxDetail.AddTaxType')]")).click();
				if (addTaxType.equalsIgnoreCase("Amt")) {
					driver.findElement(By.xpath("//md-option//div[contains(text(),'Amount')]")).click();								
				} 
				else {
				driver.findElement(By.xpath("//md-select[contains(@ng-model,'taxDetail.AddTaxType')]//div[contains(text(),'Percentage')]")).click();									
				}
			}
		}
		
		//Entering pax reduction type
		String paxReductionType = FlightSearch.getTrimTdata(queryObjects.getTestData("paxReductionType"));
		String paxReductionTypeCount = queryObjects.getTestData("paxReductionTypeCount");
		String paxReductionTypeName = queryObjects.getTestData("paxReductionTypeName");
		if(paxReductionType.equalsIgnoreCase("yes")) {
			FlightSearch reductiontype=new FlightSearch();
			reductiontype.PAXreductionType(driver, queryObjects,paxReductionTypeCount,paxReductionTypeName);
		}
		String FirstflightforAto=driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]//tbody[1]//child::td[@class='flight-name']/span")).getText();
       // Login.envwrite(Login.shareflightnm, FirstflightforAto);
     //   Login.shareflightnm=FirstflightforAto;
    	//Select Passenger
        if (!SelPax.isEmpty() && !SelPax.equalsIgnoreCase("ALL")) {
        	driver.findElement(By.xpath("//span[contains(text(),'Select passenger')]/../../preceding-sibling::i")).click();
    		Thread.sleep(200);
    		for (int sp = 1; sp <= driver.findElements(By.xpath("//div[contains(@class,'pax-type')]")).size(); sp++) {
    			if (driver.findElement(By.xpath("(//div[contains(@class,'pax-type')])["+sp+"]")).getText().contains(SelPax.toUpperCase())) {
    				if (driver.findElement(By.xpath("(//md-checkbox[@aria-label='Select Passenger'])["+sp+"]")).getAttribute("aria-checked").contains("false")) {
    					driver.findElement(By.xpath("(//md-checkbox[@aria-label='Select Passenger'])["+sp+"]")).click();
    				}
				} else {
					if (driver.findElement(By.xpath("(//md-checkbox[@aria-label='Select Passenger'])["+sp+"]")).getAttribute("aria-checked").contains("true")) {
    					driver.findElement(By.xpath("(//md-checkbox[@aria-label='Select Passenger'])["+sp+"]")).click();
    				}
				}
			}
		}
        
      
		//Click on Next
		driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Next')]")).click();
		FlightSearch.loadhandling(driver);
		
		//Meenu : Switch to manual quote if NO VALID FARE pops up
		ManualQuote=false;
		String quotescreemsg=FlightSearch.getErrorMSGfromAppliction(driver, queryObjects);
		if (quotescreemsg.contains("NO VALID FARE")|| quotescreemsg.contains("No fare is available")|| quotescreemsg.contains("No Price information") ){
			ManualQuote=true; // if No valid fare rule it is going to manual quote .....
			driver.findElement(By.xpath(cancelbuttonXpath)).click();
			FlightSearch.loadhandling(driver);
			FlightSearch.currencyType=Login.Cur;
			queryObjects.logStatus(driver, Status.PASS, "No valid Fare case doing Manual Quote","No valid Fare case doing Manual Quote", null);
		}	
		
		String ManulaQuote_Fare_amt = null;
		String ManualQuote_Euivalent_amt = null;
		if (FlightSearch.getTrimTdata(queryObjects.getTestData("ManualQuote")).equalsIgnoreCase("yes")|| ManualQuote==true) {  // Manual Quote case click book button
			//driver.findElement(By.xpath("//button[text()='Book' and contains(@ng-click,'quote.stateChange')]")).click();
			double Manua_Quote_fareanycurrency=0;
			double Service_Fee = 0;
			try{
				FlightSearch.loadhandling(driver);
				//int ss=Integer.parseInt(FlightSearch.convertinteger(ManulaQuote_Fare_amt.split("\\s+")[0]));
				
				/*if((FlightSearch.currencyType.trim().equalsIgnoreCase("ARS")|| FlightSearch.currencyType.trim().equalsIgnoreCase("COP"))){
					Manua_Quote_fareanycurrency=FlightSearch.Manual_Quote(driver, queryObjects,"yes",ManulaQuote_Fare_amt,ManualQuote_Euivalent_amt,Service_Fee);
				}
				else{
					Manua_Quote_fareanycurrency=FlightSearch.Manual_Quote(driver, queryObjects,"No","","",Service_Fee);
				}*/
				Manua_Quote_fareanycurrency=FlightSearch.Manual_Quote(driver, queryObjects,"No","","",Service_Fee);
				//Manua_Quote_fareanycurrency=Manual_Quote(driver, queryObjects,"No","","",Service_Fee);
				FlightSearch.loadhandling(driver);
			}
			catch(Exception e){
				queryObjects.logStatus(driver, Status.FAIL, "Manual quote", "Manual Quote Case Fail",e);	
			}
			if(ManualQuote==true){ // no fare case adding service Fee
				String Service_Fee_s="";
				//String Tot_Pax = FlightSearch.getTrimTdata(queryObjects.getTestData("TotalPax"));  
				List<WebElement> ServiceFee=driver.findElements(By.xpath("//div[@model='fee.TotalAmount']/div[contains(@ng-class,'amountCtrl')]"));
				int servi=ServiceFee.size();
				if(ServiceFee.size()>0){
					Service_Fee_s = ServiceFee.get(0).getText().trim();
					Service_Fee_s=Service_Fee_s.split("\\s+")[0];
					Service_Fee=Double.parseDouble(Service_Fee_s) * FlightSearch.totalnoofPAX;
					Service_Fee=Double.parseDouble(FlightSearch.roundDouble(String.valueOf(Service_Fee)));
					queryObjects.logStatus(driver, Status.PASS,"Servie fee is:","Service Fee is: "+Service_Fee,null);
				}
				Manua_Quote_fareanycurrency=Manua_Quote_fareanycurrency+Service_Fee;
			}
			FlightSearch.fareAmount=String.valueOf(Manua_Quote_fareanycurrency);
			
			//proceed to checkout and make payment
			
			driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
			FlightSearch.loadhandling(driver);
			
			FlightSearch flop=new FlightSearch();
			FlightSearch.MultipleFOPType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPType_Quote"));
			FlightSearch.MultFOPsubType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPSubType_Quote"));
			FlightSearch.fopCardNums = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPCardNums_Quote"));
			String BankNames = queryObjects.getTestData("MultipleFOPBankName_Quote").trim();
			String InstallmentNums = queryObjects.getTestData("MultipleFOPInstallmentNum_Quote").trim();
			double totalPaymentamt = Double.parseDouble(FlightSearch.fareAmount);
			flop.MulFOP(driver,queryObjects,totalPaymentamt,FlightSearch.MultipleFOPType,FlightSearch.MultFOPsubType,FlightSearch.fopCardNums,BankNames,InstallmentNums,"issueTicket");
			
			//pay
			driver.findElement(By.xpath("//button[text()='Pay']")).click();
			Thread.sleep(2000);
			//Handling FOID details and Email
			FlightSearch.enterFoiddetails(driver, queryObjects);
			
			Thread.sleep(2000);
			FlightSearch.emailhandling(driver,queryObjects);
			
			//Wait until payment successful message comes
			try {
				WebDriverWait wait = new WebDriverWait(driver, 120);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
				String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
				queryObjects.logStatus(driver, Status.PASS, "Search and Quote PNR", statusMessage, null);
			}
			catch(Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Search and Quote PNR", "Payment Unsuccessful: " + e.getMessage() , e);
			}
			//Click on Done button
			driver.findElement(By.xpath("//button[text()='Done']")).click();
			//wait till loading wrapper closed
			FlightSearch.loadhandling(driver);
			//Check for ticket status
			String status = driver.findElement(By.xpath("//div[@action='pnr']/div/div[1]/div[1]/div[2]")).getText().trim();
			if(status.equalsIgnoreCase("ticketed")) {
				queryObjects.logStatus(driver, Status.PASS, "Search and Quote PNR", "Succesffully Ticketed", null);
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "Search and Quote PNR", "Not Ticketed: status - " + status , null);
			}
		}
		//end
		else  //auto quote
		{
	        if(!(ServFee.isEmpty()))
	        {
	        	String ServFeeNextQuote=driver.findElement(By.xpath("//div[contains(@ng-repeat,'fee in quoteInfo.Fees')]/div[contains(@ng-if,'fee.Type')]//div[4]//child::div[contains(@ng-class,'!amountCtrl')]")).getText().trim();
	           String ServFeeNextQuote1[]=ServFeeNextQuote.split(" ");
	           ServFeeNextQuote=ServFeeNextQuote1[0];
	           
	           double ServFeedouble=Double.parseDouble(ServFeeNextQuote);
	           double ServFeefirstQuote=Double.parseDouble(ServFee);
	           if(!(ServFeedouble==ServFeefirstQuote))
	             queryObjects.logStatus(driver, Status.PASS, "Comparing the Service fee on different sales office ", "Service fee is different as expected: "+ServFeefirstQuote+" expected: "+ServFeedouble, null);
	           else
	             queryObjects.logStatus(driver, Status.PASS, "Comparing the Service fee on different sales office ", "Service fee is same not  as expected: "+ServFeefirstQuote+" expected: "+ServFeedouble, null);      
	        }
	
			///
			//Wait until Loading wrapper closed
			String fareDetails = driver.findElement(By.xpath(farepriceXpath)).getText().trim();
			String fareAmount = fareDetails.split("\\s+")[3];
			
			  String BaggageRuleverification=FlightSearch.getTrimTdata(queryObjects.getTestData("BaggageRuleverification"));
				if (BaggageRuleverification.equalsIgnoreCase("yes")) {
					List<WebElement> BaggRules=driver.findElements(By.xpath("//div[div[toggle-title[contains(text(),'Baggage Rules')]]]/i"));
					for (int iBaggRules = 0; iBaggRules < BaggRules.size(); iBaggRules++) {
						BaggRules.get(iBaggRules).click();
						if(driver.findElements(By.xpath("//div[contains(text(),'Baggage Charges')]")).size()>0)
							queryObjects.logStatus(driver, Status.PASS, "Quote Screen Baggage Charges checking:","Quote screen baggage details displaying",null);
					}		
				}
			
			//Click on File Fare
			driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'File Fare')]")).click();
			//Wait until Loading wrapper closed
			FlightSearch.loadhandling(driver);
			
			String totalamt=(driver.findElement(By.xpath("//div[@model='currency.total']/div")).getText().trim());
			String Balanceamt=(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText().trim());
			
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
			//Click on check out button
		//	driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
			
			// Before payment amount checking....
			//FlightSearch.loadhandling(driver);
			/*
			 * Click on Pay button
			 * By default selecting Cash as FOP
			 * May required to change in future depends on requirement
			 */
			//Get FOP from excel
			
			/*fop = FlightSearch.getTrimTdata(queryObjects.getTestData("FOP"));
			FlightSearch flop=new FlightSearch();		
			if(!fop.equals("")) {
				flop.singleFOP(driver, queryObjects,fop,"issueTicket");
			}
			else  {*/
			
			//Check this code - Jenny                                            
			String WaivePax= FlightSearch.getTrimTdata(queryObjects.getTestData("Quote_WaiverPassenger"));
			String WaiveFee = FlightSearch.getTrimTdata(queryObjects.getTestData("Quote_Waiver_Fee"));
			FlightSearch fs=new FlightSearch();
			if (!WaivePax.isEmpty()) {
				 fs.waivermethod(driver,queryObjects, WaivePax, WaiveFee,"");
				 fareAmount = driver.findElement(By.xpath("//div[@model='paymentCtrl.model.payment.totalSelected']")).getText().trim();
			 } else {
				//Click on check out button
				driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
				// Before payment amount checking....
				FlightSearch.loadhandling(driver);
			}
				double totalPaymentamt = Double.parseDouble(fareAmount);
				FlightSearch flop=new FlightSearch();
				FlightSearch.MultipleFOPType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPType_Quote"));
				FlightSearch.MultFOPsubType = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPSubType_Quote"));
				FlightSearch.fopCardNums = FlightSearch.getTrimTdata(queryObjects.getTestData("MultipleFOPCardNums_Quote"));
				String BankNames = queryObjects.getTestData("MultipleFOPBankName_Quote").trim();
				String InstallmentNums = queryObjects.getTestData("MultipleFOPInstallmentNum_Quote").trim();
				flop.MulFOP(driver,queryObjects,totalPaymentamt,FlightSearch.MultipleFOPType,FlightSearch.MultFOPsubType,FlightSearch.fopCardNums,BankNames,InstallmentNums,"issueTicket");
			//}
				
			String ServicefeeEditcheck=FlightSearch.getTrimTdata(queryObjects.getTestData("ServiceFree_Edit_checking"));
			if (ServicefeeEditcheck.equalsIgnoreCase("yes")) {
				try{
				List<WebElement> editIcon=driver.findElements(By.xpath("//div[contains(@class,'payment-status')]//i[contains(@class,'icon-edit')]"));
				int k=editIcon.size();
				if (editIcon.size()>0) 
					queryObjects.logStatus(driver, Status.FAIL, "Service Free Edit Icon checking", "EDit Icon should not show  this Currency "+Login.CURRENCY  , null);
				else
					queryObjects.logStatus(driver, Status.PASS, "Service Free Edit Icon checking", "EDit Icon is not showing  for this Currency "+Login.CURRENCY  , null);
				}catch (Exception e) {
					queryObjects.logStatus(driver, Status.PASS, "Service Free Edit Icon checking", "EDit Icon is not showing  for this Currency "+Login.CURRENCY  , null);	
				}
			}
			
			driver.findElement(By.xpath("//button[text()='Pay']")).click();
			Thread.sleep(2000);
			//Handling FOID details
			FlightSearch.enterFoiddetails(driver, queryObjects);
			//Handling Email popup
			Thread.sleep(2000);
			FlightSearch.emailhandling(driver,queryObjects);
			
			//Wait until payment successful message comes
			try {
				WebDriverWait wait = new WebDriverWait(driver, 120);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
				String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
				queryObjects.logStatus(driver, Status.PASS, "Search and Quote PNR", statusMessage, null);
			}
			catch(Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Search and Quote PNR", "Payment Unsuccessful: " + e.getMessage() , e);
			}
			//Click on Done button
			driver.findElement(By.xpath("//button[text()='Done']")).click();
			//wait till loading wrapper closed
			FlightSearch.loadhandling(driver);
			//Check for ticket status
			String status = driver.findElement(By.xpath("//div[@action='pnr']/div/div[1]/div[1]/div[2]")).getText().trim();
			if(status.equalsIgnoreCase("ticketed")) {
				queryObjects.logStatus(driver, Status.PASS, "Search and Quote PNR", "Succesffully Ticketed", null);
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "Search and Quote PNR", "Not Ticketed: status - " + status , null);
			}
			
			String Afterpaytotalamt=driver.findElement(By.xpath("//div[@model='currency.total']/div")).getText().trim();
			String AfterpayBalanceamt=driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText().trim();
			String Afterpaypadid=driver.findElement(By.xpath("//div[@model='currency.paid']/div")).getText().trim();
			
			if (Double.valueOf(AfterpayBalanceamt).intValue()==0) {
				queryObjects.logStatus(driver, Status.PASS, "After payment  Balance amount checking", "After payment  amount showing zero ", null);
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "After payment  Balance amount checking", "After payment  amount should show zero actual: "+AfterpayBalanceamt+" expected: 0", null);
			}
			if (Double.parseDouble(Afterpaytotalamt)==Double.parseDouble(fareAmount) ) {
				queryObjects.logStatus(driver, Status.PASS, "After payment  Total amount checking", "After payment Total amount showing  correctly total amt "+fareAmount, null);
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "After payment  Total amount checking", "After payment Totlal amount should show correctly actual: "+Afterpaytotalamt+" expected: "+ Double.parseDouble(fareAmount) , null);
			}
			if (Double.parseDouble(Afterpaypadid)==Double.parseDouble(Afterpaytotalamt)) {
				queryObjects.logStatus(driver, Status.PASS, "After payment  Paid amount checking", "After payment Paid amount showing  correctly paid amt : "+Afterpaypadid, null);
			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "After payment  Paid amount checking", "After payment Paid amount should show correctly actual: "+Afterpaypadid+" expected: "+Afterpaytotalamt, null);
			}
			
			/*driver.findElement(By.xpath("//div[div[text()='Tickets']]")).click();
			FlightSearch.loadhandling(driver);
			//get ticket numbers
			List<WebElement> getetkts=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]"));
			FlightSearch.gettecketno = new ArrayList<>();
			FlightSearch.tickttkype="E-Ticket";
			getetkts.forEach(a -> FlightSearch.gettecketno.add(a.getText().trim()));
			//get Ticket amount
			List<WebElement> getetktsamt=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]//preceding-sibling::div"));
			FlightSearch.gettecketamt = new ArrayList<>();
			getetktsamt.forEach(a -> FlightSearch.gettecketamt.add(a.getText().trim()));*/
		}
		
		
		//Krishna
		FlightSearch.TktStatus=new ArrayList<>();
		FlightSearch.tickttkype="E-Ticket";
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//div[div[text()='Order']]")).click();
		FlightSearch.loadhandling(driver);
		Unwholly.aTicket = null;  // this is for empty array
		driver.findElement(By.xpath("//div[text()='Tickets']")).click();
		FlightSearch.loadhandling(driver);
		
		List<WebElement> getetktsamt=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]//preceding-sibling::div"));
		FlightSearch.gettecketamt = new ArrayList<>();
		getetktsamt.forEach(a -> FlightSearch.gettecketamt.add(a.getText().trim()));
		
		List<WebElement> getetkt=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]"));
		FlightSearch.gettecketno = new ArrayList<>();
		getetkt.forEach(a -> FlightSearch.gettecketno.add(a.getText().trim()));
		
		if(FlightSearch.gettecketno.get(0).contains("-"))
			FlightSearch.conjunctiveTicketManualreissue=true;
		else
			FlightSearch.conjunctiveTicketManualreissue=false;
		
		for (WebElement Lab : getetkt) {
               String ticketnumber=Lab.getText().trim();
               ticketnumber.contains("-");
               ticketnumber=ticketnumber.split("-")[0].trim();
               List<WebElement> currentticketstate=driver.findElements(By.xpath("//span[text()='"+ticketnumber+"']/parent::div/parent::div//td[contains(@ng-if,'ticketStatusUpdate')]"));
               if(currentticketstate.get(0).getText().trim().equalsIgnoreCase("Open")) {
                    FlightSearch.TktStatus.add("Issue"); 
                 }      
        }
		
		FlightSearch.loadhandling(driver);
		//driver.findElement(By.xpath("//div[contains(text(),'EMD')]")).click();
		driver.findElement(By.xpath("//div[@translate='pssgui.emd']")).click();
		FlightSearch.loadhandling(driver);
		
		//Get EMD No
		List<WebElement> getemds=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']"));
		FlightSearch.getemdno = new ArrayList<>();
		getemds.forEach(a -> FlightSearch.getemdno.add(a.getText().trim()));
		
		//Get EMD Amount
		List<WebElement> getemdsamt=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']//preceding-sibling::div[1]"));
		FlightSearch.getemdamt = new ArrayList<>();
		getemdsamt.forEach(a -> FlightSearch.getemdamt.add(a.getText().trim()));
		Thread.sleep(2000);
		//22Mar - Navira
		String emdConfirm = FlightSearch.getTrimTdata(queryObjects.getTestData("emdConfirm"));//Column 3
		if(emdConfirm.equalsIgnoreCase("yes")) {
			FlightSearch.emdConfirmation(driver,queryObjects);

		}
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//div[div[text()='Order']]")).click();
		FlightSearch.loadhandling(driver);
	}
	
	public static void QuoteWithoutPayment(WebDriver driver, BFrameworkQueryObjects queryObjects) throws InterruptedException, IOException
	{
		
		checkBox = driver.findElement(By.xpath("//md-checkbox[contains(@class,'segment-checkbox')]"));
		if(checkBox.getAttribute("aria-checked").equalsIgnoreCase("false"))
			checkBox.click();

		//click on Actions list
		driver.findElement(By.xpath("//md-select[@aria-label='Actions']")).click();
		Thread.sleep(2000);
		//Selecting Quote Option
		driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false']//md-option[div[contains(text(),'Quote')]]")).click();

		//Selecting Coupon check box if it is not checked already
		checkBox = driver.findElement(By.xpath("//md-checkbox[@aria-label='coupon-check']"));
		if(checkBox.getAttribute("aria-checked").equalsIgnoreCase("false"))
			checkBox.click();
		
		if(pricebestbuy.equalsIgnoreCase("yes")) {
			driver.findElement(By.xpath(PriceOptionXpath)).click();
			Thread.sleep(500);
			driver.findElement(By.xpath(BestBuyXpath)).click();
			queryObjects.logStatus(driver, Status.PASS, "PricebestBuy", "Booking seat with best buy option", null);
		}
		
		

		//Click on Next
		driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Next')]")).click();
		FlightSearch.loadhandling(driver);
		
		//capture the service fee
		if(ServiceTax.equalsIgnoreCase("yes"))
		{
		 
			ServFee=driver.findElement(By.xpath("//div[contains(@ng-repeat,'fee in quoteInfo.Fees')]/div[contains(@ng-if,'fee.Type')]//div[4]//child::div[contains(@ng-class,'!amountCtrl')]")).getText().trim();
			String ServFee1[]=ServFee.split(" ");
			ServFee=ServFee1[0];
			//double ServFeedouble=Double.parseDouble(ServFee1[0]);
		}
		//Wait until Loading wrapper closed
		String fareDetails = driver.findElement(By.xpath(farepriceXpath)).getText().trim();
		String fareAmount = fareDetails.split("\\s+")[3];
		
		
		//Click on File Fare
		driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'File Fare')]")).click();
		//Wait until Loading wrapper closed
		FlightSearch.loadhandling(driver);
		
		String totalamt=(driver.findElement(By.xpath("//div[@model='currency.total']/div")).getText().trim());
		String Balanceamt=(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText().trim());
		
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
		
	}
	
	public static void ChangeSalesOffice(WebDriver driver, BFrameworkQueryObjects queryObjects,String SalesOff,String Cur) throws Exception   //22jan2018, IOException
	{
		
        	
        	driver.findElement(By.xpath("//div[@action='saleOfficeInfo']/div[@class='padding-top']/i")).click(); 
        	Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div/div[1]/md-input-container/md-select/md-select-value/span[2]")).click();
			Thread.sleep(3000);
			//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'loading-message')]")));
        	driver.findElement(By.xpath("//md-option[div[@class='md-text ng-binding' and contains(text(),'"+SalesOff+"')]]")).click(); 
       
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div/div[2]/md-input-container")).click();//Clicking on Currency drop down
        
        driver.findElement(By.xpath("//md-option[contains(@ng-value,'currency')][div[@class='md-text ng-binding' and contains(text(),'"+Cur+"')]] ")).click();
                                    
        driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div[2]/button[contains(text(),'OK')]")).click();  
       // wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'loading-message')]")));
       
        FlightSearch.loadhandling(driver);
        FlightSearch.loadhandling(driver);
        try {
        	
        		if (driver.findElement(By.xpath("//div[@class='inset padding-right-0 ng-binding'")).getText().equals(SalesOff)) {
        			queryObjects.logStatus(driver, Status.PASS, "Check given sales office details ", "Sales office is updated", null);
					
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Check given sales office details ", "Sales office is not updated", null);
				}
      
        	
        }
        	catch (Exception e) {
    			queryObjects.logStatus(driver, Status.INFO, "Sales offce", e.getLocalizedMessage(), e);

    		}
        
        Boolean CloseReport1=driver.findElements(By.xpath("//h2[contains(text(),'reminder')]")).size() >0;  
		Boolean CloseReport2=driver.findElements(By.xpath("//div[@action='saleOfficeInfo']/div[@class='padding-top']/i[contains(@class,'icon-warning pssgui')]")).size() >0;
		if(CloseReport1 ||CloseReport2)  
			Login.closePrevDayReports(driver, queryObjects);
	
		
		
		
	}
	public static boolean AddSegment(WebDriver driver,int viewnuberoflflightnoin,BFrameworkQueryObjects queryObjects,String tempXpath) throws Exception{
		int totalTravellers = Integer.parseInt(driver.findElement(By.xpath(PaxCountXpath)).getText().trim());
		String SameClass = FlightSearch.getTrimTdata(queryObjects.getTestData("SameClass"));
		String prevClass = driver.findElement(By.xpath(tempXpath + "//td[@class='flight-class']//span")).getText().trim();
		boolean classselectbon=false;
		if (viewnuberoflflightnoin>1 )  {
			for (int icontflights = 0; icontflights < viewnuberoflflightnoin; icontflights++) {
				int kk=icontflights+1;
				List<WebElement> lavbclass=driver.findElements(By.xpath("//div[div[div[div[div[span[span[i[@class='icon-arrow-up']]]]]]]]/div["+kk+"]//span[contains(@class,'active-state')]"));
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
						if (!prevClass.equalsIgnoreCase(String.valueOf(getavbclassnm.charAt(0)))) {
							int classnum=Integer.parseInt(FlightSearch.right(getavbclassnm,1));
							if (classnum>=totalTravellers) {
								lavbclass.get(iselectClass).click();
								classselectbon=true;
								break;
							}

						}
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
					if (!prevClass.equalsIgnoreCase(String.valueOf(getavbclassnm.charAt(0)))) {
						int classnum=Integer.parseInt(FlightSearch.right(getavbclassnm,1));
						if (classnum>=totalTravellers) {
							lavbclass.get(iselectClass).click();
							classselectbon=true;
							break;
						}

					}
				}
			}
		}
		return classselectbon;	
	}	
	public void NewBooking_NoReissue(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		String k="";
		String s=k.toUpperCase();
		
		//Get the previous date
		String tempXpath = "//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]][1]";
		String prevDate = driver.findElement(By.xpath(tempXpath + "//td[@class='date']//span")).getText().trim();
		
		String newBRDpoint = FlightSearch.getTrimTdata(queryObjects.getTestData("newBRDpoint")).toUpperCase();
		String newOFFpoint = FlightSearch.getTrimTdata(queryObjects.getTestData("newOFFpoint")).toUpperCase();
		
		String samerootdifffligh = FlightSearch.getTrimTdata(queryObjects.getTestData("sameroot_But_dif_fligh"));
		String getflghtnm=driver.findElement(By.xpath(tempXpath + "//td[@class='flight-name']/span")).getText().trim();
		String changeDate = FlightSearch.getTrimTdata(queryObjects.getTestData("changeDate"));
		
		List<String> flightinfo=new ArrayList<>();
		String RoundRetDate=null;
		String roundtrip=FlightSearch.getTrimTdata(queryObjects.getTestData("RoundTrip"));
		if(roundtrip.equalsIgnoreCase("Yes"))
			RoundRetDate=driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]/tbody[2]/tr/td[3]/div")).getText();
		
		flightinfo.add(driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]/tbody[1]/tr/td[2]/span")).getText());
		
		if(roundtrip.equalsIgnoreCase("Yes"))
			flightinfo.add(driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]/tbody[2]/tr/td[2]/span")).getText());
		
		//Unwholly.DeleteOldSegments(driver, queryObjects);
		//FlightSearch.loadhandling(driver);
		//Expand Availability Grid if it is not already opened
		List<WebElement> no=driver.findElements(By.xpath("//tbody[@ng-repeat='flight in flightResult.segments']"));
		int Segmt=no.size(); 
		WebDriverWait wait = new WebDriverWait(driver, 50);
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
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		//Add two days to the existing date
		SimpleDateFormat sdf = new  SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf2 = new  SimpleDateFormat("dd-MMM-yyyy");
		
		cal2.setTime(sdf2.parse(prevDate));
		cal1.setTime(sdf2.parse(prevDate));
		cal1.add(Calendar.DATE, 2);
		String newDate = sdf.format(cal1.getTime());
		if(changeDate.equalsIgnoreCase("yes")) {
			if (!sgetDate.isEmpty()) 
				cal2.add(Calendar.DATE, getDays+4);
			else
				cal2.add(Calendar.DATE, 4);
			
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
				driver.findElement(By.xpath(DestinationSecondXpath)).sendKeys(timeStamp1retDate);
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
			
		//Click on Search button
		driver.findElement(By.xpath("//button[@aria-label='Search']")).click();

		FlightSearch.loadhandling(driver);

		//Wait until Search results loaded
		WebDriverWait wait1 = new WebDriverWait(driver, 90);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(viewXpath)));
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
						
						classselectbon=AddSegment(driver,viewnuberoflflightnoin,queryObjects,tempXpath);
					}
						if (!flightno.equalsIgnoreCase(flightinfo.get(tripiter)) && sRoundTrip.equalsIgnoreCase("yes")) {
							classselectbon=AddSegment(driver,viewnuberoflflightnoin,queryObjects,tempXpath);
						} 
						

				}
				else
					classselectbon=AddSegment(driver,viewnuberoflflightnoin,queryObjects,tempXpath);

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
			List<WebElement> totSegments = driver.findElements(By.xpath("//div[@action='availability-flight-selected']//div[@ng-repeat='flight in segment.Legs']//input[@name='flight-order']"));
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
				
				List<WebElement> segno=new ArrayList<WebElement>();
				 segno=driver.findElements(By.xpath("//md-input-container[@class='order-no']/input"));
				 
				 for(int i=0;i<segno.size();i++)
						 {
					 segno.get(i).sendKeys(Integer.toString(++Segmt));
						 }
				//Selecting all segments check box
				driver.findElement(By.xpath("//md-checkbox[@aria-label='coupon-check']")).click();
				
				driver.findElement(By.xpath("//button[contains(text(),'Book')]")).click();
				FlightSearch.loadhandling(driver);
				/*for(int i=1;i<=totSegments.size();i++) {
					driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]]["+i+"]//td[@class='checkbox']//md-checkbox")).click();
					Thread.sleep(500);
				}*/
				driver.findElement(By.xpath("//md-checkbox[contains(@class,'segment-checkbox')]")).click();
				Unwholly.DeleteOldSegments(driver, queryObjects, DelSeg);
			}

	}
	
public static void Advance_Search(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		
		try {
			if (Login.Adsearch==1) {
				Login.Adsearch=2;
				String sFFPnumbers=FlightSearch.getTrimTdata(queryObjects.getTestData("FFPnumbers"));
				
				Adv_Search_call(driver,queryObjects);
				//Search with FFP Number
				driver.findElement(By.xpath("//div[@translate='pssgui.orders.and.tickets']")).click();
				Thread.sleep(1000);
			    driver.findElement(By.xpath("//input[@name='ffNumber']")).sendKeys(sFFPnumbers);
			    Thread.sleep(1000);
			    Thread.sleep(1000);
			    //Search button xpath....
				driver.findElement(By.xpath("//div[@ng-switch-when='pssgui.orders.and.tickets']//pssgui-fqtv[@action='edit']/following::div[1]//button")).click();
				FlightSearch.loadhandling(driver);
				//String PNRR=Login.envRead(Login.PNRNUM).trim();
				String PNRR=Login.PNRNUM.trim();
				if(driver.findElements(By.xpath("//tbody//tr//td[1][contains(text(),'"+ PNRR +"')]")).size()>0)  {	
					driver.findElement(By.xpath("//tbody//tr//td[1][contains(text(),'"+ PNRR +"')]")).click();
					FlightSearch.loadhandling(driver);
				}
				else
				{
					queryObjects.logStatus(driver, Status.FAIL, "Pnr Doesnt exist", "PNR", null);
				}
				
				List<WebElement>getdate=driver.findElements(By.xpath("//table[contains(@ng-table,'flightResult')][2]//td[@class='date']//span"));
				Adv_departure=getdate.get(0).getText();   //get departure date..
				
				List<WebElement>getflightnum=driver.findElements(By.xpath("//table[contains(@ng-table,'flightResult')][2]//td[@class='flight-name']//span "));//get departure flight number..
				Adv_FlightNo=getflightnum.get(0).getText();
				List<WebElement>getOrigin=driver.findElements(By.xpath("//div[@airport-code='originDestination.origin']"));//get departure origin..
				Adv_Origin=getOrigin.get(0).getText();
				driver.findElement(By.xpath("//div[@translate='pssgui.passengers']")).click();
				FlightSearch.loadhandling(driver);
		//		driver.findElement(By.xpath("//button[@translate='pssgui.edit']")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//div[div[text()='Personal Information']]")).click();
				FlightSearch.loadhandling(driver);
		
				Adv_lastname=driver.findElement(By.xpath("//input[@name='lastName']")).getAttribute("value");
				Adv_firstName=driver.findElement(By.xpath("//input[@name='firstName']")).getAttribute("value");
			    Adv_email=driver.findElement(By.xpath("//input[@name='Email Address']")).getAttribute("value");
			    Adv_countryCode=driver.findElement(By.xpath("//input[@name='countryCode']")).getAttribute("value");
				//Area_Code=Adv_countryCode.replaceAll("[^0-9]", "");
			    List<WebElement>getAreacode=driver.findElements(By.xpath("//input[@name='areacode']"));
			    Area_Code=getAreacode.get(0).getAttribute("value");
			    
				List<WebElement>getPhonenumber=driver.findElements(By.xpath("//input[@name='Phone Number']"));
				Adv_Phone_Number=getPhonenumber.get(0).getAttribute("value");
				
				//Adv_Phone_Number=driver.findElement(By.xpath("//input[@name='Phone Number']")).getAttribute("value");/// phone number
				
				driver.findElement(By.xpath("//div[@translate='pssgui.travel.document']")).click();
				driver.findElement(By.xpath("//button[@translate='pssgui.edit']")).click();
				Adv_DOB=driver.findElement(By.xpath("//form[@name='itineraryTraveler.form']//div[@class='block-bg-2 padding-top padding-left layout-row flex']//input[@ng-blur='ctrl.setFocused(false)']")).getAttribute("value");
				//to edit and save Form of Identification details
				driver.findElement(By.xpath("//md-input-container//md-select[@aria-label='Document Type']")).click();
				driver.findElement(By.xpath("//md-content//md-option[@value='PP']")).click();
				driver.findElement(By.xpath("//input[@name='stateCode']")).sendKeys("US");
				Adv_stateCode=driver.findElement(By.xpath("//input[@name='stateCode']")).getAttribute("value");
				driver.findElement(By.xpath("//input[@name='Number']")).sendKeys("Yass010");
				Thread.sleep(2000);
				Adv_Passport=driver.findElement(By.xpath("//input[@name='Number']")).getAttribute("value");
				driver.findElement(By.xpath("//button[@translate='pssgui.cancel']")).click();
			}
			String Search_By=FlightSearch.getTrimTdata(queryObjects.getTestData("Search_By"));
			//call advance search
			Adv_Search_call(driver,queryObjects);
			Thread.sleep(1000);
			if(Search_By.equalsIgnoreCase("OrderAndTickets")) 
				OrderAndTickets(driver,queryObjects);				//Call Order and tickets method
			else if (Search_By.equalsIgnoreCase("Adv_by_PassengerList")) 
				Adv_by_PassengerList(driver,queryObjects);
			else if(Search_By.equalsIgnoreCase("Search_EMD")) 
				Search_EMD(driver,queryObjects);	
			else if(Search_By.equalsIgnoreCase("Customer_Search")) 
				Customer_Search(driver,queryObjects);
			
			
		}catch(Exception e) {
			
			queryObjects.logStatus(driver, Status.FAIL, "Advance Search", "Advance search fail" , e);
		}
	}
	public static void OrderAndTickets(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		try {
			try {	
			queryObjects.logStatus(driver, Status.PASS, "OrderAndTickets checking", " Search Started" , null);	
			Thread.sleep(1000);	
			driver.findElement(By.xpath("//div[@translate='pssgui.orders.and.tickets']")).click();
			Thread.sleep(1000);
			//1--1  NOT-Working 
			driver.findElement(By.xpath("//input[@name='Area Code']")).sendKeys(Area_Code);
			driver.findElement(By.xpath("//input[@name='Phone Number']")).sendKeys(Adv_Phone_Number);
			driver.findElement(By.xpath("//form[@name='itinerary.orderstckSearchForm']//button")).click();
			FlightSearch.loadhandling(driver);
			// Search is not working ..... need to ADD validations...............
			String PNRR=Login.PNRNUM.trim();
			
			if(driver.findElements(By.xpath("//tbody//tr//td[1][contains(text(),'"+ PNRR +"')]")).size()>0) 
			{	
				driver.findElement(By.xpath("//tbody//tr//td[1][contains(text(),'"+ PNRR +"')]")).click();
				FlightSearch.loadhandling(driver);
				OrderAndTickets_verify_Areacode(driver,queryObjects);
			
			}
			else
			{
				OrderAndTickets_verify_Areacode(driver,queryObjects);
			}
			
			}
			catch(Exception e) {
						
				queryObjects.logStatus(driver, Status.FAIL, "OrderAndTickets Tab Area Cod", "OrderAndTickets Tab Area cod search  fail" , null);
			}
			//1--2
			try {
			driver.findElement(By.xpath("//input[@name='surname']")).sendKeys(Adv_lastname);
			driver.findElement(By.xpath("//input[@name='givenName']")).sendKeys(Adv_firstName);
			Thread.sleep(1000);
			driver.findElement(By.xpath("//input[@name='flight']")).sendKeys(Adv_FlightNo);
			driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).clear();
			driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(Adv_departure);
			Thread.sleep(1000);
			driver.findElement(By.xpath("//input[@name='airport']")).clear();
			driver.findElement(By.xpath("//input[@name='airport']")).sendKeys(Adv_Origin);
			FlightSearch.loadhandling(driver);
			//driver.findElement(By.xpath("//input[@name='airport']")).sendKeys(Keys.TAB);
			driver.findElement(By.xpath(clickUSpopuXpath)).click();
			driver.findElement(By.xpath("//div[@ng-switch-when='pssgui.orders.and.tickets']//form[@name='itinerary.passengerSearchForm']//button[@translate='pssgui.search']")).click();
			FlightSearch.loadhandling(driver);
			String PNRR=Login.PNRNUM.trim();
			if(driver.findElements(By.xpath("//div[contains(@class,'table-row')]/div[contains(text(),'"+PNRR+"')]")).size()>0) 
			{	
				driver.findElement(By.xpath("//div[contains(@class,'table-row')]/div[contains(text(),'"+PNRR+"')]")).click();
				FlightSearch.loadhandling(driver);
				OrderAndTickets_verify_Surname(driver,queryObjects);
			
			}
			else
				OrderAndTickets_verify_Surname(driver,queryObjects);
			}
			catch(Exception e) {
						
				queryObjects.logStatus(driver, Status.FAIL, "OrderAndTickets Tab Name Search", "OrderAndTickets Tab  Name search  fail" , e);
			}
			
			//FFp number search
			FFpnumersearch(driver, queryObjects, "pssgui.orders.and.tickets", "orders and tickets");
				
		}
		catch(Exception e) {
			
			queryObjects.logStatus(driver, Status.FAIL, "OrderAndTickets", "OrderAndTickets fail" , null);
		}
		
	}
	public static void OrderAndTickets_verify_Areacode(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		try {
			queryObjects.logStatus(driver, Status.PASS, "OrderAndTickets_verify_Areacode Search functionality checking", "Area coad Search Started" , null);
			
			driver.findElement(By.xpath("//div[@translate='pssgui.passengers']")).click();	
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//div[@translate='pssgui.personal.information']")).click();	
			Thread.sleep(1000);
			List<WebElement>Areacode=driver.findElements(By.xpath("//input[@name='areacode']"));
			String AreaCode=Areacode.get(0).getAttribute("value");
			
			Thread.sleep(1000);
			List<WebElement>Phonenum=driver.findElements(By.xpath("//input[@name='Phone Number']"));
			String PhoneNum=Phonenum.get(0).getAttribute("value");
			if(AreaCode.equalsIgnoreCase(Area_Code)) 
				queryObjects.logStatus(driver, Status.PASS, "OrderAndTickets_verify-->Area Code_phone number checking", "After search Area Code populated Correctly" , null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "OrderAndTickets_verify-->Area Code_phone number checking", "After search Area Code should populate correctly Actual "+AreaCode +" Expected "+Area_Code , null);
			
			if(PhoneNum.equalsIgnoreCase(Adv_Phone_Number)) 
				queryObjects.logStatus(driver, Status.PASS, "OrderAndTickets_verif-->Area Code_phone number checking", "After search Phone number populated Correctly" , null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "OrderAndTickets_verify-->Area Code_phone number checking", "After search Phone number should populate correctly Actual "+PhoneNum +" Expected "+Adv_Phone_Number , null);
			
			queryObjects.logStatus(driver, Status.PASS, "OrderAndTickets_verify-->Area Code_phone number functionality checking", "Area Code_phone number Search End" , null);
			Adv_Search_call(driver,queryObjects);
			
			
		}
		catch(Exception e) {
			
			queryObjects.logStatus(driver, Status.FAIL, "OrderAndTickets_verify_Areacode Search functionality checking", "ADvance Search-->Area Coad Seach Fail" , e);}
		}
	
	public static void OrderAndTickets_verify_Surname(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		try {
			queryObjects.logStatus(driver, Status.PASS, "OrderAndTickets_verify_Surname Search functionality checking", "Surname Search Search Started" , null);
			
			List<WebElement>profilee=driver.findElements(By.xpath("//div[contains(@class,'pssgui-design-sub-heading-6')]/span"));
			String profile=profilee.get(0).getText();
			String Fullname[] = profile.split(",");
			
			String Surname=Fullname[0];
			String Givenname=Fullname[1];
			Givenname=Givenname.trim();
			Adv_firstName=Adv_firstName.trim();
			List<WebElement>getdate=driver.findElements(By.xpath("//table[contains(@ng-table,'flightResult')][2]//td[@class='date']//span"));
			String departure=getdate.get(0).getText();   //get departure date..
			
			List<WebElement>getflightnum=driver.findElements(By.xpath("//table[contains(@ng-table,'flightResult')][2]//td[@class='flight-name']//span "));//get departure flight number..
			String FlightNo=getflightnum.get(0).getText();
			
			List<WebElement>getOrigin=driver.findElements(By.xpath("//div[@airport-code='originDestination.origin']"));//get departure origin..
			String Origin=getOrigin.get(0).getText();	
			
			if(Surname.equalsIgnoreCase(Adv_lastname)) 
				queryObjects.logStatus(driver, Status.PASS, "OrderAndTickets_verify_Surname Search SurName checking", "After search Last Name populated Correctly" , null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "OrderAndTickets_verify_Surname Search SurName checking", "After search Last Name should populate correctly Actual "+Surname +" Expected "+Adv_lastname , null);
			
			if(Givenname.equalsIgnoreCase(Adv_firstName)) 
				queryObjects.logStatus(driver, Status.PASS, "OrderAndTickets_verify_Surname Search firstName checking", "After search First Name populated Correctly" , null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "OrderAndTickets_verify_Surname Search firstName checking", "After search First Name should populate correctly Actual "+Givenname +" Expected "+Adv_firstName , null);
			
			if(departure.equalsIgnoreCase(Adv_departure)) 
				queryObjects.logStatus(driver, Status.PASS, "OrderAndTickets_verify_Surname Search departure checking", "After search Adv_departure Name populated Correctly" , null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "OrderAndTickets_verify_Surname Search departure checking", "After search Adv_departure Name should populate correctly Actual "+departure +" Expected "+Adv_departure , null);
			
			if(FlightNo.equalsIgnoreCase(Adv_FlightNo)) 
				queryObjects.logStatus(driver, Status.PASS, "OrderAndTickets_verify_Surname Search FlightNo checking", "After search Flight No populated Correctly" , null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "OrderAndTickets_verify_Surname Search FlightNo checking", "After search Flight No should populate correctly Actual "+FlightNo +" Expected "+Adv_FlightNo , null);
			
			if(Origin.equalsIgnoreCase(Adv_Origin)) 
				queryObjects.logStatus(driver, Status.PASS, "OrderAndTickets_verify_Surname Search Origin checking", "After search Origin populated Correctly" , null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "OrderAndTickets_verify_Surname Search Origin checking", "After search Origin should populate correctly Actual "+Origin +" Expected "+Adv_Origin , null);
			
		
			queryObjects.logStatus(driver, Status.PASS, "OrderAndTickets_verify_Surname Search functionality checking", "Surname Search Search End" , null);
			Adv_Search_call(driver,queryObjects);
			}
		catch(Exception e) {
			
			queryObjects.logStatus(driver, Status.FAIL, "OrderAndTickets_verify_Surname Search functionality checking", "ADvance Search--> Surname Search Fail" , e);}
		}
	
	
	public static void Adv_by_PassengerList(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		try {
			queryObjects.logStatus(driver, Status.PASS, "Passenger List checking", " Search Started" , null);
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@translate='pssgui.by.passenger.list']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@ng-switch-when='pssgui.by.passenger.list']//input[@name='flight']")).sendKeys(Adv_FlightNo);
			driver.findElement(By.xpath("//div[@ng-switch-when='pssgui.by.passenger.list']//input[@ng-focus='ctrl.setFocused(true)']")).clear();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@ng-switch-when='pssgui.by.passenger.list']//input[@ng-focus='ctrl.setFocused(true)']")).sendKeys(Adv_departure);
			
			driver.findElement(By.xpath("//div[@ng-switch-when='pssgui.by.passenger.list']//input[@name='airport']")).clear();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@ng-switch-when='pssgui.by.passenger.list']//input[@name='airport']")).sendKeys(Adv_Origin);
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//div[@ng-switch-when='pssgui.by.passenger.list']//input[@name='airport']")).sendKeys(Keys.TAB);
			driver.findElement(By.xpath("//div[@ng-switch-when='pssgui.by.passenger.list']//button[@translate='pssgui.search']")).click();
			FlightSearch.loadhandling(driver);
			String PNRR=Login.PNRNUM.trim();
			if(driver.findElements(By.xpath("//div[contains(@class,'pssgui-table')]/md-content/div/div[contains(text(),'"+PNRR+"')]")).size()>0) 
			{	
				driver.findElement(By.xpath("//div[contains(@class,'pssgui-table')]/md-content/div/div[contains(text(),'"+PNRR+"')]")).click();
				FlightSearch.loadhandling(driver);
				Adv_by_PassengerList_Verify(driver,queryObjects);
			
			}
			else
				Adv_by_PassengerList_Verify(driver,queryObjects);
			}
		catch(Exception e) {
			
			queryObjects.logStatus(driver, Status.FAIL, "Adv_by_PassengerList Failed", "ADvance Search Fail" , null);}
		}
	
	public static void Adv_by_PassengerList_Verify(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		try {
			queryObjects.logStatus(driver, Status.PASS, "Adv_by_PassengerList_Verify Search functionality checking", "Search Started" , null);
			

			List<WebElement>getdate=driver.findElements(By.xpath("//table[contains(@ng-table,'flightResult')][2]//td[@class='date']//span"));
			String departure=getdate.get(0).getText();  
			
			List<WebElement>getflightnum=driver.findElements(By.xpath("//table[contains(@ng-table,'flightResult')][2]//td[@class='flight-name']//span "));//get departure flight number..
			String FlightNo=getflightnum.get(0).getText();
			
			List<WebElement>getOrigin=driver.findElements(By.xpath("//div[@airport-code='originDestination.origin']"));
			String Origin=getOrigin.get(0).getText();	
			
			if(departure.equalsIgnoreCase(Adv_departure)) 
				queryObjects.logStatus(driver, Status.PASS, "Adv_by_PassengerList_Verify Search departure checking", "After search Departure populated Correctly" , null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Adv_by_PassengerList_Verify Search departure checking", "After search Departure should populate correctly Actual "+departure +" Expected "+Adv_departure , null);
			
			if(FlightNo.equalsIgnoreCase(Adv_FlightNo)) 
				queryObjects.logStatus(driver, Status.PASS, "Adv_by_PassengerList_Verify Search FlightNo checking", "After search FlightNo populated Correctly" , null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Adv_by_PassengerList_Verify Search FlightNo checking", "After search FlightNo should populate correctly Actual "+FlightNo +" Expected "+Adv_FlightNo , null);
			
			if(Origin.equalsIgnoreCase(Adv_Origin)) 
				queryObjects.logStatus(driver, Status.PASS, "Adv_by_PassengerList_Verify Search Origin checking", "After search Origin populated Correctly" , null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Adv_by_PassengerList_Verify Search Origin checking", "After search Origin should populate correctly Actual "+Origin +" Expected "+Adv_Origin , null);
			
			queryObjects.logStatus(driver, Status.PASS, "Adv_by_PassengerList_Verify Search functionality checking", "Search End" , null);
			Adv_Search_call(driver,queryObjects);
			
		
			}
		catch(Exception e) {
			
			queryObjects.logStatus(driver, Status.FAIL, "Adv_by_PassengerList_Verify Failed", "ADvance Search Fail" , null);}
		}
		
	public static void Customer_Search(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		try {
			queryObjects.logStatus(driver, Status.PASS, "Customer Search checking", " Search Started" , null);
			try {
				Thread.sleep(1000);
			queryObjects.logStatus(driver, Status.PASS, "Customer Search functionality checking", "Date of Birth and Name Search Started" , null);
			driver.findElement(By.xpath("//div[@translate='pssgui.customer.search']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//input[@name='surname']")).sendKeys(Adv_lastname);
			driver.findElement(By.xpath("//input[@name='givenName']")).sendKeys(Adv_firstName);
			Thread.sleep(1000);
			driver.findElement(By.xpath("//input[@ng-focus='ctrl.setFocused(true)']")).sendKeys(Adv_DOB);
			Thread.sleep(1000);
			driver.findElement(By.xpath("//form[@name='customerSearch.nameSearchForm']//button[@aria-label='Search']")).click();
			FlightSearch.loadhandling(driver);  // need add Verification
			queryObjects.logStatus(driver, Status.FAIL, "Customer_Search Tab Name Search Failed", "ADvance Search-->Customer Search Date of Birth and Name Search Fail" , null);
			
			}
			catch(Exception e) {
				
				queryObjects.logStatus(driver, Status.FAIL, "Customer_Search Tab name Search Failed", "ADvance Search-->Customer Search Date of Birth and name Search Fail" , e);}
			
			//Verification Require
			try
			{
			queryObjects.logStatus(driver, Status.PASS, "Customer Search functionality checking", "Email Search Started" , null);
		    driver.findElement(By.xpath("//div[@translate='pssgui.customer.search']")).click();
			driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Adv_email);
			driver.findElement(By.xpath("//form[@name='customerSearch.emailSearchForm']//button")).click();
			FlightSearch.loadhandling(driver);
			// need to add validation
			queryObjects.logStatus(driver, Status.FAIL, "Customer_Search Tab Email Search Failed", "ADvance Search-->Customer Search Email Search Fail" , null);
			
			}
			catch(Exception e) {
				
			queryObjects.logStatus(driver, Status.FAIL, "Customer_Search Tab Email Search Failed", "ADvance Search-->Customer Search Email Search Fail" , e);}
			
			//Verification Require
			try
			{
			queryObjects.logStatus(driver, Status.PASS, "Customer Search functionality checking", "Country Code  and phone number Search Started" , null);
		    driver.findElement(By.xpath("//div[@translate='pssgui.customer.search']")).click();
			driver.findElement(By.xpath("//input[@name='countryCode']")).sendKeys(Adv_countryCode);
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//input[@name='countryCode']")).sendKeys(Keys.TAB);
			driver.findElement(By.xpath("//input[@name='Area Code']")).sendKeys(Area_Code);	
			Thread.sleep(1000);
			driver.findElement(By.xpath("//input[@name='Phone Number']")).sendKeys(Adv_Phone_Number);
			driver.findElement(By.xpath("//form[@name='customerSearch.phoneSearchForm']//button")).click();
			FlightSearch.loadhandling(driver);
			queryObjects.logStatus(driver, Status.FAIL, "Customer_Search Tab CountryCode Search Failed", "ADvance Search-->Customer Search CountryCode  and phone number Search Fail" , null);
			}
			catch(Exception e) {
				
			queryObjects.logStatus(driver, Status.FAIL, "Customer_Search Tab CountryCode Search Failed", "ADvance Search-->Customer Search CountryCode and phone number Search Fail" , e);}
			
			//Verification Require
			try
			{
			queryObjects.logStatus(driver, Status.PASS, "Customer Search functionality checking", "Passport Search Started" , null);
		    driver.findElement(By.xpath("//div[@translate='pssgui.customer.search']")).click();		
			driver.findElement(By.xpath("//input[@name='passport']")).sendKeys(Adv_Passport);//get passport ID
			driver.findElement(By.xpath("//form[@name='customerSearch.passportSearchForm']//button")).click();
			FlightSearch.loadhandling(driver);
			queryObjects.logStatus(driver, Status.FAIL, "Customer_Search Tab PassportId  Search Failed", "ADvance Search-->Customer Search PassportId Search Fail" , null);
			
			}
			catch(Exception e) {
				
			queryObjects.logStatus(driver, Status.FAIL, "Customer_Search Tab PassportId  Search Failed", "ADvance Search-->Customer Search PassportId Search Fail" , e);}
			
			Adv_Search_call(driver,queryObjects);
			FFpnumersearch(driver, queryObjects, "pssgui.customer.search", "customer search");
			
			//Verification Require		
			}
		catch(Exception e) {
			
			queryObjects.logStatus(driver, Status.FAIL, "Customer_Search Failed", "ADvance Search Fail" , e);}
		}
	
	
	
	
	public static void Search_EMD(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		try {
			try {
				queryObjects.logStatus(driver, Status.PASS, "Search_EMD checking", " Search Started" , null);
				Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@translate='pssgui.search.emds']")).click();
		    //Adv_EMD_no=Login.envRead(Login.EMDNO);
			queryObjects.logStatus(driver, Status.PASS, "Search_EMD-->EMD checkin checking", " EMD Search Started" , null);
			Adv_EMD_no=FlightSearch.getemdno.get(0);
		    driver.findElement(By.xpath("//input[@name='emd number']")).sendKeys(Adv_EMD_no);
			Emd_order_ID=Login.PNRNUM;
			driver.findElement(By.xpath("//label[contains(text(),'Order Id')]/following-sibling::input")).sendKeys(Emd_order_ID);
			driver.findElement(By.xpath("//form[@name='itinerary.emdSearchForm']//button")).click();
			
			FlightSearch.loadhandling(driver);
			
			if(driver.findElements(By.xpath("//tbody//tr//td[1][contains(text(),'"+Adv_EMD_no+"')]")).size()>0) //write Xpath
			{	
				driver.findElement(By.xpath("//tbody//tr//td[1][contains(text(),'"+Adv_EMD_no+"')]")).click();
				FlightSearch.loadhandling(driver);
				Adv_by_Search_EMDNumber_Verify(driver,queryObjects);
			
			}
			else
			{
				Adv_by_Search_EMDNumber_Verify(driver,queryObjects);
			}

			}
			catch(Exception e) {
				
				queryObjects.logStatus(driver, Status.FAIL, "Search_EMD Tab EMd number Failed", "ADvance Search_EMD Tab EMd number  Fail" , e);}
			
			//Verification Require---NOT working
			try {
				queryObjects.logStatus(driver, Status.PASS, "Search_EMD-->FormOfIdentidication checking", " FormOfIdentidication Search Started" , null);
				driver.findElement(By.xpath("//div[@translate='pssgui.search.emds']")).click();	
				driver.findElement(By.xpath("//md-select//span[contains(text(),'Form of Identification')]")).click();
				//driver.findElement(By.xpath("//md-select[@ng-model='itinerary.model.identificationSearch.formOfIdentification']")).click();
				driver.findElement(By.xpath("//md-option//div[contains(text(),'Passport')]")).click();
				driver.findElement(By.xpath("//input[@name='country']")).sendKeys(Adv_stateCode);
				Thread.sleep(1000);
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//form[@name='itinerary.identificationCountySearchForm']//input[@name='country']")).sendKeys(Keys.TAB);
				driver.findElement(By.xpath("//input[@ng-model='itinerary.model.identificationSearch.issuingNumber']")).sendKeys(Adv_Passport);
				
				driver.findElement(By.xpath("//form[@name='itinerary.identificationCountySearchForm']//button")).click();
				FlightSearch.loadhandling(driver);
				String PNRRR=Login.PNRNUM.trim();
				if(driver.findElements(By.xpath("//div[contains(@class,'pssgui-table')]/md-content/div/div[contains(text(),'"+PNRRR+"')]")).size()>0) //write Xpath
				{	
					driver.findElement(By.xpath("//div[contains(@class,'pssgui-table')]/md-content/div/div[contains(text(),'"+PNRRR+"')]")).click();
					FlightSearch.loadhandling(driver);
				//	Adv_by_Search_EMD_FormOfIdentidication_Verify(driver,queryObjects);
				}
				
				
			}
			
			catch(Exception e) {
				
				queryObjects.logStatus(driver, Status.FAIL, "Search_EMD Tab Form of identification Failed", "ADvance Tab Form of identification Failed Fail" , e);}
			
			//verification required
			
			Adv_Search_call(driver,queryObjects);
			FFpnumersearch(driver, queryObjects, "pssgui.search.emds", "search emds");
		}
		catch(Exception e) {
			
			queryObjects.logStatus(driver, Status.FAIL, "Search_EMD Failed", "ADvance Search-->Search EMD Fail" , e);}
		
		}
	
	
	public static void Adv_by_Search_EMDNumber_Verify(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		try {
			queryObjects.logStatus(driver, Status.PASS, "Adv_by_Search_EMD Number_Verify Search functionality checking", "Search Started" , null);
			
			List<WebElement>getemd=driver.findElements(By.xpath("//div[contains(@class,'pssgui-link padding-left ng-binding')]"));
			String getEmdNo=getemd.get(0).getText();
			
			if(getEmdNo.equalsIgnoreCase(Adv_EMD_no)) 
				queryObjects.logStatus(driver, Status.PASS, "Adv_by_Search_EMDNumber_Verify Search departure checking", "After search EMDNO populated Correctly" , null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Adv_by_Search_EMDNumber_Verify Search departure checking", "After search EMDNO should populate correctly Actual "+getEmdNo +" Expected "+Adv_EMD_no , null);
			
			String getsearchid=driver.findElement(By.xpath("//div[contains(@class,'pssgui-design-sub-heading-2 ng-binding')]")).getText();
			
			if(getsearchid.equalsIgnoreCase(Emd_order_ID)) 
				queryObjects.logStatus(driver, Status.PASS, "Adv_by_Search_EMDNumber_Verify Search departure checking", "After search SearchID populated Correctly" , null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Adv_by_Search_EMDNumber_Verify Search departure checking", "After search SearchID should populate correctly Actual "+getsearchid +" Expected "+Emd_order_ID , null);
			 
			
			queryObjects.logStatus(driver, Status.PASS, "Adv_by_Search_EMD Number_Verify Search functionality checking", "Search End" , null);
			Adv_Search_call(driver,queryObjects);
			
		
			}
		catch(Exception e) {
			
			queryObjects.logStatus(driver, Status.FAIL, "Adv_by_PassengerList_Verify Failed", "ADvance Search Fail" , null);}
		}
	
	
	
	public static void Adv_Search_call(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		try {
			driver.findElement(By.xpath("//span[@translate='pssgui.home']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@role='button']//div[@translate='pssgui.search']")).click();//load handling
			Thread.sleep(1000);
			if (driver.findElements(By.xpath("//pssgui-itinerary-search[@action='advanced']//i[@role='button' and contains(@class,'icon-forward')]")).size()>0)
				driver.findElement(By.xpath("//pssgui-itinerary-search[@action='advanced']//i[@role='button' and contains(@class,'icon-forward')]")).click();
			Thread.sleep(1000);
			}catch(Exception e) {
				
				queryObjects.logStatus(driver, Status.FAIL, "ADvance Search Fail", "ADvance Search Fail" , e);
			}
		}
	public static void FFpnumersearch(WebDriver driver, BFrameworkQueryObjects queryObjects,String Xpath,String Tab_NM) throws Exception{
		try {
			queryObjects.logStatus(driver, Status.PASS, "Advacen search-->"+Tab_NM+"  FFP Number checking", " Search Started" , null);
			String sFFPnumbers=FlightSearch.getTrimTdata(queryObjects.getTestData("FFPnumbers"));
			Thread.sleep(1000);
		    driver.findElement(By.xpath("//input[@name='ffNumber']")).sendKeys(sFFPnumbers);
		    Thread.sleep(1000);
		    Thread.sleep(1000);
		    //Search button xpath....
			driver.findElement(By.xpath("//div[@ng-switch-when='"+Xpath+"']//pssgui-fqtv[@action='edit']/following::div[1]//button")).click();
			FlightSearch.loadhandling(driver);
			if (Tab_NM.equalsIgnoreCase("customer search")) {	
				String getFFPno=driver.findElement(By.xpath("//td[contains(@class,'frequent-flyer')]")).getText();
				String GetFFPnoAftersplit=getFFPno.split(" ")[1];
				if(GetFFPnoAftersplit.equalsIgnoreCase(sFFPnumbers))
					queryObjects.logStatus(driver, Status.PASS, "Adv_by_Search-->"+Tab_NM+" tab-->FFP number Search checking", "After search FFPnumber populated Correctly" , null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Adv_by_Search-->"+Tab_NM+" tab-->FFP number Search checking", "After search FFPnumber populated should Correctly Actual val "+GetFFPnoAftersplit +" Expected val "+sFFPnumbers , null);
				driver.findElement(By.xpath("//span[contains(text(),'Search Order')]")).click();
				FlightSearch.loadhandling(driver);
			}
			
			String PNRR=Login.PNRNUM.trim();
			if(driver.findElements(By.xpath("//tbody//tr//td[1][contains(text(),'"+ PNRR +"')]")).size()>0)  {	
				driver.findElement(By.xpath("//tbody//tr//td[1][contains(text(),'"+ PNRR +"')]")).click();
				FlightSearch.loadhandling(driver);
				String getpnr=driver.findElement(By.xpath("//div[@action='pnr']/div/div/div[1]/div[1]")).getText().trim();
				if (getpnr.equalsIgnoreCase(PNRR)) 
					queryObjects.logStatus(driver, Status.PASS, "Adv_by_Search-->"+Tab_NM+" tab-->FFP number Search checking", "After search FFPnumber related PNR  populated Correctly" , null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Adv_by_Search-->"+Tab_NM+" tab-->FFP number Search checking", "After search FFPnumber related PNR  populated should Correctly Actual val "+getpnr +" Expected val "+PNRR , null);
				queryObjects.logStatus(driver, Status.PASS, "Advacen search-->"+Tab_NM+" FFP Numbe checking", " Search END" , null);
			}
			else
				queryObjects.logStatus(driver, Status.FAIL, Tab_NM+" FFPNumber Search-->Pnr Doesnt exist", "PNR", null);
			
			
		}catch(Exception e) {
			
			queryObjects.logStatus(driver, Status.FAIL, Tab_NM+"-->FFP Number search not working", "FFPFail" , e);
		}
	}

	
	public void changePOS(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		String POSDet="";
		String Cur="";
		driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
		 Thread.sleep(2000);
		 driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Reservations')]")).click();
		 FlightSearch.loadhandling(driver);
		 Login.Call_Center_Login=queryObjects.getTestData("Call_Center_Login");
		if(Login.Env.equalsIgnoreCase("SIt")){
			POSDet=queryObjects.getTestData("SIT_Change_POS").trim();
			Cur=queryObjects.getTestData("SIT_Change_CUR").trim();
		}
		else if (Login.Env.equalsIgnoreCase("UAT")) {
			POSDet=queryObjects.getTestData("UAT_Change_POS").trim();
			Cur=queryObjects.getTestData("UAT_Change_CUR").trim();
		}
		
		PNRsearch.ChangeSalesOffice(driver,queryObjects,POSDet,Cur);
		FlightSearch.currencyType=driver.findElement(By.xpath("//div[@action='saleOfficeInfo']/div[3]")).getText();
		Login.Cur = FlightSearch.currencyType;
	}
	
	
	public boolean Search_PNR(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		Thread.sleep(2000);
		driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
		 Thread.sleep(2000);
		 driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Reservations')]")).click();
		 FlightSearch.loadhandling(driver);
		//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);//30
		driver.findElement(By.xpath("//div[text()='New Order']")).click();
		
		//Get PNR number from either test data sheet or stored PNR from environment variables
		if(FlightSearch.getTrimTdata(queryObjects.getTestData("Share_PNR")).equalsIgnoreCase("YES")) 
			//	Login.PNRNUM=SharesPNR;
				pnrNum = ISharesflow.SharesPNR;
		else
				pnrNum =Login.PNRNUM.trim();
		String etkt = FlightSearch.getTrimTdata(queryObjects.getTestData("E-ticketNum"));
		String etktNum = new String();
		boolean contflag=false;
		if(pnrNum.equals("")) 
			queryObjects.logStatus(driver, Status.FAIL, "PNR was not generated", "PNR was not generated" , null);
		else{
			if(etkt.equalsIgnoreCase("yes")) {
				queryObjects.logStatus(driver, Status.INFO, "PNR was not generated", "PNR was not generated" , null);	
				etktNum = FlightSearch.gettecketno.get(0);
			}
			contflag=true;	
		}
		if(contflag) 
		{
			try {
				
				driver.findElement(By.xpath("//span[text()='Home']")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[div[text()='Search'] and @role='button']")).click();
				Thread.sleep(2000);
				
				driver.findElement(By.xpath("//input[@aria-label='Search']")).click();
				Thread.sleep(2000);
				//Start Navira
				if(!etktNum.equals("")) {//Navira
					//Entering PNR number in search input
					driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(etktNum);	
				}
				//End
				else {
					//Entering PNR number in search input
					driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(pnrNum);
				}
				FlightSearch.loadhandling(driver);
				//Clicking on Search button
				driver.findElement(By.xpath("//div[contains(@class,'itinerary-search')]//button[contains(text(),'Search')]")).click();
				//String Pnris=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@action='pnr']/div/div/div[1]/div[1]")).getText());
				//Wait until loading wrapper closed
				FlightSearch.loadhandling(driver);
				if(!etktNum.equals("")) {
					driver.findElement(By.xpath("//div[text()='Order']")).click();
					FlightSearch.loadhandling(driver);
				}
				//queryObjects.logStatus(driver, Status.PASS, "Search PNR", "Search PNR successful" , null);
				queryObjects.logStatus(driver, Status.PASS, "Search PNR/E-Ticket", "Search PNR/E-Ticket successful" , null);//Navira
			}
			catch(Exception e) {
				contflag = false;
				//queryObjects.logStatus(driver, Status.FAIL, "Search PNR", "Search PNR failed: " + e.getLocalizedMessage() , e);
				queryObjects.logStatus(driver, Status.FAIL, "Search PNR/E-Ticket", "Search PNR/E-Ticket failed: " + e.getLocalizedMessage() , e);//Navira
			}
		}
		return contflag;
	}
	
	
	
	public static void AddInfantSpecificSegment(WebDriver driver,BFrameworkQueryObjects queryObjects,String PAXName) throws Exception{
		String AddInfantSpecificSegment = FlightSearch.getTrimTdata(queryObjects.getTestData("AddInfantSpecificSegment"));
		String Segment[] = AddInfantSpecificSegment.split(";");
		PAXName = PAXName.toUpperCase();
		driver.findElement(By.xpath("//div[text()='Tickets']/parent::div")).click();
		FlightSearch.loadhandling(driver);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[text()='Quotes']")).click();
		FlightSearch.loadhandling(driver);
		Thread.sleep(2000);
		
		//Get all the Segment Details from infant
		
		//All Segment
		try {
		
		List<String>AllOrigin = new ArrayList<>();
		AllOrigin.clear();
		
		List<String>AllDestination = new ArrayList<>();
		AllDestination.clear();
			
		List<String>CNumber = new ArrayList<>();
		CNumber.clear();
		
		List<String>SegmentNo = new ArrayList<>();
		SegmentNo.clear();
		
		List<WebElement> AllSegment= driver.findElements(By.xpath("//div[div[div[div[toggle-title[div[div[text()='"+PAXName+"']]]]]]]//md-content/div[1]//table/tbody/tr"));
		
		List<WebElement> AllOriginList = driver.findElements(By.xpath("//div[div[div[div[toggle-title[div[div[text()='"+PAXName+"']]]]]]]//div[@class='pssgui-bold origin-destin ng-binding ng-isolate-scope']"));
		AllOriginList.forEach(a -> AllOrigin.add(a.getText().trim()));
		
		List<WebElement> AllDestinationList= driver.findElements(By.xpath("//div[div[div[div[toggle-title[div[div[text()='"+PAXName+"']]]]]]]//div[@class='pssgui-bold ng-binding ng-isolate-scope']"));
		AllDestinationList.forEach(a -> AllDestination.add(a.getText().trim()));
		
		List<WebElement> CNumberList = driver.findElements(By.xpath("//div[div[div[div[toggle-title[div[div[text()='"+PAXName+"']]]]]]]//md-content/div[1]//table/tbody/tr/td[1]"));
		CNumberList.forEach(a -> CNumber.add(a.getText().trim()));
		
		//Click on Modify Quotes
		driver.findElement(By.xpath("//div[div[div[div[toggle-title[div[div[text()='"+PAXName+"']]]]]]]//button[text()='Modify Quote']")).click();
		
		for (int k=0;k<Segment.length;k++) {
			String Origin = Segment[k].split("-")[0].trim();
			String Destination = Segment[k].split("-")[1].trim();
			int Start = 0;
			int End = 0;
			for (int i=0;i<AllSegment.size();i++) {
				String O = AllOrigin.get(i).trim();
				if(Origin.equalsIgnoreCase(O)){
					Start = i;
					for(int j=i;j<AllSegment.size();j++) {
						String D = AllDestination.get(j).trim();
						if(Destination.equalsIgnoreCase(D))
							End = j;
					}
				}
			}
			
			for(int x=Start;x<=End;x++) {
				CNumber.remove(x);	
			}
		}	
		//End of For Loop
		
		//Delete the Unwanted Segment
		
		for(int s=0;s<CNumber.size();s++) {
			Thread.sleep(2000);
			List<WebElement> SegmentDeleteIcon= driver.findElements(By.xpath("//div[div[div[div[toggle-title[div[div[text()='"+PAXName+"']]]]]]]//div[@ng-repeat='flightSegment in quoteForm.manualQuoteData.QuotedSegments']//div[i[@class='icon-removed']]"));
			
			List<WebElement> SegmentNoList = driver.findElements(By.xpath("//div[div[div[div[toggle-title[div[div[text()='"+PAXName+"']]]]]]]//div[@ng-repeat='flightSegment in quoteForm.manualQuoteData.QuotedSegments']//input[@aria-label='Seq']"));
			SegmentNoList.forEach(a -> SegmentNo.add(a.getAttribute("value").trim()));
			String CouponNumber = CNumber.get(s).substring(1);
			
			for(int y = 0;y<CNumber.size();y++) {
				if(CouponNumber.equals(SegmentNo.get(y).trim())){
					SegmentDeleteIcon.get(SegmentNo.indexOf(CouponNumber)).click();
					Thread.sleep(2000);
				}
			}	
		}
		
		}catch(Exception e){
			
		}
		
		//update the Mandatory Column 
		FlightSearch.loadhandling(driver);
		List<WebElement> nvadate=driver.findElements(By.xpath("//div[div[label[span[@translate='NVA']]]]/md-datepicker/div/input"));
		Calendar calcnva = Calendar.getInstance();
		calcnva.add(Calendar.DATE, +5);
		String timeStampnva = new SimpleDateFormat("MM/dd/yyyy").format(calcnva.getTime());
		for (int nva = 0; nva < nvadate.size(); nva++) {
			nvadate.get(nva).sendKeys(timeStampnva);
			Thread.sleep(1000);
		}

		//Click on the Submit Button
		FlightSearch.loadhandling(driver);
		WebElement Submit = driver.findElement(By.xpath("//button[text()='Submit']"));
		if(Submit.isEnabled()) {
			Submit.click();
			queryObjects.logStatus(driver, Status.PASS, "Adding Infant specific segment Valiation", "specific Segment added success fully", null);
		}
		
		FlightSearch.loadhandling(driver);
			
	}	
	//atul
	//Atul - 27Jan - Method to Manual Quote for Add Infant
	
	public static double Manual_Quote_AddINFcase(WebDriver driver,BFrameworkQueryObjects queryObjects,String INFPax_nm) throws InterruptedException, IOException{
		queryObjects.logStatus(driver, Status.PASS, "Manual quote for INF PAX process started", "Manual quote for INF PAX process started", null);
		// Manual quote process started
		driver.findElement(By.xpath(TicketXpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[text()='Quotes']/parent::div")).click();
		Thread.sleep(2000);
		WebElement Manual_Quote;
		double fareAmountManualQuote=0 ;
		INFPax_nm=INFPax_nm.toUpperCase();
			Manual_Quote=driver.findElement(By.xpath("//div[div[contains(text(),'"+INFPax_nm+"')]]/following-sibling::button"));
			//driver.findElement(By.xpath("//button[@aria-label='Add Manual Quote']")).click();
			Manual_Quote.click();
			List<WebElement> farebasis=driver.findElements(By.xpath("//input[@ng-model='flightSearch.model.FareBasis']"));

			List<WebElement> classcode=driver.findElements(By.xpath("//input[@ng-model='flightSearch.model.RBDClassCode']"));
			for (int ifar = 0; ifar < farebasis.size(); ifar++) {
				farebasis.get(ifar).click();
				String mclas=classcode.get(ifar).getAttribute("value");
				farebasis.get(ifar).sendKeys(mclas);
				Thread.sleep(1000);
			}
			List<WebElement> nvadate=driver.findElements(By.xpath("//div[div[label[span[@translate='NVA']]]]/md-datepicker/div/input"));
			Calendar calcnva = Calendar.getInstance();
			calcnva.add(Calendar.DATE, +5);
			String timeStampnva = new SimpleDateFormat("MM/dd/yyyy").format(calcnva.getTime());
			for (int nva = 0; nva < nvadate.size(); nva++) {
				nvadate.get(nva).sendKeys(timeStampnva);
				Thread.sleep(1000);
			}
			
				String amt="2000";
				/*driver.findElement(By.xpath("//input[@aria-label='Fare Amount']")).sendKeys(amt);  //1000
				Thread.sleep(1000);
				driver.findElement(By.xpath("//md-input-container[input[@aria-label='Fare Amount']]/following::md-input-container[1]/input")).sendKeys(currencyType);
				fareAmountManualQuote=fareAmountManualQuote+Double.parseDouble(amt);*/
				if(Login.Cur.trim().equalsIgnoreCase("ARS")|| Login.Cur.trim().equalsIgnoreCase("COP") ){
					driver.findElement(By.xpath("//input[@aria-label='Fare Amount']")).sendKeys("100");
					driver.findElement(By.xpath("//md-input-container[input[@aria-label='Fare Amount']]/following::md-input-container[1]/input")).clear();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//md-input-container[input[@aria-label='Fare Amount']]/following::md-input-container[1]/input")).sendKeys("USD");
					Thread.sleep(1000);
					driver.findElement(By.xpath("//input[@aria-label='Equiv.Fare']")).sendKeys(amt);
					Thread.sleep(1000);
					driver.findElement(By.xpath("//md-input-container[input[@aria-label='Equiv.Fare']]/following::md-input-container[1]/input")).sendKeys(Login.Cur);
					fareAmountManualQuote=fareAmountManualQuote+Double.parseDouble(amt);
				}
				else{
					driver.findElement(By.xpath("//input[@aria-label='Fare Amount']")).sendKeys(amt);  //1000
					Thread.sleep(1000);
					driver.findElement(By.xpath("//md-input-container[input[@aria-label='Fare Amount']]/following::md-input-container[1]/input")).sendKeys(Login.Cur);
					fareAmountManualQuote=fareAmountManualQuote+Double.parseDouble(amt);
				}
			Thread.sleep(1000);
			driver.findElement(By.xpath("//button[text()='Submit']")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//md-select[@ng-model='WaiverReasonInfo.process']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//md-option[@ng-value='ProcessList'][1]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//md-select[@ng-model='WaiverReasonInfo.Reason']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//md-option[@ng-value='Reason'][1]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//button[@aria-label='ok']")).click();
			FlightSearch.loadhandling(driver);
		
		fareAmountManualQuote=fareAmountManualQuote;  // Adding total service fee amount ....
		return fareAmountManualQuote;
	}
	
	public static void SyncTicket(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		try{
		// change class ...
		driver.findElement(By.xpath("//div[text()='Order']")).click();
		FlightSearch.loadhandling(driver);
		String tempXpath = "//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]][1]";
		WebElement segChkBox = driver.findElement(By.xpath(tempXpath + "//md-checkbox"));
		if(segChkBox.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))
			segChkBox.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(tempXpath + "/tr/td[@class='flight-class']//span")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath( "//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]][1]/tr/td[@class='flight-class']//span//div")).click();
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//div[@aria-hidden='false']//md-option[@value='C']")).click();
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath(tempXpath + "//span[@class='save']")).click();
		FlightSearch.loadhandling(driver);
		queryObjects.logStatus(driver, Status.PASS,"Class updated y to C","successfully updated",null);
		
        // doing sync ticket process
        driver.findElement(By.xpath("//div[@translate='pssgui.tickets']")).click();
        FlightSearch.loadhandling(driver);
        driver.findElement(By.xpath("//div[@translate='pssgui.ticket']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//md-select[contains(@aria-label,'Select Action')]")).click();
        Thread.sleep(100);
        driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and contains(@aria-hidden,'false')]//div[contains(text(),'Sync Ticket')]/parent::md-option")).click();
        Thread.sleep(100);
        driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'isAllChecked')]")).click();
        
        driver.findElement(By.xpath("//md-radio-button[@value='CLASS']/child::div[1]")).click();
        Thread.sleep(1000);
       
        driver.findElement(By.xpath("//md-radio-button[@value='false']/child::div[1]")).click();
        
        driver.findElement(By.xpath("//button[@translate='pssgui.sync']")).click();
        FlightSearch.loadhandling(driver);
        
        String coupancntrl="";
        if(driver.findElements(By.xpath("//span[contains(text(),'ADJUSTED')]")).size()>0)
        	coupancntrl=driver.findElement(By.xpath("//span[contains(text(),'ADJUSTED')]")).getText();
       
       if(coupancntrl.contains("ADJUSTED"))
             queryObjects.logStatus(driver, Status.PASS, "After sync checking ticket status", "Ticket status updated successfully" , null);
       else
             queryObjects.logStatus(driver, Status.FAIL, "After sync checking ticket status", "Not updated status Expected :ADJUSTED but actual is: "+coupancntrl , null);     
		}
		catch(Exception e){
			 queryObjects.logStatus(driver, Status.FAIL,"Sync Method fail","Sync method fail",e);
		}
		
		
		
 }

	public void checkinvalidation(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException, InterruptedException{
		String pnris=Login.PNRNUM.trim();
		IshareLogin(driver, queryObjects, "SharesB");
		String entry1="*"+pnris;
		driver.findElement(By.name("q")).sendKeys(entry1);
		driver.findElement(By.xpath("//input[@value='Send']")).click();
		Thread.sleep(2000);
		String entry2="*ET";
		 driver.findElement(By.name("q")).sendKeys(entry2);
		driver.findElement(By.xpath("//input[@value='Send']")).click();
		Thread.sleep(2000);
		String ssroutput=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
		if(ssroutput.contains("CHECKED-IN"))
			queryObjects.logStatus(driver, Status.PASS, "After check in status validating in Shares", "it is displaying correclty" , null);
		else
			queryObjects.logStatus(driver, Status.FAIL, "After check in status validating in Shares", "it should displaying correclty Expected: CHECKED-IN  but Actual :"+ssroutput , null);
		
	}
	
	public static void IshareLogin(WebDriver driver, BFrameworkQueryObjects queryObjects, String Shares) throws IOException {
		try {
			String pResField = "//pre[@id='content-wrap']";
			String pSendBtn = "//input[@value='Send']";
			String SharesA_B="";
			try {
				SharesA_B = Shares;
			} catch (Exception e) {
				SharesA_B = "";
			}			
			WebDriverWait wait = new WebDriverWait(driver, 100);
			if (SharesA_B.contains("SharesA")) {
				driver.navigate().to("https://tpfsa.svcs.entsvcs.net");
			} else {
				driver.navigate().to("https://tpfsb.svcs.entsvcs.net");
				SharesA_B = "SharesB";
			}			
			driver.findElement(By.xpath("//input[@name='ID']")).sendKeys("wzqltv");
			driver.findElement(By.name("Password")).sendKeys("prayer08");
			driver.findElement(By.xpath("//input[@value='Login']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Terminal Emulation')]")));
			driver.findElement(By.xpath("//a[contains(text(),'Terminal Emulation')]")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			if (SharesA_B.contains("SharesA")) {
				driver.findElement(By.name("q")).sendKeys("logc uare");
				driver.findElement(By.xpath(pSendBtn)).click();
				driver.findElement(By.name("q")).sendKeys("bsia");
				driver.findElement(By.xpath(pSendBtn)).click();
				if( driver.findElement(By.xpath(pResField)).getText().contains("A-SINE COMPLETE")||driver.findElement(By.xpath(pResField)).getText().contains("A-IN USE")){	//indrajit
					queryObjects.logStatus(driver, Status.PASS, "Login to Ishares A application", "Login is successful", null);
				}else{
					queryObjects.logStatus(driver, Status.FAIL, "Login to Ishares A application", "Getting an error while login to ISHARES A applicatio", null);
				}
			} else {
				driver.findElement(By.name("q")).sendKeys("logc cmre");
				driver.findElement(By.xpath(pSendBtn)).click();
				driver.findElement(By.name("q")).sendKeys("bsib");
				driver.findElement(By.xpath(pSendBtn)).click();
				if( driver.findElement(By.xpath(pResField)).getText().contains("B-SINE COMPLETE")||driver.findElement(By.xpath(pResField)).getText().contains("B-IN USE")){	//indrajit
					queryObjects.logStatus(driver, Status.PASS, "Login to Ishares B application", "Login is successful", null);
				}else{
					queryObjects.logStatus(driver, Status.FAIL, "Login to Ishares B application", "Getting an error while login to ISHARES B applicatio", null);
				}
			}		
			
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Login to IShares", "Login Failed", null);
		}
		
	}
	
	
	public void open_EMD(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException, InterruptedException{
		try{
			String Excel_EMDno="";
			if(Login.Env.equalsIgnoreCase("SIt")){
				Excel_EMDno=queryObjects.getTestData("SIT_EMDNumber").trim();
			}
			else if (Login.Env.equalsIgnoreCase("UAT")) {
				Excel_EMDno=queryObjects.getTestData("UAT_EMDNumber").trim();
			}
			String semdnoTemp = Login.EMDNO;
			semdnoTemp=semdnoTemp.trim();
			if(!semdnoTemp.isEmpty())  // storing for multiple EMD number in ENv sheet 
				Login.EMDNO= semdnoTemp+";"+Excel_EMDno;
			else
				Login.EMDNO= Excel_EMDno;
			
			driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Reservations')]")).click();
			FlightSearch.loadhandling(driver);
			if(Excel_EMDno.contains(";")){
				String[] split_Excel_EMDno=Excel_EMDno.split(";");
				for (int iemdex = 0; iemdex < split_Excel_EMDno.length; iemdex++) {
					
					driver.findElement(By.xpath("//span[@translate='pssgui.home']")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//div[div[text()='Search'] and @role='button']")).click();
					Thread.sleep(2000);
					//Entering PNR number in search input
					driver.findElement(By.xpath("//input[@aria-label='Search']")).click();
					driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(split_Excel_EMDno[iemdex]);
					driver.findElement(By.xpath("//div[contains(@class,'itinerary-search')]//button[contains(text(),'Search')]")).click();
					FlightSearch.loadhandling(driver);
					
					driver.findElement(By.xpath("//div[div[text()='Tickets']]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//div[contains(text(),'EMD')]")).click();
					FlightSearch.loadhandling(driver);
					if(driver.findElements(By.xpath("//div[contains(text(),'"+split_Excel_EMDno[iemdex]+"')and(@role='button')]")).size()>0)
						driver.findElement(By.xpath("//div[contains(text(),'"+split_Excel_EMDno[iemdex]+"')and(@role='button')]")).click();
					else{
						queryObjects.logStatus(driver, Status.FAIL, "Search EMD", "Search Given EMD number not working" , null);
						return;
					}
					driver.findElement(By.xpath("//div[@translate='pssgui.details']")).click();
					FlightSearch.loadhandling(driver);
					String Emd_Statu=driver.findElement(By.xpath("//table/tbody//td/div[@role='button']")).getText().trim();
					if(!Emd_Statu.equalsIgnoreCase("OPEN")) {
		    			driver.findElement(By.xpath("//table/tbody//td/div[@role='button']")).click();
		    			driver.findElement(By.xpath("//label[text()='Status']/following-sibling::md-select")).click();
		    			driver.findElement(By.xpath("//md-option[contains(@ng-value,'emdstatusType')]/div[contains(text(),'OPEN')]")).click();
		    			driver.findElement(By.xpath("//button[contains(@type,'submit')]")).click();
		    			FlightSearch.loadhandling(driver);
		    			queryObjects.logStatus(driver, Status.PASS, "Emd Statu", "EMD status updated:", null);
		    		}
					else
						queryObjects.logStatus(driver, Status.INFO, "Emd Statu", "EMD status is:"+Emd_Statu, null);
					
				}
				
			}
			else{
				
				driver.findElement(By.xpath("//span[@translate='pssgui.home']")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[div[text()='Search'] and @role='button']")).click();
				Thread.sleep(2000);
				//Entering PNR number in search input
				driver.findElement(By.xpath("//input[@aria-label='Search']")).click();
				driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(Excel_EMDno);
				driver.findElement(By.xpath("//div[contains(@class,'itinerary-search')]//button[contains(text(),'Search')]")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[div[text()='Tickets']]")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[contains(text(),'EMD')]")).click();
				FlightSearch.loadhandling(driver);
				
				if(driver.findElements(By.xpath("//div[contains(text(),'"+Excel_EMDno+"')and(@role='button')]")).size()>0)
					driver.findElement(By.xpath("//div[contains(text(),'"+Excel_EMDno+"')and(@role='button')]")).click();
				else{
					queryObjects.logStatus(driver, Status.FAIL, "Search EMD", "Search Given EMD number not working" , null);
					return;
				}
				driver.findElement(By.xpath("//div[@translate='pssgui.details']")).click();
				FlightSearch.loadhandling(driver);
				String Emd_Statu=driver.findElement(By.xpath("//table/tbody//td/div[@role='button']")).getText().trim();
				if(!Emd_Statu.equalsIgnoreCase("OPEN")) {
	    			driver.findElement(By.xpath("//table/tbody//td/div[@role='button']")).click();
	    			driver.findElement(By.xpath("//label[text()='Status']/following-sibling::md-select")).click();
	    			driver.findElement(By.xpath("//md-option[contains(@ng-value,'emdstatusType')]/div[contains(text(),'OPEN')]")).click();
	    			driver.findElement(By.xpath("//button[contains(@type,'submit')]")).click();
	    			FlightSearch.loadhandling(driver);
	    			queryObjects.logStatus(driver, Status.PASS, "Emd Statu", "EMD status updated:", null);
	    		}
				else
					queryObjects.logStatus(driver, Status.INFO, "Emd Statu", "EMD status is:"+Emd_Statu, null);
			}                        
	    
			
		}
		catch(Exception e){
			queryObjects.logStatus(driver, Status.FAIL, "Search EMD", "Search Given EMD number not working" , e);
		}
	}
	
	
}
