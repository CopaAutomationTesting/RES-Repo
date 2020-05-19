package com.copa.RESscripts;

import java.io.IOException;
import java.rmi.server.LoaderHandler;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.Util.FlightSearchPageObjects;

import FrameworkCode.BFrameworkQueryObjects;

public class TicketTab  extends FlightSearchPageObjects {
	public static String pnrNum = null;
  public static void displayPnr(WebDriver driver, BFrameworkQueryObjects queryObjects,String tkt) throws IOException
  {
	 
		boolean contflag=false;
		if(tkt.equals("")) 
			queryObjects.logStatus(driver, Status.FAIL, "PNR was not generated", "PNR was not generated" , null);
		else
			contflag=true;	
		if(contflag) 
		{
			try {
				
				driver.findElement(By.xpath("//span[text()='Home']")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[div[text()='Search'] and @role='button']")).click();
				Thread.sleep(2000);
				//Entering PNR number in search input
				driver.findElement(By.xpath("//input[@aria-label='Search']")).click();
				driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(tkt);

				//Clicking on Search button
				driver.findElement(By.xpath("//div[contains(@class,'itinerary-search')]//button[contains(text(),'Search')]")).click();
				//String Pnris=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@action='pnr']/div/div/div[1]/div[1]")).getText());
				//Wait until loading wrapper closed
				FlightSearch.loadhandling(driver);
				
				queryObjects.logStatus(driver, Status.PASS, "Search PNR", "Search PNR successful" , null);
			}
			catch(Exception e) {
				contflag = false;
				queryObjects.logStatus(driver, Status.FAIL, "Search PNR", "Search PNR failed: " + e.getLocalizedMessage() , e);
			}
		} 
	  
  }
	
	public void SentEmail(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException
	{
		try
		{
			
			pnrNum =Login.PNRNUM.trim();
		TicketTab.displayPnr(driver, queryObjects,pnrNum);
		
		driver.findElement(By.xpath(TicketXpath)).click();
		
		driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'selectAll')]")).click();
		
		
		driver.findElement(By.xpath("//button[@translate='pssgui.email.ticket' and not (@disabled='disabled')]")).click();
		
		FlightSearch.emailhandling(driver,queryObjects);
		
		if(driver.findElements(By.xpath("//md-menu//i[contains(@ng-class,'icon-warning')]/following-sibling::span[contains(text(),'E-Mail has been sent.')]")).size()>0)
		{
			queryObjects.logStatus(driver, Status.PASS, "Checking Email to be sent", "Email sent successfully ", null);
		}
		else
		{
			queryObjects.logStatus(driver, Status.FAIL, "Checking Email to be sent", "Email has not sent successfully ", null);
		}
		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.FAIL, "Checking ticket tab", "Failed on ticket tab ", e);
		}
		
		
	}
	public static void detachtkt(WebDriver driver, BFrameworkQueryObjects queryObjects,String PNR) throws IOException
	{    try
		{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.open()");
		
		String maintab=driver.getWindowHandle();
		
		
		String mostRecentWindowHandle="";
		for(String winHandle:driver.getWindowHandles()){
	        mostRecentWindowHandle = winHandle;        
	    }
		driver.switchTo().window(mostRecentWindowHandle);
		
		driver.get("http://tpfsb.intvm.sys.eds.com");
		//driver.navigate().to("http://tpfsb.intvm.sys.eds.com");
		driver.findElement(By.xpath("//input[@name='ID']")).sendKeys("wzqltv");   // I share User name
		driver.findElement(By.name("Password")).sendKeys("prayer08");    // I share psw
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Terminal Emulation')]")));
		driver.findElement(By.xpath("//a[contains(text(),'Terminal Emulation')]")).click();
		
		//driver.switchTo().window(maintab);
		//driver.findElement(By.name("USER")).sendKeys("suman");
		
		WebDriverWait wait = new WebDriverWait(driver, 100);
		
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			
			driver.findElement(By.name("q")).sendKeys("logc cmre");
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			driver.findElement(By.name("q")).sendKeys("bsib");
			String IshLogMsg = (driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText()).trim();
		
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			
			driver.findElement(By.name("q")).sendKeys("logc cmre");
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			
			driver.findElement(By.name("q")).sendKeys("*"+pnrNum);
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			
			driver.findElement(By.name("q")).sendKeys("T-ETDETACH1");
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			
			String output=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
			if(output.equalsIgnoreCase("-OK-"))
			{
				queryObjects.logStatus(driver, Status.PASS, "Ticket detach checking", "ticket detach successfull", null);
			}
			else
			{
				queryObjects.logStatus(driver, Status.FAIL, "Ticket detach checking", "ticket detach not successfull ", null);
			}
			
			driver.close();
			driver.switchTo().window(maintab);
		}
			catch(Exception e)
			{
				queryObjects.logStatus(driver, Status.FAIL, "Ticket detach checking", "ticket detach not successfull ", e);
			}
	}
	
	public void WithoutPnrTicket(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException
	{
		try
		{
		pnrNum =Login.PNRNUM.trim();
		//TicketTab.displayPnr(driver, queryObjects,pnrNum);
		
		List<String> tkt=new ArrayList<String>();
		tkt.addAll(FlightSearch.gettecketno);
		
	String	tktNum=tkt.get(0);
		
		TicketTab.detachtkt(driver, queryObjects,pnrNum);
		
		queryObjects.logStatus(driver, Status.INFO, "Ticket detach checking", "ticket detach successfull", null);
		
		TicketTab.displayPnr(driver, queryObjects,tktNum);
		
		
		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.FAIL, "Ticket detach checking", "ticket detach is not successfull", null);
		}
	}
	
	public void Synctkt(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException
	{
		try
		{
		pnrNum =Login.PNRNUM.trim();
		//TicketTab.displayPnr(driver, queryObjects,pnrNum);
		
		List<String> tkt=new ArrayList<String>();
		tkt.addAll(FlightSearch.gettecketno);
	
		String	tktNum=tkt.get(0);
		TicketTab.displayPnr(driver, queryObjects,pnrNum);
		
		if(driver.findElements(By.xpath("//md-menu//i[contains(@ng-class,'icon-warning')]/following-sibling::span[contains(text(),'Ticket is out of sync with booked itinerary')]")).size()>0)
		{
			queryObjects.logStatus(driver, Status.PASS, "Checking ticket out of sync", "ticket is out of sync", null);
		}
		else
		{
			queryObjects.logStatus(driver, Status.FAIL, "Checking ticket out of sync", "ticket is not out of sync", null);
		}
        driver.findElement(By.xpath(TicketXpath)).click();
		
		//driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'selectAll')]")).click();
		FlightSearch.loadhandling(driver);
		try
		{
			///for sync ticket need to implement it is hold
			driver.findElement(By.xpath("//md-checkbox[contains(@class,'passenger-checkbox')]")).click();

			driver.findElement(By.xpath("//md-select-value/span[@class='md-select-icon']")).click();
			driver.findElement(By.xpath("//div[contains(text(),'Sync Ticket')]")).click();
			FlightSearch.loadhandling(driver);

			
			driver.findElement(By.xpath("//md-radio-group/md-radio-button[@value='false']/div[1]")).click();
			driver.findElement(By.xpath("//md-radio-group/md-radio-button[@value='CLASS']/div[1]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//button[contains(text(),'Sync')]")).click();
			FlightSearch.loadhandling(driver);
			Boolean b=false;
			String  Errormsg="";
			int size=0;
			try
			{
			 Errormsg=driver.findElement(By.xpath("//div[div[contains(text(),'Sync Tickets')]]//following-sibling::pssgui-error-panel//span[contains(text(),'Unable to sync the ticket in its current state')]")).getText();
			}
			catch(Exception e){}
			if(Errormsg.equalsIgnoreCase("Unable to sync the ticket in its current state"))
			{
				driver.findElement(By.xpath("//md-radio-group/md-radio-button[@value='ITINERARY']/div[1]")).click();
				driver.findElement(By.xpath("//button[contains(text(),'Sync')]")).click();
				b=true;
				FlightSearch.loadhandling(driver);
				size=driver.findElements(By.xpath("//div[div[contains(text(),'Sync Tickets')]]//following-sibling::pssgui-error-panel//span[contains(text(),'Unable to sync the ticket in its current state')]")).size();
				try
				{
				 Errormsg=driver.findElement(By.xpath("//div[div[contains(text(),'Sync Tickets')]]//following-sibling::pssgui-error-panel//span[contains(text(),'Unable to sync the ticket in its current state')]")).getText();
				}
				catch(Exception e){}
			}
		    if(Errormsg.equalsIgnoreCase("Unable to sync the ticket in its current state") && b && size>0)
			{
				driver.findElement(By.xpath("//md-radio-group/md-radio-button[@value='PASSENGER']/div[1]")).click();
				driver.findElement(By.xpath("//button[contains(text(),'Sync')]")).click();
			}
			
			List<WebElement> reissueticketnumbers=driver.findElements(By.xpath("//span[contains(@class,'primary-ticket-number')]"));
			
			for (WebElement ele : reissueticketnumbers) {
				String ticketnumber=ele.getText().trim();
				List<WebElement> currentticketstate=driver.findElements(By.xpath("//span[text()='"+ticketnumber+"']/parent::div/parent::div//td[contains(@ng-if,'ticketStatusUpdate')]"));
				boolean bticketstae=true;
				String stickstate=null;
				
					for (WebElement cureele : currentticketstate) {
						stickstate="ADJUSTED";
						if (!cureele.getText().trim().equalsIgnoreCase(stickstate))
							bticketstae=false;
					
				}
				if (bticketstae) 
					queryObjects.logStatus(driver, Status.PASS, "After SYNC particular ticket status checking ticket number is:"+ticketnumber, "After sync this ticket status is showing correctly ticket statu is "+stickstate, null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "After Sync particular ticket status checking ticket number is:"+ticketnumber, "After sync this ticket status should  show correctly ticket statu is "+stickstate, null);

			}
				
			
		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.FAIL, "Checking exception on ", "PNR not getting sync",null); 
		}
		
		
		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.FAIL, " checking sync status", "ticket sync is not successfull", null);
		}
	}
	
}
