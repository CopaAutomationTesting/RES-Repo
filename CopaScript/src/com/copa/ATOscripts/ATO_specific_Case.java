package com.copa.ATOscripts;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.RESscripts.FlightSearch;
import com.copa.Util.ATOflowPageObjects;

import FrameworkCode.BFrameworkQueryObjects;

public class ATO_specific_Case extends ATOflowPageObjects {
	
	static WebElement  currentElement;
	
	//Robot robot = new Robot();
	
	public void ATO_REG_126_Keyboard(WebDriver driver, BFrameworkQueryObjects queryObjects) throws AWTException, IOException, InterruptedException
	{
		
		///Alt + Shift + C keys to be at the top header panel of the screen.
		WebDriverWait wait = new WebDriverWait(driver, 50);
		try
		{
		
		FlightSearch.loadhandling(driver);
		
		 Robot robot = new Robot();
         robot.keyPress(KeyEvent.VK_ALT);
         robot.keyPress(KeyEvent.VK_SHIFT);
         robot.keyPress(KeyEvent.VK_C);
         robot.keyRelease(KeyEvent.VK_C);
         robot.keyRelease(KeyEvent.VK_SHIFT);
         robot.keyRelease(KeyEvent.VK_ALT);
        
         
         
       ///Expected Output  Pinpoint an orange square around the SALES OFFICE & CURRENCY icon.
         
         WebElement Salesoffice=driver.findElement(By.xpath("//div[@action='saleOfficeInfo']/div[@class='padding-top']/i"));
         
          currentElement =driver.switchTo().activeElement();
          
          queryObjects.logStatus(driver, Status.INFO, Salesoffice.toString(),currentElement.toString() , null);
          
         if(Salesoffice.equals(currentElement))
         {
        	 queryObjects.logStatus(driver, Status.PASS, "Checking sales office is highlighed","sales office highlighted" , null); 
         }
         else
         {
        	 queryObjects.logStatus(driver, Status.FAIL, "Checking sales office is highlighed", "sales office not highlighted", null);
         }
         
		
		
		///// Main Gate GUI page has no Alt + L hot key as ther is no left panel on the Main Gate Gui Landing page.
		
		driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
		 Thread.sleep(2000);
		 driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Gate')]")).click();
		 FlightSearch.loadhandling(driver);
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Flight Search')]")));
		 queryObjects.logStatus(driver, Status.PASS, "Search Passenger", "Flight Passenger displayed ",null);
		 
		 
		 robot.keyPress(KeyEvent.VK_ALT);
         
         robot.keyPress(KeyEvent.VK_L);
         robot.keyRelease(KeyEvent.VK_L);
         robot.keyRelease(KeyEvent.VK_ALT);
        
		 
		  
		 
		
		
		
		/////Expected Result 2: Pinpoint a red line in flight field to search flight.
		
         WebElement seachField=driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input"));
         
         currentElement =driver.switchTo().activeElement();
         
         queryObjects.logStatus(driver, Status.INFO, seachField.toString(),currentElement.toString() , null);
         if(seachField.equals(currentElement))
         {
        	 queryObjects.logStatus(driver, Status.PASS, "Checking flight field is highlighed","flight field highlighted" , null); 
         }
         else
         {
        	 queryObjects.logStatus(driver, Status.FAIL, "Checking flight field is highlighed", "flight field not highlighted", null);
         }
         
         
         
        //// Press ALT+R keys to be at the right panel in the passenger list screen.
         
         driver.findElement(By.xpath("//md-content//table[@ng-table='flightDepartArrival.tableParams']//tr[contains(@ng-click,'flightDepartArrival')][1]/td[1]")).click();
         FlightSearch.loadhandling(driver);
         Thread.sleep(2000);
         driver.findElement(By.xpath("//div[div[@translate='pssgui.all']]")).click();
         FlightSearch.loadhandling(driver);
         
       //  WebElement RESTRICTICON=driver.findElement(By.xpath("//div[contains(@ng-click,'airportPanel.restrictDisabled')]"));


         
         robot.keyPress(KeyEvent.VK_ALT);
         
         robot.keyPress(KeyEvent.VK_R);
         robot.keyRelease(KeyEvent.VK_R);
         robot.keyRelease(KeyEvent.VK_ALT);
         
         
         
         /* robot.keyPress(KeyEvent.VK_ALT);
         
         robot.keyPress(KeyEvent.VK_R);
        
         robot.keyRelease(KeyEvent.VK_ALT);
         robot.keyRelease(KeyEvent.VK_R);*/
         ////Expected Result 3: Pinpoint an orange square around the RESTRICT icon.
         
         
         Thread.sleep(1000);
       
        Thread.sleep(1000);
         currentElement =driver.switchTo().activeElement();
         WebElement RESTRICTICON=driver.findElement(By.xpath("//div[contains(@ng-click,'airportPanel.restrictDisabled')]"));
         Thread.sleep(1000);
         queryObjects.logStatus(driver, Status.INFO, RESTRICTICON.toString(),currentElement.toString() , null);
         Thread.sleep(1000);
         FlightSearch.loadhandling(driver);
         if(currentElement.equals(driver.findElement(By.xpath("//div[contains(@ng-click,'airportPanel.restrictDisabled')]"))))
         {
        	 queryObjects.logStatus(driver, Status.PASS, "Checking RESTRICTICON is highlighed","RESTRICTICON highlighted" , null); 
         }
         else
         {
        	 queryObjects.logStatus(driver, Status.FAIL, "Checking RESTRICTICON is highlighed", "RESTRICTICON not highlighted", null);
         }
         
       /////  Press ALT+SHIFT+T keys to be at the All navigation and features bar in the passenger list Tab screen.
         
         FlightSearch.loadhandling(driver);
         Thread.sleep(1000);
         robot.keyPress(KeyEvent.VK_ALT);
         robot.keyPress(KeyEvent.VK_SHIFT);
         robot.keyPress(KeyEvent.VK_T);
         robot.keyRelease(KeyEvent.VK_T);
         robot.keyRelease(KeyEvent.VK_SHIFT);
         robot.keyRelease(KeyEvent.VK_ALT);
         
          
         
         
        //// Expected Result 4: Pinpoint an orange square around ALL tab in the navigation and features bar.
         
         WebElement AllTabIcon=driver.findElement(By.xpath("//div[div[@translate='pssgui.all']]"));
         
         currentElement =driver.switchTo().activeElement();
         
         queryObjects.logStatus(driver, Status.INFO, AllTabIcon.toString(),currentElement.toString() , null);
         Thread.sleep(1000);
         if(AllTabIcon.equals(currentElement))
         {
        	 queryObjects.logStatus(driver, Status.PASS, "Checking AllTabIcon is highlighed","AllTabIcon highlighted" , null); 
         }
         else
         {
        	 queryObjects.logStatus(driver, Status.FAIL, "Checking AllTabIcon is highlighed", "AllTabIcon not highlighted", null);
         }
		
         
         /////Press ALT+SHIFT+B keys to be at the button area beneath the navigation and features bar.
         FlightSearch.loadhandling(driver);
         driver.findElement(By.xpath("//div[contains(@ng-repeat,'passengerItinerary')][1]/child::div/div[1]/div[2]/i[contains(@class,'in-active-state')]/parent::div/preceding-sibling::md-checkbox/div[1]")).click();
         Thread.sleep(3000);
         FlightSearch.loadhandling(driver);
         Thread.sleep(1000);
         robot.keyPress(KeyEvent.VK_ALT);
         robot.keyPress(KeyEvent.VK_SHIFT);
         robot.keyPress(KeyEvent.VK_B);
         robot.keyRelease(KeyEvent.VK_B); 
         robot.keyRelease(KeyEvent.VK_SHIFT);
         robot.keyRelease(KeyEvent.VK_ALT);
         
        
         FlightSearch.loadhandling(driver);
         
         /////Expected Result 5: Pinpoint an orange square around 'Off Load button'.
         
    WebElement OFFLOAD=driver.findElement(By.xpath("//button[contains(text(),'Off Load')]"));
         
         currentElement =driver.switchTo().activeElement();
         
         queryObjects.logStatus(driver, Status.INFO, AllTabIcon.toString(),currentElement.toString() , null);
         Thread.sleep(1000);
         if(OFFLOAD.equals(currentElement))
         {
        	 queryObjects.logStatus(driver, Status.PASS, "Checking OFFLOAD is highlighed","OFFLOAD highlighted" , null); 
         }
         else
         {
        	 queryObjects.logStatus(driver, Status.FAIL, "Checking OFFLOAD is highlighed", "OFFLOAD not highlighted", null);
         }
         /////Press ALT+H keys to be at the Home Icon on the gate landing page.
        
         driver.findElement(By.xpath("//div[contains(@ng-repeat,'passengerItinerary')][1]/child::div/div[1]/div[2]/i[contains(@class,'in-active-state')]/parent::div/preceding-sibling::md-checkbox/div[1]")).click();
         Thread.sleep(3000);
         FlightSearch.loadhandling(driver);
         Thread.sleep(1000);
         robot.keyPress(KeyEvent.VK_ALT);
         robot.keyPress(KeyEvent.VK_H);
       
         robot.keyRelease(KeyEvent.VK_H);
         robot.keyRelease(KeyEvent.VK_ALT);
         
         
         /////Expected Result 6: Pinpoint an orange square around HOME icon.
         
       WebElement Home=driver.findElement(By.xpath("//div[div[@translate='pssgui.home']]"));
         
         currentElement =driver.switchTo().activeElement();
         
         queryObjects.logStatus(driver, Status.INFO, Home.toString(),currentElement.toString() , null);
         Thread.sleep(1000);
         if(Home.equals(currentElement))
         {
        	 queryObjects.logStatus(driver, Status.PASS, "Checking HOME is highlighed","HOME highlighted" , null); 
         }
         else
         {
        	 queryObjects.logStatus(driver, Status.FAIL, "Checking HOME is highlighed", "HOME not highlighted", null);
         }
         
       ///  Press ALT+B keys to return to the Back Icon,previous page.
         
      
         
         
         
         Thread.sleep(1000);
         robot.keyPress(KeyEvent.VK_ALT);
         robot.keyPress(KeyEvent.VK_B);
         robot.keyRelease(KeyEvent.VK_B); 
         robot.keyRelease(KeyEvent.VK_ALT);
         
         ////Expected Result 7: Pinpoint an orange square around BACK icon.
         
 WebElement Back=driver.findElement(By.xpath("//div[div[@translate='pssgui.back']]"));
         
         currentElement =driver.switchTo().activeElement();
         
         queryObjects.logStatus(driver, Status.INFO, Back.toString(),currentElement.toString() , null);
         Thread.sleep(1000);
         if(Back.equals(currentElement))
         {
        	 queryObjects.logStatus(driver, Status.PASS, "Checking BACK is highlighed","BACK highlighted" , null); 
         }
         else
         {
        	 queryObjects.logStatus(driver, Status.FAIL, "Checking BACK is highlighed", "BACK not highlighted", null);
         }
		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.FAIL, "Checking keyboard functionality", "Getting error while checking the keyboard functiolnality", e);
		}
		
		
		
		
		
		
		
		
		
		
	}

}
