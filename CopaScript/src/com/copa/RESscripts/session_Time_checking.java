package com.copa.RESscripts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import FrameworkCode.BFrameworkQueryObjects;

public class session_Time_checking {
	WebDriverWait wait = null;
	static List<WebElement> closeReportBtn = new ArrayList<WebElement>();
	static List<WebElement> TotalcloseReportBtn = new ArrayList<WebElement>();
	/*public static final int PNRNUM = 0;
	public static final int FLIGHTNUM = 1;
	public static final int CURRENCY = 2;
	public static final int EMDNO = 3;
	public static final int EMDAmt=4;
	public static final int shareflightnm=5;
	public static final int shareflightnumssr=6;
	public static final int MultiplePNR=7;*/
	
	public static  String PNRNUM = "";
	public static  String FLIGHTNUM = "";
	public static  String CURRENCY = "";
	public static  String EMDNO = "";
	public static  String EMDAmt="";
	public static  String shareflightnm="";
	public static  String shareflightnumssr="";
	public static String MultiplePNR="";

	static String SalesOff =null;
	static String Cur=null;
	public static String Usernm=null;
	public void login(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{

		try{

		/*	Login.envwrite(PNRNUM, " ");
			Login.envwrite(FLIGHTNUM, " ");
			Login.envwrite(CURRENCY, " ");
			Login.envwrite(EMDNO, " ");
			Login.envwrite(EMDAmt, " ");
			Login.envwrite(shareflightnm, " ");
			Login.envwrite(shareflightnumssr, " ");
			Login.envwrite(MultiplePNR, " ");*/
			PNRNUM = "";
			FLIGHTNUM = "";
			CURRENCY = "";
			EMDNO = "";
			EMDAmt="";
			shareflightnm="";
			shareflightnumssr="";
			MultiplePNR="";


			String Env=FlightSearch.getTrimTdata(queryObjects.getTestData("Env")); 
			if (Env.equalsIgnoreCase("SIT")) 
				//driver.navigate().to("http://pssguicmmb.airservices.svcs.entsvcs.com:8970/");
				driver.navigate().to("http://pssguicmmb.airservices.svcs.entsvcs.com");
			else if (Env.equalsIgnoreCase("DEV")|| Env.equalsIgnoreCase("JSIT")) 
				driver.navigate().to("http://ustlssoat114.airservices.svcs.entsvcs.com:8980/");
			//driver.navigate().to("https://pssguicmt.airservices.svcs.entsvcs.com");
			else if (Env.equalsIgnoreCase("UAT")) 
				//driver.navigate().to("http://pssguicmm.airservices.svcs.entsvcs.com:8980");
				driver.navigate().to("https://pssguicmm.airservices.svcs.entsvcs.com/css");

			Usernm=queryObjects.getTestData("userName");
			driver.findElement(By.name("USER")).sendKeys(Usernm);
			driver.findElement(By.name("PASSWORD")).sendKeys(queryObjects.getTestData("password"));
			driver.findElement(By.name("submit")).click();
			Thread.sleep(10000);

			if (Env.equalsIgnoreCase("SIT")) 
				driver.findElement(By.xpath("//a[contains(text(),'css')]")).click();
			else if (Env.equalsIgnoreCase("DEV"))
				driver.findElement(By.xpath("//a[contains(text(),'copa-dev')]")).click();
			// driver.findElement(By.xpath("//a[contains(text(),'copa-prime-time')]")).click();
			else if (Env.equalsIgnoreCase("UAT")) 
				driver.findElement(By.xpath("//a[contains(text(),'css')]")).click();
			else if (Env.equalsIgnoreCase("JSIT"))
				driver.findElement(By.xpath("//a[@href='copa-jsit/']")).click();

			driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
			FlightSearch.loadhandling(driver);	
			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Reservations')]")));
			String Home = driver.findElement(By.xpath("//div[contains(text(),'Reservations')]")).getText();
			if (Home.trim().equals("Reservations")) {
				queryObjects.logStatus(driver, Status.PASS, "Login to COPA Application", "LoginSuccess", null);
				// FlightSearch.loadhandling(driver);
				Thread.sleep(3000);
				Boolean CloseReport=driver.findElements(By.xpath("//h2[contains(text(),'reminder')]")).size() >0;  
				if(CloseReport)  
					driver.findElement(By.xpath("//button[contains(@class,'md-confirm-button')]")).click();
				//closePrevDayReports(driver, queryObjects);
				SalesOff = getTrimTdata(queryObjects.getTestData("Salesoffice"));
				Cur = getTrimTdata(queryObjects.getTestData("Currency"));
				CURRENCY=Cur;
				if (!SalesOff.isEmpty()) {

					driver.findElement(By.xpath("//div[@action='saleOfficeInfo']/div[@class='padding-top']/i")).click(); 
					Thread.sleep(3000);
					driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div/div[1]/md-input-container/md-select/md-select-value/span[2]")).click();
					Thread.sleep(3000);
					//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'loading-message')]")));
					//driver.findElement(By.xpath("//md-option[div[@class='md-text ng-binding' and contains(text(),'"+SalesOff+"')]]")).click(); 
					driver.findElement(By.xpath("//md-option[div[@class='md-text ng-binding' and contains(text(),'"+SalesOff+"')]]")).click();
				}
				Thread.sleep(2000);
				driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div/div[2]/md-input-container")).click();//Clicking on Currency drop down
				Thread.sleep(3000);
				if (!Cur.isEmpty()) {

					// SIT	driver.findElement(By.xpath("//md-option[div[@class='md-text ng-binding' and contains(text(),'"+Cur+"')]]")).click();//Selecting the UDS Currency
					driver.findElement(By.xpath("//md-option[contains(@ng-value,'currency')][div[@class='md-text ng-binding' and contains(text(),'"+Cur+"')]] ")).click();
				}  
				Thread.sleep(2000);
				driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div[2]/button[contains(text(),'OK')]")).click();  
				// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'loading-message')]")));

				FlightSearch.loadhandling(driver);
				try {
					if (!SalesOff.isEmpty()) {
						if (driver.findElement(By.xpath("//div[@class='inset padding-right-0 ng-binding']")).getText().contains(SalesOff)) {
							queryObjects.logStatus(driver, Status.PASS, "Check given sales office details ", "Sales office is updated", null);

							//------------------

							Calendar cal = Calendar.getInstance();
							String Startee_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
							System.out.println(Startee_time);
							queryObjects.logStatus(driver, Status.PASS, "session Time out verification ", "Session start time "+Startee_time, null);
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


							//cal.add(Calendar.HOUR, 14);
							cal.add(Calendar.MINUTE, 5);
							String Endsss_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
							Date End_time=sdf.parse(Endsss_time);
							String Currented_time="";

							Boolean value;
							//Thread.sleep(1000);
							driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
							Thread.sleep(2000);
							driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Reservations')]")).click();
							FlightSearch.loadhandling(driver);

							do{
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//input[@aria-label='Search']")).click();
								Thread.sleep(2000);
								driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys("BWRFRL");
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//div[contains(@class,'itinerary-search')]//button[contains(text(),'Search')]")).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//span[text()='Home']")).click();
								if(driver.findElements(By.xpath("//span[contains(text(),'session has ended')]")).size()>0){
									String End_Time= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
									queryObjects.logStatus(driver, Status.FAIL, "session Time out verification ", "Session End time "+End_Time, null);
								}

								Calendar calss = Calendar.getInstance();
								Currented_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calss.getTime());
								Date Current_time=sdf.parse(Currented_time);

								value=End_time.compareTo(Current_time)<0;


							}while(!value);
							System.out.println(Currented_time);
							queryObjects.logStatus(driver, Status.PASS, "session Time out verification ", "Samse session executed continously 14 Hours:"+Currented_time, null);

							//----------------------------------------------	

						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Check given sales office details ", "Sales office is not updated", null);
						}

					}
				}
				catch (Exception e) {
					queryObjects.logStatus(driver, Status.INFO, "Sales offce", e.getLocalizedMessage(), e);

				}
				Boolean CloseReport1=driver.findElements(By.xpath("//h2[contains(text(),'reminder')]")).size() >0;  //suman
				Boolean CloseReport2=driver.findElements(By.xpath("//div[@action='saleOfficeInfo']/div[@class='padding-top']/i[contains(@class,'icon-warning pssgui')]")).size() >0;
				if(CloseReport1 ||CloseReport2)  //suman
					closePrevDayReports(driver, queryObjects);

				//closePrevDayReports(driver, queryObjects);

			}
			else {
				queryObjects.logStatus(driver, Status.FAIL, "Login to COPA Application", "LoginFailure", null);
			} 


		}
		catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Login to COPA Application", e.getLocalizedMessage(), e);

		}

	}

	public String getTrimTdata(String inp1) throws Exception{
		String returnstring = "";
		if (inp1!=null){
			returnstring=inp1.trim();
		}
		return returnstring;
	}

	public static String envRead(int cellNum) throws IOException{

		String FilePath = "C:\\MSmart\\Excel_Component\\Test_Artifacts\\TestData\\Envvalue.xlsx";

		FileInputStream input=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(input);
		XSSFSheet sh=wb.getSheet("Envvalue");
		XSSFCell c=sh.getRow(1).getCell(cellNum);
		DataFormatter format = new DataFormatter();

		input.close();
		return format.formatCellValue(c);

	}
	public static void envwrite(int cellNum, String value) throws IOException{

		String FilePath = "C:\\MSmart\\Excel_Component\\Test_Artifacts\\TestData\\Envvalue.xlsx";

		FileInputStream input=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(input);
		XSSFSheet sh=wb.getSheet("Envvalue");
		XSSFCell c=sh.getRow(1).getCell(cellNum);
		c.setCellValue(value);

		input.close();
		FileOutputStream output= new FileOutputStream(FilePath);
		wb.write(output);
		output.close();
	}

	public static void closePrevDayReports(WebDriver driver,BFrameworkQueryObjects queryObjects) throws InterruptedException, IOException {

		try {
			// driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);//15
			//If reminder popup displayed
			// driver.findElement(By.xpath("//h2[contains(text(),'reminder')]"));
			//Click on OK button
			try{
				driver.findElement(By.xpath("//button[contains(@class,'md-confirm-button')]")).click();
				Thread.sleep(1000);
			}
			catch(Exception e){}
			//Click on Reservations menu bar
			driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
			//click on sales reporting
			driver.findElement(By.xpath("//button[contains(text(),'Sales Reporting')]")).click();
			FlightSearch.loadhandling(driver);
			//Updated by Jenny
			//Selecting Agent Sales Report option
			driver.findElement(By.xpath("//md-menu-item[@ng-repeat='subMenu in landingOpt.subMenu']/button[contains(text(),'Agent Sales Report')]")).click();
			FlightSearch.loadhandling(driver);
			//Selecting Total Transaction amount link
			driver.findElement(By.xpath("//div/span[@translate='sr.transaction.amount']")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//div/button[contains(text(),'Close Report')]")).click();
			FlightSearch.loadhandling(driver);
			//Click Close Report on the confirm popup
			driver.findElement(By.xpath("//div[contains(@ng-if,'closeReportPopup.popupAction')]/button[@translate='sr.close.report']")).click();
			FlightSearch.loadhandling(driver);
			try{
				WebDriverWait wait = new WebDriverWait(driver, 5);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'No Open Reports')]")));
				//Click on close button
				driver.findElement(By.xpath("//i[@class='icon-close']/parent::div")).click();
				Thread.sleep(2000);
			}
			catch (Exception e) {}
			//Click on home buttonSales reporting button
			// driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
			Thread.sleep(2000);
			// driver.findElement(By.xpath("//button[contains(text(),'Reservations')]")).click();
			// driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);//60
		}
		catch (Exception e) {
			queryObjects.logStatus(driver, Status.INFO, "Sales report popup", e.getLocalizedMessage(), e);
			// driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);//60

		}

	}


}
