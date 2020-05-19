package com.copa.ATOscripts;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.RESscripts.*;
import com.copa.Util.ATOflowPageObjects;

import FrameworkCode.BFrameworkQueryObjects;

public class Keyboard_ATO extends ATOflowPageObjects {
	
	
	public void HotKeysCheck(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception{
		
		try{
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("PassangerDetails")).equalsIgnoreCase("Y"))
				PassangerDetails(driver,queryObjects);
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("Basic")).equalsIgnoreCase("Y"))
			HotKeysBasic(driver,queryObjects);
			
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("Gate")).equalsIgnoreCase("Y"))
			GateHotKeys(driver,queryObjects);
			
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("CheckIn")).equalsIgnoreCase("Y"))
				CheckInFlow(driver,queryObjects);
			
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("AddBag")).equalsIgnoreCase("Y"))
				AddBaggage(driver,queryObjects);
			
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("Unrec")).equalsIgnoreCase("Y"))
				Unreclist(driver,queryObjects);
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("SelectPax")).equalsIgnoreCase("Y"))
				Selectpax(driver,queryObjects);
			
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("StandbyPassenger")).equalsIgnoreCase("Y"))
				StandbyPassenger(driver,queryObjects);
			
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("BoardingPassenger")).equalsIgnoreCase("Y"))
				BoardingPassenger(driver,queryObjects);
				
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("Closeflight")).equalsIgnoreCase("Y"))
				Closeflight(driver,queryObjects);
			
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("FlightSummary")).equalsIgnoreCase("Y"))
				FlightSummary(driver,queryObjects);	
			
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("Standby")).equalsIgnoreCase("Y"))
				Standby(driver,queryObjects);
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("TabValidate")).equalsIgnoreCase("Y"))
				TabValidate(driver,queryObjects);
			
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("HeaderValidate")).equalsIgnoreCase("Y"))
				HeaderValidate(driver,queryObjects);
		}catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Ket Bord operation Faild", e.getLocalizedMessage(), e);
		}	
		
	}
	
	public void HotKeysBasic(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException{
	
		
		try{
			WebDriverWait wait = new WebDriverWait(driver, 50);
		driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
		 Thread.sleep(2000);
		 driver.findElement(By.xpath(CheckInXpath)).click();
		 FlightSearch.loadhandling(driver);
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LabelPassengerXpath)));
		 queryObjects.logStatus(driver, Status.PASS, "Search Passenger", "Search Passenger displayed ",null);
		 
		 //ALT +G to open Sales office
		 Robot robot = new Robot();
         robot.keyPress(KeyEvent.VK_ALT);
         robot.keyPress(KeyEvent.VK_G);
         robot.keyPress(KeyEvent.VK_ENTER);
         robot.keyRelease(KeyEvent.VK_ENTER);
         robot.keyRelease(KeyEvent.VK_G);
         robot.keyRelease(KeyEvent.VK_ALT);
         Thread.sleep(1000);
		 	
		 Boolean Isdisp =driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div/div[1]/md-input-container/md-select/md-select-value/span[2]")).isDisplayed();
		 if (Isdisp) {
		 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+G - Sales office verified", " ",null);
		 driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div[2]/button[contains(text(),'OK')]")).click(); 
		 }
		 
		 ////ALT +C to Given name
		 
		
         robot.keyPress(KeyEvent.VK_ALT);
         robot.keyPress(KeyEvent.VK_C);
         robot.keyPress(KeyEvent.VK_ENTER);
         robot.keyRelease(KeyEvent.VK_ENTER);
         robot.keyRelease(KeyEvent.VK_C);
         robot.keyRelease(KeyEvent.VK_ALT);
         Thread.sleep(1000);
		 
         WebElement activeElement = driver.switchTo().activeElement(); 
         String cName =  activeElement.getAttribute("ng-model"); 
        // String id = activeElement.getAttribute("id");
         
         if(cName.contains("givenName"))
        	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+C verified - focus on Given Name", " ",null);
         
         
         ////ALT +L to Flight number
		 
 		
         robot.keyPress(KeyEvent.VK_ALT);
         robot.keyPress(KeyEvent.VK_L);
         robot.keyRelease(KeyEvent.VK_L);
         robot.keyRelease(KeyEvent.VK_ALT);
         Thread.sleep(1000);
		 
         WebElement activeElement1 = driver.switchTo().activeElement(); 
         String cName1 =  activeElement1.getAttribute("ng-model"); 
        // String id = activeElement.getAttribute("id");
         
         if(cName1.contains("flightNumber"))
        	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+L verified - focus on Flight Number", " ",null);
         
         ////ALT +SHIFT +B to Clear button
		 
         robot.keyPress(KeyEvent.VK_ALT);
         robot.keyPress(KeyEvent.VK_SHIFT);
         robot.keyPress(KeyEvent.VK_B);
         robot.keyRelease(KeyEvent.VK_B);
         robot.keyRelease(KeyEvent.VK_SHIFT);
         robot.keyRelease(KeyEvent.VK_ALT);
         Thread.sleep(1000);
		 
         WebElement activeElement2 = driver.switchTo().activeElement(); 
         String cName2 =  activeElement2.getText();
        // String id = activeElement.getAttribute("id");
         
         if(cName2.contains("Clear"))
        	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+SHIFT+B verified - focus on Clear button", " ",null);

         //navigate  to flight details screen
         
         List<WebElement> NoofFlight=driver.findElements(By.xpath("//md-content/table[contains(@ng-table,'flightDepartArrival')]/tbody/tr/child::td[5][contains(text(),'OPEN')]//preceding-sibling::td[4]"));
		  // String FlightLink=NoofFlight.get(i).toString();
		   NoofFlight.get(0).click();
		   FlightSearch.loadhandling(driver);
		   
////ALT +SHIFT + T to tabs
		 
         robot.keyPress(KeyEvent.VK_ALT);
         robot.keyPress(KeyEvent.VK_SHIFT);
         robot.keyPress(KeyEvent.VK_T);
         robot.keyRelease(KeyEvent.VK_T);
         robot.keyRelease(KeyEvent.VK_SHIFT);
         robot.keyRelease(KeyEvent.VK_ALT);
         Thread.sleep(1000);
		 
         WebElement activeElement3 = driver.switchTo().activeElement(); 
         String cName3 =  activeElement3.getText();
        // String id = activeElement.getAttribute("id");
         
         if(cName3.contains("All"))
        	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+SHIFT+T verified - focus on TABS", " ",null);
          
////ALT + R to Right panel
		 
         robot.keyPress(KeyEvent.VK_ALT);
         
         robot.keyPress(KeyEvent.VK_R);
         robot.keyRelease(KeyEvent.VK_R);
         
         robot.keyRelease(KeyEvent.VK_ALT);
         Thread.sleep(1000);
		 
         WebElement activeElement4 = driver.switchTo().activeElement(); 
         String cName4 =  activeElement4.getAttribute("ng-click");
        // String id = activeElement.getAttribute("id");
         
         if(cName4.contains("restrict"))
        	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+R verified - focus on restrict (Right panel)", " ",null);
          
   
         ////ALT + H to Home
		 
         robot.keyPress(KeyEvent.VK_ALT);
         
         robot.keyPress(KeyEvent.VK_H);
         robot.keyRelease(KeyEvent.VK_H);
         
         robot.keyRelease(KeyEvent.VK_ALT);
         Thread.sleep(1000);
		 
         WebElement activeElement5 = driver.switchTo().activeElement(); 
         String cName5 =  activeElement5.getText();
        // String id = activeElement.getAttribute("id");
         
         if(cName5.contains("Home"))
        	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+H verified - focus on Home", " ",null);
     
         ////ALT + B to Back
		 
         robot.keyPress(KeyEvent.VK_ALT);
         
         robot.keyPress(KeyEvent.VK_B);
         robot.keyRelease(KeyEvent.VK_B);
         
         robot.keyRelease(KeyEvent.VK_ALT);
         Thread.sleep(1000);
		 
         WebElement activeElement6 = driver.switchTo().activeElement(); 
         String cName6 =  activeElement6.getText();
        // String id = activeElement.getAttribute("id");
         
         if(cName6.contains("Back"))
        	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+B verified - focus on Back", " ",null);
          
         
         
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "KeyBoard Actions failed", " ",e);
			
		}
		
		
	}

	
	public void GateHotKeys(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception{
		FlightSearch.loadhandling(driver);
		try {
			 
				 String sOrderNum =Login.PNRNUM.trim();				 
			 
		 boolean OrderId = driver.findElement(By.xpath("//*[contains(text(),'"+sOrderNum+"')]")).isDisplayed();
		 if (OrderId) {
			 queryObjects.logStatus(driver, Status.PASS, "Search Passenger by Order ID", "OrderID search completed: "+sOrderNum , null);
		 }else {
			 queryObjects.logStatus(driver, Status.FAIL, "Search Passenger by Order ID "+sOrderNum, "OrderID search not completed: "+sOrderNum , null);
			 }
	 
		 
		 
		Robot robot = new Robot();
		try {
		 //ALT +P,B to open Sales office
		//	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FinalCheckInXpath)));
			// driver.findElement(By.xpath(FinalCheckInXpath)).isDisplayed();
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_P);
        robot.keyPress(KeyEvent.VK_B);
        robot.keyRelease(KeyEvent.VK_B);
        robot.keyRelease(KeyEvent.VK_P);
        robot.keyRelease(KeyEvent.VK_ALT);
        Thread.sleep(1000);
        WebElement activeElement = driver.switchTo().activeElement(); 
        String cName =  activeElement.getAttribute("class");
       // String id = activeElement.getAttribute("id");
        
        if(cName.contains("boarding"))
       	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+P,B verified - focus on BoardingPass", " ",null);
		}catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "KeyBoard Action Alt+P,B failed", " ",e);
		}
		
		try {
		
        //ALT +P,T to open Sales office
		 
       robot.keyPress(KeyEvent.VK_ALT);
       robot.keyPress(KeyEvent.VK_P);
       robot.keyPress(KeyEvent.VK_T);
       robot.keyRelease(KeyEvent.VK_T);
       robot.keyRelease(KeyEvent.VK_P);
       robot.keyRelease(KeyEvent.VK_ALT);
       Thread.sleep(1000);
       WebElement activeElement2 = driver.switchTo().activeElement(); 
       String cName2 =  activeElement2.getAttribute("class");
      // String id = activeElement.getAttribute("id");
       
       if(cName2.contains("print"))
      	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+P,T verified - focus on ticket print", " ",null);
   
		}catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "KeyBoard Action Alt+P,T  failed", " ",e);
		}
		
       //ALT +K to open Check in
		try { 
			
       robot.keyPress(KeyEvent.VK_ALT);
       robot.keyPress(KeyEvent.VK_K);
      
       
       robot.keyRelease(KeyEvent.VK_K);
       robot.keyRelease(KeyEvent.VK_ALT);
       Thread.sleep(1000);
       WebElement activeElement3 = driver.switchTo().activeElement(); 
       String cName3 =  activeElement3.getText();
      // String id = activeElement.getAttribute("id");
       
       if(cName3.contains("Check In"))
      	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+K verified - focus on Proceed to Check In", " ",null);
   
		}catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "KeyBoard Action Alt+K failed", " ",e);
		}
		
		}catch(Exception e) {}
	}
	
	public void CheckInFlow(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception{
		Robot robot = new Robot();
		 //ALT +K to open Check in
		
		FlightSearch.loadhandling(driver);
		try {
			 
				 String sOrderNum = Login.PNRNUM.trim();				 
			 
		 boolean OrderId = driver.findElement(By.xpath("//*[contains(text(),'"+sOrderNum+"')]")).isDisplayed();
		 if (OrderId) {
			 queryObjects.logStatus(driver, Status.PASS, "Search Passenger by Order ID", "OrderID search completed: "+sOrderNum , null);
		 }else {
			 queryObjects.logStatus(driver, Status.FAIL, "Search Passenger by Order ID "+sOrderNum, "OrderID search not completed: "+sOrderNum , null);
			 }
	 
		try { 
			
       robot.keyPress(KeyEvent.VK_ALT);
       robot.keyPress(KeyEvent.VK_K);
       
       robot.keyRelease(KeyEvent.VK_K);
       robot.keyRelease(KeyEvent.VK_ALT);
       Thread.sleep(1000);
       WebElement activeElement3 = driver.switchTo().activeElement(); 
       String cName3 =  activeElement3.getText();
      // String id = activeElement.getAttribute("id");
       
       if(cName3.contains("Check In"))
      	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+K verified - focus on Proceed to Check In", " ",null);
   
       robot.keyPress(KeyEvent.VK_ENTER);
       robot.keyRelease(KeyEvent.VK_ENTER);
       FlightSearch.loadhandling(driver);
       
   ////ALT +C+ 4tab to Given name
		 
		
       robot.keyPress(KeyEvent.VK_ALT);
       robot.keyPress(KeyEvent.VK_C);
       
       robot.keyRelease(KeyEvent.VK_C);
       robot.keyRelease(KeyEvent.VK_ALT);
       Presstab(robot,4);
       Thread.sleep(3000);
       //FlightSearch.loadhandling(driver);
       WebElement activeElement = driver.switchTo().activeElement(); 
       String cName =  activeElement.getAttribute("name");
       
       if(cName.contains("name")) {
        	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action navigation to APIS verified - focus on Proceed to Surname", " ",null);
        	 	
       if(EnterdetailsKeyboard(driver,queryObjects)) {
    	////ALT +SHIFT +B to Cancel button
  		 
           robot.keyPress(KeyEvent.VK_ALT);
           robot.keyPress(KeyEvent.VK_SHIFT);
           robot.keyPress(KeyEvent.VK_B);
           robot.keyRelease(KeyEvent.VK_B);
           robot.keyRelease(KeyEvent.VK_SHIFT);
           robot.keyRelease(KeyEvent.VK_ALT);
           Thread.sleep(1000);
  		 
           WebElement activeElement2 = driver.switchTo().activeElement(); 
           String cName2 =  activeElement2.getText();
          // String id = activeElement.getAttribute("id");
           
           if(cName2.contains("Clear"))
          	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+SHIFT+B verified - focus on Clear button", " ",null);
           Presstab(robot,1);
           robot.keyPress(KeyEvent.VK_ENTER);
           robot.keyRelease(KeyEvent.VK_ENTER);
           FlightSearch.loadhandling(driver);
           
       ////ALT +SHIFT +B to Delete button
    		 
           robot.keyPress(KeyEvent.VK_ALT);
           robot.keyPress(KeyEvent.VK_SHIFT);
           robot.keyPress(KeyEvent.VK_B);
           robot.keyRelease(KeyEvent.VK_B);
           robot.keyRelease(KeyEvent.VK_SHIFT);
           robot.keyRelease(KeyEvent.VK_ALT);
           Thread.sleep(1000);
  		 
           WebElement activeElement4 = driver.switchTo().activeElement(); 
           String cName4 =  activeElement4.getText();
          
           
           if(cName2.contains("Delete"))
          	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+SHIFT+B verified - focus on Delete button", " ",null);
           Presstab(robot,1);
           robot.keyPress(KeyEvent.VK_ENTER);
           robot.keyRelease(KeyEvent.VK_ENTER);
           FlightSearch.loadhandling(driver);
           
           
           robot.keyPress(KeyEvent.VK_ALT);
           robot.keyPress(KeyEvent.VK_K);
           
           robot.keyRelease(KeyEvent.VK_K);
           robot.keyRelease(KeyEvent.VK_ALT);
           Thread.sleep(1000);
           WebElement activeElement5 = driver.switchTo().activeElement(); 
           String cName5 =  activeElement5.getText();
          // String id = activeElement.getAttribute("id");
           
           if(cName5.contains("Check In"))
          	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+K verified - focus on  Check In", " ",null);
       
           robot.keyPress(KeyEvent.VK_ENTER);
           robot.keyRelease(KeyEvent.VK_ENTER);
           FlightSearch.loadhandling(driver);
           Presstab(robot,1);
           
           Thread.sleep(1000);
           WebElement activeElement6 = driver.switchTo().activeElement(); 
           String cName6 =  activeElement6.getText();
          // String id = activeElement.getAttribute("id");
           
           if(cName6.contains("OK"))
          	 queryObjects.logStatus(driver, Status.PASS, "Check In Complete Pop up", " ",null);
       
           robot.keyPress(KeyEvent.VK_ENTER);
           robot.keyRelease(KeyEvent.VK_ENTER);
           FlightSearch.loadhandling(driver);
      	 queryObjects.logStatus(driver, Status.PASS, "Check In Complete with keyboard enteries", " ",null);  
           
       }
       }
		}catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "KeyBoard Action Alt+K failed", " ",e);
		}
		
		}catch(Exception e) {
			 queryObjects.logStatus(driver, Status.FAIL, "Search Passenger by Order ID ", "OrderID search not completed: ", e);
		}
	}
	
	
	public void Presstab(Robot robot,int times) throws AWTException {
		
		for(int i=1;i<=times;i++){
		 robot.keyPress(KeyEvent.VK_TAB);
	       robot.keyRelease(KeyEvent.VK_TAB);
		}
		
	}
		public boolean EnterdetailsKeyboard(WebDriver driver,BFrameworkQueryObjects queryObjects) throws AWTException, InterruptedException, IOException {
			 List<WebElement> Passengers = driver.findElements(By.xpath("//*[contains(@ng-repeat,'orderObj.Passengers')]/div/div/span"));				
					String PaxName = Passengers.get(0).getText();
					 String[] Arr = PaxName.split("/");
					 String	sLName= Arr[0];
					 String	sFname = Arr[1];
					 
					 Robot robot = new Robot();		 
					 SendKeys(robot,sLName);
					 Presstab(robot,1);
					 SendKeys(robot,sFname);
					 Presstab(robot,1);
					 SendKeys(robot,"11/08/1986");
					 Presstab(robot,1);
					 SendKeys(robot,"F");
					 Presstab(robot,2);
					 SendKeys(robot,"1234567");
					 Presstab(robot,1);
					 SendKeys(robot,"11/08/2019");
					 Presstab(robot,1);
					 SendKeys(robot,"US");
					 Thread.sleep(1000);
					 Presstab(robot,1);
					 SendKeys(robot,"US");
					 Thread.sleep(1000);
					 Presstab(robot,1);
					 SendKeys(robot,"US");
					 Thread.sleep(1000);
					 Presstab(robot,1);
					 
					 Boolean isenabled = driver.findElement(By.xpath(SubmitXpath)).isEnabled();
						if (isenabled ) {
							queryObjects.logStatus(driver, Status.PASS, "Submit button was enabled, Document was verfied:", "Submit button was enabled ", null);
						}
						
						else
						{
							queryObjects.logStatus(driver, Status.FAIL, "Submit button was not enabled, Document was not verfied:", "Submit button was not enabled ", null);
						}
						return isenabled;
			
		}
		void SendKeys(Robot robot, String keys) {
		    for (char c : keys.toCharArray()) {
		        int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
		        if (KeyEvent.CHAR_UNDEFINED == keyCode) {
		            throw new RuntimeException(
		                "Key code not found for character '" + c + "'");
		        }
		        robot.keyPress(keyCode);
		        robot.delay(100);
		        robot.keyRelease(keyCode);
		        robot.delay(100);
		    }
		}
		
	
		public void AddBaggage(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception{
														
			Robot robot = new Robot();
			 ////ALT +C to central
			 
			
	         robot.keyPress(KeyEvent.VK_ALT);
	         robot.keyPress(KeyEvent.VK_C);
	         robot.keyRelease(KeyEvent.VK_C);
	         robot.keyRelease(KeyEvent.VK_ALT);
	         Presstab(robot,11);
	         robot.keyPress(KeyEvent.VK_ENTER);
	         robot.keyRelease(KeyEvent.VK_ENTER);
	         FlightSearch.loadhandling(driver);
	         
	         robot.keyPress(KeyEvent.VK_ALT);
	         robot.keyRelease(KeyEvent.VK_ALT);
	         Presstab(robot,1);
	         robot.keyPress(KeyEvent.VK_S);
	         robot.keyRelease(KeyEvent.VK_S); 
	         
	         Presstab(robot,1);
	         //SendKeys(robot,"14");
	         driver.findElement(By.xpath(BagweightXpath)).sendKeys("14");
	         Thread.sleep(1000);
	         driver.findElement(By.xpath(BaggSubmitXpath)).click();
	         FlightSearch.loadhandling(driver);
	         driver.findElement(By.xpath(ContinueXpath)).click();
	         FlightSearch.loadhandling(driver);
	         driver.findElement(By.xpath(FinalCheckInXpath)).click();
	         FlightSearch.loadhandling(driver);
	         boolean confirmok = false;
				try {
				 confirmok = driver.findElement(By.xpath(ConfirmOKXpath)).isDisplayed();
				
				}catch (Exception e) {}
				
				if (confirmok) {
					driver.findElement(By.xpath(ConfirmOKXpath)).click();
					 queryObjects.logStatus(driver, Status.PASS, "Check in successful after add baggage", "Check in successful ", null);
				}
				else{
					 queryObjects.logStatus(driver, Status.FAIL, "Check in successful after add baggage", "Unable to add Baggage ", null);
				}
		}
	
		
		public void Unreclist(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception {
			
			WebDriverWait wait = new WebDriverWait(driver, 50);
			driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
			 Thread.sleep(2000);
			 driver.findElement(By.xpath(CheckInXpath)).click();
			 FlightSearch.loadhandling(driver);
			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LabelPassengerXpath)));
			 queryObjects.logStatus(driver, Status.PASS, "Search Passenger", "Search Passenger displayed ",null);
			 
			 //navigate  to flight details screen
	         
	         List<WebElement> NoofFlight=driver.findElements(By.xpath("//md-content/table[contains(@ng-table,'flightDepartArrival')]/tbody/tr/child::td[5][contains(text(),'OPEN')]//preceding-sibling::td[4]"));
			  // String FlightLink=NoofFlight.get(i).toString();
			   NoofFlight.get(0).click();
			   FlightSearch.loadhandling(driver);
			 
			Robot robot = new Robot();
		////ALT +SHIFT + T to tabs
			 
	         robot.keyPress(KeyEvent.VK_ALT);
	         robot.keyPress(KeyEvent.VK_SHIFT);
	         robot.keyPress(KeyEvent.VK_T);
	         robot.keyRelease(KeyEvent.VK_T);
	         robot.keyRelease(KeyEvent.VK_SHIFT);
	         robot.keyRelease(KeyEvent.VK_ALT);
	         Thread.sleep(1000);
			 
	         WebElement activeElement3 = driver.switchTo().activeElement(); 
	         String cName3 =  activeElement3.getText();
	        // String id = activeElement.getAttribute("id");
	         
	         if(cName3.contains("All"))
	        	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+SHIFT+T verified - focus on TABS", " ",null);
	         
	         Presstab(robot,7);
	         robot.keyPress(KeyEvent.VK_ENTER);
	         robot.keyRelease(KeyEvent.VK_ENTER);
	         FlightSearch.loadhandling(driver);
	         
	         WebElement activeElement4 = driver.switchTo().activeElement(); 
	         String cName4 =  activeElement4.getText();
	        // String id = activeElement.getAttribute("id");
	         
	         if(cName4.contains("Unrec"))
	        	 queryObjects.logStatus(driver, Status.PASS, "KeyBoard Action Alt+SHIFT+T verified - focus on TABS", " ",null);
	         
	         
	          
		}
		public void PassangerDetails(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception
		{
			try
			{
				String sFlight;
				String sOrderNum;
				boolean bb=false;
				WebDriverWait wait = new WebDriverWait(driver, 50);
				queryObjects.logStatus(driver, Status.PASS, "SelectPax with Keyboard", "Start",null);
				
				 Robot robot = new Robot();
				 
				 try {
					 robot.keyPress(KeyEvent.VK_ALT);
			         robot.keyPress(KeyEvent.VK_G);
			         robot.keyRelease(KeyEvent.VK_G);
			         robot.keyRelease(KeyEvent.VK_ALT);
			         Thread.sleep(1000);
			         queryObjects.logStatus(driver, Status.PASS, "Copa Airlines Selected", "Click Top header Pannel",null);
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL	, "Copa Airlines Selected", "Click Top header Pannel",null);									
					// TODO: handle exception
				}
				 
				 Presstab(robot,3);
				 queryObjects.logStatus(driver, Status.PASS, "Reservation Tab found", "Reached Reservation",null);
				 robot.keyPress(KeyEvent.VK_ENTER);
				 robot.keyRelease(KeyEvent.VK_ENTER);
				 Thread.sleep(1500);
				 
				 
				 Presstab(robot,1);
				 robot.keyPress(KeyEvent.VK_ENTER);
				 robot.keyRelease(KeyEvent.VK_ENTER);
				 FlightSearch.loadhandling(driver);
				 queryObjects.logStatus(driver, Status.PASS, "CheckIn Tab found", "Reached Checkin Page",null);
				 robot.keyPress(KeyEvent.VK_ALT);
				 robot.keyPress(KeyEvent.VK_C);
				 robot.keyRelease(KeyEvent.VK_C);
				 robot.keyRelease(KeyEvent.VK_ALT);
				 queryObjects.logStatus(driver, Status.PASS, "ATL + C", "Center Pannel",null);
				 Presstab(robot,6);
				 
				 sOrderNum = Login.PNRNUM.trim();
				 //sOrderNum="B0NG3P";
				 
				 driver.findElement(By.xpath("//pssgui-passenger-search//md-input-container/input[contains(@aria-label,'Order Number')]")).sendKeys(sOrderNum);
				/* do{
					 robot.keyPress(KeyEvent.VK_TAB);
					 robot.keyRelease(KeyEvent.VK_TAB);
					 bb = driver.switchTo().activeElement().equals(driver.findElement(By.xpath("//md-content//div/span[contains(text(),'"+sOrderNum+"')]")));
				 }while(!bb)*/;
				 
				// queryObjects.logStatus(driver, Status.PASS, "Found the Passenger", "To view Passenger info",null);
				 				
				 Presstab(robot,5);
				// pressta
				 robot.keyPress(KeyEvent.VK_ENTER);
		         robot.keyRelease(KeyEvent.VK_ENTER);
		         FlightSearch.loadhandling(driver);
		         
		        if(driver.findElements(By.xpath("//md-content//div/span[contains(text(),'"+sOrderNum+"')]")).size()>0)
		        {
		         
		         queryObjects.logStatus(driver, Status.PASS, "Searched Passenger details", "Selected specified Passenger",null);
		        }
		        else
		        {
		        	queryObjects.logStatus(driver, Status.PASS, "Searched Passenger details", "Selected specified Passenger",null);
		        }
				 
			}
			catch(Exception e)
			{
				queryObjects.logStatus(driver, Status.FAIL, "Searched Passenger details", "CheckIn Details",null);
			}
		
		}
public void Selectpax(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception {
			



			String sFlight;
			String sOrderNum;
			boolean bb=false;
			WebDriverWait wait = new WebDriverWait(driver, 50);
			queryObjects.logStatus(driver, Status.PASS, "SelectPax with Keyboard", "Start",null);
			

			 Robot robot = new Robot();
			 
			 try {
				 robot.keyPress(KeyEvent.VK_ALT);
		         robot.keyPress(KeyEvent.VK_G);
		         robot.keyRelease(KeyEvent.VK_G);
		         robot.keyRelease(KeyEvent.VK_ALT);
		         Thread.sleep(1000);
		         queryObjects.logStatus(driver, Status.PASS, "Copa Airlines Selected", "Click Top header Pannel",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL	, "Copa Airlines Selected", "Click Top header Pannel",null);
				// TODO: handle exception
			}
			 try {
				 












				 Presstab(robot,3);
				 queryObjects.logStatus(driver, Status.PASS, "Reservation Tab found", "Reached Reservation",null);
				 robot.keyPress(KeyEvent.VK_ENTER);
				 robot.keyRelease(KeyEvent.VK_ENTER);
				 Thread.sleep(1500);
				 
				

				 Presstab(robot,1);
				 robot.keyPress(KeyEvent.VK_ENTER);
				 robot.keyRelease(KeyEvent.VK_ENTER);
				 FlightSearch.loadhandling(driver);
				 queryObjects.logStatus(driver, Status.PASS, "CheckIn Tab found", "Reached Checkin Page",null);
				 robot.keyPress(KeyEvent.VK_ALT);
				 robot.keyPress(KeyEvent.VK_L);
				 robot.keyRelease(KeyEvent.VK_L);
				 robot.keyRelease(KeyEvent.VK_ALT);
				 
				 sFlight= Login.FLIGHTNUM;
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@aria-label='Flight']")).sendKeys(sFlight);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 Thread.sleep(1500);
				 Calendar calend = Calendar.getInstance();
				 String sFromDate="";
				 calend.add(Calendar.DATE, +1);
				 sFromDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@class='md-datepicker-input']")).clear();
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@class='md-datepicker-input']")).sendKeys(sFromDate);
				 System.out.print(sFromDate);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_ENTER);
		         robot.keyRelease(KeyEvent.VK_ENTER);
		         FlightSearch.loadhandling(driver);
		         queryObjects.logStatus(driver, Status.PASS, "CheckIn Page Reached", "Searched with Flight number",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "CheckIn Page Reached", "Searched with Flight number",null);
				// TODO: handle exception
			}
			 try {
				 robot.keyPress(KeyEvent.VK_ALT);
				 robot.keyPress(KeyEvent.VK_C);
				 robot.keyRelease(KeyEvent.VK_C);
				 robot.keyRelease(KeyEvent.VK_ALT);
				 queryObjects.logStatus(driver, Status.PASS, "ATL + C", "Center Pannel",null);
				 Presstab(robot,6);

				 sOrderNum =Login.PNRNUM;



				 do{
					 robot.keyPress(KeyEvent.VK_TAB);
					 robot.keyRelease(KeyEvent.VK_TAB);
					 bb = driver.switchTo().activeElement().equals(driver.findElement(By.xpath("//md-content//div/span[contains(text(),'"+sOrderNum+"')]")));
				 }while(!bb);
				 
				 
						 
				 queryObjects.logStatus(driver, Status.PASS, "Found the Passenger", "To view Passenger info",null);
				 robot.keyPress(KeyEvent.VK_SHIFT);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_SHIFT);
				 robot.keyPress(KeyEvent.VK_SHIFT);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_SHIFT);
				 robot.keyPress(KeyEvent.VK_SHIFT);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_SHIFT);



				 robot.keyPress(KeyEvent.VK_ENTER);
		         robot.keyRelease(KeyEvent.VK_ENTER);
		         FlightSearch.loadhandling(driver);




		         queryObjects.logStatus(driver, Status.PASS, "Searched Passenger details", "Selected specified Passenger",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Searched Passenger details", "CheckIn Details",null);
				// TODO: handle exception
			}
	         
		}
		
		public void StandbyPassenger(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception {
			
			String sFlight;
			WebDriverWait wait = new WebDriverWait(driver, 50);
			queryObjects.logStatus(driver, Status.PASS, "SelectPax with Keyboard", "Start",null);
			
			 Robot robot = new Robot();
			 
			 try {
				 robot.keyPress(KeyEvent.VK_ALT);
		         robot.keyPress(KeyEvent.VK_G);
		         robot.keyRelease(KeyEvent.VK_G);
		         robot.keyRelease(KeyEvent.VK_ALT);
		         Thread.sleep(1000);
		         queryObjects.logStatus(driver, Status.PASS, "Copa Airlines Selected", "Click Top header Pannel",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL	, "Copa Airlines Selected", "Click Top header Pannel",null);
				// TODO: handle exception
			}
			 try {
				 
				 Presstab(robot,3);
				 queryObjects.logStatus(driver, Status.PASS, "Reservation Tab found", "Reached Reservation",null);
				 robot.keyPress(KeyEvent.VK_ENTER);
				 robot.keyRelease(KeyEvent.VK_ENTER);
				 Thread.sleep(1500);
				 
				
				 Presstab(robot,1);
				 robot.keyPress(KeyEvent.VK_ENTER);
				 robot.keyRelease(KeyEvent.VK_ENTER);
				 FlightSearch.loadhandling(driver);
				 queryObjects.logStatus(driver, Status.PASS, "CheckIn Tab found", "Reached Checkin Page",null);
				 robot.keyPress(KeyEvent.VK_ALT);
				 robot.keyPress(KeyEvent.VK_L);
				 robot.keyRelease(KeyEvent.VK_L);
				 robot.keyRelease(KeyEvent.VK_ALT);
				 
				 sFlight= Login.FLIGHTNUM;
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@aria-label='Flight']")).sendKeys(sFlight);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 Thread.sleep(1500);
				 Calendar calend = Calendar.getInstance();
				 String sFromDate="";
				 calend.add(Calendar.DATE, +1);
				 sFromDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@class='md-datepicker-input']")).clear();
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@class='md-datepicker-input']")).sendKeys(sFromDate);
				 System.out.print(sFromDate);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_ENTER);
		         robot.keyRelease(KeyEvent.VK_ENTER);
		         FlightSearch.loadhandling(driver);
		         queryObjects.logStatus(driver, Status.PASS, "CheckIn Page Reached", "Searched with Flight number",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "CheckIn Page Reached", "Searched with Flight number",null);
				// TODO: handle exception
			}
			 try {
				 robot.keyPress(KeyEvent.VK_ALT);
				 robot.keyPress(KeyEvent.VK_SHIFT);
		         robot.keyPress(KeyEvent.VK_T);
		         robot.keyRelease(KeyEvent.VK_T);
		         robot.keyRelease(KeyEvent.VK_SHIFT);
		         robot.keyRelease(KeyEvent.VK_ALT);
		         queryObjects.logStatus(driver, Status.PASS, "Key in navigation and feature bar", "All",null);
		         Presstab(robot,5);
		         robot.keyPress(KeyEvent.VK_ENTER);
		         robot.keyRelease(KeyEvent.VK_ENTER);
		         FlightSearch.loadhandling(driver);
		         if(driver.findElements(By.xpath("//div/span[contains(text(),'No passengers for requested list type')]")).size()>0){
		        	 queryObjects.logStatus(driver, Status.INFO, "No Passgener for the Standby List", "Seats available for all passenger",null);
		         }
		         else
		         queryObjects.logStatus(driver, Status.PASS, "StandBy Passenger list", "List of Standby passenger",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Navigation to StandBy Passenger list fail", "Check script flow for List of Standby passenger",null);
				// TODO: handle exception
			}
		}
		public void BoardingPassenger(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception {
			
			String sFlight;
			String sOrderNum;
			boolean bb;
			WebDriverWait wait = new WebDriverWait(driver, 50);
			queryObjects.logStatus(driver, Status.PASS, "SelectPax with Keyboard", "Start",null);
			
			Robot robot = new Robot();
			try {
				 robot.keyPress(KeyEvent.VK_ALT);
		         robot.keyPress(KeyEvent.VK_SHIFT);
		         robot.keyPress(KeyEvent.VK_C);
		         robot.keyRelease(KeyEvent.VK_C);
		         robot.keyRelease(KeyEvent.VK_SHIFT);
		         robot.keyRelease(KeyEvent.VK_ALT);
		         Thread.sleep(1000);
		         queryObjects.logStatus(driver, Status.PASS, "Top Header Pannel of Screeen", "Acive at Center of Top header Pannel",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Top Header Pannel of Screeen", "Acive at Center of Top header Pannel",null);
				// TODO: handle exception
			}
			try {
				bb = false;
				do{
					 robot.keyPress(KeyEvent.VK_TAB);
					 robot.keyRelease(KeyEvent.VK_TAB);
					 bb = driver.switchTo().activeElement().equals(driver.findElement(By.xpath("//md-menu/button[contains(text(),'Reservation')]")));
				 }while(!bb);
				Thread.sleep(1000);
				queryObjects.logStatus(driver, Status.PASS, "Reservation tab found", "Need to click Reservation",null);
				robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
				Thread.sleep(1500);
				bb = false;
				do{
					 robot.keyPress(KeyEvent.VK_TAB);
					 robot.keyRelease(KeyEvent.VK_TAB);
					 bb = driver.switchTo().activeElement().equals(driver.findElement(By.xpath("//md-menu-item/button[contains(text(),'Check-In')]")));
				 }while(!bb);
				Thread.sleep(1000);
				queryObjects.logStatus(driver, Status.PASS, "Check-In found", "Need to click Check_In",null);
				robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        FlightSearch.loadhandling(driver);
		        queryObjects.logStatus(driver, Status.PASS, "Reached Check-In Page", "Check_In Page",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Unable to reach Check-In Page", "Script Flow for Check_In Page",null);
				// TODO: handle exception
			}
			try {
				queryObjects.logStatus(driver, Status.PASS, "Entering flight details", "Details for created PNR",null);
				 robot.keyPress(KeyEvent.VK_ALT);
				 robot.keyPress(KeyEvent.VK_L);
				 robot.keyRelease(KeyEvent.VK_L);
				 robot.keyRelease(KeyEvent.VK_ALT);





				 
				 sFlight= Login.FLIGHTNUM;
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@aria-label='Flight']")).sendKeys(sFlight);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 Thread.sleep(1500);
				 Calendar calend = Calendar.getInstance();
				 String sFromDate="";
				 calend.add(Calendar.DATE, +1);
				 sFromDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@class='md-datepicker-input']")).clear();
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@class='md-datepicker-input']")).sendKeys(sFromDate);
				 System.out.print(sFromDate);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_ENTER);
		         robot.keyRelease(KeyEvent.VK_ENTER);
		         FlightSearch.loadhandling(driver);
		         queryObjects.logStatus(driver, Status.PASS, "CheckIn Page Reached", "Searched with Flight number",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "CheckIn Page Reached", "Searched with Flight number",null);
				// TODO: handle exception
			}
			try {
				robot.keyPress(KeyEvent.VK_ALT);
		        robot.keyPress(KeyEvent.VK_SHIFT);
		        robot.keyPress(KeyEvent.VK_T);
		        robot.keyRelease(KeyEvent.VK_T);
		        robot.keyRelease(KeyEvent.VK_SHIFT);
		        robot.keyRelease(KeyEvent.VK_ALT);
		        Presstab(robot,7);
				Thread.sleep(500);
				queryObjects.logStatus(driver, Status.PASS, "Unreconciled Tab Reached", "Click Unreconciled",null);
				robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
				FlightSearch.loadhandling(driver);
				if(driver.findElements(By.xpath("//button[contains(text(),'Initiate Boarding')]")).size()>0){
					queryObjects.logStatus(driver, Status.PASS, "Initiate Boarding Started", "Start Boarding",null);
					robot.keyPress(KeyEvent.VK_ENTER);
			        robot.keyRelease(KeyEvent.VK_ENTER);
			        FlightSearch.loadhandling(driver);
			        if(driver.findElements(By.xpath("//div/span[contains(text(),'Function restricted to controlling agents')]")).size()>0){
			        	queryObjects.logStatus(driver, Status.INFO, "Unable to initiate boarding with this agent login", "Login not allowed to do boarding",null);
			        	driver.findElement(By.xpath("//md-select[@aria-label='Flight Actions']")).click();
			        	Thread.sleep(500);
			        	driver.findElement(By.xpath("//md-option/div[contains(text(),'View/Assign Controlling Agents')]")).click();
			        	FlightSearch.loadhandling(driver);	
			        	driver.findElement(By.xpath("//md-dialog//input[@aria-label='agent']")).sendKeys("cm.pty.agent");
			        	Thread.sleep(500);
			        	robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
						robot.keyPress(KeyEvent.VK_ENTER);
				        robot.keyRelease(KeyEvent.VK_ENTER);
				        FlightSearch.loadhandling(driver);
				        driver.findElement(By.xpath("//div/button[contains(text(),'Save')]")).click();
				        FlightSearch.loadhandling(driver);
				        sFlight= Login.FLIGHTNUM;
				        try {
							 robot.keyPress(KeyEvent.VK_ALT);
							 robot.keyPress(KeyEvent.VK_SHIFT);
					         robot.keyPress(KeyEvent.VK_T);
					         robot.keyRelease(KeyEvent.VK_T);
					         robot.keyRelease(KeyEvent.VK_SHIFT);
					         robot.keyRelease(KeyEvent.VK_ALT);
					         queryObjects.logStatus(driver, Status.PASS, "Key in navigation and feature bar", "In All Tab",null);
					         Presstab(robot,7);
					         robot.keyPress(KeyEvent.VK_ENTER);
					         robot.keyRelease(KeyEvent.VK_ENTER);
					         FlightSearch.loadhandling(driver);
						} catch (Exception e) {
							queryObjects.logStatus(driver, Status.FAIL, "Navigation to initiate boarding tab failed", "Check script flow for initiate boarding",null);
							// TODO: handle exception
						}
			        }
			        try {
			        	robot.keyPress(KeyEvent.VK_ALT);
				        robot.keyPress(KeyEvent.VK_C);
				        robot.keyRelease(KeyEvent.VK_C);
				        robot.keyRelease(KeyEvent.VK_ALT);
				        sOrderNum = Login.PNRNUM.trim();
				        bb = false;
						do{
							 robot.keyPress(KeyEvent.VK_TAB);
							 robot.keyRelease(KeyEvent.VK_TAB);
							 bb = driver.switchTo().activeElement().equals(driver.findElement(By.xpath("//div[contains(text(),'"+sOrderNum+"')]")));
						 }while(!bb);
						queryObjects.logStatus(driver, Status.PASS, "Got the Created PNR", "Selecting Specific PNR",null);
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_SPACE);
						robot.keyRelease(KeyEvent.VK_SPACE);
						queryObjects.logStatus(driver, Status.PASS, "Selected the PNR", "Selected Specific PNR",null);
						robot.keyPress(KeyEvent.VK_ALT);
						robot.keyPress(KeyEvent.VK_SHIFT);
				        robot.keyPress(KeyEvent.VK_B);
				        robot.keyRelease(KeyEvent.VK_B);
				        robot.keyRelease(KeyEvent.VK_SHIFT);
				        robot.keyRelease(KeyEvent.VK_ALT);
				        Presstab(robot,5);
				        robot.keyPress(KeyEvent.VK_ENTER);
				        robot.keyRelease(KeyEvent.VK_ENTER);
				        FlightSearch.loadhandling(driver);
				        queryObjects.logStatus(driver, Status.PASS, "Reconcilation Done", "Reconcilation Done for Specific PNR",null);
					} catch (Exception e) {
						queryObjects.logStatus(driver, Status.INFO, "Reconcilation Done", "Reconcilation Done Specific PNR not available",null);
						// TODO: handle exception
					}
				}
				else{
					queryObjects.logStatus(driver, Status.FAIL, "Initiate Boarding Not working", "Check Script flow for initiate boarding ",null);
				}
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Unreconcilation Not working", "Check Script flow for Unreconcilation",null);
				// TODO: handle exception
			}
		}
		public void Closeflight(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception {
			
			String sFlight;
			String sOrderNum;
			boolean bb;
			WebDriverWait wait = new WebDriverWait(driver, 50);
			queryObjects.logStatus(driver, Status.PASS, "CloseFlight with Keyboard", "Start",null);
			Robot robot = new Robot();
			try {
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_G);
				robot.keyRelease(KeyEvent.VK_G);
				robot.keyRelease(KeyEvent.VK_ALT);
				queryObjects.logStatus(driver, Status.PASS, "Copa Airlines Selected", "Click Top header Pannel",null);
				Thread.sleep(500);
				Presstab(robot,3);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        Thread.sleep(500);
		        robot.keyPress(KeyEvent.VK_DOWN	);
		        robot.keyRelease(KeyEvent.VK_DOWN);
		        robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        FlightSearch.loadhandling(driver);
		        queryObjects.logStatus(driver, Status.PASS, "Reached CheckIn Page", "@ CheckIn page",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Reached CheckIn Page", "@ CheckIn page",null);
				// TODO: handle exception
			}
			try {
				 queryObjects.logStatus(driver, Status.PASS, "Entering flight details", "Details for created PNR",null);
				 robot.keyPress(KeyEvent.VK_ALT);
				 robot.keyPress(KeyEvent.VK_L);
				 robot.keyRelease(KeyEvent.VK_L);
				 robot.keyRelease(KeyEvent.VK_ALT);
				 sFlight= Login.FLIGHTNUM;
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@aria-label='Flight']")).sendKeys(sFlight);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 Thread.sleep(1500);
				 Calendar calend = Calendar.getInstance();
				 String sFromDate="";
				 calend.add(Calendar.DATE, +0);
				 sFromDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@class='md-datepicker-input']")).clear();
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@class='md-datepicker-input']")).sendKeys(sFromDate);
				 System.out.print(sFromDate);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_ENTER);
		         robot.keyRelease(KeyEvent.VK_ENTER);
		         FlightSearch.loadhandling(driver);
		         queryObjects.logStatus(driver, Status.PASS, "CheckIn Page Reached", "Searched with Flight number",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "CheckIn Page Reached", "Searched with Flight number",null);
				// TODO: handle exception
			}
			try {
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_H);
				robot.keyRelease(KeyEvent.VK_H);
				robot.keyRelease(KeyEvent.VK_ALT);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        Thread.sleep(500);
		        robot.keyPress(KeyEvent.VK_DOWN);
		        robot.keyRelease(KeyEvent.VK_DOWN);
		        robot.keyPress(KeyEvent.VK_DOWN);
		        robot.keyRelease(KeyEvent.VK_DOWN);
		        robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        FlightSearch.loadhandling(driver);
		        Presstab(robot,2);
		        robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        Thread.sleep(500);
		        queryObjects.logStatus(driver, Status.PASS, "Closed the Flight", "Closing specific page",null);
		        FlightSearch.loadhandling(driver);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Closed the Flight", "Closing specific page",null);
				// TODO: handle exception
			}
		}
		public void FlightSummary(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception {
			
			String sFlight;
			String sOrderNum;
			boolean bb;
			WebDriverWait wait = new WebDriverWait(driver, 50);
			queryObjects.logStatus(driver, Status.PASS, "Flight summary with Keyboard", "Start",null);
			Robot robot = new Robot();
			try {
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_G);
				robot.keyRelease(KeyEvent.VK_G);
				robot.keyRelease(KeyEvent.VK_ALT);
				queryObjects.logStatus(driver, Status.PASS, "Copa Airlines Selected", "Click Top header Pannel",null);
				Thread.sleep(500);
				Presstab(robot,3);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        Thread.sleep(500);
		        robot.keyPress(KeyEvent.VK_DOWN	);
		        robot.keyRelease(KeyEvent.VK_DOWN);
		        robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        FlightSearch.loadhandling(driver);
		        queryObjects.logStatus(driver, Status.PASS, "Reached CheckIn Page", "@ CheckIn page",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Reached CheckIn Page", "@ CheckIn page",null);
				// TODO: handle exception
			}
			try {
				String origin;
				origin = FlightSearch.getTrimTdata(queryObjects.getTestData("Origin"));
				//origin=FlightSearch.getTrimTdata("Origin");
				 queryObjects.logStatus(driver, Status.PASS, "Entering flight details", "Details for created PNR",null);
				 robot.keyPress(KeyEvent.VK_ALT);
				 robot.keyPress(KeyEvent.VK_L);
				 robot.keyRelease(KeyEvent.VK_L);
				 robot.keyRelease(KeyEvent.VK_ALT);
				 sFlight= Login.FLIGHTNUM;
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@aria-label='Flight']")).sendKeys(sFlight);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 Thread.sleep(1500);
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@aria-label='From']")).clear();
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@aria-label='From']")).sendKeys(origin);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 Thread.sleep(1500);
				 Calendar calend = Calendar.getInstance();
				 String sFromDate="";
				 calend.add(Calendar.DATE, +0);
				 sFromDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@class='md-datepicker-input']")).clear();
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@class='md-datepicker-input']")).sendKeys(sFromDate);
				 System.out.print(sFromDate);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_ENTER);
		         robot.keyRelease(KeyEvent.VK_ENTER);
		         FlightSearch.loadhandling(driver);
		         queryObjects.logStatus(driver, Status.PASS, "CheckIn Page Reached", "Searched with Flight number",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "CheckIn Page Reached", "Searched with Flight number",null);
				// TODO: handle exception
			}
			try {
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_H);
				robot.keyRelease(KeyEvent.VK_H);
				robot.keyRelease(KeyEvent.VK_ALT);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        FlightSearch.loadhandling(driver);
		        queryObjects.logStatus(driver, Status.PASS, "Flight summary details", "specific flight summary details page",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Flight summary details", "specific flight summary details page",null);
				// TODO: handle exception
			}
		}
		//All Booked Standby Pax
		public void Standby(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception {
			
			String sFlight;
			String sOrderNum;
			boolean bb;
			WebDriverWait wait = new WebDriverWait(driver, 50);
			queryObjects.logStatus(driver, Status.PASS, "Flight summary with Keyboard", "Start",null);
			Robot robot = new Robot();
			try {
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_G);
				robot.keyRelease(KeyEvent.VK_G);
				robot.keyRelease(KeyEvent.VK_ALT);
				queryObjects.logStatus(driver, Status.PASS, "Copa Airlines Selected", "Click Top header Pannel",null);
				Thread.sleep(500);
				Presstab(robot,3);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        Thread.sleep(500);
		        robot.keyPress(KeyEvent.VK_DOWN	);
		        robot.keyRelease(KeyEvent.VK_DOWN);
		        robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        FlightSearch.loadhandling(driver);
		        queryObjects.logStatus(driver, Status.PASS, "Reached CheckIn Page", "@ CheckIn page",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Reached CheckIn Page", "@ CheckIn page",null);
				// TODO: handle exception
			}
			try {
				String origin;
				origin = FlightSearch.getTrimTdata(queryObjects.getTestData("Origin"));
				 queryObjects.logStatus(driver, Status.PASS, "Entering flight details", "Details for created PNR",null);
				 robot.keyPress(KeyEvent.VK_ALT);
				 robot.keyPress(KeyEvent.VK_L);
				 robot.keyRelease(KeyEvent.VK_L);
				 robot.keyRelease(KeyEvent.VK_ALT);
				 sFlight= Login.FLIGHTNUM;
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@aria-label='Flight']")).sendKeys(sFlight);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 Thread.sleep(1500);
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@aria-label='From']")).clear();
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@aria-label='From']")).sendKeys(origin);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 Thread.sleep(1500);
				 Calendar calend = Calendar.getInstance();
				 String sFromDate="";
				 calend.add(Calendar.DATE, +0);
				 sFromDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@class='md-datepicker-input']")).clear();
				 driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@class='md-datepicker-input']")).sendKeys(sFromDate);
				 System.out.print(sFromDate);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_TAB);
				 robot.keyRelease(KeyEvent.VK_TAB);
				 Thread.sleep(1500);
				 robot.keyPress(KeyEvent.VK_ENTER);
		         robot.keyRelease(KeyEvent.VK_ENTER);
		         FlightSearch.loadhandling(driver);
		         queryObjects.logStatus(driver, Status.PASS, "CheckIn Page Reached", "Searched with Flight number",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "CheckIn Page Reached", "Searched with Flight number",null);
				// TODO: handle exception
			}
			try {
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_T);
				robot.keyRelease(KeyEvent.VK_T);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				robot.keyRelease(KeyEvent.VK_ALT);
				Presstab(robot,5);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        FlightSearch.loadhandling(driver);
		        
		        if(driver.findElements(By.xpath("//md-menu-content//div[contains(text(),'No passengers for requested list type')]")).size()>0){
		        	queryObjects.logStatus(driver, Status.INFO, "No passenger for Standby List", "No Standby passenger",null);
		        }
		        robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_B);
				robot.keyRelease(KeyEvent.VK_B);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				robot.keyRelease(KeyEvent.VK_ALT);
				Presstab(robot,2);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        FlightSearch.loadhandling(driver);
		        queryObjects.logStatus(driver, Status.PASS, "Flight Standby initaiated", "specific flight Standby details",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Flight summary details", "specific flight summary details page",null);
				// TODO: handle exception


			}

		}
		public void TabValidate(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception{
			boolean bb;
			WebDriverWait wait = new WebDriverWait(driver, 50);
			WebElement activeElement = driver.switchTo().activeElement();
			String cName =  activeElement.getAttribute("ng-model");
			queryObjects.logStatus(driver, Status.PASS, "Flight summary with Keyboard", "Start",null);
			Robot robot = new Robot();
			try {
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_C);
				robot.keyRelease(KeyEvent.VK_C);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				robot.keyRelease(KeyEvent.VK_ALT);
				Thread.sleep(1000);
				queryObjects.logStatus(driver, Status.PASS, "Top Header Pannel of Screeen", "Acive at Center of Top header Pannel",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Top Header Pannel of Screeen", "Acive at Center of Top header Pannel",null);
				// TODO: handle exception
			}
			try {
				Presstab(robot,2);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_DOWN	);
				robot.keyRelease(KeyEvent.VK_DOWN);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				FlightSearch.loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Checkin Page Reached", "Validate the TAB Keys",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Checkin Page Reached", "Validate the TAB Keys",null);
				// TODO: handle exception
			}
			try {
				bb=true;
				do{
					if(driver.switchTo().activeElement().equals(driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@aria-label='Flight']")))){
						queryObjects.logStatus(driver, Status.PASS, "Pointed to Flight Search Flight TAB", "Currentyl pointing to flight input",null);
						bb=false;
					}
					else{
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
					}
				}while(bb);
				queryObjects.logStatus(driver, Status.PASS, "Tab key pressed till finding the flight search", "Left to Right ",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Tab key not pressed till finding the flight search", "Left to Right ",null);
				// TODO: handle exception
			}
			try {
				bb=true;
				do{
					if(driver.switchTo().activeElement().equals(driver.findElement(By.xpath("//form[@name='passengerSearch.form']//button[@aria-label='Proceed to checkin']")))){
						queryObjects.logStatus(driver, Status.PASS, "Pointed to Proceed to Checkin TAB", "Currently pointing to proceed to Checkin Button",null);
						bb=false;
					}
					else{
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
					}
				}while(bb);
				queryObjects.logStatus(driver, Status.PASS, "Tab key pressed till finding the proceed to Checkin Button", "Left to Right ",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Tab key pressed till finding the proceed to Checkin Button", "Left to Right ",null);
				// TODO: handle exception
			}
			try {
				bb=true;
				do{
					if(driver.switchTo().activeElement().equals(driver.findElement(By.xpath("//form[@name='flightSearch.form']//input[@aria-label='Flight']")))){
						queryObjects.logStatus(driver, Status.PASS, "Pointed to Flight Search Flight TAB", "Currently pointing to flight input",null);
						bb=false;
					}
					else{
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_SHIFT);
					}
				}while(bb);
				queryObjects.logStatus(driver, Status.PASS, "Tab key pressed till finding the flight search", "Right to left",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Tab key not pressed till finding the flight search", "Right to left ",null);
				// TODO: handle exception
			}
		}
		public void HeaderValidate(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception{
			boolean bb;
			WebDriverWait wait = new WebDriverWait(driver, 50);
			WebElement activeElement = driver.switchTo().activeElement();
			String cName =  activeElement.getAttribute("ng-model");
			queryObjects.logStatus(driver, Status.PASS, "Header pannel validation with TAB", "Top Header Pannel hot keys Validation",null);
			Robot robot = new Robot();
			try {
				bb=true;
				do{
					if(driver.findElements(By.xpath("//div[contains(text(),'Change Sales Office & Currency')]")).size()>0){
						Thread.sleep(5000);
						queryObjects.logStatus(driver, Status.PASS, "SalesOffice and Currency Change Icon", "Currently pointing to Change SalesOffice and Currency Change window",null);
						robot.keyPress(KeyEvent.VK_ESCAPE);
						robot.keyRelease(KeyEvent.VK_ESCAPE);
						bb=false;
					}
					else{
						robot.keyPress(KeyEvent.VK_ALT);
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_C);
						robot.keyRelease(KeyEvent.VK_C);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						robot.keyRelease(KeyEvent.VK_ALT);
						robot.keyPress(KeyEvent.VK_ENTER);
						robot.keyRelease(KeyEvent.VK_ENTER);
						Thread.sleep(5000);
					}
				}while(bb);
				queryObjects.logStatus(driver, Status.PASS, "ATL+SHIFT+C", "SalesOffice and Currency Change Icon",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "ATL+SHIFT+C", "SalesOffice and Currency Change Icon",null);
				// TODO: handle exception
			}
			try {
				bb=true;
				do{
					if(driver.findElements(By.xpath("//div[contains(text(),'User Profile') or contains(text(),'My Personal Information') or contains(text(),'My Preferences')]")).size()>0){
						Thread.sleep(5000);
						queryObjects.logStatus(driver, Status.PASS, "Setting Screen for Specific SalesOffice", "Setting screen for User Profile	",null);
						driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
						FlightSearch.loadhandling(driver);
						bb=false;
					}
					else{
						robot.keyPress(KeyEvent.VK_ALT);
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_S);
						robot.keyRelease(KeyEvent.VK_S);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						robot.keyRelease(KeyEvent.VK_ALT);
						Thread.sleep(5000);
					}
				}while(bb);
				queryObjects.logStatus(driver, Status.PASS, "ATL+SHIFT+S", "Setting Screen for Specific SalesOffice",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "ATL+SHIFT+S", "Setting Screen for Specific SalesOffice",null);
				// TODO: handle exception
			}
			try {
				bb=true;
				do{
					if(driver.findElements(By.xpath("//div[contains(text(),'Direct Reference System')]")).size()>0){
						Thread.sleep(5000);
						queryObjects.logStatus(driver, Status.PASS, "Display Direct Reference System for Help", "Direct Reference System",null);
						robot.keyPress(KeyEvent.VK_ESCAPE);
						robot.keyRelease(KeyEvent.VK_ESCAPE);
						Thread.sleep(1000);
						bb=false;
					}
					else{
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_F1);
						robot.keyRelease(KeyEvent.VK_F1);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_ENTER);
						robot.keyRelease(KeyEvent.VK_ENTER);
						Thread.sleep(5000);
					}
				}while(bb);
				queryObjects.logStatus(driver, Status.PASS, "SHIFT+F1 then ENTER", "Display Direct Reference System for Help",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "SHIFT+F1 then ENTER", "Display Direct Reference System for Help",null);
				// TODO: handle exception
			}
			try {
				bb=true;
				do{
					if(driver.findElements(By.xpath("//div[contains(text(),'Calculator') or contains(text(),'Converter') or contains(text(),'Fare Basis Information')]")).size()>0){
						Thread.sleep(5000);
						queryObjects.logStatus(driver, Status.PASS, "Display Tools Menu", "Tools Menu is Displayed",null);
						Thread.sleep(1000);
						robot.keyPress(KeyEvent.VK_ESCAPE);
						robot.keyRelease(KeyEvent.VK_ESCAPE);
						bb=false;
					}
					else{
						Thread.sleep(1000);
						robot.keyPress(KeyEvent.VK_ALT);
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_W);
						robot.keyRelease(KeyEvent.VK_W);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						robot.keyRelease(KeyEvent.VK_ALT);
						robot.keyPress(KeyEvent.VK_ENTER);
						robot.keyRelease(KeyEvent.VK_ENTER);
						Thread.sleep(5000);
					}
				}while(bb);
				queryObjects.logStatus(driver, Status.PASS, "ALT+SHIFT+W then ENTER", "Display Tools Menu",null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "ALT+SHIFT+W then ENTER", "Display Tools Menu",null);
				// TODO: handle exception
			}
		}
}

