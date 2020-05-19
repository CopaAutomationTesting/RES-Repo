package com.copa.RESscripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import FrameworkCode.BFrameworkQueryObjects;

public class WorldPay {
	String splitID[]=new String[3];
	List<String> split_list=new ArrayList<>();
	//String splitID ;//NS
	
	public void worldpay(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.open()");
		
		String maintab=driver.getWindowHandle();
		
		
		String mostRecentWindowHandle="";
		for(String winHandle:driver.getWindowHandles()){
	        mostRecentWindowHandle = winHandle;        
	    }
		driver.switchTo().window(mostRecentWindowHandle);
		try{
			String PNR=Login.PNRNUM.trim();
			if (PNR.isEmpty())
				return;
			 driver.navigate().to("https://secure.worldpay.com/sso/public/auth/login.html?serviceIdentifier=applicationlisttest");
			  // Login World pay Application
			 queryObjects.logStatus(driver, Status.PASS, "Login to Worldpay Account", "Login to Worldpay Account" , null);
				driver.findElement(By.name("username")).sendKeys("dxctest@copaairlines");
				driver.findElement(By.name("password")).sendKeys("Test!123");
				driver.findElement(By.id("login")).click();
				
				Thread.sleep(3000);
				Select Merchant_Code_login=new Select(driver.findElement(By.id("selectedMerchantCode")));
				WebDriverWait wait = new WebDriverWait(driver, 300);			
				Merchant_Code_login.selectByValue("COPAMOTOCC");	
				Thread.sleep(3000);
				driver.findElement(By.id("proceed")).click(); 
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='proceed' and @ disabled='disabled']")));
				 Merchant_Code_login=new Select(driver.findElement(By.id("selectedMerchantCode")));
				 Merchant_Code_login.selectByValue("COPAMOTOCC");	
				Thread.sleep(3000);
				driver.findElement(By.id("proceed")).click();
				Thread.sleep(2000);
				 wait.until(ExpectedConditions.elementToBeClickable( driver.findElement(By.xpath("//li[@id='transactions']"))));
				 
				 String []Merchantyp=FlightSearch.Worldpay_Merchant_code.split(";");
				 // this is for Marchant transaction type selection:::
				 String SMerchant_Code="";
				 List <String>temp = new ArrayList<>();;
				for (int imerch = 0; imerch < Merchantyp.length; imerch++) {
					//splitID="";
					if ( Login.SalesOff.contains("DML")){
						if (Merchantyp[imerch].equalsIgnoreCase("CC"))
							SMerchant_Code="COPAMOTOCCDML";
						else if (Merchantyp[imerch].equalsIgnoreCase("UATP")) 
							SMerchant_Code="COPAMOTOUATP";	
					}
					else{   // other than DML login 
						
						if (Merchantyp[imerch].equalsIgnoreCase("CC"))
							SMerchant_Code="COPAMOTOCC";
						else if (Merchantyp[imerch].equalsIgnoreCase("UATP")) 
							SMerchant_Code="COPAMOTOUATP";
					}
					temp.add(SMerchant_Code);
					if(!temp.contains(SMerchant_Code) || imerch==0 )   // if same value repeating stop to validate because below we are validating 
					{	
						Select Merchant_Code=new Select(driver.findElement(By.id("selectMerchantCode")));
						Merchant_Code.selectByValue(SMerchant_Code);
						Thread.sleep(3000);
						wait.until(ExpectedConditions.elementToBeClickable( driver.findElement(By.xpath("//li[@id='transactions']"))));
						
						 //Created PNR transaction validatlion like Amount and State ....
						 driver.findElement(By.xpath("//li[@id='transactions']")).click();
						 WebElement frame1=driver.findElement(By.name("iFrameForVersion1MaiIntab1"));
						driver.switchTo().frame(frame1); 
						queryObjects.logStatus(driver, Status.PASS, "Validating Transation", "PNR is"+PNR , null);
						
						wait.until(ExpectedConditions.elementToBeClickable( driver.findElement(By.xpath("//a[contains(@title,'"+PNR+"')]"))));
						 List<WebElement> etkts = driver.findElements(By.xpath("//a[contains(@title,'"+PNR+"')]"));
						int getOrdercount= etkts.size();
						// System.out.println(getOrdercount);
						List<WebElement> Order_Amount=driver.findElements(By.xpath("//tr[td[a[contains(@title,'"+PNR+"')]]]/td[3]/a"));
						
						String order_Code=queryObjects.getTestData("order_Code");
						if(order_Code.equalsIgnoreCase("yes")){
							//NS
							try {
							if(FlightSearch.CyberPNR.contains(";")) {
								splitID = FlightSearch.CyberPNR.split(";");
								split_list=Arrays.asList(splitID); 
								}
							else 
								split_list.add(FlightSearch.CyberPNR);

							}
							catch(Exception e) {
								queryObjects.logStatus(driver, Status.FAIL, "CyberPNR", e.getLocalizedMessage() , e);
							}
						}
						//NS
						//splitID=Login.envRead(Login.PNRNUM).trim();
						String get_Order_details;
						for (int Orderi = 0; Orderi < Order_Amount.size(); Orderi++) {
							get_Order_details=Order_Amount.get(Orderi).getText();
							//System.out.println(get_Order_details);
							// check amount  need to check amount----
							//click the order amount
							Order_Amount.get(Orderi).click();
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='contentpopupmain']")));
							String Transation_status=driver.findElement(By.xpath("//td[@id='status_id']")).getText().trim();
							if (Transation_status.equalsIgnoreCase("AUTHORIZED")) 
								queryObjects.logStatus(driver, Status.PASS, "PNR Status checking", "Status displaying AUTHORIZED " , null);
							else
								queryObjects.logStatus(driver, Status.FAIL, "PNR Status checking", "Status should display AUTHORIZED but Actual: "+Transation_status , null);
								
							String Transation_Amount=driver.findElement(By.xpath("//tr[td[@id='status_id']]/td[1]")).getText().trim();
							
							String amount=Transation_Amount.split("\\s+")[1].trim();
							amount=amount.replace(",", "");
							String CurrencyType=Transation_Amount.split("\\s+")[0].trim();
							
							if (FlightSearch.PayingAmount.contains(Double.parseDouble(amount))) 
								queryObjects.logStatus(driver, Status.PASS, "PNR Amount checking", "Amount displaying Correctly Amt is "+amount , null);
							else
								queryObjects.logStatus(driver, Status.FAIL, "PNR Amount checking", "Amount should display Correctly  Amt is "+amount , null);
							
							if(CurrencyType.equalsIgnoreCase(Login.Cur))
								queryObjects.logStatus(driver, Status.PASS, "Currency checking", "Currency displaying Correctly currency is "+CurrencyType , null);
							else
								queryObjects.logStatus(driver, Status.FAIL, "Currency checking", "Currency should display Correctly  Actual currency is "+CurrencyType +" Expected Currency is "+ Login.Cur, null);
							
							System.out.println(amount);
							Thread.sleep(3000);
							if(order_Code.equalsIgnoreCase("yes")){
							//Ordercode --- NS
							String ordercode = driver.findElement(By.xpath("//th[text()='order code']/following-sibling::td[1]")).getText().toUpperCase();
							//if(ordercode.contains((splitID[Orderi].toUpperCase())))
							if(split_list.contains(ordercode))
								queryObjects.logStatus(driver, Status.PASS, "Validating Ordercode", "Ordercode is correct", null);
							else
								queryObjects.logStatus(driver, Status.FAIL, "Validating Ordercode", "Ordercode is not correct", null);
							}
							//Airline Itinerary --- NS
							//1. Airline Code
							String airlineCode = driver.findElement(By.xpath("//th[text()='Airline Code']/following-sibling::td")).getText();
								if(airlineCode.equalsIgnoreCase("CM"))
									queryObjects.logStatus(driver, Status.PASS, "Validating Airline Code", "Airline Code is correct", null);
								else
									queryObjects.logStatus(driver, Status.FAIL, "Validating Airline Code", "Airline Code is not correct", null);
							//2. Airline Name	
								String airlineName = driver.findElement(By.xpath("//th[text()='Airline Name']/following-sibling::td")).getText();
								if(airlineName.equalsIgnoreCase("COPAAIRLINES"))
									queryObjects.logStatus(driver, Status.PASS, "Validating Airline Name", "Airline Name is correct", null);
								else
									queryObjects.logStatus(driver, Status.FAIL, "Validating Airline Name", "Airline Name is not correct", null);
							
							//3. Authorization Code
								String authCode = driver.findElement(By.xpath("//th[text()='authorisation code']/following-sibling::td")).getText().trim();
								if(!authCode.equalsIgnoreCase(""))
									queryObjects.logStatus(driver, Status.PASS, "Validating Authorization Code", "Authorization Code is correct", null);
								else
									queryObjects.logStatus(driver, Status.FAIL, "Validating Authorization Code", "Authorization Code is not correct", null);
							try {
							//4. Passenger Name
								String fname="";
								String lname="";
	
								fname=FlightSearch.wordpay_firstName.toUpperCase();
								lname=FlightSearch.wordpay_surname.toUpperCase();
							
									
								String paxName = driver.findElement(By.xpath("//th[text()='Passenger Name']/following-sibling::td")).getText();
								String Passenger = fname+" "+lname;
								if(paxName.equalsIgnoreCase(Passenger))
									queryObjects.logStatus(driver, Status.PASS, "Validating Passenger Name", "Passenger Name is "+Passenger, null);
								else
									queryObjects.logStatus(driver, Status.FAIL, "Validating Passenger Name", "Passenger Name is not correct", null);
							
							}	
							catch(Exception e) {	
								queryObjects.logStatus(driver, Status.FAIL, "Validating Passenger Name",e.getLocalizedMessage(),e);
							}
								driver.findElement(By.xpath("//div[@id='contentpopupbar']/a")).click();
								
								
							//
						}
						driver.switchTo().parentFrame();
					}
				}
				driver.findElement(By.xpath("//a[@alt='Logout']")).click(); 
		}
		catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "WorldPay validation", e.getLocalizedMessage(), e);
		}
		driver.close();
		driver.switchTo().window(maintab);
	}

}
