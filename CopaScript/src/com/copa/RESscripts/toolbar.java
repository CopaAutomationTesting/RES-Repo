package com.copa.RESscripts;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import FrameworkCode.BFrameworkQueryObjects;

public class toolbar {
	public static String pnrNum = null;
	public static void Ishare(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException
	{
		/*try
		{*/
		driver.findElement(By.xpath("//div[@id='toolbar']")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Native SHARES')]")).click();
		pnrNum =Login.PNRNUM.trim();
		
		driver.findElement(By.xpath("//input[contains(@aria-label,'Enter Input Shares Command')]")).sendKeys("*"+pnrNum);
		driver.findElement(By.xpath("//button[contains(@translate,'pssgui.submit')]")).click();
/*		}
		catch(Exception e)
		{
			
		}*/
		
		
	}
	
   public void FeeServicesMileageauxiliary(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException
   {   try
       {
	     driver.findElement(By.xpath("//div[@id='toolbar']")).click();
		 //driver.findElement(By.xpath("//div[contains(text(),'Native SHARES')]")).click();
		 driver.findElement(By.xpath("//div[contains(text(),'Fee Services & Other Charges')]")).click();
		 driver.findElement(By.xpath("//md-select[@ng-model='auxiliaryFare.model.selectedAuxType']")).click();
		 Thread.sleep(1000);
		 driver.findElement(By.xpath("//md-option//div[contains(text(),'Mileage auxiliary display')]")).click();
		 driver.findElement(By.xpath("//input[@name='destination']")).sendKeys("LAX");
		 driver.findElement(By.xpath("//input[@name='destination']")).sendKeys(Keys.ENTER);
		 driver.findElement(By.xpath("//button[@aria-label='Search']")).click();
		 FlightSearch.loadhandling(driver);
		 String city=driver.findElement(By.xpath("//div[contains(@ng-repeat,'DistanceData in auxiliaryFare')]//tr[3]/td[1]")).getText().trim();
		 String TPMmilage=driver.findElement(By.xpath("//div[contains(@ng-repeat,'DistanceData in auxiliaryFare')]//tr[3]/td[2]")).getText().trim();
		 String MpmMilage=driver.findElement(By.xpath("//div[contains(@ng-repeat,'DistanceData in auxiliaryFare')]//tr[3]/td[3]")).getText().trim();
		 String cumulativemilage=driver.findElement(By.xpath("//div[contains(@ng-repeat,'DistanceData in auxiliaryFare')]//tr[3]/td[4]")).getText().trim();
		 String gd=driver.findElement(By.xpath("//div[contains(@ng-repeat,'DistanceData in auxiliaryFare')]//tr[3]/td[5]")).getText().trim();
		 String highlevel=driver.findElement(By.xpath("//div[contains(@ng-repeat,'DistanceData in auxiliaryFare')]//tr[3]/td[8]")).getText().trim();
		 if(city.equalsIgnoreCase("LAX") &&TPMmilage.equalsIgnoreCase("3009") && MpmMilage.equalsIgnoreCase("3009"))
		 {
		    queryObjects.logStatus(driver, Status.PASS, "checking Mileage auxiliary display","Milage auxilary detail is displayin",null);
		 }
			 
		 else
		 {
			queryObjects.logStatus(driver, Status.FAIL, "checking Mileage auxiliary display", "Milage auxilary detail is not displayin",null);
		 }
          }
      catch(Exception e)
	   {
    	  queryObjects.logStatus(driver, Status.FAIL, "error while checking Mileage auxiliary display","" ,null);	  
	   }
   
   }
   
   public void FeeServicesPassengerfacilitydisplay(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException
   {   try
       {
	     driver.findElement(By.xpath("//div[@id='toolbar']")).click();
		 //driver.findElement(By.xpath("//div[contains(text(),'Native SHARES')]")).click();
		 driver.findElement(By.xpath("//div[contains(text(),'Fee Services & Other Charges')]")).click();
		 driver.findElement(By.xpath("//md-select[@ng-model='auxiliaryFare.model.selectedAuxType']")).click();
		 Thread.sleep(1000);
		 driver.findElement(By.xpath("//md-option//div[contains(text(),'Passenger facility charge display')]")).click();
		 driver.findElement(By.xpath("//input[@name='airport']")).sendKeys("LAX");
		 driver.findElement(By.xpath("//input[@name='airport']")).sendKeys(Keys.ENTER);
		 driver.findElement(By.xpath("//button[@aria-label='Search']")).click();
		 FlightSearch.loadhandling(driver);
		 
		 
		 if(driver.findElements(By.xpath("//div[@translate='pssgui.pfc.Information']//following-sibling::div")).size()>0)
		 {
		    queryObjects.logStatus(driver, Status.PASS, "checking Passenger facility charge display","Passenger facility charge display is displayin",null);
		 }
			 
		 else
		 {
			queryObjects.logStatus(driver, Status.FAIL, "checking Passenger facility charge display", "Passenger facility detail is not displayin",null);
		 }
          }
      catch(Exception e)
	   {
    	  queryObjects.logStatus(driver, Status.FAIL, "error while checking Mileage auxiliary display","" ,null);	  
	   }
   
   }
   
 //meenu
   public void customerSearch(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException
   {  
	   try
       {
		  queryObjects.logStatus(driver, Status.PASS, "Customer Search functionality checking", "Email Search Started" , null);
          driver.findElement(By.xpath("//div[@id='toolbar']")).click();
          driver.findElement(By.xpath("//md-menu-content/md-menu-item[15]/button/div[1]/div")).click();
          Thread.sleep(1000);
          String Search_By=FlightSearch.getTrimTdata(queryObjects.getTestData("Search_By"));
          if(Search_By.equalsIgnoreCase("EMail"))
          {   
        	  String Email=FlightSearch.getTrimTdata(queryObjects.getTestData("EMail"));
              driver.findElement(By.xpath("//input[@name='email']")).sendKeys(Email);
              Thread.sleep(1000);
              driver.findElement(By.xpath("//form[2]/div/div[2]/button")).click();
              FlightSearch.loadhandling(driver);
              //validation begins              
              String errormsg=FlightSearch.getErrorMSGfromAppliction(driver, queryObjects);
              if(errormsg.contains("Record not found"))
            	  queryObjects.logStatus(driver, Status.WARNING, "EMail search Completed","Record not Found ",null);
              else 
            	  if(driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[1]/div/table/tbody/tr/td[2]")).getText().toString().equals(Email))
            	  {
            		  queryObjects.logStatus(driver, Status.PASS, "EMail search Completed","Email Id fetched matches with the email input ",null);  
            	  }
              
            	  else
            	  {
            			queryObjects.logStatus(driver, Status.FAIL, "EMail search Completed","Email Id fetched matches with the email input",null);
            	  }
          }
          else if(Search_By.equalsIgnoreCase("Name"))
          {   
        	  String Surname=FlightSearch.getTrimTdata(queryObjects.getTestData("Surname"));
        	  String GivenName=FlightSearch.getTrimTdata(queryObjects.getTestData("GivenName"));
        	  String DOB=FlightSearch.getTrimTdata(queryObjects.getTestData("DateofBirth"));
              driver.findElement(By.xpath("//input[@name=\"surname\"]")).sendKeys(Surname);
              driver.findElement(By.xpath("//input[@name=\"givenName\"]")).sendKeys(GivenName);
              driver.findElement(By.xpath("//md-dialog//div/md-datepicker/div[1]/input")).sendKeys(DOB);
              Thread.sleep(2000);
              driver.findElement(By.xpath("//form[1]/div/div[2]/button")).click();
              //validation to be written             
                      
          }
        
       }
	   catch(Exception e)
	   {
		   queryObjects.logStatus(driver, Status.FAIL, "error while fetching Customer details","" ,null);	  
	   }
   
   }
   
   //Navira
   public static void SSRInventory(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {
	  
	 //Navigating to Reservations tab and coming back because after returning from Sales Reporting, toolbar icon is disabled - 05Mar
	  WebDriverWait wait = new WebDriverWait(driver, 50);
	  driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
	  Thread.sleep(2000);
	  driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Reservations')]")).click();
	  /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='New Order']")));
	  
	  driver.findElement(By.xpath("//div[@class='icn-logo pssgui-link']")).click();//Clicking on COPA Airlines Logo
		  */
	 
	  FlightSearch.loadhandling(driver);
	  driver.findElement(By.xpath("//div[@id='toolbar']")).click();
	  driver.findElement(By.xpath("//div[text()='SSR Inventory']")).click();
	  Thread.sleep(2000);
	  WebElement flight = driver.findElement(By.xpath("//label[text()='Flight']/following-sibling::input"));
	  WebElement from = driver.findElement(By.xpath("//div[div[text()='SSR Inventory']]/following-sibling::div//label[text()='From']/following-sibling::input"));
	  //WebElement from = driver.findElement(By.xpath("//label[text()='From']/following-sibling::input"));
	  //WebElement to = driver.findElement(By.xpath("//label[text()='To']/following-sibling::input"));
	  WebElement to = driver.findElement(By.xpath("//div[div[text()='SSR Inventory']]/following-sibling::div//label[text()='To']/following-sibling::input"));
	  WebElement date = driver.findElement(By.xpath("//div[label[span[text()='Departure Date']]]/following-sibling::md-datepicker//input"));
	  WebElement ssr = driver.findElement(By.xpath("//label[text()='ssrcode']/following-sibling::input"));
	  
	  String noseg = FlightSearch.getTrimTdata(queryObjects.getTestData("noseg"));
	  int inoseg = Integer.parseInt(noseg);
	  
	  String flightnum = FlightSearch.getTrimTdata(queryObjects.getTestData("SSRFlight"));
	  String[] arrflight = new String[10];
	  if(flightnum.contains(";")) {
		  arrflight = flightnum.split(";");
	  }
	  else
		  arrflight[0] = flightnum;
	  
	  String source = FlightSearch.getTrimTdata(queryObjects.getTestData("From"));
	  String[] arrsource = new String[10];
	  if(source.contains(";")) {
		  arrsource = source.split(";");
	  }
	  else
		  arrsource[0] = source;
	  
	  String destination = FlightSearch.getTrimTdata(queryObjects.getTestData("To"));
	  String[] arrdestination = new String[10];
	  if(destination.contains(";")) {
		  arrdestination = destination.split(";");
	  }
	  else
		  arrdestination[0] = destination;
	  
	  String ssrcode = FlightSearch.getTrimTdata(queryObjects.getTestData("SSR_Code"));
	  String[] arrssrcode = new String[10];
	  if(ssrcode.contains(";")) {
		  arrssrcode = ssrcode.split(";");
	  }
	  else
		  arrssrcode[0] = ssrcode;
	  
	  String sgetDate=FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
	  String[] arrsgetDate = new String[10];
	  if(sgetDate.contains(";")) {
		  arrsgetDate = sgetDate.split(";");
	  }
	  else
		  arrsgetDate[0] = sgetDate;
	  
	  
	  for(int iterate = 0;iterate<inoseg;iterate++) {
		  //Entering Flight Number
		  flight.click();
		  flight.clear();
		  flight.sendKeys(arrflight[iterate]);
		  from.sendKeys(Keys.ENTER);
		   
		  //Entering Source
		  from.clear();
		  from.sendKeys(arrsource[iterate]);
		  from.sendKeys(Keys.ENTER);
		   
		 //Entering Destination
		   to.click();
		   to.clear();
		   to.sendKeys(arrdestination[iterate]);
		   to.sendKeys(Keys.ENTER);
		   
		   //Entering SSR Code
		   ssr.click();
		   ssr.clear();
		   ssr.sendKeys(arrssrcode[iterate]);
		   ssr.sendKeys(Keys.ENTER);
		   
		   //Entering SSR Date
		   date.click();
		   date.clear();
		   int getDays=0;
		   getDays=Integer.parseInt(arrsgetDate[iterate]);
		   Calendar calc = Calendar.getInstance();
		   calc.add(Calendar.DATE, +getDays);
		   String timeStampd = new SimpleDateFormat("MM/dd/yyyy").format(calc.getTime());   
		   date.sendKeys(timeStampd);
		   Thread.sleep(2000);
		   
		   //Click on Display
		 driver.findElement(By.xpath("//button[text()='Display']")).click();
		 FlightSearch.loadhandling(driver);
		 
		 if(driver.findElements(By.xpath("//div//div[text()='Y ']/following-sibling::div[1]")).size()>0) {//Navira - 06Mar
			 queryObjects.logStatus(driver, Status.PASS, "Finding the SSR", "SSR found in the Table", null);
			 String Totalssr = driver.findElement(By.xpath("//div//div[text()='Y ']/following-sibling::div[1]")).getText();
			 String Soldssr = driver.findElement(By.xpath("//div//div[text()='Y ']/following-sibling::div[2]")).getText();
			 String Availssr = driver.findElement(By.xpath("//div//div[text()='Y ']/following-sibling::div[3]")).getText();
			 if(!Availssr.equalsIgnoreCase("No")) {
				 int iavailssr = Integer.parseInt(Availssr);
				 if(iavailssr > 0) {
					 queryObjects.logStatus(driver, Status.PASS, "Checking availability for SSR", "The specific SSR: "+ssrcode+" is available", null);
					 queryObjects.logStatus(driver, Status.PASS, "Checking availability for SSR", "Total "+ssrcode+" present for the flight is: "+Totalssr, null);
					 queryObjects.logStatus(driver, Status.PASS, "Checking availability for SSR", "Total "+ssrcode+" sold is: "+Soldssr, null);
					 queryObjects.logStatus(driver, Status.PASS, "Checking availability for SSR", "Total "+ssrcode+" available is: "+Availssr, null);
				 }
				 
			 }
			 else
					 queryObjects.logStatus(driver, Status.WARNING, "Checking availability for SSR", "The specific SSR: "+ssrcode+" is not available", null);
					 
		 }
		 else if(driver.findElements(By.xpath("//md-dialog//span[text()='NO ITEMS IN TBL']")).size()>0)
			 queryObjects.logStatus(driver, Status.WARNING, "Finding the SSR", "SSR not found in the Table", null);
		 else
			 queryObjects.logStatus(driver, Status.FAIL, "Finding the SSR", "Error fiding the SSR", null);
		 

		 driver.findElement(By.xpath("//div[div[text()='SSR Inventory']]/following-sibling::div//button[@aria-label='Clear']")).click();
		 FlightSearch.loadhandling(driver);
	 }	
	  
	 //Click on Close Icon
	 driver.findElement(By.xpath("//div[text()='SSR Inventory']/following-sibling::div/i")).click();
	 FlightSearch.loadhandling(driver);

	  
	}

}
