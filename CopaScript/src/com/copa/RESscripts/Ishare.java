package com.copa.RESscripts;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import FrameworkCode.BFrameworkQueryObjects;

public class Ishare {
	
	static String SetIshare=null;
	static String RevertIshares=null;
	static String GuiCheck=null;
	static String SerRestrict=null;
	static String SerCodeName=null;
	static String FlightNo=null;
	static String FlightFrom=null;
	static String FightTo=null;
	static String TravelClass=null;
	static String StartDt=null;
	static String EndDt=null;
	static String NoOfPax=null;
	public static final int shareflightnum=5;
	public static String finalssradded=null;
	boolean contflag=false;
	static String Days=null;
	public static String[] multipeflight=null;
	public static String[] multipeFlightto=null;
	public static String[] multipeFlightFrom=null;
	
	public static String[] iFllight_No_class=null;
	public static String[] iTo=null;;
	public static String[] iDays=null;;
	public static String INRPA_PNR=null;;
	public static String[] iFirstName=null;;
	public static String[] iLastName=null;;
	public static String[] iFlightnum=null;;
	public static String icode=null;;
	
	public void IshareCheck(WebDriver driver, BFrameworkQueryObjects queryObjects) {
		try
		{  
			if(true)
//				return;
			// Login.envwrite(shareflightnum, " ");
			SerRestrict=getTrimTdata(queryObjects.getTestData("SerRestrict"));
			SetIshare = queryObjects.getTestData("SetIshare");
			RevertIshares = queryObjects.getTestData("RevertIshares");
			GuiCheck  = queryObjects.getTestData("GuiCheck");
			SerRestrict = queryObjects.getTestData("SerRestrict");
			SerCodeName = queryObjects.getTestData("SerCodeName");
			FlightNo = queryObjects.getTestData("FlightNo");
			FightTo = queryObjects.getTestData("FightTo");
		    FlightFrom= queryObjects.getTestData("FlightFrom");
			TravelClass= queryObjects.getTestData("TravelClass");
			Days=queryObjects.getTestData("Days");
			NoOfPax=queryObjects.getTestData("NoOfPax");
			if(SetIshare.equalsIgnoreCase("YES"))
			{
				WebDriverWait wait = new WebDriverWait(driver, 100);
				multipeflight=FlightNo.split(";");
				multipeFlightto=FightTo.split(";");
				multipeFlightFrom=FlightFrom.split(";");
				int NoOfFlight=multipeflight.length;
				try{
					//driver.get("http://tpfsb.intvm.sys.eds.com");
					
					driver.get("https://tpfsb.svcs.entsvcs.net");
					//driver.navigate().to("http://tpfsb.intvm.sys.eds.com");
					driver.findElement(By.xpath("//input[@name='ID']")).sendKeys("wzqltv");   // I share User name
					driver.findElement(By.name("Password")).sendKeys("prayer08");    // I share psw
					driver.findElement(By.xpath("//input[@value='Login']")).click();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Terminal Emulation')]")));
					driver.findElement(By.xpath("//a[contains(text(),'Terminal Emulation')]")).click();
				
					for(int i=0;i<NoOfFlight;i++)
					{
					SetSSRonIShare(driver, queryObjects,multipeflight[i],multipeFlightto[i],multipeFlightFrom[i]);
					//Boolean	Pnrexist=flightsearch(driver, queryObjects);
					
						/*Addssrtopnr(driver, queryObjects);
						CheckOnGui(driver, queryObjects);*/
					}
				}
				catch(Exception e){
				queryObjects.logStatus(driver, Status.FAIL, "Login to Ishares", "Login to Ishares Failed", null);	
				}
			
		    }
			if(RevertIshares.equalsIgnoreCase("Yes")){
				WebDriverWait wait = new WebDriverWait(driver, 100);
				multipeflight=FlightNo.split(";");
				multipeFlightto=FightTo.split(";");
				multipeFlightFrom=FlightFrom.split(";");
				int NoOfFlight=multipeflight.length;
				try{
					driver.get("http://tpfsb.intvm.sys.eds.com");
					//driver.navigate().to("http://tpfsb.intvm.sys.eds.com");
					driver.findElement(By.xpath("//input[@name='ID']")).sendKeys("wzqltv");   // I share User name
					driver.findElement(By.name("Password")).sendKeys("prayer08");    // I share psw
					driver.findElement(By.xpath("//input[@value='Login']")).click();
					queryObjects.logStatus(driver, Status.PASS, "Login to Ishares", "Login to Ishares PAssed", null);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Terminal Emulation')]")));
					driver.findElement(By.xpath("//a[contains(text(),'Terminal Emulation')]")).click();
				
					for(int i=0;i<NoOfFlight;i++)
					{
					RemoveSSRonIShare(driver, queryObjects,multipeflight[i],multipeFlightto[i],multipeFlightFrom[i]);
					//Boolean	Pnrexist=flightsearch(driver, queryObjects);
					
						/*Addssrtopnr(driver, queryObjects);
						CheckOnGui(driver, queryObjects);*/
					}
				}
				catch(Exception e){
				queryObjects.logStatus(driver, Status.FAIL, "Login to Ishares", "Login to Ishares Failed", null);	
				}
			}
			
		}
		 catch (Exception e) {
			
		}
		
	}
	public void SetSSRonIShare(WebDriver driver, BFrameworkQueryObjects queryObjects,String FlightNo,String FightTo,String FlightFrom) throws IOException {
		
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			driver.findElement(By.name("q")).sendKeys("logc cmre");
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			driver.findElement(By.name("q")).sendKeys("bsib");
			String IshLogMsg = (driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText()).trim();
		
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			if( driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().contains("B-SINE COMPLETE")||driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().contains("B-IN USE")){	//indrajit
				queryObjects.logStatus(driver, Status.PASS, "Checking the login Ishare application", "Login to IShare application", null);
				}else{
					queryObjects.logStatus(driver, Status.FAIL, "Checking the login Ishare application", "Getting an error while login to ISHARE applicatio", null);;
				}
			}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.PASS, "Login to Ishares", "Login to Ishares Successfully", null);	
		}
		Calendar CurDate = Calendar.getInstance();
		String sCurrentdate = new SimpleDateFormat("ddMMMyy").format(CurDate.getTime());
		String sCurrentdate1 = new SimpleDateFormat("ddMMM").format(CurDate.getTime());		//indrajit
		StartDt = sCurrentdate; //"12DEC17";
			
		String snextmontdate;
		if(Days.isEmpty()){																	//indrajit
			CurDate.add(Calendar.DATE,30);
			snextmontdate = new SimpleDateFormat("ddMMMyy").format(CurDate.getTime());
		}
		else{
			int sDays = Integer.parseInt(Days);
			sDays = sDays + 10;
			CurDate.add(Calendar.DATE,sDays);
			snextmontdate = new SimpleDateFormat("ddMMMyy").format(CurDate.getTime());
		}
			EndDt =snextmontdate;
			
			/*driver.findElement(By.name("q")).sendKeys(SerNoRest);
			driver.findElement(By.xpath("//input[@value='Send']")).click();*/
			
			String f1;
			if(FlightNo.isEmpty())
			{
				driver.findElement(By.name("q")).sendKeys("a "+sCurrentdate1+" "+FlightFrom+FightTo+"");
				driver.findElement(By.xpath("//input[@value='Send']")).click();
			
				String output=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
			
				String[] flight=output.split("\n");
	 	    
	 	        //flight=output.split("\n");
	 	 	   	String[] flightNum = flight[1].split("UA|CM|BE");
	 	 	   	// flightNum = flight[1].split(" ");
	 	 	   	f1= flightNum[1].substring(0, 4).replace(" ", "");
			}
			else
			{
				if(!FlightNo.isEmpty()){
					String[] flightNum = FlightNo.split("UA|CM|BE");
					f1= flightNum[1];
				}
				else
					f1=	FlightNo;
			}
	 	 	Login.shareflightnm= f1;
	 	 	 
	 	 	String[] ssr=SerCodeName.split(";");
	 	    String SerNoRest = "K-I/CTLA/"+ssr[0]+"/F.0"+f1+"/M."+FlightFrom+"-"+FightTo+"/D."+StartDt+"-"+EndDt+"/C."+TravelClass+"/I."+SerRestrict;
			String SerNoRestdelete = "K-I/CTLD/"+ssr[0]+"/F.0"+f1+"/M."+FlightFrom+"-"+FightTo+"/D."+StartDt+"-"+EndDt+"/C."+TravelClass+"/I."+SerRestrict;
			String ChekNoSsr = "K-I/SSR*/"+SerCodeName+"/"+f1+"/"+StartDt.substring(0, 5);
			String CheckISSTable = "K-I/CTL*";
			
			driver.findElement(By.name("q")).sendKeys(CheckISSTable);
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			String ssroutput2=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
			queryObjects.logStatus(driver, Status.PASS, "SSR Inventory Table Before adding Execution", "SSR table "+ssroutput2+"" , null);
			
			    driver.findElement(By.name("q")).sendKeys(SerNoRest);
				driver.findElement(By.xpath("//input[@value='Send']")).click();
				
				String ssroutput=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
				String ssroutputINACTIVE=null;
				 finalssradded=ssr[0];
				 if(ssroutput.contains("ITEM ADDED")){
			 	    	queryObjects.logStatus(driver, Status.PASS, "SSR Removed from SSR Inventory Table", "SSR removed "+ssroutput+"" , null);
			 	     }
	 	     if(ssroutput.equalsIgnoreCase("ITEM ALREADY EXISTS") || !ssroutput.contains("ITEM ADDED"))
	 	     {
	 	    	driver.findElement(By.name("q")).sendKeys(SerNoRestdelete);
	 			driver.findElement(By.xpath("//input[@value='Send']")).click(); 
	 			
	 			driver.findElement(By.name("q")).sendKeys(SerNoRest);
	 			driver.findElement(By.xpath("//input[@value='Send']")).click();
	 			ssroutputINACTIVE=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
	 			String[] msg=ssroutputINACTIVE.split("\n");
	 			int n=msg.length;
	 			 String lastline=msg[n-1];
	 	 	    String confmsg = lastline.replaceAll("\\**", "");
	 	 	    String splitmsg=confmsg;
	 	 	    finalssradded=ssr[0];
	 			while(splitmsg.contains("ITEM ADDED AS INACTIVE"))
	 			{
	 				SerNoRest = "K-I/CTLA/"+ssr[1]+"/F.0"+f1+"/M."+FlightFrom+"-"+FightTo+"/D."+StartDt+"-"+EndDt+"/C."+TravelClass+"/I."+SerRestrict;
	 				
	 				driver.findElement(By.name("q")).sendKeys(SerNoRest);
	 	 			driver.findElement(By.xpath("//input[@value='Send']")).click();
	 				ssroutputINACTIVE=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
	 				msg=ssroutputINACTIVE.split("\n");
	 				 n=msg.length;
	 	 			lastline=msg[n-1];
	 	 			confmsg = lastline.replaceAll("\\**", "");
	 	 			splitmsg=confmsg;
	 	 			finalssradded=ssr[1];
	 	 			if(splitmsg.contains("ITEM ALREADY EXISTS")) 
	 	 			{   SerNoRestdelete = "K-I/CTLD/"+ssr[1]+"/F.0"+f1+"/M."+FlightFrom+"-"+FightTo+"/D."+StartDt+"-"+EndDt+"/C."+TravelClass+"/I."+SerRestrict;
	 	 				driver.findElement(By.name("q")).sendKeys(SerNoRestdelete);
	 	 	 			driver.findElement(By.xpath("//input[@value='Send']")).click(); 
	 	 	 			
	 	 	 			driver.findElement(By.name("q")).sendKeys(SerNoRest);
	 	 	 			driver.findElement(By.xpath("//input[@value='Send']")).click();
	 	 	 			
	 	 	 			ssroutputINACTIVE=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
	 	 				msg=ssroutputINACTIVE.split("\n");
	 	 				 n=msg.length;
	 	 	 			lastline=msg[n-1];
	 	 	 			confmsg = lastline.replaceAll("\\**", "");
	 	 	 			splitmsg=confmsg;
	 	 	 			finalssradded=ssr[1];
	 	 			}
	 			}
	 	 	    
	 	     }
	 	     else
	 	     {
	 	    	 
	 	     String[] msg=ssroutput.split("\n");
	 	     int n=msg.length;
	 	     String lastline=msg[n-1];
	 	    String confmsg = lastline.replaceAll("\\**", "");
	 	    String splitmsg=confmsg;
	 	   //int a=ssr.length;
	 	   while(splitmsg.contains("ITEM ADDED AS INACTIVE") ||!splitmsg.contains("ITEM ADDED") )
			{    
	 		    
				SerNoRest = "K-I/CTLA/"+ssr[1]+"/F.0"+f1+"/M."+FlightFrom+"-"+FightTo+"/D."+StartDt+"-"+EndDt+"/C."+TravelClass+"/I."+SerRestrict;
				driver.findElement(By.name("q")).sendKeys(SerNoRest);
		 			driver.findElement(By.xpath("//input[@value='Send']")).click();
					ssroutputINACTIVE=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
					
					 msg=ssroutputINACTIVE.split("\n");
			 	     n=msg.length;
			 	     lastline=msg[n-1];
			 	    confmsg = lastline.replaceAll("\\**", "");
			 	     splitmsg=confmsg;
			 	    finalssradded=ssr[1];
			    
				}
	 	     }
	 	    driver.findElement(By.name("q")).sendKeys(CheckISSTable);
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			String ssroutput3=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
			queryObjects.logStatus(driver, Status.PASS, "SSR Inventory Table after adding Execution", "SSR table "+ssroutput3+"" , null);
 	     // Login.envwrite(6, "");
 	     Login.shareflightnumssr= finalssradded;
 	    }
	public void RemoveSSRonIShare(WebDriver driver, BFrameworkQueryObjects queryObjects,String FlightNo,String FightTo,String FlightFrom) throws IOException {
		
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try
		{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
		driver.findElement(By.name("q")).sendKeys("logc cmre");
		driver.findElement(By.xpath("//input[@value='Send']")).click();
		driver.findElement(By.name("q")).sendKeys("bsib");
		String IshLogMsg = (driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText()).trim();
		
		driver.findElement(By.xpath("//input[@value='Send']")).click();
		if( driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().contains("B-SINE COMPLETE")||driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().contains("B-IN USE")){	//indrajit
			queryObjects.logStatus(driver, Status.PASS, "Checking the login Ishare application", "Login to IShare application", null);
			}else{
				queryObjects.logStatus(driver, Status.FAIL, "Checking the login Ishare application", "Getting an error while login to ISHARE applicatio", null);;
			}
		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.PASS, "Login to Ishares", "Login to Ishares Successfully", null);	
		}
		Calendar CurDate = Calendar.getInstance();
		String sCurrentdate = new SimpleDateFormat("ddMMMyy").format(CurDate.getTime());
		String sCurrentdate1 = new SimpleDateFormat("ddMMM").format(CurDate.getTime());		//indrajit
		StartDt = sCurrentdate; //"12DEC17";
			
		String snextmontdate;
		if(Days.isEmpty()){																	//indrajit
			CurDate.add(Calendar.DATE,30);
			snextmontdate = new SimpleDateFormat("ddMMMyy").format(CurDate.getTime());
		}
		else{
			int sDays = Integer.parseInt(Days);
			sDays = sDays + 10;
			CurDate.add(Calendar.DATE,sDays);
			snextmontdate = new SimpleDateFormat("ddMMMyy").format(CurDate.getTime());
		}
		EndDt =snextmontdate;
		String f1;
		
			if(!FlightNo.isEmpty()){
				String[] flightNum = FlightNo.split("UA|CM|BE");
				f1= flightNum[1];
			}
			else
				f1=	FlightNo;
 	 	Login.shareflightnm= f1;
 	 	String[] ssr=SerCodeName.split(";");
		String SerNoRestdelete = "K-I/CTLD/"+ssr[0]+"/F.0"+f1+"/M."+FlightFrom+"-"+FightTo+"/D."+StartDt+"-"+EndDt+"/C."+TravelClass+"/I."+SerRestrict;
		String ChekNoSsr = "K-I/SSR*/"+SerCodeName+"/"+f1+"/"+StartDt.substring(0, 5);
		String CheckISSTable = "K-I/CTL*";
 	     
 	    // Login.envwrite(shareflightnum, f1);
		   driver.findElement(By.name("q")).sendKeys(SerNoRestdelete);
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			
			String ssroutput=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
			String ssroutputINACTIVE=null;
			 finalssradded=ssr[0];
 	     if(ssroutput.contains("ITEM DELETED")){
 	    	queryObjects.logStatus(driver, Status.PASS, "SSR Removed from SSR Inventory Table", "SSR removed "+ssroutput+"" , null);
 	     }
 	     if(ssroutput.contains("ITEM NOT FOUND")){
 	    	queryObjects.logStatus(driver, Status.PASS, "SSR Restriction not done", " OR SSR restriction is failed "+ssroutput+"" , null);
 	     }
 	     
 	    driver.findElement(By.name("q")).sendKeys(CheckISSTable);
		driver.findElement(By.xpath("//input[@value='Send']")).click();
		String ssroutput2=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
		queryObjects.logStatus(driver, Status.PASS, "SSR Inventory Table After Removal Execution", "SSR table "+ssroutput2+"" , null);
 	     // Login.envwrite(6, "");
 	     Login.shareflightnumssr= finalssradded;
 	     }
//	}
	
	public String getTrimTdata(String inp1) throws Exception{
		String returnstring = "";
		if (inp1!=null){
			returnstring=inp1.trim();
		}
		return returnstring;
	}
	public void Copa_Ishare(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException, InterruptedException
	{
		 
		try {
			
			// driver.findElement(By.xpath("//div/i[@class='icon-close']")).click();
		        
		        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//md-menu/button[contains(text(),' Reservations')]")));
			FlightSearch.loadhandling(driver);
		        driver.findElement(By.xpath("//md-menu/button[contains(text(),' Reservations')]")).click();
		       
		        
		        driver.findElement(By.xpath("//md-menu-item[contains(@ng-repeat,'landingOpt in layout')]//button[contains(text(),' Reservations')]")).click();
			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class='icon-home']/parent::span")));
		  FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//i[@class='icon-home']/parent::span")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//div[text()='New Order']")).click();
			
			//Get PNR number from either test data sheet or stored PNR from environment variables
		String pnrNum =Login.PNRNUM.trim();
			
			if(pnrNum.equals("")) 
				queryObjects.logStatus(driver, Status.FAIL, "PNR was not generated", "PNR was not generated" , null);
			else
				contflag=true;	
			if(contflag) 
			{
				
					
					driver.findElement(By.xpath("//span[text()='Home']")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//div[div[text()='Search'] and @role='button']")).click();
					Thread.sleep(2000);
					//Entering PNR number in search input
					driver.findElement(By.xpath("//input[@aria-label='Search']")).click();
					driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(pnrNum);

					//Clicking on Search button
					driver.findElement(By.xpath("//div[contains(@class,'itinerary-search')]//button[contains(text(),'Search')]")).click();
					//String Pnris=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@action='pnr']/div/div/div[1]/div[1]")).getText());
					//Wait until loading wrapper closed
					FlightSearch.loadhandling(driver);
					
					queryObjects.logStatus(driver, Status.PASS, "Search PNR", "Search PNR successful" , null);
				}
		}
				catch(Exception e) {
					contflag = false;
					queryObjects.logStatus(driver, Status.FAIL, "Search PNR", "Search PNR failed: " + e.getLocalizedMessage() , e);
				}
		//Addssrtopnr(driver, queryObjects);
		CheckOnGui(driver, queryObjects);
		}
	
	public void Addssrtopnr(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver, 100);
		driver.findElement(By.xpath("//div[div[contains(text(),'Order')]]//following-sibling::div/div[contains(text(),'Services')]")).click();	
		
		driver.findElement(By.xpath("//input[@name='ssr']")).sendKeys(Login.envRead(6).trim());
		
		driver.findElement(By.xpath("//div[contains(@class,'md-virtual-repeat-scroller')]//child::li[1]")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//span/i[@class='icon-search']")).click();
		
		//AddSsrToPnr(driver, queryObjects);
		
		FlightSearch.loadhandling(driver);
		
		driver.findElement(By.xpath("//label[contains(@translate,'pssgui.explanations')]//following-sibling::input")).sendKeys("Adding SSr to PNR");
		driver.findElement(By.xpath("//i[contains(@class,'icon-content-copy')]")).click();
		driver.findElement(By.xpath("//button[contains(text(),'Add To Order')]")).click();
		FlightSearch.loadhandling(driver);
		
		Boolean button=driver.findElements(By.xpath("//button[contains(text(),'Check Out') and @disabled='disabled']")).size()>0;
		if(driver.findElement(By.xpath("//div[contains(@model,'payment.balanceDue')]")).getText().equalsIgnoreCase("0"))
		{//div[contains(@model,'payment.balanceDue')]
			driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
			driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();

			driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys(RandomStringUtils.random(6, true, false));
			
			driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys(RandomStringUtils.random(6, true, false));
			
			driver.findElement(By.xpath("//input[@name='Email Address']")).sendKeys(RandomStringUtils.random(6, true, false)+"@mphasis.com");
			
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'Proceed to Pay')]"))));
			
			driver.findElement(By.xpath("//button[contains(text(),'Proceed to Pay')]")).click();
			
	        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div/button[text()='Pay']"))));
	        String emdamt=driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//input[@aria-label='amount']")).getText().trim();
	        Login.EMDAmt= emdamt+";";
			driver.findElement(By.xpath("//div/button[text()='Pay']")).click();
			
			FlightSearch.emailhandling(driver,queryObjects);
						
			try {
				wait = new WebDriverWait(driver, 120);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
				String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
				queryObjects.logStatus(driver, Status.PASS, " EMD Payment", statusMessage, null);
			}
			catch(Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "EMD Payment", "EMD Payment Unsuccessful: " + e.getMessage() , e);
			}
		}
		FlightSearch.loadhandling(driver);
		
	}
	public void CheckOnGui(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException, InterruptedException		//SSR inventory operation
		{
		
		WebDriverWait wait = new WebDriverWait(driver, 50);
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//div[@id='toolbar']")).click();		
		driver.findElement(By.xpath("//div[div[contains(text(),'Inventory')]]")).click();
		try{
			multipeflight=FlightNo.split(";");
			int incr;
			int iter;
			multipeFlightto=FightTo.split(";");
			multipeFlightFrom=FlightFrom.split(";");
			int NoOfFlight=multipeflight.length;
			Boolean check=false;
			for(int i=0;i<1;i++){			//multipeflight[i],multipeFlightto[i],multipeFlightFrom[i]
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@ng-model,'flightNumber')]")));
				driver.findElement(By.xpath("//input[contains(@ng-model,'flightNumber')]")).clear();
				driver.findElement(By.xpath("//input[contains(@ng-model,'flightNumber')]")).click();
				driver.findElement(By.xpath("//input[contains(@ng-model,'flightNumber')]")).sendKeys(multipeflight[i]);			//send flight number from the excel sheet//indrajit
				driver.findElement(By.xpath("//input[contains(@ng-model,'flightNumber')]")).sendKeys(Keys.ENTER);
				driver.findElement(By.xpath("//input[@name='origin']")).clear();
				driver.findElement(By.xpath("//input[@name='origin']")).click();
				driver.findElement(By.xpath("//input[@name='origin']")).sendKeys(multipeFlightFrom[i]);	//send board point
				driver.findElement(By.xpath("//input[@name='origin']")).sendKeys(Keys.ENTER);
				driver.findElement(By.xpath("//input[@name='destination']")).clear();
				driver.findElement(By.xpath("//input[@name='destination']")).click();
				driver.findElement(By.xpath("//input[@name='destination']")).sendKeys(multipeFlightto[i]);	//send off point
				driver.findElement(By.xpath("//input[@name='destination']")).sendKeys(Keys.ENTER);
				
				int days = 0;													//indrajit	//for the date on which the flight is booked
				if(Days.isEmpty())
					days = 30;
				else
					days = Integer.parseInt(Days);
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Calendar c = Calendar.getInstance();
				c.setTime(new Date()); 
				c.add(Calendar.DATE, days); 
				String todayDate = sdf.format(c.getTime());
				System.out.println(todayDate); 
				if(check){
					if (NoOfFlight>0){
						//int seg=0;
						for (iter = 2; iter <= NoOfFlight; iter++) {
							//sourseDest=arySegments[seg].split("-");
							Calendar calcseg = Calendar.getInstance();
							calcseg.add(Calendar.DATE, (days+iter+1));
							todayDate = new SimpleDateFormat("MM/dd/yyyy").format(calcseg.getTime());
						}
					}
				}
				//calcseg.add(Calendar.DATE, (getDays+iter+1));
				//c.add(Calendar.DATE, (days+i+1));
				driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).clear();
				driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).click();
				driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(todayDate);	//check the specified date SSR availability
				driver.findElement(By.xpath("//input[@name='ssrcode']")).clear();
				driver.findElement(By.xpath("//input[@name='ssrcode']")).click();
				driver.findElement(By.xpath("//input[@name='ssrcode']")).sendKeys(SerCodeName);				//indrajit
				driver.findElement(By.xpath("//input[@name='ssrcode']")).sendKeys(Keys.ENTER);
				//Thread.sleep(10000);
				//WebDriverWait wait = new WebDriverWait(driver, 50);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='button' and @aria-label='Display']")));
				driver.findElement(By.xpath("//button[@type='button' and @aria-label='Display']")).click();	//button visible after few seconds
				FlightSearch.loadhandling(driver);
				//validation of services
				
				String ssrdetail= driver.findElement(By.xpath("//md-content/div[contains(@ng-repeat,'ssrData in ssrInventory')]")).getText();
				String ssrcolumdetail= driver.findElement(By.xpath("//div/div[div[contains(text(),'Available')]]")).getText();   
				String ssrcabin= driver.findElement(By.xpath("//div[2][contains(@ng-repeat,'cabinDetail in cabins')]/div[1]")).getText();		//SSR availability for specific cabin
			    String noofssr= driver.findElement(By.xpath("//div[2][contains(@ng-repeat,'cabinDetail in cabins')]/div[2]")).getText();		//total number of SSR for specific flight
				String noofssrsold= driver.findElement(By.xpath("//div[2][contains(@ng-repeat,'cabinDetail in cabins')]/div[3]")).getText();	//total number of SSR sold count
				String noofssravailable= driver.findElement(By.xpath("//div[2][contains(@ng-repeat,'cabinDetail in cabins')]/div[4]")).getText();	//total number of available SSR
				check=true;
				if (ssrcabin.equalsIgnoreCase("Y")){
					queryObjects.logStatus(driver, Status.PASS, "Checking SSR Cabin", "Cabin selected is correct "+ssrcabin, null);
					try{
						int result = noofssr.compareTo(NoOfPax);
						if(result < 0){
							queryObjects.logStatus(driver, Status.PASS, "Checking SSR detail", "SSR detail in GUI is not match "+ssrdetail, null);
							
						}
						else{
							queryObjects.logStatus(driver, Status.PASS, "Checking number of SSR available", "It is displaying correctly the number of SSr available "+noofssravailable, null);
						}
						
					}
					catch(Exception e){
						queryObjects.logStatus(driver, Status.FAIL, "SSR Details Mismatch", "Number of Pax and Available SSR have mismatch " + e.getMessage() , e);
					}
				}
				else{
					queryObjects.logStatus(driver, Status.FAIL, "Checking SSR Cabin", "Cabin selected is not restricted in Ishares, so incorrect"+ssrcabin, null);
				}
			}
			return;
			//driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();	//click cancel to close the SSR pop-up
		}
		catch(Exception e){
			queryObjects.logStatus(driver, Status.FAIL, "Open SSR Inventory Failed", "Unable to open SSR inventory tool" + e.getMessage() , e);
			return;
		}
		
	}
		
// NSRA NSPA GROUP AND CORP PNR

	public static void ishares_Nsra_nspa(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception
	{
			
		String Fllight_No_class = FlightSearch.getTrimTdata(queryObjects.getTestData("Fllight No and Class"));
		iFllight_No_class = Fllight_No_class.split(";");

		String To = FlightSearch.getTrimTdata(queryObjects.getTestData("From_To"));
		iTo = To.split(";");

		String Days = FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
		iDays = Days.split(";");

		String FirstName = FlightSearch.getTrimTdata(queryObjects.getTestData("FirstName"));
		iFirstName= FirstName.split(";");

		String LastName = FlightSearch.getTrimTdata(queryObjects.getTestData("LastName")); 
		iLastName = LastName.split(";"); 

		String Flightnum = FlightSearch.getTrimTdata(queryObjects.getTestData("Flightnum"));
		iFlightnum = Flightnum.split(";");
		
		String icode = FlightSearch.getTrimTdata(queryObjects.getTestData("Code"));
		
		String PNRType = FlightSearch.getTrimTdata(queryObjects.getTestData("PNRType"));
		
		String iAvail = "";
		
		switch(PNRType) {
		
			case "Normal":
				iAvail = "NN";
				break;
				
			case "NRPS":
				iAvail = "PNN";
				break;
				
			case "NRSA":
				iAvail = "MM";
				break;
				
			case "Group":
				iAvail = "GNN";
				break;
				
			case "Corp":
				iAvail = "GNN";
				break;
		}
		
		int getDays=0;

		WebDriverWait wait = new WebDriverWait(driver, 100);

		try
		{
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\kboobalan\\Downloads\\chromedriver.exe");
			
			driver.get("http://tpfsb.intvm.sys.eds.com");
			//driver.navigate().to("http://tpfsb.intvm.sys.eds.com");
			driver.findElement(By.xpath("//input[@name='ID']")).sendKeys("wzqltv");   // I share User name
			driver.findElement(By.name("Password")).sendKeys("prayer08");    // I share psw
			driver.findElement(By.xpath("//input[@value='Login']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Terminal Emulation')]")));
			driver.findElement(By.xpath("//a[contains(text(),'Terminal Emulation')]")).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			driver.findElement(By.name("q")).sendKeys("logc cmre");
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			driver.findElement(By.name("q")).sendKeys("bsib");
			String IshLogMsg = (driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText()).trim();

			driver.findElement(By.xpath("//input[@value='Send']")).click();
			if( driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().contains("B-SINE COMPLETE")||driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().contains("B-IN USE")){	//indrajit
				queryObjects.logStatus(driver, Status.PASS, "Checking the login Ishare application", "Login to IShare application", null);
			}else{
				queryObjects.logStatus(driver, Status.FAIL, "Checking the login Ishare application", "Getting an error while login to ISHARE applicatio", null);
			}
			//Shares Entries for NRSP PAx

			int n = iFllight_No_class.length;

			for(int k=0;k<n;k++)
			{	
				getDays=Integer.parseInt(iDays[k]);
				Calendar calc = Calendar.getInstance();
				calc.add(Calendar.DAY_OF_WEEK, +getDays);
				//calc.add(Calendar.DATE, +getDays);
				String timeStampd = new SimpleDateFormat("ddMMM").format(calc.getTime());
				Thread.sleep(2000);
				driver.findElement(By.name("q")).sendKeys("0CM "+iFllight_No_class[k]+" "+timeStampd+" "+iTo[k]+" "+iAvail+" "+iFirstName.length);  
				driver.findElement(By.xpath("//input[@value='Send']")).click();
				Thread.sleep(2000);
			}
			
			if(PNRType.equalsIgnoreCase("Group") || PNRType.equalsIgnoreCase("Corp")) {
					driver.findElement(By.name("q")).sendKeys("-"+PNRType.toUpperCase().charAt(0)+"/0"+PNRType.toUpperCase()+"/PNR"); // -C/0CORP/PNR or -G/0GROUP/PNR
					driver.findElement(By.xpath("//input[@value='Send']")).click();
					Thread.sleep(2000);
			}
				n = iFirstName.length;
				
				for(int l=0;l<n;l++){
					if (icode.isEmpty())
						driver.findElement(By.name("q")).sendKeys("-"+iLastName[l]+"/"+iFirstName[l]+"");	//Normal Case icode will be empty in excel tab
					else
						driver.findElement(By.name("q")).sendKeys("-"+icode+"12/"+iLastName[l]+"/"+iFirstName[l]+""); // icode for Non Revenue PAX (NRSA / NRPS)
					driver.findElement(By.xpath("//input[@value='Send']")).click();
					Thread.sleep(2000);
				}	
			

			driver.findElement(By.name("q")).sendKeys("6P|7T/|9LAX/N000|ER ");
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			Thread.sleep(2000);
			driver.findElement(By.name("q")).sendKeys("$-$-ZO80P");
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			Thread.sleep(2000);
			driver.findElement(By.name("q")).sendKeys("FF");
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			driver.findElement(By.name("q")).sendKeys("ER");
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			//Getting PNR from shares screen
			String text= driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText();
			String[] PNR=text.split(" ");
			Login.PNRNUM=PNR[0];		
			driver.findElement(By.name("q")).sendKeys("T-$|ET");
			driver.findElement(By.xpath("//input[@value='Send']")).click();

			Thread.sleep(2000);
			
			if(driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().contains("TKT ISSUED")||driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().contains("TKT NBR ISSUED")) { 
				queryObjects.logStatus(driver, Status.PASS, "Creating PNR in Ishare application", "Ishare PNR Created", null);
				}else{
					queryObjects.logStatus(driver, Status.FAIL, "Checking the login Ishare application", "Getting an error while creating PNR in ISHARE application", null);;
				}
			driver.findElement(By.name("q")).sendKeys("IR");
			driver.findElement(By.xpath("//input[@value='Send']")).click();

		}
		catch(Exception e) {

		}
	}
	
	
}
	

