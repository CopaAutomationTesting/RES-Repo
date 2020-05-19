package com.copa.RESscripts;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.Util.Assigh_role_pageobjects;
import com.copa.Util.FlightSearchPageObjects;

import FrameworkCode.BFrameworkQueryObjects;

public class Assign_role extends Assigh_role_pageobjects {

	String Username=null;
	String MultiRole=null;
	String singleRole=null;
	String Rolename=null;
	String city=null;
	String CityName=null;
	String Readonly=null;
	String readOnlyRole=null;
	String[] ROR;
	String[] role;
	String[] addcity;
	//String OneRole=null;
	String tmpxpath=null;
	String[] ctyname =null;
	String Privatefarecode=null;
	String Agentsine=null;

	public void role(WebDriver driver, BFrameworkQueryObjects queryObjects) throws InterruptedException, IOException
	{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		try {
			FlightSearch.loadhandling(driver);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(UserProvIcon)));
			driver.findElement(By.xpath(UserProvIcon)).click();
			//FlightSearch.loadhandling(driver);
			Boolean UserIdFieldValid=driver.findElements(By.xpath(UserIdField)).size()>0;
			if(!UserIdFieldValid) {
				queryObjects.logStatus(driver, Status.FAIL, "Checking the field on user account","Accessing the user Provesining Tool page", null);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block

			queryObjects.logStatus(driver, Status.FAIL, "Accessing the user Provesining Tool page", e.getLocalizedMessage(), e);
		}

		FlightSearch.loadhandling(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(UserIdField)));
		try
		{
			Username =getTrimTdata(queryObjects.getTestData("User"));

			driver.findElement(By.xpath(UserIdField)).sendKeys(Username);
			driver.findElement(By.xpath(SearchButton)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(AddCityButton)));
			if(driver.findElement(By.xpath(AddCityButton)).isDisplayed())
			{
				queryObjects.logStatus(driver, Status.PASS, "Verify the user page is corrrect or not","Verify the user page is corrrect", null);	
			}
			else
			{
				queryObjects.logStatus(driver, Status.FAIL, "Verify the user page is corrrect or not","Verify the user page is corrrect", null);
			}
		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.FAIL, "Checking the existing user","User does't exist->"+Username+ e.getLocalizedMessage(), e);
		}

		try {
			singleRole= getTrimTdata(queryObjects.getTestData("singleRole"));
			MultiRole= getTrimTdata(queryObjects.getTestData("MultiRole"));
			Rolename= getTrimTdata(queryObjects.getTestData("Rolename"));
			city= getTrimTdata(queryObjects.getTestData("city"));
			Readonly=getTrimTdata(queryObjects.getTestData("Readonly"));
			readOnlyRole=getTrimTdata(queryObjects.getTestData("readOnlyRole"));
			CityName= getTrimTdata(queryObjects.getTestData("CityName")).toUpperCase();
			Privatefarecode=getTrimTdata(queryObjects.getTestData("Privatefarecode"));
			Agentsine=getTrimTdata(queryObjects.getTestData("Agentsine"));
		} catch (Exception e) {

			queryObjects.logStatus(driver, Status.FAIL, "Getting the text data from excel","Unable to get data from Assign excel sheet->" +e.getLocalizedMessage(), e);
		}
		role = Rolename.split(";");
		ROR = readOnlyRole.split(";");
		addcity= readOnlyRole.split(";");
		ctyname=CityName.split(";");
		String[] Pfarecode=Privatefarecode.split(";");
		String[]  ASine=Agentsine.split(";");
		//OneRole= singleRole.spl(";");
		try
		{
			if(MultiRole.equalsIgnoreCase("Yes"))
			{     


				for(int i=0;i<role.length;i++)
				{  
					tmpxpath="//div[span[contains(text(),'Roles Mapped')]]/following-sibling::md-content[@class='roles-content _md']"; 
					//list of role already assign

					List<WebElement> rl=driver.findElements(By.xpath(tmpxpath+"//child::div/div/div"));
					List<String> assigendrole = new ArrayList<>();
					rl.forEach(a -> assigendrole.add(a.getText().trim()));
					int totalassignrole=rl.size();


					if(totalassignrole >0 && assigendrole.contains(role[i]))
					{
						//div[@role='button' and contains(text(),' Booking Specialist')]
						totalassignrole--;
					}
					else
					{
						driver.findElement(By.xpath("//div[@role='button' and contains(text(),'"+role[i]+"')]")).click();
					}

				}
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(linkUserButton)));
				driver.findElement(By.xpath(linkUserButton)).click();  

				tmpxpath="//div[span[contains(text(),'Roles Mapped')]]/following-sibling::md-content[@class='roles-content _md']"; 
				//list of role already assign

				List<WebElement> rl=driver.findElements(By.xpath(tmpxpath+"//child::div/div/div"));
				List<String> assigendrole = new ArrayList<>();
				rl.forEach(a -> assigendrole.add(a.getText().trim()));
				int totalassignrole=rl.size();
				for(int i=0;i<role.length;i++)   //change role.lenth to r1.length
				{
					// System.err.println(role[i]);
					if(assigendrole.contains(role[i]))
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking Role has assigned or not","Role has been assigned",null); 
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking Role has assigned or not","Role has not assigned",null);  
					}
				}	
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SaveButtonXpath)));
				if(driver.findElement(By.xpath(SaveButtonXpath)).isEnabled())
				{
					driver.findElement(By.xpath(SaveButtonXpath)).click();
					FlightSearch.loadhandling(driver);
					if(driver.findElements(By.xpath(FlightSearchPageObjects.loadXpath)).size()>0)
					{
						queryObjects.logStatus(driver, Status.INFO, "Checking the loading","Application is loading more time as expected",null); 
					}
				}
				else
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking button is enable or disable","Button is disable",null); 
				}


			}
			else if(Readonly.equalsIgnoreCase("Yes"))
			{
				for(int i=0;i<ROR.length;i++)
				{  
					List<WebElement> rl=driver.findElements(By.xpath(tmpxpath+"//child::div/div/div"));
					List<String> assigendrole = new ArrayList<>();
					rl.forEach(a -> assigendrole.add(a.getText().trim()));
					int totalassignrole=rl.size();



					if(totalassignrole >0 && assigendrole.contains(ROR[i]))
						totalassignrole--;
					else
						driver.findElement(By.xpath("//div[@role='button' and contains(text(),'"+ROR[i]+"')]")).click();

				}
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(linkUserButton)));
				driver.findElement(By.xpath(linkUserButton)).click();   

				tmpxpath="//div[span[contains(text(),'Roles Mapped')]]/following-sibling::md-content[@class='roles-content _md']"; 
				//list of role already assign

				List<WebElement> rl=driver.findElements(By.xpath(tmpxpath+"//child::div/div/div"));
				List<String> assigendrole = new ArrayList<>();
				rl.forEach(a -> assigendrole.add(a.getText().trim()));
				int totalassignrole=rl.size();
				for(int i=0;i<ROR.length;i++)
				{
					if(assigendrole.contains(ROR[i]))
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking Role has assigned or not","Role has been assigned",null); 
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking Role has assigned or not","Role has not assigned",null);  
					}
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SaveButtonXpath)));
					if(driver.findElement(By.xpath(SaveButtonXpath)).isEnabled())
					{
						driver.findElement(By.xpath(SaveButtonXpath)).click();
						FlightSearch.loadhandling(driver);
						if(driver.findElements(By.xpath(FlightSearchPageObjects.loadXpath)).size()>0)
						{
							queryObjects.logStatus(driver, Status.INFO, "Checking the loading","Application is loading more time as expected",null); 
						}
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking button is enable or disable","Button is disable",null); 
					}
				}
			}
			else if(city.equalsIgnoreCase("Yes")) 
			{
				try
				{
					List<WebElement> allcityrow=driver.findElements(By.xpath(AddedCityListXpath));
					List<String> cityalreadyadded = new ArrayList<>();
					allcityrow.forEach(a -> cityalreadyadded.add(a.getText().trim()));
					int totalassignrole=allcityrow.size();
					//loop for number of city pass from the excel
					for(int i=0;i<ctyname.length;i++)
					{
						//loop for checking the city is already present or not
						if(cityalreadyadded.contains(ctyname[i]))
						{
							queryObjects.logStatus(driver, Status.PASS, "City already Assign", "City is available to the user", null);
						}
						else
						{
							driver.findElement(By.xpath(AddCityButton)).click();
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EnterCityFieldXpath)));
							driver.findElement(By.xpath(EnterCityFieldXpath)).sendKeys(ctyname[i]);	
							//city list from dropdown
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SelectCityFromDropDownXpath)));
							driver.findElement(By.xpath(SelectCityFromDropDownXpath)).click();

							//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//md-input-container/label[contains(text(),'Private Fare Code')]")));
							if(!Pfarecode[i].equals(""))
							{
								driver.findElement(By.xpath(PrivateFareCodeXpath)).sendKeys(Pfarecode[i]);
							}
							//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("\"//md-input-container/label[contains(text(),'Agent Sine')]")));
							if(!ASine[i].equals(""))
							{
								driver.findElement(By.xpath(AgentSineField)).sendKeys(ASine[i]);;	

							}
							driver.findElement(By.xpath(OkButtonXpath)).click();
						}


					}
					for(int i=0;i<ctyname.length;i++)
					{    List<WebElement> allcityrow1=driver.findElements(By.xpath(AddedCityListXpath));
					List<String> cityadded1 = new ArrayList<>();
					allcityrow1.forEach(a -> cityadded1.add(a.getText().trim()));
					if(cityadded1.contains(ctyname[i]))
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking city assign list of user", "Now city is available for the user", null);
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking city assign list of user", "City is not available for user after adding.", null);
					}
					}

					/*wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SaveButtonXpath)));
		    		if(driver.findElement(By.xpath(SaveButtonXpath)).isEnabled())
		    		{
		    			driver.findElement(By.xpath(SaveButtonXpath)).click();
		    			//driver.findElement(By.xpath("//button[@ng-click='linkUser.saveUser()' and contains(text(),'Save')]")).click();
	    			FlightSearch.loadhandling(driver);
		    			if(driver.findElements(By.xpath("//div[contains(@class,'loading-wrapper')]")).size()>0)
    					{
    				 queryObjects.logStatus(driver, Status.FAIL, "Checking the loading","Application is loading more time as expected",null); 
    					}
		    		}
		    		else
		    		{
		    			queryObjects.logStatus(driver, Status.FAIL, "Checking button is enable or disable","Button is disable",null); 
		    		}
					 */
				}

				catch( Exception e)
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking city is assign for User","Fail while Assign City to user"+Username+"->"+e.getStackTrace()[0].getLineNumber(),e); 	
				}
			}
			if(singleRole.equalsIgnoreCase("Yes"))
			{

				for(int i=0;i<role.length;i++)
				{  
					tmpxpath="//div[span[contains(text(),'Roles Mapped')]]/following-sibling::md-content[@class='roles-content _md']"; 
					//list of role already assign

					List<WebElement> rl=driver.findElements(By.xpath(tmpxpath+"//child::div/div/div"));
					List<String> assigendrole = new ArrayList<>();
					rl.forEach(a -> assigendrole.add(a.getText().trim()));
					int totalassignrole=rl.size();


					if(totalassignrole >0 && assigendrole.contains(role[i]))
					{
						//div[@role='button' and contains(text(),' Booking Specialist')]
						totalassignrole--;
					}
					else
					{
						driver.findElement(By.xpath("//div[@role='button' and contains(text(),'"+role[i]+"')]")).click();
					}

				}
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(linkUserButton)));
				driver.findElement(By.xpath(linkUserButton)).click();

				//again checking the role has asigned or not
				tmpxpath="//div[span[contains(text(),'Roles Mapped')]]/following-sibling::md-content[@class='roles-content _md']"; 
				//list of role already assign

				List<WebElement> rl=driver.findElements(By.xpath(tmpxpath+"//child::div/div/div"));
				List<String> assigendrole = new ArrayList<>();
				rl.forEach(a -> assigendrole.add(a.getText().trim()));
				int totalassignrole=rl.size();
				for(int i=0;i<role.length;i++)
				{
					if(assigendrole.contains(role[i]))
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking Role has assigned or not","Role has been assigned",null); 
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking Role has assigned or not","Role has not assigned",null);  
					}
				}
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SaveButtonXpath)));
				Boolean bo=driver.findElement(By.xpath(SaveButtonXpath)).isEnabled();
				if(bo)
				{
					driver.findElement(By.xpath(SaveButtonXpath)).click();
					//driver.findElement(By.xpath("//button[@ng-click='linkUser.saveUser()' and contains(text(),'Save')]")).click();
					FlightSearch.loadhandling(driver);
					if(driver.findElements(By.xpath(FlightSearchPageObjects.loadXpath)).size()>0)
					{
						queryObjects.logStatus(driver, Status.INFO, "Checking the loading","Application is loading more time as expected",null); 
					}

				}
				else
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking button is enable or disable","Button is disable",null); 
				}
			}   


		}
		catch(Exception e)
		{

			if(Readonly.equalsIgnoreCase("Yes") || MultiRole.equalsIgnoreCase("Yes") || singleRole.equalsIgnoreCase("yes")) 
				queryObjects.logStatus(driver, Status.FAIL, "checking the role for user is added successfully","Role has not added successfuly->" +e.getLocalizedMessage(), e);
			else
				queryObjects.logStatus(driver, Status.FAIL, "add a city for User","Unable to add the city to User->" +e.getLocalizedMessage(), e);  
		}  

		finally
		{    
			String Successfullmessage="Successfully saved user";
			String	retval=driver.findElement(By.xpath("//span[contains(@ng-class,'msg-error')]")).getText().trim();
			if(driver.findElements(By.xpath("//span[contains(@ng-class,'msg-error')]")).size()>0 && !(retval.contains(Successfullmessage)))
			{

				queryObjects.logStatus(driver, Status.FAIL, "Getting Error message form application", "Getting Error message form application " +retval,null);	
			}

		}
	}


	public String getTrimTdata(String inp1) throws Exception{
		String returnstring = "";
		if (inp1!=null){
			returnstring=inp1.trim();
		}
		return returnstring;
	}
}
