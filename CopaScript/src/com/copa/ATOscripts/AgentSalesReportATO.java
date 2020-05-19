package com.copa.ATOscripts;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.copa.RESscripts.FlightSearch;

import FrameworkCode.BFrameworkQueryObjects;


public class AgentSalesReportATO {
	
	public void EMDVarificationATO(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{
		
		String show=FlightSearch.getTrimTdata(queryObjects.getTestData("SHOW"));
		String Transactions=FlightSearch.getTrimTdata(queryObjects.getTestData("Transactions"));
		String  DocumentType=FlightSearch.getTrimTdata(queryObjects.getTestData("DocumentType"));
		
		if( driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button"))!= null){
			queryObjects.logStatus(driver, Status.PASS, "Checking the visibility of Reservation link", "Reservation link is identify", null);
			}else{
				queryObjects.logStatus(driver, Status.FAIL, "Checking the visibility of Reservation link", "Reservation link is not identify", null);
			}
		 driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
		 Thread.sleep(1000);
		 driver.findElement(By.xpath("//button[contains(text(),'Sales Reporting')]")).click();
		 FlightSearch.loadhandling(driver);
		 driver.findElement(By.xpath("//button[contains(text(),'Agent Sales Report')]")).click();
		 FlightSearch.loadhandling(driver);
		 //added to check the warning message --suman 25012018
		 if(driver.findElements(By.xpath("//h2[contains(text(),'Warning')]")).size()>0)
				driver.findElement(By.xpath("//button[contains(text(),'Ok')]")).click();
		 //validating the agent sales report   // changes suman 24-01-2018
		 if( driver.findElement(By.xpath("//div/span[contains(text(),'Total Transaction Amoun')]"))!= null){
				queryObjects.logStatus(driver, Status.PASS, "Checking agent page", "Agent page is identify", null);
				}else{
					queryObjects.logStatus(driver, Status.FAIL, "Checking agent page", "Agent page is identify", null);
				}
		 try  //added on 24012018
		  {
		
	    /*String Agent=getTrimTdata(queryObjects.getTestData("Agent"));
	    String Supervisor = getTrimTdata(queryObjects.getTestData("Supervisor"));
	    */
	  
		driver.findElement(By.xpath("//md-select[@name='showRecords']")).click();
		Thread.sleep(100);
		driver.findElement(By.xpath("//md-option[@value='"+show+"']")).click();
		// this is for Transaction type
		driver.findElement(By.xpath("//md-select[@name='transactionTypeFilter']")).click();
		Thread.sleep(100);
		driver.findElement(By.xpath("//md-option[@value='"+Transactions+"' and contains(@ng-repeat,'transactionType')]")).click();
		
		if(Transactions.equalsIgnoreCase("EMD"))
		{
			 List<WebElement> listoftiket=driver.findElements(By.xpath("//div[contains(@ng-repeat,'(agentSaleItemId,agentSaleItem)')]/div/div[contains(@ng-class,'agentSaleItem.couponInfo')]//following-sibling::div[1]"));
	    	 List<String> tkttype = new ArrayList<>();
	    	 listoftiket.forEach(a -> tkttype.add(a.getText().trim()));
	    	 int total=listoftiket.size();
	    	 
	    	 if(tkttype.contains(Atoflow.tktemd1))
	    	 {
	    		 queryObjects.logStatus(driver, Status.PASS, "CChecking the all EMD NO", "EMD number is available in sales report",null); 
	    	 }
		}
		
		
	   }
		catch(Exception e)	
		 {
			 queryObjects.logStatus(driver, Status.FAIL, "Filtering the field", "Issue while filtering", e);
		 }
	}
}
