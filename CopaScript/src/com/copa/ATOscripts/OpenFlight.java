package com.copa.ATOscripts;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.Status;

import FrameworkCode.BFrameworkQueryObjects;

public class OpenFlight  implements Runnable
{
	String FligthNum="";
	String Orgin="";
	String date="";
	WebDriver driver=null;
	BFrameworkQueryObjects queryObjects;

			public  OpenFlight(String FligthNum, String Orgin, String date,WebDriver driver,BFrameworkQueryObjects queryObjects) {
				
				
				this.FligthNum=FligthNum;
		        this.Orgin=Orgin;
		        this.date=date;
		        this.driver=driver;
		        this.queryObjects=queryObjects;
		       // run();
	        }

			public void run(){
				try {
					JavascriptExecutor jse = (JavascriptExecutor)driver;
					jse.executeScript("window.open()");
					
					String maintab=driver.getWindowHandle();
					
					
					String mostRecentWindowHandle="";
					for(String winHandle:driver.getWindowHandles()){
				        mostRecentWindowHandle = winHandle;        
				    }
					driver.switchTo().window(mostRecentWindowHandle);
					driver.get("http://tpfsb.intvm.sys.eds.com");
					driver.findElement(By.xpath("//input[@name='ID']")).sendKeys("wzqltv");   // I share User name
					driver.findElement(By.name("Password")).sendKeys("prayer08");    // I share psw
					driver.findElement(By.xpath("//input[@value='Login']")).click();
					driver.findElement(By.xpath("//a[contains(text(),'Terminal Emulation')]")).click();
					
					WebDriverWait wait = new WebDriverWait(driver, 100);
					
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
						driver.findElement(By.name("q")).sendKeys("logc cmre");
						driver.findElement(By.xpath("//input[@value='Send']")).click();
						driver.findElement(By.name("q")).sendKeys("bsib");
						String IshLogMsg = (driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText()).trim();
					
						driver.findElement(By.xpath("//input[@value='Send']")).click();
						Thread.sleep(1000);
						WebElement ele=driver.findElement(By.xpath("//select[@id='Type']"));
						Select sel=new Select(ele);
						sel.selectByVisibleText("2");
						String[] DateDayMon=null;
						String day="";
						try {
							DateDayMon=date.split("-");
							day=DateDayMon[0]+DateDayMon[1];
						} catch (Exception e) {
							day = date;
						}
						String temp=FligthNum;
						String	Flight=temp.replaceAll("([A-Z])", "");
						
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						driver.findElement(By.name("q")).sendKeys("6-*A"+Flight);
						
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						driver.findElement(By.name("q")).sendKeys("/"+day+Orgin);
						driver.findElement(By.xpath("//input[@value='Send']")).click();						
						String equpno=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
						
						String[] parts = equpno.split(Flight);
						String part1 = parts[0]; 
						String part2 = parts[1].trim();
						
						String[] parts1 = part2.split(" ");
						String part3 = parts1[0].trim(); 
						String part4 = parts1[1].trim();
						
						driver.findElement(By.name("q")).sendKeys("6-I*F"+part3);
						driver.findElement(By.xpath("//input[@value='Send']")).click();
						
						String Fleet=driver.findElement(By.xpath("//pre[@id='content-wrap']")).getText().trim();
						
						//Assign Shipment
						String fleetequp=Fleet.substring(Fleet.lastIndexOf(" ")+1);
						String entry="6-CA"+day+"\n"+Flight+Orgin+fleetequp;
						
						JavascriptExecutor myExecutor = ((JavascriptExecutor)driver);
						WebElement TextBox = driver.findElement(By.name("q"));
						TextBox.getAttribute("value");
						myExecutor.executeScript("arguments[1].value = arguments[0]; ", entry, TextBox); 
												
						driver.findElement(By.xpath("//input[@value='Send']")).click();
						//Open Flight
						driver.findElement(By.name("q")).sendKeys("6-CO"+Flight+"/"+day+Orgin+".F66000");
						driver.findElement(By.xpath("//input[@value='Send']")).click();
						
						driver.close();
						driver.switchTo().window(maintab);
	
					
								try {
									queryObjects.logStatus(driver, Status.PASS,"Flight Opened","Flight number is:: "+FligthNum,null);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
				/*}
				catch(Exception e){
					try {
						queryObjects.logStatus(driver, Status.FAIL,"Flight Opened","Open flight exeception",e);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}*/
				} catch (Exception e) {
					try {
						queryObjects.logStatus(driver, Status.FAIL, "Open Flight", "Unable to Open flight, Flight Open failed", e);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
					
				
			}

}
