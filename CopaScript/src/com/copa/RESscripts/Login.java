package com.copa.RESscripts;


import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.ATOscripts.Atoflow;
import com.copa.Util.url_path;

import FrameworkCode.BFrameworkQueryObjects;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

public class Login extends url_path{

	WebDriverWait wait = null;
	static List<WebElement> closeReportBtn = new ArrayList<WebElement>();
	static List<WebElement> TotalcloseReportBtn = new ArrayList<WebElement>();
/*	public static final int PNRNUM = 0;
	public static final int FLIGHTNUM = 1;
	public static final int CURRENCY = 2;
	public static final int EMDNO = 3;
	public static final int EMDAmt=4;
	public static final int shareflightnm=5;
	public static final int shareflightnumssr=6;
	public static final int MultiplePNR=7;
	public static final int RETFLIGHTNUM = 8;
	public static final int RETSEGMENT = 9;*/
	
	public static  String PNRNUM = "";
	public static  String FLIGHTNUM = "";
	public static  String CURRENCY = "";
	public static  String EMDNO = "";
	public static  String EMDAmt="";
	public static  String shareflightnm="";
	public static  String shareflightnumssr="";
	public static String MultiplePNR="";
	public static  String RETFLIGHTNUM = "";
	public static  String RETSEGMENT = "";
	
	public static int Adsearch=0;
	public static  String Env=null;
	static String SalesOff =null;
	static String Cur=null;
	public static String Usernm=null;
	public static String Call_Center_Login=null;
	public static final int PNRDetails=0;
	public static String Change_Salesoffice="";
	public static String Change_Currency="";
	public static boolean CardDeposit=false;
	public static String CardDeposit_Change_Salesoffice="";
	public static String reissuetypeticktvalid = "";
	public void login(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{

		try{
			
			Atoflow.openflightflag=0;
			CardDeposit=false;
			Adsearch=1;
			FlightSearch.emditerr=0;
			reissuetypeticktvalid = "";
			/*Login.envwrite(PNRNUM, " ");
			Login.envwrite(FLIGHTNUM, " ");
			Login.envwrite(CURRENCY, " ");
			Login.envwrite(EMDNO, " ");
			Login.envwrite(EMDAmt, " ");
			Login.envwrite(shareflightnm, " ");
			Login.envwrite(shareflightnumssr, " ");
			Login.envwrite(MultiplePNR, " ");
			Login.envwrite(RETFLIGHTNUM, " ");
			Login.envwrite(RETSEGMENT, " ");*/
			
			PNRNUM = "";
			FLIGHTNUM = "";
			CURRENCY = "";
			EMDNO = "";
			EMDAmt="";
			shareflightnm="";
			shareflightnumssr="";
			MultiplePNR="";
			RETFLIGHTNUM = "";
			RETSEGMENT = "";

			Env=FlightSearch.getTrimTdata(queryObjects.getTestData("Env")); 
			if (Env.equalsIgnoreCase("SIT")) 
				driver.navigate().to(uSITurl);
			else if (Env.equalsIgnoreCase("DEV")|| Env.equalsIgnoreCase("JSIT")) 
				driver.navigate().to(uDevurl);
			else if (Env.equalsIgnoreCase("UAT")) 
				driver.navigate().to(uUATurl+"/css");
			else if (Env.equalsIgnoreCase("PER")) //performance environment.
				driver.navigate().to(uPERurl);
			
			Call_Center_Login=queryObjects.getTestData("Call_Center_Login");
			Usernm=queryObjects.getTestData("userName");

			Change_Salesoffice=queryObjects.getTestData("Change_Salesoffice").trim();
			Change_Currency=queryObjects.getTestData("Change_Currency").trim();
			CardDeposit_Change_Salesoffice=queryObjects.getTestData("CardDeposit_Change_Salesoffice").trim();

			driver.findElement(By.name("USER")).sendKeys(Usernm);
			driver.findElement(By.name("PASSWORD")).sendKeys(queryObjects.getTestData("password"));
			driver.findElement(By.name("submit")).click();
			Thread.sleep(10000);

			if (Env.equalsIgnoreCase("SIT")|| Env.equalsIgnoreCase("PER"))
				driver.findElement(By.xpath("//a[contains(text(),'css')]")).click();
				//driver.findElement(By.xpath("//a[contains(text(),'minorrelease')]")).click();
			else if (Env.equalsIgnoreCase("DEV"))
				//driver.findElement(By.xpath("//a[contains(text(),'copa-dev')]")).click();
				driver.findElement(By.xpath("//a[contains(text(),'copa-staging:12469')]")).click();
			//else if (Env.equalsIgnoreCase("UAT")) 
			//driver.findElement(By.xpath("//a[contains(text(),'css')]")).click();
			else if (Env.equalsIgnoreCase("JSIT"))
				driver.findElement(By.xpath("//a[@href='copa-jsit/']")).click();
			

			driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
			FlightSearch.loadhandling(driver);	
			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Reservations')]")));
			String Home = driver.findElement(By.xpath("//div[contains(text(),'Reservations')]")).getText();
			if (Home.trim().equals("Reservations")) {
				queryObjects.logStatus(driver, Status.PASS, "Login to COPA Application", "LoginSuccess", null);
				FlightSearch.loadhandling(driver);
				Boolean CloseReport=driver.findElements(By.xpath("//h2[contains(text(),'reminder')]")).size() >0;  
				if(CloseReport)  
					driver.findElement(By.xpath("//button[contains(@class,'md-confirm-button')]")).click();
				//closePrevDayReports(driver, queryObjects);
				SalesOff = getTrimTdata(queryObjects.getTestData("Salesoffice"));
				Cur = getTrimTdata(queryObjects.getTestData("Currency"));
				//Login.envwrite(CURRENCY, Cur);
				CURRENCY=Cur;
				if (!SalesOff.isEmpty()) {
					FlightSearch.loadhandling(driver);
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
				FlightSearch.loadhandling(driver);
				/*try {
					if (!SalesOff.isEmpty()) {
						if (driver.findElements(By.xpath("//div[contains(@class,'inset padding-right-0')]")).size()>0) {
							if (driver.findElement(By.xpath("//div[contains(@class,'inset padding-right-0')]")).getText().contains(SalesOff)) {
								queryObjects.logStatus(driver, Status.PASS, "Check given sales office details ", "Sales office is updated", null);
	
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Check given sales office details ", "Sales office is not updated", null);
							}
						}
					}
				}
				catch (Exception e) {
					queryObjects.logStatus(driver, Status.INFO, "Sales offce", e.getLocalizedMessage(), e);

				}*/
				Boolean CloseReport1=driver.findElements(By.xpath("//h2[contains(text(),'reminder')]")).size() >0;  //suman
				Thread.sleep(2000);
				Boolean CloseReport2=driver.findElements(By.xpath("//div[@action='saleOfficeInfo']/div[@class='padding-top']/i[contains(@class,'icon-warning pssgui')]")).size() >0;
				if(CloseReport1 ||CloseReport2)  //suman
					closePrevDayReports(driver, queryObjects);

				try {
					if (!SalesOff.isEmpty()) {
						if (driver.findElements(By.xpath("//div[contains(@class,'inset padding-right-0')]")).size()>0) {
							if (driver.findElement(By.xpath("//div[contains(@class,'inset padding-right-0')]")).getText().contains(SalesOff)) {
								queryObjects.logStatus(driver, Status.PASS, "Check given sales office details ", "Sales office is updated", null);
	
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Check given sales office details ", "Sales office is not updated", null);
							}
						}
					}
				}
				catch (Exception e) {
					queryObjects.logStatus(driver, Status.INFO, "Sales offce", e.getLocalizedMessage(), e);

				}
				
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

	public static String getTrimTdata(String inp1) throws Exception{
		String returnstring = "";
		if (inp1!=null){
			returnstring=inp1.trim();
		}
		return returnstring;
	}

	public static String envRead(int cellNum) throws IOException{

		String FilePath = pEnvExcelPath;

		FileInputStream input=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(input);
		XSSFSheet sh=wb.getSheet("Envvalue");
		XSSFCell c=sh.getRow(1).getCell(cellNum);
		DataFormatter format = new DataFormatter();

		input.close();
		return format.formatCellValue(c);

	}
	public static void envwritekishore(int cellNum, String value) throws IOException{

		String FilePath = pEnvExcelPath;

		FileInputStream input=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(input);
		XSSFSheet sh=wb.getSheet("Envvalue");
		/* int v=sh.getLastRowNum();
	        XSSFCell c=sh.getRow(v).getCell(cellNum);*/
		XSSFCell c=sh.getRow(1).getCell(cellNum);
		c.setCellValue(value);

		input.close();
		FileOutputStream output= new FileOutputStream(FilePath);
		wb.write(output);
		output.close();
	}
	public static int a;
	public static void appendData(int cellNum, String value) throws IOException{
		//public static int a;
		String FilePath = "C:/MSmart/Excel_Component/Test_Artifacts/TestData/Collect_PNR.xlsx";
		FileInputStream input=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(input);
		XSSFSheet sh=wb.getSheet("Envvalue");

		a=sh.getLastRowNum();
		Row row = sh.createRow(++a);
		row.createCell(cellNum).setCellValue(value);

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
			catch(Exception e){  System.out.println("ddd");}
			//Click on Reservations menu bar
			driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
			//click on sales reporting
			FlightSearch.loadhandling(driver);
			try{
				driver.findElement(By.xpath("//button[contains(text(),'Sales Reporting')]")).click();
				FlightSearch.loadhandling(driver);
				//Updated by Jenny
				//Selecting Agent Sales Report option
				driver.findElement(By.xpath("//md-menu-item[@ng-repeat='subMenu in landingOpt.subMenu']/button[contains(text(),'Agent Sales Report')]")).click();
				FlightSearch.loadhandling(driver);
			}
			catch(Exception e){
				driver.findElement(By.xpath("//md-menu-item[button[contains(text(),'Agent Sales Report')]]")).click();
				FlightSearch.loadhandling(driver);
			}
			//Selecting Total Transaction amount link
			driver.findElement(By.xpath("//div/span[@translate='sr.transaction.amount']")).click();
			FlightSearch.loadhandling(driver);
			
			driver.findElement(By.xpath("//div/button[contains(text(),'Close Report')]")).click();
			////Himani  _________________________________________________________
			try{				
				FlightSearch.loadhandling(driver);
				if(driver.findElements(By.xpath("//md-dialog//div//i[@class='icon-warning pssgui-design-status-critical']")).size()>0)
				{
					queryObjects.logStatus(driver, Status.PASS, "Variance Reason is not being given", "Not able to close Total Transaction amount", null);
					if(driver.findElements(By.xpath("//div[contains(text(),'Cannot close the report due to variance reason not selected')]")).size()>0){
						driver.findElement(By.xpath("//i[@class='icon-close']")).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//i[contains(@class,'toggle-arrow inset icon-forward')]")).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//md-select[contains(@ng-model,'paymentType.varianceReason')]")).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//md-content//md-option[2]/div[contains(text(),'total by range > $5')]")).click();
						try{
							driver.findElement(By.xpath("//button[contains(text(),'Close Report')]")).click();
							FlightSearch.loadhandling(driver);
							queryObjects.logStatus(driver, Status.PASS, "Variance Reason is being given", "Now able to close Total Transaction amount", null);
						}
						catch(Exception e){}
					}
				}				
			}
			catch(Exception e){
				queryObjects.logStatus(driver, Status.FAIL, "Variance Reason is not being given", "Not able to close Total Transaction amount", null);
			}				
			//_____________________________________________________________
			FlightSearch.loadhandling(driver); 
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
