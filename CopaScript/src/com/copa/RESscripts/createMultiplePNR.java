
package com.copa.RESscripts;


import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.Status;
import FrameworkCode.BFrameworkQueryObjects;
public class createMultiplePNR{
	static String FirstFlightNo=null;
	static String FirstFlightFrom=null;
	static String FirstFlightTo=null;
	static String SecondFlightNo=null;
	static String SecondFlightFrom=null;
	static String SecondFlightTo=null;
	static String FirstFlightDate=null;
	static String SecondFlightDate=null;
	static String Class=null;
	static String FirstName=null;
	static String SurName=null;

	public void Isharelogin(WebDriver driver, BFrameworkQueryObjects queryObjects) {
		try
		{  
			if(true)
			{
				
				WebDriverWait wait = new WebDriverWait(driver, 100);
				try{
					//driver.get("http://tpfsb.intvm.sys.eds.com");  //copa
					driver.get("http://tpfsa.intvm.sys.eds.com/ ");   //Ua
					
					driver.findElement(By.xpath("//input[@name='ID']")).sendKeys("wzqltv");   // I share User name
					driver.findElement(By.name("Password")).sendKeys("prayer08");    // I share psw
					driver.findElement(By.xpath("//input[@value='Login']")).click();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Terminal Emulation')]")));
					driver.findElement(By.xpath("//a[contains(text(),'Terminal Emulation')]")).click();
					queryObjects.logStatus(driver, Status.PASS, "Login to Ishares", "Login to Ishares Successful", null);
					try{
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
						//driver.findElement(By.name("q")).sendKeys("logc cmre");	//copa
						driver.findElement(By.name("q")).sendKeys("logc uare"); //ua
						driver.findElement(By.xpath("//input[@value='Send']")).click();
						//driver.findElement(By.name("q")).sendKeys("bsib");	//copa
						driver.findElement(By.name("q")).sendKeys("bsia");	//UA
						String IshLogMsg = (driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText()).trim();
						driver.findElement(By.xpath("//input[@value='Send']")).click();
//						//copa
//						if( driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().contains("B-SINE COMPLETE")||driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().contains("B-IN USE")){	//indrajit
//							queryObjects.logStatus(driver, Status.PASS, "Checking the login Ishare application", "Login to IShare application", null);
//						}
						//UA
						if( driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().contains("A-SINE COMPLETE")||driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().contains("A-IN USE")){	//indrajit
							queryObjects.logStatus(driver, Status.PASS, "Checking the login Ishare application", "Login to IShare application", null);
						}
						else{
							queryObjects.logStatus(driver, Status.FAIL, "Checking the login Ishare application", "Getting an error while login to ISHARE applicatio", null);;
						}
					}
					catch(Exception e){
						queryObjects.logStatus(driver, Status.FAIL, "Login to Ishares", "Login CMRE Ishares UN-Successfully", null);	
					}
				}catch(Exception e){
					queryObjects.logStatus(driver, Status.FAIL, "Login to Ishares", "Login to Ishares Failed", null);	
				}
			}
		}
		catch (Exception e) {
		}
	}
	public void Pnrdata(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException {
		FirstFlightNo = queryObjects.getTestData("FirstFlightNo");
		FirstFlightTo = queryObjects.getTestData("FirstFlightTo");
		FirstFlightFrom= queryObjects.getTestData("FirstFlightFrom");
		SecondFlightNo = queryObjects.getTestData("SecondFlightNo");
		SecondFlightTo = queryObjects.getTestData("SecondFlightTo");
		SecondFlightFrom= queryObjects.getTestData("SecondFlightFrom");
		FirstFlightDate=queryObjects.getTestData("FirstFlightDate");
		SecondFlightDate=queryObjects.getTestData("SecondFlightDate");
		Class = queryObjects.getTestData("Class");
		FirstName = queryObjects.getTestData("FirstName");
		SurName = queryObjects.getTestData("SurName");
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			//driver.findElement(By.name("q")).sendKeys("0CM "+FirstFlightNo+Class+" "+FirstFlightDate+" "+FirstFlightFrom+FirstFlightTo+" NN1");	//CM
			driver.findElement(By.name("q")).sendKeys("0UA "+FirstFlightNo+Class+" "+FirstFlightDate+" "+FirstFlightFrom+FirstFlightTo+" NN1");	//UA
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			if(driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().contains("SS1")){
				queryObjects.logStatus(driver, Status.PASS, "Specific seat available", "First Segment done", null);
			}
			else{
				queryObjects.logStatus(driver, Status.FAIL, "Specific seat not available", "First Segment", null);
				return; //if no data exist
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			//driver.findElement(By.name("q")).sendKeys("0CM "+SecondFlightNo+Class+" "+SecondFlightDate+" "+SecondFlightFrom+SecondFlightTo+" NN1");	//CM
			driver.findElement(By.name("q")).sendKeys("0UA "+SecondFlightNo+Class+" "+SecondFlightDate+" "+SecondFlightFrom+SecondFlightTo+" NN1");	//UA
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			if(driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().contains("SS1")){
				queryObjects.logStatus(driver, Status.PASS, "Specific seat available", "Second Segment done", null);
			}
			else{
				queryObjects.logStatus(driver, Status.FAIL, "Specific seat not available", "Second Segment", null);
				return;  //if no data exist
			}
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Flight Avaialblity fail", "FAIL", null);
			// TODO: handle exception
		}
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			driver.findElement(By.name("q")).sendKeys("-"+FirstName+"/"+SurName+"");
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			driver.findElement(By.name("q")).sendKeys("6P|7T/|9PTY/N000|ER");
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			queryObjects.logStatus(driver, Status.PASS, "Name and agent info Entered", "PNR created", null);

		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Name Entered", "name given", null);
			// TODO: handle exception
		}
		try {
			String output=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
			System.out.println(output);
			String[] pnr=output.split("\n");
			String pnrdetails="";
			pnrdetails=pnr[0]+" "+FirstName+"/"+SurName+" "+FirstFlightNo+" "+Class+" "+FirstFlightDate+" "+SecondFlightNo+" "+Class+" "+SecondFlightDate;
			Login.appendData(Login.PNRDetails,pnrdetails);
			System.out.println(pnr[0]);

			//driver.findElement(By.name("q")).sendKeys("T-$CC4444333322221111/1109/N1234|ET");	//card
			driver.findElement(By.name("q")).sendKeys("T-$|ET");								//cash
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			driver.findElement(By.name("q")).sendKeys("IR");
			driver.findElement(By.xpath("//input[@value='Send']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			driver.findElement(By.name("q")).sendKeys("I");
			driver.findElement(By.xpath("//input[@value='Send']")).click(); 



		} catch (Exception e) {
			// TODO: handle exception
		}


		//	 	     Login.envwrite(Login.shareflightnumssr, finalssradded);
	}


	public String getTrimTdata(String inp1) throws Exception{
		String returnstring = "";
		if (inp1!=null){
			returnstring=inp1.trim();
		}
		return returnstring;
	}






}




