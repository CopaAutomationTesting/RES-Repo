

package com.copa.RESscripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.Util.FlightSearchPageObjects;
import com.copa.Util.url_path;

import FrameworkCode.BFrameworkQueryObjects;
import com.copa.ATOscripts.Atoflow;
import com.copa.ATOscripts.ISharesflow;

public class FlightSearch extends FlightSearchPageObjects{

	static String fareAmount="0";
	static String currencyType;
	static boolean Manualquotecase;
	static String ffplastName;
	static String ffpfirstName;
	static boolean FFpPax=false;
	
	static String FFcode;
	static String FFnum;
	static double emdAmount;
	static int totalnoofPAX=0;
	static String Worldpay_Merchant_code="";
	
	String	arySegments[];
	String sourseDest[];
	String arrFlighNos[];
	
	String SegSpecClasstypeArr[];
	String SpecifitclassArr[];
	static int Segmt=0;
	String sgetflighno;
	static String surname = null;
	static String firstName = null;
	static String wordpay_surname = null;
	static String wordpay_firstName = null;
	//String gender = null;
	String genderM = "Male";   //Himani
	String genderF = "Female";   //Himani
	String email = null;
	String nation = null;
	String phonetype1 = null;
	String countryCode = null;
	String age;
	String fop = null;
	String multipleFOP = null;
	public static String Pnris=null;
	public static boolean Re_Assignseat=false;
	String paxReductionType;
	String paxReductionTypeCount;
	String paxReductionTypeName;
	String[] paxtypes;
	static double AfterWaiver;
	static String Waiverfare[];
	String Servicename = null;
	
	boolean detailsEntered ;
	boolean sameSurname = false;
	static boolean pnrcreated=false;
	static boolean isSeatAssigned=false;

	//Varibales for multiple FOP
	int viewhandel=0;
	int totalFemale=0;
	int totalMale=0;
	String MultipleFOP=null;
	String totalAmount=null;

	static String MultipleFOPType=null;
	static String MultFOPsubType=null;
	static String fopCardNums = null;

	String CCVnums=null;
	String partialAmount;
	String envFlight="";
	Double totalPayment;

	static String CyberPNR;
	static String BillerInfo = "";
	static String SplitBillerDet[];
	static String SplitBillerMore[];
	static String SplitName[];
	static String PNRTempPath = null;
	static String PNRTempPath2 = null;
	int Creditlength=0;
	static int emditerr=0;   // this is for tacking emd numbersss
	String[] FOPtype;
	String[] subType;
	String[] fopCardNum;
	String[] CCVnumber;
	static String seatamt;
	List<String> etktsnum;
	
	static List<String> carddetails_name=new ArrayList<>();
	static List<String> carddetails_number=new ArrayList<>();
	public static List<String> TktStatus=new ArrayList<>();  //jenny
	static List<Double> PayingAmount;

	//Variables for SSRs
	static String SSRadd = null;
	static String beforePay = null;
	static String afterPay = null;
	static String totSSRs = null;
	static String ssrNames = null;
	static String ssrXpath = null;
	static String ssrCode = null;

	static String ssrAmount = null;

	static double balDueBeforeAddSSR;
	static String balDueAfterAddSSR;
	static double ssrPrice;

	static String[] SSRname = null;

	static boolean EMDCreated = false;
	
	static int Emdcount=0;
	static String Pnrstate=null;
	public static List<String> gettecketno= new ArrayList<>();
	public static List<String> gettecketamt= new ArrayList<>();

	static List<String> getemdno= new ArrayList<>();
	static List<String> getemdamt= new ArrayList<>();
	static List<String> Listssrname=new ArrayList<>();
	static String ppspaymentmodesingle="";
	static String ppspaymentmodedouble="";
	static String tickttkype=null;
	String ManulaQuote_Fare_amt="0";
	String ManualQuote_Euivalent_amt="0";
	public static String KTNvalue="";
	Double Service_Fee=0.0;
	
	static Double TotalRefundamt=0.0;
	static Double PanaltyAmount=0.0;
	static Double Fare_Diff=0.0;
	static int PPSBillDet=0;
	static boolean Aftecybebersourec_Update_check_GUI=false;
	public static String ResidualEMD="";
	public static String ResidualAmt="";
	public static String EnrollConnect="";
	public static String EnrollPax="";
	static boolean conjunctiveTicketManualreissue=false;
	/**
	 * @param driver
	 * @param queryObjects
	 * @throws IOException
	 */
	public void PnrCreation(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException{
		try{
			/*if (true) 
				return;*/
			conjunctiveTicketManualreissue=false;
			carddetails_name=	new ArrayList<>();
			carddetails_number=	new ArrayList<>();
			
			gettecketno = new ArrayList<>();
			gettecketamt = new ArrayList<>();
			FlightSearch.TktStatus.clear();
			PayingAmount=new ArrayList<>();
			
			boolean ManualQuote = false;
			
			pnrcreated=false;
			
			TotalRefundamt=0.0;
			PanaltyAmount=0.0;
			Fare_Diff=0.0;
			
			FFpPax=false;
			
			Re_Assignseat=false;
			Service_Fee=0.0;
			Manualquotecase=false;
			detailsEntered = false;
			FlightSearch.MultipleFOPType="";
			FlightSearch.MultFOPsubType="";
			FlightSearch.fopCardNums="";
			ppspaymentmodesingle="";
			ppspaymentmodedouble="";
			Worldpay_Merchant_code="";
			Aftecybebersourec_Update_check_GUI=false;
			
			String DestnTime = "";
			String OrgTime = "";
			EnrollConnect=getTrimTdata(queryObjects.getTestData("EnrollConnect"));
			EnrollPax=getTrimTdata(queryObjects.getTestData("EnrollPaxcnt"));
			
			emdAmount=0;
			Listssrname=new ArrayList<>();
			Listssrname.add("PETC");Listssrname.add("SPML"); Listssrname.add("SPEQ");Listssrname.add("UMNR");  // add EMD A services in this list
			Listssrname.add("XBAG");Listssrname.add("XBAG"); Listssrname.add("EXST");
			Listssrname.add("PDBG");Listssrname.add("AOXY"); Listssrname.add("2NDB"); 
			Listssrname.add("KSML");Listssrname.add("BIKE");Listssrname.add("SEMN");Listssrname.add("GFML");
			Listssrname.add("FPML");Listssrname.add("OTHS");Listssrname.add("CHML");Listssrname.add("DEPU");
			Listssrname.add("WCHC");Listssrname.add("DEPA");Listssrname.add("WCLB");Listssrname.add("VIPS");
			Listssrname.add("VGML");Listssrname.add("MEQT");Listssrname.add("MAAS");Listssrname.add("MEDA");
			Listssrname.add("SVAN");Listssrname.add("WEAP");Listssrname.add("WCHS");Listssrname.add("INFE"); 
			Listssrname.add("TKTL");
			//envFlight=(Login.envRead(Login.shareflightnm)).trim();
			
			envFlight=(Login.shareflightnm).trim();
			Pnrstate="Issue";
			String Residual_Emd = FlightSearch.getTrimTdata(queryObjects.getTestData("Residual_EMD"));
			WebDriverWait wait = new WebDriverWait(driver, 50);
			//Click on home buttonSales reporting button
			driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Reservations')]")).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='New Order']")));
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(queryObjects.getObject("Reservation", Locators.XPATH))));

			driver.findElement(By.xpath("//span[@class='res-home-text ng-scope']")).click();
			Thread.sleep(500);

			//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //30
			driver.findElement(By.xpath("//div[text()='New Order']")).click();
			String  Duration = getTrimTdata(queryObjects.getTestData("Roudtrip_Duration"));
			String sNoseg=getTrimTdata(queryObjects.getTestData("Noseg"));

			String  Roundtrip = getTrimTdata(queryObjects.getTestData("RoundTrip"));
			int iterNoseg = 2;
			int totlsegments=1;
			int iter;
			int Noseg=0;
			if (!sNoseg.equals("")) {
				Noseg=Integer.parseInt(sNoseg);	
			}

			if (Roundtrip.equals("YES")) {
				totlsegments=totlsegments+1;
			}
			if (Noseg>0) {
				String  Segments = getTrimTdata(queryObjects.getTestData("Segments"));
				arySegments=Segments.split(";");
				// add verification point for test data validation......
				totlsegments=Noseg+totlsegments;
			}
			//Entering segment Date
			String sgetDate=getTrimTdata(queryObjects.getTestData("Days"));
			int getDays=0;
			if (sgetDate.isEmpty())
				getDays=30;
			else
				getDays=Integer.parseInt(sgetDate);

			Calendar calc = Calendar.getInstance();
			calc.add(Calendar.DATE, +getDays);
			String timeStampd = new SimpleDateFormat("MM/dd/yyyy").format(calc.getTime());

			WebElement Originfirst=driver.findElement(By.xpath(OriginfirstXpath));
			Originfirst.clear();
			Originfirst.sendKeys(queryObjects.getTestData("Origin"));
			//Originfirst.sendKeys(Keys.ENTER);
			Thread.sleep(1000);
			driver.findElement(By.xpath(clickUSpopuXpath)).click();
			WebElement Destinationfirst=driver.findElement(By.xpath(DestinationfirstXpath));
			String Dest=queryObjects.getTestData("Destination");
			Destinationfirst.sendKeys(Dest);
			//Destinationfirst.sendKeys(Keys.ENTER);
			Thread.sleep(1000);
			driver.findElement(By.xpath(clickUSpopuXpath)).click();
			//Login.envwrite(Login.RETSEGMENT, Dest);
			Login.RETSEGMENT=Dest;
			if (getDays!=0) {
				driver.findElement(By.xpath(DateXpath)).clear();
				driver.findElement(By.xpath(DateXpath)).sendKeys(timeStampd);
			}
			Thread.sleep(1000);
			String sgettime=getTrimTdata(queryObjects.getTestData("Time"));
			if (sgettime.isEmpty())
				sgettime="Any";

			driver.findElement(By.xpath(TimeXpath)).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-clickable')]//div[contains(text(),'"+sgettime+"')]/parent::md-option")).click();

			if (Roundtrip.toUpperCase().equals("YES")){

				driver.findElement(By.xpath(RoundtripXpath)).click();	
				Calendar retDate = Calendar.getInstance();
				iterNoseg=iterNoseg+1;
				//Updated by Jenny - Oct 2019
				if (Duration!="" && !Duration.contains("Same")) {
					retDate.add(Calendar.DATE, (getDays+Integer.parseInt(Duration)));
				}else {
					retDate.add(Calendar.DATE, (getDays+3));
				}
				String timeStamp1retDate = new SimpleDateFormat("MM/dd/yyyy").format(retDate.getTime());
				driver.findElement(By.xpath(DestinationSecondXpath)).clear();
				driver.findElement(By.xpath(DestinationSecondXpath)).sendKeys(timeStamp1retDate);
			}
			if (Noseg>0){
				int seg=0;
				for (iter = iterNoseg; iter <= totlsegments; iter++) {
					sourseDest=arySegments[seg].split("-");
					Calendar calcseg = Calendar.getInstance();
					//Updated by Jenny - Oct 2019
					int v=2*(iter-1);
					if (Duration!="" && Duration.contains("Same")) {
						calcseg.add(Calendar.DATE, (getDays+v+Integer.parseInt(Duration)));
						//calcseg.add(Calendar.DATE, (getDays+iter+Integer.parseInt(Duration)));
					}else {
						
						calcseg.add(Calendar.DATE, (getDays+v+3+1));
						//calcseg.add(Calendar.DATE, (getDays+iter+3+1));
					}
					//calcseg.add(Calendar.DATE, (getDays+iter+1));;
					String timeStamp1 = new SimpleDateFormat("MM/dd/yyyy").format(calcseg.getTime());
					driver.findElement(By.xpath(addsegmentXpath)).click();
					WebElement Origin=driver.findElement(By.xpath("//div[@ng-form='availabilityForm']/div["+iter+"]//div[contains(@class,'origin')]//input"));
					Origin.clear();
					//Origin.sendKeys(queryObjects.getTestData("Origin"));
					Origin.sendKeys(sourseDest[0]);
					//Origin.sendKeys(Keys.ENTER);
					Thread.sleep(1000);
					driver.findElement(By.xpath(clickUSpopuXpath)).click();
					WebElement Destination=driver.findElement(By.xpath("//div[@ng-form='availabilityForm']/div["+iter+"]//div[contains(@class,'destn')]//input"));
					Destination.clear();
					//Destination.sendKeys(queryObjects.getTestData("Destination"));
					Destination.sendKeys(sourseDest[1]);
					//Destination.sendKeys(Keys.ENTER);
					Thread.sleep(1000);
					driver.findElement(By.xpath(clickUSpopuXpath)).click();
					driver.findElement(By.xpath("//div[@ng-form='availabilityForm']/div["+iter+"]//div[contains(@class,'datepicker')]//input")).clear();
					driver.findElement(By.xpath("//div[@ng-form='availabilityForm']/div["+iter+"]//div[contains(@class,'datepicker')]//input")).sendKeys(timeStamp1);
					Thread.sleep(1000);
					seg=seg+1;
				}
			} //(Noseg>0)

			// Availability type : Redemption availability  (using for selecing Z class)
			String Availability_Type=getTrimTdata(queryObjects.getTestData("Availability_Type"));
			if (!Availability_Type.isEmpty()) {  // if it is not empty select particual option..
				driver.findElement(By.xpath("//md-select[@aria-label='Availability type']")).click();
				Thread.sleep(2000);
				if (Availability_Type.equals("Redemption availability")) 
					driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false' ]//child::md-option[div[contains(text(),'Redemption availability')]]")).click();
				else if (Availability_Type.equals("Alliance availability"))
					driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false' ]//child::md-option[div[contains(text(),'Alliance availability')]]")).click();
				else if (Availability_Type.equals("Carrier specific availability"))
					driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false' ]//child::md-option[div[contains(text(),'Carrier specific availability')]]")).click();
				else if (Availability_Type.equals("Any availability"))
					driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false' ]//child::md-option[div[contains(text(),'Any availability')]]")).click();
				else
					queryObjects.logStatus(driver, Status.FAIL, "Flight search Availability type checking", "Test Data/script issue Given value not available", null);	
			}
			 
			
			// Enter PAX type

			String sAdult=getTrimTdata(queryObjects.getTestData("Adult"));
			String sChil=getTrimTdata(queryObjects.getTestData("Child"));
			String sInfwithoutseat=getTrimTdata(queryObjects.getTestData("Infwithoutseat"));
			String sInfwithseat=getTrimTdata(queryObjects.getTestData("infwithseat"));
			String sClasstype=getTrimTdata(queryObjects.getTestData("classType"));
			String SegSpecClasstype=getTrimTdata(queryObjects.getTestData("SegSpecificClass"));
			String Specifitclass=getTrimTdata(queryObjects.getTestData("Specific"));
			String ClassNm;
			if (sClasstype.toUpperCase().equalsIgnoreCase("BUSINESS")) {
				ClassNm="BUSINESS";
			}
			else if (sClasstype.toUpperCase().equalsIgnoreCase("ECONOMY")) {
				ClassNm="ECONOMY";
			}
			else if (sClasstype.toUpperCase().equalsIgnoreCase("SPECIFIC")) {
				if (!Specifitclass.equalsIgnoreCase("")){
					ClassNm="SPECIFIC";
					Specifitclass=Specifitclass.toUpperCase();
				}
				else
					ClassNm="ANY";
				//Krishna - Get the Specific Trip Class Type for Specific Segment 	
			}else if (sClasstype.toUpperCase().equalsIgnoreCase("SEGSPECIFIC")) {
					ClassNm="";
					SegSpecClasstypeArr = SegSpecClasstype.split(";"); 
					SpecifitclassArr = Specifitclass.split(";");
			}
			else{
				ClassNm="ANY";
			}

			int totalpax=0;
			totalnoofPAX=0;
			driver.findElement(By.xpath(AduldnameXpath)).clear();
			if (!sAdult.equals("")) {
				driver.findElement(By.xpath(AduldnameXpath)).sendKeys(sAdult);
				totalpax=totalpax+Integer.parseInt(sAdult);
				totalnoofPAX=totalnoofPAX+Integer.parseInt(sAdult);
			} 

			if (!sChil.equals("")) {
				driver.findElement(By.xpath(ChildXpath)).clear();
				driver.findElement(By.xpath(ChildXpath)).sendKeys(sChil);
				totalpax=totalpax+Integer.parseInt(sChil);
				totalnoofPAX=totalnoofPAX+Integer.parseInt(sChil);
			}
			if (!sInfwithoutseat.equals("")) {
				driver.findElement(By.xpath(sInfwithoutseatXpath)).clear();
				driver.findElement(By.xpath(sInfwithoutseatXpath)).sendKeys(sInfwithoutseat);
				totalnoofPAX=totalnoofPAX+Integer.parseInt(sInfwithoutseat);
			}
			if (!sInfwithseat.equals("")) {
				driver.findElement(By.xpath(sInfwithseatXpath)).clear();
				driver.findElement(By.xpath(sInfwithseatXpath)).sendKeys(sInfwithseat);
				totalpax=totalpax+Integer.parseInt(sInfwithseat);
				totalnoofPAX=totalnoofPAX+Integer.parseInt(sInfwithseat);
			}

			//Calling method to add FFP before performing search
			String addFFP = getTrimTdata(queryObjects.getTestData("AddFFPbeforeSearch"));

			int totFFPbeforeSearch = 0;
			if(addFFP.equalsIgnoreCase("yes"))
				totFFPbeforeSearch = addFFPBeforeSearch(driver,queryObjects);

			// click on search button
			driver.findElement(By.xpath(searchbuttonXpath)).click();

			//WebDriverWait wait1 = new WebDriverWait(driver, 80);
			//wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(viewXpath)));
			loadhandling(driver);
			loadhandling(driver);
			// Flight Selection Logic....................
			// this list is Number of Trips (PTY-SJO , SJO-PTY)
			java.util.List<WebElement> trips=driver.findElements(By.xpath(numberoftripsXpath));
			int nuberofTrips=trips.size();

			Boolean classselectbon=false;
			String getSelected_Date="";
			Boolean break_1SEg_1Dayget=false;
			for (int tripiter = 0; tripiter < nuberofTrips; tripiter++) {
				
				//Check the next segment time -Jenny 21 Oct 2019
				if (tripiter>0) {
					int SelCnt = 0; 
					SelCnt = driver.findElements(By.xpath("//i[@class='icon-forward arrow ng-scope']/../following-sibling::div[1]/div[@ng-if='originDestination.showDateTime']")).size();
					DestnTime = driver.findElement(By.xpath("(//i[@class='icon-forward arrow ng-scope']/../following-sibling::div[1]/div[@ng-if='originDestination.showDateTime'])["+SelCnt+"]")).getText();
				}
				viewhandel=0;
				trips.get(tripiter).click();
				loadhandling(driver);
				String sFlighNos=getTrimTdata(queryObjects.getTestData("FlightNos"));
				if (sFlighNos.contains(";")) {
					arrFlighNos=sFlighNos.split(";");
				}
				
				//Krishna - Get the Specific Trip Class Type for Specific Segment 
				if (sClasstype.toUpperCase().equalsIgnoreCase("SEGSPECIFIC")) {
					ClassNm = SegSpecClasstypeArr[tripiter].toUpperCase();
					if(ClassNm.toUpperCase().equalsIgnoreCase("SPECIFIC"))
						Specifitclass = SpecifitclassArr[tripiter].toUpperCase(); 
				}
				//String 
				/*if(tripiter==0){
					
				}*/
				
				Boolean getflight=false;
				int segment_Date_Move_base_on_previous=1;	
				for (int iweeksearch = 1; iweeksearch <=7; iweeksearch++) {
					classselectbon=false;
					if(iweeksearch==1 && tripiter==0){					
						System.out.println("test");
						break_1SEg_1Dayget=true;
					}
					else{
						String Xpath_nextday="//div[contains(@items,'tab.flights')]//div[contains(@class,'carousel-item') and contains(@class,'tab-active')]/following-sibling::div[1]";
						String Xpath_nextmove="//div[contains(@items,'tab.flights')]//div[contains(@class,'carousel-item') and contains(@class,'tab-active')]/parent::div/parent::div/following-sibling::div/i";
						if(tripiter>0 && segment_Date_Move_base_on_previous==1){
							// first travel date
							
							List<WebElement> currenday=driver.findElements(By.xpath(Xpath_nextday));
							for(int smallloop=1;smallloop<=7;smallloop++){
								if(break_1SEg_1Dayget)
									break;
								currenday=driver.findElements(By.xpath(Xpath_nextday));
								//String piccurrentdat=driver.findElement(By.xpath("//div[contains(@class,'carousel-item') and contains(@class,'tab-active')]/div/div[1]")).getText();
								/*if(getSelected_Date.equalsIgnoreCase(piccurrentdat)){
									currenday.get(0).click();	
									loadhandling(driver);
									driver.findElement(By.xpath(Xpath_nextmove)).click();
									break;
								}
								else{
									currenday.get(0).click();
									loadhandling(driver);
									driver.findElement(By.xpath(Xpath_nextmove)).click();
								}*/
								currenday.get(0).click();
								loadhandling(driver);
								driver.findElement(By.xpath(Xpath_nextmove)).click();
									
							}
							
						}
						else{
							List<WebElement> currenday=driver.findElements(By.xpath(Xpath_nextday));
							currenday.get(0).click();
							loadhandling(driver);
							driver.findElement(By.xpath(Xpath_nextmove)).click();
							break_1SEg_1Dayget=false;
						}
						segment_Date_Move_base_on_previous=segment_Date_Move_base_on_previous+1;
						getSelected_Date="";
						//break_1SEg_1Dayget=false;
					}
					
					
					
					//if(classselectbon==false)
						//driver.findElement(By.xpath(Xpath_nextday)).click();
					
					// This list is View click ...
					java.util.List<WebElement> lview=driver.findElements(By.xpath(viewXpath));
					int nuberofviews=lview.size();
					// This list is all flights
					java.util.List<WebElement> lallflightno=driver.findElements(By.xpath(listofallflightsXpath));
					int nuberoflflightno=lallflightno.size();
					//Boolean getflight=false;
					for (int iviewsiter = 0; iviewsiter <nuberofviews; iviewsiter++) {
						viewhandel=iviewsiter;
						if (sFlighNos!="" || (!envFlight.isEmpty())){
							String flightno;
							if (sFlighNos.contains(";")) {
	
								flightno=arrFlighNos[tripiter];
							}
							else if (!envFlight.isEmpty()) {  // I SSr case get flight number from Ishare application.
								flightno=envFlight;
							}
							else{
								flightno=sFlighNos;
							}
							String getFlightno=lallflightno.get(iviewsiter).getText();
							if (getFlightno.equalsIgnoreCase(flightno)) {
								getflight=true;
								queryObjects.logStatus(driver, Status.INFO, "Searcheing for specific Flight", "specific flight was evailable", null);
								lview.get(iviewsiter).click();
								loadhandling(driver);
								java.util.List<WebElement> lselectedflightno=driver.findElements(By.xpath(lselectedflightnoXpath));
								int viewnuberoflflightnoin;
								viewnuberoflflightnoin=lselectedflightno.size();
								if (viewnuberoflflightnoin>1) {   //  more than one flight case
									viewhandel=iviewsiter+(viewnuberoflflightnoin-1);
									for (int icontflights = 0; icontflights < viewnuberoflflightnoin; icontflights++) {
										if (icontflights>0 && (classselectbon=false)) // if multiple flights case(multey leg case) if first flight not selected no need verify second flight
											break;
										//iviewsiter=iviewsiter+viewnuberoflflightnoin;
										int kk=icontflights+1;
										java.util.List<WebElement> lavbclass=driver.findElements(By.xpath("//div[div[div[div[div[span[span[i[@class='icon-arrow-up']]]]]]]]/div["+kk+"]//span[contains(@class,'active-state')]"));
										int avbclass=lavbclass.size();
										classselectbon=false;
										for (int iselectClass = 0; iselectClass < avbclass; iselectClass++) {
											String getavbclassnm=lavbclass.get(iselectClass).getText();
											if ((ClassNm.equalsIgnoreCase("BUSINESS")) &&  (getavbclassnm.charAt(0)=='C')){
												int classnum=Integer.parseInt(right(getavbclassnm,1));
												if (classnum>=totalpax) {
													lavbclass.get(iselectClass).click();
													classselectbon=true;
													break;
												}
											}
											if ((ClassNm.equalsIgnoreCase("ECONOMY")) && (getavbclassnm.charAt(0)=='Y' || getavbclassnm.charAt(0)=='M' || getavbclassnm.charAt(0)=='H')){
												int classnum=Integer.parseInt(right(getavbclassnm,1));
												if (classnum>=totalpax) {
													lavbclass.get(iselectClass).click();
													classselectbon=true;
													break;
												}
	
											}
											if ((ClassNm.equalsIgnoreCase("ANY")) && (getavbclassnm.charAt(0)=='C' || getavbclassnm.charAt(0)=='Y' || getavbclassnm.charAt(0)=='M' || getavbclassnm.charAt(0)=='H')){
												int classnum=Integer.parseInt(right(getavbclassnm,1));
												if (classnum>=totalpax) {
													lavbclass.get(iselectClass).click();
													classselectbon=true;
													break;
												}
	
											}
											if ((ClassNm.equalsIgnoreCase("SPECIFIC")) && (getavbclassnm.charAt(0)==Specifitclass.charAt(0))){
												int classnum=Integer.parseInt(right(getavbclassnm,1));
												if (classnum>=totalpax) {
													lavbclass.get(iselectClass).click();
													classselectbon=true;
													break;
												}
	
											}
	
										}
	
										if (classselectbon==false && segment_Date_Move_base_on_previous==7 ) {
											queryObjects.logStatus(driver, Status.INFO, "specific Flight and specific Class selection", "specific flight was available but class was not available", null);
										}
	
									}
	
	
								}
								else{
									java.util.List<WebElement> lavbclass=driver.findElements(By.xpath(lavbclassXpath));
									int avbclass=lavbclass.size();
									classselectbon=false;
									for (int iselectClass = 0; iselectClass < avbclass; iselectClass++) {
										String getavbclassnm=lavbclass.get(iselectClass).getText();
										if ((ClassNm.equalsIgnoreCase("BUSINESS")) && (getavbclassnm.charAt(0)=='C')){
											int classnum=Integer.parseInt(right(getavbclassnm,1));
											if (classnum>=totalpax) {
												lavbclass.get(iselectClass).click();
												classselectbon=true;
												break;
											}
										}
										if ((ClassNm.equalsIgnoreCase("ECONOMY")) && (getavbclassnm.charAt(0)=='Y' || getavbclassnm.charAt(0)=='M' || getavbclassnm.charAt(0)=='H')){
											int classnum=Integer.parseInt(right(getavbclassnm,1));
											if (classnum>=totalpax) {
												lavbclass.get(iselectClass).click();
												classselectbon=true;
												break;
											}
	
										}
										if ((ClassNm.equalsIgnoreCase("ANY")) && (getavbclassnm.charAt(0)=='C' || getavbclassnm.charAt(0)=='Y' || getavbclassnm.charAt(0)=='M' || getavbclassnm.charAt(0)=='H')){
											int classnum=Integer.parseInt(right(getavbclassnm,1));
											if (classnum>=totalpax) {
												lavbclass.get(iselectClass).click();
												classselectbon=true;
												break;
											}
	
										}
										if ((ClassNm.equalsIgnoreCase("SPECIFIC")) && (getavbclassnm.charAt(0)==Specifitclass.charAt(0))){
											int classnum=Integer.parseInt(right(getavbclassnm,1));
											if (classnum>=totalpax) {
												lavbclass.get(iselectClass).click();
												classselectbon=true;
												break;
											}
	
										}
	
									}  //for loop iselectClass
	
									if (classselectbon==false && segment_Date_Move_base_on_previous==7 ) {
										queryObjects.logStatus(driver, Status.FAIL, "specific Flight and specific Class selection", "specific flight was available but class was not available", null);
									}
								}
								//
								break;
							}
	
						}
						else {  ///if (sFlighNos!="")
							//Added by Jenny 25 Nov 2019
							boolean SegTime = false;
							if (Duration.contains("Same") && !DestnTime.isEmpty()) {
								OrgTime = driver.findElement(By.xpath("(//span[contains(text(),'View')])["+(iviewsiter+1)+"]/../../../div//div[@ng-if='originDestination.showDateTime']")).getText();
								if (Integer.parseInt(OrgTime.replace(":", ""))>Integer.parseInt(DestnTime.replace(":", ""))) {
									SegTime = true;
								}
							} else {
								SegTime = true;
							}
							if (SegTime) {
								lview.get(iviewsiter).click();
								loadhandling(driver);
								sgetflighno=lallflightno.get(iviewsiter).getText();
								//----------
								if (sgetflighno.length()<6 && sgetflighno.contains("CM")) {  // this is for avoid core chare flights need to check
									java.util.List<WebElement> lselectedflightno=driver.findElements(By.xpath(lselectedflightnoXpath));
									int viewnuberoflflightnoin;
									viewnuberoflflightnoin=lselectedflightno.size();
									if (viewnuberoflflightnoin>1) {
										viewhandel=iviewsiter+(viewnuberoflflightnoin-1);
										for (int icontflights = 0; icontflights < viewnuberoflflightnoin; icontflights++) {
											if ((icontflights>0) && (classselectbon==false)) // if multiple flights case(multey leg case) if first flight not selected no need verify second flight
												break;
											int kk=icontflights+1;
											java.util.List<WebElement> lavbclass=driver.findElements(By.xpath("//div[div[div[div[div[span[span[i[@class='icon-arrow-up']]]]]]]]/div["+kk+"]//span[contains(@class,'active-state')]"));
											int avbclass=lavbclass.size();
											classselectbon=false;
											for (int iselectClass = 0; iselectClass < avbclass; iselectClass++) {
												String getavbclassnm=lavbclass.get(iselectClass).getText();
												if ((ClassNm.equalsIgnoreCase("BUSINESS")) && (getavbclassnm.charAt(0)=='C')){
													int classnum=Integer.parseInt(right(getavbclassnm,1));
													if (classnum>=totalpax) {
														lavbclass.get(iselectClass).click();
														classselectbon=true;
														break;
													}
												}
												if ((ClassNm.equalsIgnoreCase("ECONOMY")) && (getavbclassnm.charAt(0)=='Y' || getavbclassnm.charAt(0)=='M' || getavbclassnm.charAt(0)=='H')){
													int classnum=Integer.parseInt(right(getavbclassnm,1));
													if (classnum>=totalpax) {
														lavbclass.get(iselectClass).click();
														classselectbon=true;
														break;
													}
			
												}
												if ((ClassNm.equalsIgnoreCase("ANY")) && (getavbclassnm.charAt(0)=='C' || getavbclassnm.charAt(0)=='Y' || getavbclassnm.charAt(0)=='M' || getavbclassnm.charAt(0)=='H')){
													int classnum=Integer.parseInt(right(getavbclassnm,1));
													if (classnum>=totalpax) {
														lavbclass.get(iselectClass).click();
														classselectbon=true;
														break;
													}
			
												}
												if ((ClassNm.equalsIgnoreCase("SPECIFIC")) && (getavbclassnm.charAt(0)==Specifitclass.charAt(0))){
													int classnum=Integer.parseInt(right(getavbclassnm,1));
													if (classnum>=totalpax) {
														lavbclass.get(iselectClass).click();
														classselectbon=true;
														break;
													}
			
												}
			
											}
			
											if (classselectbon==false) {
												queryObjects.logStatus(driver, Status.INFO, "specific Flight and specific Class selection", "specific flight was available but class was not available", null);
											}
			
			
										}
			
									}
									else{
										java.util.List<WebElement> lavbclass=driver.findElements(By.xpath(lavbclassXpath));
										int avbclass=lavbclass.size();
										classselectbon=false;
										for (int iselectClass = 0; iselectClass < avbclass; iselectClass++) {
											String getavbclassnm=lavbclass.get(iselectClass).getText();
											if ((ClassNm.equalsIgnoreCase("BUSINESS")) && (getavbclassnm.charAt(0)=='C')){
												int classnum=Integer.parseInt(right(getavbclassnm,1));
												if (classnum>=totalpax) {
													lavbclass.get(iselectClass).click();
													classselectbon=true;
													break;
												}
											}
											if ((ClassNm.equalsIgnoreCase("ECONOMY")) && (getavbclassnm.charAt(0)=='Y' || getavbclassnm.charAt(0)=='M' || getavbclassnm.charAt(0)=='H')){
												int classnum=Integer.parseInt(right(getavbclassnm,1));
												if (classnum>=totalpax) {
													lavbclass.get(iselectClass).click();
													classselectbon=true;
													break;
												}
			
											}
											if ((ClassNm.equalsIgnoreCase("ANY")) && (getavbclassnm.charAt(0)=='C' || getavbclassnm.charAt(0)=='Y' || getavbclassnm.charAt(0)=='M' || getavbclassnm.charAt(0)=='H')){
												int classnum=Integer.parseInt(right(getavbclassnm,1));
												if (classnum>=totalpax) {
													lavbclass.get(iselectClass).click();
													classselectbon=true;
													break;
												}
			
											}
											if ((ClassNm.equalsIgnoreCase("SPECIFIC")) && (getavbclassnm.charAt(0)==Specifitclass.charAt(0))){
												int classnum=Integer.parseInt(right(getavbclassnm,1));
												if (classnum>=totalpax) {
													lavbclass.get(iselectClass).click();
													classselectbon=true;
													break;
												}
			
											}
			
										}
			
										/*if (classselectbon==false) {
											queryObjects.logStatus(driver, Status.FAIL, "specific Flight and specific Class selection", "specific flight was available but class was not available", null);
										}*/
									}
									//---------
									if (classselectbon==true) {
										break;
									}
									lview.get(iviewsiter).click();
									iviewsiter=viewhandel;
								}
								else  // this is for close view button
									lview.get(iviewsiter).click();
							}
						}
	
					}
					if(classselectbon==true){
						getSelected_Date=driver.findElement(By.xpath("//div[contains(@class,'carousel-item') and contains(@class,'tab-active')]/div/div")).getText();
						break;
					}
					
				} // this is for week
				if ( (classselectbon==false) && (sFlighNos.equalsIgnoreCase("")) ) 
					queryObjects.logStatus(driver, Status.FAIL, "Search for  Flight and specific Class selection", "flights not found", null);
				if ((sFlighNos!="") && (getflight==false)) 
					queryObjects.logStatus(driver, Status.FAIL, "Searcheing for specific Flight", "specific flight was not found", null);
			}

			if(classselectbon==true) {
				classselectbon=false;
				//Clicking on Quote Button if quote = Yes in datasheet
				if(getTrimTdata(queryObjects.getTestData("Quote")).equalsIgnoreCase("yes")) {

					driver.findElement(By.xpath(QuoteButtonXpath)).click();
					queryObjects.logStatus(driver, Status.PASS, "Select Quote", "Selecting Quote Option", null);
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(QuoteOptionXpath)));
					//suman - taking the first flight number
					//String FirstflightforAto=driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]//tbody[1]//child::td[@class='flight-name']/span")).getText();
					//Login.envwrite(Login.FLIGHTNUM, FirstflightforAto); 
					List<WebElement> Flightno=driver.findElements(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]//tbody[1]//child::td[@class='flight-name']/span"));
					//Login.envwrite(Login.FLIGHTNUM, Flightno.get(0).getText().trim()); 
					Login.FLIGHTNUM=Flightno.get(0).getText().trim();
					if(Flightno.size()>1)
						Login.RETFLIGHTNUM= Flightno.get(1).getText().trim();
						
						//Login.envwrite(Login.RETFLIGHTNUM, Flightno.get(1).getText().trim()); 
					//Atul - 29Jan - to assign 1 to the segments in Quote Page
					String spec_segno=FlightSearch.getTrimTdata(queryObjects.getTestData("Specific_Seg_No"));
					if (!spec_segno.isEmpty()) {
						if(!spec_segno.contains(";")) {
							spec_segno=spec_segno+";";
						}
						String[] aspec_segno=spec_segno.split(";");
						List<WebElement> segno=new ArrayList<WebElement>();
						segno=driver.findElements(By.xpath("//md-input-container[@class='order-no']/input"));

						for(int i=0;i<segno.size();i++)
						{
							if(spec_segno!="") {
								segno.get(i).sendKeys(aspec_segno[i]);
							}
							else {
							segno.get(i).sendKeys(Integer.toString(++Segmt));
							}
						}
					}
					//clicking checkbox to select all segments
					driver.findElement(By.xpath(SlectSegmentXpath)).click();
					loadhandling(driver);
					//Selecting Pricing option - either price as booked or price as best buy
					if(getTrimTdata(queryObjects.getTestData("pricebestbuy")).equalsIgnoreCase("yes")) {
						driver.findElement(By.xpath(PriceOptionXpath)).click();
						loadhandling(driver);
						driver.findElement(By.xpath(BestBuyXpath)).click();
						queryObjects.logStatus(driver, Status.PASS, "PricebestBuy", "Booking seat with best buy option", null);
					}
					
					String TourCode=getTrimTdata(queryObjects.getTestData("TourCode"));
					Thread.sleep(1000);
					if(!TourCode.equalsIgnoreCase(""))
						driver.findElement(By.xpath("//input[contains(@ng-model,'quoteInfoCtrl.model.pricingOptions.AccountCode')]")).sendKeys(TourCode);
					//Rushil
					//Pricing by date advance options in quote done by Rushil
					String PricingByDate = getTrimTdata(queryObjects.getTestData("PricingByDate"));
					String PricingByDate_Date = getTrimTdata(queryObjects.getTestData("PricingByDate_Date"));
					//String sgetDate=getTrimTdata(queryObjects.getTestData("Days"));
					int pricingDate=0;
					if (PricingByDate_Date.isEmpty())
						pricingDate=7;
					else
						pricingDate=Integer.parseInt(PricingByDate_Date);
					Calendar pridate = Calendar.getInstance();
					pridate.add(Calendar.DATE, -pricingDate);
					String pridatetimeStampd = new SimpleDateFormat("MM/dd/yyyy").format(pridate.getTime());
					if (PricingByDate.equalsIgnoreCase("YES") && (!PricingByDate_Date.isEmpty())) {
						queryObjects.logStatus(driver, Status.PASS, "Pricing by date Option", "Pricing by date process started", null);
						driver.findElement(By.xpath(PriceDate)).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath(PriceDate_Date)).sendKeys(pridatetimeStampd);
						queryObjects.logStatus(driver, Status.PASS, "Pricing by date Option", "Pricing by date process Enter date: "+pridatetimeStampd, null);
						Thread.sleep(1000);
						/*driver.findElement(By.xpath(FareBaseSegXpath)).click();
						Thread.sleep(1000);*/
						driver.findElement(By.xpath(nextbuttonXpath)).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//md-radio-button[contains(@aria-label,'waiver') and contains(@ng-value, 'WaiverReasonInfo.Waiver')]")).click();
						//queryObjects.logStatus(driver, Status.PASS, "Pricing by date case Waiver page checking", "Pricing by date case Waiver displaying", null);
						Thread.sleep(2000);
						driver.findElement(By.xpath("//md-select[contains(@aria-label,'waiver process')and contains(@ng-model,'WaiverReasonInfo.process')]")).click();
						Thread.sleep(2000);
						driver.findElement(By.xpath("//md-select-menu[contains(@role,'presentation')]//div[contains(text(),'PURCHASE')]//parent::md-option[contains(@ng-value,'ProcessList')]")).click();
						Thread.sleep(1000);
						//Select Reason Code
						driver.findElement(By.xpath("//md-select[@aria-label='waiver reason']")).click();
						Thread.sleep(2000);

						driver.findElement(By.xpath("//md-select-menu[contains(@role,'presentation')]//div[contains(text(),'WFAR ADP')]//parent::md-option[contains(@ng-value,'Reason')]")).click();
						Thread.sleep(1000);
						
					}
					//driver.findElement(By.xpath(nextbuttonXpath)).click();
					//Pricing by date advance options ends here
					// Entering PAX Discount
					// Entering PAX Discount
					boolean BeforeCreatePNR=true;
					String PriceQuote_Discount=queryObjects.getTestData("PriceQuote_Discount");
					String PriceQuote_discountType = queryObjects.getTestData("PriceQuote_discountType");//2
					String PriceQuote_PaxForDiscount = getTrimTdata(queryObjects.getTestData("PriceQuote_PaxForDiscount"));//4
					String PriceQuote_noofSeg = getTrimTdata(queryObjects.getTestData("PriceQuote_noofSeg"));//6
					String PriceQuote_discountValue = queryObjects.getTestData("PriceQuote_discountValue");//7
					String PriceQuote_Discount_valueType = queryObjects.getTestData("PriceQuote_Discount_valueType");//8
					String PriceQuote_Discount_Taxes = queryObjects.getTestData("PriceQuote_Discount_Taxes");//9
					String PriceQuote_Discount_Ticket_Designator = queryObjects.getTestData("PriceQuote_Discount_Ticket_Designator");//10
					if(PriceQuote_Discount.equalsIgnoreCase("yes"))
						Discount(driver, queryObjects, BeforeCreatePNR,PriceQuote_discountType,PriceQuote_PaxForDiscount,PriceQuote_noofSeg,PriceQuote_discountValue,PriceQuote_Discount_valueType,PriceQuote_Discount_Taxes,PriceQuote_Discount_Ticket_Designator);
					//Entering pax reduction type
					String paxReductionType = getTrimTdata(queryObjects.getTestData("paxReductionType"));
					String paxReductionTypeCount = getTrimTdata(queryObjects.getTestData("paxReductionTypeCount"));
					String paxReductionTypeName = getTrimTdata(queryObjects.getTestData("paxReductionTypeName"));
					
					if(paxReductionType.equalsIgnoreCase("yes")) {
						PAXreductionType(driver, queryObjects,paxReductionTypeCount,paxReductionTypeName);
					}
					//Testing  Fare Basis
					String FareBase = getTrimTdata(queryObjects.getTestData("FareBase"));
					String FareBase_Class = getTrimTdata(queryObjects.getTestData("FareBase_Class"));
					if (FareBase.equalsIgnoreCase("YES") && (!FareBase_Class.isEmpty())) {
						queryObjects.logStatus(driver, Status.PASS, "Fare Base Option", "Fare Base process started", null);
						driver.findElement(By.xpath(FareBaseXpath)).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath(FareBaseCodeXpath)).sendKeys(FareBase_Class);
						queryObjects.logStatus(driver, Status.PASS, "Fare Base Option", "Fare Base process Enter class: "+FareBase_Class, null);
						Thread.sleep(1000);
						driver.findElement(By.xpath(FareBaseSegXpath)).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath(nextbuttonXpath)).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//md-select[@aria-label='waiver process']")).click();
						queryObjects.logStatus(driver, Status.PASS, "Fare Base case Waver page checking", "Fare Base case Waver displaying", null);
						Thread.sleep(2000);
						driver.findElement(By.xpath("//md-option[@ng-value='ProcessList' and @role='option'][1]")).click();
						Thread.sleep(2000);
						//Select Reason Code
						driver.findElement(By.xpath("//md-select[@aria-label='waiver reason']")).click();
						Thread.sleep(2000);

						driver.findElement(By.xpath("//div[@aria-hidden='false']//md-option[@ng-value='Reason' and @role='option'][1]")).click();
						
					}
					//meenu_add tax
					String addTax = getTrimTdata(queryObjects.getTestData("PriceQuote_AddTax"));
					String addTaxCode = getTrimTdata(queryObjects.getTestData("PriceQuote_AddTax_Code"));
					String addTaxValue = getTrimTdata(queryObjects.getTestData("PriceQuote_AddTax_Value"));
					/*String addTaxValue_Amt = getTrimTdata(queryObjects.getTestData("AddTax_Value_Amount"));
					String addTaxValue_Percentage = getTrimTdata(queryObjects.getTestData("AddTax_Value_Percentage"));*/
					String addTaxType = getTrimTdata(queryObjects.getTestData("PriceQuote_AddTax_Type"));
					/*if (addTax.equalsIgnoreCase("YES")) 
						if (!addTaxCode.isEmpty()) {
							driver.findElement(By.xpath("//div[4]/div[1]/div/div[1]/div/i")).click();
							driver.findElement(By.xpath("//md-input-container//input[contains(@ng-model,'taxDetail.AddTaxCode')]")).sendKeys(addTaxCode);
							if (!addTaxValue_Amt.isEmpty())
							{
								driver.findElement(By.xpath("//md-input-container//input[contains(@ng-model,'taxDetail.Amount')]")).sendKeys(addTaxValue_Amt);
								driver.findElement(By.xpath("//md-select[contains(@ng-model,'taxDetail.AddTaxType')]")).click();
								driver.findElement(By.xpath("//md-option//div[contains(text(),'Amount')]")).click();								
							}
							else
							{
								driver.findElement(By.xpath("//md-input-container//input[contains(@ng-model,'taxDetail.Amount')]")).sendKeys(addTaxValue_Percentage);
								driver.findElement(By.xpath("//md-select[contains(@ng-model,'taxDetail.AddTaxType')]")).click();
								driver.findElement(By.xpath("//md-select[contains(@ng-model,'taxDetail.AddTaxType')]//div[contains(text(),'Percentage')]")).click();									
							}
						}*/
					
					if (addTax.equalsIgnoreCase("YES")) 
						if (!addTaxCode.isEmpty()) {
							driver.findElement(By.xpath("//div[4]/div[1]/div/div[1]/div/i")).click();
							driver.findElement(By.xpath("//md-input-container//input[contains(@ng-model,'taxDetail.AddTaxCode')]")).sendKeys(addTaxCode);
									  
		
							driver.findElement(By.xpath("//md-input-container//input[contains(@ng-model,'taxDetail.Amount')]")).sendKeys(addTaxValue);
							driver.findElement(By.xpath("//md-select[contains(@ng-model,'taxDetail.AddTaxType')]")).click();
							if (addTaxType.equalsIgnoreCase("Amt")) {
								driver.findElement(By.xpath("//md-option//div[contains(text(),'Amount')]")).click();								
		
							} else {
		
																																																																						   
																																																   
																																																   
							driver.findElement(By.xpath("//md-select[contains(@ng-model,'taxDetail.AddTaxType')]//div[contains(text(),'Percentage')]")).click();									
							}
						}
					//end
					driver.findElement(By.xpath(nextbuttonXpath)).click();
					ManualQuote=false;
					loadhandling(driver);
					String quotescreemsg=getErrorMSGfromAppliction(driver, queryObjects);
					if (quotescreemsg.contains("NO VALID FARE")|| quotescreemsg.contains("No fare is available")|| quotescreemsg.contains("No Price information") || quotescreemsg.contains("NO FARE")){
						ManualQuote=true; // if No valid fare rule it is going to manual quote ..... NO FARE FOR PASSENGER TYPE REQUESTED
						driver.findElement(By.xpath(cancelbuttonXpath)).click();
						loadhandling(driver);
						driver.findElement(By.xpath(BookbuttonXpath)).click();
						loadhandling(driver);
						currencyType=Login.Cur;
						queryObjects.logStatus(driver, Status.PASS, "No valid Fare case doing Manual Quote","No valid Fare case doing Manual Quote", null);
					}	
					if(ManualQuote==false){
						wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(QuotePagexpath)));
						queryObjects.logStatus(driver, Status.PASS, "Quote details screen", "Quote details", null);
						loadhandling(driver);
						String fareDetails = driver.findElement(By.xpath(farepriceXpath)).getText().trim();
						fareAmount = fareDetails.split("\\s+")[3]; 
						currencyType = fareDetails.split("\\s+")[4];
						String Service_Fee_s="";
						List<WebElement> ServiceFee=driver.findElements(By.xpath("//div[div[contains(text(),'Service Fee')]]/div[2]/div/div"));
						int servi=ServiceFee.size();
						if(ServiceFee.size()>0){
							Service_Fee_s = ServiceFee.get(0).getText().trim();
							Service_Fee_s=Service_Fee_s.split("\\s+")[0];
							Service_Fee=Double.parseDouble(Service_Fee_s) * totalnoofPAX;
							Service_Fee=Double.parseDouble(roundDouble(String.valueOf(Service_Fee)));
						}
						List<WebElement> Quoteresults=driver.findElements(By.xpath("//div[div[toggle-title[contains(text(),'Quote result')]]]/i"));
						Quoteresults.get(0).click(); // for manual quote tacking value ......
						FlightSearch.loadhandling(driver);
						List<WebElement> fare_amt=driver.findElements(By.xpath("//div[@translate='pssgui.fare.amount']/following::div[1]"));
						if(fare_amt.size()>0)
							ManulaQuote_Fare_amt=fare_amt.get(0).getText().trim();

						List<WebElement> Euivalent_am=driver.findElements(By.xpath("//div[@translate='pssgui.equivalent']/following::div[1]"));
						if(Euivalent_am.size()>0)
							ManualQuote_Euivalent_amt=Euivalent_am.get(0).getText().trim();

						Quoteresults.get(0).click();


						// tax code verification
						String TaxCodeVerifiation=FlightSearch.getTrimTdata(queryObjects.getTestData("TaxCodeVerifiation"));
						if (TaxCodeVerifiation.equalsIgnoreCase("yes")) {
							Quoteresults=driver.findElements(By.xpath("//div[div[toggle-title[contains(text(),'Quote result')]]]/i"));
							String xpathis="//div[@action='taxes']/div[@ng-repeat='tax in orderTableDisplay.list']/div[2]";
							for (int iQuoteresults = 0; iQuoteresults < Quoteresults.size(); iQuoteresults++) {
								Quoteresults.get(iQuoteresults).click();
								FlightSearch.loadhandling(driver);
								PNRsearch.TaxCodeValidation(driver,queryObjects,xpathis,"Quote Screen");  // tax code verification
								Thread.sleep(2000);
								Quoteresults.get(iQuoteresults).click();
								FlightSearch.loadhandling(driver);
							}
						}
						String BaggageRuleverification=FlightSearch.getTrimTdata(queryObjects.getTestData("BaggageRuleverification"));
						if (BaggageRuleverification.equalsIgnoreCase("yes")) {
							List<WebElement> BaggRules=driver.findElements(By.xpath("//div[div[toggle-title[contains(text(),'Baggage Rules')]]]/i"));
							for (int iBaggRules = 0; iBaggRules < BaggRules.size(); iBaggRules++) {
								BaggRules.get(iBaggRules).click();
								if(driver.findElements(By.xpath("//div[contains(text(),'Baggage Charges')]")).size()>0)
									queryObjects.logStatus(driver, Status.PASS, "Quote Screen Baggage Charges checking:","Quote screen baggage details displaying",null);
							}		
						}

						driver.findElement(By.xpath(nextbuttonXpath)).click();
						queryObjects.logStatus(driver, Status.PASS, "Quote details are ", "Quote amount is: "+fareAmount+" Currencey is: "+currencyType, null);
						loadhandling(driver);
					} //manual quote
				}
				else if (getTrimTdata(queryObjects.getTestData("Book")).equalsIgnoreCase("yes")) {
					driver.findElement(By.xpath(BookbuttonXpath)).click();
					queryObjects.logStatus(driver, Status.PASS, "Select Book", "Selecting Book Option", null);
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(traveldetailsXpath)));
				}
				//Entering passenger details
				String FFPbeforePNR = getTrimTdata(queryObjects.getTestData("AddFFPbeforePNR"));
				String FFPafterPNR = getTrimTdata(queryObjects.getTestData("AddFFPafterPNR")); 
				String Female_pax = getTrimTdata(queryObjects.getTestData("No_of_Female"));   // himani need to check
				if (!Female_pax.equals(""))
					 totalFemale=Integer.parseInt(Female_pax);
				totalMale=totalnoofPAX - totalFemale;	
				wordpay_firstName=""; // clear the values
				wordpay_surname="";
				for(int ienterpax=1; ienterpax<=totalnoofPAX; ienterpax++) {
					driver.findElement(By.xpath("//md-content/div[contains(@class,'traveler-detail')][" +ienterpax + "]")).click();
					String paxType = getTrimTdata(driver.findElement(By.xpath("//div[contains(@class,'traveler-list')]/md-content/div["+ ienterpax +"]/div/div[3]")).getText());
					String allPAXdiffEmail = getTrimTdata(queryObjects.getTestData("AllPAXEmail"));
					//Need to retrieve test data from Excel Sheet   
					surname = getTrimTdata(queryObjects.getTestData("surname"));   
					String sfirstnm = getTrimTdata(queryObjects.getTestData("FirstName"));
					firstName=sfirstnm;
					String stotalFFP=getTrimTdata(queryObjects.getTestData("totalFFP"));
					String FFPnumber = getTrimTdata(queryObjects.getTestData("FFPnumbers"));
					
					if (!stotalFFP.isEmpty() && getTrimTdata(queryObjects.getTestData("AddFFPbeforeSearch")).isEmpty()) {
						if(FFPafterPNR.equalsIgnoreCase("yes")) {
							FFpPax=true;
							int itotalFFP=Integer.parseInt(stotalFFP);
							if (itotalFFP==1 && ienterpax==1) {
								driver.findElement(By.xpath(surnameXpath)).sendKeys(surname);
								driver.findElement(By.xpath(firstnmXpath)).sendKeys(sfirstnm);	
							}
							else if (itotalFFP>1) {
								String[] asurnm=surname.split(";");
								String[] afirstnm=sfirstnm.split(";");
								if (asurnm.length>=ienterpax) {
									String k1=asurnm[ienterpax-1];
									String k2=afirstnm[ienterpax-1];
									driver.findElement(By.xpath(surnameXpath)).sendKeys(k1);
									driver.findElement(By.xpath(firstnmXpath)).sendKeys(k2);
								}
								else{
									driver.findElement(By.xpath(surnameXpath)).sendKeys(RandomStringUtils.random(6, true, false));
									driver.findElement(By.xpath(firstnmXpath)).sendKeys(RandomStringUtils.random(6, true, false));
								}


							}
							else if (ienterpax>1) {
								driver.findElement(By.xpath(surnameXpath)).sendKeys(RandomStringUtils.random(6, true, false));
								driver.findElement(By.xpath(firstnmXpath)).sendKeys(RandomStringUtils.random(6, true, false));
							}
						}
						else if(FFPbeforePNR.equalsIgnoreCase("yes")) {
							int itotalFFP=Integer.parseInt(stotalFFP);
							FFpPax=true;
							if (itotalFFP==1 && itotalFFP>=ienterpax) {
								driver.findElement(By.xpath(ffnumberXpath)).click();
								driver.findElement(By.xpath(ffnumberXpath)).clear();
								driver.findElement(By.xpath(ffnumberXpath)).sendKeys(FFPnumber);
								Thread.sleep(500);

								driver.findElement(By.xpath("//button[contains(text(),'Validate FF')]")).click();
								loadhandling(driver);
								driver.findElement(By.xpath("//button[contains(text(),'Add')]")).click();
								Thread.sleep(2000);
							}
							else if(itotalFFP>1 && itotalFFP>=ienterpax){
								String[] FFPnums = FFPnumber.split(";");
								driver.findElement(By.xpath(ffnumberXpath)).click();
								driver.findElement(By.xpath(ffnumberXpath)).clear();
								driver.findElement(By.xpath(ffnumberXpath)).sendKeys(FFPnums[ienterpax-1]);
								Thread.sleep(500);

								driver.findElement(By.xpath(validateffpXpath)).click();
								loadhandling(driver);
								driver.findElement(By.xpath(addffpXpath)).click();
								Thread.sleep(500);
							}

							else {
								driver.findElement(By.xpath(surnameXpath)).sendKeys(RandomStringUtils.random(6, true, false));
								driver.findElement(By.xpath(firstnmXpath)).sendKeys(RandomStringUtils.random(6, true, false));
							}

						}
					}
					else if (getTrimTdata(queryObjects.getTestData("AddFFPbeforeSearch")).isEmpty()){  //Adding Random Surname and FirstName
						if(surname.equals("") )
							driver.findElement(By.xpath(surnameXpath)).sendKeys(RandomStringUtils.random(6, true, false));
						else if(!getTrimTdata(queryObjects.getTestData("AddMultPAX")).isEmpty()) { //Adding specific Surname and firstname to add FFP later

							String[] asurnm=surname.split(";");
							String[] afirstnm=sfirstnm.split(";");
							if (asurnm.length>=ienterpax) {
								String k1=asurnm[ienterpax-1];
								String k2=afirstnm[ienterpax-1];
								driver.findElement(By.xpath(surnameXpath)).sendKeys(k1);
								driver.findElement(By.xpath(firstnmXpath)).sendKeys(k2);
							}
							else{
								driver.findElement(By.xpath(surnameXpath)).sendKeys(RandomStringUtils.random(6, true, false));
								driver.findElement(By.xpath(firstnmXpath)).sendKeys(RandomStringUtils.random(6, true, false));
							}

						}
						else if(!sameSurname && (!surname.equals(""))){
							driver.findElement(By.xpath(surnameXpath)).sendKeys(surname);  
							if (getTrimTdata(queryObjects.getTestData("sameSurname")).equalsIgnoreCase("yes")) {
								driver.findElement(By.xpath("//i[@class='icon-content-copy']")).click();
								sameSurname = true;
							}

						}
						//Entering  FirstName
						if (getTrimTdata(queryObjects.getTestData("AddMultPAX")).isEmpty()){
							if(getTrimTdata(queryObjects.getTestData("sameFirstName")).equalsIgnoreCase("yes") || (!sfirstnm.isEmpty())) {

								driver.findElement(By.xpath(firstnmXpath)).sendKeys(sfirstnm);
							}
							else{ //Entering Random FirstName
								driver.findElement(By.xpath(firstnmXpath)).sendKeys(RandomStringUtils.random(6, true, false));
							}
						}
					}
					else if (!getTrimTdata(queryObjects.getTestData("AddFFPbeforeSearch")).isEmpty()){
						if ( totFFPbeforeSearch>=ienterpax) {
							//String kk=driver.findElement(By.xpath(surnameXpath)).getText().trim();
							//if (driver.findElement(By.xpath(surnameXpath)).getText().trim().isEmpty())
							//queryObjects.logStatus(driver, Status.FAIL, "Adding FFP before search the Flight case", "PAX name should automatically populate", null);
						}	
						else{
							driver.findElement(By.xpath(surnameXpath)).sendKeys(RandomStringUtils.random(6, true, false));
							driver.findElement(By.xpath(firstnmXpath)).sendKeys(RandomStringUtils.random(6, true, false));
						}
					}

					//Entering Date of Birth
					String Adult_Age=getTrimTdata(queryObjects.getTestData("Adult_Age"));
					String Child_Age=getTrimTdata(queryObjects.getTestData("Child_Age"));

					if (Adult_Age.isEmpty()) 
						Adult_Age="30";
					if (Child_Age.isEmpty()) 
						Child_Age="10";
					Calendar cal = Calendar.getInstance();
					if((paxType.contains("ADT")) && (!Adult_Age.contains("/")))
						cal.add(Calendar.YEAR, -Integer.parseInt(Adult_Age));
					else if(paxType.contains("CHD"))
						cal.add(Calendar.YEAR, -Integer.parseInt(Child_Age));
					else if(paxType.contains("INF"))
						cal.add(Calendar.DAY_OF_MONTH, -30);
					else if(paxType.contains("INS"))
						cal.add(Calendar.MONTH, -2);


					String timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
					if (Adult_Age.contains("/")) {
						timeStamp = Adult_Age;						
					}

					driver.findElement(By.xpath(datepickerXpath)).clear();
					driver.findElement(By.xpath(datepickerXpath)).sendKeys(timeStamp);

					if(paxType.contains("INF")) {
						driver.findElement(By.xpath(traverlwithXpath)).click();
						Thread.sleep(2000);
						driver.findElement(By.xpath(selectfirsttraverxpath)).click();

					}
					//Selecting Gender
				//	gender = "Male";
					driver.findElement(By.xpath(gengerXpath)).click();
					Thread.sleep(1000);
					if(totalFemale > 0) {						
						driver.findElement(By.xpath("//md-option[div[div[contains(text(),'" + genderF + "')]]]")).click();
						totalFemale -= 1;
					}
					else {
						driver.findElement(By.xpath("//md-option[div[div[contains(text(),'" + genderM + "')]]]")).click();
						totalMale -= 1;
					}
						
					//driver.findElement(By.xpath("//md-option[div[div[contains(text(),'" + gender + "')]]]")).click();
					Thread.sleep(2000);

					if (!(totFFPbeforeSearch>=ienterpax)) {  // add ffp for beforesearch   (!(totFFPbeforeSearch>=ienterpax))
						if((paxType.contains("ADT")) ||  (paxType.contains("CHD"))){
							if(ienterpax==1 || allPAXdiffEmail.equalsIgnoreCase("yes") ) {
								//Selecting Email
								driver.findElement(By.xpath(emailaddresXpath)).click();
								driver.findElement(By.xpath(emailaddresXpath)).clear();
								driver.findElement(By.xpath(emailaddresXpath)).sendKeys(RandomStringUtils.random(6, true, false)+"@MPHASIS.com");
							} 
						}
					}
					if (getTrimTdata(queryObjects.getTestData("AddFFPbeforeSearch")).equalsIgnoreCase("yes") && allPAXdiffEmail.equalsIgnoreCase("yes") ) {
						driver.findElement(By.xpath(emailaddresXpath)).click();
						driver.findElement(By.xpath(emailaddresXpath)).clear();
						driver.findElement(By.xpath(emailaddresXpath)).sendKeys(RandomStringUtils.random(6, true, false)+"@MPHASIS.com");
					}
					if(!detailsEntered){

						//Selecting Email
						//driver.findElement(By.xpath(emailaddresXpath)).click();
						//driver.findElement(By.xpath(emailaddresXpath)).sendKeys(RandomStringUtils.random(6, true, false)+"@mphsis.com");

						//Selecting Nationality
						nation="US";
						driver.findElement(By.xpath(nationalityXpath)).click();
						driver.findElement(By.xpath(nationalityXpath)).clear();
						driver.findElement(By.xpath(nationalityXpath)).sendKeys(nation);
						loadhandling(driver);
						//Thread.sleep(2000);
						//driver.findElement(By.xpath(nationalityXpath)).sendKeys(Keys.TAB);
						driver.findElement(By.xpath(clickUSpopuXpath)).click();
						
						 KTNvalue=getTrimTdata(queryObjects.getTestData("Know_traveler"));
						if (!KTNvalue.equals("")){
							driver.findElement(By.xpath(KTN_Xpath)).sendKeys(KTNvalue);	
						}

						//Selecting first phone type
						//phonetype1 = "Agency Phone";
						driver.findElement(By.xpath(phonenumberselecterXpath)).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath(selectfirstphonetyp1Xpath)).click();

						//Selecting countryCode
						countryCode = "US";
						driver.findElement(By.xpath(countrycodeXpath)).click();
						driver.findElement(By.xpath(countrycodeXpath)).clear();
						driver.findElement(By.xpath(countrycodeXpath)).sendKeys(countryCode);
						loadhandling(driver);
						//driver.findElement(By.xpath(countrycodeXpath)).sendKeys(Keys.TAB);
						driver.findElement(By.xpath(clickUSpopuXpath)).click();
						//	loadhandling(driver);

						//driver.findElement(By.xpath("//md-virtual-repeat-container[div[div[ul/li[md-autocomplete-parent-scope[span[span[text()='US']]]]]] and @aria-hidden='false']")).click();
						//driver.findElement(By.xpath(selectcountryXpath)).click();

						//Enter phone number
						driver.findElement(By.xpath(phonenumberXpath)).click();
						driver.findElement(By.xpath(phonenumberXpath)).clear();
						driver.findElement(By.xpath(phonenumberXpath)).sendKeys(RandomStringUtils.random(8, false, true));
						Thread.sleep(1000);
						//Entering emergency name
						driver.findElement(By.xpath(emergencynameXpath)).click();
						driver.findElement(By.xpath(emergencynameXpath)).sendKeys(RandomStringUtils.random(6, true, false));
						driver.findElement(By.xpath(emergencyphoneXpath)).click();
						driver.findElement(By.xpath(emergencyphoneXpath)).sendKeys(RandomStringUtils.random(3, true, false));
						Thread.sleep(2000);
						//SElect emergency phone type
						driver.findElement(By.xpath(phonetypeXpath)).click();
						Thread.sleep(1500);
						driver.findElement(By.xpath(selectfirstphonetypeXpath)).click();

						//Enter Country code
						driver.findElement(By.xpath(countrycodeselectXpath)).click();
						driver.findElement(By.xpath(countrycodeselectXpath)).sendKeys(countryCode);
						loadhandling(driver);
						driver.findElement(By.xpath(clickUSpopuXpath)).click();
						//driver.findElement(By.xpath(countrycodeselectXpath)).sendKeys(Keys.TAB);
						
						//driver.findElement(By.xpath("//ul/li[md-autocomplete-parent-scope[span[span[text()='US']]]]")).click();
						//driver.findElement(By.xpath(selectcountryXpath)).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath(phonenumberxpath)).click();
						driver.findElement(By.xpath(phonenumberxpath)).sendKeys(RandomStringUtils.random(8, false, true));

						detailsEntered = true;

					}
					
				
					if(ienterpax==1){  // this is for checking first pax in world pay...
						wordpay_surname=driver.findElement(By.xpath(surnameXpath)).getAttribute("value");
						wordpay_firstName=driver.findElement(By.xpath(firstnmXpath)).getAttribute("value");
					} 
				}
				
				String error=getTrimTdata(queryObjects.getTestData("Expected_erromsg"));
				if (getTrimTdata(queryObjects.getTestData("Quote")).equalsIgnoreCase("yes")){  //Quote button

					if (getTrimTdata(queryObjects.getTestData("ManualQuote")).equalsIgnoreCase("yes")|| ManualQuote==true) {  // Manual Quote case click book button
						driver.findElement(By.xpath("//button[text()='Book' and contains(@ng-click,'quote.stateChange')]")).click();
						double Manua_Quote_fareanycurrency=0;
						try{
							loadhandling(driver);
							int ss=Integer.parseInt(convertinteger(ManulaQuote_Fare_amt.split("\\s+")[0]));
							
							if((currencyType.trim().equalsIgnoreCase("ARS")|| currencyType.trim().equalsIgnoreCase("COP")) && (ss>0)){
								Manua_Quote_fareanycurrency=Manual_Quote(driver, queryObjects,"yes",ManulaQuote_Fare_amt,ManualQuote_Euivalent_amt,Service_Fee);
							}
							else{
								Manua_Quote_fareanycurrency=Manual_Quote(driver, queryObjects,"No","","",Service_Fee);
							}
							//Manua_Quote_fareanycurrency=Manual_Quote(driver, queryObjects,"No","","",Service_Fee);
							loadhandling(driver);
						}
						catch(Exception e){
							queryObjects.logStatus(driver, Status.FAIL, "Manual quote", "Manual Quote Case Fail",e);	
						}
						if(ManualQuote==true){ // no fare case adding service Fee
							String Service_Fee_s="";
							//List<WebElement> ServiceFee=driver.findElements(By.xpath("//div[div[contains(text(),'Service Fee')]]/div[2]"));
							List<WebElement> ServiceFee=driver.findElements(By.xpath("//div[@model='fee.TotalAmount']/div[contains(@ng-class,'amountCtrl')]"));
							int servi=ServiceFee.size();
							if(ServiceFee.size()>0){
								Service_Fee_s = ServiceFee.get(0).getText().trim();
								Service_Fee_s=Service_Fee_s.split("\\s+")[0];
								Service_Fee=Double.parseDouble(Service_Fee_s) * totalnoofPAX;
								Service_Fee=Double.parseDouble(roundDouble(String.valueOf(Service_Fee)));
								queryObjects.logStatus(driver, Status.PASS,"Servie fee is:","Service Fee is: "+Service_Fee,null);
							}
							Manua_Quote_fareanycurrency=Manua_Quote_fareanycurrency+Service_Fee;
						}
						fareAmount=String.valueOf(Manua_Quote_fareanycurrency);
					}
					else
						driver.findElement(By.xpath("//button[text()='Book & File Fare']")).click();
					loadhandling(driver);
					String errormsg=getErrorMSGfromAppliction(driver, queryObjects); // try to get error message....

					pnrcreated=true;
					if (error.equalsIgnoreCase("yes")){
						pnrcreated=false;
						if(!errormsg.isEmpty())
							queryObjects.logStatus(driver, Status.PASS, "While creating PNR error message verification", "it is showing error message ::"+errormsg, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "While creating PNR error message verification", "it should show the error message ", null);
					}
					else if (!errormsg.isEmpty()){
						if(currencyType.trim().equalsIgnoreCase("ARS")|| currencyType.trim().equalsIgnoreCase("COP") ) // ARS and COP currency if it is dispay waring msg Amounts exceeding maximum number of digits – Manual quote required
							if(errormsg.equalsIgnoreCase("Amounts exceeding maximum number of digits – Manual quote required")){
								queryObjects.logStatus(driver, Status.PASS,"Manual quote process started","Reasion is: Amounts exceeding maximum number of digits – Manual quote required",null);
								boolean Book_File_fare=driver.findElement(By.xpath("//button[text()='Book & File Fare']")).isEnabled();
								if(Book_File_fare)
									queryObjects.logStatus(driver, Status.FAIL,"Book and File fare button status cheking","if Manual quote required,  msg Case 'Book & File Fare' button should be disabled",null);
								else
									queryObjects.logStatus(driver, Status.PASS,"Book and File fare button status cheking","if Manual quote required,  msg Case 'Book & File Fare' button is disabled",null);
								double Manua_Quote_fare=0;
								try{
									driver.findElement(By.xpath("//button[text()='Book' and contains(@ng-click,'quote.stateChange')]")).click();
									loadhandling(driver);
									Manua_Quote_fare=Manual_Quote(driver, queryObjects,"yes",ManulaQuote_Fare_amt,ManualQuote_Euivalent_amt,Service_Fee);
								}
								catch(Exception e){
									queryObjects.logStatus(driver, Status.FAIL, "Manual quote", "Manual Case Fail",e);	
								}
								fareAmount=String.valueOf(Manua_Quote_fare);
							}		
						queryObjects.logStatus(driver, Status.INFO, "try to create the PNR", "we are unable to create the pnr it is showing error/WARNING message "+errormsg, null); 
					}
				}
				else
					driver.findElement(By.xpath("//button[text()='Book' and contains(@ng-click,'quote.stateChange')]")).click();
				// Before payment amount checking....
				//Krishna - WorldPay Getting SurName and FirstName
				loadhandling(driver);
				java.util.List<WebElement> lpassengerlist =driver.findElements(By.xpath("//div[contains(@class,'pssgui-design-sub-heading-6')]/span"));
				if(lpassengerlist.size()>0) {
					//lpassengerlist.get(0).click();
					loadhandling(driver);
					wordpay_surname=null;
					wordpay_firstName=null;
					String PassengerName = lpassengerlist.get(0).getText();
					String[] paxname = PassengerName.split(", ");
					wordpay_surname = paxname[0];
					wordpay_firstName = paxname[1];
					System.out.println("WorldPay Name ::"+wordpay_firstName+" "+wordpay_surname);
				}
				if (!error.equalsIgnoreCase("yes")){  // if no error message only store the PNR
					try{
						WebDriverWait wait2 = new WebDriverWait(driver, 100);
						wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@action='pnr']/div/div/div[1]/div[1]")));
						Thread.sleep(1000);
						Pnris=getTrimTdata(driver.findElement(By.xpath("//div[@action='pnr']/div/div/div[1]/div[1]")).getText());
						//Login.envwrite(Login.PNRNUM, Pnris);
						Login.PNRNUM=Pnris;
						queryObjects.logStatus(driver, Status.PASS, "Pnr was generated", "PNR number is:: "+Pnris, null);	

					}
					catch (Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "PNR was not generated ", e.getLocalizedMessage(), e);

					}
				}
				//Start - Navira
				if(!Login.PNRNUM.isEmpty()) {
					String confirmation = getTrimTdata(queryObjects.getTestData("Confirmation_without_payment"));
					if(confirmation.equalsIgnoreCase("yes"))
						resConfirmationwopayment(driver,queryObjects);
					
				}
				String baggage_allowance = getTrimTdata(queryObjects.getTestData("Bag_allowance"));
				if(baggage_allowance.equalsIgnoreCase("yes"))
					Baggage_allowance(driver, queryObjects);
				//End - Navira
				if (pnrcreated) {
					String totalamt=getTrimTdata(driver.findElement(By.xpath("//div[@model='currency.total']/div")).getText());
					String Balanceamt=getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());

					if (Double.parseDouble(Balanceamt)==Double.parseDouble(fareAmount)) {
						queryObjects.logStatus(driver, Status.PASS, "Quote amount and Balance amount checking", "After creating the pnr amount showing  in Balance due balance amt is: "+Balanceamt, null);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL, "Quote amount and Balance amount checking", "After creating the pnr amount should show in Balance due actual: "+Balanceamt+" expected: "+fareAmount, null);
					}
					if (Double.parseDouble(totalamt)==Double.parseDouble(fareAmount)) {
						queryObjects.logStatus(driver, Status.PASS, "Quote amount and Total amount checking", "After creating the pnr Total amount showing  correctly total amt is :"+totalamt, null);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL, "Quote amount and Total amount checking", "After creating the pnr Totlal amount should show correctly actual: "+totalamt+" expected: "+fareAmount, null);
					}

				}
				// Re Quote  case................
				if (getTrimTdata(queryObjects.getTestData("ReQuote")).equalsIgnoreCase("yes")){

					List<WebElement> dates = driver.findElements(By.xpath("//td[@class='date']/div/div/span[contains(@ng-click,'flightResult.enableInlineEdit')]"));

					for (int k=0;k<dates.size();k++) {
						List<WebElement> dates1 = driver.findElements(By.xpath("//td[@class='date']/div/div/span[contains(@ng-click,'flightResult.enableInlineEdit')]"));
						String StrDate =  dates1.get(k).getText();//driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).getText();
						dates1.get(k).click();

						SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
						java.util.Date sDate = sdf.parse(StrDate);



						Calendar cal = Calendar.getInstance();
						cal.setTime(sDate);
						cal.add(Calendar.DATE, 3); //minus number would decrement the days
						java.util.Date newdate = cal.getTime();
						String ckDate = new SimpleDateFormat("MM/dd/yyyy").format(newdate);
						String strnewdate= ckDate.toString();
						driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).clear();
						driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(strnewdate);

						driver.findElement(By.xpath("//i[@class='icon-save']")).click();
						loadhandling(driver);
					}

					driver.findElement(By.xpath("//md-select[@placeholder='Actions']")).click();
					Thread.sleep(1000);
					//driver.findElement(By.xpath("//md-option[@value='Quote']")).click();
					loadhandling(driver);
					driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false']//md-option[div[contains(text(),'Quote')]]")).click();

					//Selecting Coupon check box if it is not checked already
					WebElement checkBox = driver.findElement(By.xpath("//md-checkbox[@aria-label='coupon-check']"));
					if(checkBox.getAttribute("aria-checked").equalsIgnoreCase("false"))
						checkBox.click();

					//Click on Next
					driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Next')]")).click();
					FlightSearch.loadhandling(driver);
					//Wait until Loading wrapper closed
					//String fareDetails = driver.findElement(By.xpath(farepriceXpath)).getText().trim();
					//String fareAmount = fareDetails.split("\\s+")[2];


					//Click on File Fare
					driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'File Fare')]")).click();
					//Wait until Loading wrapper closed
					FlightSearch.loadhandling(driver);

				}
				// to enroll connect miles
				if (getTrimTdata(queryObjects.getTestData("EnrollConnect")).equalsIgnoreCase("yes")){
					int EnrollPaxcnt;
					
					if(EnrollPax.isEmpty())
						EnrollPaxcnt=1;
					else
						EnrollPaxcnt=Integer.parseInt(EnrollPax);
					List<WebElement> Frequent_Flyer=driver.findElements(By.xpath("//span[@translate=\"pssgui.add.a.frequent.flyer\"]"));
						for(int i=0;i<EnrollPaxcnt;i++)
						{
							Frequent_Flyer=driver.findElements(By.xpath("//span[@translate=\"pssgui.add.a.frequent.flyer\"]"));
							Frequent_Flyer.get(0).click();
							FlightSearch.loadhandling(driver);
							Thread.sleep(200);
							EnrollConnectMiles(driver,queryObjects);
						}

				}
				
				//TTL --- navira start
				try {
					if(FlightSearch.getTrimTdata(queryObjects.getTestData("Time_Limit_TTL")).equalsIgnoreCase("yes")) {
						if(driver.findElements(By.xpath("//div[text()='Expires']")).size()>0) {
							driver.findElement(By.xpath("//div[text()='Expires']")).click();
							wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Time Limit']")));
							driver.findElement(By.xpath("//button[contains(text(),'Edit')]")).click();
							Thread.sleep(2000);
							String TTLDate=getTrimTdata(queryObjects.getTestData("TTLDays"));
							int getTTLDays=0;
							if (TTLDate.isEmpty())
								getTTLDays=1;
							else
								getTTLDays=Integer.parseInt(TTLDate);
	
							Calendar calct = Calendar.getInstance();
							calct.add(Calendar.DATE, +getTTLDays);
							String timeStampdt = new SimpleDateFormat("MM/dd/yyyy").format(calct.getTime());
							if(getTTLDays<=getDays) {
								if (getTTLDays!=0) {
									String TTLDateXpath = "//button[@aria-label='Open calendar']/preceding-sibling::input"; 
									driver.findElement(By.xpath(TTLDateXpath)).clear();
									driver.findElement(By.xpath(TTLDateXpath)).sendKeys(timeStampdt);
									queryObjects.logStatus(driver, Status.PASS, "Checking if the date is extendable", "Date extension possible", null);
	
								}
								Thread.sleep(1000);
								driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
								Thread.sleep(2000);
								FlightSearch.loadhandling(driver);
								
								String ExtDate = driver.findElement(By.xpath("//div[text()='Expires']/following-sibling::div")).getText().trim();
								String dateForm = new SimpleDateFormat("ddMMM").format(calct.getTime());
								String[] compDate = ExtDate.split(" ");
								if(dateForm.equalsIgnoreCase(compDate[1]))
									queryObjects.logStatus(driver, Status.PASS, "Verifying Time Limit", "TTL verification successful", null);
								else
									queryObjects.logStatus(driver, Status.FAIL, "Verifying Time Limit", "Date changed and date displayed not same", null);
									
								
							}
							
							else {
								queryObjects.logStatus(driver, Status.FAIL, "Verifying Time Limit", "TTL verification not successful", null);
							}	
						}	//size()>0) {
					}	 //Time_Limit_TTL")).equalsIgnoreCase("yes")) {
					
				}
				catch(Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Verifying TTL", "TTL verification not successful", null);
				}
				//TTL --- navira End
				//to add secure flight information

				if (getTrimTdata(queryObjects.getTestData("SecureFlightDoc")).equalsIgnoreCase("yes")){
					SecureFlightInfo(driver,queryObjects);

				}
				
				ARNK(driver, queryObjects);   // rushil 
				// call fop fucntion...............
				if (pnrcreated) {
					pnrcreated=false;
					
					String addSSRSpecificSegment=FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRSpecificSegment"));
					String totSSRs=getTrimTdata(queryObjects.getTestData("totSSRs"));
					String ssrNames=getTrimTdata(queryObjects.getTestData("ssrNames"));
					String After_Pay_addSSR_OR_Book_Case_addSSR= getTrimTdata(queryObjects.getTestData("After_Pay_addSSR_OR_Book_Case_addSSR")); 
					// Add ing SSR Before paymet
					if(getTrimTdata(queryObjects.getTestData("addSSRBeforePay")).equalsIgnoreCase("yes")){
						if (getTrimTdata(queryObjects.getTestData("SpecificSSRsforSpecificPAXBefore")).equalsIgnoreCase("yes")) 
							addspecificSSR(driver,queryObjects);
						else
							addSSR(driver,queryObjects,"No",addSSRSpecificSegment,totSSRs,ssrNames,After_Pay_addSSR_OR_Book_Case_addSSR);
						if (getTrimTdata(queryObjects.getTestData("SpecificSSRforAllPAX")).equalsIgnoreCase("yes"))
							addSSR(driver, queryObjects,"",addSSRSpecificSegment,totSSRs,ssrNames,After_Pay_addSSR_OR_Book_Case_addSSR);

					}
					if (getTrimTdata(queryObjects.getTestData("PAY")).equalsIgnoreCase("yes")) {
						String sWaiver=getTrimTdata(queryObjects.getTestData("Waiver"));
						/*String Passengertypes=null;
						if(sWaiver.equalsIgnoreCase("yes"))
							Passengertypes = getPassengerdetails(driver);*/
						//Clicking on Checkout button

					//	driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
						//waiver code
						loadhandling(driver);
						CheckforError(driver,queryObjects);
						//waiver code
						String wave=getTrimTdata(queryObjects.getTestData("MultiWaiverPassenger"));
						String Waiver_Fee=queryObjects.getTestData("MultiWaiver_Fee");
						if (sWaiver.equalsIgnoreCase("yes") ) {
							waivermethod(driver, queryObjects,wave, Waiver_Fee,"");   // performaning waiver operation .....
						} else {
							//Clicking on Checkout button
							driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
							loadhandling(driver);
							CheckforError(driver,queryObjects);
						}
						//Start - Navira
						String type = getTrimTdata(queryObjects.getTestData("Type_of_Transfer"));
						if(!type.equalsIgnoreCase(""))
							bulkFare(driver, queryObjects,type);
						FlightSearch.loadhandling(driver);
						
						//End - Navira
						if (getTrimTdata(queryObjects.getTestData("AgencyPayment")).equalsIgnoreCase("yes")){
							AgencyPayment(driver,queryObjects);

						}
						fop = getTrimTdata(queryObjects.getTestData("FOP"));
						MultipleFOP=getTrimTdata(queryObjects.getTestData("MultipleFOP"));
						if(!fop.equals("")) {
							singleFOP(driver, queryObjects,fop,"issueTicket");
						}
						else if(MultipleFOP.equalsIgnoreCase("yes")) {
							double totalPaymentamt = Double.parseDouble(fareAmount);
							MultipleFOPType = getTrimTdata(queryObjects.getTestData("MultipleFOPType"));
							MultFOPsubType = getTrimTdata(queryObjects.getTestData("MultipleFOPSubType"));
							fopCardNums = getTrimTdata(queryObjects.getTestData("MultipleFOPCardNums"));
							String BankNames = queryObjects.getTestData("MultipleFOPBankName").trim();
							String InstallmentNums = queryObjects.getTestData("MultipleFOPInstallmentNum").trim();
							MulFOP(driver,queryObjects,totalPaymentamt,MultipleFOPType,MultFOPsubType,fopCardNums,BankNames,InstallmentNums,"issueTicket");
						}
						//Click on pay button
						driver.findElement(By.xpath("//button[text()='Pay' and not(@disabled='disabled')]")).click();

						// Handling FOID Details::::

						enterFoiddetails(driver,queryObjects);

						//Handling Email recipients popup
						emailhandling(driver,queryObjects);
						String Error_Msg=FlightSearch.getErrorMSGfromAppliction(driver, queryObjects);
						if (Error_Msg.contains("SameQuoteNbr")) {
							if (Error_Msg.contains("Ticket is out of sync with booked itinerary")) {
								queryObjects.logStatus(driver, Status.PASS, "Enter same segment number in both onward and return segment", "Ticket is out of sync with booked itinerary is displayed, check the segment numbers", null);
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Enter same segment number in both onward and return segment", "Message is not displayed", null);
	
							}
						}
						//payment validation
						String Review_Reject_cases=getTrimTdata(queryObjects.getTestData("Review_Reject_cases"));
						boolean PymtError_Msg = paymentvalidation(driver, queryObjects,Review_Reject_cases);

						//Emdcount
						//No.of ticket issued
						if (pnrcreated && (!PymtError_Msg))
						{
							try {
								List<WebElement> etkts = driver.findElements(By.xpath("//span[text()='Ticket']/following-sibling::span"));
								int totalTKTs = etkts.size();
								etktsnum = new ArrayList<>();

								etkts.forEach(a -> etktsnum.add(a.getText().replace(" ", "")));
								
								if(etktsnum.get(0).contains("-"))
									conjunctiveTicketManualreissue=true;
								else
									conjunctiveTicketManualreissue=false;
								if(totalTKTs == totalnoofPAX) {
									queryObjects.logStatus(driver, Status.PASS, "ETKTs Created", "ETKTs Created for all pax", null);
								}

								else
									queryObjects.logStatus(driver, Status.FAIL, "ETKTs Created", "ETKTs  not created for all pax", null);
							}
							catch(Exception e) {
								queryObjects.logStatus(driver, Status.FAIL, "ETKTs", "ETKs not created for all pax: " + e.getMessage(), e);
							}
							try {
								Emdcount=0;
								List<WebElement> emd = driver.findElements(By.xpath("//span[text()='EMD']/following-sibling::span"));
								Emdcount=emd.size();
							}
							catch(Exception e) {
								queryObjects.logStatus(driver, Status.INFO, "Emd count", "Get EMD count: " + e.getMessage(), e);
							}
							
							//After payment Amout checing and pay button checking ...........
							String Afterpay_amt=driver.findElement(By.xpath("//input[@ng-model='payments.Amount']")).getAttribute("value");
							double dAfterpay_amt=Double.parseDouble(Afterpay_amt);
							if(dAfterpay_amt>0)
								queryObjects.logStatus(driver, Status.FAIL, "After Payment Amount checking", "After payment Amount should Zero ,Actual :"+dAfterpay_amt, null);
							else
								queryObjects.logStatus(driver, Status.PASS, "After Payment Amount checking", "After payment Amount showing Zero..", null);
							
							if(driver.findElements(By.xpath("//button[text()='Pay' and @disabled='disabled']")).size()>0)
								queryObjects.logStatus(driver, Status.PASS, "After Payment Pay button checking", "After payment pay button Disable", null);
							else
								queryObjects.logStatus(driver, Status.FAIL, "After Payment Pay button checking", "After payment pay button should Disable", null);
							//Clicking on Done button
							driver.findElement(By.xpath("//button[text()='Done']")).click();

							// After payment amount checking ........
							loadhandling(driver);
							
							//Afer payment Ticket Status Checking..................
							if(driver.findElements(By.xpath("//div[@translate='Ticketed']")).size()>0)
								queryObjects.logStatus(driver, Status.PASS, "After Payment Ticket Status checking", "After payment Ticket status displaying correctly" , null);
							else
								queryObjects.logStatus(driver, Status.FAIL, "After Payment Ticket Status checking", "After payment Ticket status should displaying correctly" , null);
							
							driver.findElement(By.xpath("//div[div[text()='Tickets']]")).click();
							loadhandling(driver);
							//Start - Navira
							try {
								type = getTrimTdata(queryObjects.getTestData("Type_of_Transfer"));
								if(!type.equalsIgnoreCase("")) {
									if(driver.findElements(By.xpath("//span[contains(text(),'Bulk')]")).size()>0) {
										queryObjects.logStatus(driver, Status.PASS, "Bulk Ticket Verification", "Bulk Ticket created successfully", null);
									}
								}
							}
							catch(Exception e) {
								queryObjects.logStatus(driver, Status.FAIL, "Bulk Ticket Verification", "Bulk Ticket not created", null);
							}
							//End - Navira
							//get ticket numbers
							List<WebElement> getetkts=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]"));
							gettecketno = new ArrayList<>();
							Unwholly.aTicket = null;  // this is for empty array
							tickttkype="E-Ticket";
							getetkts.forEach(a -> gettecketno.add(a.getText().trim()));
							//get Ticket amount
							List<WebElement> getetktsamt=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]//preceding-sibling::div"));
							gettecketamt = new ArrayList<>();
							getetktsamt.forEach(a -> gettecketamt.add(a.getText().trim()));
							
							for (WebElement Lab : getetkts) {
					               String ticketnumber=Lab.getText().trim();
					               ticketnumber.contains("-");
					               ticketnumber=ticketnumber.split("-")[0].trim();
					               List<WebElement> currentticketstate=driver.findElements(By.xpath("//span[text()='"+ticketnumber+"']/parent::div/parent::div//td[contains(@ng-if,'ticketStatusUpdate')]"));
					               if(currentticketstate.get(0).getText().trim().equalsIgnoreCase("Open")) {
					                    FlightSearch.TktStatus.add("Issue"); 
					                 }      
					        }
							
							// get Ticket wise FOP detailssss
							
							if (driver.findElements(By.xpath("//span[contains(text(),'Open')]")).size()>0 && getTrimTdata(queryObjects.getTestData("Void")).equalsIgnoreCase("Ticket"))
							{

								driver.findElement(By.xpath("//span[contains(@class,'md-select-icon')]")).click();
								Thread.sleep(2000);
								//driver.findElement(By.xpath("//div[contains(text(),'Void Ticket')]")).click();
								driver.findElement(By.xpath("//div[contains(@class,'md-active')]//div[contains(text(),'Void Ticket')]")).click();//Xpath Update by Himani
								if(getTrimTdata(queryObjects.getTestData("VoidNeg")).equalsIgnoreCase("Yes")) {
									driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
									loadhandling(driver);
									if (driver.findElement(By.xpath("//td/span[contains(text(),'Open')]")).isDisplayed())
									{
										queryObjects.logStatus(driver, Status.PASS, "Ticket is Not voided: Still Open","Negative Case" ,null);
									}
									else 
									{
										queryObjects.logStatus(driver, Status.FAIL, "Ticket is voided: Issue","Negative Case",null);
									}
								}
								else {
									driver.findElement(By.xpath("//button[contains(text(),'Void Ticket')]")).click();
									loadhandling(driver);
									if (driver.findElement(By.xpath("//td[contains(text(),'VOIDED')]")).isDisplayed())
									{
										queryObjects.logStatus(driver, Status.PASS, "Ticket is voided: ","Positive case" ,null);
									}
									else 
									{
										queryObjects.logStatus(driver, Status.FAIL, "Ticket not voided: ","Positive case",null);
									}
								}
								
								return;
							}
							if(EMDCreated){
							//get EMD Numbers
							driver.findElement(By.xpath("//div[contains(text(),'EMD')]")).click();
							loadhandling(driver);
							List<WebElement> getemds=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']"));
							getemdno = new ArrayList<>();
							getemds.forEach(a -> getemdno.add(a.getText().trim()));
							//get Ticket amount
							List<WebElement> getemdsamt=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']//preceding-sibling::div[1]"));
							getemdamt = new ArrayList<>();
							getemdsamt.forEach(a -> getemdamt.add(a.getText().trim()));
							// Sending Email for EMD confirmation ....
							//22Mar - Navira   
							String emdConfirm = getTrimTdata(queryObjects.getTestData("emdConfirm"));//Column 2
							if(emdConfirm.equalsIgnoreCase("yes")) 
								emdConfirmation(driver,queryObjects);
							
							//Himani 9-Jan
							driver.findElement(By.xpath("//i[contains(@class,'toggle-arrow ng-scope icon-forward')]")).click();
							if (getTrimTdata(queryObjects.getTestData("AgencyPayment")).equalsIgnoreCase("yes")){
								for (int iEmd = 0; iEmd < getemds.size(); iEmd++) {
									
									if(driver.findElements(By.xpath("//i[contains(@class,'toggle-arrow ng-scope icon-forward')]")).size()>0)//Navira
										driver.findElement(By.xpath("//i[contains(@class,'toggle-arrow ng-scope icon-forward')]")).click();
									Thread.sleep(2000);
									if(driver.findElements(By.xpath("//table/tbody//td[contains(text(),'Agency Commission')]")).size()>0){
		        						String semdnoTemp = Login.EMDNO;
		        						semdnoTemp=semdnoTemp.trim();
		        						String emdno=getemds.get(iEmd).getText().trim();
		        						if(!semdnoTemp.isEmpty())  // storing for multiple EMD number in ENv sheet 
		        							Login.EMDNO= semdnoTemp+";"+emdno;
		        						else
		        							Login.EMDNO= emdno;
		        						
		                        		if(!driver.findElement(By.xpath("//table/tbody//td/div[@role='button']")).equals("OPEN")) {
		                        			driver.findElement(By.xpath("//table/tbody//td/div[@role='button']")).click();
		                        			driver.findElement(By.xpath("//label[text()='Status']/following-sibling::md-select")).click();
		                        			driver.findElement(By.xpath("//md-option[contains(@ng-value,'emdstatusType')]/div")).click();
		                        			driver.findElement(By.xpath("//button[contains(@type,'submit')]")).click();
		                        			loadhandling(driver);
		                        		}
		                        		                        
		                        	}
									driver.findElement(By.xpath("//i[contains(@class,'toggle-arrow ng-scope icon-arrow-down')]")).click();
									Thread.sleep(2000);
									
								}
							}
							
							//Himani
							
							//driver.findElement(By.xpath("//i[contains(@class,'toggle-arrow ng-scope icon-forward')]")).click();
                            driver.findElement(By.xpath("//div[contains(@class,'md-container md-ink-ripple')]")).click();
                            Thread.sleep(500);
                            //check if void EMD
                            if ((driver.findElements(By.xpath("//div[contains(text(),'Flown')]")).size()>0 || driver.findElements(By.xpath("//div[contains(text(),'Open')]")).size()>0) && getTrimTdata(queryObjects.getTestData("Void")).equalsIgnoreCase("EMD") )
                            {
                            	driver.findElement(By.xpath("//md-select[@aria-label='EMD Action']")).click();
                            	loadhandling(driver);

                            	//driver.findElement(By.xpath("//div[contains(@class,'md-container md-ink-ripple']")).click();
                            	if(getTrimTdata(queryObjects.getTestData("VoidNeg")).equalsIgnoreCase("Yes")) {
                            		queryObjects.logStatus(driver, Status.PASS, "EMD is not voided: ","Negative case" ,null);
                            	}
                            	else {
                            		driver.findElement(By.xpath("//div[contains(text(),' Void EMD')]")).click();
                                	loadhandling(driver);
                                	driver.findElement(By.xpath("//i[contains(@class,'toggle-arrow ng-scope icon-forward')]")).click();
                                	Thread.sleep(500);
                                	if (driver.findElement(By.xpath("//div[contains(text(),'Voided')]")).isDisplayed())
                                	{
                                		queryObjects.logStatus(driver, Status.PASS, "EMD is voided: ","Positive case" ,null);
                                	}
                                	else 
                                	{
                                		queryObjects.logStatus(driver, Status.FAIL, "EMD not voided: ","Positive case",null);
                                	}
                            	}
                            	
                            	return;
                            }
                            driver.findElement(By.xpath("//i[contains(@class,'toggle-arrow ng-scope icon-arrow-down')]")).click();
						}
                            
                            
							if (FlightSearch.getTrimTdata(queryObjects.getTestData("TaxCodeVerifiation")).equalsIgnoreCase("yes")) {
								driver.findElement(By.xpath("//div[div[text()='Tickets']]")).click();
								Thread.sleep(1000);
								getetkts=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]"));
								String xpathis="//tr[@ng-if='orderTableDisplay.tableData.taxes']/td[@ng-if='orderTableDisplay.tableData.taxes']/div/div[1]";
								for (int iticket = 0; iticket < getetkts.size(); iticket++) {
									getetkts=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]"));
									Thread.sleep(1000);
									getetkts.get(iticket).click();
									FlightSearch.loadhandling(driver);
									queryObjects.logStatus(driver, Status.PASS, "Tax Code verification for "+(iticket+1)+" pax", "Tax Code verification for "+(iticket+1)+" PAX", null);
									PNRsearch.TaxCodeValidation(driver,queryObjects,xpathis,"Ticket Tab");  // tax code verification
									driver.findElement(By.xpath("//span[contains(text(),'All Passengers')]")).click();
									FlightSearch.loadhandling(driver);
								}
							}
							coupon_control(driver, queryObjects);   // added Rushil
							if (gettecketno.containsAll(etktsnum)) {
								queryObjects.logStatus(driver, Status.PASS, "After payment Ticket number checking", "Ticket number issued correctly", null);
							}
							else {
								queryObjects.logStatus(driver, Status.FAIL, "After payment Ticket number checking", "Ticket number should issue correctly", null);
							}
							if (Login.Usernm.equalsIgnoreCase("cm.bog.agent")) // validate Colombia Fee for colombia login...
								if (driver.findElement(By.xpath("//div[contains(text(),'Service Fee de Colombia') or contains(text(),'Service Fee (CO)')]")).isDisplayed())
									queryObjects.logStatus(driver, Status.PASS, "Colombia login Colombia service free checking", "Colombia login Colombia service fee added", null);
								else
									queryObjects.logStatus(driver, Status.FAIL, "Colombia login Colombia service free checking", "Colombia login Colombia service fee should be Add", null);

							String Afterpaytotalamt=getTrimTdata(driver.findElement(By.xpath("//div[@model='currency.total']/div")).getText());
							String AfterpayBalanceamt=getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
							String Afterpaypadid=getTrimTdata(driver.findElement(By.xpath("//div[@model='currency.paid']/div")).getText());

							if (Double.valueOf(AfterpayBalanceamt).intValue()==0) {
								queryObjects.logStatus(driver, Status.PASS, "After payment  Balance amount checking", "After payment  amount showing zero ", null);
							}
							else {
								queryObjects.logStatus(driver, Status.FAIL, "After payment  Balance amount checking", "After payment  amount should show zero actual: "+AfterpayBalanceamt+" expected: 0", null);
							}
							String aexpt=roundDouble(String.valueOf(Double.parseDouble(fareAmount) + emdAmount));
							String aact=roundDouble(Afterpaytotalamt);
							if (sWaiver.equalsIgnoreCase("yes") ) {
								aexpt=roundDouble(String.valueOf(AfterWaiver + emdAmount));
							}
							
							if (Double.parseDouble(aact)==Double.parseDouble(aexpt)) {
								queryObjects.logStatus(driver, Status.PASS, "After payment  Total amount checking", "After payment Total amount showing  correctly total amt "+aact, null);
							}
							else if (getTrimTdata(queryObjects.getTestData("AgencyPayment")).equalsIgnoreCase("yes")){
								queryObjects.logStatus(driver, Status.WARNING, "AgencyPayment: After payment  Total amount checking", "After payment Totlal amount should show correctly actual: "+aact+" expected: "+aexpt, null);
								queryObjects.logStatus(driver, Status.WARNING, "AgencyPayment: After payment  Total amount checking","fare Amount is:"+Double.parseDouble(fareAmount) +" emd Amount is: "+emdAmount ,null);
							}
							else {
								queryObjects.logStatus(driver, Status.FAIL, "After payment  Total amount checking", "After payment Totlal amount should show correctly actual: "+aact+" expected: "+aexpt, null);
								queryObjects.logStatus(driver, Status.FAIL, "After payment  Total amount checking","fare Amount is:"+Double.parseDouble(fareAmount) +" emd Amount is: "+emdAmount ,null);
							}
							
							if (Double.parseDouble(roundDouble(Afterpaypadid))==Double.parseDouble(roundDouble(Afterpaytotalamt))) {
								queryObjects.logStatus(driver, Status.PASS, "After payment  Paid amount checking", "After payment Paid amount showing  correctly paid amt : "+Afterpaypadid, null);
							}
							else {
								queryObjects.logStatus(driver, Status.FAIL, "After payment  Paid amount checking", "After payment Paid amount should show correctly actual: "+Afterpaypadid+" expected: "+Afterpaytotalamt, null);
							}
							classselectbon=true;
							//Adding Multiple PNR - Added by Jenny
							//String mulpnr=Login.envRead(Login.MultiplePNR).trim();
							String mulpnr=Login.MultiplePNR.trim();
							if (!mulpnr.equals("")) {
								String MulPNR = Login.MultiplePNR.trim();
								//String MulPNR = Login.envRead(Login.MultiplePNR).trim();
								Pnris = MulPNR +";"+ Pnris;
								Login.MultiplePNR= Pnris;
								//Login.envwrite(Login.MultiplePNR, Pnris);
							} else 
								Login.MultiplePNR= Pnris;
							// jenny code Resudual emd
							//Create Residual EMD
							if (Residual_Emd.contains("Reissue")) {
								Reissue_Residual(driver, queryObjects, Residual_Emd);
							}
							//  jenny code resudual emd
						}
					}
					else{   //getTrimTdata(queryObjects.getTestData("PAY")).equalsIgnoreCase("yes")
						classselectbon=true;
					}
				}	

			}
			String ssurnm=getTrimTdata(queryObjects.getTestData("surname")).toUpperCase();
			String sfirstnm=getTrimTdata(queryObjects.getTestData("FirstName")).toUpperCase();

			String modifyName = getTrimTdata(queryObjects.getTestData("Modify_Firstname"));

			if(!modifyName.equals("")) {
				changeFirstnm(driver, queryObjects, ssurnm, sfirstnm, modifyName);
			}

			// adding FFP -----------
			String addFFPafterPNR = getTrimTdata(queryObjects.getTestData("AddFFPafterPNR"));
			if(addFFPafterPNR.equalsIgnoreCase("yes")) { 
				String stotalFFP=getTrimTdata(queryObjects.getTestData("totalFFP"));
				String sFFPnumbers=getTrimTdata(queryObjects.getTestData("FFPnumbers"));
				String sFFPcode=getTrimTdata(queryObjects.getTestData("FFPcode"));


				if (!stotalFFP.isEmpty()) {
					int itotalFFP=Integer.parseInt(stotalFFP);
					if (itotalFFP==1) {
						FFcode=sFFPcode;
						FFnum=sFFPnumbers;
						ffplastName=ssurnm.toUpperCase();
						ffpfirstName=sfirstnm.toUpperCase();
						ffp(driver,queryObjects);	
					}
					else if (itotalFFP>1) {
						String[] asurnm=ssurnm.split(";");
						String[] afirstnm=sfirstnm.split(";");
						String[] aFFPnumbers=sFFPnumbers.split(";");
						String[] aFFPcode=sFFPcode.split(";");
						for (int iffpiter = 0; iffpiter < itotalFFP; iffpiter++) {
							FFcode=aFFPcode[iffpiter];
							FFnum=aFFPnumbers[iffpiter];
							ffplastName=asurnm[iffpiter].toUpperCase();
							ffpfirstName=afirstnm[iffpiter].toUpperCase();
							ffp(driver,queryObjects);
						}
					}
				}
			}
			// Assign seat...............
			String sAssignseatAllPAX=getTrimTdata(queryObjects.getTestData("AssignseatAllPAX"));
			String assignSeatSinglePAX = getTrimTdata(queryObjects.getTestData("AssignSeatSinglePAX"));
			String assignSpecSeat = getTrimTdata(queryObjects.getTestData("AssignSpecSeat"));
			if ((classselectbon==true) && (sAssignseatAllPAX.equalsIgnoreCase("YES") || !assignSeatSinglePAX.equals("") || !assignSpecSeat.equals(""))){  
				assignSeat(driver,queryObjects,totalpax, sAssignseatAllPAX, assignSeatSinglePAX, assignSpecSeat);
				if (!getTrimTdata(queryObjects.getTestData("Expected_erromsg")).equalsIgnoreCase("disabled"))
					paymentAfterassignseat(driver, queryObjects);
			}
			
			String addSSRSpecificSegment=FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRSpecificSegment"));
			String totSSRs=getTrimTdata(queryObjects.getTestData("totSSRs"));
			String ssrNames=getTrimTdata(queryObjects.getTestData("ssrNames"));
			String After_Pay_addSSR_OR_Book_Case_addSSR= getTrimTdata(queryObjects.getTestData("After_Pay_addSSR_OR_Book_Case_addSSR")); 
			if (getTrimTdata(queryObjects.getTestData("addSSRAfterPay")).equalsIgnoreCase("yes")) {
				if (getTrimTdata(queryObjects.getTestData("SpecificSSRsforSpecificPAXAfter")).equalsIgnoreCase("yes")) 
					addspecificSSR(driver,queryObjects);
				else
					addSSR(driver, queryObjects,"",addSSRSpecificSegment,totSSRs,ssrNames,After_Pay_addSSR_OR_Book_Case_addSSR);
				if (getTrimTdata(queryObjects.getTestData("SpecificSSRforAllPAX")).equalsIgnoreCase("yes"))
					addSSR(driver, queryObjects,"",addSSRSpecificSegment,totSSRs,ssrNames,After_Pay_addSSR_OR_Book_Case_addSSR);

				String saddSSRafterpay=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
			//	Servicename= driver.findElement(By.xpath("//div[contains(@ng-repeat,\"service in quoteDetail.passenger.payableServices\")]")).getText();
				if(!saddSSRafterpay.isEmpty())
				{
					if (Double.parseDouble(saddSSRafterpay)>0 || driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).isEnabled()) {
						// ssr case payment
						queryObjects.logStatus(driver, Status.PASS, "After add ssr Pay the amount","Try to pay amount", null);
						//Click on check out button
						//Add Tax and Verify the same
						/*if (FlightSearch.getTrimTdata(queryObjects.getTestData("TaxCodeVerifiation")).contains("SetTax-SSR")) {
							String TaxCode=FlightSearch.getTrimTdata(queryObjects.getTestData("TaxCode"));
							String TaxPer = FlightSearch.getTrimTdata(queryObjects.getTestData("TaxPrcnt"));
							String ModPrice = FlightSearch.getTrimTdata(queryObjects.getTestData("ModifyPrice"));
							driver.findElement(By.xpath("//i[@class='icon-add']/..//span[@translate='pssgui.add.a.tax']")).click();
							driver.findElement(By.xpath("//input[@ng-model='TaxInfo.TaxCode']")).sendKeys(TaxCode);
							if (!TaxPer.isEmpty()) {
								driver.findElement(By.xpath("//input[@ng-model='service.Amount']")).sendKeys(TaxPer);
							}
							if (!ModPrice.isEmpty()) {
								driver.findElement(By.xpath("//input[@ng-model='TaxInfo.pricingOption']")).sendKeys(ModPrice);
							}
						}*/
						String sWaiver=getTrimTdata(queryObjects.getTestData("Waiver"));
						String Passengertypes=null;
						/*if(sWaiver.equalsIgnoreCase("yes"))
							Passengertypes = getPassengerdetails(driver);*/
						String saddSSRamt=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
						double daddSSRamt=Double.parseDouble(saddSSRamt);
					//	driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
						FlightSearch.loadhandling(driver);	
						//waiver code
						String wave=getTrimTdata(queryObjects.getTestData("payseat_WaiverPassenger"));
						String Waiver_Fee=queryObjects.getTestData("payseat_Waiver_Fee");
						if (sWaiver.equalsIgnoreCase("yes") ) {
							waivermethod(driver, queryObjects,wave,Waiver_Fee,"");   // performaning waiver operation .....
						} else {
							driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
							FlightSearch.loadhandling(driver);
						}
						if (FlightSearch.getTrimTdata(queryObjects.getTestData("TaxCodeVerifiation")).contains("SetTax-SSR")) {
							String TaxCode=FlightSearch.getTrimTdata(queryObjects.getTestData("TaxCode"));
							String TaxPer = FlightSearch.getTrimTdata(queryObjects.getTestData("TaxPrcnt"));
							String ModPrice = FlightSearch.getTrimTdata(queryObjects.getTestData("ModifyPrice"));
							if (!ModPrice.isEmpty()) {
								driver.findElement(By.xpath("//i[contains(@ng-click,'quoteDetail.editService')]")).click();
								driver.findElement(By.xpath("//input[@ng-model='service.Amount']")).sendKeys(ModPrice);
								loadhandling(driver);
								driver.findElement(By.xpath("//input[@ng-model='service.Amount']")).clear();
								driver.findElement(By.xpath("//input[@ng-model='service.Amount']")).sendKeys(ModPrice);
								driver.findElement(By.xpath("//input[@ng-model='service.Amount']")).sendKeys(Keys.TAB);
								saddSSRafterpay = ModPrice;
							}
							if (!TaxPer.isEmpty()) {
								driver.findElement(By.xpath("//i[@class='icon-add']/..//span[@translate='pssgui.add.a.tax']")).click();
								driver.findElement(By.xpath("//input[@ng-model='TaxInfo.TaxCode']")).sendKeys(TaxCode);
								driver.findElement(By.xpath("//input[@ng-model='TaxInfo.pricingOption']")).sendKeys(TaxPer);
								saddSSRafterpay = Double.parseDouble(saddSSRafterpay) + Double.parseDouble(TaxPer)+"";
							}
							driver.findElement(By.xpath("//md-select[@ng-model='WaiverReasonInfo.process']")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath("//div[contains(@class,'md-active')]//md-option[@ng-value='ProcessList']/div[1]")).click();
		
							driver.findElement(By.xpath("//md-select[@ng-model='WaiverReasonInfo.Reason']")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath("//md-option[@ng-repeat='Reason in WaiverReasonInfo.process.Reason']/div[contains(text(),'WOTH SERVICERECOVERY')]")).click();
							driver.findElement(By.xpath("//button[text()='Next']")).click();
							loadhandling(driver);
							if (Double.parseDouble(driver.findElement(By.xpath("//input[@name='amount']")).getAttribute("value"))==Double.parseDouble(saddSSRafterpay)) {
								queryObjects.logStatus(driver, Status.PASS, "Check the amount to be paid is per the service fee and the tax provided", "SSR amount is updated as the given value", null);
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Check the amount to be paid is per the service fee and the tax provided", "SSR amount to be paid is correct", null);
							}
						}
						// Before payment amount checking....
						FlightSearch.loadhandling(driver);
						/*fop = FlightSearch.getTrimTdata(queryObjects.getTestData("FOP"));
						FlightSearch.singleFOP(driver, queryObjects,fop,"SSRCASE");*/
						//mul fop
						// clear the static value
						MultipleFOPType="";
						MultFOPsubType="";
						fopCardNums="";

						MultipleFOPType = FlightSearch.getTrimTdata(queryObjects.getTestData("paySSRMultipleFOPType"));
						MultFOPsubType = FlightSearch.getTrimTdata(queryObjects.getTestData("paySSRMultipleFOPSubType"));
						fopCardNums = FlightSearch.getTrimTdata(queryObjects.getTestData("paySSRMultipleFOPCardNums"));
						String BankNames = queryObjects.getTestData("paySSRMultipleBankName").trim();
						String InstallmentNums = queryObjects.getTestData("paySSRMultipleInstallmentNum").trim();
						MulFOP(driver,queryObjects,daddSSRamt,MultipleFOPType,MultFOPsubType,fopCardNums,BankNames,InstallmentNums,"SSRCASE");
						
						//

						driver.findElement(By.xpath("//button[text()='Pay' and not(@disabled='disabled')]")).click();
						// Handling FOID Details::::

						FlightSearch.enterFoiddetails(driver,queryObjects);

						//Handling Email recipients popup
						FlightSearch.emailhandling(driver,queryObjects);
						
						try {
							wait = new WebDriverWait(driver, 120);
							String Expected_erromsg=queryObjects.getTestData("Expected_erromsg");
							if(Expected_erromsg.equalsIgnoreCase("Non-Carrier(OA) Flights are not supported to issue an EMD")) {
								queryObjects.logStatus(driver, Status.PASS, "Other Airlines - SSR assignment", "Non-Carrier(OA) Flights are not supported to issue an EMD", null);		 
								driver.findElement(By.xpath("//button[text()='Cancel']")).click();
						        loadhandling(driver);
						        return;
							}
							else {
								wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
								String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
								queryObjects.logStatus(driver, Status.PASS, "Payment", statusMessage, null);
							}

						}
						catch(Exception e) {
							queryObjects.logStatus(driver, Status.FAIL, "Payment", "Payment Unsuccessful: " + e.getMessage() , e);
						}
						driver.findElement(By.xpath("//button[text()='Done']")).click();
						loadhandling(driver);
					}
				}
				
				//Krishna

				driver.findElement(By.xpath("//div[div[text()='Order']]")).click();
				loadhandling(driver);
				
				driver.findElement(By.xpath("//div[text()='Tickets']")).click();
				loadhandling(driver);
				
				driver.findElement(By.xpath("//div[contains(text(),'EMD')]")).click();
				FlightSearch.loadhandling(driver);
				
				//Get EMD No
				List<WebElement> getemds=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']"));
				FlightSearch.getemdno = new ArrayList<>();
				getemds.forEach(a -> FlightSearch.getemdno.add(a.getText().trim()));
				
				//Get EMD Amount
				List<WebElement> getemdsamt=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']//preceding-sibling::div[1]"));
				FlightSearch.getemdamt = new ArrayList<>();
				getemdsamt.forEach(a -> FlightSearch.getemdamt.add(a.getText().trim()));
				Thread.sleep(2000);
				
				loadhandling(driver);
				driver.findElement(By.xpath("//div[div[text()='Order']]")).click();
				loadhandling(driver);
				
				//22Mar - Navira
				String errorInQuote = getTrimTdata(queryObjects.getTestData("errorQuoteScreen"));//Column1 22Mar
				if(errorInQuote.equalsIgnoreCase("yes")) {
					driver.findElement(By.xpath("//div[div[text()='Tickets']]")).click();
					loadhandling(driver);
					List<WebElement> getetkts=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]"));
					boolean No_Fare_rue=true;
					for (int ct = 0; ct < getetkts.size(); ct++) {
						getetkts.get(ct).click();
						loadhandling(driver);
						driver.findElement(By.xpath("//div[text()='Fare Rules']")).click();
						loadhandling(driver);
						if(driver.findElements(By.xpath("//div[text()='No Fare Rules available for this Order']")).size()>0)
							No_Fare_rue=true;
						else
							No_Fare_rue=false;
					}
					if(No_Fare_rue)
						queryObjects.logStatus(driver, Status.PASS, "Checking No fares rules message", "No fare rules message present", null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "Checking No fares rules message", " 'No fare rules message ' should be displayed  ", null);
				}
				
				//Check Baggage Rules Updated after Adding SSR
				String Rules = getTrimTdata(queryObjects.getTestData("Rules"));//Jenny Feb 10
				if (!getTrimTdata(queryObjects.getTestData("Rules")).isEmpty()) {
					if (!Rules.isEmpty() && Rules.contains("BaggageRules")) {
						driver.findElement(By.xpath("//div[div[text()='Tickets']]")).click();
						loadhandling(driver);
						List<WebElement> getetkts=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]"));
						String Origin = Rules.substring(Rules.indexOf(";")+1, Rules.indexOf("-"));
						String Destination = Rules.substring(Rules.indexOf("-")+1, Rules.length());
						for (int ct = 0; ct < getetkts.size(); ct++) {
							getetkts.get(ct).click();
							loadhandling(driver);
							driver.findElement(By.xpath("//div[contains(text(),'Baggage Rules')]")).click();
							//Check the Baggage Rules are added for the onward segments
							List<WebElement> airports=driver.findElements(By.xpath("//div[contains(@class,'airport-code')]"));
							for (int ac = 0; ac < airports.size(); ac++) {
								if (airports.get(ac).getText().contains(Origin.join(" ", ""))) {
									driver.findElement(By.xpath("//div[contains(@class,'airport-code') and contains(text(),'"+Origin+" ')]/parent::div/following-sibling::div//i[contains(@class,'icon-forward')]")).click();
									if (driver.findElements(By.xpath("//div[contains(@ng-repeat,'rules in baggage')]")).size()>0) {
										queryObjects.logStatus(driver, Status.PASS, "Check the Baggage Rules updated after adding SSR", "Baggage Rules are updated" , null);
									} else {
										queryObjects.logStatus(driver, Status.FAIL, "Check the Baggage Rules updated after adding SSR", "Baggage Rules are not updated" , null);
									}
									driver.findElement(By.xpath("//div[contains(@class,'airport-code') and contains(text(),'"+Origin+" ')]/parent::div/following-sibling::div//i[contains(@class,'icon-arrow-down')]")).click();
									if (!airports.get(ac).getText().contains(" "+Destination) && airports.get(ac+1).getText().contains(" "+Destination)) {
										driver.findElement(By.xpath("//div[contains(text()[2],'"+Destination+"')]/../following-sibling::div[contains(@ng-if,'BaggageInfo')]/..//i[contains(@class,'icon-forward')]")).click();
										if (driver.findElements(By.xpath("//div[contains(@ng-repeat,'rules in baggage')]")).size()>0) {
											queryObjects.logStatus(driver, Status.PASS, "Check the Baggage Rules updated after adding SSR", "Baggage Rules are updated for the second segment" , null);
										} else {
											queryObjects.logStatus(driver, Status.FAIL, "Check the Baggage Rules updated after adding SSR", "Baggage Rules are not updated for the second segment" , null);
										}
										driver.findElement(By.xpath("//div[contains(text()[2],'"+Destination+"')]/../following-sibling::div[contains(@ng-if,'BaggageInfo')]/..//i[contains(@class,'icon-arrow-down')]")).click();
									}
								} 
								//BaggageRules;HAV-COR
								//BaggageRules;PTY-JFK
							}
						}
					}
				}
			}
			SSRComets_Update(driver, queryObjects);
			if (getTrimTdata(queryObjects.getTestData("SplitPNR")).equalsIgnoreCase("yes")) {
				PNRsearch Split = new PNRsearch();
				//String oldPnr =Login.envRead(Login.PNRNUM).trim();
				String oldPnr =Login.PNRNUM.trim();
				Split.SplitBooking(driver,queryObjects,oldPnr);
			}

			String refund=getTrimTdata(queryObjects.getTestData("Refund"));
			if ((classselectbon==true) && (refund.equalsIgnoreCase("YES")))  {	
				if(! Login.CardDeposit_Change_Salesoffice.isEmpty())   // card Deposit case change the POS
					PNRsearch.ChangeSalesOffice(driver,queryObjects,Login.CardDeposit_Change_Salesoffice,Login.Cur);
				refundticket(driver,queryObjects);
			}
			if ((pnrcreated) && (getTrimTdata(queryObjects.getTestData("PNRVerify_CCFOP")).equalsIgnoreCase("YES"))) {			
				//Refresh the PNR - Give PNR number and click search
				  if(Pnris.contains(";"))
	                    Pnris = Pnris.split(";")[1].trim();   // tacking last value need to check
				driver.findElement(By.xpath("//pssgui-search-panel[@label='pssgui.search.reservations']/div/md-input-container/input[@ng-model='searchPanel.searchText']")).sendKeys(Pnris);
				Thread.sleep(500);
				driver.findElement(By.xpath("//pssgui-search-panel[@label='pssgui.search.reservations']/div/i[@class='icon-search']")).click();
				loadhandling(driver);
				Thread.sleep(500);
				//Click on order tab
				driver.findElement(By.xpath("//div[@translate='pssgui.order']")).click();
				Thread.sleep(500);
				//Click on Remarks tab
				driver.findElement(By.xpath(remarks_Xpath)).click();
				Thread.sleep(500);
				PNRTempPath = "//div[contains(@class, 'pssgui-link ng-binding') and contains(text(), ':') and contains(text(), '"+Pnris+"') and contains(text(), '-1')]";
				try{
					//Get the PNR number
					if ((MultipleFOP.equalsIgnoreCase("yes")) && (Creditlength ==2)) {
						PNRTempPath2 = "//div[contains(@class, 'pssgui-link ng-binding') and contains(text(), ':') and contains(text(), '"+Pnris+"') and contains(text(), '-2')]";
						CyberPNR= (driver.findElement(By.xpath(PNRTempPath)).getText().trim())+";"+(driver.findElement(By.xpath(PNRTempPath2)).getText().trim());
					} else {
						CyberPNR= driver.findElement(By.xpath("//div[contains(@class, 'pssgui-link ng-binding') and contains(text(), ':') and contains(text(), '"+Pnris+"')]")).getText().trim();
					}	
					Aftecybebersourec_Update_check_GUI=true;
				}catch(Exception e){
					queryObjects.logStatus(driver, Status.FAIL, "PNR not available in Remarks tab", "PNR not available in Remarks tab", null);
				}
			}

		}
		catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Flight search", e.getLocalizedMessage(), e);

		}
	}
	//After create the pnr add FFP
	public static  void ffp(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException{


		try {
			FFpPax=true;
			//Clicking on Travelers tab
			//FFcode=getTrimTdata(queryObjects.getTestData(""));
			//driver.findElement(By.xpath("//div[div[contains(text(),'Travelers')]]")).click();
			ffplastName=ffplastName.toUpperCase();
			ffpfirstName=ffpfirstName.toUpperCase();
			FFcode=FFcode.toUpperCase();
			driver.findElement(By.xpath("//div[div[span[contains(text(),'"+ffplastName+"') and contains(text(),'"+ffpfirstName+"')]]]//span[contains(@ng-click,'frequentFlyer')]")).click();
			loadhandling(driver);
			//Clicking on Edit button
			driver.findElement(By.xpath("//button[contains(text(),'Edit')]")).click();
			loadhandling(driver);
			//clicking on FF program list and selecting the program
			driver.findElement(By.xpath("//pssgui-menu[@menu-model='fqtvDetail.ProgramID']//md-select")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@id,'select_container')]//md-option[div[span[contains(text(),'"+ FFcode +"')]]]")).click();
			Thread.sleep(2000);
			//Entering FF number
			driver.findElement(By.xpath("//input[@name='ffNumber']")).click();
			driver.findElement(By.xpath("//input[@name='ffNumber']")).clear();
			driver.findElement(By.xpath("//input[@name='ffNumber']")).sendKeys(FFnum);

			//Click on "Validate FF" button
			/*try{
				driver.findElement(By.xpath("//button[contains(text(),'Validate FF') and not(@disabled='disabled')]")).click();
				Thread.sleep(2000);
				//click on update
				driver.findElement(By.xpath("//button[contains(text(),'Cancel') and not(@disabled='disabled')]")).click();
				Thread.sleep(1000);
			}

			catch(Exception e) {
				//Log status fail
			}*/
			driver.findElement(By.xpath("//button[contains(text(),'OK') and not(@disabled='disabled')]")).click();
			loadhandling(driver);
			String errormsg="";
			try{
				errormsg=driver.findElement(By.xpath("//span[contains(@class,'msg-error')]")).getText().trim();
			}catch(Exception e) {queryObjects.logStatus(driver, Status.INFO, "FFP log", e.getLocalizedMessage(), e);}
			if (!errormsg.isEmpty())
				queryObjects.logStatus(driver, Status.FAIL, "Adding FFP time getting error message ", "it is showing error message:: "+errormsg, null);

			String getfftype="";
			try{
				//getfftype=driver.findElement(By.xpath("//div[div[span[contains(text(),'"+ffplastName+"') and contains(text(),'"+ffpfirstName+"')]]]//div[contains(@ng-if,'fqtvInfo.ProgramID')]/span[2]")).getText().trim();
				//getfftype=driver.findElement(By.xpath("//div[div[span[contains(text(),'"+ffplastName+"') and contains(text(),'"+ffpfirstName+"')]]]//span[contains(@ng-if,'fqtvInfo.ProgramID')]")).getText().trim();
				getfftype=driver.findElement(By.xpath("//div[div[span[contains(text(),'"+ffplastName+"') and contains(text(),'"+ffpfirstName+"')]]]//span[contains(text(),'"+ FFcode +"')]")).getText().trim();
			}catch(Exception e) {queryObjects.logStatus(driver, Status.INFO, "FFP log", e.getLocalizedMessage(), e);}
			if (getfftype.equalsIgnoreCase(FFcode))
				queryObjects.logStatus(driver, Status.PASS, "FFp and validating", "FFP added successfully", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "FFp and validating", "After click on Ok button FFP should add in PAX grid", null);
			//Log status pass
		}
		catch(Exception e) {
			//Log status fail
			queryObjects.logStatus(driver, Status.FAIL, "FFP log", e.getLocalizedMessage(), e);
		}

	}

	public static String getTrimTdata(String inp1) throws Exception{
		String returnstring = "";
		if (inp1!=null){
			returnstring=inp1.trim();
		}
		return returnstring;
	}

	public static String right(String value, int length) {
		// To get right characters from a string, change the begin index.
		return value.substring(value.length() - length);
	}

	// enter Adtional credit card details.
	public static void enterCCdetails(WebDriver driver,BFrameworkQueryObjects queryObjects, String CCVnum ) throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 2);
		String expDate = new SimpleDateFormat("MMyyyy").format(cal.getTime());
		BillerInfo = FlightSearch.getTrimTdata(queryObjects.getTestData("BillerDetails"));//Added by Jenny
		
		if (BillerInfo != "") {
			
			SplitBillerMore=BillerInfo.split("~");
			
			SplitBillerDet = SplitBillerMore[PPSBillDet].split(";");
			if(SplitBillerMore.length > 1)
				PPSBillDet=PPSBillDet+1;
			
			SplitName = SplitBillerDet[0].split(" ");			
		}
		String activediv="//div[contains(@class,'amount-container') and @aria-hidden='false']";
		//driver.findElement(By.xpath("//input[@name='CCCC']")).sendKeys(Keys.TAB);
		//Robot robot = new Robot();
		//robot.mouseMove(10,10);
		//Entering Expiry date
		//driver.findElement(By.xpath(activediv+"//input[@name='CCEX']")).click();
		driver.findElement(By.xpath(activediv+"//input[@name='CCEX']")).sendKeys(expDate);
		//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //5
		try{
			if (driver.findElement(By.xpath(activediv+"//input[@name='CCAC']")).isDisplayed()) {
				String authCode = "1234";
				//Entering Authorization Code
				//driver.findElement(By.xpath(activediv+"//input[@name='CCAC']")).click();
				driver.findElement(By.xpath(activediv+"//input[@name='CCAC']")).sendKeys(authCode);
			}
		}
		catch (Exception e) {
			queryObjects.logStatus(driver, Status.INFO, "Card details", e.getLocalizedMessage(), e);
		}

		try{
			//robot.mouseMove(20,20);
			if (driver.findElement(By.xpath(activediv+"//input[@name='CCCV']")).isDisplayed()) {
				if(CCVnum.isEmpty())
					CCVnum= "1234";
				//Entering Authorization Code
				//driver.findElement(By.xpath(activediv+"//input[@name='CCCV']")).click();
				driver.findElement(By.xpath(activediv+"//input[@name='CCCV']")).sendKeys(CCVnum);
				CCVnum="";
			}
		}
		catch (Exception e) {
			queryObjects.logStatus(driver, Status.INFO, "Card details", e.getLocalizedMessage(), e);
		}
		try{
			//robot.mouseMove(9,25);
			if (driver.findElement(By.xpath(activediv+"//input[@name='CCCH']")).isDisplayed()) {
				String carholdernm = "";//Updated by Jenny
				if (BillerInfo!= "") {
					carholdernm = SplitBillerDet[0];					
				}else {
					carholdernm = "copacard";
				}
				//Entering Authorization Code
				//driver.findElement(By.xpath(activediv+"//input[@name='CCCH']")).click();
				driver.findElement(By.xpath(activediv+"//input[@name='CCCH']")).sendKeys(carholdernm);
			}
			//robot.mouseMove(20,26);
			if (driver.findElement(By.xpath(activediv+"//input[@name='GINA']")).isDisplayed()) {
				String carholdergvnnm = "";//Updated by Jenny
				if (BillerInfo!= "") {
					carholdergvnnm = SplitName[0];					
				}else {
					carholdergvnnm = "copaCH";
				}

				//Entering Authorization Code
				//driver.findElement(By.xpath(activediv+"//input[@name='GINA']")).click();
				driver.findElement(By.xpath(activediv+"//input[@name='GINA']")).sendKeys(carholdergvnnm);
			}
			//robot.mouseMove(8,27);
			if (driver.findElement(By.xpath(activediv+"//input[@name='SUNA']")).isDisplayed()) {
				String carholdersurnm = "";//Updated by Jenny
				if (BillerInfo!= "") {
					carholdersurnm = SplitName[1];					
				}else {
					carholdersurnm = "copaMPHASIS";
				}
				//Entering Authorization Code
				//driver.findElement(By.xpath(activediv+"//input[@name='SUNA']")).click();
				driver.findElement(By.xpath(activediv+"//input[@name='SUNA']")).sendKeys(carholdersurnm);
			}
			//robot.mouseMove(20,28);
			if (driver.findElement(By.xpath(activediv+"//input[@name='EMAIL']")).isDisplayed()) {
				String carholderemail = "";//Updated by Jenny
				if (BillerInfo!= "") {
					carholderemail = SplitBillerDet[7];					
				}else {
					carholderemail = "copaMPHASIS@HPE.COM";
				}
				//Entering Authorization Code
				//driver.findElement(By.xpath(activediv+"//input[@name='EMAIL']")).click();
				driver.findElement(By.xpath(activediv+"//input[@name='EMAIL']")).sendKeys(carholderemail);
			}
			//robot.mouseMove(5,29);
			if (driver.findElement(By.xpath(activediv+"//input[@name='FONE']")).isDisplayed()) {
				String carholderphone = "";//Updated by Jenny
				if (BillerInfo!= "") {
					carholderphone = SplitBillerDet[6];					
				}else {
					carholderphone = "123456787";
				}
				//Entering Authorization Code
				//driver.findElement(By.xpath(activediv+"//input[@name='FONE']")).click();
				driver.findElement(By.xpath(activediv+"//input[@name='FONE']")).sendKeys(carholderphone);
			}
			//robot.mouseMove(20,30);
			if (driver.findElement(By.xpath(activediv+"//input[@name='ADL1']")).isDisplayed()) {
				String carholderadd = "";//Updated by Jenny
				if (BillerInfo!= "") {
					carholderadd = SplitBillerDet[1];					
				}else {
					carholderadd = "DLFBLOCK";
				}
				//Entering Authorization Code
				//driver.findElement(By.xpath(activediv+"//input[@name='ADL1']")).click();
				driver.findElement(By.xpath(activediv+"//input[@name='ADL1']")).sendKeys(carholderadd);
			}
			//robot.mouseMove(30,31);
			if (driver.findElement(By.xpath(activediv+"//input[@name='CITY']")).isDisplayed()) {
				String cardholdecity = "";//Updated by Jenny
				if (BillerInfo!= "") {
					cardholdecity = SplitBillerDet[2];					
				}else {
					cardholdecity = "panama";
				}
				//Entering Authorization Code
				//driver.findElement(By.xpath(activediv+"//input[@name='CITY']")).click();
				driver.findElement(By.xpath(activediv+"//input[@name='CITY']")).sendKeys(cardholdecity);
			}
			//robot.mouseMove(20,32);
			if (driver.findElement(By.xpath(activediv+"//input[@name='STAT']")).isDisplayed()) {
				String cardholdeState = "";//Updated by Jenny
				if (BillerInfo!= "") {
					cardholdeState = SplitBillerDet[3];					
				}else {
					cardholdeState = "PA";
				}
				//Entering Authorization Code
				//driver.findElement(By.xpath(activediv+"//input[@name='STAT']")).click();
				driver.findElement(By.xpath(activediv+"//input[@name='STAT']")).sendKeys(cardholdeState);
			}
			//robot.mouseMove(4,33);
			if (driver.findElement(By.xpath(activediv+"//input[@name='ZIP']")).isDisplayed()) {
				String cardholdezip = "";//Updated by Jenny
				if (BillerInfo!= "") {
					cardholdezip = SplitBillerDet[4];					
				}else {
					cardholdezip = "32408";
				}
				//Entering Authorization Code
				//driver.findElement(By.xpath(activediv+"//input[@name='ZIP']")).click();
				driver.findElement(By.xpath(activediv+"//input[@name='ZIP']")).sendKeys(cardholdezip);
			}
			//robot.mouseMove(20,34);
			if (driver.findElement(By.xpath(activediv+"//input[@name='country']")).isDisplayed()) {
				String cardholdecountry = "";//Updated by Jenny
				if (BillerInfo!= "") {
					cardholdecountry = SplitBillerDet[5];					
				}else {
					cardholdecountry = "PA";
				}
				//Entering Authorization Code
				//driver.findElement(By.xpath(activediv+"//input[@name='country']")).click();
				driver.findElement(By.xpath(activediv+"//input[@name='country']")).sendKeys(cardholdecountry);
				loadhandling(driver);
				//driver.findElement(By.xpath("//md-virtual-repeat-container[@aria-hidden='false']//ul/li[1]")).click();
				driver.findElement(By.xpath(clickUSpopuXpath)).click();
				//driver.findElement(By.xpath(activediv+"//input[@name='country']")).sendKeys(Keys.TAB);
			}


		}
		catch (Exception e) {
			queryObjects.logStatus(driver, Status.INFO, "Card details", e.getLocalizedMessage(), e);
		}
		//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //60
		
		//this is for clicking Autorize payment button if enable click
		List<WebElement> authorize_payment=driver.findElements(By.xpath("//button[@translate='pssgui.authorize.payment']"));
		if(authorize_payment.size()>0){
			authorize_payment.get(0).click();
			queryObjects.logStatus(driver, Status.PASS, " Clicking Autorize payment button", "Credit card payment in ATO login clicking the button", null);
			loadhandling(driver);
		}
		
	}

	public static void emailhandling(WebDriver driver,BFrameworkQueryObjects queryObjects) throws InterruptedException, Exception{
		loadhandling(driver);
		//Handling Email recipients popup
		//(RandomStringUtils.random(6, true, false)+"@mphsis.com");
		String Expected_erromsg=queryObjects.getTestData("Expected_erromsg");
		if(driver.findElements(By.xpath("//div[contains(text(),'Email Recipient')]")).size()>0) {

			java.util.List<WebElement> lang = driver.findElements(By.xpath("//pssgui-menu[@action='dropdown']//md-input-container/md-select"));
			for(int i =0; i<lang.size(); i++) {
				//driver.findElement(By.xpath("//div[contains(@ng-repeat,'printEmail.recipients')]["+(i+1)+"]//input")).clear();
				WebElement email=driver.findElement(By.xpath("//div[contains(@ng-repeat,'printEmail.recipients')]["+(i+1)+"]//input"));
				if (email.getAttribute("class").contains("ng-empty")) {
					email.sendKeys(RandomStringUtils.random(6, true, false)+"@mphsis.com");
					loadhandling(driver);
				}
				lang.get(i).click();
				loadhandling(driver);
				//driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'clickable')]//md-option[2]")).click();
				//22Mar - Navira
				String emailLanguage = getTrimTdata(queryObjects.getTestData("emailLang"));
				if(emailLanguage.equalsIgnoreCase("Spanish"))
					driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'clickable')]//md-option[1]")).click();
				else if(emailLanguage.equalsIgnoreCase("Portugese"))
					driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'clickable')]//md-option[3]")).click();
				else
					driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'clickable')]//md-option[2]")).click();
				Thread.sleep(3000);
			}	

			driver.findElement(By.xpath("//md-dialog//button[contains(text(),'Email')]")).click();
			loadhandling(driver);
			
			//kishore
			String error_count_xpath="//div[div[div[contains(text(),'Email Recipient')]]]//child::span[contains(@class,'error-count')]";
			if(driver.findElements(By.xpath(error_count_xpath)).size()>0){
				String Error_count=driver.findElement(By.xpath(error_count_xpath)).getText().trim();
				int iError_count=Integer.parseInt(Error_count);
				boolean bError=false;
				if(iError_count>=1){
					bError=true;
					driver.findElement(By.xpath("//div[div[div[contains(text(),'Email Recipient')]]]//child::span[contains(@class,'error-count')]//preceding-sibling::span")).click();
					Thread.sleep(3000);
				}

				if(bError==true){
					
				//WebElement ee=	driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and @aria-hidden='false']"));
				WebElement email=	driver.findElement(By.xpath("//div[contains(text(),'Email Recipient')]"));
				 if(Expected_erromsg.equalsIgnoreCase("invalid_Email")){	
					 List<WebElement> GetError_msg = driver.findElements(By.xpath("//md-menu-content[@class='error-panel-menu']/md-menu-item/div/div[contains(@class,'msg-error') or contains(@class,'msg-alert')]"));
					 for (int iErro =0 ; iErro < GetError_msg.size(); iErro++) { 
						 String sError_msg=GetError_msg.get(iErro).getText().trim();
						 if(sError_msg.contains("102 - One or more fields in the request contains invalid data. c:billTo/c:email for which values are invalid")){
							 queryObjects.logStatus(driver, Status.PASS, "While payment Given Wrong email id Error message checking", "If we given wrong Email id it is  display Error message", null);
							 
						 }
						 if(sError_msg.contains("Non-Carrier(OA) Flights are not supported to issue an EMD")){
							 queryObjects.logStatus(driver, Status.PASS, "Other Airlines - SSR assignment", "Non-Carrier(OA) Flights are not supported to issue an EMD", null);		 
							 
						 }
						//Navira - 05Mar
						 else if(sError_msg.contains("(1) INVALID CREDIT CARD NUMBER: 10027")){
							 queryObjects.logStatus(driver, Status.PASS, "Negative Case - Providing incorrect Card Number", "(1) INVALID CREDIT CARD NUMBER: 10027", null);		 
							 
						 }
						//Navira - 19Mar
						 else if(sError_msg.contains("Merchant ID for payment")){
							 queryObjects.logStatus(driver, Status.WARNING, "Merchant ID for Payment error", "Credit Card/ Currency not tagged to the POS", null);		 
							 
						 }
					 }			 		 
				}
					Actions actions = new Actions(driver);
			        actions.moveToElement(email);
			        actions.click();
			       // if(Expected_erromsg.equalsIgnoreCase("invalid_Email")||Expected_erromsg.equalsIgnoreCase("Non-Carrier(OA) Flights are not supported to issue an EMD")){
		        	 //if(Expected_erromsg.equalsIgnoreCase("invalid_Email")||Expected_erromsg.equalsIgnoreCase("Non-Carrier(OA) Flights are not supported to issue an EMD")||Expected_erromsg.equalsIgnoreCase("(1) INVALID CREDIT CARD NUMBER: 10027")){	//Navira - 05Mar
	        		 if(Expected_erromsg.equalsIgnoreCase("invalid_Email")||Expected_erromsg.equalsIgnoreCase("Non-Carrier(OA) Flights are not supported to issue an EMD")||Expected_erromsg.equalsIgnoreCase("(1) INVALID CREDIT CARD NUMBER: 10027")||Expected_erromsg.equalsIgnoreCase("Merchant ID for payment")){	//Navira - 05Mar
			        	 actions.build().perform();
			        	driver.findElement(By.xpath("//button[@aria-label='Cancel']")).click();
					}
			        
				}
			}
			//kishore	
			
		}
	}

	public static void loadhandling(WebDriver driver){
		try {
			//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  //5
			Thread.sleep(2000);
			driver.findElement(By.xpath(loadXpath));
			//driver.findElement(By.xpath("//div[contains(@class,'loading-wrapper')]"));
			WebDriverWait wait = new WebDriverWait(driver, 300);
			wait = new WebDriverWait(driver, 300);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loadXpath)));
			//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'loading-wrapper')]")));
			Thread.sleep(2000);
		}
		catch(Exception e) {
			//driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);//60
		}		

	}

	public static void assignSeat(WebDriver driver,BFrameworkQueryObjects queryObjects,int totalPAX, String sAssignseatAllPAX, String assignSeatSinglePAX, String assignSpecSeat) throws InterruptedException, IOException {
		try{ 
			
			String Seatnm="";
			String Reassginstnm="";
			String paymentSeat="";
			List<WebElement> TotalFlight=new ArrayList<>();
			//Clicking on Services button
			WebDriverWait wait = new WebDriverWait(driver, 300);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[div[text()='Services']]")));
			driver.findElement(By.xpath("//div[div[text()='Services']]")).click();
			loadhandling(driver);
			//Selecting "All Segments" check box
			//WebDriverWait wait = new WebDriverWait(driver, 300);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//md-checkbox[div[span[text()='All Segments']]]")));

			String cheboxstateAllSegment=driver.findElement(By.xpath("//md-checkbox[div[span[text()='All Segments']]]")).getAttribute("aria-checked");
			String cheboxstateAllpax=driver.findElement(By.xpath("//md-checkbox[div[span[text()='All Segments']]]")).getAttribute("aria-checked");
			if (Boolean.parseBoolean(cheboxstateAllSegment)==false)
				driver.findElement(By.xpath("//md-checkbox[div[span[text()='All Segments']]]")).click();
			Thread.sleep(2000);
			//Selecting All Passengers
			if (Boolean.parseBoolean(cheboxstateAllpax)==false)
				driver.findElement(By.xpath("//md-checkbox[div[span[text()='All Passengers']]]")).click();
			Thread.sleep(1000);

			//Selecting Assign Seat icon
			driver.findElement(By.xpath("//button[@class='seat-link']")).click();
			loadhandling(driver);

			if(sAssignseatAllPAX.equalsIgnoreCase("yes") && getTrimTdata(queryObjects.getTestData("Expected_erromsg")).equalsIgnoreCase("disabled")) {
				Boolean AssignSeat = driver.findElement(By.xpath("//form[@name='seat.form']/div[1]//div[contains(@class,'passengerNo')]")).isEnabled();
				if (AssignSeat) {

					queryObjects.logStatus(driver, Status.PASS, "Assigning Seat should be disabled", "Seat was disabled", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Assigning Seat should be disabled", "Seat was enabled", null);
				}

			}
			else if(sAssignseatAllPAX.equalsIgnoreCase("yes")) {
				TotalFlight=driver.findElements(By.xpath("//md-content[contains(@class,'segment-box')]/div[contains(@class,'seatmap-segmen')]/div"));
				int size=TotalFlight.size();
				seatamt="";
				double dAmount = 0; 
				for(int allflights=1;allflights<=size;allflights++)
				{
					if(allflights>=2)
					{
						driver.findElement(By.xpath("//md-content[contains(@class,'segment-box')]/div[contains(@class,'seatmap-segmen')]/div["+allflights+"]//child::tbody/tr[@ng-repeat='leg in flight']/td[1]/md-checkbox/div")).click();
						loadhandling(driver);
					}
					String flightnos=driver.findElement(By.xpath("//md-content[contains(@class,'segment-box')]/div[contains(@class,'seatmap-segmen')]/div["+allflights+"]/div/div[1]")).getText();
					for(int i=1; i<=totalPAX; i++) {
						
						Seatnm="";
						Reassginstnm="";
						//clicking on the number assigned for the passenger
						//Thread.sleep(3000);
						loadhandling(driver);
						try {
							//loadhandling(driver);
							if(driver.findElements(By.xpath("//form[@name='seat.form']/div["+ i +"]//div[contains(@class,'passengerNo')and not (contains(@class,'selected'))]")).size()>0) 
								driver.findElement(By.xpath("//form[@name='seat.form']/div["+ i +"]//div[contains(@class,'passengerNo')and not (contains(@class,'selected'))]")).click();
							
								if (Re_Assignseat) {
									Seatnm=driver.findElement(By.xpath("//form[@name='seat.form']/div["+ i +"]//div[div[contains(@class,'passengerNo')]]/span/input")).getAttribute("value");
								}
							Thread.sleep(4000);
							paymentSeat=getTrimTdata(queryObjects.getTestData("paymentSeat"));
							if(paymentSeat.equalsIgnoreCase("yes")){
								//newy added for critical
								if (!assignSpecSeat.equals("")) {
									if(!assignSpecSeat.equalsIgnoreCase("exit window")) {  // other than exit seat need to click drop down...
										//driver.findElement(By.xpath("//form[@name='seat.form']/div[1]//div[@class='passenger-seat-details']/div[contains(text(),'"+i+"')]/following::md-input-container[1]")).click();
										driver.findElement(By.xpath("//form[@name='seat.form']/div[1]//div[@class='passenger-seat-details']/div[contains(text(),'1')]/following::md-input-container["+i+"]")).click();
										Thread.sleep(1000);
									}
									if(assignSpecSeat.equalsIgnoreCase("exit")) {
										driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-option[div[contains(text(),'Exit')]]")).click();
										//driver.findElement(By.xpath("//span[text()='Yes']/parent::button")).click();
										driver.findElement(By.xpath("//md-dialog/md-dialog-actions/button[2]")).click();//meenu
										Thread.sleep(1000);
									}
									else if(assignSpecSeat.equalsIgnoreCase("premium")) {
										driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-option[div[contains(text(),'Premium')]]")).click();
									}
									// exit row window selection // rushil
									else if(assignSpecSeat.equalsIgnoreCase("exit window")) {
										List<WebElement> rows = driver.findElements(By.xpath("(//div[contains(@class,'seat-row')]/div[contains(@class,'exit-row exit-left')]/../div[@class='cabin-rows'])"));
										int rowCount = rows.size();
										
										if(rowCount>0) {
											boolean ere=false;
										for(int rwcnt=1;rwcnt<=rowCount;rwcnt++) {
											List<WebElement> cells = driver.findElements(By.xpath("(//div[contains(@class,'seat-row')]/div[contains(@class,'exit-row exit-left')]/../div[@class='cabin-rows'])["+rwcnt+"]//div[contains(@class,'air-seat-list')]//div[contains(@class,'seat-holder')]"));
											int cellCount = cells.size();
											if(cellCount>1) {
											 if(driver.findElement(By.xpath("((//div[contains(@class,'seat-row')]/div[contains(@class,'exit-row exit-left')]/../div[@class='cabin-rows'])["+rwcnt+"]//div[contains(@class,'air-seat-list')]//div[contains(@class,'seat-holder')])[1]")).getAttribute("class").contains("icn-available")) {
												 driver.findElement(By.xpath("((//div[contains(@class,'seat-row')]/div[contains(@class,'exit-row exit-left')]/../div[@class='cabin-rows'])["+rwcnt+"]//div[contains(@class,'air-seat-list')]//div[contains(@class,'seat-holder')])[1]")).click();
												 Thread.sleep(1000);
												 driver.findElement(By.xpath("//md-dialog-actions//button[contains(text(),'Yes')]")).click();
												 queryObjects.logStatus(driver, Status.PASS, "Selecting Window seat in exit row", "Window seat selected", null);
												 ere=true;
												 break;
											 }
											 else if(driver.findElement(By.xpath("((//div[contains(@class,'seat-row')]/div[contains(@class,'exit-row exit-left')]/../div[@class='cabin-rows'])["+rwcnt+"]//div[contains(@class,'air-seat-list')]//div[contains(@class,'seat-holder')])["+cellCount+"]")).getAttribute("class").contains("icn-available")) {
												 driver.findElement(By.xpath("((//div[contains(@class,'seat-row')]/div[contains(@class,'exit-row exit-left')]/../div[@class='cabin-rows'])["+rwcnt+"]//div[contains(@class,'air-seat-list')]//div[contains(@class,'seat-holder')])["+cellCount+"]")).click();
												 Thread.sleep(1000);
												 driver.findElement(By.xpath("//md-dialog-actions//button[contains(text(),'Yes')]")).click();
												 queryObjects.logStatus(driver, Status.PASS, "Selecting Window seat in exit row", "Window seat selected", null);
												 ere=true;
												 break;
											 }
											}
											else {
												queryObjects.logStatus(driver, Status.FAIL, "Checking if Exit row is empty", "Seats in Exit row" +cellCount, null);
											}
										}
										if (ere=false) {
											queryObjects.logStatus(driver, Status.FAIL, "Checking if no exit row window seats available", "Available windows seats in Exit row" + ere, null);
										} else {

										}
										}
										else {
											queryObjects.logStatus(driver, Status.FAIL, "Checking if any Exit row exist in seatmap", "No. of Exit row" +rowCount, null);
										}
										}
									
									
									} else {
									
									//critical casse added
									List<WebElement> allPaymentseat=driver.findElements(By.xpath("//div[span[text()='V']]"));
									if (allPaymentseat.size()==0) 
										allPaymentseat=driver.findElements(By.xpath("//div[span[text()='T']]"));
									if (allPaymentseat.size()==0) 
										queryObjects.logStatus(driver, Status.FAIL, "Try to Assigning PaymentSeat", "Payment Seats not available", null);
									else{
										allPaymentseat.get(0).click();
										loadhandling(driver);
										String getseatamt=driver.findElement(By.xpath("//div[contains(@ng-class,'amountCtrl')]")).getText().trim();
										seatamt=getseatamt.split("\\s+")[0];
										// CR payment seat amount validation
										double kk=0;
	                                    /*String row = getTrimTdata(queryObjects.getTestData("seat_rownumber"));
	                                    String rowno[] = new String[10];
	                                    String amt[] = new String[10];
	                                    if(!row.isEmpty()){
		                                    if(row.contains(";")) {//Multiple segments
		                                           rowno = row.split(";");
		                                           
			                                   //double sum = 0;
			                                   //for(int iter=0;iter<rowno.length;iter++) {
			                                          driver.findElement(By.xpath("//md-content[contains(@class,'segment-box')]/div[contains(@class,'seatmap-segmen')]/div["+(iter+1)+"]//child::tbody/tr[@ng-repeat='leg in flight']/td[1]/md-checkbox/div")).click();
			                                         loadhandling(driver);
			                                     int iRows = Integer.parseInt(rowno[allflights-1]);
			                                     amt[allflights-1] = envRead_PremSeats(iRows,1);//Column 1 is for V seats
			                                     
			                                      kk=Double.parseDouble(seatamt);
			                                     dAmount = dAmount + Double.parseDouble(amt[allflights-1]);
			                                     String finAmount = Double.toString(dAmount); 
			                                     String curcurrency=driver.findElement(By.xpath("//div[@action='saleOfficeInfo']/div[3]")).getText();
			                                     Double dAmt = Unwholly.Convert_Amt(driver,queryObjects,"USD",curcurrency,finAmount);
			                                     if(dAmt==kk) {
			                                            queryObjects.logStatus(driver, Status.PASS, "Verifying the amount for payment seats", "Expected Amount: "+dAmt+" and Actual Amount: "+kk+" are matching", null);
			                                     }
			                                    // else
			                                          //  queryObjects.logStatus(driver, Status.WARNING, "Verifying the amount for payment seats", "Expected Amount: "+dAmt+" and Actual Amount: "+kk+" are not matching Data ::Test data dependancy", null);            
		                                    }
		                                    else {
		                                           int iRow = Integer.parseInt(rowno[1]);
		                                           String expAmt = envRead_PremSeats(iRow,2);//Column 2 is for V seats
		                                           dAmount = Double.parseDouble(expAmt);
		                                           if(dAmount==kk) {
		                                                  queryObjects.logStatus(driver, Status.PASS, "Verifying the amount for payment seats", "Expected Amount: "+dAmount+" and Actual Amount: "+kk+" are matching", null);
		                                           }
		                                           //else
		                                             //     queryObjects.logStatus(driver, Status.WARNING, "Verifying the amount for payment seats", "Expected Amount: "+dAmount+" and Actual Amount: "+kk+" are matching ::Test data dependancy", null);
		                                           
		                                    }
										}
	                                    else */
										// CR payment seat amount validation
										 kk=Double.parseDouble(seatamt); 
	                                    
										if(FFpPax==false){   // if no FFP pax for created PNR then amount will applicable 
											if (kk>0.0)
												queryObjects.logStatus(driver, Status.PASS, "After assigen non Free seat amount checking", "seat amount showing", null);
											else
												queryObjects.logStatus(driver, Status.FAIL, "After assigen non Free seat amount checking", "seat should show amount it is showing " +seatamt, null);
										}
										else if(FFpPax){
											if (kk==0.0 || kk==0)
												queryObjects.logStatus(driver, Status.PASS, "After assigen non Free seat amount checking", "Not displaying amount for FFP pax with/without non FFP pax  both  in same PNR", null);
											else
												queryObjects.logStatus(driver, Status.FAIL, "After assigen non Free seat amount checking", "should Not displaying amount for FFP pax with/without non FFP pax  both  in same PNR " +seatamt, null);
										}
									}
	
								}
							}
							else{  // else part of payment seat
								//Clicking on list item to select type of seat to be assigned
								/*driver.findElement(By.xpath("//form[@name='seat.form']/div["+ i +"]//md-input-container/md-select")).click();
								Thread.sleep(4000);
								//Selecting option
								driver.findElement(By.xpath("//div[contains(@id,'select_container') and @aria-hidden='false']//md-option[div[text()='Nonsmoking, aisle seat']]")).click();
								Thread.sleep(4000);*/
								if(!assignSpecSeat.equals("")) {
									driver.findElement(By.xpath("//form[@name='seat.form']/div['"+i+"']//div[@class='passenger-seat-details']/div[contains(text(),'"+i+"')]/following::md-input-container[1]")).click();
									Thread.sleep(1000);

									if(assignSpecSeat.equalsIgnoreCase("nonsmoking window")) {
										driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-option[div[contains(text(),'window')]]")).click();
									}
									else if(assignSpecSeat.equalsIgnoreCase("nonsmoking aisle")) {
										driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-option[div[contains(text(),'aisle')]]")).click();
									}
									else if(assignSpecSeat.equalsIgnoreCase("exit")) {
										driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-option[div[contains(text(),'Exit')]]")).click();
										driver.findElement(By.xpath("//span[text()='Yes']/parent::button")).click();
										Thread.sleep(1000);
									}
									else if(assignSpecSeat.equalsIgnoreCase("premium")) {
										driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-option[div[contains(text(),'Premium')]]")).click();
									}
									// exit row window selection // rushil
									else if(assignSpecSeat.equalsIgnoreCase("exit window")) {
										List<WebElement> rows = driver.findElements(By.xpath("(//div[contains(@class,'seat-row')]/div[contains(@class,'exit-row exit-left')]/../div[@class='cabin-rows'])"));
										int rowCount = rows.size();
										
										if(rowCount>1) {
											boolean ere=false;
											for(int rwcnt=1;rwcnt<=rowCount;rwcnt++) {
												List<WebElement> cells = driver.findElements(By.xpath("(//div[contains(@class,'seat-row')]/div[contains(@class,'exit-row exit-left')]/../div[@class='cabin-rows'])["+rwcnt+"]//div[contains(@class,'air-seat-list')]//div[contains(@class,'seat-holder')]"));
												int cellCount = cells.size();
												if(cellCount>1) {
												 if(driver.findElement(By.xpath("((//div[contains(@class,'seat-row')]/div[contains(@class,'exit-row exit-left')]/../div[@class='cabin-rows'])["+rwcnt+"]//div[contains(@class,'air-seat-list')]//div[contains(@class,'seat-holder')])[1]")).getAttribute("class").contains("icn available")) {
													 driver.findElement(By.xpath("((//div[contains(@class,'seat-row')]/div[contains(@class,'exit-row exit-left')]/../div[@class='cabin-rows'])[2]//div[contains(@class,'air-seat-list')]//div[contains(@class,'seat-holder')])[1]")).click();
													 Thread.sleep(1000);
													 driver.findElement(By.xpath("//md-dialog-actions//button[contains(text(),'Yes')]")).click();
													 queryObjects.logStatus(driver, Status.PASS, "Selecting Window seat in exit row", "Window seat selected", null);
													 ere=true;
													 break;
												 } 
												 else if(driver.findElement(By.xpath("((//div[contains(@class,'seat-row')]/div[contains(@class,'exit-row exit-left')]/../div[@class='cabin-rows'])["+rwcnt+"]//div[contains(@class,'air-seat-list')]//div[contains(@class,'seat-holder')])["+cellCount+"]")).getAttribute("class").contains("icn available")) {
													 driver.findElement(By.xpath("((//div[contains(@class,'seat-row')]/div[contains(@class,'exit-row exit-left')]/../div[@class='cabin-rows'])[2]//div[contains(@class,'air-seat-list')]//div[contains(@class,'seat-holder')])["+cellCount+"]")).click();
													 Thread.sleep(1000);
													 driver.findElement(By.xpath("//md-dialog-actions//button[contains(text(),'Yes')]")).click();
													 queryObjects.logStatus(driver, Status.PASS, "Selecting Window seat in exit row", "Window seat selected", null);
													 ere=true;
													 break;
												 }
												}
									else {
										queryObjects.logStatus(driver, Status.FAIL, "Checking if Exit row is empty", "Seats in Exit row" +cellCount, null);
									}
								}
								if (ere=false) {
									queryObjects.logStatus(driver, Status.FAIL, "Checking if no exit row window seats available", "Available windows seats in Exit row" + ere, null);
								}
							}
							else {
								queryObjects.logStatus(driver, Status.FAIL, "Checking if any Exit row exist in seatmap", "No. of Exit row" +rowCount, null);
							}
						}
								}
						//end of exit row window condition
								else {
								String seatMapXpath="//div[contains(@class,'cabin')]//child::div[contains(@class,'icn-available') and not (contains(@class,'icn-chargeable')) and not (contains(@class,'icn-held')) and not (contains(@class,'seat-selection-color'))]";
								List<WebElement> allPaymentseat=driver.findElements(By.xpath(seatMapXpath));
								if(allPaymentseat.size()>0)  // if seat map available only click
									allPaymentseat.get(0).click();
								else
									queryObjects.logStatus(driver, Status.WARNING,"Seat Assignment","Flight map not available",null);
								}
							
							}
							if (Re_Assignseat) {
								Reassginstnm=driver.findElement(By.xpath("//form[@name='seat.form']/div["+ i +"]//div[div[contains(@class,'passengerNo')]]/span/input")).getAttribute("value");
								if(!Reassginstnm.equalsIgnoreCase(Seatnm))
									queryObjects.logStatus(driver, Status.PASS,"Seat Re_Assigen validation","Seat Re_Assigned successfully for this flight no "+flightnos +"and "+i+" PAX ",null);
								else
									queryObjects.logStatus(driver, Status.FAIL,"Seat Re_Assigen validation","Seat Re_Assigned un successfully for this flight no "+flightnos +"and "+i+" PAX ",null);
							}
						}
						catch (Exception e){
							queryObjects.logStatus(driver, Status.FAIL, "Assigning Seat", e.getLocalizedMessage(), e);
						}
					}
				}
				if(paymentSeat.equalsIgnoreCase("yes"))
					driver.findElement(By.xpath("//div[@aria-disabled='false' and span[contains(text(),'Add To Order')]]")).click();
				else
					driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click(); //Clicking on submit button
				loadhandling(driver);

				if(!paymentSeat.equalsIgnoreCase("yes")){
					//wait=new WebDriverWait(driver, 50);
					//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='seat-link' and @disabled='disabled']")));
					loadhandling(driver);
					//Need to check whether seat assigned or not
					List<WebElement> seatsAssigned = driver.findElements(By.xpath("//div[contains(@ng-repeat,'seat in serviceIcon')]//span[@ng-if='seat.number']"));

					for(WebElement seat: seatsAssigned) {
						if(seat.getText().trim().length() < 2) {
							/*isSeatAssigned = false;
							break;*/
						}
						else{
							isSeatAssigned=true;}
					}
				}

			}
			else if(!assignSeatSinglePAX.equals("")) {
				TotalFlight=driver.findElements(By.xpath("//md-content[contains(@class,'segment-box')]/div[contains(@class,'seatmap-segmen')]/div"));
				int size=TotalFlight.size();
				for(int allflights=1;allflights<=size;allflights++)
				{
					if(allflights>=2)
					{
						driver.findElement(By.xpath("//md-content[contains(@class,'segment-box')]/div[contains(@class,'seatmap-segmen')]/div["+allflights+"]//child::tbody/tr[@ng-repeat='leg in flight']/td[1]/md-checkbox/div")).click();
						loadhandling(driver);
					}
					driver.findElement(By.xpath("//form[@name='seat.form']/div[1]//div[@class='passenger-seat-details']/div[contains(text(),'"+assignSeatSinglePAX+"')]/preceding-sibling::div")).click();
					if(!assignSpecSeat.equals("")) {
						driver.findElement(By.xpath("//form[@name='seat.form']/div[1]//div[@class='passenger-seat-details']/div[contains(text(),'"+assignSeatSinglePAX+"')]/following::md-input-container[1]")).click();
						Thread.sleep(1000);

						if(assignSpecSeat.equalsIgnoreCase("nonsmoking window")) {
							driver.findElement(By.xpath("//div[@aria-hidden='false']//md-option[div[contains(text(),'window')]]")).click();
						}
						else if(assignSpecSeat.equalsIgnoreCase("nonsmoking aisle")) {
							driver.findElement(By.xpath("//div[@aria-hidden='false']//md-option[div[contains(text(),'aisle')]]")).click();
						}
						else if(assignSpecSeat.equalsIgnoreCase("exit")) {
							driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-option[div[contains(text(),'Exit')]]")).click();
							driver.findElement(By.xpath("//span[text()='Yes']/parent::button")).click();
							Thread.sleep(1000);
						}
						else if(assignSpecSeat.equalsIgnoreCase("premium")) {
							driver.findElement(By.xpath("//div[@aria-hidden='false']//md-option[div[contains(text(),'Premium')]]")).click();
						}
					}
					//Clicking on submit button
					driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
					loadhandling(driver);
					wait=new WebDriverWait(driver, 50);
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='seat-link' and @disabled='disabled']")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@ng-repeat,'seat in serviceIcon')]//span[@ng-if='seat.number']")));
					Thread.sleep(4000);
					loadhandling(driver);
					//Need to check whether seat assigned or not
					List<WebElement> seatsAssigned = driver.findElements(By.xpath("//div[contains(@ng-repeat,'seat in serviceIcon')]//span[@ng-if='seat.number']"));
					isSeatAssigned=false;
					for(WebElement seat: seatsAssigned) {
						/*if(seat.getText().trim().length() < 2) {
						isSeatAssigned = false;
						break;
					}
					else{
						isSeatAssigned=true;}*/
						if(seat.getText().trim().length()==2)
							isSeatAssigned=true;
					}

				}



			}
			if (getTrimTdata(queryObjects.getTestData("Expected_erromsg")).equalsIgnoreCase("") && (!paymentSeat.equalsIgnoreCase("yes"))){
				if(isSeatAssigned ) 
					queryObjects.logStatus(driver, Status.PASS, "Assigning Seat", "Seat assigned", null);
				else 
					queryObjects.logStatus(driver, Status.WARNING, "Assigning Seat", "Seat/Flights assign have problem", null);
			}
		}

		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Assigning Seat", e.getLocalizedMessage(), e);
		}


	}

	public void  MulFOP(WebDriver driver,BFrameworkQueryObjects queryObjects,double totalPaymentamt,String MultipleFOPType,String MultFOPsubType,String fopCardNums ,String BankNames,String InstallmentNums,String tansationType) throws Exception{


		//	totalAmount = amount.getText().trim();
		//totalPayment = Double.parseDouble(fareAmount);
		totalPayment = totalPaymentamt;
		PPSBillDet=0;  // this is for entering builling details more than one case so start 0 then increase

		String loginPOS=Login.Call_Center_Login;
		String CreditCard_Xpath = null;


		CCVnums = getTrimTdata(queryObjects.getTestData("MulCCV"));
		if (!CCVnums.isEmpty())
			CCVnumber = CCVnums.split(";");

		ppspaymentmodedouble=MultipleFOPType;

		FOPtype = MultipleFOPType.split(";");
		subType = MultFOPsubType.split(";");
		fopCardNum = fopCardNums.split(";");
		String[] BankName;
		String[] InstallmentNum;
		//String BankNames = queryObjects.getTestData("BankName").trim();
		//String InstallmentNums = queryObjects.getTestData("InstallmentNum").trim();
		BankName=BankNames.split(";");
		InstallmentNum=InstallmentNums.split(";");
		
		int n=FOPtype.length;
		for(int k=0;k<n;k++)
		{
			if(FOPtype[k].equalsIgnoreCase("creditcard"))
				Creditlength++;
		}

		//String semdno = Login.envRead(Login.EMDNO);
		String semdno = Login.EMDNO;
		String[] asemdno=semdno.split(";");

		String semdamt = Login.EMDAmt;
		//String semdamt = Login.envRead(Login.EMDAmt);

		String[] asemdamt=semdamt.split(";");

		int emditer=0;
		partialAmount = convertinteger(String.valueOf(totalPayment/FOPtype.length));  //(totalPayment/FOPtype.length);
		WebElement amount=null;
		WebDriverWait wait = new WebDriverWait(driver, 60);
		for(int ifop = 1; ifop<=FOPtype.length; ifop++) {

			if(ifop < FOPtype.length) {
				if(!subType[ifop-1].equalsIgnoreCase("emd")){
					wait = new WebDriverWait(driver, 60);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//input[@aria-label='amount']")));
					amount = driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//input[@aria-label='amount']"));
					amount.click();
					amount.clear();
					amount.sendKeys(partialAmount);
				}
			}
			String payamtmulfop=driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//input[@aria-label='amount']")).getAttribute("value").trim();
			storepayAmount(payamtmulfop);
			queryObjects.logStatus(driver, Status.PASS, "Form of Payment", "FOP type: " + FOPtype[ifop-1], null);
			if (loginPOS.equalsIgnoreCase("YES")) 
				CreditCard_Xpath=Credit_Debitcard_CC_Xpath;
			else
				if ((FOPtype[ifop-1].equalsIgnoreCase("creditcard")))
					CreditCard_Xpath=Creditcard_ATO_CTO_Xpath;
				else if ((FOPtype[ifop-1].equalsIgnoreCase("Debitcard")))
					CreditCard_Xpath=Debitcard_ATO_CTO_Xpath;

			if(FOPtype[ifop-1].equalsIgnoreCase("cash")) {
				//if(ifop>1) {
					wait = new WebDriverWait(driver, 60);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")));	
					//Clicking on FOP select list
					driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Cash')]]")).click();
					//paymentmodeHidenchek(driver);
				//}
			}
			else if(FOPtype[ifop-1].equalsIgnoreCase("creditcard")) {
				//Clicking on FOP select list

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")));
				driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")).click();
				Thread.sleep(2000);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CreditCard_Xpath)));
				driver.findElement(By.xpath(CreditCard_Xpath)).click();
				Thread.sleep(3000);
				//paymentmodeHidenchek(driver);
				//amount.click();
				//Click on list to select subtype
				driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='subType']")).click();

				selectingoneCrditCard(driver, queryObjects, subType[ifop-1]);

				WebElement ccSubType = driver.findElement(By.xpath(CardNumber_Xpath));
				//ccSubType.click();
				ccSubType.sendKeys(fopCardNum[ifop-1]);
				String CCVnumberval="";
				if (!CCVnums.isEmpty())
					CCVnumberval=CCVnumber[ifop-1];
				
				storecarddetails(subType[ifop-1],fopCardNum[ifop-1]);
				
				//Etring credit card other details
				enterCCdetails(driver,queryObjects,CCVnumberval);
			}//End of if statement to enter credit card details
			else if(FOPtype[ifop-1].equalsIgnoreCase("Debitcard")) {
				//Clicking on FOP select list

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")));
				driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")).click();
				Thread.sleep(3000);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CreditCard_Xpath)));
				driver.findElement(By.xpath(CreditCard_Xpath)).click();
				Thread.sleep(3000);
				//paymentmodeHidenchek(driver);
				//amount.click();
				//Click on list to select subtype
				driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='subType']")).click();

				selectingoneCrditCard(driver, queryObjects, subType[ifop-1]);

				WebElement ccSubType = driver.findElement(By.xpath(CardNumber_Xpath));
				//ccSubType.click();
				ccSubType.sendKeys(fopCardNum[ifop-1]);
				String CCVnumberval="";
				if (!CCVnums.isEmpty())
					CCVnumberval=CCVnumber[ifop-1];
				//Etring credit card other details
				enterCCdetails(driver,queryObjects,CCVnumberval);
			}//End of if statement to enter credit card details

			else if(FOPtype[ifop-1].equalsIgnoreCase("installment")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")));
				//Clicking on FOP select list
				driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")).click();
				Thread.sleep(3000);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CreditCard_Xpath)));
				driver.findElement(By.xpath(CreditCard_Xpath)).click();


				//Click on list to select subtype
				driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='subType']")).click();
				
				selectingoneCrditCard(driver,queryObjects,subType[ifop-1]);
				WebElement ccSubType = driver.findElement(By.xpath(CardNumber_Xpath));
				//ccSubType.click();
				ccSubType.sendKeys(fopCardNum[ifop-1]);

				//edited by srini
				
				List<WebElement> bank=driver.findElements(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@name='Bank']"));
				if(bank.size()>0)
				{
					//driver.findElements(By.xpath("//md-select[contains(@ng-model,'formControls.ngModel') and contains(@aria-label,'Bank')]")).get(0).click();
					driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@name='Bank']")).click();
					String BankNmXpath="//div[@role='presentation' and @aria-hidden='false']//md-option[contains(@ng-switch-when,'Bank')]/div[contains(text(),'"+BankName[ifop-1]+"')]";
					if (driver.findElements(By.xpath(BankNmXpath)).size()>0) 
						driver.findElement(By.xpath(BankNmXpath)).click();
					else
						queryObjects.logStatus(driver, Status.FAIL, "Bank Name wrong : Test Data issue","Given name not available", null);
				}
				
				
				if(InstallmentNum[ifop-1].isEmpty())
				{
					driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[contains(@ng-model,'formControls.ngModel') and contains(@aria-label,'Installments')]")).click();
					loadhandling(driver);
					driver.findElement(By.xpath("//div[@role='presentation' and @aria-hidden='false']//md-option[contains(@ng-switch-when,'CCIN') and contains(@value,'3')]/div")).click();
				}
				else
				{	
					driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[contains(@ng-model,'formControls.ngModel') and contains(@aria-label,'Installments')]")).click();
					loadhandling(driver);
					driver.findElement(By.xpath("//div[@role='presentation' and @aria-hidden='false']//md-option[contains(@ng-switch-when,'CCIN') and contains(@value,'"+InstallmentNum[ifop-1]+"')]/div")).click();
					//driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option["+InstallmentNum[ifop-1]+"]")).click();
				}
				
				//Clicking on installment list and selecting option '6'
				/*driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@aria-label='Installments']")).click();
				loadhandling(driver);
				driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[6]")).click();*/
				Thread.sleep(2000);
				String CCVnumberval="";
				if (!CCVnums.isEmpty())
					CCVnumberval=CCVnumber[ifop-1];
				
				storecarddetails(subType[ifop-1],fopCardNum[ifop-1]);
				//Etring credit card other details
				enterCCdetails(driver,queryObjects,CCVnumberval);
			}//End of if statement to enter credit card details

			else if(FOPtype[ifop-1].equalsIgnoreCase("misc")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")));
				driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Miscellaneous')]]")).click();
				//paymentmodeHidenchek(driver);
				//Selecting sub type
				driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='subType']")).click();
				Thread.sleep(2000);
				if(subType[ifop-1].contains("ResidualEMD")) {
					selectingonemisc(driver,queryObjects,"emd");
				} else {
					selectingonemisc(driver,queryObjects,subType[ifop-1]);
				}
				//selectingonemisc(driver,queryObjects,subType[ifop-1]);
				loadhandling(driver);
				if((subType[ifop-1].equalsIgnoreCase("Check")) || (subType[ifop-1].equalsIgnoreCase("banktransfer"))) {
					//Enter Check/Deposit date
					String ckDate = null;
					Calendar cal = Calendar.getInstance();
					ckDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
					driver.findElement(By.xpath("//div[contains(@class,'md-datepicker-input-container')]/input")).sendKeys(ckDate);
					Thread.sleep(2000);

				}
				else if (subType[ifop-1].equalsIgnoreCase("Tarjeta_Clave")) {
					driver.findElement(By.xpath("//input[@name='MSTCRF']")).sendKeys("15624");				
				}
				else if(subType[ifop-1].equalsIgnoreCase("emd")) {
					//driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'EMD')]]")).click();	
					//Enter check number
					String getemdamt="";
					getemdamt = asemdno[emditerr];
					
					if(getemdamt.isEmpty())
						queryObjects.logStatus(driver, Status.PASS, "EMD Number not available", "EMD number nog generated"  , null);
					
					driver.findElement(By.xpath("//input[@name='MSEMDN' and contains(@aria-invalid,'true')]")).sendKeys(getemdamt);
					//driver.findElement(By.xpath("//i[contains(@ng-click,'paymentTemplate.validateEMD')]")).click();
					Thread.sleep(3000);
					List<WebElement> emdclick=	driver.findElements(By.xpath("//input[@name='MSEMDN']//ancestor::div[@parentmodel='paymentTemplate.parentmodel']/following-sibling::div/i"));
					emdclick.get(emditer).click();
					
					FlightSearch.loadhandling(driver);
					//String expectedEMD_Amt=asemdamt[emditer];
				//	String Aftherpayemdval=driver.findElement(By.xpath("//td[contains(text(),'"+Login.envRead(Login.CURRENCY)+"')]")).getText().trim().split("\\s+")[0];
					driver.findElement(By.xpath("//button[contains(text(),'Confirm')]")).click();
					/*if (Double.parseDouble(roundDouble(Aftherpayemdval))==Double.parseDouble(roundDouble(expectedEMD_Amt)))
						//.(expectedEMD_Amt)) 
						queryObjects.logStatus(driver, Status.PASS, "EMD Amounts checking ", "while payment EMD amount showing correctly "  , null);

					else
						queryObjects.logStatus(driver, Status.FAIL, "EMD Amounts checking ", "while payment EMD amount should show correctly Actual EMD amount is :"+Aftherpayemdval+" Expected EMD amount is: "+ expectedEMD_Amt , null);*/
					emditer=emditer+1;
					emditerr++;  //For Multiple EMd Payment - Himani

				}
				else if(subType[ifop-1].contains("ResidualEMD")) {
					int EmdIndx = 0;String EMDNum = ""; String EMDAmt = "";
					String SptEmd[] = null; String SptAmt[] = null;
					if (subType[ifop-1].replace("ResidualEMD", "").isEmpty()) {
						EmdIndx = 1;
					} else {
						EmdIndx = Integer.parseInt(subType[ifop-1].replace("ResidualEMD", ""));
					}
					if (ResidualEMD.contains(";") && ResidualAmt.contains(";")) {
						SptEmd = ResidualEMD.split(";");
						SptAmt = ResidualAmt.split(";");
						EMDNum = SptEmd[EmdIndx-1];
						EMDAmt = SptAmt[EmdIndx-1];
					} else {
						EMDNum = ResidualEMD;
						EMDAmt = ResidualAmt;
					}
					if(!EMDNum.isEmpty())
						driver.findElement(By.xpath("//input[@name='MSEMDN' and contains(@aria-invalid,'true')]")).sendKeys(EMDNum);
						Thread.sleep(3000);
						List<WebElement> emdclick=	driver.findElements(By.xpath("//input[@name='MSEMDN']//ancestor::div[@parentmodel='paymentTemplate.parentmodel']/following-sibling::div/i"));
						emdclick.get(emditer).click();
						FlightSearch.loadhandling(driver);
						//Check the EMD Date, Status and AMount in the EMD details popup
						String gDte = driver.findElement(By.xpath("(//tbody[@class='table-body']//tr)[1]/td[3]")).getText().trim();
						String gDay = gDte.substring(4, gDte.indexOf(","));
						if (gDay.length()==1) {
							gDay = "0"+gDay;
						}
						if (Atoflow.AddDateStr(0, "MMM", "day", null).toLowerCase().contains(gDte.substring(0, 3).toLowerCase()) && Atoflow.AddDateStr(0, "yyyy", "day", null).contains(gDte.substring(gDte.length()-4, gDte.length())) && gDay.contains(Atoflow.AddDateStr(0, "dd", "day", null))) {
							if (driver.findElement(By.xpath("//tbody[@class='table-body']//tr[1]/td[7]")).getText().trim().contains("O") && driver.findElement(By.xpath("(//tbody[@class='table-body']//tr)[1]/td[8]")).getText().trim().contains(EMDAmt)) {
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "EMD details - Verify Emd Status and Amount verification", "Emd Status and EMD Amount verification is successful " , null);
							}
						}else {
							queryObjects.logStatus(driver, Status.FAIL, "EMD details - Verify Emd Creation date", "Emd creation date verification is successful " , null);
						}
						driver.findElement(By.xpath("//button[contains(text(),'Confirm')]")).click();
						emditer=emditer+1;

				}
				else if (subType[ifop-1].equalsIgnoreCase("SST")) {
					driver.findElement(By.xpath("//input[@name='MSSSDE']")).sendKeys("TESTCOPA");
					driver.findElement(By.xpath("//input[@name='MSSSNR']")).sendKeys("5656565656565656");				
				}
				else if(subType[ifop-1].equalsIgnoreCase("barter")) {
					driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("1000001674");//Should ask Gomathi 
					driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
				}
				else if(subType[ifop-1].equalsIgnoreCase("account")) {
                    driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("1500001587");//Should ask Gomathi 
                    driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
             }

				else if(subType[ifop-1].equalsIgnoreCase("famtrip")) {
					driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("6040032ASU");//Should ask Gomathi 
					driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
				}
				//Navira - 06Mar
				else if(subType[ifop-1].equalsIgnoreCase("Sponsorships")) {
					driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("6030043005");//Should ask Gomathi 
					driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
				}
				else if(subType[ifop-1].equalsIgnoreCase("Employee Rewards")) {
                    driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("4000002ADZ");//Should ask Gomathi 
                    driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
				}

				else if(subType[ifop-1].equalsIgnoreCase("Donations")) {
					driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("1000002PTY");//Should ask Gomathi 
					driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
				}
				
				else if(subType[ifop-1].equalsIgnoreCase("Business Travel")) {
					driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("5302003CLO");//Should ask Gomathi 
					driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
				}
				//25Mar - navira
				else if(subType[ifop-1].equalsIgnoreCase("Public relations")) {
					driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("6030032DEN"); 
					driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
				}
				else {
					driver.findElement(By.xpath("//input[@name='MSCMDE']")).sendKeys("TESTCOPA");				
				}
			}

			Thread.sleep(3000);
			List<WebElement> paybutton=driver.findElements(By.xpath("//button[text()='Pay' and not(@disabled='disabled')]"));
			int paybutton_size=paybutton.size();
			if(ifop < FOPtype.length && (paybutton_size==0))
				driver.findElement(By.xpath("//button[contains(text(),'Add Another Payment')]")).click();
			else
				break;  // if pay enable means (if using EMD that is sufficient amout it will enable pay) so break the mulfop loop
		}

	}
	public static void enterFoiddetails(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException{
		try{
			loadhandling(driver);
			//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);//5
			if (driver.findElement(By.xpath("//div[contains(text(),'FOID Details')]")).isDisplayed()) {
				List<WebElement> foidelemnts=driver.findElements(By.xpath("//div[@ng-click='foid.selectPassenger(passengerIndex)']/div"));
				for (int ifoidselection = 0; ifoidselection < foidelemnts.size(); ifoidselection++) {
					foidelemnts.get(ifoidselection).click();
					Thread.sleep(3000);
					driver.findElement(By.xpath("//md-select[@ng-model='activePassenger.foidDocument.DocumentType']")).click();
					Thread.sleep(1000);
					String foid_nm=queryObjects.getTestData("Foid_Name").trim();
					if(foid_nm.isEmpty()){ 
						//if value empty it is entering default select passport	
						driver.findElement(By.xpath("//div[contains(@class,' md-active md-clickable')]//md-option[3]")).click();
						
						Thread.sleep(1000);
						if(!driver.findElement(By.xpath("//input[@name='stateCode']")).getAttribute("value").trim().isEmpty())
							driver.findElement(By.xpath("//input[@name='stateCode']")).clear();
						Thread.sleep(1000);
						if(!driver.findElement(By.xpath("//input[@name='Number']")).getAttribute("value").trim().isEmpty())
							driver.findElement(By.xpath("//input[@name='Number']")).clear();
					}
					else{
						// it will select as per option
						driver.findElement(By.xpath("//div[contains(@class,' md-active md-clickable')]//div[contains(text(),'"+foid_nm+"')]/parent::md-option")).click();
						Thread.sleep(1000);
						if(foid_nm.equalsIgnoreCase("Frequent Flyer")){
							//driver.findElement(By.xpath("//div[contains(@class,' md-active md-clickable')]//div[contains(text(),'Frequent Flyer')]/parent::md-option")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath("//input[@name='ffpCC']")).sendKeys("CM");
							
							if(!driver.findElement(By.xpath("//input[@name='Number']")).getAttribute("value").trim().isEmpty())
								driver.findElement(By.xpath("//input[@name='Number']")).clear();
							driver.findElement(By.xpath("//input[@name='Number']")).sendKeys("31232313213");
						}
						else if(foid_nm.equalsIgnoreCase("National Identification Card")){
							Thread.sleep(1000);
							driver.findElement(By.xpath("//input[@name='stateCode']")).sendKeys("PA");
							
							if(!driver.findElement(By.xpath("//input[@name='Number']")).getAttribute("value").trim().isEmpty())
								driver.findElement(By.xpath("//input[@name='Number']")).clear();
							driver.findElement(By.xpath("//input[@name='Number']")).sendKeys("31232313213");
						}
						else if(foid_nm.equalsIgnoreCase("Drivers License") || (foid_nm.equalsIgnoreCase("Locally Defined Identification")) )  {
							Thread.sleep(1000);
							driver.findElement(By.xpath("//input[@name='stateCode']")).sendKeys("PA");
							
							if(!driver.findElement(By.xpath("//input[@name='Number']")).getAttribute("value").trim().isEmpty())
								driver.findElement(By.xpath("//input[@name='Number']")).clear();
							driver.findElement(By.xpath("//input[@name='Number']")).sendKeys("31232313213");
						}

						else if(foid_nm.equalsIgnoreCase("Confirmation Number") || foid_nm.equalsIgnoreCase("Ticket Number")){
							
							if(!driver.findElement(By.xpath("//input[@name='Number']")).getAttribute("value").trim().isEmpty())
								driver.findElement(By.xpath("//input[@name='Number']")).clear();
							driver.findElement(By.xpath("//input[@name='Number']")).sendKeys("31232313213");
						}
						else
							queryObjects.logStatus(driver, Status.PASS, "Foid details", "Test data Foid Name wrong/Xpath change " , null);
						
					}
					
				} 
				driver.findElement(By.xpath("//button[text()='Next']")).click();
				Thread.sleep(2000);
				queryObjects.logStatus(driver, Status.PASS, "Foid details", "Foid Details:Enter successfully " , null);
			}

		}catch(Exception e) {
			queryObjects.logStatus(driver, Status.INFO, "Foid details", "Foid Details: " + e.getMessage() , e);
		}
		//driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS); //60
	}
	//Method to add SSRs
	public  void addSSR(WebDriver driver, BFrameworkQueryObjects queryObjects,String afterSplitssr,String addSSRSpecificSegment,String totSSRs,String ssrNames,String After_Pay_addSSR_OR_Book_Case_addSSR) throws Exception {
		
		/*String addSSRSpecificSegment=FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRSpecificSegment"));
		String totSSRs=getTrimTdata(queryObjects.getTestData("totSSRs"));
		String ssrNames=getTrimTdata(queryObjects.getTestData("ssrNames"));
		String After_Pay_addSSR_OR_Book_Case_addSSR= getTrimTdata(queryObjects.getTestData("After_Pay_addSSR_OR_Book_Case_addSSR")); */
		
		int freeSSR = 1, paidSSR = 1;
		try {

			WebElement ssrWebEle = null;
			//Clicking on services tab
			driver.findElement(By.xpath("//div[text()='Order']")).click();
			loadhandling(driver);
			int segment=driver.findElements(By.xpath("//tbody[@ng-repeat='flight in flightResult.segments']")).size();
			driver.findElement(By.xpath("//div[div[text()='Services']]")).click();

			loadhandling(driver);

			//Get Balance due to before adding SSR
			//balDueBeforeAddSSR = Double.parseDouble(driver.findElement(By.xpath("//div[contains(@model,'payment.balanceDue')]")).getText().trim());

			//Click on AllSegments checkbox only if it is not selected
			/*	WebElement allSegmChkBox = driver.findElement(By.xpath("//md-checkbox[@aria-label='All Segments']"));
			if(allSegmChkBox.getAttribute("aria-checked").equalsIgnoreCase("false"))
				allSegmChkBox.click();
			Thread.sleep(1000)*/;
			
			//Krishna - Add SSR for Specific Segment
			if(!addSSRSpecificSegment.isEmpty()) {
				String SegNo = addSSRSpecificSegment;
				WebElement SpecificSegmChkbox = driver.findElement(By.xpath("//div[@current-item='ssrCtrl.model.segmentStartIndex']/div[2]/div/div["+SegNo+"]//md-checkbox[@aria-label='checkbox']"));
				if(SpecificSegmChkbox.getAttribute("aria-checked").equalsIgnoreCase("false"))
					SpecificSegmChkbox.click();
			}
			else {
				//Click on AllSegments checkbox only if it is not selected
				WebElement allSegmChkBox = driver.findElement(By.xpath("//md-checkbox[@aria-label='All Segments']"));
				if(allSegmChkBox.getAttribute("aria-checked").equalsIgnoreCase("false"))
					allSegmChkBox.click();
			}
			
			//Clicking on AllPassengers check box only if it is not selected
			WebElement allPaxChkBox = driver.findElement(By.xpath("//md-checkbox[@aria-label='All Passengers']"));
			if(allPaxChkBox.getAttribute("aria-checked").equalsIgnoreCase("false"))
				allPaxChkBox.click();
			Thread.sleep(1000);
			//Expanding Browser services form
			driver.findElement(By.xpath("//div[toggle-title[div[text()='Browse Services']]]/preceding-sibling::i")).click();
			totSSRs = totSSRs;
			ssrNames = ssrNames;
			String ssrcase= After_Pay_addSSR_OR_Book_Case_addSSR;///After_Pay_addSSR
			for(int issr = 1;issr<=Integer.parseInt(totSSRs); issr++) {
				//Get Balance due to before adding SSR
				//if(issr==1){
				if(ssrcase.equalsIgnoreCase("yes") && ((issr==1)|| (ssrAmount.equalsIgnoreCase("0.0")) ))
					balDueBeforeAddSSR=0;
				else
					balDueBeforeAddSSR = Double.parseDouble(driver.findElement(By.xpath("//div[contains(@model,'payment.balanceDue')]")).getText().trim());
		

				String no_ssr = getTrimTdata(queryObjects.getTestData("No_SSR_in_Catalog"));//22Mar - Navira
				String	ssrType= new String();
				String	ssrsubType="";
				String SplitSubType[];
				if (ssrNames.contains("-")) {
					SplitSubType = ssrNames.split("-");
					ssrNames = SplitSubType[0];
					ssrsubType = SplitSubType[1];
				}
				if(Integer.parseInt(totSSRs) == 1) {
					//Clicking on the SSR
					if (ssrsubType!="")
						ssrXpath = "//div[div[contains(text(),'"+ssrsubType+"')]]";
					else
						ssrXpath = "//div[div[contains(text(),'"+ssrNames+"')]]";
					
					//Navira - 22Mar
					if(driver.findElements(By.xpath(ssrXpath)).size()==0 ) {
						if(no_ssr.equalsIgnoreCase("yes"))
							queryObjects.logStatus(driver, Status.PASS, "Checking if SSR is present in Catalog - Negative Scenario", "SSR is not present", null);						
					}
					
					else {
						ssrWebEle = driver.findElement(By.xpath(ssrXpath));
	
						ssrCode = driver.findElement(By.xpath(ssrXpath + "//div[1]")).getText().trim();
	
						ssrType = driver.findElement(By.xpath(ssrXpath + "//div[3]/div/div[1]")).getText().trim();
					}
				}
				else {
					SSRname = ssrNames.split(";");
					ssrXpath =  "//div[div[contains(text(),'"+SSRname[issr-1]+"')]]";

					ssrWebEle = driver.findElement(By.xpath(ssrXpath));
					ssrCode = driver.findElement(By.xpath(ssrXpath + "//div[1]")).getText().trim();
					ssrType = driver.findElement(By.xpath(ssrXpath + "//div[3]/div/div[1]")).getText().trim();

				}
				int iPaxCountXpath=0;int iINFPaxCountXpath=0;

				iPaxCountXpath = Integer.parseInt(driver.findElement(By.xpath(PaxCountXpath)).getText().trim());
				iINFPaxCountXpath = Integer.parseInt(driver.findElement(By.xpath(INFPaxCountXpath)).getText().trim());

				//22Mar - Navira
				if(driver.findElements(By.xpath(ssrXpath)).size()==0) {
					if(no_ssr.equalsIgnoreCase("yes")) {
						ssrAmount = "0.0";
						break;
					}
					
				}
				else {
					//Clicking on SSR name to add to order
					ssrWebEle.click();
					loadhandling(driver);
					//Adding SSr
					ssrAmount="0";
					ssrAmount=String.valueOf(selectingSSR(driver, ssrCode,queryObjects));
					queryObjects.logStatus(driver, Status.PASS, "SSR Type is "+ssrType + " SSR cod is : "+ssrCode, "SSR Amount is "+ssrAmount, null);
				}
				int paxcount=iPaxCountXpath+iINFPaxCountXpath;
				if(!ssrType.equalsIgnoreCase("FREE")){ 
					/*if (Listssrname.contains(ssrCode))
						emdAmount = emdAmount + (Double.parseDouble(ssrAmount)*paxcount *segment);
					else
						emdAmount = emdAmount + (Double.parseDouble(ssrAmount)*paxcount);*/
					emdAmount = emdAmount + (Double.parseDouble(ssrAmount));
				}
				//Click on Add to Order
				if(!ssrAmount.equalsIgnoreCase("FREE") && (!ssrAmount.equalsIgnoreCase("0.0"))) {//Meenu Code //Check
					driver.findElement(By.xpath("//form[@name='ssrSubcategory.form']//button[contains(text(),'Add To Order')]")).click();

					loadhandling(driver);
					CheckforError(driver,queryObjects);
					balDueAfterAddSSR = driver.findElement(By.xpath("//div[contains(@model,'payment.balanceDue')]")).getText().trim();
					balDueBeforeAddSSR=Double.parseDouble(roundDouble(String.valueOf(balDueBeforeAddSSR+Double.parseDouble(ssrAmount))));
					//double expt=Double.parseDouble(roundDouble(String.valueOf(balDueBeforeAddSSR+Double.parseDouble(ssrAmount))));  // need to check
					double expt=balDueBeforeAddSSR;
					double act=Double.parseDouble(roundDouble(balDueAfterAddSSR));

					if( act == expt) {

						queryObjects.logStatus(driver, Status.PASS, "Adding SSr Case ", "Amount calculated correctly", null);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL, "Adding SSr Case", "Amount not calculated correctly - Actual: " + act + "Expected: " + expt, null);
					}
				}
				else {
					driver.findElement(By.xpath("//form[@name='ssrSubcategory.form']//button[contains(text(),'Add To Order')]")).click();
					loadhandling(driver);
					String errormsg=FlightSearch.getErrorMSGfromAppliction(driver,queryObjects);
					if(errormsg.contains("SSR NOT AVAILABLE"))		 // Added by kishore 16 April  ... need to check														
					{
						//Click on Cancel button
						driver.findElement(By.xpath("//button[text()='Cancel']")).click();
						//wait till loading wrapper closed
						FlightSearch.loadhandling(driver);
						queryObjects.logStatus(driver, Status.WARNING, "Trying to add SSR ","while adding ssr displying warning message  SSR NOT AVAILABLEfrom pplication", null);
					}
				}
				// if test case is issr --> and expecting error message it is not validating ssr is added or not
				if (!getTrimTdata(queryObjects.getTestData("Expected_erromsg")).equalsIgnoreCase("ssr_expected_error") && (ssrAmount.equalsIgnoreCase("FREE"))&& (ssrAmount.equalsIgnoreCase("0.0"))) {
					//  ssr validatiobn 
					for(int ipax = 1; ipax<=totalnoofPAX; ipax++) {
						//Expanding Passenger details grid
						try {
							driver.findElement(By.xpath("//div[contains(@class,'passenger-list')]/div["+ipax+"]//i[contains(@class,'icon-forward')]")).click();
							Thread.sleep(1000);
						}
						catch(Exception e) {

						}
						String addedSSR = null;

						if((!ssrAmount.equalsIgnoreCase("0.0")) && (!ssrAmount.equalsIgnoreCase("FREE")) && (!Listssrname.contains(ssrCode))){
						//if((!ssrAmount.equalsIgnoreCase("FREE")) && (!Listssrname.contains(ssrCode))){
							//Checking for the SSR code added
							addedSSR = driver.findElement(By.xpath("//div[contains(@class,'passenger-list')]/div["+ ipax +"]//div[contains(@ng-repeat,'standAloneService')]["+paidSSR+"]/div[1]")).getText().trim();
						}
						else {
							addedSSR =driver.findElement(By.xpath("//div[contains(@class,'passenger-list')]/div["+ipax+"]//div[contains(@ng-repeat,'serviceInfos')]["+freeSSR+"]//div[@class='services-list']//div[contains(@class,'segment-services')][1]")).getText().trim();
						}

						if(Integer.parseInt(totSSRs) == 1) {
							if(addedSSR.contains(ssrNames) || addedSSR.contains(ssrCode)) {
								queryObjects.logStatus(driver, Status.PASS, "Adding one SSr: " + ssrNames, "Given SSR added successfully to the passenger: " + ipax, null);
							}
							else {
								queryObjects.logStatus(driver, Status.FAIL, "Adding one SSr: " + ssrNames, "Given SSR not added to the passenger: " + ipax, null);
							}
						}
						else {
							if(addedSSR.contains(SSRname[issr-1]) || addedSSR.contains(ssrCode)) {
								queryObjects.logStatus(driver, Status.PASS, "Adding SSr: " + SSRname[issr-1], "Given SSR added successfully to the passenger: " + ipax, null);
							}
							else {
								queryObjects.logStatus(driver, Status.FAIL, "Adding SSr: " + SSRname[issr-1], "Given SSR not added to the passenger: " + ipax, null);
							}
						}

						//Collapse passenger details grid
						try {
							driver.findElement(By.xpath("//div[contains(@class,'passenger-list')]/div["+ipax+"]//i[contains(@class,'icon-arrow-down')]")).click();
						}
						catch(Exception e) {

						}
						Thread.sleep(1000);
					}
					if((!ssrAmount.equalsIgnoreCase("0.0")) && !ssrAmount.equalsIgnoreCase("FREE")) {
					//if(!ssrAmount.equalsIgnoreCase("FREE")) {
						paidSSR++;
					}
					else {
						freeSSR++;
					}
				}
				
			}
					
			queryObjects.logStatus(driver, Status.PASS, "Adding SSRs", "All SSRs added succesfully", null);
		}

		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Adding SSRs", "All SSRs not added: " + e.getLocalizedMessage(), e);
		}
	}

	public static void refundticket(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{

		if(! Login.CardDeposit_Change_Salesoffice.isEmpty())   // card Deposit case change the POS
			PNRsearch.ChangeSalesOffice(driver,queryObjects,Login.CardDeposit_Change_Salesoffice,Login.Cur);
		
		String Before_Refund_changepos=queryObjects.getTestData("Before_Refund_changepos").trim();
		if(Before_Refund_changepos.equalsIgnoreCase("yes"))  //Before Do Refund change POS-
			PNRsearch.ChangeSalesOffice(driver,queryObjects,Login.Change_Salesoffice,Login.Change_Currency);
		
		String refundfareAmount = "";
		queryObjects.logStatus(driver, Status.INFO, "Performing Refund operation","Refund the ticket",null);
		driver.findElement(By.xpath("//div[text()='Order']")).click();
		Thread.sleep(1000);

		int segment=driver.findElements(By.xpath("//tbody[@ng-repeat='flight in flightResult.segments']")).size();

		driver.findElement(By.xpath("//md-select[@placeholder='Actions']")).click();
		Thread.sleep(1000);
		String refundType=FlightSearch.getTrimTdata(queryObjects.getTestData("Refind_Type"));
		//WebDriverWait wait = new WebDriverWait(driver, 70);
		
		if (refundType.equalsIgnoreCase("Vol")) {
			driver.findElement(By.xpath("//md-option[@value='voluntary-refund']")).click();
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Voluntary Refund Options']")));
		}
		if (refundType.equalsIgnoreCase("InVol")) {
			driver.findElement(By.xpath("//md-option[@value='involuntary-refund']")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//md-select[@aria-label='waiver process']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//md-option[@ng-value='ProcessList' and @role='option']")).click();
			Thread.sleep(2000);
			//Select Reason Code
			driver.findElement(By.xpath("//md-select[@aria-label='waiver reason']")).click();
			Thread.sleep(2000);

			driver.findElement(By.xpath("//div[@aria-hidden='false']//md-option[@ng-value='Reason' and @role='option'][1]")).click();
		}
		loadhandling(driver);

		driver.findElement(By.xpath("//button[text()='Next']")).click();
		loadhandling(driver);
		if (FlightSearch.Manualquotecase==false){
			//wait = new WebDriverWait(driver, 100);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add To Order']")));
			loadhandling(driver);
			int getelementssize=driver.findElements(By.xpath("//div[contains(@model,'fareDifference')]/div[2]//span[contains(@ng-if,'IsRefund')]")).size();
			//if (getelementssize>0) {
				
				String amt=driver.findElement(By.xpath("//div[@title='pssgui.total']//div[text()='Total']/following-sibling::div")).getText().trim();
				if (amt.contains("(")) {
					refundfareAmount = amt.replaceAll("[()]", "").trim();
					refundfareAmount=roundDouble((refundfareAmount.split("\\s+")[0]).trim());
					queryObjects.logStatus(driver, Status.INFO, "Performing Refund operation","Refund Amount "+amt+"Refunt to PAX",null);
					
					// Penalty checking ..... Created pnr Q and K class panality should display
					List<WebElement> classcheck=driver.findElements(By.xpath("//md-dialog[@aria-label='pssgui-dialog']//child::td[contains(@class,'flight-class')]/div/div/span"));
					ArrayList getClasscheck = new ArrayList<>();
					classcheck.forEach(a -> getClasscheck.add(a.getText().trim().toUpperCase()));
					
					if (getClasscheck.contains("Q") || getClasscheck.contains("K")) {
						String GetPenalityamt=driver.findElement(By.xpath("//div[@title='pssgui.penalty' and @model='leftTabInfo.farePrice.rebookingFee']/div[2]")).getText().trim();
						double getpenamt=Double.parseDouble(GetPenalityamt.split("\\s+")[0]);
						if (getpenamt>0) 
							queryObjects.logStatus(driver, Status.PASS, "SPECIFIC class Q or K case Penalty checking", "it is displaying penalty, Penalty amount is:"+getpenamt, null);
						else if(refundType.equalsIgnoreCase("InVol"))//Navira - 05Mar
							queryObjects.logStatus(driver, Status.PASS, "SPECIFIC class Q or K case Penalty checking", "it should  be zero penalty, amount is:"+getpenamt, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "SPECIFIC class Q or K case Penalty checking", "it should  more than zero penalty, amount is:"+getpenamt, null);
						
						String Fare_Difference=driver.findElement(By.xpath("//div[@title='pssgui.fare.difference' and @model='leftTabInfo.farePrice.fareDifference']/div[2]")).getText().trim();
						String Fare_Difference_split = Fare_Difference.replaceAll("[()]", "").trim();
						String Fare_Difference_Amount=roundDouble((Fare_Difference_split.split("\\s+")[0]).trim());
						queryObjects.logStatus(driver, Status.PASS, "SPECIFIC class Q or K case Get Fare Differnce Amount", "Fare Difference Amount is :"+Fare_Difference_Amount, null);
						
						 TotalRefundamt=Double.parseDouble(refundfareAmount);
						 PanaltyAmount=getpenamt;
						 Fare_Diff=Double.parseDouble(Fare_Difference_Amount);
						 double diffamt=Math.abs(Double.parseDouble(roundDouble (String.valueOf(Fare_Diff-PanaltyAmount))));
					
						if (TotalRefundamt==diffamt) 
							queryObjects.logStatus(driver, Status.PASS, "SPECIFIC class Q or K case Total Refund amount checking", "Total Refund amount calculation correct", null);
						else
							queryObjects.logStatus(driver, Status.PASS, "SPECIFIC class Q or K case Total Refund amount checking", "Total Refund amount calculation Wrong Tot amt="+TotalRefundamt +" Fare Diff amt="+Fare_Diff +" PanaltyAmount is "+PanaltyAmount , null);
						
					}
					String Exp_Errormsg=queryObjects.getTestData("Expected_erromsg");
					if(Exp_Errormsg.equalsIgnoreCase("RefundCase_Checkout_Button_disabled_for_other_currency")){			
						if(driver.findElements(By.xpath("//button[text()='Add To Order' and @disabled='disabled']")).size()>0)
							queryObjects.logStatus(driver, Status.PASS, "Issue/Reissue one currecny and Refund in other currecny", "Add To Order button Disabled", null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Issue/Reissue one currecny and Refund in other currecny", "Add To Order button should be Disabled", null);
						return;
					}
					driver.findElement(By.xpath("//button[text()='Add To Order']")).click();
					loadhandling(driver);
			}
			else{
				queryObjects.logStatus(driver, Status.PASS, "Refund case", "Amount was not refund to pAX", null);
				
			// Refund case After click Add to order payment amount checking....
			}
	
		}
		else if (FlightSearch.Manualquotecase==true){
			String Manualquote_errormsg="";
			Manualquote_errormsg=FlightSearch.getErrorMSGfromAppliction(driver, queryObjects);
			if(Manualquote_errormsg.contains("TRR is unable to determine") || Manualquote_errormsg.contains("Manual Refund Required")){
				queryObjects.logStatus(driver, Status.PASS, "Manual refund case expecting error message", "After select voluntary refund displayied error message like Manual refund required", null);
			}
			else
				queryObjects.logStatus(driver, Status.PASS, "Manual Quote PNR trying Manual Refund but is accepted Automatic Quote", "Manual Quote PNR trying Manual Refund but is accepted Automatic Quote", null);
			try{
				
				boolean volrefundcase=(refundType.equalsIgnoreCase("Vol") && !(Manualquote_errormsg.isEmpty()));
				if (volrefundcase) {   // if IN vol refund no need manual refund
					refundfareAmount=Manual_refund(driver,queryObjects);	
				}
				else{  // if Manual Quote case and Invol refund no need do manual refund so get auto quote amount
					String amt=driver.findElement(By.xpath("//div[@title='pssgui.total']//div[text()='Total']/following-sibling::div")).getText().trim();
					refundfareAmount = amt.replaceAll("[()]", "").trim();
					refundfareAmount=roundDouble((refundfareAmount.split("\\s+")[0]).trim());
					queryObjects.logStatus(driver, Status.INFO, "Performing Refund operation","Amount "+amt+"Refunt to PAX",null);
					String Exp_Errormsg=queryObjects.getTestData("Expected_erromsg");
					if(Exp_Errormsg.equalsIgnoreCase("RefundCase_Checkout_Button_disabled_for_other_currency")){
						
						if(driver.findElements(By.xpath("//button[text()='Add To Order' and @disabled='disabled']")).size()>0)
							queryObjects.logStatus(driver, Status.PASS, "Issue/Reissue one currecny and Refund in other currecny", "Add To Order button Disabled", null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Issue/Reissue one currecny and Refund in other currecny", "Add To Order button should be Disabled", null);
						return;
					}	
					driver.findElement(By.xpath("//button[text()='Add To Order']")).click();
					loadhandling(driver);
				}
					
				
			}catch(Exception e){
				queryObjects.logStatus(driver, Status.FAIL, "Manual refund process strted", "Manual refund case issue", e);
			}	
		}
		String totalamt=getTrimTdata(driver.findElement(By.xpath("//div[@model='currency.total']/div")).getText());
		String Balanceamt=getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
		String Paidamt=getTrimTdata(driver.findElement(By.xpath("//div[@model='currency.paid']/div")).getText());

		Balanceamt=Balanceamt.replaceAll("[()]", "").trim();
		totalamt=totalamt.replaceAll("[()]", "").trim();
		Paidamt=Paidamt.replaceAll("[()]", "").trim();
		Login.CardDeposit=true;  // This flag is using for Execute Card Deposit functionality  
		double Acttotal=Math.abs(Double.parseDouble(Paidamt)-Double.parseDouble(Balanceamt));
		if (Double.parseDouble(Balanceamt)==Double.parseDouble(refundfareAmount)) {
			queryObjects.logStatus(driver, Status.PASS, "Refund case After click Add to orde:: Refund amount and Balance amount checking", "Refund case After click Add to orde amount showing  in Balance due "+Balanceamt, null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Refund case After click Add to orde:: Refund amount and Balance amount checking", "Refund case After click Add to orde amount should show in Balance due actual: "+Balanceamt+" expected: "+refundfareAmount, null);
		}
		if (Double.parseDouble(totalamt)==Double.parseDouble(roundDouble(String.valueOf(Acttotal)))) {
			queryObjects.logStatus(driver, Status.PASS, "Refund case After click Add to orde:: Refund amount and Total amount echecking", "Refund case After click Add to orde Total amount showing  correctlyactual: total amt is : "+totalamt, null);
		}
		else {
			queryObjects.logStatus(driver, Status.FAIL, "Refund case After click Add to orde:: Refund amount and Total amount echecking", "Refund case After click Add to orde Totlal amount should show correctly actual: "+totalamt+" expected: "+Acttotal, null);
		}

		driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
		loadhandling(driver);
		
		String Residual_EMD =FlightSearch.getTrimTdata(queryObjects.getTestData("Residual_EMD"));  // REsidual Emd functionality 
		if(Residual_EMD.equalsIgnoreCase("yes"))
			driver.findElement(By.xpath("//md-checkbox[@ng-model='refundOverride.isResidualEmd' and @aria-disabled='false'  and @aria-checked='false']")).click();

		
		WebDriverWait wait1 = new WebDriverWait(driver, 100);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Refund']")));
		queryObjects.logStatus(driver, Status.INFO, "Payment Return process starte", "Return process starte", null);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='Refund']")).click();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Pay']")));
		loadhandling(driver);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='Pay']")).click();

		try{
			Thread.sleep(2000);
			if (driver.findElements(By.xpath("//div[text()='FOID Details']")).size()>0) {
				if(driver.findElements(By.xpath("//button[text()='Next' and (@disabled='disabled')]")).size()>0)
				     FlightSearch.enterFoiddetails(driver,queryObjects); 
				else
					driver.findElement(By.xpath("//button[text()='Next']")).click();
			}

		}catch (Exception e) {
			queryObjects.logStatus(driver, Status.INFO, "Refund case FOID detils", e.getLocalizedMessage(), e);

		}

		//Handling Email recipients popup
		emailhandling(driver,queryObjects);
		
		CardDepositExecution(driver, queryObjects); //Card Deposit functionality
		
		//Card Deposit functionality -- applicable  create pnr in ATO / CTO  refun in call center
		try {
			//wait = new WebDriverWait(driver, 120);
			//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
			loadhandling(driver);
			String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
			queryObjects.logStatus(driver, Status.PASS, "Return Payment", statusMessage, null);
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Return Payment", "Payment Unsuccessful: " + e.getMessage() , e);
		}
		
		/*if(Residual_EMD.equalsIgnoreCase("yes")) {
			if(driver.findElements(By.xpath("//span[contains(text(),'EMD')]")).size()>2) 
				queryObjects.logStatus(driver, Status.PASS, "Residual EMD case EMD count checking", "After Payment Displaying all EMD " , null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Residual EMD case EMD count checking", "After Payment it should display all EMD" , null);
		}*/
		
		//Clicking on Done button
		driver.findElement(By.xpath("//button[text()='Done']")).click();

		loadhandling(driver);
		driver.findElement(By.xpath("//div[text()='Tickets']")).click();
		loadhandling(driver);
		List<WebElement> getetktsamt=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]//preceding-sibling::div"));
		gettecketamt = new ArrayList<>();
		getetktsamt.forEach(a -> gettecketamt.add(a.getText().trim()));

		List<WebElement> getetkts=driver.findElements(By.xpath("//span[contains(@class,'pssgui-link primary-ticket-number')]"));
		gettecketno = new ArrayList<>();
		//Unwholly.aTicket = new ArrayList<>();
		Unwholly.aTicket=null;
		getetkts.forEach(a -> gettecketno.add(a.getText().trim()));
		List<WebElement> refundstatus=driver.findElements(By.xpath("//td[contains(@ng-if,'ticketStatusUpdate')]"));
		int Refundcount=0;
		String AfterRefund_Expected_Ticket_State="";  
		String Typedesider=queryObjects.getTestData("Residual_EMD").trim();
		if(Typedesider.equalsIgnoreCase("YES"))  // IF TEST CASE Residual EMD is Exchanged, if test case is other than RES EMD Refund.
			AfterRefund_Expected_Ticket_State="EXCHANGED";
		else
			AfterRefund_Expected_Ticket_State="REFUNDED";
	//	FlightSearch.TktStatus=new ArrayList<>();
		for (WebElement ele : refundstatus) {
			if (ele.getText().trim().equalsIgnoreCase(AfterRefund_Expected_Ticket_State)){
				Refundcount=Refundcount+1;
				//FlightSearch.TktStatus.add("Refund");//This the first refunded ticket
			}	
			
		}
		
//		//Krishna
//		int salesRefundcount=0;   // sales report validation, so added this loop
//        for (WebElement Lab : getetkts) {
//        	salesRefundcount++;
//               String ticketnumber=Lab.getText().trim();
//               List<WebElement> currentticketstate=driver.findElements(By.xpath("//span[text()='"+ticketnumber+"']/parent::div/parent::div//td[contains(@ng-if,'ticketStatusUpdate')]"));
//               if(currentticketstate.get(0).getText().trim().equalsIgnoreCase(AfterRefund_Expected_Ticket_State)) {
//                    FlightSearch.TktStatus.add(salesRefundcount-1, "Refund"); 
//                 }      
//        }

        
      //Krishna
      		boolean EmptyTicketStatus=false;
      		if(FlightSearch.TktStatus.size()==0)
      			EmptyTicketStatus=true;
      			
      		int salesRefundcount=0;   // sales report validation, so added this loop
              for (WebElement Lab : getetkts) {
              	salesRefundcount++;
                     String ticketnumber=Lab.getText().trim();
                     ticketnumber.contains("-");
                     ticketnumber=ticketnumber.split("-")[0].trim();
                     List<WebElement> currentticketstate=driver.findElements(By.xpath("//span[text()='"+ticketnumber+"']/parent::div/parent::div//td[contains(@ng-if,'ticketStatusUpdate')]"));
                     if(EmptyTicketStatus) {
                  	   if(currentticketstate.get(0).getText().trim().equalsIgnoreCase(AfterRefund_Expected_Ticket_State))
                  		   FlightSearch.TktStatus.add("Refund"); 
                     }else if(FlightSearch.TktStatus.size()>=salesRefundcount){
                  	   if(currentticketstate.get(0).getText().trim().equalsIgnoreCase(AfterRefund_Expected_Ticket_State))
                  		   FlightSearch.TktStatus.set(salesRefundcount-1, "Refund");
                     }  
              }  
        
		String totalTravels=driver.findElement(By.xpath(PaxCountXpath)).getText().trim();
		int totalTravers=Integer.parseInt(totalTravels);
		
		String totalTravelsInf=driver.findElement(By.xpath(INFPaxCountXpath)).getText().trim();
		int totalTraversInf=Integer.parseInt(totalTravelsInf);
		
		totalTravers = totalTravers + totalTraversInf;

		if (segment*totalTravers==Refundcount) 
			queryObjects.logStatus(driver, Status.PASS, "After refund segment status checking", "After refund status was changed to :"+AfterRefund_Expected_Ticket_State, null);
		else
			queryObjects.logStatus(driver, Status.FAIL, "After refund segment status checking", "After refund status should chang to :"+AfterRefund_Expected_Ticket_State, null);
		
		if(Residual_EMD.equalsIgnoreCase("yes")) {
			Residual_EMD_Validation(driver,queryObjects);
		}

	}

	public static String roundDouble(String number) {
		Double dnumber=Double.parseDouble(number);
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_DOWN);

		return df.format(dnumber);
	}
	public static String roundDouble_one(String number) {
		Double dnumber=Double.parseDouble(number);
		DecimalFormat df = new DecimalFormat("#.#");
		df.setRoundingMode(RoundingMode.HALF_DOWN);

		return df.format(dnumber);
	}
	public static String convertinteger(String number) {
		Double dnumber=Double.parseDouble(number);
		DecimalFormat df = new DecimalFormat("#");
		df.setRoundingMode(RoundingMode.HALF_DOWN);

		return df.format(dnumber);
	}
	public void changeFirstnm(WebDriver driver,BFrameworkQueryObjects queryObjects,String slastName,String sfirstName, String modifyName) throws Exception{
		//updating PAX first name
		try{
			driver.findElement(By.xpath("//div[div[span[contains(text(),'"+ slastName +"') and contains(text(),'"+ sfirstName +"')]]]//span[contains(@ng-click,'frequentFlyer')]")).click();
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "change Firstnm", "Test Data issue we should give First nama and last name " + e.getLocalizedMessage(), e);
		}
		loadhandling(driver);
		driver.findElement(By.xpath(passangerTabXpath)).click();	
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[div[text()='Personal Information']]")).click();	
		loadhandling(driver);
		driver.findElement(By.xpath("//button[@translate='pssgui.edit']")).click();	
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@ng-model='personalInfo.model.Firstname']")).clear();
		String modifysurname=getTrimTdata(queryObjects.getTestData("Modify_Surname"));
		driver.findElement(By.xpath("//input[@ng-model='personalInfo.model.Firstname']")).sendKeys(modifyName);
		String name=slastName;
		if (modifysurname!="") {
			//slastName=modifysurname;
			driver.findElement(By.xpath("//input[@ng-model='personalInfo.model.Lastname']")).clear();
			driver.findElement(By.xpath("//input[@ng-model='personalInfo.model.Lastname']")).sendKeys(modifysurname);
			name=modifysurname.toUpperCase();
			//slastName = slastName.toUpperCase();
		};

		driver.findElement(By.xpath("//button[text()='OK']")).click();
		loadhandling(driver);
		try{
			driver.findElement(By.xpath("//div[div[span[contains(text(),'"+ name +"') and contains(text(),'"+ modifyName.toUpperCase() +"')]]]//span[contains(@ng-click,'frequentFlyer')]")).click();
			//driver.findElement(By.xpath("//span[contains(text(),'"+ slastName +"')]")).click();
			queryObjects.logStatus(driver, Status.PASS, "change Firstnm", "First name was updated " , null);
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "change Firstnm", "First name not updated " + e.getLocalizedMessage(), e);
		}

	}

	public int addFFPBeforeSearch(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception  {
		String totalFFP = getTrimTdata(queryObjects.getTestData("totalFFP"));
		String FFPcode = getTrimTdata(queryObjects.getTestData("FFPcode"));
		String FFPnumber = getTrimTdata(queryObjects.getTestData("FFPnumbers"));
		List<WebElement> ffpList = new ArrayList<>();
		List<WebElement> ffpTxtBox = new ArrayList<>();
		int retVal = 0;
		if(!totalFFP.isEmpty()) {
			FFpPax=true;
			ffpList = driver.findElements(By.xpath("//pssgui-menu[@menu-model='fqtvDetail.ProgramID']//md-select"));
			ffpTxtBox = driver.findElements(By.xpath("//input[@name='ffNumber']"));
			if(Integer.parseInt(totalFFP) == 1) {
				ffpList.get(0).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[contains(@value,'"+FFPcode+"')]")).click();

				Thread.sleep(1000);
				ffpTxtBox.get(0).click();
				ffpTxtBox.get(0).clear();
				ffpTxtBox.get(0).sendKeys(FFPnumber);
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[contains(text(),'Validate FF') and not(@disabled='disabled')]")).click();
				loadhandling(driver);
				driver.findElement(By.xpath("//button[contains(text(),'Add') and not(@disabled='disabled')]")).click();
				Thread.sleep(1000);

				retVal = 1;

			}
			else if(Integer.parseInt(totalFFP) > 1) {
				String[] aFFPcodes = FFPcode.split(";");
				String[] aFFnumbers = FFPnumber.split(";");
				for(int i=0;i<Integer.parseInt(totalFFP); i++) {
					ffpList = driver.findElements(By.xpath("//pssgui-menu[@menu-model='fqtvDetail.ProgramID']//md-select"));
					ffpList.get(i).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[@value='"+aFFPcodes[i]+"']")).click();
					Thread.sleep(1000);
					ffpTxtBox.get(i).click();
					ffpTxtBox.get(i).clear();
					ffpTxtBox.get(i).sendKeys(aFFnumbers[i]);
					Thread.sleep(1000);
					driver.findElement(By.xpath("//button[contains(text(),'Validate FF') and not(@disabled='disabled')]")).click();
					loadhandling(driver);
					driver.findElement(By.xpath("//button[contains(text(),'Add') and not(@disabled='disabled')]")).click();
					Thread.sleep(1000);
				}
				retVal = Integer.parseInt(totalFFP);
			}
		}
		return retVal;

	}
	public static String getErrorMSGfromAppliction(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException{
		//...............//span[contains(@ng-class,'msg-error')]
		String retval="";
		//	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  //5
		if(driver.findElements(By.xpath("//span[contains(@ng-class,'msg-error')]")).size()>0)
			retval=driver.findElement(By.xpath("//span[contains(@ng-class,'msg-error')]")).getText().trim();
		/*try {
			retval=driver.findElement(By.xpath("//span[contains(@ng-class,'msg-error')]")).getText().trim();
		} catch (Exception e) {
			//queryObjects.logStatus(driver, Status.INFO, "Getting Error message form application", "Getting Error message form application " + e.getLocalizedMessage(), e);
			queryObjects.logStatus(driver, Status.INFO, "Checking for the error message ", "No error message is displayed ", null);
		}
		//driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);//60 */	
	return retval;
	}

	public static void selectingoneCrditCard(WebDriver driver,BFrameworkQueryObjects queryObjects,String cardtype) throws InterruptedException, IOException{
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]")));
		if(cardtype.equalsIgnoreCase("visa")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[normalize-space()='Visa']]")).click();
			if(Worldpay_Merchant_code.isEmpty())
				Worldpay_Merchant_code="CC";
			else
				Worldpay_Merchant_code=Worldpay_Merchant_code+";CC";
		}
		else if(cardtype.equalsIgnoreCase("Visa_Debit")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[normalize-space()='Visa Debit']]")).click();
			if(Worldpay_Merchant_code.isEmpty())
				Worldpay_Merchant_code="CC";
			else
				Worldpay_Merchant_code=Worldpay_Merchant_code+";CC";
		}
		else if(cardtype.equalsIgnoreCase("mastercard")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[normalize-space()='MasterCard']]")).click();
			if(Worldpay_Merchant_code.isEmpty())
				Worldpay_Merchant_code="CC";
			else
				Worldpay_Merchant_code=Worldpay_Merchant_code+";CC";
		}
		else if(cardtype.equalsIgnoreCase("uatp")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[normalize-space()='UATP']]")).click();if(Worldpay_Merchant_code.isEmpty())
				Worldpay_Merchant_code="UATP";
			else
				Worldpay_Merchant_code=Worldpay_Merchant_code+";UATP";
		}
		else if(cardtype.equalsIgnoreCase("american_express")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[normalize-space()='American Express']]")).click();
			if(Worldpay_Merchant_code.isEmpty())
				Worldpay_Merchant_code="CC";
			else
				Worldpay_Merchant_code=Worldpay_Merchant_code+";CC";
		}
		else if(cardtype.equalsIgnoreCase("diners_club")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[normalize-space()='Diners Club']]")).click();
			if(Worldpay_Merchant_code.isEmpty())
				Worldpay_Merchant_code="CC";
			else
				Worldpay_Merchant_code=Worldpay_Merchant_code+";CC";
		}
		else if(cardtype.equalsIgnoreCase("Maestro")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[normalize-space()='Maestro']]")).click();
			if(Worldpay_Merchant_code.isEmpty())
				Worldpay_Merchant_code="CC";
			else
				Worldpay_Merchant_code=Worldpay_Merchant_code+";CC";
		}
		else if(cardtype.equalsIgnoreCase("Tarjeta_Naranja")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[normalize-space()='Tarjeta Naranja']]")).click();
			if(Worldpay_Merchant_code.isEmpty())
				Worldpay_Merchant_code="CC";
			else
				Worldpay_Merchant_code=Worldpay_Merchant_code+";CC";
		}
		else if(cardtype.equalsIgnoreCase("Local Card")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[normalize-space()='Local Card']]")).click();
			if(Worldpay_Merchant_code.isEmpty())
				Worldpay_Merchant_code="CC";
			else
				Worldpay_Merchant_code=Worldpay_Merchant_code+";CC";
		}
		else{
			queryObjects.logStatus(driver, Status.FAIL, "Given Credit card name not available in script card name is "+cardtype, "Given Credit Card name not availabnle is script ",null);
		}
		Thread.sleep(2000);

	}

	public static void addspecificSSR(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{

		loadhandling(driver);
		String SpecficSSRs = getTrimTdata(queryObjects.getTestData("SpecificSSRsforSpecificPAX"));
		String MaxSSR = getTrimTdata(queryObjects.getTestData("MaxSSR"));

		int MaxAdult=10;
		int MaxChild=10;
		if (!MaxSSR.equals("")) {

			String[]  ArrMax = MaxSSR.split(";");
			for (int imax=0; imax<ArrMax.length; imax++) {
				String[] ArrSpec = ArrMax[imax].split("-");

				String Maxtype = ArrSpec[0];
				String Maxvalue = ArrSpec[1];     
				if (Maxtype.equalsIgnoreCase("adult")){
					MaxAdult = Integer.parseInt(Maxvalue);
				}
				else {
					MaxChild =  Integer.parseInt(Maxvalue);


				}

			}
		}

		int iSSRforchild=0;
		int iSSRforadult=0;
		String[] ArrSpec =SpecficSSRs.split(";");
		for (int issr=0; issr<=ArrSpec.length-1; issr++) {
			String[] ArrSSRs = ArrSpec[issr].split("-");

			String SSRtype = ArrSSRs[0];
			//Krishna - Total Number of SSR
			String SSRNo = ArrSSRs[0].replaceAll("[^0-9]", "");
			
			SSRtype = ArrSSRs[0].replaceAll("[^A-Z,^a-z]", "");
			int AddedSSRNo = 0;
			String SSRvalue = ArrSSRs[1];     
			String[] SSRs = SSRvalue.split(",");

			//java.util.List<WebElement> lpassengerlist =driver.findElements(By.xpath("//div[contains(@class,'pssgui-design-sub-heading-6')]/span"));
			//java.util.List<WebElement> lpassengerlist =driver.findElements(By.xpath("//div[contains(@class,'pssgui-design-sub-heading-6')]/span"));
			java.util.List<WebElement> lpassengerlist =driver.findElements(By.xpath("//div[contains(@class,'pssgui-design-sub-heading-6')]/span[contains(@role,'button')]"));
			int noofpax=lpassengerlist.size();
			if(totalnoofPAX==0) {
				totalnoofPAX=noofpax;
			}
			for(int ipax = 0; ipax<totalnoofPAX; ipax++) {
				//Expanding Passenger details grid
				try {

					lpassengerlist.get(ipax).click();
					loadhandling(driver);
					String PassengerName = lpassengerlist.get(ipax).getText();
					String Passengertype = driver.findElement(By.xpath("//div[@class='md-text']/span")).getText();

					/*if (Passengertype.equalsIgnoreCase("child") && SSRtype.equalsIgnoreCase("child")&& iSSRforchild < MaxChild )  {
						Serviceadd(driver,queryObjects,PassengerName,SSRs,SSRtype,SSRvalue); 
						iSSRforchild++;
					}

					else if (Passengertype.equalsIgnoreCase("adult")&& SSRtype.equalsIgnoreCase("adult")&& iSSRforadult < MaxAdult ) {
						Serviceadd(driver,queryObjects,PassengerName,SSRs,SSRtype,SSRvalue);
						iSSRforadult++;
					}*/
					if (Passengertype.equalsIgnoreCase("child") && SSRtype.equalsIgnoreCase("child")&& iSSRforchild < MaxChild )  {
						//Krishna - If SSRNo is Empty Add for Specific SSR Type 
						if(SSRNo.isEmpty()) {
							Serviceadd(driver,queryObjects,PassengerName,SSRs,SSRtype,SSRvalue); 
							iSSRforchild++;
						}
						//Krishna - If SSRNo is Not Empty, Then Add SSR Only for Number of SSRNo for Specific SSR Type
						else if(AddedSSRNo < Integer.parseInt(SSRNo)) {
							Serviceadd(driver,queryObjects,PassengerName,SSRs,SSRtype,SSRvalue); 
							iSSRforchild++;
							AddedSSRNo++;
						}
					}
					/*else if (Passengertype.equalsIgnoreCase("adult")&& SSRtype.equalsIgnoreCase("adult")&& iSSRforadult < MaxAdult ) {
						Serviceadd(driver,queryObjects,PassengerName,SSRs,SSRtype,SSRvalue);
						iSSRforadult++;
					} */
					else if (Passengertype.equalsIgnoreCase("Infant without seat (lap baby)")&& SSRtype.equalsIgnoreCase("INF")) {
						Serviceadd(driver,queryObjects,PassengerName,SSRs,SSRtype,SSRvalue);
						iSSRforadult++;
					}
					else if (Passengertype.equalsIgnoreCase("adult")&& SSRtype.equalsIgnoreCase("adult")&& iSSRforadult < MaxAdult ) {
						//Krishna - If SSRNo is Empty Add for Specific SSR Type 
						if(SSRNo.isEmpty()) {
							Serviceadd(driver,queryObjects,PassengerName,SSRs,SSRtype,SSRvalue); 
							iSSRforchild++;
						}
						//Krishna - If SSRNo is Not Empty, Then Add SSR Only for Number of SSRNo for Specific SSR Type
						else if(AddedSSRNo < Integer.parseInt(SSRNo)) {
							Serviceadd(driver,queryObjects,PassengerName,SSRs,SSRtype,SSRvalue); 
							iSSRforchild++;
							AddedSSRNo++;
						}
					}

					Thread.sleep(1000);
					//Validating balance due amount
					// queryObjects.logStatus(driver, Status.PASS, "Adding SSRs","SSRs added succesfully", null);
				}

				catch(Exception e) {

				}

			}
			//Krishna - If Specific SSR is Added for Specific SSR Type for Specific Count Only
			if(AddedSSRNo>0) {
				queryObjects.logStatus(driver, Status.PASS, "Added "+AddedSSRNo+" "+SSRtype+"-"+SSRvalue, "Added Specific No of SSR for Specific SSR Type ", null);
			}

		}
	}

	public static void paymentmodeHidenchek(WebDriver driver) {

		try {
			//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Boolean paymentmodeHidenchek=driver.findElements(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]")).size() >0;
			for (int i=1; i<10;i++){
				paymentmodeHidenchek=driver.findElements(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]")).size() >0;
				if(paymentmodeHidenchek)
				{
					JavascriptExecutor js = (JavascriptExecutor) driver;
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append("var x = $(\'"+paymentmodeHidenchek+"\');");
					stringBuilder.append("x.click();");
					js.executeScript(stringBuilder.toString());
				}
				else
					break;
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		} 
		//driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);//60
	}


	public static void Serviceadd(WebDriver driver, BFrameworkQueryObjects queryObjects, String PassengerName, String[] SSRs,String SSRtype, String SSRvalue)throws Exception {
		WebElement ssrWebEle = null;
		driver.findElement(By.xpath("//div[div[text()='Services']]")).click();
		loadhandling(driver);

		//unchecking the passengers  selected
		WebElement allPaxChkBox = driver.findElement(By.xpath("//md-checkbox[@aria-label='All Passengers']"));
		if(allPaxChkBox.getAttribute("aria-checked").equalsIgnoreCase("false")) {
			allPaxChkBox.click();
			Thread.sleep(1000);
			allPaxChkBox.click();
			Thread.sleep(1000);
		}
		else {
			allPaxChkBox.click();
		}
		String ssrcase=getTrimTdata(queryObjects.getTestData("After_Pay_addSSR_OR_Book_Case_addSSR"));  ///After_Pay_addSSR
		if(ssrcase.equalsIgnoreCase("yes"))
			balDueBeforeAddSSR=0;
		else
			balDueBeforeAddSSR = Double.parseDouble(driver.findElement(By.xpath("//div[contains(@model,'payment.balanceDue')]")).getText().trim());
		
		//Click on AllSegments checkbox only if it is not selected
		/*WebElement allSegmChkBox = driver.findElement(By.xpath("//md-checkbox[@aria-label='All Segments']"));
		if(allSegmChkBox.getAttribute("aria-checked").equalsIgnoreCase("false"))
			allSegmChkBox.click();
		Thread.sleep(1000);*/
		//Krishna - Add SSR for Specific Segment
		if(!FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRSpecificSegment")).isEmpty()) {
			String SegNo = FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRSpecificSegment")).trim();
			WebElement SpecificSegmChkbox = driver.findElement(By.xpath("//div[@current-item='ssrCtrl.model.segmentStartIndex']/div[2]/div/div["+SegNo+"]//md-checkbox[@aria-label='checkbox']"));
			if(SpecificSegmChkbox.getAttribute("aria-checked").equalsIgnoreCase("false"))
				SpecificSegmChkbox.click();
		}
		else {
			//Click on AllSegments checkbox only if it is not selected
			WebElement allSegmChkBox = driver.findElement(By.xpath("//md-checkbox[@aria-label='All Segments']"));
			if(allSegmChkBox.getAttribute("aria-checked").equalsIgnoreCase("false"))
				allSegmChkBox.click();
		}
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[contains(text(),'"+PassengerName+"')]//ancestor::div[contains(@ng-repeat,'ssrPassenger.passengers')]//md-checkbox")).click();
		loadhandling(driver);
		//Expanding Browser services form
		driver.findElement(By.xpath("//div[toggle-title[div[text()='Browse Services']]]/preceding-sibling::i")).click();

		for (int issrs=0; issrs<SSRs.length;issrs++) {

			String ssrXpath =  "//div[div[contains(text(),'"+SSRs[issrs]+"')]]";
			String	ssrType;
			ssrWebEle = driver.findElement(By.xpath(ssrXpath));
			ssrCode = driver.findElement(By.xpath(ssrXpath + "//div[1]")).getText().trim();
			ssrType = driver.findElement(By.xpath(ssrXpath + "//div[3]/div/div[1]")).getText().trim();
			String ssrnm=driver.findElement(By.xpath(ssrXpath + "//div[2]")).getText().trim();
			ssrWebEle.click();
			loadhandling(driver);
			ssrAmount=String.valueOf(selectingSSR(driver, ssrCode,queryObjects));

			if(!ssrType.equalsIgnoreCase("FREE")){
				/*  if (Listssrname.contains(ssrCode)) hh
        		   emdAmount = emdAmount + (Double.parseDouble(ssrAmount));
        	   		//emdAmount = emdAmount + (Double.parseDouble(ssrAmount)*totalnoofPAX *segment);
        	   else*/
				emdAmount = emdAmount + (Double.parseDouble(ssrAmount));
			}
			//Clicking on SSR name to add to order

			//Click on Add to Order
			driver.findElement(By.xpath("//form[@name='ssrSubcategory.form']//button[contains(text(),'Add To Order')]")).click();

			loadhandling(driver);
			CheckforError(driver,queryObjects);
			//collapsing Browser services form
			driver.findElement(By.xpath("//div[toggle-title[div[text()='Browse Services']]]/preceding-sibling::i")).click();

			loadhandling(driver);
			Thread.sleep(2000);
			try {
				//driver.findElement(By.xpath("//div[contains(text(),'"+PassengerName+"')]//ancestor::div[@class='hpe-pssgui toggle layout-column flex collapse']/div/div/i")).click();
				driver.findElement(By.xpath("//div[contains(text(),'"+PassengerName+"')]//ancestor::div[contains(@class,'collapse')]/div/div/i")).click();
				Thread.sleep(1000);
			}
			catch(Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "SSR Case : paX view issue", "SSR PAX view " + e.getMessage() , e);
			}
			//dd   adding EMDA
			List<WebElement> lSSRs = driver.findElements(By.xpath("//div[contains(@ng-click,'ssrPassenger.editServices')]"));//getText().trim();

			//String[] addedSSR;
			List<String> addedSSR=new ArrayList<>();
			String ss=null;
			try{
				for (int ilssrs=0; ilssrs<lSSRs.size(); ilssrs++) {
					ss=lSSRs.get(ilssrs).getText().trim();
					addedSSR.add(ss);   
				}
			}
			//EMDS
			
			
			catch(Exception e) {
				System.out.println(e);
			}
			
			List<WebElement> lSSRstandalone = driver.findElements(By.xpath("//div[contains(@ng-repeat,'paxInfo.standAloneService')]/div[contains(@class,'ng-binding')]"));
			for (int ilssrst=0; ilssrst<lSSRstandalone.size(); ilssrst++) {
				ss=lSSRstandalone.get(ilssrst).getText().trim();
				addedSSR.add(ss);   
			}
			
			double ssramt = Integer.parseInt(ssrAmount);//Modified by Navira
			if(addedSSR.contains(ssrnm.toUpperCase()) && (ssrAmount.equalsIgnoreCase("0.0") || ssrAmount.equalsIgnoreCase("FREE"))  )  {  // SSR free cases only we are validating ... because  paid ssr, ssr code will display after pay only...
				queryObjects.logStatus(driver, Status.PASS, "Adding one SSr: " + ssrnm, "Given SSR added successfully to the passenger " , null);
			}
			else if(addedSSR.contains(ssrnm.toUpperCase()) && (ssramt>0.0))
                queryObjects.logStatus(driver, Status.PASS, "Adding one SSr: " + ssrnm, "Given SSR is Paid SSR " , null);
			else {
				queryObjects.logStatus(driver, Status.FAIL, "Adding one SSr: " + ssrnm, "Given SSR not added to the passenger: " , null);
			}
			//ddd
			balDueAfterAddSSR = driver.findElement(By.xpath("//div[contains(@model,'payment.balanceDue')]")).getText().trim();
			//if(!ssrAmount.equalsIgnoreCase("FREE")) {
			balDueBeforeAddSSR=Double.parseDouble(roundDouble(String.valueOf(balDueBeforeAddSSR + Double.parseDouble(ssrAmount))));// need to check
			if(!ssrAmount.equalsIgnoreCase("FREE") && !ssrAmount.equalsIgnoreCase("0.0")) {
				if( Double.parseDouble(roundDouble(balDueAfterAddSSR)) ==balDueBeforeAddSSR ) {
					queryObjects.logStatus(driver, Status.PASS, "Adding SSr ", "Amount calculated correctly", null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Adding SSr ", "Amount not calculated correctly - Actual: " + balDueAfterAddSSR + "Expected: " + (balDueBeforeAddSSR + Double.parseDouble(ssrAmount)), null);
				}
			}
		}


	}


	public static Double selectingSSR(WebDriver driver, String ssrCode,BFrameworkQueryObjects queryObjects) throws InterruptedException, IOException {

		double ssrAmt=0;
		String EMDs_SSSRAmtXpath="//div[contains(@ng-if,'Service.Is')]/div[2]";
		String EMDA_SSSRAmtXpath="//div[contains(@ng-if,'Service.Is')]/div/div[1]/div[2]";	

		if(ssrCode.matches("0BX")) {
			ssrAmt=ssramtget(driver, queryObjects, EMDs_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("0B5|UMNR")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("VGML")) {
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("KSML")) {
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation   
		}
		else if(ssrCode.matches("FPML")) {
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation   
		}
		else if(ssrCode.matches("BIKE")) {
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation   
		} else if(ssrCode.matches("SEMN")) {
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation   
		} else if(ssrCode.matches("GFML")) {
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation///MEDA//OTHS
		} else if(ssrCode.matches("OTHS")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		} else if(ssrCode.matches("CHML")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		} else if(ssrCode.matches("DEPU")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		} else if(ssrCode.matches("DEPA")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		} else if(ssrCode.matches("WCLB")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		} else if(ssrCode.matches("VIPS")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		} else if(ssrCode.matches("MEQT")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		} else if(ssrCode.matches("MAAS")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		} else if(ssrCode.matches("MEDA")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		} else if(ssrCode.matches("SVAN")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("WEAP")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("WCBW")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("SURFBOARD UPTO 32KG")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("SURFBOARD UPTO 45KG")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}else if(ssrCode.matches("SURFBOARD UPTO 23KG")) {
				//Enter Explanation
				driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
				driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
				Thread.sleep(1000);
				//Copy to all legs
				driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
				ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		 else if(ssrCode.matches("AOXY")) {
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation   
		} else if(ssrCode.matches("2NDB")) {
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation   
		} else if(ssrCode.matches("DBU1 FLYUP BIDDING UP")) {
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation   
		} else if(ssrCode.matches("DBU3 FLYUP  INSTANT")) {
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation   
		}
		
		else if(ssrCode.matches("WCHR")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("WCHC")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("INFE")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("WCHS")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2s");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("PETC")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("1CAT");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("SPEQ")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("1BOARD");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("DEAF")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("DEAF");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("XBAG")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("20KG");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("EXST")) { 
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2seat");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("SPML")) { 
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("2MEAL");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("PDBG")) {   // only one
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("20KG");
			Thread.sleep(1000);
			//Copy to all legs
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else if(ssrCode.matches("TKTL")||ssrCode.matches("PRICELOOCK UP TO 24 HRS")) {
			//Enter Explanation
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).click();
			driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("03MAR/1800HS");
			Thread.sleep(1000);
			//Copy to all legs
			if(driver.findElements(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).size()>0)
				driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//span[contains(@ng-click,'copyToLegs')]")).click();
			ssrAmt=ssramtget(driver, queryObjects, EMDA_SSSRAmtXpath);  //amount calculation
		}
		else{
			if (driver.findElements(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).size()>0)
				queryObjects.logStatus(driver, Status.FAIL, "selectingSSR nor available need add in script ssr code is :"+ssrCode, "ssr code "+ssrCode , null);
			ssrAmt=0;
		}
		return ssrAmt;


	}

	public static double ssramtget(WebDriver driver, BFrameworkQueryObjects queryObjects ,String xPath) throws IOException{
		double ssritem=0;
		try{
			List<WebElement> ssramt=null;
			ssramt=driver.findElements(By.xpath(xPath));

			for (int issramt = 0; issramt < ssramt.size(); issramt++) {
				ssritem=ssritem+Double.parseDouble(ssramt.get(issramt).getText().split("\\s+")[0]);
			}
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "SSR amount issue", "SSR Amount  issue: " + e.getMessage() , e);
			ssritem=0;
		}
		return ssritem;

	}
	public static void singleFOP(WebDriver driver,BFrameworkQueryObjects queryObjects,String fop,String tansationType) throws Exception{

		String loginPOS=Login.Call_Center_Login;
		String CreditCard_Xpath = null;
		PPSBillDet=0;
		if (loginPOS.equalsIgnoreCase("YES")) 
			CreditCard_Xpath=Credit_Debitcard_CC_Xpath;
		else
			if ((fop.equalsIgnoreCase("creditcard")))
				CreditCard_Xpath=Creditcard_ATO_CTO_Xpath;
			else if ((fop.equalsIgnoreCase("Debitcard")))
				CreditCard_Xpath=Debitcard_ATO_CTO_Xpath;
		
		/*String semdno = Login.EMDNO;   need to check with yashodha
		String[] asemdno=semdno.split(";");


		String semdamt = Login.EMDAmt;
		//String semdamt = Login.envRead(Login.EMDAmt);

		String[] asemdamt=semdamt.split(";");

		int emditer=0;*/

		ppspaymentmodesingle=fop;
		queryObjects.logStatus(driver, Status.INFO, "Form of Payment", "FOP type: " + fop, null);

		String payamtsinglefop=driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//input[@aria-label='amount']")).getAttribute("value").trim();
		
		storepayAmount(payamtsinglefop);
		
		//Directly clicking on Pay button if FOP is Cash since by default CASH will be selected as FOP
		WebDriverWait wait = new WebDriverWait(driver, 60);
		if(fop.equalsIgnoreCase("Cash")) {
			driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")).click();
			Thread.sleep(1000);	
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Cash')]]")).click();
			Thread.sleep(3000);
		}

		//Else, selecting the FOP and entering all the required details
		else if(fop.equalsIgnoreCase("creditcard")){

			//Clicking on FOP select list
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//md-select[@id='paymentIndex.paymentType']")));
			driver.findElement(By.xpath("//md-select[@id='paymentIndex.paymentType']")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(CreditCard_Xpath)).click();

			String ccSubType = getTrimTdata(queryObjects.getTestData("ccSubType"));
			//Selecting VISA as FOP Subtype
			driver.findElement(By.xpath("//md-select[@id='subType']")).click();
			selectingoneCrditCard(driver, queryObjects, ccSubType);
			queryObjects.logStatus(driver, Status.PASS, "Credit Card subtype", "Credit card subtype: " + ccSubType , null);

			String creditcardNo = queryObjects.getTestData("ccNum");
			String CCVnum = getTrimTdata(queryObjects.getTestData("CCV"));
			//Entering Credit Card number
			//driver.findElement(By.xpath("//input[@name='CCCC']")).click();
			driver.findElement(By.xpath(CardNumber_Xpath)).sendKeys(creditcardNo);
			storecarddetails(ccSubType,creditcardNo);
			enterCCdetails(driver,queryObjects,CCVnum);

			//End of if statement to enter credit card details

		}
		else if(fop.equalsIgnoreCase("Debitcard")){

			//Clicking on FOP select list
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//md-select[@id='paymentIndex.paymentType']")));
			//driver.findElement(By.xpath("//md-select[@id='paymentIndex.paymentType']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//md-select[@id='paymentIndex.paymentType']")));
			driver.findElement(By.xpath("//md-select[@id='paymentIndex.paymentType']")).click();
			
			
			Thread.sleep(3000);
			driver.findElement(By.xpath(CreditCard_Xpath)).click();

			String ccSubType = getTrimTdata(queryObjects.getTestData("ccSubType"));
			//Selecting VISA as FOP Subtype
			driver.findElement(By.xpath("//md-select[@id='subType']")).click();
			selectingoneCrditCard(driver, queryObjects, ccSubType);
			queryObjects.logStatus(driver, Status.PASS, "Credit Card subtype", "Credit card subtype: " + ccSubType , null);

			String creditcardNo = queryObjects.getTestData("ccNum");
			String CCVnum = getTrimTdata(queryObjects.getTestData("CCV"));
			//Entering Credit Card number
			//driver.findElement(By.xpath("//input[@name='CCCC']")).click(); CardNumber_Xpath
			//driver.findElement(By.xpath("//input[@name='CCCC']")).sendKeys(creditcardNo);
			driver.findElement(By.xpath(CardNumber_Xpath)).sendKeys(creditcardNo);
			enterCCdetails(driver,queryObjects,CCVnum);

			//End of if statement to enter credit card details

		}

		else if(fop.equalsIgnoreCase("installment")) {

			//Clicking on FOP select list
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")));
			driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CreditCard_Xpath)));
			driver.findElement(By.xpath(CreditCard_Xpath)).click();
			Thread.sleep(2000);
			//Click on list to select subtype
			driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='subType']")).click();
			Thread.sleep(2000);
			String sccSubType = getTrimTdata(queryObjects.getTestData("ccSubType"));
			String creditcardNo = queryObjects.getTestData("ccNum");
			String CCVnum = getTrimTdata(queryObjects.getTestData("CCV"));

			selectingoneCrditCard(driver,queryObjects,sccSubType);
			WebElement ccSubType = driver.findElement(By.xpath(CardNumber_Xpath));
			ccSubType.click();
			ccSubType.sendKeys(creditcardNo);
			storecarddetails(sccSubType,creditcardNo);
			
			//edited by srini
			List<WebElement> bank=driver.findElements(By.xpath("//md-select[contains(@ng-model,'formControls.ngModel') and contains(@aria-label,'Bank')]"));
			if(bank.size()>0)
			{
				//driver.findElements(By.xpath("//md-select[contains(@ng-model,'formControls.ngModel') and contains(@aria-label,'Bank')]")).get(0).click();
				driver.findElement(By.xpath("//md-select[contains(@ng-model,'formControls.ngModel') and contains(@aria-label,'Bank')]")).click();
				driver.findElement(By.xpath("//md-option[contains(@ng-switch-when,'Bank')]/div[contains(text(),'Afirme')]")).click();
			}
			
			String InstallmentNum = queryObjects.getTestData("InstallmentNum");
			String BankName = queryObjects.getTestData("BankName");
					
			
			if(InstallmentNum.isEmpty())
			{
				driver.findElement(By.xpath("//md-select[contains(@ng-model,'formControls.ngModel') and contains(@aria-label,'Installments')]")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//md-option[contains(@ng-switch-when,'CCIN') and contains(@value,'3')]/div")).click();
			}
			else
			{	
				driver.findElement(By.xpath("//md-select[contains(@ng-model,'formControls.ngModel') and contains(@aria-label,'Installments')]")).click();
				Thread.sleep(2000);
				//driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option["+InstallmentNum+"]")).click();
				driver.findElement(By.xpath("//md-option[contains(@ng-switch-when,'CCIN') and contains(@value,'"+InstallmentNum+"')]/div")).click();
			}
			
			//Clicking on installment list and selecting option '6'
			/*driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@aria-label='Installments']")).click();
			loadhandling(driver);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[3]")).click();*/
			loadhandling(driver);
			//Etring credit card other details
			enterCCdetails(driver,queryObjects,CCVnum);
			
		}//End of if statement to enter credit card details
		else if(fop.equalsIgnoreCase("misc")) {
			//Clicking on FOP select list
			driver.findElement(By.xpath("//md-select[@id='paymentIndex.paymentType']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//md-option[div[contains(text(),'Miscellaneous')]]")));
			driver.findElement(By.xpath("//md-option[div[contains(text(),'Miscellaneous')]]")).click();

			//Selecting miscellaneous sub type
			String miscSubType = queryObjects.getTestData("miscSubType");
			driver.findElement(By.xpath("//md-select[@id='subType']")).click();
			selectingonemisc(driver, queryObjects, miscSubType);
			loadhandling(driver);
			if((miscSubType.equalsIgnoreCase("Check")) || (miscSubType.equalsIgnoreCase("banktransfer"))) {
				//Enter Check/Deposit date
				String ckDate = null;
				Calendar cal = Calendar.getInstance();
				ckDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
				driver.findElement(By.xpath("//div[contains(@class,'md-datepicker-input-container')]/input")).sendKeys(ckDate);
				Thread.sleep(2000);
			}
			/*else if (miscSubType.equalsIgnoreCase("emd")) {			
			}*/
			/*else if (miscSubType.equalsIgnoreCase("emd")) {			

				String getemdno="";
				getemdno = asemdno[emditer];
				if(getemdno.isEmpty())
					queryObjects.logStatus(driver, Status.PASS, "EMD Number not available", "EMD number nog generated"  , null);
				driver.findElement(By.xpath("//input[@name='MSEMDN' and contains(@aria-invalid,'true')]")).sendKeys(getemdno);
				//driver.findElement(By.xpath("//i[contains(@ng-click,'paymentTemplate.validateEMD')]")).click();
				Thread.sleep(3000);
				List<WebElement> emdclick=	driver.findElements(By.xpath("//input[@name='MSEMDN']//ancestor::div[@parentmodel='paymentTemplate.parentmodel']/following-sibling::div/i"));
				emdclick.get(emditer).click();
				
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[contains(text(),'Confirm')]")).click();
				emditer=emditer+1;
			}*/
			else if (miscSubType.equalsIgnoreCase("SST")) {
				driver.findElement(By.xpath("//input[@name='MSSSDE']")).sendKeys("TESTCOPA");
				driver.findElement(By.xpath("//input[@name='MSSSNR']")).sendKeys("5656565656565656");				
			}
			else if (miscSubType.equalsIgnoreCase("Tarjeta_Clave")) {
				driver.findElement(By.xpath("//input[@name='MSTCRF']")).sendKeys("15624");				
			}
			else if(miscSubType.equalsIgnoreCase("barter")) {
				driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("1000001674");//Should ask Gomathi 
				driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
			}
            else if(miscSubType.equalsIgnoreCase("account")) {
                driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("1500001587");//Should ask Gomathi 
                driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
            }

			else if(miscSubType.equalsIgnoreCase("famtrip")) {
				driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("6040032ASU");//Should ask Gomathi 
				driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
			}
			//Navira - 06Mar
			else if(miscSubType.equalsIgnoreCase("Sponsorships")) {
				driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("6030043005");//Should ask Gomathi 
				driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
			}
			else if(miscSubType.equalsIgnoreCase("Employee Rewards")) {
                driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("4000002ADZ");//Should ask Gomathi 
                driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
			}

			else if(miscSubType.equalsIgnoreCase("Donations")) {
				driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("1000002PTY");//Should ask Gomathi 
				driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
			}
			else if(miscSubType.equalsIgnoreCase("Business Travel")) {
				driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("5302003CLO");//Should ask Gomathi 
				driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
			}
			//25Mar - Navira
			else if(miscSubType.equalsIgnoreCase("Public relations")) {
				driver.findElement(By.xpath("//input[@name='MSCCRF']")).sendKeys("6030032DEN");//Should ask Gomathi 
				driver.findElement(By.xpath("//input[@name='MSCCDE']")).sendKeys("TESTCOPA");
			}
			else {
				driver.findElement(By.xpath("//input[@name='MSCMDE']")).sendKeys("TESTCOPA");				
			}

		}


	}
	public static void selectingonemisc(WebDriver driver,BFrameworkQueryObjects queryObjects,String misctype) throws InterruptedException, IOException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]")));

		if(misctype.equalsIgnoreCase("Check")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Check')]]")).click();
			//driver.findElement(By.xpath("/html/body/div[5]/md-select-menu/md-content/md-option[1]/div[1]")).click();
			loadhandling(driver);
			driver.findElement(By.xpath("//input[@name='MSCKNR']")).sendKeys("500240025");

		}
		else if(misctype.equalsIgnoreCase("banktransfer")) {
			driver.findElement(By.xpath("//div[contains(@class,'md-active')]//md-option[div[contains(text(),'Bank Transfer')]]")).click();
			//driver.findElement(By.xpath("/html/body/div[5]/md-select-menu/md-content/md-option[13]/div")).click();
			loadhandling(driver);
			driver.findElement(By.xpath("//input[@name='MSBTRF']")).sendKeys("10024");

		}
		else if(misctype.equalsIgnoreCase("emd")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'EMD')]]")).click();
			//driver.findElement(By.xpath("/html/body/div[5]/md-select-menu/md-content/md-option[12]/div")).click();
		}
		else if(misctype.equalsIgnoreCase("Denied_Boarding")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Denied Boarding Compensation')]]")).click();
			//driver.findElement(By.xpath("/html/body/div[5]/md-select-menu/md-content/md-option[2]/div[1]")).click();
		}
		else if(misctype.equalsIgnoreCase("Ground_Transportation")) {
			//driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Ground Transportation')]]")).click();
			driver.findElement(By.xpath("/html/body/div[5]/md-select-menu/md-content/md-option[3]/div[1]")).click();
		}
		else if(misctype.equalsIgnoreCase("Hotel_Meals_issues")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Hotel and Meals issues')]]")).click();
			//driver.findElement(By.xpath("/html/body/div[5]/md-select-menu/md-content/md-option[4]/div")).click();
		}
		else if(misctype.equalsIgnoreCase("Guard_Interrupt_Flight")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Guard Interrupt Flight')]]")).click();
			//driver.findElement(By.xpath("/html/body/div[5]/md-select-menu/md-content/md-option[5]/div")).click();
		}
		else if(misctype.equalsIgnoreCase("Detention_Guard")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Detention, Guard')]]")).click();
			//driver.findElement(By.xpath("/html/body/div[5]/md-select-menu/md-content/md-option[6]/div")).click();
		}
		else if(misctype.equalsIgnoreCase("FIM_Rerouting")) {
			//driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Transportation FIM &amp; Rerouting')]]")).click();
			driver.findElement(By.xpath("/html/body/div[5]/md-select-menu/md-content/md-option[7]/div")).click();
		}
		else if(misctype.equalsIgnoreCase("Rerouting_OverSales")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Transportation Rerouting - Over Sales')]]")).click();
			//driver.findElement(By.xpath("/html/body/div[5]/md-select-menu/md-content/md-option[8]/div")).click();
		}
		else if(misctype.equalsIgnoreCase("Rerouting_Interrupt_Flight")) {
			//driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Transportation FIM &amp; Rerouting Interrupt Flight')]]")).click();
			driver.findElement(By.xpath("/html/body/div[5]/md-select-menu/md-content/md-option[9]/div")).click();
		}
		else if(misctype.equalsIgnoreCase("Transportation_Others")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Ground Transportation Others')]]")).click();
			//driver.findElement(By.xpath("/html/body/div[5]/md-select-menu/md-content/md-option[10]/div")).click();
		}
		else if(misctype.equalsIgnoreCase("Meal_Others")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Meal Others')]]")).click();
			//driver.findElement(By.xpath("/html/body/div[5]/md-select-menu/md-content/md-option[11]/div")).click();
		}
		else if(misctype.equalsIgnoreCase("SST")) {
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'SST')]]")).click();
			//driver.findElement(By.xpath("/html/body/div[5]/md-select-menu/md-content/md-option[14]/div")).click();
		}
		else if (misctype.equalsIgnoreCase("Tarjeta_Clave")) {
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Tarjeta Clave')]]")).click();
			Thread.sleep(1000);			
		}
		else if (misctype.equalsIgnoreCase("barter")) {
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'With Cost Center')]]")).click();
			driver.findElement(By.xpath("//md-select[@aria-label='Account name']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Barter')]]")).click();
			Thread.sleep(1000);			
		}

        else if (misctype.equalsIgnoreCase("account")) {
               Thread.sleep(1000);
               driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'With Cost Center')]]")).click();
               driver.findElement(By.xpath("//md-select[@aria-label='Account name']")).click();
               Thread.sleep(1000);
               driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option/div[contains(text(),'Account Receivable Clients')]")).click();
               Thread.sleep(1000);              
        }

		else if (misctype.equalsIgnoreCase("famtrip")) {
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'With Cost Center')]]")).click();
			driver.findElement(By.xpath("//md-select[@aria-label='Account name']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option/div[contains(text(),'FAM TRIPS')]")).click();
			Thread.sleep(1000);			
		}
		
		//Navira - 06Mar
		else if (misctype.equalsIgnoreCase("Sponsorships")) {
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'With Cost Center')]]")).click();
			driver.findElement(By.xpath("//md-select[@aria-label='Account name']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Sponsorship')]]")).click();
			Thread.sleep(1000);			
		}

        else if (misctype.equalsIgnoreCase("Employee Rewards")) {
               Thread.sleep(1000);
               driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'With Cost Center')]]")).click();
               driver.findElement(By.xpath("//md-select[@aria-label='Account name']")).click();
               Thread.sleep(1000);
               driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Employee reward')]]")).click();
               Thread.sleep(1000);              
        }

		else if (misctype.equalsIgnoreCase("Donations")) {
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'With Cost Center')]]")).click();
			driver.findElement(By.xpath("//md-select[@aria-label='Account name']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option/div[contains(text(),'Donations')]")).click();
			Thread.sleep(1000);			
		}
		
		else if (misctype.equalsIgnoreCase("Business Travel")) {
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'With Cost Center')]]")).click();
			driver.findElement(By.xpath("//md-select[@aria-label='Account name']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option/div[contains(text(),'Business Travel Staff')]")).click();
			Thread.sleep(1000);			
		}
		//25Mar - Navira
		else if (misctype.equalsIgnoreCase("Public relations")) {
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'With Cost Center')]]")).click();
			driver.findElement(By.xpath("//md-select[@aria-label='Account name']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option/div[contains(text(),'Public relations')]")).click();
			Thread.sleep(1000);			
		}
		else{
			queryObjects.logStatus(driver, Status.FAIL, "Given Credit card name not available in script", "Given Credit Card name not availabnle is script ",null);
		}
		Thread.sleep(2000);		
	}
	public static String getPassengerdetails(WebDriver driver) {
		String sPassengertype = "";

		//List<WebElement> lpassengerlist =driver.findElements(By.xpath("//div[contains(@class,'pssgui-design-sub-heading-6')]/span"));
		//List<WebElement> lpassengerlist =driver.findElements(By.xpath("//div[contains(@class,'pssgui-design-sub-heading-6')]"));
		//Krishna
        List<WebElement> lpassengerlist = new ArrayList<WebElement>();
        if(driver.findElements(By.xpath("//div[contains(@class,'pssgui-design-sub-heading-6')]/span")).size()>0)
           lpassengerlist =driver.findElements(By.xpath("//div[contains(@class,'pssgui-design-sub-heading-6')]/span"));
        else
           lpassengerlist =driver.findElements(By.xpath("//div[contains(@class,'pssgui-design-sub-heading-6')]"));

		
		for(int ipax = 0; ipax<totalnoofPAX; ipax++) {

			lpassengerlist.get(ipax).click();
			loadhandling(driver);
			String PassengerName = lpassengerlist.get(ipax).getText();
			String Passengertype = driver.findElement(By.xpath("//div[@class='md-text']/span")).getText();
			sPassengertype = sPassengertype+PassengerName+"-"+Passengertype+";";
		}
		return sPassengertype;

	}

	public void EnrollConnectMiles(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException {
		try{
			driver.findElement(By.xpath(passangerTabXpath)).click();
			loadhandling(driver);
			driver.findElement(By.xpath("//div[contains(text(),'Frequent Flyer')]")).click();
			loadhandling(driver);
			driver.findElement(By.xpath("//div[contains(text(),'Enroll ConnectMiles') and contains(@class,'pssgui-link')]")).click();
			Thread.sleep(2000);
			String nationality=driver.findElement(By.xpath("//input[@aria-label='Nationality']")).getAttribute("value");
			if(nationality.equalsIgnoreCase("")){
				driver.findElement(By.xpath("//input[@aria-label='Nationality']")).sendKeys("US");
				loadhandling(driver);
				//driver.findElement(By.xpath("//input[@aria-label='Nationality']")).sendKeys(Keys.TAB);
				driver.findElement(By.xpath(clickUSpopuXpath)).click();
			}
			String Email=driver.findElement(By.xpath("//input[@aria-label='Email Address']")).getAttribute("value");
			if(Email.equalsIgnoreCase("")){
				driver.findElement(By.xpath("//input[@aria-label='Email Address']")).sendKeys(RandomStringUtils.random(6, true, false)+"@copa.com");
			}
			String dateEnrol=driver.findElement(By.xpath("//md-datepicker[@type='date']//input")).getAttribute("value");
			if(dateEnrol.equalsIgnoreCase("")){
				Calendar cal2nowshow = Calendar.getInstance();
				SimpleDateFormat sdf2nowshow = new  SimpleDateFormat("MM/dd/yyyy");
				cal2nowshow.add(Calendar.DATE, -1440);
				String pDate=sdf2nowshow.format(cal2nowshow.getTime());
				driver.findElement(By.xpath("//md-datepicker[@type='date']//input")).sendKeys(pDate);
			}
			Thread.sleep(1000);
			driver.findElement(By.xpath("//button[contains(@ng-click,'enroll')]")).click();
			loadhandling(driver);
			driver.findElement(By.xpath("//button[contains(@ng-click,'add')]")).click();
			loadhandling(driver);
			driver.findElement(By.xpath("//button[text()='OK' and not (@disabled='disabled')]")).click();
            loadhandling(driver);
            queryObjects.logStatus(driver, Status.PASS, "EnrollConnectMiles","EnrollConnectMiles Added Successfully",null);
		}catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "EnrollConnectMiles issue","EnrollConnectMiles",e);
		}
	}

	public void PAXreductionType(WebDriver driver,BFrameworkQueryObjects queryObjects,String paxReductionTypeCount,String paxReductionTypeName) throws Exception{

	
		String[]paxtypes;
		driver.findElement(By.xpath(PaxReductionXpath)).click();
		Thread.sleep(1000);
		if(Integer.parseInt(paxReductionTypeCount) == 1) {
			driver.findElement(By.xpath(PaxReductiontypeXpath)).clear();
			driver.findElement(By.xpath(PaxReductiontypeXpath)).sendKeys(paxReductionTypeName);
			Thread.sleep(1000);
			//FlightSearch.driver.findElement(By.xpath(PaxReductiontypeXpath)).sendKeys(Keys.TAB);
			driver.findElement(By.xpath(clickUSpopuXpath)).click();
			if(paxReductionTypeName.equalsIgnoreCase("SRC")) {
				List<WebElement> paxtypesagelis=driver.findElements(By.xpath(paxreductionageXpath));
				paxtypesagelis.get(0).sendKeys("70");
			}

		}

		else if(Integer.parseInt(paxReductionTypeCount) > 1) {
			paxtypes = paxReductionTypeName.split(";");
			int ireductyp=0;

			for(int ipaxreduc=0; ipaxreduc<paxtypes.length; ipaxreduc++) {
				ireductyp=ireductyp+1;
				List<WebElement> paxtypeslist=new ArrayList<>();
				paxtypeslist=driver.findElements(By.xpath(PaxReductiontypeXpath));
				paxtypeslist.get(ipaxreduc).clear();
				paxtypeslist.get(ipaxreduc).sendKeys(paxtypes[ipaxreduc]);
				//paxtypeslist.get(ipaxreduc).sendKeys(Keys.TAB);
				Thread.sleep(1000);
				driver.findElement(By.xpath(clickUSpopuXpath)).click();
				//paxtype(paxtypes[ipaxreduc], driver, queryObjects);
				if(paxtypes[ipaxreduc].equalsIgnoreCase("SRC")) {
					List<WebElement> paxtypesagelis=driver.findElements(By.xpath(paxreductionageXpath));
					paxtypesagelis.get(ipaxreduc).sendKeys("70");
				}

			}

		}


	}

	public void paymentAfterassignseat(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{
		String saddpayemtSeat=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@model='shoppingCart.model.payment.balanceDue']/div")).getText());
		
		String paymentseatchk=queryObjects.getTestData("paymentSeat");
		//saddpayemtSeat="240.00";
		if(!saddpayemtSeat.isEmpty())
		{
			if( ((Double.parseDouble(saddpayemtSeat))>0.0) && (FFpPax==false)  )
				
			{
				double paymentseatamt=Double.parseDouble(saddpayemtSeat);
				
				//String getemtTemp= Login.envRead(Login.EMDAmt);
				String getemtTemp= Login.EMDAmt;
				getemtTemp=getemtTemp.trim();
				if(!getemtTemp.isEmpty())  // storing for multiple EMD number in ENv sheet 
					Login.EMDAmt=getemtTemp+";"+saddpayemtSeat;
				else
					Login.EMDAmt= saddpayemtSeat;
				/*if(!getemtTemp.isEmpty())  // storing for multiple EMD number in ENv sheet 
					Login.envwrite(Login.EMDAmt, getemtTemp+";"+saddpayemtSeat);
				else
					Login.envwrite(Login.EMDAmt, saddpayemtSeat);*/
				// ssr case payment
				queryObjects.logStatus(driver, Status.PASS, "After add PayMent seat try to pay the amount","Try to pay amount", null);
				//Click on check out button
				driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
				FlightSearch.loadhandling(driver);	
				String errormsg=getErrorMSGfromAppliction(driver, queryObjects); // try to get error message....
				if (errormsg.equalsIgnoreCase("Invalid preferred seat for this class") && totalnoofPAX > 9)	
				{
					queryObjects.logStatus(driver, Status.WARNING, "Invalid preferred seat for this class", "Seat Cannot be assigned for group passengers" ,null);
				}
				else
					{
					// clear the static value
					MultipleFOPType="";
					MultFOPsubType="";
					fopCardNums="";
					
					MultipleFOPType = FlightSearch.getTrimTdata(queryObjects.getTestData("payseatMultipleFOPType"));
					MultFOPsubType = FlightSearch.getTrimTdata(queryObjects.getTestData("payseatMultipleFOPSubType"));
					fopCardNums = FlightSearch.getTrimTdata(queryObjects.getTestData("payseatMultipleFOPCardNums"));
					String BankNames = queryObjects.getTestData("payseatMultipleBankName").trim();
					String InstallmentNums = queryObjects.getTestData("payseatMultipleInstallmentNum").trim();
					MulFOP(driver,queryObjects,paymentseatamt,MultipleFOPType,MultFOPsubType,fopCardNums,BankNames,InstallmentNums,"SEATCASE");
	
					driver.findElement(By.xpath("//button[text()='Pay' and not(@disabled='disabled')]")).click();
	
					// Handling FOID Details::::
	
					FlightSearch.enterFoiddetails(driver,queryObjects);
	
					//Handling Email recipients popup
					FlightSearch.emailhandling(driver,queryObjects);
	
					try {
						WebDriverWait wait = new WebDriverWait(driver, 120);
						wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
						String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
						queryObjects.logStatus(driver, Status.PASS, "Payment", statusMessage, null);
						String emdno=driver.findElement(By.xpath("//span[text()='EMD']/following-sibling::span")).getText().trim();
	
						System.err.println(emdno);
						//String semdnoTemp = Login.envRead(Login.EMDNO);
						String semdnoTemp = Login.EMDNO;
						semdnoTemp=semdnoTemp.trim();
						
						if(!semdnoTemp.isEmpty())  // storing for multiple EMD number in ENv sheet 
							Login.EMDNO= semdnoTemp+";"+emdno;
						else
							Login.EMDNO= emdno;
						
						/*if(!semdnoTemp.isEmpty())  // storing for multiple EMD number in ENv sheet 
							Login.envwrite(Login.EMDNO, semdnoTemp+";"+emdno);
						else
							Login.envwrite(Login.EMDNO, emdno);*/
						
	
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Payment", "Payment Unsuccessful: " + e.getMessage() , e);
					}
					driver.findElement(By.xpath("//button[text()='Done']")).click();
					loadhandling(driver);
				}
			}
			else if (FFpPax && (paymentseatchk.equalsIgnoreCase("yes")) )  {
				if ((Double.parseDouble(saddpayemtSeat)==0.0) || (Double.parseDouble(saddpayemtSeat)==0) )
					queryObjects.logStatus(driver, Status.PASS, "FFP Vs payment seat functionality checking","Not displaying amount for FFP pax with/without non FFP pax  both  in same PNR",null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "FFP Vs payment seat functionality checking","should not displaying amount for FFP pax with/without non FFP pax  both  in same PNR",null);		
			}
			/*else if(FFpPax==false)
				queryObjects.logStatus(driver, Status.FAIL,"","",null);*/
		}
	}
	public void AgencyPayment(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException {
		try{
			queryObjects.logStatus(driver, Status.PASS, "AgencyPayment Process", "AgencyPayment Process started" ,null);
			driver.findElement(By.xpath("//button[contains(text(),'Agency Payment')]")).click();
			loadhandling(driver);
			Thread.sleep(3000);//Wait required - Himani
			// Navira 
			String agency_name = FlightSearch.getTrimTdata(queryObjects.getTestData("AgencyName"));
            if(agency_name.equalsIgnoreCase(""))//NS
              driver.findElement(By.xpath("//div[@id='adjTable']/md-content/div[1]/span/md-radio-group/md-radio-button")).click();
            else {
              driver.findElement(By.xpath("//md-radio-button[@aria-label='agencyName']/div/div[1]")).click();
              driver.findElement(By.xpath("//label[contains(text(),'Name or IATA Number')]/following-sibling::input")).sendKeys(agency_name);
              FlightSearch.loadhandling(driver);
              driver.findElement(By.xpath("//div[@id='adjTable']/md-content/div[1]/span/md-radio-group/md-radio-button")).click();
            }
			//
		//	driver.findElement(By.xpath("//div[@id='adjTable']/md-content/div[1]/span/md-radio-group/md-radio-button")).click();
			loadhandling(driver);
			driver.findElement(By.xpath("//button[contains(text(),'Confirm')]")).click();
			loadhandling(driver);
			fareAmount=driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//input[@aria-label='amount']")).getAttribute("value");
			queryObjects.logStatus(driver, Status.PASS, "After agent amount deduction", "Going to Pay amount is "+fareAmount ,null);
		}catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "AgencyPayment Process", "AgencyPayment Process Filed " +e.getLocalizedMessage() ,e);
		}
	}
	public void SecureFlightInfo(WebDriver driver,BFrameworkQueryObjects queryObjects ) throws IOException {
		try{
			driver.findElement(By.xpath(passangerTabXpath)).click();
			loadhandling(driver);
			driver.findElement(By.xpath("//div[contains(text(),'Travel document')]")).click();
			loadhandling(driver);
			driver.findElement(By.xpath("//button[contains(@ng-click,'itineraryTraveler.editItem')]")).click();
			loadhandling(driver);
			driver.findElement(By.xpath("//md-select[contains(@ng-model,'DocumentType')]")).click();
			//driver.findElement(By.xpath("//md-input-container[label[contains(text(),'Document Name')]]//md-select-value[contains(@class,'md-select-value')]")).click();
			loadhandling(driver);

			driver.findElement(By.xpath("//md-option[@value='PP']/div[contains(text(),'Passport')]")).click();
			loadhandling(driver);     
			driver.findElement(By.xpath("//input[contains(@ng-model,'CountryOrStateCode')]")).sendKeys("US");
			Thread.sleep(1000);
			driver.findElement(By.xpath("//input[contains(@ng-model,'DocID')]")).sendKeys("367812468");
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[div[toggle-title[div[contains(text(),'Form of Identification')]]]]/i")).click();
			Thread.sleep(1000);
			if(driver.findElements(By.xpath("//input[@aria-label='Country of Issuance']")).size()>0)
			{
				
				
				driver.findElement(By.xpath("//input[@aria-label='Country of Issuance']")).sendKeys("US");
				Thread.sleep(2000);

				driver.findElement(By.xpath(clickUSpopuXpath)).click();
				//driver.findElement(By.xpath("//input[@aria-label='Country of Issuance']")).sendKeys(Keys.TAB);
				Thread.sleep(1000);
			/*//	driver.findElement(By.xpath("//input[@aria-label='Nationality' and (@required='required')]")).sendKeys("US");
				Thread.sleep(1000);
				driver.findElement(By.xpath("//input[@aria-label='Nationality' and (@required='required')]")).sendKeys(Keys.TAB);
				Thread.sleep(1000);*/ //YYYYYYY
			}
			driver.findElement(By.xpath("//button[text()='OK' and not (@disabled='disabled')]")).click();
			loadhandling(driver);
			queryObjects.logStatus(driver, Status.PASS, "Secureflight info", "Secureflight info  updated " ,null);

		}catch(Exception e) {

			queryObjects.logStatus(driver, Status.FAIL, "Secureflight info", "Secureflight info not updated " + e.getLocalizedMessage(), e);
		}
	}

	public static void CheckforError(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException{
		String sError="";
		sError = getErrorMSGfromAppliction(driver,queryObjects);

		//check if SSR not available

		if (sError.contains("SSR NOT AVAILABLE")) {

			fareAmount = String.valueOf(Double.parseDouble(fareAmount)-emdAmount);
			queryObjects.logStatus(driver, Status.WARNING, "SSR was not available", "SSR was not available in Application ", null);

		}
	}
	public static double Manual_Quote(WebDriver driver,BFrameworkQueryObjects queryObjects,String worningCase,String ManulaQuote_Fare_amt,String ManualQuote_Euivalent_amt,Double Service_Fee) throws Exception{
		queryObjects.logStatus(driver, Status.PASS, "Manual quote process started", "Manual quote process started", null);
		Manualquotecase=true;
		String Manual_Quote_Pricing_Construction=queryObjects.getTestData("Manual_Quote_Pricing_Construction");
		// Manual quote process started
		driver.findElement(By.xpath(TicketXpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[text()='Quotes']/parent::div")).click();
		Thread.sleep(2000);
		List<WebElement> Manual_Quote=driver.findElements(By.xpath("//button[@aria-label='Add Manual Quote']"));
		double fareAmountManualQuote=0 ;
		String amt="2000";
		for (int iquote = 0; iquote < Manual_Quote.size(); iquote++) {
			Manual_Quote=driver.findElements(By.xpath("//button[@aria-label='Add Manual Quote']"));
			//driver.findElement(By.xpath("//button[@aria-label='Add Manual Quote']")).click();
			Manual_Quote.get(iquote).click();
			List<WebElement> farebasis=driver.findElements(By.xpath("//input[@ng-model='flightSearch.model.FareBasis']"));

			List<WebElement> classcode=driver.findElements(By.xpath("//input[@ng-model='flightSearch.model.RBDClassCode']"));
			for (int ifar = 0; ifar < farebasis.size(); ifar++) {
				farebasis.get(ifar).click();
				String mclas=classcode.get(ifar).getAttribute("value");
				farebasis.get(ifar).sendKeys(mclas);
				Thread.sleep(1000);
			}
			List<WebElement> nvadate=driver.findElements(By.xpath("//div[div[label[span[@translate='NVA']]]]/md-datepicker/div/input"));
			Calendar calcnva = Calendar.getInstance();
			calcnva.add(Calendar.DATE, +5);
			String timeStampnva = new SimpleDateFormat("MM/dd/yyyy").format(calcnva.getTime());
			for (int nva = 0; nva < nvadate.size(); nva++) {
				nvadate.get(nva).sendKeys(timeStampnva);
				Thread.sleep(1000);
			}
			
			// enter Manual Quote Pricing Construction details
			if(!Manual_Quote_Pricing_Construction.equals("")){
				driver.findElement(By.xpath("//textarea[@ng-model='quoteForm.manualQuoteData.Construction']")).sendKeys(Manual_Quote_Pricing_Construction);
				driver.findElement(By.xpath("//textarea[@ng-model='quoteForm.manualQuoteData.Endorsement']")).sendKeys("NONEND/NONTRANS/");
			}
			
			/*List<WebElement>  carrier=driver.findElements(By.xpath("//md-input-container[label[@translate='pssgui.carrier']]/input"));
			List<WebElement>  flt_number=driver.findElements(By.xpath("//md-input-container[label[@translate='pssgui.flt.number']]/input"));
			for (int icarrier = 0; icarrier < carrier.size(); icarrier++) {
				carrier.get(icarrier).click();
				carrier.get(icarrier).sendKeys("CM");
				flt_number.get(icarrier).click();
				flt_number.get(icarrier).sendKeys("360");
			}*/
			//String manpartialAmount = convertinteger(String.valueOf(Double.parseDouble(fareAmount)/totalpax));

			//driver.findElement(By.xpath("//input[@aria-label='Fare Amount']")).sendKeys(manpartialAmount);
			//Navira - 19 Mar - Adding Manual Tax
			double iTaxAmount = 0.0;
			if(getTrimTdata(queryObjects.getTestData("ManualTax")).equalsIgnoreCase("yes")){//Column 1 - 19Mar
				String Taxes = getTrimTdata(queryObjects.getTestData("ManualTaxCode"));//Column 2 - 19Mar
				String Amount = getTrimTdata(queryObjects.getTestData("ManualTaxAmount"));//Column 3 - 19Mar
				String mTaxes[] = Taxes.split(";");
				String mAmount[] = Amount.split(";");
				for(int itera = 0;itera<mTaxes.length;itera++) {
					
					if(itera == 0) {}
						
					else {
						driver.findElement(By.xpath("//div[div[text()='Taxes']]//div[@role='button']/i[@class='icon-add']")).click();
						Thread.sleep(3000);
					}
					WebElement TaxCode = driver.findElement(By.xpath("//label[text()='Tax Code']/following-sibling::input[contains(@class,'ng-empty')]"));
					WebElement TaxAmount = driver.findElement(By.xpath("//label[text()='Tax Amount']/following-sibling::input[contains(@class,'ng-empty')]"));
					TaxCode.click();
					TaxCode.clear();
					TaxCode.sendKeys(mTaxes[itera]);
					Thread.sleep(3000);
					
					TaxAmount.click();
					TaxAmount.clear();
					TaxAmount.sendKeys(mAmount[itera]);
					Thread.sleep(3000);
					
					iTaxAmount = iTaxAmount + Double.parseDouble(mAmount[itera]);
					Thread.sleep(5000);
				}
	
			}
			if(worningCase.equalsIgnoreCase("YES"))	{   
				String fareamt=ManulaQuote_Fare_amt.split("\\s+")[0];
				String fareamtCurrency=ManulaQuote_Fare_amt.split("\\s+")[1];
				String EuivalenAmt =convertinteger(ManualQuote_Euivalent_amt.split("\\s+")[0]);

				driver.findElement(By.xpath("//input[@aria-label='Fare Amount']")).sendKeys(fareamt);  //1000
				Thread.sleep(1000);
				driver.findElement(By.xpath("//md-input-container[input[@aria-label='Fare Amount']]/following::md-input-container[1]/input")).sendKeys(fareamtCurrency);
				Thread.sleep(1000);
				driver.findElement(By.xpath("//input[@aria-label='Equiv.Fare']")).sendKeys(EuivalenAmt);
				Thread.sleep(1000);
				driver.findElement(By.xpath("//md-input-container[input[@aria-label='Equiv.Fare']]/following::md-input-container[1]/input")).sendKeys(currencyType);
				fareAmountManualQuote=fareAmountManualQuote+Double.parseDouble(EuivalenAmt)+iTaxAmount;
			}
			else{
				//String amt="2000";
				/*driver.findElement(By.xpath("//input[@aria-label='Fare Amount']")).sendKeys(amt);  //1000
				Thread.sleep(1000);
				driver.findElement(By.xpath("//md-input-container[input[@aria-label='Fare Amount']]/following::md-input-container[1]/input")).sendKeys(currencyType);
				fareAmountManualQuote=fareAmountManualQuote+Double.parseDouble(amt);*/
				
				if(currencyType.trim().equalsIgnoreCase("ARS")|| currencyType.trim().equalsIgnoreCase("COP") ){  // as per gomathi told updated
					driver.findElement(By.xpath("//input[@aria-label='Fare Amount']")).sendKeys("100");
					driver.findElement(By.xpath("//md-input-container[input[@aria-label='Fare Amount']]/following::md-input-container[1]/input")).clear();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//md-input-container[input[@aria-label='Fare Amount']]/following::md-input-container[1]/input")).sendKeys("USD");
					Thread.sleep(1000);
					driver.findElement(By.xpath("//input[@aria-label='Equiv.Fare']")).sendKeys(amt);
					Thread.sleep(1000);
					driver.findElement(By.xpath("//md-input-container[input[@aria-label='Equiv.Fare']]/following::md-input-container[1]/input")).sendKeys(currencyType);
					fareAmountManualQuote=fareAmountManualQuote+Double.parseDouble(amt)+iTaxAmount;
				}
				else{
					driver.findElement(By.xpath("//input[@aria-label='Fare Amount']")).sendKeys(amt);  //1000
					Thread.sleep(1000);
					driver.findElement(By.xpath("//md-input-container[input[@aria-label='Fare Amount']]/following::md-input-container[1]/input")).sendKeys(currencyType);
					fareAmountManualQuote=fareAmountManualQuote+Double.parseDouble(amt)+iTaxAmount;
				}
			}


			Thread.sleep(1000);

			driver.findElement(By.xpath("//button[text()='Submit']")).click();
			loadhandling(driver);
			driver.findElement(By.xpath("//md-select[@ng-model='WaiverReasonInfo.process']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//md-option[@ng-value='ProcessList'][1]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//md-select[@ng-model='WaiverReasonInfo.Reason']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//md-option[@ng-value='Reason'][1]")).click();
			loadhandling(driver);
			driver.findElement(By.xpath("//button[@aria-label='ok']")).click();
			loadhandling(driver);
		}
		//meenu : to distinguish group and normal PNR 
			if (Manual_Quote.size()>9) 
				fareAmountManualQuote=fareAmountManualQuote-Double.parseDouble(amt);
		fareAmountManualQuote=fareAmountManualQuote+Service_Fee;  // Adding total service fee amount ....
		return fareAmountManualQuote;
	}

	public static String Manual_refund(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		String Total_price="0";
		String Refund_Tax_AMT_only=queryObjects.getTestData("Refund_Tax_AMT_only");
		queryObjects.logStatus(driver, Status.PASS, "Manual Refund process started", "Manual Refund process started", null);
		driver.findElement(By.xpath("//md-select[contains(@aria-label,'Actions')]")).click();		
		Thread.sleep(2000);
		if(driver.findElement(By.xpath("//md-option[@value='voluntary-manual-refund' and (@aria-disabled='false')]")).isEnabled())
			driver.findElement(By.xpath("//md-option[@value='voluntary-manual-refund' and (@aria-disabled='false')]")).click();
		else
			queryObjects.logStatus(driver, Status.FAIL, "Manual refund link disabled", "Manual refund link disabled", null);
		for (int Nopax=1;Nopax<=FlightSearch.totalnoofPAX;Nopax++){
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath(nextbuttonXpath)).click();
			FlightSearch.loadhandling(driver);
			
			//Base_fare=String.valueOf(Integer.parseInt(Base_fare)/2);
			if(Refund_Tax_AMT_only.equalsIgnoreCase("yes")){
				System.out.println("");
				double Taxamt=0;
				List<WebElement> get_tax=driver.findElements(By.xpath("//div[contains(@pssgui-toggle,'ticketTax.toggleState')]//toggle-title/div[2]/div[1]"));
				List<WebElement> tax_charge=driver.findElements(By.xpath("//input[@name='tax refund amount']"));
				for (int Taxi = 0; Taxi < get_tax.size(); Taxi++) {
					String taxaa=get_tax.get(Taxi).getText();
					tax_charge.get(Taxi).sendKeys(taxaa);
					Taxamt=Taxamt+Double.parseDouble(taxaa);
				}
				Total_price=String.valueOf(Double.parseDouble(Total_price)+Taxamt);
			}
			else{
				String fare_Amt=driver.findElement(By.xpath("(//div[contains(@class,'pssgui-ticket-details')]//span[contains(@ng-if,'ticketTaxDetail.action')])[2]")).getText();
				driver.findElement(By.xpath("//input[@name='refund amount']")).sendKeys(fare_Amt);
				if(currencyType.trim().equalsIgnoreCase("ARS")|| currencyType.trim().equalsIgnoreCase("COP") ){
					Thread.sleep(2000);
					fare_Amt=driver.findElement(By.xpath("(//div[contains(@class,'pssgui-ticket-details')]//span[contains(@ng-if,'ticketTaxDetail.action')])[3]")).getText();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//input[@name='equivalentfare amount']")).sendKeys(fare_Amt);
				}
				FlightSearch.loadhandling(driver);
				Total_price=String.valueOf(Double.parseDouble(Total_price)+Double.parseDouble(fare_Amt));
			}
			driver.findElement(By.xpath("//button[text()='Add To Order' and not(@disabled='disabled')]")).click();
			FlightSearch.loadhandling(driver);
		}
		return Total_price;
	}
	
	// just storing Card details in Arry ...because enter card number in card deposit screen....
	public static void storecarddetails(String ccname,String ccnumber){
		
		if (ccname.equalsIgnoreCase("American_Express")) 
			ccname="American Express";   // this is as per Card Deposit screen
			
		carddetails_name.add(ccname.toUpperCase());
		carddetails_number.add(ccnumber);
	}
	
	public static void storepayAmount(String amt){
		double a=0.0;
		try{
		a=Double.parseDouble(amt);
		PayingAmount.add(a);
		}catch(Exception e){}
	}

	public static void CardDepositExecution(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException{
		//Card Deposit functionality -- applicable  book PNR in ATO / CTO ==> refund in Call center 	
		int cardnm=carddetails_name.size();
				if (Login.CardDeposit==true && cardnm > 0 && (!Login.CardDeposit_Change_Salesoffice.isEmpty())){   //if condition true execute card deposit functionality
					try{
						queryObjects.logStatus(driver, Status.PASS, "Trying to enter Card Deposit details", "Card Deposit Details", null);
						driver.findElement(By.xpath("//div[contains(@class,'card-deposit')]//child::button[text()='Next']")).click();
						loadhandling(driver);
						List<WebElement> cardtype_Depositscreen=driver.findElements(By.xpath("//div[contains(@class,'card-deposit')]//child::md-select[contains(@aria-label,'FOP Subtype')]/md-select-value/span/div"));
						List<WebElement> cardNumber_Depositscreen=driver.findElements(By.xpath("//div[contains(@class,'card-deposit')]//child::input[contains(@aria-label,'Credit Card Number')]"));
						List<WebElement> cardExpirDT_Depositscreen=driver.findElements(By.xpath("//div[contains(@class,'card-deposit')]//child::input[contains(@aria-label,'Expiry date')]"));
						List<WebElement> cardHoldername_Depositscreen=driver.findElements(By.xpath("//div[contains(@class,'card-deposit')]//child::input[contains(@aria-label,'Cardholder name')]"));
					
						//int kk=cardtype_Depositscreen.size();
						for (int i = 0; i < cardtype_Depositscreen.size(); i++) {
							String CCName=cardtype_Depositscreen.get(i).getText().toUpperCase();
							
							if (carddetails_name.contains(CCName)) {
								int a=carddetails_name.indexOf(CCName);
								String CCNumber=carddetails_number.get(a);
								cardNumber_Depositscreen.get(i).sendKeys(CCNumber);
							}
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.YEAR, 2);
							String CArd_expDate = new SimpleDateFormat("MMyyyy").format(cal.getTime());
							cardExpirDT_Depositscreen.get(i).sendKeys(CArd_expDate);
							cardHoldername_Depositscreen.get(i).sendKeys("MphasisDXC");
							
						}
						queryObjects.logStatus(driver, Status.PASS, "Card Deposit details Entered successfully", "Card Deposit Details entered", null);
						driver.findElement(By.xpath("//div[contains(@class,'card-deposit')]//child::button[text()='Submit']")).click();
						loadhandling(driver);
					}
					catch(Exception e){
						queryObjects.logStatus(driver, Status.FAIL, "Card Deposit case validation", "Card Deposit not displaying", e);
					}
					 
				}
				
	}
	public static void Residual_EMD_Validation(WebDriver driver ,BFrameworkQueryObjects queryObjects) throws IOException{

		//get EMD Numbers
		driver.findElement(By.xpath("//div[contains(text(),'EMD')]")).click();
		loadhandling(driver);
		List<WebElement> getemds=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']"));
		ArrayList Residual_getemdno = new ArrayList<>();
		getemds.forEach(a -> Residual_getemdno.add(a.getText().trim()));
		//get EMD amount
		List<WebElement> getemdsamt=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']//preceding-sibling::div[1]"));
		int EMD_count=0;
		Boolean REs_EMD_OPEn=false;
		for (int iEMD = 0; iEMD < getemds.size(); iEMD++) {
			getemds=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']"));
			getemdsamt=driver.findElements(By.xpath("//div[@class='pssgui-link padding-left ng-binding']//preceding-sibling::div[1]"));
			
			String residualEMD=getemdsamt.get(iEMD).getText().split("\\s+")[0];
			double cResidualEmd=Double.parseDouble(residualEMD);
			String ResEMDno=getemds.get(iEMD).getText();
			System.out.println("EMD no: "+ResEMDno);
			getemds.get(iEMD).click();
			loadhandling(driver);
			String EMD_Status=driver.findElement(By.xpath("//div[@model='emdDetail.model']//child::tbody/tr/td[7]/div")).getText().trim();
			//String EMD_Status=driver.findElement(By.xpath("//div[@model='emdDetail.model']//child::tbody/tr/td[9]/div")).getText().trim();			
		//	String EMD_Status=driver.findElement(By.xpath("//td[text()='99I']/following-sibling::td[2]/div")).getText().trim();
			if (cResidualEmd==TotalRefundamt) {
				EMD_count=EMD_count+1;
				if(EMD_Status.equalsIgnoreCase("Open")){
					queryObjects.logStatus(driver, Status.PASS, "Residual EMD case RESIDUAL VALUE(Total Refund Amount) ticket status checking", "it is Displaying correctly (OPEN) EMD no:"+ResEMDno , null);
					REs_EMD_OPEn=true;
					//String semdnoTemp = Login.envRead(Login.EMDNO);
					String semdnoTemp = Login.EMDNO;
					
					semdnoTemp=semdnoTemp.trim();
					
					if(!semdnoTemp.isEmpty())  // storing for multiple EMD number in ENv sheet 
						Login.EMDNO= semdnoTemp+";"+ResEMDno;
					else
						Login.EMDNO= ResEMDno;
					
					/*if(!semdnoTemp.isEmpty())  // storing for multiple EMD number in ENv sheet 
						Login.envwrite(Login.EMDNO, semdnoTemp+";"+ResEMDno);
					else
						Login.envwrite(Login.EMDNO, ResEMDno);*/
				}
				else
					queryObjects.logStatus(driver, Status.FAIL, "Residual EMD case RESIDUAL VALUE(Total Refund Amount) ticket status checking", "EMD ticket status should display OPEN EMD no:"+ResEMDno , null);
			}
			else if (cResidualEmd==PanaltyAmount) {
				EMD_count=EMD_count+1;
				if(EMD_Status.equalsIgnoreCase("Flown"))
					queryObjects.logStatus(driver, Status.PASS, "Residual EMD case PENALTY FEE(PENALTY  Amount) ticket status checking", "it is Displaying correctly (Flown) EMD no:"+ResEMDno , null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Residual EMD case PENALTY FEE(PENALTY Amount) ticket status checking", "EMD ticket status should display FLOWN EMD no:"+ResEMDno , null);
			}
			else if (cResidualEmd==Fare_Diff) {
				EMD_count=EMD_count+1;
				if(EMD_Status.equalsIgnoreCase("Exchanged"))
					queryObjects.logStatus(driver, Status.PASS, "Residual EMD case RESIDUAL VALUE(Fare Diff) ticket status checking", "it is Displaying correctly (Exchanged) EMD no:"+ResEMDno , null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Residual EMD case RESIDUAL VALUE(Fare Diff) ticket status checking", "EMD ticket status should display EXCHANGED EMD no:"+ResEMDno , null);
			}
			driver.findElement(By.xpath("//span[@translate='pssgui.all.passengers']")).click();
			loadhandling(driver);
			
		}
		if(EMD_count<3)
			queryObjects.logStatus(driver, Status.PASS,"EMD tab EMD validation","it is displaying correcnty",null);
		else
			queryObjects.logStatus(driver, Status.FAIL,"EMD tab EMD validation","it is should display correcnty",null);	
		if(REs_EMD_OPEn==false)
			queryObjects.logStatus(driver, Status.FAIL,"REsidual EMD Checking","Residual EMD(Total Refund Amount) should OPEN State..",null);
	
	}



public static void waivermethod(WebDriver driver, BFrameworkQueryObjects queryObjects, String wave, String Waiver_Fee, String withoutPnr_paxnm) throws Exception{
	
	//String wave=getTrimTdata(queryObjects.getTestData("WaiverPassenger"));
			String Paxtypes="";
			if(!wave.isEmpty() && withoutPnr_paxnm.isEmpty())
				Paxtypes = getPassengerdetails(driver);
			if(!withoutPnr_paxnm.isEmpty())
				Paxtypes=withoutPnr_paxnm;
			queryObjects.logStatus(driver, Status.PASS, "Waiver functionality started ", "Waiver--> Removing service fee and try to pay with out service" , null);
			try {
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
				FlightSearch.loadhandling(driver);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				if (wave.equalsIgnoreCase("infant") ) {
					String[] arrayPassenger = Paxtypes.split(";");
		
					for (int itr=0; itr<=arrayPassenger.length-1; itr++) {
		
						String[] aTemp = arrayPassenger[itr].split("-");
						String sPassengerName = aTemp[0];
						String sPassengerType = aTemp[1];
		
						if (sPassengerType.contains("Infant")) {
							String XpathServiceFee =	"//div[normalize-space(text())='"+sPassengerName+"']//ancestor::div[@class='pax-info-result ng-scope']//child::";
							//String Servicefee = driver.findElement(By.xpath(XpathServiceFee+"div[contains(@ng-if,'editFee')]/div/div")).getText();
							//Servicefee = Servicefee.split("\\s+")[0];
							Double val=0.0;
							String taxinfo=null;
							try{
								// This is Service fee waiver 	
							//String taxinfo=driver.findElement(By.xpath(XpathServiceFee+"div[contains(@ng-repeat,'Taxinfo')]/div/div/div[2]/div[2]")).getText();
								taxinfo=driver.findElement(By.xpath(XpathServiceFee+"div[contains(@ng-repeat,'fee') or  contains(@ng-repeat,'Taxinfo') ]/div/div/div[2]/div/div")).getText();
								queryObjects.logStatus(driver, Status.PASS, "Service fee Waiver", "Service fee Waiver", null);
							}
							catch(Exception e){
								List<WebElement> servicefeewave=driver.findElements(By.xpath(XpathServiceFee+"div[contains(@model,'service')]/div"));
								taxinfo=servicefeewave.get(0).getText();   // get First Serviec waiver 
								queryObjects.logStatus(driver, Status.PASS, "Service fee Waiver", "Servic Waiver", null);
							}
							val =Double.parseDouble(taxinfo.split("\\s+")[0]);
							
							//driver.findElement(By.xpath(XpathServiceFee+"i[@ng-click='quoteDetail.editService(fee)']")).click();
							driver.findElement(By.xpath(XpathServiceFee+"i[contains(@ng-click,'quoteDetail.editService')]")).click();
							loadhandling(driver);
							//String Waiver_Fee=queryObjects.getTestData("Waiver_Fee");
							//driver.findElement(By.xpath(XpathServiceFee+"input[@aria-label='service fee']")).clear();
							driver.findElement(By.xpath(XpathServiceFee+"input[contains(@aria-label,'service')]")).clear();   // it is clearing the tax
							Thread.sleep(1000);
							driver.findElement(By.xpath(XpathServiceFee+"input[contains(@aria-label,'service')]")).sendKeys(Waiver_Fee);
							loadhandling(driver);
							driver.findElement(By.xpath(XpathServiceFee+"input[contains(@aria-label,'service')]")).clear();   // it is clearing the tax
							Thread.sleep(1000);
							driver.findElement(By.xpath(XpathServiceFee+"input[contains(@aria-label,'service')]")).sendKeys(Waiver_Fee);
							loadhandling(driver);
							//clicking the first element
		
							String Xpathwaiverdropdown ="//toggle-title/div[text()='"+sPassengerName+"']//ancestor::div[@ng-if='WaiverReasonInfo']//child::";
							driver.findElement(By.xpath(Xpathwaiverdropdown+"md-select[@ng-model='WaiverReasonInfo.process']")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath("//div[contains(@class,'md-active')]//md-option[@ng-value='ProcessList']/div[1]")).click();
		
							driver.findElement(By.xpath(Xpathwaiverdropdown+"md-select[@ng-model='WaiverReasonInfo.Reason']")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath("//div[contains(@class,'md-active')]//md-option[@ng-repeat='Reason in WaiverReasonInfo.process.Reason']/div[1]")).click();
							Thread.sleep(1000);
							double wave_fee=Double.parseDouble(Waiver_Fee);
							String afterwaive=driver.findElement(By.xpath("//div[@model='paymentCtrl.model.payment.total']/div")).getText();
                            Waiverfare = afterwaive.split(" ");
                            AfterWaiver = Double.parseDouble(Waiverfare[0]);

							fareAmount = String.valueOf(Double.parseDouble(fareAmount)-val);
							fareAmount = String.valueOf(Double.parseDouble(fareAmount)+wave_fee);

						}
		
					}
		
				}
				else if (wave.equalsIgnoreCase("all") ) {
					String[] arrayPassenger = Paxtypes.split(";");
		
					for (int itr=0; itr<arrayPassenger.length; itr++) {
						String[] aTemp = arrayPassenger[itr].split("-");
						String sPassengerName = aTemp[0];
						//String sPassengerType = aTemp[1];
						String XpathServiceFee =	"//div[normalize-space(text())='"+sPassengerName+"']//ancestor::div[@class='pax-info-result ng-scope']//child::";
						//String Servicefee = driver.findElement(By.xpath(XpathServiceFee+"div[contains(@ng-if,'editFee')]/div/div")).getText();
						//Servicefee = Servicefee.split("\\s+")[0];
						Double val=0.0;
						String taxinfo=null;
						try{
							// This is Service fee waiver 	
						//String taxinfo=driver.findElement(By.xpath(XpathServiceFee+"div[contains(@ng-repeat,'Taxinfo')]/div/div/div[2]/div[2]")).getText();
							taxinfo=driver.findElement(By.xpath(XpathServiceFee+"div[contains(@ng-repeat,'fee') or  contains(@ng-repeat,'Taxinfo') ]/div/div/div[2]/div/div")).getText();
							queryObjects.logStatus(driver, Status.PASS, "Service fee Waiver", "Service fee Waiver", null);
						}
						catch(Exception e){
							if (! Login.SalesOff.contains("BOG")){  // if othr than bog this will work
								List<WebElement> servicefeewave=driver.findElements(By.xpath(XpathServiceFee+"div[contains(@model,'service') or contains(@model,'fee') ]/div"));
								if(servicefeewave.size()>0){
									taxinfo=servicefeewave.get(0).getText();   // get First Serviec waiver 
									queryObjects.logStatus(driver, Status.PASS, "Service fee Waiver", "Servic Waiver", null);
								}
								else
									queryObjects.logStatus(driver, Status.WARNING, "Service fee/ Service  Waiver xpath changed", "Waiver Xpath need to check", null);
							}
							else{   // if bog pos this will work
								List<WebElement> servicefeewave=driver.findElements(By.xpath(XpathServiceFee+"div[contains(@model,'service') or contains(@model,'fee') ]/div[2]"));
								if(servicefeewave.size()==0)
									servicefeewave=driver.findElements(By.xpath(XpathServiceFee+"div[contains(@model,'service') or contains(@model,'fee') ]/div"));
								if(servicefeewave.size()>0){
									taxinfo=servicefeewave.get(0).getText();   // get First Serviec waiver 
									queryObjects.logStatus(driver, Status.PASS, "Service fee Waiver", "Servic Waiver", null);
								}
								else
									queryObjects.logStatus(driver, Status.WARNING, "Service fee/ Service  Waiver xpath changed", "Waiver Xpath need to check", null);
							}
								
							
						}
						val =Double.parseDouble(taxinfo.split("\\s+")[0]);
						//driver.findElement(By.xpath(XpathServiceFee+"i[@ng-click='quoteDetail.editService(fee)']")).click();
						driver.findElement(By.xpath(XpathServiceFee+"i[contains(@ng-click,'quoteDetail.editService')]")).click();
						
						loadhandling(driver);
						//String Waiver_Fee=queryObjects.getTestData("Waiver_Fee");
						//driver.findElement(By.xpath(XpathServiceFee+"input[@aria-label='service fee']")).clear();   // it is clearing the tax
						driver.findElement(By.xpath(XpathServiceFee+"input[contains(@aria-label,'service')]")).clear();   // it is clearing the tax
						Thread.sleep(1000);
						driver.findElement(By.xpath(XpathServiceFee+"input[contains(@aria-label,'service')]")).sendKeys(Waiver_Fee);
						loadhandling(driver);
						driver.findElement(By.xpath(XpathServiceFee+"input[contains(@aria-label,'service')]")).clear();   // it is clearing the tax
						Thread.sleep(1000);
						driver.findElement(By.xpath(XpathServiceFee+"input[contains(@aria-label,'service')]")).sendKeys(Waiver_Fee);
						loadhandling(driver);
						//clicking the first element
		
						String Xpathwaiverdropdown ="//toggle-title/div[text()='"+sPassengerName+"']//ancestor::div[@ng-if='WaiverReasonInfo']//child::";
						driver.findElement(By.xpath(Xpathwaiverdropdown+"md-select[@ng-model='WaiverReasonInfo.process']")).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath("//div[contains(@class,'md-active')]//md-option[@ng-value='ProcessList']/div[1]")).click();
		
						driver.findElement(By.xpath(Xpathwaiverdropdown+"md-select[@ng-model='WaiverReasonInfo.Reason']")).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath("//div[contains(@class,'md-active')]//md-option[@ng-repeat='Reason in WaiverReasonInfo.process.Reason']/div[1]")).click();
						Thread.sleep(1000);
						double wave_fee=Double.parseDouble(Waiver_Fee);
						String afterwaive=driver.findElement(By.xpath("//div[@model='paymentCtrl.model.payment.total']/div")).getText();
                        Waiverfare = afterwaive.split(" ");
                        AfterWaiver = Double.parseDouble(Waiverfare[0]);

						fareAmount = String.valueOf(Double.parseDouble(fareAmount)-val);
						fareAmount = String.valueOf(Double.parseDouble(fareAmount)+wave_fee);
		
					}
				}
				driver.findElement(By.xpath("//button[text()='Next']")).click();
				queryObjects.logStatus(driver, Status.PASS, "Waiver", "Waiver done", null);
				loadhandling(driver);
		
			}
			catch(Exception e){
				queryObjects.logStatus(driver, Status.FAIL, "Waiver ", "Waiver Unsuccessful: " + e.getMessage() , e);
			}
}
	
	public static boolean paymentvalidation(WebDriver driver, BFrameworkQueryObjects queryObjects,String Review_Reject_cases) throws IOException{
		boolean PymtError_Msg=false;
		EMDCreated = false;
		pnrcreated=false;
		String Errormsg="";
		try {
			//String Review_Reject_cases=getTrimTdata(queryObjects.getTestData("Review_Reject_cases"));
			String Actwarningmsg="";
			if (!Review_Reject_cases.isEmpty()) {
				try{
					Thread.sleep(1500);
					Actwarningmsg=driver.findElement(By.xpath("//div[contains(@class,'md-dialog-content-body')]")).getText().trim();
				}
				catch(Exception e){
					queryObjects.logStatus(driver, Status.FAIL,"PPS Review/Reject  case ","Xpath change",null);
				}
				if (Review_Reject_cases.equalsIgnoreCase("REVIEW")){
					if(Actwarningmsg.contains("480")) 
						queryObjects.logStatus(driver, Status.PASS, "PPS Review case warning message validation", "Review case message displaying correctly", null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "PPS Review case warning message validation", "Review case message displaying wrong :: "+Actwarningmsg, null);
				}
				else if(Review_Reject_cases.equalsIgnoreCase("REJECT") && Actwarningmsg.contains("481")){ 
					if(Actwarningmsg.contains("481")) 
						queryObjects.logStatus(driver, Status.PASS, "PPS Reject case warning message validation", "Reject case message displaying correctly", null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "PPS Reject case warning message validation", "Reject case message displaying wrong :: "+Actwarningmsg, null);
				}
				else
					queryObjects.logStatus(driver, Status.FAIL, "REVIEW/REJECT case", "REVIEW/REJECT case should display message :: "+Actwarningmsg, null);

			}
			String Expected_erromsg=queryObjects.getTestData("Expected_erromsg");   // this is for if we give Wrong Error message checking functionality
			//if(Expected_erromsg.equalsIgnoreCase("invalid_Email")){	
			//if(Expected_erromsg.equalsIgnoreCase("invalid_Email")||Expected_erromsg.equalsIgnoreCase("Non-Carrier(OA) Flights are not supported to issue an EMD")){
			if(Expected_erromsg.equalsIgnoreCase("invalid_Email")||Expected_erromsg.equalsIgnoreCase("Non-Carrier(OA) Flights are not supported to issue an EMD")||Expected_erromsg.equalsIgnoreCase("(1) INVALID CREDIT CARD NUMBER: 10027")||Expected_erromsg.equalsIgnoreCase("Merchant ID for payment")){//Navira - 19Mar
	
				String error_count_xpath="//span[contains(@class,'error-count')]";
				if(driver.findElements(By.xpath(error_count_xpath)).size()>0){
					//String getMssg=driver.findElement(By.xpath("//span[contains(@class,'error-count')]//preceding-sibling::span")).getText().trim();
					boolean getMssg=false;
					driver.findElement(By.xpath("//span[contains(@class,'error-count')]//preceding-sibling::span")).click();
					Thread.sleep(2000);
					List<WebElement> GetError_msg = driver.findElements(By.xpath("//md-menu-content[@class='error-panel-menu']/md-menu-item/div/div[contains(@class,'msg-error') or contains(@class,'msg-alert')]"));
					 for (int iErro =0 ; iErro < GetError_msg.size(); iErro++) { 
						 String sError_msg=GetError_msg.get(iErro).getText().trim();
						 if(sError_msg.contains("102 - One or more fields in the request contains invalid data. c:billTo/c:email for which values are invalid")){
							 Errormsg="102 - One or more fields in the request contains invalid data. c:billTo/c:email for which values are invalid";
							 getMssg=true;	 
						 }
						 if(sError_msg.contains("Non-Carrier(OA) Flights are not supported to issue an EMD")){
							 Errormsg="Non-Carrier(OA) Flights are not supported to issue an EMD";	
							 getMssg=true;
						 }
						//Navira - 05Mar
						 else if(sError_msg.contains("(1) INVALID CREDIT CARD NUMBER")){
							 Errormsg="(1) INVALID CREDIT CARD NUMBER";	
							 getMssg=true;
						 }
						//Navira - 05Mar
						 else if(sError_msg.contains("Merchant ID for payment")){
							 Errormsg="Merchant ID for payment";	
						 }
					 }			 
					 
					Thread.sleep(3000);
					WebElement email=	driver.findElement(By.xpath("//button[text()='Done']"));
					Actions actions = new Actions(driver);
			        actions.moveToElement(email);
			        actions.click();
			        actions.build().perform();
			      //Navira - Handling clicking on Done button -18Mar
			        //driver.findElement(By.xpath("//button[text()='Cancel']")).click();
			        
			        if(driver.findElements(By.xpath("//button[text()='Cancel' and not(@disabled='disabled')]")).size()>0)
			        	driver.findElement(By.xpath("//button[text()='Cancel']")).click();
			        else
			        	driver.findElement(By.xpath("//button[text()='Done']")).click();
			        loadhandling(driver);
			        PymtError_Msg = true;
			        /*if(Errormsg.equalsIgnoreCase("Non-Carrier(OA) Flights are not supported to issue an EMD"))
						 return PymtError_Msg;
			        else {*/
			        if(getMssg)
			        	 queryObjects.logStatus(driver, Status.PASS, "Input Email/ Non-Carrier(OA) Flights Checking", "Error: "+Errormsg +" Found", null);
			        else if(Errormsg.equalsIgnoreCase("Merchant ID for payment"))
				    	queryObjects.logStatus(driver, Status.WARNING, "Credit Card/ Currency not tagged to the POS", "Error: "+Errormsg +" Found", null);
			        else
				       	queryObjects.logStatus(driver, Status.FAIL, "Input Email/ Non-Carrier(OA) Flights Checking", "Error: "+Errormsg +" NOT Found", null);
				        
				        	/*queryObjects.logStatus(driver, Status.PASS, "While payment Given Wrong email id Error message checking", "If we given wrong Email id proper Error message displaying", null);
				        else
				        	queryObjects.logStatus(driver, Status.FAIL, "While payment Given Wrong email id Error message checking", "If we given wrong Email id it should be display Error message like  102 - One or more fields in the request contains invalid data", null);
				       
			        }*/
				}
				else
					queryObjects.logStatus(driver, Status.FAIL, "While payment Given Wrong email id/other fliths(OA) Error message checking", "If we given wrong Email id it should be display Error message like   102/ Non-Carrier(OA)", null);
			}
			else {
				driver.findElement(By.xpath("//md-dialog-actions/button[contains(text(), 'Ok')]")).click();
				loadhandling(driver);
				PymtError_Msg = true;
				Boolean Cancelbtn = driver.findElement(By.xpath("//button[text()='Cancel']")).isEnabled();
				if (Cancelbtn) {
					driver.findElement(By.xpath("//button[text()='Cancel']")).click();	
					loadhandling(driver);
				} else {
					Boolean Donebtn = driver.findElement(By.xpath("//button[text()='Done']")).isEnabled();
					if (Donebtn) {
						//Check for Payment successful message if done is enabled
						WebDriverWait wait = new WebDriverWait(driver, 120);   
						wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
						String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
						queryObjects.logStatus(driver, Status.PASS, "Payment", statusMessage, null);
						if(driver.findElements(By.xpath("//span[contains(text(),'EMD')]")).size()>0) {
							EMDCreated = true;
						}
						
						//Click on done button
						driver.findElement(By.xpath("//button[text()='Done']")).click();
						loadhandling(driver);
					}

				}
				pnrcreated=true;
			}

		} catch (Exception e) {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
			String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
			if(driver.findElements(By.xpath("//span[contains(text(),'EMD')]")).size()>0) {
				EMDCreated = true;
			}
			queryObjects.logStatus(driver, Status.PASS, "Payment", statusMessage, null);
			pnrcreated=true;	
		}
		return PymtError_Msg;
	}
	
	//Discount(driver,queryObjects,false,false)
	public void Discount(WebDriver driver, BFrameworkQueryObjects queryObjects,Boolean BeforeCreatePNR,String discountType,String dispax, String segments,String discValue,String valueType,String discTax,String ticketDesignator ) throws NumberFormatException, IOException, InterruptedException, Exception{
		//NS
			driver.findElement(By.xpath("//div[toggle-title[span[text()='Discounts']]]/preceding-sibling::i")).click();
			if(BeforeCreatePNR) {
			
				if(discountType.equalsIgnoreCase("Passenger")) {//3
					driver.findElement(By.xpath("//div[span[text()='Passenger']]/preceding-sibling::div")).click();
					//Need to write selecting passengers

					String paxcount[] = dispax.split(";");
					List<WebElement> paxcheck = driver.findElements(By.xpath("//div/div/md-checkbox"));
					if(paxcount.length==paxcheck.size()) {
						for(int iter1=0; iter1<paxcheck.size();iter1++) {
							if(paxcount[iter1].equalsIgnoreCase("1"))
								paxcheck.get(iter1).click();
						}
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL, "Values for Passengers", "Enter values for all passengers", null);
					}
					
				}
				else if(discountType.equalsIgnoreCase("Segment")) {//5
						
						driver.findElement(By.xpath("//div[span[text()='Segment']]/preceding-sibling::div")).click();
					
					
					if(segments.equalsIgnoreCase("all")) {
						driver.findElement(By.xpath("//div[2]//th/md-checkbox")).click();
					}
					else {
						int numofSeg = Integer.parseInt(segments);
						//driver.findElement(By.xpath("//div[2]//tr["+(numofSeg)+"]//span/md-checkbox")).click();
						driver.findElement(By.xpath("//div[2]//tr["+(numofSeg)+"]//span/md-checkbox[contains(@ng-if,'discounts')]")).click(); //atul
						/*for(int iter2=0; iter2<numofSeg; iter2++) {
							driver.findElement(By.xpath("//div[2]//tr["+(iter2+1)+"]//span/md-checkbox")).click();
						}*/
					}
				}
				
			}
			else {

				if(segments.equalsIgnoreCase("all")) {
					driver.findElement(By.xpath("//div[2]//th/md-checkbox")).click();
				}
				else {
					int numofSeg = Integer.parseInt(segments);
					driver.findElement(By.xpath("//div[2]//tr[contains(@ng-repeat,'flight')]["+numofSeg+"]//span/md-checkbox")).click();
					/*for(int iter2=0; iter2<numofSeg; iter2++) {
						driver.findElement(By.xpath("//div[2]//tr["+(iter2+1)+"]//span/md-checkbox")).click();
					}*/
				}
			}				
	
			
			driver.findElement(By.xpath("//label[text()='Value']/following-sibling::input")).clear();
			driver.findElement(By.xpath("//label[text()='Value']/following-sibling::input")).click();
			driver.findElement(By.xpath("//label[text()='Value']/following-sibling::input")).sendKeys(discValue);
			
			driver.findElement(By.xpath("//label[text()='Value Type']/following-sibling::md-select")).click();
			Thread.sleep(2000);
			valueType=valueType.toUpperCase();
			if(valueType.equalsIgnoreCase("AMOUNT"))
				driver.findElement(By.xpath("//div[contains(text(),'Amount')]")).click();
			else if(valueType.equalsIgnoreCase("PERCENTAGE"))
				driver.findElement(By.xpath("//div[contains(text(),'Percentage')]")).click();
			Thread.sleep(2000);
			
			driver.findElement(By.xpath("//label[text()='Taxes']/following-sibling::md-select")).click();
			Thread.sleep(2000);
			discTax=discTax.toUpperCase();
			if(discTax.equalsIgnoreCase("AFTER"))
				driver.findElement(By.xpath("//md-option[@value='PostTax']")).click();
			else if(discTax.equalsIgnoreCase("BEFORE"))
				driver.findElement(By.xpath("//md-option[@value='PreTax']")).click();
			Thread.sleep(2000);
			
			driver.findElement(By.xpath("//label[text()='Ticket Designator']/following-sibling::input")).clear();
			driver.findElement(By.xpath("//label[text()='Ticket Designator']/following-sibling::input")).click();
			driver.findElement(By.xpath("//label[text()='Ticket Designator']/following-sibling::input")).sendKeys(ticketDesignator);							
		
	}

	public static void Reissue_Residual(WebDriver driver, BFrameworkQueryObjects queryObjects, String Residual_EMD) throws IOException, Exception{
		if (Residual_EMD.contains("Reissue")) {
			driver.findElement(By.xpath("//div[text()='Order']/..")).click();
			//driver.findElement(By.xpath("(//md-checkbox[@ng-model='flight.isChecked'])[1]")).click();//Single Segment
			driver.findElement(By.xpath("(//md-checkbox[@ng-model='flight.isChecked'])[2]")).click();//Single Segment
			driver.findElement(By.xpath("//md-select[contains(@aria-label,'Actions')]")).click();		
			Thread.sleep(1000);
			driver.findElement(By.xpath("//md-option[div[contains(text(),'Voluntary Reissue')]]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(nextbuttonXpath)).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//button[text()='Add To Order']")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
			Thread.sleep(1000);
			
			if (driver.findElement(By.xpath("//span[contains(text(),'Residual EMD')]")).isDisplayed()) {									
				driver.findElement(By.xpath("//button[contains(text(),'Reissue')]")).click();
				ResidualAmt = driver.findElement(By.xpath("//label[contains(@translate,'pssgui.payment.amount')]/../input")).getAttribute("value");
				ResidualAmt = ResidualAmt.replace("(", "").replace(")", "");
				if (Integer.parseInt(Residual_EMD.replace("Reissue-", ""))>1) {
					ResidualAmt = Double.parseDouble(ResidualAmt)/Integer.parseInt(Residual_EMD.replace("Reissue-", ""))+"";										
				}
				driver.findElement(By.xpath("//button[text()='Pay' and not(@disabled='disabled')]")).click();
				FlightSearch.enterFoiddetails(driver, queryObjects);
				FlightSearch.emailhandling(driver,queryObjects);
				for (int ge = 1; ge <= Integer.parseInt(Residual_EMD.replace("Reissue-", "")); ge++) {
					if (ResidualEMD.isEmpty()) {
						ResidualEMD = driver.findElement(By.xpath("(//span[@translate='pssgui.emd']/following-sibling::span)["+ge+"]")).getText();
					} else {
						ResidualAmt = ResidualAmt+";"+ResidualAmt;
						ResidualEMD = ResidualEMD+";"+driver.findElement(By.xpath("(//span[@translate='pssgui.emd']/following-sibling::span)["+ge+"]")).getText();
					}										
				}									
				//Clicking on Done button
				driver.findElement(By.xpath("//button[text()='Done']")).click();
				Thread.sleep(200);									
			}		
			
		}
	}
	
	public void SSRComets_Update(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{
		// Himani 28Jan
			// For added SSR, update the added explanation and update
			// Validate the AVSC details - check if SSR name is present in Additional Service
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("UpdateSSRcomment")).equalsIgnoreCase("yes")) {
				if(!ssrCode.equals("NULL")){
					driver.findElement(By.xpath("//div[div[text()='Services']]")).click();
					loadhandling(driver);				
					for(int ipax = 1; ipax<=totalnoofPAX; ipax++) {
						driver.findElement(By.xpath("//i[contains(@class,'toggle-arrow ng-scope icon-forward')]")).click();
						Thread.sleep(2000);
						if(driver.findElements(By.xpath("//div[@ng-transclude = 'content']//div[contains(text(),'"+ssrCode+"')]")).size()>0) {
						driver.findElement(By.xpath("//div[div[div[div[div[div[div[div[contains(text(),'"+ssrCode+"')]]]]]]]]/div[1]//div[contains(@role,'button')]")).click();
						List<WebElement> noofcom = driver.findElements(By.xpath("//md-input-container//input[contains(@aria-label,\"Extra text\")]"));
								
						for(int comment = 0; comment<noofcom.size();comment++) {
							try {
								noofcom.get(comment).click();
								noofcom.get(comment).clear();
								noofcom.get(comment).sendKeys("UPDATED SSR EXPLANATION");	
								queryObjects.logStatus(driver, Status.PASS, "Updating SSR comment", "Update successful", null);
								
							}
							catch(Exception e)
							{
								queryObjects.logStatus(driver, Status.WARNING, "Updating SSR comment", "Update Unsuccessful ", e);
							}
						}
						loadhandling(driver);
						driver.findElement(By.xpath("//button[contains(text(),'Update')]")).click();
						loadhandling(driver);
						driver.findElement(By.xpath("//div[contains(@role,\"button\") and contains(text(),'ADDITIONAL SERVICE')]")).click();
						loadhandling(driver);
						List<WebElement> Thecomments = driver.findElements(By.xpath("//md-input-container//input[contains(@aria-label,\"Extra text\")]"));
						
						for(WebElement getcomment:Thecomments) {
							if(getcomment.getText().equals(Servicename)){
								queryObjects.logStatus(driver, Status.PASS, "Validating ASVC", "Validation successful "+Servicename+" Present", null);
							}
						}	
						loadhandling(driver);
						driver.findElement(By.xpath("//div[contains(@ng-click,\"dlgCtrl.closeDialog()\")]")).click();
						
					}
					Thread.sleep(2000);
				}
				
			}
		}
						
					//Update ssr comment code ends here
	}
	//Start - Navira
		public static void bulkFare(WebDriver driver, BFrameworkQueryObjects queryObjects, String typeofTransfer) throws Exception{
			try {
				if(driver.findElements(By.xpath("//md-radio-button/div[contains(text(),'"+typeofTransfer+"')]/preceding-sibling::div")).size()>0) {
					driver.findElement(By.xpath("//md-radio-button/div[contains(text(),'"+typeofTransfer+"')]/preceding-sibling::div")).click();
					Thread.sleep(5000);
					queryObjects.logStatus(driver, Status.PASS, "BT (Bulk Order) radio button must be clicked", "Bulk Order option selected", null);
				}
			}
			catch(Exception e) {
				queryObjects.logStatus(driver, Status.PASS, "BT (Bulk Order) radio button must be clicked", "Bulk Order option selected", null);
				
			}
		}
		public void resConfirmationwopayment(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {
			driver.findElement(By.xpath("//div[text()='Summary']")).click();
			loadhandling(driver);
			if(driver.findElements(By.xpath("//md-checkbox[div[span[text()='Confirm PAX Advsd']]]")).size()>0)
			{
				queryObjects.logStatus(driver, Status.INFO, "Checking Confirmation Check Box", "Confirmation Check Box present", null);
				driver.findElement(By.xpath("//md-checkbox[div[span[text()='Confirm PAX Advsd']]]/div[1]")).click();
				if(driver.findElements(By.xpath("//button[text()='Email Itinerary' and @disabled='disabled']")).size()>0) 
					queryObjects.logStatus(driver, Status.FAIL, "Checking if Email tab is enabled", "Email Itinerary Tab is disabled", null);
				else if(driver.findElement(By.xpath("//button[text()='Email Itinerary']")).isEnabled())
				{
					queryObjects.logStatus(driver, Status.PASS, "Checking if Email tab is enabled", "Email Itinerary Tab is enabled", null);
					driver.findElement(By.xpath("//button[text()='Email Itinerary']")).click();
					loadhandling(driver);
					emailhandling(driver, queryObjects);
					boolean errmessage = driver.findElements(By.xpath("//span[text()='E-Mail has been sent.']")).size()>0;
					if(errmessage)
						queryObjects.logStatus(driver, Status.PASS, "Verifying if the confirmation email is sent", "Confirmation mail sent successfully", null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "Verifying if the confirmation email is sent", "Confirmation mail is not sent", null);

				}
			}
			//20Mar - Navira
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//div[div[text()='Order']]")).click();
			FlightSearch.loadhandling(driver);
		}
		
		//End - Navira
		public static String envRead_PremSeats(int row,int column) throws IOException{

	        String FilePath = url_path.pEnvExcelPath;

	        FileInputStream input=new FileInputStream(FilePath);
	        XSSFWorkbook wb=new XSSFWorkbook(input);
	        XSSFSheet sh=wb.getSheet("SeatPricing");
	        XSSFCell c=sh.getRow(row).getCell(column);
	        DataFormatter format = new DataFormatter();

	        input.close();
	        return format.formatCellValue(c);

	 }
		
		public static void ARNK(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException, Exception{
			//call ARNK function written by Rushil
			String ARNK_Before=FlightSearch.getTrimTdata(queryObjects.getTestData("ARNK Before"));
			String ARNK_After=FlightSearch.getTrimTdata(queryObjects.getTestData("ARNK After"));
			String ARNK_Segment=FlightSearch.getTrimTdata(queryObjects.getTestData("ARNK Segment"));
			String ARNK_pricebestBuy=FlightSearch.getTrimTdata(queryObjects.getTestData("ARNK PricebestBuy"));
			String ARNK_quote = FlightSearch.getTrimTdata(queryObjects.getTestData("ARNK QUOTE"));
			if(ARNK_Before.equalsIgnoreCase("yes")||ARNK_After.equalsIgnoreCase("yes")) {
				if(ARNK_Segment!= null) {
					List<WebElement> no=driver.findElements(By.xpath("//tbody[@ng-repeat='flight in flightResult.segments']"));
					int	Segmt=no.size();
					String tempXpath = "//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]][1]";
					//CouponCtrl_Seg.substring(0, CouponCtrl_Seg.indexOf("-"))
					//CouponCtrl_Seg.substring(CouponCtrl_Seg.indexOf("-")+1, CouponCtrl_Seg.length())
					//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]][1]//div[contains(@airport-code,'.origin')]
					//Himani 24April
					List <WebElement> li= driver.findElements(By.xpath("//md-checkbox[contains(@ng-model,'flight.isChecked')]"));
					for(int iSegmt=1;iSegmt<=Segmt;iSegmt++) {
						if(driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]]["+iSegmt+"]//div[contains(@airport-code,'.origin')]")).getText().contains(ARNK_Segment.substring(0, ARNK_Segment.indexOf("-")))) {
							if(driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]]["+iSegmt+"]//div[contains(@airport-code,'.destination')]")).getText().contains(ARNK_Segment.substring(ARNK_Segment.indexOf("-")+1, ARNK_Segment.length()))) {
								//driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult')]//tbody["+iSegmt+"]//td[@class='checkbox']//div[contains(@class,'md-container')]")).click();
								li.get(iSegmt-1).click();
								break;
							}
						}
					}
					//Select Actions Dropdown
					driver.findElement(By.xpath("//md-select[contains(@aria-label,'Actions')]")).click();		
					Thread.sleep(2000);
					if(ARNK_Before.equalsIgnoreCase("yes")) {
						driver.findElement(By.xpath("//md-option[div[contains(text(),'ARNK Before')]]")).click();
						FlightSearch.loadhandling(driver);
						queryObjects.logStatus(driver, Status.PASS, "ARNK Before", "ARNK Before Successful",null);
						if(!ARNK_quote.equalsIgnoreCase("no")) {
							driver.findElement(By.xpath("//md-select[@placeholder='Actions']")).click();
							Thread.sleep(1000);
							loadhandling(driver);
							driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false']//md-option[div[contains(text(),'Quote')]]")).click();
	
							//Selecting Coupon check box if it is not checked already
							WebElement checkBox = driver.findElement(By.xpath("//md-checkbox[@aria-label='coupon-check']"));
							if(checkBox.getAttribute("aria-checked").equalsIgnoreCase("false"))
								checkBox.click();
							if(!ARNK_pricebestBuy.equalsIgnoreCase("")) {
								driver.findElement(By.xpath(PriceOptionXpath)).click();
								Thread.sleep(2000);
								if(ARNK_pricebestBuy.equalsIgnoreCase("yes")) 
									driver.findElement(By.xpath(BestBuyXpath)).click();
							}
								Thread.sleep(2000);
							//Click on Next
							driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Next')]")).click();
							FlightSearch.loadhandling(driver);
							//Wait until Loading wrapper closed
							//Click on File Fare
							driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'File Fare')]")).click();
							//Wait until Loading wrapper closed
							FlightSearch.loadhandling(driver);
							String amt=driver.findElement(By.xpath("//div[contains(@model,'currency.total')]//div")).getText();
							fareAmount=amt.trim();
							pnrcreated=true;
						}
					}
					if(ARNK_After.equalsIgnoreCase("yes")) {
						driver.findElement(By.xpath("//md-option[div[contains(text(),'ARNK After')]]")).click();
						FlightSearch.loadhandling(driver);
						queryObjects.logStatus(driver, Status.PASS, "ARNK After", "ARNK After Successful",null);
						if(!ARNK_quote.equalsIgnoreCase("no")) {
							driver.findElement(By.xpath("//md-select[@placeholder='Actions']")).click();
							Thread.sleep(1000);
							loadhandling(driver);
							driver.findElement(By.xpath("//div[contains(@class,'md-select-menu-container') and @aria-hidden='false']//md-option[div[contains(text(),'Quote')]]")).click();
	
							//Selecting Coupon check box if it is not checked already
							WebElement checkBox = driver.findElement(By.xpath("//md-checkbox[@aria-label='coupon-check']"));
							if(checkBox.getAttribute("aria-checked").equalsIgnoreCase("false"))
								checkBox.click();
							if(!ARNK_pricebestBuy.equalsIgnoreCase("")) {
								driver.findElement(By.xpath(PriceOptionXpath)).click();
								Thread.sleep(2000);
								if(ARNK_pricebestBuy.equalsIgnoreCase("yes")) 
									driver.findElement(By.xpath(BestBuyXpath)).click();
							}
								Thread.sleep(2000);
							//Click on Next
							driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'Next')]")).click();
							FlightSearch.loadhandling(driver);
							//Wait until Loading wrapper closed
							//Click on File Fare
							driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer')]//button[contains(text(),'File Fare')]")).click();
							//Wait until Loading wrapper closed
							FlightSearch.loadhandling(driver);
							String amt=driver.findElement(By.xpath("//div[contains(@model,'currency.total')]//div")).getText();
							fareAmount=amt.trim();
							pnrcreated=true;
						}
					}
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "ARNK", "Enter ARNK Segment",null);
				}
			}
			//end of ARNK code
		}
		public void coupon_control(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException, Exception{
			//Get the coupon control start from here written by Rushil		
			String CouponCtrl=FlightSearch.getTrimTdata(queryObjects.getTestData("CouponCtrl"));
			String CouponCtrl_Seg = FlightSearch.getTrimTdata(queryObjects.getTestData("CouponCtrl_Seg"));
			String CouponCtrl_Pax = FlightSearch.getTrimTdata(queryObjects.getTestData("CouponCtrl_PAX"));
			if (CouponCtrl.equals("yes")) {
				try {
					int frwcnt=0;
					int srwcnt=0;
				driver.findElement(By.xpath("//div[div[text()='Tickets']]")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//div[div[text()='Ticket']]")).click();
				Thread.sleep(1000);
				//deselect all checkbox
				if(driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'ticketCtrl.selectAll')]")).getAttribute("aria-checked").equals("false")) {
					driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'ticketCtrl.selectAll')]")).click();
					driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'ticketCtrl.selectAll')]")).click();
				}
				else {
					driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'ticketCtrl.selectAll')]")).click();
				}
				if (CouponCtrl_Pax.isEmpty()) {
					CouponCtrl_Pax = "ALL";
				}
				if (CouponCtrl_Seg.isEmpty()) {
					CouponCtrl_Seg = "ALL";
				}
				
				if (CouponCtrl_Pax.equals("ALL")) {
					List<WebElement> ipaxname = driver.findElements(By.xpath("//div[@class='pssgui-design-sub-heading-6 word-break']//span[@ng-if=' paxInfo.TicketedFullName ']"));
					int ipaxcount=ipaxname.size();
					if (CouponCtrl_Seg.equals("ALL")) {	
						for(int pxcnt=0;pxcnt<ipaxcount;pxcnt++) {
							driver.findElement(By.xpath("//span[@ng-if=' paxInfo.TicketedFullName ' and contains(text(),'"+ipaxname.get(pxcnt)+"')]//preceding::md-checkbox[1]")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath("(//span[@class='md-select-icon'])["+(pxcnt+1)+"]")).click();
							Thread.sleep(1000);
							try {
							driver.findElement(By.xpath("//div[contains(@class,'md-clickable')]//div[contains(text(),'Get Control')]/..")).click();
							Thread.sleep(10000);
							queryObjects.logStatus(driver, Status.PASS, "Get Control", "Tried coupon control",null);
							}catch(Exception e) {
								queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", e);
							}
							//verifying the status is open or not
							List<WebElement> irow = driver.findElements(By.xpath("(//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr"));
							int irowcnt=irow.size();
							for(int rwcnt=1;rwcnt<=irowcnt;rwcnt++) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
							}
							queryObjects.logStatus(driver, Status.PASS, "Get Control", "Coupon control successful",null);
							}
					} else {
						 for(int pxcnt=0;pxcnt<ipaxcount;pxcnt++) {
							List<WebElement> irow = driver.findElements(By.xpath("(//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr"));
							int irowcnt=irow.size();
							for(int rwcnt=1;rwcnt<=irowcnt;rwcnt++) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.origin')]")).getText().contains(CouponCtrl_Seg.substring(0, CouponCtrl_Seg.indexOf("-")))) {
									if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.destination')]")).getText().contains(CouponCtrl_Seg.substring(CouponCtrl_Seg.indexOf("-")+1, CouponCtrl_Seg.length()))) {
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'checkbox ')]")).click();
										frwcnt=rwcnt;
										break;
									}
									else if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+(rwcnt+1)+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.destination')]")).getText().contains(CouponCtrl_Seg.substring(CouponCtrl_Seg.indexOf("-")+1, CouponCtrl_Seg.length()))){
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'checkbox ')]")).click();
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+(rwcnt+1)+"]/td[contains(@class,'checkbox ')]")).click();
										frwcnt=rwcnt;
										srwcnt=(rwcnt+1);
										break;
									}
								}
							}
							driver.findElement(By.xpath("(//span[@class='md-select-icon'])["+(pxcnt+1)+"]")).click();
							Thread.sleep(1000);
							try {
								driver.findElement(By.xpath("//div[contains(@class,'md-clickable')]//div[contains(text(),'Get Control')]/..")).click();
								Thread.sleep(10000);
								queryObjects.logStatus(driver, Status.PASS, "Get Control", "Tried coupon control",null);
								}catch(Exception e) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", e);
								}
							//verifying the status is open or not
							if(srwcnt!= 0) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+frwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+srwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
								}
							else {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+frwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
							}
						}
					}
				} 
				else if (CouponCtrl_Pax.equals("Adult")) {
					List<WebElement> ipaxname = driver.findElements(By.xpath("//div[@class='pssgui-design-sub-heading-6 word-break']//span[@ng-if=' paxInfo.TicketedFullName ']"));
					int ipaxcount=ipaxname.size();
					if (CouponCtrl_Seg.equals("ALL")) {
						for(int pxcnt=0;pxcnt<ipaxcount;pxcnt++) {
							if(driver.findElement(By.xpath("//div[@class='pssgui-design-sub-heading-6 word-break']//span[@ng-if=' paxInfo.TicketedFullName 'and contains(text(),'"+ipaxname.get(pxcnt)+"')]//following-sibling::span[@ng-if='paxInfo.PassengerReductionTypeCode']")).getText().contains("ADT")) {
							driver.findElement(By.xpath("//span[@ng-if=' paxInfo.TicketedFullName ' and contains(text(),'"+ipaxname.get(pxcnt)+"')]//preceding::md-checkbox[1]")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath("(//span[@class='md-select-icon'])["+(pxcnt+1)+"]")).click();
							Thread.sleep(1000);
							try {
								driver.findElement(By.xpath("//div[contains(@class,'md-clickable')]//div[contains(text(),'Get Control')]/..")).click();
								Thread.sleep(10000);
								queryObjects.logStatus(driver, Status.PASS, "Get Control", "Tried coupon control",null);
								}catch(Exception e) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", e);
								}
							//verifying the status is open or not
							List<WebElement> irow = driver.findElements(By.xpath("(//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr"));
							int irowcnt=irow.size();
							for(int rwcnt=1;rwcnt<=irowcnt;rwcnt++) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
							}
							queryObjects.logStatus(driver, Status.PASS, "Get Control", "Coupon control successful",null);
							}
						}
						
					} else {
						for(int pxcnt=0;pxcnt<ipaxcount;pxcnt++) {
							if(driver.findElement(By.xpath("//div[@class='pssgui-design-sub-heading-6 word-break']//span[@ng-if=' paxInfo.TicketedFullName 'and contains(text(),'"+ipaxname.get(pxcnt)+"')]//following-sibling::span[@ng-if='paxInfo.PassengerReductionTypeCode']")).getText().contains("ADT")) {
							List<WebElement> irow = driver.findElements(By.xpath("(//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr"));
							int irowcnt=irow.size();
							for(int rwcnt=1;rwcnt<=irowcnt;rwcnt++) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.origin')]")).getText().contains(CouponCtrl_Seg.substring(0, CouponCtrl_Seg.indexOf("-")))) {
									if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.destination')]")).getText().contains(CouponCtrl_Seg.substring(CouponCtrl_Seg.indexOf("-")+1, CouponCtrl_Seg.length()))) {
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'checkbox ')]")).click();
										frwcnt=rwcnt;
										break;
									}
									else if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+(rwcnt+1)+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.destination')]")).getText().contains(CouponCtrl_Seg.substring(CouponCtrl_Seg.indexOf("-")+1, CouponCtrl_Seg.length()))){
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'checkbox ')]")).click();
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+(rwcnt+1)+"]/td[contains(@class,'checkbox ')]")).click();
										frwcnt=rwcnt;
										srwcnt=(rwcnt+1);
										break;
									}
								}
							}
							driver.findElement(By.xpath("(//span[@class='md-select-icon'])["+(pxcnt+1)+"]")).click();
							Thread.sleep(1000);
							try {
								driver.findElement(By.xpath("//div[contains(@class,'md-clickable')]//div[contains(text(),'Get Control')]/..")).click();
								Thread.sleep(10000);
								queryObjects.logStatus(driver, Status.PASS, "Get Control", "Tried coupon control",null);
								}catch(Exception e) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", e);
								}
							//verifying the status is open or not
							if(srwcnt!= 0) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+frwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+srwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
								}
							else {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+frwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
							}
						}
						}
					}
				}
				else if (CouponCtrl_Pax.equals("Child")) {

					List<WebElement> ipaxname = driver.findElements(By.xpath("//div[@class='pssgui-design-sub-heading-6 word-break']//span[@ng-if=' paxInfo.TicketedFullName ']"));
					int ipaxcount=ipaxname.size();
					if (CouponCtrl_Seg.equals("ALL")) {
						for(int pxcnt=0;pxcnt<ipaxcount;pxcnt++) {
							if(driver.findElement(By.xpath("//div[@class='pssgui-design-sub-heading-6 word-break']//span[@ng-if=' paxInfo.TicketedFullName 'and contains(text(),'"+ipaxname.get(pxcnt)+"')]//following-sibling::span[@ng-if='paxInfo.PassengerReductionTypeCode']")).getText().contains("CHD")) {
							driver.findElement(By.xpath("//span[@ng-if=' paxInfo.TicketedFullName ' and contains(text(),'"+ipaxname.get(pxcnt)+"')]//preceding::md-checkbox[1]")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath("(//span[@class='md-select-icon'])["+(pxcnt+1)+"]")).click();
							Thread.sleep(1000);
							try {
								driver.findElement(By.xpath("//div[contains(@class,'md-clickable')]//div[contains(text(),'Get Control')]/..")).click();
								Thread.sleep(10000);
								queryObjects.logStatus(driver, Status.PASS, "Get Control", "Tried coupon control",null);
								}catch(Exception e) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", e);
								}
							//verifying the status is open or not
							List<WebElement> irow = driver.findElements(By.xpath("(//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr"));
							int irowcnt=irow.size();
							for(int rwcnt=1;rwcnt<=irowcnt;rwcnt++) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
							}
							queryObjects.logStatus(driver, Status.PASS, "Get Control", "Coupon control successful",null);
							}
						}
					} else {
						for(int pxcnt=0;pxcnt<ipaxcount;pxcnt++) {
							if(driver.findElement(By.xpath("//div[@class='pssgui-design-sub-heading-6 word-break']//span[@ng-if=' paxInfo.TicketedFullName 'and contains(text(),'"+ipaxname.get(pxcnt)+"')]//following-sibling::span[@ng-if='paxInfo.PassengerReductionTypeCode']")).getText().contains("CHD")) {
							List<WebElement> irow = driver.findElements(By.xpath("(//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr"));
							int irowcnt=irow.size();
							for(int rwcnt=1;rwcnt<=irowcnt;rwcnt++) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.origin')]")).getText().contains(CouponCtrl_Seg.substring(0, CouponCtrl_Seg.indexOf("-")))) {
									if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.destination')]")).getText().contains(CouponCtrl_Seg.substring(CouponCtrl_Seg.indexOf("-")+1, CouponCtrl_Seg.length()))) {
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'checkbox ')]")).click();
										frwcnt=rwcnt;
										break;
									}
									else if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+(rwcnt+1)+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.destination')]")).getText().contains(CouponCtrl_Seg.substring(CouponCtrl_Seg.indexOf("-")+1, CouponCtrl_Seg.length()))){
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'checkbox ')]")).click();
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+(rwcnt+1)+"]/td[contains(@class,'checkbox ')]")).click();
										frwcnt=rwcnt;
										srwcnt=(rwcnt+1);
										break;
									}
								}
							}
							driver.findElement(By.xpath("(//span[@class='md-select-icon'])["+(pxcnt+1)+"]")).click();
							Thread.sleep(1000);
							try {
								driver.findElement(By.xpath("//div[contains(@class,'md-clickable')]//div[contains(text(),'Get Control')]/..")).click();
								Thread.sleep(10000);
								queryObjects.logStatus(driver, Status.PASS, "Get Control", "Tried coupon control",null);
								}catch(Exception e) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", e);
								}
							//verifying the status is open or not
							if(srwcnt!= 0) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+frwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+srwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
								}
							else {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+frwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
							}
						}
						}
					}
				
				}
				else if (CouponCtrl_Pax.equals("Infant without seat")) {


					List<WebElement> ipaxname = driver.findElements(By.xpath("//div[@class='pssgui-design-sub-heading-6 word-break']//span[@ng-if=' paxInfo.TicketedFullName ']"));
					int ipaxcount=ipaxname.size();
					if (CouponCtrl_Seg.equals("ALL")) {
						for(int pxcnt=0;pxcnt<ipaxcount;pxcnt++) {
							if(driver.findElement(By.xpath("//div[@class='pssgui-design-sub-heading-6 word-break']//span[@ng-if=' paxInfo.TicketedFullName 'and contains(text(),'"+ipaxname.get(pxcnt)+"')]//following-sibling::span[@ng-if='paxInfo.PassengerReductionTypeCode']")).getText().contains("INF")) {
							driver.findElement(By.xpath("//span[@ng-if=' paxInfo.TicketedFullName ' and contains(text(),'"+ipaxname.get(pxcnt)+"')]//preceding::md-checkbox[1]")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath("(//span[@class='md-select-icon'])["+(pxcnt+1)+"]")).click();
							Thread.sleep(1000);
							try {
								driver.findElement(By.xpath("//div[contains(@class,'md-clickable')]//div[contains(text(),'Get Control')]/..")).click();
								Thread.sleep(10000);
								queryObjects.logStatus(driver, Status.PASS, "Get Control", "Tried coupon control",null);
								}catch(Exception e) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", e);
								}
							//verifying the status is open or not
							List<WebElement> irow = driver.findElements(By.xpath("(//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr"));
							int irowcnt=irow.size();
							for(int rwcnt=1;rwcnt<=irowcnt;rwcnt++) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
							}
							queryObjects.logStatus(driver, Status.PASS, "Get Control", "Coupon control successful",null);
							}
						}
					} else {
						for(int pxcnt=0;pxcnt<ipaxcount;pxcnt++) {
							if(driver.findElement(By.xpath("//div[@class='pssgui-design-sub-heading-6 word-break']//span[@ng-if=' paxInfo.TicketedFullName 'and contains(text(),'"+ipaxname.get(pxcnt)+"')]//following-sibling::span[@ng-if='paxInfo.PassengerReductionTypeCode']")).getText().contains("INF")) {
							List<WebElement> irow = driver.findElements(By.xpath("(//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr"));
							int irowcnt=irow.size();
							for(int rwcnt=1;rwcnt<=irowcnt;rwcnt++) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.origin')]")).getText().contains(CouponCtrl_Seg.substring(0, CouponCtrl_Seg.indexOf("-")))) {
									if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.destination')]")).getText().contains(CouponCtrl_Seg.substring(CouponCtrl_Seg.indexOf("-")+1, CouponCtrl_Seg.length()))) {
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'checkbox ')]")).click();
										frwcnt=rwcnt;
										break;
									}
									else if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+(rwcnt+1)+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.destination')]")).getText().contains(CouponCtrl_Seg.substring(CouponCtrl_Seg.indexOf("-")+1, CouponCtrl_Seg.length()))){
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'checkbox ')]")).click();
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+(rwcnt+1)+"]/td[contains(@class,'checkbox ')]")).click();
										frwcnt=rwcnt;
										srwcnt=(rwcnt+1);
										break;
									}
								}
							}
							driver.findElement(By.xpath("(//span[@class='md-select-icon'])["+(pxcnt+1)+"]")).click();
							Thread.sleep(1000);
							try {
								driver.findElement(By.xpath("//div[contains(@class,'md-clickable')]//div[contains(text(),'Get Control')]/..")).click();
								Thread.sleep(10000);
								queryObjects.logStatus(driver, Status.PASS, "Get Control", "Tried coupon control",null);
								}catch(Exception e) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", e);
								}
							//verifying the status is open or not
							if(srwcnt!= 0) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+frwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+srwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
								}
							else {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+frwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
							}
						}
						}
					}
				}
				else if (CouponCtrl_Pax.equals("Infant with seat")) {
					List<WebElement> ipaxname = driver.findElements(By.xpath("//div[@class='pssgui-design-sub-heading-6 word-break']//span[@ng-if=' paxInfo.TicketedFullName ']"));
					int ipaxcount=ipaxname.size();
					if (CouponCtrl_Seg.equals("ALL")) {
						for(int pxcnt=0;pxcnt<ipaxcount;pxcnt++) {
							if(driver.findElement(By.xpath("//div[@class='pssgui-design-sub-heading-6 word-break']//span[@ng-if=' paxInfo.TicketedFullName 'and contains(text(),'"+ipaxname.get(pxcnt)+"')]//following-sibling::span[@ng-if='paxInfo.PassengerReductionTypeCode']")).getText().contains("INS")) {
							driver.findElement(By.xpath("//span[@ng-if=' paxInfo.TicketedFullName ' and contains(text(),'"+ipaxname.get(pxcnt)+"')]//preceding::md-checkbox[1]")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath("(//span[@class='md-select-icon'])["+(pxcnt+1)+"]")).click();
							Thread.sleep(1000);
							try {
								driver.findElement(By.xpath("//div[contains(@class,'md-clickable')]//div[contains(text(),'Get Control')]/..")).click();
								Thread.sleep(10000);
								queryObjects.logStatus(driver, Status.PASS, "Get Control", "Tried coupon control",null);
								}catch(Exception e) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", e);
								}
							//verifying the status is open or not
							List<WebElement> irow = driver.findElements(By.xpath("(//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr"));
							int irowcnt=irow.size();
							for(int rwcnt=1;rwcnt<=irowcnt;rwcnt++) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
							}
							queryObjects.logStatus(driver, Status.PASS, "Get Control", "Coupon control successful",null);
							}
						}
					} else {
						for(int pxcnt=0;pxcnt<ipaxcount;pxcnt++) {
							if(driver.findElement(By.xpath("//div[@class='pssgui-design-sub-heading-6 word-break']//span[@ng-if=' paxInfo.TicketedFullName 'and contains(text(),'"+ipaxname.get(pxcnt)+"')]//following-sibling::span[@ng-if='paxInfo.PassengerReductionTypeCode']")).getText().contains("INS")) {
							List<WebElement> irow = driver.findElements(By.xpath("(//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr"));
							int irowcnt=irow.size();
							for(int rwcnt=1;rwcnt<=irowcnt;rwcnt++) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.origin')]")).getText().contains(CouponCtrl_Seg.substring(0, CouponCtrl_Seg.indexOf("-")))) {
									if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.destination')]")).getText().contains(CouponCtrl_Seg.substring(CouponCtrl_Seg.indexOf("-")+1, CouponCtrl_Seg.length()))) {
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'checkbox ')]")).click();
										frwcnt=rwcnt;
										break;
									}
									else if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+(rwcnt+1)+"]/td[contains(@class,'od-segment')]//div[contains(@airport-code,'.destination')]")).getText().contains(CouponCtrl_Seg.substring(CouponCtrl_Seg.indexOf("-")+1, CouponCtrl_Seg.length()))){
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+rwcnt+"]/td[contains(@class,'checkbox ')]")).click();
										driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+(rwcnt+1)+"]/td[contains(@class,'checkbox ')]")).click();
										frwcnt=rwcnt;
										srwcnt=(rwcnt+1);
										break;
									}
								}
							}
							driver.findElement(By.xpath("(//span[@class='md-select-icon'])["+(pxcnt+1)+"]")).click();
							Thread.sleep(1000);
							try {
								driver.findElement(By.xpath("//div[contains(@class,'md-clickable')]//div[contains(text(),'Get Control')]/..")).click();
								Thread.sleep(10000);
								queryObjects.logStatus(driver, Status.PASS, "Get Control", "Tried coupon control",null);
								}catch(Exception e) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", e);
								}
							//verifying the status is open or not
							if(srwcnt!= 0) {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+frwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+srwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
								}
							else {
								if(driver.findElement(By.xpath("((//md-content/div[contains(@class,'pax-info')])["+(pxcnt+1)+"]//tbody/tr)["+frwcnt+"]/td[contains(@ng-if,'segment.ticketStatusUpdate')]//span")).getText().contains("CNTRL")) {
									queryObjects.logStatus(driver, Status.FAIL, "Get Control", "Failed to get coupon control", null);
								}
							}
						}
						}
					}
				}
				}catch(Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Coupon control", "Failed to do coupon control ", e);	
				}
				}
			
			//coupon control ends here
		}
		
		//Start Navira - Baggage Allowance Verification - 27Feb
		public static void Baggage_allowance(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {
			try{
				driver.findElement(By.xpath("//div[text()='Summary']")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[toggle-title[span[text()='Baggage Allowance']]]/preceding-sibling::i")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//div[toggle-title[span[text()='Baggage Rules']]]/preceding-sibling::i")).click();
				Thread.sleep(2000);
				
				queryObjects.logStatus(driver, Status.PASS, "Baggage Allowance Verification", "Baggage Allowance Verification successful", null);	
				List<WebElement> arrRules = driver.findElements(By.xpath("//toggle-content/div[contains(@ng-repeat,'rules in baggage')]"));
				for(int iter1=0;iter1<arrRules.size();iter1++)
					queryObjects.logStatus(driver, Status.PASS, "Baggage Allowance Verification", "Baggage Rules Value is :"+arrRules.get(iter1).getText(), null);	

			}
			catch(Exception e) {
				queryObjects.logStatus(driver, Status.PASS, "Baggage Allowance Verification", "Baggage Allowance Verification not successful", null);	
			}
			//End
		}
		//Navira - 22Mar
		public static void emdConfirmation(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {
			
			if(driver.findElements(By.xpath("//md-checkbox[@aria-label='check-all' and @aria-checked='false']")).size()>0)
				driver.findElement(By.xpath("//md-checkbox[@aria-label='check-all']")).click();
			driver.findElement(By.xpath("//button[@aria-label='email EMD']")).click();
			Thread.sleep(2000);
			emailhandling(driver, queryObjects);
			Thread.sleep(3000);
			boolean errmessage = driver.findElements(By.xpath("//span[text()='E-Mail has been sent.']")).size()>0;
			if(errmessage)
				queryObjects.logStatus(driver, Status.PASS, "Verifying if the confirmation email is sent", "Confirmation mail sent successfully", null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Verifying if the confirmation email is sent", "Confirmation mail is not sent", null);
			
		}
		
		
		public void couponvalidation(WebDriver driver, BFrameworkQueryObjects queryObjects,String expMsg) throws Exception{
			boolean retval=Unwholly.getpnrinexcel(driver, queryObjects);
			driver.findElement(By.xpath("//div[div[text()='Tickets']]")).click();
			
			List<WebElement> reissueticketnumbers=driver.findElements(By.xpath("//span[contains(@class,'primary-ticket-number')]"));
			String ticketnumber=reissueticketnumbers.get(0).getText().trim();
			List<WebElement> currentticketstate=driver.findElements(By.xpath("//span[text()='"+ticketnumber+"']/parent::div/parent::div//td[contains(@ng-if,'ticketStatusUpdate')]"));
			String couponstate=currentticketstate.get(0).getText().trim();
			if(couponstate.equalsIgnoreCase(expMsg))
				queryObjects.logStatus(driver, Status.PASS," Coupon status checking"," Coupon status displaying correctly ",null);
			else
				queryObjects.logStatus(driver, Status.FAIL," Coupon status checking"," Coupon status should display correctly Actual :"+couponstate+" Expected :"+expMsg,null);

		}
	
}


